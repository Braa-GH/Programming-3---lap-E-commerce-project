-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 17, 2022 at 10:24 PM
-- Server version: 10.4.22-MariaDB
-- PHP Version: 8.1.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ecommerce`
--

-- --------------------------------------------------------

--
-- Table structure for table `card`
--

CREATE TABLE `card` (
  `userId` int(11) DEFAULT NULL,
  `itemId` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `card`
--

INSERT INTO `card` (`userId`, `itemId`) VALUES
(1, 4),
(1, 6),
(1, 4),
(1, 4),
(10, 3),
(10, 4),
(10, 6),
(10, 8);

-- --------------------------------------------------------

--
-- Table structure for table `item`
--

CREATE TABLE `item` (
  `id` int(11) NOT NULL,
  `name` varchar(250) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `seller` varchar(250) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `item`
--

INSERT INTO `item` (`id`, `name`, `price`, `quantity`, `seller`) VALUES
(3, 'MIUI mobile', 600, 8, 'Braa'),
(4, 'Hp laptop', 1200, 11, 'Ahmed'),
(5, 'IPhone mobile', 1000, 30, 'Omar'),
(6, 'TV', 3000, 15, 'Nasa'),
(8, 'Nike shoes', 60, 99, 'Braa'),
(9, 'T-shirt', 40, 120, 'Ahmed'),
(10, 'Java book', 50, 200, 'Nasa'),
(12, 'Bag', 50, 8, 'Omar'),
(13, 'Car', 5000, 1, 'Ali'),
(14, 'Bike', 600, 3, 'Braa');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `username` varchar(100) DEFAULT NULL,
  `email` varchar(250) DEFAULT NULL,
  `password` varchar(250) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `username`, `email`, `password`) VALUES
(1, 'Braa', 'braa@gmail.com', 'k{jj<9'),
(2, 'Ahmed', 'ahmedRami@gmail.com', '89<?>=6'),
(3, 'Nasa', 'Nasa_space@gmail.com', 'qrSdvvzrug$3'),
(4, 'Omar', 'omar@gmail.com', '<;:7896543'),
(9, 'root', 'root@', '{vvy7'),
(10, 'Ali', 'Ali@gmail.com', '4a7d1ed414474e4033ac29ccb8653d9b');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `item`
--
ALTER TABLE `item`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `item`
--
ALTER TABLE `item`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
