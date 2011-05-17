# Sequel Pro dump
# Version 2492
# http://code.google.com/p/sequel-pro
#
# Host: localhost (MySQL 5.4.3-beta-log)
# Database: ewhiteboard
# Generation Time: 2010-09-16 15:14:15 -0500
# ************************************************************

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


#create database 
#  Make sure you run this script only with root user .


drop database IF EXISTS ewhiteboard;

CREATE DATABASE ewhiteboard;

# create the user 
DELETE FROM `mysql`.`user` WHERE `User`='inno_user';
CREATE USER 'inno_user'@'localhost' IDENTIFIED BY 'inno_mysql';

# grant privilages to the user

GRANT SELECT,INSERT,UPDATE,DELETE ON ewhiteboard.* TO 'inno_user'@'localhost';

FLUSH PRIVILEGES;

use ewhiteboard;
# Dump of table organization
# ------------------------------------------------------------
# Sequel Pro dump
# Version 2492
# http://code.google.com/p/sequel-pro
#
# Host: localhost (MySQL 5.4.3-beta-log)
# Database: ewhiteboard
# Generation Time: 2010-11-17 01:09:25 -0500
# ************************************************************

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table client_media
# ------------------------------------------------------------

DROP TABLE IF EXISTS `client_media`;

CREATE TABLE `client_media` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content_id` varchar(100) NOT NULL,
  `content_source` varchar(200) NOT NULL,
  `content_desc` varchar(200) DEFAULT NULL,
  `content_folder` varchar(5) DEFAULT 'false',
  `content_folder_seq` int(2) NOT NULL DEFAULT '0',
  `content_group` int(10) DEFAULT NULL,
  `content_name` varchar(200) DEFAULT NULL,
  `content_type` varchar(300) DEFAULT '1',
  `inserted_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=268 DEFAULT CHARSET=latin1;

LOCK TABLES `client_media` WRITE;
/*!40000 ALTER TABLE `client_media` DISABLE KEYS */;
INSERT INTO `client_media` (`id`,`content_id`,`content_source`,`content_desc`,`content_folder`,`content_folder_seq`,`content_group`,`content_name`,`content_type`,`inserted_date`)
VALUES
	(251,'27','/opt/InnowhiteData/ClientMedia/27.txt',NULL,'false',0,NULL,NULL,'DOCUMENT','2010-11-16 18:10:36'),
	(252,'89','/opt/InnowhiteData/ClientMedia/89.ods',NULL,'false',0,NULL,NULL,'DOCUMENT','2010-11-16 18:10:36'),
	(253,'90','/opt/InnowhiteData/ClientMedia/90.docx',NULL,'false',0,NULL,NULL,'DOCUMENT','2010-11-16 18:10:41'),
	(254,'72','/opt/InnowhiteData/ClientMedia/72.jpeg',NULL,'false',0,NULL,NULL,'IMAGE','2010-11-16 18:10:42'),
	(255,'34','/opt/InnowhiteData/ClientMedia/34.jpeg',NULL,'false',0,NULL,NULL,'IMAGE','2010-11-16 18:10:42'),
	(256,'35','/opt/InnowhiteData/ClientMedia/35.jpeg',NULL,'false',0,NULL,NULL,'IMAGE','2010-11-16 18:10:43'),
	(257,'88','/opt/InnowhiteData/ClientMedia/88.pptx',NULL,'false',0,NULL,NULL,'DOCUMENT','2010-11-16 18:10:43'),
	(258,'24','/opt/InnowhiteData/ClientMedia/24.swf',NULL,'false',0,NULL,NULL,'EUREKA','2010-11-16 18:10:43'),
	(259,'29','/opt/InnowhiteData/ClientMedia/29.jpeg',NULL,'false',0,NULL,NULL,'IMAGE','2010-11-16 18:10:44'),
	(260,'92','/opt/InnowhiteData/ClientMedia/92.pps',NULL,'false',0,NULL,NULL,'DOCUMENT','2010-11-16 18:10:53'),
	(261,'67','/opt/InnowhiteData/ClientMedia/67.mp3',NULL,'false',0,NULL,NULL,'AUDIO','2010-11-16 18:11:09'),
	(262,'87','/opt/InnowhiteData/ClientMedia/87.ppt',NULL,'false',0,NULL,NULL,'DOCUMENT','2010-11-16 18:11:19'),
	(263,'83','/Users/firemonk/data/83.flv',NULL,'false',0,NULL,NULL,'VIDEO','2010-11-16 18:14:20'),
	(266,'66','/Users/firemonk/data/66.flv',NULL,'false',0,NULL,NULL,'VIDEO','2010-11-16 18:19:45'),
	(265,'22','/Users/firemonk/data/22.flv',NULL,'false',0,NULL,NULL,'VIDEO','2010-11-16 18:17:32'),
	(267,'58','/Users/firemonk/data/58.flv',NULL,'false',0,NULL,NULL,'VIDEO','2010-11-16 18:22:42');

/*!40000 ALTER TABLE `client_media` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table lesson_plan
# ------------------------------------------------------------

DROP TABLE IF EXISTS `lesson_plan`;

CREATE TABLE `lesson_plan` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `course_id` varchar(200) DEFAULT NULL,
  `lesson_plan_id` varchar(200) DEFAULT NULL,
  `lesson_plan_xml` varchar(30000) DEFAULT NULL,
  `inserted_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=144 DEFAULT CHARSET=latin1;

LOCK TABLES `lesson_plan` WRITE;
/*!40000 ALTER TABLE `lesson_plan` DISABLE KEYS */;
INSERT INTO `lesson_plan` (`id`,`course_id`,`lesson_plan_id`,`lesson_plan_xml`,`inserted_date`)
VALUES
	(3,'123','asdasdasdasd','<?xml version=\"1.0\"?><lesson_plan>	<components>		<component>			<component_id>4</component_id>			<component_type>INTRO</component_type>			<component_title>LP-Refraction</component_title>			<component_instruction></component_instruction>			<component_objective>Light is electromagnetic radiation of a				wavelength that is visible to the human eye </component_objective>			<component_bottom_show>false</component_bottom_show>		</component>		<component>			<component_id>34</component_id>			<component_seq>1</component_seq>			<component_quest_asset_id>38</component_quest_asset_id>			<component_type>VIDEO</component_type>			<component_name>video title</component_name>			<component_description></component_description>			<component_source>				http://223.27.120.34/frontend.php/helper/library/content/video/stream/38.flv			</component_source>			<component_purpose></component_purpose>			<component_bottom_show>true</component_bottom_show>		</component>		<component>			<component_id>31</component_id>			<component_seq>2</component_seq>			<component_quest_asset_id>35</component_quest_asset_id>			<component_type>IMAGE</component_type>			<component_name>I1002-First law</component_name>			<component_description>The incident ray, the reflected ray and the				normal to the reflection surface at the point of the incidence lie				in the same plane.</component_description>			<component_source>				http://223.27.120.34/frontend.php/helper/library/content/image/stream/35			</component_source>			<component_purpose>The incident ray, the reflected ray and the				normal to the reflection surface at the point of the incidence lie				in the same plane.</component_purpose>			<component_bottom_show>true</component_bottom_show>		</component>		<component>			<component_id>4</component_id>			<component_seq>3</component_seq>			<component_quest_asset_id />			<component_type>Slide Show</component_type>			<component_name />			<component_description />			<component_source></component_source>			<component_purpose />			<component_bottom_show>true</component_bottom_show>			<subcomponents>				<subcomponent>					<subcomponent_id>26</subcomponent_id>					<subcomponent_seq>1</subcomponent_seq>					<subcomponent_quest_asset_id>29</subcomponent_quest_asset_id>					<subcomponent_type>IMAGE</subcomponent_type>					<subcomponent_name>I102-Generic picture					</subcomponent_name>					<subcomponent_description>Mount Hood reflected in Mirror Lake,						Oregon.</subcomponent_description>					<subcomponent_source>						http://223.27.120.34/frontend.php/helper/library/content/image/stream/29					</subcomponent_source>					<subcomponent_purpose>In acoustics, reflection causes echoes and						is used in sonar. In geology, it is important in the study of						seismic waves. Reflection is observed with surface waves in bodies						of water. Reflection is observed with many types of						electromagnetic wave, besides visible light.					</subcomponent_purpose>				</subcomponent>				<subcomponent>					<subcomponent_id>30</subcomponent_id>					<subcomponent_seq>2</subcomponent_seq>					<subcomponent_quest_asset_id>34</subcomponent_quest_asset_id>					<subcomponent_type>IMAGE</subcomponent_type>					<subcomponent_name>I1002-Reflection					</subcomponent_name>					<subcomponent_description>In acoustics, reflection causes echoes						and is used in sonar. </subcomponent_description>					<subcomponent_source>						http://223.27.120.34/frontend.php/helper/library/content/image/stream/34					</subcomponent_source>					<subcomponent_purpose>In acoustics, reflection causes echoes and						is used in sonar. In geology, it is important in the study of						seismic waves. Reflection is observed with surface waves in bodies						of water. Reflection is observed with many types of						electromagnetic wave, besides visible light.					</subcomponent_purpose>				</subcomponent>				<subcomponent>					<subcomponent_id>31</subcomponent_id>					<subcomponent_seq>3</subcomponent_seq>					<subcomponent_quest_asset_id>35</subcomponent_quest_asset_id>					<subcomponent_type>IMAGE</subcomponent_type>					<subcomponent_name>I1002-First law</subcomponent_name>					<subcomponent_description>The incident ray, the reflected ray						and the normal to the reflection surface at the point of the						incidence lie in the same plane.</subcomponent_description>					<subcomponent_source>						http://223.27.120.34/frontend.php/helper/library/content/image/stream/35					</subcomponent_source>					<subcomponent_purpose>The incident ray, the reflected ray and						the normal to the reflection surface at the point of the incidence						lie in the same plane.</subcomponent_purpose>				</subcomponent>				<subcomponent>					<subcomponent_id>68</subcomponent_id>					<subcomponent_seq>4</subcomponent_seq>					<subcomponent_quest_asset_id>72</subcomponent_quest_asset_id>					<subcomponent_type>IMAGE</subcomponent_type>					<subcomponent_name>I-105 electric</subcomponent_name>					<subcomponent_description></subcomponent_description>					<subcomponent_source>						http://223.27.120.34/frontend.php/helper/library/content/image/stream/72					</subcomponent_source>					<subcomponent_purpose></subcomponent_purpose>				</subcomponent>			</subcomponents>		</component>		<component>			<component_id>3</component_id>			<component_seq>4</component_seq>			<component_quest_asset_id />			<component_type>TEST</component_type>			<component_name>Test</component_name>			<component_description>instruction</component_description>			<component_source>				http://223.27.120.34/frontend.php/helper/3/4/12/openInetComponent			</component_source>			<component_purpose>objective</component_purpose>			<component_bottom_show>true</component_bottom_show>		</component>		<component>			<component_id>5</component_id>			<component_seq>5</component_seq>			<component_quest_asset_id />			<component_type>TEST</component_type>			<component_name>T101 Test</component_name>			<component_description>Test include true-false,				fill-in-the-blank, matching,multiple choice questions,single choice				and descriptive. ...</component_description>			<component_source>				http://223.27.120.34/frontend.php/helper/5/4/12/openInetComponent			</component_source>			<component_purpose>objective of test to improve the better				understanding of the magnet lesson</component_purpose>			<component_bottom_show>true</component_bottom_show>		</component>	</components></lesson_plan>','2010-10-29 18:32:41'),
	(22,'iuii','1234123','<?xml version=\"1.0\"?><lesson_plan><components><component><component_id>4</component_id><component_type>INTRO</component_type><component_title>LP-Refraction</component_title><component_instruction></component_instruction><component_objective>Light is electromagnetic radiation of a wavelength that is visible to the human eye </component_objective><component_bottom_show>false</component_bottom_show></component><component><component_id>34</component_id><component_seq>1</component_seq><component_quest_asset_id>38</component_quest_asset_id><component_type>VIDEO</component_type><component_name>video title</component_name><component_description></component_description><component_source>http://223.27.120.34/frontend.php/helper/library/content/video/stream/38.flv</component_source><component_purpose></component_purpose><component_bottom_show>true</component_bottom_show></component><component><component_id>31</component_id><component_seq>2</component_seq><component_quest_asset_id>35</component_quest_asset_id><component_type>IMAGE</component_type><component_name>I1002-First law</component_name><component_description>The incident ray, the reflected ray and the normal to the reflection surface at the point of the incidence lie in the same plane.</component_description><component_source>http://223.27.120.34/frontend.php/helper/library/content/image/stream/35</component_source><component_purpose>The incident ray, the reflected ray and the normal to the reflection surface at the point of the incidence lie in the same plane.</component_purpose><component_bottom_show>true</component_bottom_show></component><component><component_id>4</component_id><component_seq>3</component_seq><component_quest_asset_id/><component_type>Slide Show</component_type><component_name/><component_description/><component_source></component_source><component_purpose/><component_bottom_show>true</component_bottom_show><subcomponents><subcomponent><subcomponent_id>26</subcomponent_id><subcomponent_seq>1</subcomponent_seq><subcomponent_quest_asset_id>29</subcomponent_quest_asset_id><subcomponent_type>IMAGE</subcomponent_type><subcomponent_name>I102-Generic picture</subcomponent_name><subcomponent_description>Mount Hood reflected in Mirror Lake, Oregon.</subcomponent_description><subcomponent_source>http://223.27.120.34/frontend.php/helper/library/content/image/stream/29</subcomponent_source><subcomponent_purpose>In acoustics, reflection causes echoes and is used in sonar. In geology, it is important in the study of seismic waves. Reflection is observed with surface waves in bodies of water. Reflection is observed with many types of electromagnetic wave, besides visible light.</subcomponent_purpose></subcomponent><subcomponent><subcomponent_id>30</subcomponent_id><subcomponent_seq>2</subcomponent_seq><subcomponent_quest_asset_id>34</subcomponent_quest_asset_id><subcomponent_type>IMAGE</subcomponent_type><subcomponent_name>I1002-Reflection</subcomponent_name><subcomponent_description>In acoustics, reflection causes echoes and is used in sonar. </subcomponent_description><subcomponent_source>http://223.27.120.34/frontend.php/helper/library/content/image/stream/34</subcomponent_source><subcomponent_purpose>In acoustics, reflection causes echoes and is used in sonar. In geology, it is important in the study of seismic waves. Reflection is observed with surface waves in bodies of water. Reflection is observed with many types of electromagnetic wave, besides visible light.</subcomponent_purpose></subcomponent><subcomponent><subcomponent_id>31</subcomponent_id><subcomponent_seq>3</subcomponent_seq><subcomponent_quest_asset_id>35</subcomponent_quest_asset_id><subcomponent_type>IMAGE</subcomponent_type><subcomponent_name>I1002-First law</subcomponent_name><subcomponent_description>The incident ray, the reflected ray and the normal to the reflection surface at the point of the incidence lie in the same plane.</subcomponent_description><subcomponent_source>http://223.27.120.34/frontend.php/helper/library/content/image/stream/35</subcomponent_source><subcomponent_purpose>The incident ray, the reflected ray and the normal to the reflection surface at the point of the incidence lie in the same plane.</subcomponent_purpose></subcomponent><subcomponent><subcomponent_id>68</subcomponent_id><subcomponent_seq>4</subcomponent_seq><subcomponent_quest_asset_id>72</subcomponent_quest_asset_id><subcomponent_type>IMAGE</subcomponent_type><subcomponent_name>I-105 electric</subcomponent_name><subcomponent_description></subcomponent_description><subcomponent_source>http://223.27.120.34/frontend.php/helper/library/content/image/stream/72</subcomponent_source><subcomponent_purpose></subcomponent_purpose></subcomponent></subcomponents></component><component><component_id>3</component_id><component_seq>4</component_seq><component_quest_asset_id/><component_type>TEST</component_type><component_name>Test</component_name><component_description>instruction</component_description><component_source>http://223.27.120.34/frontend.php/helper/3/4/12/openInetComponent</component_source><component_purpose>objective</component_purpose><component_bottom_show>true</component_bottom_show></component><component><component_id>5</component_id><component_seq>5</component_seq><component_quest_asset_id/><component_type>TEST</component_type><component_name>T101 Test</component_name><component_description>Test include true-false, fill-in-the-blank, matching,multiple choice questions,single choice and descriptive. ...</component_description><component_source>http://223.27.120.34/frontend.php/helper/5/4/12/openInetComponent</component_source><component_purpose>objective of test to improve the better understanding of the magnet lesson</component_purpose><component_bottom_show>true</component_bottom_show></component></components></lesson_plan> ','2010-10-31 20:27:08'),
	(35,'12','1234123','<?xml version=\"1.0\"?><lesson_plan><components><component><component_id>12</component_id><component_type>INTRO</component_type><component_title>videos</component_title><component_instruction></component_instruction><component_objective>reflection videos</component_objective><component_bottom_show>false</component_bottom_show></component><component><component_id>78</component_id><component_seq>1</component_seq><component_quest_asset_id>83</component_quest_asset_id><component_type>VIDEO</component_type><component_name>Reflection</component_name><component_description>Reflection is the change in direction of a wavefront at an interface between two different media.</component_description><component_source>http://223.27.120.34/frontend.php/helper/library/content/video/stream/83.flv</component_source><component_purpose>Reflection is the change in direction of a wavefront at an interface between two different media so that the wavefront returns into the medium from which it originated.</component_purpose><component_bottom_show>true</component_bottom_show></component><component><component_id>20</component_id><component_seq>2</component_seq><component_quest_asset_id>22</component_quest_asset_id><component_type>VIDEO</component_type><component_name>V101</component_name><component_description></component_description><component_source>http://223.27.120.34/frontend.php/helper/library/content/video/stream/22.flv</component_source><component_purpose></component_purpose><component_bottom_show>true</component_bottom_show></component><component><component_id>54</component_id><component_seq>3</component_seq><component_quest_asset_id>58</component_quest_asset_id><component_type>VIDEO</component_type><component_name>move it</component_name><component_description></component_description><component_source>http://223.27.120.34/frontend.php/helper/library/content/video/stream/58.flv</component_source><component_purpose></component_purpose><component_bottom_show>true</component_bottom_show></component><component><component_id>62</component_id><component_seq>4</component_seq><component_quest_asset_id>66</component_quest_asset_id><component_type>VIDEO</component_type><component_name>V-101 light</component_name><component_description>The fixed value of the speed of light in SI units results from the fact that the metre is now defined in terms of the speed of light.</component_description><component_source>http://223.27.120.34/frontend.php/helper/library/content/video/stream/66.flv</component_source><component_purpose>The fixed value of the speed of light in SI units results from the fact that the metre is now defined in terms of the speed of light.</component_purpose><component_bottom_show>true</component_bottom_show></component></components></lesson_plan> ','2010-11-01 15:09:02'),
	(143,'12','4','<?xml version=\"1.0\"?><lesson_plan><components><component><component_id>12</component_id><component_type>INTRO</component_type><component_title>videos</component_title><component_instruction></component_instruction><component_objective>reflection videos</component_objective><component_bottom_show>false</component_bottom_show></component><component><component_id>78</component_id><component_seq>1</component_seq><component_quest_asset_id>83</component_quest_asset_id><component_type>VIDEO</component_type><component_name>Reflection</component_name><component_description>Reflection is the change in direction of a wavefront at an interface between two different media.</component_description><component_source>http://223.27.120.34/frontend.php/helper/library/content/video/stream/83.flv</component_source><component_purpose>Reflection is the change in direction of a wavefront at an interface between two different media so that the wavefront returns into the medium from which it originated.</component_purpose><component_bottom_show>true</component_bottom_show></component><component><component_id>20</component_id><component_seq>2</component_seq><component_quest_asset_id>22</component_quest_asset_id><component_type>VIDEO</component_type><component_name>V101</component_name><component_description></component_description><component_source>http://223.27.120.34/frontend.php/helper/library/content/video/stream/22.flv</component_source><component_purpose></component_purpose><component_bottom_show>true</component_bottom_show></component><component><component_id>54</component_id><component_seq>3</component_seq><component_quest_asset_id>58</component_quest_asset_id><component_type>VIDEO</component_type><component_name>move it</component_name><component_description></component_description><component_source>http://223.27.120.34/frontend.php/helper/library/content/video/stream/58.flv</component_source><component_purpose></component_purpose><component_bottom_show>true</component_bottom_show></component><component><component_id>62</component_id><component_seq>4</component_seq><component_quest_asset_id>66</component_quest_asset_id><component_type>VIDEO</component_type><component_name>V-101 light</component_name><component_description>The fixed value of the speed of light in SI units results from the fact that the metre is now defined in terms of the speed of light.</component_description><component_source>http://223.27.120.34/frontend.php/helper/library/content/video/stream/66.flv</component_source><component_purpose>The fixed value of the speed of light in SI units results from the fact that the metre is now defined in terms of the speed of light.</component_purpose><component_bottom_show>true</component_bottom_show></component></components></lesson_plan> ','2010-11-16 23:54:20'),
	(50,'12','2','<?xml version=\"1.0\"?><lesson_plan><components><component><component_id>12</component_id><component_type>INTRO</component_type><component_title>videos</component_title><component_instruction></component_instruction><component_objective>reflection videos</component_objective><component_bottom_show>false</component_bottom_show></component><component><component_id>78</component_id><component_seq>1</component_seq><component_quest_asset_id>83</component_quest_asset_id><component_type>VIDEO</component_type><component_name>Reflection</component_name><component_description>Reflection is the change in direction of a wavefront at an interface between two different media.</component_description><component_source>http://223.27.120.34/frontend.php/helper/library/content/video/stream/83.flv</component_source><component_purpose>Reflection is the change in direction of a wavefront at an interface between two different media so that the wavefront returns into the medium from which it originated.</component_purpose><component_bottom_show>true</component_bottom_show></component><component><component_id>20</component_id><component_seq>2</component_seq><component_quest_asset_id>22</component_quest_asset_id><component_type>VIDEO</component_type><component_name>V101</component_name><component_description></component_description><component_source>http://223.27.120.34/frontend.php/helper/library/content/video/stream/22.flv</component_source><component_purpose></component_purpose><component_bottom_show>true</component_bottom_show></component><component><component_id>54</component_id><component_seq>3</component_seq><component_quest_asset_id>58</component_quest_asset_id><component_type>VIDEO</component_type><component_name>move it</component_name><component_description></component_description><component_source>http://223.27.120.34/frontend.php/helper/library/content/video/stream/58.flv</component_source><component_purpose></component_purpose><component_bottom_show>true</component_bottom_show></component><component><component_id>62</component_id><component_seq>4</component_seq><component_quest_asset_id>66</component_quest_asset_id><component_type>VIDEO</component_type><component_name>V-101 light</component_name><component_description>The fixed value of the speed of light in SI units results from the fact that the metre is now defined in terms of the speed of light.</component_description><component_source>http://223.27.120.34/frontend.php/helper/library/content/video/stream/66.flv</component_source><component_purpose>The fixed value of the speed of light in SI units results from the fact that the metre is now defined in terms of the speed of light.</component_purpose><component_bottom_show>true</component_bottom_show></component></components></lesson_plan> ','2010-11-03 13:41:41'),
	(133,'2','14','<?xml version=\"1.0\"?><lesson_plan><components><component><component_id>2</component_id><component_type>INTRO</component_type><component_title>LP101 Light</component_title><component_instruction></component_instruction><component_objective>objective</component_objective><component_bottom_show>false</component_bottom_show></component><component><component_id>3</component_id><component_seq>1</component_seq><component_quest_asset_id/><component_type>Slide Show</component_type><component_name/><component_description/><component_source></component_source><component_purpose/><component_bottom_show>true</component_bottom_show><subcomponents><subcomponent><subcomponent_id>19</subcomponent_id><subcomponent_seq>1</subcomponent_seq><subcomponent_quest_asset_id>21</subcomponent_quest_asset_id><subcomponent_type>IMAGE</subcomponent_type><subcomponent_name>I101-electroscope</subcomponent_name><subcomponent_description></subcomponent_description><subcomponent_source>http://223.27.120.34/frontend.php/helper/library/content/image/stream/21.jpeg</subcomponent_source><subcomponent_purpose></subcomponent_purpose></subcomponent><subcomponent><subcomponent_id>21</subcomponent_id><subcomponent_seq>2</subcomponent_seq><subcomponent_quest_asset_id>23</subcomponent_quest_asset_id><subcomponent_type>IMAGE</subcomponent_type><subcomponent_name>I102-jpg</subcomponent_name><subcomponent_description></subcomponent_description><subcomponent_source>http://223.27.120.34/frontend.php/helper/library/content/image/stream/23.jpeg</subcomponent_source><subcomponent_purpose></subcomponent_purpose></subcomponent></subcomponents></component><component><component_id>22</component_id><component_seq>2</component_seq><component_quest_asset_id>24</component_quest_asset_id><component_type>EUREKA</component_type><component_name>E101</component_name><component_description></component_description><component_source>http://223.27.120.34/frontend.php/helper/library/content/eureka/stream/24.swf</component_source><component_purpose></component_purpose><component_bottom_show>true</component_bottom_show></component><component><component_id>18</component_id><component_seq>3</component_seq><component_quest_asset_id>20</component_quest_asset_id><component_type>LINK</component_type><component_name>L101</component_name><component_description></component_description><component_source>http://en.wikipedia.org/wiki/Light</component_source><component_purpose></component_purpose><component_bottom_show>true</component_bottom_show></component><component><component_id>17</component_id><component_seq>4</component_seq><component_quest_asset_id>19</component_quest_asset_id><component_type>DOCUMENT</component_type><component_name>D102-pdf</component_name><component_description></component_description><component_source>http://223.27.120.34/frontend.php/helper/library/content/document/stream/19.pdf</component_source><component_purpose></component_purpose><component_bottom_show>true</component_bottom_show></component><component><component_id>16</component_id><component_seq>5</component_seq><component_quest_asset_id>18</component_quest_asset_id><component_type>DOCUMENT</component_type><component_name>D101</component_name><component_description></component_description><component_source>http://223.27.120.34/frontend.php/helper/library/content/document/stream/18.txt</component_source><component_purpose></component_purpose><component_bottom_show>true</component_bottom_show></component><component><component_id>4</component_id><component_seq>6</component_seq><component_quest_asset_id/><component_type>LEARNING_SET</component_type><component_name>LS-01</component_name><component_description>instruction</component_description><component_source>http://223.27.120.34/frontend.php/helper/4/2/14/openInetComponent</component_source><component_purpose>objective</component_purpose><component_bottom_show>true</component_bottom_show></component><component><component_id>3</component_id><component_seq>7</component_seq><component_quest_asset_id/><component_type>TEST</component_type><component_name>Test</component_name><component_description>instruction</component_description><component_source>http://223.27.120.34/frontend.php/helper/3/2/14/openInetComponent</component_source><component_purpose>objective</component_purpose><component_bottom_show>true</component_bottom_show></component><component><component_id>5</component_id><component_seq>8</component_seq><component_quest_asset_id/><component_type>TEST</component_type><component_name>T101 Test</component_name><component_description>Test include true-false, fill-in-the-blank, matching,multiple choice questions,single choice and descriptive. ...</component_description><component_source>http://223.27.120.34/frontend.php/helper/5/2/14/openInetComponent</component_source><component_purpose>objective of test to improve the better understanding of the magnet lesson</component_purpose><component_bottom_show>true</component_bottom_show></component><component><component_id>7</component_id><component_seq>9</component_seq><component_quest_asset_id/><component_type>TEST</component_type><component_name>test 1 min</component_name><component_description>instr</component_description><component_source>http://223.27.120.34/frontend.php/helper/7/2/14/openInetComponent</component_source><component_purpose>obj</component_purpose><component_bottom_show>true</component_bottom_show></component></components></lesson_plan> ','2010-11-13 08:18:21'),
	(141,'14','2','<?xml version=\"1.0\"?><lesson_plan><components><component><component_id>14</component_id><component_type>INTRO</component_type><component_title>Refraction_content</component_title><component_instruction>Reflection is the change in direction of a wavefront at an interface between two different media so that the wavefront returns into the medium from which it originated.</component_instruction><component_objective>Reflection is the change in direction of a wavefront at an interface between two different media so that the wavefront returns into the medium from which it originated.</component_objective><component_bottom_show>false</component_bottom_show></component><component><component_id>62</component_id><component_seq>1</component_seq><component_quest_asset_id>66</component_quest_asset_id><component_type>VIDEO</component_type><component_name>V-101 light</component_name><component_description>The fixed value of the speed of light in SI units results from the fact that the metre is now defined in terms of the speed of light.</component_description><component_source>http://223.27.120.34/frontend.php/helper/library/content/video/stream/66.flv</component_source><component_purpose>The fixed value of the speed of light in SI units results from the fact that the metre is now defined in terms of the speed of light.</component_purpose><component_bottom_show>true</component_bottom_show></component><component><component_id>63</component_id><component_seq>2</component_seq><component_quest_asset_id>67</component_quest_asset_id><component_type>AUDIO</component_type><component_name>A-102 Intro to light</component_name><component_description>The fixed value of the speed of light in SI units results from the fact that the metre is now defined in terms of the speed of light.</component_description><component_source>http://223.27.120.34/frontend.php/helper/library/content/audio/stream/67.mp3</component_source><component_purpose>The fixed value of the speed of light in SI units results from the fact that the metre is now defined in terms of the speed of light.</component_purpose><component_bottom_show>true</component_bottom_show></component><component><component_id>24</component_id><component_seq>3</component_seq><component_quest_asset_id>27</component_quest_asset_id><component_type>DOCUMENT</component_type><component_name>D102-Description</component_name><component_description>Reflection is the change in direction of a wavefront at an interface between two different media so that the wavefront returns into the medium from which it originated.</component_description><component_source>http://223.27.120.34/frontend.php/helper/library/content/document/stream/27.txt</component_source><component_purpose>Reflection is the change in direction of a wavefront at an interface between two different media so that the wavefront returns into the medium from which it originated.</component_purpose><component_bottom_show>true</component_bottom_show></component><component><component_id>81</component_id><component_seq>4</component_seq><component_quest_asset_id>87</component_quest_asset_id><component_type>DOCUMENT</component_type><component_name>virtual box</component_name><component_description>VirtualBox is a general-purpose full virtualizer for x86 hardware.</component_description><component_source>http://223.27.120.34/frontend.php/helper/library/content/document/stream/87.ppt</component_source><component_purpose>VirtualBox is a general-purpose full virtualizer for x86 hardware.</component_purpose><component_bottom_show>true</component_bottom_show></component><component><component_id>82</component_id><component_seq>5</component_seq><component_quest_asset_id>88</component_quest_asset_id><component_type>DOCUMENT</component_type><component_name>RSS pptx</component_name><component_description>A fast, concise, library that simplifies how to traverse HTML documents, handle events, perform animations, and add AJAX.</component_description><component_source>http://223.27.120.34/frontend.php/helper/library/content/document/stream/88.pptx</component_source><component_purpose>A fast, concise, library that simplifies how to traverse HTML documents, handle events, perform animations, and add AJAX.</component_purpose><component_bottom_show>true</component_bottom_show></component><component><component_id>86</component_id><component_seq>6</component_seq><component_quest_asset_id>92</component_quest_asset_id><component_type>DOCUMENT</component_type><component_name>D113-pps</component_name><component_description></component_description><component_source>http://223.27.120.34/frontend.php/helper/library/content/document/stream/92.pps</component_source><component_purpose></component_purpose><component_bottom_show>true</component_bottom_show></component><component><component_id>83</component_id><component_seq>7</component_seq><component_quest_asset_id>89</component_quest_asset_id><component_type>DOCUMENT</component_type><component_name>D110-ods</component_name><component_description></component_description><component_source>http://223.27.120.34/frontend.php/helper/library/content/document/stream/89.ods</component_source><component_purpose></component_purpose><component_bottom_show>true</component_bottom_show></component><component><component_id>84</component_id><component_seq>8</component_seq><component_quest_asset_id>90</component_quest_asset_id><component_type>DOCUMENT</component_type><component_name>D-105 electric docx</component_name><component_description>A fast, concise, library that simplifies how to traverse HTML documents, handle events, perform animations, and add AJAX.</component_description><component_source>http://223.27.120.34/frontend.php/helper/library/content/document/stream/90.docx</component_source><component_purpose>A fast, concise, library that simplifies how to traverse HTML documents, handle events, perform animations, and add AJAX.</component_purpose><component_bottom_show>true</component_bottom_show></component><component><component_id>25</component_id><component_seq>9</component_seq><component_quest_asset_id>28</component_quest_asset_id><component_type>LINK</component_type><component_name>L102-Wikipedia </component_name><component_description>Reflection is the change in direction of a wavefront at an interface between two different media so that the wavefront returns into the medium from which it originated.</component_description><component_source>http://en.wikipedia.org/wiki/Reflection_%28physics%29</component_source><component_purpose>Reflection is the change in direction of a wavefront at an interface between two different media so that the wavefront returns into the medium from which it originated.</component_purpose><component_bottom_show>true</component_bottom_show></component><component><component_id>30</component_id><component_seq>10</component_seq><component_quest_asset_id>34</component_quest_asset_id><component_type>IMAGE</component_type><component_name>I1002-Reflection</component_name><component_description>In acoustics, reflection causes echoes and is used in sonar. </component_description><component_source>http://223.27.120.34/frontend.php/helper/library/content/image/stream/34.jpeg</component_source><component_purpose>In acoustics, reflection causes echoes and is used in sonar. In geology, it is important in the study of seismic waves. Reflection is observed with surface waves in bodies of water. Reflection is observed with many types of electromagnetic wave, besides visible light.</component_purpose><component_bottom_show>true</component_bottom_show></component><component><component_id>4</component_id><component_seq>11</component_seq><component_quest_asset_id/><component_type>Slide Show</component_type><component_name/><component_description/><component_source></component_source><component_purpose/><component_bottom_show>true</component_bottom_show><subcomponents><subcomponent><subcomponent_id>26</subcomponent_id><subcomponent_seq>1</subcomponent_seq><subcomponent_quest_asset_id>29</subcomponent_quest_asset_id><subcomponent_type>IMAGE</subcomponent_type><subcomponent_name>I102-Generic picture</subcomponent_name><subcomponent_description>Mount Hood reflected in Mirror Lake, Oregon.</subcomponent_description><subcomponent_source>http://223.27.120.34/frontend.php/helper/library/content/image/stream/29.jpeg</subcomponent_source><subcomponent_purpose>In acoustics, reflection causes echoes and is used in sonar. In geology, it is important in the study of seismic waves. Reflection is observed with surface waves in bodies of water. Reflection is observed with many types of electromagnetic wave, besides visible light.</subcomponent_purpose></subcomponent><subcomponent><subcomponent_id>30</subcomponent_id><subcomponent_seq>2</subcomponent_seq><subcomponent_quest_asset_id>34</subcomponent_quest_asset_id><subcomponent_type>IMAGE</subcomponent_type><subcomponent_name>I1002-Reflection</subcomponent_name><subcomponent_description>In acoustics, reflection causes echoes and is used in sonar. </subcomponent_description><subcomponent_source>http://223.27.120.34/frontend.php/helper/library/content/image/stream/34.jpeg</subcomponent_source><subcomponent_purpose>In acoustics, reflection causes echoes and is used in sonar. In geology, it is important in the study of seismic waves. Reflection is observed with surface waves in bodies of water. Reflection is observed with many types of electromagnetic wave, besides visible light.</subcomponent_purpose></subcomponent><subcomponent><subcomponent_id>31</subcomponent_id><subcomponent_seq>3</subcomponent_seq><subcomponent_quest_asset_id>35</subcomponent_quest_asset_id><subcomponent_type>IMAGE</subcomponent_type><subcomponent_name>I1002-First law</subcomponent_name><subcomponent_description>The incident ray, the reflected ray and the normal to the reflection surface at the point of the incidence lie in the same plane.</subcomponent_description><subcomponent_source>http://223.27.120.34/frontend.php/helper/library/content/image/stream/35.jpeg</subcomponent_source><subcomponent_purpose>The incident ray, the reflected ray and the normal to the reflection surface at the point of the incidence lie in the same plane.</subcomponent_purpose></subcomponent><subcomponent><subcomponent_id>68</subcomponent_id><subcomponent_seq>4</subcomponent_seq><subcomponent_quest_asset_id>72</subcomponent_quest_asset_id><subcomponent_type>IMAGE</subcomponent_type><subcomponent_name>I-105 electric</subcomponent_name><subcomponent_description></subcomponent_description><subcomponent_source>http://223.27.120.34/frontend.php/helper/library/content/image/stream/72.jpeg</subcomponent_source><subcomponent_purpose></subcomponent_purpose></subcomponent></subcomponents></component><component><component_id>22</component_id><component_seq>12</component_seq><component_quest_asset_id>24</component_quest_asset_id><component_type>EUREKA</component_type><component_name>E101</component_name><component_description></component_description><component_source>http://223.27.120.34/frontend.php/helper/library/content/eureka/stream/24.swf</component_source><component_purpose></component_purpose><component_bottom_show>true</component_bottom_show></component><component><component_id>6</component_id><component_seq>13</component_seq><component_quest_asset_id/><component_type>LEARNING_SET</component_type><component_name>LS-102</component_name><component_description>No negative marking</component_description><component_source>http://223.27.120.34/frontend.php/helper/6/14/2/openInetComponent</component_source><component_purpose>practise test</component_purpose><component_bottom_show>true</component_bottom_show></component><component><component_id>12</component_id><component_seq>14</component_seq><component_quest_asset_id/><component_type>TEST</component_type><component_name>Test with 25 questions</component_name><component_description>instruction</component_description><component_source>http://223.27.120.34/frontend.php/helper/12/14/2/openInetComponent</component_source><component_purpose>objective</component_purpose><component_bottom_show>true</component_bottom_show></component><component><component_id>13</component_id><component_seq>15</component_seq><component_quest_asset_id/><component_type>TEST</component_type><component_name>Unit Test</component_name><component_description>read the question carefully</component_description><component_source>http://223.27.120.34/frontend.php/helper/13/14/2/openInetComponent</component_source><component_purpose>to achieve more knowledge about the work and energy</component_purpose><component_bottom_show>true</component_bottom_show></component></components></lesson_plan> ','2010-11-16 18:30:28');

/*!40000 ALTER TABLE `lesson_plan` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table organization
# ------------------------------------------------------------

DROP TABLE IF EXISTS `organization`;

CREATE TABLE `organization` (
  `id` int(11) NOT NULL,
  `parent_org` varchar(150) DEFAULT NULL,
  `org_name` varchar(150) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `exp_date` date DEFAULT NULL,
  `salt_key` varchar(45) DEFAULT NULL,
  `inserted_date` datetime DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `organization` WRITE;
/*!40000 ALTER TABLE `organization` DISABLE KEYS */;
INSERT INTO `organization` (`id`,`parent_org`,`org_name`,`start_date`,`exp_date`,`salt_key`,`inserted_date`,`updated_date`)
VALUES
	(1001,'ABC','Company1','2010-05-01','2010-10-25','1001_Company1_ABC','2010-07-05 11:52:31','2010-07-05 11:52:31'),
	(1002,'DEFAULT_WB','TestInnowhite','2010-05-01','2010-10-25','abc287uiry323p03asdf','2010-07-08 17:46:31','2010-07-08 17:46:31'),
	(1003,'INET','INET','2010-05-10','2011-05-10','abc287uiry323p03asdf','2010-07-05 11:52:31',NULL);

/*!40000 ALTER TABLE `organization` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table room
# ------------------------------------------------------------

DROP TABLE IF EXISTS `room`;

CREATE TABLE `room` (
  `room_id` varchar(45) NOT NULL,
  `room_name` varchar(150) NOT NULL,
  `org_name` varchar(150) NOT NULL,
  `room_active` varchar(15) DEFAULT 'INACTIVE',
  `users_count` int(11) DEFAULT NULL,
  `view_count` int(11) DEFAULT NULL,
  `group_leader` varchar(150) DEFAULT NULL,
  `inserted_date` datetime DEFAULT NULL,
  `test_date` timestamp NULL DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `id` int(7) NOT NULL AUTO_INCREMENT,
  `record` varchar(11) DEFAULT 'FALSE',
  `course` varchar(50) DEFAULT NULL,
  `lesson_plan_id` varchar(50) DEFAULT NULL,
  `playback_active` varchar(50) DEFAULT NULL,
  `client_name` varchar(50) DEFAULT NULL,
  `room_archieve` varchar(1) DEFAULT 'N',
  `room_archieved_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=328 DEFAULT CHARSET=latin1;

LOCK TABLES `room` WRITE;
/*!40000 ALTER TABLE `room` DISABLE KEYS */;
INSERT INTO `room` (`room_id`,`room_name`,`org_name`,`room_active`,`users_count`,`view_count`,`group_leader`,`inserted_date`,`test_date`,`start_date`,`end_date`,`id`,`record`,`course`,`lesson_plan_id`,`playback_active`,`client_name`,`room_archieve`,`room_archieved_date`)
VALUES
	('80219633417','9','TestInnowhite','INACTIVE',0,0,NULL,'2010-07-18 19:07:01',NULL,'2010-07-18 19:08:56','2010-07-18 19:09:54',36,NULL,NULL,NULL,NULL,NULL,'N',NULL),
	('26983342253','room1001','TestInnowhite','INACTIVE',0,0,NULL,'2010-08-30 22:31:38',NULL,NULL,NULL,37,'null','null',NULL,NULL,NULL,'N',NULL),
	('29193602614','room1002scope=Public','TestInnowhite','INACTIVE',0,0,NULL,'2010-08-30 22:35:19',NULL,NULL,NULL,38,'null','java',NULL,NULL,NULL,'N',NULL),
	('29898523940','room10022scope=Public','TestInnowhite','INACTIVE',0,0,NULL,'2010-08-30 22:36:29',NULL,NULL,NULL,39,'null','java12',NULL,NULL,NULL,'N',NULL),
	('32884982410','room_23scope=Public','TestInnowhite','INACTIVE',0,0,NULL,'2010-08-30 22:41:28',NULL,NULL,NULL,40,'null','rails',NULL,NULL,NULL,'N',NULL),
	('33969638820','a','TestInnowhite','ACTIVE',0,0,NULL,'2010-08-30 22:43:16',NULL,NULL,NULL,41,'TRUE','234',NULL,NULL,NULL,'N',NULL),
	('38810968102','a','TestInnowhite','INACTIVE',0,0,NULL,'2010-08-30 22:51:21',NULL,NULL,NULL,42,'Public','2342',NULL,NULL,NULL,'N',NULL),
	('39769567183','a','TestInnowhite','INACTIVE',0,0,NULL,'2010-08-30 22:52:56',NULL,NULL,NULL,43,'Public','23422',NULL,NULL,NULL,'N',NULL),
	('75359132001','adadf','TestInnowhite','INACTIVE',0,0,NULL,'2010-08-30 23:52:15',NULL,NULL,NULL,44,NULL,'32111',NULL,NULL,NULL,'N',NULL),
	('7774590964','eeee','TestInnowhite','ACTIVE',0,0,NULL,'2010-08-30 23:56:14',NULL,'2010-08-30 23:57:24','2010-08-30 23:58:57',45,'true','java',NULL,NULL,NULL,'N',NULL),
	('04665435458','101','TestInnowhite','INACTIVE',0,0,NULL,'2010-08-31 00:41:06',NULL,'2010-08-31 00:52:08','2010-08-31 00:52:18',46,'true','c',NULL,NULL,NULL,'N',NULL),
	('52514551814','dhiraj1','TestInnowhite','INACTIVE',0,0,NULL,'2010-08-31 07:34:11',NULL,NULL,NULL,47,'true','java',NULL,NULL,NULL,'N',NULL),
	('5369918234','dhiraj2','TestInnowhite','INACTIVE',0,0,NULL,'2010-08-31 07:36:09',NULL,NULL,NULL,48,'true','flex',NULL,NULL,NULL,'N',NULL),
	('5969300465','dhiraj21','TestInnowhite','INACTIVE',0,0,NULL,'2010-08-31 07:46:09',NULL,'2010-08-31 07:48:50','2010-08-31 07:50:15',49,'true','rails',NULL,NULL,NULL,'N',NULL),
	('28107551561','12312','TestInnowhite','ACTIVE',0,0,NULL,'2010-08-31 20:00:10',NULL,'2010-08-31 20:00:41',NULL,50,'false','qwe',NULL,NULL,NULL,'N',NULL),
	('33966641123','qwe','TestInnowhite','ACTIVE',0,0,NULL,'2010-08-31 20:09:56',NULL,'2010-08-31 20:10:23',NULL,51,'false','rails',NULL,NULL,NULL,'N',NULL),
	('39159197766','jhg','TestInnowhite','INACTIVE',0,0,NULL,'2010-08-31 20:18:35',NULL,NULL,NULL,52,'false','java',NULL,NULL,NULL,'N',NULL),
	('39214904941','jhg','TestInnowhite','INACTIVE',0,0,NULL,'2010-08-31 20:18:41',NULL,'2010-08-31 20:20:22','2010-08-31 20:20:47',53,'false','java123',NULL,NULL,NULL,'N',NULL),
	('41023877605','j','TestInnowhite','INACTIVE',0,0,NULL,'2010-08-31 20:21:42',NULL,'2010-08-31 20:29:30','2010-08-31 20:30:33',54,'false','j',NULL,NULL,NULL,'N',NULL),
	('4708703227','tinkuroom','TestInnowhite','INACTIVE',0,0,NULL,'2010-08-31 20:31:48',NULL,'2010-08-31 20:33:04','2010-08-31 20:42:16',55,'true','101',NULL,NULL,NULL,'N',NULL),
	('55234428736','tinkuroom','TestInnowhite','INACTIVE',0,0,NULL,'2010-08-31 20:45:23',NULL,NULL,NULL,56,'true','101',NULL,NULL,NULL,'N',NULL),
	('55255826789','tinkuroom','TestInnowhite','ACTIVE',0,0,NULL,'2010-08-31 20:45:25',NULL,'2010-08-31 20:45:44',NULL,57,'true','101',NULL,NULL,NULL,'N',NULL),
	('65866273421','u','TestInnowhite','INACTIVE',0,0,NULL,'2010-08-31 21:03:06',NULL,NULL,NULL,58,'false','i',NULL,NULL,NULL,'N',NULL),
	('75166352929','ok','TestInnowhite','INACTIVE',0,0,NULL,'2010-08-31 21:18:36',NULL,NULL,NULL,59,'false','uo',NULL,NULL,NULL,'N',NULL),
	('78962506843','t','TestInnowhite','INACTIVE',0,0,NULL,'2010-08-31 21:24:56',NULL,NULL,NULL,60,'false','t',NULL,NULL,NULL,'N',NULL),
	('79428783786','t','TestInnowhite','INACTIVE',0,0,NULL,'2010-08-31 21:25:42',NULL,NULL,NULL,61,'false','t',NULL,NULL,NULL,'N',NULL),
	('87032037293','8','TestInnowhite','INACTIVE',0,0,NULL,'2010-09-01 00:25:03',NULL,NULL,NULL,62,'true','7',NULL,NULL,NULL,'N',NULL),
	('88484118825','8','TestInnowhite','INACTIVE',0,0,NULL,'2010-09-01 00:27:28',NULL,NULL,NULL,63,'true','7',NULL,NULL,NULL,'N',NULL),
	('88626316490','85','TestInnowhite','INACTIVE',0,0,NULL,'2010-09-01 00:27:42',NULL,NULL,NULL,64,'true','76',NULL,NULL,NULL,'N',NULL),
	('92871155966','ty','INET','INACTIVE',0,0,NULL,'2010-09-01 00:34:47',NULL,NULL,NULL,65,'false','gtrr',NULL,NULL,NULL,'N','2010-10-14 13:03:37'),
	('93241515151','ty','INET','INACTIVE',0,0,NULL,'2010-09-01 00:35:24',NULL,NULL,NULL,66,'false','gtrr',NULL,NULL,NULL,'N','2010-10-14 13:03:37'),
	('97376385598','t','INET','INACTIVE',0,0,NULL,'2010-09-01 00:42:17',NULL,NULL,NULL,67,'false','t',NULL,NULL,NULL,'N','2010-10-14 13:03:37'),
	('97479263486','tk','INET','INACTIVE',0,0,NULL,'2010-09-01 00:42:27',NULL,NULL,NULL,68,'false','tk',NULL,NULL,NULL,'N','2010-10-14 13:03:37'),
	('9818002499','tkj','INET','INACTIVE',0,0,NULL,'2010-09-01 00:43:38',NULL,NULL,NULL,69,'false','tkj',NULL,NULL,NULL,'N','2010-10-14 13:03:37'),
	('02321215171','78','INET','INACTIVE',0,0,NULL,'2010-09-01 00:50:32',NULL,NULL,NULL,70,'false','87',NULL,NULL,NULL,'N','2010-10-14 13:03:37'),
	('0366112183','78k','INET','INACTIVE',0,0,NULL,'2010-09-01 00:52:46',NULL,NULL,NULL,71,'false','k87',NULL,NULL,NULL,'N','2010-10-14 13:03:37'),
	('04959316498','tyj','INET','INACTIVE',0,0,NULL,'2010-09-01 00:54:55',NULL,NULL,NULL,72,'false','gtrrm',NULL,NULL,NULL,'N','2010-10-14 13:03:37'),
	('06331075470','nui','INET','INACTIVE',0,0,NULL,'2010-09-01 00:57:13',NULL,NULL,NULL,73,'false','iui',NULL,NULL,NULL,'N','2010-10-14 13:03:37'),
	('12043694724','kjhko','INET','INACTIVE',0,0,NULL,'2010-09-01 01:06:44',NULL,NULL,NULL,74,'false','hjjkhk',NULL,NULL,NULL,'N','2010-10-14 13:03:37'),
	('12100207836','kjhko','INET','INACTIVE',0,0,NULL,'2010-09-01 01:06:50',NULL,NULL,NULL,75,'false','hjjkhkkj',NULL,NULL,NULL,'N','2010-10-14 13:03:37'),
	('17671714896','kjhkjh','INET','INACTIVE',0,0,NULL,'2010-09-01 01:16:07',NULL,NULL,NULL,76,'false','kjh',NULL,NULL,NULL,'N','2010-10-14 13:03:37'),
	('21164373720','jghjy','INET','INACTIVE',0,0,NULL,'2010-09-01 01:21:56',NULL,'2010-09-01 01:22:43','2010-09-01 01:23:14',77,'false','jhggj',NULL,NULL,NULL,'N','2010-10-14 13:03:37'),
	('22004487665','jghjy','INET','INACTIVE',0,0,NULL,'2010-09-01 01:23:20',NULL,'2010-09-01 01:25:58','2010-09-01 01:26:03',78,'false','jhggj',NULL,NULL,NULL,'N','2010-10-14 13:03:37'),
	('28741164091','kjh','INET','INACTIVE',0,0,NULL,'2010-09-01 01:34:34',NULL,'2010-09-01 01:34:44','2010-09-01 01:34:51',79,'false','kjhkh',NULL,NULL,NULL,'N','2010-10-14 13:03:37'),
	('31083152190','kjh','INET','INACTIVE',0,0,NULL,'2010-09-01 01:38:28',NULL,NULL,NULL,80,'false','kjhkh',NULL,NULL,NULL,'N','2010-10-14 13:03:37'),
	('31096646695','kjh','INET','INACTIVE',0,0,NULL,'2010-09-01 01:38:29',NULL,'2010-09-01 01:38:32','2010-09-01 01:39:14',81,'false','kjhkh',NULL,NULL,NULL,'N','2010-10-14 13:03:37'),
	('21217681273','j','INET','ACTIVE',0,0,NULL,'2010-09-01 18:02:01',NULL,'2010-09-01 18:02:10',NULL,82,'true','jnm,',NULL,NULL,NULL,'N','2010-10-14 13:03:37'),
	('23540035508','jhh','INET','INACTIVE',0,0,NULL,'2010-09-01 18:05:54',NULL,'2010-09-01 18:06:58','2010-09-01 18:41:35',83,'false','jhjh',NULL,NULL,NULL,'N','2010-10-14 13:03:37'),
	('46767106380','kjhjk','INET','ACTIVE',0,0,NULL,'2010-09-01 18:44:36',NULL,'2010-09-01 18:44:56',NULL,84,'false','kjhk',NULL,NULL,NULL,'N','2010-10-14 13:03:37'),
	('48949965998','jhgjgh','INET','INACTIVE',0,0,NULL,'2010-09-01 18:48:14',NULL,'2010-09-01 18:48:31','2010-09-01 18:48:39',85,'false','rails',NULL,NULL,NULL,'N','2010-10-14 13:03:37'),
	('49833972008','hjhj','INET','ACTIVE',0,0,NULL,'2010-09-01 18:49:43',NULL,'2010-09-01 18:49:51',NULL,86,'false','jhjhjh',NULL,NULL,NULL,'N','2010-10-14 13:03:37'),
	('51582184197','hjhj','INET','INACTIVE',0,0,NULL,'2010-09-01 18:52:38',NULL,'2010-09-01 18:52:49','2010-09-01 18:57:51',87,'false','javalk',NULL,NULL,NULL,'N','2010-10-14 13:03:37'),
	('55666058078','hjhj','INET','INACTIVE',0,0,NULL,'2010-09-01 18:59:26',NULL,'2010-09-01 18:59:39','2010-09-01 19:01:37',88,'false','javalkui',NULL,NULL,NULL,'N','2010-10-14 13:03:37'),
	('57195003424','hjhj','INET','INACTIVE',0,0,NULL,'2010-09-01 19:01:59',NULL,'2010-09-01 19:03:27','2010-09-01 19:04:14',89,'false','javalkui',NULL,NULL,NULL,'N','2010-10-14 13:03:37'),
	('59297348157','johnroom','INET','INACTIVE',0,0,NULL,'2010-09-01 19:05:29',NULL,'2010-09-01 19:06:52','2010-09-01 19:07:27',90,'false','rails123',NULL,NULL,NULL,'N','2010-10-14 13:03:37'),
	('70563263366','johnroom','INET','INACTIVE',0,0,NULL,'2010-09-01 19:24:16',NULL,NULL,NULL,91,'false','rails123',NULL,NULL,NULL,'N','2010-10-14 13:03:37'),
	('70592027179','johnroom','INET','INACTIVE',0,0,NULL,'2010-09-01 19:24:19',NULL,NULL,NULL,92,'false','rails123',NULL,NULL,NULL,'N','2010-10-14 13:03:37'),
	('70621327874','johnroom','INET','INACTIVE',0,0,NULL,'2010-09-01 19:24:22',NULL,'2010-09-01 19:24:31','2010-09-01 19:24:36',93,'false','rails1238',NULL,NULL,NULL,'N','2010-10-14 13:03:37'),
	('61749982957','test','INET','INACTIVE',0,0,NULL,'2010-09-01 21:56:14',NULL,NULL,NULL,94,'false','math101',NULL,NULL,NULL,'N','2010-10-14 13:03:37'),
	('23149682961','gjk','INET','INACTIVE',0,0,NULL,'2010-09-05 10:58:34',NULL,NULL,NULL,95,'false','math101','null',NULL,NULL,'N','2010-10-14 13:03:37'),
	('85504394725','jhgh','INET','INACTIVE',0,0,NULL,'2010-09-06 22:02:30',NULL,NULL,NULL,96,'false','hgjgjh','null',NULL,NULL,'N','2010-10-14 13:03:37'),
	('880548822','mmbnm','INET','INACTIVE',0,0,NULL,'2010-09-06 22:06:45',NULL,NULL,NULL,97,'false','mnbmnbmn','1234123',NULL,NULL,'N','2010-10-14 13:03:37'),
	('12950368633','jhgh','INET','INACTIVE',0,0,NULL,'2010-09-06 22:48:15',NULL,NULL,NULL,98,'false','hgjgjh','null',NULL,NULL,'N','2010-10-14 13:03:37'),
	('1372017750','jhgh','INET','INACTIVE',0,0,NULL,'2010-09-06 22:49:32',NULL,NULL,NULL,99,'false','hgjgjh','null',NULL,NULL,'N','2010-10-14 13:03:37'),
	('room3','jhgh','INET','INACTIVE',0,0,NULL,'2010-09-06 22:51:51',NULL,'2010-10-05 23:02:51','2010-10-05 23:03:02',100,'false','hgjgjh','null','INACTIVE',NULL,'N','2010-10-14 13:03:37'),
	('room2','jhgh','INET','INACTIVE',0,0,NULL,'2010-09-06 22:54:02',NULL,'2010-11-05 01:31:26','2010-11-05 01:31:35',101,'false','hgjgjh','1234123','INACTIVE',NULL,'N','2010-10-14 13:03:37'),
	('room1','gfdgfdgdgd','INET','INACTIVE',0,0,NULL,'2010-09-06 23:01:32',NULL,'2010-11-16 18:29:16','2010-11-16 18:30:40',102,'false','fgfgfgfg','1234123','INACTIVE',NULL,'N','2010-10-14 13:03:37'),
	('56758017898','rew','INET','INACTIVE',0,0,NULL,'2010-09-27 00:34:35',NULL,NULL,NULL,103,'false','math987','1234123',NULL,NULL,'N','2010-10-14 13:03:37'),
	('56797165615','rew','INET','INACTIVE',0,0,NULL,'2010-09-27 00:34:39',NULL,NULL,NULL,104,'false','math987','1234123',NULL,NULL,'N','2010-10-14 13:03:37'),
	('56833086746','rew','INET','INACTIVE',0,0,NULL,'2010-09-27 00:34:43',NULL,NULL,NULL,105,'false','math9877','1234123',NULL,NULL,'N','2010-10-14 13:03:37'),
	('57511182041','rew','INET','INACTIVE',0,0,NULL,'2010-09-27 00:35:51',NULL,NULL,NULL,106,'false','math98779','1234123',NULL,NULL,'N','2010-10-14 13:03:37'),
	('58765987826','rew','INET','INACTIVE',0,0,NULL,'2010-09-27 00:37:56',NULL,NULL,NULL,107,'false','math98779','1234123',NULL,NULL,'N','2010-10-14 13:03:37'),
	('66322232437','nbbb','INET','INACTIVE',0,0,NULL,'2010-09-27 00:50:32',NULL,NULL,NULL,108,'false','87665','1234123',NULL,NULL,'N','2010-10-14 13:03:37'),
	('66338493648','nbbb','INET','INACTIVE',0,0,NULL,'2010-09-27 00:50:33',NULL,NULL,NULL,109,'false','87665','1234123',NULL,NULL,'N','2010-10-14 13:03:37'),
	('66374836361','nbbb','INET','INACTIVE',0,0,NULL,'2010-09-27 00:50:37',NULL,'2010-09-27 00:50:58','2010-09-27 00:51:34',110,'false','87665','1234123','INACTIVE',NULL,'N','2010-10-14 13:03:37'),
	('18274705793','kjhk','INET','INACTIVE',0,0,NULL,'2010-09-27 13:23:47',NULL,'2010-09-27 13:24:39','2010-09-27 13:27:51',111,'false','jhk','1234123','INACTIVE',NULL,'Y','2010-10-14 13:22:40'),
	('2075798756','kjhk','INET','INACTIVE',0,0,NULL,'2010-09-27 13:27:55',NULL,'2010-09-27 13:40:39','2010-09-27 13:40:49',112,'false','jhk','1234123','INACTIVE',NULL,'Y','2010-10-14 13:22:40'),
	('28254314747','kjhk','INET','INACTIVE',0,0,NULL,'2010-09-27 13:40:25',NULL,NULL,NULL,113,'false','jhk','1234123',NULL,NULL,'Y','2010-10-14 13:22:40'),
	('28523333643','kjhk','INET','INACTIVE',0,0,NULL,'2010-09-27 13:40:52',NULL,'2010-09-27 13:40:59','2010-09-27 13:41:06',114,'false','jhk','1234123','INACTIVE',NULL,'Y','2010-10-14 13:22:40'),
	('28737531286','kjhk','INET','INACTIVE',0,0,NULL,'2010-09-27 13:41:13',NULL,'2010-09-27 13:41:19','2010-09-27 13:41:24',115,'false','jhk','1234123','INACTIVE',NULL,'Y','2010-10-14 13:22:40'),
	('28927076963','kjhk','INET','INACTIVE',0,0,NULL,'2010-09-27 13:41:32',NULL,'2010-09-27 13:41:40','2010-09-27 13:41:45',116,'false','jhk','1234123','INACTIVE',NULL,'Y','2010-10-14 13:22:40'),
	('29081697176','kjhk','INET','INACTIVE',0,0,NULL,'2010-09-27 13:41:48',NULL,'2010-09-27 13:41:58','2010-09-27 13:42:15',117,'false','jhk','1234123','INACTIVE',NULL,'Y','2010-10-14 13:22:40'),
	('3147961345','hgfhf','INET','INACTIVE',0,0,NULL,'2010-09-27 13:45:47',NULL,'2010-09-27 13:45:56','2010-09-27 13:46:26',118,'false','hgfhgf','1234123','INACTIVE',NULL,'N','2010-10-14 13:03:37'),
	('47400508553','jghjg','INET','INACTIVE',0,0,NULL,'2010-09-27 14:12:20',NULL,'2010-09-27 14:13:16','2010-09-27 14:13:24',119,'false','jhgjgh','1234123','INACTIVE',NULL,'N','2010-10-14 13:03:37'),
	('88899288265','lkj','INET','INACTIVE',0,0,NULL,'2010-09-27 15:21:30',NULL,'2010-09-27 15:21:41','2010-09-27 15:21:55',120,'false','lkj','1234123','INACTIVE',NULL,'N','2010-10-14 13:03:37'),
	('49919042414','jkh','INET','INACTIVE',0,0,NULL,'2010-10-04 15:43:11',NULL,'2010-10-04 15:47:50','2010-10-04 15:49:02',121,'false','kjhk','1234123','INACTIVE',NULL,'N','2010-10-14 13:03:37'),
	('67561248369','sd','INET','INACTIVE',0,0,NULL,'2010-10-04 16:12:36',NULL,'2010-10-04 16:12:43','2010-10-04 16:12:48',122,'false','sdfsdf','1234123','INACTIVE',NULL,'N','2010-10-14 13:03:37'),
	('43403835639','math101','INET','INACTIVE',0,0,NULL,'2010-10-04 23:52:20',NULL,NULL,NULL,123,'false','1234','1234123',NULL,NULL,'N','2010-10-14 13:03:37'),
	('43564154772','math101','INET','INACTIVE',0,0,NULL,'2010-10-04 23:52:36',NULL,NULL,NULL,124,'false','1234','1234123',NULL,NULL,'N','2010-10-14 13:03:37'),
	('43599548701','math101','INET','INACTIVE',0,0,NULL,'2010-10-04 23:52:39',NULL,'2010-10-04 23:53:03','2010-10-04 23:53:03',125,'false','1234','1234123','INACTIVE',NULL,'N','2010-10-14 13:03:37'),
	('44453294631','math101','INET','INACTIVE',0,0,NULL,'2010-10-04 23:54:05',NULL,NULL,NULL,126,'false','1234','1234123',NULL,NULL,'N','2010-10-14 13:03:37'),
	('44531645612','math101','INET','INACTIVE',0,0,NULL,'2010-10-04 23:54:13',NULL,'2010-10-04 23:54:18','2010-10-05 00:07:15',127,'false','12345','1234123','INACTIVE',NULL,'N','2010-10-14 13:03:37'),
	('91121453423','hkjk','INET','INACTIVE',0,0,NULL,'2010-10-05 01:11:52',NULL,'2010-10-05 01:12:16','2010-10-05 01:13:50',128,'false','kjhkj','1234123','INACTIVE',NULL,'N','2010-10-14 13:03:37'),
	('94399041243','hkjk','INET','INACTIVE',0,0,NULL,'2010-10-05 01:17:19',NULL,'2010-10-05 01:24:31','2010-10-05 01:26:40',129,'false','kjhkj','1234123','INACTIVE',NULL,'N','2010-10-14 13:03:37'),
	('52909935237','myroom','INET','INACTIVE',0,0,NULL,'2010-10-05 11:14:50',NULL,'2010-10-05 11:14:58','2010-10-05 11:15:03',130,'false','math101','1234123','INACTIVE',NULL,'N','2010-10-14 13:03:37'),
	('07668034020','mnb','INET','INACTIVE',0,0,NULL,'2010-10-05 21:06:06',NULL,'2010-10-05 21:06:14','2010-10-05 21:18:47',131,'false','nbmbmn','1234123','INACTIVE',NULL,'N','2010-10-14 13:03:37'),
	('37450064393','mnb','INET','INACTIVE',0,0,NULL,'2010-10-05 21:55:45',NULL,NULL,NULL,132,'false',NULL,'1234123',NULL,NULL,'N','2010-10-14 13:03:37'),
	('37507301024','mnb','INET','INACTIVE',0,0,NULL,'2010-10-05 21:55:50',NULL,'2010-10-05 21:56:06','2010-10-05 21:56:10',133,'false',NULL,'1234123','INACTIVE',NULL,'N','2010-10-14 13:03:37'),
	('39847594545','kjh','INET','INACTIVE',0,0,NULL,'2010-10-05 21:59:44',NULL,'2010-10-05 22:00:07','2010-10-05 22:00:15',134,'false',NULL,'1234123','INACTIVE',NULL,'N','2010-10-14 13:03:37'),
	('76146942415','kjh','INET','INACTIVE',0,0,NULL,'2010-10-05 23:00:14',NULL,'2010-10-05 23:00:24','2010-10-05 23:00:52',135,'false','khjkhj','1234123','INACTIVE',NULL,'N','2010-10-14 13:03:37'),
	('82219844582','kjh','INET','INACTIVE',0,0,NULL,'2010-10-05 23:10:21',NULL,'2010-10-05 23:10:51','2010-10-05 23:10:56',136,'false','khjkhj','1234123','INACTIVE',NULL,'N','2010-10-14 13:03:37'),
	('13407977978','room1nam','INET','ACTIVE',0,0,NULL,'2010-10-06 00:02:20',NULL,'2010-10-06 00:02:32',NULL,137,'true','math','1234123',NULL,NULL,'N','2010-10-14 13:03:37'),
	('21843281683','johnuser','INET','ACTIVE',0,0,NULL,'2010-10-06 00:16:24',NULL,'2010-10-06 00:16:42','2010-10-06 00:16:54',138,'false','mymath1','1234123','INACTIVE',NULL,'N','2010-10-14 13:03:37'),
	('2285492344','johnuser','INET','ACTIVE',0,0,NULL,'2010-10-06 00:18:05',NULL,'2010-10-06 00:18:12','2010-10-06 00:18:27',139,'false','mymath12','1234123','INACTIVE',NULL,'N','2010-10-14 13:03:37'),
	('2353987649','johnuser','INET','INACTIVE',0,0,NULL,'2010-10-06 00:19:13',NULL,'2010-10-06 00:19:27','2010-10-06 00:19:37',140,'true','mymath123','1234123','INACTIVE',NULL,'N','2010-10-14 13:03:37'),
	('26101806647','johnuser','INET','INACTIVE',0,0,NULL,'2010-10-06 00:23:30',NULL,NULL,NULL,141,'true','mymath123','1234123',NULL,NULL,'N','2010-10-14 13:03:37'),
	('26248316593','johnuser','INET','INACTIVE',0,0,NULL,'2010-10-06 00:23:44',NULL,NULL,NULL,142,'true','mymath12389','1234123',NULL,NULL,'N','2010-10-14 13:03:37'),
	('264886344','johnuser','INET','INACTIVE',0,0,NULL,'2010-10-06 00:24:08',NULL,NULL,NULL,143,'false','mymath123899','1234123',NULL,NULL,'N','2010-10-14 13:03:37'),
	('48968435479','nb','INET','INACTIVE',0,0,NULL,'2010-10-11 14:21:36',NULL,NULL,NULL,144,'false','nb','1234123',NULL,NULL,'Y','2010-10-14 17:44:54'),
	('54378758668','nb','INET','INACTIVE',0,0,NULL,'2010-10-11 14:30:37',NULL,NULL,NULL,145,'false','nb','1234123',NULL,NULL,'Y','2010-10-14 17:44:54'),
	('70053438648','nb','INET','INACTIVE',0,0,NULL,'2010-10-11 14:56:45',NULL,NULL,NULL,146,'false','nb','1234123',NULL,NULL,'Y','2010-10-14 17:44:54'),
	('71770213081','nb','INET','INACTIVE',0,0,NULL,'2010-10-11 14:59:37',NULL,NULL,NULL,147,'false','nb','1234123',NULL,NULL,'Y','2010-10-14 17:44:54'),
	('73668284845','nb','INET','INACTIVE',0,0,NULL,'2010-10-11 15:02:46',NULL,NULL,NULL,148,'false','nb','1234123',NULL,NULL,'Y','2010-10-14 17:44:54'),
	('79587862804','nb','INET','INACTIVE',0,0,NULL,'2010-10-11 15:12:38',NULL,NULL,NULL,149,'false','nb','1234123',NULL,NULL,'Y','2010-10-14 17:44:54'),
	('82773398673','nb','INET','INACTIVE',0,0,NULL,'2010-10-11 15:17:57',NULL,NULL,NULL,150,'false','nb','1234123',NULL,NULL,'Y','2010-10-14 17:44:54'),
	('08567291878','nb','INET','INACTIVE',0,0,NULL,'2010-10-11 16:00:56',NULL,NULL,NULL,151,'false','nb','1234123',NULL,NULL,'Y','2010-10-14 17:44:54'),
	('09985072690','nb','INET','INACTIVE',0,0,NULL,'2010-10-11 16:03:18',NULL,NULL,NULL,152,'false','nb','1234123',NULL,NULL,'Y','2010-10-14 17:44:54'),
	('1003282387','nb','INET','INACTIVE',0,0,NULL,'2010-10-11 16:03:23',NULL,NULL,NULL,153,'false','nb','1234123',NULL,NULL,'Y','2010-10-14 17:44:54'),
	('09191478329','room1','INET','INACTIVE',0,0,NULL,'2010-10-12 00:21:59',NULL,NULL,NULL,154,'false','rooma','1234123',NULL,NULL,'N','2010-10-14 13:03:37'),
	('34911711868','zx','INET','INACTIVE',0,0,NULL,'2010-10-12 01:04:51',NULL,NULL,NULL,155,'false','zx','1234123',NULL,NULL,'N','2010-10-14 13:03:37'),
	('70695777985','room1','INET','INACTIVE',0,0,NULL,'2010-10-15 13:24:29',NULL,'2010-10-15 13:24:44','2010-10-15 13:52:12',156,'false','room2','1234123','INACTIVE',NULL,'N',NULL),
	('2379531602','room1','INET','INACTIVE',0,0,NULL,'2010-10-15 14:52:59',NULL,NULL,NULL,157,'false','iuii','1234123',NULL,NULL,'N',NULL),
	('24951691982','room1','INET','INACTIVE',0,0,NULL,'2010-10-15 14:54:55',NULL,NULL,NULL,158,'false','iuii','1234123',NULL,NULL,'N',NULL),
	('26279374269','room1','INET','INACTIVE',0,0,NULL,'2010-10-15 14:57:08',NULL,NULL,NULL,159,'false','iuii','1234123',NULL,NULL,'N',NULL),
	('29527676018','room1','INET','INACTIVE',0,0,NULL,'2010-10-15 15:02:32',NULL,NULL,NULL,160,'false','iuii','1234123',NULL,NULL,'N',NULL),
	('30941085229','room1','INET','INACTIVE',0,0,NULL,'2010-10-15 15:04:54',NULL,NULL,NULL,161,'false','iuii','1234123',NULL,NULL,'N',NULL),
	('32109426149','room1','INET','INACTIVE',0,0,NULL,'2010-10-15 15:06:50',NULL,NULL,NULL,162,'false','iuii','1234123',NULL,NULL,'N',NULL),
	('49681315643','room1','INET','INACTIVE',0,0,NULL,'2010-10-15 15:36:08',NULL,NULL,NULL,163,'false','iuii','1234123',NULL,NULL,'N',NULL),
	('52788906713','room1','INET','INACTIVE',0,0,NULL,'2010-10-15 15:41:18',NULL,NULL,NULL,164,'false','iuii','1234123',NULL,NULL,'N',NULL),
	('54072897695','room1','INET','INACTIVE',0,0,NULL,'2010-10-15 15:43:27',NULL,NULL,NULL,165,'false','iuii','1234123',NULL,NULL,'N',NULL),
	('76742644541','room1','INET','INACTIVE',0,0,NULL,'2010-10-15 16:21:14',NULL,NULL,NULL,166,'false','iuii','1234123',NULL,NULL,'N',NULL),
	('7906336945','room1','INET','INACTIVE',0,0,NULL,'2010-10-15 16:25:06',NULL,NULL,NULL,167,'false','iuii','1234123',NULL,NULL,'N',NULL),
	('9392146603','room1','INET','INACTIVE',0,0,NULL,'2010-10-15 16:49:52',NULL,NULL,NULL,168,'false','iuii','1234123',NULL,NULL,'N',NULL),
	('94831204969','room1','INET','INACTIVE',0,0,NULL,'2010-10-15 16:51:23',NULL,NULL,NULL,169,'false','iuii','1234123',NULL,NULL,'N',NULL),
	('0316118204','room1','INET','INACTIVE',0,0,NULL,'2010-10-15 17:05:16',NULL,NULL,NULL,170,'false','iuii','1234123',NULL,NULL,'N',NULL),
	('14998271259','room1','INET','INACTIVE',0,0,NULL,'2010-10-15 17:24:59',NULL,NULL,NULL,171,'false','iuii','1234123',NULL,NULL,'N',NULL),
	('84630492597','room1','INET','INACTIVE',0,0,NULL,'2010-10-16 17:34:23',NULL,NULL,NULL,172,'false','iuii','1234123',NULL,NULL,'N',NULL),
	('85227456805','room1','INET','INACTIVE',0,0,NULL,'2010-10-16 17:35:22',NULL,NULL,NULL,173,'false','iuii','1234123',NULL,NULL,'N',NULL),
	('852668046','room1','INET','INACTIVE',0,0,NULL,'2010-10-16 17:35:26',NULL,NULL,NULL,174,'false','iuii','1234123',NULL,NULL,'N',NULL),
	('87851542263','room1','INET','INACTIVE',0,0,NULL,'2010-10-16 17:39:45',NULL,NULL,NULL,175,'false','iuii','1234123',NULL,NULL,'N',NULL),
	('88474117988','room1','INET','INACTIVE',0,0,NULL,'2010-10-16 17:40:47',NULL,NULL,NULL,176,'false','iuii','1234123',NULL,NULL,'N',NULL),
	('89747573170','room1','INET','INACTIVE',0,0,NULL,'2010-10-16 17:42:54',NULL,NULL,NULL,177,'false','iuii','1234123',NULL,NULL,'N',NULL),
	('91568892404','room1','INET','INACTIVE',0,0,NULL,'2010-10-16 17:45:56',NULL,NULL,NULL,178,'false','iuii','1234123',NULL,NULL,'N',NULL),
	('92618707554','room1','INET','INACTIVE',0,0,NULL,'2010-10-16 17:47:41',NULL,NULL,NULL,179,'false','iuii','1234123',NULL,NULL,'N',NULL),
	('19499095697','room1','INET','INACTIVE',0,0,NULL,'2010-10-17 16:45:49',NULL,NULL,NULL,180,'false','iuii','1234123',NULL,NULL,'N',NULL),
	('22523496902','mm','INET','INACTIVE',0,0,NULL,'2010-10-17 16:50:52',NULL,'2010-10-17 16:54:36','2010-10-17 16:56:47',181,'false','mm','1234123','INACTIVE',NULL,'N',NULL),
	('83467616883','room1','INET','INACTIVE',0,0,NULL,'2010-10-29 19:25:46',NULL,NULL,NULL,182,'false','iuii','1234123',NULL,NULL,'N',NULL),
	('2750384635','room1','INET','INACTIVE',0,0,NULL,'2010-10-30 10:32:30',NULL,NULL,NULL,183,'false','iuii','1234123',NULL,NULL,'N',NULL),
	('58970122580','myjihmroom','INET','INACTIVE',0,0,NULL,'2010-10-30 11:24:57',NULL,NULL,NULL,184,'false','12','1234123',NULL,NULL,'N',NULL),
	('5904093520','myjihmroom','INET','INACTIVE',0,0,NULL,'2010-10-30 11:25:04',NULL,NULL,NULL,185,'false','12','1234123',NULL,NULL,'N',NULL),
	('59052467034','myjihmroom','INET','INACTIVE',0,0,NULL,'2010-10-30 11:25:05',NULL,NULL,NULL,186,'false','12','1234123',NULL,NULL,'N',NULL),
	('59127885223','myjihmroom','INET','INACTIVE',0,0,NULL,'2010-10-30 11:25:12',NULL,NULL,NULL,187,'false','12','1234123',NULL,NULL,'N',NULL),
	('59138797765','myjihmroom','INET','INACTIVE',0,0,NULL,'2010-10-30 11:25:13',NULL,NULL,NULL,188,'false','12','1234123',NULL,NULL,'N',NULL),
	('35311092471','room1','INET','INACTIVE',0,0,NULL,'2010-10-31 20:05:31',NULL,NULL,NULL,189,'false','iuii','1234123',NULL,NULL,'N',NULL),
	('40073544648','room1','INET','INACTIVE',0,0,NULL,'2010-10-31 20:13:27',NULL,NULL,NULL,190,'false','iuii','1234123',NULL,NULL,'N',NULL),
	('40253283650','room1','INET','INACTIVE',0,0,NULL,'2010-10-31 20:13:45',NULL,NULL,NULL,191,'false','iuii','1234123',NULL,NULL,'N',NULL),
	('40433377486','room1','INET','INACTIVE',0,0,NULL,'2010-10-31 20:14:03',NULL,NULL,NULL,192,'false','iuii','1234123',NULL,NULL,'N',NULL),
	('41549046638','room1','INET','INACTIVE',0,0,NULL,'2010-10-31 20:15:54',NULL,NULL,NULL,193,'false','iuii','1234123',NULL,NULL,'N',NULL),
	('42314716992','room1','INET','INACTIVE',0,0,NULL,'2010-10-31 20:17:11',NULL,NULL,NULL,194,'false','iuii','1234123',NULL,NULL,'N',NULL),
	('43828205233','room1','INET','INACTIVE',0,0,NULL,'2010-10-31 20:19:42',NULL,NULL,NULL,195,'false','iuii','1234123',NULL,NULL,'N',NULL),
	('43893497667','room1','INET','INACTIVE',0,0,NULL,'2010-10-31 20:19:49',NULL,NULL,NULL,196,'false','iuii','1234123',NULL,NULL,'N',NULL),
	('44411174994','room1','INET','INACTIVE',0,0,NULL,'2010-10-31 20:20:41',NULL,NULL,NULL,197,'false','iuii','1234123',NULL,NULL,'N',NULL),
	('46721744648','room1','INET','INACTIVE',0,0,NULL,'2010-10-31 20:24:32',NULL,NULL,NULL,198,'false','iuii','1234123',NULL,NULL,'N',NULL),
	('46836721051','room1','INET','INACTIVE',0,0,NULL,'2010-10-31 20:24:43',NULL,NULL,NULL,199,'false','iuii','1234123',NULL,NULL,'N',NULL),
	('48249146896','room1','INET','INACTIVE',0,0,NULL,'2010-10-31 20:27:04',NULL,NULL,NULL,200,'false','iuii','1234123',NULL,NULL,'N',NULL),
	('48273928318','room1','INET','INACTIVE',0,0,NULL,'2010-10-31 20:27:07',NULL,NULL,NULL,201,'false','iuii','1234123',NULL,NULL,'N',NULL),
	('0319003337','room1','INET','INACTIVE',0,0,NULL,'2010-10-31 21:58:39',NULL,NULL,NULL,202,'false','12','4',NULL,NULL,'N',NULL),
	('06598495278','room1','INET','INACTIVE',0,0,NULL,'2010-10-31 22:04:19',NULL,NULL,NULL,203,'false','12','4',NULL,NULL,'N',NULL),
	('20530065493','room1','INET','INACTIVE',0,0,NULL,'2010-10-31 22:27:33',NULL,NULL,NULL,204,'false','iuii','1234123',NULL,NULL,'N',NULL),
	('27596623502','j','INET','INACTIVE',0,0,NULL,'2010-10-31 22:39:19',NULL,NULL,NULL,205,'false','12','1234123',NULL,NULL,'N',NULL),
	('35786165932','room1','INET','INACTIVE',0,0,NULL,'2010-10-31 22:52:58',NULL,NULL,NULL,206,'false','12','4',NULL,NULL,'N',NULL),
	('36911873796','room1','INET','INACTIVE',0,0,NULL,'2010-10-31 22:54:51',NULL,NULL,NULL,207,'false','iuii','1234123',NULL,NULL,'N',NULL),
	('37062918673','room1','INET','INACTIVE',0,0,NULL,'2010-10-31 22:55:06',NULL,NULL,NULL,208,'false','12','4',NULL,NULL,'N',NULL),
	('37189535137','room1','INET','INACTIVE',0,0,NULL,'2010-10-31 22:55:18',NULL,NULL,NULL,209,'false','12','4',NULL,NULL,'N',NULL),
	('37254438651','j','INET','INACTIVE',0,0,NULL,'2010-10-31 22:55:25',NULL,'2010-11-01 18:10:29','2010-11-01 18:38:29',210,'false','12','1234123','INACTIVE',NULL,'N',NULL),
	('53166177845','room1','INET','INACTIVE',0,0,NULL,'2010-10-31 23:21:56',NULL,NULL,NULL,211,'false','iuii','1234123',NULL,NULL,'N',NULL),
	('53682461012','room1','INET','INACTIVE',0,0,NULL,'2010-10-31 23:22:48',NULL,NULL,NULL,212,'false','12','4',NULL,NULL,'N',NULL),
	('65452301739','room1','INET','INACTIVE',0,0,NULL,'2010-10-31 23:42:25',NULL,NULL,NULL,213,'false','12','4',NULL,NULL,'N',NULL),
	('97299597164','room1','INET','INACTIVE',0,0,NULL,'2010-11-01 14:28:49',NULL,'2010-11-01 15:01:38','2010-11-01 15:02:23',214,'false','12','4','INACTIVE',NULL,'N',NULL),
	('17796426518','kk','INET','INACTIVE',0,0,NULL,'2010-11-01 15:02:59',NULL,NULL,NULL,215,'false','kk','1234123',NULL,NULL,'N',NULL),
	('17900977136','room1','INET','ACTIVE',0,0,NULL,'2010-11-01 15:03:10',NULL,'2010-11-01 15:26:52','2010-11-01 15:26:31',216,'false','12','4','INACTIVE',NULL,'N',NULL),
	('21114706858','room1','INET','INACTIVE',0,0,NULL,'2010-11-01 15:08:31',NULL,NULL,NULL,217,'false','12','4',NULL,NULL,'N',NULL),
	('21417087510','kk','INET','INACTIVE',0,0,NULL,'2010-11-01 15:09:01',NULL,'2010-11-16 22:27:23','2010-11-16 23:27:49',218,'false','12','1234123','INACTIVE',NULL,'N',NULL),
	('31068471629','room1','INET','INACTIVE',0,0,NULL,'2010-11-01 15:25:06',NULL,NULL,NULL,219,'false','12','4',NULL,NULL,'N',NULL),
	('34362058715','room1','INET','INACTIVE',0,0,NULL,'2010-11-01 15:30:36',NULL,NULL,NULL,220,'false','12','4',NULL,NULL,'N',NULL),
	('37483086186','room1','INET','INACTIVE',0,0,NULL,'2010-11-01 15:35:48',NULL,NULL,NULL,221,'false','12','4',NULL,NULL,'N',NULL),
	('39000365978','room1','INET','INACTIVE',0,0,NULL,'2010-11-01 15:38:20',NULL,NULL,NULL,222,'false','12','4',NULL,NULL,'N',NULL),
	('41703776504','room1','INET','INACTIVE',0,0,NULL,'2010-11-01 15:42:50',NULL,NULL,NULL,223,'false','12','4',NULL,NULL,'N',NULL),
	('42412518061','room1','INET','INACTIVE',0,0,NULL,'2010-11-01 15:44:01',NULL,NULL,NULL,224,'false','iuii','1234123',NULL,NULL,'N',NULL),
	('60625955025','room1','INET','INACTIVE',0,0,NULL,'2010-11-01 16:14:22',NULL,NULL,NULL,225,'false','12','4',NULL,NULL,'N',NULL),
	('62031217343','room1','INET','INACTIVE',0,0,NULL,'2010-11-01 16:16:43',NULL,NULL,NULL,226,'false','12','4',NULL,NULL,'N',NULL),
	('71173084945','room1','INET','INACTIVE',0,0,NULL,'2010-11-01 16:31:57',NULL,NULL,NULL,227,'false','12','4',NULL,NULL,'N',NULL),
	('29478704672','room1','INET','INACTIVE',0,0,NULL,'2010-11-01 18:09:07',NULL,NULL,NULL,228,'false','12','4',NULL,NULL,'N',NULL),
	('61603591772','room1','INET','INACTIVE',0,0,NULL,'2010-11-01 19:02:40',NULL,NULL,NULL,229,'false','12','4',NULL,NULL,'N',NULL),
	('62548983278','room1','INET','INACTIVE',0,0,NULL,'2010-11-01 19:04:14',NULL,NULL,NULL,230,'false','12','4',NULL,NULL,'N',NULL),
	('64132271410','room1','INET','INACTIVE',0,0,NULL,'2010-11-01 19:06:53',NULL,NULL,NULL,231,'false','12','4',NULL,NULL,'N',NULL),
	('83371063827','room1','INET','INACTIVE',0,0,NULL,'2010-11-01 19:38:57',NULL,NULL,NULL,232,'false','12','4',NULL,NULL,'N',NULL),
	('22613956768','room1','INET','INACTIVE',0,0,NULL,'2010-11-01 20:44:21',NULL,NULL,NULL,233,'false','12','4',NULL,NULL,'N',NULL),
	('96961071778','room1','INET','INACTIVE',0,0,NULL,'2010-11-03 13:41:36',NULL,NULL,NULL,234,'false','12','2',NULL,NULL,'N',NULL),
	('0875279569','room1','INET','INACTIVE',0,0,NULL,'2010-11-03 14:01:15',NULL,NULL,NULL,235,'false','14','2',NULL,NULL,'N',NULL),
	('2935910513','room1','INET','INACTIVE',0,0,NULL,'2010-11-03 14:35:35',NULL,NULL,NULL,236,'false','14','2',NULL,NULL,'N',NULL),
	('2953219529','room1','INET','INACTIVE',0,0,NULL,'2010-11-03 14:35:53',NULL,NULL,NULL,237,'false','14','2',NULL,NULL,'N',NULL),
	('35757624343','room1','INET','INACTIVE',0,0,NULL,'2010-11-03 14:46:15',NULL,NULL,NULL,238,'false','14','2',NULL,NULL,'N',NULL),
	('36411873146','room1','INET','INACTIVE',0,0,NULL,'2010-11-03 14:47:21',NULL,NULL,NULL,239,'false','14','2',NULL,NULL,'N',NULL),
	('6406786713','room1','INET','INACTIVE',0,0,NULL,'2010-11-03 15:33:26',NULL,NULL,NULL,240,'false','14','2',NULL,NULL,'N',NULL),
	('66778767988','room1','INET','INACTIVE',0,0,NULL,'2010-11-03 15:37:57',NULL,NULL,NULL,241,'false','14','2',NULL,NULL,'N',NULL),
	('69758294807','room1','INET','INACTIVE',0,0,NULL,'2010-11-03 15:42:55',NULL,NULL,NULL,242,'false','14','2',NULL,NULL,'N',NULL),
	('13869537582','room1','INET','INACTIVE',0,0,NULL,'2010-11-03 16:56:26',NULL,NULL,NULL,243,'false','14','2',NULL,NULL,'N',NULL),
	('21674491','room1','INET','INACTIVE',0,0,NULL,'2010-11-03 17:09:27',NULL,NULL,NULL,244,'false','14','2',NULL,NULL,'N',NULL),
	('49690464883','room1','INET','INACTIVE',0,0,NULL,'2010-11-03 17:56:09',NULL,NULL,NULL,245,'false','12','4',NULL,NULL,'N',NULL),
	('51383238745','room1','INET','INACTIVE',0,0,NULL,'2010-11-03 17:58:58',NULL,NULL,NULL,246,'false','iuii','1234123',NULL,NULL,'N',NULL),
	('57312443170','room1','INET','INACTIVE',0,0,NULL,'2010-11-03 18:08:51',NULL,NULL,NULL,247,'false','12','4',NULL,NULL,'N',NULL),
	('60441122104','room1','INET','INACTIVE',0,0,NULL,'2010-11-03 18:14:04',NULL,NULL,NULL,248,'false','12','4',NULL,NULL,'N',NULL),
	('60650906939','room1','INET','INACTIVE',0,0,NULL,'2010-11-03 18:14:25',NULL,NULL,NULL,249,'false','12','4',NULL,NULL,'N',NULL),
	('61939136336','room1','INET','INACTIVE',0,0,NULL,'2010-11-03 18:16:33',NULL,NULL,NULL,250,'false','12','4',NULL,NULL,'N',NULL),
	('6360640305','room1','INET','INACTIVE',0,0,NULL,'2010-11-03 18:19:20',NULL,NULL,NULL,251,'false','12','4',NULL,NULL,'N',NULL),
	('63744057714','room1','INET','INACTIVE',0,0,NULL,'2010-11-03 18:19:34',NULL,NULL,NULL,252,'false','14','2',NULL,NULL,'N',NULL),
	('84116931055','room1','INET','INACTIVE',0,0,NULL,'2010-11-03 21:40:11',NULL,NULL,NULL,253,'false','12','4',NULL,NULL,'N',NULL),
	('8671508429','room1','INET','INACTIVE',0,0,NULL,'2010-11-03 21:44:31',NULL,NULL,NULL,254,'false','12','4',NULL,NULL,'N',NULL),
	('91184071660','room1','INET','INACTIVE',0,0,NULL,'2010-11-03 21:51:58',NULL,NULL,NULL,255,'false','12','4',NULL,NULL,'N',NULL),
	('95815523656','room1','INET','INACTIVE',0,0,NULL,'2010-11-03 21:59:41',NULL,NULL,NULL,256,'false','12','4',NULL,NULL,'N',NULL),
	('96226727887','room1','INET','INACTIVE',0,0,NULL,'2010-11-04 11:53:42',NULL,NULL,NULL,257,'false','12','4',NULL,NULL,'N',NULL),
	('97188501102','room1','INET','INACTIVE',0,0,NULL,'2010-11-04 11:55:18',NULL,NULL,NULL,258,'false','12','4',NULL,NULL,'N',NULL),
	('30622837285','room1','INET','INACTIVE',0,0,NULL,'2010-11-04 12:51:02',NULL,NULL,NULL,259,'false','12','4',NULL,NULL,'N',NULL),
	('31246346425','room1','INET','INACTIVE',0,0,NULL,'2010-11-04 12:52:04',NULL,NULL,NULL,260,'false','12','4',NULL,NULL,'N',NULL),
	('34503657315','room1','INET','INACTIVE',0,0,NULL,'2010-11-04 12:57:30',NULL,NULL,NULL,261,'false','12','4',NULL,NULL,'N',NULL),
	('41281603673','room1','INET','INACTIVE',0,0,NULL,'2010-11-04 13:08:48',NULL,NULL,NULL,262,'false','12','4',NULL,NULL,'N',NULL),
	('42324925746','room1','INET','INACTIVE',0,0,NULL,'2010-11-04 13:10:32',NULL,NULL,NULL,263,'false','12','4',NULL,NULL,'N',NULL),
	('3905121403','room1','INET','INACTIVE',0,0,NULL,'2010-11-04 15:51:45',NULL,NULL,NULL,264,'false','12','4',NULL,NULL,'N',NULL),
	('39801157910','room1','INET','INACTIVE',0,0,NULL,'2010-11-04 15:53:00',NULL,NULL,NULL,265,'false','12','4',NULL,NULL,'N',NULL),
	('83387393277','room1','INET','INACTIVE',0,0,NULL,'2010-11-04 17:05:38',NULL,NULL,NULL,266,'false','12','4',NULL,NULL,'N',NULL),
	('91803956190','room1','INET','INACTIVE',0,0,NULL,'2010-11-04 17:19:40',NULL,NULL,NULL,267,'false','12','4',NULL,NULL,'N',NULL),
	('44869448363','room1','INET','INACTIVE',0,0,NULL,'2010-11-05 00:21:26',NULL,NULL,NULL,268,'false','12','4',NULL,NULL,'N',NULL),
	('86053833410','room1','INET','INACTIVE',0,0,NULL,'2010-11-05 01:30:05',NULL,NULL,NULL,269,'false','iuii','1234123',NULL,NULL,'N',NULL),
	('8617998308','room1','INET','INACTIVE',0,0,NULL,'2010-11-05 01:30:18',NULL,NULL,NULL,270,'false','12','4',NULL,NULL,'N',NULL),
	('75869344603','room1','INET','INACTIVE',0,0,NULL,'2010-11-06 16:06:26',NULL,NULL,NULL,271,'false','12','4',NULL,NULL,'N',NULL),
	('6642187917','room1','INET','INACTIVE',0,0,NULL,'2010-11-07 15:50:42',NULL,NULL,NULL,272,'false','12','4',NULL,NULL,'N',NULL),
	('80621522635','room1','INET','INACTIVE',0,0,NULL,'2010-11-07 16:14:22',NULL,NULL,NULL,273,'false','12','4',NULL,NULL,'N',NULL),
	('5644173474','room1','INET','INACTIVE',0,0,NULL,'2010-11-07 18:20:44',NULL,NULL,NULL,274,'false','12','4',NULL,NULL,'N',NULL),
	('0365831190','room1','INET','INACTIVE',0,0,NULL,'2010-11-08 21:39:25',NULL,NULL,NULL,275,'false','12','4',NULL,NULL,'N',NULL),
	('21499433280','room1','INET','INACTIVE',0,0,NULL,'2010-11-08 22:09:09',NULL,NULL,NULL,276,'false','12','4',NULL,NULL,'N',NULL),
	('26114016997','room1','INET','INACTIVE',0,0,NULL,'2010-11-08 22:16:51',NULL,NULL,NULL,277,'false','12','4',NULL,NULL,'N',NULL),
	('29353643869','room1','INET','INACTIVE',0,0,NULL,'2010-11-08 22:22:15',NULL,NULL,NULL,278,'false','12','4',NULL,NULL,'N',NULL),
	('34386265725','room1','INET','INACTIVE',0,0,NULL,'2010-11-08 22:30:38',NULL,NULL,NULL,279,'false','iuii','1234123',NULL,NULL,'N',NULL),
	('62152806445','room1','INET','INACTIVE',0,0,NULL,'2010-11-09 18:43:35',NULL,NULL,NULL,280,'false','12','4',NULL,NULL,'N',NULL),
	('6439458829','room1','INET','INACTIVE',0,0,NULL,'2010-11-09 18:47:19',NULL,NULL,NULL,281,'false','12','4',NULL,NULL,'N',NULL),
	('7128641215','room1','INET','INACTIVE',0,0,NULL,'2010-11-09 18:58:48',NULL,NULL,NULL,282,'false','12','4',NULL,NULL,'N',NULL),
	('88121553772','room1','INET','INACTIVE',0,0,NULL,'2010-11-09 19:26:52',NULL,NULL,NULL,283,'false','12','4',NULL,NULL,'N',NULL),
	('885306626','room1','INET','INACTIVE',0,0,NULL,'2010-11-09 19:27:33',NULL,NULL,NULL,284,'false','14','2',NULL,NULL,'N',NULL),
	('91108556910','room1','INET','INACTIVE',0,0,NULL,'2010-11-09 19:31:50',NULL,NULL,NULL,285,'false','14','2',NULL,NULL,'N',NULL),
	('91806481532','room1','INET','INACTIVE',0,0,NULL,'2010-11-09 19:33:00',NULL,NULL,NULL,286,'false','14','2',NULL,NULL,'N',NULL),
	('93652677792','room1','INET','INACTIVE',0,0,NULL,'2010-11-09 19:36:05',NULL,NULL,NULL,287,'false','14','2',NULL,NULL,'N',NULL),
	('94480574031','room1','INET','INACTIVE',0,0,NULL,'2010-11-09 19:37:28',NULL,NULL,NULL,288,'false','14','2',NULL,NULL,'N',NULL),
	('94834127875','room1','INET','INACTIVE',0,0,NULL,'2010-11-09 19:38:03',NULL,NULL,NULL,289,'false','14','2',NULL,NULL,'N',NULL),
	('97763292492','room1','INET','INACTIVE',0,0,NULL,'2010-11-09 19:42:56',NULL,NULL,NULL,290,'false','iuii','1234123',NULL,NULL,'N',NULL),
	('179682831','room1','INET','INACTIVE',0,0,NULL,'2010-11-09 20:16:36',NULL,NULL,NULL,291,'false','14','2',NULL,NULL,'N',NULL),
	('79630775947','room1','INET','INACTIVE',0,0,NULL,'2010-11-09 21:59:23',NULL,NULL,NULL,292,'false','12','4',NULL,NULL,'N',NULL),
	('17516031796','room1','INET','INACTIVE',0,0,NULL,'2010-11-09 23:02:31',NULL,NULL,NULL,293,'false','12','4',NULL,NULL,'N',NULL),
	('17621394847','room1','INET','INACTIVE',0,0,NULL,'2010-11-09 23:02:42',NULL,NULL,NULL,294,'false','12','4',NULL,NULL,'N',NULL),
	('18086648568','room1','INET','INACTIVE',0,0,NULL,'2010-11-09 23:03:28',NULL,NULL,NULL,295,'false','14','2',NULL,NULL,'N',NULL),
	('18820591878','room1','INET','INACTIVE',0,0,NULL,'2010-11-09 23:04:42',NULL,NULL,NULL,296,'false','14','2',NULL,NULL,'N',NULL),
	('62228804463','room1','INET','INACTIVE',0,0,NULL,'2010-11-10 00:17:02',NULL,NULL,NULL,297,'false','12','4',NULL,NULL,'N',NULL),
	('17733364416','room1','INET','INACTIVE',0,0,NULL,'2010-11-10 18:29:33',NULL,NULL,NULL,298,'false','12','4',NULL,NULL,'N',NULL),
	('19070545311','room1','INET','INACTIVE',0,0,NULL,'2010-11-10 18:31:47',NULL,NULL,NULL,299,'false','12','4',NULL,NULL,'N',NULL),
	('19662977094','room1','INET','INACTIVE',0,0,NULL,'2010-11-10 18:32:46',NULL,NULL,NULL,300,'false','14','2',NULL,NULL,'N',NULL),
	('22965015344','room1','INET','INACTIVE',0,0,NULL,'2010-11-10 18:38:16',NULL,NULL,NULL,301,'false','14','2',NULL,NULL,'N',NULL),
	('38759473808','room1','INET','INACTIVE',0,0,NULL,'2010-11-10 19:04:35',NULL,NULL,NULL,302,'false','14','2',NULL,NULL,'N',NULL),
	('19664786353','room1','INET','INACTIVE',0,0,NULL,'2010-11-10 21:19:26',NULL,NULL,NULL,303,'false','12','4',NULL,NULL,'N',NULL),
	('29591236055','room1','INET','INACTIVE',0,0,NULL,'2010-11-10 21:35:59',NULL,NULL,NULL,304,'false','12','4',NULL,NULL,'N',NULL),
	('29606094021','room1','INET','INACTIVE',0,0,NULL,'2010-11-10 21:36:00',NULL,NULL,NULL,305,'false','14','2',NULL,NULL,'N',NULL),
	('37908303031','room1','INET','INACTIVE',0,0,NULL,'2010-11-10 21:49:50',NULL,NULL,NULL,306,'false','iuii','1234123',NULL,NULL,'N',NULL),
	('79831662133','room1','INET','INACTIVE',0,0,NULL,'2010-11-10 22:59:43',NULL,NULL,NULL,307,'false','12','4',NULL,NULL,'N',NULL),
	('33779206429','room1','INET','INACTIVE',0,0,NULL,'2010-11-11 00:29:37',NULL,NULL,NULL,308,'false','12','4',NULL,NULL,'N',NULL),
	('73249411022','room1','INET','INACTIVE',0,0,NULL,'2010-11-11 01:35:24',NULL,NULL,NULL,309,'false','12','4',NULL,NULL,'N',NULL),
	('75531273104','room1','INET','INACTIVE',0,0,NULL,'2010-11-11 12:45:53',NULL,NULL,NULL,310,'false','iuii','1234123',NULL,NULL,'N',NULL),
	('95314134391','room1','INET','INACTIVE',0,0,NULL,'2010-11-11 13:18:51',NULL,NULL,NULL,311,'false','12','4',NULL,NULL,'N',NULL),
	('35420923576','room1','INET','INACTIVE',0,0,NULL,'2010-11-11 14:25:42',NULL,NULL,NULL,312,'false','12','4',NULL,NULL,'N',NULL),
	('76277086413','room1','INET','INACTIVE',0,0,NULL,'2010-11-11 15:33:47',NULL,NULL,NULL,313,'false','12','4',NULL,NULL,'N',NULL),
	('12429475122','room1','INET','INACTIVE',0,0,NULL,'2010-11-11 19:20:42',NULL,NULL,NULL,314,'false','12','4',NULL,NULL,'N',NULL),
	('66922888718','room1','INET','INACTIVE',0,0,NULL,'2010-11-11 20:51:32',NULL,NULL,NULL,315,'false','12','4',NULL,NULL,'N',NULL),
	('42713244677','room1','INET','INACTIVE',0,0,NULL,'2010-11-13 08:17:51',NULL,NULL,NULL,316,'false','iuii','1234123',NULL,NULL,'N',NULL),
	('42992985898','room1','INET','INACTIVE',0,0,NULL,'2010-11-13 08:18:19',NULL,NULL,NULL,317,'false','2','14',NULL,NULL,'N',NULL),
	('63665482103','room1','INET','INACTIVE',0,0,NULL,'2010-11-15 19:12:46',NULL,NULL,NULL,318,'false','12','4',NULL,NULL,'N',NULL),
	('63828672444','room1','INET','INACTIVE',0,0,NULL,'2010-11-15 19:13:02',NULL,NULL,NULL,319,'false','14','2',NULL,NULL,'N',NULL),
	('06488551897','room1','INET','INACTIVE',0,0,NULL,'2010-11-15 20:24:08',NULL,NULL,NULL,320,'false','12','4',NULL,NULL,'N',NULL),
	('77577242769','room1','INET','INACTIVE',0,0,NULL,'2010-11-15 22:22:37',NULL,NULL,NULL,321,'false','12','4',NULL,NULL,'N',NULL),
	('07107493255','room1','INET','INACTIVE',0,0,NULL,'2010-11-15 23:11:50',NULL,NULL,NULL,322,'false','14','2',NULL,NULL,'N',NULL),
	('90287264458','room1','INET','INACTIVE',0,0,NULL,'2010-11-16 18:10:28',NULL,NULL,NULL,323,'false','14','2',NULL,NULL,'N',NULL),
	('9226947569','room1','INET','INACTIVE',0,0,NULL,'2010-11-16 18:13:46',NULL,NULL,NULL,324,'false','12','4',NULL,NULL,'N',NULL),
	('02266108777','room1','INET','INACTIVE',0,0,NULL,'2010-11-16 18:30:26',NULL,'2010-11-16 18:34:21','2010-11-16 18:36:42',325,'false','14','2','INACTIVE',NULL,'N',NULL),
	('17180781092','room1','INET','INACTIVE',0,0,NULL,'2010-11-16 21:41:58',NULL,NULL,NULL,326,'false','12','4',NULL,NULL,'N',NULL),
	('96577971993','room1','INET','INACTIVE',0,0,NULL,'2010-11-16 23:54:17',NULL,NULL,NULL,327,'false','12','4',NULL,NULL,'N',NULL);

/*!40000 ALTER TABLE `room` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table user_media
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_media`;

CREATE TABLE `user_media` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(100) NOT NULL,
  `mediaURL` varchar(200) NOT NULL,
  `mediaDescription` varchar(200) DEFAULT NULL,
  `mediaFolder` varchar(5) DEFAULT 'false',
  `mediaFolderSeq` int(2) NOT NULL DEFAULT '0',
  `mediaGroup` int(10) DEFAULT NULL,
  `mediaName` varchar(200) DEFAULT NULL,
  `mediaType` int(1) DEFAULT '1',
  `insertedDate` datetime DEFAULT NULL,
  `media_type` varchar(50) DEFAULT NULL,
  `org_name` varchar(50) DEFAULT NULL,
  `lesson_plan` varchar(50) DEFAULT NULL,
  `course_id` varchar(50) DEFAULT NULL,
  `content_id` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=541 DEFAULT CHARSET=latin1;

LOCK TABLES `user_media` WRITE;
/*!40000 ALTER TABLE `user_media` DISABLE KEYS */;
INSERT INTO `user_media` (`id`,`userName`,`mediaURL`,`mediaDescription`,`mediaFolder`,`mediaFolderSeq`,`mediaGroup`,`mediaName`,`mediaType`,`insertedDate`,`media_type`,`org_name`,`lesson_plan`,`course_id`,`content_id`)
VALUES
	(535,'John','John__29.jpeg','sd',NULL,0,0,'29.jpeg',1,'2010-11-08 22:18:40',NULL,NULL,NULL,NULL,NULL),
	(536,'John','John__34.jpeg','sdsd',NULL,0,0,'34.jpeg',1,'2010-11-08 22:19:00',NULL,NULL,NULL,NULL,NULL),
	(537,'john','john__Screen5i7shot5i72010-11-155i7at5i78.01.295i7PM.png','sxc',NULL,0,0,'Screen5i7shot5i72010-11-155i7at5i78.01.295i7PM.png',1,'2010-11-15 20:12:19',NULL,NULL,NULL,NULL,NULL),
	(538,'john','john__Screen5i7shot5i72010-11-155i7at5i78.01.295i7PM.png','lnnmbmn',NULL,0,0,'Screen5i7shot5i72010-11-155i7at5i78.01.295i7PM.png',1,'2010-11-15 20:13:00',NULL,NULL,NULL,NULL,NULL),
	(539,'john','john__Screen5i7shot5i72010-11-155i7at5i78.13.335i7PM.png','lnnmbmn',NULL,0,0,'Screen5i7shot5i72010-11-155i7at5i78.13.335i7PM.png',1,'2010-11-15 20:13:51',NULL,NULL,NULL,NULL,NULL),
	(540,'john','john__Screen5i7shot5i72010-11-155i7at5i78.13.335i7PM.png','lnnmbmnsdd',NULL,0,0,'Screen5i7shot5i72010-11-155i7at5i78.13.335i7PM.png',1,'2010-11-15 20:14:39',NULL,NULL,NULL,NULL,NULL);

/*!40000 ALTER TABLE `user_media` ENABLE KEYS */;
UNLOCK TABLES;





/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
