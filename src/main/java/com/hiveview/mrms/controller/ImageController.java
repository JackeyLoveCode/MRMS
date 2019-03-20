/**  
 * Project Name:MRMS  
 * File Name:ImageController.java  
 * Package Name:com.hiveview.mrms.controller  
 * Date:2018年11月17日下午9:19:29  
 * Copyright (c) 2018, 1570964489@qq.com All Rights Reserved.  
 *  
*/  
  
package com.hiveview.mrms.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**  
 * ClassName:ImageController <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason:   TODO ADD REASON. <br/>  
 * Date:     2018年11月17日 下午9:19:29 <br/>  
 * @author   Administrator  
 * @version    
 * @since    JDK 1.6  
 * @see        
 */
@Controller
public class ImageController {
	
	@RequestMapping("showImg")
	public void showImg(HttpServletRequest request,HttpServletResponse response,String pathName) {
		File imgFile = new File(pathName);
		FileInputStream fin = null;
		OutputStream output=null;
		try {
			fin = new FileInputStream(imgFile);
			output = response.getOutputStream();
			byte[] bytes = new byte[1024 * 10];
			int n = 0;
			while((n = fin.read(bytes)) != -1) {
				output.write(bytes, 0, n);
			}
			output.flush();
			output.close();
		} catch (Exception e) {
			  
			// TODO Auto-generated catch block  
			e.printStackTrace();  
			
		}
	}
}
  
