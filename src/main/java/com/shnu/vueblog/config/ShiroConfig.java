package com.shnu.vueblog.config;

import com.shnu.vueblog.shiro.AccountRealm;
import com.shnu.vueblog.shiro.JwtFilter;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: vueblog
 *
 * @description: shiro配置 ,启用注解拦截控制器
 *
 * @author: Legendary_Hu
 *
 * @create: 2022-03-16 14:42
 *
 **/
@Configuration
public class ShiroConfig {
    @Bean
    public SessionManager sessionManager(RedisSessionDAO redisSessionDAO){
        DefaultSessionManager sessionManager = new DefaultSessionManager();

        //inject redisSessioDAO
        sessionManager.setSessionDAO(redisSessionDAO);
        return sessionManager;

    }

    //ShiroFilterChainDefinition
    @Bean
    ShiroFilterChainDefinition shiroFilterChainDefinition(){
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        Map<String,String> filterMap = new LinkedHashMap<>();
        filterMap.put("/**", "jwt");
        chainDefinition.addPathDefinitions(filterMap);
        return chainDefinition;

    }


    //shiroFilterFactoryBean 3
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") SecurityManager securityManager,
                                                         ShiroFilterChainDefinition shiroFilterChainDefinition,
                                                         @Qualifier("jwtFilter") JwtFilter jwtFilter){
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        Map<String, Filter> filters = new HashMap<>();
        filters.put("jwt", jwtFilter);
        shiroFilter.setFilters(filters);
        Map<String, String> filterMap = shiroFilterChainDefinition.getFilterChainMap();
        shiroFilter.setFilterChainDefinitionMap(filterMap);
        return shiroFilter;
    }

    //DefaultWebSecurityManager  2
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getSecurityManager(AccountRealm accountRealm, SessionManager sessionManager, RedisCacheManager redisCacheManager){

        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager(accountRealm);

        //inject sessionManager
        securityManager.setSessionManager(sessionManager);

        //inject redisCacheManager
        securityManager.setCacheManager(redisCacheManager);
        /*
         * 关闭shiro自带的session，详情见文档
         */
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        return securityManager;


    }

    //创建 realm 对象，需要自定义类: 1
    @Bean(name = "user")
    public AccountRealm accountRealm(){
        return new AccountRealm();
    }
    @Bean(name = "jwtFilter")
    JwtFilter jwtFilter(){
        return  new JwtFilter();
    }
}
