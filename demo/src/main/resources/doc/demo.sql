/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50619
Source Host           : localhost:3306
Source Database       : demo

Target Server Type    : MYSQL
Target Server Version : 50619
File Encoding         : 65001

Date: 2018-01-09 09:50:49
*/

SET FOREIGN_KEY_CHECKS=0;

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
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES ('30', '1', '1');
INSERT INTO `role_permission` VALUES ('31', '1', '2');
INSERT INTO `role_permission` VALUES ('32', '1', '3');
INSERT INTO `role_permission` VALUES ('33', '1', '4');
INSERT INTO `role_permission` VALUES ('34', '1', '5');
INSERT INTO `role_permission` VALUES ('35', '1', '6');
INSERT INTO `role_permission` VALUES ('36', '1', '8');
INSERT INTO `role_permission` VALUES ('37', '1', '9');

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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', '8711576c3cf0222a95d51170b9faa30e2ca6eb14', 'd387728ca4a39732a87c3d6b0a296849', '管理员', 'M', '20', null, '1997-12-07 00:00:00', '15989305930', '222@qq.com', 'Y', null, null, '1', '2018-01-03 10:15:16');
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
