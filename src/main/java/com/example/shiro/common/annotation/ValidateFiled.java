package com.example.shiro.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author rudolf.
 * @date 2019/10/8.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateFiled {
    /**
     * 是否为空
     * @return
     */
    boolean notNull () default false;

    /**
     * 索引位置 [ 索引位置 如果是对象的话就是0 当前仅支持对象数据传参 ]
     * @return
     */
    int index () default -1;

    /**
     * 如果参数是基本数据类型或String ，就不用指定该参数，如果参数是对象，要验证对象里面某个属性，就用该参数指定属性名
     */
    String filedName () default "";

    /**
     * 错误信息
     * @return
     */
    String message () default "";

    /**
     * 验证长度
     * @return
     */
    int length () default -1;

}
