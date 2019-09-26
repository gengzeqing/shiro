package com.example.shiro.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.shiro.model.sys.SysAdmin;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 管理员表 Mapper 接口
 * </p>
 *
 * @author Generator
 * @since 2019-06-03
 */
public interface SysAdminMapper extends CommonMapper<SysAdmin> {

    /**
     * 通过账号获取用户
     *
     * @param account
     * @return
     * @date 2017年2月17日 下午11:07:46
     */
    SysAdmin getByAccount(@Param("account") String account);

    /**
     * 根据条件查询管理员列表
     * @param page
     * @param condition
     * @return
     */
    List<SysAdmin> queryPage(Page<SysAdmin> page, Map<String, Object> condition);

    /**
     * 根据管理员账户计数
     * @param account
     * @param accountId
     * @return
     */
    int countByAccount(@Param("account") String account, @Param("accountId") Long accountId);

    /**
     * 根据ID删除管理员用户
     * @param accountId
     * @param deleted
     */
    void deleteAdminById(@Param("accountId") Long accountId, @Param("deleted") Integer deleted);

    /**
     * 重置密码
     * @param accountId
     * @param password
     * @param salt
     */
    void updatePassword(@Param("accountId") Long accountId, @Param("password") String password, @Param("salt") String salt);

    /**
     * 更新管理员用户可用状态（冻结/解冻）
     * @param accountId
     * @param enableStatus
     */
    void updateValid(@Param("accountId") Long accountId, @Param("enableStatus") int enableStatus);
}
