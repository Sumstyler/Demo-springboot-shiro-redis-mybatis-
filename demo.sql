/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50619
Source Host           : localhost:3306
Source Database       : demo

Target Server Type    : MYSQL
Target Server Version : 50619
File Encoding         : 65001

Date: 2018-04-27 15:47:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for export_template
-- ----------------------------
DROP TABLE IF EXISTS `export_template`;
CREATE TABLE `export_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(120) DEFAULT NULL COMMENT '模板名',
  `file_name` varchar(255) DEFAULT NULL COMMENT '模板文件名字',
  `new_file_name` varchar(255) DEFAULT NULL COMMENT '模板新的文件名',
  `export_name` varchar(255) DEFAULT NULL COMMENT '导出文件名',
  `creater` int(11) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` int(11) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `index_01` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Records of export_template
-- ----------------------------
INSERT INTO `export_template` VALUES ('6', '11', '1.png', 'bd74bf4a-76a6-47f9-a13d-b225344850e6.png', '22', '1', '2018-01-30 22:29:01', '1', '2018-01-31 10:08:47');
INSERT INTO `export_template` VALUES ('7', '22', 'a.ftl', 'c873a28a-3211-4cf8-94e8-0d1d526e5c9a.ftl', '22', '1', '2018-01-30 23:08:30', '1', '2018-01-31 10:11:55');
INSERT INTO `export_template` VALUES ('8', '1231', 'a.ftl', 'b63c3c5d-546b-4727-9d2d-042662533f6d.ftl', '1', '1', '2018-01-31 10:24:01', '1', '2018-01-31 10:52:57');
INSERT INTO `export_template` VALUES ('9', 'test', 'test.ftl', 'a1349b7c-3447-4687-a6bd-1aed91a26141.ftl', 'mytest.doc', '1', '2018-01-31 15:53:31', null, null);

-- ----------------------------
-- Table structure for file_info
-- ----------------------------
DROP TABLE IF EXISTS `file_info`;
CREATE TABLE `file_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(120) DEFAULT NULL,
  `newName` varchar(120) DEFAULT NULL,
  `size` int(11) DEFAULT NULL,
  `md5` varchar(256) DEFAULT NULL,
  `suffixName` varchar(20) DEFAULT NULL,
  `uploadTime` datetime DEFAULT NULL,
  `valid` char(1) DEFAULT NULL COMMENT 'Y:代表有效  N:代表删除',
  `creater` int(11) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index1` (`md5`,`valid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Records of file_info
-- ----------------------------
INSERT INTO `file_info` VALUES ('1', '1.png', '7b06bf32-4120-479f-ac68-b2b28a7e08a6.png', '29012', '7543D36080A66B267B121B3D1657C088', '.png', '2018-01-29 22:20:18', 'Y', null, '2018-01-29 22:20:18');
INSERT INTO `file_info` VALUES ('2', '2.png', 'cc85a353-c5f2-4db4-a8f8-d04bc70f90ad.png', '13819', 'AFDEA8F5FBBB3588C52B323CEE618C5C', '.png', '2018-01-29 23:46:47', 'Y', null, '2018-01-29 23:46:47');
INSERT INTO `file_info` VALUES ('3', '2.xlsx', '0c6a0c93-1ba5-4dca-86b8-1cbc242383f1.xlsx', '161851', 'ef36b820c8a38c7738ece6f4c16d2e99', '.xlsx', '2018-01-30 09:46:40', 'Y', null, '2018-01-30 09:46:40');

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `path` varchar(125) DEFAULT NULL,
  `percode` varchar(255) DEFAULT NULL,
  `parentid` int(11) DEFAULT NULL,
  `icon` varchar(125) DEFAULT NULL,
  `sid` int(3) DEFAULT NULL,
  `valid` char(1) DEFAULT NULL,
  `creater` int(11) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` int(11) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES ('1', '根节点', null, null, 'root', null, null, '1', 'Y', null, null, null, null);
INSERT INTO `permission` VALUES ('2', '主页', 'MENU', '/home', 'home', '1', 'fa-home', '2', 'Y', null, null, null, null);
INSERT INTO `permission` VALUES ('3', '系统管理', 'MENU', '', 'system:manager', '1', 'fa-table', '3', 'Y', null, null, null, null);
INSERT INTO `permission` VALUES ('4', '用户管理', 'MENU', '/user/manager', 'user:manager', '3', null, '4', 'Y', null, null, null, '2018-01-03 12:47:35');
INSERT INTO `permission` VALUES ('5', '角色管理', 'MENU', '/role/manager', 'role:manager', '3', null, '5', 'Y', null, null, null, null);
INSERT INTO `permission` VALUES ('6', '资源管理', 'MENU', '/resource/manager', 'resource:manager', '3', null, '6', 'Y', null, null, null, '2017-12-26 21:40:44');
INSERT INTO `permission` VALUES ('8', '用户保存', 'FUN', null, 'user:save', '4', null, '7', 'Y', null, '2018-01-03 21:20:12', null, '2018-01-03 21:20:43');
INSERT INTO `permission` VALUES ('9', '会话管理', 'MENU', '/sessions/manager', 'sessions:manager', '3', null, '8', 'Y', null, null, null, null);
INSERT INTO `permission` VALUES ('11', '测试', 'MENU', '/demo/test', 'demo:test', '1', null, '9', 'Y', null, '2018-01-29 23:18:14', null, null);
INSERT INTO `permission` VALUES ('12', '内容管理', 'MENU', '/content/table', 'content:manager', '3', null, '10', 'Y', null, '2018-01-30 11:00:00', null, null);
INSERT INTO `permission` VALUES ('13', '导出配置', 'MENU', '/export/table', 'export:manager', '3', null, '6', 'Y', null, '2018-01-30 15:27:47', null, null);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `valid` char(2) DEFAULT NULL,
  `creater` int(11) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` int(11) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '管理组', 'Y', '1', '2017-12-26 13:29:46', '1', '2017-12-26 13:51:25');
INSERT INTO `role` VALUES ('2', '用户组2', 'N', '1', '2017-12-26 13:54:37', '1', '2017-12-26 13:57:58');
INSERT INTO `role` VALUES ('3', '测试1', 'Y', '1', '2017-12-26 13:58:15', null, null);
INSERT INTO `role` VALUES ('4', '测试2', 'Y', '1', '2017-12-26 13:58:20', null, null);
INSERT INTO `role` VALUES ('5', '测试3', 'Y', '1', '2017-12-26 13:58:26', null, null);
INSERT INTO `role` VALUES ('6', '资源管理', 'Y', '1', '2017-12-26 13:58:31', '1', '2017-12-26 15:34:21');
INSERT INTO `role` VALUES ('7', '测试5', 'Y', '1', '2017-12-26 13:58:36', null, null);
INSERT INTO `role` VALUES ('8', '测试6', 'Y', '1', '2017-12-26 13:58:41', null, null);
INSERT INTO `role` VALUES ('10', '测试8', 'N', '1', '2017-12-26 13:58:53', '1', '2017-12-26 16:32:01');
INSERT INTO `role` VALUES ('11', '测试9', 'Y', '1', '2017-12-26 13:58:59', null, null);
INSERT INTO `role` VALUES ('12', '测试10', 'Y', '1', '2017-12-26 13:59:43', null, null);
INSERT INTO `role` VALUES ('13', '测试11', 'Y', '1', '2017-12-26 13:59:51', null, null);
INSERT INTO `role` VALUES ('14', '测试12', 'Y', '1', '2017-12-26 13:59:56', null, null);
INSERT INTO `role` VALUES ('15', '测试13', 'Y', '1', '2017-12-26 14:00:01', null, null);
INSERT INTO `role` VALUES ('16', '测试14', 'Y', '1', '2017-12-26 14:00:12', null, null);

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL,
  `permission_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=106 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES ('95', '1', '1');
INSERT INTO `role_permission` VALUES ('96', '1', '2');
INSERT INTO `role_permission` VALUES ('97', '1', '3');
INSERT INTO `role_permission` VALUES ('98', '1', '4');
INSERT INTO `role_permission` VALUES ('99', '1', '5');
INSERT INTO `role_permission` VALUES ('100', '1', '6');
INSERT INTO `role_permission` VALUES ('101', '1', '8');
INSERT INTO `role_permission` VALUES ('102', '1', '9');
INSERT INTO `role_permission` VALUES ('103', '1', '11');
INSERT INTO `role_permission` VALUES ('104', '1', '12');
INSERT INTO `role_permission` VALUES ('105', '1', '13');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(31) NOT NULL COMMENT '账号',
  `password` varchar(63) NOT NULL COMMENT '密码',
  `salt` varchar(255) DEFAULT NULL,
  `name` varchar(63) DEFAULT NULL COMMENT '名字',
  `sex` varchar(1) DEFAULT NULL,
  `age` varchar(3) DEFAULT NULL COMMENT 'F：女  M：男',
  `photo` text,
  `birthday` datetime DEFAULT NULL,
  `tel` varchar(15) DEFAULT NULL,
  `email` varchar(31) DEFAULT NULL,
  `valid` char(1) DEFAULT NULL,
  `creater` int(11) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` int(11) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', '8711576c3cf0222a95d51170b9faa30e2ca6eb14', 'd387728ca4a39732a87c3d6b0a296849', '管理员', 'M', '20', null, '1997-12-07 00:00:00', '15989305930', '222@qq.com11', 'Y', null, null, '1', '2018-01-30 22:22:03');
INSERT INTO `user` VALUES ('2', 'user', '546beb3ba8b2b729cc132f83a19981ac676f02ae', '6d25c55f72fc3f21cfc1fdff1bae04fc', '测试用户', 'F', '27', null, '2017-12-25 00:00:00', '15989305930', '111@qq.com', 'Y', '1', '2017-12-26 10:31:44', '1', '2018-01-03 10:15:37');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('7', '2', '1');
INSERT INTO `user_role` VALUES ('8', '1', '1');
