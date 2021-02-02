CREATE DATABASE  IF NOT EXISTS `kurs` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `kurs`;
-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: kurs
-- ------------------------------------------------------
-- Server version	8.0.19

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cars`
--

DROP TABLE IF EXISTS `cars`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cars` (
  `modelID` int NOT NULL,
  `carType` int NOT NULL,
  `carColor` varchar(50) NOT NULL,
  `carYear` varchar(4) NOT NULL,
  `carPower` varchar(45) NOT NULL,
  `carPlate` varchar(9) NOT NULL,
  `carVIN` varchar(17) NOT NULL,
  `carInsurance` varchar(10) NOT NULL,
  `carStatus` int NOT NULL,
  `carTransmission` int NOT NULL,
  PRIMARY KEY (`carVIN`),
  KEY `fk_models_idx` (`modelID`),
  KEY `fk_status_idx` (`carStatus`),
  KEY `fk_type_idx` (`carType`),
  KEY `fk_transmission_idx` (`carTransmission`),
  CONSTRAINT `fk_models` FOREIGN KEY (`modelID`) REFERENCES `models` (`modelID`),
  CONSTRAINT `fk_status` FOREIGN KEY (`carStatus`) REFERENCES `statuses` (`statusID`),
  CONSTRAINT `fk_transmission` FOREIGN KEY (`carTransmission`) REFERENCES `transmission` (`transmissionID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cars`
--

LOCK TABLES `cars` WRITE;
/*!40000 ALTER TABLE `cars` DISABLE KEYS */;
INSERT INTO `cars` VALUES (38,1,'Белый','2018','125','В837МЕ197','7WEMOGM1LWH8ODPC5','4BZTFHE2V0',0,2),(36,0,'Чёрный','2019','123','К873КО777','CFMROY1A8VBCF2D2S','HKCOPMC3XD',0,1),(34,0,'Серебряный','2017','82','О732ХТ777','IS5U12PLJ4OTVCYRQ','Q0E0K3XY6P',2,0),(35,0,'Белый','2019','105','Н834ТС777','JWIXF4FCDTDG64VDO','67DV1KCXO7',0,1),(33,0,'Серебристый','2019','100','М006ЕН777','KJU7E3OVJUTJDAQW7','L0XEHB0NXL',0,1),(39,0,'Белый','2019','150','О837ХТ777','RM87DQTEO6A6Q1WY4','88DGTATUMF',1,1),(33,0,'Белый','2019','100','Н043РВ777','S9JF8ERDYOIFNUMQI','78FXKPTDLD',1,1),(41,5,'Серый','2017','156','Е748ТС777','W56DG68J98MEA477M','FP03UOH079',0,3),(34,0,'Синий','2017','82','В197ХУ197','X58GB80KMWL6OR76P','CMMKPY88JJ',0,0);
/*!40000 ALTER TABLE `cars` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clients`
--

DROP TABLE IF EXISTS `clients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clients` (
  `fio` varchar(100) NOT NULL,
  `passport` varchar(10) NOT NULL,
  `address` varchar(100) NOT NULL,
  `driverLicense` varchar(10) NOT NULL,
  `phone` varchar(11) NOT NULL,
  PRIMARY KEY (`passport`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clients`
--

LOCK TABLES `clients` WRITE;
/*!40000 ALTER TABLE `clients` DISABLE KEYS */;
INSERT INTO `clients` VALUES ('Акимов Аким Акимович','6472837463','г. Королёв ул. Комитетская д.19 кв.7','9974831353','89163728493'),('Никифров Маким Вячеич','6473823746','г. Мытищи ул.Юбилейная д.37 кв.9','9906738499','89266015640');
/*!40000 ALTER TABLE `clients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `models`
--

DROP TABLE IF EXISTS `models`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `models` (
  `modelID` int NOT NULL AUTO_INCREMENT,
  `mark` varchar(50) NOT NULL,
  `model` varchar(50) NOT NULL,
  `modelPrice` decimal(10,2) NOT NULL,
  PRIMARY KEY (`modelID`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `models`
--

LOCK TABLES `models` WRITE;
/*!40000 ALTER TABLE `models` DISABLE KEYS */;
INSERT INTO `models` VALUES (33,'Kia','Rio IV',2375.00),(34,'Renault','Logan II',1805.00),(35,'Volkswagen','Polo',2470.00),(36,'Hyundai','Solaris II',2470.00),(37,'Skoda','Rapid',2565.00),(38,'Ford','Focus III',3140.00),(39,'Mazda','6',4693.00),(40,'BMW','320i',5478.00),(41,'Merceses','CLA 200',6000.00);
/*!40000 ALTER TABLE `models` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rents`
--

DROP TABLE IF EXISTS `rents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rents` (
  `rentalContract` varchar(10) NOT NULL,
  `client` varchar(10) NOT NULL,
  `car` varchar(17) NOT NULL,
  `rentalDate` date NOT NULL,
  `receptionDate` date NOT NULL,
  `totalPrice` decimal(15,2) NOT NULL,
  PRIMARY KEY (`rentalContract`),
  KEY `fk_client_idx` (`client`),
  KEY `fk_car_idx` (`car`),
  CONSTRAINT `fk_car` FOREIGN KEY (`car`) REFERENCES `cars` (`carVIN`),
  CONSTRAINT `fk_client` FOREIGN KEY (`client`) REFERENCES `clients` (`passport`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rents`
--

LOCK TABLES `rents` WRITE;
/*!40000 ALTER TABLE `rents` DISABLE KEYS */;
INSERT INTO `rents` VALUES ('2NN7LAW4E9','6472837463','RM87DQTEO6A6Q1WY4','2020-05-04','2020-05-07',14079.00),('V0HQV4ANN5','6473823746','S9JF8ERDYOIFNUMQI','2020-05-05','2020-05-12',16625.00);
/*!40000 ALTER TABLE `rents` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `statuses`
--

DROP TABLE IF EXISTS `statuses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `statuses` (
  `statusID` int NOT NULL,
  `statusName` varchar(10) NOT NULL,
  PRIMARY KEY (`statusID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `statuses`
--

LOCK TABLES `statuses` WRITE;
/*!40000 ALTER TABLE `statuses` DISABLE KEYS */;
INSERT INTO `statuses` VALUES (0,'Ожидание'),(1,'В работе'),(2,'В ремонте'),(3,'В архиве');
/*!40000 ALTER TABLE `statuses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transmission`
--

DROP TABLE IF EXISTS `transmission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transmission` (
  `transmissionID` int NOT NULL,
  `transmissionName` varchar(15) NOT NULL,
  PRIMARY KEY (`transmissionID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transmission`
--

LOCK TABLES `transmission` WRITE;
/*!40000 ALTER TABLE `transmission` DISABLE KEYS */;
INSERT INTO `transmission` VALUES (0,'Механика'),(1,'Автомат'),(2,'Вариатор'),(3,'Робот');
/*!40000 ALTER TABLE `transmission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `types`
--

DROP TABLE IF EXISTS `types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `types` (
  `typeID` int NOT NULL,
  `typeName` varchar(15) NOT NULL,
  PRIMARY KEY (`typeID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `types`
--

LOCK TABLES `types` WRITE;
/*!40000 ALTER TABLE `types` DISABLE KEYS */;
INSERT INTO `types` VALUES (0,'Седан'),(1,'Хэтчбек'),(2,'Универсал'),(3,'Кабриолет'),(4,'Родстер'),(5,'Купе');
/*!40000 ALTER TABLE `types` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-05-05  7:13:16
