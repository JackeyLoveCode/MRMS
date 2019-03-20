/**  
 * Project Name:MRMS  
 * File Name:UserController.java  
 * Package Name:com.hiveview.mrms.controller  
 * Date:2018年11月15日下午2:29:54  
 * Copyright (c) 2018, 1570964489@qq.com All Rights Reserved.  
 *  
*/  
  
package com.hiveview.mrms.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hiveview.mrms.pojo.AjaxResult;
import com.hiveview.mrms.pojo.EasyUIDatagridResult;
import com.hiveview.mrms.pojo.User;
import com.hiveview.mrms.service.UserService;

/**  
 * ClassName:UserController <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason:   TODO ADD REASON. <br/>  
 * Date:     2018年11月15日 下午2:29:54 <br/>  
 * @author   Administrator  
 * @version    
 * @since    JDK 1.6  
 * @see        
 */
@Controller
@RequestMapping("user/")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("list")
	@ResponseBody
	public EasyUIDatagridResult list(Integer page,Integer rows) {
		EasyUIDatagridResult easyUIDatagridResult = new EasyUIDatagridResult();
		easyUIDatagridResult = userService.findAll(page,rows);
		return easyUIDatagridResult;
	}
	
	@RequestMapping("save")
	@ResponseBody
	public AjaxResult save(User user) {
		AjaxResult ajaxResult = userService.save(user);
		return ajaxResult;
	}
	
	@RequestMapping("update")
	@ResponseBody
	public AjaxResult update(User user) {
		AjaxResult ajaxResult= userService.update(user);
		return ajaxResult;
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public AjaxResult delete(@RequestParam("ids")ArrayList<Integer> ids) {
		AjaxResult ajaxResult = userService.deleteUser(ids);
		return ajaxResult;
		
	}
	/**
	 * 
	 * login:登录  
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>  
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/>  
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>  
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>  
	 *  
	 * @author Administrator  
	 * @param user
	 * @param session
	 * @return  
	 * @since JDK 1.6
	 */
	@RequestMapping("login")
	@ResponseBody
	public AjaxResult login(User user,String code,HttpSession session) {
		AjaxResult ajaxResult = new AjaxResult();
		String session_code = (String) session.getAttribute("code");
		if(code != null && code.equalsIgnoreCase(session_code)) {
			ajaxResult = userService.login(user);
			if(ajaxResult.getStatus() != -1) {
				session.setAttribute("user",ajaxResult.getData());
			}	
		}else {
			ajaxResult.setStatus(-1);
			ajaxResult.setMsg("验证码不正确");
		}
		return ajaxResult;
	}
	
	@RequestMapping("logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/index.html";
	}
}
  
