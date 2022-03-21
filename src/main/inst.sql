CREATE DATABASE userinfo;
USE userinfo;
ALTER DATABASE userinfo CHARACTER SET UTF8;
CREATE TABLE `userlist` (
   `id` bigint(15) NOT NULL AUTO_INCREMENT,
   `username` varchar(15) COLLATE utf8_bin NOT NULL,
   `password` varchar(15) COLLATE utf8_bin DEFAULT NULL,
   PRIMARY KEY (`username`),
   UNIQUE KEY `id` (`id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin