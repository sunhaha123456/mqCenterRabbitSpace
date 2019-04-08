/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50520
Source Host           : localhost:3306
Source Database       : mq_center

Target Server Type    : MYSQL
Target Server Version : 50520
File Encoding         : 65001

Date: 2019-04-08 14:06:17
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_mq_msg
-- ----------------------------
DROP TABLE IF EXISTS `tb_mq_msg`;
CREATE TABLE `tb_mq_msg` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `last_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `active_build_mq_msg_user_id` int(11) DEFAULT NULL COMMENT '主动构建消息的管理员id',
  `deliver_date` datetime DEFAULT NULL,
  `request_push_dest_addr` varchar(255) DEFAULT NULL COMMENT '请求送达地址',
  `request_push_interval_second` varchar(255) DEFAULT '0' COMMENT '请求间隔多少秒后送达',
  `request_push_msg_content` varchar(500) DEFAULT NULL COMMENT '要推送的消息内容',
  `request_push_platform` int(11) DEFAULT NULL COMMENT '请求推送方平台',
  `request_push_remark` varchar(255) DEFAULT NULL COMMENT '请求推送方备注',
  `status` tinyint(4) DEFAULT '0' COMMENT '推送状态',
  `total_push_count` int(11) DEFAULT '0' COMMENT '累计推送次数',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_mq_msg
-- ----------------------------

-- ----------------------------
-- Table structure for tb_mq_msg_push_releation
-- ----------------------------
DROP TABLE IF EXISTS `tb_mq_msg_push_releation`;
CREATE TABLE `tb_mq_msg_push_releation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `last_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `active_push_mq_msg_user_id` int(11) DEFAULT NULL COMMENT '主动推送消息的管理员id',
  `mq_msg_id` bigint(20) DEFAULT NULL,
  `push_type` tinyint(4) DEFAULT '0' COMMENT '推送状态',
  `status` tinyint(4) DEFAULT '0' COMMENT '推送状态',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_mq_msg_push_releation
-- ----------------------------

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `last_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `uname` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用户名',
  `upwd` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '密码',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES ('1', '2019-04-03 18:29:02', '2019-04-03 18:29:02', 'superAdmin', 'e10adc3949ba59abbe56e057f20f883e');
