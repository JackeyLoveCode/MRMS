/**  
 * Project Name:MRMS  
 * File Name:MovieTypeController.java  
 * Package Name:com.hiveview.mrms.controller  
 * Date:2018年11月15日上午10:54:20  
 * Copyright (c) 2018, 1570964489@qq.com All Rights Reserved.  
 *  
*/  
  
package com.hiveview.mrms.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hiveview.mrms.pojo.AjaxResult;
import com.hiveview.mrms.pojo.EasyUIDatagridResult;
import com.hiveview.mrms.pojo.MovieType;
import com.hiveview.mrms.service.MovieTypeService;

/**  
 * ClassName:MovieTypeController <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason:   TODO ADD REASON. <br/>  
 * Date:     2018年11月15日 上午10:54:20 <br/>  
 * @author   Administrator  
 * @version    
 * @since    JDK 1.6  
 * @see        
 */
@Controller
@RequestMapping("movieType")
public class MovieTypeController {
	
	@Autowired
	private MovieTypeService movieTypeService;
	
	@RequestMapping("findAll")
	@ResponseBody
	public EasyUIDatagridResult list(Integer page,Integer rows) {
		EasyUIDatagridResult easyUIDatagridResult = movieTypeService.findAll(page,rows);
		return easyUIDatagridResult;
	}
	
	@RequestMapping("save")
	@ResponseBody
	public AjaxResult save(MovieType movieType) {
		AjaxResult ajaxResult = movieTypeService.save(movieType);
		return ajaxResult;
	}
	
	@RequestMapping("update")
	@ResponseBody
	public AjaxResult update(MovieType movieType) {
		AjaxResult ajaxResult = movieTypeService.update(movieType);
		return ajaxResult;
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public AjaxResult delete(@RequestParam("ids")ArrayList<Integer> ids) {
		AjaxResult ajaxResult = movieTypeService.delete(ids);
		return ajaxResult;
	}
}
  
