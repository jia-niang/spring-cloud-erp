CREATE DATABASE IF NOT EXISTS `spring_erp` DEFAULT CHARSET 'utf8mb4' COLLATE 'utf8mb4_unicode_ci';
GRANT ALL ON `spring_erp`.* TO 'admin'@'%';
FLUSH PRIVILEGES;
USE spring_erp;

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user`
(
    `id`                bigint unsigned NOT NULL AUTO_INCREMENT,
    `account`           varchar(64) COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '账号',
    `email`             varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '邮箱',
    `phone`             char(11) COLLATE utf8mb4_unicode_ci     NOT NULL COMMENT '手机号',
    `name`              varchar(64) COLLATE utf8mb4_unicode_ci  NOT NULL DEFAULT '' COMMENT '用户名',
    `sex`               tinyint unsigned NOT NULL DEFAULT '0' COMMENT '性别',
    `avatar`            varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '头像',
    `email_verified_at` timestamp NULL DEFAULT NULL COMMENT '邮箱验证时间',
    `password`          varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
    `created_at`        timestamp NULL DEFAULT NULL,
    `updated_at`        timestamp NULL DEFAULT NULL,
    `is_deleted`        tinyint unsigned NOT NULL DEFAULT '0' COMMENT '软删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `user_account_unique` (`account`),
    UNIQUE KEY `user_email_unique` (`email`),
    UNIQUE KEY `user_phone_unique` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `user_admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_admin`
(
    `user_id`        bigint unsigned NOT NULL,
    `access_token`   varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '授权TOKEN',
    `created_type`   tinyint unsigned NOT NULL DEFAULT '0' COMMENT '来源',
    `vip_level`      tinyint unsigned NOT NULL DEFAULT '0' COMMENT '会员等级',
    `vip_expired_at` timestamp NULL DEFAULT NULL COMMENT 'vip到期时间',
    `status`         tinyint unsigned NOT NULL DEFAULT '0' COMMENT '状态',
    `created_at`     timestamp NULL DEFAULT NULL,
    `updated_at`     timestamp NULL DEFAULT NULL,
    `active_time`    timestamp NULL DEFAULT NULL COMMENT '活跃时间',
    `is_deleted`     tinyint unsigned NOT NULL DEFAULT '0' COMMENT '软删除',
    PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `user_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_member`
(
    `user_id`         bigint unsigned NOT NULL,
    `status`          tinyint unsigned NOT NULL DEFAULT '0' COMMENT '状态',
    `access_token`    varchar(64) COLLATE utf8mb4_unicode_ci  NOT NULL DEFAULT '' COMMENT '授权TOKEN',
    `openid`          varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '微信openid',
    `last_login_time` timestamp NULL DEFAULT NULL COMMENT '最后登陆时间',
    `created_at`      timestamp NULL DEFAULT NULL,
    `updated_at`      timestamp NULL DEFAULT NULL,
    `is_deleted`      tinyint unsigned NOT NULL DEFAULT '0' COMMENT '软删除',
    PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `user_password_reset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_password_reset`
(
    `type`       varchar(64) COLLATE utf8mb4_unicode_ci  NOT NULL,
    `email`      varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `token`      varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `created_at` timestamp NULL DEFAULT NULL,
    KEY          `user_password_reset_type_email_index` (`type`, `email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;