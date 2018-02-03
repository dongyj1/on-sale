package com.webstore.dao;

import com.webstore.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao {

    @Select("select * from user_info where #{id}")
    public User getById(@Param("id") long id);

}
