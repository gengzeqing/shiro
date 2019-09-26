package com.example.shiro.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.shiro.model.sys.SysDept;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 部门表 Mapper 接口
 * </p>
 *
 * @author Generator
 * @since 2019-06-03
 */
@Repository
public interface SysDeptMapper extends CommonMapper<SysDept> {

    /**
     * 分页查询部门列表
     * @param page
     * @param condition
     * @return
     */
    List<SysDept> queryPage(Page<SysDept> page, Map<String, Object> condition);

    /**
     * 按ID删除部门
     * @param deptId
     * @param deleted
     */
    void deleteDeptById(@Param("deptId") Long deptId, @Param("deleted") int deleted);

    /**
     * 查询所有可用的部门列表
     * @return
     */
    List<SysDept> queryDeptList();
}
