package com.example.springboot_jsp_shiro.service;

import com.example.springboot_jsp_shiro.entity.Perms;
import com.example.springboot_jsp_shiro.entity.User;

import java.util.List;

public interface UserService {
    void register(User user);

    User findByUsername(String username);

    User findRolesByUsername(String username);

    List<Perms> findPermsByRoleId(String id);

}
