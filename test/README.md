整合springboot+jpa+redis+RabbitMQ+shiro+easyUI datagrid +jquery
默认的账号:admin  密码:123
建表的sql:


SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `s_user`;
CREATE TABLE `s_user` (
  `id` varchar(50) NOT NULL,
  `name` varchar(18) DEFAULT NULL,
  `age` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;