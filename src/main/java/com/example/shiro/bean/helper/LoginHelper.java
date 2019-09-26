package com.example.shiro.bean.helper;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.example.shiro.common.constants.Administrator;
import com.example.shiro.common.constants.RedisConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 登录处理
 * @Author: yaokui
 * @Date: 2019/6/5
 */
@Component
public class LoginHelper {

    @Autowired
    public RedisTemplate<String, String> redisTemplate;

    public void checkErrorLogin(String account)  {
        // 不能冻结超级管理员
        if (Administrator.SYS_ADMIN_ACCOUNT.equals(account) || Administrator.DHPH_ADMIN_ACCOUNT.equals(account)) {
            return;
        }

        Integer errorNumber = getErrorNumber(account);
        BoundValueOperations<String, String> boundValueOperations = redisTemplate.boundValueOps(RedisConstants.KEY_PREFIX_PASSWORD_ERROR_CURRENT_COUNT + account);
        if(boundValueOperations != null){
            if(errorNumber != null){
                if(errorNumber.equals(RedisConstants.KEY_PASSWORD_ERROR_MAX_COUNT)){
                    return;
                } else {
                    AtomicInteger count = new AtomicInteger(errorNumber);
                    count.getAndIncrement();
                    boundValueOperations.set(String.valueOf(count.get()));
                    if(count.get() == RedisConstants.KEY_PASSWORD_ERROR_MAX_COUNT){
                        boundValueOperations.expire(10, TimeUnit.MINUTES);
                    }
                }
            } else {
                // 第一次登录失败
                boundValueOperations.set("1");
            }
        }
    }

    public Integer getErrorNumber(String account){
        BoundValueOperations<String, String> boundValueOperations = redisTemplate.boundValueOps(RedisConstants.KEY_PREFIX_PASSWORD_ERROR_CURRENT_COUNT + account);
        if(boundValueOperations != null) {
            String cacheValue = boundValueOperations.get();
            if(StringUtils.isNotEmpty(cacheValue)){
                return Integer.valueOf(cacheValue);
            }
        }
        return null;
    }

    public String getErrorMsg(String account){
        Integer cacheValue = getErrorNumber(account);
        // for admin user error
        if(cacheValue == null){
            return "账号或密码错误";
        }
        // for freeze user
        if(cacheValue.equals(RedisConstants.KEY_PASSWORD_ERROR_MAX_COUNT)) {
            return "此账号由于账号或密码输入错误次数过多，已被冻结10分钟，或联系管理员解决该问题";
        }
        // for error number
        return String.format("账号或密码错误，您还有%s次机会", RedisConstants.KEY_PASSWORD_ERROR_MAX_COUNT - cacheValue);
    }

    public boolean isFreeze(String account){
        Integer cacheValue = getErrorNumber(account);
        if(cacheValue == null){
            return false;
        }
        if(cacheValue.equals(RedisConstants.KEY_PASSWORD_ERROR_MAX_COUNT)){
            return true;
        }
        return false;
    }

    public void deleteErrorLogin(String account){
        redisTemplate.delete(RedisConstants.KEY_PREFIX_PASSWORD_ERROR_CURRENT_COUNT + account);
    }

}
