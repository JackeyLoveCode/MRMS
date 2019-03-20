package com.hiveview.mrms.mapper;

import com.hiveview.mrms.pojo.Condition;
import com.hiveview.mrms.pojo.Favorite;
import com.hiveview.mrms.pojo.FavoriteExample;
import com.hiveview.mrms.pojo.User;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface FavoriteMapper {
    int countByExample(FavoriteExample example);

    int deleteByExample(FavoriteExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Favorite record);

    int insertSelective(Favorite record);

    List<Favorite> selectByExample(FavoriteExample example);

    Favorite selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Favorite record, @Param("example") FavoriteExample example);

    int updateByExample(@Param("record") Favorite record, @Param("example") FavoriteExample example);

    int updateByPrimaryKeySelective(Favorite record);

    int updateByPrimaryKey(Favorite record);

	List<Map<String, Object>> selectAllByUser(@Param("user") User user, @Param("condition") Condition condition);

    int countAllByUser(@Param("user") User user, @Param("condition") Condition condition);
}