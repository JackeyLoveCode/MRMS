/**  
 * Project Name:MRMS  
 * File Name:AdminController.java  
 * Package Name:com.hiveview.mrms.controller  
 * Date:2018年11月12日上午10:23:51  
 * Copyright (c) 2018, 1570964489@qq.com All Rights Reserved.  
 *  
*/  
  
package com.hiveview.mrms.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hiveview.mrms.pojo.Admin;
import com.hiveview.mrms.pojo.AjaxResult;
import com.hiveview.mrms.service.AdminService;
import com.hiveview.mrms.util.JsonView;

/**  
 * ClassName:AdminController <br/>  
 * Function: 跟管理员有关的操作都在这里
 * Reason:   TODO ADD REASON. <br/>  
 * Date:     2018年11月12日 上午10:23:51 <br/>  
 * @author   Administrator  
 * @version    
 * @since    JDK 1.6  
 * @see        
 */
@Controller
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@RequestMapping("/admin/doLogin")
	public ModelAndView login(Admin admin,HttpSession session,HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView();
		AjaxResult ajaxResult = adminService.login(admin);
		session.setAttribute("user", (Admin)ajaxResult.getData());
		if(ajaxResult.getStatus() == 200) {
			modelAndView.setViewName("redirect:index.html");
			return modelAndView;
		}
		modelAndView.addObject("msg", "用户名和密码不匹配");
		modelAndView.setViewName("redirect:login.html");
		return modelAndView;
	}
	
	@RequestMapping("/admin/changPassword")
	@ResponseBody
	public AjaxResult changePassword(String newPassword,String code,HttpServletRequest request) {
		AjaxResult ajaxResult = new AjaxResult();
		HttpSession httpSession = request.getSession(false);
		System.out.println(httpSession.getAttribute("user"));
		Admin admin = (Admin) httpSession.getAttribute("user");
		if(admin == null) {
			ajaxResult.setStatus(201);
			ajaxResult.setMsg("登录失效");
			return ajaxResult;
		}
		String session_code = (String) httpSession.getAttribute("code");
		if(session_code.equalsIgnoreCase(code)) {
			ajaxResult = adminService.changePassword(admin,newPassword);
			return ajaxResult;
		}else {
			ajaxResult.setStatus(202);
			ajaxResult.setMsg("验证码不正确");
			return ajaxResult;
		}
		
	}
	
	
	@RequestMapping("admin/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:login.html";
	}
}
  
