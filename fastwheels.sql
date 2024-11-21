-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Nov 21, 2024 at 02:18 AM
-- Server version: 8.3.0
-- PHP Version: 8.2.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `fastwheels`
--

-- --------------------------------------------------------

--
-- Table structure for table `auth_assignment`
--

DROP TABLE IF EXISTS `auth_assignment`;
CREATE TABLE IF NOT EXISTS `auth_assignment` (
  `item_name` varchar(64) COLLATE utf8mb3_unicode_ci NOT NULL,
  `user_id` varchar(64) COLLATE utf8mb3_unicode_ci NOT NULL,
  `created_at` int DEFAULT NULL,
  PRIMARY KEY (`item_name`,`user_id`),
  KEY `idx-auth_assignment-user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

--
-- Dumping data for table `auth_assignment`
--

INSERT INTO `auth_assignment` (`item_name`, `user_id`, `created_at`) VALUES
('client', '10', 1732148968),
('client', '11', 1732149089),
('client', '12', 1732149216),
('client', '13', 1732149275),
('client', '14', 1732149633),
('client', '15', 1732150308),
('client', '16', 1732150776),
('client', '8', 1732148672),
('client', '9', 1732148768);

-- --------------------------------------------------------

--
-- Table structure for table `auth_item`
--

DROP TABLE IF EXISTS `auth_item`;
CREATE TABLE IF NOT EXISTS `auth_item` (
  `name` varchar(64) COLLATE utf8mb3_unicode_ci NOT NULL,
  `type` smallint NOT NULL,
  `description` text COLLATE utf8mb3_unicode_ci,
  `rule_name` varchar(64) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `data` blob,
  `created_at` int DEFAULT NULL,
  `updated_at` int DEFAULT NULL,
  PRIMARY KEY (`name`),
  KEY `rule_name` (`rule_name`),
  KEY `idx-auth_item-type` (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

--
-- Dumping data for table `auth_item`
--

INSERT INTO `auth_item` (`name`, `type`, `description`, `rule_name`, `data`, `created_at`, `updated_at`) VALUES
('addVehicle', 2, 'Insert Vehicle for rent', NULL, NULL, 1731787046, 1731787046),
('admin', 1, NULL, NULL, NULL, 1731787046, 1731787046),
('client', 1, NULL, NULL, NULL, 1731787046, 1731787046),
('deleteVehicle', 2, 'Delete vehicle', NULL, NULL, 1731787046, 1731787046),
('funcionario', 1, NULL, NULL, NULL, 1731787046, 1731787046),
('manageReservations', 2, 'Manage car reservations', NULL, NULL, 1731787046, 1731787046),
('manageTicket', 2, 'Manage client support tickets', NULL, NULL, 1731787046, 1731787046),
('manageVehicle', 2, 'Manage vehicle', NULL, NULL, 1731787046, 1731787046);

-- --------------------------------------------------------

--
-- Table structure for table `auth_item_child`
--

DROP TABLE IF EXISTS `auth_item_child`;
CREATE TABLE IF NOT EXISTS `auth_item_child` (
  `parent` varchar(64) COLLATE utf8mb3_unicode_ci NOT NULL,
  `child` varchar(64) COLLATE utf8mb3_unicode_ci NOT NULL,
  PRIMARY KEY (`parent`,`child`),
  KEY `child` (`child`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

--
-- Dumping data for table `auth_item_child`
--

INSERT INTO `auth_item_child` (`parent`, `child`) VALUES
('admin', 'addVehicle'),
('client', 'addVehicle'),
('funcionario', 'addVehicle'),
('admin', 'deleteVehicle'),
('admin', 'manageReservations'),
('funcionario', 'manageReservations'),
('admin', 'manageTicket'),
('funcionario', 'manageTicket'),
('admin', 'manageVehicle'),
('client', 'manageVehicle'),
('funcionario', 'manageVehicle');

-- --------------------------------------------------------

--
-- Table structure for table `auth_rule`
--

DROP TABLE IF EXISTS `auth_rule`;
CREATE TABLE IF NOT EXISTS `auth_rule` (
  `name` varchar(64) COLLATE utf8mb3_unicode_ci NOT NULL,
  `data` blob,
  `created_at` int DEFAULT NULL,
  `updated_at` int DEFAULT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `carphotos`
--

DROP TABLE IF EXISTS `carphotos`;
CREATE TABLE IF NOT EXISTS `carphotos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `carId` int NOT NULL,
  `photoUrl` varchar(200) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx-carPhotos-carId` (`carId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `carreviews`
--

DROP TABLE IF EXISTS `carreviews`;
CREATE TABLE IF NOT EXISTS `carreviews` (
  `id` int NOT NULL AUTO_INCREMENT,
  `carId` int NOT NULL,
  `comment` varchar(300) NOT NULL,
  `createdAt` timestamp NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx-carReview-carId` (`carId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `clients`
--

DROP TABLE IF EXISTS `clients`;
CREATE TABLE IF NOT EXISTS `clients` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(150) NOT NULL,
  `email` varchar(80) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `roleId` int NOT NULL,
  `createdAt` timestamp NOT NULL,
  `balance` decimal(10,2) NOT NULL,
  `iban` varchar(30) NOT NULL,
  `userId` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx-clients-roleId` (`roleId`),
  KEY `idx-clients-userId` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `favorites`
--

DROP TABLE IF EXISTS `favorites`;
CREATE TABLE IF NOT EXISTS `favorites` (
  `id` int NOT NULL AUTO_INCREMENT,
  `clientId` int NOT NULL,
  `carId` int NOT NULL,
  `createdAt` timestamp NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx-favorites-clientId` (`clientId`),
  KEY `idx-favorites-carId` (`carId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `localizations`
--

DROP TABLE IF EXISTS `localizations`;
CREATE TABLE IF NOT EXISTS `localizations` (
  `id` int NOT NULL AUTO_INCREMENT,
  `carId` int NOT NULL,
  `locationCity` varchar(100) NOT NULL,
  `locationX` float NOT NULL,
  `locationY` float NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx-localization-carId` (`carId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `migration`
--

DROP TABLE IF EXISTS `migration`;
CREATE TABLE IF NOT EXISTS `migration` (
  `version` varchar(180) NOT NULL,
  `apply_time` int DEFAULT NULL,
  PRIMARY KEY (`version`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `migration`
--

INSERT INTO `migration` (`version`, `apply_time`) VALUES
('m000000_000000_base', 1731694513),
('m130524_201442_init', 1731694515),
('m190124_110200_add_verification_token_column_to_user_table', 1731694515),
('m241115_195536_add_admin_user', 1731700564),
('m241115_195725_add_admin_user', 1731700668),
('m140506_102106_rbac_init', 1731778534),
('m170907_052038_rbac_add_index_on_auth_assignment_user_id', 1731778534),
('m180523_151638_rbac_updates_indexes_without_prefix', 1731778535),
('m200409_110543_rbac_update_mssql_trigger', 1731778535),
('m241116_174250_init_rbac', 1731787046),
('m241118_222149_create_roles_table', 1732044082),
('m241118_222258_create_clients_table', 1732044082),
('m241118_222330_create_notifications_table', 1732044082),
('m241118_222359_create_supportTickets_table', 1732044082),
('m241118_222507_create_userCars_table', 1732044082),
('m241118_222643_create_favorites_table', 1732044083),
('m241118_222656_create_carReview_table', 1732044083),
('m241118_222706_create_carPhotos_table', 1732044083),
('m241118_222719_create_localization_table', 1732044083),
('m241118_225735_create_reservations_table', 1732044083),
('m241118_225843_create_payments_table', 1732044083),
('m241119_223434_rename_table_carReview', 1732128523),
('m241119_224606_rename_table_localization', 1732128523),
('m241119_232018_addfk_table_clients', 1732128523),
('m241121_005613_insert_roles_into_roles_table', 1732150625);

-- --------------------------------------------------------

--
-- Table structure for table `notifications`
--

DROP TABLE IF EXISTS `notifications`;
CREATE TABLE IF NOT EXISTS `notifications` (
  `id` int NOT NULL AUTO_INCREMENT,
  `clientId` int NOT NULL,
  `content` varchar(300) NOT NULL,
  `createdAt` timestamp NOT NULL,
  `read` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx-notifications-clientId` (`clientId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `payments`
--

DROP TABLE IF EXISTS `payments`;
CREATE TABLE IF NOT EXISTS `payments` (
  `id` int NOT NULL AUTO_INCREMENT,
  `reserveId` int NOT NULL,
  `clientId` int NOT NULL,
  `carId` int NOT NULL,
  `method` varchar(30) NOT NULL,
  `value` decimal(10,2) NOT NULL,
  `status` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx-payments-reserveId` (`reserveId`),
  KEY `idx-payments-clientId` (`clientId`),
  KEY `idx-payments-carId` (`carId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `reservations`
--

DROP TABLE IF EXISTS `reservations`;
CREATE TABLE IF NOT EXISTS `reservations` (
  `id` int NOT NULL AUTO_INCREMENT,
  `clientId` int NOT NULL,
  `carId` int NOT NULL,
  `dateStart` timestamp NOT NULL,
  `dateEnd` timestamp NOT NULL,
  `createAt` timestamp NULL DEFAULT NULL,
  `filled` tinyint(1) DEFAULT NULL,
  `value` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx-reservations-clientId` (`clientId`),
  KEY `idx-reservations-carId` (`carId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
CREATE TABLE IF NOT EXISTS `roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `roleName` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `roles`
--

INSERT INTO `roles` (`id`, `roleName`) VALUES
(1, 'client'),
(2, 'funcionario'),
(3, 'administrador');

-- --------------------------------------------------------

--
-- Table structure for table `supporttickets`
--

DROP TABLE IF EXISTS `supporttickets`;
CREATE TABLE IF NOT EXISTS `supporttickets` (
  `id` int NOT NULL AUTO_INCREMENT,
  `clientId` int NOT NULL,
  `content` varchar(1000) NOT NULL,
  `createdAt` timestamp NOT NULL,
  `closed` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx-supportTickets-clientId` (`clientId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) COLLATE utf8mb3_unicode_ci NOT NULL,
  `auth_key` varchar(32) COLLATE utf8mb3_unicode_ci NOT NULL,
  `password_hash` varchar(255) COLLATE utf8mb3_unicode_ci NOT NULL,
  `password_reset_token` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb3_unicode_ci NOT NULL,
  `status` smallint NOT NULL DEFAULT '10',
  `created_at` int NOT NULL,
  `updated_at` int NOT NULL,
  `verification_token` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `password_reset_token` (`password_reset_token`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `usercars`
--

DROP TABLE IF EXISTS `usercars`;
CREATE TABLE IF NOT EXISTS `usercars` (
  `id` int NOT NULL AUTO_INCREMENT,
  `clientId` int NOT NULL,
  `carName` varchar(80) NOT NULL,
  `carModel` varchar(30) NOT NULL,
  `carYear` int NOT NULL,
  `carDoors` int NOT NULL,
  `createdAt` timestamp NOT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `availableFrom` timestamp NOT NULL,
  `availableTo` timestamp NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx-userCars-clientId` (`clientId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `auth_assignment`
--
ALTER TABLE `auth_assignment`
  ADD CONSTRAINT `auth_assignment_ibfk_1` FOREIGN KEY (`item_name`) REFERENCES `auth_item` (`name`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `auth_item`
--
ALTER TABLE `auth_item`
  ADD CONSTRAINT `auth_item_ibfk_1` FOREIGN KEY (`rule_name`) REFERENCES `auth_rule` (`name`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Constraints for table `auth_item_child`
--
ALTER TABLE `auth_item_child`
  ADD CONSTRAINT `auth_item_child_ibfk_1` FOREIGN KEY (`parent`) REFERENCES `auth_item` (`name`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `auth_item_child_ibfk_2` FOREIGN KEY (`child`) REFERENCES `auth_item` (`name`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `clients`
--
ALTER TABLE `clients`
  ADD CONSTRAINT `clients_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
