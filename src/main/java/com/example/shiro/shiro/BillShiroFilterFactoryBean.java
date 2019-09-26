/*
 * Copyright (c) 2018 北京德鸿普惠科技有限公司.
 * All rights reserved.
 */
package com.example.shiro.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.FilterChainResolver;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.BeanInitializationException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 自定义ShiroFilterFactory实现，主要是为了实现在分布式系统中保持Session共享，同时在一些特定的请求不更新Session有效期.
 * <p>
 *
 * @author 李春杰 创建于 19/5/28
 */
@Slf4j
public class BillShiroFilterFactoryBean extends ShiroFilterFactoryBean {
    /**
     * 不用更新Session的请求
     */
    private static String[] PRIVILEGE_REQUEST_URI = {"/sys/live", "/sys/message/unReadTotal"};


    @Override
    protected AbstractShiroFilter createInstance() throws Exception {
        log.debug("Creating Shiro Filter instance.");

        SecurityManager securityManager = getSecurityManager();
        if (securityManager == null) {
            String msg = "SecurityManager property must be set.";
            throw new BeanInitializationException(msg);
        }

        if (!(securityManager instanceof WebSecurityManager)) {
            String msg = "The security manager does not implement the WebSecurityManager interface.";
            throw new BeanInitializationException(msg);
        }

        FilterChainManager manager = createFilterChainManager();

        //Expose the constructed FilterChainManager by first wrapping it in a
        // FilterChainResolver implementation. The AbstractShiroFilter implementations
        // do not know about FilterChainManagers - only resolvers:
        PathMatchingFilterChainResolver chainResolver = new PathMatchingFilterChainResolver();
        chainResolver.setFilterChainManager(manager);

        //Now create a concrete ShiroFilter instance and apply the acquired SecurityManager and built
        //FilterChainResolver.  It doesn't matter that the instance is an anonymous inner class
        //here - we're just using it because it is a concrete AbstractShiroFilter instance that accepts
        //injection of the SecurityManager and FilterChainResolver:
        return new WmpSpringShiroFilter((WebSecurityManager) securityManager, chainResolver);
    }

    private static final class WmpSpringShiroFilter extends AbstractShiroFilter {

        WmpSpringShiroFilter(WebSecurityManager webSecurityManager, FilterChainResolver resolver) {
            super();
            if (webSecurityManager == null) {
                throw new IllegalArgumentException("WebSecurityManager property cannot be null.");
            }
            setSecurityManager(webSecurityManager);
            if (resolver != null) {
                setFilterChainResolver(resolver);
            }
        }

        @Override
        protected void updateSessionLastAccessTime(ServletRequest request, ServletResponse response) {
            String requestURI = ((HttpServletRequest)request).getRequestURI();

            for (String uri : PRIVILEGE_REQUEST_URI) {
                if (requestURI.contains(uri)) {
                    return;
                }
            }

            super.updateSessionLastAccessTime(request, response);
        }
    }
}
