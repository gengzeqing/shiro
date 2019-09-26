package com.example.shiro.service.shiro.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.shiro.common.enums.DeletedStatus;
import com.example.shiro.mapper.SysRoleMenuMapper;
import com.example.shiro.model.sys.SysRoleMenu;
import com.example.shiro.service.shiro.ISysRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 文章管理表 服务实现类
 * </p>
 *
 * @author yaokui
 * @since 2019-06-04
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements ISysRoleMenuService {

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public List<Long> findMenuIdsByRoleIds(String roleIds) {
        return sysRoleMenuMapper.findMenuIdsByRoleIds(roleIds);
    }

    @Override
    public int getCountByMenuId(Long menuId) {
        return sysRoleMenuMapper.getCountByMenuId(menuId);
    }

    @Override
    public void bindMenus(Long roleId, List<Long> menuIdList) {
        // 删除角色原分配的权限
        sysRoleMenuMapper.deleteByRoleId(roleId);
        // 绑定权限
        if (menuIdList != null || menuIdList.size() > 0) {
            for (Long menuId : menuIdList) {
                SysRoleMenu sysRoleMenu = new SysRoleMenu();
                sysRoleMenu.setRoleId(roleId);
                sysRoleMenu.setMenuId(menuId);
                sysRoleMenu.setDeleted(DeletedStatus.UN_DELETED.getCode());
                sysRoleMenuMapper.insert(sysRoleMenu);
            }
        }
    }
}
