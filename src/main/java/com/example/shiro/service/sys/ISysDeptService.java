package com.example.shiro.service.sys;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.shiro.model.sys.SysDept;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 部门表 服务类
 * </p>
 *
 * @author Generator
 * @since 2019-06-03
 */
public interface ISysDeptService {

    /**
     * 根据ID查询部门信息
     * @param deptId
     * @return
     */
    SysDept selectById(Long deptId);

    /**
     * 分页查询部门列表
     * @param page
     * @return
     */
    Page<SysDept> queryPage(Page<SysDept> page);

    /**
     * 添加部门
     * @param sysDept
     */
    void createDept(SysDept sysDept);

    /**
     * 修改部门
     * @param sysDept
     */
    void updateDept(SysDept sysDept);

    /**
     * 删除部门
     * @param deptId
     */
    void deleteById(Long deptId);

    /**
     * 查询所有可用的部门列表
     * @return
     */
    List<Map<String, Object>> queryDeptList();
}
