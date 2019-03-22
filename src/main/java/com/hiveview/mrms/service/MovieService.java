/**  
 * Project Name:MRMS  
 * File Name:MovieService.java  
 * Package Name:com.hiveview.mrms.service  
 * Date:2018年11月13日上午11:11:08  
 * Copyright (c) 2018, 1570964489@qq.com All Rights Reserved.  
 *  
*/  
  
package com.hiveview.mrms.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hiveview.mrms.mapper.FavoriteMapper;
import com.hiveview.mrms.mapper.MovieMapper;
import com.hiveview.mrms.mapper.MovieTypeMapper;
import com.hiveview.mrms.pojo.AjaxResult;
import com.hiveview.mrms.pojo.Condition;
import com.hiveview.mrms.pojo.EasyUIDatagridResult;
import com.hiveview.mrms.pojo.Favorite;
import com.hiveview.mrms.pojo.FavoriteExample;
import com.hiveview.mrms.pojo.Movie;
import com.hiveview.mrms.pojo.MovieExample;
import com.hiveview.mrms.pojo.MovieType;
import com.hiveview.mrms.pojo.MovieTypeExample;

/**  
 * ClassName:MovieService <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason:   TODO ADD REASON. <br/>  
 * Date:     2018年11月13日 上午11:11:08 <br/>  
 * @author   Administrator  
 * @version    
 * @since    JDK 1.6  
 * @see        
 */
@Service
public class MovieService {
	@Autowired
	private MovieMapper movieMapper;

	@Autowired
	private FavoriteMapper favoriteMapper;
	
	@Autowired
	private MovieTypeMapper movieTypeMapper;
	
	public AjaxResult favorite(Integer userId, Integer movieId) {
		FavoriteExample example = new FavoriteExample();
		example.createCriteria().andUseridEqualTo(userId).andMovieidEqualTo(movieId);
		List<Favorite> favorites = favoriteMapper.selectByExample(example);
		if(favorites != null && favorites.size() > 0) {
			return new AjaxResult(202,"用户已收藏",null);
		}
		Favorite favorite = new Favorite();  
		favorite.setMovieid(movieId);
		favorite.setUserid(userId);
		favorite.setCollectionTime(new Date());
		favorite.setState((short) 1);//1:已收藏，2：未收藏
		int result = 0;
		result += favoriteMapper.insert(favorite);
		Movie movie = movieMapper.selectByPrimaryKey(movieId);
		movie.setFavoriteTimes(movie.getFavoriteTimes() + 1);
		result += movieMapper.updateByPrimaryKey(movie);
		if(result == 2){
			return new AjaxResult(200, "收藏成功", null);
		}
		return null;
	}
	
	public List<String> findAllArea(){
		List<String> areas= new ArrayList<>();
		areas.add("港台");
		areas.add("香港");
		areas.add("台湾");
		areas.add("欧美");
		areas.add("美国");
		areas.add("大陆");
		areas.add("日本");
		areas.add("韩国");
		return areas;
	}
	/**
	 * 
	 * findAllTypes:查找所有电影类型  
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>  
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/>  
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>  
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>  
	 *  
	 * @author Administrator  
	 * @return  
	 * @since JDK 1.6
	 */
	public List<Map<String, Object>> findAllTypes() {	
		  MovieTypeExample example = new MovieTypeExample();
		  List<MovieType> movieTypes = movieTypeMapper.selectByExample(example);
		  List<Map<String, Object>> seList = new ArrayList<>();
		  for (MovieType movieType : movieTypes) {
			HashMap<String, Object> map = new HashMap<>();
			map.put("id",movieType.getId());
			map.put("text",movieType.getName());
			seList.add(map);
		  }
		  Map<String,Object> map = new HashMap<String,Object>();
		  map.put("id", -1);
		  map.put("text", "请选择");
		  seList.add(map);
		  return seList;
	}

	/**
	 * 
	 * save:保存电影到数据库 
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>  
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/>  
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>  
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>  
	 *  
	 * @author Administrator  
	 * @param movie
	 * @return  
	 * @since JDK 1.6
	 */
	public AjaxResult save(Movie movie) {
		AjaxResult ajaxResult = new AjaxResult();  
		int success = movieMapper.insert(movie);
		if(success == 1) {
			ajaxResult.setStatus(200);
			ajaxResult.setMsg("添加成功");
			return ajaxResult;
		}
		ajaxResult.setStatus(-1);
		ajaxResult.setMsg("未知错误");
		return ajaxResult;
	}

	
	/**
	 * 
	 * findAllMovies:寻找所有电影  
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
	public EasyUIDatagridResult findAllMovies(Condition condition) {
		EasyUIDatagridResult easyUIDatagridResult = new EasyUIDatagridResult();
		easyUIDatagridResult.setPage(condition.getPage());
		PageHelper.startPage(condition.getPage(), condition.getRows());
		MovieExample example = new MovieExample();
		MovieExample.Criteria criteria = example.createCriteria();
		if(condition.getArea() != null && !"全部".equals(condition.getArea())){
			criteria.andAreaEqualTo(condition.getArea());
		}
		if(condition.getType() != null && !"全部".equals(condition.getType())){
			Integer targetType = 0;
			MovieTypeExample movieTypeExample = new MovieTypeExample();
			List<MovieType> movieTypes = movieTypeMapper.selectByExample(movieTypeExample);
			for (MovieType movieType : movieTypes) {
				if(condition.getType().equals(movieType.getName())){
					targetType = movieType.getId();
				}
			}
			criteria.andTypeEqualTo(targetType);
		}
		if(condition.getYear() != null && !"全部".equals(condition.getYear())){
			try {
				criteria.andTimeEqualTo(new SimpleDateFormat("yyyy").parse(condition.getYear()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		List<Movie> movies = movieMapper.selectByExample(example);
		List<Map<String, Object>> finalList = new ArrayList<>();
		for (Movie movie : movies) {
			HashMap<String, Object> map = new HashMap<>();
			map.put("id", movie.getId());
			map.put("title", movie.getTitle());
			map.put("actors", movie.getActors());
			map.put("area", movie.getArea());
			map.put("imgPath",movie.getImgPath());
			MovieType movieType= movieTypeMapper.selectByPrimaryKey(movie.getType());
			map.put("type", movieType.getName());
			map.put("plot", movie.getPlot());
			map.put("language", movie.getLanguage());
			map.put("moviePath", movie.getMoviePath());
			map.put("director", movie.getDirector());
			map.put("time",new SimpleDateFormat("yyyy-MM-dd").format(movie.getTime()));
			map.put("playTimes",movie.getPlayTimes() == null ? 0 : movie.getPlayTimes());
			map.put("favoriteTimes",movie.getFavoriteTimes() == null ? 0 : movie.getFavoriteTimes());
			finalList.add(map);
		}
		PageInfo<Movie> pageInfo = new PageInfo<>(movies);
		easyUIDatagridResult.setPage(condition.getPage());
		easyUIDatagridResult.setPerPageRows(condition.getRows());
		easyUIDatagridResult.setRows(finalList);
		easyUIDatagridResult.setTotal(pageInfo.getTotal());
		return easyUIDatagridResult;
	}

	public Map<String, Object> findOneById(Integer id) {  
		Movie movie = movieMapper.selectByPrimaryKey(id);
		HashMap<String, Object> map = new HashMap<>();
		map.put("id", movie.getId());
		map.put("title", movie.getTitle());
		map.put("actors", movie.getActors());
		MovieType movieType= movieTypeMapper.selectByPrimaryKey(movie.getType());
		map.put("name", movieType.getName());
		map.put("type", movie.getType());
		map.put("moviePath",movie.getMoviePath());
		map.put("imgPath",movie.getImgPath());
		map.put("language", movie.getLanguage());
		map.put("director", movie.getDirector());
		map.put("plot", movie.getPlot());
		map.put("time",new SimpleDateFormat("yyyy-MM-dd").format(movie.getTime()));
		return map;
	}

	public AjaxResult updateMovie(Movie movie) {
		AjaxResult ajaxResult = new AjaxResult();
		int code = movieMapper.updateByPrimaryKeySelective(movie);
		if(code == 1) {
			ajaxResult.setStatus(200);
			ajaxResult.setMsg("修改成功");
			return ajaxResult;
		}
		ajaxResult.setStatus(-1);
		ajaxResult.setMsg("发生错误");
		return ajaxResult;
	}

	/**
	 * 
	 * removeAllMovie:批量删除电影  
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>  
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/>  
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>  
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>  
	 *  
	 * @author Administrator  
	 * @param ids
	 * @return  
	 * @since JDK 1.6
	 */
	public AjaxResult delete(ArrayList<Integer> ids) {
		AjaxResult ajaxResult = new AjaxResult();
		int count = 0;  
		if(ids != null && ids.size() > 0) {
			for (Integer id : ids) {
				count += movieMapper.deleteByPrimaryKey(id);
			}
		}
		if(ids.size() == count) {
			ajaxResult.setStatus(200);
			ajaxResult.setMsg("删除成功");
			return ajaxResult;
		}
		ajaxResult.setStatus(-1);	
		return ajaxResult;
	}
	
	public List<Map<String, Object>> queryByCondition(Condition condition) {
		  
		List<Map<String, Object>> movies = movieMapper.queryByCondition(condition);
		for (Map<String, Object> movie : movies) {
			Set<String> keys = movie.keySet();
			for (String key : keys) {
				if("time".equals(key)){
					movie.put("time",new SimpleDateFormat("yyyy-MM-dd").format(movie.get("time")));
				}
				if("type".equals(key)){
					movie.put("type",movie.get("name"));
				}
			}
		}
		int total = movieMapper.countByCondition(condition);
		condition.setTotal(total);
		condition.setTotalPages(condition.getTotalPages());
		Map<String,Object> conditionMap = new HashMap<String,Object>();
		conditionMap.put("condition",condition);
		movies.add(0,conditionMap);
		return movies;
	}
	public EasyUIDatagridResult searchMovie(String searchText) {
		EasyUIDatagridResult easyUIDatagridResult = new EasyUIDatagridResult();
		searchText = searchText.trim();
		List<Map<String, Object>> movies = movieMapper.selectMovieBytitleOrActors(searchText);
		for (Map<String, Object> movie : movies) {
			Set<String> keys = movie.keySet();
			for (String key : keys) {
				if("time".equals(key)){
					String timeStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(movie.get("time"));
					movie.put("time",timeStr);
				}
			}
		}
		easyUIDatagridResult.setRows(movies);
		easyUIDatagridResult.setTotal(movies == null || movies.size() == 0 ?  0 : movies.size());
		return easyUIDatagridResult;
	}

	public List<Map<String, Object>> selectAll() {
		MovieExample example = new MovieExample();
		List<Movie> movies = movieMapper.selectByExample(example);
		List<Map<String, Object>> finalList = new ArrayList<>();
		for (Movie movie : movies) {
			HashMap<String, Object> map = new HashMap<>();
			map.put("id", movie.getId());
			map.put("title", movie.getTitle());
			map.put("actors", movie.getActors());
			map.put("area", movie.getArea());
			map.put("imgPath",movie.getImgPath());
			MovieType movieType= movieTypeMapper.selectByPrimaryKey(movie.getType());
			map.put("type", movieType.getName());
			map.put("plot", movie.getPlot());
			map.put("language", movie.getLanguage());
			map.put("moviePath", movie.getMoviePath());
			map.put("director", movie.getDirector());
			map.put("time",new SimpleDateFormat("yyyy-MM-dd").format(movie.getTime()));
			finalList.add(map);
		}  
		 
		return finalList;
	}


	public List<Movie> findRecommentMovies() {
		List<Movie> movies = movieMapper.getMoviesOrderByPlayTimes();
		return movies;
	}
}
  

