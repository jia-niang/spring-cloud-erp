CREATE DATABASE IF NOT EXISTS `spring_erp` DEFAULT CHARSET 'utf8mb4' COLLATE 'utf8mb4_unicode_ci';
GRANT ALL ON `spring_erp`.* TO 'admin'@'%';
FLUSH PRIVILEGES;
USE spring_erp;

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user`
(
    `id`                bigint unsigned                         NOT NULL AUTO_INCREMENT,
    `account`           varchar(64) COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '账号',
    `email`             varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '邮箱',
    `phone`             char(11) COLLATE utf8mb4_unicode_ci     NOT NULL COMMENT '手机号',
    `name`              varchar(64) COLLATE utf8mb4_unicode_ci  NOT NULL DEFAULT '' COMMENT '用户名',
    `sex`               tinyint unsigned                        NOT NULL DEFAULT '0' COMMENT '性别',
    `avatar`            varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '头像',
    `password`          varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
    `email_verified_at` timestamp                               NULL     DEFAULT NULL COMMENT '邮箱验证时间',
    `created_at`        timestamp                               NULL     DEFAULT NULL COMMENT '创建时间',
    `updated_at`        timestamp                               NULL     DEFAULT NULL COMMENT '更新时间',
    `deleted_time`      bigint(13) unsigned                     NOT NULL DEFAULT '0' COMMENT '逻辑删除时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `user_account_unique` (`account`, `deleted_time`),
    UNIQUE KEY `user_phone_unique` (`phone`, `deleted_time`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `password_reset`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `password_reset`
(
    `type`       varchar(64) COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '类型',
    `email`      varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '邮箱',
    `token`      varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密钥',
    `created_at` timestamp                               NULL DEFAULT NULL COMMENT '创建时间',
    KEY `user_password_reset_type_email_index` (`type`, `email`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;