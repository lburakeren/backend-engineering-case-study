DROP DATABASE IF EXISTS `mysql-db`;
CREATE DATABASE IF NOT EXISTS `mysql-db`;

USE `mysql-db`;

CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `coins` int NOT NULL,
  `country` varchar(255) DEFAULT NULL,
  `level` int NOT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `reward_coin` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `tournaments` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `tournament_date` datetime(6) DEFAULT NULL,
  `tournament_name` varchar(255) DEFAULT NULL,
  `is_active` bit(1) NOT NULL,
  `fra_score` int NOT NULL,
  `ger_score` int NOT NULL,
  `tur_score` int NOT NULL,
  `uk_score` int NOT NULL,
  `usa_score` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `tournament_groups` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `tournament_id` bigint NOT NULL,
  `is_ready` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK69cxdijppe91a9f11li2pbq4` (`tournament_id`),
  CONSTRAINT `FK69cxdijppe91a9f11li2pbq4` FOREIGN KEY (`tournament_id`) REFERENCES `tournaments` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `group_participants` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `score` int NOT NULL,
  `group_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpk9clrnqp71eg3dqfimolmx9q` (`group_id`),
  KEY `FKlyg610dvabcq9ehyp34k6u5c0` (`user_id`),
  CONSTRAINT `FKlyg610dvabcq9ehyp34k6u5c0` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKpk9clrnqp71eg3dqfimolmx9q` FOREIGN KEY (`group_id`) REFERENCES `tournament_groups` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


INSERT INTO `users` (`coins`, `country`, `level`, `user_name`, `reward_coin`) VALUES
(6243, 'USA', 37, 'alice23', 0),
(7345, 'TURKEY', 45, 'john_doe', 0),
(8000, 'FRANCE', 28, 'emma_smith', 0),
(5612, 'UK', 22, 'michael_jones', 0),
(9201, 'GERMANY', 34, 'sofia_brown', 0),
(6754, 'USA', 39, 'liam_wilson', 0),
(5120, 'TURKEY', 29, 'isabella_taylor', 0),
(8599, 'FRANCE', 23, 'noah_miller', 0),
(7432, 'UK', 41, 'olivia_davis', 0),
(9885, 'GERMANY', 38, 'james_garcia', 0),
(5726, 'USA', 50, 'ava_martinez', 0),
(6203, 'TURKEY', 31, 'william_lopez', 0),
(4376, 'FRANCE', 25, 'mia_hall', 0),
(5945, 'UK', 27, 'benjamin_sanchez', 0),
(8900, 'GERMANY', 40, 'lucas_wright', 0),
(7325, 'USA', 35, 'charlotte_johnson', 0),
(4684, 'TURKEY', 26, 'elijah_thomas', 0),
(5168, 'FRANCE', 42, 'amelia_white', 0),
(7532, 'UK', 33, 'ethan_king', 0),
(8604, 'GERMANY', 49, 'abigail_scott', 0),
(9701, 'USA', 44, 'jack_young', 0),
(6554, 'TURKEY', 24, 'chloe_harris', 0),
(5843, 'FRANCE', 36, 'mason_clark', 0),
(7455, 'UK', 20, 'ava_robinson', 0),
(5123, 'GERMANY', 48, 'james_lopez', 0),
(8337, 'USA', 30, 'sophia_wright', 0),
(9784, 'TURKEY', 39, 'henry_james', 0),
(4152, 'FRANCE', 47, 'mila_reed', 0),
(5430, 'UK', 46, 'harper_bell', 0),
(6825, 'GERMANY', 32, 'daniel_morris', 0),
(5921, 'USA', 50, 'zoe_cook', 0),
(4016, 'TURKEY', 21, 'alexander_cooper', 0),
(7103, 'FRANCE', 38, 'lily_perez', 0),
(8888, 'UK', 29, 'sebastian_gonzalez', 0),
(5692, 'GERMANY', 24, 'samantha_reed', 0),
(7050, 'USA', 45, 'benjamin_lee', 0),
(8620, 'TURKEY', 26, 'victoria_james', 0),
(9451, 'FRANCE', 34, 'jackson_anderson', 0),
(7704, 'UK', 27, 'aiden_martin', 0),
(6000, 'GERMANY', 48, 'madison_hall', 0),
(5079, 'USA', 30, 'ella_wood', 0),
(6124, 'TURKEY', 49, 'michael_carter', 0),
(4808, 'FRANCE', 37, 'elena_jackson', 0),
(8953, 'UK', 20, 'ryan_king', 0),
(7584, 'GERMANY', 31, 'grace_hall', 0),
(6892, 'USA', 43, 'theo_brown', 0),
(7821, 'TURKEY', 50, 'nora_young', 0),
(9290, 'FRANCE', 36, 'tyler_walker', 0),
(8350, 'UK', 22, 'oliver_lee', 0),
(6107, 'GERMANY', 40, 'scarlett_lopez', 0);