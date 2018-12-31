/**
 * Author:  Agneese Saini
 * Created: Nov 28, 2018
 */

-- --------------------------------------------------------

--
-- Table structure for table `images`
--

CREATE TABLE `images` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(2048) NOT NULL,
  `for_post_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `items`
--

CREATE TABLE `items` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(128) DEFAULT NULL,
  `info` text NOT NULL,
  `price` int(11) DEFAULT NULL,
  `address` varchar(128) DEFAULT NULL,
  `city` varchar(64) NOT NULL,
  `province` varchar(64) NOT NULL,
  `postal_code` varchar(6) DEFAULT NULL,
  `country` varchar(64) NOT NULL,
  `post_owner_id` int(11) NOT NULL,
  `post_init_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `post_exit_timestamp` timestamp NULL DEFAULT NULL,
  `main_image_id` int(11) DEFAULT NULL,
  `category` varchar(32) DEFAULT 'Laptop',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `email` varchar(128) NOT NULL,
  `password_hash` varchar(64) NOT NULL,
  `register_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `last_login_timestamp` timestamp NULL DEFAULT NULL,
  `ip_address` varchar(18) DEFAULT NULL,
  `address` varchar(128) DEFAULT NULL,
  `province` varchar(64) DEFAULT NULL,
  `city` varchar(64) DEFAULT NULL,
  `postal_code` varchar(6) DEFAULT NULL,
  `country` varchar(64) DEFAULT NULL,
  `phone_number` int(11) DEFAULT NULL,
  `profile_picture_url` varchar(2048) DEFAULT NULL,
  `request_key` varchar(64) DEFAULT NULL,
  `request_timestamp` timestamp NULL DEFAULT NULL,
  `request_type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Sample data for table `items`
--

INSERT INTO `items` (`id`, `title`, `info`, `price`, `address`, `city`, `province`, `postal_code`, `country`, `post_owner_id`, `post_init_timestamp`, `post_exit_timestamp`, `main_image_id`, `category`) VALUES
(1, 'Test item tittle', 'Test item description, the description only can contain upto 80 characters for this box and the rest of the text is trimmed and replaced with ...', 1000, NULL, 'Ottawa', 'ON', 'K2B6B1', 'Canada', 1, '2018-11-22 10:53:58', '2018-11-22 10:53:58', 1, 'General Laptop'),
(2, 'Solid wood round dining table and 4 chairs -- pending pick up', 'pending pick up -- solid wood table with pedestal base, 4 chairs. Heat mark on table top. Slight wear but still in great shape and would make an excellent chalk paint project. Microfiber seats, easy wipe with damp cloth. Pick up only, cash only, Deer Run Stittsville. Will take table top off for easy transport.', 50, NULL, 'Ottawa', 'ON', 'K2B6B1', 'Canada', 1, '2018-11-22 10:00:55', NULL, NULL, 'General Laptop'),
(3, 'Table en pin IKEA', 'Belle table solide en pin', 55, NULL, 'Gatineau', 'Montreal', 'QC', 'Canada', 2, '2018-11-22 22:03:11', NULL, NULL, 'Gaming Laptop'),
(4, 'Computer schreen 1080p 22 inch IPS x2 asus vz229', '200 for both or 120 each.\r\n\r\nI paid 225 + tx each 6 month again. Used less thsn a week.', 240, NULL, 'Ottawa', 'Ontario', '', 'Canada', 3, '2018-11-22 22:03:11', NULL, NULL, 'General Laptop'),
(5, 'Large rug', 'Large cream color woven wool rug\r\n\r\n8x10 approximately\r\n\r\nPick up in Kanata South', 100, NULL, 'Ottawa', 'Ontario', NULL, 'Canada', 5, '2018-11-22 22:03:11', NULL, NULL, 'Gaming Laptop'),
(6, '52â€� Samsung plasma', 'Selling a 52â€� Samsung plasma tv.\r\nScreen has two scratches but you canâ€™t tell when itâ€™s on. \r\n$150 OBO pickup today as weâ€™re moving', 150, NULL, 'Ottawa', 'Ontario', NULL, 'Canada', 2, '2018-11-22 22:03:11', NULL, NULL, 'Other');

-- --------------------------------------------------------