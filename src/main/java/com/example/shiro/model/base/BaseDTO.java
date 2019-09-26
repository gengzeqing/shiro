/*
 * Copyright (c) 2018 北京德鸿普惠科技有限公司.
 * All rights reserved.
 */
package com.example.shiro.model.base;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * 所有的Model类或DO类的基类，实现Serializable接口，子类默认可序列化.
 * <p>采用JSON格式输出当前对象的字符串表示，其中Date类型的输出格式为：yyyy-MM-dd HH:mm:ss.S
 *
 * @author 李春杰 创建于 19/2/19
 */
public class BaseDTO implements Serializable {
    private static final long serialVersionUID = -7756343144806558003L;

    @Override
    public String toString() {
        return getClass().getSimpleName() + JSON.toJSONStringWithDateFormat(this, "yyyy-MM-dd HH:mm:ss.S");
    }
}
