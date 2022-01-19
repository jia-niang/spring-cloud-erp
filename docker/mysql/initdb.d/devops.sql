CREATE DATABASE IF NOT EXISTS `devops_drone` DEFAULT CHARSET 'utf8mb4' COLLATE 'utf8mb4_unicode_ci';
GRANT ALL ON `devops_drone`.* TO 'admin'@'%';
FLUSH PRIVILEGES;


CREATE DATABASE IF NOT EXISTS `devops_zipkin` DEFAULT CHARSET 'utf8mb4' COLLATE 'utf8mb4_unicode_ci';
GRANT ALL ON `devops_zipkin`.* TO 'admin'@'%';
FLUSH PRIVILEGES;
