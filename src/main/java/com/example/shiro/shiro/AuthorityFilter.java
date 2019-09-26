package com.example.shiro.shiro;

import com.alibaba.fastjson.JSON;
import com.example.shiro.common.enums.BizExceptionEnum;
import com.example.shiro.model.base.Result;
import com.example.shiro.utils.HttpKit;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionKey;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * AuthorityFilter 过滤器
 */
@Slf4j
public class AuthorityFilter extends AuthenticationFilter {

    @Setter
    private DefaultWebSessionManager sessionManager ;

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setContentType("application/json;charset=utf-8");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Origin", HttpKit.getOrigin());
        try {
            Serializable sessionId = sessionManager.getSessionId(new WebSessionKey(request, response));
            Session session = sessionManager.getSession(new DefaultSessionKey(sessionId));
//            if (session == null) {
//                log.info("用户未登录！");
//                httpResponse.getWriter().print(JSON.toJSONString(Result.sessionTimeout("用户未登录")));
//                return false ;
//            }
            return true ;
        } catch (SessionException e) {
            log.error("", e);
            httpResponse.getWriter().print(JSON.toJSONString(Result.error(BizExceptionEnum.SESSION_TIMEOUT.getFriendlyCode(),
                    BizExceptionEnum.SESSION_TIMEOUT.getFriendlyMsg())));
            return false ;
        }
    }

}
