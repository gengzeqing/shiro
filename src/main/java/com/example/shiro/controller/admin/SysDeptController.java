package com.example.shiro.controller.admin;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.shiro.common.enums.BizExceptionEnum;
import com.example.shiro.controller.BaseController;
import com.example.shiro.exception.BusinessException;
import com.example.shiro.model.base.PageResp;
import com.example.shiro.model.base.Result;
import com.example.shiro.model.builder.SysDeptBuilder;
import com.example.shiro.model.dto.SysDeptReqDTO;
import com.example.shiro.model.dto.SysDeptResDTO;
import com.example.shiro.model.form.SysDeptForm;
import com.example.shiro.model.sys.SysDept;
import com.example.shiro.service.sys.ISysDeptService;
import com.example.shiro.utils.ParamsCheckUtils;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 系统部门控制器
 *
 * @Author: yaokui
 * @Date: 2019/6/10
 */
@Slf4j
@RestController
@RequestMapping("/ms/authority/dept")
public class SysDeptController extends BaseController {

    @Autowired
    private ISysDeptService sysDeptService;

    /**
     * 部门分页列表
     * @param sysDeptReqDTO
     * @return
     */
    @PostMapping("/list")
    public Result<PageResp<SysDeptResDTO>> queryPage(@RequestBody SysDeptReqDTO sysDeptReqDTO) {
        Page<SysDept> page = sysDeptReqDTO.toPage();
        Page<SysDept> sysDeptPage = sysDeptService.queryPage(page);
        List<SysDept> sysDeptList = sysDeptPage.getRecords();

        List<SysDeptResDTO> sysDeptResDTOList = Lists.newArrayList();
        sysDeptList.forEach(user -> sysDeptResDTOList.add(SysDeptBuilder.modelToDto(user)));

        Result<PageResp<SysDeptResDTO>> sysDeptResPage = PageResp.success(sysDeptPage,sysDeptResDTOList);
        return sysDeptResPage;
    }

    /**
     * 部门列表 （下拉）
     *
     * @return
     */
    @GetMapping("/allList")
    public Result deptList() {
        List<Map<String, Object>> deptList = sysDeptService.queryDeptList();

        return Result.success(deptList);
    }

    /**
     * 添加部门
     *
     * @param sysDeptForm
     * @return
     */
    @PostMapping("/create")
    public Result create(@RequestBody SysDeptForm sysDeptForm) {
        //校验参数
        if (ParamsCheckUtils.isNull(sysDeptForm.getFullName())) {
            throw new BusinessException(BizExceptionEnum.REQUEST_INVALIDATE);
        }

        SysDept sysDept = SysDeptBuilder.createModel(sysDeptForm);
        // 保存部门信息
        sysDeptService.createDept(sysDept);
        log.info("添加部门成功");

        return Result.success();
    }

    /**
     * 更新部门 查询部门
     *
     * @param deptId
     * @return
     */
    @GetMapping("/forUpdate/{deptId}")
    public Result forUpdate(@PathVariable Long deptId) {
        //校验参数
        if (deptId == null) {
            throw new BusinessException(BizExceptionEnum.REQUEST_INVALIDATE);
        }

        SysDept sysDept =  sysDeptService.selectById(deptId);
        if (sysDept == null) {
            throw new BusinessException(BizExceptionEnum.DEPT_NOT_EXISTED);
        }
        SysDeptResDTO sysDeptResDTO = SysDeptBuilder.modelToDto(sysDept);
        return Result.success(sysDeptResDTO);
    }

    /**
     * 修改部门
     *
     * @param sysDeptForm
     * @return
     */
    @PostMapping("/update")
    public Result update(@RequestBody SysDeptForm sysDeptForm) {
        //校验参数
        if (ParamsCheckUtils.isNull(sysDeptForm.getFullName())) {
            throw new BusinessException(BizExceptionEnum.REQUEST_INVALIDATE);
        }

        SysDept sysDept = sysDeptService.selectById(sysDeptForm.getDeptId());
        sysDept = SysDeptBuilder.updateModel(sysDeptForm, sysDept);

        // 修改部门提交
        sysDeptService.updateDept(sysDept);
        log.info("添加管理员部门成功");

        return Result.success();
    }

    /**
     * 删除部门
     *
     * @param deptId
     * @return
     */
    @GetMapping("/delete/{deptId}")
    public Result delete(@PathVariable Long deptId) {
        //校验参数
        if (deptId == null) {
            throw new BusinessException(BizExceptionEnum.REQUEST_INVALIDATE);
        }

        SysDept sysDept = sysDeptService.selectById(deptId);
        if (sysDept == null) {
            throw new BusinessException(BizExceptionEnum.DEPT_NOT_EXISTED);
        }

        // 删除部门
        sysDeptService.deleteById(deptId);

        return Result.success();
    }

}
