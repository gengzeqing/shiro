/**
 * Copyright 2018 人人开源 http://www.renren.io
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on util "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.example.shiro.shiro.config;


import com.example.shiro.shiro.AuthorityFilter;
import com.example.shiro.shiro.realm.UserRealm;
import com.example.shiro.shiro.BillShiroFilterFactoryBean;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.apache.shiro.session.mgt.AbstractSessionManager.DEFAULT_GLOBAL_SESSION_TIMEOUT;

/**
 * shiro相关系统配置
 *
 * @Author: yaokui
 * @Date: 2019/6/3
 */
@Configuration
public class ShiroConfig {

    @Autowired(required = false)
    private RedisSessionDAO redisSessionDAO;

    @Autowired(required = false)
    private RedisCacheManager redisCacheManager;

    @Bean("sessionManager")
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO);
        // session 默认超时：30分钟
        sessionManager.setGlobalSessionTimeout(DEFAULT_GLOBAL_SESSION_TIMEOUT);
        return sessionManager;
    }

    @Bean("securityManager")
    public SessionsSecurityManager securityManager(UserRealm userRealm, DefaultWebSessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        securityManager.setSessionManager(sessionManager);
        securityManager.setCacheManager(redisCacheManager);
        return securityManager;
    }

    @Bean
    public BillShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager, DefaultWebSessionManager sessionManager) {
        BillShiroFilterFactoryBean tiebeiBillShiroFilterFactoryBean = new BillShiroFilterFactoryBean();
        tiebeiBillShiroFilterFactoryBean.setSecurityManager(securityManager);
        // 指定免拦截请求
        tiebeiBillShiroFilterFactoryBean.setFilterChainDefinitionMap(shiroFilterChainDefinitionMap());
        AuthorityFilter authorityFilter = new AuthorityFilter();
        authorityFilter.setSessionManager(sessionManager);
        tiebeiBillShiroFilterFactoryBean.postProcessBeforeInitialization(authorityFilter, "admin");
        return tiebeiBillShiroFilterFactoryBean;
    }

    /**
     * 配置shiro拦截器链
     * <p>
     * anon  不需要认证
     * authc 需要认证
     * admin  验证通过或RememberMe登录的都可以
     * <p>
     * 当应用开启了rememberMe时,用户下次访问时可以是一个user,但不会是authc,因为authc是需要重新认证的
     * <p>
     * 顺序从上到下,优先级依次降低
     */
    private Map<String, String> shiroFilterChainDefinitionMap() {
        Map<String, String> filterMap = new LinkedHashMap<>(15);
        filterMap.put("/ms/admin/login", "anon");
        filterMap.put("/ms/admin/login/captcha/**", "anon");
        //todo 方便开发暂时注释
        //filterMap.put("/**", "admin");
        return filterMap;
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        // shiro-spring-boot-web-starter ShiroWebFilterConfiguration 自动配置类 需要自动装配此Bean，除此无其他用途
        return new DefaultShiroFilterChainDefinition();
    }

    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }
}
