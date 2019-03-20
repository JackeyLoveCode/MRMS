/**  
 * Project Name:MRMS  
 * File Name:IndexController.java  
 * Package Name:com.hiveview.mrms.controller  
 * Date:2018年11月17日下午8:15:38  
 * Copyright (c) 2018, 1570964489@qq.com All Rights Reserved.  
 *  
*/  
  
package com.hiveview.mrms.controller;  
/**  
 * ClassName:IndexController <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason:   TODO ADD REASON. <br/>  
 * Date:     2018年11月17日 下午8:15:38 <br/>  
 * @author   Administrator  
 * @version    
 * @since    JDK 1.6  
 * @see        
 */

import java.util.List;
import java.util.Map;

import com.hiveview.mrms.pojo.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hiveview.mrms.pojo.EasyUIDatagridResult;
import com.hiveview.mrms.service.MovieService;

@Controller
public class IndexController {
	@Autowired
	private MovieService movieService;
	
	/**
	 * 
	 * toIndexPage:准备数据  
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>  
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/>  
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>  
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>  
	 *  
	 * @author Administrator  
	 * @param page
	 * @param rows
	 * @return  
	 * @since JDK 1.6
	 */
	@RequestMapping("index")
	public ModelAndView toIndexPage(@RequestParam(value="page",required=false,defaultValue="1")Integer page,
									@RequestParam(value="rows",required=false,defaultValue="3")Integer rows) {
		ModelAndView modelAndView = new ModelAndView();
		Condition condition = new Condition();
		condition.setPage(1);
		condition.setRows(3);
		EasyUIDatagridResult easyUIDatagridResult = movieService.findAllMovies(condition);
		List<Map<String, Object>> movieTypes = movieService.findAllTypes();
		List<String> areas = movieService.findAllArea();
		for (Map<String, Object> map : movieTypes) {
			if((Integer)map.get("id") == -1) {
				map.clear();
			}
		}
		modelAndView.addObject("areas", areas);
		modelAndView.addObject("movieTypes", movieTypes);
		modelAndView.addObject("movies", easyUIDatagridResult.getRows());
		modelAndView.setViewName("index");
		return modelAndView;
	}

}
  



