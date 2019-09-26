package com.example.shiro.service.shiro.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.shiro.common.enums.DeletedStatus;
import com.example.shiro.mapper.SysMenuMapper;
import com.example.shiro.model.sys.SysMenu;
import com.example.shiro.service.shiro.ISysMenuService;
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
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public SysMenu selectById(Long menuId) {
        SysMenu sysMenu = sysMenuMapper.selectById(menuId);
        return sysMenu;
    }

    @Override
    public int getCountByCode(String menuCode, Long menuId) {
        int menuCount = sysMenuMapper.getCountByCode(menuCode, menuId);
        return menuCount;
    }

    @Override
    public List<SysMenu> findByMenuIds(String menuIds) {
        List<SysMenu> sysMenuList = sysMenuMapper.findByMenuIds(menuIds);
        
        return sysMenuList;
    }

    @Override
    public Page<SysMenu> queryPage(Page<SysMenu> page) {
        List<SysMenu> dataList = sysMenuMapper.queryPage(page, page.getCondition());

        page.setRecords(dataList);
        return page;
    }

    @Override
    public List<Map<String, Object>> queryMenuList(int menuType) {
        List<SysMenu> sysMenuList = sysMenuMapper.queryMenuList(menuType);

        List<Map<String, Object>> menuList = Lists.newArrayList();
        sysMenuList.forEach(sysMenu -> {
            Map<String, Object> param = new HashMap<>();
            param.put("menuId", sysMenu.getId());
            param.put("menuCode", sysMenu.getCode());
            param.put("menuName", sysMenu.getName());
            menuList.add(param);
        });

        return menuList;
    }

    @Override
    public List<SysMenu> queryAllMenuList() {
        List<SysMenu> sysMenuList = sysMenuMapper.queryAllMenuList();

        return sysMenuList;
    }

    @Override
    @Transactional
    public void createMenu(SysMenu sysMenu) {
        sysMenuMapper.insert(sysMenu);
    }

    @Override
    @Transactional
    public void updateMenu(SysMenu sysMenu) {
        sysMenuMapper.updateAllColumnById(sysMenu);
    }

    @Override
    @Transactional
    public void deleteById(Long menuId) {
        sysMenuMapper.deleteMenuById(menuId, DeletedStatus.DELETED.getCode());
    }

    @Override
    public int getCountByParentId(Long menuId) {
        return sysMenuMapper.getCountByParentId(menuId);
    }

}
