-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 03, 2018 at 03:46 PM
-- Server version: 10.1.25-MariaDB
-- PHP Version: 5.6.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `facebook`
--

-- --------------------------------------------------------

--
-- Table structure for table `comments`
--

CREATE TABLE `comments` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `message` text NOT NULL,
  `post_id` int(11) NOT NULL,
  `post_date` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `comments`
--

INSERT INTO `comments` (`id`, `user_id`, `message`, `post_id`, `post_date`) VALUES
(1, 1, 'what is this?', 7, '2017-12-31 18:21:47'),
(2, 1, 'test comment\n', 7, '2017-12-31 18:26:21'),
(3, 1, 'test comment 2', 7, '2017-12-31 18:26:28'),
(5, 1, 'another test', 7, '2017-12-31 18:31:54'),
(6, 1, 'hello', 5, '2018-01-01 11:06:52'),
(7, 1, 'test comment 1\n', 6, '2018-01-01 22:15:14'),
(8, 1, 'test comment 2', 6, '2018-01-01 22:15:21'),
(9, 1, 'test comment 3', 6, '2018-01-01 22:15:37'),
(10, 1, 'test comment 4', 6, '2018-01-01 22:15:47'),
(14, 1, 'hi', 16, '2018-01-02 13:11:27'),
(16, 1, 'test2', 4, '2018-01-02 15:10:37'),
(17, 1, '99\n', 4, '2018-01-02 16:30:55'),
(18, 1, 'hi', 4, '2018-01-02 16:31:03'),
(19, 5, 'Hi Hassan! ', 13, '2018-01-02 22:55:10'),
(20, 5, 'test comment', 17, '2018-01-02 22:55:48'),
(21, 1, 'hello', 4, '2018-01-02 23:58:09'),
(22, 1, 'first test comment on first post on bikers group', 20, '2018-01-03 12:07:22'),
(23, 1, 'hey ameer', 29, '2018-01-03 19:43:15'),
(24, 9, 'how are you?', 29, '2018-01-03 19:43:38');

-- --------------------------------------------------------

--
-- Table structure for table `friendrequests`
--

CREATE TABLE `friendrequests` (
  `id` int(10) NOT NULL,
  `user_id` int(10) NOT NULL,
  `friend_id` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `friends`
--

CREATE TABLE `friends` (
  `id` int(11) NOT NULL,
  `user_id` int(10) NOT NULL,
  `friend_id` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `friends`
--

INSERT INTO `friends` (`id`, `user_id`, `friend_id`) VALUES
(1, 1, 5),
(2, 5, 1),
(23, 1, 9),
(24, 9, 1);

-- --------------------------------------------------------

--
-- Table structure for table `groups`
--

CREATE TABLE `groups` (
  `id` int(11) NOT NULL,
  `creater_id` int(11) NOT NULL,
  `group_name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `groups`
--

INSERT INTO `groups` (`id`, `creater_id`, `group_name`) VALUES
(1, 1, 'Bikers Group');

-- --------------------------------------------------------

--
-- Table structure for table `likes`
--

CREATE TABLE `likes` (
  `id` int(10) NOT NULL,
  `user_id` int(10) NOT NULL,
  `fetch_id` int(10) NOT NULL,
  `post_type` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `likes`
--

INSERT INTO `likes` (`id`, `user_id`, `fetch_id`, `post_type`) VALUES
(8, 1, 6, 1),
(22, 1, 5, 4),
(44, 5, 4, 1),
(76, 1, 15, 4),
(80, 1, 15, 4),
(81, 1, 15, 4),
(82, 1, 15, 4),
(133, 1, 17, 4),
(135, 1, 16, 4),
(137, 1, 12, 4),
(142, 5, 19, 4),
(144, 5, 20, 4),
(146, 5, 17, 1),
(151, 5, 16, 1),
(152, 5, 13, 1),
(153, 1, 20, 4),
(167, 1, 5, 1),
(168, 1, 1, 1),
(187, 1, 16, 1),
(188, 1, 17, 1),
(200, 1, 13, 1),
(202, 1, 19, 2),
(214, 1, 20, 2),
(216, 9, 29, 1),
(217, 1, 29, 1),
(218, 9, 23, 4);

-- --------------------------------------------------------

--
-- Table structure for table `messages`
--

CREATE TABLE `messages` (
  `id` int(11) NOT NULL,
  `from_id` int(11) NOT NULL,
  `to_id` int(11) NOT NULL,
  `message` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `pages`
--

CREATE TABLE `pages` (
  `id` int(11) NOT NULL,
  `creater_id` int(11) NOT NULL,
  `page_name` varchar(50) NOT NULL,
  `page_detail` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `pages`
--

INSERT INTO `pages` (`id`, `creater_id`, `page_name`, `page_detail`) VALUES
(2, 1, 'hassan page', '');

-- --------------------------------------------------------

--
-- Table structure for table `posts`
--

CREATE TABLE `posts` (
  `id` int(10) NOT NULL,
  `user_id` int(10) NOT NULL,
  `fetch_id` int(10) NOT NULL,
  `body` text NOT NULL,
  `post_date` datetime NOT NULL,
  `post_type` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `posts`
--

INSERT INTO `posts` (`id`, `user_id`, `fetch_id`, `body`, `post_date`, `post_type`) VALUES
(1, 1, 1, 'test', '2017-12-01 00:00:00', 1),
(2, 5, 1, 'yo whats up', '2017-12-25 00:00:00', 1),
(4, 5, 1, 'This post should show up on the next page', '2017-12-28 00:00:00', 1),
(5, 1, 1, 'hi guys!!!!!!', '2017-12-24 00:00:00', 1),
(13, 1, 5, 'hello Ali!', '2018-01-02 12:42:00', 1),
(16, 1, 5, 'hello test			', '2018-01-02 13:02:36', 1),
(17, 5, 5, 'test post on my own profile', '2018-01-02 22:55:37', 1),
(20, 1, 1, 'test', '2018-01-03 11:45:38', 2),
(21, 1, 1, 'test post 2\n', '2018-01-03 17:45:34', 2),
(22, 1, 1, 'test post 3', '2018-01-03 17:45:44', 2),
(26, 1, 2, 'my first post', '2018-01-03 19:30:59', 3),
(27, 1, 2, 'second post', '2018-01-03 19:39:41', 3),
(28, 1, 2, 'third post', '2018-01-03 19:39:48', 3),
(29, 9, 1, 'hi hassan', '2018-01-03 19:42:55', 1),
(30, 9, 9, 'a post on my own wall', '2018-01-03 19:43:55', 1);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `firstName` varchar(50) NOT NULL,
  `lastName` varchar(50) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `dateOfBirth` date NOT NULL,
  `lastOnline` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `firstName`, `lastName`, `username`, `password`, `email`, `dateOfBirth`, `lastOnline`) VALUES
(1, 'hassan', 'nawaz', 'hassan', '123', 'hassan@domain.com', '1947-04-11', '0000-00-00 00:00:00'),
(5, 'Ali', 'Nawaz', 'alinawazsolid', '128', 'alinawazsolid@gmail.com', '1980-05-03', '1960-03-03 12:00:00'),
(7, 'hassan', 'nawaz', 'hassan2', '123', 'hassan2@domain.com', '1997-02-01', '2017-12-25 18:54:31'),
(8, 'ali', 'nawaz', 'ali', '123', 'ali@gmail.com', '1990-07-05', '2017-12-25 19:57:48'),
(9, 'ameer', 'ali', 'am', '123', '123', '1997-05-01', '2018-01-03 18:20:10');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `comments`
--
ALTER TABLE `comments`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `friendrequests`
--
ALTER TABLE `friendrequests`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `friends`
--
ALTER TABLE `friends`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `friend_id` (`friend_id`);

--
-- Indexes for table `groups`
--
ALTER TABLE `groups`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `likes`
--
ALTER TABLE `likes`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `messages`
--
ALTER TABLE `messages`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `pages`
--
ALTER TABLE `pages`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `posts`
--
ALTER TABLE `posts`
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
-- AUTO_INCREMENT for table `comments`
--
ALTER TABLE `comments`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;
--
-- AUTO_INCREMENT for table `friendrequests`
--
ALTER TABLE `friendrequests`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `friends`
--
ALTER TABLE `friends`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;
--
-- AUTO_INCREMENT for table `groups`
--
ALTER TABLE `groups`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `likes`
--
ALTER TABLE `likes`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=219;
--
-- AUTO_INCREMENT for table `messages`
--
ALTER TABLE `messages`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `pages`
--
ALTER TABLE `pages`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `posts`
--
ALTER TABLE `posts`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;
--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `friends`
--
ALTER TABLE `friends`
  ADD CONSTRAINT `friends_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `friends_ibfk_2` FOREIGN KEY (`friend_id`) REFERENCES `users` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
