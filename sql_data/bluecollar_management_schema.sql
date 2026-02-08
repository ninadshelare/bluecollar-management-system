-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
--
-- Host: localhost    Database: bluecollar_management
-- ------------------------------------------------------
-- Server version	8.0.43

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `attendance`
--

DROP TABLE IF EXISTS `attendance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attendance` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `contract_id` bigint NOT NULL,
  `work_date` date NOT NULL,
  `check_in` datetime(6) DEFAULT NULL,
  `check_out` datetime(6) DEFAULT NULL,
  `status` enum('PRESENT','ABSENT','HALF_DAY') DEFAULT NULL,
  `total_hours` double DEFAULT NULL,
  `work_request_id` bigint DEFAULT NULL,
  `worker_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `contract_id` (`contract_id`,`work_date`),
  UNIQUE KEY `UKgoy6j7m258a0hlxh4p9wxr1ed` (`worker_id`,`work_date`),
  KEY `FKhs50lyj5fpjiq89rxk69vaus8` (`work_request_id`),
  CONSTRAINT `attendance_ibfk_1` FOREIGN KEY (`contract_id`) REFERENCES `contract` (`id`),
  CONSTRAINT `FKhs50lyj5fpjiq89rxk69vaus8` FOREIGN KEY (`work_request_id`) REFERENCES `work_request` (`id`),
  CONSTRAINT `FKqs83odp2f8mwlywc2wk2s73dh` FOREIGN KEY (`worker_id`) REFERENCES `worker` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `contract`
--

DROP TABLE IF EXISTS `contract`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contract` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `work_request_id` bigint NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date DEFAULT NULL,
  `monthly_salary` decimal(10,2) NOT NULL,
  `status` enum('ACTIVE','PAUSED','TERMINATED') DEFAULT 'ACTIVE',
  PRIMARY KEY (`id`),
  KEY `work_request_id` (`work_request_id`),
  CONSTRAINT `contract_ibfk_1` FOREIGN KEY (`work_request_id`) REFERENCES `work_request` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `address_line1` varchar(255) DEFAULT NULL,
  `address_line2` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `pincode` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `customer_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `feedback`
--

DROP TABLE IF EXISTS `feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feedback` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `work_request_id` bigint NOT NULL,
  `rating` int DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `worker_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `work_request_id` (`work_request_id`),
  KEY `FKi09akqdb747xga26931nryfcf` (`worker_id`),
  CONSTRAINT `feedback_ibfk_1` FOREIGN KEY (`work_request_id`) REFERENCES `work_request` (`id`),
  CONSTRAINT `FKi09akqdb747xga26931nryfcf` FOREIGN KEY (`worker_id`) REFERENCES `worker` (`id`),
  CONSTRAINT `feedback_chk_1` CHECK ((`rating` between 1 and 5))
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `hourly_work_log`
--

DROP TABLE IF EXISTS `hourly_work_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hourly_work_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `work_request_id` bigint NOT NULL,
  `check_in` datetime DEFAULT NULL,
  `check_out` datetime DEFAULT NULL,
  `total_hours` decimal(5,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `work_request_id` (`work_request_id`),
  CONSTRAINT `hourly_work_log_ibfk_1` FOREIGN KEY (`work_request_id`) REFERENCES `work_request` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `job`
--

DROP TABLE IF EXISTS `job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `job` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `work_request_id` bigint NOT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `status` enum('IN_PROGRESS','COMPLETED') DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `work_request_id` (`work_request_id`),
  CONSTRAINT `job_ibfk_1` FOREIGN KEY (`work_request_id`) REFERENCES `work_request` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `maid_attendance`
--

DROP TABLE IF EXISTS `maid_attendance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `maid_attendance` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `worker_id` bigint NOT NULL,
  `attendance_date` date NOT NULL,
  `status` enum('PRESENT','ABSENT') NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `worker_id` (`worker_id`,`attendance_date`),
  CONSTRAINT `fk_attendance_worker` FOREIGN KEY (`worker_id`) REFERENCES `worker` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `maid_salary`
--

DROP TABLE IF EXISTS `maid_salary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `maid_salary` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `worker_id` bigint NOT NULL,
  `total_days` int NOT NULL,
  `present_days` int NOT NULL,
  `calculated_amount` double DEFAULT NULL,
  `monthly_price` double DEFAULT NULL,
  `paid` bit(1) NOT NULL DEFAULT b'0',
  `salary_month` varbinary(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `worker_id` (`worker_id`),
  UNIQUE KEY `UK90bjuebf5dmhwhv1qcn2fakk0` (`worker_id`,`salary_month`),
  CONSTRAINT `fk_salary_worker` FOREIGN KEY (`worker_id`) REFERENCES `worker` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `work_request_id` bigint NOT NULL,
  `amount` double NOT NULL,
  `payment_type` enum('FIXED','HOURLY','MONTHLY') NOT NULL,
  `status` enum('PENDING','PAID','FAILED') DEFAULT 'PENDING',
  `paid_at` datetime DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `pricing_type` enum('HOURLY','MONTHLY','PER_JOB') DEFAULT NULL,
  `worker_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `work_request_id` (`work_request_id`),
  KEY `FKmbtjhfmxyvqjxkwtrry7xvc1q` (`worker_id`),
  CONSTRAINT `FKmbtjhfmxyvqjxkwtrry7xvc1q` FOREIGN KEY (`worker_id`) REFERENCES `worker` (`id`),
  CONSTRAINT `payment_ibfk_1` FOREIGN KEY (`work_request_id`) REFERENCES `work_request` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `schedule`
--

DROP TABLE IF EXISTS `schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `schedule` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `contract_id` bigint NOT NULL,
  `work_day` enum('MON','TUE','WED','THU','FRI','SAT','SUN') DEFAULT NULL,
  `start_time` time DEFAULT NULL,
  `end_time` time DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `contract_id` (`contract_id`),
  CONSTRAINT `schedule_ibfk_1` FOREIGN KEY (`contract_id`) REFERENCES `contract` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `service`
--

DROP TABLE IF EXISTS `service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `description` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `service_category`
--

DROP TABLE IF EXISTS `service_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service_category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKrq4iui706ylaju1tyhc8j6wo4` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('ADMIN','CUSTOMER','WORKER') NOT NULL,
  `status` enum('ACTIVE','BLOCKED') DEFAULT 'ACTIVE',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `work_request`
--

DROP TABLE IF EXISTS `work_request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `work_request` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `customer_id` bigint NOT NULL,
  `worker_id` bigint NOT NULL,
  `service_id` bigint NOT NULL,
  `employment_model` enum('ON_DEMAND','HOURLY','MONTHLY') NOT NULL,
  `status` varchar(20) DEFAULT NULL,
  `requested_date` date DEFAULT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `requested_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `worker_id` (`worker_id`),
  KEY `fk_work_request_customer` (`customer_id`),
  KEY `fk_work_request_service` (`service_id`),
  CONSTRAINT `FK8yyim6c01sl2er47wrgy8isp` FOREIGN KEY (`service_id`) REFERENCES `service_category` (`id`),
  CONSTRAINT `fk_work_request_customer` FOREIGN KEY (`customer_id`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_work_request_service` FOREIGN KEY (`service_id`) REFERENCES `service_category` (`id`),
  CONSTRAINT `FKkbt2ocxwvw9oasir52mutluc4` FOREIGN KEY (`customer_id`) REFERENCES `user` (`id`),
  CONSTRAINT `work_request_ibfk_2` FOREIGN KEY (`worker_id`) REFERENCES `worker` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `worker`
--

DROP TABLE IF EXISTS `worker`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `worker` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `employment_model` enum('ON_DEMAND','HOURLY','MONTHLY') NOT NULL,
  `rating` double DEFAULT NULL,
  `available` tinyint(1) DEFAULT '1',
  `verified` tinyint(1) DEFAULT '0',
  `experience_years` int DEFAULT NULL,
  `service_id` bigint NOT NULL,
  `worker_type` varchar(50) NOT NULL,
  `active` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `FKcvargffig6o4fvqbflgchyljt` (`service_id`),
  CONSTRAINT `FKcvargffig6o4fvqbflgchyljt` FOREIGN KEY (`service_id`) REFERENCES `service_category` (`id`),
  CONSTRAINT `worker_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `worker_pricing`
--

DROP TABLE IF EXISTS `worker_pricing`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `worker_pricing` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `price` double DEFAULT NULL,
  `pricing_type` enum('HOURLY','MONTHLY','PER_JOB') DEFAULT NULL,
  `worker_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKj8l4fp7sx8d61w523qw3ewpgx` (`worker_id`),
  CONSTRAINT `FKtl53p1ryj01684ekuem7vvw5` FOREIGN KEY (`worker_id`) REFERENCES `worker` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `worker_service`
--

DROP TABLE IF EXISTS `worker_service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `worker_service` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `worker_id` bigint NOT NULL,
  `service_id` bigint NOT NULL,
  `fixed_price` decimal(10,2) DEFAULT NULL,
  `hourly_rate` decimal(10,2) DEFAULT NULL,
  `monthly_salary` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `worker_id` (`worker_id`,`service_id`),
  KEY `service_id` (`service_id`),
  CONSTRAINT `worker_service_ibfk_1` FOREIGN KEY (`worker_id`) REFERENCES `worker` (`id`),
  CONSTRAINT `worker_service_ibfk_2` FOREIGN KEY (`service_id`) REFERENCES `service` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-02-03 14:13:07
