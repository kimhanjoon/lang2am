-- --------------------------------------------------------
-- 호스트:                          127.0.0.1
-- 서버 버전:                        10.1.11-MariaDB - mariadb.org binary distribution
-- 서버 OS:                        Win32
-- HeidiSQL 버전:                  9.1.0.4867
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- lang2am 의 데이터베이스 구조 덤핑
CREATE DATABASE IF NOT EXISTS `lang2am` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `lang2am`;


-- 테이블 lang2am의 구조를 덤프합니다. TB_CODE
CREATE TABLE IF NOT EXISTS `TB_CODE` (
  `CODE` varchar(50) NOT NULL,
  `NO` int(11) NOT NULL AUTO_INCREMENT,
  `TYPE` varchar(20) NOT NULL,
  `DESCRIPTION` varchar(4000) DEFAULT NULL,
  `CREATED_TIME` datetime DEFAULT NULL,
  `CREATED_IP` varchar(50) DEFAULT NULL,
  `CREATED_ID` varchar(50) DEFAULT NULL,
  `MODIFIED_TIME` datetime DEFAULT NULL,
  `MODIFIED_IP` varchar(50) DEFAULT NULL,
  `MODIFIED__ID` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`CODE`),
  UNIQUE KEY `no` (`NO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 내보낼 데이터가 선택되어 있지 않습니다.


-- 테이블 lang2am의 구조를 덤프합니다. TB_REFERER
CREATE TABLE IF NOT EXISTS `TB_REFERER` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(50) NOT NULL,
  `REFERER_TYPE` varchar(50) DEFAULT NULL,
  `REFERER_NAME` varchar(50) DEFAULT NULL,
  `REFERER_POSITION` varchar(50) DEFAULT NULL,
  `CREATED_TIME` datetime DEFAULT NULL,
  `CREATED_IP` varchar(50) DEFAULT NULL,
  `CREATED_ID` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `code` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 내보낼 데이터가 선택되어 있지 않습니다.


-- 테이블 lang2am의 구조를 덤프합니다. TB_SCREENSHOT
CREATE TABLE IF NOT EXISTS `TB_SCREENSHOT` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(50) NOT NULL,
  `LOCALE` varchar(10) NOT NULL,
  `SCREENSHOT` longtext NOT NULL,
  `CREATED_TIME` datetime DEFAULT NULL,
  `CREATED_IP` varchar(50) DEFAULT NULL,
  `CREATED_ID` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 내보낼 데이터가 선택되어 있지 않습니다.


-- 테이블 lang2am의 구조를 덤프합니다. TB_TAG
CREATE TABLE IF NOT EXISTS `TB_TAG` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(50) DEFAULT NULL,
  `LOCALE` varchar(10) DEFAULT NULL,
  `TAG` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 내보낼 데이터가 선택되어 있지 않습니다.


-- 테이블 lang2am의 구조를 덤프합니다. TB_TEXT
CREATE TABLE IF NOT EXISTS `TB_TEXT` (
  `CODE` varchar(50) NOT NULL,
  `LOCALE` varchar(10) NOT NULL,
  `TEXT` varchar(4000) NOT NULL,
  `COMMENT` varchar(4000) DEFAULT NULL,
  `STATUS` varchar(20) DEFAULT NULL,
  `CREATED_TIME` datetime DEFAULT NULL,
  `CREATED_IP` varchar(50) DEFAULT NULL,
  `CREATED_ID` varchar(50) DEFAULT NULL,
  `MODIFIED_TIME` datetime DEFAULT NULL,
  `MODIFIED_IP` varchar(50) DEFAULT NULL,
  `MODIFIED_ID` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`CODE`,`LOCALE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 내보낼 데이터가 선택되어 있지 않습니다.


-- 테이블 lang2am의 구조를 덤프합니다. TB_TEXT_HISTORY
CREATE TABLE IF NOT EXISTS `TB_TEXT_HISTORY` (
  `CODE` varchar(50) NOT NULL,
  `LOCALE` varchar(10) NOT NULL,
  `CHANGED_TIME` datetime NOT NULL,
  `TEXT` varchar(4000) NOT NULL,
  `COMMENT` varchar(4000) DEFAULT NULL,
  `STATUS` varchar(20) DEFAULT NULL,
  `CREATED_TIME` datetime DEFAULT NULL,
  `CREATED_IP` varchar(50) DEFAULT NULL,
  `CREATED_ID` varchar(50) DEFAULT NULL,
  `MODIFIED_TIME` datetime DEFAULT NULL,
  `MODIFIED_IP` varchar(50) DEFAULT NULL,
  `MODIFIED_ID` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`CODE`,`LOCALE`,`CHANGED_TIME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- 내보낼 데이터가 선택되어 있지 않습니다.
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
