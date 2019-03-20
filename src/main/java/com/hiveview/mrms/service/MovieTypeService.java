/**  
 * Project Name:MRMS  
 * File Name:MovieTypeService.java  
 * Package Name:com.hiveview.mrms.service  
 * Date:2018年11月15日上午10:55:03  
 * Copyright (c) 2018, 1570964489@qq.com All Rights Reserved.  
 *  
*/  
  
package com.hiveview.mrms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hiveview.mrms.mapper.MovieTypeMapper;
import com.hiveview.mrms.pojo.AjaxResult;
import com.hiveview.mrms.pojo.EasyUIDatagridResult;
import com.hiveview.mrms.pojo.MovieType;
import com.hiveview.mrms.pojo.MovieTypeExample;

/**  
 * ClassName:MovieTypeService <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason:   TODO ADD REASON. <br/>  
 * Date:     2018年11月15日 上午10:55:03 <br/>  
 * @author   Administrator  
 * @version    
 * @since    JDK 1.6  
 * @see        
 */
@Service
public class MovieTypeService {

	@Autowired
	private MovieTypeMapper movieTypeMapper;
	
	public EasyUIDatagridResult findAll(Integer page, Integer rows) {
		EasyUIDatagridResult easyUIDatagridResult = new EasyUIDatagridResult();  
		PageHelper.startPage(page, rows);
		MovieTypeExample example = new MovieTypeExample();
		List<MovieType> movieTypes = movieTypeMapper.selectByExample(example);
		PageInfo<MovieType> pageInfo = new PageInfo<>(movieTypes);
		easyUIDatagridResult.setTotal(pageInfo.getTotal());
		easyUIDatagridResult.setRows(movieTypes);
		return easyUIDatagridResult;
	}

	public List<MovieType> getMovieTypeList(){
		MovieTypeExample example = new MovieTypeExample(); 
		List<MovieType> movieTypes = movieTypeMapper.selectByExample(example);
		return movieTypes;
	}
	
	public AjaxResult save(MovieType movieType) {
		AjaxResult ajaxResult = new AjaxResult();  
		int code = movieTypeMapper.insert(movieType);
		if(code == 1) {
			ajaxResult.setStatus(200);
			ajaxResult.setMsg("添加成功");
			return ajaxResult;
		}
		ajaxResult.setStatus(-1);
		return ajaxResult;
	}

	public AjaxResult update(MovieType movieType) {
		AjaxResult ajaxResult = new AjaxResult();  
		int code = movieTypeMapper.updateByPrimaryKey(movieType);
		if(code == 1) {
			ajaxResult.setStatus(200);
			ajaxResult.setMsg("更新成功");
			return ajaxResult;
		}
		ajaxResult.setStatus(-1);
		ajaxResult.setMsg("未知错误");
		return ajaxResult;
	}

	public AjaxResult delete(ArrayList<Integer> ids) {
		AjaxResult ajaxResult = new AjaxResult();
		int count = 0;  
		if(ids != null && ids.size() > 0) {
			for (Integer id : ids) {
				count += movieTypeMapper.deleteByPrimaryKey(id);
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

}
  
