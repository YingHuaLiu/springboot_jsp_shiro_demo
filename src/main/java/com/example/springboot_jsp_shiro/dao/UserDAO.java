package com.example.springboot_jsp_shiro.dao;

import com.example.springboot_jsp_shiro.entity.Perms;
import com.example.springboot_jsp_shiro.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDAO {
    void save(User user);

    User findUserByUsername(String username);

    User findRolesByUsername(String username);

    List<Perms> findPermsByRoleId(String id);
}
