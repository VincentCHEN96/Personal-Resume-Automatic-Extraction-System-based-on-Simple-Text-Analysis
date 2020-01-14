/*
Navicat MySQL Data Transfer

Source Server         : 本地连接
Source Server Version : 50714
Source Host           : localhost:3306
Source Database       : automatic_extraction_system

Target Server Type    : MYSQL
Target Server Version : 50714
File Encoding         : 65001

Date: 2018-05-23 09:26:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(16) NOT NULL,
  `password` varchar(16) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'test1', 'test001');
INSERT INTO `user` VALUES ('2', 'test2', 'test002');
INSERT INTO `user` VALUES ('4', 'test3', 'test003');
INSERT INTO `user` VALUES ('5', 'test4', 'test004');
