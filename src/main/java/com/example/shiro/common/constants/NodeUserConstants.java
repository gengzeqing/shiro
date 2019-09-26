package com.example.shiro.common.constants;

/**
 * @author chenhaili
 * @date 2019/6/25
 */
public class NodeUserConstants {
    /**
     * 盐 长度
     */
    public static int SALT_LENGTH=20;

    /**  密码加密算法 */
    public final static String HASH_ALGORITHM_NAME = "SHA-256";
    /**  密码循环次数 */
    public final static int HASH_ITERATIONS = 16;

    public final static String ACCESS_URL_PREFIX="https://";

    /**
     * 身份证或营业执照有效期 长期
     */
    public final static String ID_CARD_LONG_TERM="长期";

    /**
     * 京东打款认证接口固定字符串
     */
    public final static  String BLICSCOPE="经营范围";

    /**
     * 记住我 失效时间 30天 ,单位秒
     */
    public final static Integer COOKIE_AGE=2592000;
    /**
     * cookie加密的密钥
     */
    public final static String REMEMBER_ME_PRIKEY="4AvVhmFLUs0KTA3Kprsdag==";

    /**
     * 官网-以元为单位金额精确位数
     */
    public final  static Integer SET_SCALE=2;

    /**
     * 票面金额保留6为小数 四舍五入
     */
    public final static Integer BILL_AMOUNT_SET_SCALE=6;

    /**
     * 票据上传支付的图片类型
     */
    public final static String BILL_UPLOAD_PIC_TYPE="jpg,png,bmp,gif";

    /**
     * 一整年天数
     */
    public final static Integer ALL_YEAR_DAYS=360;

    /**
     * 十万
     */
    public final static Integer TEN_AMOUNT=100000;

    /**
     * 订单支付过期时长
     */
    public final static Integer ORDER_PAY_EXPIRE=1;

    /**
     * 1元=100分
     */
    public final static Integer Y2F=100;

    /**
     * 1万=10000元
     */
    public final static Integer W2Y=10000;

    /**
     * 1万=1000000分
     */
    public final static Integer W2F=1000000;

    /**
     * 京东支付成功结果页
     */
    public static final String PAYMENTSUCCESSPAGEURL="";


}
