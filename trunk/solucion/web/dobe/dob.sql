/*
SQLyog Community v11.26 (32 bit)
MySQL - 5.5.16 : Database - academico
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`academico` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `academico`;

/*Table structure for table `dobe_aprendizaje` */

DROP TABLE IF EXISTS `dobe_aprendizaje`;

CREATE TABLE `dobe_aprendizaje` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `dobe_curso` int(11) DEFAULT NULL,
  `activo` tinyint(1) DEFAULT '0',
  `reflexivo` tinyint(1) DEFAULT '0',
  `teorico` tinyint(1) DEFAULT '0',
  `pragmatico` tinyint(1) DEFAULT '0',
  `dobe_estudiante` int(11) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `fk_curso_aprendizaje` (`dobe_curso`),
  KEY `fk_estudiante_aprendizaje` (`dobe_estudiante`),
  CONSTRAINT `fk_curso_aprendizaje` FOREIGN KEY (`dobe_curso`) REFERENCES `dobe_cursos` (`codigo`),
  CONSTRAINT `fk_estudiante_aprendizaje` FOREIGN KEY (`dobe_estudiante`) REFERENCES `dobe_estudiantes` (`codigo`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=259 DEFAULT CHARSET=latin1;

/*Data for the table `dobe_aprendizaje` */

insert  into `dobe_aprendizaje`(`codigo`,`dobe_curso`,`activo`,`reflexivo`,`teorico`,`pragmatico`,`dobe_estudiante`) values (253,1,0,0,0,0,66),(254,2,0,0,0,0,66),(255,3,0,0,0,0,66),(256,4,0,0,0,0,66),(257,5,0,0,0,0,66),(258,6,0,0,0,0,66);

/*Table structure for table `dobe_caracteristicas` */

DROP TABLE IF EXISTS `dobe_caracteristicas`;

CREATE TABLE `dobe_caracteristicas` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `dobe_estado` int(11) DEFAULT NULL,
  `observacion` longtext,
  `dobe_estudiante` int(11) DEFAULT NULL,
  `estado` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`codigo`),
  KEY `fk_estudiante_carac` (`dobe_estudiante`),
  KEY `fk_estaduicar` (`dobe_estado`),
  CONSTRAINT `fk_estaduicar` FOREIGN KEY (`dobe_estado`) REFERENCES `dobe_estados` (`codigo`),
  CONSTRAINT `fk_estudiante_carac` FOREIGN KEY (`dobe_estudiante`) REFERENCES `dobe_estudiantes` (`codigo`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=latin1;

/*Data for the table `dobe_caracteristicas` */

insert  into `dobe_caracteristicas`(`codigo`,`dobe_estado`,`observacion`,`dobe_estudiante`,`estado`) values (79,36,'ASD',66,0),(80,37,'ASD',66,1);

/*Table structure for table `dobe_club` */

DROP TABLE IF EXISTS `dobe_club`;

CREATE TABLE `dobe_club` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `dobe_curso` int(11) DEFAULT NULL,
  `club` varchar(100) DEFAULT NULL,
  `opcional` varchar(100) DEFAULT NULL,
  `calificacion` varchar(50) DEFAULT NULL,
  `dobe_grado` int(11) DEFAULT NULL,
  `dobe_estudiante` int(11) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `fk_curso_club` (`dobe_curso`),
  KEY `fk_estado_club` (`dobe_grado`),
  KEY `fk_estudi_club` (`dobe_estudiante`),
  CONSTRAINT `fk_curso_club` FOREIGN KEY (`dobe_curso`) REFERENCES `dobe_cursos` (`codigo`),
  CONSTRAINT `fk_estado_club` FOREIGN KEY (`dobe_grado`) REFERENCES `dobe_estados` (`codigo`),
  CONSTRAINT `fk_estudi_club` FOREIGN KEY (`dobe_estudiante`) REFERENCES `dobe_estudiantes` (`codigo`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=259 DEFAULT CHARSET=latin1;

/*Data for the table `dobe_club` */

insert  into `dobe_club`(`codigo`,`dobe_curso`,`club`,`opcional`,`calificacion`,`dobe_grado`,`dobe_estudiante`) values (253,1,'','','',31,66),(254,2,'','','',31,66),(255,3,'','','',31,66),(256,4,'','','',31,66),(257,5,'','','',31,66),(258,6,'','','',31,66);

/*Table structure for table `dobe_cursos` */

DROP TABLE IF EXISTS `dobe_cursos`;

CREATE TABLE `dobe_cursos` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) DEFAULT NULL,
  `secuencia` int(11) DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/*Data for the table `dobe_cursos` */

insert  into `dobe_cursos`(`codigo`,`nombre`,`secuencia`) values (1,'Octavo',8),(2,'Noveno',9),(3,'Decimo',10),(4,'Primero',11),(5,'Segundo',12),(6,'Tercero',13);

/*Table structure for table `dobe_detalle` */

DROP TABLE IF EXISTS `dobe_detalle`;

CREATE TABLE `dobe_detalle` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) DEFAULT NULL,
  `tipo` int(11) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `fk_tipo_detalle` (`tipo`),
  CONSTRAINT `fk_tipo_detalle` FOREIGN KEY (`tipo`) REFERENCES `dobe_tipos` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=latin1;

/*Data for the table `dobe_detalle` */

insert  into `dobe_detalle`(`codigo`,`nombre`,`tipo`) values (40,'ESPECIFICO 1',41),(41,'ESEPECIFICO 2',41),(42,'INESPECIFICO 1',40),(43,'INESPEC 2',40),(44,'Enfermadades catastróficas',42),(45,'Movilidad humana',42),(46,'Adolescentes infractores',42),(47,'Sordera',43),(48,'Hipoacústica',43);

/*Table structure for table `dobe_economica` */

DROP TABLE IF EXISTS `dobe_economica`;

CREATE TABLE `dobe_economica` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `dobe_curso` int(11) DEFAULT NULL,
  `dobe_vivienda` int(11) DEFAULT NULL,
  `dobe_tipovivienda` int(11) DEFAULT NULL,
  `dobe_estudiante` int(11) DEFAULT NULL,
  `dobe_estructura` int(11) DEFAULT NULL,
  `dobe_servicios` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `fk_vivienda_eco` (`dobe_vivienda`),
  KEY `fk_tipovivienda_eco` (`dobe_tipovivienda`),
  KEY `fk_dobe_estructura_eco` (`dobe_estructura`),
  KEY `fk_curso_dobe` (`dobe_curso`),
  KEY `fk_estudia_eco` (`dobe_estudiante`),
  CONSTRAINT `fk_curso_dobe` FOREIGN KEY (`dobe_curso`) REFERENCES `dobe_cursos` (`codigo`),
  CONSTRAINT `fk_dobe_estructura_eco` FOREIGN KEY (`dobe_estructura`) REFERENCES `dobe_estados` (`codigo`),
  CONSTRAINT `fk_estudia_eco` FOREIGN KEY (`dobe_estudiante`) REFERENCES `dobe_estudiantes` (`codigo`) ON DELETE CASCADE,
  CONSTRAINT `fk_tipovivienda_eco` FOREIGN KEY (`dobe_tipovivienda`) REFERENCES `dobe_estados` (`codigo`),
  CONSTRAINT `fk_vivienda_eco` FOREIGN KEY (`dobe_vivienda`) REFERENCES `dobe_estados` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=308 DEFAULT CHARSET=latin1;

/*Data for the table `dobe_economica` */

insert  into `dobe_economica`(`codigo`,`dobe_curso`,`dobe_vivienda`,`dobe_tipovivienda`,`dobe_estudiante`,`dobe_estructura`,`dobe_servicios`) values (302,1,21,15,66,23,'19'),(303,2,21,15,66,23,'17,18'),(304,3,21,15,66,23,'18'),(305,4,21,15,66,23,'18'),(306,5,21,15,66,23,'17,18,19'),(307,6,21,15,66,23,'');

/*Table structure for table `dobe_estados` */

DROP TABLE IF EXISTS `dobe_estados`;

CREATE TABLE `dobe_estados` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) DEFAULT NULL,
  `tipo` varchar(2) DEFAULT NULL COMMENT 'P:parientes; C:civil; E:estados; V: vivienda; TP: madera; S: servicios; I:ingresos; E:EGRESOs; CL:CLUBES',
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=latin1;

/*Data for the table `dobe_estados` */

insert  into `dobe_estados`(`codigo`,`nombre`,`tipo`) values (1,'Padre','P'),(2,'Madre','P'),(3,'Tio','P'),(4,'Tías','P'),(5,'Soltero','C'),(6,'Casado','C'),(7,'Viudo','C'),(8,'Divorciado','C'),(9,'Organizado','E'),(10,'Desorganizado','E'),(11,'Incompleto','E'),(12,'Reorganizado','E'),(13,'Piezas','TV'),(14,'Departamento','TV'),(15,'Casa','TV'),(16,'Edificio','TV'),(17,'Agua','S'),(18,'Luz','S'),(19,'Telefono','S'),(20,'Prestada','V'),(21,'Arrendada','V'),(22,'Propia','V'),(23,'Cemento','TE'),(24,'Madera','TE'),(25,'Mixto','TE'),(26,'PADRE','IN'),(27,'MADRE','IN'),(28,'ARRIENDO','EG'),(29,'ALIMENTACIÓN','EG'),(30,'PRESTAMOS','EG'),(31,'Alto','GR'),(32,'Medio','GR'),(33,'Bajo','GR'),(34,'Siempre','CA'),(35,'A Veces','CA'),(36,'Tímid','CR'),(37,'Celoso','CR'),(38,'Participa en pandillas?','SO'),(39,'Es integrante de alguna pandilla?','SO'),(40,'.','P'),(41,'.','C');

/*Table structure for table `dobe_estructura` */

DROP TABLE IF EXISTS `dobe_estructura`;

CREATE TABLE `dobe_estructura` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `nombres` varchar(100) DEFAULT NULL,
  `edad` int(2) DEFAULT NULL,
  `dobe_civil` int(1) DEFAULT NULL,
  `dobe_relacion` int(11) DEFAULT NULL,
  `dobe_estudiante` int(11) DEFAULT NULL,
  `instruccion` varchar(50) DEFAULT NULL,
  `ocupacion` varchar(50) DEFAULT NULL,
  `lugartrabajo` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `fk_dobe_civill` (`dobe_civil`),
  KEY `fk_dobe_relacion_es` (`dobe_relacion`),
  KEY `fk_dobe_estudiante_es` (`dobe_estudiante`),
  CONSTRAINT `fk_dobe_civill` FOREIGN KEY (`dobe_civil`) REFERENCES `dobe_estados` (`codigo`),
  CONSTRAINT `fk_dobe_estudiante_es` FOREIGN KEY (`dobe_estudiante`) REFERENCES `dobe_estudiantes` (`codigo`) ON DELETE CASCADE,
  CONSTRAINT `fk_dobe_relacion_es` FOREIGN KEY (`dobe_relacion`) REFERENCES `dobe_estados` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=497 DEFAULT CHARSET=latin1;

/*Data for the table `dobe_estructura` */

insert  into `dobe_estructura`(`codigo`,`nombres`,`edad`,`dobe_civil`,`dobe_relacion`,`dobe_estudiante`,`instruccion`,`ocupacion`,`lugartrabajo`) values (489,'',0,6,2,66,'','',''),(490,'',0,6,2,66,'','',''),(491,'',0,6,2,66,'','',''),(492,'',0,6,2,66,'','',''),(493,'',0,6,2,66,'','',''),(494,'',0,6,2,66,'','',''),(495,'',0,6,2,66,'','',''),(496,'',0,6,2,66,'','','');

/*Table structure for table `dobe_estructura1` */

DROP TABLE IF EXISTS `dobe_estructura1`;

CREATE TABLE `dobe_estructura1` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `dobe_civil` int(1) DEFAULT NULL,
  `dobe_relacion` int(11) DEFAULT NULL,
  `dobe_estudiante` int(11) DEFAULT NULL,
  `dobe_tipohogar` int(11) DEFAULT NULL,
  `trabaja` tinyint(1) DEFAULT '0',
  `remunerado` tinyint(1) DEFAULT '0',
  `dobe_curso` int(11) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `fk_dobe_civill01` (`dobe_civil`),
  KEY `fk_dobe_relacion_es01` (`dobe_relacion`),
  KEY `fk_dobe_estudiante_es01` (`dobe_estudiante`),
  KEY `fk_dobe_tipohoja` (`dobe_tipohogar`),
  KEY `fk_dobe_curso_re` (`dobe_curso`),
  CONSTRAINT `fk_dobe_civill01` FOREIGN KEY (`dobe_civil`) REFERENCES `dobe_estados` (`codigo`),
  CONSTRAINT `fk_dobe_curso_re` FOREIGN KEY (`dobe_curso`) REFERENCES `dobe_cursos` (`codigo`),
  CONSTRAINT `fk_dobe_estudiante_es01` FOREIGN KEY (`dobe_estudiante`) REFERENCES `dobe_estudiantes` (`codigo`) ON DELETE CASCADE,
  CONSTRAINT `fk_dobe_relacion_es01` FOREIGN KEY (`dobe_relacion`) REFERENCES `dobe_estados` (`codigo`),
  CONSTRAINT `fk_dobe_tipohoja` FOREIGN KEY (`dobe_tipohogar`) REFERENCES `dobe_estados` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=349 DEFAULT CHARSET=latin1;

/*Data for the table `dobe_estructura1` */

insert  into `dobe_estructura1`(`codigo`,`dobe_civil`,`dobe_relacion`,`dobe_estudiante`,`dobe_tipohogar`,`trabaja`,`remunerado`,`dobe_curso`) values (343,6,2,66,10,0,0,1),(344,6,2,66,10,0,0,2),(345,6,2,66,10,0,0,3),(346,6,2,66,10,0,0,4),(347,6,2,66,10,0,0,5),(348,6,2,66,10,0,0,6);

/*Table structure for table `dobe_estudiantes` */

DROP TABLE IF EXISTS `dobe_estudiantes`;

CREATE TABLE `dobe_estudiantes` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `apellidos` varchar(50) DEFAULT NULL,
  `nombres` varchar(50) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `vivenjuntos` tinyint(1) DEFAULT '0',
  `explique` longtext,
  `lugarnacimiento` varchar(50) DEFAULT NULL,
  `nacionalidad` varchar(50) DEFAULT NULL,
  `raza` varchar(30) DEFAULT NULL,
  `direccion` varchar(100) DEFAULT NULL,
  `direccion1` varchar(100) DEFAULT NULL,
  `direccion2` varchar(100) DEFAULT NULL,
  `telefono` varchar(50) DEFAULT NULL,
  `celular` varchar(50) DEFAULT NULL,
  `curso` varchar(100) DEFAULT NULL,
  `aniolectivo` int(4) DEFAULT NULL,
  `proviene` varchar(100) DEFAULT NULL,
  `observacion2` longtext,
  `observacion1` longtext,
  `observacion` longtext,
  `lugarocupa` int(11) DEFAULT '0',
  `hermanos` int(2) DEFAULT NULL,
  `hombres` int(2) DEFAULT NULL,
  `mujeres` int(2) DEFAULT NULL,
  `habitaciones` int(11) DEFAULT '0',
  `sshh` int(11) DEFAULT '0',
  `repitio` tinyint(1) DEFAULT '0',
  `deserto` tinyint(1) DEFAULT '0',
  `observacionrepitio` longtext,
  `observaciondeserto` longtext,
  `seguimiento` longtext,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=latin1;

/*Data for the table `dobe_estudiantes` */

insert  into `dobe_estudiantes`(`codigo`,`apellidos`,`nombres`,`fecha`,`vivenjuntos`,`explique`,`lugarnacimiento`,`nacionalidad`,`raza`,`direccion`,`direccion1`,`direccion2`,`telefono`,`celular`,`curso`,`aniolectivo`,`proviene`,`observacion2`,`observacion1`,`observacion`,`lugarocupa`,`hermanos`,`hombres`,`mujeres`,`habitaciones`,`sshh`,`repitio`,`deserto`,`observacionrepitio`,`observaciondeserto`,`seguimiento`) values (66,'JADAN','GEOVANNY',NULL,1,'ASDF','LOJA','ECUADOR','MESTIZO','CONDADO','SHOPING','NO SE','511515','09805555','OCTAVO',2014,'QUITO',NULL,NULL,'SDSD',0,2,1,1,23,2,0,1,'AAAA','DSFSADF',NULL);

/*Table structure for table `dobe_ingresos` */

DROP TABLE IF EXISTS `dobe_ingresos`;

CREATE TABLE `dobe_ingresos` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `ingresos` int(11) DEFAULT NULL,
  `valori` decimal(8,2) DEFAULT '0.00',
  `observaciones` longtext,
  `dobe_estudiante` int(11) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `fk_dobe_estados_ingres` (`ingresos`),
  KEY `fk_dobe_estudiante` (`dobe_estudiante`),
  CONSTRAINT `fk_dobe_estados_ingres` FOREIGN KEY (`ingresos`) REFERENCES `dobe_estados` (`codigo`),
  CONSTRAINT `fk_dobe_estudiante` FOREIGN KEY (`dobe_estudiante`) REFERENCES `dobe_estudiantes` (`codigo`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=246 DEFAULT CHARSET=latin1;

/*Data for the table `dobe_ingresos` */

insert  into `dobe_ingresos`(`codigo`,`ingresos`,`valori`,`observaciones`,`dobe_estudiante`) values (241,26,'23.00','IN',66),(242,27,'3.00','IN',66),(243,28,'0.00','EG',66),(244,29,'0.00','EG',66),(245,30,'0.00','EG',66);

/*Table structure for table `dobe_psicopedagogico` */

DROP TABLE IF EXISTS `dobe_psicopedagogico`;

CREATE TABLE `dobe_psicopedagogico` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `dobe_curso` int(11) DEFAULT NULL,
  `aprovechaimiento` decimal(9,2) DEFAULT NULL,
  `comportamiento` decimal(9,2) DEFAULT NULL,
  `dobe_estudiante` int(11) DEFAULT NULL,
  `menores` int(11) DEFAULT NULL,
  `mayores` int(11) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `fk_curso_psico` (`dobe_curso`),
  KEY `fk_curso_estud` (`dobe_estudiante`),
  CONSTRAINT `fk_curso_estud` FOREIGN KEY (`dobe_estudiante`) REFERENCES `dobe_estudiantes` (`codigo`) ON DELETE CASCADE,
  CONSTRAINT `fk_curso_psico` FOREIGN KEY (`dobe_curso`) REFERENCES `dobe_cursos` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=259 DEFAULT CHARSET=latin1;

/*Data for the table `dobe_psicopedagogico` */

insert  into `dobe_psicopedagogico`(`codigo`,`dobe_curso`,`aprovechaimiento`,`comportamiento`,`dobe_estudiante`,`menores`,`mayores`) values (253,1,'0.00','0.00',66,0,0),(254,2,'0.00','0.00',66,0,0),(255,3,'0.00','0.00',66,0,0),(256,4,'0.00','0.00',66,0,0),(257,5,'0.00','0.00',66,0,0),(258,6,'0.00','0.00',66,0,0);

/*Table structure for table `dobe_representantes` */

DROP TABLE IF EXISTS `dobe_representantes`;

CREATE TABLE `dobe_representantes` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `dobe_curso` int(11) DEFAULT NULL,
  `nombres` varchar(100) DEFAULT NULL,
  `dobe_relacion` int(11) DEFAULT NULL,
  `telefono` varchar(50) DEFAULT NULL,
  `dobe_estudiante` int(11) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `fk_dobe_relacion` (`dobe_relacion`),
  KEY `fk_dobe_curso` (`dobe_curso`),
  KEY `fK_dobe_estudiantes` (`dobe_estudiante`),
  CONSTRAINT `fk_dobe_curso` FOREIGN KEY (`dobe_curso`) REFERENCES `dobe_cursos` (`codigo`),
  CONSTRAINT `fK_dobe_estudiantes` FOREIGN KEY (`dobe_estudiante`) REFERENCES `dobe_estudiantes` (`codigo`) ON DELETE CASCADE,
  CONSTRAINT `fk_dobe_relacion` FOREIGN KEY (`dobe_relacion`) REFERENCES `dobe_estados` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=397 DEFAULT CHARSET=latin1;

/*Data for the table `dobe_representantes` */

insert  into `dobe_representantes`(`codigo`,`dobe_curso`,`nombres`,`dobe_relacion`,`telefono`,`dobe_estudiante`) values (391,1,'',2,'',66),(392,2,'',2,'',66),(393,3,'',2,'',66),(394,4,'',2,'',66),(395,5,'',2,'',66),(396,6,'',2,'',66);

/*Table structure for table `dobe_social` */

DROP TABLE IF EXISTS `dobe_social`;

CREATE TABLE `dobe_social` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `dobe_estado` int(11) DEFAULT NULL,
  `estado` tinyint(1) DEFAULT '0',
  `dobe_estudiante` int(11) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `fk_curso_social` (`dobe_estado`),
  KEY `fk_estudiante_social` (`dobe_estudiante`),
  CONSTRAINT `fk_estado_social` FOREIGN KEY (`dobe_estado`) REFERENCES `dobe_estados` (`codigo`),
  CONSTRAINT `fk_estudiante_social` FOREIGN KEY (`dobe_estudiante`) REFERENCES `dobe_estudiantes` (`codigo`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=latin1;

/*Data for the table `dobe_social` */

insert  into `dobe_social`(`codigo`,`dobe_estado`,`estado`,`dobe_estudiante`) values (77,38,1,66),(78,39,1,66);

/*Table structure for table `dobe_tipos` */

DROP TABLE IF EXISTS `dobe_tipos`;

CREATE TABLE `dobe_tipos` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) DEFAULT NULL,
  `tipo` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=latin1;

/*Data for the table `dobe_tipos` */

insert  into `dobe_tipos`(`codigo`,`nombre`,`tipo`) values (40,'Inespecíficos del aprendizaje','T'),(41,'Específicos del aprendizaje','T'),(42,'EN SITUACIÓN DE RIESGO','N'),(43,'AUDITIVA','N');

/*Table structure for table `dobe_transtornos` */

DROP TABLE IF EXISTS `dobe_transtornos`;

CREATE TABLE `dobe_transtornos` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `dobe_detalle` int(11) DEFAULT NULL,
  `estado` tinyint(1) DEFAULT '0',
  `dobe_estudiante` int(11) DEFAULT NULL,
  `dobe_curso` int(11) DEFAULT NULL,
  `tipo` varchar(1) DEFAULT NULL COMMENT 't: transtor N: necesidad',
  PRIMARY KEY (`codigo`),
  KEY `fk_curso_trans` (`dobe_detalle`),
  KEY `fk_estudiante_trans` (`dobe_estudiante`),
  KEY `fk_dobe_curso_trans` (`dobe_curso`),
  CONSTRAINT `fk_dobe_curso_trans` FOREIGN KEY (`dobe_curso`) REFERENCES `dobe_cursos` (`codigo`),
  CONSTRAINT `fk_estado_trans` FOREIGN KEY (`dobe_detalle`) REFERENCES `dobe_detalle` (`codigo`),
  CONSTRAINT `fk_estudiante_trans` FOREIGN KEY (`dobe_estudiante`) REFERENCES `dobe_estudiantes` (`codigo`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1345 DEFAULT CHARSET=latin1;

/*Data for the table `dobe_transtornos` */

insert  into `dobe_transtornos`(`codigo`,`dobe_detalle`,`estado`,`dobe_estudiante`,`dobe_curso`,`tipo`) values (1291,42,1,66,1,'T'),(1292,42,0,66,2,'T'),(1293,42,0,66,3,'T'),(1294,42,0,66,4,'T'),(1295,42,0,66,5,'T'),(1296,42,0,66,6,'T'),(1297,43,1,66,1,'T'),(1298,43,0,66,2,'T'),(1299,43,0,66,3,'T'),(1300,43,0,66,4,'T'),(1301,43,0,66,5,'T'),(1302,43,0,66,6,'T'),(1303,40,0,66,1,'T'),(1304,40,1,66,2,'T'),(1305,40,0,66,3,'T'),(1306,40,0,66,4,'T'),(1307,40,0,66,5,'T'),(1308,40,0,66,6,'T'),(1309,41,0,66,1,'T'),(1310,41,1,66,2,'T'),(1311,41,0,66,3,'T'),(1312,41,0,66,4,'T'),(1313,41,0,66,5,'T'),(1314,41,0,66,6,'T'),(1315,44,0,66,1,'N'),(1316,44,1,66,2,'N'),(1317,44,0,66,3,'N'),(1318,44,0,66,4,'N'),(1319,44,0,66,5,'N'),(1320,44,0,66,6,'N'),(1321,45,0,66,1,'N'),(1322,45,1,66,2,'N'),(1323,45,0,66,3,'N'),(1324,45,0,66,4,'N'),(1325,45,0,66,5,'N'),(1326,45,0,66,6,'N'),(1327,46,0,66,1,'N'),(1328,46,1,66,2,'N'),(1329,46,0,66,3,'N'),(1330,46,0,66,4,'N'),(1331,46,0,66,5,'N'),(1332,46,0,66,6,'N'),(1333,47,0,66,1,'N'),(1334,47,0,66,2,'N'),(1335,47,0,66,3,'N'),(1336,47,0,66,4,'N'),(1337,47,0,66,5,'N'),(1338,47,1,66,6,'N'),(1339,48,0,66,1,'N'),(1340,48,0,66,2,'N'),(1341,48,0,66,3,'N'),(1342,48,0,66,4,'N'),(1343,48,0,66,5,'N'),(1344,48,1,66,6,'N');

/*Table structure for table `dobe_trato` */

DROP TABLE IF EXISTS `dobe_trato`;

CREATE TABLE `dobe_trato` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `padre` varchar(2) DEFAULT NULL,
  `madre` varchar(2) DEFAULT NULL,
  `representante` varchar(2) DEFAULT NULL,
  `observaciones` longtext,
  `dobe_estudiante` int(11) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `fk_dobe_estudia_bs` (`dobe_estudiante`),
  CONSTRAINT `fk_dobe_estudia_bs` FOREIGN KEY (`dobe_estudiante`) REFERENCES `dobe_estudiantes` (`codigo`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=latin1;

/*Data for the table `dobe_trato` */

insert  into `dobe_trato`(`codigo`,`padre`,`madre`,`representante`,`observaciones`,`dobe_estudiante`) values (56,'R','MB','B',NULL,66);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
