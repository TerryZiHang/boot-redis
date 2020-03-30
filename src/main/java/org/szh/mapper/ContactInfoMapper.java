package org.szh.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.szh.bean.ContactInfo;

public interface ContactInfoMapper {
    int deleteByPrimaryKey(String markId);

    int insert(ContactInfo record);

    int insertSelective(ContactInfo record);

    ContactInfo selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(ContactInfo record);

    int updateByPrimaryKey(ContactInfo record);
    
    @Select("select mark_id as markId,name,phone,email,position from t_contact_info")
    List<ContactInfo> selectAll();
    
    @Delete("delete from t_contact_info")
    int deleteAll();
}