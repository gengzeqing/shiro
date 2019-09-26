package com.example.shiro.model.base;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.shiro.utils.MapUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * 分页对象
 * @author: tanxuhui
 * @create: 2019-03-19 20:42
 */
@Setter
@Getter
public class PageReq<T>  {
    private static final long serialVersionUID = 1L;
    // 当前页
    private Integer pageNum = 1;
    // 每页显示的总条数
    private Integer pageSize = 20;

    public Page<T> toPage(){
        Page<T> page = new Page<>(pageNum, pageSize);
        page.setCondition(MapUtils.object2Map(this));
        return page;
    }
}
