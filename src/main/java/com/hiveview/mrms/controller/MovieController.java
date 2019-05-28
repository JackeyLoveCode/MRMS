/**  
 * Project Name:MRMS  
 * File Name:MovieController.java  
 * Package Name:com.hiveview.mrms.controller  
 * Date:2018年11月13日上午11:10:11  
 * Copyright (c) 2018, 1570964489@qq.com All Rights Reserved.  
 *  
*/  
  
package com.hiveview.mrms.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.soap.Detail;

import com.alibaba.fastjson.JSONObject;
import com.hiveview.mrms.pojo.*;
import com.hiveview.mrms.service.FavoriteService;
import com.sun.imageio.plugins.common.ImageUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;

import com.hiveview.mrms.service.MovieService;
import com.hiveview.mrms.util.UploadImageUtil;

import javafx.scene.chart.PieChart.Data;

/**  
 * ClassName:MovieController <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason:   TODO ADD REASON. <br/>  
 * Date:     2018年11月13日 上午11:10:11 <br/>  
 * @author   Administrator  
 * @version    
 * @since    JDK 1.6  
 * @see        
 */
@Controller
@RequestMapping("movie/")
public class MovieController {

	private static Logger logger = Logger.getLogger(MovieController.class);

	@Value("${img_upload_path}")
	private  String IMG_UPLOAD_PATH;
	
	
	
	@Autowired
	private MovieService movieService;
	
	@Autowired
	private FavoriteService favoriteService;
	
	/**
	 * 
	 * findAllTypes:查询所有分类，初始化下拉列表 
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>  
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/>  
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>  
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>  
	 *  
	 * @author Administrator  
	 * @return  
	 * @since JDK 1.6
	 */
	@RequestMapping("findAllTypes")
	@ResponseBody
	public List<Map<String,Object>> findAllTypes(){
		List<Map<String, Object>> movieTypes = movieService.findAllTypes();
		return movieTypes;
	}
	
	/**
	 * 
	 * save:保存数据 
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>  
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/>  
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>  
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>  
	 *  
	 * @author Administrator  
	 * @param file
	 * @param request
	 * @return  
	 * @since JDK 1.6
	 */
	@RequestMapping("save")
	@ResponseBody
	public AjaxResult save(@RequestParam(value="imageFile", required=false) MultipartFile file,
							HttpServletRequest request) {
		MultipartHttpServletRequest req = (MultipartHttpServletRequest)request;
		Movie movie = this.initMovieInstance(req);
		String fileName = UploadImageUtil.getImageName(file);
        movie.setImgPath(IMG_UPLOAD_PATH+fileName);
        //向数据库写入数据
        AjaxResult ajaxResult = movieService.save(movie);
        UploadImageUtil.uploadImage(IMG_UPLOAD_PATH,file, request); 
		return ajaxResult;
		
	}
	
	
	/**
	 * 
	 * findAllMovies:分页查询所有电影
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>  
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/>  
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>  
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>  
	 *  
	 * @author Administrator  
	 * @param condition
	 * @return  
	 * @since JDK 1.6
	 */
	@RequestMapping("list")
	@ResponseBody
	public EasyUIDatagridResult findAllMovies(Condition condition,Model model){
		logger.info("/movie/list->MovieController->findAllMovies:[page="+condition.getPage()+"rows="+condition.getRows()+"]");
		model.addAttribute("page", condition.getPage());
		EasyUIDatagridResult easyUIDatagridResult = movieService.findAllMovies(condition);
		logger.info("/movie/list->MovieController->findAllMovies:["+ JSONObject.toJSONString(easyUIDatagridResult) +"]");
		return easyUIDatagridResult;
	}
	
	
	/**
	 * 
	 * favorite:收藏  
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>  
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/>  
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>  
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>  
	 *  
	 * @author Administrator  
	 * @param
	 * @param session
	 * @return  
	 * @since JDK 1.6
	 */
	@RequestMapping("favorite")
	@ResponseBody
	public AjaxResult favorite(Integer movieId,HttpSession session) {
		User user = (User) session.getAttribute("user");
		if(user == null) {
			return new AjaxResult(201, "用户未登录", null);
		}
		AjaxResult ajaxResult = movieService.favorite(user.getId(),movieId);
		return ajaxResult;
	}
	/**
	 * 
	 * Detail:查看电影详情 
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>  
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/>  
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>  
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>  
	 *  
	 * @author Administrator  
	 * @param id
	 * @return  
	 * @since JDK 1.6
	 */
	@RequestMapping("detail")
	public ModelAndView Detail(HttpServletRequest request,Integer id) {
		ModelAndView modelAndView = new ModelAndView();
		Map<String, Object> movie = movieService.findOneById(id);
		List<Map<String, Object>> movieTypes = movieService.findAllTypes();
		for (Map<String, Object> map : movieTypes) {
			if((Integer)map.get("id") == -1) {
				map.clear();
			}
		}
		//检验Session是否过期
		HttpSession session = request.getSession(false);
		if(session == null){
			modelAndView.addObject("isNeedToLogin",true);
			modelAndView.setViewName("movieDetail");
			return modelAndView;
		}
		modelAndView.addObject("isNeedToLogin",false);
		//设置Session间隔时间
		session.setMaxInactiveInterval(3600);
		User user = (User) session.getAttribute("user");
		if(user != null){
			Favorite favorite = new Favorite();
			favorite.setUserid(user.getId());
			favorite.setMovieid(id);
			favorite = favoriteService.findByCon(favorite);
			//检验该电影是否被用户收藏
			modelAndView.addObject("isFavorited",favorite == null ? false:true);
		}
		modelAndView.addObject("movie", movie);
		modelAndView.addObject("movieTypes", movieTypes);
		modelAndView.setViewName("movieDetail");
		return modelAndView;
	}
	/**
	 * 
	 * search:搜索电影 
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>  
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/>  
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>  
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>  
	 *  
	 * @author Administrator  
	 * @param searchText
	 * @return  
	 * @since JDK 1.6
	 */
	@RequestMapping("search")
	@ResponseBody
	public EasyUIDatagridResult search(String searchText){
		EasyUIDatagridResult easyUIDatagridResult = movieService.searchMovie(searchText);
		return easyUIDatagridResult;
	}
	/**
	 * 
	 * listByCon:多条件查询电影  
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>  
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/>  
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>  
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>  
	 *  
	 * @author Administrator  
	 * @param condition
	 * @return  
	 * @since JDK 1.6
	 */
	@RequestMapping("listByCon")
	@ResponseBody
	public List<Map<String, Object>> listByCon(Condition condition){
		List<Map<String, Object>> movies = movieService.queryByCondition(condition);
		return movies;
	}
	/*
	 * 回显
	 */
	@RequestMapping("edit")
	@ResponseBody
	public Map<String, Object> backShow(Integer id) {
		Map<String, Object> map = movieService.findOneById(id);
		return map;
	}
	
	@RequestMapping("update")
	@ResponseBody
	public AjaxResult updateMovie(@RequestParam(value="imageFile", required=false) MultipartFile file,
			HttpServletRequest request) {
		MultipartHttpServletRequest req = (MultipartHttpServletRequest)request;
		Movie movie = this.initMovieInstance(req);
		if(!"".equals(file.getOriginalFilename())){
			String fileName = UploadImageUtil.getImageName(file);
			movie.setImgPath(IMG_UPLOAD_PATH+fileName);
		}
		//向数据库写入数据
		AjaxResult ajaxResult = movieService.updateMovie(movie);
		UploadImageUtil.uploadImage(IMG_UPLOAD_PATH,file, request);
		return ajaxResult;
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public AjaxResult remove(@RequestParam("ids") ArrayList<Integer> ids) {
		AjaxResult ajaxResult = movieService.delete(ids);
		return ajaxResult;
	}
	
	@RequestMapping("findAll")
	@ResponseBody
	public List<Map<String, Object>> findAll(){
		List<Map<String, Object>> movies = movieService.selectAll();
		return movies;
	}

	@RequestMapping("getRecommendMovies")
	@ResponseBody
	public List<Movie> getRecommendMovies(){
		try{
			List<Movie> recommendMovies = movieService.findRecommentMovies();
			return recommendMovies;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public Movie initMovieInstance(MultipartHttpServletRequest request) {
		Movie movie = new Movie();
		//接收请求参数
		if(request.getParameter("id") != null) {
			movie.setId(Integer.parseInt(request.getParameter("id")));
		}
		String title = request.getParameter("title");
		String actors = request.getParameter("actors");
		Integer type = Integer.parseInt(request.getParameter("type"));
		String area = request.getParameter("area");
		String language = request.getParameter("language");
		String director = request.getParameter("director");
		String moviePath = request.getParameter("moviePath");
		if(request.getParameter("imagePath") != null && request.getParameter("imagePath") != "") {
			movie.setImgPath(request.getParameter("imagePath"));
		}
		String time = request.getParameter("time");
		String plot = request.getParameter("plot");
		Date timeDate = null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			timeDate = simpleDateFormat.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();  
		}
		movie.setTitle(title);
		movie.setActors(actors);
		movie.setType(type);
		movie.setArea(area);
		movie.setLanguage(language);
		movie.setDirector(director);
		movie.setMoviePath(moviePath);
		movie.setTime(timeDate);
		movie.setPlot(plot);
		return movie;
	}
	
	
	
	
	
	
	
	
	
	
}
  
