package com.example.springboot_jsp_shiro.config;

import com.example.springboot_jsp_shiro.shiro.cache.RedisCacheManager;
import com.example.springboot_jsp_shiro.shiro.realms.CustomerRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.HashMap;

@Configuration
public class ShiroConfig {
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager) {
        //创建ShiroFilter，负责拦截所有请求
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        //给shiroFilter设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);

        //配置系统的受限资源和公共资源
        HashMap<String, String> map = new HashMap<>();
        map.put("/user/register", "anon");
        map.put("/register.jsp", "anon");
        map.put("/user/login", "anon"); //anon 设置为公共资源
        map.put("**", "authc"); //authc：请求这个资源需要认证和授权

        //设置默认认证界面路径
        shiroFilterFactoryBean.setLoginUrl("/login.jsp");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);

        return shiroFilterFactoryBean;
    }

    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(Realm realm) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(realm);
        return defaultWebSecurityManager;
    }

    @Bean
    @Primary
    public Realm getRealm() {
        CustomerRealm customerRealm=new CustomerRealm();

        //hash凭证匹配器
        HashedCredentialsMatcher matcher=new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");
        matcher.setHashIterations(1024);
        customerRealm.setCredentialsMatcher(matcher);

        //缓存管理器
        customerRealm.setCacheManager(new RedisCacheManager());
        customerRealm.setCachingEnabled(true); //开启全局缓存
        customerRealm.setAuthenticationCachingEnabled(true); //开启认证缓存
        customerRealm.setAuthenticationCacheName("authenticationCache");
        customerRealm.setAuthorizationCachingEnabled(true); //开启授权缓存
        customerRealm.setAuthorizationCacheName("authorizationCache");


        return customerRealm;
    }
}
