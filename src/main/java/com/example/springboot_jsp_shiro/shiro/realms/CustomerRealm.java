package com.example.springboot_jsp_shiro.shiro.realms;

import com.example.springboot_jsp_shiro.entity.Perms;
import com.example.springboot_jsp_shiro.entity.User;
import com.example.springboot_jsp_shiro.service.UserService;
import com.example.springboot_jsp_shiro.utils.ApplicationContextUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;

public class CustomerRealm extends AuthorizingRealm {
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String principal = (String) principals.getPrimaryPrincipal();
        UserService userService = (UserService) ApplicationContextUtils.getBean("UserService");
        User user = userService.findRolesByUsername(principal);
        if (!CollectionUtils.isEmpty(user.getRoles())) {
            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            user.getRoles().forEach(role -> {
                simpleAuthorizationInfo.addRole(role.getName());
                //权限信息
                List<Perms> perms = userService.findPermsByRoleId(role.getId());
                if (!CollectionUtils.isEmpty(perms)) {
                    perms.forEach(perm -> simpleAuthorizationInfo.addStringPermission(perm.getName()));
                }
            });

            return simpleAuthorizationInfo;
        }

//        if ("xiaochen".equals(pricipal)) {
//            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
//            simpleAuthorizationInfo.addRole("user");
//            simpleAuthorizationInfo.addStringPermission("user:add:*");
//            return simpleAuthorizationInfo;
//        }
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String principal = (String) token.getPrincipal();
        //从工厂获取service对象
        UserService userService = (UserService) ApplicationContextUtils.getBean("UserService");
        User user = userService.findByUsername(principal);

        if (!ObjectUtils.isEmpty(user)) {
            return new SimpleAuthenticationInfo(user.getUsername(),
                    user.getPassword(),
                    ByteSource.Util.bytes(user.getSalt()),
                    this.getName());
        }
        return null;
    }
}
