/**  
 * Project Name:MRMS  
 * File Name:PageController.java  
 * Package Name:com.hiveview.mrms.controller  
 * Date:2018年11月7日下午5:49:58  
 * Copyright (c) 2018, 1570964489@qq.com All Rights Reserved.  
 *  
*/  
  
package com.hiveview.mrms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**  
 * ClassName:PageController <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason:   TODO ADD REASON. <br/>  
 * Date:     2018年11月7日 下午5:49:58 <br/>  
 * @author   Administrator  
 * @version    
 * @since    JDK 1.6  
 * @see        
 */
@Controller
public class PageController {
	@RequestMapping("/{f_url}/{s_url}")
	public String toAdminPage(HttpServletRequest request,@PathVariable("f_url")String f_url,@PathVariable("s_url")String s_url) {
		HttpSession session = request.getSession();
		return f_url+"/"+s_url;
		
	}
	
	@RequestMapping("/{url}")
	public String toPage(@PathVariable("url")String url) {
		return url;
	}
}
  
