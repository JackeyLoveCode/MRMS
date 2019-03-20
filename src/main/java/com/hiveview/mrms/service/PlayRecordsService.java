/**  
 * Project Name:MRMS  
 * File Name:PlayRecordsService.java  
 * Package Name:com.hiveview.mrms.service  
 * Date:2018年11月21日下午2:11:19  
 * Copyright (c) 2018, 1570964489@qq.com All Rights Reserved.  
 *  
*/  
  
package com.hiveview.mrms.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hiveview.mrms.mapper.MovieMapper;
import com.hiveview.mrms.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiveview.mrms.mapper.PlayRecordsMapper;

/**  
 * ClassName:PlayRecordsService <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason:   TODO ADD REASON. <br/>  
 * Date:     2018年11月21日 下午2:11:19 <br/>  
 * @author   Administrator  
 * @version    
 * @since    JDK 1.6  
 * @see        
 */
@Service
public class PlayRecordsService {
	
	@Autowired
	private PlayRecordsMapper playRecordsMapper;

	@Autowired
	private MovieMapper movieMapper;

	public AjaxResult save(Integer id, User user) {
		  
		PlayRecords playRecords = new PlayRecords();
		playRecords.setPlayTime(new Date());
		playRecords.setMovieid(id);
		playRecords.setUserid(user.getId());
		//修改电影播放量
		int success = 0;
		Movie movie = movieMapper.selectByPrimaryKey(id);
		movie.setPlayTimes(movie.getPlayTimes() + 1);
		success += movieMapper.updateByPrimaryKey(movie);
		//需要查询该电影以前是否播放过,若播放过，直接修改播放记录的播放时间，否则添加一条记录到库里
		PlayRecordsExample playRecordsExample = new PlayRecordsExample();
		playRecordsExample.createCriteria().andUseridEqualTo(user.getId()).andMovieidEqualTo(id);
		List<PlayRecords> records = playRecordsMapper.selectByExample(playRecordsExample);
		if(records != null && records.size() > 0){
			PlayRecords record = records.get(records.size() - 1);
			record.setPlayTime(new Date());
			int result = playRecordsMapper.updateByPrimaryKeySelective(record);
			if(result == 1){
				return new AjaxResult(200,"修改成功",null);
			}
		}
		success += playRecordsMapper.insert(playRecords);
		if(success == 2) {
			return new AjaxResult(200,"添加播放记录成功",null);
		}
		return null;
	}

	public EasyUIDatagridResult findAll(User user, Condition condition) {
		EasyUIDatagridResult easyUIDatagridResult = new EasyUIDatagridResult();
		List<Map<String, Object>> records = playRecordsMapper.selectRecordsByUserid(user,condition);
		for (Map<String, Object> recordMap : records) {
			Set<String> keys = recordMap.keySet();
			for (String key : keys) {
				if("play_time".equals(key)){
					recordMap.put("play_time",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(recordMap.get("play_time")));
				}
			}
		}
		int total = playRecordsMapper.countRecordsByUserid(user,condition);
		easyUIDatagridResult.setPage(condition.getPage());
		easyUIDatagridResult.setPerPageRows(condition.getRows());
		easyUIDatagridResult.setRows(records);
		easyUIDatagridResult.setTotal(total);
		return easyUIDatagridResult;
	}
	
	
	
	
}
  
