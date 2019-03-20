package com.hiveview.mrms.mapper;

import com.hiveview.mrms.pojo.Condition;
import com.hiveview.mrms.pojo.PlayRecords;
import com.hiveview.mrms.pojo.PlayRecordsExample;
import com.hiveview.mrms.pojo.User;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface PlayRecordsMapper {
    int countByExample(PlayRecordsExample example);

    int deleteByExample(PlayRecordsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PlayRecords record);

    int insertSelective(PlayRecords record);

    List<PlayRecords> selectByExample(PlayRecordsExample example);

    PlayRecords selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PlayRecords record, @Param("example") PlayRecordsExample example);

    int updateByExample(@Param("record") PlayRecords record, @Param("example") PlayRecordsExample example);

    int updateByPrimaryKeySelective(PlayRecords record);

    int updateByPrimaryKey(PlayRecords record);

	List<Map<String, Object>> selectRecordsByUserid(@Param("user") User user, @Param("condition") Condition condition);

    int countRecordsByUserid(@Param("user") User user, @Param("condition") Condition condition);
}