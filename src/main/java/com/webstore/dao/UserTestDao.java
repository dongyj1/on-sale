package com.webstore.dao;

import com.webstore.domain.UserTest;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserTestDao {

    @Select("select * from user_test where id = #{id}")
    public UserTest getById(@Param("id") int id);

    @Insert("insert into user_test(id, name)values(#{id}, #{name})")
    public int insert(UserTest userTest);


}
