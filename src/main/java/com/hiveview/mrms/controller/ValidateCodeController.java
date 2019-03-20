/**  
 * Project Name:MRMS  
 * File Name:ValidateCodeController.java  
 * Package Name:com.hiveview.mrms.controller  
 * Date:2018年11月8日下午2:35:34  
 * Copyright (c) 2018, 1570964489@qq.com All Rights Reserved.  
 *  
*/  
  
package com.hiveview.mrms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hiveview.mrms.util.ValidateCode;

/**  
 * ClassName:ValidateCodeController <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason:   TODO ADD REASON. <br/>  
 * Date:     2018年11月8日 下午2:35:34 <br/>  
 * @author   Administrator  
 * @version    
 * @since    JDK 1.6  
 * @see        
 */
@Controller
public class ValidateCodeController {
	
	/**
	 * 响应验证码页面
	 * @return
	 */
	@RequestMapping(value="validateCode")
	public String validateCode(HttpServletRequest request,HttpServletResponse response) throws Exception{
		// 设置响应的类型格式为图片格式
		response.setContentType("image/jpeg");
		//禁止图像缓存。
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
 
		HttpSession session = request.getSession(false);
 
		ValidateCode vCode = new ValidateCode(120,40,5,100);
		session.setAttribute("code", vCode.getCode());
		vCode.write(response.getOutputStream());
		return null;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
  
