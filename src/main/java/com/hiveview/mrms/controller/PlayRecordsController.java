/**  
 * Project Name:MRMS  
 * File Name:PlayRecordsController.java  
 * Package Name:com.hiveview.mrms.controller  
 * Date:2018年11月21日下午2:10:16  
 * Copyright (c) 2018, 1570964489@qq.com All Rights Reserved.  
 *  
*/  
  
package com.hiveview.mrms.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.hiveview.mrms.pojo.Condition;
import com.hiveview.mrms.pojo.EasyUIDatagridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hiveview.mrms.pojo.AjaxResult;
import com.hiveview.mrms.pojo.User;
import com.hiveview.mrms.service.PlayRecordsService;

/**  
 * ClassName:PlayRecordsController <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason:   TODO ADD REASON. <br/>  
 * Date:     2018年11月21日 下午2:10:16 <br/>  
 * @author   Administrator  
 * @version    
 * @since    JDK 1.6  
 * @see        
 */
@Controller
@RequestMapping("playRecords/")
public class PlayRecordsController {
	
	@Autowired
	private PlayRecordsService playRecordsService;
	
	@RequestMapping("add")
	@ResponseBody
	public AjaxResult add(Integer id,HttpSession httpSession) {
		User user = (User) httpSession.getAttribute("user");
		if(user == null) {
			return new AjaxResult(201, "用户未登录", null);
		}
		AjaxResult ajaxResult = playRecordsService.save(id,user);
		return ajaxResult;
	}

	/**
	 * 分页查询播放记录
	 * @param session
	 * @param condition
	 * @return
	 */
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
            easyUIDatagridResult = playRecordsService.findAll(user,condition);
            easyUIDatagridResult.setStatus(200);
        }catch(Exception ex){
	        ex.printStackTrace();
        }
		return easyUIDatagridResult;
	}
	
}
  
