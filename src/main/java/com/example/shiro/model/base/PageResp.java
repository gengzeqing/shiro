package com.example.shiro.model.base;

import com.baomidou.mybatisplus.plugins.Page;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 分页对象
 * @author: tanxuhui
 * @create: 2019-03-19 20:42
 */
@Setter
@Getter
public class PageResp<T> implements Serializable {
    private static final long serialVersionUID = -175830694466731423L;

    // 当前页
    private Integer pageNum;
    // 每页显示的总条数
    private Integer pageSize;
    // 总记录数
    private Integer totalCount;
    // 总页数
    private Integer totalPage;

    /**
     * 查询数据列表
     */
    private List<T> records = Collections.emptyList();

    /**
     * 返回成功状态的带业务数据内容的结果对象
     * @param page 分页结果
     * @param dataList 接口响应对象列表
     */
    public static <T> Result<PageResp<T>> success(Page page,List<T> dataList) {
        PageResp<T> PageResp = new PageResp();
        PageResp.setPageNum(page.getCurrent());
        PageResp.setPageSize(page.getSize());
        PageResp.setTotalCount(page.getTotal());
        PageResp.setTotalPage(page.getPages());
        PageResp.setRecords(dataList);
        return Result.success(PageResp);
    }


    /**
     * 返回成功状态的带业务数据内容的结果对象
     * @param page 分页结果
     */
    public static <T> Result<PageResp<T>> success(Page page) {
        return success(page,page.getRecords());
    }

}
