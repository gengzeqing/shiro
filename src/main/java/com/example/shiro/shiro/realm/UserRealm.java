package com.example.shiro.shiro.realm;

import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.example.shiro.model.dto.LoginAdminResDTO;
import com.example.shiro.model.sys.SysMenu;
import com.example.shiro.service.shiro.ISysAdminRoleService;
import com.example.shiro.service.shiro.ISysMenuService;
import com.example.shiro.service.shiro.ISysRoleMenuService;
import com.example.shiro.shiro.AuthorityToken;
import com.example.shiro.utils.StrKit;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * shiro认证/授权
 */
@Component
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private ISysMenuService sysMenuService;

    @Autowired
    private ISysAdminRoleService sysAdminRoleService;

    @Autowired
    private ISysRoleMenuService sysRoleMenuService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof AuthorityToken;
    }

    /**
     * 认证(登录认证时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        return new SimpleAuthenticationInfo(token.getPrincipal(), token.getCredentials(), getName());
    }

    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        LoginAdminResDTO loginUser = (LoginAdminResDTO) principals.getPrimaryPrincipal();
        Long accountId = loginUser.getAccountId();
        Set<String> userPermissions = this.getUserPermissions(accountId);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(userPermissions);
        return info;
    }

    /**
     * 根据用户id查询拥有的接口访问权限
     *
     * @param accountId
     * @return
     */
    private Set<String> getUserPermissions(Long accountId) {
        // 获取用户关联的角色ID集
        List<Long> roleIdList = sysAdminRoleService.findRoleIdsByUserId(accountId);

        // 获取角色关联的菜单ID集
        List<Long> menuIdList = sysRoleMenuService.findMenuIdsByRoleIds(StrKit.listToStr(roleIdList));

        // 获取所有权限菜单
        List<SysMenu> menuList = sysMenuService.findByMenuIds(StrKit.listToStr(menuIdList));

        Set<String> permsSet = new HashSet<>();
        menuList.forEach(menu -> {
            if (Objects.nonNull(menu) && StringUtils.isNotEmpty(menu.getCode())) {
                permsSet.add(menu.getCode());
            }
        });

        return permsSet;
    }

}
