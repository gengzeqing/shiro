package com.example.shiro.service.sys;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.shiro.model.base.Result;
import com.example.shiro.model.sys.SysAdmin;
import com.example.shiro.model.sys.SysMenu;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 管理员表 服务类
 * </p>
 *
 * @author Generator
 * @since 2019-06-03
 */
public interface ISysAdminService {

    /**
     * 根据账号查询管理员信息
     * @param account
     * @return
     */
    SysAdmin getByAccount(String account);

    /**
     * 根据管理员ID查询管理员信息
     * @param userId
     * @return
     */
    SysAdmin selectById(Long userId);

    /**
     * 管理员用户登录
     *
     * @param account
     * @param password
     * @return
     */
    Result loginByAccount(String account, String password);

    /**
     * 获取管理员登录菜单
     * @param account
     * @return
     */
    List<SysMenu> getLoginMenusByAccount(String account);
    /**
     * 根据条件查询管理员列表
     * @param page
     * @param conditions
     * @return
     */
    Page<SysAdmin> queryPage(Page<SysAdmin> page, Map<String, Object> conditions);

    /**
     * 根据管理员账户计数
     * @param account
     * @param accountId
     * @return
     */
    int countByAccount(String account, Long accountId);

    /**
     * 添加管理员用户
     * @param sysAdmin
     */
    void createAdmin(SysAdmin sysAdmin);

    /**
     * 修改管理员提交
     * @param sysAdmin
     */
    void updateAdmin(SysAdmin sysAdmin);

    /**
     * 删除用户
     * @param accountId
     */
    void deleteById(Long accountId);

    /**
     * 更新密码
     * @param accountId
     * @param newPassword
     */
    void updatePassword(Long accountId, String newPassword);

    /**
     * 冻结/解冻用户状态
     * @param accountId
     * @param enableStatus
     */
    void updateValid(Long accountId, int enableStatus);

    /**
     * 验证原密码
     * @param accountId
     * @param oldPassword
     * @return
     */
    boolean checkOldPwd(Long accountId, String oldPassword);

}
