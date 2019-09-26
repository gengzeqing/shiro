-- 部门表
CREATE TABLE `sys_dept` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父部门id',
  `simple_name` varchar(45) DEFAULT NULL COMMENT '部门简称',
  `full_name` varchar(255) DEFAULT NULL COMMENT '部门全称',
  `priority` int(11) DEFAULT NULL COMMENT '排序',
  `tips` varchar(255) DEFAULT NULL COMMENT '提示',
  `deleted` tinyint(4) DEFAULT '0' COMMENT '删除(0：未删除  1：已删除）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='部门表';

-- 登录日志
CREATE TABLE `sys_login_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `log_name` varchar(255) DEFAULT NULL COMMENT '日志名称',
  `account` varchar(45) DEFAULT NULL COMMENT '管理员账号',
  `message` text COMMENT '具体消息',
  `ip` varchar(255) DEFAULT NULL COMMENT '登录ip',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=391 DEFAULT CHARSET=utf8 COMMENT='登录日志';

-- 菜单表
CREATE TABLE `sys_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `code` varchar(255) DEFAULT NULL COMMENT '菜单标识',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '菜单父id',
  `name` varchar(255) DEFAULT NULL COMMENT '菜单名称',
  `icon` varchar(255) DEFAULT NULL COMMENT '菜单图标',
  `url` varchar(1000) DEFAULT NULL COMMENT 'url地址',
  `priority` int(11) DEFAULT NULL COMMENT '菜单排序号',
  `levels` int(65) DEFAULT NULL COMMENT '菜单层级',
  `type` tinyint(4) DEFAULT NULL COMMENT '类型（0：其他  1：菜单  2：按钮）',
  `tips` varchar(255) DEFAULT NULL COMMENT '备注',
  `status` tinyint(4) DEFAULT NULL COMMENT '菜单状态（1：启用  2：冻结）',
  `is_open` tinyint(4) DEFAULT NULL COMMENT '是否打开（1:打开   0:不打开）',
  `deleted` tinyint(4) DEFAULT '0' COMMENT '删除(0：未删除  1：已删除）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=178 DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- 操作日志
CREATE TABLE `sys_operation_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `log_type` varchar(255) DEFAULT NULL COMMENT '日志类型',
  `log_name` varchar(255) DEFAULT NULL COMMENT '日志名称',
  `m_user_id` bigint(20) DEFAULT NULL COMMENT '管理员id',
  `class_name` varchar(255) DEFAULT NULL COMMENT '类名称',
  `method` text COMMENT '方法名称',
  `message` text COMMENT '备注信息',
  `ip` varchar(45) DEFAULT NULL COMMENT 'IP地址',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=859 DEFAULT CHARSET=utf8 COMMENT='操作日志';

-- 角色表
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(255) DEFAULT NULL COMMENT '角色名称',
  `alias` varchar(255) DEFAULT NULL COMMENT '角色别名',
  `priority` int(11) DEFAULT NULL COMMENT '排序优先级',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父角色id',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门id',
  `deleted` tinyint(4) DEFAULT '0' COMMENT '删除(0：未删除  1：已删除）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- 角色和菜单关联表
CREATE TABLE `sys_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单id',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色id',
  `deleted` tinyint(4) DEFAULT '0' COMMENT '删除(0：未删除  1：已删除）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1450 DEFAULT CHARSET=utf8 COMMENT='角色和菜单关联表';

-- 管理员表
CREATE TABLE `sys_admin` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `account` varchar(45) DEFAULT NULL COMMENT '账号',
  `cn_name` varchar(45) DEFAULT NULL COMMENT '姓名',
  `password` varchar(45) DEFAULT NULL COMMENT '密码',
  `salt` varchar(45) DEFAULT NULL COMMENT 'md5密码盐',
  `sex` tinyint(4) DEFAULT NULL COMMENT '性别（1：男 2：女）',
  `email` varchar(45) DEFAULT NULL COMMENT '电子邮件',
  `phone` varchar(45) DEFAULT NULL COMMENT '电话',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门id',
  `desensitization_type` tinyint(4) DEFAULT '0' COMMENT '0 脱敏访问 1正常访问',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态(0：冻结  1：启用）',
  `deleted` tinyint(4) DEFAULT '0' COMMENT '删除(0：未删除  1：已删除）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='管理员表';

-- 角色和菜单关联表
CREATE TABLE `sys_admin_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) DEFAULT NULL COMMENT '菜单id',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色id',
  `deleted` tinyint(4) DEFAULT '0' COMMENT '删除(0：未删除  1：已删除）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='角色和菜单关联表';