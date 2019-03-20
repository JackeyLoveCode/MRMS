/**  
 * Project Name:MRMS  
 * File Name:FavoriteService.java  
 * Package Name:com.hiveview.mrms.service  
 * Date:2018年11月22日下午2:06:12  
 * Copyright (c) 2018, 1570964489@qq.com All Rights Reserved.  
 *  
*/  
  
package com.hiveview.mrms.service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hiveview.mrms.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiveview.mrms.mapper.FavoriteMapper;

/**  
 * ClassName:FavoriteService <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason:   TODO ADD REASON. <br/>  
 * Date:     2018年11月22日 下午2:06:12 <br/>  
 * @author   Administrator  
 * @version    
 * @since    JDK 1.6  
 * @see        
 */
@Service
public class FavoriteService {
	
	@Autowired
	private FavoriteMapper favoriteMapper;

	public EasyUIDatagridResult findAll(User user, Condition condition) {
		EasyUIDatagridResult easyUIDatagridResult = new EasyUIDatagridResult();
		List<Map<String, Object>> favorites = favoriteMapper.selectAllByUser(user,condition);
        for (Map<String, Object> favoriteMap : favorites) {
            Set<String> keys = favoriteMap.keySet();
            for (String key : keys) {
                if("collection_time".equals(key)){
                	String collectionTimeStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS").format(favoriteMap.get("collection_time"));
                	favoriteMap.put("collection_time",collectionTimeStr);
				}
            }
        }
		int total = favoriteMapper.countAllByUser(user,condition);
		easyUIDatagridResult.setPage(condition.getPage());
		easyUIDatagridResult.setPerPageRows(condition.getRows());
		easyUIDatagridResult.setRows(favorites);
		easyUIDatagridResult.setTotal(total);
		return easyUIDatagridResult;
	}

	public boolean cancle(Integer id, User user) {
		  
		FavoriteExample example = new FavoriteExample();
		example.createCriteria().andUseridEqualTo(user.getId()).andMovieidEqualTo(id);
		int success = favoriteMapper.deleteByExample(example);
		if(success == 1) {
			return true;
		}
		return false;
	}

	public Favorite findByCon(Favorite favorite) {
		FavoriteExample favoriteExample = new FavoriteExample();
		favoriteExample.createCriteria().andUseridEqualTo(favorite.getUserid()).andMovieidEqualTo(favorite.getMovieid());
		List<Favorite> favorites = favoriteMapper.selectByExample(favoriteExample);
		return favorites == null ? null : (favorites.size() > 0 ? favorites.get(0) : null);
	}
}
  
