package com.example.springboot_jsp_shiro.service;

import com.example.springboot_jsp_shiro.dao.UserDAO;
import com.example.springboot_jsp_shiro.entity.Perms;
import com.example.springboot_jsp_shiro.entity.Role;
import com.example.springboot_jsp_shiro.entity.User;
import com.example.springboot_jsp_shiro.utils.SaltUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("UserService")
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;

    @Override
    public void register(User user) {
        //生成随机盐
        String salt = SaltUtils.getSalt(8);
        //md5 + salt + hash进行加密
        Md5Hash md5Hash = new Md5Hash(user.getPassword(), salt, 1024);
        user.setPassword(md5Hash.toHex());
        user.setSalt(salt);
        userDAO.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userDAO.findUserByUsername(username);
    }

    @Override
    public User findRolesByUsername(String username) {
        return userDAO.findRolesByUsername(username);
    }

    @Override
    public List<Perms> findPermsByRoleId(String id) {
        return userDAO.findPermsByRoleId(id);
    }
}
