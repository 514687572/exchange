-- MySQL dump 10.16  Distrib 10.1.35-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: db_exchange
-- ------------------------------------------------------
-- Server version	10.1.35-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `admin_menu`
--

DROP TABLE IF EXISTS `admin_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin_menu` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) NOT NULL DEFAULT '0',
  `order` int(11) NOT NULL DEFAULT '0',
  `title` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `icon` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `uri` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin_menu`
--

LOCK TABLES `admin_menu` WRITE;
/*!40000 ALTER TABLE `admin_menu` DISABLE KEYS */;
INSERT INTO `admin_menu` VALUES (2,0,48,'������������','fa-tasks',NULL,NULL,'2018-10-08 03:45:45'),(3,2,49,'������������','fa-users','auth/users',NULL,'2018-10-08 03:45:45'),(4,2,50,'������','fa-user','auth/roles',NULL,'2018-10-08 03:45:45'),(5,2,51,'������������','fa-ban','auth/permissions',NULL,'2018-10-08 03:45:45'),(6,2,52,'������������','fa-bars','auth/menu',NULL,'2018-10-08 03:45:45'),(7,2,53,'������������������','fa-history','auth/logs',NULL,'2018-10-08 03:45:45'),(8,0,45,'������������','fa-book','/articles','2018-06-22 01:35:47','2018-10-08 03:45:44'),(9,32,2,'������������','fa-user','systemUser','2018-06-22 22:29:42','2018-07-30 09:56:48'),(10,0,43,'������������','fa-cny','/coinConfig','2018-06-29 08:51:27','2018-10-08 03:45:44'),(11,47,22,'������������������','fa-cny','tradeMarket','2018-07-03 06:39:58','2018-10-08 03:45:43'),(12,47,21,'������������','fa-gear','config/system','2018-07-04 04:05:09','2018-10-08 03:45:43'),(13,0,46,'������������','fa-bullhorn','advertise','2018-07-05 02:08:28','2018-10-08 03:45:44'),(14,10,44,'������������','fa-cny','/coinConfig','2018-07-05 07:17:52','2018-10-08 03:45:44'),(15,0,7,'������������','fa-dollar','#','2018-07-06 08:40:22','2018-10-08 03:45:42'),(16,49,30,'������������������������','fa-eye','tradeManager','2018-07-06 08:42:24','2018-10-08 03:45:44'),(17,48,25,'������������','fa-certificate','/finance/transfer','2018-07-09 06:46:54','2018-10-08 03:45:43'),(18,48,24,'������������������','fa-futbol-o','transfer/history','2018-07-09 06:51:06','2018-10-08 03:45:43'),(19,15,8,'������������','fa-american-sign-language-interpreting','reward/register','2018-07-09 06:54:25','2018-10-08 03:45:42'),(20,15,9,'������������','fa-american-sign-language-interpreting','reward/referral','2018-07-09 06:54:48','2018-10-08 03:45:42'),(24,0,47,'Api������������','fa-user-md','userApi','2018-07-19 01:59:36','2018-10-08 03:45:44'),(25,0,10,'������������','fa-bars','#','2018-07-24 06:15:09','2018-10-08 03:45:42'),(26,25,14,'������������','fa-american-sign-language-interpreting','release/report','2018-07-24 06:18:08','2018-10-08 03:45:43'),(27,25,13,'������������','fa-american-sign-language-interpreting','dispatch/config','2018-07-24 06:59:42','2018-10-08 03:45:43'),(28,25,12,'������������','fa-american-sign-language-interpreting','dispatch/coin','2018-07-24 09:40:42','2018-10-08 03:45:42'),(29,25,11,'������','fa-american-sign-language-interpreting','dispatch','2018-07-25 03:42:28','2018-10-08 03:45:42'),(30,0,15,'C2C������','fa-bars','javascript:;','2018-07-25 07:03:25','2018-10-08 03:45:43'),(31,30,16,'C2C������������','fa-american-sign-language-interpreting','ctc/applications','2018-07-25 07:05:48','2018-10-08 03:45:43'),(32,0,1,'������������','fa-user','#','2018-07-30 09:56:29','2018-07-30 09:56:48'),(33,32,4,'������������','fa-american-sign-language-interpreting','user/coin','2018-07-30 09:57:06','2018-10-08 03:45:42'),(34,48,27,'������������������','fa-american-sign-language-interpreting','finance/bill','2018-07-30 10:29:02','2018-10-08 03:45:43'),(37,38,6,'������������','fa-american-sign-language-interpreting','userGroup','2018-08-17 03:00:16','2018-10-08 03:45:42'),(38,32,5,'������������','fa-object-group','#','2018-08-18 06:06:59','2018-10-08 03:45:42'),(41,16,31,'���������������','fa-american-sign-language-interpreting','tradeWarnConfig','2018-08-20 02:10:02','2018-10-08 03:45:44'),(42,47,20,'���������������������������','fa-bars','marketFreeConfig','2018-08-20 03:54:02','2018-10-08 03:45:43'),(43,16,32,'������������������������������','fa-american-sign-language-interpreting','specialAccountHandler','2018-08-21 02:06:17','2018-10-08 03:45:44'),(44,30,18,'C2C������������������','fa-american-sign-language-interpreting','c2c/order','2018-08-22 07:23:45','2018-10-08 03:45:43'),(45,30,17,'C2C������������','fa-american-sign-language-interpreting','c2c/config','2018-08-22 07:24:17','2018-10-08 03:45:43'),(46,48,26,'������������������','fa-bars','finance/secondTransfer','2018-08-22 08:57:04','2018-10-08 03:45:43'),(47,0,19,'������������','fa-align-justify','#','2018-08-22 10:34:37','2018-10-08 03:45:43'),(48,0,23,'������������','fa-align-justify','#','2018-08-22 10:37:03','2018-10-08 03:45:43'),(49,0,29,'������������������','fa-bars','#','2018-08-22 10:39:14','2018-10-08 03:45:43'),(50,16,33,'������������������','fa-american-sign-language-interpreting','tradeManager','2018-08-28 06:27:23','2018-10-08 03:45:44'),(52,48,28,'������������������������','fa-american-sign-language-interpreting','trade','2018-09-03 09:53:43','2018-10-08 03:45:43'),(53,49,39,'������������������������','fa-align-justify','#','2018-09-04 03:19:04','2018-10-08 03:45:44'),(54,53,42,'������������������','fa-american-sign-language-interpreting','timeMonitoringConfig','2018-09-04 03:19:29','2018-10-08 03:45:44'),(55,49,38,'���������������','fa-hourglass-start','serviceStatus','2018-09-04 07:33:55','2018-10-08 03:45:44'),(56,53,41,'������������������','fa-american-sign-language-interpreting','timeSpecialAccountHandler','2018-09-04 09:11:15','2018-10-08 03:45:44'),(57,53,40,'������������������������','fa-american-sign-language-interpreting','transactions','2018-09-17 02:41:24','2018-10-08 03:45:44'),(58,49,37,'������������','fa-bars','global/monitor','2018-09-28 07:53:46','2018-10-08 03:45:44'),(59,49,36,'������������������','fa-bars','cashConfig','2018-09-28 07:54:45','2018-10-08 03:45:44'),(60,49,35,'������������','fa-bars','cashMonitor','2018-09-28 07:55:46','2018-10-08 03:45:44'),(61,49,34,'������������������������','fa-clock-o','monitoringConfig','2018-09-28 08:32:20','2018-10-08 03:45:44'),(62,0,54,'������������������������','fa-bars','/trade','2018-10-03 03:37:46','2018-10-08 03:45:45'),(63,32,3,'������������������','fa-american-sign-language-interpreting','userTradeStatus','2018-10-08 03:45:30','2018-10-08 03:45:42'),(64,32,0,'���������������������','fa-american-sign-language-interpreting','lastTrade','2018-10-09 07:04:08','2018-10-09 07:04:08'),(65,25,0,'���������������','fa-american-sign-language-interpreting','lockUserCoin','2018-11-07 02:36:31','2018-11-07 02:36:31'),(66,25,0,'������������������������','fa-american-sign-language-interpreting','lockHistory','2018-11-07 02:46:54','2018-11-07 02:46:54'),(67,25,0,'������������������������','fa-bars','lockDetail','2018-11-08 03:06:05','2018-11-08 03:06:05'),(68,0,0,'���������������������','fa-bars','tradeLog','2018-11-16 06:55:31','2018-11-16 06:55:31');
/*!40000 ALTER TABLE `admin_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `admin_operation_log`
--

DROP TABLE IF EXISTS `admin_operation_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin_operation_log` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `path` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `method` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ip` varchar(15) COLLATE utf8mb4_unicode_ci NOT NULL,
  `input` longtext COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `admin_operation_log_user_id_index` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26162 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `admin_permissions`
--

DROP TABLE IF EXISTS `admin_permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin_permissions` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `slug` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `http_method` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `http_path` text COLLATE utf8mb4_unicode_ci,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `admin_permissions_name_unique` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin_permissions`
--

LOCK TABLES `admin_permissions` WRITE;
/*!40000 ALTER TABLE `admin_permissions` DISABLE KEYS */;
INSERT INTO `admin_permissions` VALUES (1,'������������','*','','*',NULL,'2018-07-19 06:22:20'),(3,'Login','auth.login','','/auth/login\r\n/auth/logout',NULL,NULL),(4,'User setting','auth.setting','GET,PUT','/auth/setting',NULL,NULL),(5,'Auth management','auth.management','','/auth/roles\r\n/auth/permissions\r\n/auth/menu\r\n/auth/logs',NULL,NULL),(6,'������������','������������','','/systemUser\r\n/','2018-07-19 06:48:42','2018-07-19 08:31:42'),(7,'������������','������������','GET,PUT','/systemUser/*/edit\r\n/systemUser/*','2018-07-19 08:35:44','2018-07-19 08:38:46'),(8,'������������������������','������������������������','POST','/systemUser/release\r\n/systemUser/ChangeStatus\r\n/systemUser/resetPassword\r\n/systemUser/resetPassword','2018-07-19 08:48:59','2018-07-19 08:48:59'),(9,'������������������������','������������������������','GET','/user/record/*','2018-07-19 08:54:10','2018-07-19 08:54:37'),(10,'������api������������','������api������������','GET','/userApi','2018-07-19 08:58:26','2018-07-19 08:58:26'),(11,'������api������������������������','������api������������������������','POST','/userApi/release','2018-07-19 08:58:53','2018-07-19 08:58:53'),(12,'321','123','','/auth/login\r\n/auth/logout\r\n/systemUser*','2018-10-13 13:24:18','2018-10-13 13:24:18'),(13,'667','766','','/auth/login\r\n/auth/logout\r\n/systemUser*\r\n/articles\r\n/transfer/history','2018-10-13 13:26:11','2018-10-13 13:33:09');
/*!40000 ALTER TABLE `admin_permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `admin_role_menu`
--

DROP TABLE IF EXISTS `admin_role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin_role_menu` (
  `role_id` int(11) NOT NULL,
  `menu_id` int(11) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  KEY `admin_role_menu_role_id_menu_id_index` (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin_role_menu`
--

LOCK TABLES `admin_role_menu` WRITE;
/*!40000 ALTER TABLE `admin_role_menu` DISABLE KEYS */;
INSERT INTO `admin_role_menu` VALUES (1,2,NULL,NULL),(1,8,NULL,NULL),(1,9,NULL,NULL),(1,10,NULL,NULL),(1,11,NULL,NULL),(1,12,NULL,NULL),(1,13,NULL,NULL),(1,14,NULL,NULL),(1,15,NULL,NULL),(1,16,NULL,NULL),(1,17,NULL,NULL),(1,18,NULL,NULL),(1,19,NULL,NULL),(1,20,NULL,NULL),(1,24,NULL,NULL),(2,9,NULL,NULL),(2,24,NULL,NULL),(1,25,NULL,NULL),(2,25,NULL,NULL),(1,26,NULL,NULL),(2,26,NULL,NULL),(1,27,NULL,NULL),(2,27,NULL,NULL),(1,28,NULL,NULL),(2,28,NULL,NULL),(1,29,NULL,NULL),(2,29,NULL,NULL),(1,30,NULL,NULL),(2,30,NULL,NULL),(1,31,NULL,NULL),(2,31,NULL,NULL),(1,32,NULL,NULL),(2,32,NULL,NULL),(1,33,NULL,NULL),(2,33,NULL,NULL),(1,34,NULL,NULL),(2,34,NULL,NULL),(1,37,NULL,NULL),(2,37,NULL,NULL),(1,38,NULL,NULL),(1,41,NULL,NULL),(2,41,NULL,NULL),(6,41,NULL,NULL),(7,41,NULL,NULL),(1,42,NULL,NULL),(2,42,NULL,NULL),(6,42,NULL,NULL),(7,42,NULL,NULL),(1,43,NULL,NULL),(2,43,NULL,NULL),(6,43,NULL,NULL),(7,43,NULL,NULL),(1,44,NULL,NULL),(2,44,NULL,NULL),(6,44,NULL,NULL),(7,44,NULL,NULL),(1,45,NULL,NULL),(2,45,NULL,NULL),(6,45,NULL,NULL),(7,45,NULL,NULL),(1,46,NULL,NULL),(2,46,NULL,NULL),(6,46,NULL,NULL),(7,46,NULL,NULL),(1,47,NULL,NULL),(2,47,NULL,NULL),(6,47,NULL,NULL),(7,47,NULL,NULL),(1,48,NULL,NULL),(2,48,NULL,NULL),(6,48,NULL,NULL),(7,48,NULL,NULL),(1,49,NULL,NULL),(2,49,NULL,NULL),(6,49,NULL,NULL),(7,49,NULL,NULL),(1,50,NULL,NULL),(1,52,NULL,NULL),(1,53,NULL,NULL),(1,54,NULL,NULL),(1,55,NULL,NULL),(1,56,NULL,NULL),(1,57,NULL,NULL),(1,58,NULL,NULL),(1,59,NULL,NULL),(1,60,NULL,NULL),(1,61,NULL,NULL),(1,63,NULL,NULL),(1,64,NULL,NULL),(9,32,NULL,NULL),(9,9,NULL,NULL),(9,8,NULL,NULL),(1,65,NULL,NULL),(1,66,NULL,NULL),(1,67,NULL,NULL),(1,68,NULL,NULL);
/*!40000 ALTER TABLE `admin_role_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `admin_role_permissions`
--

DROP TABLE IF EXISTS `admin_role_permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin_role_permissions` (
  `role_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  KEY `admin_role_permissions_role_id_permission_id_index` (`role_id`,`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin_role_permissions`
--

LOCK TABLES `admin_role_permissions` WRITE;
/*!40000 ALTER TABLE `admin_role_permissions` DISABLE KEYS */;
INSERT INTO `admin_role_permissions` VALUES (2,6,NULL,NULL),(6,11,NULL,NULL),(6,10,NULL,NULL),(7,3,NULL,NULL),(7,4,NULL,NULL),(7,6,NULL,NULL),(1,1,NULL,NULL),(8,3,NULL,NULL),(9,13,NULL,NULL),(10,1,NULL,NULL);
/*!40000 ALTER TABLE `admin_role_permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `admin_role_users`
--

DROP TABLE IF EXISTS `admin_role_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin_role_users` (
  `role_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  KEY `admin_role_users_role_id_user_id_index` (`role_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin_role_users`
--

LOCK TABLES `admin_role_users` WRITE;
/*!40000 ALTER TABLE `admin_role_users` DISABLE KEYS */;
INSERT INTO `admin_role_users` VALUES (1,1,NULL,NULL),(1,2,NULL,NULL),(1,3,NULL,NULL),(1,4,NULL,NULL),(1,5,NULL,NULL),(8,7,NULL,NULL),(1,8,NULL,NULL),(1,6,NULL,NULL),(1,9,NULL,NULL),(1,10,NULL,NULL),(1,11,NULL,NULL),(9,12,NULL,NULL),(10,13,NULL,NULL),(1,13,NULL,NULL),(1,14,NULL,NULL);
/*!40000 ALTER TABLE `admin_role_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `admin_roles`
--

DROP TABLE IF EXISTS `admin_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin_roles` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `slug` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `admin_roles_name_unique` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin_roles`
--

LOCK TABLES `admin_roles` WRITE;
/*!40000 ALTER TABLE `admin_roles` DISABLE KEYS */;
INSERT INTO `admin_roles` VALUES (1,'Administrator','administrator','2018-06-22 00:23:27','2018-06-22 00:23:27'),(2,'������������','������������','2018-07-19 06:37:25','2018-07-19 08:25:54'),(6,'������api������������','������api������������','2018-07-19 09:00:15','2018-07-19 09:00:15'),(7,'������','������','2018-08-10 07:22:01','2018-08-10 07:22:01'),(8,'123','123','2018-08-30 03:05:07','2018-08-30 03:05:07'),(9,'899','888','2018-10-13 13:27:18','2018-10-13 13:27:18'),(10,'������HK������','������HK������','2018-10-17 08:03:58','2018-10-17 08:03:58');
/*!40000 ALTER TABLE `admin_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `admin_user_permissions`
--

DROP TABLE IF EXISTS `admin_user_permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin_user_permissions` (
  `user_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  KEY `admin_user_permissions_user_id_permission_id_index` (`user_id`,`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin_user_permissions`
--

LOCK TABLES `admin_user_permissions` WRITE;
/*!40000 ALTER TABLE `admin_user_permissions` DISABLE KEYS */;
INSERT INTO `admin_user_permissions` VALUES (2,1,NULL,NULL),(3,1,NULL,NULL),(4,1,NULL,NULL),(1,1,NULL,NULL),(5,1,NULL,NULL),(6,1,NULL,NULL),(7,3,NULL,NULL),(8,1,NULL,NULL),(9,1,NULL,NULL),(10,1,NULL,NULL),(11,1,NULL,NULL),(12,13,NULL,NULL),(13,1,NULL,NULL),(14,1,NULL,NULL);
/*!40000 ALTER TABLE `admin_user_permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `admin_users`
--

DROP TABLE IF EXISTS `admin_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin_users` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(190) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(60) COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `avatar` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `remember_token` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `admin_users_username_unique` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin_users`
--

LOCK TABLES `admin_users` WRITE;
/*!40000 ALTER TABLE `admin_users` DISABLE KEYS */;
INSERT INTO `admin_users` VALUES (1,'admin','$2y$10$I.NdbWYJ4/pMrkCu5rsWVeEupzZGtXjakMkRa4Jga/w9VrdAhfN36','Administrator',NULL,'Y7j02UmDvijNzUR0SY248wE04SEupLZ9C5wEfYlcX7eE1QXX8UtRmmDWfNDJ','2018-06-22 00:23:27','2018-06-22 00:23:27'),(2,'blaine','$2y$10$8AKTv9j3I0fDuQcJfyMaVulVqYIE74THKgn1NNkU4Je51uVJReAF6','blaine',NULL,'PZ3TRiQ9X76UCPIOhMXCRKm32HBwXucOnvgr0PZwKrv3sLsYSZgoaWWvWw7V','2018-07-16 09:17:48','2018-07-17 07:03:44'),(3,'JOLIE','$2y$10$rNEd8ANM.ZQd0rppg4dxROziLaU3Vgepz4gwvZHN6eSEyFje1rQSe','JOLIE TAM','images/������1M.PNG','OJJwM9jah2LTSjbeybdt26U2RN3xNvN5UWpce4LvEwwuIaBfU2rafFf9IDnc','2018-08-08 08:29:24','2018-08-08 08:29:24'),(4,'jan','$2y$10$soTo68jk1VNeDUsw09eCn.nZjyD3bSQE.Y50cnjugYAIuViYCSFSC','jan',NULL,NULL,'2018-08-09 02:26:34','2018-08-09 02:26:34'),(5,'qq','$2y$10$2tFYI6BOYE4uqchhWaKSX.4oY27xslYXympAz2pOs95.lCNGnXmBa','qq',NULL,NULL,'2018-08-29 07:06:52','2018-08-29 07:06:52'),(6,'KCS','$2y$10$yujUzHB453Y3eM5aFpV2b.yx3F10pWO7BfDqCs3SoZdeBdic/t9d.','KCS',NULL,'faUFmRbDY8FnNaKMquNPc6ipaOVFHRmf3ghBL7sArmQkBFoSChuRj8k3pPBY','2018-08-30 02:35:56','2018-08-30 02:46:20'),(7,'123','$2y$10$MelgmoM7O0XbY6izVrVj4OSJou/amI006N/OfB4oKKSbG2w.zR/d6','123',NULL,'zDwG4cMOhYq7THsNuFoO3tMTWfSoUbf6usYILtCuGU3CiGLCgg3UlQf87t1f','2018-08-30 03:06:04','2018-08-30 03:06:04'),(8,'PMtest','$2y$10$b98egmIhv6bB5tp5WICuhuol6PCzho8A2RXQSoR9eQnTAm5/8dWPy','pm������','images/1.jpg','oj5YLnGv2CSVCdFI4AfZrAKI641Pmc2AZ05dsgELeeWqIQKE5H4bx9lIMUty','2018-09-03 01:42:59','2018-09-03 01:42:59'),(9,'whf1','$2y$10$ZBdUKEolvSt9K5e0opNoreBCdViUQj7sfHKv2x22NzhrQgf5JzDrm','������������',NULL,'BdpdKp8QWAA7x7uwtmPtqwYh9ykWyQI5WDpPJr274dI77yykeSAl2nCwb72G','2018-09-29 01:46:47','2018-11-08 06:55:40'),(10,'lcmao','$2y$10$wiclONhcmOz4p6HQj6gHwuGXX8p1EOsqhlcW/QkL6YdcWJ40HwqTu','lcmao',NULL,'CtucYMgpCM1YhhKWXeNUN7mFA9MtKwTYhUERzrYzDiHIJmaiy47dTZcGXPNe','2018-10-08 08:17:24','2018-10-08 08:17:24'),(11,'whf2','$2y$10$hCvwjLxpxmsqi1Dl1okFHOjk8cg/NwK0KOIJkmuFw4l/qIuFJK20a','������222',NULL,'IPvsTxvQt0ffMZTOHB0jDdqrtcKp1rV0nKJmL6tHXErCd8CDJwXY8u3RADHZ','2018-10-10 07:30:57','2018-10-10 07:30:57'),(12,'667ceshi','$2y$10$Vw4vZwnuMhyTIUF16/Mar.HLPCSyGaXTpyBdg.dkIPGvjyegNy3L2','667',NULL,'emY1x7UMm8DQI6BkLI7ur8HAQdIV2pWQi951rQxlmZ8FpvAlvLskxxUFx4g9','2018-10-13 13:29:59','2018-10-13 13:29:59'),(13,'hk001','$2y$10$bq4ai79GoK1ZxIkUAUFW3u3LwthNsWxhtka3y29wGjHbo8yCO5g.e','hk001',NULL,'toM8dogffAJgBs55o7L9pyRIitORwl2lKTZMJNIYJNSo7YxzyJM3d4XqlhF6','2018-10-17 08:05:06','2018-10-17 08:05:06'),(14,'admin1','$2y$10$qEYFL8nw1sHMCTkk8U15AuvVpF5PTHeXtba6.illOlkfyjiAD.y2.','admin1',NULL,'5ZEidErT5Ttkt37unUEOUIOICAKFplY9wSvIvRj3G5atMpGFd4FKV2g5ks1h','2018-11-10 02:48:22','2018-11-10 02:48:22');
/*!40000 ALTER TABLE `admin_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `migrations`
--

DROP TABLE IF EXISTS `migrations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `migrations` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `migration` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `batch` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `migrations`
--

LOCK TABLES `migrations` WRITE;
/*!40000 ALTER TABLE `migrations` DISABLE KEYS */;
/*!40000 ALTER TABLE `migrations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `password_resets`
--

DROP TABLE IF EXISTS `password_resets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `password_resets` (
  `email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `token` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  KEY `password_resets_email_index` (`email`(191))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `password_resets`
--

LOCK TABLES `password_resets` WRITE;
/*!40000 ALTER TABLE `password_resets` DISABLE KEYS */;
/*!40000 ALTER TABLE `password_resets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_admin`
--

DROP TABLE IF EXISTS `t_admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_admin` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_name` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '������',
  `position` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '������������',
  `password` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '������',
  `salt` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '������',
  `status` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '������(0:������ 1:������)',
  `creator` char(36) COLLATE utf8mb4_bin NOT NULL COMMENT '���������ID',
  `remark` text COLLATE utf8mb4_bin COMMENT '������',
  `createTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '������������',
  `lastTime` datetime DEFAULT NULL COMMENT '������������������',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name_UNIQUE` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='���������/���������������������������';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_admin`
--

LOCK TABLES `t_admin` WRITE;
/*!40000 ALTER TABLE `t_admin` DISABLE KEYS */;
INSERT INTO `t_admin` VALUES (1,'admin','admin','$5$5TsrgLx55hRFn65Z$YWw3Hi9TqC3RiGfuapQ2.Gz1m9cioTmvULqlJ4peXE7','$5$5TsrgLx55hRFn65Z','0','1','���������������','2018-06-25 15:45:33','2018-08-21 12:29:33'),(3,'admin2','admin2','$5$5TsrgLx55hRFn65Z$YWw3Hi9TqC3RiGfuapQ2.Gz1m9cioTmvULqlJ4peXE7','$5$5TsrgLx55hRFn65Z','0','0','���������������','2018-06-25 15:45:33','2018-06-29 11:33:09'),(4,'admin4','admin4','$5$i3qQVSmaroVFExVY$mx7/ngCha0cbbmeuEd9Gxz4N/KylGiT8v6ycdye1EL0','$5$i3qQVSmaroVFExVY','0','0','���������������','2018-06-28 16:01:55','2018-06-28 16:01:55'),(8,'2121','st21ring','$5$XjvWXlOxYMUkQ6E3$EDK4Mg8QqA6BI4QNZXDMRMR6Jbt914oPr4tVGn4z.s6','$5$XjvWXlOxYMUkQ6E3','0','1','','2018-07-16 17:09:05',NULL),(10,'blaine','blaine','$5$txzYKXB3iCayKmMD$.Hh9GF/x76KUcsp9OwuyGAOdtlNkW2zhxANRAGazkK7','$5$txzYKXB3iCayKmMD','0','1','','2018-07-16 17:17:48','2018-07-17 15:03:44'),(13,'test',NULL,'$5$Wj2mVXkkO/1YFoZ3$rkKSAtgF1qyZJKLfH2gAxmOMzxUFg6Ei/fF.mb.HJ7/','$5$Wj2mVXkkO/1YFoZ3','0','1','','2018-07-17 11:31:56','2018-07-17 11:32:02'),(14,'JOLIE','JOLIE TAM','$5$I7J08Pevxf3PvSx5$Glz4diJLhtvaikGPudhdWa6Q7gSKaZEqe8DdCU/LHO8','$5$I7J08Pevxf3PvSx5','0','1','','2018-08-08 16:29:24',NULL),(15,'jan','jan','$5$DYGvRr5TXJXUPjar$AmGrCJ5s1RHq5QGlFz2tRPGSJRN1Bd.wFoQKylVfDR5','$5$DYGvRr5TXJXUPjar','0','1','','2018-08-09 10:26:34',NULL),(16,NULL,NULL,NULL,NULL,'0','',NULL,'2018-08-21 20:00:08',NULL),(17,'qq','qq','$5$tQ9BzjeNTCS7hQuM$GLhPbTgxQl3bqleBdkahWPiai8Omk2DwbAFpf7TpCx2','$5$tQ9BzjeNTCS7hQuM','0','1','','2018-08-29 15:06:52',NULL),(18,'KCS','KCS','$5$uLS/mP.SPM5MkanT$rEQ/oiV1bEFKcfciD5YlYV72r/f1rp4tnjJhot64gSC','$5$uLS/mP.SPM5MkanT','0','1','','2018-08-30 10:35:56','2018-08-30 10:46:20'),(19,'123','123','$5$hkIZIwNDViYO6/bJ$2eHRv8/0usSRBFQYkrGIAXkfcvCLseCMEQUoL6TmBm4','$5$hkIZIwNDViYO6/bJ','0','1','','2018-08-30 11:06:04',NULL),(20,'PMtest','pm������','$5$R4FGN5QkIHrAYDT7$zY.d/mHr9pDwbGe6aDpsBRonussPQVJztYNV36l.jv/','$5$R4FGN5QkIHrAYDT7','0','1','','2018-09-03 09:42:59',NULL),(21,'whf1','������������','$5$BoDL4M1jGt5So65Q$5FLvNT/wy6k1xk9DyED3xNTnxKIoHQDv9SoM9b1nzgD','$5$BoDL4M1jGt5So65Q','0','1','','2018-09-29 09:46:47','2018-11-08 14:55:40'),(22,'lcmao','lcmao','$5$WppeuzYuSAR23kOw$hmEorVupemjRm3zoOCKgqNpXJ2pi2gaLdm63xh.JiH6','$5$WppeuzYuSAR23kOw','0','1','','2018-10-08 16:17:24',NULL),(23,'whf2','������222','$5$CtaSW2il8bK1RzKW$s9LHpHookOhuajihoDPtcfXJWg04I9e3LbtGCpq7qo4','$5$CtaSW2il8bK1RzKW','0','1','','2018-10-10 15:30:57',NULL),(24,'667ceshi','667','$5$EqgZh6Lluaai1Hy9$ZEubjX5XdzU4kooDv78WREqngabbu3YCweDAsddq7g/','$5$EqgZh6Lluaai1Hy9','0','1','','2018-10-13 21:29:59',NULL),(25,'hk001','hk001','$5$GrRiD1iyzrqGXZNq$sf0MG04YK3at7z6loV5hmAz0D1LkHiywwtyadXGP3M.','$5$GrRiD1iyzrqGXZNq','0','1','','2018-10-17 16:05:06',NULL),(26,'admin1','admin1','$5$MiqU6yfE/hiwhBU9$wHeteAMERO/xpsMY1/ykNdWd3Bpwzh1X3daXDfQn7N4','$5$MiqU6yfE/hiwhBU9','0','1','','2018-11-10 10:48:22',NULL);
/*!40000 ALTER TABLE `t_admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_admin_log`
--

DROP TABLE IF EXISTS `t_admin_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_admin_log` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `admin_id` int(11) unsigned NOT NULL,
  `type` varchar(10) COLLATE utf8mb4_bin NOT NULL COMMENT '������������������',
  `add_time` int(11) unsigned NOT NULL COMMENT '���������������',
  `operation_ip` varchar(16) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '���������������ip',
  `comment` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '������������������������������',
  PRIMARY KEY (`id`),
  KEY `fk_user_log_admin_id_idx` (`admin_id`),
  CONSTRAINT `fk_user_log_admin_id` FOREIGN KEY (`admin_id`) REFERENCES `t_admin` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='���������������������������������������������������������������������';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_admin_log`
--

LOCK TABLES `t_admin_log` WRITE;
/*!40000 ALTER TABLE `t_admin_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_admin_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_admin_resources`
--

DROP TABLE IF EXISTS `t_admin_resources`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_admin_resources` (
  `res_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '������ID',
  `name` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '������������',
  `res_level` tinyint(2) NOT NULL COMMENT '������������(1:������������,2:������������,3:������������)',
  `url` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '������URL',
  `pre_id` int(11) DEFAULT NULL COMMENT '���������ID',
  `sort_index` int(6) DEFAULT NULL COMMENT '������(������������)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '������������ ',
  `last_up_time` datetime DEFAULT NULL COMMENT '������������������ ',
  PRIMARY KEY (`res_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='���������';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_admin_resources`
--

LOCK TABLES `t_admin_resources` WRITE;
/*!40000 ALTER TABLE `t_admin_resources` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_admin_resources` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_admin_role`
--

DROP TABLE IF EXISTS `t_admin_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_admin_role` (
  `role_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '������ID',
  `name` varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT '������������',
  `create_user_id` char(36) COLLATE utf8mb4_bin NOT NULL COMMENT '������������������',
  `des` varchar(512) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '������',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '������������ ',
  `last_up_time` datetime DEFAULT NULL COMMENT '������������������ ',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '������������(0:������ 1:������)',
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `inx_osr_role_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='���������';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_admin_role`
--

LOCK TABLES `t_admin_role` WRITE;
/*!40000 ALTER TABLE `t_admin_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_admin_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_admin_role_res`
--

DROP TABLE IF EXISTS `t_admin_role_res`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_admin_role_res` (
  `role_id` int(11) unsigned NOT NULL COMMENT '������ID',
  `res_id` int(11) NOT NULL COMMENT '������ID',
  PRIMARY KEY (`role_id`,`res_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='���������������';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_admin_role_res`
--

LOCK TABLES `t_admin_role_res` WRITE;
/*!40000 ALTER TABLE `t_admin_role_res` DISABLE KEYS */;
INSERT INTO `t_admin_role_res` VALUES (0,0);
/*!40000 ALTER TABLE `t_admin_role_res` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_admin_token`
--

DROP TABLE IF EXISTS `t_admin_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_admin_token` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '������ID',
  `token` varchar(64) NOT NULL COMMENT 'token',
  `expire_time` datetime NOT NULL COMMENT '������������',
  `last_update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_userId` (`user_id`),
  KEY `idx_token` (`token`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='������������token���';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_admin_token`
--

LOCK TABLES `t_admin_token` WRITE;
/*!40000 ALTER TABLE `t_admin_token` DISABLE KEYS */;
INSERT INTO `t_admin_token` VALUES (1,10,'29b76290100753a0c029a07bfd61c03c','2018-11-20 10:37:32','2018-11-20 09:37:32'),(2,1,'f4b6261b2d7c74dd59267024df4a386f','2018-12-04 14:04:57','2018-12-04 13:04:57'),(3,3,'b20f62e41562787186d3002bd4ff89c7','2018-08-28 16:04:40','2018-08-28 15:04:40'),(4,14,'11a16112f55e4ef5ca6aa8748a96dd8e','2018-09-04 11:52:04','2018-09-04 10:52:04'),(5,17,'f1f8375885646f8cb1d71cd0a39a3d31','2018-08-29 16:09:50','2018-08-29 15:09:50'),(6,18,'ab20023e9a6f5c34528ba6d74801d186','2018-11-21 12:17:59','2018-11-21 11:17:59'),(7,19,'b1a4ee4a83ce06f222d9e8010bf9d822','2018-08-30 12:06:16','2018-08-30 11:06:16'),(8,20,'54d74ac72da2bdb0e2e7c660008faa6c','2018-11-21 11:41:14','2018-11-21 10:41:14'),(9,21,'ef8fb4f6005fb691ef5efc8a4891c1c5','2018-11-29 19:37:12','2018-11-29 18:37:12'),(10,22,'3730733d50f55c538332a5e392a58b49','2018-10-10 17:52:07','2018-10-10 16:52:07'),(11,23,'dd8d928377cdab595b134f5d94e96a52','2018-10-12 19:24:41','2018-10-12 18:24:41'),(12,24,'a941a05912c06f1a0a11eadb8c775288','2018-10-13 22:34:10','2018-10-13 21:34:10'),(13,25,'0a7cb9facfea047bf710dc0901d79529','2018-10-19 01:46:24','2018-10-19 00:46:24'),(14,26,'34fd08e14edaa20352ba1d17021a3ab4','2018-11-21 12:59:45','2018-11-21 11:59:45');
/*!40000 ALTER TABLE `t_admin_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_admin_user_role`
--

DROP TABLE IF EXISTS `t_admin_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_admin_user_role` (
  `user_id` int(11) unsigned NOT NULL COMMENT '������id',
  `role_id` int(11) NOT NULL COMMENT '������ID',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='���������������';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_admin_user_role`
--

LOCK TABLES `t_admin_user_role` WRITE;
/*!40000 ALTER TABLE `t_admin_user_role` DISABLE KEYS */;
INSERT INTO `t_admin_user_role` VALUES (0,0),(17,1),(18,1),(19,1),(20,1),(21,1),(22,1),(23,1),(24,1),(25,1),(26,1);
/*!40000 ALTER TABLE `t_admin_user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_advertise`
--

DROP TABLE IF EXISTS `t_advertise`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_advertise` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '������������',
  `position` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '������������',
  `start_time` datetime DEFAULT NULL COMMENT '������������',
  `end_time` datetime DEFAULT NULL COMMENT '������������',
  `status` int(11) DEFAULT NULL COMMENT '0:������  1:������',
  `url` varchar(512) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '������������',
  `link` varchar(512) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '������������',
  `remark` text COLLATE utf8mb4_bin COMMENT '������',
  `createTime` datetime NOT NULL COMMENT '������������',
  `lastTime` datetime DEFAULT NULL COMMENT '������������',
  `type` tinyint(4) DEFAULT NULL COMMENT '���������0:������  1:������',
  `content` text COLLATE utf8mb4_bin COMMENT '���������������',
  `locale` varchar(8) COLLATE utf8mb4_bin NOT NULL COMMENT '��������������� ������: ������_��������� ������zh_CN,zh_TW, en_US',
  `client_type` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '���������������������������1���������������2������������',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='���������������';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_advertise`
--

LOCK TABLES `t_advertise` WRITE;
/*!40000 ALTER TABLE `t_advertise` DISABLE KEYS */;
INSERT INTO `t_advertise` VALUES (3,'banner2',NULL,'2018-08-31 00:00:00','2018-10-11 00:00:00',0,'http://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/668cd86154e042da3b0c1b57ec52c49e.jpeg','111',NULL,'2018-08-31 18:44:29',NULL,0,'<p></p><p>111</p>','zh_CN',1),(6,'2121212',NULL,'2018-10-10 00:00:00','2018-10-10 00:00:00',0,'https://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/344c6301eb4824e21d72a92b3d1b84e1.png',NULL,NULL,'2018-10-10 14:50:50',NULL,0,'<p></p><p>1111</p>','en_US',2),(7,'app������',NULL,'2018-10-10 00:00:00','2018-10-11 00:00:00',0,'https://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/4866f213306158b87aa39a42c88b47c8.png','www.baidu.com',NULL,'2018-10-10 18:12:16',NULL,0,'<p></p><p>APP������</p>','zh_CN',2),(8,'app������',NULL,'2018-10-16 00:00:00','2018-11-22 00:00:00',0,'https://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/5a97e727270c918b5792674749b5d711.png',NULL,NULL,'2018-10-16 17:05:53',NULL,1,'<p></p><p>app������app������app������app������app������app������app������</p>','zh_CN',2);
/*!40000 ALTER TABLE `t_advertise` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_app_version`
--

DROP TABLE IF EXISTS `t_app_version`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_app_version` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '������',
  `code` int(11) DEFAULT NULL COMMENT '������',
  `version_name` varchar(20) DEFAULT NULL COMMENT '������',
  `platform` varchar(25) DEFAULT NULL COMMENT '������',
  `url` varchar(255) DEFAULT NULL COMMENT '������',
  `content` varchar(255) DEFAULT NULL COMMENT '������',
  `force` int(11) DEFAULT NULL COMMENT '(0-������������1-������)',
  `create_time` datetime DEFAULT NULL COMMENT '������������',
  `last_time` datetime DEFAULT NULL COMMENT '������������',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_app_version`
--

LOCK TABLES `t_app_version` WRITE;
/*!40000 ALTER TABLE `t_app_version` DISABLE KEYS */;
INSERT INTO `t_app_version` VALUES (1,1300,'1.3.0','Android','https://fir.im/g45t','android',0,'2018-10-08 16:24:27','2018-11-08 16:24:29'),(2,1300,'1.3.0','ios','https://fir.im/g45t','IOS',0,'2018-10-08 16:24:08','2018-11-08 16:24:11');
/*!40000 ALTER TABLE `t_app_version` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_article`
--

DROP TABLE IF EXISTS `t_article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_article` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(128) COLLATE utf8mb4_bin NOT NULL COMMENT '������������',
  `type` tinyint(4) NOT NULL COMMENT '������������ 1:������ 2���������������',
  `locale` varchar(8) COLLATE utf8mb4_bin NOT NULL COMMENT '��������������� ������: ������_��������� ������zh_CN,zh_TW, en_US',
  `content` longtext COLLATE utf8mb4_bin NOT NULL COMMENT '������������������������',
  `status` tinyint(4) NOT NULL COMMENT '��������������� 1: ������ 2������������',
  `creator` int(11) unsigned NOT NULL COMMENT '���������id',
  `created_at` datetime NOT NULL COMMENT '������������',
  `updated_at` datetime NOT NULL COMMENT '���������������������������',
  `display_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '������������������������������������������������������',
  PRIMARY KEY (`id`),
  KEY `ix_type` (`type`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='���������������';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_cash_monitoring_config`
--

DROP TABLE IF EXISTS `t_cash_monitoring_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_cash_monitoring_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `coin_name` varchar(20) NOT NULL COMMENT '������������',
  `roll_in_number` varchar(20) DEFAULT NULL COMMENT '���������������',
  `roll_out_number` varchar(20) DEFAULT NULL COMMENT '���������������',
  `last_refresh_time` int(20) DEFAULT '0' COMMENT '���������������������',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_cash_monitoring_config`
--

LOCK TABLES `t_cash_monitoring_config` WRITE;
/*!40000 ALTER TABLE `t_cash_monitoring_config` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_cash_monitoring_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_change_coin`
--

DROP TABLE IF EXISTS `t_change_coin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_change_coin` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `want_change_time` datetime NOT NULL DEFAULT '2000-01-01 00:00:00' COMMENT '������������������������������',
  `coin_name` varchar(12) NOT NULL DEFAULT '' COMMENT '������',
  `user_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '������id',
  `status` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '���������������������0���������������������1���������������',
  `change_available` decimal(32,8) NOT NULL DEFAULT '0.00000000' COMMENT '���������������������',
  `change_freeze` decimal(32,8) NOT NULL DEFAULT '0.00000000' COMMENT '������������������',
  `reason` varchar(8) NOT NULL DEFAULT '' COMMENT '������������������������������������������received:���������������������send:���������',
  `comment` varchar(100) DEFAULT NULL COMMENT '������������������������������������������������������������������������������������',
  `create_time` datetime NOT NULL COMMENT '������������',
  `update_time` datetime DEFAULT NULL COMMENT '���������������������������������������t_user_coin���������',
  PRIMARY KEY (`id`),
  KEY `changed_change_time` (`status`,`want_change_time`)
) ENGINE=InnoDB AUTO_INCREMENT=11661465 DEFAULT CHARSET=utf8 COMMENT='������������������������������������';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_coin`
--

DROP TABLE IF EXISTS `t_coin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_coin` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(8) NOT NULL DEFAULT '' COMMENT '���������������������������',
  `symbol` varchar(8) NOT NULL COMMENT '������������������������������������������������������������������������������������������',
  `category` varchar(8) NOT NULL COMMENT '������������������������������btc���eth���token���real���������������������������������������������������������������������������������������������������',
  `display_name` varchar(32) NOT NULL COMMENT '���������������������������������������������������������������������������������������',
  `display_name_all` varchar(255) DEFAULT NULL COMMENT '���������������������������������������������������������������������������zh:������������������������������������������������������������������������������������������������',
  `image` varchar(128) DEFAULT NULL COMMENT '������������',
  `icon` varchar(128) NOT NULL COMMENT '���������������������',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '������',
  `status` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '���������0������������1���������������',
  `server_address` varchar(100) NOT NULL COMMENT '���������������������������������������������������',
  `server_port` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '���������������������������������������������������',
  `server_user` varchar(45) DEFAULT NULL COMMENT '���������������������������������������',
  `server_password` varchar(45) DEFAULT NULL COMMENT '������������������������������������',
  `send_fee` float(8,4) unsigned NOT NULL DEFAULT '0.0000' COMMENT '������������������������������������������������������',
  `received_fee` float(8,4) unsigned NOT NULL DEFAULT '0.0000' COMMENT '���������������������������������������,������',
  `contract_address` varchar(64) DEFAULT NULL COMMENT '������������������������������������������������������',
  `coin_self_parameter` varchar(500) DEFAULT NULL COMMENT '������������������������������������������������������������������������������������������������json������',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '������������',
  `last_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '������������������������',
  `coin_base` varchar(128) DEFAULT NULL COMMENT '���������������',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COMMENT='������������������';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_coin`
--

LOCK TABLES `t_coin` WRITE;
/*!40000 ALTER TABLE `t_coin` DISABLE KEYS */;
INSERT INTO `t_coin` VALUES (1,'BON','BON','token','BON','BON','http://zcoinpro-1256505246.cos.ap-hongkong.myqcloud.com/77cc704abf4c228fa1e4db44574dea1e.png','http://zcoinpro-1256505246.cos.ap-hongkong.myqcloud.com/227bb45e96ca38c9c43790d8a0ac120e.png',0,0,'127.0.0.1',3331,'user','pass',0.0000,0.0000,'0x','string','2018-06-27 17:01:59','2018-10-10 17:13:25',NULL),(2,'BTC','BTC','btc','BTC','BTC','http://zcoinpro-1256505246.cos.ap-hongkong.myqcloud.com/77cc704abf4c228fa1e4db44574dea1e.png','http://zcoinpro-1256505246.cos.ap-hongkong.myqcloud.com/77cc704abf4c228fa1e4db44574dea1e.png',0,0,'39.108.94.158',18755,'user','pass',0.0000,0.0000,'','string','2018-06-27 17:14:03','2018-10-10 16:21:23',NULL),(8,'ETH','ETH','eth','ETH','ETH','http://zcoinpro-1256505246.cos.ap-hongkong.myqcloud.com/be7267b5d052a8fbcb8a1c3b06e5941e.png','http://zcoinpro-1256505246.cos.ap-hongkong.myqcloud.com/290dedb17c0a3c90cbc89c6bdb1e29a0.png',0,0,'47.75.240.124',8066,'user','pass',0.0000,0.0000,'','6','2018-07-02 10:33:13','2018-10-09 17:14:49','0x5846a28a084955df5c3b54ddb3a7c68a0b45cd96'),(9,'USDT','USDT','usdt','USDT','USDT','http://zcoinpro-1256505246.cos.ap-hongkong.myqcloud.com/c662d3d5517a0f2ae8b124a3eb154f07.jpg','http://zcoinpro-1256505246.cos.ap-hongkong.myqcloud.com/04061aa22829dd59095a2e740c6c37ac.jpg',0,0,'39.108.94.158',18755,'user','pass',0.0000,0.0000,'','3132','2018-07-08 16:27:18','2018-10-10 16:24:04',NULL),(10,'LTC','LTC','btc','LTC',NULL,'http://zcoinpro-1256505246.cos.ap-hongkong.myqcloud.com/b907a39b6c39ec3cd1f61a0e34096813.png','http://zcoinpro-1256505246.cos.ap-hongkong.myqcloud.com/37e155edaf91fdd8a5b2eb521a064413.jpg',0,0,'39.108.94.158',18755,'user','pass',0.0000,0.0000,'21','21','2018-07-11 10:09:18','2018-10-10 16:24:06',NULL),(11,'EST','EST','btc','EST',NULL,'xxxx','icon',2,0,'39.108.94.158',18755,'user','pass',0.0000,0.0000,NULL,NULL,'2018-07-11 10:20:20','2018-10-10 16:24:09',NULL),(16,'1111','1111','real','1111','1111','http://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/172ae3551557eeb927ceaa977cd6e75a.png','http://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/b287be69d5f7a38c17ba16a85f898e0b.png',0,1,'1111',1111,'111','1111',0.0000,0.0000,'111','111','2018-08-31 16:50:33','2018-10-18 11:09:10',NULL),(19,'YT','YT','eth','YT','YT','https://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/37501c919d77042bbfe54bba4e0a9725.jpg','https://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/e4d8cd610836a77c74e7d5bcc1dd764c.jpg',0,0,'124389',0,'0','0',0.0000,0.0000,'0','0','2018-09-03 10:38:52','2018-11-20 16:29:36',NULL),(20,'DG','DG','eth','DG','DG','https://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/116cb4d04a320cb2c9b8599097eb2b49.png','https://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/e2c9757e6ba36d39f0b7bf8b66140343.png',0,0,'1323464',0,'0123','0',0.0000,0.0000,'0','0','2018-09-03 11:11:44','2018-11-08 18:22:51','1'),(21,'PKT','PKT','token','PKT','PKT','http://caesaronly.oss-cn-hongkong.aliyuncs.com/c3f33176179960712cdb6f2561064643.png','http://caesaronly.oss-cn-hongkong.aliyuncs.com/4fcd1ca8c1ac4c7bbf369e51efa58727.png',0,0,'47.75.240.124',8066,'user','pass',0.0000,0.0000,'0x2e68db8bd74cc4d58ea502365f2437b2c949559b','0','2018-10-09 18:01:25','2018-10-10 17:12:12','0xd6f2876baa565d5031dc49e9f28c65234489ca1c'),(22,'TEST','xt','token','xt','aaa','https://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/12a537fd2c7acd929b8629b61a0e4fcf.jfif','https://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/39397010b8e864e5c9fe1f394807e17b.jfif',0,0,'39.108.94.158',8066,'user','pass',0.0000,0.0000,'0x2e68db8bd74cc4d58ea502365f2437b2c949559b','string','2018-10-10 17:48:53','2018-10-10 17:48:53',NULL),(23,'NGT','NGT','eth','NGT','NGT','https://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/fa034ed93827ecdbf881909971489039.png','https://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/99761f67d96bcaa4110fc4282763ae3e.png',0,0,'123456789',0,'0','0',0.0000,0.0000,'0','0','2018-11-13 22:57:05','2018-11-13 22:57:05',NULL);
/*!40000 ALTER TABLE `t_coin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_coin_config`
--

DROP TABLE IF EXISTS `t_coin_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_coin_config` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `coin_name` varchar(32) NOT NULL COMMENT '������',
  `transfer_fee_rate` decimal(10,4) NOT NULL DEFAULT '0.0000' COMMENT '���������������',
  `transfer_fee_static` decimal(10,4) DEFAULT '0.0000' COMMENT '���������������������',
  `transfer_fee_select` int(11) DEFAULT '0' COMMENT '���������������:0������1������',
  `transfer_min_amount` decimal(32,8) DEFAULT '0.00000000' COMMENT '���������������',
  `transfer_max_amount` decimal(32,8) DEFAULT '0.00000000' COMMENT '���������������',
  `node_confirm_count` int(11) DEFAULT '3' COMMENT '���������������������',
  `maximum_amount_day` decimal(32,8) DEFAULT '0.00000000' COMMENT '������������������������',
  `maximum_number_day` int(11) DEFAULT '1' COMMENT '������������������������',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_coinname` (`coin_name`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_coin_config`
--

LOCK TABLES `t_coin_config` WRITE;
/*!40000 ALTER TABLE `t_coin_config` DISABLE KEYS */;
INSERT INTO `t_coin_config` VALUES (1,'BTC',0.0001,6.0000,0,1.00000000,10000.00000000,3,0.00000000,1),(2,'BON',0.0300,15.0000,0,1.00000000,10000.00000000,3,0.00000000,1),(3,'USDT',0.0200,2.0000,1,1.00000000,10000.00000000,3,0.00000000,1),(4,'ETH',0.2000,6.0000,0,1.00000000,10000.00000000,3,100000.00000000,10000),(6,'1111',111.0000,NULL,0,111.00000000,11111.00000000,NULL,0.00000000,1),(7,'AAA',0.1000,NULL,0,0.00000000,0.00000000,NULL,0.00000000,1),(8,'BBB',0.0001,NULL,0,0.00000000,0.00000000,NULL,0.00000000,1),(9,'YT',0.0000,NULL,0,1.00000000,10000.00000000,NULL,0.00000000,1),(10,'DG',0.0030,1.0000,0,1.00000000,100.00000000,NULL,0.00000000,1),(11,'TEST',0.1000,6.0000,0,1.00000000,1000.00000000,NULL,0.00000000,1),(12,'NGT',0.0000,NULL,0,100.00000000,10000.00000000,NULL,0.00000000,1);
/*!40000 ALTER TABLE `t_coin_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_coin_difference`
--

DROP TABLE IF EXISTS `t_coin_difference`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_coin_difference` (
  `coin_name` varchar(20) NOT NULL,
  `roll_in_total` decimal(18,8) DEFAULT NULL COMMENT '���������������������������',
  `tody_add_roll_in_total` decimal(18,8) DEFAULT NULL COMMENT '���������������������',
  `user_balance_total` decimal(18,8) DEFAULT NULL COMMENT '������������������������',
  `point_total` decimal(18,8) DEFAULT NULL COMMENT '������������������',
  `today_add_point_total` decimal(18,8) DEFAULT NULL COMMENT '���������������������������',
  `refresh_time` datetime DEFAULT NULL COMMENT '������������������������������',
  PRIMARY KEY (`coin_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_coin_difference`
--

LOCK TABLES `t_coin_difference` WRITE;
/*!40000 ALTER TABLE `t_coin_difference` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_coin_difference` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_coin_difference_config`
--

DROP TABLE IF EXISTS `t_coin_difference_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_coin_difference_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '������',
  `coin_name` varchar(20) NOT NULL COMMENT '������',
  `more_difference` varchar(20) NOT NULL COMMENT '���������������������������������',
  `less_difference` varchar(20) NOT NULL COMMENT '���������������������������������',
  `refresh_time` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_coin_difference_config`
--

LOCK TABLES `t_coin_difference_config` WRITE;
/*!40000 ALTER TABLE `t_coin_difference_config` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_coin_difference_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_coin_group_config`
--

DROP TABLE IF EXISTS `t_coin_group_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_coin_group_config` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `group_id` int(11) NOT NULL COMMENT '���������������Id,���������������������',
  `coin_name` varchar(20) NOT NULL DEFAULT '' COMMENT '������������',
  `con_value` varchar(20) DEFAULT '0' COMMENT '���������������������������������������',
  `remark` varchar(200) DEFAULT NULL COMMENT '������',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '0:��������������� 1���������������',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8 COMMENT='������������-���������������,���������USDT������������������������������������������������������������';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_coin_group_config`
--

LOCK TABLES `t_coin_group_config` WRITE;
/*!40000 ALTER TABLE `t_coin_group_config` DISABLE KEYS */;
INSERT INTO `t_coin_group_config` VALUES (1,1,'1111',NULL,NULL,1),(2,2,'1111',NULL,NULL,1),(3,3,'1111',NULL,NULL,1),(4,4,'1111',NULL,NULL,1),(5,5,'1111',NULL,NULL,1),(6,6,'1111',NULL,NULL,1),(7,1,'AAA',NULL,NULL,0),(8,2,'AAA',NULL,NULL,0),(9,3,'AAA',NULL,NULL,0),(10,4,'AAA',NULL,NULL,0),(11,5,'AAA',NULL,NULL,0),(12,6,'AAA',NULL,NULL,0),(13,1,'BBB',NULL,NULL,0),(14,2,'BBB',NULL,NULL,0),(15,3,'BBB',NULL,NULL,0),(16,4,'BBB',NULL,NULL,0),(17,5,'BBB',NULL,NULL,0),(18,6,'BBB',NULL,NULL,0),(19,1,'YT',NULL,NULL,1),(20,2,'YT',NULL,NULL,1),(21,3,'YT',NULL,NULL,1),(22,4,'YT',NULL,NULL,1),(23,5,'YT',NULL,NULL,1),(24,6,'YT',NULL,NULL,1),(25,1,'DG',NULL,NULL,1),(26,2,'DG',NULL,NULL,1),(27,3,'DG',NULL,NULL,1),(28,4,'DG',NULL,NULL,1),(29,5,'DG',NULL,NULL,1),(30,6,'DG',NULL,NULL,1),(31,1,'TEST',NULL,NULL,0),(32,2,'TEST',NULL,NULL,0),(33,3,'TEST',NULL,NULL,0),(34,4,'TEST',NULL,NULL,0),(35,5,'TEST',NULL,NULL,0),(36,6,'TEST',NULL,NULL,0),(37,7,'TEST',NULL,NULL,0),(38,1,'NGT',NULL,NULL,0),(39,2,'NGT',NULL,NULL,0),(40,3,'NGT',NULL,NULL,0),(41,4,'NGT',NULL,NULL,0),(42,5,'NGT',NULL,NULL,0),(43,6,'NGT',NULL,NULL,0),(44,7,'NGT',NULL,NULL,0),(45,8,'NGT',NULL,NULL,0),(46,9,'NGT',NULL,NULL,0);
/*!40000 ALTER TABLE `t_coin_group_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_coin_stat`
--

DROP TABLE IF EXISTS `t_coin_stat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_coin_stat` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '������',
  `coin_name` varchar(20) DEFAULT NULL COMMENT '������������',
  `total_fee` decimal(18,8) DEFAULT NULL COMMENT '���������������������������������',
  `last_refresh_time` int(20) DEFAULT '0' COMMENT '������������������',
  `total_cureency` decimal(18,8) DEFAULT NULL COMMENT '���������������',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='������������������������������';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_coin_stat`
--

LOCK TABLES `t_coin_stat` WRITE;
/*!40000 ALTER TABLE `t_coin_stat` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_coin_stat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_config`
--

DROP TABLE IF EXISTS `t_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_config` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `conf_name` varchar(50) NOT NULL DEFAULT '' COMMENT '������������������������������������������������',
  `conf_value` varchar(1000) DEFAULT NULL COMMENT '������������',
  `comment` varchar(40) DEFAULT NULL COMMENT '������������',
  PRIMARY KEY (`id`),
  UNIQUE KEY `key_name_UNIQUE` (`conf_name`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8mb4 COMMENT='���������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_config`
--

LOCK TABLES `t_config` WRITE;
/*!40000 ALTER TABLE `t_config` DISABLE KEYS */;
INSERT INTO `t_config` VALUES (6,'LastMatchTradeId','101942','���������������������ID'),(7,'user.register.reward','100','������������'),(8,'user.referrer.reward','100','������������'),(9,'user.referrer.url','http://47.75.240.124','������������'),(10,'platform.coin.name','DET','���������(������������������)'),(11,'bcn.price','6.8','USDT���������������������������������'),(15,'trade.mining.reward','0','������������������������������������������������ 20������20%'),(16,'trade.fee.reward','0','������������������������������������������������������ 20������20%'),(20,'referrer.reward.ranking','[{\"phone\":\"1111124\",\"reward\":112.3214},{\"phone\":\"2222214\",\"reward\":22.3214},{\"phone\":\"333332144\",\"reward\":2.3214}]','������������������'),(21,'LastSyncBlock_usdt','2960100',''),(22,'market.base.coin','USDT','���������������������������'),(23,'trade.mining.last_id','0',''),(24,'trade.mining.recommend.reward','51','������������������������������������������������������ 20������20%'),(25,'official_group.wx_image_link','','������������������������������'),(26,'official_group.qq_link','https://jq.qq.com/?_wv=1027&k=5iwc21l','������qq���������'),(27,'convert.release.time','2018-07-24=2018-08-01,2018-07-25=2018-09-01,2018-07-26=2018-09-02,2018-07-27=2018-09-03,2018-07-28=2018-09-04',NULL),(28,'convert.plat.time','2018-07-24 11:04:00-21:00:00,2018-07-25 15:00:00-16:00:00,2018-07-26 15:00:00-20:00:00,2018-07-27 15:00:00-20:00:00,2018-07-28 15:00:00-20:00:00',NULL),(29,'trade.min.shout.coin','10000','���������������������BON������'),(30,'eth.gas.price','60000000000','gasPrice'),(31,'eth.gas.limit','60000','gasLimit'),(34,'trade.share.out.end.time','1543975200','������������������������������������������������'),(36,'mining.ext.release.rate','0','������������������������������������������������������������,20������20%'),(43,'LastSyncEth_127.0.0.1:8066','120710',''),(44,'gather.last.id.ETH','3',''),(45,'LastSyncEth_47.75.240.124:8066','436718',''),(47,'user.trade.stat.last.log.id','1176842',''),(48,'com.legal.money.display','USDT',' ���������������������������������������������'),(49,'gather.last.id.PKT','41',''),(50,'LastSyncBlock_LTC','41296',''),(51,'LastSyncBlock_EST','41296',''),(52,'LastSyncBlock_BTC','41296',''),(53,'reward.trade.fee.last.time','1543939200','���������������������������������������������������������'),(54,'reward.trade.fee.rec.user','0.2','������������������������������������������������������'),(55,'reward.trade.fee.rec.rec.user','0.3','������������������������������������������������������������');
/*!40000 ALTER TABLE `t_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_cron_config`
--

DROP TABLE IF EXISTS `t_cron_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_cron_config` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `cron_name` varchar(128) DEFAULT NULL COMMENT 'cron������',
  `cron_type` int(11) DEFAULT NULL COMMENT '������������������1cron,2simple',
  `cron_expression` varchar(32) DEFAULT NULL COMMENT 'cron���������',
  `cron_no` varchar(32) DEFAULT NULL COMMENT '������',
  `status` int(11) DEFAULT '0' COMMENT '������:0���������1������',
  `cron_className` varchar(128) DEFAULT NULL COMMENT '���������',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_cron_config`
--

LOCK TABLES `t_cron_config` WRITE;
/*!40000 ALTER TABLE `t_cron_config` DISABLE KEYS */;
INSERT INTO `t_cron_config` VALUES (1,'������������1���������',1,'0 0 1 * * ?',NULL,1,'com.cmd.exchange.common.task.MyDispatchJob','2018-07-20 18:53:24','2018-07-23 11:35:45'),(2,'���30���������',1,'*/30 * * * * ?',NULL,1,'com.cmd.exchange.common.task.MyDispatchJob','2018-07-20 18:54:00','2018-07-23 11:35:45'),(3,'���10������������������',1,'0 0,10,20,30,40,50 * * * ?',NULL,1,'com.cmd.exchange.common.task.MyDispatchJob','2018-07-20 18:54:00','2018-07-23 11:35:45');
/*!40000 ALTER TABLE `t_cron_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_delay_release`
--

DROP TABLE IF EXISTS `t_delay_release`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_delay_release` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `release_time` datetime NOT NULL COMMENT '���������������',
  `coin_name` varchar(12) NOT NULL DEFAULT '' COMMENT '������',
  `user_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '������id',
  `amount` decimal(24,8) unsigned NOT NULL DEFAULT '0.00000000' COMMENT '���������������',
  `had_release` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '������������������',
  `create_time` datetime NOT NULL COMMENT '������������',
  `update_time` datetime DEFAULT NULL COMMENT '������������������������',
  PRIMARY KEY (`id`),
  KEY `release_time` (`release_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='������������������������������������������������������';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_delay_release`
--

LOCK TABLES `t_delay_release` WRITE;
/*!40000 ALTER TABLE `t_delay_release` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_delay_release` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_delay_reward_trade`
--

DROP TABLE IF EXISTS `t_delay_reward_trade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_delay_reward_trade` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '������id',
  `coin_name` varchar(8) NOT NULL DEFAULT '' COMMENT '������������',
  `change_amount` decimal(24,8) NOT NULL DEFAULT '0.00000000' COMMENT '������������',
  `comment` varchar(100) DEFAULT NULL COMMENT '������������',
  `add_time` datetime DEFAULT NULL COMMENT '������������',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=264 DEFAULT CHARSET=utf8mb4 COMMENT='���������������������������������������������������������������';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_delay_reward_trade`
--

LOCK TABLES `t_delay_reward_trade` WRITE;
/*!40000 ALTER TABLE `t_delay_reward_trade` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_delay_reward_trade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_delay_shareout`
--

DROP TABLE IF EXISTS `t_delay_shareout`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_delay_shareout` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '������id',
  `coin_name` varchar(8) NOT NULL DEFAULT '' COMMENT '������������',
  `change_amount` decimal(24,8) NOT NULL DEFAULT '0.00000000' COMMENT '������������',
  `user_base_coin` decimal(32,8) NOT NULL DEFAULT '0.00000000' COMMENT '���������������BON)',
  `comment` varchar(100) DEFAULT NULL COMMENT '������������',
  `add_time` datetime DEFAULT NULL COMMENT '������������',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='������������������������������������������';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_delay_shareout`
--

LOCK TABLES `t_delay_shareout` WRITE;
/*!40000 ALTER TABLE `t_delay_shareout` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_delay_shareout` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_dispatch_config`
--

DROP TABLE IF EXISTS `t_dispatch_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_dispatch_config` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `cron_id` int(11) DEFAULT NULL COMMENT '������������ID',
  `cron_name` varchar(128) DEFAULT NULL COMMENT '������������������',
  `lock_name` varchar(32) DEFAULT NULL COMMENT '������������',
  `lock_rate` decimal(10,4) DEFAULT '0.0000' COMMENT '������������',
  `free_rate` decimal(10,4) DEFAULT '0.0000' COMMENT '������������',
  `status` int(11) DEFAULT '0' COMMENT '1���������0������',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_dispatch_config`
--

LOCK TABLES `t_dispatch_config` WRITE;
/*!40000 ALTER TABLE `t_dispatch_config` DISABLE KEYS */;
INSERT INTO `t_dispatch_config` VALUES (1,1,'������������1���������','������������������',1.0000,0.5000,1,'2018-09-03 10:29:33','2018-11-12 10:23:25'),(2,2,'���30���������','���������30���������',1.0000,0.1000,1,'2018-09-05 18:40:19','2018-09-12 14:11:15'),(3,1,'������������1���������','������������������',0.0000,1.0000,1,'2018-09-12 17:04:17','2018-11-12 10:26:20'),(4,2,'���30���������','���1���',0.0000,1.0000,1,'2018-10-18 21:38:13','2018-11-12 10:26:23'),(5,2,'���30���������','123',1.0000,1.0000,1,'2018-11-09 16:51:01','2018-11-12 09:51:03');
/*!40000 ALTER TABLE `t_dispatch_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_dispatch_job`
--

DROP TABLE IF EXISTS `t_dispatch_job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_dispatch_job` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `mobile` varchar(128) DEFAULT NULL,
  `cron_id` int(11) DEFAULT NULL COMMENT '���������ID(������������������������)',
  `cron_name` varchar(128) DEFAULT NULL COMMENT '������������������',
  `dispatch_no` varchar(32) DEFAULT NULL COMMENT '������������',
  `coin_name` varchar(10) DEFAULT NULL COMMENT '������',
  `comment` varchar(128) DEFAULT NULL COMMENT '������������',
  `amount` decimal(32,8) DEFAULT NULL COMMENT '������������',
  `amount_all` decimal(32,8) DEFAULT NULL COMMENT '������������������',
  `lock_rate` decimal(32,8) DEFAULT NULL COMMENT '������������',
  `free_rate` decimal(32,8) DEFAULT NULL COMMENT '������������',
  `status` int(11) DEFAULT NULL COMMENT '������',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_createtime` (`create_time`),
  KEY `idx_cronid` (`cron_id`)
) ENGINE=InnoDB AUTO_INCREMENT=138 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_dispatch_job`
--

LOCK TABLES `t_dispatch_job` WRITE;
/*!40000 ALTER TABLE `t_dispatch_job` DISABLE KEYS */;
INSERT INTO `t_dispatch_job` VALUES (1,138,'18576635213',1,'������������1���������',NULL,'TCC',NULL,0.00000000,0.00000001,0.20000000,0.80000000,NULL,'2018-09-05 15:39:13','2018-09-05 15:39:13'),(2,152,'13900000001',2,'���30���������',NULL,'YT','YT',0.00000000,10000000.00000000,1.00000000,0.10000000,NULL,'2018-09-12 14:13:12','2018-09-12 14:18:00'),(3,151,'18576635555',1,'������������1���������',NULL,'BTC','1',0.00000000,1.00000000,0.20000000,0.80000000,NULL,'2018-09-12 15:10:24','2018-09-13 01:00:00'),(4,152,'13900000001',1,'������������1���������',NULL,'BTC','1',0.00000000,1.00000000,0.20000000,0.80000000,NULL,'2018-09-12 15:10:24','2018-09-13 01:00:00'),(5,142,NULL,1,'������������1���������',NULL,'USDT',NULL,0.00000000,1.00000000,0.20000000,0.80000000,NULL,'2018-09-12 15:24:44','2018-09-13 01:00:00'),(6,28,'16620855816',1,'������������1���������',NULL,'USDT',NULL,0.00000000,2000.00000000,0.00000000,1.00000000,NULL,'2018-09-12 17:04:41','2018-09-12 17:04:41'),(7,153,'13590469049',2,'���30���������',NULL,'YT','YT������',0.00000000,1000000.00000000,1.00000000,0.10000000,NULL,'2018-09-13 11:43:30','2018-09-13 11:48:30'),(8,30,NULL,2,'���30���������',NULL,'USDT',NULL,0.00000000,100.00000000,1.00000000,0.10000000,NULL,'2018-09-17 15:02:20','2018-09-17 15:07:00'),(9,19,'18612341234',1,'������������1���������',NULL,'BTC',NULL,0.00000000,2000.00000000,0.00000000,1.00000000,NULL,'2018-09-19 10:40:25','2018-09-19 10:40:25'),(10,19,'18612341234',1,'������������1���������',NULL,'ETH',NULL,0.00000000,2000.00000000,0.00000000,1.00000000,NULL,'2018-09-19 10:40:33','2018-09-19 10:40:33'),(11,19,'18612341234',1,'������������1���������',NULL,'USDT',NULL,0.00000000,2000.00000000,0.00000000,1.00000000,NULL,'2018-09-19 10:40:50','2018-09-19 10:40:50'),(12,28,'16620855816',1,'������������1���������',NULL,'BTC',NULL,0.00000000,20.00000000,0.00000000,1.00000000,NULL,'2018-09-29 10:44:49','2018-09-29 10:44:49'),(13,142,NULL,1,'������������1���������',NULL,'BTC',NULL,0.00000000,20.00000000,0.00000000,1.00000000,NULL,'2018-09-29 10:44:49','2018-09-29 10:44:49'),(14,28,'16620855816',1,'������������1���������',NULL,'ETH',NULL,0.00000000,1001.00000000,0.00000000,1.00000000,NULL,'2018-09-29 10:51:43','2018-09-29 10:51:43'),(15,142,NULL,1,'������������1���������',NULL,'ETH',NULL,0.00000000,1001.00000000,0.00000000,1.00000000,NULL,'2018-09-29 10:51:43','2018-09-29 10:51:43'),(16,142,NULL,1,'������������1���������',NULL,'USDT',NULL,0.00000000,1054.00000000,0.00000000,1.00000000,NULL,'2018-09-29 10:54:21','2018-09-29 10:54:21'),(17,138,'18576635213',1,'������������1���������',NULL,'BTC',NULL,0.00000000,10000.00000000,0.20000000,0.80000000,NULL,'2018-09-29 17:58:36','2018-09-30 01:00:00'),(18,138,'18576635213',1,'������������1���������',NULL,'USDT',NULL,0.00000000,10000.00000000,0.20000000,0.80000000,NULL,'2018-09-29 17:58:50','2018-09-30 01:00:00'),(19,138,'18576635213',1,'������������1���������',NULL,'LTC',NULL,0.00000000,10000.00000000,0.20000000,0.80000000,NULL,'2018-09-29 17:59:00','2018-09-30 01:00:00'),(20,138,'18576635213',1,'������������1���������',NULL,'LTC',NULL,0.00000000,10000.00000000,0.20000000,0.80000000,NULL,'2018-09-29 17:59:10','2018-09-30 01:00:00'),(21,138,'18576635213',1,'������������1���������',NULL,'EST',NULL,0.00000000,10000.00000000,0.20000000,0.80000000,NULL,'2018-09-29 17:59:23','2018-09-30 01:00:00'),(22,28,'16620855816',1,'������������1���������',NULL,'TCC',NULL,0.00000000,1.00000000,0.00000000,1.00000000,NULL,'2018-09-30 10:29:06','2018-09-30 10:29:06'),(23,28,'16620855816',1,'������������1���������',NULL,'BTC',NULL,0.00000000,1.00000000,0.00000000,1.00000000,NULL,'2018-09-30 10:29:46','2018-09-30 10:29:46'),(24,28,'16620855816',1,'������������1���������',NULL,'ETH',NULL,0.00000000,2.00000000,0.00000000,1.00000000,NULL,'2018-09-30 10:31:15','2018-09-30 10:31:15'),(25,28,'16620855816',1,'������������1���������',NULL,'DET',NULL,0.00000000,1.00000000,0.00000000,1.00000000,NULL,'2018-09-30 10:46:32','2018-09-30 10:46:32'),(26,28,'16620855816',1,'������������1���������',NULL,'DET',NULL,0.00000000,2000.00000000,0.00000000,1.00000000,NULL,'2018-09-30 10:47:14','2018-09-30 10:47:14'),(27,142,NULL,1,'������������1���������',NULL,'DET',NULL,0.00000000,2000.00000000,0.00000000,1.00000000,NULL,'2018-09-30 10:47:14','2018-09-30 10:47:14'),(28,147,'18823670239',1,'������������1���������',NULL,'DET',NULL,0.00000000,1000.00000000,0.00000000,1.00000000,NULL,'2018-09-30 10:51:24','2018-09-30 10:51:24'),(29,28,'16620855816',1,'������������1���������',NULL,'DG',NULL,0.00000000,2000.00000000,0.00000000,1.00000000,NULL,'2018-10-09 11:10:12','2018-10-09 11:10:12'),(30,32,NULL,2,'���30���������',NULL,'BTC','������',0.00000000,1000.00000000,1.00000000,0.10000000,NULL,'2018-10-15 20:14:41','2018-10-15 20:19:30'),(31,153,'13590469049',1,'������������1���������',NULL,'USDT',NULL,0.00000000,100000000.00000000,0.00000000,1.00000000,NULL,'2018-10-15 21:10:58','2018-10-15 21:10:58'),(32,32,NULL,1,'������������1���������',NULL,'USDT',NULL,0.00000000,10000.00000000,0.00000000,1.00000000,NULL,'2018-10-15 21:20:04','2018-10-15 21:20:04'),(33,176,NULL,2,'���30���������',NULL,'USDT',NULL,0.00000000,1000000.00000000,1.00000000,0.10000000,NULL,'2018-10-15 21:31:17','2018-10-15 21:36:00'),(34,38,'13500000001',2,'���30���������',NULL,'BTC',NULL,0.00000000,100000.00000000,1.00000000,0.10000000,NULL,'2018-10-16 16:03:38','2018-10-16 16:06:00'),(35,38,'13500000001',2,'���30���������',NULL,'ETH',NULL,0.00000000,100000.00000000,1.00000000,0.10000000,NULL,'2018-10-16 16:03:53','2018-10-16 16:06:10'),(36,38,'13500000001',2,'���30���������',NULL,'USDT',NULL,0.00000000,1000000.00000000,1.00000000,0.10000000,NULL,'2018-10-16 16:04:13','2018-10-16 16:06:40'),(37,126,'13500000089',2,'���30���������',NULL,'USDT',NULL,0.00000000,1000.00000000,1.00000000,0.10000000,NULL,'2018-10-18 21:33:23','2018-10-18 21:38:00'),(38,126,'13500000089',2,'���30���������',NULL,'DG',NULL,0.00000000,1000000.00000000,0.10000000,0.90000000,NULL,'2018-10-18 21:38:51','2018-10-18 21:39:00'),(39,126,'13500000089',2,'���30���������',NULL,'ETH',NULL,0.00000000,100000000.00000000,0.10000000,0.90000000,NULL,'2018-10-18 21:39:39','2018-10-18 21:40:00'),(40,182,'18673582692',1,'������������1���������',NULL,'BTC',NULL,0.00000000,1000.00000000,0.00000000,1.00000000,NULL,'2018-11-07 10:38:12','2018-11-07 10:38:12'),(41,182,'18673582692',1,'������������1���������',NULL,'USDT',NULL,0.00000000,1000.00000000,0.00000000,1.00000000,NULL,'2018-11-07 10:38:31','2018-11-07 10:38:31'),(42,180,'18673582690',1,'������������1���������',NULL,'BTC',NULL,0.00000000,100.00000000,0.00000000,1.00000000,NULL,'2018-11-07 10:39:13','2018-11-07 10:39:13'),(43,180,'18673582690',1,'������������1���������',NULL,'USDT',NULL,0.00000000,100.00000000,0.00000000,1.00000000,NULL,'2018-11-07 10:39:30','2018-11-07 10:39:30'),(44,180,'18673582690',1,'������������1���������',NULL,'USDT',NULL,0.00000000,1000.00000000,0.00000000,1.00000000,NULL,'2018-11-07 10:40:05','2018-11-07 10:40:05'),(45,180,'18673582690',1,'������������1���������',NULL,'USDT',NULL,0.00000000,10000.00000000,0.00000000,1.00000000,NULL,'2018-11-07 10:40:57','2018-11-07 10:40:57'),(46,188,'18673582650',1,'������������1���������',NULL,'BTC',NULL,0.00000000,100.00000000,0.20000000,0.80000000,NULL,'2018-11-07 11:11:45','2018-11-08 01:00:00'),(47,189,'18673582651',1,'������������1���������',NULL,'BTC',NULL,0.00000000,100.00000000,0.20000000,0.80000000,NULL,'2018-11-07 11:11:45','2018-11-08 01:00:00'),(48,190,'18673582652',1,'������������1���������',NULL,'BTC',NULL,0.00000000,100.00000000,0.20000000,0.80000000,NULL,'2018-11-07 11:11:45','2018-11-08 01:00:00'),(49,188,'18673582650',1,'������������1���������',NULL,'USDT',NULL,0.00000000,20000.00000000,0.00000000,1.00000000,NULL,'2018-11-07 11:12:10','2018-11-07 11:12:10'),(50,189,'18673582651',1,'������������1���������',NULL,'USDT',NULL,0.00000000,20000.00000000,0.00000000,1.00000000,NULL,'2018-11-07 11:12:10','2018-11-07 11:12:10'),(51,190,'18673582652',1,'������������1���������',NULL,'USDT',NULL,0.00000000,20000.00000000,0.00000000,1.00000000,NULL,'2018-11-07 11:12:10','2018-11-07 11:12:10'),(52,192,'18673582654',1,'������������1���������',NULL,'USDT',NULL,0.00000000,20000.00000000,0.00000000,1.00000000,NULL,'2018-11-07 12:04:42','2018-11-07 12:04:42'),(53,193,'18673582655',1,'������������1���������',NULL,'USDT',NULL,0.00000000,20000.00000000,0.00000000,1.00000000,NULL,'2018-11-07 12:04:42','2018-11-07 12:04:42'),(54,192,'18673582654',1,'������������1���������',NULL,'BTC',NULL,0.00000000,80.00000000,0.00000000,1.00000000,NULL,'2018-11-07 12:04:51','2018-11-07 12:04:51'),(55,193,'18673582655',1,'������������1���������',NULL,'BTC',NULL,0.00000000,80.00000000,0.00000000,1.00000000,NULL,'2018-11-07 12:04:51','2018-11-07 12:04:51'),(56,194,'18673582656',1,'������������1���������',NULL,'BTC',NULL,0.00000000,80.00000000,0.00000000,1.00000000,NULL,'2018-11-07 14:31:19','2018-11-07 14:31:19'),(57,194,'18673582656',1,'������������1���������',NULL,'USDT',NULL,0.00000000,1000.00000000,0.00000000,1.00000000,NULL,'2018-11-07 14:31:51','2018-11-07 14:31:51'),(58,195,'18673582646',1,'������������1���������',NULL,'BTC',NULL,0.00000000,80.00000000,0.00000000,1.00000000,NULL,'2018-11-07 14:57:37','2018-11-07 14:57:37'),(59,196,'18673582657',1,'������������1���������',NULL,'BTC',NULL,0.00000000,80.00000000,0.00000000,1.00000000,NULL,'2018-11-07 14:57:37','2018-11-07 14:57:37'),(60,197,'18673582648',1,'������������1���������',NULL,'BTC',NULL,0.00000000,80.00000000,0.00000000,1.00000000,NULL,'2018-11-07 14:57:37','2018-11-07 14:57:37'),(61,198,'18673582649',1,'������������1���������',NULL,'BTC',NULL,0.00000000,80.00000000,0.00000000,1.00000000,NULL,'2018-11-07 14:57:37','2018-11-07 14:57:37'),(62,195,'18673582646',1,'������������1���������',NULL,'USDT',NULL,0.00000000,1000.00000000,0.00000000,1.00000000,NULL,'2018-11-07 14:58:00','2018-11-07 14:58:00'),(63,196,'18673582657',1,'������������1���������',NULL,'USDT',NULL,0.00000000,1000.00000000,0.00000000,1.00000000,NULL,'2018-11-07 14:58:00','2018-11-07 14:58:00'),(64,197,'18673582648',1,'������������1���������',NULL,'USDT',NULL,0.00000000,1000.00000000,0.00000000,1.00000000,NULL,'2018-11-07 14:58:00','2018-11-07 14:58:00'),(65,198,'18673582649',1,'������������1���������',NULL,'USDT',NULL,0.00000000,1000.00000000,0.00000000,1.00000000,NULL,'2018-11-07 14:58:00','2018-11-07 14:58:00'),(66,199,'18673582658',1,'������������1���������',NULL,'BTC',NULL,0.00000000,80.00000000,0.00000000,1.00000000,NULL,'2018-11-07 15:16:10','2018-11-07 15:16:10'),(67,199,'18673582658',1,'������������1���������',NULL,'USDT',NULL,0.00000000,10000.00000000,0.00000000,1.00000000,NULL,'2018-11-07 15:16:35','2018-11-07 15:16:35'),(68,157,'15220167142',1,'������������1���������',NULL,'USDT','66',0.00000000,100.00000000,0.00000000,1.00000000,NULL,'2018-11-08 15:57:18','2018-11-08 15:57:18'),(69,202,NULL,2,'���30���������',NULL,'BTC',NULL,0.00000000,1000000.00000000,0.10000000,0.90000000,NULL,'2018-11-09 17:27:38','2018-11-09 17:28:00'),(70,203,NULL,2,'���30���������',NULL,'BTC',NULL,0.00000000,1000000.00000000,0.10000000,0.90000000,NULL,'2018-11-09 17:27:38','2018-11-09 17:28:00'),(71,202,NULL,2,'���30���������',NULL,'USDT',NULL,0.00000000,1000000.00000000,0.10000000,0.90000000,NULL,'2018-11-09 17:27:51','2018-11-09 17:28:00'),(72,203,NULL,2,'���30���������',NULL,'USDT',NULL,0.00000000,1000000.00000000,0.10000000,0.90000000,NULL,'2018-11-09 17:27:51','2018-11-09 17:28:00'),(73,205,'13812345678',1,'������������1���������',NULL,'USDT',NULL,0.00000000,1000000.00000000,0.00000000,1.00000000,NULL,'2018-11-10 10:18:42','2018-11-10 10:18:42'),(74,205,'13812345678',1,'������������1���������',NULL,'BTC',NULL,0.00000000,1000000.00000000,0.00000000,1.00000000,NULL,'2018-11-10 10:19:00','2018-11-10 10:19:00'),(75,204,'18975375666',1,'������������1���������',NULL,'BTC',NULL,0.00000000,100000.00000000,0.00000000,1.00000000,NULL,'2018-11-10 10:33:34','2018-11-10 10:33:34'),(76,206,'18975375667',1,'������������1���������',NULL,'BTC',NULL,0.00000000,100000.00000000,0.00000000,1.00000000,NULL,'2018-11-10 10:33:34','2018-11-10 10:33:34'),(77,207,'18975375668',1,'������������1���������',NULL,'BTC',NULL,0.00000000,100000.00000000,0.00000000,1.00000000,NULL,'2018-11-10 10:33:34','2018-11-10 10:33:34'),(78,204,'18975375666',1,'������������1���������',NULL,'USDT',NULL,0.00000000,1000000.00000000,0.00000000,1.00000000,NULL,'2018-11-10 10:33:45','2018-11-10 10:33:45'),(79,206,'18975375667',1,'������������1���������',NULL,'USDT',NULL,0.00000000,1000000.00000000,0.00000000,1.00000000,NULL,'2018-11-10 10:33:45','2018-11-10 10:33:45'),(80,207,'18975375668',1,'������������1���������',NULL,'USDT',NULL,0.00000000,1000000.00000000,0.00000000,1.00000000,NULL,'2018-11-10 10:33:45','2018-11-10 10:33:45'),(81,209,'13900000011',2,'���30���������',NULL,'YT',NULL,0.00000000,1000000.00000000,1.00000000,0.10000000,NULL,'2018-11-12 09:45:55','2018-11-12 09:50:30'),(82,204,'18975375666',1,'������������1���������',NULL,'PKT',NULL,0.00000000,10000.00000000,0.00000000,1.00000000,NULL,'2018-11-13 19:50:16','2018-11-13 19:50:16'),(83,204,'18975375666',1,'������������1���������',NULL,'YT',NULL,0.00000000,10000.00000000,0.00000000,1.00000000,NULL,'2018-11-13 19:50:27','2018-11-13 19:50:27'),(84,212,'18975375663',1,'������������1���������',NULL,'BTC','1',0.00000000,1.00000000,0.00000000,1.00000000,NULL,'2018-11-14 17:26:45','2018-11-14 17:26:45'),(85,212,'18975375663',1,'������������1���������',NULL,'USDT','112',0.00000000,10000.00000000,0.00000000,1.00000000,NULL,'2018-11-14 17:27:11','2018-11-14 17:27:11'),(86,212,'18975375663',2,'���30���������',NULL,'USDT',NULL,0.00000000,10000.00000000,0.00000000,1.00000000,NULL,'2018-11-15 09:47:07','2018-11-15 09:47:07'),(87,212,'18975375663',2,'���30���������',NULL,'BTC',NULL,0.00000000,10000.00000000,0.00000000,1.00000000,NULL,'2018-11-15 09:48:25','2018-11-15 09:48:25'),(88,136,'13500000099',2,'���30���������',NULL,'BTC',NULL,0.00000000,10000.00000000,0.00000000,1.00000000,NULL,'2018-11-15 09:53:17','2018-11-15 09:53:17'),(89,136,'13500000099',2,'���30���������',NULL,'USDT',NULL,0.00000000,10000.00000000,0.00000000,1.00000000,NULL,'2018-11-15 09:53:32','2018-11-15 09:53:32'),(90,211,'18975375662',2,'���30���������',NULL,'BTC',NULL,0.00000000,10000.00000000,0.00000000,1.00000000,NULL,'2018-11-16 09:25:09','2018-11-16 09:25:09'),(91,38,'13500000001',2,'���30���������',NULL,'PKT',NULL,0.00000000,1000.00000000,0.00000000,1.00000000,NULL,'2018-11-17 16:21:12','2018-11-17 16:21:12'),(92,213,'13877778881',2,'���30���������',NULL,'YT',NULL,0.00000000,10000.00000000,1.00000000,1.00000000,NULL,'2018-11-17 17:58:55','2018-11-17 17:59:00'),(93,214,'13877778882',2,'���30���������',NULL,'YT',NULL,0.00000000,10000.00000000,1.00000000,1.00000000,NULL,'2018-11-17 17:58:55','2018-11-17 17:59:00'),(94,215,'13877778883',2,'���30���������',NULL,'YT',NULL,0.00000000,10000.00000000,1.00000000,1.00000000,NULL,'2018-11-17 17:58:55','2018-11-17 17:59:00'),(95,213,'13877778881',2,'���30���������',NULL,'PKT',NULL,0.00000000,10000.00000000,1.00000000,1.00000000,NULL,'2018-11-17 17:59:29','2018-11-17 17:59:30'),(96,214,'13877778882',2,'���30���������',NULL,'PKT',NULL,0.00000000,10000.00000000,1.00000000,1.00000000,NULL,'2018-11-17 17:59:29','2018-11-17 17:59:30'),(97,215,'13877778883',2,'���30���������',NULL,'PKT',NULL,0.00000000,10000.00000000,1.00000000,1.00000000,NULL,'2018-11-17 17:59:29','2018-11-17 17:59:30'),(98,40,'13500000003',2,'���30���������',NULL,'PKT',NULL,0.00000000,1000000.00000000,1.00000000,1.00000000,NULL,'2018-11-17 18:01:04','2018-11-17 18:01:30'),(99,222,'13200000000',1,'������������1���������',NULL,'YT',NULL,0.00000000,10000.00000000,0.00000000,1.00000000,NULL,'2018-11-18 11:27:17','2018-11-18 11:27:17'),(100,223,'13200000001',1,'������������1���������',NULL,'YT',NULL,0.00000000,10000.00000000,0.00000000,1.00000000,NULL,'2018-11-18 11:27:17','2018-11-18 11:27:17'),(101,224,'13200000002',1,'������������1���������',NULL,'YT',NULL,0.00000000,10000.00000000,0.00000000,1.00000000,NULL,'2018-11-18 11:27:17','2018-11-18 11:27:17'),(102,216,'13100000000',1,'������������1���������',NULL,'YT',NULL,0.00000000,10000.00000000,0.00000000,1.00000000,NULL,'2018-11-18 11:28:16','2018-11-18 11:28:16'),(103,217,'13100000001',1,'������������1���������',NULL,'YT',NULL,0.00000000,10000.00000000,0.00000000,1.00000000,NULL,'2018-11-18 11:28:16','2018-11-18 11:28:16'),(104,218,'15000000001',1,'������������1���������',NULL,'YT',NULL,0.00000000,10000.00000000,0.00000000,1.00000000,NULL,'2018-11-18 11:28:16','2018-11-18 11:28:16'),(105,219,'13100000002',1,'������������1���������',NULL,'YT',NULL,0.00000000,10000.00000000,0.00000000,1.00000000,NULL,'2018-11-18 11:28:16','2018-11-18 11:28:16'),(106,220,'15000000002',1,'������������1���������',NULL,'YT',NULL,0.00000000,10000.00000000,0.00000000,1.00000000,NULL,'2018-11-18 11:28:16','2018-11-18 11:28:16'),(107,221,'15000000003',1,'������������1���������',NULL,'YT',NULL,0.00000000,10000.00000000,0.00000000,1.00000000,NULL,'2018-11-18 11:28:16','2018-11-18 11:28:16'),(108,225,'13300000000',1,'������������1���������',NULL,'YT',NULL,0.00000000,10000.00000000,0.00000000,1.00000000,NULL,'2018-11-18 11:28:16','2018-11-18 11:28:16'),(109,226,'13300000001',1,'������������1���������',NULL,'YT',NULL,0.00000000,10000.00000000,0.00000000,1.00000000,NULL,'2018-11-18 11:28:16','2018-11-18 11:28:16'),(110,227,'13300000002',1,'������������1���������',NULL,'YT',NULL,0.00000000,10000.00000000,0.00000000,1.00000000,NULL,'2018-11-18 11:28:16','2018-11-18 11:28:16'),(111,216,'13100000000',1,'������������1���������',NULL,'PKT',NULL,0.00000000,10000.00000000,0.00000000,1.00000000,NULL,'2018-11-18 11:28:55','2018-11-18 11:28:55'),(112,217,'13100000001',1,'������������1���������',NULL,'PKT',NULL,0.00000000,10000.00000000,0.00000000,1.00000000,NULL,'2018-11-18 11:28:55','2018-11-18 11:28:55'),(113,218,'15000000001',1,'������������1���������',NULL,'PKT',NULL,0.00000000,10000.00000000,0.00000000,1.00000000,NULL,'2018-11-18 11:28:55','2018-11-18 11:28:55'),(114,219,'13100000002',1,'������������1���������',NULL,'PKT',NULL,0.00000000,10000.00000000,0.00000000,1.00000000,NULL,'2018-11-18 11:28:55','2018-11-18 11:28:55'),(115,220,'15000000002',1,'������������1���������',NULL,'PKT',NULL,0.00000000,10000.00000000,0.00000000,1.00000000,NULL,'2018-11-18 11:28:55','2018-11-18 11:28:55'),(116,221,'15000000003',1,'������������1���������',NULL,'PKT',NULL,0.00000000,10000.00000000,0.00000000,1.00000000,NULL,'2018-11-18 11:28:55','2018-11-18 11:28:55'),(117,222,'13200000000',1,'������������1���������',NULL,'PKT',NULL,0.00000000,10000.00000000,0.00000000,1.00000000,NULL,'2018-11-18 11:28:55','2018-11-18 11:28:55'),(118,223,'13200000001',1,'������������1���������',NULL,'PKT',NULL,0.00000000,10000.00000000,0.00000000,1.00000000,NULL,'2018-11-18 11:28:55','2018-11-18 11:28:55'),(119,224,'13200000002',1,'������������1���������',NULL,'PKT',NULL,0.00000000,10000.00000000,0.00000000,1.00000000,NULL,'2018-11-18 11:28:55','2018-11-18 11:28:55'),(120,225,'13300000000',1,'������������1���������',NULL,'PKT',NULL,0.00000000,10000.00000000,0.00000000,1.00000000,NULL,'2018-11-18 11:28:55','2018-11-18 11:28:55'),(121,226,'13300000001',1,'������������1���������',NULL,'PKT',NULL,0.00000000,10000.00000000,0.00000000,1.00000000,NULL,'2018-11-18 11:28:55','2018-11-18 11:28:55'),(122,227,'13300000002',1,'������������1���������',NULL,'PKT',NULL,0.00000000,10000.00000000,0.00000000,1.00000000,NULL,'2018-11-18 11:28:55','2018-11-18 11:28:55'),(123,228,'13100000005',1,'������������1���������',NULL,'YT',NULL,0.00000000,10000.00000000,0.00000000,1.00000000,NULL,'2018-11-18 12:28:34','2018-11-18 12:28:34'),(124,229,'13100000006',1,'������������1���������',NULL,'YT',NULL,0.00000000,10000.00000000,0.00000000,1.00000000,NULL,'2018-11-18 12:28:34','2018-11-18 12:28:34'),(125,228,'13100000005',1,'������������1���������',NULL,'PKT',NULL,0.00000000,10000.00000000,0.00000000,1.00000000,NULL,'2018-11-18 12:28:44','2018-11-18 12:28:44'),(126,229,'13100000006',1,'������������1���������',NULL,'PKT',NULL,0.00000000,10000.00000000,0.00000000,1.00000000,NULL,'2018-11-18 12:28:44','2018-11-18 12:28:44'),(127,238,'13813800003',2,'���30���������',NULL,'BTC',NULL,0.00000000,100.00000000,0.00000000,1.00000000,NULL,'2018-11-21 11:03:03','2018-11-21 11:03:03'),(128,239,'13813800004',2,'���30���������',NULL,'USDT',NULL,0.00000000,1000.00000000,1.00000000,0.10000000,NULL,'2018-11-21 11:13:39','2018-11-21 11:18:30'),(129,239,'13813800004',2,'���30���������',NULL,'USDT',NULL,0.00000000,100000.00000000,0.00000000,1.00000000,NULL,'2018-11-21 11:15:21','2018-11-21 11:15:21'),(130,239,'13813800004',2,'���30���������',NULL,'USDT',NULL,0.00000000,9000.00000000,0.00000000,1.00000000,NULL,'2018-11-21 11:19:19','2018-11-21 11:19:19'),(131,239,'13813800004',2,'���30���������',NULL,'USDT',NULL,0.00000000,1000000.00000000,0.00000000,1.00000000,NULL,'2018-11-21 11:20:14','2018-11-21 11:20:14'),(132,239,'13813800004',2,'���30���������',NULL,'USDT',NULL,0.00000000,990000.00000000,0.00000000,1.00000000,NULL,'2018-11-21 11:20:43','2018-11-21 11:20:43'),(133,239,'13813800004',2,'���30���������',NULL,'BTC',NULL,0.00000000,101.00000000,0.00000000,1.00000000,NULL,'2018-11-21 11:23:33','2018-11-21 11:23:33'),(134,240,'13813800005',2,'���30���������',NULL,'USDT',NULL,0.00000000,1.00000000,0.00000000,1.00000000,NULL,'2018-11-21 13:24:30','2018-11-21 13:24:30'),(135,236,'13813800001',2,'���30���������',NULL,'USDT',NULL,0.00000000,2000.00000000,0.00000000,1.00000000,NULL,'2018-11-21 13:39:25','2018-11-21 13:39:25'),(136,236,'13813800001',2,'���30���������',NULL,'BTC',NULL,0.00000000,100.00000000,0.00000000,1.00000000,NULL,'2018-11-21 13:47:44','2018-11-21 13:47:44'),(137,240,'13813800005',2,'���30���������',NULL,'USDT',NULL,0.00000000,5000.00000000,0.00000000,1.00000000,NULL,'2018-11-21 13:49:01','2018-11-21 13:49:01');
/*!40000 ALTER TABLE `t_dispatch_job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_dispatch_log`
--

DROP TABLE IF EXISTS `t_dispatch_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_dispatch_log` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '������ID',
  `mobile` varchar(128) DEFAULT NULL COMMENT '���������',
  `coin_name` varchar(10) DEFAULT NULL COMMENT '������',
  `amount` decimal(32,8) DEFAULT NULL COMMENT '������',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=277 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_dispatch_log`
--

LOCK TABLES `t_dispatch_log` WRITE;
/*!40000 ALTER TABLE `t_dispatch_log` DISABLE KEYS */;
INSERT INTO `t_dispatch_log` VALUES (1,138,'18576635213','TCC',0.00000001,'2018-09-05 15:39:13','2018-09-05 15:39:13'),(2,152,'13900000001','YT',0.00000000,'2018-09-12 14:13:12','2018-09-12 14:13:12'),(3,152,'13900000001','YT',1000000.00000000,'2018-09-12 14:13:30','2018-09-12 14:13:30'),(4,152,'13900000001','YT',1000000.00000000,'2018-09-12 14:14:00','2018-09-12 14:14:00'),(5,152,'13900000001','YT',1000000.00000000,'2018-09-12 14:14:30','2018-09-12 14:14:30'),(6,152,'13900000001','YT',1000000.00000000,'2018-09-12 14:15:00','2018-09-12 14:15:00'),(7,152,'13900000001','YT',1000000.00000000,'2018-09-12 14:15:30','2018-09-12 14:15:30'),(8,152,'13900000001','YT',1000000.00000000,'2018-09-12 14:16:00','2018-09-12 14:16:00'),(9,152,'13900000001','YT',1000000.00000000,'2018-09-12 14:16:30','2018-09-12 14:16:30'),(10,152,'13900000001','YT',1000000.00000000,'2018-09-12 14:17:00','2018-09-12 14:17:00'),(11,152,'13900000001','YT',1000000.00000000,'2018-09-12 14:17:30','2018-09-12 14:17:30'),(12,152,'13900000001','YT',1000000.00000000,'2018-09-12 14:18:00','2018-09-12 14:18:00'),(13,151,'18576635555','BTC',0.80000000,'2018-09-12 15:10:24','2018-09-12 15:10:24'),(14,152,'13900000001','BTC',0.80000000,'2018-09-12 15:10:24','2018-09-12 15:10:24'),(15,142,NULL,'USDT',0.80000000,'2018-09-12 15:24:44','2018-09-12 15:24:44'),(16,28,'16620855816','USDT',2000.00000000,'2018-09-12 17:04:41','2018-09-12 17:04:41'),(17,151,'18576635555','BTC',0.20000000,'2018-09-13 01:00:00','2018-09-13 01:00:00'),(18,152,'13900000001','BTC',0.20000000,'2018-09-13 01:00:00','2018-09-13 01:00:00'),(19,142,NULL,'USDT',0.20000000,'2018-09-13 01:00:00','2018-09-13 01:00:00'),(20,153,'13590469049','YT',0.00000000,'2018-09-13 11:43:30','2018-09-13 11:43:30'),(21,153,'13590469049','YT',100000.00000000,'2018-09-13 11:44:00','2018-09-13 11:44:00'),(22,153,'13590469049','YT',100000.00000000,'2018-09-13 11:44:30','2018-09-13 11:44:30'),(23,153,'13590469049','YT',100000.00000000,'2018-09-13 11:45:00','2018-09-13 11:45:00'),(24,153,'13590469049','YT',100000.00000000,'2018-09-13 11:45:30','2018-09-13 11:45:30'),(25,153,'13590469049','YT',100000.00000000,'2018-09-13 11:46:00','2018-09-13 11:46:00'),(26,153,'13590469049','YT',100000.00000000,'2018-09-13 11:46:30','2018-09-13 11:46:30'),(27,153,'13590469049','YT',100000.00000000,'2018-09-13 11:47:00','2018-09-13 11:47:00'),(28,153,'13590469049','YT',100000.00000000,'2018-09-13 11:47:30','2018-09-13 11:47:30'),(29,153,'13590469049','YT',100000.00000000,'2018-09-13 11:48:00','2018-09-13 11:48:00'),(30,153,'13590469049','YT',100000.00000000,'2018-09-13 11:48:30','2018-09-13 11:48:30'),(31,30,NULL,'USDT',0.00000000,'2018-09-17 15:02:20','2018-09-17 15:02:20'),(32,30,NULL,'USDT',10.00000000,'2018-09-17 15:02:30','2018-09-17 15:02:30'),(33,30,NULL,'USDT',10.00000000,'2018-09-17 15:03:00','2018-09-17 15:03:00'),(34,30,NULL,'USDT',10.00000000,'2018-09-17 15:03:30','2018-09-17 15:03:30'),(35,30,NULL,'USDT',10.00000000,'2018-09-17 15:04:00','2018-09-17 15:04:00'),(36,30,NULL,'USDT',10.00000000,'2018-09-17 15:04:30','2018-09-17 15:04:30'),(37,30,NULL,'USDT',10.00000000,'2018-09-17 15:05:00','2018-09-17 15:05:00'),(38,30,NULL,'USDT',10.00000000,'2018-09-17 15:05:30','2018-09-17 15:05:30'),(39,30,NULL,'USDT',10.00000000,'2018-09-17 15:06:00','2018-09-17 15:06:00'),(40,30,NULL,'USDT',10.00000000,'2018-09-17 15:06:30','2018-09-17 15:06:30'),(41,30,NULL,'USDT',10.00000000,'2018-09-17 15:07:00','2018-09-17 15:07:00'),(42,19,'18612341234','BTC',2000.00000000,'2018-09-19 10:40:25','2018-09-19 10:40:25'),(43,19,'18612341234','ETH',2000.00000000,'2018-09-19 10:40:33','2018-09-19 10:40:33'),(44,19,'18612341234','USDT',2000.00000000,'2018-09-19 10:40:50','2018-09-19 10:40:50'),(45,28,'16620855816','BTC',20.00000000,'2018-09-29 10:44:49','2018-09-29 10:44:49'),(46,142,NULL,'BTC',20.00000000,'2018-09-29 10:44:49','2018-09-29 10:44:49'),(47,28,'16620855816','ETH',1001.00000000,'2018-09-29 10:51:43','2018-09-29 10:51:43'),(48,142,NULL,'ETH',1001.00000000,'2018-09-29 10:51:43','2018-09-29 10:51:43'),(49,142,NULL,'USDT',1054.00000000,'2018-09-29 10:54:21','2018-09-29 10:54:21'),(50,138,'18576635213','BTC',8000.00000000,'2018-09-29 17:58:36','2018-09-29 17:58:36'),(51,138,'18576635213','USDT',8000.00000000,'2018-09-29 17:58:50','2018-09-29 17:58:50'),(52,138,'18576635213','LTC',8000.00000000,'2018-09-29 17:59:00','2018-09-29 17:59:00'),(53,138,'18576635213','LTC',8000.00000000,'2018-09-29 17:59:10','2018-09-29 17:59:10'),(54,138,'18576635213','EST',8000.00000000,'2018-09-29 17:59:23','2018-09-29 17:59:23'),(55,142,NULL,'BTC',-10.00000000,'2018-09-29 18:22:06','2018-09-29 18:22:06'),(56,138,'18576635213','BTC',2000.00000000,'2018-09-30 01:00:00','2018-09-30 01:00:00'),(57,138,'18576635213','USDT',2000.00000000,'2018-09-30 01:00:00','2018-09-30 01:00:00'),(58,138,'18576635213','LTC',2000.00000000,'2018-09-30 01:00:00','2018-09-30 01:00:00'),(59,138,'18576635213','LTC',2000.00000000,'2018-09-30 01:00:00','2018-09-30 01:00:00'),(60,138,'18576635213','EST',2000.00000000,'2018-09-30 01:00:00','2018-09-30 01:00:00'),(61,28,'16620855816','TCC',1.00000000,'2018-09-30 10:29:06','2018-09-30 10:29:06'),(62,28,'16620855816','BTC',1.00000000,'2018-09-30 10:29:46','2018-09-30 10:29:46'),(63,28,'16620855816','ETH',2.00000000,'2018-09-30 10:31:15','2018-09-30 10:31:15'),(64,28,'16620855816','DET',1.00000000,'2018-09-30 10:46:32','2018-09-30 10:46:32'),(65,28,'16620855816','DET',2000.00000000,'2018-09-30 10:47:14','2018-09-30 10:47:14'),(66,142,NULL,'DET',2000.00000000,'2018-09-30 10:47:14','2018-09-30 10:47:14'),(67,147,'18823670239','DET',1000.00000000,'2018-09-30 10:51:24','2018-09-30 10:51:24'),(68,28,'16620855816','DG',2000.00000000,'2018-10-09 11:10:12','2018-10-09 11:10:12'),(69,32,NULL,'BTC',0.00000000,'2018-10-15 20:14:41','2018-10-15 20:14:41'),(70,32,NULL,'BTC',100.00000000,'2018-10-15 20:15:00','2018-10-15 20:15:00'),(71,32,NULL,'BTC',100.00000000,'2018-10-15 20:15:30','2018-10-15 20:15:30'),(72,32,NULL,'BTC',100.00000000,'2018-10-15 20:16:00','2018-10-15 20:16:00'),(73,32,NULL,'BTC',100.00000000,'2018-10-15 20:16:30','2018-10-15 20:16:30'),(74,32,NULL,'BTC',100.00000000,'2018-10-15 20:17:00','2018-10-15 20:17:00'),(75,32,NULL,'BTC',100.00000000,'2018-10-15 20:17:30','2018-10-15 20:17:30'),(76,32,NULL,'BTC',100.00000000,'2018-10-15 20:18:00','2018-10-15 20:18:00'),(77,32,NULL,'BTC',100.00000000,'2018-10-15 20:18:30','2018-10-15 20:18:30'),(78,32,NULL,'BTC',100.00000000,'2018-10-15 20:19:00','2018-10-15 20:19:00'),(79,32,NULL,'BTC',100.00000000,'2018-10-15 20:19:30','2018-10-15 20:19:30'),(80,153,'13590469049','USDT',100000000.00000000,'2018-10-15 21:10:58','2018-10-15 21:10:58'),(81,32,NULL,'USDT',10000.00000000,'2018-10-15 21:20:04','2018-10-15 21:20:04'),(82,176,NULL,'USDT',0.00000000,'2018-10-15 21:31:17','2018-10-15 21:31:17'),(83,176,NULL,'USDT',100000.00000000,'2018-10-15 21:31:30','2018-10-15 21:31:30'),(84,176,NULL,'USDT',100000.00000000,'2018-10-15 21:32:00','2018-10-15 21:32:00'),(85,176,NULL,'USDT',100000.00000000,'2018-10-15 21:32:30','2018-10-15 21:32:30'),(86,176,NULL,'USDT',100000.00000000,'2018-10-15 21:33:00','2018-10-15 21:33:00'),(87,176,NULL,'USDT',100000.00000000,'2018-10-15 21:33:30','2018-10-15 21:33:30'),(88,176,NULL,'USDT',100000.00000000,'2018-10-15 21:34:00','2018-10-15 21:34:00'),(89,176,NULL,'USDT',100000.00000000,'2018-10-15 21:34:30','2018-10-15 21:34:30'),(90,176,NULL,'USDT',100000.00000000,'2018-10-15 21:35:00','2018-10-15 21:35:00'),(91,176,NULL,'USDT',100000.00000000,'2018-10-15 21:35:30','2018-10-15 21:35:30'),(92,176,NULL,'USDT',100000.00000000,'2018-10-15 21:36:00','2018-10-15 21:36:00'),(93,38,'13500000001','BTC',0.00000000,'2018-10-16 16:03:38','2018-10-16 16:03:38'),(94,38,'13500000001','BTC',10000.00000000,'2018-10-16 16:03:40','2018-10-16 16:03:40'),(95,38,'13500000001','ETH',0.00000000,'2018-10-16 16:03:53','2018-10-16 16:03:53'),(96,38,'13500000001','BTC',10000.00000000,'2018-10-16 16:04:00','2018-10-16 16:04:00'),(97,38,'13500000001','ETH',10000.00000000,'2018-10-16 16:04:00','2018-10-16 16:04:00'),(98,38,'13500000001','BTC',10000.00000000,'2018-10-16 16:04:10','2018-10-16 16:04:10'),(99,38,'13500000001','ETH',10000.00000000,'2018-10-16 16:04:10','2018-10-16 16:04:10'),(100,38,'13500000001','USDT',0.00000000,'2018-10-16 16:04:13','2018-10-16 16:04:13'),(101,38,'13500000001','BTC',10000.00000000,'2018-10-16 16:04:30','2018-10-16 16:04:30'),(102,38,'13500000001','ETH',10000.00000000,'2018-10-16 16:04:30','2018-10-16 16:04:30'),(103,38,'13500000001','USDT',100000.00000000,'2018-10-16 16:04:30','2018-10-16 16:04:30'),(104,38,'13500000001','BTC',10000.00000000,'2018-10-16 16:04:40','2018-10-16 16:04:40'),(105,38,'13500000001','ETH',10000.00000000,'2018-10-16 16:04:40','2018-10-16 16:04:40'),(106,38,'13500000001','USDT',100000.00000000,'2018-10-16 16:04:40','2018-10-16 16:04:40'),(107,38,'13500000001','BTC',10000.00000000,'2018-10-16 16:05:00','2018-10-16 16:05:00'),(108,38,'13500000001','ETH',10000.00000000,'2018-10-16 16:05:00','2018-10-16 16:05:00'),(109,38,'13500000001','USDT',100000.00000000,'2018-10-16 16:05:00','2018-10-16 16:05:00'),(110,38,'13500000001','BTC',10000.00000000,'2018-10-16 16:05:10','2018-10-16 16:05:10'),(111,38,'13500000001','ETH',10000.00000000,'2018-10-16 16:05:10','2018-10-16 16:05:10'),(112,38,'13500000001','USDT',100000.00000000,'2018-10-16 16:05:10','2018-10-16 16:05:10'),(113,38,'13500000001','BTC',10000.00000000,'2018-10-16 16:05:30','2018-10-16 16:05:30'),(114,38,'13500000001','ETH',10000.00000000,'2018-10-16 16:05:30','2018-10-16 16:05:30'),(115,38,'13500000001','USDT',100000.00000000,'2018-10-16 16:05:30','2018-10-16 16:05:30'),(116,38,'13500000001','BTC',10000.00000000,'2018-10-16 16:05:40','2018-10-16 16:05:40'),(117,38,'13500000001','ETH',10000.00000000,'2018-10-16 16:05:40','2018-10-16 16:05:40'),(118,38,'13500000001','USDT',100000.00000000,'2018-10-16 16:05:40','2018-10-16 16:05:40'),(119,38,'13500000001','BTC',10000.00000000,'2018-10-16 16:06:00','2018-10-16 16:06:00'),(120,38,'13500000001','ETH',10000.00000000,'2018-10-16 16:06:00','2018-10-16 16:06:00'),(121,38,'13500000001','USDT',100000.00000000,'2018-10-16 16:06:00','2018-10-16 16:06:00'),(122,38,'13500000001','ETH',10000.00000000,'2018-10-16 16:06:10','2018-10-16 16:06:10'),(123,38,'13500000001','USDT',100000.00000000,'2018-10-16 16:06:10','2018-10-16 16:06:10'),(124,38,'13500000001','USDT',100000.00000000,'2018-10-16 16:06:30','2018-10-16 16:06:30'),(125,38,'13500000001','USDT',100000.00000000,'2018-10-16 16:06:40','2018-10-16 16:06:40'),(126,126,'13500000089','USDT',0.00000000,'2018-10-18 21:33:23','2018-10-18 21:33:23'),(127,126,'13500000089','USDT',100.00000000,'2018-10-18 21:33:30','2018-10-18 21:33:30'),(128,126,'13500000089','USDT',100.00000000,'2018-10-18 21:34:00','2018-10-18 21:34:00'),(129,126,'13500000089','USDT',100.00000000,'2018-10-18 21:34:30','2018-10-18 21:34:30'),(130,126,'13500000089','USDT',100.00000000,'2018-10-18 21:35:00','2018-10-18 21:35:00'),(131,126,'13500000089','USDT',100.00000000,'2018-10-18 21:35:30','2018-10-18 21:35:30'),(132,126,'13500000089','USDT',100.00000000,'2018-10-18 21:36:00','2018-10-18 21:36:00'),(133,126,'13500000089','USDT',100.00000000,'2018-10-18 21:36:30','2018-10-18 21:36:30'),(134,126,'13500000089','USDT',100.00000000,'2018-10-18 21:37:00','2018-10-18 21:37:00'),(135,126,'13500000089','USDT',100.00000000,'2018-10-18 21:37:30','2018-10-18 21:37:30'),(136,126,'13500000089','USDT',100.00000000,'2018-10-18 21:38:00','2018-10-18 21:38:00'),(137,126,'13500000089','DG',900000.00000000,'2018-10-18 21:38:51','2018-10-18 21:38:51'),(138,126,'13500000089','DG',100000.00000000,'2018-10-18 21:39:00','2018-10-18 21:39:00'),(139,126,'13500000089','ETH',90000000.00000000,'2018-10-18 21:39:39','2018-10-18 21:39:39'),(140,126,'13500000089','ETH',10000000.00000000,'2018-10-18 21:40:00','2018-10-18 21:40:00'),(141,126,'13500000089','ETH',-45000000.00000000,'2018-10-18 21:40:51','2018-10-18 21:40:51'),(142,126,'13500000089','ETH',-45000000.00000000,'2018-10-18 21:41:24','2018-10-18 21:41:24'),(143,182,'18673582692','BTC',1000.00000000,'2018-11-07 10:38:12','2018-11-07 10:38:12'),(144,182,'18673582692','USDT',1000.00000000,'2018-11-07 10:38:31','2018-11-07 10:38:31'),(145,180,'18673582690','BTC',100.00000000,'2018-11-07 10:39:13','2018-11-07 10:39:13'),(146,180,'18673582690','USDT',100.00000000,'2018-11-07 10:39:30','2018-11-07 10:39:30'),(147,180,'18673582690','USDT',1000.00000000,'2018-11-07 10:40:05','2018-11-07 10:40:05'),(148,180,'18673582690','USDT',10000.00000000,'2018-11-07 10:40:57','2018-11-07 10:40:57'),(149,188,'18673582650','BTC',80.00000000,'2018-11-07 11:11:45','2018-11-07 11:11:45'),(150,189,'18673582651','BTC',80.00000000,'2018-11-07 11:11:45','2018-11-07 11:11:45'),(151,190,'18673582652','BTC',80.00000000,'2018-11-07 11:11:45','2018-11-07 11:11:45'),(152,188,'18673582650','USDT',20000.00000000,'2018-11-07 11:12:10','2018-11-07 11:12:10'),(153,189,'18673582651','USDT',20000.00000000,'2018-11-07 11:12:10','2018-11-07 11:12:10'),(154,190,'18673582652','USDT',20000.00000000,'2018-11-07 11:12:10','2018-11-07 11:12:10'),(155,192,'18673582654','USDT',20000.00000000,'2018-11-07 12:04:42','2018-11-07 12:04:42'),(156,193,'18673582655','USDT',20000.00000000,'2018-11-07 12:04:42','2018-11-07 12:04:42'),(157,192,'18673582654','BTC',80.00000000,'2018-11-07 12:04:51','2018-11-07 12:04:51'),(158,193,'18673582655','BTC',80.00000000,'2018-11-07 12:04:51','2018-11-07 12:04:51'),(159,194,'18673582656','BTC',80.00000000,'2018-11-07 14:31:19','2018-11-07 14:31:19'),(160,194,'18673582656','USDT',1000.00000000,'2018-11-07 14:31:51','2018-11-07 14:31:51'),(161,195,'18673582646','BTC',80.00000000,'2018-11-07 14:57:37','2018-11-07 14:57:37'),(162,196,'18673582657','BTC',80.00000000,'2018-11-07 14:57:37','2018-11-07 14:57:37'),(163,197,'18673582648','BTC',80.00000000,'2018-11-07 14:57:37','2018-11-07 14:57:37'),(164,198,'18673582649','BTC',80.00000000,'2018-11-07 14:57:37','2018-11-07 14:57:37'),(165,195,'18673582646','USDT',1000.00000000,'2018-11-07 14:58:00','2018-11-07 14:58:00'),(166,196,'18673582657','USDT',1000.00000000,'2018-11-07 14:58:00','2018-11-07 14:58:00'),(167,197,'18673582648','USDT',1000.00000000,'2018-11-07 14:58:00','2018-11-07 14:58:00'),(168,198,'18673582649','USDT',1000.00000000,'2018-11-07 14:58:00','2018-11-07 14:58:00'),(169,199,'18673582658','BTC',80.00000000,'2018-11-07 15:16:10','2018-11-07 15:16:10'),(170,199,'18673582658','USDT',10000.00000000,'2018-11-07 15:16:35','2018-11-07 15:16:35'),(171,188,'18673582650','BTC',20.00000000,'2018-11-08 01:00:00','2018-11-08 01:00:00'),(172,189,'18673582651','BTC',20.00000000,'2018-11-08 01:00:00','2018-11-08 01:00:00'),(173,190,'18673582652','BTC',20.00000000,'2018-11-08 01:00:00','2018-11-08 01:00:00'),(174,157,'15220167142','USDT',100.00000000,'2018-11-08 15:57:18','2018-11-08 15:57:18'),(175,202,NULL,'BTC',900000.00000000,'2018-11-09 17:27:38','2018-11-09 17:27:38'),(176,203,NULL,'BTC',900000.00000000,'2018-11-09 17:27:38','2018-11-09 17:27:38'),(177,202,NULL,'USDT',900000.00000000,'2018-11-09 17:27:51','2018-11-09 17:27:51'),(178,203,NULL,'USDT',900000.00000000,'2018-11-09 17:27:51','2018-11-09 17:27:51'),(179,202,NULL,'BTC',100000.00000000,'2018-11-09 17:28:00','2018-11-09 17:28:00'),(180,203,NULL,'BTC',100000.00000000,'2018-11-09 17:28:00','2018-11-09 17:28:00'),(181,202,NULL,'USDT',100000.00000000,'2018-11-09 17:28:00','2018-11-09 17:28:00'),(182,203,NULL,'USDT',100000.00000000,'2018-11-09 17:28:00','2018-11-09 17:28:00'),(183,205,'13812345678','USDT',1000000.00000000,'2018-11-10 10:18:42','2018-11-10 10:18:42'),(184,205,'13812345678','BTC',1000000.00000000,'2018-11-10 10:19:00','2018-11-10 10:19:00'),(185,204,'18975375666','BTC',100000.00000000,'2018-11-10 10:33:34','2018-11-10 10:33:34'),(186,206,'18975375667','BTC',100000.00000000,'2018-11-10 10:33:34','2018-11-10 10:33:34'),(187,207,'18975375668','BTC',100000.00000000,'2018-11-10 10:33:34','2018-11-10 10:33:34'),(188,204,'18975375666','USDT',1000000.00000000,'2018-11-10 10:33:45','2018-11-10 10:33:45'),(189,206,'18975375667','USDT',1000000.00000000,'2018-11-10 10:33:45','2018-11-10 10:33:45'),(190,207,'18975375668','USDT',1000000.00000000,'2018-11-10 10:33:45','2018-11-10 10:33:45'),(191,209,'13900000011','YT',0.00000000,'2018-11-12 09:45:55','2018-11-12 09:45:55'),(192,209,'13900000011','YT',100000.00000000,'2018-11-12 09:46:00','2018-11-12 09:46:00'),(193,209,'13900000011','YT',100000.00000000,'2018-11-12 09:46:30','2018-11-12 09:46:30'),(194,209,'13900000011','YT',100000.00000000,'2018-11-12 09:47:00','2018-11-12 09:47:00'),(195,209,'13900000011','YT',100000.00000000,'2018-11-12 09:47:30','2018-11-12 09:47:30'),(196,209,'13900000011','YT',100000.00000000,'2018-11-12 09:48:00','2018-11-12 09:48:00'),(197,209,'13900000011','YT',100000.00000000,'2018-11-12 09:48:30','2018-11-12 09:48:30'),(198,209,'13900000011','YT',100000.00000000,'2018-11-12 09:49:00','2018-11-12 09:49:00'),(199,209,'13900000011','YT',100000.00000000,'2018-11-12 09:49:30','2018-11-12 09:49:30'),(200,209,'13900000011','YT',100000.00000000,'2018-11-12 09:50:00','2018-11-12 09:50:00'),(201,209,'13900000011','YT',100000.00000000,'2018-11-12 09:50:30','2018-11-12 09:50:30'),(202,204,'18975375666','PKT',10000.00000000,'2018-11-13 19:50:16','2018-11-13 19:50:16'),(203,204,'18975375666','YT',10000.00000000,'2018-11-13 19:50:27','2018-11-13 19:50:27'),(204,212,'18975375663','BTC',1.00000000,'2018-11-14 17:26:45','2018-11-14 17:26:45'),(205,212,'18975375663','USDT',10000.00000000,'2018-11-14 17:27:11','2018-11-14 17:27:11'),(206,212,'18975375663','USDT',10000.00000000,'2018-11-15 09:47:07','2018-11-15 09:47:07'),(207,212,'18975375663','BTC',10000.00000000,'2018-11-15 09:48:25','2018-11-15 09:48:25'),(208,136,'13500000099','BTC',10000.00000000,'2018-11-15 09:53:17','2018-11-15 09:53:17'),(209,136,'13500000099','USDT',10000.00000000,'2018-11-15 09:53:32','2018-11-15 09:53:32'),(210,211,'18975375662','BTC',10000.00000000,'2018-11-16 09:25:09','2018-11-16 09:25:09'),(211,38,'13500000001','PKT',1000.00000000,'2018-11-17 16:21:12','2018-11-17 16:21:12'),(212,213,'13877778881','YT',0.00000000,'2018-11-17 17:58:55','2018-11-17 17:58:55'),(213,214,'13877778882','YT',0.00000000,'2018-11-17 17:58:55','2018-11-17 17:58:55'),(214,215,'13877778883','YT',0.00000000,'2018-11-17 17:58:55','2018-11-17 17:58:55'),(215,213,'13877778881','YT',10000.00000000,'2018-11-17 17:59:00','2018-11-17 17:59:00'),(216,214,'13877778882','YT',10000.00000000,'2018-11-17 17:59:00','2018-11-17 17:59:00'),(217,215,'13877778883','YT',10000.00000000,'2018-11-17 17:59:00','2018-11-17 17:59:00'),(218,213,'13877778881','PKT',0.00000000,'2018-11-17 17:59:29','2018-11-17 17:59:29'),(219,214,'13877778882','PKT',0.00000000,'2018-11-17 17:59:29','2018-11-17 17:59:29'),(220,215,'13877778883','PKT',0.00000000,'2018-11-17 17:59:29','2018-11-17 17:59:29'),(221,213,'13877778881','PKT',10000.00000000,'2018-11-17 17:59:30','2018-11-17 17:59:30'),(222,214,'13877778882','PKT',10000.00000000,'2018-11-17 17:59:30','2018-11-17 17:59:30'),(223,215,'13877778883','PKT',10000.00000000,'2018-11-17 17:59:30','2018-11-17 17:59:30'),(224,40,'13500000003','PKT',0.00000000,'2018-11-17 18:01:04','2018-11-17 18:01:04'),(225,40,'13500000003','PKT',1000000.00000000,'2018-11-17 18:01:30','2018-11-17 18:01:30'),(226,222,'13200000000','YT',10000.00000000,'2018-11-18 11:27:17','2018-11-18 11:27:17'),(227,223,'13200000001','YT',10000.00000000,'2018-11-18 11:27:17','2018-11-18 11:27:17'),(228,224,'13200000002','YT',10000.00000000,'2018-11-18 11:27:17','2018-11-18 11:27:17'),(229,216,'13100000000','YT',10000.00000000,'2018-11-18 11:28:16','2018-11-18 11:28:16'),(230,217,'13100000001','YT',10000.00000000,'2018-11-18 11:28:16','2018-11-18 11:28:16'),(231,218,'15000000001','YT',10000.00000000,'2018-11-18 11:28:16','2018-11-18 11:28:16'),(232,219,'13100000002','YT',10000.00000000,'2018-11-18 11:28:16','2018-11-18 11:28:16'),(233,220,'15000000002','YT',10000.00000000,'2018-11-18 11:28:16','2018-11-18 11:28:16'),(234,221,'15000000003','YT',10000.00000000,'2018-11-18 11:28:16','2018-11-18 11:28:16'),(235,225,'13300000000','YT',10000.00000000,'2018-11-18 11:28:16','2018-11-18 11:28:16'),(236,226,'13300000001','YT',10000.00000000,'2018-11-18 11:28:16','2018-11-18 11:28:16'),(237,227,'13300000002','YT',10000.00000000,'2018-11-18 11:28:16','2018-11-18 11:28:16'),(238,216,'13100000000','PKT',10000.00000000,'2018-11-18 11:28:55','2018-11-18 11:28:55'),(239,217,'13100000001','PKT',10000.00000000,'2018-11-18 11:28:55','2018-11-18 11:28:55'),(240,218,'15000000001','PKT',10000.00000000,'2018-11-18 11:28:55','2018-11-18 11:28:55'),(241,219,'13100000002','PKT',10000.00000000,'2018-11-18 11:28:55','2018-11-18 11:28:55'),(242,220,'15000000002','PKT',10000.00000000,'2018-11-18 11:28:55','2018-11-18 11:28:55'),(243,221,'15000000003','PKT',10000.00000000,'2018-11-18 11:28:55','2018-11-18 11:28:55'),(244,222,'13200000000','PKT',10000.00000000,'2018-11-18 11:28:55','2018-11-18 11:28:55'),(245,223,'13200000001','PKT',10000.00000000,'2018-11-18 11:28:55','2018-11-18 11:28:55'),(246,224,'13200000002','PKT',10000.00000000,'2018-11-18 11:28:55','2018-11-18 11:28:55'),(247,225,'13300000000','PKT',10000.00000000,'2018-11-18 11:28:55','2018-11-18 11:28:55'),(248,226,'13300000001','PKT',10000.00000000,'2018-11-18 11:28:55','2018-11-18 11:28:55'),(249,227,'13300000002','PKT',10000.00000000,'2018-11-18 11:28:55','2018-11-18 11:28:55'),(250,228,'13100000005','YT',10000.00000000,'2018-11-18 12:28:34','2018-11-18 12:28:34'),(251,229,'13100000006','YT',10000.00000000,'2018-11-18 12:28:34','2018-11-18 12:28:34'),(252,228,'13100000005','PKT',10000.00000000,'2018-11-18 12:28:44','2018-11-18 12:28:44'),(253,229,'13100000006','PKT',10000.00000000,'2018-11-18 12:28:44','2018-11-18 12:28:44'),(254,238,'13813800003','BTC',100.00000000,'2018-11-21 11:03:03','2018-11-21 11:03:03'),(255,239,'13813800004','USDT',0.00000000,'2018-11-21 11:13:39','2018-11-21 11:13:39'),(256,239,'13813800004','USDT',100.00000000,'2018-11-21 11:14:00','2018-11-21 11:14:00'),(257,239,'13813800004','USDT',100.00000000,'2018-11-21 11:14:30','2018-11-21 11:14:30'),(258,239,'13813800004','USDT',100.00000000,'2018-11-21 11:15:00','2018-11-21 11:15:00'),(259,239,'13813800004','USDT',100000.00000000,'2018-11-21 11:15:21','2018-11-21 11:15:21'),(260,239,'13813800004','USDT',100.00000000,'2018-11-21 11:15:30','2018-11-21 11:15:30'),(261,239,'13813800004','USDT',100.00000000,'2018-11-21 11:16:00','2018-11-21 11:16:00'),(262,239,'13813800004','USDT',100.00000000,'2018-11-21 11:16:30','2018-11-21 11:16:30'),(263,239,'13813800004','USDT',100.00000000,'2018-11-21 11:17:00','2018-11-21 11:17:00'),(264,239,'13813800004','USDT',100.00000000,'2018-11-21 11:17:30','2018-11-21 11:17:30'),(265,239,'13813800004','USDT',100.00000000,'2018-11-21 11:18:00','2018-11-21 11:18:00'),(266,239,'13813800004','USDT',100.00000000,'2018-11-21 11:18:30','2018-11-21 11:18:30'),(267,239,'13813800004','USDT',9000.00000000,'2018-11-21 11:19:19','2018-11-21 11:19:19'),(268,239,'13813800004','USDT',1000000.00000000,'2018-11-21 11:20:14','2018-11-21 11:20:14'),(269,239,'13813800004','USDT',990000.00000000,'2018-11-21 11:20:43','2018-11-21 11:20:43'),(270,239,'13813800004','USDT',-10000.00000000,'2018-11-21 11:21:14','2018-11-21 11:21:14'),(271,239,'13813800004','USDT',-90000.00000000,'2018-11-21 11:21:47','2018-11-21 11:21:47'),(272,239,'13813800004','BTC',101.00000000,'2018-11-21 11:23:33','2018-11-21 11:23:33'),(273,240,'13813800005','USDT',1.00000000,'2018-11-21 13:24:30','2018-11-21 13:24:30'),(274,236,'13813800001','USDT',2000.00000000,'2018-11-21 13:39:25','2018-11-21 13:39:25'),(275,236,'13813800001','BTC',100.00000000,'2018-11-21 13:47:44','2018-11-21 13:47:44'),(276,240,'13813800005','USDT',5000.00000000,'2018-11-21 13:49:01','2018-11-21 13:49:01');
/*!40000 ALTER TABLE `t_dispatch_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_etc_address`
--

DROP TABLE IF EXISTS `t_etc_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_etc_address` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '������ID',
  `address` varchar(64) DEFAULT NULL COMMENT '������',
  `password` varchar(32) DEFAULT NULL COMMENT '������������',
  `filename` varchar(128) DEFAULT NULL COMMENT '������������',
  `credentials` text COMMENT '������������',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '������������',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_etc_address`
--

LOCK TABLES `t_etc_address` WRITE;
/*!40000 ALTER TABLE `t_etc_address` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_etc_address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_eth_address`
--

DROP TABLE IF EXISTS `t_eth_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_eth_address` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '������ID',
  `address` varchar(64) DEFAULT NULL COMMENT '������',
  `password` varchar(32) DEFAULT NULL COMMENT '������������',
  `filename` varchar(128) DEFAULT NULL COMMENT '������������',
  `credentials` text COMMENT '������������',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '������������',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_eth_address`
--

LOCK TABLES `t_eth_address` WRITE;
/*!40000 ALTER TABLE `t_eth_address` DISABLE KEYS */;
INSERT INTO `t_eth_address` VALUES (1,6,'0x76288f4222a60db52c2360426d5fe0fa7a1c3582','623994124','UTC--2018-08-27T06-34-24.157000000Z--76288f4222a60db52c2360426d5fe0fa7a1c3582.json','{\"address\":\"76288f4222a60db52c2360426d5fe0fa7a1c3582\",\"id\":\"9b32f6d7-535e-44f0-8a93-fbe2403275c6\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"ad8dc2fc0e8c7191c529a03c554f59a9c43574f97ac49e51c59629176414e2e6\",\"cipherparams\":{\"iv\":\"26f01df5b87b7a1a506a2eb27ab4239f\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"5aba2a7104e9602196d8abe846276f865d0550630b757e8225d1206fb91ae678\"},\"mac\":\"8629512f84b999630115a69f8d85ee0766b1026c2cdf32330154402d6bb9f1f6\"}}','2018-08-27 14:34:25'),(2,7,'0x17dfaad634c4521fa2d9ec3cc4112451a0235857','1249418731','UTC--2018-08-27T06-49-06.533000000Z--17dfaad634c4521fa2d9ec3cc4112451a0235857.json','{\"address\":\"17dfaad634c4521fa2d9ec3cc4112451a0235857\",\"id\":\"2eb5dca6-9028-4f7c-bf99-00c7a39db55f\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"006a62779fe48bac92b78fe99ae2ed2705a5e96f56f6ed692c38528802ba166c\",\"cipherparams\":{\"iv\":\"55fc586576f5d800e478e2a2d7cbf50a\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"da18243bde9086192105a85dcb56504ee6b576b0920e01d9aad64eb7117d366e\"},\"mac\":\"761a0804a326b457564e8a833e30d3484335ad58d2ec2906a6cdfa492b083c85\"}}','2018-08-27 14:49:07'),(3,1,'0x5846a28a084955df5c3b54ddb3a7c68a0b45cd96','1496271170','UTC--2018-08-27T07-43-00.224000000Z--5846a28a084955df5c3b54ddb3a7c68a0b45cd96.json','{\"address\":\"5846a28a084955df5c3b54ddb3a7c68a0b45cd96\",\"id\":\"4a3052b0-dc5f-4663-8e30-0d2dc977465e\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"fe63dc5ca88a3b2e9e7fe7a59473de0b5c82a7953d39ffc22bc99d69e587b45f\",\"cipherparams\":{\"iv\":\"09c8241f0442e955abbd508ed8bc0b8a\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"378d7a18dda90479e836a839a4f4006c634cf8e2d55b950570b764b71a55ac71\"},\"mac\":\"c4c261f22f2f95f4f8f5ed8c69c6589a09e2915e1ae222b7ea519b0ee864d6e8\"}}','2018-08-27 15:43:02'),(5,3,'0x109cc9bbc40f8b163526c2b9aef6798ae9a7e7e1','915903141','UTC--2018-08-27T07-48-13.277000000Z--109cc9bbc40f8b163526c2b9aef6798ae9a7e7e1.json','{\"address\":\"109cc9bbc40f8b163526c2b9aef6798ae9a7e7e1\",\"id\":\"b76addef-0dbe-44e1-99ad-52b9cd733203\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"dd8b9ab39fda3d6565db76685f956cf886c4e650747066df30811a274e3a631f\",\"cipherparams\":{\"iv\":\"0c55d3d19589a9d38677b0baffca2243\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"ce7a62c6c04e63a9acb1564a7a9cbfc7ae257a29940773415d314d18ba6211a8\"},\"mac\":\"a46e375592b0b27c6826b2afa250be7289b35a59baa9821e3d303433db4e7579\"}}','2018-08-27 15:48:14'),(6,19,'0xfd1e13c11f2fe9b23b5221f47cac1e9d3444a147','392297548','UTC--2018-08-28T03-27-41.114000000Z--fd1e13c11f2fe9b23b5221f47cac1e9d3444a147.json','{\"address\":\"fd1e13c11f2fe9b23b5221f47cac1e9d3444a147\",\"id\":\"0a064913-cf17-4f72-8c0e-fb45e8598ff2\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"b9046bb218245b2c72235320d0fc902d3f03ed82eecca6b8a2fcd0f55e8b0b91\",\"cipherparams\":{\"iv\":\"6fe04f9bcfe3866525d5073d114d2cbe\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"e5a1cb58b555791644a33a15f159309d139dab65ff91bf480928217e1a6d4b37\"},\"mac\":\"607a2f230d686dbc7e41e1f85389fd2308df7526ac72e0b002c653a033069ec7\"}}','2018-08-28 11:27:42'),(7,19,'0x398c8c017d3effa5f781f5f2a64a02006f4baef6','1957952100','UTC--2018-08-28T03-27-43.58000000Z--398c8c017d3effa5f781f5f2a64a02006f4baef6.json','{\"address\":\"398c8c017d3effa5f781f5f2a64a02006f4baef6\",\"id\":\"c3978575-3fdc-4a99-8426-9c70a966b3dc\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"59379fdb13de51dac46887aebca8b76f524e2af00077e8b7141c05e0791aa1f2\",\"cipherparams\":{\"iv\":\"5bc893dbe7b93d093bbbb3820a6890bb\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"c95b631e8673b53596a4512f1a8a4b8b88257fb7a5075434df16b7be3bf82e42\"},\"mac\":\"0b35e13bb2e09abdbbad595830c1bd6af821200d8b7a5efd2f1bd85425526e90\"}}','2018-08-28 11:27:44'),(8,17,'0x231db84ffdd69ce8cad5c86593a9ec2ed0d79815','17397898','UTC--2018-08-31T09-48-25.153000000Z--231db84ffdd69ce8cad5c86593a9ec2ed0d79815.json','{\"address\":\"231db84ffdd69ce8cad5c86593a9ec2ed0d79815\",\"id\":\"b5f50701-14a4-4785-8287-4b67f81344e6\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"067e2dba85ae2286df7dbf6ed51b3984298c7e0ac630c0196f25c84acc30a289\",\"cipherparams\":{\"iv\":\"a6f256200655d59b30daddaf0fc10a91\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"88c925c4dc17a69373d84269f21f228f0d00b390989c264de0e8aed920a95dcb\"},\"mac\":\"952d39921accbe9b79dfaccedc0acda64b273bc597e31eaffea8de5c3adcc597\"}}','2018-08-31 17:48:26'),(9,138,'0xd16ad97e697d2bd897aec8ff6234ddd160194dff','575512950','UTC--2018-09-03T08-09-14.890000000Z--d16ad97e697d2bd897aec8ff6234ddd160194dff.json','{\"address\":\"d16ad97e697d2bd897aec8ff6234ddd160194dff\",\"id\":\"5f50ddf3-ecb5-4042-89e0-5eba35b1e7cc\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"8d27137a42e402b8328b3f1a835992420346ab064db167beb8cd54cd2764aa26\",\"cipherparams\":{\"iv\":\"ba8e26adec036cc71eed1a0cfc8073a4\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"4312f9bc8d4f3efc93ea43c03c18417c32dcd4b09725c50887edd0c7a1595490\"},\"mac\":\"0a120455a42cec06e39d90081ff279a08161b001dc16be69036b241bd2c7338f\"}}','2018-09-03 16:09:16'),(10,137,'0x0563008df1998f68fc245ac7cb75239688892ac8','1579396329','UTC--2018-09-04T02-16-28.314000000Z--0563008df1998f68fc245ac7cb75239688892ac8.json','{\"address\":\"0563008df1998f68fc245ac7cb75239688892ac8\",\"id\":\"836bb60b-3f17-46e7-ab0b-74066cda960d\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"abc6d5678a53ff918704a3ee1dd2fa505774b388e69546a3c8e6bda2fc3f4c1b\",\"cipherparams\":{\"iv\":\"92da1124855230614b95b9a781d61320\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"fd0edc122044dad7feff41836837bd6fdb3bb4e4db90af3fe28cff3bd2a6fad6\"},\"mac\":\"ac21993feb32cbc604923627458ff7ae2feab65bde7c7aef6b710f77baf8cc88\"}}','2018-09-04 10:16:31'),(11,137,'0xaef29b6087d3c06b62d989151a46d39a8ea09f18','382466190','UTC--2018-09-04T02-16-28.705000000Z--aef29b6087d3c06b62d989151a46d39a8ea09f18.json','{\"address\":\"aef29b6087d3c06b62d989151a46d39a8ea09f18\",\"id\":\"62bcdd4e-efac-4ba8-8a5a-5f3755f9a0cc\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"135b8a7caac38bc5c14e079b20d2404f0ed2497c6281f288bca26d31bdd49861\",\"cipherparams\":{\"iv\":\"87e87355d99c42bc328d3a372859d078\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"34d970dce598f31a186f8e5b148793ef5a6f14a42e6266cecadf0da47685219b\"},\"mac\":\"a2c6134c927efb5a65cdc0430480a490c526b6f096d6735708b284e66fb32c16\"}}','2018-09-04 10:16:31'),(12,137,'0xac03490b4fb70ba2694b883660cb68d137a6b1fc','1464280668','UTC--2018-09-04T02-16-29.534000000Z--ac03490b4fb70ba2694b883660cb68d137a6b1fc.json','{\"address\":\"ac03490b4fb70ba2694b883660cb68d137a6b1fc\",\"id\":\"045527d6-8ba5-4e19-9b4a-7ba5b72cbda2\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"5fd1b67c1ad4a24f388cc0138eb3164dd1691ce17551ae5ac7fc5000d034d600\",\"cipherparams\":{\"iv\":\"4527252e81e61550f9512ed4c372ecf8\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"1ce848f6c727422ea6e9da2ce1a4f4642bdbc68c5a5dac78cf7f001c851111c0\"},\"mac\":\"a4a40ce907eef0868f804f151ac9697c48525eb952c69843e4b02d3dd4c900af\"}}','2018-09-04 10:16:31'),(13,137,'0xee56535232a54a8918e281671b4a8ebf759f4b34','592885072','UTC--2018-09-04T02-16-29.701000000Z--ee56535232a54a8918e281671b4a8ebf759f4b34.json','{\"address\":\"ee56535232a54a8918e281671b4a8ebf759f4b34\",\"id\":\"c27b8131-30ce-4292-9585-a8cf442ff93a\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"b8090dfa85d9c37087a1397d3dfe7ed7eb0d7d5000e916d2b45fb3e368dbcfc7\",\"cipherparams\":{\"iv\":\"735467c6f929cd154362427af4310f37\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"73859c9ac72c82a40cbddc4d670a003f1f218f1dc30671602cda4d57331d35b6\"},\"mac\":\"7f943760f9ec83fbf4682438fca0ea2e15dbcc3a0f6096424c5a6376ee6f4aa8\"}}','2018-09-04 10:16:31'),(14,142,'0x46245984f05a7e597538747c367b917e52b54270','330053325','UTC--2018-09-04T09-33-52.74000000Z--46245984f05a7e597538747c367b917e52b54270.json','{\"address\":\"46245984f05a7e597538747c367b917e52b54270\",\"id\":\"e8caa0f4-72da-4e6f-ad66-2f5f03ac0bb1\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"d0b49603db59975a73bd8b50cb4971f4eacfabd9daa9fa881e79f5f710e63bf8\",\"cipherparams\":{\"iv\":\"92635001b8cf38e813885a110d3c3124\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"9a9acac2d151f8ab7ab9f8702636347f24ea7ce4e06b33c108da45e4457c86ca\"},\"mac\":\"b73daae10903f7e0f2b3b9171bf667382a6d7e3db16a2e74179deac9b4526ce9\"}}','2018-09-04 17:33:53'),(15,40,'0x01953f8339bf9664431e9ce360b83a100155c7b0','1878562639','UTC--2018-09-05T09-31-07.33000000Z--01953f8339bf9664431e9ce360b83a100155c7b0.json','{\"address\":\"01953f8339bf9664431e9ce360b83a100155c7b0\",\"id\":\"b3f0a2a8-a0f2-4056-b36d-273268b2e70a\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"02616fd5cdb5e0a6d878b7921684e2ed97e5617ff226c436d1d9fac19153461f\",\"cipherparams\":{\"iv\":\"ac7b1af88febd773674739a0ee9e44ac\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"eb4099d857370093d0a712e9b50530fbefffffd453c6e7d0f05763e6a91935ca\"},\"mac\":\"fe14e1b666ca4696efb16e517e4f382b0740cfe07a02beed25445bfdd5119bbf\"}}','2018-09-05 17:31:08'),(16,147,'0x4fdfb5d1c935924f6a2c9cb86ffba88e19aa764c','55414749','UTC--2018-09-07T07-51-45.545000000Z--4fdfb5d1c935924f6a2c9cb86ffba88e19aa764c.json','{\"address\":\"4fdfb5d1c935924f6a2c9cb86ffba88e19aa764c\",\"id\":\"96717cf5-eb75-4567-a49e-81c20ffe24ee\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"f01586b33783d39691eee02a43d27a2fb98fa07d6e96dd302d913575dda2f9f0\",\"cipherparams\":{\"iv\":\"a8a655ddeb3d2e665ff8331f053cf6ab\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"16cb511c02a9214ccc7f0d16f438bc46323f499dce733ec7bfc8bc6ba0fabb91\"},\"mac\":\"ffb158a46ca74be1396bb71eba0b99ad69569c1f626cb7fadcec9d534bc4b5f3\"}}','2018-09-07 15:51:49'),(17,147,'0x2925056de2935f6a39253a6f996cf41c9bbea426','976793953','UTC--2018-09-07T07-51-45.580000000Z--2925056de2935f6a39253a6f996cf41c9bbea426.json','{\"address\":\"2925056de2935f6a39253a6f996cf41c9bbea426\",\"id\":\"583d967b-3d54-4e44-b14c-e796865326da\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"e4fa9d02351411510c3ba20365ceff05298b1a67bc1c2eae230a67c74e5f6325\",\"cipherparams\":{\"iv\":\"54c2a91c9c4efa51f667e1d5cad0a942\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"07523d884aa69ca773bc0468ddd81bf1847e31672b1377f724128c9652518f5f\"},\"mac\":\"f6cd131c99bb2e9fb56c7059c9c90ce142c5bf0703ea493fb594f8dd7f4c057d\"}}','2018-09-07 15:51:49'),(18,147,'0xa8286b776e8fb5d28267f3e190381bc9df23df50','1924352839','UTC--2018-09-07T07-51-45.876000000Z--a8286b776e8fb5d28267f3e190381bc9df23df50.json','{\"address\":\"a8286b776e8fb5d28267f3e190381bc9df23df50\",\"id\":\"882f28f5-5a24-435f-af04-538113feecec\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"0d508beb79c4e60a5b5b9c1dc56846ad8c4f74d52bb058f532a05bd7049d5576\",\"cipherparams\":{\"iv\":\"676b8691d77c80cb46d3d51ae7a5fbc1\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"2dbb75e67725db04ef0501c43daa86eeee2a22287344fe617e10f46fcbda1859\"},\"mac\":\"e35ae5e05eee26a9dc8d09a71a865ac7fe7afd88affd930daa5fd3bb23d8e639\"}}','2018-09-07 15:51:49'),(19,147,'0x77cab918692f2c8a795ae1b7d0f93d1440a2d4cc','719209645','UTC--2018-09-07T07-51-47.411000000Z--77cab918692f2c8a795ae1b7d0f93d1440a2d4cc.json','{\"address\":\"77cab918692f2c8a795ae1b7d0f93d1440a2d4cc\",\"id\":\"ed908158-e9c3-4f81-99a4-c70184069f25\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"2d161b43240f1ed1a3fa07c13342cd0ece2383531cd22d11d412a7c65a836046\",\"cipherparams\":{\"iv\":\"5c69ffb713ee173059d37324b4086b2d\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"111f90909625e79ff75a888ab30126bdc1e92ad07e953043e4f699312e22c7cb\"},\"mac\":\"4de662bf6a781400bfce6089e01394de47bc77613edfb2eb57a61b0507738f21\"}}','2018-09-07 15:51:49'),(20,147,'0x2ae6568e594bbdb7c7969b38aa37ce9b8ebfc815','867390818','UTC--2018-09-07T07-51-47.798000000Z--2ae6568e594bbdb7c7969b38aa37ce9b8ebfc815.json','{\"address\":\"2ae6568e594bbdb7c7969b38aa37ce9b8ebfc815\",\"id\":\"dd09d4a3-8275-4cb3-b337-26a368c88eb6\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"57ecaedb65163eae49039e519211bc6e1ea854afa9987fd6595549a717f666e1\",\"cipherparams\":{\"iv\":\"b0af82698809c9f09ca6ef7582b41027\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"5313779691bf3b34f7479de9e386ba540c0f29be3da777274a3579dd80c3782c\"},\"mac\":\"dfcbdf9c76710b7e2f795328c986688e0d6173c6f3bded1489548b5d61ac9580\"}}','2018-09-07 15:51:49'),(21,147,'0x2f03131533e94d67bf609dd3a10233c9ff1d2490','1425805238','UTC--2018-09-07T07-51-48.121000000Z--2f03131533e94d67bf609dd3a10233c9ff1d2490.json','{\"address\":\"2f03131533e94d67bf609dd3a10233c9ff1d2490\",\"id\":\"3afd53f9-7773-4d69-83fd-7c43ae185ec2\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"f4d99dc18fd1f9001ddbec4e0a70fc4712636e56793821ef6fdc2e3f1cbead7b\",\"cipherparams\":{\"iv\":\"e14c00a1ca0e2f6be09964c87272c7c0\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"46ae8a6e8e64d4fff9b31f0f8856bef4ed8b7c7baada23718175601f72226243\"},\"mac\":\"1a30413905f0b1a95a83a25e112395d5c091a96152c1a4ff33bc4c7c6b21b063\"}}','2018-09-07 15:51:50'),(22,38,'0xbda3a52601c4e150fc8b4c5e3610566fa93c777e','135638866','UTC--2018-09-08T14-48-48.478000000Z--bda3a52601c4e150fc8b4c5e3610566fa93c777e.json','{\"address\":\"bda3a52601c4e150fc8b4c5e3610566fa93c777e\",\"id\":\"1de838a7-f234-48e6-aeb7-37e530145a16\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"3ad58efe2160057067b47e37de730a6043ba3552fd03c9fe631db3dec49547e0\",\"cipherparams\":{\"iv\":\"9fb7c99e5e8564d9d1dbaa68d713639e\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"b4647fa3d342223a704ff5d93216568c5c8c897db8277c67bc28d201d43cf988\"},\"mac\":\"e4e1b146e4878b2b329538a10ac2a5360a1c621c60ba90917c781a9b6dbf7804\"}}','2018-09-08 22:48:50'),(23,28,'0xe2d7b786b5516497968dabd699a32f66bb0b64c1','656998833','UTC--2018-09-18T06-27-38.193000000Z--e2d7b786b5516497968dabd699a32f66bb0b64c1.json','{\"address\":\"e2d7b786b5516497968dabd699a32f66bb0b64c1\",\"id\":\"5766ce99-9339-488f-82c7-beb81213fea2\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"42b114c7db72e0438065452c83dd1175084db1293e8d533be89750b7c32b5d68\",\"cipherparams\":{\"iv\":\"25c79d2451ac597a1c3eb32cf46dc7a6\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"1bc29eb40390e666541b1781e6691b992fa5301efef155a2899a47723606596f\"},\"mac\":\"42a398e38b4d5d3cb0344e39d6b45f6e3ab3c1ae96179578f8bfebd312d5c96a\"}}','2018-09-18 14:27:39'),(24,157,'0xd6f2876baa565d5031dc49e9f28c65234489ca1c','701375602','UTC--2018-09-28T07-48-38.487000000Z--d6f2876baa565d5031dc49e9f28c65234489ca1c.json','{\"address\":\"d6f2876baa565d5031dc49e9f28c65234489ca1c\",\"id\":\"0a2a1466-0080-4edc-93bb-732d75c6f229\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"e87972e14f66ebf3b9e6fc7d309aa8051df7f24ec1a79cf24d7402e9766fe37e\",\"cipherparams\":{\"iv\":\"b11596a5c0253fa710332a08ae763117\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"21f24f4bf55e54ed13fce4b16f2eec1372458a294ae339af9b7c22bf1cd9dbfe\"},\"mac\":\"dcafe6dad70478d29fc29e3f74c38699a3b231bdeba24967f8b03a694212bbbc\"}}','2018-09-28 15:48:40'),(25,157,'0x4ee152302daa333753a5111fd64c4a1b99a16181','270031788','UTC--2018-09-28T07-48-38.596000000Z--4ee152302daa333753a5111fd64c4a1b99a16181.json','{\"address\":\"4ee152302daa333753a5111fd64c4a1b99a16181\",\"id\":\"8306195f-774a-4ba4-99b1-376f9cf50605\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"6e8bb3545d4d8b2a7772484d3db0b2b7d6342d3df329eb8be2f59ff6ce121eb8\",\"cipherparams\":{\"iv\":\"2044fce89ebe5712ee567b48a773be5b\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"f2e50a846e262961e1cb01465181c8fac45ff9b87517071714725a9c249bdbc6\"},\"mac\":\"c273309fa87dc854db4168d7fbae856210c1754b63390049f75abb4b887da13c\"}}','2018-09-28 15:48:40'),(26,158,'0x5da71645824aa9891926ff7f7aa2725ad4cb1831','1014420956','UTC--2018-09-28T07-49-51.547000000Z--5da71645824aa9891926ff7f7aa2725ad4cb1831.json','{\"address\":\"5da71645824aa9891926ff7f7aa2725ad4cb1831\",\"id\":\"b9a992a5-97c6-4b48-b2bd-243e99362a9b\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"432e58df75a84876aa23406aa50c09cbfb92a3cb4215b43469b8653e6bfe1691\",\"cipherparams\":{\"iv\":\"efc4e63346111d69bb9fd4c72378c020\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"1360c4b2e91393fb6faf374776c00cceb694e2a03573609227873fc0e02f2f7a\"},\"mac\":\"ed082d4f67650930ad076f26dcfc997c581f66fe5f337b6d5b4ba094e0f1b38f\"}}','2018-09-28 15:49:52'),(27,159,'0x0fe7687043a5995f490994b101807cda014f81d0','722084055','UTC--2018-09-29T07-38-23.8000000Z--0fe7687043a5995f490994b101807cda014f81d0.json','{\"address\":\"0fe7687043a5995f490994b101807cda014f81d0\",\"id\":\"872d71be-c71e-4c05-9db4-a3b42aad08e5\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"3004e41135629f6f46013e7dc900650407bc5d975e205ff6d7d1f59e602610a2\",\"cipherparams\":{\"iv\":\"ca52096a0e30697929811f5347336f2f\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"c74b8333ff9406dff431730bc8f55e594c55e0c1a3988635ec6ef292ff9613a4\"},\"mac\":\"a70e12458ad0c6ab8a67088339c841b2aa1cd59407d35da193b701669b685c99\"}}','2018-09-29 15:38:27'),(28,159,'0xb9dc4e62d6d701590b4aad934fe55f441c5fb15f','291980812','UTC--2018-09-29T07-38-27.486000000Z--b9dc4e62d6d701590b4aad934fe55f441c5fb15f.json','{\"address\":\"b9dc4e62d6d701590b4aad934fe55f441c5fb15f\",\"id\":\"1dcde864-f4e0-43e6-aa4b-a564d33c39f7\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"2886ca3b7d53cecfbd9672a4bea24a1ae33280057e7352b8572f0b9abe858726\",\"cipherparams\":{\"iv\":\"d2b9c2abc3e3380af104f7c484c0b901\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"f16786cd7908f64e06eae9657a0197248d6b7f98dd0302c8bb4c885eec35f612\"},\"mac\":\"2b2ab49b6549d7c895b9b34bde282583dfb40efc8c01101d2a6171a719e3f5db\"}}','2018-09-29 15:38:31'),(29,159,'0x44a75bbcf18f4d22a9406fbd996bcac397aae829','1327919618','UTC--2018-09-29T07-38-28.387000000Z--44a75bbcf18f4d22a9406fbd996bcac397aae829.json','{\"address\":\"44a75bbcf18f4d22a9406fbd996bcac397aae829\",\"id\":\"031ff5b1-71ed-4db2-bf00-4d087f19a5a0\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"4ca7af17cd419379842e39b5c28bb754d33b193ef9c1272fa278df6ca4814180\",\"cipherparams\":{\"iv\":\"7289080f1c64ba89eee106b278025829\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"0fabc89c1e2bfe45e30d79a1862498fecff190f13aa708feb16af3f89b5b255d\"},\"mac\":\"aef380e1725cf29a2eba17b0b750b5a044a405b7485f1ea71361877af4377cfd\"}}','2018-09-29 15:38:31'),(30,159,'0x275325b35e572bf20468d47c7a20f2479a1a4c91','1436414359','UTC--2018-09-29T07-38-28.548000000Z--275325b35e572bf20468d47c7a20f2479a1a4c91.json','{\"address\":\"275325b35e572bf20468d47c7a20f2479a1a4c91\",\"id\":\"52546917-e7b8-45df-a4b1-a3a82eb4a822\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"2cba7bc50205daa23969c0d0b90a1e97739762040e76f8e394b805f88ef0c257\",\"cipherparams\":{\"iv\":\"ab12465858ea49c8060be2e94a87be73\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"de76a33671e0f29d063acd924803f4622625eaff408bea53759557fab6f15c64\"},\"mac\":\"3473ca1d182474f03d091870fb76990fe85b36017cada43da65c04e6919d2e00\"}}','2018-09-29 15:38:31'),(31,159,'0xe49d982fc0fb1491e2671ae3e8a37f04cbd930c3','1536705386','UTC--2018-09-29T07-38-29.270000000Z--e49d982fc0fb1491e2671ae3e8a37f04cbd930c3.json','{\"address\":\"e49d982fc0fb1491e2671ae3e8a37f04cbd930c3\",\"id\":\"2304ee0f-8d67-4c24-a177-110b5af34cdd\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"a014c661611a7ba00828a61bd9bb476c311aa0e6021e512b5fd6c67b9e4b43a0\",\"cipherparams\":{\"iv\":\"60699c2fc31bd70588ee830780c2b63c\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"6a5358907e7eea361a85e4fe34beb89f2b5c5703a20f1c9e6a115e16f640c7ef\"},\"mac\":\"6502efd884c84e44bee37bc0510c2e44830f0793c6ef58747821e15d568445f9\"}}','2018-09-29 15:38:31'),(32,159,'0xc82882a7157759fdf77b5c27b1a3ff610fccae16','1974841301','UTC--2018-09-29T07-38-28.548000000Z--c82882a7157759fdf77b5c27b1a3ff610fccae16.json','{\"address\":\"c82882a7157759fdf77b5c27b1a3ff610fccae16\",\"id\":\"5cad641b-1f82-4a39-b65b-3d054de16996\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"d365fbab4d25509fab6fa5d00eef4cc04d6657b8bc7f0d73f6ea3689bf06dcfb\",\"cipherparams\":{\"iv\":\"f4c38534985dcef9cc65dab492d1b77b\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"5a110bd807b5600a7db6dff7ff6d97764d2d25811f6e7a4a8a2f46b0a598b979\"},\"mac\":\"92e8b76bd74c728362cf535a111a6466e7c2fb14ffa2fa88594b9c95d24bc1f5\"}}','2018-09-29 15:38:31'),(33,161,'0x6d3f3223d40f96f935452fa1262d4c238eaba8e9','733985436','UTC--2018-09-29T08-43-41.101000000Z--6d3f3223d40f96f935452fa1262d4c238eaba8e9.json','{\"address\":\"6d3f3223d40f96f935452fa1262d4c238eaba8e9\",\"id\":\"37c74a49-2b85-4548-8d51-e3bc03c53c84\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"5679cc1206052f897f6efa2f5665668b985a26c6ec1911be89930dc59da20f93\",\"cipherparams\":{\"iv\":\"8faaa8b938d423b9b28e7a5e12a777fb\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"e36d84d84a394307a3c81dfc709e48b6ca417e0780915b6d327aa4a6ced1b067\"},\"mac\":\"7e61525fa09ce905e5b96d773b7cf7db6d5a6b4c0e4d5434d2aec3c03d80510d\"}}','2018-09-29 16:43:43'),(34,161,'0x9d141c9439dd3cd8e8abd63e68659796cd5c822e','1726104997','UTC--2018-09-29T08-43-42.860000000Z--9d141c9439dd3cd8e8abd63e68659796cd5c822e.json','{\"address\":\"9d141c9439dd3cd8e8abd63e68659796cd5c822e\",\"id\":\"ce6e8a02-04ae-43db-9739-8b537eb10be7\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"cabbc56e9bb5f37059981033a764563db3605833f8bb0e05b9204807bb0423eb\",\"cipherparams\":{\"iv\":\"984f5ab4081adcde0ab16f329f4c2504\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"ea78e36831107f9c45014f519b43284f0539c622d7fed18195b75e2d2a889cb9\"},\"mac\":\"e275ea8d0136f09897db5ccb846235607c9891e523acf1c4f281370d9470fccb\"}}','2018-09-29 16:43:44'),(35,161,'0xc5eedd9eac1cae94b48731def95bfcc88a3fbbd1','1986121253','UTC--2018-09-29T08-43-45.143000000Z--c5eedd9eac1cae94b48731def95bfcc88a3fbbd1.json','{\"address\":\"c5eedd9eac1cae94b48731def95bfcc88a3fbbd1\",\"id\":\"3460f91d-1c03-4799-ad56-3cdfbe299f56\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"a994615b23b3557cb6f0770354ea8fc0e9817fc1d88b699da4c61b9db2e2c80f\",\"cipherparams\":{\"iv\":\"72b197bf4d9280b1a328dc084fec195b\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"f8c75d490aeadbf3607d61c244544ae7ff9d8e9f55d82187b730db23b675e949\"},\"mac\":\"2ec8a702803d3077e3bb348d9a9c4f72f1fbeb17432617fe5a6797a03fc6221e\"}}','2018-09-29 16:43:46'),(36,160,'0x31dd5de5b52f1d76b6287d3461c09887e8fa7f8b','1721780563','UTC--2018-09-29T08-55-11.979000000Z--31dd5de5b52f1d76b6287d3461c09887e8fa7f8b.json','{\"address\":\"31dd5de5b52f1d76b6287d3461c09887e8fa7f8b\",\"id\":\"68cf3cda-c898-4a2e-badb-6d82d99972ff\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"33e208b49e062e659554d90681df127e28d7ee8c1a7c190015684eef6973fc67\",\"cipherparams\":{\"iv\":\"86ba402956208843a4b9d51241be2a7e\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"54f2f859023df1e0bb8b0c3175f42d5376952330894387e157bc3accc0882a6e\"},\"mac\":\"b5cec5c92fc671148fb70ea7ed52db326bc788a2330ebba71bc5f347427c19a4\"}}','2018-09-29 16:55:14'),(37,160,'0x1eee9f6cd1c5455eac6ddaac6f926565b43f4343','1621217928','UTC--2018-09-29T08-55-13.238000000Z--1eee9f6cd1c5455eac6ddaac6f926565b43f4343.json','{\"address\":\"1eee9f6cd1c5455eac6ddaac6f926565b43f4343\",\"id\":\"1d27d57c-186a-4389-af16-bc46c6a8aff5\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"410047fbfba651c572f00991e3ff37ae03f0e1ed997e48ddf2f6739a8bda0da9\",\"cipherparams\":{\"iv\":\"9a1988794fab0d66fa55b2d11e6b7edc\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"6606e3fc862dee48c1c5204697df884ab34c4250019a7015dab5c38fad00b55e\"},\"mac\":\"a61bca91fb2b95fbade721caa0811d866edaf972f1cca3e2b0bf500de1674deb\"}}','2018-09-29 16:55:14'),(38,5,'0xc88a5b3c63eb4070e36f2ba35ce51abd732883ab','1013491300','UTC--2018-10-08T06-20-52.585000000Z--c88a5b3c63eb4070e36f2ba35ce51abd732883ab.json','{\"address\":\"c88a5b3c63eb4070e36f2ba35ce51abd732883ab\",\"id\":\"b8027d7d-5d10-4f15-a2a3-a47f70602122\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"85103f77cd06ed9e4de6803dc40cabee45e7d44a106e584d5e25e418a20c3f92\",\"cipherparams\":{\"iv\":\"6162fbb1e758e18e07b7a05f733d957e\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"aee2aebb5f99df0caad07f4d106377186608566ef489ea2a9f618d472e58d404\"},\"mac\":\"07fb2a546c46a2af106a13e7a399ed6f0644ecd457f0b2bb44c4e75374157583\"}}','2018-10-08 14:20:53'),(39,169,'0x778c4aa9b71e60515982f74c1160a2233fd6e0c4','1867346620','UTC--2018-10-08T09-01-06.819000000Z--778c4aa9b71e60515982f74c1160a2233fd6e0c4.json','{\"address\":\"778c4aa9b71e60515982f74c1160a2233fd6e0c4\",\"id\":\"69fea4e6-39bb-4344-beeb-6d3d19955805\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"b174a7eb1cb7e8d8d61854f4f5ac06a62dce98d3af69d5c7de723d6163915c33\",\"cipherparams\":{\"iv\":\"b7885c592b0e480539db0464139c47c2\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"4216ce1a77d3fc1e8f6f4254f867cc17ce1f6d6dc96527ddec0fa6b452089e50\"},\"mac\":\"ef6c1a88fcab8a4379404abc2e7dcae72fee9f13654cc3dc71e8426ccb3df596\"}}','2018-10-08 17:01:09'),(40,169,'0xce260498ac2356792d67d157252365279fba40cb','158165002','UTC--2018-10-08T09-01-08.847000000Z--ce260498ac2356792d67d157252365279fba40cb.json','{\"address\":\"ce260498ac2356792d67d157252365279fba40cb\",\"id\":\"f10f6b56-056b-480a-a790-337d6c248560\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"d607f6a1cd223b9bebf4b87cc1686417dcf98bbb91eb24fe5b90f61d0a23450b\",\"cipherparams\":{\"iv\":\"747c01ed74e0d06f196fccdc779417bd\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"32ee279c251231fb9d192c4b123f54b639b922680803809ee65bc340eb52fad5\"},\"mac\":\"944e346c7cf18d9c224f0249fd6384bd6f0b97cdf6c04a75c0054b35cc253e5f\"}}','2018-10-08 17:01:10'),(43,170,'0x9ba4e6181920fc6ec6634a8d626f44fbfaf42b6f','505963366','UTC--2018-10-08T09-01-58.120000000Z--9ba4e6181920fc6ec6634a8d626f44fbfaf42b6f.json','{\"address\":\"9ba4e6181920fc6ec6634a8d626f44fbfaf42b6f\",\"id\":\"8c513749-6d48-4e9d-afa2-439ffd174382\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"f9ff30875810823164c948713723c2000aee86c07be4feb57483375962767645\",\"cipherparams\":{\"iv\":\"0510c772e3412771bb432ee3e8f415e1\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"de6d9839c1a5d6ece57de0da4fc34ec396a8941b103f86bc06ed905bb7828fb2\"},\"mac\":\"fff8a148a17f1f631d8cdb31c88900bd9d0086836b3c0465637597e71897e0e7\"}}','2018-10-08 17:01:59'),(44,170,'0x807aceff69d79aa65a85f74ef812d09d96784cb1','1521871810','UTC--2018-10-08T09-01-59.967000000Z--807aceff69d79aa65a85f74ef812d09d96784cb1.json','{\"address\":\"807aceff69d79aa65a85f74ef812d09d96784cb1\",\"id\":\"020efa2c-bb4f-4da7-bf90-67aeeb85e638\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"aff08e56993dc60cba54bd93cf4087d30ee0e504281035ccd389de7a09606a7a\",\"cipherparams\":{\"iv\":\"aa916be39eef3e124df42dda91fd751c\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"f6572ae917421eecbc55b995525f59e34fb97a0fa00e48b7aa6997b890c1a574\"},\"mac\":\"552ec09e73334b938f2c083989a31e69b694152a5e7335b2c9b0946de08c9b08\"}}','2018-10-08 17:02:01'),(45,170,'0xe49037624b2ff50eae70a04192555c12e8bf3dd2','1242578093','UTC--2018-10-08T09-02-00.587000000Z--e49037624b2ff50eae70a04192555c12e8bf3dd2.json','{\"address\":\"e49037624b2ff50eae70a04192555c12e8bf3dd2\",\"id\":\"d87a4923-b93f-4b47-a603-7dbcdc27acd6\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"ec24c2af779d17d608a62f5279f4be44df2402a544c08bb737072c189e239471\",\"cipherparams\":{\"iv\":\"fc2891a00fd2d193a26930b4b0e11360\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"5752ea2c2acb50e8a0282c3720db24b174d3dacdc452762faa0357ff014e33d4\"},\"mac\":\"c6998468aea31ae4b27613349413ac332d824d2558d8f09351c233be90a82aa4\"}}','2018-10-08 17:02:02'),(46,171,'0xdc4fcd55933d5067709bafae95a1c43e6efba0eb','658097365','UTC--2018-10-11T11-33-01.871000000Z--dc4fcd55933d5067709bafae95a1c43e6efba0eb.json','{\"address\":\"dc4fcd55933d5067709bafae95a1c43e6efba0eb\",\"id\":\"4c3836e9-7524-4525-afe0-5710c46c31ff\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"f4a2e28c4d9214959b1d408d53603847cc9fc85b7446575885b89da52aa9b2d3\",\"cipherparams\":{\"iv\":\"b019b060f6eb97ae86feaf99eee8c9a1\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"adcb1b9d974532f301750b0e0b32e3dfa077c285bbbd2a23b867adbc1a883142\"},\"mac\":\"100258c850581eb6b45d78b66b7315a7a20f5f8193693c27ee4cfbca60f26b9f\"}}','2018-10-11 19:33:03'),(47,12,'0xab0764233533f9efa96f95696c2e9389e8550b02','891879892','UTC--2018-10-11T12-13-43.21000000Z--ab0764233533f9efa96f95696c2e9389e8550b02.json','{\"address\":\"ab0764233533f9efa96f95696c2e9389e8550b02\",\"id\":\"f7cd9eb1-0921-4632-a67a-9d8a1e3dd37b\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"278f0468530f07b3117f143e4078003274766b049fc4667e2ea19b7075eb79e1\",\"cipherparams\":{\"iv\":\"6d9a84c2776ba1d2fde84e75c07f0d99\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"98dffaadcd0e794d2a765719d4af2e7c8b81b1852e6ee7e2ee0d2d7013674364\"},\"mac\":\"55d955094239e70ead907c43ab9acdd66e31aad1a820c016cbfcbcb4d6adca77\"}}','2018-10-11 20:13:44'),(48,187,'0x0d655772d19ede3f30d9691e6f74ec386f82cd0d','1458159247','UTC--2018-11-06T08-37-41.26000000Z--0d655772d19ede3f30d9691e6f74ec386f82cd0d.json','{\"address\":\"0d655772d19ede3f30d9691e6f74ec386f82cd0d\",\"id\":\"dde33043-1b17-4626-9b90-aeccd427e7d1\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"5a5bad452843ae92ca3497671b3fe964e946db0698eac5183c976777506edd6a\",\"cipherparams\":{\"iv\":\"ca165114bcdbb5345b65535d17240318\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"6f3a46e7c2f5f804851614fa97275b92c49a029e5866eec23243382dd65d695c\"},\"mac\":\"047873570617d6dffa8546c1313cf68f65f8894a90253c99183567f47bfc667d\"}}','2018-11-06 16:37:42'),(49,182,'0x9f91cfce46d363909c49f6b726e1a0f4015d729d','542847551','UTC--2018-11-07T02-32-18.60000000Z--9f91cfce46d363909c49f6b726e1a0f4015d729d.json','{\"address\":\"9f91cfce46d363909c49f6b726e1a0f4015d729d\",\"id\":\"0f2342f8-38b3-4cb3-a231-97de5a5bb2fa\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"227f2cbc545756462dd5b5f3dfcace3acd092920b3304eee71524d112c1ef609\",\"cipherparams\":{\"iv\":\"9a44bddb1f027949b33303eea936f292\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"36b6707380818f27f23ffa3f595ee9da145ddebf7fa556232b7a5c626ddae9e2\"},\"mac\":\"209c1e149ccef2cd122243fd6073cbe715170835ab9aaa152134a62c2dba64b9\"}}','2018-11-07 10:32:19'),(50,201,'0x7c2963c52f10f76131da4ab03cb85fb29ecd5cd2','1074838301','UTC--2018-11-08T10-22-08.101000000Z--7c2963c52f10f76131da4ab03cb85fb29ecd5cd2.json','{\"address\":\"7c2963c52f10f76131da4ab03cb85fb29ecd5cd2\",\"id\":\"4c776245-8542-4304-81a2-fa37542b710e\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"a90ead63957a67bf0878950ddfdf0b035c946f9aab903e0bd56175939335688a\",\"cipherparams\":{\"iv\":\"2b8b93ffcc2e19755c164fbc59a5665a\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"1abcf5e2ff7ebec12c354211a140cf58c77e1a657a4fb23079291182df5b62d8\"},\"mac\":\"d075be1c3cfcf9c6bd49b7a0caeb74b7468704fbca8fdc23086cb188e092ea3f\"}}','2018-11-08 18:22:09'),(51,200,'0x6eee139ba3ab4444c6767cafe517a606e70bf926','1117651166','UTC--2018-11-08T10-23-05.782000000Z--6eee139ba3ab4444c6767cafe517a606e70bf926.json','{\"address\":\"6eee139ba3ab4444c6767cafe517a606e70bf926\",\"id\":\"cad0fc73-df2e-4ae6-8dfa-72f75045ac93\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"bf669b85274dab663363f111b1b21ae5d3b500a0497bfa1f96df6a46687fcd5f\",\"cipherparams\":{\"iv\":\"f951be599fbf1279018d44ac482c7651\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"343b550eb6c4cfdd42f328c6997aa78c643f5f0d29ce6a3b88bfef0b454426f5\"},\"mac\":\"e33cc382d9b4d363e064d9aba27aaff25a5979ecd64d197b892a4f5767b0b71f\"}}','2018-11-08 18:23:07'),(53,230,'0xd3065d51424daac12475413082a80b9a95f7f887','14306438','UTC--2018-11-19T07-42-31.406000000Z--d3065d51424daac12475413082a80b9a95f7f887.json','{\"address\":\"d3065d51424daac12475413082a80b9a95f7f887\",\"id\":\"6f9c5749-adfb-4a1b-be9f-07a613382fc8\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"5b5337f931c15bb0ee34120a59bd6cb6e34ea50507ca7e5fa324c855749452cc\",\"cipherparams\":{\"iv\":\"b83b3b855e3e5770d85e57e2ac10b3d9\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"4ff37c1ca93c6105834f6868196c78b643f6df19bc5f592316a455b1aa063c14\"},\"mac\":\"843d5507fb9292d61cc63a5130035682447c607d7743f8b6e81bbc28fcbb8045\"}}','2018-11-19 15:42:33'),(55,235,'0xf7f614f7f8d49df27b3d0e62841ce28d90ac61e3','150212781','UTC--2018-11-19T07-57-14.67000000Z--f7f614f7f8d49df27b3d0e62841ce28d90ac61e3.json','{\"address\":\"f7f614f7f8d49df27b3d0e62841ce28d90ac61e3\",\"id\":\"42d3ba32-9b30-4eaa-9800-094473ec8709\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"94f1b71edcbf6097e1d7ab57cdad2697905d22c0efba0a67636c34d990e703ff\",\"cipherparams\":{\"iv\":\"244e58c7bc742df8b931fb5be5aaf0ec\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"3d4ead4f6c10d83a0ec1cec225b3c25d11bcf10530739b53c47bb0b47ccea157\"},\"mac\":\"37ba089854d36604f547f14ba1ed3f02488144a53a4bf80c443bff3b6491d1fc\"}}','2018-11-19 15:57:16'),(57,235,'0xd75b9785c090a78778cfd1ac37793509c9ae18dc','770662717','UTC--2018-11-19T07-57-17.366000000Z--d75b9785c090a78778cfd1ac37793509c9ae18dc.json','{\"address\":\"d75b9785c090a78778cfd1ac37793509c9ae18dc\",\"id\":\"17fd0896-24c5-4d9c-a95f-81f67229b038\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"74f1ac3c17455a20a1348d70eaa915beb5227c10320e384ee65ab353e4dd6cd6\",\"cipherparams\":{\"iv\":\"41c437aa6d74e2777797dfd6bddfcaa7\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"e8adcb3670ca21eedf890058aa60fd126deaeedac7dd903ed374002bd1e8e1c4\"},\"mac\":\"eda7a4cbc4e667f444e71e288081b003ab0b9362e6ec443371b34d8acf219202\"}}','2018-11-19 15:57:19'),(58,234,'0xef8ca660228327fcfe7e2b0b33be90c74aae20c2','557486907','UTC--2018-11-19T07-59-16.271000000Z--ef8ca660228327fcfe7e2b0b33be90c74aae20c2.json','{\"address\":\"ef8ca660228327fcfe7e2b0b33be90c74aae20c2\",\"id\":\"cc0e2f93-1eb6-4040-a037-9bd56ba8cd1d\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"c3b83b913807e5b71a9a0d806b624e83b8a0fdc99bd1d9f47b341ec366730208\",\"cipherparams\":{\"iv\":\"be8d47711e98644688a8f6f409dd1390\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"34d7ce67dfef8911de31efde9264cad2f1c1db532cfc3aa406633e9d91a0c15a\"},\"mac\":\"889a6f42441ea66148917deea177b427705d2064f9a8fc757896c32fe9d46403\"}}','2018-11-19 15:59:17'),(60,233,'0x5df94f71e32b01925db3ac2f83b2fd6be8a63808','417407611','UTC--2018-11-19T08-01-19.160000000Z--5df94f71e32b01925db3ac2f83b2fd6be8a63808.json','{\"address\":\"5df94f71e32b01925db3ac2f83b2fd6be8a63808\",\"id\":\"cd903164-c941-489d-b023-2caf75a83d5f\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"eee25bae769b0192e41b4e57f586ec7b4cc47b4fce14fdf6353bca53496886ba\",\"cipherparams\":{\"iv\":\"80724b28bbb1a9dbb0308a008b7f2523\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"3cb9449abc6a3da7018e5a8fd961df389b1b432918b7ff0ddb4d3aa60c41dd4f\"},\"mac\":\"d5a777a8bb3b9fba53bb32001d5043bb7090833d27207bed0759df727a7fadf2\"}}','2018-11-19 16:01:22'),(63,232,'0xd8c21a63aaf6c897bb0f2887a8724eda18599b58','1164693323','UTC--2018-11-19T08-02-51.433000000Z--d8c21a63aaf6c897bb0f2887a8724eda18599b58.json','{\"address\":\"d8c21a63aaf6c897bb0f2887a8724eda18599b58\",\"id\":\"af6251ed-a290-48ae-b825-c4c75629c9c7\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"5d7dcea9b2c01e0c668919c0b0933d03626882d8c6625cb172ea1692249b3bf3\",\"cipherparams\":{\"iv\":\"c2faf8cb45f62e99b9dbc55dcbdd19c3\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"8964f642b6fb419c402395cebcce523ca93937a8d0c7b6edbd3e64a8c1b283c1\"},\"mac\":\"385876c129c80f58fc9b8e2668eea7bf4fe7beef715fc1f915c39be4e4b770d3\"}}','2018-11-19 16:02:53'),(65,231,'0x960bcf9e36554348d14c777162d8ce8fb038d394','289156378','UTC--2018-11-20T07-04-32.838000000Z--960bcf9e36554348d14c777162d8ce8fb038d394.json','{\"address\":\"960bcf9e36554348d14c777162d8ce8fb038d394\",\"id\":\"a7d87a58-d997-44f9-aafb-cc572305c700\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"1fdf16866ac6145139dbf3a40cf72d5c5ad95596252f861e88993d48ec3e184c\",\"cipherparams\":{\"iv\":\"5f810a2324af19eb6d31fa008c2c9465\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"2a8ad87ddc661a7d59fcbfa23223b46704fe3188962b08659bac4123ba5d5512\"},\"mac\":\"396c1a28ad3f364acd43b3e847fdb218a52fcffcecfb8ced2501f1adc0a9f1bf\"}}','2018-11-20 15:04:35'),(66,239,'0xda786c596d62cb9f09fe168c6b2943e2bb7f02d7','1713067409','UTC--2018-11-28T09-16-57.956000000Z--da786c596d62cb9f09fe168c6b2943e2bb7f02d7.json','{\"address\":\"da786c596d62cb9f09fe168c6b2943e2bb7f02d7\",\"id\":\"4ee1cefc-2ef7-46bf-a536-5ac54faa29f9\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"2032208709677bf6df8ea94d6af13bba591fd1f723df80639d85cd3663ceb479\",\"cipherparams\":{\"iv\":\"488bff5e74e97716a2e2d543743ba8ce\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"3ff4fe17283c196dae7c3bfea01e9d5e4330841bcbe8502bb17571a175bece16\"},\"mac\":\"6c46be75bad091e59331e6d6d791372fe8c5f6c3dbf5a568dbfd5828972925b1\"}}','2018-11-28 17:16:59');
/*!40000 ALTER TABLE `t_eth_address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_exchange_rate`
--

DROP TABLE IF EXISTS `t_exchange_rate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_exchange_rate` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `coin_name` varchar(8) NOT NULL DEFAULT '' COMMENT '������������������������������',
  `settlement_currency` varchar(8) NOT NULL DEFAULT '' COMMENT '���������������������',
  `price` decimal(24,8) unsigned NOT NULL DEFAULT '0.00000000' COMMENT '���������������1���coin���������������������������������������',
  `comment` varchar(50) DEFAULT NULL COMMENT '���������������������������������������������������������',
  `add_time` datetime NOT NULL COMMENT '���������������������',
  `update_time` datetime NOT NULL COMMENT '������������������������',
  `price_hour_ago` decimal(24,8) unsigned NOT NULL DEFAULT '0.00000000' COMMENT '������������������',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_ex_coin_currency` (`settlement_currency`,`coin_name`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='������������������2������������������������';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_exchange_rate`
--

LOCK TABLES `t_exchange_rate` WRITE;
/*!40000 ALTER TABLE `t_exchange_rate` DISABLE KEYS */;
INSERT INTO `t_exchange_rate` VALUES (1,'BTC','USDT',3974.15000000,'from market','2018-08-25 00:01:00','2018-12-05 00:01:08',14.81179707),(2,'ETH','USDT',110.76000000,'from market','2018-08-25 00:01:00','2018-12-05 00:01:08',20.50000000),(3,'LTC','USDT',52.02000000,'from market','2018-08-25 00:01:00','2018-10-19 00:01:00',0.00000000),(4,'YT','DG',24.00000000,'from market','2018-09-04 00:01:00','2018-10-09 00:01:00',20.26509832),(5,'DG','YT',1.60000000,'from market','2018-09-04 00:01:00','2018-12-05 00:01:08',9.81987578),(6,'USDT','CNY',2.00000000,'������������','2018-09-04 00:00:00','2018-09-04 00:00:00',2.00000000),(7,'DG','CNY',3.00000000,'������������','2018-09-04 00:00:00','2018-09-04 00:00:00',3.00000000),(9,'PKT','USDT',10.00000000,'from market','2018-10-11 00:01:01','2018-12-05 00:01:08',10.00000000),(10,'YT','PKT',101.00000000,'from market','2018-10-11 00:01:01','2018-12-05 00:01:08',1.60000000),(11,'YT','USDT',1.50000000,'from market','2018-10-11 00:01:01','2018-12-05 00:01:08',1.50000000),(12,'YT','ETH',1.50000000,'from market','2018-10-11 00:01:01','2018-10-17 00:01:17',1.50000000),(14,'BTC','YT',55556.00000000,'from market','2018-11-20 00:01:02','2018-12-05 00:01:08',55556.00000000),(15,'BON','ETH',1000.00000000,'from market','2018-11-20 00:01:02','2018-12-05 00:01:08',1000.00000000);
/*!40000 ALTER TABLE `t_exchange_rate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_google_auth`
--

DROP TABLE IF EXISTS `t_google_auth`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_google_auth` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `mobile` varchar(128) NOT NULL COMMENT '������������email',
  `type` varchar(6) NOT NULL COMMENT '������',
  `code` varchar(16) DEFAULT NULL COMMENT 'google���������',
  `status` int(11) DEFAULT '0' COMMENT '������������',
  `lastTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '������������',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_mobile_type` (`mobile`,`type`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_google_auth`
--

LOCK TABLES `t_google_auth` WRITE;
/*!40000 ALTER TABLE `t_google_auth` DISABLE KEYS */;
INSERT INTO `t_google_auth` VALUES (1,'8616620855816','10','694774',1,'2018-10-18 15:52:40');
/*!40000 ALTER TABLE `t_google_auth` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_google_secret`
--

DROP TABLE IF EXISTS `t_google_secret`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_google_secret` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '������ID',
  `secret` varchar(128) DEFAULT NULL COMMENT 'Google������',
  `status` int(11) DEFAULT '0' COMMENT '0������������1������OK',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_userid` (`user_id`),
  KEY `idx_secret` (`secret`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_google_secret`
--

LOCK TABLES `t_google_secret` WRITE;
/*!40000 ALTER TABLE `t_google_secret` DISABLE KEYS */;
INSERT INTO `t_google_secret` VALUES (1,3,'62QPA75SXY33ELA4',0,'2018-08-30 17:55:13','2018-08-30 17:55:49'),(2,139,'ND342ZY5PJ6QS242',0,'2018-09-04 11:25:25','2018-09-04 11:32:53'),(3,28,'62VR5STTO5L2S675',0,'2018-10-18 15:49:34','2018-10-18 18:31:20'),(4,173,'C7Q7FSTYS5BICKJA',0,'2018-10-18 18:03:10','2018-10-18 18:25:47');
/*!40000 ALTER TABLE `t_google_secret` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_lock_coin_record`
--

DROP TABLE IF EXISTS `t_lock_coin_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_lock_coin_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `lock_no` varchar(64) DEFAULT NULL COMMENT '���������������������������������������',
  `user_id` int(11) NOT NULL DEFAULT '0',
  `coin_name` varchar(8) NOT NULL DEFAULT '' COMMENT '������������',
  `lock_rate` decimal(6,4) unsigned NOT NULL DEFAULT '0.0000' COMMENT '������������',
  `lock_amount` decimal(32,8) unsigned NOT NULL DEFAULT '0.00000000' COMMENT '������������������',
  `release_rate` decimal(6,4) unsigned NOT NULL DEFAULT '0.0000' COMMENT '���������������������������������������',
  `release_amount` decimal(32,8) unsigned NOT NULL DEFAULT '0.00000000' COMMENT '���������������������������',
  `cron_name` varchar(128) DEFAULT NULL COMMENT '������������������(t_cron_config���)',
  `cron_expression` varchar(32) DEFAULT NULL COMMENT 'cron���������',
  `create_time` datetime NOT NULL DEFAULT '2000-01-01 00:00:00' COMMENT '������������',
  PRIMARY KEY (`id`),
  KEY `create_time` (`create_time`),
  KEY `cron_name` (`cron_name`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=91 DEFAULT CHARSET=utf8 COMMENT='���������������������������';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_lock_coin_record`
--

LOCK TABLES `t_lock_coin_record` WRITE;
/*!40000 ALTER TABLE `t_lock_coin_record` DISABLE KEYS */;
INSERT INTO `t_lock_coin_record` VALUES (1,NULL,1,'USDT',0.1000,9998679.11645740,0.1000,999867.91164574,'���30���������','*/30 * * * * ?','2018-11-07 10:31:41'),(2,NULL,3,'USDT',0.1000,3344.87783602,0.1000,334.48778360,'���30���������','*/30 * * * * ?','2018-11-07 10:31:41'),(3,NULL,4,'USDT',0.1000,985.65000000,0.1000,98.56500000,'���30���������','*/30 * * * * ?','2018-11-07 10:31:41'),(4,NULL,6,'USDT',0.1000,10000.00000000,0.1000,1000.00000000,'���30���������','*/30 * * * * ?','2018-11-07 10:31:41'),(5,NULL,17,'USDT',0.1000,49.81759528,0.1000,4.98175953,'���30���������','*/30 * * * * ?','2018-11-07 10:31:41'),(6,NULL,19,'USDT',0.1000,199.76750000,0.1000,19.97675000,'���30���������','*/30 * * * * ?','2018-11-07 10:31:41'),(7,NULL,1,'USDT',0.1000,8998811.20481166,0.1000,899881.12048117,'���30���������','*/30 * * * * ?','2018-11-07 10:32:04'),(8,NULL,3,'USDT',0.1000,3010.39005242,0.1000,301.03900524,'���30���������','*/30 * * * * ?','2018-11-07 10:32:04'),(9,NULL,4,'USDT',0.1000,887.08500000,0.1000,88.70850000,'���30���������','*/30 * * * * ?','2018-11-07 10:32:04'),(10,NULL,6,'USDT',0.1000,9000.00000000,0.1000,900.00000000,'���30���������','*/30 * * * * ?','2018-11-07 10:32:04'),(11,NULL,17,'USDT',0.1000,44.83583575,0.1000,4.48358358,'���30���������','*/30 * * * * ?','2018-11-07 10:32:04'),(12,NULL,19,'USDT',0.1000,179.79075000,0.1000,17.97907500,'���30���������','*/30 * * * * ?','2018-11-07 10:32:04'),(13,NULL,1,'ETH',0.1000,1000.18880000,0.1000,100.01888000,'���30���������','*/30 * * * * ?','2018-11-07 10:32:25'),(14,NULL,3,'ETH',0.1000,1000.10010000,0.1000,100.01001000,'���30���������','*/30 * * * * ?','2018-11-07 10:32:25'),(15,NULL,4,'ETH',0.1000,999.89900000,0.1000,99.98990000,'���30���������','*/30 * * * * ?','2018-11-07 10:32:25'),(16,NULL,6,'ETH',0.1000,8.00000000,0.1000,0.80000000,'���30���������','*/30 * * * * ?','2018-11-07 10:32:25'),(17,NULL,7,'ETH',0.1000,10.48000000,0.1000,1.04800000,'���30���������','*/30 * * * * ?','2018-11-07 10:32:25'),(18,NULL,12,'eth',0.1000,0.10000000,0.1000,0.01000000,'���30���������','*/30 * * * * ?','2018-11-07 10:32:25'),(19,NULL,17,'ETH',0.1000,48.88900000,0.1000,4.88890000,'���30���������','*/30 * * * * ?','2018-11-07 10:32:25'),(20,NULL,19,'ETH',0.1000,1197.80000000,0.1000,119.78000000,'���30���������','*/30 * * * * ?','2018-11-07 10:32:25'),(21,NULL,12,'BON',0.1000,10.00000000,0.1000,1.00000000,'���30���������','*/30 * * * * ?','2018-11-07 10:33:13'),(22,NULL,17,'BON',0.1000,20.00000000,0.1000,2.00000000,'���30���������','*/30 * * * * ?','2018-11-07 10:33:13'),(23,NULL,19,'BON',0.1000,1000.00000000,0.1000,100.00000000,'���30���������','*/30 * * * * ?','2018-11-07 10:33:13'),(24,NULL,1,'BTC',0.1000,1.78555500,0.1000,0.17855550,'���30���������','*/30 * * * * ?','2018-11-07 10:34:27'),(25,NULL,3,'BTC',0.1000,1156.37006311,0.1000,115.63700631,'���30���������','*/30 * * * * ?','2018-11-07 10:34:27'),(26,NULL,4,'BTC',0.1000,1003.55160160,0.1000,100.35516016,'���30���������','*/30 * * * * ?','2018-11-07 10:34:27'),(27,NULL,5,'BTC',0.1000,0.02000000,0.1000,0.00200000,'���30���������','*/30 * * * * ?','2018-11-07 10:34:27'),(28,NULL,17,'BTC',0.1000,39.55739340,0.1000,3.95573934,'���30���������','*/30 * * * * ?','2018-11-07 10:34:27'),(29,NULL,19,'BTC',0.1000,199.80000000,0.1000,19.98000000,'���30���������','*/30 * * * * ?','2018-11-07 10:34:27'),(30,NULL,1,'BTC',0.1000,1.60699950,0.1000,0.16069995,'���30���������','*/30 * * * * ?','2018-11-07 10:34:30'),(31,NULL,3,'BTC',0.1000,1040.73305680,0.1000,104.07330568,'���30���������','*/30 * * * * ?','2018-11-07 10:34:30'),(32,NULL,4,'BTC',0.1000,903.19644144,0.1000,90.31964414,'���30���������','*/30 * * * * ?','2018-11-07 10:34:30'),(33,NULL,5,'BTC',0.1000,0.01800000,0.1000,0.00180000,'���30���������','*/30 * * * * ?','2018-11-07 10:34:30'),(34,NULL,17,'BTC',0.1000,35.60165406,0.1000,3.56016541,'���30���������','*/30 * * * * ?','2018-11-07 10:34:30'),(35,NULL,19,'BTC',0.1000,179.82000000,0.1000,17.98200000,'���30���������','*/30 * * * * ?','2018-11-07 10:34:30'),(36,NULL,1,'BTC',0.1000,1.44629955,0.1000,0.14462996,'���30���������','*/30 * * * * ?','2018-11-07 10:34:31'),(37,NULL,3,'BTC',0.1000,936.65975112,0.1000,93.66597511,'���30���������','*/30 * * * * ?','2018-11-07 10:34:31'),(38,NULL,4,'BTC',0.1000,812.87679730,0.1000,81.28767973,'���30���������','*/30 * * * * ?','2018-11-07 10:34:31'),(39,NULL,5,'BTC',0.1000,0.01620000,0.1000,0.00162000,'���30���������','*/30 * * * * ?','2018-11-07 10:34:31'),(40,NULL,17,'BTC',0.1000,32.04148865,0.1000,3.20414887,'���30���������','*/30 * * * * ?','2018-11-07 10:34:31'),(41,NULL,19,'BTC',0.1000,161.83800000,0.1000,16.18380000,'���30���������','*/30 * * * * ?','2018-11-07 10:34:31'),(42,NULL,1,'BTC',0.1000,1.30166960,0.1000,0.13016696,'���30���������','*/30 * * * * ?','2018-11-07 10:34:32'),(43,NULL,3,'BTC',0.1000,842.99377601,0.1000,84.29937760,'���30���������','*/30 * * * * ?','2018-11-07 10:34:32'),(44,NULL,4,'BTC',0.1000,731.58911757,0.1000,73.15891176,'���30���������','*/30 * * * * ?','2018-11-07 10:34:32'),(45,NULL,5,'BTC',0.1000,0.01458000,0.1000,0.00145800,'���30���������','*/30 * * * * ?','2018-11-07 10:34:32'),(46,NULL,17,'BTC',0.1000,28.83733979,0.1000,2.88373398,'���30���������','*/30 * * * * ?','2018-11-07 10:34:33'),(47,NULL,19,'BTC',0.1000,145.65420000,0.1000,14.56542000,'���30���������','*/30 * * * * ?','2018-11-07 10:34:33'),(48,NULL,1,'BTC',0.1000,1.17150264,0.1000,0.11715026,'���30���������','*/30 * * * * ?','2018-11-07 10:34:34'),(49,NULL,3,'BTC',0.1000,758.69439841,0.1000,75.86943984,'���30���������','*/30 * * * * ?','2018-11-07 10:34:34'),(50,NULL,4,'BTC',0.1000,658.43020581,0.1000,65.84302058,'���30���������','*/30 * * * * ?','2018-11-07 10:34:34'),(51,NULL,5,'BTC',0.1000,0.01312200,0.1000,0.00131220,'���30���������','*/30 * * * * ?','2018-11-07 10:34:34'),(52,NULL,17,'BTC',0.1000,25.95360581,0.1000,2.59536058,'���30���������','*/30 * * * * ?','2018-11-07 10:34:34'),(53,NULL,19,'BTC',0.1000,131.08878000,0.1000,13.10887800,'���30���������','*/30 * * * * ?','2018-11-07 10:34:34'),(54,NULL,1,'BTC',0.1000,1.05435237,0.1000,0.10543524,'���30���������','*/30 * * * * ?','2018-11-07 10:34:37'),(55,NULL,3,'BTC',0.1000,682.82495857,0.1000,68.28249586,'���30���������','*/30 * * * * ?','2018-11-07 10:34:37'),(56,NULL,4,'BTC',0.1000,592.58718523,0.1000,59.25871852,'���30���������','*/30 * * * * ?','2018-11-07 10:34:37'),(57,NULL,5,'BTC',0.1000,0.01180980,0.1000,0.00118098,'���30���������','*/30 * * * * ?','2018-11-07 10:34:37'),(58,NULL,17,'BTC',0.1000,23.35824523,0.1000,2.33582452,'���30���������','*/30 * * * * ?','2018-11-07 10:34:37'),(59,NULL,19,'BTC',0.1000,117.97990200,0.1000,11.79799020,'���30���������','*/30 * * * * ?','2018-11-07 10:34:37'),(60,NULL,12,'BON',0.1000,9.00000000,0.1000,0.90000000,'���30���������','*/30 * * * * ?','2018-11-07 10:35:11'),(61,NULL,17,'BON',0.1000,18.00000000,0.1000,1.80000000,'���30���������','*/30 * * * * ?','2018-11-07 10:35:11'),(62,NULL,19,'BON',0.1000,900.00000000,0.1000,90.00000000,'���30���������','*/30 * * * * ?','2018-11-07 10:35:11'),(63,NULL,12,'BON',0.1000,8.10000000,0.1000,0.81000000,'���30���������','*/30 * * * * ?','2018-11-07 10:35:43'),(64,NULL,17,'BON',0.1000,16.20000000,0.1000,1.62000000,'���30���������','*/30 * * * * ?','2018-11-07 10:35:43'),(65,NULL,19,'BON',0.1000,810.00000000,0.1000,81.00000000,'���30���������','*/30 * * * * ?','2018-11-07 10:35:43'),(66,NULL,1,'BTC',0.1000,1.88555500,0.1000,0.18855550,'������������1���������','0 0 1 * * ?','2018-11-07 16:28:06'),(67,NULL,3,'BTC',0.1000,1156.37006311,0.1000,115.63700631,'������������1���������','0 0 1 * * ?','2018-11-07 16:28:06'),(68,NULL,4,'BTC',0.1000,1003.55160160,0.1000,100.35516016,'������������1���������','0 0 1 * * ?','2018-11-07 16:28:06'),(69,NULL,5,'BTC',0.1000,0.02000000,0.1000,0.00200000,'������������1���������','0 0 1 * * ?','2018-11-07 16:28:06'),(70,NULL,17,'BTC',0.1000,39.55739340,0.1000,3.95573934,'������������1���������','0 0 1 * * ?','2018-11-07 16:28:06'),(71,NULL,19,'BTC',0.1000,199.80000000,0.1000,19.98000000,'������������1���������','0 0 1 * * ?','2018-11-07 16:28:06'),(72,NULL,17,'USDT',0.1000,50.00000000,0.2000,10.00000000,'���10������������������','0 0,10,20,30,40,50 * * * ?','2018-11-07 16:32:08'),(73,NULL,17,'USDT',0.2000,100.00000000,0.5000,50.00000000,'���10������������������','0 0,10,20,30,40,50 * * * ?','2018-11-07 17:29:26'),(74,NULL,17,'USDT',0.2000,80.00000000,0.5000,40.00000000,'���10������������������','0 0,10,20,30,40,50 * * * ?','2018-11-07 17:31:10'),(75,NULL,1,'USDT',0.1000,9998564.20145740,0.4000,3999425.68058296,'���10������������������','0 0,10,20,30,40,50 * * * ?','2018-11-07 18:07:42'),(76,NULL,3,'USDT',0.1000,3344.87783602,0.4000,1337.95113441,'���10������������������','0 0,10,20,30,40,50 * * * ?','2018-11-07 18:07:42'),(77,NULL,4,'USDT',0.1000,985.65000000,0.4000,394.26000000,'���10������������������','0 0,10,20,30,40,50 * * * ?','2018-11-07 18:07:42'),(78,NULL,6,'USDT',0.1000,10000.00000000,0.4000,4000.00000000,'���10������������������','0 0,10,20,30,40,50 * * * ?','2018-11-07 18:07:42'),(79,NULL,17,'USDT',0.1000,50.00000000,0.4000,20.00000000,'���10������������������','0 0,10,20,30,40,50 * * * ?','2018-11-07 18:07:42'),(80,NULL,19,'USDT',0.1000,199.76750000,0.4000,79.90700000,'���10������������������','0 0,10,20,30,40,50 * * * ?','2018-11-07 18:07:42'),(81,NULL,199,'USDT',0.1000,900.00406200,0.4000,360.00162480,'���10������������������','0 0,10,20,30,40,50 * * * ?','2018-11-07 18:07:42'),(82,'1518391147081834219',12,'BON',0.1000,10.00000000,0.1000,1.00000000,'���30���������','*/30 * * * * ?','2018-11-08 15:47:39'),(83,'1518391147081834219',17,'BON',0.1000,20.00000000,0.1000,2.00000000,'���30���������','*/30 * * * * ?','2018-11-08 15:47:39'),(84,'1518391147081834219',19,'BON',0.1000,1000.00000000,0.1000,100.00000000,'���30���������','*/30 * * * * ?','2018-11-08 15:47:39'),(85,'1618531105081834222',169,'BTC',0.7000,69.86000000,0.3000,20.95800000,'���10������������������','0 0,10,20,30,40,50 * * * ?','2018-11-08 16:05:53'),(86,'1618421150081983719',169,'BTC',0.7000,69.86000000,0.3000,20.95800000,'���10������������������','0 0,10,20,30,40,50 * * * ?','2018-11-08 16:50:42'),(87,'0918111148121983722',38,'USDT',1.0000,1000000.01000000,0.1000,100000.00100000,'���30���������','*/30 * * * * ?','2018-11-12 09:48:11'),(88,'0918231154121983725',38,'YT',1.0000,993783.96040000,0.1000,99378.39604000,'���30���������','*/30 * * * * ?','2018-11-12 09:54:23'),(89,'0918231154121983725',209,'YT',1.0000,1000000.00000000,0.1000,100000.00000000,'���30���������','*/30 * * * * ?','2018-11-12 09:54:23'),(90,'1018091114121983731',38,'ETH',1.0000,100000.00000000,0.2000,20000.00000000,'���30���������','*/30 * * * * ?','2018-11-12 10:14:09');
/*!40000 ALTER TABLE `t_lock_coin_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_market`
--

DROP TABLE IF EXISTS `t_market`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_market` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(10) NOT NULL DEFAULT '' COMMENT '������������_������������,������������',
  `coin_name` varchar(8) NOT NULL DEFAULT '' COMMENT '���������������������������������������������',
  `settlement_currency` varchar(8) NOT NULL COMMENT '������������������������������������������������������',
  `fee_coin` decimal(12,8) unsigned NOT NULL DEFAULT '0.00000000' COMMENT '���������������������������(������)���������������������������������������������������������������������������������������������������',
  `fee_currency` decimal(12,8) unsigned NOT NULL DEFAULT '0.00000000' COMMENT '���������������������������������������������������������������������������������������������������������������������������������������������*������������',
  `fee_coin_user_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '���������������������������������������������������������������������������',
  `fee_currency_user_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '���������������������������������������������������������������������������',
  `on_market_time` datetime DEFAULT NULL COMMENT '������������������������������������������������������������������������������������',
  `day_exchange_begin` time DEFAULT NULL COMMENT '������������������������',
  `day_exchange_end` time DEFAULT NULL COMMENT '������������������������',
  `last_day_price` decimal(24,8) unsigned DEFAULT '0.00000000' COMMENT '������������������������������',
  `max_increase` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '������������������������������������������������*100������������',
  `min_decrease` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '������������������������������������������������*100������������',
  `min_exchange_num` decimal(24,8) unsigned NOT NULL DEFAULT '0.00000000' COMMENT '���������������������������',
  `max_exchange_num` decimal(24,8) unsigned NOT NULL DEFAULT '0.00000000' COMMENT '���������������������������',
  `closed` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '���������������������0���������������������1������������������',
  `create_time` datetime NOT NULL COMMENT '������������������',
  `last_update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `coin_url` varchar(256) DEFAULT NULL COMMENT '������������������������',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_market_coin_current` (`coin_name`,`settlement_currency`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COMMENT='���������������������������������';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_market`
--

LOCK TABLES `t_market` WRITE;
/*!40000 ALTER TABLE `t_market` DISABLE KEYS */;
INSERT INTO `t_market` VALUES (15,'BTC_USDT','BTC','USDT',0.20000000,0.20000000,0,0,NULL,'00:00:00','23:59:59',3974.15000000,20000,10000,0.00010000,20000.00000000,0,'2018-07-13 17:44:58','2018-12-05 00:01:00','http://zcoinpro-1256505246.cos.ap-hongkong.myqcloud.com/e6e95a51a63780536a7bfc6214d9cbad.png'),(18,'ETH_USDT','ETH','USDT',0.06000000,0.08000000,0,0,NULL,'14:35:00','14:40:00',110.76000000,10000,10000,0.01000000,10000.00000000,0,'2018-07-31 18:11:05','2018-12-05 00:01:00',NULL),(23,'DG_YT','DG','YT',0.10000000,0.15000000,0,0,NULL,'00:05:00','23:55:00',1.60000000,1000,1000,2.00000000,5.00000000,0,'2018-09-03 14:38:46','2018-11-21 00:01:02','https://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/������������_20180903142501.png'),(24,'PKT_USDT','PKT','USDT',0.02000000,0.02000000,0,0,NULL,'00:00:00','23:55:55',10.00000000,100000000,100000000,1.00000000,10000.00000000,0,'2018-10-09 19:07:41','2018-11-17 17:45:53',NULL),(25,'YT_PKT','YT','PKT',0.02000000,0.02000000,0,0,NULL,'00:00:00','23:55:55',101.00000000,100000000,100000000,1.00000000,10000.00000000,0,'2018-10-10 10:49:44','2018-11-21 00:01:02',NULL),(26,'YT_USDT','YT','USDT',0.10000000,0.10000000,0,0,NULL,'00:00:00','23:55:55',1.50000000,10000,100000,1.00000000,100.00000000,0,'2018-10-10 11:50:56','2018-11-19 12:02:11',NULL),(28,'BTC_YT','BTC','YT',0.10000000,0.20000000,0,0,NULL,'00:00:00','23:00:00',55556.00000000,1000,1000,1.00000000,1000.00000000,0,'2018-11-19 12:05:17','2018-11-19 12:29:42',NULL),(29,'BON_ETH','BON','ETH',0.10000000,0.20000000,0,0,NULL,'00:00:00','23:55:00',1000.00000000,1000,1000,1.00000000,1000.00000000,0,'2018-11-19 17:25:32','2018-11-20 00:01:02','https://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/1.png');
/*!40000 ALTER TABLE `t_market` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_market_group_config`
--

DROP TABLE IF EXISTS `t_market_group_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_market_group_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '������',
  `market_id` int(11) NOT NULL COMMENT '������������������������Id',
  `group_id` int(11) NOT NULL COMMENT '���������������������id',
  `buy_con_value` varchar(20) DEFAULT '0' COMMENT '������������������',
  `remark` varchar(50) DEFAULT NULL COMMENT '������',
  `sell_con_value` varchar(20) DEFAULT '0' COMMENT '���������������',
  `coin_name` varchar(20) DEFAULT NULL COMMENT '���������������������',
  `settlement_currency` varchar(20) DEFAULT NULL COMMENT '���������������������',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_market_group_config`
--

LOCK TABLES `t_market_group_config` WRITE;
/*!40000 ALTER TABLE `t_market_group_config` DISABLE KEYS */;
INSERT INTO `t_market_group_config` VALUES (1,15,1,'50',NULL,'50','BTC','USDT'),(2,15,2,'20',NULL,'20','BTC','USDT'),(3,18,1,'1',NULL,'1','ETH','USDT'),(4,15,3,'10',NULL,'10','BTC','USDT'),(5,21,5,'1',NULL,'3','YT','DG'),(8,15,5,'1',NULL,'1','BTC','USDT'),(9,15,6,'1',NULL,'1','BTC','USDT'),(11,23,5,'2',NULL,'3','DG','YT'),(12,25,2,'1.20',NULL,'3.2','YT','PKT');
/*!40000 ALTER TABLE `t_market_group_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_merchant`
--

DROP TABLE IF EXISTS `t_merchant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_merchant` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) unsigned NOT NULL COMMENT '���������������id',
  `name` varchar(50) NOT NULL COMMENT '������/���������������������',
  `phone` varchar(20) NOT NULL DEFAULT '' COMMENT '������',
  `email` varchar(50) DEFAULT NULL COMMENT '������/������������������������������',
  `type` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '������������������1���������������������2������������������',
  `status` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '���������0������������1��������������� 2:���������3:������������',
  `working_status` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '������������������������������������������������������1������������������0���������������������',
  `bank_type` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '���������������0������������������1���������������2���������������',
  `bank_user` varchar(32) DEFAULT NULL COMMENT '������������������������',
  `bank_name` varchar(40) DEFAULT NULL COMMENT '���������������������',
  `bank_no` varchar(50) DEFAULT NULL COMMENT '������������������������',
  `add_time` int(11) NOT NULL COMMENT '������������',
  `reason` varchar(255) DEFAULT NULL COMMENT '������������',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`),
  KEY `fk_merchant_user_id_idx` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='������/������������������������';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_merchant`
--

LOCK TABLES `t_merchant` WRITE;
/*!40000 ALTER TABLE `t_merchant` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_merchant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_merchant_bill`
--

DROP TABLE IF EXISTS `t_merchant_bill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_merchant_bill` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `merchant_id` int(11) unsigned NOT NULL,
  `coin_name` varchar(8) DEFAULT NULL COMMENT '���������������������������������������������',
  `sub_type` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '������������0���������������������������������������1������������������������������������',
  `reason` varchar(8) NOT NULL COMMENT '���������������������������received:���������������������send:���������',
  `change_amount` decimal(32,8) DEFAULT NULL COMMENT ' ������������������������������������������������������������������',
  `comment` varchar(100) DEFAULT NULL COMMENT '���������������������������������������������������������������������',
  `last_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_user_bill_merchant_id_idx` (`merchant_id`),
  KEY `last_time` (`last_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='���������������������������������������������������������������';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_merchant_bill`
--

LOCK TABLES `t_merchant_bill` WRITE;
/*!40000 ALTER TABLE `t_merchant_bill` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_merchant_bill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_merchant_coin`
--

DROP TABLE IF EXISTS `t_merchant_coin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_merchant_coin` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `merchant_id` int(11) unsigned NOT NULL COMMENT '������id',
  `coin_name` varchar(8) NOT NULL COMMENT '������������',
  `available_balance` decimal(32,8) NOT NULL DEFAULT '0.00000000' COMMENT '������������',
  `freeze_balance` decimal(32,8) NOT NULL DEFAULT '0.00000000' COMMENT '���������������',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '���������������������0������������1���������������',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_user_coin` (`merchant_id`,`coin_name`),
  CONSTRAINT `fk_merchant_id` FOREIGN KEY (`merchant_id`) REFERENCES `t_merchant` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='������������������������';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_merchant_coin`
--

LOCK TABLES `t_merchant_coin` WRITE;
/*!40000 ALTER TABLE `t_merchant_coin` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_merchant_coin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_merchant_coin_conf`
--

DROP TABLE IF EXISTS `t_merchant_coin_conf`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_merchant_coin_conf` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `coin_name` varchar(8) NOT NULL DEFAULT '' COMMENT '������������',
  `cny_price` decimal(24,8) unsigned NOT NULL DEFAULT '0.00000000' COMMENT '���������������',
  `dollar_price` decimal(24,8) unsigned NOT NULL DEFAULT '0.00000000' COMMENT '������������',
  `hkdollar_price` decimal(24,8) unsigned NOT NULL DEFAULT '0.00000000' COMMENT '������������',
  `order_min_amount` decimal(24,8) unsigned NOT NULL DEFAULT '0.00000000' COMMENT '���������������������������������',
  `order_max_amount` decimal(24,8) unsigned NOT NULL DEFAULT '9999999999999.00000000' COMMENT '���������������������������������',
  `fee_rate` decimal(6,4) unsigned NOT NULL DEFAULT '0.0000' COMMENT '���������������������1,0.2������20%',
  `status` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '0���������������1������������',
  PRIMARY KEY (`id`),
  UNIQUE KEY `coin_name` (`coin_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='������������������������������';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_merchant_coin_conf`
--

LOCK TABLES `t_merchant_coin_conf` WRITE;
/*!40000 ALTER TABLE `t_merchant_coin_conf` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_merchant_coin_conf` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_mining_stat`
--

DROP TABLE IF EXISTS `t_mining_stat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_mining_stat` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stat_time` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '���������������������������������������������������������',
  `trade_bonus` decimal(32,8) unsigned NOT NULL DEFAULT '0.00000000' COMMENT '���������������������������������������������(���������������)���������������������������������������������',
  `trade_rec_bonus` decimal(32,8) unsigned DEFAULT NULL COMMENT '������������������������������������������������������������������������������������������������',
  `other_bonus` decimal(32,8) unsigned NOT NULL DEFAULT '0.00000000' COMMENT '���������������������������������������������������������������',
  PRIMARY KEY (`id`),
  UNIQUE KEY `stat_time` (`stat_time`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='���������������������������������������';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_mining_stat`
--

LOCK TABLES `t_mining_stat` WRITE;
/*!40000 ALTER TABLE `t_mining_stat` DISABLE KEYS */;
INSERT INTO `t_mining_stat` VALUES (1,1535040000,0.00000000,0.00000000,1000.00000000),(2,1535299200,0.00000000,0.00000000,200.00000000),(3,1535385600,0.00000000,0.00000000,100.00000000),(4,1535558400,0.00000000,0.00000000,100.00000000),(5,1535644800,0.00000000,0.00000000,100.00000000),(6,1535731200,0.00000000,0.00000000,100.00000000),(7,1535904000,0.00000000,0.00000000,100.00000000);
/*!40000 ALTER TABLE `t_mining_stat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_national_code`
--

DROP TABLE IF EXISTS `t_national_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_national_code` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `national_name` varchar(50) DEFAULT NULL,
  `chinese_name` varchar(50) DEFAULT NULL,
  `abbre` varchar(10) DEFAULT NULL,
  `code` varchar(20) DEFAULT NULL,
  `land` varchar(255) DEFAULT NULL,
  `price` decimal(10,4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=239 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_national_code`
--

LOCK TABLES `t_national_code` WRITE;
/*!40000 ALTER TABLE `t_national_code` DISABLE KEYS */;
INSERT INTO `t_national_code` VALUES (1,'Afghanistan','���������','AF','93','������',1.1430),(2,'Albania','���������������','AL','355','������',0.3410),(3,'Algeria','���������������','DZ','213','������',0.9830),(4,'American Samoa','���������','AS','684','���������',0.7900),(5,'Andorra','������������������','AD','376','������',0.2020),(6,'Angola','���������','AO','244','������',0.5660),(7,'Anguilla','������������','AI','1264','������',0.5010),(8,'Antarctica','���������','AQ','672','���������',NULL),(9,'Antigua and Barbuda','���������������������','AG','1268','������',0.5010),(10,'Argentina','���������','AR','54','������',0.2770),(11,'Armenia','������������','AM','374','������',0.4210),(12,'Aruba','���������','AW','297','������',0.5010),(13,'Australia','������������','AU','61','���������',0.5180),(14,'Austria','���������','AT','43','������',0.9190),(15,'Azerbaijan','������������','AZ','994','������',1.1840),(16,'Bahamas','���������','BS','1242','������',0.2610),(17,'Bahrain','������','BH','973','������',0.3090),(18,'Bangladesh','������������','BD','880','������',0.7420),(19,'Barbados','������������','BB','1246','������',0.5010),(20,'Belarus','������������','BY','375','������',0.1640),(21,'Belgium','���������','BE','32','������',0.5820),(22,'Belize','������������','BZ','501','������',0.3410),(23,'Benin','������','BJ','229','������',0.6620),(24,'Bermuda','���������','BM','1441','������',0.3410),(25,'Bhutan','������','BT','975','������',1.1430),(26,'Bolivia','������������','BO','591','������',0.8550),(27,'Bosnia and Herzegovina','������������������������������','BA','387','������',0.2610),(28,'Botswana','������������','BW','267','������',0.8220),(29,'Brazil','������','BR','55','������',0.2450),(30,'British Indian Ocean Territory','���������������������','IO','246','������',1.1430),(31,'Brunei Darussalam','���������������������','BN','673','������',0.3410),(32,'Bulgaria','������������','BG','359','������',0.9190),(33,'Burkina Faso','���������������','BF','226','������',0.3410),(34,'Burundi','���������','BI','257','������',1.1430),(35,'Cambodia','���������','KH','855','������',0.3940),(36,'Cameroon','���������','CM','237','������',0.6620),(37,'Canada','���������','CA','1','������',0.2130),(38,'Cape Verde','���������','CV','238','������',0.9830),(39,'Cayman Islands','������������','KY','1345','������',0.5010),(40,'Central African Republic','���������������','CF','236','������',0.4050),(41,'Chad','������','TD','235','������',0.4210),(42,'Chile','������','CL','56','������',0.5500),(43,'China','������','CN','86','������',0.0700),(44,'Christmas Island','���������','CX','61','������',0.5180),(45,'Cocos (Keeling) Islands','���������������','CC','61','���������',0.5180),(46,'Colombia','������������','CO','57','������',0.2900),(47,'Comoros','���������','KM','269','������',0.5980),(48,'Congo','������','CG','242','������',0.7110),(49,'Congo, The Democratic Republic Of The','���������������������','ZR','243','������',1.0940),(50,'Cook Islands','������������','CK','682','���������',0.4050),(51,'Costa Rica','���������������','CR','506','������',0.4530),(52,'Cote D\'Ivoire','Cote D\'Ivoire','CI','225','������',0.6620),(53,'Croatia (local name: Hrvatska)','������������','HR','385','������',0.6620),(54,'Cuba','������','CU','53','������',0.2450),(55,'Cyprus','������������','CY','357','������',0.7420),(56,'Czech Republic','������','CZ','420','������',0.6140),(57,'Denmark','������','DK','45','������',0.2770),(58,'Djibouti','���������','DJ','253','������',1.0790),(59,'Dominica','���������������','DM','1767','������',0.4800),(60,'Dominican Republic','���������������������','DO','1849','������',NULL),(61,'East Timor','���������','TP','670','������',1.1430),(62,'Ecuador','������������','EC','593','������',1.3040),(63,'Egypt','������','EG','20','������',0.5010),(64,'El Salvador','������������','SV','503','������',0.5010),(65,'Equatorial Guinea','���������������','GQ','240','������',0.3410),(66,'Eritrea','������������������','ER','291','������',3.2300),(67,'Estonia','������������','EE','372','������',0.6620),(68,'Ethiopia','���������������','ET','251','������',0.3410),(69,'Falkland Islands (Malvinas)','���������������','FK','5','������',0.2610),(70,'Faroe Islands','������������','FO','298','������',0.3410),(71,'Fiji','������','FJ','679','���������',0.5820),(72,'Finland','������','FI','358','������',0.6780),(73,'France','������','FR','33','������',0.7900),(74,'France Metropolitan','���������������','FX','33','������',0.7900),(75,'French Guiana','���������������','GF','594','������',1.3040),(76,'French Polynesia','���������������������','PF','689','���������',0.8170),(77,'Gabon','������','GA','241','������',0.3570),(78,'Gambia',' ���������','GM','220','������',0.4050),(79,'Georgia','������������','GE','995','������',1.1430),(80,'Germany','������','DE','49','������',0.5180),(81,'Ghana','������','GH','233','������',0.5820),(82,'Gibraltar','������������','GI','350','������',0.3250),(83,'Greece','������','GR','30','������',0.6300),(84,'Greenland','���������','GL','45','������',0.2770),(85,'Grenada','������������','GD','1473','������',0.5010),(86,'Guadeloupe','���������������','GP','590','������',1.3040),(87,'Guam','������','GU','1671','���������',1.3040),(88,'Guatemala','������������','GT','502','������',0.6620),(89,'Guinea','���������','GN','224','���������',1.1430),(90,'Guinea-Bissau','���������������','GW','245','������',0.6620),(91,'Guyana','���������','GY','592','������',0.5340),(92,'Haiti','������','HT','509','������',0.8220),(93,'Honduras','������������','HN','504','������',0.3410),(94,'Hong Kong','������','HK','852','������',0.4200),(95,'Hungary','���������','HU','36','������',0.7260),(96,'Iceland','������','IS','354','������',0.4210),(97,'India','������','IN','91','������',0.1810),(98,'Indonesia','���������������','ID','62','������',0.3730),(99,'Iran (Islamic Republic of)','������������������������������','IR','98','������',1.1430),(100,'Iraq','���������','IQ','964','������',0.8220),(101,'Ireland','���������','IE','353','������',0.6300),(102,'Israel','���������','IL','972','������',0.3090),(103,'Italy','���������','IT','39','������',0.6300),(104,'Jamaica','���������','JM','1876','������',0.5010),(105,'Japan','������','JP','81','������',0.5970),(106,'Jordan','������','JO','962','������',0.2610),(107,'Kazakhstan','���������','KZ','7','������',0.3410),(108,'Kenya','���������','KE','254','������',0.2130),(109,'Kuwait','���������','KW','965','������',0.6300),(110,'Kyrgyzstan','������������','KG','996','������',0.3410),(111,'Latvia','������������','LV','371','������',0.5180),(112,'Lebanon','���������','LB','961','������',0.4050),(113,'Lesotho','���������','LS','266','������',0.4050),(114,'Liberia','������������','LR','231','������',0.9830),(115,'Libyan Arab Jamahiriya','���������','LY','218','������',0.5340),(116,'Liechtenstein','���������������','LI','423','������',0.4210),(117,'Lithuania','���������','LT','370','������',0.1970),(118,'Luxembourg','���������','LU','352','������',0.4050),(119,'Macau','������������','MO','853','������',0.5820),(120,'Madagascar','���������������','MG','261','������',0.4210),(121,'Malawi','���������','MW','265','������',0.5010),(122,'Malaysia','������������','MY','60','������',0.5820),(123,'Maldives','������������','MV','960','������',0.1820),(124,'Mali','������','ML','223','������',0.3570),(125,'Malta','���������','MT','356','������',0.3410),(126,'Marshall Islands','���������������','MH','692','���������',3.2300),(127,'Martinique','���������������','MQ','596','������',1.3040),(128,'Mauritania','���������������','MR','222','������',1.1430),(129,'Mauritius','������������','MU','230','������',1.1430),(130,'Mayotte','���������','YT','262','������',0.5570),(131,'Mexico','���������','MX','52','������',0.3410),(132,'Micronesia','������������������','FM','691','���������',0.6620),(133,'Moldova','������������','MD','373','������',0.9030),(134,'Monaco','���������','MC','377','������',0.5010),(135,'Mongolia','���������','MN','976','������',0.5010),(136,'Montenegro','���������������','MNE','382','������',0.2450),(137,'Montserrat','���������������','MS','1664','������',0.2610),(138,'Morocco','���������','MA','212','������',0.5820),(139,'Mozambique','������������','MZ','258','������',0.5010),(140,'Myanmar','������','MM','95','������',0.9830),(141,'Namibia','������������','NA','264','������',0.5500),(142,'Nauru','������','NR','674','���������',0.5010),(143,'Nepal','���������','NP','977','������',0.9030),(144,'Netherlands','������','NL','31','������',0.8220),(145,'Netherlands Antilles','������������������������','AN','599','������',0.4800),(146,'New Caledonia','������������������','NC','687','���������',0.8710),(147,'New Zealand','���������','NZ','64','���������',0.9830),(148,'Nicaragua','������������','NI','505','������',0.4210),(149,'Niger','���������','NE','227','������',1.2240),(150,'Nigeria','������������','NG','234','������',0.5010),(151,'Norfolk Island','������������','NF','6723','���������',0.7420),(152,'North Korea','������','KP','850','������',0.5340),(153,'Northern Mariana Islands','���������������������','MP','1670','���������',0.5110),(154,'Norway','������','NO','47','������',0.5340),(155,'Oman','������','OM','968','������',0.3570),(156,'Pakistan','������������','PK','92','������',0.2450),(157,'Palau','������','PW','680','���������',0.2610),(158,'Palestine','������������','PS','970','������',0.4210),(159,'Panama','���������','PA','507','������',0.5010),(160,'Papua New Guinea','���������������������','PG','675','���������',0.4210),(161,'Paraguay','���������','PY','595','������',0.3090),(162,'Peru','������','PE','51','������',0.3730),(163,'Philippines','������������������','PH','63','������',0.2610),(164,'Pitcairn','���������������','PN','64','���������',0.9830),(165,'Poland','������','PL','48','������',0.3410),(166,'Portugal','���������','PT','351','������',0.4050),(167,'Puerto Rico','������������','PR','1787','������',0.3890),(168,'Qatar','���������','QA','974','������',0.4370),(169,'Reunion','������������','RE','262','������',0.5570),(170,'Romania','������������','RO','40','������',1.1430),(171,'Russian Federation','���������������','RU','7','������',0.3410),(172,'Rwanda','���������','RW','250','������',0.3730),(173,'Samoa','���������������','WS','685','���������',0.6620),(174,'San Marino','���������������������','SM','378','������',0.8220),(175,'Saudi Arabia','���������������','SA','966','������',0.3570),(176,'Senegal','������������','SN','221','������',0.7420),(177,'Serbia','���������������������','SRB','381','������',0.2650),(178,'Seychelles','���������','SC','248','������',0.3890),(179,'Sierra Leone','������������','SL','232','������',1.1430),(180,'Singapore','���������','SG','65','������',0.4340),(181,'Slovakia (Slovak Republic)','���������������������������������������������','SK','421','������',0.5180),(182,'Slovenia','���������������','SI','386','������',0.2770),(183,'Solomon Islands','���������������','SB','677','���������',0.3410),(184,'Somalia','���������','SO','252','������',0.9190),(185,'South Africa','������','ZA','27','������',0.3410),(186,'South Korea','������','KR','82','������',0.5340),(187,'Spain','���������','ES','34','������',0.9830),(188,'Sri Lanka','������������','LK','94','������',0.3410),(189,'Sudan','������','SD','249','������',1.1430),(190,'Suriname','���������','SR','597','������',0.6620),(191,'Swaziland','������������','SZ','268','������',0.4210),(192,'Sweden','������','SE','46','������',0.7740),(193,'Switzerland','������','CH','41','������',0.5660),(194,'Syrian Arab Republic','���������','SY','963','������',0.9190),(195,'Taiwan','������������','TW','886','������',0.4800),(196,'Tajikistan','���������','TJ','992','������',0.2290),(197,'Tanzania','������������','TZ','255','������',1.1430),(198,'Thailand','������','TH','66','������',0.2610),(199,'Togo','������','TG','228','������',0.4370),(200,'Tokelau','���������','TK','690','���������',3.2300),(201,'Tonga','������','TO','676','���������',0.5820),(202,'Trinidad and Tobago','������������������������','TT','1868','������',0.5010),(203,'Tunisia','���������','TN','216','������',0.9030),(204,'Turkey','���������','TR','90','������',0.9430),(205,'Turkmenistan','���������','TM','993','������',0.1640),(206,'Turks and Caicos Islands','���������������������������','TC','1809','������',0.5010),(207,'Tuvalu','���������','TV','688','���������',3.2300),(208,'Uganda','���������','UG','256','������',1.8710),(209,'Ukraine','���������','UA','380','������',0.2450),(210,'United Arab Emirates','������������������������','AE','971','������',0.3980),(211,'United Kingdom','������','UK','44','������',0.4690),(212,'United States','������','US','1','������',0.2130),(213,'Uruguay','���������','UY','598','������',0.5340),(214,'Uzbekistan','������������������','UZ','998','������',0.8060),(215,'Vanuatu','������������','VU','678','���������',0.5820),(216,'Vatican City State (Holy See)','���������(������������)','VA','39','������',0.6300),(217,'Venezuela','������������','VE','58','������',0.3090),(218,'Vietnam','������','VN','84','������',0.5820),(219,'Virgin Islands (British)','���������������(������)','VG','1284','������',NULL),(220,'Virgin Islands (U.S.)','���������������(������)','VI','1340','������',0.5010),(221,'Wallis And Futuna Islands','���������������������������','WF','681','���������',3.2300),(222,'Western Sahara','������������','EH','685','������',0.6620),(223,'Yemen','������','YE','967','������',0.4530),(224,'Yugoslavia','������������','YU','381','������',0.2650),(225,'Zambia','���������','ZM','260','������',0.3250),(226,'Zimbabwe','������������','ZW','263','������',0.4050),(227,'the Republic of Abkhazia','������������','ABH','7','������',0.3410),(228,'the Republic of South Ossetia','������������','','7','������',0.3410),(229,'Bailiwick of Guernsey','������������','','441481','������',NULL),(230,'Bailiwick of Jersey','���������','','44','������',0.4690),(231,'Lao People\'s Democratic Republic','������','LAO','856','������',0.5340),(232,'The Republic of Macedonia','���������','MKD','389','������',0.2450),(233,'The Federation of Saint Kitts and Nevis','���������������������','KNA','1869','������',0.5010),(234,'Santa Luzia Island','���������������','','1758','������',0.4800),(235,'Saint Vincent and the Grenadines','������������������������������','VCT','1784','������',0.5010),(236,'Sao Tome and Principe','������������������������','STP','239','������',0.6620),(237,'Saint Martin','������������','','590','������',1.3040),(238,'The Republic of South Sudan','������������������','SSD','211','������',0.6620);
/*!40000 ALTER TABLE `t_national_code` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_order_complaint`
--

DROP TABLE IF EXISTS `t_order_complaint`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_order_complaint` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) unsigned NOT NULL,
  `order_id` int(11) unsigned NOT NULL COMMENT '������id',
  `type` int(1) unsigned NOT NULL COMMENT '���������������1������������������2������������������',
  `credential_urls` varchar(560) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '���������������������������',
  `credential_comment` varchar(400) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '���������������������������',
  `status` int(1) unsigned NOT NULL DEFAULT '0' COMMENT '���������0���������������1������������',
  `add_time` int(11) unsigned NOT NULL COMMENT '������������',
  `finish_time` int(11) DEFAULT NULL COMMENT '������������',
  PRIMARY KEY (`id`),
  KEY `fk_order_comp_user_id_idx` (`user_id`),
  KEY `fk_order_comp_order_id_idx` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='���������������������������������������������������';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_order_complaint`
--

LOCK TABLES `t_order_complaint` WRITE;
/*!40000 ALTER TABLE `t_order_complaint` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_order_complaint` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_otc`
--

DROP TABLE IF EXISTS `t_otc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_otc` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '������ID',
  `user_name` varchar(32) DEFAULT NULL COMMENT '���������',
  `appl_no` varchar(64) DEFAULT NULL COMMENT '������',
  `type` int(11) NOT NULL COMMENT '1���2���',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '������',
  `coin_name` varchar(10) NOT NULL COMMENT '������',
  `legal_name` varchar(10) DEFAULT 'cny' COMMENT '������',
  `amount` decimal(32,8) DEFAULT '0.00000000' COMMENT '���������',
  `price` decimal(32,8) DEFAULT '0.00000000' COMMENT '������',
  `fee_rate` decimal(32,8) DEFAULT '0.00000000' COMMENT '���������������������',
  `fee` decimal(32,8) DEFAULT '0.00000000' COMMENT '���������(���������������)',
  `amount_frozen` decimal(32,8) DEFAULT '0.00000000' COMMENT '���������(������������),���������������������������',
  `amount_success` decimal(32,8) DEFAULT '0.00000000' COMMENT '���������������������',
  `amount_all` decimal(32,8) DEFAULT '0.00000000' COMMENT '������������',
  `comment` varchar(256) DEFAULT NULL COMMENT '������',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `min_amount` decimal(32,8) DEFAULT '0.00000000' COMMENT '������������������������',
  `max_amount` decimal(32,8) DEFAULT '0.00000000' COMMENT '������������������������',
  PRIMARY KEY (`id`),
  KEY `idx_user_id_type_status` (`user_id`,`status`,`type`)
) ENGINE=InnoDB AUTO_INCREMENT=240 DEFAULT CHARSET=utf8 COMMENT='c2c���������������������������������������������';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_otc`
--

LOCK TABLES `t_otc` WRITE;
/*!40000 ALTER TABLE `t_otc` DISABLE KEYS */;
INSERT INTO `t_otc` VALUES (1,3,'18617208190','1518310840241039419',1,100,'ETH','cny',5.00000000,10.00000000,0.01000000,0.05000000,0.00000000,0.00000000,5.00000000,NULL,'2018-08-24 15:40:31','2018-09-01 11:09:18',1.00000000,50.00000000),(2,3,'18617208190','1518190844241039422',1,100,'ETH','cny',9.00000000,1.00000000,0.01000000,0.10000000,0.00000000,1.00000000,10.00000000,NULL,'2018-08-24 15:44:19','2018-10-15 11:30:18',1.00000000,10.00000000),(3,3,'18617208190','1518240846241039425',1,100,'USDT','cny',1.00000000,50.00000000,0.02000000,0.02000000,0.00000000,0.00000000,1.00000000,NULL,'2018-08-24 15:46:24','2018-08-29 11:07:40',1.00000000,50.00000000),(4,4,'987@qq.com','1518010850241039428',2,1,'ETH','cny',0.00000000,1.00000000,0.01000000,0.01000000,0.00000000,1.00000000,1.00000000,'','2018-08-24 15:50:01','2018-08-24 15:50:49',1.00000000,10.00000000),(5,3,'18617208190','1518520847271103819',1,100,'ETH','cny',1.00000000,1.00000000,0.01000000,0.01000000,0.00000000,0.00000000,1.00000000,NULL,'2018-08-27 15:47:52','2018-08-29 11:07:42',0.50000000,1.00000000),(6,3,'18617208190','1118460805291004619',1,100,'ETH','cny',10.00000000,1.00000000,0.01000000,0.10000000,0.00000000,0.00000000,10.00000000,NULL,'2018-08-29 11:05:46','2018-08-29 11:07:43',1.00000000,10.00000000),(7,3,'18617208190','1118510806291004622',1,100,'ETH','cny',1.00000000,15.98000000,0.01000000,0.01000000,0.00000000,0.00000000,1.00000000,NULL,'2018-08-29 11:06:51','2018-10-15 11:30:16',10.00000000,15.98000000),(8,3,'18617208190','1118440811291004625',2,100,'ETH','cny',1.00000000,10.00000000,0.01000000,0.01000000,0.00000000,0.00000000,1.00000000,NULL,'2018-08-29 11:11:44','2018-10-15 11:30:13',1.00000000,10.00000000),(9,3,'18617208190','1118020812291004628',2,100,'BTC','cny',10.00000000,12.00000000,0.01000000,0.10000000,0.00000000,0.00000000,10.00000000,NULL,'2018-08-29 11:12:02','2018-10-15 11:29:43',1.00000000,120.00000000),(10,3,'18617208190','1118310813291004631',1,100,'ETH','cny',1.00000000,999.00000000,0.01000000,0.01000000,0.00000000,0.00000000,1.00000000,NULL,'2018-08-29 11:13:31','2018-08-29 11:13:40',10.00000000,999.00000000),(11,3,'18617208190','1118170814291004634',2,100,'BTC','cny',0.36346348,999.00000000,0.01000000,0.01000000,0.62052052,0.01601600,1.00000000,NULL,'2018-08-29 11:14:17','2018-10-15 11:29:40',10.00000000,999.00000000),(12,4,'987@qq.com','1118370825291004637',1,1,'BTC','cny',0.00000000,999.00000000,0.01000000,0.00016016,0.00000000,0.01601600,0.01601600,NULL,'2018-08-29 11:25:37','2018-08-29 11:26:04',10.00000000,999.00000000),(13,4,'987@qq.com','1118290826291004643',1,100,'BTC','cny',0.98398400,999.00000000,0.01000000,0.00983984,0.00000000,0.00000000,0.98398400,NULL,'2018-08-29 11:26:29','2018-08-29 11:26:31',10.00000000,999.00000000),(14,4,'987@qq.com','1118220835291004649',2,100,'ETH','cny',1.00000000,15.98000000,0.01000000,0.01000000,0.00000000,0.00000000,1.00000000,'','2018-08-29 11:35:22','2018-08-29 11:35:28',10.00000000,15.98000000),(15,3,'18617208190','1118500836291004655',1,100,'ETH','cny',0.50000000,666.00000000,0.01000000,0.00500000,0.00000000,0.00000000,0.50000000,NULL,'2018-08-29 11:36:50','2018-10-15 11:30:11',6.66000000,7.00000000),(16,3,'18617208190','1118510837291004658',1,100,'ETH','cny',0.01600000,999.00000000,0.01000000,0.00016000,0.00000000,0.00000000,0.01600000,NULL,'2018-08-29 11:37:51','2018-10-15 11:30:08',10.00000000,15.98000000),(17,4,'987@qq.com','1218310803291004661',1,100,'USDT','cny',0.01600000,999.00000000,0.02000000,0.00032000,0.00000000,0.00000000,0.01600000,NULL,'2018-08-29 12:03:31','2018-10-15 11:29:21',10.00000000,15.98000000),(18,4,'987@qq.com','1218360808291004664',1,100,'BTC','cny',0.98398400,999.00000000,0.01000000,0.00983984,0.00000000,0.00000000,0.98398400,NULL,'2018-08-29 12:08:36','2018-08-29 12:08:39',10.00000000,999.00000000),(19,3,'18617208190','1018090848301004670',2,100,'USDT','cny',0.01599599,999.00000000,0.02000000,0.00031992,0.00000000,0.00000000,0.01599599,'','2018-08-30 10:48:09','2018-08-30 10:48:30',10.00000000,15.98000000),(20,19,'18612341234','1218350807301004676',1,100,'ETH','cny',0.01100000,666.00000000,0.01000000,0.00011000,0.00000000,0.00000000,0.01100000,NULL,'2018-08-30 12:07:35','2018-10-15 11:30:05',6.66000000,7.32000000),(21,19,'18612341234','1218170811301004679',1,100,'BTC','cny',0.01561561,999.00000000,0.01000000,0.00015616,0.00000000,0.00000000,0.01561561,NULL,'2018-08-30 12:11:17','2018-08-30 12:42:06',10.00000000,999.00000000),(22,5,'15200905810','1618550808301004685',1,100,'BTC','cny',0.10010010,999.00000000,0.01000000,0.00100100,0.00000000,0.00000000,0.10010010,NULL,'2018-08-30 16:08:55','2018-08-30 16:39:07',10.00000000,999.00000000),(23,3,'18617208190','1818040810301004691',1,100,'USDT','cny',0.55000000,999.00000000,0.02000000,0.01100000,0.00000000,0.00000000,0.55000000,NULL,'2018-08-30 18:10:04','2018-10-15 11:29:17',50.00000000,500.00000000),(24,3,'18617208190','1818290810301004694',1,100,'BTC','cny',0.88800000,666.00000000,0.01000000,0.00888000,0.00000000,0.00000000,0.88800000,NULL,'2018-08-30 18:10:29','2018-10-15 11:29:37',30.00000000,591.00000000),(25,3,'18617208190','1818540810301004697',2,100,'USDT','cny',0.88000000,299.00000000,0.02000000,0.01760000,0.00000000,0.00000000,0.88000000,NULL,'2018-08-30 18:10:54','2018-10-15 11:29:13',20.00000000,263.00000000),(26,5,'15200905810','16183908563110046100',1,2,'BTC','cny',0.00000000,999.00000000,0.01000000,0.00100100,0.10010010,0.00000000,0.10010010,NULL,'2018-08-31 16:56:39','2018-08-31 16:56:39',10.00000000,999.00000000),(27,5,'15200905810','16184409100110046106',1,2,'BTC','cny',0.00000000,999.00000000,0.01000000,0.00400400,0.40040040,0.00000000,0.40040040,NULL,'2018-09-01 16:10:44','2018-09-01 16:10:44',10.00000000,999.00000000),(28,5,'15200905810','1018121002081073419',1,2,'BTC','cny',0.00000000,999.00000000,0.01000000,0.00100000,0.10000000,0.00000000,0.10000000,NULL,'2018-10-08 10:02:12','2018-10-08 10:02:12',10.00000000,999.00000000),(29,170,'15220167102','1318001025121749819',2,100,'usdt','CNY',30.00000000,1.20000000,0.02000000,0.60000000,0.00000000,0.00000000,30.00000000,NULL,'2018-10-12 13:25:00','2018-10-15 11:29:07',1.20000000,30.00000000),(30,169,'15220167101','1418151011121749822',1,100,'usdt','CNY',1.00000000,1.20000000,0.02000000,0.02000000,0.00000000,0.00000000,1.00000000,NULL,'2018-10-12 14:11:15','2018-10-12 14:41:55',1.20000000,30.00000000),(31,169,'15220167101','1418501028121749828',1,100,'usdt','CNY',2.00000000,1.20000000,0.02000000,0.04000000,0.00000000,0.00000000,2.00000000,NULL,'2018-10-12 14:28:50','2018-10-12 14:53:52',1.20000000,30.00000000),(32,12,'13823774714','1518491036121749834',1,100,'usdt','CNY',2.50000000,1.20000000,0.02000000,0.05000000,0.00000000,0.00000000,2.50000000,NULL,'2018-10-12 15:36:49','2018-10-12 16:07:39',1.20000000,30.00000000),(33,169,'15220167101','1518381051121749840',1,100,'usdt','CNY',1.00000000,1.20000000,0.02000000,0.02000000,0.00000000,0.00000000,1.00000000,NULL,'2018-10-12 15:51:38','2018-10-12 16:22:35',1.20000000,30.00000000),(34,169,'15220167101','1518081057121749846',1,100,'usdt','CNY',1.00000000,1.20000000,0.02000000,0.02000000,0.00000000,0.00000000,1.00000000,NULL,'2018-10-12 15:57:08','2018-10-12 15:58:00',1.20000000,30.00000000),(35,170,'15220167102','1518151059121749852',1,100,'usdt','CNY',30.00000000,1.30000000,0.02000000,0.60000000,0.00000000,0.00000000,30.00000000,NULL,'2018-10-12 15:59:15','2018-10-15 11:29:01',1.30000000,39.00000000),(36,12,'13823774714','1618081005121749855',1,100,'usdt','CNY',2.50000000,1.20000000,0.02000000,0.05000000,0.00000000,0.00000000,2.50000000,NULL,'2018-10-12 16:05:08','2018-10-12 16:35:16',1.20000000,30.00000000),(37,12,'13823774714','1618081007121749861',1,100,'usdt','CNY',2.50000000,1.20000000,0.02000000,0.05000000,0.00000000,0.00000000,2.50000000,NULL,'2018-10-12 16:07:08','2018-10-12 16:37:16',1.20000000,30.00000000),(38,12,'13823774714','1618171011121749867',1,100,'usdt','CNY',2.50000000,1.20000000,0.02000000,0.05000000,0.00000000,0.00000000,2.50000000,NULL,'2018-10-12 16:11:17','2018-10-12 16:41:30',1.20000000,30.00000000),(40,170,'15220167102','1618411014121749876',2,100,'eth','CNY',27.00000000,1.10000000,0.01000000,0.30000000,1.00000000,2.00000000,30.00000000,NULL,'2018-10-12 16:14:41','2018-10-15 11:30:02',1.10000000,33.00000000),(41,170,'15220167102','1618051015121749879',1,100,'eth','CNY',28.00000000,1.30000000,0.01000000,0.30000000,1.00000000,1.00000000,30.00000000,NULL,'2018-10-12 16:15:05','2018-10-15 11:29:59',1.30000000,39.00000000),(42,169,'15220167101','1618371016121749882',1,100,'eth','CNY',29.99999900,1.10000000,0.01000000,0.29999999,0.00000000,0.00000000,29.99999900,NULL,'2018-10-12 16:16:37','2018-10-12 16:16:44',1.10000000,33.00000000),(43,169,'15220167101','1618131017121749888',1,100,'eth','CNY',1.00000000,1.10000000,0.01000000,0.01000000,0.00000000,0.00000000,1.00000000,NULL,'2018-10-12 16:17:13','2018-10-12 16:47:58',1.10000000,33.00000000),(44,12,'13823774714','1618441017121749894',1,100,'usdt','CNY',21.50000000,1.20000000,0.02000000,0.43000000,0.00000000,0.00000000,21.50000000,NULL,'2018-10-12 16:17:44','2018-10-12 16:47:58',1.20000000,30.00000000),(45,12,'13823774714','16184310191217498100',1,100,'USDT','cny',0.10000000,299.00000000,0.02000000,0.00200000,0.00000000,0.00000000,0.10000000,NULL,'2018-10-12 16:19:43','2018-10-12 16:49:58',20.00000000,263.00000000),(46,12,'13823774714','16182110201217498106',1,100,'USDT','cny',0.10000000,299.00000000,0.02000000,0.00200000,0.00000000,0.00000000,0.10000000,NULL,'2018-10-12 16:20:21','2018-10-12 16:51:13',20.00000000,263.00000000),(47,170,'15220167102','16183910211217498112',2,100,'usdt','CNY',26.00000000,1.20000000,0.02000000,0.60000000,3.00000000,1.00000000,30.00000000,NULL,'2018-10-12 16:21:39','2018-10-15 11:28:58',1.20000000,36.00000000),(48,12,'13823774714','16185410211217498115',1,100,'usdt','CNY',1.00000000,1.20000000,0.02000000,0.02000000,0.00000000,0.00000000,1.00000000,NULL,'2018-10-12 16:21:54','2018-10-12 16:52:13',1.20000000,36.00000000),(49,169,'15220167101','16184510241217498121',1,100,'eth','CNY',1.00000000,1.10000000,0.01000000,0.01000000,0.00000000,0.00000000,1.00000000,NULL,'2018-10-12 16:24:45','2018-10-12 16:55:29',1.10000000,33.00000000),(50,169,'15220167101','16180010321217498127',1,2,'eth','CNY',0.00000000,1.10000000,0.01000000,0.01000000,1.00000000,0.00000000,1.00000000,NULL,'2018-10-12 16:32:00','2018-10-12 16:32:00',1.10000000,33.00000000),(51,169,'15220167101','16184510331217498133',1,2,'usdt','CNY',0.00000000,1.20000000,0.02000000,0.02000000,1.00000000,0.00000000,1.00000000,NULL,'2018-10-12 16:33:45','2018-10-12 16:33:45',1.20000000,36.00000000),(52,169,'15220167101','16181310581217498139',1,100,'usdt','CNY',29.00000000,1.20000000,0.02000000,0.58000000,0.00000000,0.00000000,29.00000000,NULL,'2018-10-12 16:58:13','2018-10-12 16:58:18',1.20000000,36.00000000),(53,169,'15220167101','17180910021217498145',1,1,'usdt','CNY',0.00000000,1.20000000,0.02000000,0.02000000,0.00000000,1.00000000,1.00000000,NULL,'2018-10-12 17:02:09','2018-10-12 17:05:27',1.20000000,36.00000000),(55,169,'15220167101','17180710061217498154',2,100,'eth','CNY',1.00000000,1.30000000,0.01000000,0.01000000,0.00000000,0.00000000,1.00000000,'','2018-10-12 17:06:07','2018-10-12 17:07:58',1.30000000,39.00000000),(56,12,'13823774714','17184710291217498160',1,100,'usdt','CNY',1.00000000,1.20000000,0.02000000,0.02000000,0.00000000,0.00000000,1.00000000,NULL,'2018-10-12 17:29:47','2018-10-12 17:59:57',1.20000000,36.00000000),(57,12,'13823774714','17185410381217498166',1,2,'usdt','CNY',0.00000000,1.20000000,0.02000000,0.02000000,1.00000000,0.00000000,1.00000000,NULL,'2018-10-12 17:38:54','2018-10-12 17:38:54',1.20000000,36.00000000),(58,169,'15220167101','17185910541217498172',1,100,'eth','CNY',1.00000000,1.10000000,0.01000000,0.01000000,0.00000000,0.00000000,1.00000000,NULL,'2018-10-12 17:54:59','2018-10-12 18:25:23',1.10000000,33.00000000),(59,5,'15200905810','17185710561217498178',1,2,'BTC','cny',0.00000000,999.00000000,0.01000000,0.00010010,0.01001001,0.00000000,0.01001001,NULL,'2018-10-12 17:56:57','2018-10-12 17:56:57',10.00000000,999.00000000),(60,169,'15220167101','17183910581217498184',1,100,'eth','CNY',1.00000000,1.10000000,0.01000000,0.01000000,0.00000000,0.00000000,1.00000000,NULL,'2018-10-12 17:58:39','2018-10-12 18:29:37',1.10000000,33.00000000),(61,169,'15220167101','18184010041217498190',1,2,'usdt','CNY',0.00000000,1.20000000,0.02000000,0.02000000,1.00000000,0.00000000,1.00000000,NULL,'2018-10-12 18:04:40','2018-10-12 18:04:40',1.20000000,36.00000000),(62,5,'15200905810','18183310211217498196',1,100,'BTC','cny',0.01001001,999.00000000,0.01000000,0.00010010,0.00000000,0.00000000,0.01001001,NULL,'2018-10-12 18:21:33','2018-10-12 18:51:47',10.00000000,999.00000000),(63,28,'16620855816','18183510211217498202',1,100,'ETH','cny',7.22702362,18.21000000,0.01000000,0.18210000,0.00000000,10.98297638,18.21000000,NULL,'2018-10-12 18:21:35','2018-10-15 11:29:55',1.00000000,200.00000000),(64,5,'15200905810','18182810221217498205',1,100,'BTC','cny',0.01001001,999.00000000,0.01000000,0.00010010,0.00000000,0.00000000,0.01001001,NULL,'2018-10-12 18:22:28','2018-10-12 18:52:47',10.00000000,999.00000000),(65,12,'13823774714','18182310231217498211',1,1,'eth','CNY',0.00000000,1.10000000,0.01000000,0.01000000,0.00000000,1.00000000,1.00000000,NULL,'2018-10-12 18:23:23','2018-10-12 18:26:38',1.10000000,33.00000000),(66,142,'406593508@qq.com','18185610231217498217',2,1,'ETH','cny',0.00000000,18.21000000,0.01000000,0.10982976,0.00000000,10.98297638,10.98297638,'','2018-10-12 18:23:56','2018-10-12 18:26:11',1.00000000,200.00000000),(67,5,'15200905810','18184310241217498223',1,100,'BTC','cny',0.01001001,999.00000000,0.01000000,0.00010010,0.00000000,0.00000000,0.01001001,NULL,'2018-10-12 18:24:43','2018-10-12 18:55:00',10.00000000,999.00000000),(69,169,'15220167101','18180710281217498232',2,2,'eth','CNY',0.00000000,1.30000000,0.01000000,0.01000000,1.00000000,0.00000000,1.00000000,'','2018-10-12 18:28:07','2018-10-12 18:28:07',1.30000000,39.00000000),(70,12,'13823774714','18184910341217498238',1,100,'usdt','CNY',1.66666600,1.20000000,0.02000000,0.03333332,0.00000000,0.00000000,1.66666600,NULL,'2018-10-12 18:34:49','2018-10-12 19:05:29',1.20000000,36.00000000),(71,12,'13823774714','18182210361217498244',1,100,'usdt','CNY',1.66666600,1.20000000,0.02000000,0.03333332,0.00000000,0.00000000,1.66666600,NULL,'2018-10-12 18:36:22','2018-10-12 19:06:43',1.20000000,36.00000000),(72,12,'13823774714','18184510401217498250',1,100,'usdt','CNY',1.00000000,1.20000000,0.02000000,0.02000000,0.00000000,0.00000000,1.00000000,NULL,'2018-10-12 18:40:45','2018-10-12 19:10:57',1.20000000,36.00000000),(73,5,'15200905810','18181510411217498256',1,100,'BTC','cny',0.01001001,999.00000000,0.01000000,0.00010010,0.00000000,0.00000000,0.01001001,NULL,'2018-10-12 18:41:15','2018-10-12 19:11:57',10.00000000,999.00000000),(74,17,'18673582670','18185910411217498262',1,100,'BTC','cny',0.02302302,999.00000000,0.01000000,0.00023023,0.00000000,0.00000000,0.02302302,NULL,'2018-10-12 18:41:59','2018-10-12 18:57:42',10.00000000,999.00000000),(75,169,'15220167101','18183810421217498268',1,100,'eth','CNY',1.00000000,1.10000000,0.01000000,0.01000000,0.00000000,0.00000000,1.00000000,NULL,'2018-10-12 18:42:38','2018-10-12 18:52:56',1.10000000,33.00000000),(76,17,'18673582670','18184610441217498274',1,100,'BTC','CNY',1.00000000,43582.00000000,0.01000000,0.01000000,0.00000000,0.00000000,1.00000000,NULL,'2018-10-12 18:44:46','2018-10-15 11:29:33',438.00000000,43582.00000000),(77,17,'18673582670','18180910461217498277',1,100,'BTC','cny',0.31041040,999.00000000,0.01000000,0.00310410,0.00000000,0.00000000,0.31041040,NULL,'2018-10-12 18:46:09','2018-10-12 18:57:32',10.00000000,999.00000000),(78,12,'13823774714','18184910501217498283',1,100,'usdt','CNY',1.00000000,1.20000000,0.02000000,0.02000000,0.00000000,0.00000000,1.00000000,NULL,'2018-10-12 18:50:49','2018-10-12 19:21:24',1.20000000,36.00000000),(80,17,'18673582670','18184410551217498292',1,100,'eth','CNY',28.00000000,1.10000000,0.01000000,0.28000000,0.00000000,0.00000000,28.00000000,NULL,'2018-10-12 18:55:44','2018-10-12 18:55:48',1.10000000,33.00000000),(81,169,'15220167101','18183510561217498298',2,1,'eth','CNY',0.00000000,1.30000000,0.01000000,0.01000000,0.00000000,1.00000000,1.00000000,'','2018-10-12 18:56:35','2018-10-12 18:59:27',1.30000000,39.00000000),(82,169,'15220167101','19182710001217498304',1,100,'eth','CNY',1.00000000,1.10000000,0.01000000,0.01000000,0.00000000,0.00000000,1.00000000,NULL,'2018-10-12 19:00:27','2018-10-12 19:30:51',1.10000000,33.00000000),(86,17,'18673582670','19180010051217498319',2,100,'usdt','CNY',2.30769200,1.30000000,0.02000000,0.04615384,0.00000000,0.00000000,2.30769200,'','2018-10-12 19:05:00','2018-10-12 19:35:05',1.30000000,39.00000000),(88,169,'15220167101','19182410071217498328',1,1,'eth','CNY',0.00000000,1.10000000,0.01000000,0.01000000,0.00000000,1.00000000,1.00000000,NULL,'2018-10-12 19:07:24','2018-10-12 19:12:07',1.10000000,33.00000000),(90,5,'15200905810','10181910151517498337',1,2,'BTC','cny',0.00000000,999.00000000,0.01000000,0.00010010,0.01001001,0.00000000,0.01001001,NULL,'2018-10-15 10:15:19','2018-10-15 10:15:19',10.00000000,999.00000000),(91,169,'15220167101','1018081045151313619',1,100,'usdt','CNY',10.00000000,10.00000000,0.02000000,0.20000000,0.00000000,0.00000000,10.00000000,NULL,'2018-10-15 10:45:08','2018-10-15 11:28:37',10.00000000,99.99000000),(94,17,'18673582670','1018261052151313628',2,1,'BTC','CNY',0.00000000,42193.46000000,0.01000000,1.00000000,0.00000000,100.00000000,100.00000000,NULL,'2018-10-15 10:52:26','2018-10-15 11:45:01',42193.46000000,4219346.00000000),(95,169,'15220167101','1018521052151313631',1,1,'BTC','CNY',0.00000000,42193.46000000,0.01000000,0.01000000,0.00000000,1.00000000,1.00000000,NULL,'2018-10-15 10:52:52','2018-10-15 11:14:12',42193.46000000,4219346.00000000),(96,169,'15220167101','1118591016151668719',1,100,'BTC','CNY',98.80000000,42193.46000000,0.01000000,0.98800000,0.00000000,0.00000000,98.80000000,NULL,'2018-10-15 11:16:59','2018-10-15 11:17:04',42193.46000000,4219346.00000000),(97,169,'15220167101','1118201017151668725',1,1,'BTC','CNY',0.00000000,42193.46000000,0.01000000,0.98800000,0.00000000,98.80000000,98.80000000,NULL,'2018-10-15 11:17:20','2018-10-15 11:45:01',42193.46000000,4219346.00000000),(98,5,'15200905810','1118311019151668731',1,1,'BTC','CNY',0.00000000,42193.46000000,0.01000000,0.00200000,0.00000000,0.20000000,0.20000000,NULL,'2018-10-15 11:19:31','2018-10-15 11:42:38',42193.46000000,4219346.00000000),(100,169,'15220167101','1118461032151668740',1,100,'usdt','CNY',50.00000000,6.80000000,0.02000000,1.00000000,0.00000000,0.00000000,50.00000000,NULL,'2018-10-15 11:32:46','2018-10-15 11:33:41',6.80000000,340.00000000),(101,169,'15220167101','1118481032151668743',1,100,'usdt','CNY',50.00000000,6.80000000,0.02000000,1.00000000,0.00000000,0.00000000,50.00000000,NULL,'2018-10-15 11:32:48','2018-10-15 11:33:43',6.80000000,340.00000000),(102,169,'15220167101','1118491032151668746',1,100,'usdt','CNY',50.00000000,6.80000000,0.02000000,1.00000000,0.00000000,0.00000000,50.00000000,NULL,'2018-10-15 11:32:49','2018-10-15 11:33:44',6.80000000,340.00000000),(103,169,'15220167101','1118511032151668749',1,100,'usdt','CNY',50.00000000,6.80000000,0.02000000,1.00000000,0.00000000,0.00000000,50.00000000,NULL,'2018-10-15 11:32:51','2018-10-15 11:33:45',6.80000000,340.00000000),(104,169,'15220167101','1118511032151668752',1,100,'usdt','CNY',50.00000000,6.80000000,0.02000000,1.00000000,0.00000000,0.00000000,50.00000000,NULL,'2018-10-15 11:32:51','2018-10-15 11:33:46',6.80000000,340.00000000),(105,169,'15220167101','1118511032151668755',1,100,'usdt','CNY',50.00000000,6.80000000,0.02000000,1.00000000,0.00000000,0.00000000,50.00000000,NULL,'2018-10-15 11:32:51','2018-10-15 11:33:48',6.80000000,340.00000000),(106,169,'15220167101','1118511032151668758',1,100,'usdt','CNY',50.00000000,6.80000000,0.02000000,1.00000000,0.00000000,0.00000000,50.00000000,NULL,'2018-10-15 11:32:51','2018-10-18 20:47:25',6.80000000,340.00000000),(107,169,'15220167101','1118141034151668761',2,100,'usdt','CNY',49.00000000,6.80000000,0.02000000,1.00000000,0.00000000,1.00000000,50.00000000,NULL,'2018-10-15 11:34:14','2018-10-18 20:48:07',6.80000000,340.00000000),(108,171,'18673582671','1118261035151668764',1,100,'usdt','CNY',50.00000000,6.80000000,0.02000000,1.00000000,0.00000000,0.00000000,50.00000000,NULL,'2018-10-15 11:35:26','2018-10-15 11:35:30',6.80000000,340.00000000),(109,171,'18673582671','1118361035151668770',1,100,'usdt','CNY',50.00000000,6.80000000,0.02000000,1.00000000,0.00000000,0.00000000,50.00000000,NULL,'2018-10-15 11:35:36','2018-10-15 11:53:27',6.80000000,340.00000000),(110,17,'18673582670','1118391036151668776',2,100,'BTC','CNY',0.88000000,43800.00000000,0.01000000,0.01000000,0.00000000,0.12000000,1.00000000,NULL,'2018-10-15 11:36:39','2018-10-18 20:48:29',438.00000000,10000.00000000),(111,169,'15220167101','1118051037151668779',1,100,'BTC','CNY',0.22828700,43800.00000000,0.01000000,0.00228287,0.00000000,0.00000000,0.22828700,NULL,'2018-10-15 11:37:05','2018-10-15 11:38:54',438.00000000,10000.00000000),(112,171,'18673582671','1118071054151668785',1,100,'usdt','CNY',50.00000000,6.80000000,0.02000000,1.00000000,0.00000000,0.00000000,50.00000000,NULL,'2018-10-15 11:54:07','2018-10-15 11:55:51',6.80000000,340.00000000),(113,171,'18673582671','1118031056151668791',1,100,'usdt','CNY',1.00000000,6.80000000,0.02000000,0.02000000,0.00000000,0.00000000,1.00000000,NULL,'2018-10-15 11:56:03','2018-10-15 11:56:10',6.80000000,340.00000000),(114,171,'18673582671','1118231056151668797',1,100,'usdt','CNY',1.00000000,6.80000000,0.02000000,0.02000000,0.00000000,0.00000000,1.00000000,NULL,'2018-10-15 11:56:23','2018-10-15 12:27:29',6.80000000,340.00000000),(115,171,'18673582671','11183710561516687103',1,100,'usdt','CNY',1.00000000,6.80000000,0.02000000,0.02000000,0.00000000,0.00000000,1.00000000,NULL,'2018-10-15 11:56:37','2018-10-15 12:27:29',6.80000000,340.00000000),(116,171,'18673582671','11181610571516687109',1,100,'usdt','CNY',1.00000000,6.80000000,0.02000000,0.02000000,0.00000000,0.00000000,1.00000000,NULL,'2018-10-15 11:57:16','2018-10-15 12:27:29',6.80000000,340.00000000),(117,171,'18673582671','11181810571516687115',1,100,'usdt','CNY',1.00000000,6.80000000,0.02000000,0.02000000,0.00000000,0.00000000,1.00000000,NULL,'2018-10-15 11:57:18','2018-10-15 12:27:29',6.80000000,340.00000000),(118,171,'18673582671','11182110571516687121',1,100,'usdt','CNY',1.00000000,6.80000000,0.02000000,0.02000000,0.00000000,0.00000000,1.00000000,NULL,'2018-10-15 11:57:21','2018-10-15 12:27:29',6.80000000,340.00000000),(119,171,'18673582671','11182410571516687127',1,100,'usdt','CNY',1.00000000,6.80000000,0.02000000,0.02000000,0.00000000,0.00000000,1.00000000,NULL,'2018-10-15 11:57:24','2018-10-15 12:27:29',6.80000000,340.00000000),(120,171,'18673582671','11182610571516687133',1,100,'usdt','CNY',1.00000000,6.80000000,0.02000000,0.02000000,0.00000000,0.00000000,1.00000000,NULL,'2018-10-15 11:57:26','2018-10-15 12:27:29',6.80000000,340.00000000),(121,171,'18673582671','11182810571516687139',1,100,'usdt','CNY',1.00000000,6.80000000,0.02000000,0.02000000,0.00000000,0.00000000,1.00000000,NULL,'2018-10-15 11:57:28','2018-10-15 12:27:29',6.80000000,340.00000000),(122,171,'18673582671','11183110571516687145',1,100,'usdt','CNY',1.00000000,6.80000000,0.02000000,0.02000000,0.00000000,0.00000000,1.00000000,NULL,'2018-10-15 11:57:31','2018-10-15 12:28:29',6.80000000,340.00000000),(123,171,'18673582671','11183610571516687151',1,100,'usdt','CNY',1.00000000,6.80000000,0.02000000,0.02000000,0.00000000,0.00000000,1.00000000,NULL,'2018-10-15 11:57:36','2018-10-15 12:28:29',6.80000000,340.00000000),(124,171,'18673582671','11180710581516687157',1,100,'usdt','CNY',1.00000000,6.80000000,0.02000000,0.02000000,0.00000000,0.00000000,1.00000000,NULL,'2018-10-15 11:58:07','2018-10-15 12:28:29',6.80000000,340.00000000),(125,171,'18673582671','11180710581516687163',1,100,'usdt','CNY',1.00000000,6.80000000,0.02000000,0.02000000,0.00000000,0.00000000,1.00000000,NULL,'2018-10-15 11:58:07','2018-10-15 12:28:29',6.80000000,340.00000000),(126,171,'18673582671','11180710581516687169',1,100,'usdt','CNY',1.00000000,6.80000000,0.02000000,0.02000000,0.00000000,0.00000000,1.00000000,NULL,'2018-10-15 11:58:07','2018-10-15 12:28:29',6.80000000,340.00000000),(127,171,'18673582671','11180810581516687175',1,100,'usdt','CNY',1.00000000,6.80000000,0.02000000,0.02000000,0.00000000,0.00000000,1.00000000,NULL,'2018-10-15 11:58:08','2018-10-15 12:28:29',6.80000000,340.00000000),(128,171,'18673582671','11180810581516687181',1,100,'usdt','CNY',1.00000000,6.80000000,0.02000000,0.02000000,0.00000000,0.00000000,1.00000000,NULL,'2018-10-15 11:58:08','2018-10-15 12:28:29',6.80000000,340.00000000),(129,171,'18673582671','11180810581516687187',1,100,'usdt','CNY',1.00000000,6.80000000,0.02000000,0.02000000,0.00000000,0.00000000,1.00000000,NULL,'2018-10-15 11:58:08','2018-10-15 12:28:29',6.80000000,340.00000000),(130,171,'18673582671','11180810581516687193',1,100,'usdt','CNY',1.00000000,6.80000000,0.02000000,0.02000000,0.00000000,0.00000000,1.00000000,NULL,'2018-10-15 11:58:08','2018-10-15 12:28:29',6.80000000,340.00000000),(131,171,'18673582671','11180910581516687199',1,100,'usdt','CNY',1.00000000,6.80000000,0.02000000,0.02000000,0.00000000,0.00000000,1.00000000,NULL,'2018-10-15 11:58:09','2018-10-15 12:28:29',6.80000000,340.00000000),(132,171,'18673582671','11180910581516687205',1,100,'usdt','CNY',1.00000000,6.80000000,0.02000000,0.02000000,0.00000000,0.00000000,1.00000000,NULL,'2018-10-15 11:58:09','2018-10-15 12:28:29',6.80000000,340.00000000),(133,171,'18673582671','11180910581516687211',1,100,'usdt','CNY',1.00000000,6.80000000,0.02000000,0.02000000,0.00000000,0.00000000,1.00000000,NULL,'2018-10-15 11:58:09','2018-10-15 12:28:29',6.80000000,340.00000000),(134,169,'15220167101','11180010591516687217',1,100,'BTC','CNY',0.01000000,43800.00000000,0.01000000,0.00010000,0.00000000,0.00000000,0.01000000,NULL,'2018-10-15 11:59:00','2018-10-15 11:59:03',438.00000000,10000.00000000),(135,171,'18673582671','12180210011516687223',1,100,'BTC','CNY',0.02283105,43800.00000000,0.01000000,0.00022831,0.00000000,0.00000000,0.02283105,NULL,'2018-10-15 12:01:02','2018-10-15 12:01:10',438.00000000,10000.00000000),(136,169,'15220167101','12180710021516687229',1,1,'eth','CNY',0.00000000,1310.88000000,0.01000000,0.11000000,0.00000000,11.00000000,11.00000000,NULL,'2018-10-15 12:02:07','2018-10-17 15:39:52',1310.88000000,14419.68000000),(138,171,'18673582671','12180210041516687235',1,1,'ETH','CNY',0.00000000,1311.00000000,0.01000000,0.10000000,0.00000000,10.00000000,10.00000000,NULL,'2018-10-15 12:04:02','2018-10-15 12:19:22',1311.00000000,13110.00000000),(139,169,'15220167101','12181710041516687238',2,1,'ETH','CNY',0.00000000,1311.00000000,0.01000000,0.10000000,0.00000000,10.00000000,10.00000000,'','2018-10-15 12:04:17','2018-10-15 12:19:22',1311.00000000,13110.00000000),(141,171,'18673582671','12182410241516687247',1,100,'BTC','CNY',1.00000000,43258.00000000,0.01000000,0.01000000,0.00000000,0.00000000,1.00000000,NULL,'2018-10-15 12:24:24','2018-10-15 16:21:26',4325.00000000,43258.00000000),(142,169,'15220167101','12183210241516687250',2,100,'BTC','CNY',1.00000000,43258.00000000,0.01000000,0.01000000,0.00000000,0.00000000,1.00000000,'','2018-10-15 12:24:32','2018-10-15 12:54:54',4325.00000000,43258.00000000),(143,171,'18673582671','12183410251516687256',2,100,'ETH','CNY',1.00000000,1310.80000000,0.01000000,0.01000000,0.00000000,0.00000000,1.00000000,NULL,'2018-10-15 12:25:34','2018-10-15 16:21:25',131.00000000,1310.80000000),(144,169,'15220167101','12184210251516687259',1,100,'ETH','CNY',1.00000000,1310.80000000,0.01000000,0.01000000,0.00000000,0.00000000,1.00000000,NULL,'2018-10-15 12:25:42','2018-10-15 12:55:54',131.00000000,1310.80000000),(145,171,'18673582671','12183210261516687265',2,100,'ETH','CNY',1.00000000,1310.00000000,0.01000000,0.01000000,0.00000000,0.00000000,1.00000000,NULL,'2018-10-15 12:26:32','2018-10-15 16:21:24',131.00000000,1310.00000000),(146,169,'15220167101','12183910261516687268',1,100,'ETH','CNY',1.00000000,1310.00000000,0.01000000,0.01000000,0.00000000,0.00000000,1.00000000,NULL,'2018-10-15 12:26:39','2018-10-15 12:57:07',131.00000000,1310.00000000),(147,171,'18673582671','16184110081516687274',1,1,'BTC','CNY',0.00000000,43800.00000000,0.01000000,0.00020000,0.00000000,0.02000000,0.02000000,NULL,'2018-10-15 16:08:41','2018-10-15 16:11:08',438.00000000,10000.00000000),(148,17,'18673582670','16181210121516687280',2,100,'BTC','CNY',1.00000000,43220.00000000,0.01000000,0.01000000,0.00000000,0.00000000,1.00000000,NULL,'2018-10-15 16:12:12','2018-10-18 20:48:16',432.00000000,43220.00000000),(149,17,'18673582670','16185110121516687283',1,100,'BTC','CNY',1.00000000,1500.00000000,0.01000000,0.01000000,0.00000000,0.00000000,1.00000000,NULL,'2018-10-15 16:12:51','2018-10-18 20:47:03',1499.00000000,1500.00000000),(150,17,'18673582670','16180710131516687286',1,100,'BTC','CNY',1.00000000,1600.00000000,0.01000000,0.01000000,0.00000000,0.00000000,1.00000000,NULL,'2018-10-15 16:13:07','2018-10-18 20:47:01',160.00000000,1600.00000000),(151,171,'18673582671','16184110161516687289',1,100,'BTC','CNY',0.10000000,43220.00000000,0.01000000,0.00100000,0.00000000,0.00000000,0.10000000,NULL,'2018-10-15 16:16:41','2018-10-15 16:16:43',432.00000000,43220.00000000),(152,171,'18673582671','16184810161516687295',1,100,'BTC','CNY',0.10000000,43220.00000000,0.01000000,0.00100000,0.00000000,0.00000000,0.10000000,NULL,'2018-10-15 16:16:48','2018-10-15 16:16:50',432.00000000,43220.00000000),(153,171,'18673582671','16185410161516687301',1,100,'BTC','CNY',0.10000000,43220.00000000,0.01000000,0.00100000,0.00000000,0.00000000,0.10000000,NULL,'2018-10-15 16:16:54','2018-10-15 16:16:56',432.00000000,43220.00000000),(154,171,'18673582671','16180010171516687307',1,100,'BTC','CNY',0.10000000,43220.00000000,0.01000000,0.00100000,0.00000000,0.00000000,0.10000000,NULL,'2018-10-15 16:17:00','2018-10-15 16:17:02',432.00000000,43220.00000000),(155,171,'18673582671','16181110181516687313',1,100,'usdt','CNY',1.20000000,6.80000000,0.02000000,0.02400000,0.00000000,0.00000000,1.20000000,NULL,'2018-10-15 16:18:11','2018-10-15 16:20:38',6.80000000,340.00000000),(156,174,'18673582672','16184310241516687319',1,100,'BTC','CNY',0.10000000,43800.00000000,0.01000000,0.00100000,0.00000000,0.00000000,0.10000000,NULL,'2018-10-15 16:24:43','2018-10-18 20:48:29',438.00000000,10000.00000000),(157,174,'18673582672','16184910241516687325',1,1,'BTC','CNY',0.00000000,43800.00000000,0.01000000,0.00100000,0.00000000,0.10000000,0.10000000,NULL,'2018-10-15 16:24:49','2018-10-15 16:32:37',438.00000000,10000.00000000),(158,174,'18673582672','16180810351516687331',1,100,'BTC','CNY',0.10000000,43220.00000000,0.01000000,0.00100000,0.00000000,0.00000000,0.10000000,NULL,'2018-10-15 16:35:08','2018-10-18 20:48:16',432.00000000,43220.00000000),(159,153,'994222322@qq.com','20182110281516687337',1,100,'USDT','cny',10.00000000,10.00000000,0.02000000,0.20000000,0.00000000,0.00000000,10.00000000,NULL,'2018-10-15 20:28:21','2018-10-18 20:46:59',10.00000000,11.00000000),(160,32,'223456789@qq.com','20183610301516687340',1,100,'BTC','CNY',0.02313743,43220.00000000,0.01000000,0.00023137,0.00000000,0.00000000,0.02313743,NULL,'2018-10-15 20:30:36','2018-10-18 20:48:10',432.00000000,43220.00000000),(161,153,'994222322@qq.com','20183210311516687346',1,100,'usdt','CNY',1.47058823,6.80000000,0.02000000,0.02941176,0.00000000,0.00000000,1.47058823,NULL,'2018-10-15 20:31:32','2018-10-18 20:48:07',6.80000000,340.00000000),(162,32,'223456789@qq.com','20184710321516687352',1,100,'BTC','cny',1.00000000,10000.00000000,0.01000000,0.01000000,0.00000000,0.00000000,1.00000000,NULL,'2018-10-15 20:32:47','2018-10-18 20:46:57',100.00000000,10000.00000000),(165,32,'223456789@qq.com','20185010331516687361',2,1,'BTC','cny',0.00000000,10000.00000000,0.01000000,0.01000000,0.00000000,1.00000000,1.00000000,NULL,'2018-10-15 20:33:50','2018-10-15 20:35:58',100.00000000,10000.00000000),(166,153,'994222322@qq.com','20180210341516687364',1,1,'BTC','cny',0.00000000,10000.00000000,0.01000000,0.01000000,0.00000000,1.00000000,1.00000000,NULL,'2018-10-15 20:34:02','2018-10-15 20:35:58',100.00000000,10000.00000000),(167,32,'223456789@qq.com','20180410381516687370',2,1,'btc','CNY',0.00000000,10000.00000000,0.01000000,0.01000000,0.00000000,1.00000000,1.00000000,NULL,'2018-10-15 20:38:04','2018-10-15 20:39:53',100.00000000,10000.00000000),(168,153,'994222322@qq.com','20184010381516687373',1,1,'btc','CNY',0.00000000,10000.00000000,0.01000000,0.01000000,0.00000000,1.00000000,1.00000000,NULL,'2018-10-15 20:38:40','2018-10-15 20:39:53',100.00000000,10000.00000000),(169,153,'994222322@qq.com','21182610171516687379',1,100,'USDT','cny',9000.00000000,6.80000000,0.02000000,200.00000000,0.00000000,1000.00000000,10000.00000000,NULL,'2018-10-15 21:17:26','2018-10-18 20:46:54',100.00000000,10000.00000000),(170,153,'994222322@qq.com','21182310401516687382',1,100,'USDT','cny',10000.00000000,6.80000000,0.02000000,200.00000000,0.00000000,0.00000000,10000.00000000,NULL,'2018-10-15 21:40:23','2018-10-18 20:46:51',680.00000000,68000.00000000),(171,176,'549588789@qq.com','21180510411516687385',2,100,'USDT','cny',1000.00000000,6.80000000,0.02000000,20.00000000,0.00000000,0.00000000,1000.00000000,'','2018-10-15 21:41:05','2018-10-15 21:42:02',100.00000000,10000.00000000),(172,176,'549588789@qq.com','21182510441516687391',2,100,'USDT','cny',1000.00000000,6.80000000,0.02000000,20.00000000,0.00000000,0.00000000,1000.00000000,'','2018-10-15 21:44:25','2018-10-15 21:47:38',100.00000000,10000.00000000),(173,176,'549588789@qq.com','21181510501516687397',2,1,'USDT','cny',0.00000000,6.80000000,0.02000000,20.00000000,0.00000000,1000.00000000,1000.00000000,'','2018-10-15 21:50:15','2018-10-15 21:58:18',100.00000000,10000.00000000),(174,153,'994222322@qq.com','22180710001516687403',2,100,'USDT','cny',10000.00000000,6.80000000,0.02000000,200.00000000,0.00000000,0.00000000,10000.00000000,NULL,'2018-10-15 22:00:07','2018-10-18 20:47:44',680.00000000,68000.00000000),(175,176,'549588789@qq.com','22184010001516687406',1,100,'USDT','cny',1000.00000000,6.80000000,0.02000000,20.00000000,0.00000000,0.00000000,1000.00000000,NULL,'2018-10-15 22:00:40','2018-10-18 20:47:44',680.00000000,68000.00000000),(176,174,'18673582672','11180710041616687412',1,2,'ETH','cny',1.00000000,1.00000000,0.01000000,0.01000000,0.00000000,0.00000000,1.00000000,NULL,'2018-10-16 11:04:07','2018-11-09 16:32:26',0.50000000,1.00000000),(177,174,'18673582672','11185310051616687415',1,100,'ETH','cny',209.00000000,20.00000000,0.01000000,2.09000000,0.00000000,0.00000000,209.00000000,NULL,'2018-10-16 11:05:53','2018-10-18 20:46:46',1.00000000,22.00000000),(178,30,'123456789@qq.com','11183310071616687418',1,100,'BTC','cny',10.00000000,10000.00000000,0.01000000,0.10000000,0.00000000,0.00000000,10.00000000,NULL,'2018-10-16 11:07:33','2018-10-16 11:59:25',1000.00000000,100000.00000000),(179,17,'18673582670','11181010311616687421',1,100,'USDT','cny',100.00000000,6.80000000,0.02000000,2.00000000,0.00000000,0.00000000,100.00000000,NULL,'2018-10-16 11:31:10','2018-10-16 11:51:42',680.00000000,68000.00000000),(180,38,'13500000001','16183610011616687427',1,100,'USDT','cny',99.99000000,7.00000000,0.02000000,2.00000000,0.00000000,0.01000000,100.00000000,NULL,'2018-10-16 16:01:36','2018-10-18 20:46:42',0.07000000,700.00000000),(181,30,'123456789@qq.com','16181510211616687430',2,1,'USDT','cny',0.00000000,7.00000000,0.02000000,0.00020000,0.00000000,0.01000000,0.01000000,'','2018-10-16 16:21:15','2018-10-16 16:23:45',0.07000000,700.00000000),(183,17,'18673582670','17185510281616687442',2,100,'ETH','cny',1.00000000,1.00000000,0.01000000,0.01000000,0.00000000,0.00000000,1.00000000,'','2018-10-16 17:28:55','2018-10-18 20:47:41',0.50000000,1.00000000),(184,12,'13823774714','21181910021616687448',1,100,'USDT','cny',200.00000000,6.80000000,0.02000000,4.00000000,0.00000000,0.00000000,200.00000000,NULL,'2018-10-16 21:02:19','2018-10-16 21:22:32',680.00000000,68000.00000000),(185,12,'13823774714','21182110031616687454',1,100,'usdt','CNY',2.00000000,6.80000000,0.02000000,0.04000000,0.00000000,0.00000000,2.00000000,NULL,'2018-10-16 21:03:21','2018-10-16 21:23:32',6.80000000,340.00000000),(186,17,'18673582670','15185210001716687460',1,1,'usdt','CNY',0.00000000,6.80000000,0.02000000,0.02000000,0.00000000,1.00000000,1.00000000,NULL,'2018-10-17 15:00:52','2018-10-17 15:05:42',6.80000000,340.00000000),(187,17,'18673582670','15181110151716687466',2,100,'USDT','cny',99.99000000,7.00000000,0.02000000,1.99980000,0.00000000,0.00000000,99.99000000,'','2018-10-17 15:15:11','2018-10-17 15:36:05',0.07000000,700.00000000),(195,17,'18673582670','15183810161716687493',2,1,'eth','CNY',0.00000000,1310.88000000,0.01000000,0.11000000,0.00000000,11.00000000,11.00000000,'','2018-10-17 15:16:38','2018-10-17 15:39:52',1310.88000000,14419.68000000),(196,30,'123456789@qq.com','16185610401716687499',2,100,'USDT','cny',1.00000000,7.00000000,0.02000000,0.02000000,0.00000000,0.00000000,1.00000000,'','2018-10-17 16:40:56','2018-10-17 17:01:24',0.07000000,700.00000000),(197,32,'223456789@qq.com','16183810471716687505',1,100,'USDT','cny',1176.47058823,6.80000000,0.02000000,23.52941176,0.00000000,0.00000000,1176.47058823,NULL,'2018-10-17 16:47:38','2018-10-18 20:47:37',680.00000000,68000.00000000),(198,153,'994222322@qq.com','16182310511716687511',1,100,'BTC','CNY',0.02313743,43220.00000000,0.01000000,0.00023137,0.00000000,0.00000000,0.02313743,NULL,'2018-10-17 16:51:23','2018-10-17 16:53:59',432.00000000,43220.00000000),(199,5,'15200905810','1418301038181316719',2,100,'BTC','CNY',0.01000000,10000.00000000,0.01000000,0.00010000,0.00000000,0.00000000,0.01000000,NULL,'2018-10-18 14:38:30','2018-10-18 20:46:36',10.00000000,100.00000000),(200,174,'18673582672','2018081051181316722',1,100,'btc','CNY',100.00000000,42300.00000000,0.01000000,1.00000000,0.00000000,0.00000000,100.00000000,NULL,'2018-10-18 20:51:08','2018-10-18 20:52:06',42300.00000000,4230000.00000000),(201,174,'18673582672','2018431052181316731',2,100,'btc','CNY',99.00000000,42300.00000000,0.01000000,0.99000000,0.00000000,0.00000000,99.00000000,NULL,'2018-10-18 20:52:43','2018-10-18 20:54:31',42300.00000000,4187700.00000000),(202,174,'18673582672','2018561054181316734',2,1,'btc','CNY',0.00000000,42300.00000000,0.01000000,0.99000000,0.00000000,99.00000000,99.00000000,NULL,'2018-10-18 20:54:56','2018-10-18 21:04:03',42300.00000000,4187700.00000000),(203,171,'18673582671','2018541058181316737',1,1,'btc','CNY',0.00000000,42300.00000000,0.01000000,0.10000000,0.00000000,10.00000000,10.00000000,NULL,'2018-10-18 20:58:54','2018-10-18 21:01:55',42300.00000000,4187700.00000000),(204,171,'18673582671','2118331003181316743',1,1,'btc','CNY',0.00000000,42300.00000000,0.01000000,0.89000000,0.00000000,89.00000000,89.00000000,NULL,'2018-10-18 21:03:33','2018-10-18 21:04:03',42300.00000000,4187700.00000000),(205,126,'13500000089','2118521043181316749',1,1,'usdt','cny',0.00000000,6.60000000,0.02000000,10.00000000,0.00000000,500.00000000,500.00000000,NULL,'2018-10-18 21:43:52','2018-10-19 13:03:47',1.00000000,3300.00000000),(206,23,'552386231@qq.com','1218461059191316752',2,100,'usdt','cny',500.00000000,6.90000000,0.02000000,10.00000000,0.00000000,0.00000000,500.00000000,NULL,'2018-10-19 12:59:46','2018-10-19 13:05:18',100.00000000,3450.00000000),(207,23,'552386231@qq.com','1318241001191316755',2,1,'usdt','cny',0.00000000,6.60000000,0.02000000,10.00000000,0.00000000,500.00000000,500.00000000,'','2018-10-19 13:01:24','2018-10-19 13:03:47',1.00000000,3300.00000000),(208,23,'552386231@qq.com','1318591004191316761',1,2,'usdt','cny',471.01449276,6.90000000,0.02000000,10.00000000,0.00000000,28.98550724,500.00000000,NULL,'2018-10-19 13:04:59','2018-10-19 13:12:49',100.00000000,3450.00000000),(209,126,'13500000089','1318151010191316764',2,1,'usdt','cny',0.00000000,6.90000000,0.02000000,0.57971014,0.00000000,28.98550724,28.98550724,'','2018-10-19 13:10:15','2018-10-19 13:12:49',100.00000000,3450.00000000),(210,23,'552386231@qq.com','1318081045191316770',2,2,'usdt','cny',341.17647059,6.80000000,0.02000000,8.00000000,58.82352941,0.00000000,400.00000000,NULL,'2018-10-19 13:45:08','2018-11-09 16:02:03',10.00000000,400.00000000),(211,126,'13500000089','1318561047191316773',1,2,'usdt','cny',0.00000000,6.80000000,0.02000000,1.17647059,58.82352941,0.00000000,58.82352941,NULL,'2018-10-19 13:47:56','2018-10-19 13:47:56',10.00000000,400.00000000),(212,126,'13500000089','1318251054191316779',1,2,'usdt','cny',30.00000000,6.90000000,0.02000000,0.60000000,0.00000000,0.00000000,30.00000000,NULL,'2018-10-19 13:54:25','2018-10-19 13:54:25',1.00000000,207.00000000),(213,126,'13500000089','1318461057191316782',2,1,'usdt','cny',0.00000000,6.90000000,0.02000000,12.00000000,0.00000000,600.00000000,600.00000000,NULL,'2018-10-19 13:57:46','2018-10-19 14:07:48',0.10000000,4140.00000000),(214,23,'552386231@qq.com','1418351001191316785',1,1,'usdt','cny',0.00000000,6.90000000,0.02000000,12.00000000,0.00000000,600.00000000,600.00000000,NULL,'2018-10-19 14:01:35','2018-10-19 14:07:48',0.10000000,4140.00000000),(215,169,'15220167101','1618361101091900419',2,100,'ETH','cny',1.00000000,1.00000000,0.01000000,0.01000000,0.00000000,0.00000000,1.00000000,'','2018-11-09 16:01:36','2018-11-09 16:32:26',0.50000000,1.00000000),(216,169,'15220167101','1618541101091900425',1,100,'usdt','cny',1.47058823,6.80000000,0.02000000,0.02941176,0.00000000,0.00000000,1.47058823,NULL,'2018-11-09 16:01:54','2018-11-09 16:02:03',10.00000000,400.00000000),(217,169,'15220167101','1018061140191561519',1,2,'ETH','cny',10.00000000,1.50000000,0.01000000,0.10000000,0.00000000,0.00000000,10.00000000,NULL,'2018-11-19 10:40:06','2018-11-19 11:10:55',1.50000000,15.00000000),(218,170,'15220167102','1018511140191561522',2,100,'ETH','cny',1.00000000,1.50000000,0.01000000,0.01000000,0.00000000,0.00000000,1.00000000,'','2018-11-19 10:40:51','2018-11-19 11:10:55',1.50000000,15.00000000),(219,170,'15220167102','1018561155191561528',2,100,'ETH','cny',1.00000000,1.50000000,0.01000000,0.01000000,0.00000000,0.00000000,1.00000000,'','2018-11-19 10:55:56','2018-11-19 10:58:55',1.50000000,15.00000000),(220,170,'15220167102','1118151101191561534',2,100,'ETH','cny',1.00000000,1.50000000,0.01000000,0.01000000,0.00000000,0.00000000,1.00000000,'','2018-11-19 11:01:15','2018-11-19 11:03:19',1.50000000,15.00000000),(221,169,'15220167101','1118061109191561540',2,2,'ETH','cny',8.00000000,1.30000000,0.01000000,0.10000000,1.00000000,1.00000000,10.00000000,NULL,'2018-11-19 11:09:06','2018-11-20 17:48:46',1.30000000,10.00000000),(222,170,'15220167102','1118591109191561543',1,100,'ETH','cny',1.00000000,1.30000000,0.01000000,0.01000000,0.00000000,0.00000000,1.00000000,NULL,'2018-11-19 11:09:59','2018-11-19 11:12:08',1.30000000,10.00000000),(223,170,'15220167102','1118301113191561549',1,2,'ETH','cny',0.00000000,1.30000000,0.01000000,0.01000000,1.00000000,0.00000000,1.00000000,NULL,'2018-11-19 11:13:30','2018-11-19 11:13:30',1.30000000,10.00000000),(224,170,'15220167102','1218161133191794619',1,100,'ETH','cny',1.00000000,1.30000000,0.01000000,0.01000000,0.00000000,0.00000000,1.00000000,NULL,'2018-11-19 12:33:16','2018-11-19 12:33:31',1.30000000,10.00000000),(225,231,'15220166100','1418301159201908719',1,1,'ETH','cny',0.00000000,1.30000000,0.01000000,0.01000000,0.00000000,1.00000000,1.00000000,NULL,'2018-11-20 14:59:30','2018-11-20 15:03:10',1.30000000,10.00000000),(226,231,'15220166100','1618521126201908725',1,100,'ETH','cny',1.00000000,1.30000000,0.01000000,0.01000000,0.00000000,0.00000000,1.00000000,NULL,'2018-11-20 16:26:52','2018-11-20 16:26:55',1.30000000,10.00000000),(227,157,'15220167142','1718411122201908731',1,100,'ETH','cny',1.00000000,1.30000000,0.01000000,0.01000000,0.00000000,0.00000000,1.00000000,NULL,'2018-11-20 17:22:41','2018-11-20 17:22:44',1.30000000,10.00000000),(228,157,'15220167142','1718331148201908737',1,100,'ETH','cny',7.69230700,1.30000000,0.01000000,0.07692307,0.00000000,0.00000000,7.69230700,NULL,'2018-11-20 17:48:33','2018-11-20 17:48:46',1.30000000,10.00000000),(229,238,'13813800003','1118301155211908749',2,100,'usdt','cny',1000.00000000,3.00000000,0.02000000,20.00000000,0.00000000,0.00000000,1000.00000000,NULL,'2018-11-21 11:55:30','2018-11-21 11:55:36',2000.00000000,3000.00000000),(230,238,'13813800003','1118041157211908761',2,100,'usdt','cny',2500.00000000,5.00000000,0.02000000,50.00000000,0.00000000,0.00000000,2500.00000000,NULL,'2018-11-21 11:57:04','2018-11-21 11:57:08',11000.00000000,12500.00000000),(231,238,'13813800003','1118311157211908764',2,100,'usdt','cny',2800.00000000,5.00000000,0.02000000,56.00000000,0.00000000,0.00000000,2800.00000000,NULL,'2018-11-21 11:57:31','2018-11-21 11:57:33',13888.00000000,13999.00000000),(232,238,'13813800003','1118481158211908779',2,1,'usdt','cny',0.00000000,3.00000000,0.02000000,58.00000000,0.00000000,2900.00000000,2900.00000000,NULL,'2018-11-21 11:58:48','2018-11-21 12:00:17',8000.00000000,8700.00000000),(233,239,'13813800004','1118391159211908782',1,1,'usdt','cny',0.00000000,3.00000000,0.02000000,58.00000000,0.00000000,2900.00000000,2900.00000000,NULL,'2018-11-21 11:59:39','2018-11-21 12:00:17',8000.00000000,8700.00000000),(234,240,'13813800002','13183011312119087106',2,100,'usdt','cny',1900.00000000,5.00000000,0.02000000,38.00000000,0.00000000,0.00000000,1900.00000000,NULL,'2018-11-21 13:31:30','2018-11-22 10:07:05',9000.00000000,9500.00000000),(235,240,'13813800005','13180711332119087118',2,100,'usdt','cny',1900.00000000,10.00000000,0.02000000,38.00000000,0.00000000,0.00000000,1900.00000000,NULL,'2018-11-21 13:33:07','2018-11-21 13:33:27',18000.00000000,19000.00000000),(236,240,'13813800005','13185711332119087121',2,100,'usdt','cny',1939.00000000,5.00000000,0.02000000,38.78000000,0.00000000,0.00000000,1939.00000000,NULL,'2018-11-21 13:33:57','2018-11-21 13:34:01',9000.00000000,9695.00000000),(237,236,'13813800001','13183211432119087130',2,100,'usdt','cny',1960.00000000,5.00000000,0.02000000,39.20000000,0.00000000,0.00000000,1960.00000000,NULL,'2018-11-21 13:43:32','2018-11-21 13:43:36',5000.00000000,9800.00000000),(238,240,'13813800005','13180211462119087139',2,1,'usdt','cny',0.00000000,5.00000000,0.02000000,39.20000000,0.00000000,1960.00000000,1960.00000000,NULL,'2018-11-21 13:46:02','2018-11-21 13:47:05',5000.00000000,9800.00000000),(239,236,'13813800001','13181711462119087142',1,1,'usdt','cny',0.00000000,5.00000000,0.02000000,39.20000000,0.00000000,1960.00000000,1960.00000000,NULL,'2018-11-21 13:46:17','2018-11-21 13:47:05',5000.00000000,9800.00000000);
/*!40000 ALTER TABLE `t_otc` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_otc_bill`
--

DROP TABLE IF EXISTS `t_otc_bill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_otc_bill` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '������ID',
  `coin_name` varchar(10) NOT NULL COMMENT '������',
  `type` int(11) NOT NULL COMMENT '������',
  `amount` decimal(32,8) NOT NULL DEFAULT '0.00000000' COMMENT '������',
  `price` decimal(32,8) DEFAULT '0.00000000' COMMENT '������',
  `fee` decimal(32,8) DEFAULT '0.00000000' COMMENT '���������',
  `order_no` varchar(64) NOT NULL COMMENT '���������������������������',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_userid_coinname_type` (`user_id`,`coin_name`,`type`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8 COMMENT='������������������';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_otc_bill`
--

LOCK TABLES `t_otc_bill` WRITE;
/*!40000 ALTER TABLE `t_otc_bill` DISABLE KEYS */;
INSERT INTO `t_otc_bill` VALUES (1,3,'ETH',1,1.00000000,0.00000000,NULL,'1518010850241039431','2018-08-24 15:50:49','2018-08-24 15:50:49'),(2,4,'ETH',2,1.00000000,0.00000000,NULL,'1518010850241039431','2018-08-24 15:50:49','2018-08-24 15:50:49'),(3,4,'BTC',1,0.01601600,0.00000000,NULL,'1118370825291004640','2018-08-29 11:26:05','2018-08-29 11:26:05'),(4,3,'BTC',2,0.01601600,0.00000000,NULL,'1118370825291004640','2018-08-29 11:26:05','2018-08-29 11:26:05'),(5,169,'usdt',1,1.00000000,0.00000000,NULL,'17180910021217498148','2018-10-12 17:05:27','2018-10-12 17:05:27'),(6,170,'usdt',2,1.00000000,0.00000000,NULL,'17180910021217498148','2018-10-12 17:05:27','2018-10-12 17:05:27'),(7,28,'ETH',1,10.98297638,0.00000000,NULL,'18185610231217498220','2018-10-12 18:26:11','2018-10-12 18:26:11'),(8,142,'ETH',2,10.98297638,0.00000000,NULL,'18185610231217498220','2018-10-12 18:26:11','2018-10-12 18:26:11'),(9,12,'eth',1,1.00000000,0.00000000,NULL,'18182310231217498214','2018-10-12 18:26:38','2018-10-12 18:26:38'),(10,170,'eth',2,1.00000000,0.00000000,NULL,'18182310231217498214','2018-10-12 18:26:38','2018-10-12 18:26:38'),(11,170,'eth',1,1.00000000,0.00000000,NULL,'18183510561217498301','2018-10-12 18:59:27','2018-10-12 18:59:27'),(12,169,'eth',2,1.00000000,0.00000000,NULL,'18183510561217498301','2018-10-12 18:59:27','2018-10-12 18:59:27'),(13,169,'eth',1,1.00000000,0.00000000,NULL,'19182410071217498331','2018-10-12 19:12:07','2018-10-12 19:12:07'),(14,170,'eth',2,1.00000000,0.00000000,NULL,'19182410071217498331','2018-10-12 19:12:07','2018-10-12 19:12:07'),(15,169,'BTC',1,1.00000000,0.00000000,NULL,'1018521052151313634','2018-10-15 11:14:12','2018-10-15 11:14:12'),(16,17,'BTC',2,1.00000000,0.00000000,NULL,'1018521052151313634','2018-10-15 11:14:12','2018-10-15 11:14:12'),(17,5,'BTC',1,0.20000000,0.00000000,NULL,'1118311019151668734','2018-10-15 11:42:38','2018-10-15 11:42:38'),(18,17,'BTC',2,0.20000000,0.00000000,NULL,'1118311019151668734','2018-10-15 11:42:38','2018-10-15 11:42:38'),(19,169,'BTC',1,98.80000000,0.00000000,NULL,'1118201017151668728','2018-10-15 11:45:01','2018-10-15 11:45:01'),(20,17,'BTC',2,98.80000000,0.00000000,NULL,'1118201017151668728','2018-10-15 11:45:01','2018-10-15 11:45:01'),(21,171,'ETH',1,10.00000000,0.00000000,NULL,'12181710041516687241','2018-10-15 12:19:22','2018-10-15 12:19:22'),(22,169,'ETH',2,10.00000000,0.00000000,NULL,'12181710041516687241','2018-10-15 12:19:22','2018-10-15 12:19:22'),(23,171,'BTC',1,0.02000000,0.00000000,NULL,'16184110081516687277','2018-10-15 16:11:08','2018-10-15 16:11:08'),(24,17,'BTC',2,0.02000000,0.00000000,NULL,'16184110081516687277','2018-10-15 16:11:08','2018-10-15 16:11:08'),(25,174,'BTC',1,0.10000000,0.00000000,NULL,'16184910241516687328','2018-10-15 16:32:37','2018-10-15 16:32:37'),(26,17,'BTC',2,0.10000000,0.00000000,NULL,'16184910241516687328','2018-10-15 16:32:37','2018-10-15 16:32:37'),(27,153,'BTC',1,1.00000000,0.00000000,NULL,'20180210341516687367','2018-10-15 20:35:58','2018-10-15 20:35:58'),(28,32,'BTC',2,1.00000000,0.00000000,NULL,'20180210341516687367','2018-10-15 20:35:58','2018-10-15 20:35:58'),(29,153,'btc',1,1.00000000,0.00000000,NULL,'20184010381516687376','2018-10-15 20:39:53','2018-10-15 20:39:53'),(30,32,'btc',2,1.00000000,0.00000000,NULL,'20184010381516687376','2018-10-15 20:39:53','2018-10-15 20:39:53'),(31,153,'USDT',1,1000.00000000,0.00000000,NULL,'21181510501516687400','2018-10-15 21:58:18','2018-10-15 21:58:18'),(32,176,'USDT',2,1000.00000000,0.00000000,NULL,'21181510501516687400','2018-10-15 21:58:18','2018-10-15 21:58:18'),(33,38,'USDT',1,0.01000000,0.00000000,NULL,'16181510211616687433','2018-10-16 16:23:45','2018-10-16 16:23:45'),(34,30,'USDT',2,0.01000000,0.00000000,NULL,'16181510211616687433','2018-10-16 16:23:45','2018-10-16 16:23:45'),(35,17,'usdt',1,1.00000000,0.00000000,NULL,'15185210001716687463','2018-10-17 15:05:42','2018-10-17 15:05:42'),(36,169,'usdt',2,1.00000000,0.00000000,NULL,'15185210001716687463','2018-10-17 15:05:42','2018-10-17 15:05:42'),(37,169,'eth',1,11.00000000,0.00000000,NULL,'15183810161716687496','2018-10-17 15:39:52','2018-10-17 15:39:52'),(38,17,'eth',2,11.00000000,0.00000000,NULL,'15183810161716687496','2018-10-17 15:39:52','2018-10-17 15:39:52'),(39,171,'btc',1,10.00000000,0.00000000,NULL,'2018541058181316740','2018-10-18 21:01:55','2018-10-18 21:01:55'),(40,174,'btc',2,10.00000000,0.00000000,NULL,'2018541058181316740','2018-10-18 21:01:55','2018-10-18 21:01:55'),(41,171,'btc',1,89.00000000,0.00000000,NULL,'2118331003181316746','2018-10-18 21:04:03','2018-10-18 21:04:03'),(42,174,'btc',2,89.00000000,0.00000000,NULL,'2118331003181316746','2018-10-18 21:04:03','2018-10-18 21:04:03'),(43,126,'usdt',1,500.00000000,0.00000000,NULL,'1318241001191316758','2018-10-19 13:03:47','2018-10-19 13:03:47'),(44,23,'usdt',2,500.00000000,0.00000000,NULL,'1318241001191316758','2018-10-19 13:03:47','2018-10-19 13:03:47'),(45,23,'usdt',1,28.98550724,0.00000000,NULL,'1318151010191316767','2018-10-19 13:12:49','2018-10-19 13:12:49'),(46,126,'usdt',2,28.98550724,0.00000000,NULL,'1318151010191316767','2018-10-19 13:12:49','2018-10-19 13:12:49'),(47,23,'usdt',1,600.00000000,0.00000000,NULL,'1418351001191316788','2018-10-19 14:07:48','2018-10-19 14:07:48'),(48,126,'usdt',2,600.00000000,0.00000000,NULL,'1418351001191316788','2018-10-19 14:07:48','2018-10-19 14:07:48'),(49,231,'ETH',1,1.00000000,0.00000000,NULL,'1418301159201908722','2018-11-20 15:03:10','2018-11-20 15:03:10'),(50,169,'ETH',2,1.00000000,0.00000000,NULL,'1418301159201908722','2018-11-20 15:03:10','2018-11-20 15:03:10'),(51,239,'usdt',1,2900.00000000,0.00000000,NULL,'1118391159211908785','2018-11-21 12:00:17','2018-11-21 12:00:17'),(52,238,'usdt',2,2900.00000000,0.00000000,NULL,'1118391159211908785','2018-11-21 12:00:17','2018-11-21 12:00:17'),(53,236,'usdt',1,1960.00000000,0.00000000,NULL,'13181711462119087145','2018-11-21 13:47:05','2018-11-21 13:47:05'),(54,240,'usdt',2,1960.00000000,0.00000000,NULL,'13181711462119087145','2018-11-21 13:47:05','2018-11-21 13:47:05');
/*!40000 ALTER TABLE `t_otc_bill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_otc_kline`
--

DROP TABLE IF EXISTS `t_otc_kline`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_otc_kline` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `coin_name` varchar(10) DEFAULT NULL COMMENT '������',
  `legal_name` varchar(10) DEFAULT 'cny' COMMENT '������',
  `open_price` decimal(32,8) DEFAULT NULL COMMENT '���������',
  `close_price` decimal(32,8) DEFAULT NULL COMMENT '���������',
  `high_price` decimal(32,8) DEFAULT NULL COMMENT '���������',
  `low_price` decimal(32,8) DEFAULT NULL COMMENT '���������',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='������������������������';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_otc_kline`
--

LOCK TABLES `t_otc_kline` WRITE;
/*!40000 ALTER TABLE `t_otc_kline` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_otc_kline` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_otc_market`
--

DROP TABLE IF EXISTS `t_otc_market`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_otc_market` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `coin_name` varchar(50) NOT NULL DEFAULT '0' COMMENT '������������',
  `legal_name` varchar(50) NOT NULL DEFAULT 'cny' COMMENT '���������������������cny(���������)',
  `last_price` decimal(24,8) NOT NULL COMMENT '���������������',
  `min_exchange_num` decimal(24,8) NOT NULL COMMENT '���������������������������',
  `max_exchange_num` decimal(24,8) NOT NULL COMMENT '���������������������������',
  `status` tinyint(4) NOT NULL COMMENT '0������������1���������',
  `create_time` datetime NOT NULL COMMENT '������������������',
  `update_time` datetime NOT NULL COMMENT '������������',
  `fee_rate` decimal(24,8) DEFAULT NULL COMMENT '���������������������',
  `expired_time_cancel` int(11) DEFAULT NULL COMMENT '������������(������)',
  `expired_time_freeze` int(11) DEFAULT NULL COMMENT '������������(������)',
  `max_appl_buy_count` int(11) DEFAULT '10' COMMENT '���������������(������)',
  `max_appl_sell_count` int(11) DEFAULT '10' COMMENT '���������������(������)',
  PRIMARY KEY (`id`),
  KEY `coin` (`coin_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='c2c���������������������������';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_otc_market`
--

LOCK TABLES `t_otc_market` WRITE;
/*!40000 ALTER TABLE `t_otc_market` DISABLE KEYS */;
INSERT INTO `t_otc_market` VALUES (1,'usdt','cny',5.00000000,0.00000000,10000.00000000,0,'2018-07-24 12:02:35','2018-11-21 13:47:05',0.02000000,20,50,200000,200000),(2,'btc','cny',42300.00000000,0.00000000,10000.00000000,0,'2018-07-24 12:02:35','2018-10-18 21:04:03',0.01000000,2,2,2,2),(3,'eth','cny',1.30000000,0.00000000,10000.00000000,0,'2018-07-24 12:02:35','2018-11-20 15:03:10',0.01000000,2,2,10,10);
/*!40000 ALTER TABLE `t_otc_market` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_otc_order`
--

DROP TABLE IF EXISTS `t_otc_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_otc_order` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `order_no` varchar(64) DEFAULT NULL COMMENT '������',
  `deposit_application_id` bigint(20) DEFAULT NULL COMMENT '������������ID',
  `withdrawal_application_id` bigint(20) DEFAULT NULL COMMENT '������������ID',
  `deposit_user_id` int(11) DEFAULT NULL COMMENT '������������ID',
  `withdrawal_user_id` int(11) DEFAULT NULL COMMENT '������������ID',
  `deposit_user_name` varchar(32) DEFAULT NULL COMMENT '���������������',
  `withdrawal_user_name` varchar(32) DEFAULT NULL COMMENT '���������������',
  `status` int(11) DEFAULT NULL COMMENT '������',
  `coin_name` varchar(10) DEFAULT NULL COMMENT '������',
  `legal_name` varchar(10) DEFAULT NULL COMMENT '������',
  `price` decimal(32,8) DEFAULT NULL COMMENT '������',
  `amount` decimal(32,8) DEFAULT NULL COMMENT '������',
  `fee_rate` decimal(32,8) DEFAULT NULL COMMENT '������������',
  `withdrawal_desc` varchar(256) DEFAULT NULL COMMENT '������������',
  `deposit_desc` varchar(256) DEFAULT NULL COMMENT '������������',
  `credential_comment` varchar(256) DEFAULT NULL COMMENT '������������',
  `credential_urls` varchar(1024) DEFAULT NULL COMMENT '������������',
  `cancel_desc` varchar(256) DEFAULT NULL COMMENT '������������',
  `appeal_role` int(11) DEFAULT NULL COMMENT '���������1������������2���������',
  `appeal_code` varchar(50) DEFAULT NULL COMMENT '���������',
  `appeal_desc` varchar(256) DEFAULT NULL COMMENT '������������',
  `remark` varchar(256) DEFAULT NULL COMMENT '������',
  `accept_time` datetime DEFAULT NULL COMMENT '������������',
  `upload_credential_time` datetime DEFAULT NULL COMMENT '������������������',
  `cancel_time` datetime DEFAULT NULL COMMENT '������������',
  `expire_time` datetime DEFAULT NULL COMMENT '������������',
  `finish_time` datetime DEFAULT NULL COMMENT '������������',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `pay_type` int(11) DEFAULT NULL COMMENT '������������(0������������1������������2������)',
  `create_id` int(11) DEFAULT NULL COMMENT '���������������������ID���������������������������',
  PRIMARY KEY (`id`),
  KEY `idx_deposit_appl_id` (`deposit_application_id`),
  KEY `idx_withdraw_appl_id` (`withdrawal_application_id`),
  KEY `idx_userid_status_createtime` (`deposit_user_id`,`withdrawal_user_id`,`status`,`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=141 DEFAULT CHARSET=utf8 COMMENT='c2c���������������������������������������������';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_otc_order`
--

LOCK TABLES `t_otc_order` WRITE;
/*!40000 ALTER TABLE `t_otc_order` DISABLE KEYS */;
INSERT INTO `t_otc_order` VALUES (1,'1518010850241039431',2,4,3,4,'18617208190','987@qq.com',1,'ETH','cny',1.00000000,1.00000000,0.01000000,'',NULL,'45345','http://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/1535097011744timg.jfif',NULL,NULL,NULL,NULL,NULL,'2018-08-24 15:50:01','2018-08-24 15:50:13',NULL,'2018-08-24 16:50:13','2018-08-24 15:50:49','2018-08-24 15:50:01','2018-08-24 15:50:49',2,4),(2,'1118370825291004640',12,11,4,3,'987@qq.com','18617208190',1,'BTC','cny',999.00000000,0.01601600,0.01000000,NULL,NULL,'45345','blob:http://47.75.240.124/5e30218c-e271-4352-85b0-9524f84188bf',NULL,NULL,NULL,NULL,NULL,'2018-08-29 11:25:37','2018-08-29 11:25:47',NULL,'2018-08-29 12:25:47','2018-08-29 11:26:04','2018-08-29 11:25:37','2018-08-29 11:26:04',0,4),(3,'1118290826291004646',13,11,4,3,'987@qq.com','18617208190',100,'BTC','cny',999.00000000,0.98398400,0.01000000,NULL,NULL,NULL,NULL,'',NULL,NULL,NULL,NULL,'2018-08-29 11:26:29',NULL,'2018-08-29 11:26:31','2018-08-29 11:56:29',NULL,'2018-08-29 11:26:29','2018-08-29 11:26:31',NULL,4),(4,'1118220835291004652',7,14,3,4,'18617208190','987@qq.com',100,'ETH','cny',15.98000000,1.00000000,0.01000000,'',NULL,NULL,NULL,'',NULL,NULL,NULL,NULL,'2018-08-29 11:35:22',NULL,'2018-08-29 11:35:28','2018-08-29 12:05:22',NULL,'2018-08-29 11:35:22','2018-08-29 11:35:28',NULL,4),(5,'1218360808291004667',18,11,4,3,'987@qq.com','18617208190',100,'BTC','cny',999.00000000,0.98398400,0.01000000,NULL,NULL,NULL,NULL,'',NULL,NULL,NULL,NULL,'2018-08-29 12:08:36',NULL,'2018-08-29 12:08:39','2018-08-29 12:38:36',NULL,'2018-08-29 12:08:36','2018-08-29 12:08:39',NULL,4),(6,'1018090848301004673',17,19,4,3,'987@qq.com','18617208190',100,'USDT','cny',999.00000000,0.01599599,0.02000000,'',NULL,NULL,NULL,'',NULL,NULL,NULL,NULL,'2018-08-30 10:48:09',NULL,'2018-08-30 10:48:30','2018-08-30 11:18:09',NULL,'2018-08-30 10:48:09','2018-08-30 10:48:30',NULL,3),(7,'1218170811301004682',21,11,19,3,'18612341234','18617208190',100,'BTC','cny',999.00000000,0.01561561,0.01000000,NULL,NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-08-30 12:11:17',NULL,'2018-08-30 12:42:06','2018-08-30 12:41:17',NULL,'2018-08-30 12:11:17','2018-08-30 12:42:06',NULL,19),(8,'1618550808301004688',22,11,5,3,'15200905810','18617208190',100,'BTC','cny',999.00000000,0.10010010,0.01000000,NULL,NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-08-30 16:08:55',NULL,'2018-08-30 16:39:07','2018-08-30 16:38:55',NULL,'2018-08-30 16:08:55','2018-08-30 16:39:07',NULL,5),(9,'16183908563110046103',26,11,5,3,'15200905810','18617208190',7,'BTC','cny',999.00000000,0.10010010,0.01000000,NULL,NULL,'','http://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/1535706656306.png',NULL,NULL,NULL,NULL,'������������������(������������)','2018-08-31 16:56:39','2018-08-31 16:57:16',NULL,'2018-08-31 17:57:16',NULL,'2018-08-31 16:56:39','2018-08-31 17:57:47',0,5),(10,'16184409100110046109',27,11,5,3,'15200905810','18617208190',7,'BTC','cny',999.00000000,0.40040040,0.01000000,NULL,NULL,'','https://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/1535790304799.png',NULL,NULL,NULL,NULL,'������������������(������������)','2018-09-01 16:10:44','2018-09-01 16:10:57',NULL,'2018-09-01 17:10:57',NULL,'2018-09-01 16:10:44','2018-09-01 17:12:00',0,5),(11,'1018121002081073422',28,11,5,3,'15200905810','18617208190',7,'BTC','cny',999.00000000,0.10000000,0.01000000,NULL,NULL,'','https://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/1538965149748.png',NULL,NULL,NULL,NULL,'������������������(������������)','2018-10-08 10:02:12','2018-10-08 10:03:31',NULL,'2018-10-08 11:03:31',NULL,'2018-10-08 10:02:12','2018-10-08 11:04:34',2,5),(12,'1418151011121749825',30,29,169,170,'15220167101','15220167102',100,'usdt','CNY',1.20000000,1.00000000,0.02000000,NULL,NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-10-12 14:11:15',NULL,'2018-10-12 14:41:55','2018-10-12 14:41:15',NULL,'2018-10-12 14:11:15','2018-10-12 14:41:55',NULL,169),(13,'1418501028121749831',31,29,169,170,'15220167101','15220167102',100,'usdt','CNY',1.20000000,2.00000000,0.02000000,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2018-10-12 14:28:50',NULL,'2018-10-12 14:53:52','2018-10-12 14:58:50',NULL,'2018-10-12 14:28:50','2018-10-12 14:53:52',NULL,169),(14,'1518491036121749837',32,29,12,170,'13823774714','15220167102',100,'usdt','CNY',1.20000000,2.50000000,0.02000000,NULL,NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-10-12 15:36:49',NULL,'2018-10-12 16:07:39','2018-10-12 16:06:49',NULL,'2018-10-12 15:36:49','2018-10-12 16:07:39',NULL,12),(15,'1518381051121749843',33,29,169,170,'15220167101','15220167102',100,'usdt','CNY',1.20000000,1.00000000,0.02000000,NULL,NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-10-12 15:51:38',NULL,'2018-10-12 16:22:35','2018-10-12 16:21:38',NULL,'2018-10-12 15:51:38','2018-10-12 16:22:35',NULL,169),(16,'1518081057121749849',34,29,169,170,'15220167101','15220167102',100,'usdt','CNY',1.20000000,1.00000000,0.02000000,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2018-10-12 15:57:08',NULL,'2018-10-12 15:58:00','2018-10-12 16:27:08',NULL,'2018-10-12 15:57:08','2018-10-12 15:58:00',NULL,169),(17,'1618081005121749858',36,29,12,170,'13823774714','15220167102',100,'usdt','CNY',1.20000000,2.50000000,0.02000000,NULL,NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-10-12 16:05:08',NULL,'2018-10-12 16:35:16','2018-10-12 16:35:08',NULL,'2018-10-12 16:05:08','2018-10-12 16:35:16',NULL,12),(18,'1618081007121749864',37,29,12,170,'13823774714','15220167102',100,'usdt','CNY',1.20000000,2.50000000,0.02000000,NULL,NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-10-12 16:07:08',NULL,'2018-10-12 16:37:16','2018-10-12 16:37:08',NULL,'2018-10-12 16:07:08','2018-10-12 16:37:16',NULL,12),(19,'1618171011121749870',38,29,12,170,'13823774714','15220167102',100,'usdt','CNY',1.20000000,2.50000000,0.02000000,NULL,NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-10-12 16:11:17',NULL,'2018-10-12 16:41:30','2018-10-12 16:41:17',NULL,'2018-10-12 16:11:17','2018-10-12 16:41:30',NULL,12),(20,'1618371016121749885',42,40,169,170,'15220167101','15220167102',100,'eth','CNY',1.10000000,29.99999900,0.01000000,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2018-10-12 16:16:37',NULL,'2018-10-12 16:16:44','2018-10-12 16:46:37',NULL,'2018-10-12 16:16:37','2018-10-12 16:16:44',NULL,169),(21,'1618131017121749891',43,40,169,170,'15220167101','15220167102',100,'eth','CNY',1.10000000,1.00000000,0.01000000,NULL,NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-10-12 16:17:13',NULL,'2018-10-12 16:47:58','2018-10-12 16:47:13',NULL,'2018-10-12 16:17:13','2018-10-12 16:47:58',NULL,169),(22,'1618441017121749897',44,29,12,170,'13823774714','15220167102',100,'usdt','CNY',1.20000000,21.50000000,0.02000000,NULL,NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-10-12 16:17:44',NULL,'2018-10-12 16:47:58','2018-10-12 16:47:44',NULL,'2018-10-12 16:17:44','2018-10-12 16:47:58',NULL,12),(23,'16184310191217498103',45,25,12,3,'13823774714','18617208190',100,'USDT','cny',299.00000000,0.10000000,0.02000000,NULL,NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-10-12 16:19:43',NULL,'2018-10-12 16:49:58','2018-10-12 16:49:43',NULL,'2018-10-12 16:19:43','2018-10-12 16:49:58',NULL,12),(24,'16182110201217498109',46,25,12,3,'13823774714','18617208190',100,'USDT','cny',299.00000000,0.10000000,0.02000000,NULL,NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-10-12 16:20:21',NULL,'2018-10-12 16:51:13','2018-10-12 16:50:21',NULL,'2018-10-12 16:20:21','2018-10-12 16:51:13',NULL,12),(25,'16185410211217498118',48,47,12,170,'13823774714','15220167102',100,'usdt','CNY',1.20000000,1.00000000,0.02000000,NULL,NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-10-12 16:21:54',NULL,'2018-10-12 16:52:13','2018-10-12 16:51:54',NULL,'2018-10-12 16:21:54','2018-10-12 16:52:13',NULL,12),(26,'16184510241217498124',49,40,169,170,'15220167101','15220167102',100,'eth','CNY',1.10000000,1.00000000,0.01000000,NULL,NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-10-12 16:24:45',NULL,'2018-10-12 16:55:29','2018-10-12 16:54:45',NULL,'2018-10-12 16:24:45','2018-10-12 16:55:29',NULL,169),(27,'16180010321217498130',50,40,169,170,'15220167101','15220167102',7,'eth','CNY',1.10000000,1.00000000,0.01000000,NULL,NULL,'','https://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/1539333613008.png',NULL,NULL,NULL,NULL,'������������������(������������)','2018-10-12 16:32:00','2018-10-12 16:32:22',NULL,'2018-10-12 17:32:22',NULL,'2018-10-12 16:32:00','2018-10-12 17:33:02',1,169),(28,'16184510331217498136',51,47,169,170,'15220167101','15220167102',7,'usdt','CNY',1.20000000,1.00000000,0.02000000,NULL,NULL,'','https://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/1539333721492.png',NULL,NULL,NULL,NULL,'������������������(������������)','2018-10-12 16:33:45','2018-10-12 16:33:54',NULL,'2018-10-12 17:33:54',NULL,'2018-10-12 16:33:45','2018-10-12 17:34:25',1,169),(29,'16181310581217498142',52,47,169,170,'15220167101','15220167102',100,'usdt','CNY',1.20000000,29.00000000,0.02000000,NULL,NULL,NULL,NULL,'',NULL,NULL,NULL,NULL,'2018-10-12 16:58:13',NULL,'2018-10-12 16:58:17','2018-10-12 17:28:13',NULL,'2018-10-12 16:58:13','2018-10-12 16:58:17',NULL,169),(30,'17180910021217498148',53,47,169,170,'15220167101','15220167102',1,'usdt','CNY',1.20000000,1.00000000,0.02000000,NULL,NULL,'','https://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/1539335760678.png',NULL,NULL,'225843','���������',NULL,'2018-10-12 17:02:09','2018-10-12 17:02:48',NULL,'2018-10-12 18:02:48','2018-10-12 17:05:27','2018-10-12 17:02:09','2018-10-12 17:05:27',2,169),(31,'17180710061217498157',41,55,170,169,'15220167102','15220167101',100,'eth','CNY',1.30000000,1.00000000,0.01000000,'',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2018-10-12 17:06:07',NULL,'2018-10-12 17:07:58','2018-10-12 17:36:07',NULL,'2018-10-12 17:06:07','2018-10-12 17:07:58',NULL,169),(32,'17184710291217498163',56,47,12,170,'13823774714','15220167102',100,'usdt','CNY',1.20000000,1.00000000,0.02000000,NULL,NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-10-12 17:29:47',NULL,'2018-10-12 17:59:57','2018-10-12 17:59:47',NULL,'2018-10-12 17:29:47','2018-10-12 17:59:57',NULL,12),(33,'17185410381217498169',57,47,12,170,'13823774714','15220167102',7,'usdt','CNY',1.20000000,1.00000000,0.02000000,NULL,NULL,'���������','http://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/e7c257ea41b41b9f875a92a94f2bad291539337243449.jpg',NULL,NULL,NULL,NULL,'������������������(������������)','2018-10-12 17:38:54','2018-10-12 17:40:48',NULL,'2018-10-12 18:40:48',NULL,'2018-10-12 17:38:54','2018-10-12 18:41:38',1,12),(34,'17185910541217498175',58,40,169,170,'15220167101','15220167102',100,'eth','CNY',1.10000000,1.00000000,0.01000000,NULL,NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-10-12 17:54:59',NULL,'2018-10-12 18:25:23','2018-10-12 18:24:59',NULL,'2018-10-12 17:54:59','2018-10-12 18:25:23',NULL,169),(35,'17185710561217498181',59,11,5,3,'15200905810','18617208190',7,'BTC','cny',999.00000000,0.01001001,0.01000000,NULL,NULL,'','https://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/1539338734318.png',NULL,NULL,NULL,NULL,'������������������(������������)','2018-10-12 17:56:57','2018-10-12 17:57:11',NULL,'2018-10-12 18:57:11',NULL,'2018-10-12 17:56:57','2018-10-12 18:58:15',2,5),(36,'17183910581217498187',60,40,169,170,'15220167101','15220167102',100,'eth','CNY',1.10000000,1.00000000,0.01000000,NULL,NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-10-12 17:58:39',NULL,'2018-10-12 18:29:37','2018-10-12 18:28:39',NULL,'2018-10-12 17:58:39','2018-10-12 18:29:37',NULL,169),(37,'18184010041217498193',61,47,169,170,'15220167101','15220167102',7,'usdt','CNY',1.20000000,1.00000000,0.02000000,NULL,NULL,'������������','http://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/ec5a6330230a52c69616ceb814e463ad1539338695406.jpg',NULL,NULL,NULL,NULL,'������������������(������������)','2018-10-12 18:04:40','2018-10-12 18:05:01',NULL,'2018-10-12 19:05:01',NULL,'2018-10-12 18:04:40','2018-10-12 19:05:53',0,169),(38,'18183310211217498199',62,11,5,3,'15200905810','18617208190',100,'BTC','cny',999.00000000,0.01001001,0.01000000,NULL,NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-10-12 18:21:33',NULL,'2018-10-12 18:51:47','2018-10-12 18:51:33',NULL,'2018-10-12 18:21:33','2018-10-12 18:51:47',NULL,5),(39,'18182810221217498208',64,11,5,3,'15200905810','18617208190',100,'BTC','cny',999.00000000,0.01001001,0.01000000,NULL,NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-10-12 18:22:28',NULL,'2018-10-12 18:52:47','2018-10-12 18:52:28',NULL,'2018-10-12 18:22:28','2018-10-12 18:52:47',NULL,5),(40,'18182310231217498214',65,40,12,170,'13823774714','15220167102',1,'eth','CNY',1.10000000,1.00000000,0.01000000,NULL,NULL,'���������','http://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/e7c257ea41b41b9f875a92a94f2bad291539339829521.jpg',NULL,12,'177490','���������','deal by system','2018-10-12 18:23:23','2018-10-12 18:23:55',NULL,'2018-10-12 19:23:55','2018-10-12 18:26:38','2018-10-12 18:23:23','2018-10-12 18:26:38',1,12),(41,'18185610231217498220',63,66,28,142,'16620855816','406593508@qq.com',1,'ETH','cny',18.21000000,10.98297638,0.01000000,'',NULL,'45345','https://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/1539339858661%E6%B3%A8%E5%86%8C%E9%A1%B5.jpg',NULL,NULL,NULL,NULL,NULL,'2018-10-12 18:23:56','2018-10-12 18:24:24',NULL,'2018-10-12 19:24:24','2018-10-12 18:26:11','2018-10-12 18:23:56','2018-10-12 18:26:11',2,142),(42,'18184310241217498226',67,11,5,3,'15200905810','18617208190',100,'BTC','cny',999.00000000,0.01001001,0.01000000,NULL,NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-10-12 18:24:43',NULL,'2018-10-12 18:55:00','2018-10-12 18:54:43',NULL,'2018-10-12 18:24:43','2018-10-12 18:55:00',NULL,5),(43,'18180710281217498235',41,69,170,169,'15220167102','15220167101',7,'eth','CNY',1.30000000,1.00000000,0.01000000,'',NULL,'45345','blob:http://47.75.240.124/cfea572e-49e0-4b8d-93cf-f54e604e755c',NULL,NULL,NULL,NULL,'������������������(������������)','2018-10-12 18:28:07','2018-10-12 18:32:11',NULL,'2018-10-12 19:32:11',NULL,'2018-10-12 18:28:07','2018-10-12 19:32:25',2,169),(44,'18184910341217498241',70,47,12,170,'13823774714','15220167102',100,'usdt','CNY',1.20000000,1.66666600,0.02000000,NULL,NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-10-12 18:34:49',NULL,'2018-10-12 19:05:29','2018-10-12 19:04:49',NULL,'2018-10-12 18:34:49','2018-10-12 19:05:29',NULL,12),(45,'18182210361217498247',71,47,12,170,'13823774714','15220167102',100,'usdt','CNY',1.20000000,1.66666600,0.02000000,NULL,NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-10-12 18:36:22',NULL,'2018-10-12 19:06:43','2018-10-12 19:06:22',NULL,'2018-10-12 18:36:22','2018-10-12 19:06:43',NULL,12),(46,'18184510401217498253',72,47,12,170,'13823774714','15220167102',100,'usdt','CNY',1.20000000,1.00000000,0.02000000,NULL,NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-10-12 18:40:45',NULL,'2018-10-12 19:10:57','2018-10-12 19:10:45',NULL,'2018-10-12 18:40:45','2018-10-12 19:10:57',NULL,12),(47,'18181510411217498259',73,11,5,3,'15200905810','18617208190',100,'BTC','cny',999.00000000,0.01001001,0.01000000,NULL,NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-10-12 18:41:15',NULL,'2018-10-12 19:11:57','2018-10-12 19:11:15',NULL,'2018-10-12 18:41:15','2018-10-12 19:11:57',NULL,5),(48,'18185910411217498265',74,11,17,3,'18673582670','18617208190',100,'BTC','cny',999.00000000,0.02302302,0.01000000,NULL,NULL,NULL,NULL,'',NULL,NULL,NULL,NULL,'2018-10-12 18:41:59',NULL,'2018-10-12 18:57:42','2018-10-12 19:11:59',NULL,'2018-10-12 18:41:59','2018-10-12 18:57:42',NULL,17),(49,'18183810421217498271',75,40,169,170,'15220167101','15220167102',100,'eth','CNY',1.10000000,1.00000000,0.01000000,NULL,NULL,'���������','http://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/c1831b36201bfe8a453468f7595f515a1539340967541.jpg','canceled by system',169,'752759','yy',NULL,'2018-10-12 18:42:38','2018-10-12 18:43:02','2018-10-12 18:52:56','2018-10-12 19:43:02',NULL,'2018-10-12 18:42:38','2018-10-12 18:52:56',1,169),(50,'18180910461217498280',77,11,17,3,'18673582670','18617208190',100,'BTC','cny',999.00000000,0.31041040,0.01000000,NULL,NULL,NULL,NULL,'',NULL,NULL,NULL,NULL,'2018-10-12 18:46:09',NULL,'2018-10-12 18:57:32','2018-10-12 19:16:09',NULL,'2018-10-12 18:46:09','2018-10-12 18:57:32',NULL,17),(51,'18184910501217498286',78,47,12,170,'13823774714','15220167102',100,'usdt','CNY',1.20000000,1.00000000,0.02000000,NULL,NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-10-12 18:50:49',NULL,'2018-10-12 19:21:24','2018-10-12 19:20:49',NULL,'2018-10-12 18:50:49','2018-10-12 19:21:24',NULL,12),(52,'18184410551217498295',80,40,17,170,'18673582670','15220167102',100,'eth','CNY',1.10000000,28.00000000,0.01000000,NULL,NULL,NULL,NULL,'',NULL,NULL,NULL,NULL,'2018-10-12 18:55:44',NULL,'2018-10-12 18:55:48','2018-10-12 19:25:44',NULL,'2018-10-12 18:55:44','2018-10-12 18:55:48',NULL,17),(53,'18183510561217498301',41,81,170,169,'15220167102','15220167101',1,'eth','CNY',1.30000000,1.00000000,0.01000000,'',NULL,'45345','blob:http://47.75.240.124/bf8c0f6d-51aa-4be5-b152-bbf2f1c65231',NULL,NULL,NULL,NULL,NULL,'2018-10-12 18:56:35','2018-10-12 18:57:10',NULL,'2018-10-12 19:57:10','2018-10-12 18:59:27','2018-10-12 18:56:35','2018-10-12 18:59:27',1,169),(54,'19182710001217498307',82,40,169,170,'15220167101','15220167102',100,'eth','CNY',1.10000000,1.00000000,0.01000000,NULL,NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-10-12 19:00:27',NULL,'2018-10-12 19:30:51','2018-10-12 19:30:27',NULL,'2018-10-12 19:00:27','2018-10-12 19:30:51',NULL,169),(55,'19180010051217498322',35,86,170,17,'15220167102','18673582670',100,'usdt','CNY',1.30000000,2.30769200,0.02000000,'',NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-10-12 19:05:00',NULL,'2018-10-12 19:35:05','2018-10-12 19:35:00',NULL,'2018-10-12 19:05:00','2018-10-12 19:35:05',NULL,17),(56,'19182410071217498331',88,40,169,170,'15220167101','15220167102',1,'eth','CNY',1.10000000,1.00000000,0.01000000,NULL,NULL,'������������','http://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/c1831b36201bfe8a453468f7595f515a1539342557585.jpg',NULL,NULL,NULL,NULL,NULL,'2018-10-12 19:07:24','2018-10-12 19:09:49',NULL,'2018-10-12 20:09:49','2018-10-12 19:12:07','2018-10-12 19:07:24','2018-10-12 19:12:07',2,169),(57,'10181910151517498340',90,11,5,3,'15200905810','18617208190',7,'BTC','cny',999.00000000,0.01001001,0.01000000,NULL,NULL,'','https://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/1539570609186.png',NULL,NULL,NULL,NULL,'������������������(������������)','2018-10-15 10:15:19','2018-10-15 10:15:32',NULL,'2018-10-15 11:15:32',NULL,'2018-10-15 10:15:19','2018-10-15 11:16:20',2,5),(58,'1018521052151313634',95,94,169,17,'15220167101','18673582670',1,'BTC','CNY',42193.46000000,1.00000000,0.01000000,NULL,NULL,'���������','http://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/99855c73cc9384f4e77dd39738ee07ac1539572021694.jpg',NULL,NULL,NULL,NULL,NULL,'2018-10-15 10:52:52','2018-10-15 10:53:42',NULL,'2018-10-15 11:53:42','2018-10-15 11:14:12','2018-10-15 10:52:52','2018-10-15 11:14:12',1,169),(59,'1118591016151668722',96,94,169,17,'15220167101','18673582670',100,'BTC','CNY',42193.46000000,98.80000000,0.01000000,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2018-10-15 11:16:59',NULL,'2018-10-15 11:17:04','2018-10-15 11:46:59',NULL,'2018-10-15 11:16:59','2018-10-15 11:17:04',NULL,169),(60,'1118201017151668728',97,94,169,17,'15220167101','18673582670',1,'BTC','CNY',42193.46000000,98.80000000,0.01000000,NULL,NULL,'���������','http://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/43c799cc8a6003b417f987783df3337d1539573454813.jpg',NULL,NULL,NULL,NULL,NULL,'2018-10-15 11:17:20','2018-10-15 11:17:34',NULL,'2018-10-15 12:17:34','2018-10-15 11:45:01','2018-10-15 11:17:20','2018-10-15 11:45:01',1,169),(61,'1118311019151668734',98,94,5,17,'15200905810','18673582670',1,'BTC','CNY',42193.46000000,0.20000000,0.01000000,NULL,NULL,'','https://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/1539573964092.png',NULL,17,'672394','���������','deal by system','2018-10-15 11:19:31','2018-10-15 11:19:48',NULL,'2018-10-15 12:19:48','2018-10-15 11:42:38','2018-10-15 11:19:31','2018-10-15 11:42:38',1,5),(62,'1118261035151668767',108,107,171,169,'18673582671','15220167101',100,'usdt','CNY',6.80000000,50.00000000,0.02000000,NULL,NULL,NULL,NULL,'',NULL,NULL,NULL,NULL,'2018-10-15 11:35:26',NULL,'2018-10-15 11:35:30','2018-10-15 12:05:26',NULL,'2018-10-15 11:35:26','2018-10-15 11:35:30',NULL,171),(63,'1118361035151668773',109,107,171,169,'18673582671','15220167101',100,'usdt','CNY',6.80000000,50.00000000,0.02000000,NULL,NULL,NULL,NULL,'',NULL,NULL,NULL,NULL,'2018-10-15 11:35:36',NULL,'2018-10-15 11:53:27','2018-10-15 12:05:36',NULL,'2018-10-15 11:35:36','2018-10-15 11:53:27',NULL,171),(64,'1118051037151668782',111,110,169,17,'15220167101','18673582670',100,'BTC','CNY',43800.00000000,0.22828700,0.01000000,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2018-10-15 11:37:05',NULL,'2018-10-15 11:38:54','2018-10-15 12:07:05',NULL,'2018-10-15 11:37:05','2018-10-15 11:38:54',NULL,169),(65,'1118071054151668788',112,107,171,169,'18673582671','15220167101',100,'usdt','CNY',6.80000000,50.00000000,0.02000000,NULL,NULL,NULL,NULL,'',NULL,NULL,NULL,NULL,'2018-10-15 11:54:07',NULL,'2018-10-15 11:55:51','2018-10-15 12:24:07',NULL,'2018-10-15 11:54:07','2018-10-15 11:55:51',NULL,171),(66,'1118031056151668794',113,107,171,169,'18673582671','15220167101',100,'usdt','CNY',6.80000000,1.00000000,0.02000000,NULL,NULL,NULL,NULL,'',NULL,NULL,NULL,NULL,'2018-10-15 11:56:03',NULL,'2018-10-15 11:56:10','2018-10-15 12:26:03',NULL,'2018-10-15 11:56:03','2018-10-15 11:56:10',NULL,171),(67,'11182310561516687100',114,107,171,169,'18673582671','15220167101',100,'usdt','CNY',6.80000000,1.00000000,0.02000000,NULL,NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-10-15 11:56:23',NULL,'2018-10-15 12:27:29','2018-10-15 12:26:23',NULL,'2018-10-15 11:56:23','2018-10-15 12:27:29',NULL,171),(68,'11183710561516687106',115,107,171,169,'18673582671','15220167101',100,'usdt','CNY',6.80000000,1.00000000,0.02000000,NULL,NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-10-15 11:56:37',NULL,'2018-10-15 12:27:29','2018-10-15 12:26:37',NULL,'2018-10-15 11:56:37','2018-10-15 12:27:29',NULL,171),(69,'11181610571516687112',116,107,171,169,'18673582671','15220167101',100,'usdt','CNY',6.80000000,1.00000000,0.02000000,NULL,NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-10-15 11:57:16',NULL,'2018-10-15 12:27:29','2018-10-15 12:27:16',NULL,'2018-10-15 11:57:16','2018-10-15 12:27:29',NULL,171),(70,'11181810571516687118',117,107,171,169,'18673582671','15220167101',100,'usdt','CNY',6.80000000,1.00000000,0.02000000,NULL,NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-10-15 11:57:18',NULL,'2018-10-15 12:27:29','2018-10-15 12:27:18',NULL,'2018-10-15 11:57:18','2018-10-15 12:27:29',NULL,171),(71,'11182110571516687124',118,107,171,169,'18673582671','15220167101',100,'usdt','CNY',6.80000000,1.00000000,0.02000000,NULL,NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-10-15 11:57:21',NULL,'2018-10-15 12:27:29','2018-10-15 12:27:21',NULL,'2018-10-15 11:57:21','2018-10-15 12:27:29',NULL,171),(72,'11182410571516687130',119,107,171,169,'18673582671','15220167101',100,'usdt','CNY',6.80000000,1.00000000,0.02000000,NULL,NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-10-15 11:57:24',NULL,'2018-10-15 12:27:29','2018-10-15 12:27:24',NULL,'2018-10-15 11:57:24','2018-10-15 12:27:29',NULL,171),(73,'11182610571516687136',120,107,171,169,'18673582671','15220167101',100,'usdt','CNY',6.80000000,1.00000000,0.02000000,NULL,NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-10-15 11:57:26',NULL,'2018-10-15 12:27:29','2018-10-15 12:27:26',NULL,'2018-10-15 11:57:26','2018-10-15 12:27:29',NULL,171),(74,'11182810571516687142',121,107,171,169,'18673582671','15220167101',100,'usdt','CNY',6.80000000,1.00000000,0.02000000,NULL,NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-10-15 11:57:28',NULL,'2018-10-15 12:27:29','2018-10-15 12:27:28',NULL,'2018-10-15 11:57:28','2018-10-15 12:27:29',NULL,171),(75,'11183110571516687148',122,107,171,169,'18673582671','15220167101',100,'usdt','CNY',6.80000000,1.00000000,0.02000000,NULL,NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-10-15 11:57:31',NULL,'2018-10-15 12:28:29','2018-10-15 12:27:31',NULL,'2018-10-15 11:57:31','2018-10-15 12:28:29',NULL,171),(76,'11183610571516687154',123,107,171,169,'18673582671','15220167101',100,'usdt','CNY',6.80000000,1.00000000,0.02000000,NULL,NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-10-15 11:57:36',NULL,'2018-10-15 12:28:29','2018-10-15 12:27:36',NULL,'2018-10-15 11:57:36','2018-10-15 12:28:29',NULL,171),(77,'11180710581516687160',124,107,171,169,'18673582671','15220167101',100,'usdt','CNY',6.80000000,1.00000000,0.02000000,NULL,NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-10-15 11:58:07',NULL,'2018-10-15 12:28:29','2018-10-15 12:28:07',NULL,'2018-10-15 11:58:07','2018-10-15 12:28:29',NULL,171),(78,'11180710581516687166',125,107,171,169,'18673582671','15220167101',100,'usdt','CNY',6.80000000,1.00000000,0.02000000,NULL,NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-10-15 11:58:07',NULL,'2018-10-15 12:28:29','2018-10-15 12:28:07',NULL,'2018-10-15 11:58:07','2018-10-15 12:28:29',NULL,171),(79,'11180710581516687172',126,107,171,169,'18673582671','15220167101',100,'usdt','CNY',6.80000000,1.00000000,0.02000000,NULL,NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-10-15 11:58:07',NULL,'2018-10-15 12:28:29','2018-10-15 12:28:07',NULL,'2018-10-15 11:58:07','2018-10-15 12:28:29',NULL,171),(80,'11180810581516687178',127,107,171,169,'18673582671','15220167101',100,'usdt','CNY',6.80000000,1.00000000,0.02000000,NULL,NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-10-15 11:58:08',NULL,'2018-10-15 12:28:29','2018-10-15 12:28:08',NULL,'2018-10-15 11:58:08','2018-10-15 12:28:29',NULL,171),(81,'11180810581516687184',128,107,171,169,'18673582671','15220167101',100,'usdt','CNY',6.80000000,1.00000000,0.02000000,NULL,NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-10-15 11:58:08',NULL,'2018-10-15 12:28:29','2018-10-15 12:28:08',NULL,'2018-10-15 11:58:08','2018-10-15 12:28:29',NULL,171),(82,'11180810581516687190',129,107,171,169,'18673582671','15220167101',100,'usdt','CNY',6.80000000,1.00000000,0.02000000,NULL,NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-10-15 11:58:08',NULL,'2018-10-15 12:28:29','2018-10-15 12:28:08',NULL,'2018-10-15 11:58:08','2018-10-15 12:28:29',NULL,171),(83,'11180810581516687196',130,107,171,169,'18673582671','15220167101',100,'usdt','CNY',6.80000000,1.00000000,0.02000000,NULL,NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-10-15 11:58:08',NULL,'2018-10-15 12:28:29','2018-10-15 12:28:08',NULL,'2018-10-15 11:58:08','2018-10-15 12:28:29',NULL,171),(84,'11180910581516687202',131,107,171,169,'18673582671','15220167101',100,'usdt','CNY',6.80000000,1.00000000,0.02000000,NULL,NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-10-15 11:58:09',NULL,'2018-10-15 12:28:29','2018-10-15 12:28:09',NULL,'2018-10-15 11:58:09','2018-10-15 12:28:29',NULL,171),(85,'11180910581516687208',132,107,171,169,'18673582671','15220167101',100,'usdt','CNY',6.80000000,1.00000000,0.02000000,NULL,NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-10-15 11:58:09',NULL,'2018-10-15 12:28:29','2018-10-15 12:28:09',NULL,'2018-10-15 11:58:09','2018-10-15 12:28:29',NULL,171),(86,'11180910581516687214',133,107,171,169,'18673582671','15220167101',100,'usdt','CNY',6.80000000,1.00000000,0.02000000,NULL,NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-10-15 11:58:09',NULL,'2018-10-15 12:28:29','2018-10-15 12:28:09',NULL,'2018-10-15 11:58:09','2018-10-15 12:28:29',NULL,171),(87,'11180010591516687220',134,110,169,17,'15220167101','18673582670',100,'BTC','CNY',43800.00000000,0.01000000,0.01000000,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2018-10-15 11:59:00',NULL,'2018-10-15 11:59:03','2018-10-15 12:29:00',NULL,'2018-10-15 11:59:00','2018-10-15 11:59:03',NULL,169),(88,'12180210011516687226',135,110,171,17,'18673582671','18673582670',100,'BTC','CNY',43800.00000000,0.02283105,0.01000000,NULL,NULL,NULL,NULL,'',NULL,NULL,NULL,NULL,'2018-10-15 12:01:02',NULL,'2018-10-15 12:01:10','2018-10-15 12:31:02',NULL,'2018-10-15 12:01:02','2018-10-15 12:01:10',NULL,171),(89,'12181710041516687241',138,139,171,169,'18673582671','15220167101',1,'ETH','CNY',1311.00000000,10.00000000,0.01000000,'',NULL,'','https://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/1539576716776.png',NULL,NULL,NULL,NULL,NULL,'2018-10-15 12:04:17','2018-10-15 12:04:57',NULL,'2018-10-15 13:04:57','2018-10-15 12:19:22','2018-10-15 12:04:17','2018-10-15 12:19:22',2,169),(90,'12183210241516687253',141,142,171,169,'18673582671','15220167101',100,'BTC','CNY',43258.00000000,1.00000000,0.01000000,'',NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-10-15 12:24:32',NULL,'2018-10-15 12:54:54','2018-10-15 12:54:32',NULL,'2018-10-15 12:24:32','2018-10-15 12:54:54',NULL,169),(91,'12184210251516687262',144,143,169,171,'15220167101','18673582671',100,'ETH','CNY',1310.80000000,1.00000000,0.01000000,NULL,NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-10-15 12:25:42',NULL,'2018-10-15 12:55:54','2018-10-15 12:55:42',NULL,'2018-10-15 12:25:42','2018-10-15 12:55:54',NULL,169),(92,'12183910261516687271',146,145,169,171,'15220167101','18673582671',100,'ETH','CNY',1310.00000000,1.00000000,0.01000000,NULL,NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-10-15 12:26:39',NULL,'2018-10-15 12:57:07','2018-10-15 12:56:39',NULL,'2018-10-15 12:26:39','2018-10-15 12:57:07',NULL,169),(93,'16184110081516687277',147,110,171,17,'18673582671','18673582670',1,'BTC','CNY',43800.00000000,0.02000000,0.01000000,NULL,NULL,'45345','https://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/1539591043706D703A536-D10D-428e-8A52-817AAECFD5D8.png',NULL,NULL,NULL,NULL,NULL,'2018-10-15 16:08:41','2018-10-15 16:10:45',NULL,'2018-10-15 17:10:45','2018-10-15 16:11:08','2018-10-15 16:08:41','2018-10-15 16:11:08',1,171),(94,'16184110161516687292',151,148,171,17,'18673582671','18673582670',100,'BTC','CNY',43220.00000000,0.10000000,0.01000000,NULL,NULL,NULL,NULL,'',NULL,NULL,NULL,NULL,'2018-10-15 16:16:41',NULL,'2018-10-15 16:16:43','2018-10-15 16:21:41',NULL,'2018-10-15 16:16:41','2018-10-15 16:16:43',NULL,171),(95,'16184810161516687298',152,148,171,17,'18673582671','18673582670',100,'BTC','CNY',43220.00000000,0.10000000,0.01000000,NULL,NULL,NULL,NULL,'',NULL,NULL,NULL,NULL,'2018-10-15 16:16:48',NULL,'2018-10-15 16:16:50','2018-10-15 16:21:48',NULL,'2018-10-15 16:16:48','2018-10-15 16:16:50',NULL,171),(96,'16185410161516687304',153,148,171,17,'18673582671','18673582670',100,'BTC','CNY',43220.00000000,0.10000000,0.01000000,NULL,NULL,NULL,NULL,'',NULL,NULL,NULL,NULL,'2018-10-15 16:16:54',NULL,'2018-10-15 16:16:56','2018-10-15 16:21:54',NULL,'2018-10-15 16:16:54','2018-10-15 16:16:56',NULL,171),(97,'16180010171516687310',154,148,171,17,'18673582671','18673582670',100,'BTC','CNY',43220.00000000,0.10000000,0.01000000,NULL,NULL,NULL,NULL,'',NULL,NULL,NULL,NULL,'2018-10-15 16:17:00',NULL,'2018-10-15 16:17:02','2018-10-15 16:22:00',NULL,'2018-10-15 16:17:00','2018-10-15 16:17:02',NULL,171),(98,'16181110181516687316',155,107,171,169,'18673582671','15220167101',100,'usdt','CNY',6.80000000,1.20000000,0.02000000,NULL,NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-10-15 16:18:11',NULL,'2018-10-15 16:20:38','2018-10-15 16:20:11',NULL,'2018-10-15 16:18:11','2018-10-15 16:20:38',NULL,171),(99,'16184310241516687322',156,110,174,17,'18673582672','18673582670',100,'BTC','CNY',43800.00000000,0.10000000,0.01000000,NULL,NULL,'45345','https://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/15395919295791.jpg','canceled by system',174,'672035','������������',NULL,'2018-10-15 16:24:43','2018-10-15 16:25:29','2018-10-18 20:48:29','2018-10-15 16:30:29',NULL,'2018-10-15 16:24:43','2018-10-18 20:48:29',1,174),(100,'16184910241516687328',157,110,174,17,'18673582672','18673582670',1,'BTC','CNY',43800.00000000,0.10000000,0.01000000,NULL,NULL,'45345','https://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/1539591910346a2.png',NULL,NULL,NULL,NULL,'deal by system','2018-10-15 16:24:49','2018-10-15 16:25:11',NULL,'2018-10-15 16:30:11','2018-10-15 16:32:37','2018-10-15 16:24:49','2018-10-15 16:32:37',1,174),(101,'16180810351516687334',158,148,174,17,'18673582672','18673582670',100,'BTC','CNY',43220.00000000,0.10000000,0.01000000,NULL,NULL,'45345','https://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/15395925132901.jpg','canceled by system',NULL,NULL,NULL,'������������������(������������)','2018-10-15 16:35:08','2018-10-15 16:35:14','2018-10-18 20:48:16','2018-10-15 16:37:14',NULL,'2018-10-15 16:35:08','2018-10-18 20:48:16',1,174),(102,'20183610301516687343',160,148,32,17,'223456789@qq.com','18673582670',100,'BTC','CNY',43220.00000000,0.02313743,0.01000000,NULL,NULL,'45345','https://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/1539606650303DET.png','canceled by system',NULL,NULL,NULL,'������������������(������������)','2018-10-15 20:30:36','2018-10-15 20:32:06','2018-10-18 20:48:10','2018-10-15 20:34:06',NULL,'2018-10-15 20:30:36','2018-10-18 20:48:10',1,32),(103,'20183210311516687349',161,107,153,169,'994222322@qq.com','15220167101',100,'usdt','CNY',6.80000000,1.47058823,0.02000000,NULL,NULL,'45345','https://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/15396067034241.png','canceled by system',NULL,NULL,NULL,'������������������(������������)','2018-10-15 20:31:32','2018-10-15 20:31:53','2018-10-18 20:48:07','2018-10-15 20:36:53',NULL,'2018-10-15 20:31:32','2018-10-18 20:48:07',2,153),(104,'20180210341516687367',166,165,153,32,'994222322@qq.com','223456789@qq.com',1,'BTC','cny',10000.00000000,1.00000000,0.01000000,NULL,NULL,'45345','https://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/15396068530511.png',NULL,NULL,NULL,NULL,NULL,'2018-10-15 20:34:02','2018-10-15 20:34:18',NULL,'2018-10-15 20:36:18','2018-10-15 20:35:58','2018-10-15 20:34:02','2018-10-15 20:35:58',2,153),(105,'20184010381516687376',168,167,153,32,'994222322@qq.com','223456789@qq.com',1,'btc','CNY',10000.00000000,1.00000000,0.01000000,NULL,NULL,'','https://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/1539607286009.png',NULL,NULL,NULL,NULL,NULL,'2018-10-15 20:38:40','2018-10-15 20:39:22',NULL,'2018-10-15 20:41:22','2018-10-15 20:39:53','2018-10-15 20:38:40','2018-10-15 20:39:53',2,153),(106,'21180510411516687388',169,171,153,176,'994222322@qq.com','549588789@qq.com',100,'USDT','cny',6.80000000,1000.00000000,0.02000000,'',NULL,'45345','https://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/1539610908928timg.jpg','',NULL,NULL,NULL,NULL,'2018-10-15 21:41:05','2018-10-15 21:41:54','2018-10-15 21:42:02','2018-10-15 21:46:54',NULL,'2018-10-15 21:41:05','2018-10-15 21:42:02',2,176),(107,'21182510441516687394',169,172,153,176,'994222322@qq.com','549588789@qq.com',100,'USDT','cny',6.80000000,1000.00000000,0.02000000,'',NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-10-15 21:44:25',NULL,'2018-10-15 21:47:38','2018-10-15 21:46:25',NULL,'2018-10-15 21:44:25','2018-10-15 21:47:38',NULL,176),(108,'21181510501516687400',169,173,153,176,'994222322@qq.com','549588789@qq.com',1,'USDT','cny',6.80000000,1000.00000000,0.02000000,'',NULL,'45345','https://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/1539611455540timg.jpg',NULL,NULL,NULL,NULL,'deal by system','2018-10-15 21:50:15','2018-10-15 21:50:58',NULL,'2018-10-15 21:55:58','2018-10-15 21:58:18','2018-10-15 21:50:15','2018-10-15 21:58:18',2,176),(109,'22184010001516687409',175,174,176,153,'549588789@qq.com','994222322@qq.com',100,'USDT','cny',6.80000000,1000.00000000,0.02000000,NULL,NULL,'45345','https://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/1539612044259timg.jpg','canceled by system',NULL,NULL,NULL,'������������������(������������)','2018-10-15 22:00:40','2018-10-15 22:00:49','2018-10-18 20:47:44','2018-10-15 22:50:49',NULL,'2018-10-15 22:00:40','2018-10-18 20:47:44',2,176),(110,'11181010311616687424',179,174,17,153,'18673582670','994222322@qq.com',100,'USDT','cny',6.80000000,100.00000000,0.02000000,NULL,NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-10-16 11:31:10',NULL,'2018-10-16 11:51:42','2018-10-16 11:51:10',NULL,'2018-10-16 11:31:10','2018-10-16 11:51:42',NULL,17),(111,'16181510211616687433',180,181,38,30,'13500000001','123456789@qq.com',1,'USDT','cny',7.00000000,0.01000000,0.02000000,'',NULL,'45345','https://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/1539678175154%E5%BE%AE%E4%BF%A1%E6%88%AA%E5%9B%BE_20181016160750.png',NULL,NULL,NULL,NULL,NULL,'2018-10-16 16:21:15','2018-10-16 16:22:56',NULL,'2018-10-16 17:12:56','2018-10-16 16:23:45','2018-10-16 16:21:15','2018-10-16 16:23:45',2,30),(112,'17185510281616687445',176,183,174,17,'18673582672','18673582670',100,'ETH','cny',1.00000000,1.00000000,0.01000000,'',NULL,'45345','https://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/1539682172497a2.png','canceled by system',NULL,NULL,NULL,'������������������(������������)','2018-10-16 17:28:55','2018-10-16 17:29:33','2018-10-18 20:47:41','2018-10-16 18:29:33',NULL,'2018-10-16 17:28:55','2018-10-18 20:47:41',1,17),(113,'21181910021616687451',184,174,12,153,'13823774714','994222322@qq.com',100,'USDT','cny',6.80000000,200.00000000,0.02000000,NULL,NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-10-16 21:02:19',NULL,'2018-10-16 21:22:32','2018-10-16 21:22:19',NULL,'2018-10-16 21:02:19','2018-10-16 21:22:32',NULL,12),(114,'21182110031616687457',185,107,12,169,'13823774714','15220167101',100,'usdt','CNY',6.80000000,2.00000000,0.02000000,NULL,NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-10-16 21:03:21',NULL,'2018-10-16 21:23:32','2018-10-16 21:23:21',NULL,'2018-10-16 21:03:21','2018-10-16 21:23:32',NULL,12),(115,'15185210001716687463',186,107,17,169,'18673582670','15220167101',1,'usdt','CNY',6.80000000,1.00000000,0.02000000,NULL,NULL,'������','http://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/c205dedbe90d8ef1a7867c9fe7c1cc531539759684761.jpg',NULL,NULL,NULL,NULL,NULL,'2018-10-17 15:00:52','2018-10-17 15:01:24',NULL,'2018-10-17 15:51:24','2018-10-17 15:05:42','2018-10-17 15:00:52','2018-10-17 15:05:42',2,17),(116,'15181110151716687469',180,187,38,17,'13500000001','18673582670',100,'USDT','cny',7.00000000,99.99000000,0.02000000,'',NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-10-17 15:15:11',NULL,'2018-10-17 15:36:05','2018-10-17 15:35:11',NULL,'2018-10-17 15:15:11','2018-10-17 15:36:05',NULL,17),(117,'15183810161716687496',136,195,169,17,'15220167101','18673582670',1,'eth','CNY',1310.88000000,11.00000000,0.01000000,'',NULL,'','https://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/1539760933197.png',NULL,NULL,'892353','KTV���������',NULL,'2018-10-17 15:16:38','2018-10-17 15:17:06',NULL,'2018-10-17 16:17:06','2018-10-17 15:39:52','2018-10-17 15:16:38','2018-10-17 15:39:52',1,17),(118,'16185610401716687502',180,196,38,30,'13500000001','123456789@qq.com',100,'USDT','cny',7.00000000,1.00000000,0.02000000,'',NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-10-17 16:40:56',NULL,'2018-10-17 17:01:24','2018-10-17 17:00:56',NULL,'2018-10-17 16:40:56','2018-10-17 17:01:24',NULL,30),(119,'16183810471716687508',197,174,32,153,'223456789@qq.com','994222322@qq.com',100,'USDT','cny',6.80000000,1176.47058823,0.02000000,NULL,NULL,'45345','https://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/1539766078255%E5%BE%AE%E4%BF%A1%E6%88%AA%E5%9B%BE_20181017164750.png','canceled by system',NULL,NULL,NULL,'������������������(������������)','2018-10-17 16:47:38','2018-10-17 16:48:08','2018-10-18 20:47:37','2018-10-17 17:38:08',NULL,'2018-10-17 16:47:38','2018-10-18 20:47:37',2,32),(120,'16182310511716687514',198,148,153,17,'994222322@qq.com','18673582670',100,'BTC','CNY',43220.00000000,0.02313743,0.01000000,NULL,NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-10-17 16:51:23',NULL,'2018-10-17 16:53:59','2018-10-17 16:53:23',NULL,'2018-10-17 16:51:23','2018-10-17 16:53:59',NULL,153),(121,'2018541058181316740',203,202,171,174,'18673582671','18673582672',1,'btc','CNY',42300.00000000,10.00000000,0.01000000,NULL,NULL,'45345','https://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/15398675544971.jpg',NULL,NULL,NULL,NULL,'deal by system','2018-10-18 20:58:54','2018-10-18 20:59:18',NULL,'2018-10-18 21:01:18','2018-10-18 21:01:55','2018-10-18 20:58:54','2018-10-18 21:01:55',1,171),(122,'2118331003181316746',204,202,171,174,'18673582671','18673582672',1,'btc','CNY',42300.00000000,89.00000000,0.01000000,NULL,NULL,'45345','https://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/15398678182911.jpg',NULL,NULL,NULL,NULL,NULL,'2018-10-18 21:03:33','2018-10-18 21:03:40',NULL,'2018-10-18 21:05:40','2018-10-18 21:04:03','2018-10-18 21:03:33','2018-10-18 21:04:03',1,171),(123,'1318241001191316758',205,207,126,23,'13500000089','552386231@qq.com',1,'usdt','cny',6.60000000,500.00000000,0.02000000,'',NULL,'45345','https://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/15399253312861.png',NULL,NULL,NULL,NULL,NULL,'2018-10-19 13:01:24','2018-10-19 13:02:15',NULL,'2018-10-19 13:52:15','2018-10-19 13:03:47','2018-10-19 13:01:24','2018-10-19 13:03:47',2,23),(124,'1318151010191316767',208,209,23,126,'552386231@qq.com','13500000089',1,'usdt','cny',6.90000000,28.98550724,0.02000000,'',NULL,'45345','https://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/153992588202110.png',NULL,NULL,NULL,NULL,NULL,'2018-10-19 13:10:15','2018-10-19 13:11:34',NULL,'2018-10-19 14:01:34','2018-10-19 13:12:49','2018-10-19 13:10:15','2018-10-19 13:12:49',2,126),(125,'1318561047191316776',211,210,126,23,'13500000089','552386231@qq.com',7,'usdt','cny',6.80000000,58.82352941,0.02000000,NULL,NULL,'45345','https://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/153992811934725_214733_8776ed0d5e0dd08.jpg',NULL,NULL,NULL,NULL,'������������������(������������)','2018-10-19 13:47:56','2018-10-19 13:48:48',NULL,'2018-10-19 14:38:48',NULL,'2018-10-19 13:47:56','2018-10-19 14:39:04',2,126),(126,'1418351001191316788',214,213,23,126,'552386231@qq.com','13500000089',1,'usdt','cny',6.90000000,600.00000000,0.02000000,NULL,NULL,'45345','https://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/15399289238541.png',NULL,NULL,NULL,NULL,NULL,'2018-10-19 14:01:35','2018-10-19 14:03:11',NULL,'2018-10-19 14:53:11','2018-10-19 14:07:48','2018-10-19 14:01:35','2018-10-19 14:07:48',2,23),(127,'1618361101091900422',176,215,174,169,'18673582672','15220167101',100,'ETH','cny',1.00000000,1.00000000,0.01000000,'',NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-11-09 16:01:36',NULL,'2018-11-09 16:32:26','2018-11-09 16:31:36',NULL,'2018-11-09 16:01:36','2018-11-09 16:32:26',NULL,169),(128,'1618541101091900428',216,210,169,23,'15220167101','552386231@qq.com',100,'usdt','cny',6.80000000,1.47058823,0.02000000,NULL,NULL,NULL,NULL,'',NULL,NULL,NULL,NULL,'2018-11-09 16:01:54',NULL,'2018-11-09 16:02:03','2018-11-09 16:21:54',NULL,'2018-11-09 16:01:54','2018-11-09 16:02:03',NULL,169),(129,'1018511140191561525',217,218,169,170,'15220167101','15220167102',100,'ETH','cny',1.50000000,1.00000000,0.01000000,'',NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-11-19 10:40:51',NULL,'2018-11-19 11:10:55','2018-11-19 11:10:51',NULL,'2018-11-19 10:40:51','2018-11-19 11:10:55',NULL,170),(130,'1018561155191561531',217,219,169,170,'15220167101','15220167102',100,'ETH','cny',1.50000000,1.00000000,0.01000000,'',NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-11-19 10:55:56',NULL,'2018-11-19 10:58:55','2018-11-19 10:57:56',NULL,'2018-11-19 10:55:56','2018-11-19 10:58:55',NULL,170),(131,'1118151101191561537',217,220,169,170,'15220167101','15220167102',100,'ETH','cny',1.50000000,1.00000000,0.01000000,'',NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-11-19 11:01:15',NULL,'2018-11-19 11:03:19','2018-11-19 11:03:15',NULL,'2018-11-19 11:01:15','2018-11-19 11:03:19',NULL,170),(132,'1118591109191561546',222,221,170,169,'15220167102','15220167101',100,'ETH','cny',1.30000000,1.00000000,0.01000000,NULL,NULL,NULL,NULL,'������������(������������)',NULL,NULL,NULL,NULL,'2018-11-19 11:09:59',NULL,'2018-11-19 11:12:08','2018-11-19 11:11:59',NULL,'2018-11-19 11:09:59','2018-11-19 11:12:08',NULL,170),(133,'1118301113191561552',223,221,170,169,'15220167102','15220167101',7,'ETH','cny',1.30000000,1.00000000,0.01000000,NULL,NULL,'45345','https://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/15425972275823333.jpg',NULL,NULL,NULL,NULL,'������������������(������������)','2018-11-19 11:13:30','2018-11-19 11:13:47',NULL,'2018-11-19 11:15:47',NULL,'2018-11-19 11:13:30','2018-11-19 11:16:32',2,170),(134,'1218161133191794622',224,221,170,169,'15220167102','15220167101',100,'ETH','cny',1.30000000,1.00000000,0.01000000,NULL,NULL,'','https://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/1542602892291.png','',NULL,NULL,NULL,NULL,'2018-11-19 12:33:16','2018-11-19 12:33:28','2018-11-19 12:33:31','2018-11-19 12:35:28',NULL,'2018-11-19 12:33:16','2018-11-19 12:33:31',2,170),(135,'1418301159201908722',225,221,231,169,'15220166100','15220167101',1,'ETH','cny',1.30000000,1.00000000,0.01000000,NULL,NULL,'������','http://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/43c799cc8a6003b417f987783df3337d1542697189597.jpg',NULL,NULL,'029855','ftttdff','deal by system','2018-11-20 14:59:30','2018-11-20 14:59:48',NULL,'2018-11-20 15:01:48','2018-11-20 15:03:10','2018-11-20 14:59:30','2018-11-20 15:03:10',2,231),(136,'1618521126201908728',226,221,231,169,'15220166100','15220167101',100,'ETH','cny',1.30000000,1.00000000,0.01000000,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2018-11-20 16:26:52',NULL,'2018-11-20 16:26:55','2018-11-20 16:28:52',NULL,'2018-11-20 16:26:52','2018-11-20 16:26:55',NULL,231),(137,'1718411122201908734',227,221,157,169,'15220167142','15220167101',100,'ETH','cny',1.30000000,1.00000000,0.01000000,NULL,NULL,NULL,NULL,'',NULL,NULL,NULL,NULL,'2018-11-20 17:22:41',NULL,'2018-11-20 17:22:44','2018-11-20 17:24:41',NULL,'2018-11-20 17:22:41','2018-11-20 17:22:44',NULL,157),(138,'1718331148201908740',228,221,157,169,'15220167142','15220167101',100,'ETH','cny',1.30000000,7.69230700,0.01000000,NULL,NULL,'���������','http://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/43c799cc8a6003b417f987783df3337d1542707325314.jpg',NULL,NULL,NULL,NULL,NULL,'2018-11-20 17:48:33','2018-11-20 17:48:43','2018-11-20 17:48:46','2018-11-20 17:50:43',NULL,'2018-11-20 17:48:33','2018-11-20 17:48:46',1,157),(139,'1118391159211908785',233,232,239,238,'13813800004','13813800003',1,'usdt','cny',3.00000000,2900.00000000,0.02000000,NULL,NULL,'45345','https://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/1542772783207%E5%BE%AE%E4%BF%A1%E6%88%AA%E5%9B%BE_20181121115352.png',NULL,NULL,NULL,NULL,NULL,'2018-11-21 11:59:39','2018-11-21 11:59:46',NULL,'2018-11-21 12:49:46','2018-11-21 12:00:17','2018-11-21 11:59:39','2018-11-21 12:00:17',2,239),(140,'13181711462119087145',239,238,236,240,'13813800001','13813800005',1,'usdt','cny',5.00000000,1960.00000000,0.02000000,NULL,NULL,'45345','https://nfsshenzhenjiaoyi.oss-cn-hongkong.aliyuncs.com/1542779181544%E5%BE%AE%E4%BF%A1%E6%88%AA%E5%9B%BE_20181121115352.png',NULL,NULL,NULL,NULL,NULL,'2018-11-21 13:46:17','2018-11-21 13:46:24',NULL,'2018-11-21 14:36:24','2018-11-21 13:47:05','2018-11-21 13:46:17','2018-11-21 13:47:05',2,236);
/*!40000 ALTER TABLE `t_otc_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_otc_pay`
--

DROP TABLE IF EXISTS `t_otc_pay`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_otc_pay` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '������ID',
  `bank_type` int(11) NOT NULL COMMENT '���������������0������������������1���������������2���������������',
  `bank_name` varchar(64) NOT NULL COMMENT '������������������,������������������',
  `bank_user` varchar(64) NOT NULL COMMENT '������������������������/���������������������(������)',
  `bank_no` varchar(64) NOT NULL COMMENT '���������������������/���������/���������',
  `order_id` bigint(20) NOT NULL COMMENT '������������������',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_otc_pay`
--

LOCK TABLES `t_otc_pay` WRITE;
/*!40000 ALTER TABLE `t_otc_pay` DISABLE KEYS */;
INSERT INTO `t_otc_pay` VALUES (1,4,2,'������','������','483',1,'2018-08-24 15:50:13'),(2,3,0,'������','���������','6222022010022821455',2,'2018-08-29 11:25:47'),(3,3,0,'������','���������','6222022010022821455',9,'2018-08-31 16:57:16'),(4,3,0,'������','���������','6222022010022821455',10,'2018-09-01 16:10:57'),(5,3,2,'������','���������','987652',11,'2018-10-08 10:03:31'),(6,170,1,'���������','xiaoming','zhifubao7102',27,'2018-10-12 16:32:22'),(7,170,1,'���������','xiaoming','zhifubao7102',28,'2018-10-12 16:33:54'),(8,170,2,'������','xiaoming','weixin7102',30,'2018-10-12 17:02:48'),(9,170,1,'���������','xiaoming','Zhofubao7102',33,'2018-10-12 17:40:44'),(10,170,1,'���������','xiaoming','Zhofubao7102',33,'2018-10-12 17:40:48'),(11,3,2,'������','���������','987652',35,'2018-10-12 17:57:11'),(12,170,0,'������������','xiaoming','6217007200041743900',37,'2018-10-12 18:05:01'),(13,170,1,'���������','xiaoming','Zhofubao7102',40,'2018-10-12 18:23:55'),(14,142,2,'������','���������2','������34���2',41,'2018-10-12 18:24:24'),(15,169,2,'������','������','rr',43,'2018-10-12 18:32:11'),(16,170,1,'���������','xiaoming','Zhofubao7102',49,'2018-10-12 18:43:02'),(17,169,1,'���������','������','ces',53,'2018-10-12 18:57:10'),(18,170,2,'������','xiaoming','weixin7102',56,'2018-10-12 19:09:49'),(19,3,2,'������','���������','987652',57,'2018-10-15 10:15:32'),(20,17,1,'���������','hk','55332266',58,'2018-10-15 10:53:42'),(21,17,1,'���������','hk','55332266',60,'2018-10-15 11:17:34'),(22,17,1,'���������','hk','55332266',61,'2018-10-15 11:19:48'),(23,169,2,'������','������','rr',89,'2018-10-15 12:04:57'),(24,17,1,'���������','hk','55332266',93,'2018-10-15 16:10:45'),(25,17,1,'���������','hk','55332266',100,'2018-10-15 16:25:11'),(26,17,1,'���������','hk','55332266',99,'2018-10-15 16:25:29'),(27,17,1,'���������','hk','55332266',101,'2018-10-15 16:35:14'),(28,169,2,'������','������','rr',103,'2018-10-15 20:31:53'),(29,17,1,'���������','hk','55332266',102,'2018-10-15 20:32:06'),(30,32,2,'������','������','3534534',104,'2018-10-15 20:34:18'),(31,32,2,'������','������','3534534',105,'2018-10-15 20:39:22'),(32,176,2,'������','������','549588789@qq.com',106,'2018-10-15 21:41:54'),(33,176,2,'������','������','549588789@qq.com',108,'2018-10-15 21:50:58'),(34,153,2,'������','������','1',109,'2018-10-15 22:00:49'),(35,30,2,'������','asd','15646764231364654',111,'2018-10-16 16:22:56'),(36,17,1,'���������','hk','55332266',112,'2018-10-16 17:29:33'),(37,169,2,'������','������','rr',115,'2018-10-17 15:01:24'),(38,17,1,'���������','hk','55332266',117,'2018-10-17 15:17:06'),(39,153,2,'������','������','1',119,'2018-10-17 16:48:08'),(40,174,1,'���������','100','22336655',121,'2018-10-18 20:59:18'),(41,174,1,'���������','100','22336655',122,'2018-10-18 21:03:40'),(42,23,2,'������','���������','546543212131564',123,'2018-10-19 13:02:15'),(43,126,2,'������','���������','4564565',124,'2018-10-19 13:11:34'),(44,23,2,'������','���������','546543212131564',125,'2018-10-19 13:48:48'),(45,126,2,'������','���������','4564565',126,'2018-10-19 14:03:11'),(46,169,2,'������','������','rr',133,'2018-11-19 11:13:47'),(47,169,2,'������','������','rr',134,'2018-11-19 12:33:28'),(48,169,2,'������','������','rr',135,'2018-11-20 14:59:48'),(49,169,1,'���������','������','ces',138,'2018-11-20 17:48:43'),(50,238,2,'������','5644asdf','',139,'2018-11-21 11:59:46'),(51,240,2,'������','654������������','',140,'2018-11-21 13:46:24');
/*!40000 ALTER TABLE `t_otc_pay` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_otc_stat`
--

DROP TABLE IF EXISTS `t_otc_stat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_otc_stat` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '������ID',
  `coin_name` varchar(10) NOT NULL COMMENT '������',
  `legal_name` varchar(10) DEFAULT 'cny' COMMENT '������',
  `amount_pass` decimal(32,8) DEFAULT '0.00000000' COMMENT '������������������',
  `amount_fail` decimal(32,8) DEFAULT '0.00000000' COMMENT '������������������',
  `trade_pass` int(11) DEFAULT '0' COMMENT '������������������',
  `trade_fail` int(11) DEFAULT '0' COMMENT '������������������',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_userid_coinname` (`user_id`,`coin_name`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_otc_stat`
--

LOCK TABLES `t_otc_stat` WRITE;
/*!40000 ALTER TABLE `t_otc_stat` DISABLE KEYS */;
INSERT INTO `t_otc_stat` VALUES (1,4,'ETH','cny',1.00000000,0.00000000,1,0,'2018-08-24 15:50:49','2018-08-24 15:50:49'),(2,3,'BTC','cny',0.01601600,0.00000000,1,0,'2018-08-29 11:26:05','2018-08-29 11:26:05'),(3,170,'usdt','CNY',1.00000000,66.50000000,1,11,'2018-10-12 17:05:27','2018-10-12 17:05:27'),(4,142,'ETH','cny',10.98297638,0.00000000,1,0,'2018-10-12 18:26:11','2018-10-12 18:26:11'),(5,170,'eth','CNY',2.00000000,62.99999900,2,7,'2018-10-12 18:26:38','2018-10-12 19:12:07'),(6,169,'eth','CNY',1.00000000,3.00000000,1,3,'2018-10-12 18:59:27','2018-11-20 15:03:10'),(7,17,'BTC','CNY',100.12000000,99.46111805,5,8,'2018-10-15 11:14:12','2018-10-15 16:32:37'),(8,32,'BTC','cny',2.00000000,0.00000000,2,0,'2018-10-15 20:35:58','2018-10-15 20:39:53'),(9,176,'USDT','cny',1000.00000000,2000.00000000,1,2,'2018-10-15 21:58:18','2018-10-15 21:58:18'),(10,30,'USDT','cny',0.01000000,0.00000000,1,0,'2018-10-16 16:23:45','2018-10-16 16:23:45'),(11,169,'usdt','CNY',1.00000000,174.20000000,1,26,'2018-10-17 15:05:42','2018-10-17 15:05:42'),(12,17,'eth','CNY',11.00000000,0.00000000,1,0,'2018-10-17 15:39:52','2018-10-17 15:39:52'),(13,174,'btc','CNY',99.00000000,0.00000000,2,0,'2018-10-18 21:01:55','2018-10-18 21:04:03'),(14,23,'usdt','cny',500.00000000,0.00000000,1,0,'2018-10-19 13:03:47','2018-10-19 13:03:47'),(15,126,'usdt','cny',628.98550724,0.00000000,2,0,'2018-10-19 13:12:49','2018-10-19 14:07:48'),(16,238,'usdt','cny',2900.00000000,0.00000000,1,0,'2018-11-21 12:00:17','2018-11-21 12:00:17'),(17,240,'usdt','cny',1960.00000000,0.00000000,1,0,'2018-11-21 13:47:05','2018-11-21 13:47:05');
/*!40000 ALTER TABLE `t_otc_stat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_received_coin`
--

DROP TABLE IF EXISTS `t_received_coin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_received_coin` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) unsigned NOT NULL COMMENT '������������������id',
  `address` varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT '���������������������',
  `coin_name` varchar(8) COLLATE utf8mb4_bin NOT NULL COMMENT '������������',
  `txid` varchar(100) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '���������������id',
  `amount` decimal(32,8) unsigned NOT NULL DEFAULT '0.00000000' COMMENT '���������������������������',
  `fee` decimal(20,8) NOT NULL DEFAULT '0.00000000' COMMENT '������������������������������������������������amount-fee',
  `tx_time` int(11) unsigned DEFAULT NULL COMMENT '���������������������������������������������������������������������������',
  `received_time` int(11) unsigned DEFAULT NULL COMMENT '���������������������������������������������',
  `status` tinyint(1) DEFAULT '0' COMMENT '���������1���������������',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_coin_txid` (`coin_name`,`txid`),
  KEY `fk_received_user_id_idx` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='���������������������������';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_received_coin`
--

LOCK TABLES `t_received_coin` WRITE;
/*!40000 ALTER TABLE `t_received_coin` DISABLE KEYS */;
INSERT INTO `t_received_coin` VALUES (1,6,'0x76288f4222a60db52c2360426d5fe0fa7a1c3582','ETH','0x813d107700236d12bc4fabd3540921ef1c3f717923cb07d8adbca0555c41624d',100.00000000,0.00000000,1535353053,1535353360,1),(3,1,'0x5846a28a084955df5c3b54ddb3a7c68a0b45cd96','ETH','0xe1e4ab98e90350adbbce990b050633c589ba6042f34a6443bd21532ea36337ad',10000.00000000,0.00000000,1535355860,1535356136,1),(8,7,'0x17dfaad634c4521fa2d9ec3cc4112451a0235857','ETH','inner-1535458203369-6--2122780788',0.80000000,0.20000000,1535458203,1535458203,1),(9,7,'0x17dfaad634c4521fa2d9ec3cc4112451a0235857','ETH','inner-1535512036521-6-413086451',0.80000000,0.20000000,1535512036,1535512036,1),(10,157,'0xd6f2876baa565d5031dc49e9f28c65234489ca1c','ETH','inner-1538209114754-158-2132270891',7.20000000,1.80000000,1538209114,1538209114,1),(11,157,'0xd6f2876baa565d5031dc49e9f28c65234489ca1c','ETH','inner-1538210430425-158-1298838112',8.00000000,2.00000000,1538210430,1538210430,1),(12,159,'0xc82882a7157759fdf77b5c27b1a3ff610fccae16','BON','inner-1538210718612-161--332815452',9.70000000,0.30000000,1538210718,1538210718,1),(13,158,'0x5da71645824aa9891926ff7f7aa2725ad4cb1831','ETH','0xd4253d8ce2287a799fd49ee3d1c02e76fb08f3de3f239b4c769ce7c2fbbd89f8',2.50000000,0.00000000,1538210822,1538210959,1),(14,158,'0x5da71645824aa9891926ff7f7aa2725ad4cb1831','ETH','0x746be0de89da43d764fb062012b6d754c41de6b457e9f57c0b79258f7ce4b1d0',2.00000000,0.00000000,1538211303,1538211566,1),(15,158,'0x5da71645824aa9891926ff7f7aa2725ad4cb1831','ETH','0x7bbc0759c581f50c6c3de0effba70d7e51c4ba118bb9496bcf590f1773c67b2b',3.00000000,0.00000000,1538211374,1538211814,1),(16,158,'0x5da71645824aa9891926ff7f7aa2725ad4cb1831','ETH','0x1d1aff478a3f2adf3cb648ea076008ea236521c17f0c8ee9af755e789532ee77',4.00000000,0.00000000,1538211709,1538211941,1),(17,159,'0xc82882a7157759fdf77b5c27b1a3ff610fccae16','BON','inner-1538274668143-147-2048205312',97.00000000,3.00000000,1538274668,1538274668,1),(18,169,'0x778c4aa9b71e60515982f74c1160a2233fd6e0c4','PKT','0x8f7bf3fac07fff9d47218c256887b957f3f4934f5319b6715dfee6ef7648a29a',10.00000000,0.00000000,1539079481,1539079696,1),(19,170,'0x9ba4e6181920fc6ec6634a8d626f44fbfaf42b6f','PKT','0xb32f3262e63a1fcf916a5a9f5ebda1f19eee397a18992953cee6875e834f5789',100.00000000,0.00000000,1539079910,1539080184,1),(20,17,'0x231db84ffdd69ce8cad5c86593a9ec2ed0d79818','BTC','inner-1539156240752-17-1299189936',4.99950000,0.00050000,1539156240,1539156240,1),(21,17,'0x231db84ffdd69ce8cad5c86593a9ec2ed0d79815','ETH','inner-1539156302097-17-524459400',6.40000000,1.60000000,1539156302,1539156302,1),(22,17,'0x231db84ffdd69ce8cad5c86593a9ec2ed0d79815','ETH','inner-1539156384835-17--1139431320',8.00000000,2.00000000,1539156384,1539156384,1),(23,17,'0x231db84ffdd69ce8cad5c86593a9ec2ed0d79818','BTC','inner-1539156733120-17-858949600',9.99900000,0.00100000,1539156733,1539156733,1),(24,157,'0xd6f2876baa565d5031dc49e9f28c65234489ca1c','PKT','0x28ee2ce802e07a805565ba4f794a91500bdacd4936400af6b2ae7974da138230',8.00000000,0.00000000,1539160535,1539160760,1),(25,169,'0x778c4aa9b71e60515982f74c1160a2233fd6e0c4','PKT','0x203974f0e85130c4dd86b670a6e3942c620ae01d80e974eee93750c44e4aa3fc',8.00000000,0.00000000,1539161242,1539161545,1),(26,170,'0x9ba4e6181920fc6ec6634a8d626f44fbfaf42b6f','PKT','0x9959dd4d16b950467edfa41f7cd4fcab850c7e7504a4b6ca9d9073266ae2e357',100.00000000,0.00000000,1539161283,1539161672,1),(27,157,'0xd6f2876baa565d5031dc49e9f28c65234489ca1c','PKT','0x1b2c0c30cc9247b39d90aabe18635eb5211a8bd9670e0e3e3f0cd98dee40f14c',8.00000000,0.00000000,1539161311,1539161920,1),(28,157,'0xd6f2876baa565d5031dc49e9f28c65234489ca1c','PKT','0xb718bd015b7e86eb5ab8a192ff92488a7dba5af90bd413d793c5189da6a34948',7.92000000,0.00000000,1539161569,1539162047,1),(29,169,'0x778c4aa9b71e60515982f74c1160a2233fd6e0c4','PKT','0x61721b38f83a962358db1e72422e10476bc364decae3f4dff26814481188b2fb',40.00000000,0.00000000,1539162051,1539162294,1),(30,157,'0xd6f2876baa565d5031dc49e9f28c65234489ca1c','PKT','0x8c8b72419d0ffba7a01aab2e65c0ffd115a1f7a2745204cde043ece2ba5b3933',99.00000000,0.00000000,1539162051,1539162422,1),(31,169,'0x778c4aa9b71e60515982f74c1160a2233fd6e0c4','PKT','0x40b2d1c9d4bf11408a3b93a4cfba5d4f6d0a7cd35d59dc651d5182fbdd756b2c',40.00000000,0.00000000,1539162093,1539162549,1),(32,170,'0x9ba4e6181920fc6ec6634a8d626f44fbfaf42b6f','PKT','0xbb6a3414e35dbb66db33338991d52566e2112ab8e78422747c33cfd2383a7420',40.00000000,0.00000000,1539162302,1539162796,1),(33,157,'0xd6f2876baa565d5031dc49e9f28c65234489ca1c','PKT','0xc0ae6eb4320823901183bd6cac460a44d6f25b33b5d9a40dcfd710149784f738',79.20000000,0.00000000,1539165219,1539165484,1),(34,157,'0xd6f2876baa565d5031dc49e9f28c65234489ca1c','PKT','0x46938d9af264522e8ba89b46429d2cc870e87c66b2b5f52466cd0fc6428576d7',39.60000000,0.00000000,1539165282,1539165742,1),(35,169,'0x778c4aa9b71e60515982f74c1160a2233fd6e0c4','PKT','0x3a784e4122e0875b858c28c6c29f0390151cdd52c5331378e0de66632054edd3',10.00000000,0.00000000,1539166255,1539166468,1),(36,169,'0x778c4aa9b71e60515982f74c1160a2233fd6e0c4','PKT','0xda1e7371136d0606c50b36f4587dd2fb4a219fe5e53224b4fdb3c03bc477391f',20.00000000,0.00000000,1539167345,1539167507,1),(37,170,'0x9ba4e6181920fc6ec6634a8d626f44fbfaf42b6f','PKT','0x538b1616009ebe5a171d51a7dbf330411ed65d8762cdb76024cfd5519c883e27',20.00000000,0.00000000,1539167410,1539167635,1),(38,169,'0x778c4aa9b71e60515982f74c1160a2233fd6e0c4','ETH','0x8bf48e29ace0af00b29be640a424cfe1fa43c60feb2e2d1ee6edd20dc1bb1ae2',10.00000000,0.00000000,1539168793,1539169064,1),(39,170,'0x9ba4e6181920fc6ec6634a8d626f44fbfaf42b6f','ETH','0x62db641cab89f3f887cc363f56a54a75cd06371923ec1866f11f89d094879f92',20.00000000,0.00000000,1539168804,1539169191,1),(40,157,'0xd6f2876baa565d5031dc49e9f28c65234489ca1c','PKT','0xe9e173c750c94de9558f96bc87164cfdcd2d9cfba6d8b86e0d5f697fa33939f6',29.70000000,0.00000000,1539203421,1539203826,1),(41,157,'0xd6f2876baa565d5031dc49e9f28c65234489ca1c','PKT','0xb123ca0538b8030a030ca99932fec48b930cc6c7d642419c1713ea357daf7355',19.80000000,0.00000000,1539203448,1539203953,1),(42,142,'0x46245984f05a7e597538747c367b917e52b54270','DG','inner-1539242632804-28--591355255',19.94000000,0.06000000,1539242632,1539242632,1),(43,17,'0x231db84ffdd69ce8cad5c86593a9ec2ed0d79815','ETH','inner-1539259079475-17-785850132',80.00000000,20.00000000,1539259079,1539259079,1),(44,17,'0x231db84ffdd69ce8cad5c86593a9ec2ed0d79818','BTC','inner-1539762874219-17-2067847297',9.99900000,0.00100000,1539762874,1539762874,1),(45,17,'0x231db84ffdd69ce8cad5c86593a9ec2ed0d79818','BTC','inner-1539762952730-17--495874171',9.99900000,0.00100000,1539762952,1539762952,1),(46,17,'0x231db84ffdd69ce8cad5c86593a9ec2ed0d79815','DG','inner-1539832244147-17-1158784058',9.97000000,0.03000000,1539832244,1539832244,1),(47,17,'0x231db84ffdd69ce8cad5c86593a9ec2ed0d79815','dg','inner-1539832644484-17--1276355104',9.97000000,0.03000000,1539832644,1539832644,1),(48,142,'0x46245984f05a7e597538747c367b917e52b54270','ETH','inner-1539881203570-28-1018335622',9.60000000,2.40000000,1539881203,1539881203,1),(49,231,'0x960bcf9e36554348d14c777162d8ce8fb038d394','YT','inner-1542702579907-169--1743871247',10.00000000,0.00000000,1542702579,1542702579,1),(50,231,'0x960bcf9e36554348d14c777162d8ce8fb038d394','YT','inner-1542702703073-169-550417673',20.00000000,0.00000000,1542702703,1542702703,1),(51,231,'0x960bcf9e36554348d14c777162d8ce8fb038d394','YT','inner-1542704222442-169--1026071875',30.00000000,0.00000000,1542704222,1542704222,1),(52,169,'0x778c4aa9b71e60515982f74c1160a2233fd6e0c4','yt','inner-1542704317993-231--1313401787',15.00000000,0.00000000,1542704317,1542704317,1),(53,169,'0x778c4aa9b71e60515982f74c1160a2233fd6e0c4','yt','inner-1542704512022-231-1561061633',10.00000000,0.00000000,1542704512,1542704512,1),(54,169,'0x778c4aa9b71e60515982f74c1160a2233fd6e0c4','yt','inner-1542704604874-231-860746194',33.00000000,0.00000000,1542704604,1542704604,1),(55,169,'0x778c4aa9b71e60515982f74c1160a2233fd6e0c4','YT','inner-1542704676510-231--1007355964',33.00000000,0.00000000,1542704676,1542704676,1),(56,231,'0x960bcf9e36554348d14c777162d8ce8fb038d394','YT','inner-1542707125458-157-245336677',16.00000000,0.00000000,1542707125,1542707125,1),(57,157,'0xd6f2876baa565d5031dc49e9f28c65234489ca1c','YT','inner-1542707218375-231--2113635704',15.00000000,0.00000000,1542707218,1542707218,1);
/*!40000 ALTER TABLE `t_received_coin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_send_coin`
--

DROP TABLE IF EXISTS `t_send_coin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_send_coin` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) unsigned NOT NULL COMMENT '������������������id',
  `received_user_id` int(11) unsigned DEFAULT NULL COMMENT '���������������id������������������������������������������',
  `address` varchar(64) NOT NULL COMMENT '���������������',
  `coin_name` varchar(8) NOT NULL DEFAULT '' COMMENT '������������',
  `txid` varchar(100) DEFAULT '' COMMENT '���������������id',
  `amount` decimal(20,8) unsigned NOT NULL DEFAULT '0.00000000' COMMENT '���������������������������',
  `fee` decimal(20,8) unsigned DEFAULT NULL COMMENT '������������',
  `send_time` datetime NOT NULL COMMENT '���������������������������������������������',
  `status` int(11) DEFAULT '0' COMMENT '���������1���������������������,0������������2������������',
  `last_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `second_status` int(11) DEFAULT '0' COMMENT '������������������������������������0,1���������������������������������������������������������1,0���������������������������������������1,1���������������������������������������2:1��������������������������������� ���2:0������������������������������������',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_coin_txid` (`coin_name`,`txid`),
  KEY `fk_send_coin_user_id_idx` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=utf8mb4 COMMENT='������������������������������';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_send_coin`
--

LOCK TABLES `t_send_coin` WRITE;
/*!40000 ALTER TABLE `t_send_coin` DISABLE KEYS */;
INSERT INTO `t_send_coin` VALUES (17,6,NULL,'0x76f08dd668977eb0b45e495494574145728f6c08','ETH','0xf5f7fc3ae86ae455448b8e4d44250661766222c6ce7567f852869db27ea57a60',1.00000000,0.20000000,'2018-08-28 19:47:32',1,'2018-08-28 19:50:56',1),(18,6,NULL,'0x76f08dd668977eb0b45e495494574145728f6c08','ETH','0xe784967d2e252004e8c4f6b48d7c92cab7eccc24e7fd8131d0d87cf6be109872',1.00000000,0.20000000,'2018-08-28 19:58:39',1,'2018-08-28 20:00:06',1),(19,6,NULL,'0x76f08dd668977eb0b45e495494574145728f6c08','ETH','0xcd3a53d33afdf56478921de42af571653da87a8547f80c0b135919556a8890fc',7.00000000,6.00000000,'2018-08-28 20:04:53',1,'2018-08-28 20:05:43',1),(20,6,NULL,'0x76f08dd668977eb0b45e495494574145728f6c08','ETH',NULL,1.00000000,0.20000000,'2018-08-28 20:08:27',2,'2018-08-28 20:08:48',0),(21,6,NULL,'0x76f08dd668977eb0b45e495494574145728f6c08','ETH',NULL,1.00000000,0.20000000,'2018-08-28 20:09:15',2,'2018-08-28 20:09:36',1),(22,6,7,'0x17dfaad634c4521fa2d9ec3cc4112451a0235857','ETH','inner-1535458203369-6--2122780788',1.00000000,0.20000000,'2018-08-28 20:10:03',1,'2018-08-28 20:10:03',0),(23,6,7,'0x17dfaad634c4521fa2d9ec3cc4112451a0235857','ETH','inner-1535512036521-6-413086451',1.00000000,0.20000000,'2018-08-29 11:07:16',1,'2018-08-29 11:07:16',0),(24,6,NULL,'0x76f08dd668977eb0b45e495494574145728f6c08','ETH','0x71f8664d45f6a981a1747c287b0cc59d9d9aa557ea698d427f469504b9c89a1b',1.00000000,0.20000000,'2018-08-29 11:14:36',1,'2018-08-29 11:16:52',1),(25,6,NULL,'0x76f08dd668977eb0b45e495494574145728f6c08','ETH',NULL,1.00000000,0.20000000,'2018-08-29 11:18:57',2,'2018-08-29 11:19:08',0),(26,6,NULL,'0x76f08dd668977eb0b45e495494574145728f6c08','ETH',NULL,1.00000000,0.20000000,'2018-08-29 11:21:26',2,'2018-08-29 11:23:56',1),(27,19,NULL,'434343434343434343434343434343434343434343','ETH',NULL,22.00000000,4.40000000,'2018-09-04 17:46:57',0,'2018-09-04 17:46:57',0),(28,30,NULL,'0x5846a28a084955df5c3b54ddb3a7c68a0b45cd96','DG',NULL,10.00000000,0.03000000,'2018-09-06 15:36:45',2,'2018-09-06 15:42:11',1),(29,30,NULL,'0x5846a28a084955df5c3b54ddb3a7c68a0b45cd96','DG',NULL,100.00000000,0.30000000,'2018-09-06 15:37:58',2,'2018-09-06 15:42:07',1),(30,17,NULL,'CTrLa4BPRN5DrLDPqFbesPeKAbPXi7iaLa','BTC',NULL,1.11000000,0.00011100,'2018-09-14 15:58:16',0,'2018-09-14 16:05:27',1),(31,17,NULL,'CTrLa4BPRN5DrLDPqFbesPeKAbPXi7iaLa','BTC',NULL,1.00000000,0.00010000,'2018-09-14 16:13:29',0,'2018-09-14 16:13:29',0),(32,17,NULL,'CTrLa4BPRN5DrLDPqFbesPeKAbPXi7iaLa','BTC',NULL,1.00000000,0.00010000,'2018-09-14 16:13:46',0,'2018-09-14 16:13:46',0),(33,17,NULL,'CTrLa4BPRN5DrLDPqFbesPeKAbPXi7iaLa','BTC',NULL,1.00000000,0.00010000,'2018-09-14 16:13:50',0,'2018-09-14 16:13:50',0),(34,17,NULL,'CTrLa4BPRN5DrLDPqFbesPeKAbPXi7iaLa','BTC',NULL,1.00000000,0.00010000,'2018-09-14 16:13:50',0,'2018-09-14 16:13:50',0),(35,17,NULL,'CTrLa4BPRN5DrLDPqFbesPeKAbPXi7iaLa','BTC',NULL,1.00000000,0.00010000,'2018-09-14 16:13:51',0,'2018-09-14 16:15:01',1),(36,17,NULL,'CTrLa4BPRN5DrLDPqFbesPeKAbPXi7iaLa','BTC',NULL,1.00000000,0.00010000,'2018-09-14 16:13:51',0,'2018-09-14 16:13:51',0),(37,17,NULL,'CTrLa4BPRN5DrLDPqFbesPeKAbPXi7iaLa','BTC',NULL,1.00000000,0.00010000,'2018-09-14 16:13:51',0,'2018-09-14 16:13:51',0),(38,17,NULL,'CTrLa4BPRN5DrLDPqFbesPeKAbPXi7iaLa','BTC',NULL,1.00000000,0.00010000,'2018-09-14 16:13:51',0,'2018-09-14 16:14:47',1),(39,17,NULL,'CTrLa4BPRN5DrLDPqFbesPeKAbPXi7iaLa','BTC',NULL,1.00000000,0.00010000,'2018-09-14 16:13:51',0,'2018-09-14 16:14:52',1),(40,17,NULL,'CTrLa4BPRN5DrLDPqFbesPeKAbPXi7iaLa','BTC',NULL,1.00000000,0.00010000,'2018-09-14 16:13:51',0,'2018-09-14 16:14:54',1),(41,17,NULL,'CTrLa4BPRN5DrLDPqFbesPeKAbPXi7iaLa','BTC',NULL,1.00000000,0.00010000,'2018-09-14 16:13:51',0,'2018-09-14 16:14:56',1),(42,17,NULL,'CTrLa4BPRN5DrLDPqFbesPeKAbPXi7iaLa','BTC',NULL,1.00000000,0.00010000,'2018-09-14 16:13:51',0,'2018-09-14 16:14:59',1),(43,38,NULL,'123','YT',NULL,100.00000000,0.00000000,'2018-09-20 10:05:29',0,'2018-09-20 10:05:59',1),(44,38,NULL,'123','DG',NULL,100.00000000,0.30000000,'2018-09-20 10:08:30',0,'2018-09-20 10:08:57',1),(45,158,NULL,'0x87499Dc4F1de4fc5dF809fFCA529533319B01fbE','ETH',NULL,3.50000000,0.70000000,'2018-09-28 17:03:53',0,'2018-09-28 17:03:53',0),(46,158,NULL,'0x87499Dc4F1de4fc5dF809fFCA529533319B01fbE','ETH',NULL,4.00000000,0.80000000,'2018-09-28 17:09:38',0,'2018-09-28 17:09:38',0),(47,158,NULL,'0x87499Dc4F1de4fc5dF809fFCA529533319B01fbE','ETH',NULL,10.00000000,2.00000000,'2018-09-28 17:34:34',0,'2018-09-28 17:34:34',0),(48,158,157,'0xd6f2876baa565d5031dc49e9f28c65234489ca1c','ETH','inner-1538209114754-158-2132270891',9.00000000,1.80000000,'2018-09-29 16:18:34',1,'2018-09-29 16:18:34',0),(49,158,NULL,'0x87499Dc4F1de4fc5dF809fFCA529533319B01fbE','ETH',NULL,10.00000000,2.00000000,'2018-09-29 16:30:16',0,'2018-09-29 16:30:16',0),(50,158,157,'0xd6f2876baa565d5031dc49e9f28c65234489ca1c','ETH','inner-1538210430425-158-1298838112',10.00000000,2.00000000,'2018-09-29 16:40:30',1,'2018-09-29 16:40:30',0),(51,158,NULL,'0x87499Dc4F1de4fc5dF809fFCA529533319B01fbE','ETH',NULL,3.50000000,0.70000000,'2018-09-29 16:44:04',0,'2018-09-29 16:44:04',0),(52,161,159,'0xc82882a7157759fdf77b5c27b1a3ff610fccae16','BON','inner-1538210718612-161--332815452',10.00000000,0.30000000,'2018-09-29 16:45:18',1,'2018-10-09 17:45:58',0),(53,157,NULL,'0x87499Dc4F1de4fc5dF809fFCA529533319B01fbE','ETH',NULL,3.00000000,0.60000000,'2018-09-29 16:56:49',0,'2018-09-29 16:56:49',0),(54,157,NULL,'0x87499Dc4F1de4fc5dF809fFCA529533319B01fbE','ETH',NULL,5.00000000,1.00000000,'2018-09-29 16:57:11',0,'2018-09-29 16:57:11',0),(55,158,NULL,'0x87499Dc4F1de4fc5dF809fFCA529533319B01fbE','ETH',NULL,49.00000000,9.80000000,'2018-09-29 17:14:05',0,'2018-09-29 17:14:05',0),(56,158,NULL,'0x87499Dc4F1de4fc5dF809fFCA529533319B01fbE','ETH',NULL,10.00000000,2.00000000,'2018-09-29 17:55:41',0,'2018-09-29 17:55:41',0),(57,158,NULL,'0x87499Dc4F1de4fc5dF809fFCA529533319B01fbE','ETH',NULL,10.00000000,2.00000000,'2018-09-29 18:02:45',0,'2018-10-09 11:30:28',1),(58,147,159,'0xc82882a7157759fdf77b5c27b1a3ff610fccae16','BON','inner-1538274668143-147-2048205312',100.00000000,3.00000000,'2018-09-30 10:31:08',1,'2018-09-30 10:31:08',0),(59,170,NULL,'0x8B4dDc40A7C9F64Fe151AEad795cD01eb41f984B','ETH',NULL,2.00000000,0.40000000,'2018-10-09 16:59:38',2,'2018-10-09 17:00:16',0),(60,170,NULL,'0x8B4dDc40A7C9F64Fe151AEad795cD01eb41f984B','ETH',NULL,4.00000000,0.80000000,'2018-10-09 17:01:36',2,'2018-10-09 17:02:05',0),(61,17,NULL,'CTrLa4BPRN5DrLDPqFbesPeKAbPXi7iaLa','BTC',NULL,1.00000000,0.00010000,'2018-10-10 15:22:54',0,'2018-10-10 15:27:25',1),(62,17,17,'0x231db84ffdd69ce8cad5c86593a9ec2ed0d79818','BTC','inner-1539156240752-17-1299189936',5.00000000,0.00050000,'2018-10-10 15:24:00',1,'2018-10-10 15:24:00',0),(63,17,17,'0x231db84ffdd69ce8cad5c86593a9ec2ed0d79815','ETH','inner-1539156302097-17-524459400',8.00000000,1.60000000,'2018-10-10 15:25:02',1,'2018-10-10 15:25:02',0),(64,17,17,'0x231db84ffdd69ce8cad5c86593a9ec2ed0d79815','ETH','inner-1539156384835-17--1139431320',10.00000000,2.00000000,'2018-10-10 15:26:24',1,'2018-10-10 15:26:24',0),(65,17,17,'0x231db84ffdd69ce8cad5c86593a9ec2ed0d79818','BTC','inner-1539156733120-17-858949600',10.00000000,0.00100000,'2018-10-10 15:32:13',1,'2018-10-10 15:32:13',0),(66,17,NULL,'0x231db84ffdd69ce8cad5c86593a9ec2ed0d7981A','BTC',NULL,10.00000000,0.00100000,'2018-10-10 16:10:04',0,'2018-10-10 16:10:26',1),(67,28,142,'0x46245984f05a7e597538747c367b917e52b54270','DG','inner-1539242632804-28--591355255',20.00000000,0.06000000,'2018-10-11 15:23:52',1,'2018-10-11 15:23:52',0),(68,17,17,'0x231db84ffdd69ce8cad5c86593a9ec2ed0d79815','ETH','inner-1539259079475-17-785850132',100.00000000,20.00000000,'2018-10-11 19:57:59',1,'2018-10-11 19:57:59',0),(69,17,NULL,'CTrLa4BPRN5DrLDPqFbesPeKAbPXi7iaLa','BTC',NULL,10.00000000,0.00100000,'2018-10-11 20:01:21',0,'2018-10-11 20:01:39',1),(70,28,NULL,'thgegdfgvdgjegr','USDT',NULL,20.00000000,2.00000000,'2018-10-17 10:18:33',0,'2018-10-17 10:18:33',0),(71,17,17,'0x231db84ffdd69ce8cad5c86593a9ec2ed0d79818','BTC','inner-1539762874219-17-2067847297',10.00000000,0.00100000,'2018-10-17 15:54:34',1,'2018-10-17 15:54:34',0),(72,17,17,'0x231db84ffdd69ce8cad5c86593a9ec2ed0d79818','BTC','inner-1539762952730-17--495874171',10.00000000,0.00100000,'2018-10-17 15:55:52',1,'2018-10-17 15:55:52',0),(73,17,17,'0x231db84ffdd69ce8cad5c86593a9ec2ed0d79815','DG','inner-1539832244147-17-1158784058',10.00000000,0.03000000,'2018-10-18 11:10:44',1,'2018-10-18 11:10:44',0),(74,17,17,'0x231db84ffdd69ce8cad5c86593a9ec2ed0d79815','dg','inner-1539832644484-17--1276355104',10.00000000,0.03000000,'2018-10-18 11:17:24',1,'2018-10-18 11:17:24',0),(75,28,142,'0x46245984f05a7e597538747c367b917e52b54270','ETH','inner-1539881203570-28-1018335622',12.00000000,2.40000000,'2018-10-19 00:46:43',1,'2018-10-19 00:46:43',0),(76,169,231,'0x960bcf9e36554348d14c777162d8ce8fb038d394','YT','inner-1542702579907-169--1743871247',10.00000000,0.00000000,'2018-11-20 16:29:39',1,'2018-11-20 16:29:39',0),(77,169,231,'0x960bcf9e36554348d14c777162d8ce8fb038d394','YT','inner-1542702703073-169-550417673',20.00000000,0.00000000,'2018-11-20 16:31:43',1,'2018-11-20 16:31:43',0),(78,169,231,'0x960bcf9e36554348d14c777162d8ce8fb038d394','YT','inner-1542704222442-169--1026071875',30.00000000,0.00000000,'2018-11-20 16:57:02',1,'2018-11-20 16:57:02',0),(79,231,169,'0x778c4aa9b71e60515982f74c1160a2233fd6e0c4','yt','inner-1542704317993-231--1313401787',15.00000000,0.00000000,'2018-11-20 16:58:37',1,'2018-11-20 16:58:37',0),(80,231,169,'0x778c4aa9b71e60515982f74c1160a2233fd6e0c4','yt','inner-1542704512022-231-1561061633',10.00000000,0.00000000,'2018-11-20 17:01:52',1,'2018-11-20 17:01:52',0),(81,231,169,'0x778c4aa9b71e60515982f74c1160a2233fd6e0c4','yt','inner-1542704604874-231-860746194',33.00000000,0.00000000,'2018-11-20 17:03:24',1,'2018-11-20 17:03:24',0),(82,231,169,'0x778c4aa9b71e60515982f74c1160a2233fd6e0c4','YT','inner-1542704676510-231--1007355964',33.00000000,0.00000000,'2018-11-20 17:04:36',1,'2018-11-20 17:04:36',0),(83,157,231,'0x960bcf9e36554348d14c777162d8ce8fb038d394','YT','inner-1542707125458-157-245336677',16.00000000,0.00000000,'2018-11-20 17:45:25',1,'2018-11-20 17:45:25',0),(84,231,157,'0xd6f2876baa565d5031dc49e9f28c65234489ca1c','YT','inner-1542707218375-231--2113635704',15.00000000,0.00000000,'2018-11-20 17:46:58',1,'2018-11-20 17:46:58',0);
/*!40000 ALTER TABLE `t_send_coin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_server_config`
--

DROP TABLE IF EXISTS `t_server_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_server_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `server_type` varchar(20) DEFAULT NULL COMMENT '������������������1.��������� 2.��������������������� 3.���������������4��������������� 5.',
  `server_inner_address` varchar(20) DEFAULT NULL COMMENT '���������������ip',
  `server_external_address` varchar(20) DEFAULT NULL COMMENT '���������������������',
  `server_name` varchar(20) DEFAULT NULL COMMENT '������������',
  `port` varchar(20) DEFAULT NULL COMMENT '������������',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_server_config`
--

LOCK TABLES `t_server_config` WRITE;
/*!40000 ALTER TABLE `t_server_config` DISABLE KEYS */;
INSERT INTO `t_server_config` VALUES (2,'1','545456','555','5555','8080'),(3,'api_server','172.31.30.125','172.31.30.125','api������1','8080'),(4,'api_server','172.31.30.125','172.31.30.125','api������2','8088'),(5,'task_server','172.31.30.125','172.31.30.125','������������1','8090'),(6,'db_server','172.31.30.125','172.31.30.125','���������������','3306'),(7,'nginx_server','172.31.30.125','172.31.30.125','nginx���������','80'),(8,'admin_sever','172.31.30.125','172.31.30.125','���������������','8089');
/*!40000 ALTER TABLE `t_server_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_share_out_bonus_log`
--

DROP TABLE IF EXISTS `t_share_out_bonus_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_share_out_bonus_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '������id',
  `coin_name` varchar(12) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '���������������������������������������',
  `user_base_coin` decimal(32,8) unsigned NOT NULL DEFAULT '0.00000000' COMMENT '������������������������������������������',
  `user_bonus` decimal(24,8) unsigned NOT NULL DEFAULT '0.00000000' COMMENT '������������������coin_name���������������������',
  `create_time` datetime DEFAULT NULL COMMENT '������������',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='������������������';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_share_out_bonus_log`
--

LOCK TABLES `t_share_out_bonus_log` WRITE;
/*!40000 ALTER TABLE `t_share_out_bonus_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_share_out_bonus_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_sms_captcha`
--

DROP TABLE IF EXISTS `t_sms_captcha`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_sms_captcha` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `mobile` varchar(128) NOT NULL COMMENT '���������',
  `type` varchar(6) DEFAULT NULL COMMENT '���������:01������,02������������,03������������,04���������������05���������������',
  `code` varchar(6) DEFAULT NULL COMMENT '���������',
  `status` int(11) DEFAULT '0' COMMENT '0������������1������������',
  `lastTime` datetime DEFAULT NULL COMMENT '���������������15������������',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_mobile` (`mobile`,`type`)
) ENGINE=InnoDB AUTO_INCREMENT=233 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sms_captcha`
--

LOCK TABLES `t_sms_captcha` WRITE;
/*!40000 ALTER TABLE `t_sms_captcha` DISABLE KEYS */;
INSERT INTO `t_sms_captcha` VALUES (1,'8615118148788','01','368273',0,'2018-08-23 18:52:26'),(2,'8613421344475','01','123456',0,'2018-08-23 18:58:59'),(3,'8618720975730','01','123456',0,'2018-08-23 19:44:14'),(4,'8618617208190','01','123456',0,'2018-08-24 09:46:24'),(5,'86987@qq.com','01','123456',0,'2018-08-24 09:46:50'),(6,'8618617208190','03','123456',0,'2018-08-24 10:20:30'),(7,'86987@qq.com','03','123456',0,'2018-08-24 10:22:17'),(8,'8615200905810','01','123456',0,'2018-08-24 11:09:48'),(9,'8618612345678','01','123456',0,'2018-08-27 12:07:54'),(10,'8618712345678','01','123456',0,'2018-08-27 12:09:28'),(11,'8615118148777','01','123456',0,'2018-08-27 12:24:23'),(12,'8618720975731','01','123456',0,'2018-08-27 12:32:08'),(13,'8618720975732','01','123456',0,'2018-08-27 14:22:47'),(14,'8618720975733','01','123456',0,'2018-08-27 14:26:39'),(15,'8613823774714','01','123456',0,'2018-08-27 14:37:56'),(16,'8618720975734','01','123456',0,'2018-08-27 14:38:29'),(17,'8618720975735','01','123456',0,'2018-08-27 14:48:28'),(18,'8618720975736','01','123456',0,'2018-08-27 14:51:58'),(19,'8618720975737','01','123456',0,'2018-08-27 14:55:59'),(20,'8618612345678','06','123456',0,'2018-08-29 11:22:14'),(21,'8618612345678','03','123456',0,'2018-08-28 20:13:22'),(22,'8618673582670','01','123456',0,'2018-08-28 10:53:26'),(23,'86346872990@qq.com','01','123456',0,'2018-08-28 11:22:02'),(24,'8618612341234','01','123456',0,'2018-08-28 11:25:56'),(25,'8618612341234','03','123456',0,'2018-08-28 11:31:20'),(26,'862710532526@qq.com','01','123456',0,'2018-08-28 12:21:36'),(27,'862710532526@qq.com','03','123456',0,'2018-08-29 10:54:31'),(28,'863325918@qq.com','01','059878',0,'2018-08-28 14:12:10'),(29,'862710532526@qq.com','02','232203',0,'2018-08-28 14:52:28'),(30,'86505654560@qq.com','01','595669',0,'2018-08-28 14:53:32'),(31,'86250776025@qq.com','01','338993',0,'2018-08-28 16:39:11'),(32,'863325918@qq.com','08','412037',0,'2018-08-28 15:33:59'),(33,'8613928437176','06','244962',0,'2018-08-28 16:45:54'),(34,'86348853142@qq.com','01','123456',0,'2018-08-31 17:47:53'),(35,'8613825972016','01','123456',0,'2018-08-29 20:45:21'),(36,'8618682487401','01','123456',0,'2018-08-29 19:13:56'),(37,'8613826534684','01','123456',0,'2018-08-30 14:59:52'),(38,'8613923499228','01','123456',0,'2018-08-30 13:12:42'),(39,'8618169340241','01','123456',0,'2018-08-30 12:06:28'),(40,'8618617208190','09','123456',0,'2018-08-30 17:56:41'),(41,'861505861679@qq.com','01','123456',0,'2018-08-31 17:32:54'),(42,'86552386231@qq.com','01','123456',0,'2018-08-31 17:37:47'),(43,'86149543604@qq.com','01','123456',0,'2018-08-31 17:43:00'),(44,'86495345760@qq.com','01','123456',0,'2018-08-31 17:44:46'),(45,'86123456@qq.com','01','123456',0,'2018-08-31 17:50:33'),(46,'8616620855816','01','123456',0,'2018-09-01 17:34:08'),(47,'8612345678@qq.com','01','123456',0,'2018-09-03 09:51:07'),(48,'86123456789@qq.com','01','123456',0,'2018-09-03 09:52:42'),(49,'861123456789@qq.com','01','123456',0,'2018-09-03 09:53:17'),(50,'86223456789@qq.com','01','123456',0,'2018-09-03 09:54:16'),(51,'8633456789@qq.com','01','123456',0,'2018-09-03 09:54:42'),(52,'864456789@qq.com','01','123456',0,'2018-09-03 09:59:39'),(53,'86556789123@qq.com','01','123456',0,'2018-09-03 10:00:21'),(54,'866678912345@qq.com','01','123456',0,'2018-09-03 10:00:49'),(55,'86778912345@qq.com','01','123456',0,'2018-09-03 10:01:18'),(56,'8618975375666','01','123456',0,'2018-11-10 09:49:52'),(57,'86lingsan2004@163.com','01','123456',0,'2018-09-03 15:41:50'),(58,'8618576635213','01','123456',0,'2018-09-03 16:02:45'),(59,'8615612345678','01','123456',0,'2018-09-03 16:13:38'),(60,'8618617208190','06','123456',0,'2018-09-03 18:28:08'),(61,'8615612345678@163.com','01','123456',0,'2018-09-03 18:46:08'),(62,'8615612345678','09','123456',0,'2018-09-04 11:33:34'),(63,'86994222322@qq.com','01','123456',0,'2018-09-12 15:19:26'),(64,'86555@qq.com','01','123456',0,'2018-09-04 14:33:11'),(65,'8616620855816','06','123456',0,'2018-10-19 00:47:36'),(66,'8618612341234','06','123456',0,'2018-09-12 16:22:09'),(67,'86406593508@qq.com','01','123456',0,'2018-09-04 17:31:09'),(68,'8618665909816','01','123456',0,'2018-09-04 17:45:09'),(69,'8619920083867','01','123456',0,'2018-09-10 17:55:42'),(70,'862649325661@qq.com','01','123456',0,'2018-09-04 17:49:54'),(71,'86838212893@qq.com','01','123456',0,'2018-09-04 17:51:31'),(72,'8618938648085','01','123456',0,'2018-09-05 17:12:16'),(73,'86123456789@qq.com','06','123456',0,'2018-09-20 10:42:55'),(74,'86123456789@qq.com','03','123456',0,'2018-09-06 15:37:11'),(75,'8613682417938','01','123456',0,'2018-09-07 11:27:48'),(76,'8618823670239','01','123456',0,'2018-09-07 14:44:47'),(77,'8618682487403','01','123456',0,'2018-09-07 14:55:35'),(78,'8613751053360','01','123456',0,'2018-09-10 16:33:16'),(79,'8618576635211','01','123456',0,'2018-09-07 18:31:01'),(80,'86121211','01','123456',0,'2018-09-07 18:36:07'),(81,'861857663@183.com','01','123456',0,'2018-09-07 18:41:49'),(82,'8618576635214','02','123456',0,'2018-09-07 18:44:46'),(83,'861212121@163.com','02','123456',0,'2018-09-07 18:46:17'),(84,'8618823456789','02','123456',0,'2018-09-07 18:47:37'),(85,'8617634567890','02','123456',0,'2018-09-07 18:48:30'),(86,'8618798765432','02','123456',0,'2018-09-07 18:51:16'),(87,'8618765467812','02','123456',0,'2018-09-07 18:51:53'),(88,'8618756754325','02','123456',0,'2018-09-07 18:53:43'),(89,'8618765456765','02','123456',0,'2018-09-07 18:55:15'),(90,'8618976545678','02','123456',0,'2018-09-07 18:56:06'),(91,'8618967654567','01','123456',0,'2018-09-07 18:57:29'),(92,'86789@qq.com','02','123456',0,'2018-09-07 19:00:29'),(93,'86422911094@qq.com','01','123456',0,'2018-09-07 19:02:06'),(94,'86422911094@qq.com','02','123456',0,'2018-09-07 19:03:02'),(95,'8618576635555','01','123456',0,'2018-09-07 19:27:50'),(96,'8600000001','01','123456',0,'2018-09-12 14:02:35'),(97,'8600000001@163.com','01','123456',0,'2018-09-12 14:03:41'),(98,'8613900000001','07','123456',0,'2018-09-12 14:13:37'),(99,'8613590469049','07','123456',0,'2018-09-12 15:20:43'),(100,'8618612341234','02','123456',0,'2018-09-12 16:23:33'),(101,'8618673582670','06','123456',0,'2018-10-18 11:18:55'),(102,'86123@163.com','08','123456',0,'2018-09-13 16:31:48'),(103,'8618673582670','03','123456',0,'2018-09-14 15:58:54'),(104,'8613500000001','06','123456',0,'2018-09-20 10:09:21'),(105,'8613500000001','03','123456',0,'2018-09-20 10:05:30'),(106,'8615118138788','01','123456',0,'2018-09-25 11:30:14'),(107,'8617607615123','01','123456',0,'2018-09-25 11:31:55'),(108,'8612345678900@qq.com','01','123456',0,'2018-09-27 13:46:28'),(109,'8615220167142','01','123456',0,'2018-09-28 15:01:09'),(110,'8615220167143','01','123456',0,'2018-09-28 15:04:05'),(111,'8615220167143','06','123456',0,'2018-09-29 18:03:33'),(112,'8615220167143','03','123456',0,'2018-09-28 17:04:05'),(113,'8618823670240','01','123456',0,'2018-09-29 15:34:23'),(114,'8618823670240','06','123456',0,'2018-09-29 15:59:54'),(115,'8618823670240','03','123456',0,'2018-09-29 15:58:47'),(116,'8618823670241','01','123456',0,'2018-09-29 16:15:45'),(117,'8618823670242','01','123456',0,'2018-09-29 16:40:32'),(118,'8618823670242','06','123456',0,'2018-09-29 16:46:12'),(119,'8618823670242','03','123456',0,'2018-09-29 16:45:28'),(120,'8618823670243','01','123456',0,'2018-09-29 16:52:38'),(121,'8615220167142','06','123456',0,'2018-11-20 17:46:02'),(122,'8615220167142','03','123456',0,'2018-09-29 16:57:12'),(123,'8615512344321','01','123456',0,'2018-09-29 17:44:11'),(124,'8615212345678','01','123456',0,'2018-09-29 17:48:40'),(125,'8618823670239','06','123456',0,'2018-09-30 10:32:05'),(126,'8618823670239','03','123456',0,'2018-09-30 10:31:29'),(127,'8618823670250','01','123456',0,'2018-09-30 18:13:29'),(128,'8615236282361','01','123456',0,'2018-09-30 20:54:10'),(129,'8615220167149','01','123456',0,'2018-10-08 10:04:08'),(130,'8615118148666','01','123456',0,'2018-10-08 11:26:05'),(131,'8615220167101','01','123456',0,'2018-10-08 16:48:12'),(132,'8615220167102','01','123456',0,'2018-10-08 16:48:37'),(133,'8615220167102','06','123456',0,'2018-10-09 17:02:27'),(134,'8615220167102','03','123456',0,'2018-10-09 16:57:35'),(135,'8615220167101','06','123456',0,'2018-11-20 16:57:54'),(136,'8618673582671','01','123456',0,'2018-10-10 17:03:39'),(137,'8616620855816','03','123456',0,'2018-10-11 15:23:55'),(138,'863325919@qq.com','01','123456',0,'2018-10-11 20:27:59'),(139,'8618673582620','07','123456',0,'2018-10-11 20:29:24'),(140,'8618244979393','01','123456',0,'2018-10-12 17:54:36'),(141,'86406593508@qq.com','03','123456',0,'2018-10-12 18:26:46'),(142,'8615220167101','03','123456',0,'2018-11-20 15:02:57'),(143,'8613823774714','03','123456',0,'2018-10-12 18:38:56'),(144,'8613823774714','04','123456',0,'2018-10-12 18:56:25'),(145,'8618673582670','04','123456',0,'2018-10-17 14:59:32'),(146,'86406593508@qq.com','06','123456',0,'2018-10-15 09:49:18'),(147,'8618673582671','03','123456',0,'2018-10-15 11:27:31'),(148,'8615220167101','04','123456',0,'2018-10-15 11:28:36'),(149,'8618244979393','03','123456',0,'2018-10-17 15:21:00'),(150,'8618673582672','01','123456',0,'2018-10-15 16:23:33'),(151,'8613813813813','01','123456',0,'2018-10-15 16:25:34'),(152,'8613418888888','01','123456',0,'2018-10-15 16:33:37'),(153,'86223456789@qq.com','03','123456',0,'2018-10-15 20:36:16'),(154,'86549588789@qq.com','01','123456',0,'2018-10-15 21:24:20'),(155,'8618578996090','01','123456',0,'2018-10-18 11:22:45'),(156,'86generaljking@163.com','01','123456',0,'2018-10-18 11:20:06'),(157,'8616620855816','09','123456',0,'2018-10-18 18:31:58'),(158,'8618244979393','09','123456',0,'2018-10-18 18:26:36'),(159,'8618673582672','03','123456',0,'2018-10-18 21:02:34'),(160,'86carrie@wiselinkcn.com','01','123456',0,'2018-10-23 09:44:32'),(161,'8613510674155','01','123456',0,'2018-10-23 09:48:44'),(162,'86490703646@qq.com','01','123456',0,'2018-10-25 17:23:35'),(163,'8613602463851','01','123456',0,'2018-10-25 17:24:45'),(164,'8613011223344','01','123456',0,'2018-11-06 10:42:37'),(165,'8618673582680','01','123456',0,'2018-11-06 15:01:58'),(166,'8618673582690','01','123456',0,'2018-11-06 15:10:17'),(167,'8618673582691','01','123456',0,'2018-11-06 15:11:22'),(168,'8618673582692','01','123456',0,'2018-11-06 15:17:34'),(169,'863325910@qq.com','01','123456',0,'2018-11-06 15:22:01'),(170,'863325911@qq.com','01','123456',0,'2018-11-06 15:24:38'),(171,'8618673586900','01','123456',0,'2018-11-06 15:27:37'),(172,'8618673582600','01','123456',0,'2018-11-06 15:26:53'),(173,'8613267074714','01','123456',0,'2018-11-06 15:33:21'),(174,'8618673582693','01','123456',0,'2018-11-06 15:34:30'),(175,'8613823774714','06','123456',0,'2018-11-06 15:37:23'),(176,'8618673582694','01','123456',0,'2018-11-06 15:40:00'),(177,'8618673582695','01','123456',0,'2018-11-06 15:46:19'),(178,'8618673582650','01','123456',0,'2018-11-07 11:06:12'),(179,'8618673582651','01','123456',0,'2018-11-07 11:07:12'),(180,'8618673582652','01','123456',0,'2018-11-07 11:08:01'),(181,'8618673582653','01','123456',0,'2018-11-07 11:08:48'),(182,'8618673582654','01','123456',0,'2018-11-07 12:02:41'),(183,'8618673582655','01','123456',0,'2018-11-07 12:03:39'),(184,'8618673582656','01','123456',0,'2018-11-07 14:30:22'),(185,'8618673582646','01','123456',0,'2018-11-07 14:50:07'),(186,'8618673582657','01','123456',0,'2018-11-07 14:51:00'),(187,'8618673582648','01','123456',0,'2018-11-07 14:53:17'),(188,'8618673582649','01','123456',0,'2018-11-07 14:54:53'),(189,'8618673582658','01','123456',0,'2018-11-07 15:15:11'),(190,'86rr@126.com','01','123456',0,'2018-11-08 17:10:07'),(191,'8615220167148','01','123456',0,'2018-11-08 18:08:47'),(192,'8613800138000@qq.com','01','123456',0,'2018-11-09 16:59:17'),(193,'86123456789@163.com','01','123456',0,'2018-11-09 17:09:53'),(194,'86123456@163.com','01','123456',0,'2018-11-09 17:16:53'),(195,'8613812345678','01','123456',0,'2018-11-10 10:18:17'),(196,'8618975375667','01','123456',0,'2018-11-10 10:24:21'),(197,'8618975375668','01','123456',0,'2018-11-10 10:25:28'),(198,'8613000000001','01','123456',0,'2018-11-12 09:41:26'),(199,'8613900000011','01','123456',0,'2018-11-12 09:43:22'),(200,'8618975375661','01','123456',0,'2018-11-14 17:07:56'),(201,'8618975375662','01','123456',0,'2018-11-14 17:13:21'),(202,'8618975375663','01','123456',0,'2018-11-14 17:15:57'),(203,'8613877778881','01','123456',0,'2018-11-17 17:48:27'),(204,'8613877778882','01','123456',0,'2018-11-17 17:51:36'),(205,'8613877778883','01','123456',0,'2018-11-17 17:54:13'),(206,'8613100000000','01','123456',0,'2018-11-18 10:48:37'),(207,'8613100000001','01','123456',0,'2018-11-18 10:53:23'),(208,'8615000000001','01','123456',0,'2018-11-18 10:55:53'),(209,'8613100000002','01','123456',0,'2018-11-18 10:57:16'),(210,'8615000000002','01','123456',0,'2018-11-18 10:57:25'),(211,'8615000000003','01','123456',0,'2018-11-18 10:58:36'),(212,'8613200000000','01','123456',0,'2018-11-18 10:59:00'),(213,'8613200000001','01','123456',0,'2018-11-18 11:00:27'),(214,'8613200000002','01','123456',0,'2018-11-18 11:04:22'),(215,'8613300000000','01','123456',0,'2018-11-18 11:05:46'),(216,'8613300000001','01','123456',0,'2018-11-18 11:06:54'),(217,'8613300000002','01','123456',0,'2018-11-18 11:07:56'),(218,'8613100000005','01','123456',0,'2018-11-18 11:19:28'),(219,'8613100000006','01','123456',0,'2018-11-18 11:20:14'),(220,'8615220167104','01','123456',0,'2018-11-19 15:41:00'),(221,'8615220166100','01','123456',0,'2018-11-19 15:51:47'),(222,'8615220166101','01','123456',0,'2018-11-19 15:52:48'),(223,'8615220166102','01','123456',0,'2018-11-19 15:53:56'),(224,'8615220166103','01','123456',0,'2018-11-19 15:55:02'),(225,'8615220166104','01','123456',0,'2018-11-19 15:55:48'),(226,'8615220166100','06','123456',0,'2018-11-20 17:47:50'),(227,'8615220166100','03','123456',0,'2018-11-20 16:59:08'),(228,'8613813800001','01','123456',0,'2018-11-21 09:17:53'),(229,'8613813800002','01','123456',0,'2018-11-21 10:38:07'),(230,'8613813800003','01','123456',0,'2018-11-21 11:01:49'),(231,'8613813800004','01','123456',0,'2018-11-21 11:13:05'),(232,'8613813800005','01','123456',0,'2018-11-21 13:22:49');
/*!40000 ALTER TABLE `t_sms_captcha` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_super_merchant_order`
--

DROP TABLE IF EXISTS `t_super_merchant_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_super_merchant_order` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `order_no` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '���������������������������������������������',
  `type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '���������������1���������/���������2���������/������',
  `coin_name` varchar(8) NOT NULL DEFAULT '' COMMENT '���������������������������������������������������������������������',
  `merchant_id` int(11) unsigned NOT NULL COMMENT '���������������',
  `super_merchant_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '������������������������id���0���������������������',
  `amount` decimal(20,8) unsigned NOT NULL COMMENT '������������������������������',
  `settlement_currency` varchar(8) NOT NULL DEFAULT '' COMMENT '���������������������������������CNY���USD',
  `price` decimal(24,8) unsigned NOT NULL DEFAULT '0.00000000' COMMENT '���������������',
  `add_time` int(11) unsigned NOT NULL COMMENT '������������������',
  `accept_time` int(11) unsigned DEFAULT NULL COMMENT '������������',
  `proof_time` int(11) unsigned DEFAULT NULL COMMENT '������������������',
  `finish_time` int(11) unsigned DEFAULT NULL COMMENT '���������������������������������������������������',
  `fee` decimal(20,8) unsigned NOT NULL DEFAULT '0.00000000' COMMENT '���������������������������������������������������������������amount-fee,������������������������������������������������amount-fee������������������������������������������������',
  `status` int(3) unsigned NOT NULL COMMENT '������������1���������;2���������,3���������,4���������,5,���������100���������',
  `credential_urls` varchar(560) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '���������������������������',
  `credential_comment` varchar(400) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '���������������������������',
  `bank_type` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '���������������0������������������1���������������2���������������',
  `bank_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '������������������������',
  `bank_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '������������������',
  `bank_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '���������������������',
  `cancel_type` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '���������������������������������������������������������',
  `cancel_comment` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '������������������������������������',
  `remake` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '���������������������',
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_no_UNIQUE` (`order_no`),
  KEY `fk_order_merchant_id_idx` (`merchant_id`),
  KEY `fk_order_super_merchant_id_idx` (`super_merchant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='���������������������������������������������������������������������������������';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_super_merchant_order`
--

LOCK TABLES `t_super_merchant_order` WRITE;
/*!40000 ALTER TABLE `t_super_merchant_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_super_merchant_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_task_server`
--

DROP TABLE IF EXISTS `t_task_server`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_task_server` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `task_name` varchar(45) NOT NULL DEFAULT '' COMMENT '������������������',
  `server_address` varchar(45) NOT NULL COMMENT '���������������������������������',
  `last_update_time` int(11) unsigned NOT NULL COMMENT '���������������������������',
  PRIMARY KEY (`id`),
  UNIQUE KEY `task_name_UNIQUE` (`task_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='���������������������������������������������������������������������������������������������������������������������������������������������1���������������������3������������������������������������������������������������������������';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_task_server`
--

LOCK TABLES `t_task_server` WRITE;
/*!40000 ALTER TABLE `t_task_server` DISABLE KEYS */;
INSERT INTO `t_task_server` VALUES (1,'TradeBgMatchTask','172.31.30.125',1543980418),(2,'ShareOutBonusTask','172.31.30.125',1543980417);
/*!40000 ALTER TABLE `t_task_server` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_time_monitoring`
--

DROP TABLE IF EXISTS `t_time_monitoring`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_time_monitoring` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '������',
  `monitoring_type` varchar(50) NOT NULL COMMENT '���������������',
  `monitoring_name` varchar(50) NOT NULL COMMENT '���������������:������������������������������������������������',
  `num_minutes` int(11) NOT NULL DEFAULT '5' COMMENT '���������������������������������������',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_time_monitoring`
--

LOCK TABLES `t_time_monitoring` WRITE;
/*!40000 ALTER TABLE `t_time_monitoring` DISABLE KEYS */;
INSERT INTO `t_time_monitoring` VALUES (1,'TIME_MONITORING_TRADE','������������',1),(2,'TIME_MONITORING_H_F_TRADE','������������������',1),(3,'TIME_MONITORING_COIN','������������',1),(5,'TIME_MONITORING_SYSTEM','������������������',5);
/*!40000 ALTER TABLE `t_time_monitoring` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_time_monitoring_config`
--

DROP TABLE IF EXISTS `t_time_monitoring_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_time_monitoring_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '������',
  `monitoring_type` varchar(30) NOT NULL COMMENT '������������',
  `coin_name` varchar(50) NOT NULL COMMENT '���������������������������������������������',
  `num_minutes` int(10) NOT NULL DEFAULT '5' COMMENT '���������������������������������������������',
  `settlement_currency` varchar(50) NOT NULL COMMENT '������������������������������������������������������',
  `buy_number` int(10) DEFAULT NULL COMMENT '������������',
  `sell_number` int(10) DEFAULT NULL COMMENT '������������',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_time_monitoring_config`
--

LOCK TABLES `t_time_monitoring_config` WRITE;
/*!40000 ALTER TABLE `t_time_monitoring_config` DISABLE KEYS */;
INSERT INTO `t_time_monitoring_config` VALUES (6,'TIME_MONITORING_H_F_TRADE','YT',1,'DG',1,2);
/*!40000 ALTER TABLE `t_time_monitoring_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_trade`
--

DROP TABLE IF EXISTS `t_trade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_trade` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) unsigned NOT NULL,
  `coin_name` varchar(8) NOT NULL DEFAULT '' COMMENT '���������������������������������������������',
  `settlement_currency` varchar(8) NOT NULL COMMENT '������������������������������������������������������',
  `price` decimal(20,8) unsigned NOT NULL COMMENT '������������������������������������������������������1���coin_name���������������������������������',
  `amount` decimal(20,8) unsigned NOT NULL COMMENT '���������������������/������������������',
  `total_currency` decimal(24,8) unsigned NOT NULL COMMENT '������������������������������������������������������������������������������������������������price*amount,������������������',
  `type` tinyint(1) unsigned NOT NULL COMMENT '���������������������1������������2���������',
  `price_type` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '���������������0���������������1������������',
  `deal_coin` decimal(20,8) unsigned NOT NULL DEFAULT '0.00000000' COMMENT '������������������(������)������',
  `deal_currency` decimal(24,8) unsigned NOT NULL DEFAULT '0.00000000' COMMENT '������������������������������������������������������������������������������������������������������������������������������������������������)',
  `fee_coin` decimal(20,8) unsigned NOT NULL DEFAULT '0.00000000' COMMENT '���������������������������(������)���������������������������������������������������������������������������������������������������',
  `fee_currency` decimal(24,8) unsigned NOT NULL DEFAULT '0.00000000' COMMENT '������������������������������������������������������������������������������������������������������������������������������������������������',
  `add_time` int(11) unsigned NOT NULL COMMENT '���������������������',
  `last_trade_time` int(11) unsigned DEFAULT NULL COMMENT '���������������������������������������������������������������������������������',
  `status` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '���������0���������������������1������������������2���������������������',
  PRIMARY KEY (`id`),
  KEY `fk_trade_user_id_idx` (`user_id`),
  KEY `index_getMatchSellTrade` (`status`,`type`,`price`) COMMENT '���������������������������'
) ENGINE=InnoDB AUTO_INCREMENT=101943 DEFAULT CHARSET=utf8mb4 COMMENT='������������������';
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `t_trade_bonus_log`
--

DROP TABLE IF EXISTS `t_trade_bonus_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_trade_bonus_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) unsigned NOT NULL DEFAULT '0',
  `coin_name` varchar(12) NOT NULL DEFAULT '' COMMENT '������������',
  `settlement_currency` varchar(12) NOT NULL DEFAULT '' COMMENT '������������',
  `trade_log_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '���������id',
  `type` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '���������1���������2������',
  `trade_fee` decimal(24,8) unsigned NOT NULL DEFAULT '0.00000000' COMMENT '������������������������������������������������������������������������������������������������������������',
  `recommend_user_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '������������id',
  `trade_bonus` decimal(24,8) unsigned NOT NULL DEFAULT '0.00000000' COMMENT '������������������������������',
  `recommend_bonus` decimal(24,8) unsigned DEFAULT NULL COMMENT '������������������������',
  `create_time` datetime DEFAULT NULL COMMENT '������/������������',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `recommend_user_id` (`recommend_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='������������������';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_trade_bonus_log`
--

LOCK TABLES `t_trade_bonus_log` WRITE;
/*!40000 ALTER TABLE `t_trade_bonus_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_trade_bonus_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_trade_fee_return_detail`
--

DROP TABLE IF EXISTS `t_trade_fee_return_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_trade_fee_return_detail` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `trade_log_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '���������id���t_trade_log���������id',
  `ret_time` datetime DEFAULT NULL COMMENT '���������������������',
  `rec_user_rate` decimal(6,4) unsigned NOT NULL DEFAULT '0.0000' COMMENT '���������������������������������������',
  `rec2_user_rate` decimal(6,4) unsigned NOT NULL DEFAULT '0.0000' COMMENT '���������������������������������������������',
  `buy_ret_coin` varchar(8) NOT NULL DEFAULT '' COMMENT '���������������������������������������������',
  `buy_rec_user_id` int(11) unsigned DEFAULT NULL COMMENT '������������������ID',
  `buy_rec_ret` decimal(24,8) unsigned DEFAULT NULL COMMENT '���������������������������������',
  `buy_rec2_user_id` int(11) unsigned DEFAULT NULL COMMENT '������������������������ID',
  `buy_rec2_ret` decimal(24,8) unsigned DEFAULT NULL COMMENT '���������������������������������������',
  `sell_ret_coin` varchar(8) NOT NULL DEFAULT '' COMMENT '���������������������������������������������',
  `sell_rec_user_id` int(11) unsigned DEFAULT NULL COMMENT '������������������ID',
  `sell_rec_ret` decimal(24,8) unsigned DEFAULT NULL COMMENT '���������������������������������',
  `sell_rec2_user_id` int(11) unsigned DEFAULT NULL COMMENT '������������������������ID',
  `sell_rec2_ret` decimal(24,8) unsigned DEFAULT NULL COMMENT '���������������������������������������',
  PRIMARY KEY (`id`),
  KEY `trade_log_id` (`trade_log_id`),
  CONSTRAINT `t_trade_fee_return_detail_ibfk_1` FOREIGN KEY (`trade_log_id`) REFERENCES `t_trade_log` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=120 DEFAULT CHARSET=utf8 COMMENT='������������������������������������������������������';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_trade_fee_return_detail`
--

LOCK TABLES `t_trade_fee_return_detail` WRITE;
/*!40000 ALTER TABLE `t_trade_fee_return_detail` DISABLE KEYS */;
INSERT INTO `t_trade_fee_return_detail` VALUES (1,409078,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',3,0.00300000,NULL,NULL,'USDT',17,0.00180000,3,0.00120000),(2,409102,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',3,0.14700000,NULL,NULL,'USDT',17,0.08820000,3,0.05880000),(3,409103,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',3,0.00300000,NULL,NULL,'USDT',NULL,NULL,NULL,NULL),(4,409135,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',17,0.03000000,3,0.02000000,'USDT',3,0.30000000,NULL,NULL),(5,409142,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',3,0.15000000,NULL,NULL,'USDT',3,0.15000000,NULL,NULL),(6,409143,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',3,0.15000000,NULL,NULL,'USDT',3,0.15000000,NULL,NULL),(7,409144,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',3,0.15000000,NULL,NULL,'USDT',3,0.15000000,NULL,NULL),(8,409145,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',3,0.15000000,NULL,NULL,'USDT',3,0.15000000,NULL,NULL),(9,432730,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',NULL,NULL,NULL,NULL,'USDT',3,0.15000000,NULL,NULL),(10,743249,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',181,0.06000000,180,0.04000000,'USDT',181,391.74000000,180,261.16000000),(11,743618,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',189,0.06000000,188,0.04000000,'USDT',189,390.84000000,188,260.56000000),(12,744171,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',192,0.09000000,NULL,NULL,'USDT',192,585.63000000,NULL,NULL),(13,746025,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',197,0.00461100,196,0.00307400,'USDT',197,29.99916600,196,19.99944400),(14,746224,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',193,0.04607400,192,0.03071600,'USDT',193,299.98781400,192,199.99187600),(15,763847,'2018-11-15 16:49:09',0.3000,0.2000,'DG',200,0.02400000,169,0.01600000,'YT',200,0.06120000,169,0.04080000),(16,763904,'2018-11-15 16:49:09',0.3000,0.2000,'DG',169,0.01200000,NULL,NULL,'YT',200,0.03240000,169,0.02160000),(17,778781,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',202,0.00180000,1,0.00120000,'USDT',NULL,NULL,NULL,NULL),(18,778787,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',NULL,NULL,NULL,NULL,'USDT',202,36.01200000,1,24.00800000),(19,778789,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',NULL,NULL,NULL,NULL,'USDT',202,36.00600000,1,24.00400000),(20,778797,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',NULL,NULL,NULL,NULL,'USDT',202,36.00000000,1,24.00000000),(21,778798,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',1,0.00000300,NULL,NULL,'USDT',202,0.03600600,1,0.02400400),(22,778799,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',202,0.00600000,1,0.00400000,'USDT',202,0.60000000,1,0.40000000),(23,778801,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',202,0.00600000,1,0.00400000,'USDT',202,36.00000000,1,24.00000000),(24,778809,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',202,0.00599400,1,0.00399600,'USDT',202,35.96999400,1,23.97999600),(25,778810,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',202,0.00600000,1,0.00400000,'USDT',202,36.01200000,1,24.00800000),(26,778811,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',1,0.00000300,NULL,NULL,'USDT',NULL,NULL,NULL,NULL),(27,778812,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',202,0.00000600,1,0.00000400,'USDT',202,0.03600600,1,0.02400400),(28,778813,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',202,0.00599400,1,0.00399600,'USDT',202,35.96999400,1,23.97999600),(29,778814,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',1,0.00000300,NULL,NULL,'USDT',NULL,NULL,NULL,NULL),(30,778815,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',202,0.00000600,1,0.00000400,'USDT',202,0.03600600,1,0.02400400),(31,778816,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',1,0.00000300,NULL,NULL,'USDT',202,0.03600600,1,0.02400400),(32,778817,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',1,0.00000300,NULL,NULL,'USDT',202,0.03600600,1,0.02400400),(33,778818,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',1,0.00000300,NULL,NULL,'USDT',202,0.03600600,1,0.02400400),(34,778819,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',1,0.00000300,NULL,NULL,'USDT',202,0.03600600,1,0.02400400),(35,778820,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',1,0.00000300,NULL,NULL,'USDT',202,0.03600600,1,0.02400400),(36,778821,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',1,0.00000300,NULL,NULL,'USDT',202,0.03600600,1,0.02400400),(37,778822,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',1,0.00000300,NULL,NULL,'USDT',202,0.03600600,1,0.02400400),(38,778823,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',1,0.00000300,NULL,NULL,'USDT',202,0.03600600,1,0.02400400),(39,778824,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',1,0.00000300,NULL,NULL,'USDT',202,0.03600600,1,0.02400400),(40,778825,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',1,0.00000300,NULL,NULL,'USDT',202,0.03600600,1,0.02400400),(41,778826,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',1,0.00000300,NULL,NULL,'USDT',202,0.03600600,1,0.02400400),(42,778827,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',1,0.00000300,NULL,NULL,'USDT',202,0.03600600,1,0.02400400),(43,778828,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',1,0.00000300,NULL,NULL,'USDT',202,0.03600600,1,0.02400400),(44,778829,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',1,0.00000300,NULL,NULL,'USDT',202,0.03600600,1,0.02400400),(45,778830,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',1,0.00000300,NULL,NULL,'USDT',202,0.03600600,1,0.02400400),(46,778831,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',1,0.00000300,NULL,NULL,'USDT',202,0.03600600,1,0.02400400),(47,778840,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',1,0.00000300,NULL,NULL,'USDT',202,0.03600600,1,0.02400400),(48,778841,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',1,0.00000300,NULL,NULL,'USDT',202,0.03600600,1,0.02400400),(49,778842,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',1,0.00000300,NULL,NULL,'USDT',202,0.03600600,1,0.02400400),(50,778843,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',1,0.00000300,NULL,NULL,'USDT',202,0.03600600,1,0.02400400),(51,778844,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',202,0.00587400,1,0.00391600,'USDT',202,35.24987400,1,23.49991600),(52,778845,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',1,0.00000300,NULL,NULL,'USDT',202,0.03601200,1,0.02400800),(53,778846,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',1,0.00000300,NULL,NULL,'USDT',202,0.03601200,1,0.02400800),(54,778847,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',1,0.00000300,NULL,NULL,'USDT',202,0.03601200,1,0.02400800),(55,778848,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',1,0.00000300,NULL,NULL,'USDT',202,0.03601200,1,0.02400800),(56,778849,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',1,0.00000300,NULL,NULL,'USDT',202,0.03601200,1,0.02400800),(57,778850,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',1,0.00000300,NULL,NULL,'USDT',202,0.03601200,1,0.02400800),(58,778851,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',1,0.00000300,NULL,NULL,'USDT',202,0.03601200,1,0.02400800),(59,778852,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',1,0.00000300,NULL,NULL,'USDT',202,0.03601200,1,0.02400800),(60,778853,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',202,0.00595200,1,0.00396800,'USDT',202,35.72390400,1,23.81593600),(61,778854,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',1,0.00300000,NULL,NULL,'USDT',202,36.01800000,1,24.01200000),(62,778855,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',1,0.00300000,NULL,NULL,'USDT',202,36.02400000,1,24.01600000),(63,778856,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',1,0.03000000,NULL,NULL,'USDT',202,360.30000000,1,240.20000000),(64,778857,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',1,0.00299400,NULL,NULL,'USDT',NULL,NULL,NULL,NULL),(65,778858,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',1,0.00300000,NULL,NULL,'USDT',NULL,NULL,NULL,NULL),(66,778867,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',202,0.00012600,1,0.00008400,'USDT',1,0.37844100,NULL,NULL),(67,778868,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',202,0.00600000,1,0.00400000,'USDT',1,18.01800000,NULL,NULL),(68,778869,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',202,0.00600000,1,0.00400000,'USDT',1,18.01500000,NULL,NULL),(69,778870,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',202,0.00600000,1,0.00400000,'USDT',1,18.01200000,NULL,NULL),(70,778871,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',202,0.00600000,1,0.00400000,'USDT',1,18.00900000,NULL,NULL),(71,778872,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',202,0.00004800,1,0.00003200,'USDT',1,0.14404800,NULL,NULL),(72,778873,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',202,0.00012600,1,0.00008400,'USDT',1,0.37806300,NULL,NULL),(73,778874,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',202,0.00600000,1,0.00400000,'USDT',1,18.00000000,NULL,NULL),(74,778875,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',202,0.00600000,1,0.00400000,'USDT',1,17.99700000,NULL,NULL),(75,778876,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',202,0.00600000,1,0.00400000,'USDT',1,0.30000000,NULL,NULL),(76,789987,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',NULL,NULL,NULL,NULL,'USDT',202,0.75612600,1,0.50408400),(77,789988,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',NULL,NULL,NULL,NULL,'USDT',202,0.75625200,1,0.50416800),(78,789989,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',NULL,NULL,NULL,NULL,'USDT',202,0.75637800,1,0.50425200),(79,789990,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',NULL,NULL,NULL,NULL,'USDT',202,0.75650400,1,0.50433600),(80,789991,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',NULL,NULL,NULL,NULL,'USDT',202,0.75663000,1,0.50442000),(81,789992,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',NULL,NULL,NULL,NULL,'USDT',202,0.75675600,1,0.50450400),(82,789993,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',NULL,NULL,NULL,NULL,'USDT',202,0.75688200,1,0.50458800),(83,789994,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',NULL,NULL,NULL,NULL,'USDT',202,0.75700800,1,0.50467200),(84,789997,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',202,0.00012600,1,0.00008400,'USDT',NULL,NULL,NULL,NULL),(85,789998,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',202,0.00012600,1,0.00008400,'USDT',NULL,NULL,NULL,NULL),(86,789999,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',202,0.00012600,1,0.00008400,'USDT',NULL,NULL,NULL,NULL),(87,790000,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',202,0.00012600,1,0.00008400,'USDT',NULL,NULL,NULL,NULL),(88,790001,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',202,0.00012600,1,0.00008400,'USDT',NULL,NULL,NULL,NULL),(89,790002,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',202,0.00012600,1,0.00008400,'USDT',NULL,NULL,NULL,NULL),(90,790003,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',202,0.00012600,1,0.00008400,'USDT',NULL,NULL,NULL,NULL),(91,790004,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',202,0.00012600,1,0.00008400,'USDT',NULL,NULL,NULL,NULL),(92,790029,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',204,0.03000000,NULL,NULL,'USDT',206,180.00000000,204,120.00000000),(93,840734,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',204,0.03000000,NULL,NULL,'USDT',NULL,NULL,NULL,NULL),(94,840749,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',204,0.03000000,NULL,NULL,'USDT',204,179.97000000,NULL,NULL),(95,856973,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',NULL,NULL,NULL,NULL,'USDT',204,360.00000000,NULL,NULL),(96,856981,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',204,0.06000000,NULL,NULL,'USDT',NULL,NULL,NULL,NULL),(97,857018,'2018-11-15 16:49:09',0.3000,0.2000,'BTC',211,0.06000000,210,0.04000000,'USDT',211,600.00000000,210,400.00000000),(98,867630,'2018-11-16 01:30:03',0.3000,0.2000,'BTC',211,0.00000600,210,0.00000400,'USDT',NULL,NULL,NULL,NULL),(99,867639,'2018-11-16 01:30:03',0.3000,0.2000,'BTC',211,0.00010200,210,0.00006800,'USDT',NULL,NULL,NULL,NULL),(100,867643,'2018-11-16 01:30:03',0.3000,0.2000,'BTC',211,0.04815600,210,0.03210400,'USDT',NULL,NULL,NULL,NULL),(101,867667,'2018-11-16 01:30:03',0.3000,0.2000,'BTC',NULL,NULL,NULL,NULL,'USDT',211,272.75788800,210,181.83859200),(102,882916,'2018-11-17 01:30:00',0.3000,0.2000,'BTC',211,0.00000600,210,0.00000400,'USDT',NULL,NULL,NULL,NULL),(103,882920,'2018-11-17 01:30:00',0.3000,0.2000,'BTC',NULL,NULL,NULL,NULL,'USDT',211,332.99040000,210,221.99360000),(104,882929,'2018-11-17 01:30:00',0.3000,0.2000,'BTC',210,0.00000600,NULL,NULL,'USDT',NULL,NULL,NULL,NULL),(105,882938,'2018-11-17 01:30:00',0.3000,0.2000,'BTC',NULL,NULL,NULL,NULL,'USDT',210,332.99040000,NULL,NULL),(106,904182,'2018-11-18 01:30:05',0.2000,0.3000,'YT',NULL,NULL,NULL,NULL,'PKT',214,400.00000000,213,600.00000000),(107,904213,'2018-11-18 01:30:05',0.2000,0.3000,'YT',NULL,NULL,NULL,NULL,'PKT',213,100.00000000,NULL,NULL),(108,915572,'2018-11-19 01:30:13',0.2000,0.3000,'YT',220,0.04000000,218,0.06000000,'PKT',220,4.00000000,218,6.00000000),(109,915677,'2018-11-19 01:30:13',0.2000,0.3000,'YT',216,0.04000000,NULL,NULL,'PKT',216,4.00000000,NULL,NULL),(110,916222,'2018-11-19 01:30:13',0.2000,0.3000,'YT',223,0.04000000,222,0.06000000,'PKT',NULL,NULL,NULL,NULL),(111,916333,'2018-11-19 01:30:13',0.2000,0.3000,'YT',NULL,NULL,NULL,NULL,'PKT',226,4.00000000,225,6.00000000),(112,932747,'2018-11-20 01:30:13',0.2000,0.3000,'YT',226,0.04000000,225,0.06000000,'PKT',223,4.00000000,222,6.00000000),(113,934169,'2018-11-20 01:30:13',0.2000,0.3000,'DG',234,0.01600000,232,0.02400000,'YT',NULL,NULL,NULL,NULL),(114,934243,'2018-11-20 01:30:13',0.2000,0.3000,'DG',232,0.04000000,231,0.06000000,'YT',NULL,NULL,NULL,NULL),(115,934307,'2018-11-20 01:30:13',0.2000,0.3000,'DG',231,0.04000000,NULL,NULL,'YT',231,0.12600000,NULL,NULL),(116,934580,'2018-11-20 01:30:13',0.2000,0.3000,'DG',231,0.04000000,NULL,NULL,'YT',232,0.12600000,231,0.18900000),(117,934592,'2018-11-20 01:30:13',0.2000,0.3000,'DG',231,0.04000000,NULL,NULL,'YT',234,0.02520000,232,0.03780000),(118,935568,'2018-11-20 01:30:13',0.2000,0.3000,'BON',231,0.04000000,NULL,NULL,'ETH',232,80.00000000,231,120.00000000),(119,935633,'2018-11-20 01:30:13',0.2000,0.3000,'DG',231,0.04000000,NULL,NULL,'YT',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `t_trade_fee_return_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_trade_log`
--

DROP TABLE IF EXISTS `t_trade_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_trade_log` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `coin_name` varchar(8) NOT NULL DEFAULT '' COMMENT '���������������������������������������������',
  `settlement_currency` varchar(8) NOT NULL COMMENT '������������������������������������������������������',
  `price` decimal(20,8) unsigned NOT NULL COMMENT '������������������������������������������������������1���coin_name���������������������������������',
  `amount` decimal(20,8) unsigned NOT NULL COMMENT '���������������������������������/���������������',
  `buy_user_id` int(11) unsigned NOT NULL COMMENT '������������id',
  `sell_user_id` int(11) unsigned NOT NULL COMMENT '������������id',
  `add_time` int(11) unsigned NOT NULL COMMENT '���������������������',
  `buy_fee_coin` decimal(24,8) unsigned NOT NULL DEFAULT '0.00000000' COMMENT '���������������������������������������������������������������������������������������������������������������������������������amount-fee_buy',
  `sell_fee_currency` decimal(24,8) unsigned NOT NULL DEFAULT '0.00000000' COMMENT '���������������������������������������������������������������������������������������������������������������������������������������price*amount-fee_sell',
  `buy_trade_id` int(11) unsigned NOT NULL COMMENT '���������t_trade���������������������id',
  `sell_trade_id` int(11) unsigned NOT NULL COMMENT '���������t_trade���������������������id',
  PRIMARY KEY (`id`),
  KEY `fk_buy_trade_id_idx` (`buy_trade_id`),
  KEY `fk_sell_trade_id_idx` (`sell_trade_id`),
  KEY `index_buy_user_id` (`buy_user_id`),
  KEY `index_sell_user_id` (`sell_user_id`),
  KEY `add_time` (`add_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1176843 DEFAULT CHARSET=utf8mb4 COMMENT='���������������������������������������������������������������������������������������������t_trade������������';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_trade_no_warning_user`
--

DROP TABLE IF EXISTS `t_trade_no_warning_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_trade_no_warning_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '������',
  `user_name` varchar(20) DEFAULT NULL COMMENT '���������',
  `user_id` int(11) DEFAULT NULL COMMENT '������id',
  `create_time` datetime NOT NULL,
  `no_warning_type` varchar(50) NOT NULL DEFAULT '1' COMMENT '���������������������',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_trade_no_warning_user`
--

LOCK TABLES `t_trade_no_warning_user` WRITE;
/*!40000 ALTER TABLE `t_trade_no_warning_user` DISABLE KEYS */;
INSERT INTO `t_trade_no_warning_user` VALUES (6,'15220167142',157,'2018-09-28 16:20:18','11');
/*!40000 ALTER TABLE `t_trade_no_warning_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_trade_stat`
--

DROP TABLE IF EXISTS `t_trade_stat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_trade_stat` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `stat_time` int(11) unsigned NOT NULL COMMENT '������������',
  `coin_name` varchar(8) NOT NULL DEFAULT '' COMMENT '���������������������������������������������',
  `settlement_currency` varchar(8) NOT NULL COMMENT '������������������������������������������������������',
  `stat_cycle` smallint(5) unsigned NOT NULL COMMENT '������������������������������������1,5,15,30,60,1440',
  `order_times` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '������������',
  `order_amount` decimal(32,8) unsigned NOT NULL DEFAULT '0.00000000' COMMENT '������������',
  `order_total_currency` decimal(24,8) unsigned NOT NULL DEFAULT '0.00000000' COMMENT '������������������������',
  `order_user_num` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '������������������',
  `deal_times` int(11) unsigned NOT NULL COMMENT '���������������',
  `deal_amount` decimal(32,8) unsigned NOT NULL DEFAULT '0.00000000' COMMENT '������������',
  `deal_total_currency` decimal(32,8) unsigned NOT NULL DEFAULT '0.00000000' COMMENT '���������������������������',
  `fee_amount` decimal(32,8) unsigned NOT NULL DEFAULT '0.00000000' COMMENT '���������������������������������',
  `fee_total_currency` decimal(32,8) unsigned NOT NULL DEFAULT '0.00000000' COMMENT '���������������������������������',
  `deal_min_price` decimal(24,8) unsigned NOT NULL DEFAULT '0.00000000' COMMENT '���������������������',
  `deal_max_price` decimal(24,8) unsigned NOT NULL DEFAULT '0.00000000' COMMENT '���������������������',
  `deal_first_price` decimal(24,8) unsigned NOT NULL DEFAULT '0.00000000' COMMENT '������������������������������������',
  `deal_last_price` decimal(24,8) unsigned NOT NULL DEFAULT '0.00000000' COMMENT '���������������������������������������',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_stat_time_cycle` (`stat_time`,`stat_cycle`,`coin_name`,`settlement_currency`)
) ENGINE=InnoDB AUTO_INCREMENT=380184 DEFAULT CHARSET=utf8mb4 COMMENT='������������������������';
/*!40101 SET character_set_client = @saved_cs_client */;
