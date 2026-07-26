-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: localhost    Database: crs
-- ------------------------------------------------------
-- Server version	8.0.33

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
-- Database creation
--
CREATE DATABASE IF NOT EXISTS `crs`;
USE `crs`;

--
-- Table structure for table `Users`
--
DROP TABLE IF EXISTS `Users`;
CREATE TABLE `Users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `password` varchar(255) NOT NULL,
  `role` varchar(20) NOT NULL,
  `username` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_r43af9ap4edm43mmtq01oddj6` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `Users`
--
LOCK TABLES `Users` WRITE;
INSERT INTO `Users` VALUES (1,'admin123','ADMIN','admin'),(2,'pass123','STUDENT','student1'),(3,'fac123','FACULTY','faculty1');
UNLOCK TABLES;

--
-- Table structure for table `Students`
--
DROP TABLE IF EXISTS `Students`;
CREATE TABLE `Students` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `contact` varchar(100) DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `name` varchar(100) NOT NULL,
  `program` varchar(100) DEFAULT NULL,
  `year` int DEFAULT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKq0t2j9wdf6dhlj799q332v34h` (`user_id`),
  CONSTRAINT `FKq0t2j9wdf6dhlj799q332v34h` FOREIGN KEY (`user_id`) REFERENCES `Users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `Students`
--
LOCK TABLES `Students` WRITE;
INSERT INTO `Students` VALUES (1,'john@example.com','2000-05-15','John Doe','Computer Science',2,2);
UNLOCK TABLES;

--
-- Table structure for table `Faculty`
--
DROP TABLE IF EXISTS `Faculty`;
CREATE TABLE `Faculty` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `department` varchar(100) DEFAULT NULL,
  `name` varchar(100) NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKq8s9t2wdf6dhlj799q332v34h` (`user_id`),
  CONSTRAINT `FKq8s9t2wdf6dhlj799q332v34h` FOREIGN KEY (`user_id`) REFERENCES `Users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `Faculty`
--
LOCK TABLES `Faculty` WRITE;
INSERT INTO `Faculty` VALUES (1,'Computer Science','Dr. Smith',3);
UNLOCK TABLES;

--
-- Table structure for table `Courses`
--
DROP TABLE IF EXISTS `Courses`;
CREATE TABLE `Courses` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `credits` int NOT NULL,
  `department` varchar(100) DEFAULT NULL,
  `max_capacity` int NOT NULL,
  `title` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `Courses`
--
LOCK TABLES `Courses` WRITE;
INSERT INTO `Courses` VALUES (1,3,'Computer Science',50,'Introduction to Programming'),(2,4,'Computer Science',30,'Data Structures'),(3,4,'Computer Science',20,'Advanced Algorithms');
UNLOCK TABLES;

--
-- Table structure for table `Prerequisites`
--
DROP TABLE IF EXISTS `Prerequisites`;
CREATE TABLE `Prerequisites` (
  `course_id` bigint NOT NULL,
  `prerequisite_course_id` bigint NOT NULL,
  PRIMARY KEY (`course_id`,`prerequisite_course_id`),
  KEY `FKd2p8k9wdf6dhlj799q332v34h` (`prerequisite_course_id`),
  CONSTRAINT `FKd2p8k9wdf6dhlj799q332v34h` FOREIGN KEY (`prerequisite_course_id`) REFERENCES `Courses` (`id`),
  CONSTRAINT `FKg1q5p9wdf6dhlj799q332v34h` FOREIGN KEY (`course_id`) REFERENCES `Courses` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `Prerequisites`
--
LOCK TABLES `Prerequisites` WRITE;
INSERT INTO `Prerequisites` VALUES (2,1),(3,2);
UNLOCK TABLES;

--
-- Table structure for table `Enrollments`
--
DROP TABLE IF EXISTS `Enrollments`;
CREATE TABLE `Enrollments` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `grade` varchar(2) DEFAULT NULL,
  `semester` varchar(20) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  `course_id` bigint NOT NULL,
  `student_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKb8p7m9wdf6dhlj799q332v34h` (`course_id`),
  KEY `FKj9q4n9wdf6dhlj799q332v34h` (`student_id`),
  CONSTRAINT `FKb8p7m9wdf6dhlj799q332v34h` FOREIGN KEY (`course_id`) REFERENCES `Courses` (`id`),
  CONSTRAINT `FKj9q4n9wdf6dhlj799q332v34h` FOREIGN KEY (`student_id`) REFERENCES `Students` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `Enrollments`
--
LOCK TABLES `Enrollments` WRITE;
UNLOCK TABLES;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
-- Dump completed
