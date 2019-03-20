/**  
 * Project Name:MRMS  
 * File Name:DateConverter.java  
 * Package Name:com.hiveview.mrms.converters  
 * Date:2018年11月14日下午6:02:11  
 * Copyright (c) 2018, 1570964489@qq.com All Rights Reserved.  
 *  
*/  
  
package com.hiveview.mrms.converters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

/**  
 * ClassName:DateConverter <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason:   TODO ADD REASON. <br/>  
 * Date:     2018年11月14日 下午6:02:11 <br/>  
 * @author   Administrator  
 * @version    
 * @since    JDK 1.6  
 * @see        
 */
public class DateConverter implements Converter<String, Date> {

	@Override
	public Date convert(String source) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");    
		dateFormat.setLenient(false);    
		try {    
			return dateFormat.parse(source);
		}
		catch (ParseException e) {    
			e.printStackTrace();    
		}           
		return null;      
		
	}

}

  
