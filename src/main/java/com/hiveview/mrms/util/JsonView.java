/**  
 * Project Name:MRMS  
 * File Name:JsonView.java  
 * Package Name:com.hiveview.mrms.util  
 * Date:2018年11月16日上午10:19:14  
 * Copyright (c) 2018, 1570964489@qq.com All Rights Reserved.  
 *  
*/  
  
package com.hiveview.mrms.util;

import java.io.IOException;


import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

/**  
 * ClassName:JsonView <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason:   TODO ADD REASON. <br/>  
 * Date:     2018年11月16日 上午10:19:14 <br/>  
 * @author   Administrator  
 * @version    
 * @since    JDK 1.6  
 * @see        
 */
public class JsonView {
	public static ModelAndView Render(Object model, HttpServletResponse response)
	{
		MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
	
		MediaType jsonMimeType = MediaType.APPLICATION_JSON;

		try {
			jsonConverter.write(model, jsonMimeType, new ServletServerHttpResponse(response));
			} catch (HttpMessageNotWritableException e) {
			e.printStackTrace();
			} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
}
  
