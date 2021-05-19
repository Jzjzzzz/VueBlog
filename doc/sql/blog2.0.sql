/*
 Navicat Premium Data Transfer

 Source Server         : test
 Source Server Type    : MySQL
 Source Server Version : 50732
 Source Host           : localhost:3306
 Source Schema         : blog2.0

 Target Server Type    : MySQL
 Target Server Version : 50732
 File Encoding         : 65001

 Date: 19/05/2021 19:06:04
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for dict
-- ----------------------------
DROP TABLE IF EXISTS `dict`;
CREATE TABLE `dict`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `parent_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '上级id',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '名称',
  `value` int(11) DEFAULT NULL COMMENT '值',
  `dict_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '编码',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '删除标记（0:不可用 1:可用）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_parent_id_value`(`parent_id`, `value`) USING BTREE,
  INDEX `idx_parent_id`(`parent_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 82008 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '数据字典' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dict
-- ----------------------------
INSERT INTO `dict` VALUES (1, 0, '全部分类', NULL, 'ROOT', '2021-04-28 20:13:38', '2021-04-28 20:13:38', 0);
INSERT INTO `dict` VALUES (20000, 1, '行业', NULL, 'industry', '2021-04-28 20:13:38', '2021-04-28 20:13:38', 0);
INSERT INTO `dict` VALUES (20001, 20000, 'IT', 1, NULL, '2021-04-28 20:13:38', '2021-04-28 20:13:38', 0);
INSERT INTO `dict` VALUES (20002, 20000, '医生', 2, NULL, '2021-04-28 20:13:38', '2021-04-28 20:13:38', 0);
INSERT INTO `dict` VALUES (20003, 20000, '教师', 3, NULL, '2021-04-28 20:13:38', '2021-04-28 20:13:38', 0);
INSERT INTO `dict` VALUES (20004, 20000, '导游', 4, NULL, '2021-04-28 20:13:38', '2021-04-28 20:13:38', 0);
INSERT INTO `dict` VALUES (20005, 20000, '律师', 5, NULL, '2021-04-28 20:13:38', '2021-04-28 20:13:38', 0);
INSERT INTO `dict` VALUES (20006, 20000, '其他', 6, NULL, '2021-04-28 20:13:38', '2021-04-28 20:13:38', 0);
INSERT INTO `dict` VALUES (30000, 1, '学历', NULL, 'education', '2021-04-28 20:13:38', '2021-04-28 20:13:38', 0);
INSERT INTO `dict` VALUES (30001, 30000, '高中', 1, NULL, '2021-04-28 20:13:38', '2021-04-28 20:13:38', 0);
INSERT INTO `dict` VALUES (30002, 30000, '大专', 2, NULL, '2021-04-28 20:13:38', '2021-04-28 20:13:38', 0);
INSERT INTO `dict` VALUES (30003, 30000, '本科', 3, NULL, '2021-04-28 20:13:38', '2021-04-28 20:13:38', 0);
INSERT INTO `dict` VALUES (30004, 30000, '研究生', 4, NULL, '2021-04-28 20:13:38', '2021-04-28 20:13:38', 0);
INSERT INTO `dict` VALUES (30005, 30000, '其他', 5, NULL, '2021-04-28 20:13:38', '2021-04-28 20:13:38', 0);
INSERT INTO `dict` VALUES (40000, 1, '收入', NULL, 'income', '2021-04-28 20:13:38', '2021-04-28 20:13:38', 0);
INSERT INTO `dict` VALUES (40001, 40000, '0-3000', 1, NULL, '2021-04-28 20:13:38', '2021-04-28 20:13:38', 0);
INSERT INTO `dict` VALUES (40002, 40000, '3000-5000', 2, NULL, '2021-04-28 20:13:38', '2021-04-28 20:13:38', 0);
INSERT INTO `dict` VALUES (40003, 40000, '5000-10000', 3, NULL, '2021-04-28 20:13:38', '2021-04-28 20:13:38', 0);
INSERT INTO `dict` VALUES (40004, 40000, '10000以上', 4, NULL, '2021-04-28 20:13:38', '2021-04-28 20:13:38', 0);
INSERT INTO `dict` VALUES (50000, 1, '收入来源', NULL, 'returnSource', '2021-04-28 20:13:38', '2021-04-28 20:13:38', 0);
INSERT INTO `dict` VALUES (50001, 50000, '工资', 1, NULL, '2021-04-28 20:13:38', '2021-04-28 20:13:38', 0);
INSERT INTO `dict` VALUES (50002, 50000, '股票', 2, NULL, '2021-04-28 20:13:38', '2021-04-28 20:13:38', 0);
INSERT INTO `dict` VALUES (50003, 50000, '兼职', 3, NULL, '2021-04-28 20:13:38', '2021-04-28 20:13:38', 0);
INSERT INTO `dict` VALUES (60000, 1, '关系', NULL, 'relation', '2021-04-28 20:13:38', '2021-04-28 20:13:38', 0);
INSERT INTO `dict` VALUES (60001, 60000, '夫妻', 1, NULL, '2021-04-28 20:13:38', '2021-04-28 20:13:38', 0);
INSERT INTO `dict` VALUES (60002, 60000, '兄妹', 2, NULL, '2021-04-28 20:13:38', '2021-04-28 20:13:38', 0);
INSERT INTO `dict` VALUES (60003, 60000, '父母', 3, NULL, '2021-04-28 20:13:38', '2021-04-28 20:13:38', 0);
INSERT INTO `dict` VALUES (60004, 60000, '其他', 4, NULL, '2021-04-28 20:13:38', '2021-04-28 20:13:38', 0);
INSERT INTO `dict` VALUES (82000, 1, '学校性质', NULL, 'SchoolStatus', '2021-04-28 20:13:38', '2021-04-28 20:13:38', 0);
INSERT INTO `dict` VALUES (82001, 82000, '211/985', NULL, NULL, '2021-04-28 20:13:38', '2021-04-28 20:13:38', 0);
INSERT INTO `dict` VALUES (82002, 82000, '一本', NULL, NULL, '2021-04-28 20:13:38', '2021-04-28 20:13:38', 0);
INSERT INTO `dict` VALUES (82003, 82000, '二本', NULL, NULL, '2021-04-28 20:13:38', '2021-04-28 20:13:38', 0);
INSERT INTO `dict` VALUES (82004, 82000, '三本', NULL, NULL, '2021-04-28 20:13:38', '2021-04-28 20:13:38', 0);
INSERT INTO `dict` VALUES (82005, 82000, '高职高专', NULL, NULL, '2021-04-28 20:13:38', '2021-04-28 20:13:38', 0);
INSERT INTO `dict` VALUES (82006, 82000, '中职中专', NULL, NULL, '2021-04-28 20:13:38', '2021-04-28 20:13:38', 0);
INSERT INTO `dict` VALUES (82007, 82000, '高中及以下', NULL, NULL, '2021-04-28 20:13:38', '2021-04-28 20:13:38', 0);

-- ----------------------------
-- Table structure for genre
-- ----------------------------
DROP TABLE IF EXISTS `genre`;
CREATE TABLE `genre`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分类名称',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除(1:已删除，0:未删除)',
  `status` tinyint(3) UNSIGNED NOT NULL COMMENT '状态',
  `sort` int(11) DEFAULT NULL COMMENT '排序字段，越大越靠前',
  `clickCount` int(11) DEFAULT NULL COMMENT '点击数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of genre
-- ----------------------------
INSERT INTO `genre` VALUES (13, '编程生活', '2021-05-18 20:39:57', '2021-05-18 20:43:44', 0, 1, 1, 0);
INSERT INTO `genre` VALUES (14, '杂谈', '2021-05-18 20:40:11', '2021-05-18 20:40:11', 0, 1, 0, 0);
INSERT INTO `genre` VALUES (15, '游戏', '2021-05-18 20:40:19', '2021-05-18 20:40:19', 0, 1, 0, 0);

-- ----------------------------
-- Table structure for tag
-- ----------------------------
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `content` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标签内容',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除(1:已删除，0:未删除)',
  `clickCount` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '点击数',
  `sort` int(11) DEFAULT NULL COMMENT '排序字段，越大越靠前',
  `status` tinyint(1) NOT NULL COMMENT '状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tag
-- ----------------------------
INSERT INTO `tag` VALUES (4, 'Java', '2021-05-14 19:28:10', '2021-05-18 20:32:34', 0, '0', 1, 1);
INSERT INTO `tag` VALUES (5, 'HTML', '2021-05-14 19:28:29', '2021-05-18 12:52:34', 0, '0', 0, 1);
INSERT INTO `tag` VALUES (6, 'C#', '2021-05-14 20:04:15', '2021-05-18 12:53:56', 0, '0', 0, 1);
INSERT INTO `tag` VALUES (7, 'JVM', '2021-05-18 13:47:27', '2021-05-18 13:52:53', 0, '0', 2, 1);
INSERT INTO `tag` VALUES (8, 'CSS', '2021-05-18 13:50:53', '2021-05-18 13:50:53', 0, '0', 0, 1);
INSERT INTO `tag` VALUES (9, 'JUC', '2021-05-18 13:51:01', '2021-05-18 13:51:01', 0, '0', 0, 1);

-- ----------------------------
-- Table structure for web_config
-- ----------------------------
DROP TABLE IF EXISTS `web_config`;
CREATE TABLE `web_config`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `logo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'logo(文件UID)',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '网站名称',
  `summary` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '网站介绍',
  `keyword` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '网站关键字',
  `author` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '网站作者',
  `record_num` int(11) DEFAULT NULL COMMENT '网站备案号',
  `status` tinyint(1) NOT NULL COMMENT '状态',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL COMMENT '更新时间',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '网站标题',
  `ali_pay` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '支付宝收款码',
  `weixin_pay` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '微信收款码',
  `github` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'github地址',
  `gitee` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '码云地址',
  `qq_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'qq号',
  `qq_group` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'qq群',
  `we_chat` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '微信号',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '邮箱',
  `show_list` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '显示的列表（用于控制邮箱、QQ、QQ群、Github、Gitee、微信是否显示在前端）',
  `login_type_list` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '登录方式列表（用于控制前端登录方式，如账号密码,码云,Github,QQ,微信）',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of web_config
-- ----------------------------
INSERT INTO `web_config` VALUES (1, NULL, 'Jzj的博客', '用springboot+vue搭建', '博客，个人', 'Jzj', 111, 1, '2021-05-19 18:40:24', '2021-05-19 18:51:17', '记录编程', NULL, NULL, '', 'https://gitee.com/jzjzz/VueBlog.git', '946232976', NULL, NULL, '946232976@qq.com', NULL, NULL, 0);

SET FOREIGN_KEY_CHECKS = 1;
