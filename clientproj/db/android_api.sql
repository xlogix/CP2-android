-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 03, 2017 at 05:28 AM
-- Server version: 10.1.21-MariaDB
-- PHP Version: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `android_api`
--

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

CREATE TABLE `employee` (
  `id` int(11) NOT NULL,
  `emp_code` varchar(3) NOT NULL,
  `emp_name` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`id`, `emp_code`, `emp_name`) VALUES
(10, '111', 'TEST'),
(12, '222', 'TEST2'),
(13, '888', 'TEST888');

-- --------------------------------------------------------

--
-- Table structure for table `entry`
--

CREATE TABLE `entry` (
  `id` int(11) NOT NULL,
  `entry_date` date NOT NULL,
  `shift` varchar(250) CHARACTER SET utf8 NOT NULL,
  `loom_no` varchar(250) CHARACTER SET utf8 NOT NULL,
  `quality` varchar(250) CHARACTER SET utf8 NOT NULL,
  `emp_code` varchar(250) CHARACTER SET utf8 NOT NULL,
  `emp_name` varchar(250) CHARACTER SET utf8 NOT NULL,
  `start_reading` varchar(250) CHARACTER SET utf8 NOT NULL,
  `end_reading` varchar(250) CHARACTER SET utf8 NOT NULL,
  `production` varchar(250) CHARACTER SET utf8 NOT NULL,
  `type` varchar(250) CHARACTER SET utf8 NOT NULL,
  `remarks` varchar(250) CHARACTER SET utf8 NOT NULL,
  `ts` varchar(20) CHARACTER SET utf8 NOT NULL,
  `user_log` varchar(50) CHARACTER SET utf8 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=armscii8 COLLATE=armscii8_bin;

--
-- Dumping data for table `entry`
--

INSERT INTO `entry` (`id`, `entry_date`, `shift`, `loom_no`, `quality`, `emp_code`, `emp_name`, `start_reading`, `end_reading`, `production`, `type`, `remarks`, `ts`, `user_log`) VALUES
(12, '2017-06-05', 'DAY', '222', 'TEST', '111', 'TEST', '1111', '5555', '4444', '11*11', 'undefined', '1496644865', 'admin@gmail.com'),
(13, '2017-06-06', 'DAY', '141', 'TEST', '111', 'TEST', '444', '5555', '5111', '11*11', 'undefined', '1496750019', 'test1@gmail.com'),
(14, '2017-06-06', 'NIGHT', '456', 'TEST', '222', 'TEST2', '144', '555', '411', '11*11', 'undefined', '1496750051', 'test1@gmail.com'),
(15, '2017-06-06', 'NIGHT', '444', 'TEST', '111', 'TEST', '444', '555', '111', '11*11', 'undefined', '1496750596', 'test1@gmail.com'),
(16, '2017-06-06', 'NIGHT', '444', 'TEST', '111', 'TEST', '444', '555', '111', '11*11', 'undefined', '1496750751', 'test1@gmail.com'),
(17, '2017-06-07', 'DAY', '111', 'TEST', '111', 'TEST', '444', '9999', '9555', '10*10', 'TEST', '1496817317', 'test1@gmail.com'),
(18, '2017-06-07', 'DAY', '444', 'TEST', '111', 'TEST', '444', '1495', '1051', '10*10', 'TEST', '1496828393', 'test1@gmail.com'),
(19, '2017-06-07', 'DAY', '555', 'TEST', '111', 'TEST', '444', '12345', '11901', '10*10', 'TEST', '1496829899', 'test1@gmail.com'),
(20, '2017-06-07', 'DAY', '111', 'TEST', '111', 'TEST', '444', '666', '222', '11*11', 'TEST', '1496830020', 'test1@gmail.com'),
(21, '2017-06-15', 'DAY', '444', '', '111', 'TEST', '4444', '5555', '1111', '10*10', '', '1497528296', 'admin@gmail.com'),
(24, '2017-06-18', 'Night', '222', 'TEST', '222', 'TEST', '0712', '0750', '38', '12*12', '', '1497793366', 'test1@gmail.com'),
(25, '2017-06-24', 'Day', '12', '', '121', '', '1234', '1254', '20', '10*10', '', '1498286488', 'admin@gmail.com'),
(26, '2017-06-25', 'Day/Night', '11', 'ans', '225', 'avi', '1675', '1680', '5', '11*11', '', '1498334369', 'admin@gmail.com'),
(27, '2017-06-25', 'Day/Night', '', '', '', '', '', '', '', 'Mess', '', '1498388931', 'test1@gmail.com'),
(28, '2017-06-25', 'Day/Night', '', '', '', '', '', '', '', 'Mess', '', '1498388942', 'test1@gmail.com'),
(29, '2017-06-26', 'Day', '12', 'an', '121', 'teat', '1234', '', '', '11*11', '', '1498472501', 'admin@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `mess_matrix`
--

CREATE TABLE `mess_matrix` (
  `id` int(11) NOT NULL,
  `production_min` varchar(20) NOT NULL,
  `production_max` varchar(20) NOT NULL,
  `mess_count` varchar(20) NOT NULL,
  `mess_size` varchar(20) NOT NULL,
  `incentive` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `mess_matrix`
--

INSERT INTO `mess_matrix` (`id`, `production_min`, `production_max`, `mess_count`, `mess_size`, `incentive`) VALUES
(1, '0', '1000', '1', '10*10', '0'),
(2, '1001', '1100', '1', '10*10', '4'),
(3, '1101', '1200', '1', '10*10', '8'),
(4, '1201', '999999', '1', '10*10', '12'),
(5, '0', '2000', '2', '10*10', '0'),
(6, '2001', '2100', '2', '10*10', '15'),
(7, '2101', '2300', '2', '10*10', '20'),
(8, '2301', '999999', '2', '10*10', '25'),
(9, '0', '2900', '3', '10*10', '0'),
(10, '2901', '3000', '3', '10*10', '20'),
(11, '3001', '3300', '3', '10*10', '30'),
(12, '3301', '999999', '3', '10*10', '40'),
(13, '0', '900', '1', '11*11', '0'),
(14, '901', '1000', '1', '11*11', '4'),
(15, '1001', '1100', '1', '11*11', '8'),
(16, '1101', '999999', '1', '11*11', '12'),
(17, '0', '1800', '2', '11*11', '0'),
(18, '1801', '1900', '2', '11*11', '15'),
(19, '1901', '2100', '2', '11*11', '20'),
(20, '2101', '999999', '2', '11*11', '25'),
(21, '0', '2600', '3', '11*11', '0'),
(22, '2601', '2700', '3', '11*11', '20'),
(23, '2701', '3000', '3', '11*11', '30'),
(24, '3001', '999999', '3', '11*11', '40'),
(25, '0', '800', '1', '12*12', '0'),
(26, '801', '900', '1', '12*12', '4'),
(27, '901', '1000', '1', '12*12', '8'),
(28, '1000', '999999', '1', '12*12', '12'),
(29, '0', '1600', '2', '12*12', '0'),
(30, '1601', '1700', '2', '12*12', '15'),
(31, '1701', '1900', '2', '12*12', '20'),
(32, '1901', '999999', '2', '12*12', '25'),
(33, '0', '2300', '3', '12*12', '0'),
(34, '2301', '2400', '3', '12*12', '20'),
(35, '2401', '2700', '3', '12*12', '30'),
(36, '2701', '999999', '3', '12*12', '40');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `email` varchar(40) NOT NULL,
  `name` varchar(90) NOT NULL,
  `designation` varchar(90) NOT NULL,
  `member_from` varchar(90) NOT NULL,
  `password` varchar(40) NOT NULL,
  `member_type` varchar(30) DEFAULT 'user'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `email`, `name`, `designation`, `member_from`, `password`, `member_type`) VALUES
(1, 'test1@gmail.com', 'Test1', 'Web Developer', '01-01-2017', 'e10adc3949ba59abbe56e057f20f883e', 'user'),
(2, 'test2@gmail.com', 'Test2', 'Web Developer', '01-01-2017', 'e10adc3949ba59abbe56e057f20f883e', 'user'),
(3, 'admin@gmail.com', 'Admin', 'Admin', '01-01-2017', 'e10adc3949ba59abbe56e057f20f883e', 'admin');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `entry`
--
ALTER TABLE `entry`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `mess_matrix`
--
ALTER TABLE `mess_matrix`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `employee`
--
ALTER TABLE `employee`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;
--
-- AUTO_INCREMENT for table `entry`
--
ALTER TABLE `entry`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;
--
-- AUTO_INCREMENT for table `mess_matrix`
--
ALTER TABLE `mess_matrix`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=37;
--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
