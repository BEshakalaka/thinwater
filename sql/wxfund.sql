/*
 Navicat Premium Data Transfer

 Source Server         : vps
 Source Server Type    : MySQL
 Source Server Version : 50729
 Source Host           : 134.175.82.194:3306
 Source Schema         : wxfund

 Target Server Type    : MySQL
 Target Server Version : 50729
 File Encoding         : 65001

 Date: 10/11/2020 01:19:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for receive_msg
-- ----------------------------
DROP TABLE IF EXISTS `receive_msg`;
CREATE TABLE `receive_msg`  (
  `id` int(30) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `to_user_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '开发者微信号',
  `from_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '发送方帐号（一个OpenID）',
  `create_time` datetime(0) NOT NULL COMMENT '消息创建时间 （整型）',
  `msg_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息类型，文本为text',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文本消息内容',
  `msg_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息id，64位整型',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 85 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for send_msg
-- ----------------------------
DROP TABLE IF EXISTS `send_msg`;
CREATE TABLE `send_msg`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户的id',
  `msg` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '回复邮件内容',
  `create_time` datetime(0) NOT NULL COMMENT '时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_email
-- ----------------------------
DROP TABLE IF EXISTS `user_email`;
CREATE TABLE `user_email`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增长id',
  `from_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '发送方帐号（一个OpenID）',
  `email` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '邮箱',
  `create_time` datetime(0) NOT NULL COMMENT '创建日期',
  `is_del` int(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`, `from_user_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_fund_code
-- ----------------------------
DROP TABLE IF EXISTS `user_fund_code`;
CREATE TABLE `user_fund_code`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `user_id` int(11) NOT NULL COMMENT 'user_email的id',
  `fund_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '基金code',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `is_del` int(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
