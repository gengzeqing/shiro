package com.example.shiro.service.sys.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.shiro.common.enums.DeletedStatus;
import com.example.shiro.mapper.SysDeptMapper;
import com.example.shiro.model.sys.SysDept;
import com.example.shiro.service.sys.ISysDeptService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 文章管理表 服务实现类
 * </p>
 *
 * @author yaokui
 * @since 2019-06-04
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements ISysDeptService {

    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Override
    public SysDept selectById(Long deptId) {
        SysDept sysDept = sysDeptMapper.selectById(deptId);
        return sysDept;
    }

    @Override
    public Page<SysDept> queryPage(Page<SysDept> page) {
        List<SysDept> dataList = sysDeptMapper.queryPage(page,page.getCondition());

        page.setRecords(dataList);
        return page;
    }

    @Override
    @Transactional
    public void createDept(SysDept sysDept) {
        sysDeptMapper.insert(sysDept);
    }

    @Override
    @Transactional
    public void updateDept(SysDept sysDept) {
        sysDeptMapper.updateById(sysDept);
    }

    @Override
    @Transactional
    public void deleteById(Long deptId) {
        sysDeptMapper.deleteDeptById(deptId, DeletedStatus.DELETED.getCode());
    }

    @Override
    public List<Map<String, Object>> queryDeptList() {
        List<SysDept> sysDeptList = sysDeptMapper.queryDeptList();

        List<Map<String, Object>> deptList = Lists.newArrayList();
        sysDeptList.forEach(sysDept -> {
            Map<String, Object> param = new HashMap<>();
            param.put("deptId", sysDept.getId());
            param.put("simpleName", sysDept.getSimpleName());
            param.put("fullName", sysDept.getFullName());
            deptList.add(param);
        });

        return deptList;
    }
}
