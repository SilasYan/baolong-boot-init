CREATE DATABASE IF NOT EXISTS baolong_boot_init DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE baolong_boot_init;


DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
  `user_account` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '账号',
  `user_password` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `user_email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户邮箱',
  `user_phone` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户手机号',
  `user_name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `user_avatar` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户头像',
  `user_profile` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户简介',
  `user_role` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT 'USER' COMMENT '用户角色（USER-普通用户, ADMIN-管理员）',
  `share_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '分享码',
  `is_disabled` tinyint NOT NULL DEFAULT 0 COMMENT '是否禁用（0-正常, 1-禁用）',
  `is_delete` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除（0-正常, 1-删除）',
  `edit_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '编辑时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_user_account`(`user_account` ASC) USING BTREE,
  UNIQUE INDEX `uk_user_email`(`user_email` ASC) USING BTREE,
  UNIQUE INDEX `uk_user_phone`(`user_phone` ASC) USING BTREE,
  INDEX `idx_user_name`(`user_name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

INSERT INTO `user` VALUES (1, 'admin', '123456', '510132075@qq.com', '15279292310', '管理员', NULL, NULL, 'ADMIN', NULL, 0, 0, '2025-04-03 20:45:00', '2025-04-03 20:45:00', '2025-04-03 20:45:00');
INSERT INTO `user` VALUES (2, 'user1', '123456', '1@qq.com', NULL, '用户1', NULL, NULL, 'USER', NULL, 0, 0, '2025-04-03 20:45:00', '2025-04-03 20:45:00', '2025-04-03 20:45:00');
INSERT INTO `user` VALUES (3, 'user2', '123456', '2@qq.com', NULL, '用户2', NULL, NULL, 'USER', NULL, 0, 0, '2025-04-03 20:45:00', '2025-04-03 20:45:00', '2025-04-03 20:45:00');
INSERT INTO `user` VALUES (4, 'user3', '123456', '3@qq.com', NULL, '用户3', NULL, NULL, 'USER', NULL, 0, 0, '2025-04-03 20:45:00', '2025-04-03 20:45:00', '2025-04-03 20:45:00');
INSERT INTO `user` VALUES (5, 'user4', '123456', '4@qq.com', NULL, '用户4', NULL, NULL, 'USER', NULL, 0, 0, '2025-04-03 20:45:00', '2025-04-03 20:45:00', '2025-04-03 20:45:00');
INSERT INTO `user` VALUES (6, 'user5', '123456', '5@qq.com', NULL, '用户5', NULL, NULL, 'USER', NULL, 0, 0, '2025-04-03 20:45:00', '2025-04-03 20:45:00', '2025-04-03 20:45:00');
INSERT INTO `user` VALUES (7, 'user6', '123456', '6@qq.com', NULL, '用户6', NULL, NULL, 'USER', NULL, 0, 0, '2025-04-03 20:45:00', '2025-04-03 20:45:00', '2025-04-03 20:45:00');
INSERT INTO `user` VALUES (8, 'user7', '123456', '7@qq.com', NULL, '用户7', NULL, NULL, 'USER', NULL, 0, 0, '2025-04-03 20:45:00', '2025-04-03 20:45:00', '2025-04-03 20:45:00');
INSERT INTO `user` VALUES (9, 'user8', '123456', '8@qq.com', NULL, '用户8', NULL, NULL, 'USER', NULL, 0, 0, '2025-04-03 20:45:00', '2025-04-03 20:45:00', '2025-04-03 20:45:00');
INSERT INTO `user` VALUES (10, 'user9', '123456', '9@qq.com', NULL, '用户9', NULL, NULL, 'USER', NULL, 0, 0, '2025-04-03 20:45:00', '2025-04-03 20:45:00', '2025-04-03 20:45:00');
INSERT INTO `user` VALUES (11, 'user0', '123456', '0@qq.com', NULL, '用户', NULL, NULL, 'USER', NULL, 0, 0, '2025-04-03 20:45:00', '2025-04-03 20:45:00', '2025-04-03 20:45:00');

