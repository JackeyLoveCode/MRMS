package com.hiveview.mrms.mapper;

import com.hiveview.mrms.pojo.Condition;
import com.hiveview.mrms.pojo.Movie;
import com.hiveview.mrms.pojo.MovieExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface MovieMapper {
    int countByExample(MovieExample example);

    int deleteByExample(MovieExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Movie record);

    int insertSelective(Movie record);

    List<Movie> selectByExample(MovieExample example);

    Movie selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Movie record, @Param("example") MovieExample example);

    int updateByExample(@Param("record") Movie record, @Param("example") MovieExample example);

    int updateByPrimaryKeySelective(Movie record);

    int updateByPrimaryKey(Movie record);

	List<Map<String, Object>> queryByCondition(Condition condition);

	List<Map<String, Object>> selectMovieBytitleOrActors(@Param("searchText") String searchText);

	int countByCondition(Condition condition);

    List<Movie> getMoviesOrderByPlayTimes();
}