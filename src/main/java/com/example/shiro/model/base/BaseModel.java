package com.example.shiro.model.base;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.activerecord.Model;

import java.io.Serializable;

/**
 * @author: tanxuhui
 * @create: 2019-05-28 10:38
 */
public class BaseModel<T extends BaseModel> extends Model<T> {


    @Override
    protected Serializable pkVal() {
        return null;
    }


    @Override
    public String toString() {
        return getClass().getSimpleName() + JSON.toJSONStringWithDateFormat(this, "yyyy-MM-dd HH:mm:ss.S");
    }
}
