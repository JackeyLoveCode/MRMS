/**  
 * Project Name:MRMS  
 * File Name:FavoriteController.java  
 * Package Name:com.hiveview.mrms.controller  
 * Date:2018年11月22日下午2:05:25  
 * Copyright (c) 2018, 1570964489@qq.com All Rights Reserved.  
 *  
*/  
  
package com.hiveview.mrms.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.hiveview.mrms.pojo.AjaxResult;
import com.hiveview.mrms.pojo.Condition;
import com.hiveview.mrms.pojo.EasyUIDatagridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hiveview.mrms.pojo.User;
import com.hiveview.mrms.service.FavoriteService;

/**  
 * ClassName:FavoriteController <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason:   TODO ADD REASON. <br/>  
 * Date:     2018年11月22日 下午2:05:25 <br/>  
 * @author   Administrator  
 * @version    
 * @since    JDK 1.6  
 * @see        
 */
@Controller
@RequestMapping("favorite/")
public class FavoriteController {
	
	@Autowired
	private FavoriteService favoriteService;
	
	@RequestMapping("list")
	@ResponseBody
	public EasyUIDatagridResult list(HttpSession session, Condition condition) {
		EasyUIDatagridResult easyUIDatagridResult = new EasyUIDatagridResult();
		try{
			User user = (User) session.getAttribute("user");
			if(user == null) {
				easyUIDatagridResult.setStatus(-1);
				easyUIDatagridResult.setMsg("用户未登录");
				return easyUIDatagridResult;
			}
			easyUIDatagridResult = favoriteService.findAll(user,condition);
			easyUIDatagridResult.setStatus(200);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return easyUIDatagridResult;
	}
	
	@RequestMapping("cancle")
	@ResponseBody
	public AjaxResult cancle(Integer id,HttpSession session) {
		AjaxResult ajaxResult = new AjaxResult();
		User user = (User) session.getAttribute("user");
		boolean success =  favoriteService.cancle(id,user);
		if(success){
			ajaxResult.setStatus(200);
			ajaxResult.setMsg("操作成功");
		}
		return ajaxResult;
	}
}
  
