# 0级部门
INSERT INTO sys_dept (id, parent_id, simple_name, full_name, priority, tips, deleted)
VALUES (1, 0, '总公司', '总公司', 1, '', 0);

# 超级管理员角色
INSERT INTO sys_role (id, parent_id, name, alias, dept_id, priority, deleted)
VALUES (1, 0, '超级管理员', 'administrator', 1, 1, 0);

# 超级管理员用户
INSERT INTO sys_admin (id, account, cn_name, password, salt, sex, email, phone, dept_id, desensitization_type, status, deleted)
VALUES (1, 'SysAdmin', '系统管理员', 'b4c04acac6dc70b816a5f33bab962a2b', '7cade2addd830ae59a75afbfef09246e', 1, 'cas@qq.com', '18200000000', 1, 1, 1, 0);
INSERT INTO sys_admin (id, account, cn_name, password, salt, sex, email, phone, dept_id, desensitization_type, status, deleted)
VALUES (2, 'DhphAdmin', '系统管理员', '7442e32dc54c816a3de9f8b1daf1d1d9', '946c5d93d8a6989a545a5cc6824dc9b7', 1, 'dhph@dhbank.com', null, 1, 0, 1, 0);
INSERT INTO sys_admin (id, account, cn_name, password, salt, sex, email, phone, dept_id, desensitization_type, status, deleted)
VALUES (3, 'BillAdmin', '绿洲保险', 'f495b9a29cae1dbd9fdbd6124635f647', '12baf2c1436fc2d308fc25439044b5da', 1, null, null, 1, 0, 1, 0);

# 管理员用户关联角色
INSERT INTO sys_admin_role (id, user_id, role_id, deleted) VALUES (1, 1, 1, 0);
INSERT INTO sys_admin_role (id, user_id, role_id, deleted) VALUES (2, 2, 1, 0);
INSERT INTO sys_admin_role (id, user_id, role_id, deleted) VALUES (3, 3, 1, 0);

# 初始化菜单
INSERT INTO sys_menu (id, code, parent_id, name, icon, url, priority, levels, type, tips, status, is_open, deleted) VALUES (2, 'content', 0, '运营管理', null, null, 2, 1, 1, null, 1, 0, 0);
INSERT INTO sys_menu (id, code, parent_id, name, icon, url, priority, levels, type, tips, status, is_open, deleted) VALUES (3, 'product', 0, '业务管理', null, null, 3, 1, 1, null, 1, 0, 0);
INSERT INTO sys_menu (id, code, parent_id, name, icon, url, priority, levels, type, tips, status, is_open, deleted) VALUES (4, 'customer', 0, '客户管理', null, null, 4, 1, 1, null, 1, 0, 0);
INSERT INTO sys_menu (id, code, parent_id, name, icon, url, priority, levels, type, tips, status, is_open, deleted) VALUES (5, 'authority', 6, '权限管理', null, null, 5, 1, 1, null, 1, 0, 0);
INSERT INTO sys_menu (id, code, parent_id, name, icon, url, priority, levels, type, tips, status, is_open, deleted) VALUES (6, 'system', 0, '系统设置', null, null, 6, 1, 1, null, 1, 0, 0);
INSERT INTO sys_menu (id, code, parent_id, name, icon, url, priority, levels, type, tips, status, is_open, deleted) VALUES (7, 'user', 5, '用户管理', 'fa-cog fa-spin', '/setting/auth/user', 1, 2, 1, null, 1, 0, 0);
INSERT INTO sys_menu (id, code, parent_id, name, icon, url, priority, levels, type, tips, status, is_open, deleted) VALUES (8, 'dept', 5, '部门管理', 'fa-cog fa-spin', '/setting/auth/dept', 2, 2, 1, null, 1, 0, 0);
INSERT INTO sys_menu (id, code, parent_id, name, icon, url, priority, levels, type, tips, status, is_open, deleted) VALUES (9, 'role', 5, '角色管理', 'fa-cog fa-spin', '/setting/auth/roles', 3, 2, 1, null, 1, 0, 0);
INSERT INTO sys_menu (id, code, parent_id, name, icon, url, priority, levels, type, tips, status, is_open, deleted) VALUES (10, 'menu', 5, '菜单管理', 'fa-cog fa-spin', '/setting/auth/menu', 4, 2, 1, 'setting.auth.menu.list', 1, 0, 0);
INSERT INTO sys_menu (id, code, parent_id, name, icon, url, priority, levels, type, tips, status, is_open, deleted) VALUES (11, 'user_list', 7, '查询用户列表', null, '/authority/user/list', 1, 3, 2, 'setting.auth.user.list', 1, 0, 0);
INSERT INTO sys_menu (id, code, parent_id, name, icon, url, priority, levels, type, tips, status, is_open, deleted) VALUES (12, 'user_add', 7, '添加用户', null, '/authority/user/create', 2, 3, 2, 'setting.auth.user.create', 1, 0, 0);
INSERT INTO sys_menu (id, code, parent_id, name, icon, url, priority, levels, type, tips, status, is_open, deleted) VALUES (13, 'user_edit', 7, '修改用户', null, '/authority/user/edit', 3, 3, 2, 'setting.auth.user.update', 1, 0, 0);
INSERT INTO sys_menu (id, code, parent_id, name, icon, url, priority, levels, type, tips, status, is_open, deleted) VALUES (14, 'user_delete', 7, '删除用户', null, '/authority/user/delete', 4, 3, 2, 'setting.auth.user.destroy', 1, 0, 0);
INSERT INTO sys_menu (id, code, parent_id, name, icon, url, priority, levels, type, tips, status, is_open, deleted) VALUES (15, 'user_reset_pwd', 7, '重置密码', null, '/authority/user/resetPwd', 5, 3, 2, 'setting.auth.user.resetPassword', 1, 0, 0);
INSERT INTO sys_menu (id, code, parent_id, name, icon, url, priority, levels, type, tips, status, is_open, deleted) VALUES (16, 'user_frozen', 7, '冻结用户', null, '/authority/user/frozen', 6, 3, 2, 'setting.auth.user.frozen', 1, 0, 0);
INSERT INTO sys_menu (id, code, parent_id, name, icon, url, priority, levels, type, tips, status, is_open, deleted) VALUES (17, 'user_unfrozen', 7, '解冻用户', null, '/authority/user/unfrozen', 7, 3, 2, null, 1, 0, 0);
INSERT INTO sys_menu (id, code, parent_id, name, icon, url, priority, levels, type, tips, status, is_open, deleted) VALUES (18, 'user_allot', 7, '分配角色', null, '/authority/user/allot', 8, 3, 2, 'setting.auth.user.roles', 1, 0, 0);
INSERT INTO sys_menu (id, code, parent_id, name, icon, url, priority, levels, type, tips, status, is_open, deleted) VALUES (19, 'dept_list', 8, '查询部门列表', null, '/authority/dept/list', 1, 3, 2, 'setting.auth.dept.list', 1, 0, 0);
INSERT INTO sys_menu (id, code, parent_id, name, icon, url, priority, levels, type, tips, status, is_open, deleted) VALUES (20, 'dept_add', 8, '添加部门', null, '/authority/dept/create', 2, 3, 2, 'setting.auth.dept.create', 1, 0, 0);
INSERT INTO sys_menu (id, code, parent_id, name, icon, url, priority, levels, type, tips, status, is_open, deleted) VALUES (21, 'dept_edit', 8, '修改部门', null, '/authority/dept/edit', 2, 3, 2, 'setting.auth.dept.update', 1, 0, 0);
INSERT INTO sys_menu (id, code, parent_id, name, icon, url, priority, levels, type, tips, status, is_open, deleted) VALUES (22, 'dept_delete', 8, '删除部门', null, '/authority/dept/delete', 2, 3, 2, 'setting.auth.dept.destroy', 1, 0, 0);
INSERT INTO sys_menu (id, code, parent_id, name, icon, url, priority, levels, type, tips, status, is_open, deleted) VALUES (23, 'role_list', 9, '查看角色列表', null, 'setting.auth.roles.list', 1, 3, 2, 'setting.auth.roles.list', 1, 0, 0);
INSERT INTO sys_menu (id, code, parent_id, name, icon, url, priority, levels, type, tips, status, is_open, deleted) VALUES (24, 'role_add', 9, '添加角色', null, '/authority/role/create', 2, 3, 2, 'setting.auth.roles.create', 1, 0, 0);
INSERT INTO sys_menu (id, code, parent_id, name, icon, url, priority, levels, type, tips, status, is_open, deleted) VALUES (25, 'role_edit', 9, '修改角色', null, '/authority/role/edit', 3, 3, 2, 'setting.auth.roles.update', 1, 0, 0);
INSERT INTO sys_menu (id, code, parent_id, name, icon, url, priority, levels, type, tips, status, is_open, deleted) VALUES (26, 'role_delete', 9, '删除角色', null, '/authority/role/delete', 4, 3, 2, 'setting.auth.roles.destroy', 1, 0, 0);
INSERT INTO sys_menu (id, code, parent_id, name, icon, url, priority, levels, type, tips, status, is_open, deleted) VALUES (27, 'role_allot', 9, '分配权限', null, '/authority/role/allot', 5, 3, 2, 'setting.auth.roles.permission', 1, 0, 0);
INSERT INTO sys_menu (id, code, parent_id, name, icon, url, priority, levels, type, tips, status, is_open, deleted) VALUES (28, 'menu_list', 10, '查看菜单列表', null, '/authority/menu/list', 1, 3, 2, 'setting.auth.menu.list', 1, 0, 0);
INSERT INTO sys_menu (id, code, parent_id, name, icon, url, priority, levels, type, tips, status, is_open, deleted) VALUES (29, 'menu_add', 10, '添加菜单', null, '/authority/menu/create', 2, 3, 2, 'setting.auth.menu.create', 1, 0, 0);
INSERT INTO sys_menu (id, code, parent_id, name, icon, url, priority, levels, type, tips, status, is_open, deleted) VALUES (30, 'menu_edit', 10, '修改菜单', null, '/authority/menu/edit', 3, 3, 2, 'setting.auth.menu.update', 1, 0, 0);
INSERT INTO sys_menu (id, code, parent_id, name, icon, url, priority, levels, type, tips, status, is_open, deleted) VALUES (31, 'menu_delete', 10, '删除菜单', null, '/authority/menu/delete', 4, 3, 2, 'setting.auth.menu.destroy', 1, 0, 0);

# 角色关联权限
INSERT INTO insurance.sys_role_menu (menu_id, role_id, deleted) VALUES
  (1, 1, 0), (2, 1, 0), (3, 1, 0), (4, 1, 0), (5, 1, 0), (6, 1, 0),
  (7, 1, 0), (8, 1, 0), (9, 1, 0), (10, 1, 0), (11, 1, 0), (12, 1, 0),
  (13, 1, 0), (14, 1, 0), (15, 1, 0), (16, 1, 0), (17, 1, 0), (18, 1, 0),
  (19, 1, 0), (20, 1, 0), (21, 1, 0), (22, 1, 0), (23, 1, 0), (24, 1, 0),
  (25, 1, 0), (26, 1, 0), (27, 1, 0), (28, 1, 0), (29, 1, 0), (30, 1, 0),
  (31, 1, 0);
