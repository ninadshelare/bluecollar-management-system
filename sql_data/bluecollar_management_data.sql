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
INSERT INTO `customer` VALUES (3,6,NULL,'1234567890','bandra','bandra','mumbai','Maharashtra','400050');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `feedback`
--

LOCK TABLES `feedback` WRITE;
/*!40000 ALTER TABLE `feedback` DISABLE KEYS */;
INSERT INTO `feedback` VALUES (1,4,5,'Great service','2026-01-28 01:01:01',1),(2,4,5,'Excellent Service',NULL,1),(3,4,5,'Excellent Service','2026-01-28 02:16:49',1),(4,4,5,'Excellent Service','2026-01-28 21:47:58',1),(5,8,3,'nice','2026-02-01 20:47:56',4),(6,8,4,'mast service','2026-02-01 22:30:05',4),(7,8,4,'','2026-02-01 23:56:06',4),(8,9,2,'','2026-02-01 23:56:12',4),(9,10,1,'','2026-02-01 23:56:20',4),(10,9,2,'','2026-02-01 23:56:26',4),(11,8,4,'fghth','2026-02-03 13:47:52',4),(12,8,2,'fghthhh','2026-02-03 13:47:59',4);
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
-- Dumping data for table `maid_attendance`
--

LOCK TABLES `maid_attendance` WRITE;
/*!40000 ALTER TABLE `maid_attendance` DISABLE KEYS */;
/*!40000 ALTER TABLE `maid_attendance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `maid_salary`
--

LOCK TABLES `maid_salary` WRITE;
/*!40000 ALTER TABLE `maid_salary` DISABLE KEYS */;
/*!40000 ALTER TABLE `maid_salary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
INSERT INTO `payment` VALUES (1,4,600,'FIXED','PENDING',NULL,'2026-01-28 00:53:44.809497','HOURLY',1),(2,5,600,'FIXED','PENDING',NULL,'2026-01-28 01:45:45.058770','HOURLY',1),(3,8,500,'FIXED','PAID',NULL,'2026-02-01 18:51:25.400258','HOURLY',4),(4,9,1500,'FIXED','PAID',NULL,'2026-02-01 20:19:30.571233','HOURLY',4),(5,10,2000,'FIXED','PAID',NULL,'2026-02-01 23:55:34.299260','HOURLY',4),(6,11,1000,'FIXED','PAID',NULL,'2026-02-02 14:17:16.345220','HOURLY',4),(7,12,500,'FIXED','PAID',NULL,'2026-02-02 19:29:47.900891','PER_JOB',5),(8,13,500,'FIXED','PAID',NULL,'2026-02-02 19:37:52.121354','PER_JOB',5),(9,14,1000,'FIXED','PAID',NULL,'2026-02-03 14:07:23.965691','MONTHLY',6);
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
INSERT INTO `service_category` VALUES (1,'Electrical repair services','ELECTRICIAN'),(2,'Plumbing services','PLUMBER'),(3,'Wood and furniture work','CARPENTER'),(4,'Painting services','PAINTER'),(5,'Daily wage labour work','LABOUR'),(6,'Household maid services','MAID');
/*!40000 ALTER TABLE `service_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Ramesh','ramesh@gmail.com','$2a$10$AonZ6Kslv6otuKj.oaWwCe4uJ3NkLDLmkITVrgX7u3C0ugAjuWeoq','CUSTOMER','ACTIVE','2026-01-27 21:43:23'),(2,'Suresh','suresh@gmail.com','$2a$10$eSjo8Qdl9zdwI3uDD2kEGekSp9Z05w6gO3h1t1DmnzjAWx6nqE1u2','WORKER','ACTIVE','2026-01-27 22:09:12'),(3,'example','example@gmail.com','$2a$10$Z4Hm7Qlt.jXAVbYi6ZRHou5dJdHoay2G7oQLVoEfMKBSvY0552K9S','CUSTOMER','ACTIVE','2026-01-29 23:39:07'),(4,'shivam','shivam@gmail.com','$2a$10$w.nCbiMPjEBfKL.E2IwVv.Gn3g8NsGixQC0IvYH1hymF2ng.fyLQK','WORKER','ACTIVE','2026-01-31 03:35:05'),(5,'test','test@gmail.com','$2a$10$9aPRorCqbXPPOgbG4rAym.7onhFom0KILVbilZQ1iWLH6fsjju3PW','WORKER','ACTIVE','2026-01-31 03:35:50'),(6,'God','god@gmail.com','$2a$10$OViY8pwqrOFQSI1.uvQ7c.HXJRLTVJfa7t.Xm2ttdb65Q9AyllH4S','CUSTOMER','ACTIVE','2026-01-31 03:37:28'),(7,'maid','maid@gmail.com','$2a$10$RGA0NtPjturyIWaMJxhWqeJgsl6VduKlPmT.2F/xgf8lVVW7Kr6rG','WORKER','ACTIVE','2026-02-02 21:16:14');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `work_request`
--

LOCK TABLES `work_request` WRITE;
/*!40000 ALTER TABLE `work_request` DISABLE KEYS */;
INSERT INTO `work_request` VALUES (4,1,1,1,'ON_DEMAND','COMPLETED',NULL,'2026-01-28 00:31:35','2026-01-28 00:31:34.969495'),(5,1,1,1,'ON_DEMAND','COMPLETED',NULL,'2026-01-28 01:43:25','2026-01-28 01:43:25.818746'),(6,1,1,1,'ON_DEMAND','PENDING',NULL,'2026-01-28 22:11:13','2026-01-28 22:11:13.561757'),(7,1,1,1,'ON_DEMAND','PENDING',NULL,'2026-01-28 22:33:14','2026-01-28 22:33:13.965101'),(8,6,4,1,'ON_DEMAND','COMPLETED',NULL,'2026-02-01 18:47:13','2026-02-01 18:47:13.336202'),(9,6,4,1,'ON_DEMAND','COMPLETED',NULL,'2026-02-01 20:18:40','2026-02-01 20:18:40.592460'),(10,6,4,1,'ON_DEMAND','COMPLETED',NULL,'2026-02-01 23:55:06','2026-02-01 23:55:06.814487'),(11,6,4,1,'ON_DEMAND','COMPLETED',NULL,'2026-02-02 14:16:04','2026-02-02 14:16:04.239265'),(12,6,5,1,'ON_DEMAND','COMPLETED',NULL,'2026-02-02 19:29:11','2026-02-02 19:29:11.039730'),(13,6,5,1,'ON_DEMAND','COMPLETED',NULL,'2026-02-02 19:37:24','2026-02-02 19:37:24.878532'),(14,6,6,6,'ON_DEMAND','COMPLETED',NULL,'2026-02-03 14:03:57','2026-02-03 14:03:57.065975');
/*!40000 ALTER TABLE `work_request` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `worker`
--

LOCK TABLES `worker` WRITE;
/*!40000 ALTER TABLE `worker` DISABLE KEYS */;
INSERT INTO `worker` VALUES (1,1,'ON_DEMAND',5,1,0,5,1,'SKILLED',_binary '\0'),(2,1,'ON_DEMAND',0,1,0,5,1,'SKILLED',_binary '\0'),(3,4,'ON_DEMAND',0,1,0,2,2,'SKILLED',_binary '\0'),(4,5,'ON_DEMAND',2.8,0,0,4,4,'SKILLED',_binary '\0'),(5,5,'ON_DEMAND',0,1,0,7,1,'SKILLED',_binary ''),(6,7,'ON_DEMAND',0,1,0,2,6,'MAID',_binary '');
/*!40000 ALTER TABLE `worker` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `worker_pricing`
--

LOCK TABLES `worker_pricing` WRITE;
/*!40000 ALTER TABLE `worker_pricing` DISABLE KEYS */;
INSERT INTO `worker_pricing` VALUES (8,500,'PER_JOB',5),(9,1000,'MONTHLY',6);
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

-- Dump completed on 2026-02-03 14:13:32
