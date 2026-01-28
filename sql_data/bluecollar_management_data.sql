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
-- Dumping data for table `attendance`
--

LOCK TABLES `attendance` WRITE;
/*!40000 ALTER TABLE `attendance` DISABLE KEYS */;
/*!40000 ALTER TABLE `attendance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `contract`
--

LOCK TABLES `contract` WRITE;
/*!40000 ALTER TABLE `contract` DISABLE KEYS */;
/*!40000 ALTER TABLE `contract` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `feedback`
--

LOCK TABLES `feedback` WRITE;
/*!40000 ALTER TABLE `feedback` DISABLE KEYS */;
INSERT INTO `feedback` VALUES (1,4,5,'Great service','2026-01-28 01:01:01',1),(2,4,5,'Excellent Service',NULL,1),(3,4,5,'Excellent Service','2026-01-28 02:16:49',1),(4,4,5,'Excellent Service','2026-01-28 21:47:58',1);
/*!40000 ALTER TABLE `feedback` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `hourly_work_log`
--

LOCK TABLES `hourly_work_log` WRITE;
/*!40000 ALTER TABLE `hourly_work_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `hourly_work_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `job`
--

LOCK TABLES `job` WRITE;
/*!40000 ALTER TABLE `job` DISABLE KEYS */;
/*!40000 ALTER TABLE `job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
INSERT INTO `payment` VALUES (1,4,600,'FIXED','PENDING',NULL,'2026-01-28 00:53:44.809497','HOURLY',1),(2,5,600,'FIXED','PENDING',NULL,'2026-01-28 01:45:45.058770','HOURLY',1);
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `schedule`
--

LOCK TABLES `schedule` WRITE;
/*!40000 ALTER TABLE `schedule` DISABLE KEYS */;
/*!40000 ALTER TABLE `schedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `service`
--

LOCK TABLES `service` WRITE;
/*!40000 ALTER TABLE `service` DISABLE KEYS */;
/*!40000 ALTER TABLE `service` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `service_category`
--

LOCK TABLES `service_category` WRITE;
/*!40000 ALTER TABLE `service_category` DISABLE KEYS */;
INSERT INTO `service_category` VALUES (1,'Electrical repair services','ELECTRICIAN'),(2,'Plumbing services','PLUMBER');
/*!40000 ALTER TABLE `service_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Ramesh','ramesh@gmail.com','$2a$10$AonZ6Kslv6otuKj.oaWwCe4uJ3NkLDLmkITVrgX7u3C0ugAjuWeoq','CUSTOMER','ACTIVE','2026-01-27 21:43:23'),(2,'Suresh','suresh@gmail.com','$2a$10$eSjo8Qdl9zdwI3uDD2kEGekSp9Z05w6gO3h1t1DmnzjAWx6nqE1u2','WORKER','ACTIVE','2026-01-27 22:09:12');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `work_request`
--

LOCK TABLES `work_request` WRITE;
/*!40000 ALTER TABLE `work_request` DISABLE KEYS */;
INSERT INTO `work_request` VALUES (4,1,1,1,'ON_DEMAND','COMPLETED',NULL,'2026-01-28 00:31:35','2026-01-28 00:31:34.969495'),(5,1,1,1,'ON_DEMAND','COMPLETED',NULL,'2026-01-28 01:43:25','2026-01-28 01:43:25.818746'),(6,1,1,1,'ON_DEMAND','PENDING',NULL,'2026-01-28 22:11:13','2026-01-28 22:11:13.561757'),(7,1,1,1,'ON_DEMAND','PENDING',NULL,'2026-01-28 22:33:14','2026-01-28 22:33:13.965101');
/*!40000 ALTER TABLE `work_request` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `worker`
--

LOCK TABLES `worker` WRITE;
/*!40000 ALTER TABLE `worker` DISABLE KEYS */;
INSERT INTO `worker` VALUES (1,1,'ON_DEMAND',5,1,0,5,1),(2,1,'ON_DEMAND',0,1,0,5,1);
/*!40000 ALTER TABLE `worker` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `worker_pricing`
--

LOCK TABLES `worker_pricing` WRITE;
/*!40000 ALTER TABLE `worker_pricing` DISABLE KEYS */;
INSERT INTO `worker_pricing` VALUES (4,300,'HOURLY',1),(5,800,'PER_JOB',2);
/*!40000 ALTER TABLE `worker_pricing` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `worker_service`
--

LOCK TABLES `worker_service` WRITE;
/*!40000 ALTER TABLE `worker_service` DISABLE KEYS */;
/*!40000 ALTER TABLE `worker_service` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-01-28 22:58:45
