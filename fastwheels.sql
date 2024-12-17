-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Dec 14, 2024 at 11:15 PM
-- Server version: 8.0.31
-- PHP Version: 8.1.13

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
('addVehicle', 2, 'Insert Vehicle for rent', NULL, NULL, 1732821973, 1732821973),
('admin', 1, NULL, NULL, NULL, 1732821973, 1732821973),
('client', 1, NULL, NULL, NULL, 1732821973, 1732821973),
('deleteVehicle', 2, 'Delete vehicle', NULL, NULL, 1732821973, 1732821973),
('funcionario', 1, NULL, NULL, NULL, 1732821973, 1732821973),
('manageReservations', 2, 'Manage car reservations', NULL, NULL, 1732821973, 1732821973),
('manageTicket', 2, 'Manage client support tickets', NULL, NULL, 1732821973, 1732821973),
('manageVehicle', 2, 'Manage vehicle', NULL, NULL, 1732821973, 1732821973);

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `clients`
--

INSERT INTO `clients` (`id`, `name`, `email`, `phone`, `roleId`, `createdAt`, `balance`, `iban`, `userId`) VALUES
(1, 'adminteste', 'teste@gmail.com', 'none', 3, '2024-11-27 21:33:12', '0.00', 'none', 2),
(2, 'client', 'client@gmail.com', 'none', 1, '2024-11-27 22:19:04', '0.00', 'none', 3);

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `migration`
--

DROP TABLE IF EXISTS `migration`;
CREATE TABLE IF NOT EXISTS `migration` (
  `version` varchar(180) NOT NULL,
  `apply_time` int DEFAULT NULL,
  PRIMARY KEY (`version`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `migration`
--

INSERT INTO `migration` (`version`, `apply_time`) VALUES
('m000000_000000_base', 1731968391),
('m130524_201442_init', 1731968404),
('m140506_102106_rbac_init', 1731968441),
('m170907_052038_rbac_add_index_on_auth_assignment_user_id', 1731968441),
('m180523_151638_rbac_updates_indexes_without_prefix', 1731968441),
('m190124_110200_add_verification_token_column_to_user_table', 1731968404),
('m200409_110543_rbac_update_mssql_trigger', 1731968441),
('m241115_195725_add_admin_user', 1731968404),
('m241116_174250_init_rbac', 1731968453),
('m241118_222149_create_roles_table', 1731969013),
('m241118_222258_create_clients_table', 1731969014),
('m241118_222330_create_notifications_table', 1731969014),
('m241118_222359_create_supportTickets_table', 1731969014),
('m241118_222507_create_userCars_table', 1731969349),
('m241118_222643_create_favorites_table', 1731969349),
('m241118_222656_create_carReview_table', 1731969567),
('m241118_222706_create_carPhotos_table', 1731969567),
('m241118_222719_create_localization_table', 1731969568),
('m241118_225735_create_reservations_table', 1731970771),
('m241118_225843_create_payments_table', 1731970772),
('m241119_223434_rename_table_carReview', 1732056438),
('m241119_224606_rename_table_localization', 1732056438),
('m241119_232018_addfk_table_clients', 1732060728),
('m241121_005613_insert_roles_into_roles_table', 1732158647),
('m241205_183346_rename_carName_to_carBrand_table_userCars', 1733423711),
('m241210_212544_add_fields_to_userCars', 1733866674),
('m241210_220304_drop_localizations_table', 1733868377),
('m241211_225549_add_field_cidade_to_userCars_table', 1733957933);

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
CREATE TABLE IF NOT EXISTS `roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `roleName` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `username`, `auth_key`, `password_hash`, `password_reset_token`, `email`, `status`, `created_at`, `updated_at`, `verification_token`) VALUES
(1, 'admin', '64nJcL3DqByI5NFSNDkrASzae0lDJDNN', '$2y$13$bc2fSB7eal3V9gixST1R9u8npN44S1tnLBCp3e130JxZS623EiTEm', NULL, 'admin@example.com', 10, 1731968404, 1731968404, NULL),
(2, 'adminteste', 'slXYg3oXmz4EklgWHHkTI6DD_qLoJdjE', '$2y$13$MTvJk0wWvT/gqToOmqlw6.Qx9KKgGfYTgpYhTX02Lb19wa.v/IdtO', NULL, 'teste@gmail.com', 10, 1732743025, 1732743025, '5O2LFASVtes7BVL5yidav4OWfH7csZ4z_1732743025'),
(3, 'client', 'XO8ImsADzhVOviitVw8eDwMr47CXf3fH', '$2y$13$dZyQ/rhJsDyiGgq5H8Fi6OcXjFQgAS6ts4XjQRCZ5lLtjFIZs/4G2', NULL, 'client@gmail.com', 10, 1732745944, 1732745944, 'Rpi-xLxjSox5VtyKdAoUuT5AdCptJ4tl_1732745944');

-- --------------------------------------------------------

--
-- Table structure for table `usercars`
--

DROP TABLE IF EXISTS `usercars`;
CREATE TABLE IF NOT EXISTS `usercars` (
  `id` int NOT NULL AUTO_INCREMENT,
  `clientId` int NOT NULL,
  `carBrand` varchar(80) NOT NULL,
  `carModel` varchar(30) NOT NULL,
  `carYear` int NOT NULL,
  `carDoors` int NOT NULL,
  `createdAt` timestamp NOT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `availableFrom` timestamp NOT NULL,
  `availableTo` timestamp NOT NULL,
  `morada` varchar(200) NOT NULL,
  `codigoPostal` varchar(10) NOT NULL,
  `cidade` varchar(30) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx-userCars-clientId` (`clientId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
-- Constraints for table `carphotos`
--
ALTER TABLE `carphotos`
  ADD CONSTRAINT `fk-carPhotos-carId` FOREIGN KEY (`carId`) REFERENCES `usercars` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `carreviews`
--
ALTER TABLE `carreviews`
  ADD CONSTRAINT `fk-carReview-carId` FOREIGN KEY (`carId`) REFERENCES `usercars` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `clients`
--
ALTER TABLE `clients`
  ADD CONSTRAINT `fk-clients-roleId` FOREIGN KEY (`roleId`) REFERENCES `roles` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `idx-clients-userId` FOREIGN KEY (`userId`) REFERENCES `user` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `favorites`
--
ALTER TABLE `favorites`
  ADD CONSTRAINT `fk-favorites-carId` FOREIGN KEY (`carId`) REFERENCES `usercars` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk-favorites-clientId` FOREIGN KEY (`clientId`) REFERENCES `clients` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `notifications`
--
ALTER TABLE `notifications`
  ADD CONSTRAINT `fk-notifications-clientId` FOREIGN KEY (`clientId`) REFERENCES `clients` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `payments`
--
ALTER TABLE `payments`
  ADD CONSTRAINT `fk-payments-carId` FOREIGN KEY (`carId`) REFERENCES `usercars` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk-payments-clientId` FOREIGN KEY (`clientId`) REFERENCES `clients` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk-payments-reserveId` FOREIGN KEY (`reserveId`) REFERENCES `reservations` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `reservations`
--
ALTER TABLE `reservations`
  ADD CONSTRAINT `fk-reservations-carId` FOREIGN KEY (`carId`) REFERENCES `usercars` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk-reservations-clientId` FOREIGN KEY (`clientId`) REFERENCES `clients` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `supporttickets`
--
ALTER TABLE `supporttickets`
  ADD CONSTRAINT `fk-supportTickets-clientId` FOREIGN KEY (`clientId`) REFERENCES `clients` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `usercars`
--
ALTER TABLE `usercars`
  ADD CONSTRAINT `fk-userCars-clientId` FOREIGN KEY (`clientId`) REFERENCES `clients` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
