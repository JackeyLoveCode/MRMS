/**  
 * Project Name:MRMS  
 * File Name:UploadImageUtil.java  
 * Package Name:com.hiveview.mrms.util  
 * Date:2018年11月14日上午9:48:52  
 * Copyright (c) 2018, 1570964489@qq.com All Rights Reserved.  
 *  
*/  
  
package com.hiveview.mrms.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

/**  
 * ClassName:UploadImageUtil <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason:   TODO ADD REASON. <br/>  
 * Date:     2018年11月14日 上午9:48:52 <br/>  
 * @author   Administrator  
 * @version    
 * @since    JDK 1.6  
 * @see        
 */


public class UploadImageUtil {
	
	public static String getImageName(MultipartFile file) {
		String[] s = file.getOriginalFilename().split("^\\$");
		String fileName = s[0];
        return fileName;
	}
	
	public static void uploadImage(String targetPath,MultipartFile file,HttpServletRequest request) {
		File f = new File(targetPath);
		try {
			if(!f.exists()) {
				f.getParentFile().mkdir();
	            f.createNewFile();
			}
	        String fileName = getImageName(file);
	        File targetfile = new File(targetPath, fileName);
	        if(!targetfile.exists()) {
		        InputStream ins = null;
		        FileOutputStream fos = null;
				ins = file.getInputStream();
				fos = new FileOutputStream(targetfile);
				byte b[] = new byte[1024];
			    int temp = 0;
			    while((temp = ins.read(b)) != -1){
			            fos.write(b, 0, temp);
			    }
			    fos.close();
			    ins.close();
	        }
		} catch (IOException e) {
			// TODO Auto-generated catch block  
			e.printStackTrace();  
		}
       
	}

	public static void main(String[] args) {
		int a = 1;
		int b =3;
		System.out.println(a % b);
	}
}
  
