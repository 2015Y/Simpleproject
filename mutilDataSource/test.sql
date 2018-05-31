/*
Navicat MySQL Data Transfer

Source Server         : 本机Mysql
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : springboot

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2018-05-31 14:50:10
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for s_user
-- ----------------------------
DROP TABLE IF EXISTS `s_user`;
CREATE TABLE `s_user` (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `name` varchar(18) NOT NULL DEFAULT '',
  `age` varchar(18) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `age_index` (`age`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;


/*
Navicat MySQL Data Transfer

Source Server         : 本机Mysql
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : springboot_db2

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2018-05-31 14:49:49
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for test
-- ----------------------------
DROP TABLE IF EXISTS `test`;
CREATE TABLE `test` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `age` int(11) NOT NULL,
  `remarks` varchar(128) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8;

