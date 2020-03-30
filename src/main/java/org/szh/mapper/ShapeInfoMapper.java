package org.szh.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.szh.bean.ShapeInfo;

public interface ShapeInfoMapper {
    int deleteByPrimaryKey(String markId);

    int insert(ShapeInfo record);

    int insertSelective(ShapeInfo record);

    ShapeInfo selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(ShapeInfo record);

    int updateByPrimaryKey(ShapeInfo record);

    @Select("select mark_id from t_shape_info")
	List<String> selectIds();

    @Select("select mark_id as markId,theme,description from t_shape_info")
	List<ShapeInfo> selectAll();
}