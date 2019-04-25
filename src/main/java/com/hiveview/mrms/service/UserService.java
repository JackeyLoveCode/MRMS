/**  
 * Project Name:MRMS  
 * File Name:UserService.java  
 * Package Name:com.hiveview.mrms.service  
 * Date:2018年11月15日下午2:30:34  
 * Copyright (c) 2018, 1570964489@qq.com All Rights Reserved.  
 *  
*/  
  
package com.hiveview.mrms.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hiveview.mrms.mapper.UserMapper;
import com.hiveview.mrms.pojo.AjaxResult;
import com.hiveview.mrms.pojo.EasyUIDatagridResult;
import com.hiveview.mrms.pojo.User;
import com.hiveview.mrms.pojo.UserExample;

/**  
 * ClassName:UserService <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason:   TODO ADD REASON. <br/>  
 * Date:     2018年11月15日 下午2:30:34 <br/>  
 * @author   Administrator  
 * @version    
 * @since    JDK 1.6  
 * @see        
 */
@Service
public class UserService {
	@Autowired
	private UserMapper userMapper;

	public EasyUIDatagridResult findAll(Integer page, Integer rows) {
		EasyUIDatagridResult easyUIDatagridResult = new EasyUIDatagridResult();  
		PageHelper.startPage(page, rows);
		UserExample example = new UserExample();
		List<User> users = userMapper.selectByExample(example);
		PageInfo<User> pageInfo = new PageInfo<>(users);
		easyUIDatagridResult.setRows(users);
		easyUIDatagridResult.setTotal(pageInfo.getTotal());
		return easyUIDatagridResult;
	}

	public AjaxResult save(User user) {
		user.setRegDate(new Date());
		userMapper.insert(user);  
		return new AjaxResult(200, "添加成功", user);
	}

	public AjaxResult update(User user) {
		userMapper.updateByPrimaryKeySelective(user);  
		return new AjaxResult(200,"更新成功", null);
	}

	public AjaxResult deleteUser(ArrayList<Integer> ids) {
		AjaxResult ajaxResult = new AjaxResult();
		int count = 0;  
		if(ids != null && ids.size() > 0) {
			for (Integer id : ids) {
				count += userMapper.deleteByPrimaryKey(id);
			}
		}
		if(ids.size() == count) {
			ajaxResult.setStatus(200);
			ajaxResult.setMsg("删除成功");
			return ajaxResult;
		}
		ajaxResult.setStatus(-1);	
		return ajaxResult;
	}

	public AjaxResult login(User user) {
		  
		UserExample example = new UserExample();
		example.createCriteria().andNickNameEqualTo(user.getNickName()).andPasswordEqualTo(user.getPassword());
		List<User> users = userMapper.selectByExample(example);
		if(users != null && users.size() > 0) {
			AjaxResult ajaxResult = new AjaxResult(200, "登录成功", users.get(0));
			return ajaxResult;
		}
		return new AjaxResult(-1,"用户名和密码不匹配",null);
	}

	public List<User> findByNickname(User user) {
		UserExample userExample = new UserExample();
		userExample.createCriteria().andNickNameEqualTo(user.getNickName());
		List<User> users = userMapper.selectByExample(userExample);
		return users;
	}
}
  
