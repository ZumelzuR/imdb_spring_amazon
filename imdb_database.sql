-- MySQL dump 10.13  Distrib 5.7.12, for osx10.9 (x86_64)
--
-- Table structure for table `actor`
--

DROP TABLE IF EXISTS `actor`;

CREATE TABLE `actor` (
  `id` varchar(53) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `date_born` varchar(45) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `actor_id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=big5;

--
-- Table structure for table `movie`
--

DROP TABLE IF EXISTS `movie`;

CREATE TABLE `movie` (
  `id` varchar(63) NOT NULL,
  `name` varchar(60) DEFAULT NULL,
  `year` varchar(45) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=big5;

--
-- Table structure for table `actor_movie`
--

DROP TABLE IF EXISTS `actor_movie`;

CREATE TABLE `actor_movie` (
  `actor_id` varchar(53) NOT NULL,
  `movie_id` varchar(63) NOT NULL,
  PRIMARY KEY (`actor_id`,`movie_id`),
  KEY `movie_id_fk_idx` (`movie_id`),
  CONSTRAINT `actor_id_fk` FOREIGN KEY (`actor_id`) REFERENCES `actor` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `movie_id_fk` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=big5;


