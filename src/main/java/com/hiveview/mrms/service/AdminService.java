/**  
 * Project Name:MRMS  
 * File Name:AdminService.java  
 * Package Name:com.hiveview.mrms.service  
 * Date:2018年11月12日上午11:32:42  
 * Copyright (c) 2018, 1570964489@qq.com All Rights Reserved.  
 *  
*/  
  
package com.hiveview.mrms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiveview.mrms.mapper.AdminMapper;
import com.hiveview.mrms.pojo.Admin;
import com.hiveview.mrms.pojo.AdminExample;
import com.hiveview.mrms.pojo.AjaxResult;

/**  
 * ClassName:AdminService <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason:   TODO ADD REASON. <br/>  
 * Date:     2018年11月12日 上午11:32:42 <br/>  
 * @author   Administrator  
 * @version    
 * @since    JDK 1.6  
 * @see        
 */
@Service
public class AdminService {

	@Autowired
	private AdminMapper adminMapper;

	public AjaxResult login(Admin admin) {
		AjaxResult ajaxResult = new AjaxResult();
		
		AdminExample example = new AdminExample();
		example.createCriteria().andUsernameEqualTo(admin.getUsername()).
								andPasswordEqualTo(admin.getPassword());
		List<Admin> admins = adminMapper.selectByExample(example);
		if(admins != null && admins.size() > 0) {
			ajaxResult.setStatus(200);
			ajaxResult.setData(admins.get(0));
		}else {
			ajaxResult.setStatus(-1);
			ajaxResult.setMsg("用户名和密码不匹配");
		}
		return ajaxResult;
		
	}

	public AjaxResult changePassword(Admin admin, String newPassword) {
		admin.setPassword(newPassword);  
		int code = adminMapper.updateByPrimaryKeySelective(admin);
		if(code == 1) {
			return new AjaxResult(200, "修改成功", null);
		}
		return new AjaxResult(-1,"修改失败",null);
	}
}
  
