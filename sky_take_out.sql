/*
 Navicat Premium Data Transfer

 Source Server         : O_DENTIST
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : sky_take_out

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 03/05/2023 12:11:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for address_book
-- ----------------------------
DROP TABLE IF EXISTS `address_book`;
CREATE TABLE `address_book`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(0) NOT NULL COMMENT '用户id',
  `consignee` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '收货人',
  `sex` varchar(2) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '性别',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '手机号',
  `province_code` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '省级区划编号',
  `province_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '省级名称',
  `city_code` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '市级区划编号',
  `city_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '市级名称',
  `district_code` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '区级区划编号',
  `district_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '区级名称',
  `detail` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '详细地址',
  `label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标签',
  `is_default` tinyint(1) NOT NULL DEFAULT 0 COMMENT '默认 0 否 1是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '地址簿' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of address_book
-- ----------------------------
INSERT INTO `address_book` VALUES (2, 4, 'ODENTIST', '0', '13412306123', '51', '四川省', '5101', '成都市', '510117', '郫都区', '天府大道二号', '1', 1);
INSERT INTO `address_book` VALUES (3, 4, 'ODENTIST2', '0', '13412306123', '11', '北京市', '1101', '市辖区', '110102', '西城区', '龙井寺', '2', 0);

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` int(0) NULL DEFAULT NULL COMMENT '类型   1 菜品分类 2 套餐分类',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '分类名称',
  `sort` int(0) NOT NULL DEFAULT 0 COMMENT '顺序',
  `status` int(0) NULL DEFAULT NULL COMMENT '分类状态 0:禁用，1:启用',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `create_user` bigint(0) NULL DEFAULT NULL COMMENT '创建人',
  `update_user` bigint(0) NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_category_name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '菜品及套餐分类' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (11, 1, '酒水饮料', 10, 1, '2022-06-09 22:09:18', '2022-06-09 22:09:18', 1, 1);
INSERT INTO `category` VALUES (12, 1, '传统主食', 9, 1, '2022-06-09 22:09:32', '2022-06-09 22:18:53', 1, 1);
INSERT INTO `category` VALUES (13, 2, '人气套餐', 12, 1, '2022-06-09 22:11:38', '2022-06-10 11:04:40', 1, 1);
INSERT INTO `category` VALUES (15, 2, '商务套餐', 13, 1, '2022-06-09 22:14:10', '2022-06-10 11:04:48', 1, 1);
INSERT INTO `category` VALUES (16, 1, '蜀味烤鱼', 4, 1, '2022-06-09 22:15:37', '2023-04-21 17:14:09', 1, 1);
INSERT INTO `category` VALUES (17, 1, '蜀味牛蛙', 5, 1, '2022-06-09 22:16:14', '2023-04-21 17:14:10', 1, 1);
INSERT INTO `category` VALUES (18, 1, '特色蒸菜', 6, 1, '2022-06-09 22:17:42', '2022-06-09 22:17:42', 1, 1);
INSERT INTO `category` VALUES (19, 1, '新鲜时蔬', 7, 1, '2022-06-09 22:18:12', '2022-06-09 22:18:28', 1, 1);
INSERT INTO `category` VALUES (20, 1, '水煮鱼', 8, 1, '2022-06-09 22:22:29', '2022-06-09 22:23:45', 1, 1);
INSERT INTO `category` VALUES (21, 1, '汤类', 11, 1, '2022-06-10 10:51:47', '2022-06-10 10:51:47', 1, 1);

-- ----------------------------
-- Table structure for dish
-- ----------------------------
DROP TABLE IF EXISTS `dish`;
CREATE TABLE `dish`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '菜品名称',
  `category_id` bigint(0) NOT NULL COMMENT '菜品分类id',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '菜品价格',
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '图片',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '描述信息',
  `status` int(0) NULL DEFAULT 1 COMMENT '0 停售 1 起售',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `create_user` bigint(0) NULL DEFAULT NULL COMMENT '创建人',
  `update_user` bigint(0) NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_dish_name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 83 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '菜品' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dish
-- ----------------------------
INSERT INTO `dish` VALUES (46, '王老吉', 11, 6.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/41bfcacf-7ad4-4927-8b26-df366553a94c.png', '', 1, '2022-06-09 22:40:47', '2022-06-09 22:40:47', 1, 1);
INSERT INTO `dish` VALUES (47, '北冰洋', 11, 4.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/4451d4be-89a2-4939-9c69-3a87151cb979.png', '还是小时候的味道', 1, '2022-06-10 09:18:49', '2022-06-10 09:18:49', 1, 1);
INSERT INTO `dish` VALUES (48, '雪花啤酒', 11, 4.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/bf8cbfc1-04d2-40e8-9826-061ee41ab87c.png', '', 1, '2022-06-10 09:22:54', '2022-06-10 09:22:54', 1, 1);
INSERT INTO `dish` VALUES (49, '米饭', 12, 2.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/76752350-2121-44d2-b477-10791c23a8ec.png', '精选五常大米', 1, '2022-06-10 09:30:17', '2022-06-10 09:30:17', 1, 1);
INSERT INTO `dish` VALUES (50, '馒头', 12, 1.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/475cc599-8661-4899-8f9e-121dd8ef7d02.png', '优质面粉', 1, '2022-06-10 09:34:28', '2022-06-10 09:34:28', 1, 1);
INSERT INTO `dish` VALUES (51, '老坛酸菜鱼', 20, 56.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/4a9cefba-6a74-467e-9fde-6e687ea725d7.png', '原料：汤，草鱼，酸菜', 0, '2022-06-10 09:40:51', '2023-04-20 20:37:59', 1, 1);
INSERT INTO `dish` VALUES (52, '经典酸菜鮰鱼', 20, 66.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/5260ff39-986c-4a97-8850-2ec8c7583efc.png', '原料：酸菜，江团，鮰鱼', 1, '2022-06-10 09:46:02', '2022-06-10 09:46:02', 1, 1);
INSERT INTO `dish` VALUES (53, '蜀味水煮草鱼', 20, 38.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/a6953d5a-4c18-4b30-9319-4926ee77261f.png', '原料：草鱼，汤', 1, '2022-06-10 09:48:37', '2022-06-10 09:48:37', 1, 1);
INSERT INTO `dish` VALUES (54, '清炒小油菜', 19, 18.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/3613d38e-5614-41c2-90ed-ff175bf50716.png', '原料：小油菜', 1, '2022-06-10 09:51:46', '2022-06-10 09:51:46', 1, 1);
INSERT INTO `dish` VALUES (55, '蒜蓉娃娃菜', 19, 18.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/4879ed66-3860-4b28-ba14-306ac025fdec.png', '原料：蒜，娃娃菜', 1, '2022-06-10 09:53:37', '2022-06-10 09:53:37', 1, 1);
INSERT INTO `dish` VALUES (56, '清炒西兰花', 19, 18.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/e9ec4ba4-4b22-4fc8-9be0-4946e6aeb937.png', '原料：西兰花', 1, '2022-06-10 09:55:44', '2022-06-10 09:55:44', 1, 1);
INSERT INTO `dish` VALUES (57, '炝炒圆白菜', 19, 18.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/22f59feb-0d44-430e-a6cd-6a49f27453ca.png', '原料：圆白菜', 1, '2022-06-10 09:58:35', '2022-06-10 09:58:35', 1, 1);
INSERT INTO `dish` VALUES (58, '清蒸鲈鱼', 18, 98.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/c18b5c67-3b71-466c-a75a-e63c6449f21c.png', '原料：鲈鱼', 1, '2022-06-10 10:12:28', '2022-06-10 10:12:28', 1, 1);
INSERT INTO `dish` VALUES (59, '东坡肘子', 18, 138.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/a80a4b8c-c93e-4f43-ac8a-856b0d5cc451.png', '原料：猪肘棒', 1, '2022-06-10 10:24:03', '2022-06-10 10:24:03', 1, 1);
INSERT INTO `dish` VALUES (60, '梅菜扣肉', 18, 58.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/6080b118-e30a-4577-aab4-45042e3f88be.png', '原料：猪肉，梅菜', 1, '2022-06-10 10:26:03', '2022-06-10 10:26:03', 1, 1);
INSERT INTO `dish` VALUES (61, '剁椒鱼头', 18, 66.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/13da832f-ef2c-484d-8370-5934a1045a06.png', '原料：鲢鱼，剁椒', 0, '2022-06-10 10:28:54', '2023-04-21 15:44:32', 1, 1);
INSERT INTO `dish` VALUES (62, '金汤酸菜牛蛙', 17, 88.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/7694a5d8-7938-4e9d-8b9e-2075983a2e38.png', '原料：鲜活牛蛙，酸菜', 1, '2022-06-10 10:33:05', '2022-06-10 10:33:05', 1, 1);
INSERT INTO `dish` VALUES (63, '香锅牛蛙', 17, 88.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/f5ac8455-4793-450c-97ba-173795c34626.png', '配料：鲜活牛蛙，莲藕，青笋', 1, '2022-06-10 10:35:40', '2022-06-10 10:35:40', 1, 1);
INSERT INTO `dish` VALUES (64, '馋嘴牛蛙', 17, 68.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/7a55b845-1f2b-41fa-9486-76d187ee9ee1.png', '配料：鲜活牛蛙，丝瓜，黄豆芽', 1, '2022-06-10 10:37:52', '2023-04-27 11:00:54', 1, 1);
INSERT INTO `dish` VALUES (65, '草鱼2斤', 16, 68.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/b544d3ba-a1ae-4d20-a860-81cb5dec9e03.png', '原料：草鱼，黄豆芽，莲藕', 1, '2022-06-10 10:41:08', '2022-06-10 10:41:08', 1, 1);
INSERT INTO `dish` VALUES (66, '江团鱼2斤', 16, 119.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/a101a1e9-8f8b-47b2-afa4-1abd47ea0a87.png', '配料：江团鱼，黄豆芽，莲藕', 1, '2022-06-10 10:42:42', '2022-06-10 10:42:42', 1, 1);
INSERT INTO `dish` VALUES (67, '鮰鱼2斤', 16, 72.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/8cfcc576-4b66-4a09-ac68-ad5b273c2590.png', '原料：鮰鱼，黄豆芽，莲藕', 1, '2022-06-10 10:43:56', '2022-06-10 10:43:56', 1, 1);
INSERT INTO `dish` VALUES (68, '鸡蛋汤', 21, 4.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/c09a0ee8-9d19-428d-81b9-746221824113.png', '配料：鸡蛋，紫菜', 1, '2022-06-10 10:54:25', '2022-06-10 10:54:25', 1, 1);
INSERT INTO `dish` VALUES (69, '白菜豆腐汤', 21, 16.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/16d0a3d6-2253-4cfc-9b49-bf7bd9eb2ad2.png', '配料：豆腐，白菜', 1, '2022-06-10 10:55:02', '2023-04-20 15:23:43', 1, 1);
INSERT INTO `dish` VALUES (82, 'tes2', 16, 1111.00, 'https://odentist-image-restore.oss-cn-chengdu.aliyuncs.com/c426c903-5811-4571-a4e6-74b6ea7576f3.png', '', 0, '2023-04-27 11:44:22', '2023-04-27 11:44:22', 1, 1);

-- ----------------------------
-- Table structure for dish_flavor
-- ----------------------------
DROP TABLE IF EXISTS `dish_flavor`;
CREATE TABLE `dish_flavor`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `dish_id` bigint(0) NOT NULL COMMENT '菜品',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '口味名称',
  `value` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '口味数据list',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 109 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '菜品口味关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dish_flavor
-- ----------------------------
INSERT INTO `dish_flavor` VALUES (40, 10, '甜味', '[\"无糖\",\"少糖\",\"半糖\",\"多糖\",\"全糖\"]');
INSERT INTO `dish_flavor` VALUES (41, 7, '忌口', '[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]');
INSERT INTO `dish_flavor` VALUES (42, 7, '温度', '[\"热饮\",\"常温\",\"去冰\",\"少冰\",\"多冰\"]');
INSERT INTO `dish_flavor` VALUES (45, 6, '忌口', '[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]');
INSERT INTO `dish_flavor` VALUES (46, 6, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]');
INSERT INTO `dish_flavor` VALUES (47, 5, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]');
INSERT INTO `dish_flavor` VALUES (48, 5, '甜味', '[\"无糖\",\"少糖\",\"半糖\",\"多糖\",\"全糖\"]');
INSERT INTO `dish_flavor` VALUES (49, 2, '甜味', '[\"无糖\",\"少糖\",\"半糖\",\"多糖\",\"全糖\"]');
INSERT INTO `dish_flavor` VALUES (50, 4, '甜味', '[\"无糖\",\"少糖\",\"半糖\",\"多糖\",\"全糖\"]');
INSERT INTO `dish_flavor` VALUES (51, 3, '甜味', '[\"无糖\",\"少糖\",\"半糖\",\"多糖\",\"全糖\"]');
INSERT INTO `dish_flavor` VALUES (52, 3, '忌口', '[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]');
INSERT INTO `dish_flavor` VALUES (86, 52, '忌口', '[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]');
INSERT INTO `dish_flavor` VALUES (87, 52, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]');
INSERT INTO `dish_flavor` VALUES (88, 51, '忌口', '[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]');
INSERT INTO `dish_flavor` VALUES (89, 51, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]');
INSERT INTO `dish_flavor` VALUES (92, 53, '忌口', '[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]');
INSERT INTO `dish_flavor` VALUES (93, 53, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]');
INSERT INTO `dish_flavor` VALUES (94, 54, '忌口', '[\"不要葱\",\"不要蒜\",\"不要香菜\"]');
INSERT INTO `dish_flavor` VALUES (95, 56, '忌口', '[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]');
INSERT INTO `dish_flavor` VALUES (96, 57, '忌口', '[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]');
INSERT INTO `dish_flavor` VALUES (97, 60, '忌口', '[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]');
INSERT INTO `dish_flavor` VALUES (101, 66, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]');
INSERT INTO `dish_flavor` VALUES (102, 67, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]');
INSERT INTO `dish_flavor` VALUES (103, 65, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]');
INSERT INTO `dish_flavor` VALUES (104, 72, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]');
INSERT INTO `dish_flavor` VALUES (109, 79, '甜味', '[\"无糖\",\"少糖\",\"半糖\",\"多糖\",\"全糖\"]');

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '姓名',
  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '密码',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '手机号',
  `sex` varchar(2) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '性别',
  `id_number` varchar(18) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '身份证号',
  `status` int(0) NOT NULL DEFAULT 1 COMMENT '状态 0:禁用，1:启用',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `create_user` bigint(0) NULL DEFAULT NULL COMMENT '创建人',
  `update_user` bigint(0) NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '员工信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES (1, '管理员', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '13812312312', '1', '110101199001010047', 1, '2022-02-15 15:51:20', '2022-02-17 09:16:20', 10, 1);
INSERT INTO `employee` VALUES (2, 'xiaozhi', 'xiaozhi', 'e10adc3949ba59abbe56e057f20f883e', '13812344321', '1', '111222333444555666', 1, '2023-04-19 10:27:48', '2023-04-19 10:27:51', 10, 1);
INSERT INTO `employee` VALUES (3, '人工客服', '10086', 'e10adc3949ba59abbe56e057f20f883e', '13412306123', '1', '100000000000000000', 1, '2023-04-20 08:47:55', '2023-04-30 10:18:38', 1, 1);

-- ----------------------------
-- Table structure for operation_log
-- ----------------------------
DROP TABLE IF EXISTS `operation_log`;
CREATE TABLE `operation_log`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键，自增',
  `url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '记录的url',
  `port_check` int(0) NOT NULL COMMENT '1是后端，2是前端',
  `ip` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `method` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `exception_detected` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `args` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_id` int(0) NOT NULL,
  `update_time` datetime(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '操作日志记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of operation_log
-- ----------------------------
INSERT INTO `operation_log` VALUES (17, 'http://localhost:8080/admin/category/page', 1, '127.0.0.1', 'page', NULL, '[{\"page\":1,\"pageSize\":10}]', 1, '2023-04-21 16:48:57');
INSERT INTO `operation_log` VALUES (18, 'http://localhost:8080/admin/category/page', 1, '127.0.0.1', 'page', NULL, '[{\"page\":1,\"pageSize\":10}]', 1, '2023-04-21 16:50:08');
INSERT INTO `operation_log` VALUES (19, 'http://localhost:8080/admin/category/page', 1, '127.0.0.1', 'page', NULL, '[{\"page\":1,\"pageSize\":10}]', 1, '2023-04-21 16:50:19');
INSERT INTO `operation_log` VALUES (20, 'http://localhost:8080/admin/category/page', 1, '192.168.43.227', 'page', NULL, '[{\"page\":1,\"pageSize\":10}]', 1, '2023-04-21 17:13:27');
INSERT INTO `operation_log` VALUES (21, 'http://localhost:8080/admin/category/page', 1, '192.168.43.227', 'page', NULL, '[{\"page\":1,\"pageSize\":10}]', 1, '2023-04-21 17:14:07');
INSERT INTO `operation_log` VALUES (22, 'http://localhost:8080/admin/category/page', 1, '192.168.43.227', 'page', NULL, '[{\"page\":1,\"pageSize\":10}]', 1, '2023-04-21 17:14:09');
INSERT INTO `operation_log` VALUES (23, 'http://localhost:8080/admin/category/page', 1, '192.168.43.227', 'page', NULL, '[{\"page\":1,\"pageSize\":10}]', 1, '2023-04-21 17:14:10');
INSERT INTO `operation_log` VALUES (24, 'http://localhost:8080/admin/category/page', 1, '10.254.1.192', 'page', NULL, '[{\"page\":1,\"pageSize\":10}]', 1, '2023-04-27 09:10:33');
INSERT INTO `operation_log` VALUES (25, 'http://localhost:8080/admin/category/page', 1, '10.254.1.192', 'page', NULL, '[{\"page\":1,\"pageSize\":10}]', 1, '2023-04-27 11:00:42');
INSERT INTO `operation_log` VALUES (26, 'http://localhost:8080/admin/category/page', 1, '10.254.1.192', 'page', NULL, '[{\"page\":1,\"pageSize\":10}]', 1, '2023-04-27 11:10:15');
INSERT INTO `operation_log` VALUES (27, 'http://localhost:8080/admin/category/page', 1, '10.254.1.192', 'page', NULL, '[{\"page\":1,\"pageSize\":10}]', 1, '2023-04-27 15:18:06');
INSERT INTO `operation_log` VALUES (28, 'http://localhost:8080/admin/category/page', 1, '10.254.1.192', 'page', NULL, '[{\"page\":1,\"pageSize\":10}]', 1, '2023-04-27 15:34:00');
INSERT INTO `operation_log` VALUES (29, 'http://localhost:8080/admin/category/page', 1, '10.254.1.251', 'page', NULL, '[{\"page\":1,\"pageSize\":10}]', 1, '2023-04-30 10:14:44');
INSERT INTO `operation_log` VALUES (30, 'http://localhost:8080/admin/category/page', 1, '10.254.1.251', 'page', NULL, '[{\"page\":1,\"pageSize\":10}]', 1, '2023-04-30 10:18:12');

-- ----------------------------
-- Table structure for order_detail
-- ----------------------------
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '名字',
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '图片',
  `order_id` bigint(0) NOT NULL COMMENT '订单id',
  `dish_id` bigint(0) NULL DEFAULT NULL COMMENT '菜品id',
  `setmeal_id` bigint(0) NULL DEFAULT NULL COMMENT '套餐id',
  `dish_flavor` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '口味',
  `number` int(0) NOT NULL DEFAULT 1 COMMENT '数量',
  `amount` decimal(10, 2) NOT NULL COMMENT '金额',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '订单明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_detail
-- ----------------------------
INSERT INTO `order_detail` VALUES (5, '馒头', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/475cc599-8661-4899-8f9e-121dd8ef7d02.png', 4, 50, NULL, '无糖', 1, 1.00);
INSERT INTO `order_detail` VALUES (6, '米饭', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/76752350-2121-44d2-b477-10791c23a8ec.png', 4, 49, NULL, '无糖', 1, 2.00);
INSERT INTO `order_detail` VALUES (7, 'tes2', 'https://odentist-image-restore.oss-cn-chengdu.aliyuncs.com/c426c903-5811-4571-a4e6-74b6ea7576f3.png', 4, 82, NULL, NULL, 78, 1111.00);
INSERT INTO `order_detail` VALUES (8, '鮰鱼2斤', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/8cfcc576-4b66-4a09-ac68-ad5b273c2590.png', 4, 67, NULL, NULL, 17, 72.00);
INSERT INTO `order_detail` VALUES (9, '江团鱼2斤', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/a101a1e9-8f8b-47b2-afa4-1abd47ea0a87.png', 4, 66, NULL, NULL, 5, 119.00);
INSERT INTO `order_detail` VALUES (10, '草鱼2斤', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/b544d3ba-a1ae-4d20-a860-81cb5dec9e03.png', 4, 65, NULL, NULL, 6, 68.00);
INSERT INTO `order_detail` VALUES (11, '江团鱼2斤', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/a101a1e9-8f8b-47b2-afa4-1abd47ea0a87.png', 5, 66, NULL, NULL, 6, 119.00);
INSERT INTO `order_detail` VALUES (12, '鸡蛋汤', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/c09a0ee8-9d19-428d-81b9-746221824113.png', 6, 68, NULL, NULL, 3, 4.00);
INSERT INTO `order_detail` VALUES (13, '蒜蓉娃娃菜', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/4879ed66-3860-4b28-ba14-306ac025fdec.png', 7, 55, NULL, NULL, 1, 18.00);
INSERT INTO `order_detail` VALUES (14, '米饭', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/76752350-2121-44d2-b477-10791c23a8ec.png', 1682818120789, 49, NULL, '无糖', 1, 2.00);
INSERT INTO `order_detail` VALUES (15, '白菜豆腐汤', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/16d0a3d6-2253-4cfc-9b49-bf7bd9eb2ad2.png', 1682824802805, 69, NULL, NULL, 1, 16.00);
INSERT INTO `order_detail` VALUES (16, '雪花啤酒', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/bf8cbfc1-04d2-40e8-9826-061ee41ab87c.png', 1682827699207, 48, NULL, '无糖', 1, 4.00);
INSERT INTO `order_detail` VALUES (17, '东坡肘子', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/a80a4b8c-c93e-4f43-ac8a-856b0d5cc451.png', 1683009196583, 59, NULL, NULL, 1, 138.00);

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `number` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '订单号',
  `status` int(0) NOT NULL DEFAULT 1 COMMENT '订单状态 1待付款 2待接单 3已接单 4派送中 5已完成 6已取消 7退款',
  `user_id` bigint(0) NOT NULL COMMENT '下单用户',
  `address_book_id` bigint(0) NOT NULL COMMENT '地址id',
  `order_time` datetime(0) NOT NULL COMMENT '下单时间',
  `checkout_time` datetime(0) NULL DEFAULT NULL COMMENT '结账时间',
  `pay_method` int(0) NOT NULL DEFAULT 1 COMMENT '支付方式 1微信,2支付宝',
  `pay_status` tinyint(0) NOT NULL DEFAULT 0 COMMENT '支付状态 0未支付 1已支付 2退款',
  `amount` decimal(10, 2) NOT NULL COMMENT '实收金额',
  `remark` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '手机号',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '地址',
  `user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '用户名称',
  `consignee` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '收货人',
  `cancel_reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '订单取消原因',
  `rejection_reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '订单拒绝原因',
  `cancel_time` datetime(0) NULL DEFAULT NULL COMMENT '订单取消时间',
  `estimated_delivery_time` datetime(0) NULL DEFAULT NULL COMMENT '预计送达时间',
  `delivery_status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '配送状态  1立即送出  0选择具体时间',
  `delivery_time` datetime(0) NULL DEFAULT NULL COMMENT '送达时间',
  `pack_amount` int(0) NULL DEFAULT NULL COMMENT '打包费',
  `tableware_number` int(0) NULL DEFAULT NULL COMMENT '餐具数量',
  `tableware_status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '餐具数量状态  1按餐量提供  0选择具体数量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES (4, '1682668962242', 5, 4, 2, '2023-04-28 16:02:42', NULL, 1, 0, 89002.00, '', '13412306123', '天府大道二号', NULL, 'ODENTIST', '菜品已销售完，暂时无法接单', NULL, NULL, '2023-04-28 17:02:00', 0, NULL, 108, 0, 0);
INSERT INTO `orders` VALUES (5, '1682675807903', 6, 4, 2, '2023-04-28 17:56:48', NULL, 1, 0, 726.00, '', '13412306123', '天府大道二号', NULL, 'ODENTIST', '支付超时，自动取消', NULL, '2023-05-02 19:35:00', '2023-04-28 18:56:00', 0, NULL, 6, 0, 0);
INSERT INTO `orders` VALUES (6, '1682740326870', 6, 4, 2, '2023-04-29 11:52:07', NULL, 1, 0, 21.00, '', '13412306123', '天府大道二号', NULL, 'ODENTIST', '支付超时，自动取消', NULL, '2023-05-02 19:35:00', '2023-04-29 12:52:00', 0, NULL, 3, 0, 0);
INSERT INTO `orders` VALUES (7, '1682741710595', 6, 4, 2, '2023-04-29 12:15:11', NULL, 1, 0, 25.00, '', '13412306123', '天府大道二号', NULL, 'ODENTIST', NULL, NULL, NULL, '2023-04-29 13:15:00', 0, NULL, 1, 0, 0);
INSERT INTO `orders` VALUES (1682818120789, '1682818120789', 6, 4, 2, '2023-04-30 09:28:41', NULL, 1, 0, 9.00, '', '13412306123', '天府大道二号', NULL, 'ODENTIST', '支付超时，自动取消', NULL, '2023-05-02 19:35:00', '2023-04-30 10:28:00', 0, NULL, 1, 0, 0);
INSERT INTO `orders` VALUES (1682824802805, '1682824802805', 5, 4, 2, '2023-04-30 11:20:03', NULL, 1, 0, 23.00, '', '13412306123', '天府大道二号', NULL, 'ODENTIST', NULL, NULL, NULL, '2023-04-30 12:20:00', 1, NULL, 1, 0, 0);
INSERT INTO `orders` VALUES (1682827699207, '1682827699207', 6, 4, 2, '2023-04-30 12:08:19', NULL, 1, 0, 11.00, '', '13412306123', '天府大道二号', NULL, 'ODENTIST', NULL, NULL, NULL, '2023-04-30 13:08:00', 0, NULL, 1, 0, 0);
INSERT INTO `orders` VALUES (1683009196583, '1683009196583', 5, 4, 2, '2023-05-02 14:33:17', NULL, 1, 0, 145.00, '', '13412306123', '天府大道二号', NULL, 'ODENTIST', NULL, NULL, NULL, '2023-05-02 15:33:00', 0, NULL, 1, 0, 0);

-- ----------------------------
-- Table structure for setmeal
-- ----------------------------
DROP TABLE IF EXISTS `setmeal`;
CREATE TABLE `setmeal`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `category_id` bigint(0) NOT NULL COMMENT '菜品分类id',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '套餐名称',
  `price` decimal(10, 2) NOT NULL COMMENT '套餐价格',
  `status` int(0) NULL DEFAULT 1 COMMENT '售卖状态 0:停售 1:起售',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '描述信息',
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '图片',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `create_user` bigint(0) NULL DEFAULT NULL COMMENT '创建人',
  `update_user` bigint(0) NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_setmeal_name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 43 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '套餐' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of setmeal
-- ----------------------------
INSERT INTO `setmeal` VALUES (32, 13, '经典午餐', 68.00, 1, '又多又好', 'https://odentist-image-restore.oss-cn-chengdu.aliyuncs.com/07e213b0-094f-4189-9982-ac0b5c697ff0.png', '2023-04-20 17:07:00', '2023-04-27 16:03:36', 1, 1);
INSERT INTO `setmeal` VALUES (33, 13, '超值早餐', 6.00, 1, '又多又好', 'https://odentist-image-restore.oss-cn-chengdu.aliyuncs.com/d14bf166-104e-4ce1-ab0c-1e5a38fdc539.png', '2023-04-20 17:14:03', '2023-04-27 16:03:44', 1, 1);
INSERT INTO `setmeal` VALUES (39, 13, '酸菜鱼配米饭', 48.00, 1, '免配送', 'https://odentist-image-restore.oss-cn-chengdu.aliyuncs.com/04fd83c6-7e63-43c1-b9f4-a4c49fd80123.png', '2023-04-20 17:49:31', '2023-04-27 16:03:48', 1, 1);

-- ----------------------------
-- Table structure for setmeal_dish
-- ----------------------------
DROP TABLE IF EXISTS `setmeal_dish`;
CREATE TABLE `setmeal_dish`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `setmeal_id` bigint(0) NULL DEFAULT NULL COMMENT '套餐id',
  `dish_id` bigint(0) NULL DEFAULT NULL COMMENT '菜品id',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '菜品名称 （冗余字段）',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '菜品单价（冗余字段）',
  `copies` int(0) NULL DEFAULT NULL COMMENT '菜品份数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 73 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '套餐菜品关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of setmeal_dish
-- ----------------------------
INSERT INTO `setmeal_dish` VALUES (53, 33, 68, '鸡蛋汤', 4.00, 1);
INSERT INTO `setmeal_dish` VALUES (54, 33, 50, '馒头', 1.00, 2);
INSERT INTO `setmeal_dish` VALUES (61, 39, 49, '米饭', 2.00, 1);
INSERT INTO `setmeal_dish` VALUES (62, 39, 51, '老坛酸菜鱼', 56.00, 1);
INSERT INTO `setmeal_dish` VALUES (63, 32, 60, '梅菜扣肉', 58.00, 1);
INSERT INTO `setmeal_dish` VALUES (64, 32, 55, '蒜蓉娃娃菜', 18.00, 1);
INSERT INTO `setmeal_dish` VALUES (65, 32, 69, '白菜豆腐汤', 16.00, 1);
INSERT INTO `setmeal_dish` VALUES (66, 32, 49, '米饭', 2.00, 1);

-- ----------------------------
-- Table structure for shopping_cart
-- ----------------------------
DROP TABLE IF EXISTS `shopping_cart`;
CREATE TABLE `shopping_cart`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '商品名称',
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '图片',
  `user_id` bigint(0) NOT NULL COMMENT '主键',
  `dish_id` bigint(0) NULL DEFAULT NULL COMMENT '菜品id',
  `setmeal_id` bigint(0) NULL DEFAULT NULL COMMENT '套餐id',
  `dish_flavor` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '口味',
  `number` int(0) NOT NULL DEFAULT 1 COMMENT '数量',
  `amount` decimal(10, 2) NOT NULL COMMENT '金额',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 50 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '购物车' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of shopping_cart
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `openid` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '微信用户唯一标识',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '姓名',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '手机号',
  `sex` varchar(2) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '性别',
  `id_number` varchar(18) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '身份证号',
  `avatar` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '头像',
  `create_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '用户信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (4, 'oZu-74q04FHI3d6L0Cl8EssR7z_Y', NULL, NULL, NULL, NULL, NULL, '2023-04-23 16:01:23');

SET FOREIGN_KEY_CHECKS = 1;
