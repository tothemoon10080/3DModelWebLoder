package com.cloudisk.demo1.mapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;

@Service
public interface UserMapper{

    @Select("select password from userlist where username = #{username}")
    String getUserpassbyName(String username);
    
    @Select("select * from userlist where id = #{id}")
    String getUserbyId(int id);

    @Insert("insert into userlist ( username , password) value(#{username},#{password})")
    void registerUser(String username, String password );

    @Delete("delete from userlist where username = #{username}")
    void deleteUser(String username);

    @Update("update userlist set password = #{newpassword} where username = #{username} ")
    void changePass(String username , String newpassword);    
}