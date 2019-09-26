package com.example.shiro.common.constants;

/**
 * redis常量配置
 * @author chenhaili
 * @date 2019/6/24
 */
public class RedisConstants {
    /**
     * 官网-注册-系统验证码前缀
     */
    public static String NODE_REGISTER_CAPTCHA_PREFIX="TIEBEI_NODE_REGISTER_CAPTCHA_";

    /**
     * 官网-登陆-系统验证码前缀
     */
    public static String NODE_LOGIN_CAPTCHA_PREFIX="TIEBEI_NODE_LOGIN_CAPTCHA_";

    /**
     * 官网-注册-短信验证码次数控制前缀
     */
    public static String NODE_REGISTER_SMS_CODE_INCRE_PREFIX="TIEBEI_NODE_REGISTER_SMS_CODE_INCRE_";

    /**
     * 官网-登陆-短信验证码次数控制前缀
     */
    public static String NODE_LOGIN_SMS_CODE_INCRE_PREFIX="TIEBEI_NODE_LOGIN_SMS_CODE_INCRE_";

    /**
     * 官网-登陆前缀
     */
    public static String NODE_LOGIN_PREIFX="TIEBEI_NODE_LOGIN_";
    /**
     * 官网-交易-短信验证码次数控制前缀
     */
    public static String NODE_TRANSACTION_SMS_CODE_INCRE_PREFIX="TIEBEI_NODE_TRANSACTION_SMS_CODE_INCRE_";

    /**
     * 官网-忘记密码-短信验证码次数控制前缀
     */
    public static String NODE_FORGETPWD_SMS_CODE_INCRE_PREFIX="TIEBEI_NODE_FORGETPWD_SMS_CODE_INCRE_";
    /**
     * 官网-注册-短信验证码值前缀
     */
    public static String NODE_REGISTER_SMS_CODE_VAL_PREFIX="TIEBEI_NODE_REGISTER_SMS_CODE_VAL_";

    /**
     * 官网-登陆-短信验证码值前缀
     */
    public static String NODE_LOGIN_SMS_CODE_VAL_PREFIX="TIEBEI_NODE_LOGIN_SMS_CODE_VAL_";
    /**
     * 官网-交易-短信验证码值前缀
     */
    public static String NODE_TRANSACTION_SMS_CODE_VAL_PREFIX="TIEBEI_NODE_TRANSACTION_SMS_CODE_VAL_";

    /**
     * 官网-忘记密码-短信验证码值前缀
     */
    public static String NODE_FORGETPWD_SMS_CODE_VAL_PREFIX="TIEBEI_NODE_FORGETPWD_SMS_CODE_VAL_";

    /**
     * 官网-注册-短信验证码redis increment 步长
     */
    public static int NODE_REGISTER_REDIS_INCR_STEP=1;

    /**
     * 官网-短信和图片验证码过期时间:10分钟 即600秒
     */
    public static int NODE_SMSCODE_AND_CAPTCHA_EXPIRE = 600;

    /**
     * 官网-注册和登陆-短信验证码发送次数
     */
    public static int NODE_REGISTER_AND_LOGIN_AND_FORGETPWD_SMS_CODE_SEND_COUNT=10;

    /**
     * 一整天的毫秒数
     */
    public static long SIX_HOUR_MILLISECOND=86400000L;

    //-----------------姚奎 开始----------------------
    /**
     * 过期时间 - 验证码 - 10分钟
     */
    public final static long EXPIRE_10_MINUTES = 60 * 10;
    /**
     * 过期时间 - token - 30分钟
     */
    public final static long EXPIRE_30_MINUTES = 60 * 30;
    /**
     * key前缀 - 验证码前缀 - WMP:CAPTCHA:
     */
    public static final String KEY_PREFIX_LOGIN_CAPTCHA = "INSURANCE_CAPTCHA_";
    /**
     * key前缀 - 被锁定的用户名
     */
    public static final String KEY_PREFIX_LOCKED_USERNAME = "INSURANCE_LOCKED_USERNAME_";
    /**
     * key前缀 - 当前用户密码输入错误次数
     */
    public static final String KEY_PREFIX_PASSWORD_ERROR_CURRENT_COUNT = "INSURANCE_PASSWORD_ERROR_";
    /**
     * key前缀 - 密码错误锁定次数 - 5
     */
    public static final int KEY_PASSWORD_ERROR_MAX_COUNT = 5;
    /**
     * key前缀 - 当前登录的管理员账号
     */
    public static final String KEY_PREFIX_LOGIN_ACCOUNT = "INSURANCE_LOGIN_ACCOUNT_";
    //-----------------姚奎 结束------------

    /**
     * key前缀-用户类型:1.买方,2.卖方
     */
    public static final String KEY_PREFIX_USER_TYPE="TIEBEI_USER_TYPE_";

    /**
     * key-百度ocr识别token key
     */
    public static final String BAIDU_OCR_ACCESS_TOKEN_KEY="BAIDU_OCR_ACCESS_TOKEN_KEY";

    /**
     * 百度ocr access_token 存活时间
     */
    public static final Long  BAIDU_ORC_ACCESS_TOKEN_EXPIRE=86400*28L;

    public static final String NULL_STR="null";
}
