package com.example.shiro.exceptionHandler;

import com.example.shiro.bean.helper.LoginHelper;
import com.example.shiro.common.enums.BizExceptionEnum;
import com.example.shiro.exception.BusinessException;
import com.example.shiro.exception.LoginException;
import com.example.shiro.model.base.Result;
import com.example.shiro.model.dto.LoginAdminResDTO;
import com.example.shiro.utils.HttpKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.UnknownSessionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.UndeclaredThrowableException;

/**
 * 全局的的异常拦截器（拦截所有的控制器）（带有@RequestMapping注解的方法上都会拦截）
 *
 * @author yaokui
 * @since 2019-06-04
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired(required = false)
    private LoginHelper loginHelper;

    /**
     * 拦截业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public Result handleBizException(BusinessException e) {
        HttpKit.getRequest().setAttribute("tip", "业务异常");
        log.info("业务异常:", e);
        return Result.error(e.getFriendlyCode(), e.getFriendlyMsg());
    }

    /**
     * 用户未登录
     */
    @ExceptionHandler(AuthenticationException.class)
    public Result unAuth(AuthenticationException e) {
        HttpKit.getRequest().setAttribute("tip", "登录失效，请重新登录");
        log.error("登录失效，请重新登录：", e);
        return Result.error(BizExceptionEnum.USER_NOT_LOGIN.getFriendlyCode(), BizExceptionEnum.USER_NOT_LOGIN.getFriendlyMsg());
    }

    /**
     * 账号密码错误
     */
    @ExceptionHandler(LoginException.class)
    public Result loginError(LoginException e) {
        String account = HttpKit.getRequest().getParameter("account");
        // loginHelper.checkErrorLogin(account);
        HttpKit.getRequest().setAttribute("tip", loginHelper.getErrorMsg(account));
        log.error("账号密码错误：", e);
        return Result.error(e.getFriendlyCode(), e.getFriendlyMsg());
    }

    /**
     * 无权访问该资源
     */
    @ExceptionHandler(UndeclaredThrowableException.class)
    public Result credentials(UndeclaredThrowableException e) {
        HttpKit.getRequest().setAttribute("tip", "权限异常");
        log.error("权限异常!", e);
        return Result.error(String.valueOf(BizExceptionEnum.NO_PERMISSION.getFriendlyCode()), BizExceptionEnum.NO_PERMISSION.getFriendlyMsg());
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public Result notFount(RuntimeException e) {
        LoginAdminResDTO loginAdminResDTO = (LoginAdminResDTO) SecurityUtils.getSubject().getPrincipal();
        //Long accountId = loginAdminResDTO.getAccountId();
        HttpKit.getRequest().setAttribute("tip", "服务器未知运行时异常");
        log.error("运行时异常:", e);
        return Result.error(String.valueOf(BizExceptionEnum.SERVER_ERROR.getFriendlyCode()), BizExceptionEnum.SERVER_ERROR.getFriendlyMsg());
    }

    /**
     * session失效的异常拦截
     */
    @ExceptionHandler(InvalidSessionException.class)
    public Result sessionTimeout(InvalidSessionException e, Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("tips", "session超时");
        assertAjax(request, response);
        return Result.error(BizExceptionEnum.SESSION_TIMEOUT.getFriendlyCode(), BizExceptionEnum.SESSION_TIMEOUT.getFriendlyMsg());
    }

    /**
     * session异常
     */
    @ExceptionHandler(UnknownSessionException.class)
    public Result sessionTimeout(UnknownSessionException e, Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("tips", "session超时");
        assertAjax(request, response);
        return Result.error(BizExceptionEnum.SESSION_TIMEOUT.getFriendlyCode(), BizExceptionEnum.SESSION_TIMEOUT.getFriendlyMsg());
    }

    private void assertAjax(HttpServletRequest request, HttpServletResponse response) {
        if (request.getHeader("x-requested-with") != null
                && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
            //如果是ajax请求响应头会有，x-requested-with
            response.setHeader("sessionstatus", "timeout");//在响应头设置session状态
        }
    }

}
