/*
 Navicat Premium Data Transfer

 Source Server         : bysj
 Source Server Type    : MySQL
 Source Server Version : 50722
 Source Host           : 127.0.0.1:3306
 Source Schema         : edu

 Target Server Type    : MySQL
 Target Server Version : 50722
 File Encoding         : 65001

 Date: 19/10/2020 16:26:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin_login_log
-- ----------------------------
DROP TABLE IF EXISTS `admin_login_log`;
CREATE TABLE `admin_login_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `admin_id` bigint(20) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `ip` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_agent` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '浏览器登录类型',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 315 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '后台用户登录日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin_login_log
-- ----------------------------
INSERT INTO `admin_login_log` VALUES (285, 3, '2020-08-24 14:05:21', '0:0:0:0:0:0:0:1', NULL, NULL);
INSERT INTO `admin_login_log` VALUES (286, 10, '2020-08-24 14:05:39', '0:0:0:0:0:0:0:1', NULL, NULL);
INSERT INTO `admin_login_log` VALUES (287, 16, '2020-09-09 10:27:02', '0:0:0:0:0:0:0:1', NULL, NULL);
INSERT INTO `admin_login_log` VALUES (288, 16, '2020-09-09 10:29:54', '0:0:0:0:0:0:0:1', NULL, NULL);
INSERT INTO `admin_login_log` VALUES (289, 10, '2020-09-09 10:32:43', '0:0:0:0:0:0:0:1', NULL, NULL);
INSERT INTO `admin_login_log` VALUES (290, 16, '2020-09-09 10:35:33', '0:0:0:0:0:0:0:1', NULL, NULL);
INSERT INTO `admin_login_log` VALUES (291, 16, '2020-09-09 10:37:42', '0:0:0:0:0:0:0:1', NULL, NULL);
INSERT INTO `admin_login_log` VALUES (292, 16, '2020-09-09 10:46:02', '0:0:0:0:0:0:0:1', NULL, NULL);
INSERT INTO `admin_login_log` VALUES (293, 16, '2020-09-09 10:46:31', '0:0:0:0:0:0:0:1', NULL, NULL);
INSERT INTO `admin_login_log` VALUES (294, 16, '2020-09-09 11:46:02', '0:0:0:0:0:0:0:1', NULL, NULL);
INSERT INTO `admin_login_log` VALUES (295, 16, '2020-09-09 11:48:20', '0:0:0:0:0:0:0:1', NULL, NULL);
INSERT INTO `admin_login_log` VALUES (296, 16, '2020-09-09 15:39:57', '0:0:0:0:0:0:0:1', NULL, NULL);
INSERT INTO `admin_login_log` VALUES (297, 16, '2020-09-09 16:00:28', '0:0:0:0:0:0:0:1', NULL, NULL);
INSERT INTO `admin_login_log` VALUES (298, 16, '2020-09-09 18:08:06', '0:0:0:0:0:0:0:1', NULL, NULL);
INSERT INTO `admin_login_log` VALUES (299, 16, '2020-09-10 11:24:03', '0:0:0:0:0:0:0:1', NULL, NULL);
INSERT INTO `admin_login_log` VALUES (300, 16, '2020-09-10 11:48:03', '0:0:0:0:0:0:0:1', NULL, NULL);
INSERT INTO `admin_login_log` VALUES (301, 1, '2020-09-10 11:49:15', '0:0:0:0:0:0:0:1', NULL, NULL);
INSERT INTO `admin_login_log` VALUES (302, 16, '2020-09-10 12:02:14', '0:0:0:0:0:0:0:1', NULL, NULL);
INSERT INTO `admin_login_log` VALUES (303, 16, '2020-09-21 11:33:57', '0:0:0:0:0:0:0:1', NULL, NULL);
INSERT INTO `admin_login_log` VALUES (304, 16, '2020-09-21 11:43:10', '0:0:0:0:0:0:0:1', NULL, NULL);
INSERT INTO `admin_login_log` VALUES (305, 16, '2020-09-21 12:01:22', '127.0.0.1', NULL, NULL);
INSERT INTO `admin_login_log` VALUES (306, 16, '2020-09-21 12:02:08', '127.0.0.1', NULL, NULL);
INSERT INTO `admin_login_log` VALUES (307, 16, '2020-09-21 15:44:00', '0:0:0:0:0:0:0:1', NULL, NULL);
INSERT INTO `admin_login_log` VALUES (308, 16, '2020-09-21 15:45:31', '0:0:0:0:0:0:0:1', NULL, NULL);
INSERT INTO `admin_login_log` VALUES (309, 16, '2020-09-22 12:21:50', '0:0:0:0:0:0:0:1', NULL, NULL);
INSERT INTO `admin_login_log` VALUES (310, 16, '2020-09-26 19:12:30', '0:0:0:0:0:0:0:1', NULL, NULL);
INSERT INTO `admin_login_log` VALUES (311, 16, '2020-10-08 23:50:32', '0:0:0:0:0:0:0:1', NULL, NULL);
INSERT INTO `admin_login_log` VALUES (312, 16, '2020-10-10 17:04:15', '0:0:0:0:0:0:0:1', NULL, 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.83 Safari/537.36');
INSERT INTO `admin_login_log` VALUES (313, 16, '2020-10-10 17:05:53', '0:0:0:0:0:0:0:1', NULL, 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.83 Safari/537.36');
INSERT INTO `admin_login_log` VALUES (314, 16, '2020-10-10 19:27:17', '0:0:0:0:0:0:0:1', NULL, 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.83 Safari/537.36');

SET FOREIGN_KEY_CHECKS = 1;
