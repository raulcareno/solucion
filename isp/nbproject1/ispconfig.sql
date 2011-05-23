/*
SQLyog Community Edition- MySQL GUI v8.04 
MySQL - 6.0.4-alpha-community : Database - isp
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

CREATE DATABASE /*!32312 IF NOT EXISTS*/`isp` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `isp`;

/*Table structure for table `accesos` */

DROP TABLE IF EXISTS `accesos`;

CREATE TABLE `accesos` (
  `codigoacc` int(11) NOT NULL AUTO_INCREMENT,
  `perfil` int(11) DEFAULT NULL,
  `modulo` varchar(100) DEFAULT NULL,
  `guardar` tinyint(1) DEFAULT NULL,
  `eliminar` tinyint(1) DEFAULT NULL,
  `actualizar` tinyint(1) DEFAULT NULL,
  `ingresar` tinyint(1) DEFAULT '0',
  `grupo` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`codigoacc`),
  KEY `FK_perfil` (`perfil`),
  KEY `FKB9CEF71382A62D53` (`perfil`),
  CONSTRAINT `FK_accesos_perfil` FOREIGN KEY (`perfil`) REFERENCES `perfil` (`codigo`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=204 DEFAULT CHARSET=latin1;

/*Data for the table `accesos` */

insert  into `accesos`(`codigoacc`,`perfil`,`modulo`,`guardar`,`eliminar`,`actualizar`,`ingresar`,`grupo`) values (1,NULL,'Accesos',1,1,1,1,NULL),(2,NULL,'Auditoria',1,1,1,1,NULL),(3,NULL,'Cantones',1,1,1,1,NULL),(4,NULL,'Clientes',1,1,1,1,NULL),(5,NULL,'Comisiones',1,1,1,1,NULL),(6,NULL,'Contratos',1,1,1,1,NULL),(7,NULL,'Equipos',1,1,1,1,NULL),(8,NULL,'Empresa',1,1,1,1,NULL),(9,NULL,'Marcas',1,1,1,1,NULL),(10,NULL,'Nodos',1,1,1,1,NULL),(11,NULL,'Perfiles',1,1,1,1,NULL),(12,NULL,'Proveedores',1,1,1,1,NULL),(13,NULL,'Sectores',1,1,1,1,NULL),(14,NULL,'Empleados',1,1,1,1,NULL),(15,NULL,'Planes',1,1,1,1,NULL),(16,NULL,'Facturacion',1,1,1,1,NULL),(17,NULL,'Reportes',1,1,1,1,NULL),(18,NULL,'EmpleadosSectores',1,1,1,1,NULL),(19,NULL,'Radios',1,1,1,1,NULL),(185,1,'Accesos',1,1,1,1,''),(186,1,'Auditoria',1,1,1,1,''),(187,1,'Cantones',1,1,1,1,''),(188,1,'Clientes',1,1,1,1,''),(189,1,'Comisiones',1,1,1,1,''),(190,1,'Contratos',1,1,1,1,''),(191,1,'Equipos',1,1,1,1,''),(192,1,'Empresa',1,1,1,1,''),(193,1,'Marcas',1,1,1,1,''),(194,1,'Nodos',1,1,1,1,''),(195,1,'Perfiles',1,1,1,1,''),(196,1,'Proveedores',1,1,1,1,''),(197,1,'Sectores',1,1,1,1,''),(198,1,'Empleados',1,1,1,1,''),(199,1,'Planes',1,1,1,1,''),(200,1,'Facturacion',1,1,1,1,''),(201,1,'Reportes',1,1,1,1,''),(202,1,'EmpleadosSectores',1,1,1,1,''),(203,1,'Radios',1,1,1,1,'');

/*Table structure for table `auditoria` */

DROP TABLE IF EXISTS `auditoria`;

CREATE TABLE `auditoria` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `empleado` int(11) DEFAULT NULL,
  `fecha` datetime DEFAULT NULL,
  `tipo` varchar(100) DEFAULT NULL,
  `ip` varchar(100) DEFAULT NULL,
  `pc` varchar(200) DEFAULT NULL,
  `tabla` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FK_auditoria_empleado` (`empleado`),
  CONSTRAINT `FK_auditoria_empleado` FOREIGN KEY (`empleado`) REFERENCES `empleados` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=192 DEFAULT CHARSET=latin1;

/*Data for the table `auditoria` */

insert  into `auditoria`(`codigo`,`empleado`,`fecha`,`tipo`,`ip`,`pc`,`tabla`) values (1,1,'2011-04-17 23:05:13','-','127.0.0.1','Ingreso Sistema','-'),(2,1,'2011-04-17 23:05:48','-','127.0.0.1','Ingreso Sistema','-'),(3,1,'2011-04-18 08:14:33','-','127.0.0.1','Ingreso Sistema','-'),(4,1,'2011-04-18 08:15:56','-','127.0.0.1','Ingreso Sistema','-'),(5,1,'2011-04-18 09:01:24','-','127.0.0.1','Ingreso Sistema','-'),(6,1,'2011-04-18 09:03:45','-','127.0.0.1','Ingreso Sistema','-'),(7,1,'2011-04-18 09:06:58','-','127.0.0.1','Ingreso Sistema','-'),(8,1,'2011-04-18 09:10:14','-','127.0.0.1','Ingreso Sistema','-'),(9,1,'2011-04-18 09:55:46','-','127.0.0.1','Ingreso Sistema','-'),(10,1,'2011-04-18 10:09:22','-','127.0.0.1','Ingreso Sistema','-'),(11,1,'2011-04-18 10:14:05','Guardar','127.0.0.1','persistencia.Perfil[codigo=1]','Accesos'),(12,1,'2011-04-18 10:19:26','-','127.0.0.1','Ingreso Sistema','-'),(13,1,'2011-04-18 10:19:48','Guardar','127.0.0.1','persistencia.Perfil[codigo=1]','Accesos'),(14,1,'2011-04-18 10:20:45','Guardar','127.0.0.1','persistencia.Perfil[codigo=1]','Accesos'),(15,1,'2011-04-18 10:23:42','-','127.0.0.1','Ingreso Sistema','-'),(16,1,'2011-04-18 10:23:59','Guardar','127.0.0.1','persistencia.Perfil[codigo=4]','Accesos'),(17,1,'2011-04-18 10:31:32','-','127.0.0.1','Ingreso Sistema','-'),(18,1,'2011-04-18 10:34:20','-','127.0.0.1','Ingreso Sistema','-'),(19,1,'2011-04-18 10:36:47','Guardar','127.0.0.1','persistencia.Perfil[codigo=4]','Accesos'),(20,1,'2011-04-18 10:42:02','Guardar','127.0.0.1','persistencia.Perfil[codigo=3]','Accesos'),(21,1,'2011-04-18 10:42:08','Guardar','127.0.0.1','persistencia.Perfil[codigo=2]','Accesos'),(22,1,'2011-04-18 10:49:34','-','127.0.0.1','Ingreso Sistema','-'),(23,1,'2011-04-18 10:51:20','-','127.0.0.1','Ingreso Sistema','-'),(24,1,'2011-04-18 11:04:09','-','127.0.0.1','Ingreso Sistema','-'),(25,1,'2011-04-18 11:20:15','-','127.0.0.1','Ingreso Sistema','-'),(26,1,'2011-04-18 11:26:16','Guardar','127.0.0.1','JCINFORM SOLUCIONES INFORMATICAS','Empresa'),(27,1,'2011-04-18 11:28:50','Guardar','127.0.0.1','JCINFORM SOLUCIONES INFORMATICAS','Empresa'),(28,1,'2011-04-18 11:37:56','-','127.0.0.1','Ingreso Sistema','-'),(29,1,'2011-04-18 11:39:20','Guardar','127.0.0.1','JCINFORM SOLUCIONES INFORMATICAS','Empresa'),(30,1,'2011-04-18 11:40:39','Guardar','127.0.0.1','JCINFORM SOLUCIONES INFORMATICAS','Empresa'),(31,1,'2011-04-18 11:45:35','Guardar','127.0.0.1','JCINFORM SOLUCIONES INFORMATICAS','Empresa'),(32,1,'2011-04-18 11:51:55','Guardar','127.0.0.1','JCINFORM SOLUCIONES INFORMATICAS','Empresa'),(33,1,'2011-04-18 12:05:51','Guardar','127.0.0.1','JCINFORM SOLUCIONES INFORMATICAS','Empresa'),(34,1,'2011-04-18 12:14:14','Actualizar','127.0.0.1','2 - Pedro Vicente Maldonado','Cantones'),(35,1,'2011-04-18 12:14:37','Guardar','127.0.0.1','3 - Santo Domingo','Cantones'),(36,1,'2011-04-18 12:15:22','Actualizar','127.0.0.1','3 - Santo Domingo de los Tsachilas','Cantones'),(37,1,'2011-04-18 12:15:29','Eliminar','127.0.0.1','3 - Santo Domingo de los Tsachilas','Cantones'),(38,1,'2011-04-18 12:18:07','Actualizar','127.0.0.1','3 - FACTURADOR','Perfiles'),(39,1,'2011-04-18 12:18:50','Eliminar','127.0.0.1','4 - VENDEDOR','Perfiles'),(40,1,'2011-04-18 12:18:54','Eliminar','127.0.0.1','3 - FACTURADOR','Perfiles'),(41,1,'2011-04-18 12:19:03','Guardar','127.0.0.1','5 - VENDEDOR','Perfiles'),(42,1,'2011-04-18 12:39:38','Guardar','127.0.0.1','3 - Centro','Sectores'),(43,1,'2011-04-18 12:44:04','Actualizar','127.0.0.1','3 - Centro','Sectores'),(44,1,'2011-04-18 12:44:35','Actualizar','127.0.0.1','3 - Centro','Sectores'),(45,1,'2011-04-18 13:31:17','-','127.0.0.1','Ingreso Sistema','-'),(46,1,'2011-04-18 13:36:24','Guardar','127.0.0.1',' JCINFORM SOLUCIONES INFORMATICAS','Proveedores'),(47,1,'2011-04-18 13:38:53','Guardar','127.0.0.1',' JCINFORM SOLUCIONES INFORMATICAS','Proveedores'),(48,1,'2011-04-18 13:39:58','Guardar','127.0.0.1',' JCINFORM SOLUCIONES INFORMATICAS','Proveedores'),(49,1,'2011-04-18 13:41:23','Guardar','127.0.0.1',' CONTADORES ASOCIADOS','Proveedores'),(50,1,'2011-04-18 13:42:14','Guardar','127.0.0.1',' asdf','Proveedores'),(51,1,'2011-04-18 13:42:31','Guardar','127.0.0.1',' asdf','Proveedores'),(52,1,'2011-04-18 13:42:42','Eliminar','127.0.0.1','asdf','Proveedores'),(53,1,'2011-04-18 13:54:17','Guardar','127.0.0.1','persistencia.Perfil[codigo=1]','Accesos'),(54,1,'2011-04-18 13:54:27','-','127.0.0.1','Ingreso Sistema','-'),(55,1,'2011-04-18 13:56:13','Guardar','127.0.0.1','JADAN ORTEGA GEOVANNY','Empleados'),(56,1,'2011-04-18 13:56:47','Guardar','127.0.0.1','Cahueñas Angelica','Empleados'),(57,1,'2011-04-18 13:58:16','-','127.0.0.1','Ingreso Sistema','-'),(58,1,'2011-04-18 14:34:24','-','127.0.0.1','Ingreso Sistema','-'),(59,1,'2011-04-18 16:35:47','-','127.0.0.1','Ingreso Sistema','-'),(60,1,'2011-04-18 20:27:34','-','127.0.0.1','Ingreso Sistema','-'),(61,1,'2011-04-18 20:51:03','-','127.0.0.1','Ingreso Sistema','-'),(62,1,'2011-04-18 21:02:26','-','127.0.0.1','Ingreso Sistema','-'),(63,1,'2011-04-18 21:08:51','-','127.0.0.1','Ingreso Sistema','-'),(64,1,'2011-04-18 21:12:34','Guardar','127.0.0.1','ApeCliente Cliente','Clientes'),(65,1,'2011-04-18 21:13:00','Guardar','127.0.0.1','ApeCliente Cliente','Clientes'),(66,1,'2011-04-18 21:13:17','Guardar','127.0.0.1','ApeCliente Cliente','Clientes'),(67,1,'2011-04-18 21:13:57','Guardar','127.0.0.1','ApeCliente Cliente','Clientes'),(68,1,'2011-04-18 21:15:35','Eliminar','127.0.0.1','2 2','Clientes'),(69,1,'2011-04-18 21:19:18','Guardar','127.0.0.1','1 - TRENDNET','Marcas'),(70,1,'2011-04-18 21:19:26','Guardar','127.0.0.1','2 - DLINK','Marcas'),(71,1,'2011-04-18 21:19:32','Guardar','127.0.0.1','3 - OTRO','Marcas'),(72,1,'2011-04-18 21:19:36','Eliminar','127.0.0.1','3 - OTRO','Marcas'),(73,1,'2011-04-25 22:47:17','-','127.0.0.1','Ingreso Sistema','-'),(74,1,'2011-04-25 22:48:02','Guardar','127.0.0.1','Router Router','Equipos'),(75,1,'2011-04-25 23:10:00','Guardar','127.0.0.1','Switch Switch','Equipos'),(76,1,'2011-04-25 23:12:37','Guardar','127.0.0.1','Antena Antena','Equipos'),(77,1,'2011-04-26 11:00:30','-','127.0.0.1','Ingreso Sistema','-'),(78,1,'2011-04-26 11:04:11','-','127.0.0.1','Ingreso Sistema','-'),(79,1,'2011-04-26 11:38:45','Guardar','127.0.0.1','cantidad2Tue Apr 26 11:38:24 COT 2011','Controlequipos'),(80,1,'2011-04-26 18:02:54','-','127.0.0.1','Ingreso Sistema','-'),(81,1,'2011-04-26 18:14:07','Actualizar','127.0.0.1','3 - Centro','Sectores'),(82,1,'2011-04-26 18:24:39','Actualizar','127.0.0.1','2 - CONDADO','Sectores'),(83,1,'2011-04-26 18:24:55','Actualizar','127.0.0.1','1 - COTOCOLLAO','Sectores'),(84,1,'2011-04-26 18:45:07','Guardar','127.0.0.1','SUPER ADMINISTRADOR','Accesos'),(85,1,'2011-04-26 18:46:42','Guardar','127.0.0.1','SUPER ADMINISTRADOR','Accesos'),(86,1,'2011-04-26 18:47:11','-','127.0.0.1','Ingreso Sistema','-'),(87,1,'2011-04-26 18:49:05','Guardar','127.0.0.1','1 - ADSL 256 kb','Planes'),(88,1,'2011-04-26 18:51:01','Actualizar','127.0.0.1','1 - ADSL 256 kb','Planes'),(89,1,'2011-04-26 18:53:50','Actualizar','127.0.0.1','1 - ADSL 256 kb','Planes'),(90,1,'2011-04-26 18:54:27','Guardar','127.0.0.1','2 - ADSL 512 KB','Planes'),(91,1,'2011-04-26 18:56:37','Actualizar','127.0.0.1','2 - ADSL 512 KB','Planes'),(92,1,'2011-04-26 18:57:09','Guardar','127.0.0.1','3 - ADSL 1GB','Planes'),(93,1,'2011-04-26 18:57:58','Guardar','127.0.0.1','4 - REINSTALACION SERVICIO','Planes'),(94,1,'2011-04-26 18:58:48','Guardar','127.0.0.1','5 - SERVICIO TECNICO','Planes'),(95,1,'2011-04-26 19:57:10','-','127.0.0.1','Ingreso Sistema','-'),(96,1,'2011-04-26 20:31:12','Guardar','127.0.0.1','persistencia.Empleados[codigo=1]','Comisiones'),(97,1,'2011-04-26 20:31:37','Guardar','127.0.0.1','persistencia.Empleados[codigo=1]','Comisiones'),(98,1,'2011-04-26 20:31:58','Guardar','127.0.0.1','persistencia.Empleados[codigo=2]','Comisiones'),(99,1,'2011-04-26 20:35:02','Guardar','127.0.0.1','persistencia.Empleados[codigo=1]','Comisiones'),(100,1,'2011-04-26 20:36:18','Guardar','127.0.0.1','persistencia.Empleados[codigo=2]','Comisiones'),(101,1,'2011-04-26 22:16:44','-','127.0.0.1','Ingreso Sistema','-'),(102,1,'2011-04-26 22:31:22','Guardar','127.0.0.1','persistencia.Empleados[codigo=2]','SectoresEmpleados'),(103,1,'2011-04-26 22:31:41','Guardar','127.0.0.1','persistencia.Empleados[codigo=1]','SectoresEmpleados'),(104,1,'2011-04-26 22:44:23','-','127.0.0.1','Ingreso Sistema','-'),(105,1,'2011-04-26 22:49:31','-','127.0.0.1','Ingreso Sistema','-'),(106,1,'2011-04-26 22:50:48','Guardar','127.0.0.1','persistencia.Empleados[codigo=2]','SectoresEmpleados'),(107,1,'2011-04-26 22:55:01','-','127.0.0.1','Ingreso Sistema','-'),(108,1,'2011-04-26 22:59:26','Guardar','127.0.0.1','SUPER ADMINISTRADOR','Accesos'),(109,1,'2011-04-26 23:00:20','-','127.0.0.1','Ingreso Sistema','-'),(110,1,'2011-04-26 23:00:27','Guardar','127.0.0.1','persistencia.Empleados[codigo=1]','SectoresEmpleados'),(111,1,'2011-05-02 09:41:12','-','127.0.0.1','Ingreso Sistema','-'),(112,1,'2011-05-02 09:44:37','Guardar','127.0.0.1','persistencia.Empleados[codigo=1]','Comisiones'),(113,1,'2011-05-02 10:22:32','-','127.0.0.1','Ingreso Sistema','-'),(114,1,'2011-05-02 10:26:46','Actualizar','127.0.0.1','1 - ADSL 256 KB','Planes'),(115,1,'2011-05-02 10:54:30','-','127.0.0.1','Ingreso Sistema','-'),(116,1,'2011-05-02 11:40:47','Guardar','127.0.0.1','micliente Mi Cliente','Clientes'),(117,1,'2011-05-02 11:47:39','-','127.0.0.1','Ingreso Sistema','-'),(118,1,'2011-05-02 14:17:45','-','127.0.0.1','Ingreso Sistema','-'),(119,1,'2011-05-02 20:15:43','-','127.0.0.1','Ingreso Sistema','-'),(120,1,'2011-05-02 21:09:50','Guardar','127.0.0.1','Cahueñas Angelica','Clientes'),(121,1,'2011-05-02 21:16:04','Guardar','127.0.0.1','Jadán Carlos','Clientes'),(122,1,'2011-05-02 21:16:20','Guardar','127.0.0.1','Jadán Carlos','Clientes'),(123,1,'2011-05-02 21:17:13','Guardar','127.0.0.1','CONSUMIDOR FINAL','Clientes'),(124,1,'2011-05-02 21:17:32','Guardar','127.0.0.1','Jadán Carlos','Clientes'),(125,1,'2011-05-02 21:18:02','Guardar','127.0.0.1','JADAN STALIN','Clientes'),(126,1,'2011-05-02 21:26:19','Guardar','127.0.0.1','JADAN STALIN','Clientes'),(127,1,'2011-05-02 21:26:31','Guardar','127.0.0.1','Jadan Geovanny','Clientes'),(128,1,'2011-05-03 09:58:41','-','127.0.0.1','Ingreso Sistema','-'),(129,1,'2011-05-03 12:59:20','-','127.0.0.1','Ingreso Sistema','-'),(130,1,'2011-05-03 13:24:47','Guardar','127.0.0.1','192.168.0.52 Pedro Vic.','Nodos'),(131,1,'2011-05-03 15:42:03','-','127.0.0.1','Ingreso Sistema','-'),(132,1,'2011-05-03 17:40:08','Guardar','127.0.0.1','JADAN CAHUEÑAS ISMAEL FRANCISCO','Empleados'),(133,1,'2011-05-03 17:40:47','Guardar','127.0.0.1','JADAN CAHUEÑAS ISMAEL FRANCISCO','Empleados'),(134,1,'2011-05-03 17:44:14','Guardar','127.0.0.1','JADAN CAHUEÑAS SAMI ROMINA','Empleados'),(135,1,'2011-05-03 17:45:30','Guardar','127.0.0.1','JADAN CAHUEÑAS ISMAEL FRANCISCO','Empleados'),(136,1,'2011-05-03 18:02:20','-','127.0.0.1','Ingreso Sistema','-'),(137,1,'2011-05-03 18:16:50','-','127.0.0.1','Ingreso Sistema','-'),(138,1,'2011-05-03 18:19:12','-','127.0.0.1','Ingreso Sistema','-'),(139,1,'2011-05-03 18:36:29','-','127.0.0.1','Ingreso Sistema','-'),(140,1,'2011-05-03 18:44:37','-','127.0.0.1','Ingreso Sistema','-'),(141,1,'2011-05-03 19:26:30','-','127.0.0.1','Ingreso Sistema','-'),(142,1,'2011-05-03 19:27:22','-','127.0.0.1','Ingreso Sistema','-'),(143,1,'2011-05-03 19:33:34','Guardar','127.0.0.1','JADAN CAHUEÑAS SAMI ROMINA','Empleados'),(144,1,'2011-05-03 19:35:15','Guardar','127.0.0.1','Cahueñas Angelica','Clientes'),(145,1,'2011-05-03 19:35:38','Guardar','127.0.0.1','JADAN CAHUEÑAS SAMI ROMINA','Empleados'),(146,1,'2011-05-04 08:45:38','-','127.0.0.1','Ingreso Sistema','-'),(147,1,'2011-05-04 11:18:33','-','127.0.0.1','Ingreso Sistema','-'),(148,1,'2011-05-04 12:54:18','-','127.0.0.1','Ingreso Sistema','-'),(149,1,'2011-05-04 13:47:28','Actualizar','127.0.0.1','000001 - null null','Contratos'),(150,1,'2011-05-04 13:56:09','-','127.0.0.1','Ingreso Sistema','-'),(151,1,'2011-05-04 13:58:57','-','127.0.0.1','Ingreso Sistema','-'),(152,1,'2011-05-04 16:11:14','-','127.0.0.1','Ingreso Sistema','-'),(153,1,'2011-05-04 16:11:51','Guardar','127.0.0.1','Bastidas Miguel','Empleados'),(154,2,'2011-05-04 16:11:58','-','127.0.0.1','Ingreso Sistema','-'),(155,2,'2011-05-04 16:11:59','-','186.5.68.74','Ingreso Sistema','-'),(156,2,'2011-05-04 16:18:28','-','186.5.68.74','Ingreso Sistema','-'),(157,1,'2011-05-04 16:20:38','-','127.0.0.1','Ingreso Sistema','-'),(158,1,'2011-05-04 16:48:28','-','127.0.0.1','Ingreso Sistema','-'),(159,1,'2011-05-04 16:52:23','-','127.0.0.1','Ingreso Sistema','-'),(160,1,'2011-05-04 17:11:07','-','127.0.0.1','Ingreso Sistema','-'),(161,1,'2011-05-04 17:13:04','Actualizar','127.0.0.1','000001 - null null','Contratos'),(162,1,'2011-05-04 17:13:40','Actualizar','127.0.0.1','000001 - null null','Contratos'),(163,1,'2011-05-04 17:15:49','Actualizar','127.0.0.1','000001 - null null','Contratos'),(164,1,'2011-05-04 17:16:00','Actualizar','127.0.0.1','000001 - null null','Contratos'),(165,1,'2011-05-04 17:20:21','-','127.0.0.1','Ingreso Sistema','-'),(166,1,'2011-05-05 09:09:32','-','127.0.0.1','Ingreso Sistema','-'),(167,2,'2011-05-05 12:41:30','-','186.5.68.74','Ingreso Sistema','-'),(168,2,'2011-05-05 12:52:00','-','190.95.148.131','Ingreso Sistema','-'),(169,2,'2011-05-05 12:58:03','Guardar','190.95.148.131','gualotuña henriquez juan carlos','Clientes'),(170,2,'2011-05-05 13:00:51','Guardar','190.95.148.131','192.168.0.52 Pedro Vic.','Nodos'),(171,1,'2011-05-09 14:17:27','-','127.0.0.1','Ingreso Sistema','-'),(172,1,'2011-05-09 14:49:14','-','127.0.0.1','Ingreso Sistema','-'),(173,1,'2011-05-10 08:35:26','-','127.0.0.1','Ingreso Sistema','-'),(174,1,'2011-05-10 08:36:44','Guardar','127.0.0.1','SUPER ADMINISTRADOR','Accesos'),(175,1,'2011-05-10 08:37:28','Guardar','127.0.0.1','SUPER ADMINISTRADOR','Accesos'),(176,1,'2011-05-10 08:38:02','Guardar','127.0.0.1','SUPER ADMINISTRADOR','Accesos'),(177,1,'2011-05-10 08:38:34','-','127.0.0.1','Ingreso Sistema','-'),(178,1,'2011-05-10 08:42:13','Guardar','127.0.0.1','1 - RADIO','Radioses'),(179,1,'2011-05-13 08:55:56','-','127.0.0.1','Ingreso Sistema','-'),(180,1,'2011-05-13 09:00:30','Actualizar','127.0.0.1','2 - Pedro Vicente Maldonado','Cantones'),(181,1,'2011-05-13 09:00:37','Actualizar','127.0.0.1','4 - Sur','Sectores'),(182,1,'2011-05-13 09:00:48','Guardar','127.0.0.1','5 - Centro','Sectores'),(183,1,'2011-05-13 09:01:55','Guardar','127.0.0.1',' JCINFORM SOLUCIONES INFORMATICAS','Proveedores'),(184,1,'2011-05-13 09:02:19','Guardar','127.0.0.1','Bastidas Miguel','Empleados'),(185,1,'2011-05-13 09:03:02','Guardar','127.0.0.1','jcinform.persistencia.Empleados[codigo=2]','Comisiones'),(186,1,'2011-05-13 09:05:07','-','127.0.0.1','Ingreso Sistema','-'),(187,1,'2011-05-13 09:06:48','Guardar','127.0.0.1','jcinform.persistencia.Empleados[codigo=1]','SectoresEmpleados'),(188,1,'2011-05-13 09:07:01','Guardar','127.0.0.1','jcinform.persistencia.Empleados[codigo=2]','SectoresEmpleados'),(189,1,'2011-05-13 09:07:32','Actualizar','127.0.0.1','1 - RADIO','Radioses'),(190,1,'2011-05-17 13:09:33','-','127.0.0.1','Ingreso Sistema','-'),(191,1,'2011-05-17 20:50:39','-','127.0.0.1','Ingreso Sistema','-');

/*Table structure for table `cabeceracompra` */

DROP TABLE IF EXISTS `cabeceracompra`;

CREATE TABLE `cabeceracompra` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `sucursal` int(11) DEFAULT NULL,
  `factura` int(11) DEFAULT NULL,
  `proveedores` int(11) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `cantidad` int(11) DEFAULT NULL,
  `pu` double(9,2) DEFAULT NULL,
  `subtotal` double(9,2) DEFAULT NULL,
  `iva` double(9,2) DEFAULT NULL,
  `total` double(9,2) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FK_cabeceracompra_proveedores` (`proveedores`),
  KEY `FK_cabeceracompra_sucursal` (`sucursal`),
  CONSTRAINT `FK_cabeceracompra_proveedores` FOREIGN KEY (`proveedores`) REFERENCES `proveedores` (`codigo`),
  CONSTRAINT `FK_cabeceracompra_sucursal` FOREIGN KEY (`sucursal`) REFERENCES `sucursal` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `cabeceracompra` */

/*Table structure for table `canton` */

DROP TABLE IF EXISTS `canton`;

CREATE TABLE `canton` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `canton` */

insert  into `canton`(`codigo`,`nombre`) values (1,'Quito'),(2,'Pedro Vicente Maldonado');

/*Table structure for table `clientes` */

DROP TABLE IF EXISTS `clientes`;

CREATE TABLE `clientes` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `identificacion` varchar(13) DEFAULT NULL,
  `apellidos` varchar(50) DEFAULT NULL,
  `nombres` varchar(50) DEFAULT NULL,
  `direccion` varchar(100) DEFAULT NULL,
  `telefono` varchar(50) DEFAULT NULL,
  `sector` int(11) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `usuario` varchar(100) DEFAULT NULL,
  `clave` varchar(100) DEFAULT NULL,
  `formapago` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FK_clientes_sector` (`sector`),
  CONSTRAINT `FK_clientes_sector` FOREIGN KEY (`sector`) REFERENCES `sector` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/*Data for the table `clientes` */

insert  into `clientes`(`codigo`,`identificacion`,`apellidos`,`nombres`,`direccion`,`telefono`,`sector`,`email`,`usuario`,`clave`,`formapago`) values (0,'9999999999999','CONSUMIDOR','FINAL','S/D','9999999999',1,'S/E','**********','',NULL),(1,'1717996134','Cahueñas','Angelica','Cotocollao','1234-567',1,'email','1717992267','LyGFRW9k08uTcgByx69OOg==',NULL),(2,'1309700548','Jadan','Geovanny','Condado Shopin','5255588',1,'','1309700548','aFHz+z8AQoFNQUxxZCdWHg==',NULL),(4,'1818181818001','Jadán','Carlos','Direccion','1111-111',1,'','1818181818','6MSLxTVFX3DpkOmvfWWROQ==',NULL),(5,'1919191919','JADAN','STALIN','DIRECCION','8888-888',1,'','1919191919','px3pGHZORlK5zyK/u7zcfg==',NULL),(6,'1714846043','gualotuña henriquez','juan carlos','el centenario mz.19 cs.10','2763-544',3,'thegualo@hotmail.com','1714846043','1t1YyPcnj3wSw4dxh9tEIw==',NULL);

/*Table structure for table `comisiones` */

DROP TABLE IF EXISTS `comisiones`;

CREATE TABLE `comisiones` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `plan` int(11) DEFAULT NULL,
  `vendedores` int(11) DEFAULT NULL,
  `porcentaje` double(9,2) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FK_comisiones_plan` (`plan`),
  KEY `FK_comisiones_vendedores` (`vendedores`),
  CONSTRAINT `FK_comisiones_plan` FOREIGN KEY (`plan`) REFERENCES `plan` (`codigo`),
  CONSTRAINT `FK_comisiones_vendedores` FOREIGN KEY (`vendedores`) REFERENCES `empleados` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

/*Data for the table `comisiones` */

insert  into `comisiones`(`codigo`,`plan`,`vendedores`,`porcentaje`) values (1,1,1,12.00),(2,2,1,3.00),(3,3,1,2.00),(4,4,1,1.00),(5,5,1,0.00),(6,1,2,13.00),(7,2,2,13.00),(8,3,2,13.00),(9,4,2,13.00),(10,5,2,13.00);

/*Table structure for table `contratos` */

DROP TABLE IF EXISTS `contratos`;

CREATE TABLE `contratos` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `estado` varchar(20) DEFAULT NULL,
  `sucursal` int(11) DEFAULT NULL,
  `contrato` varchar(15) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `empleados` int(11) DEFAULT NULL,
  `registrador` int(11) DEFAULT NULL,
  `instalador` int(11) DEFAULT NULL,
  `clientes` int(11) DEFAULT NULL,
  `plan` int(11) DEFAULT NULL,
  `formapago` int(11) DEFAULT NULL,
  `nocuenta` varchar(20) DEFAULT NULL,
  `tipocuenta` varchar(20) DEFAULT NULL,
  `diapago` int(11) DEFAULT NULL,
  `fechapago` date DEFAULT NULL,
  `ip` varchar(20) DEFAULT NULL,
  `usuario` varchar(30) DEFAULT NULL,
  `clave` varchar(30) DEFAULT NULL,
  `nodos` int(11) DEFAULT NULL,
  `autorizado` tinyint(1) DEFAULT NULL,
  `fechainstalacion` datetime DEFAULT NULL,
  `fechafinal` datetime DEFAULT NULL,
  `equipos1` int(11) DEFAULT NULL,
  `equipos2` int(11) DEFAULT NULL,
  `equipos3` int(11) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FK_contratos_nodos` (`nodos`),
  KEY `FK_contratos_clientes` (`clientes`),
  KEY `FK_contratos_plan` (`plan`),
  KEY `FK_contratos_equipos2` (`equipos2`),
  KEY `FK_contratos_equipos1` (`equipos1`),
  KEY `FK_contratos_equipos3` (`equipos3`),
  KEY `FK_contratos` (`empleados`),
  KEY `FK_contratos_registrador` (`registrador`),
  KEY `FK_contratos_instalador` (`instalador`),
  KEY `FK_contratos_sucursal` (`sucursal`),
  CONSTRAINT `FK_contratos` FOREIGN KEY (`empleados`) REFERENCES `empleados` (`codigo`),
  CONSTRAINT `FK_contratos_clientes` FOREIGN KEY (`clientes`) REFERENCES `clientes` (`codigo`),
  CONSTRAINT `FK_contratos_equipos1` FOREIGN KEY (`equipos1`) REFERENCES `detallecompra` (`codigo`),
  CONSTRAINT `FK_contratos_equipos2` FOREIGN KEY (`equipos2`) REFERENCES `detallecompra` (`codigo`),
  CONSTRAINT `FK_contratos_equipos3` FOREIGN KEY (`equipos3`) REFERENCES `detallecompra` (`codigo`),
  CONSTRAINT `FK_contratos_instalador` FOREIGN KEY (`instalador`) REFERENCES `empleados` (`codigo`),
  CONSTRAINT `FK_contratos_nodos` FOREIGN KEY (`nodos`) REFERENCES `nodos` (`codigo`),
  CONSTRAINT `FK_contratos_plan` FOREIGN KEY (`plan`) REFERENCES `plan` (`codigo`),
  CONSTRAINT `FK_contratos_registrador` FOREIGN KEY (`registrador`) REFERENCES `empleados` (`codigo`),
  CONSTRAINT `FK_contratos_sucursal` FOREIGN KEY (`sucursal`) REFERENCES `sucursal` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `contratos` */

insert  into `contratos`(`codigo`,`estado`,`sucursal`,`contrato`,`fecha`,`empleados`,`registrador`,`instalador`,`clientes`,`plan`,`formapago`,`nocuenta`,`tipocuenta`,`diapago`,`fechapago`,`ip`,`usuario`,`clave`,`nodos`,`autorizado`,`fechainstalacion`,`fechafinal`,`equipos1`,`equipos2`,`equipos3`) values (1,'Activo',NULL,'000001','2011-05-04',2,1,NULL,2,1,NULL,NULL,NULL,NULL,NULL,'ip','usu','clave',1,0,'2011-05-04 13:28:14',NULL,2,2,3);

/*Table structure for table `contratosplanes` */

DROP TABLE IF EXISTS `contratosplanes`;

CREATE TABLE `contratosplanes` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `plan` int(11) DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `contratosplanes` */

/*Table structure for table `cxcobrar` */

DROP TABLE IF EXISTS `cxcobrar`;

CREATE TABLE `cxcobrar` (
  `codigo` varchar(16) NOT NULL,
  `factura` varchar(16) DEFAULT NULL,
  `debe` decimal(9,2) DEFAULT NULL,
  `haber` decimal(9,2) DEFAULT NULL,
  `total` decimal(9,2) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `tipo` varchar(10) DEFAULT NULL,
  `efectivo` double(9,2) DEFAULT NULL,
  `cheque` decimal(9,2) DEFAULT NULL,
  `debito` decimal(9,2) DEFAULT NULL,
  `tarjeta` decimal(9,2) DEFAULT NULL,
  `nocheque` decimal(9,2) DEFAULT NULL,
  `notarjeta` decimal(9,2) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FK_cxcobrar_factura` (`factura`),
  CONSTRAINT `FK_cxcobrar_factura` FOREIGN KEY (`factura`) REFERENCES `factura` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `cxcobrar` */

/*Table structure for table `detalle` */

DROP TABLE IF EXISTS `detalle`;

CREATE TABLE `detalle` (
  `codigo` varchar(20) NOT NULL,
  `factura` varchar(50) DEFAULT NULL,
  `plan` int(11) DEFAULT NULL,
  `equipos` int(11) DEFAULT NULL,
  `mes` int(2) DEFAULT NULL,
  `cantidad` int(2) DEFAULT NULL,
  `precio` decimal(9,2) DEFAULT NULL,
  `descuento` decimal(8,2) DEFAULT NULL,
  `beca` decimal(8,2) DEFAULT NULL,
  `total` decimal(9,2) DEFAULT NULL,
  `descripcion` varchar(100) DEFAULT NULL,
  `anio` int(10) DEFAULT NULL,
  `asignado` int(11) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FK_producto` (`plan`),
  KEY `FK_detalle` (`factura`),
  KEY `FK_detalle_equipos` (`equipos`),
  CONSTRAINT `FK_detalle` FOREIGN KEY (`factura`) REFERENCES `factura` (`codigo`),
  CONSTRAINT `FK_detalle_equipos` FOREIGN KEY (`equipos`) REFERENCES `equipos` (`codigo`),
  CONSTRAINT `FK_detalle_plan_oproducto` FOREIGN KEY (`plan`) REFERENCES `plan` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `detalle` */

/*Table structure for table `detallecompra` */

DROP TABLE IF EXISTS `detallecompra`;

CREATE TABLE `detallecompra` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `compra` int(11) DEFAULT NULL,
  `equipos` int(11) DEFAULT NULL,
  `cantidad` int(11) DEFAULT NULL,
  `costo` double(9,2) DEFAULT NULL,
  `pvp1` double(9,2) DEFAULT NULL,
  `pvp2` double(9,2) DEFAULT NULL,
  `pvp3` double(9,2) DEFAULT NULL,
  `pvp4` double(9,2) DEFAULT NULL,
  `asignado` tinyint(1) DEFAULT NULL,
  `estado` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FK_controlequipos` (`equipos`),
  KEY `FK_detallecompra_cabecompra` (`compra`),
  CONSTRAINT `FK_controlequipos` FOREIGN KEY (`equipos`) REFERENCES `equipos` (`codigo`),
  CONSTRAINT `FK_detallecompra_cabecompra` FOREIGN KEY (`compra`) REFERENCES `cabeceracompra` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `detallecompra` */

insert  into `detallecompra`(`codigo`,`compra`,`equipos`,`cantidad`,`costo`,`pvp1`,`pvp2`,`pvp3`,`pvp4`,`asignado`,`estado`) values (1,NULL,3,NULL,89.00,NULL,NULL,NULL,NULL,0,NULL),(2,NULL,2,NULL,90.00,NULL,NULL,NULL,NULL,0,NULL),(3,NULL,3,NULL,0.00,NULL,NULL,NULL,NULL,0,NULL),(4,NULL,1,NULL,0.00,NULL,NULL,NULL,NULL,0,NULL);

/*Table structure for table `empleados` */

DROP TABLE IF EXISTS `empleados`;

CREATE TABLE `empleados` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `identificacion` varchar(13) DEFAULT NULL,
  `apellidos` varchar(50) DEFAULT NULL,
  `nombres` varchar(50) DEFAULT NULL,
  `direccion` varchar(50) DEFAULT NULL,
  `telefono` varchar(50) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `perfil` int(11) DEFAULT NULL,
  `usuario` varchar(50) DEFAULT NULL,
  `clave` varchar(100) DEFAULT NULL,
  `estado` tinyint(1) DEFAULT '1',
  `tipo` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FK_empleados_perfil` (`perfil`),
  CONSTRAINT `FK_empleados_perfil` FOREIGN KEY (`perfil`) REFERENCES `perfil` (`codigo`) ON DELETE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `empleados` */

insert  into `empleados`(`codigo`,`identificacion`,`apellidos`,`nombres`,`direccion`,`telefono`,`email`,`perfil`,`usuario`,`clave`,`estado`,`tipo`) values (1,'1309700548','JADAN CAHUEÑAS','SAMI ROMINA','COTOCOLLAO','5103843','jcinform@gmail.com',1,'geova','F1FHsX1prCg=',1,NULL),(2,'1717996134','Bastidas','Miguel','direccion','5555-555','email',2,'miguel','aHBLYnhpcY4=',1,NULL);

/*Table structure for table `empleadossucursal` */

DROP TABLE IF EXISTS `empleadossucursal`;

CREATE TABLE `empleadossucursal` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `sucursal` int(11) DEFAULT NULL,
  `empleados` int(11) DEFAULT NULL,
  `estado` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`codigo`),
  KEY `FK_empleadossector_sector` (`sucursal`),
  KEY `FK_empleadossector_empleado` (`empleados`),
  CONSTRAINT `FK_empleadossector_empleado` FOREIGN KEY (`empleados`) REFERENCES `empleados` (`codigo`),
  CONSTRAINT `FK_empleadossector_sucursal` FOREIGN KEY (`sucursal`) REFERENCES `sucursal` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/*Data for the table `empleadossucursal` */

insert  into `empleadossucursal`(`codigo`,`sucursal`,`empleados`,`estado`) values (1,1,1,1),(2,2,1,1),(3,3,1,0),(4,1,2,0),(5,2,2,0),(6,3,2,0);

/*Table structure for table `empresa` */

DROP TABLE IF EXISTS `empresa`;

CREATE TABLE `empresa` (
  `ruc` varchar(13) NOT NULL,
  `razonsocial` varchar(100) DEFAULT NULL,
  `representante` varchar(100) DEFAULT NULL,
  `direccion` varchar(100) DEFAULT NULL,
  `telefono` varchar(50) DEFAULT NULL,
  `telefono2` varchar(50) DEFAULT NULL,
  `movil` varchar(50) DEFAULT NULL,
  `documento` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `web` varchar(50) DEFAULT NULL,
  `reportes` varchar(200) DEFAULT NULL,
  `fotos` varchar(200) DEFAULT NULL,
  `usuariomail` varchar(200) DEFAULT NULL,
  `clavemail` varchar(200) DEFAULT NULL,
  `autorizacion` tinyint(1) DEFAULT NULL,
  `smtp` varchar(20) DEFAULT NULL,
  `puerto` varchar(20) DEFAULT NULL,
  `logo` varchar(200) DEFAULT NULL,
  `star` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`ruc`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `empresa` */

insert  into `empresa`(`ruc`,`razonsocial`,`representante`,`direccion`,`telefono`,`telefono2`,`movil`,`documento`,`email`,`web`,`reportes`,`fotos`,`usuariomail`,`clavemail`,`autorizacion`,`smtp`,`puerto`,`logo`,`star`) values ('1309700548001','JCINFORM SOLUCIONES INFORMATICAS','GEOVANNY JADAN','DIRECCION','TELEFONO','51515','561551','000001','png','www.jcinform.com','c:\\reportesisp\\','c:\\fotosisp\\','email','claveemail',1,'smt.gmail','587',NULL,1);

/*Table structure for table `equipos` */

DROP TABLE IF EXISTS `equipos`;

CREATE TABLE `equipos` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) DEFAULT NULL,
  `modelo` varchar(50) DEFAULT NULL,
  `tipo` varchar(50) DEFAULT NULL,
  `caracteristica` varchar(50) DEFAULT NULL,
  `marcas` int(11) DEFAULT NULL,
  `costo` double(9,2) DEFAULT NULL,
  `pvp1` double(9,2) DEFAULT NULL,
  `pvp2` double(9,2) DEFAULT NULL,
  `pvp3` double(9,2) DEFAULT NULL,
  `pvp4` double(9,2) DEFAULT NULL,
  `bien` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FK_equipos_marcas` (`marcas`),
  CONSTRAINT `FK_equipos_marcas` FOREIGN KEY (`marcas`) REFERENCES `marcas` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `equipos` */

insert  into `equipos`(`codigo`,`nombre`,`modelo`,`tipo`,`caracteristica`,`marcas`,`costo`,`pvp1`,`pvp2`,`pvp3`,`pvp4`,`bien`) values (1,'Router','6765',NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL),(2,'Switch','8677',NULL,NULL,2,NULL,NULL,NULL,NULL,NULL,NULL),(3,'Antena','677',NULL,NULL,2,NULL,NULL,NULL,NULL,NULL,NULL);

/*Table structure for table `factura` */

DROP TABLE IF EXISTS `factura`;

CREATE TABLE `factura` (
  `codigo` varchar(50) NOT NULL,
  `sucursal` int(11) DEFAULT NULL,
  `numero` varchar(16) DEFAULT NULL,
  `sector` int(11) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `clientes` int(11) DEFAULT NULL,
  `total` decimal(9,2) DEFAULT NULL,
  `estado` tinyint(1) DEFAULT NULL,
  `observacion` varchar(500) DEFAULT NULL,
  `efectivo` decimal(9,2) DEFAULT NULL,
  `deposito` decimal(9,2) DEFAULT NULL,
  `cheque` decimal(9,2) DEFAULT NULL,
  `banco` varchar(50) DEFAULT NULL,
  `nocuenta` varchar(50) DEFAULT NULL,
  `nocheque` varchar(50) DEFAULT NULL,
  `tarjeta` decimal(9,2) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FK_cabecerafactura` (`clientes`),
  KEY `FK_factura_sector` (`sector`),
  KEY `FK_factura_sucursal` (`sucursal`),
  CONSTRAINT `FK_factura_clientes` FOREIGN KEY (`clientes`) REFERENCES `clientes` (`codigo`),
  CONSTRAINT `FK_factura_sector` FOREIGN KEY (`sector`) REFERENCES `sector` (`codigo`),
  CONSTRAINT `FK_factura_sucursal` FOREIGN KEY (`sucursal`) REFERENCES `sucursal` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `factura` */

/*Table structure for table `marcas` */

DROP TABLE IF EXISTS `marcas`;

CREATE TABLE `marcas` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `marcas` */

insert  into `marcas`(`codigo`,`nombre`) values (1,'TRENDNET'),(2,'DLINK');

/*Table structure for table `nodos` */

DROP TABLE IF EXISTS `nodos`;

CREATE TABLE `nodos` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) DEFAULT NULL,
  `ip` varchar(20) DEFAULT NULL,
  `sector` int(11) DEFAULT NULL,
  `usuario` varchar(50) DEFAULT NULL,
  `clave` varchar(50) DEFAULT NULL,
  `ssid` varchar(50) DEFAULT NULL,
  `seguridad` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FK_nodos_sector` (`sector`),
  CONSTRAINT `FK_nodos_sector` FOREIGN KEY (`sector`) REFERENCES `sector` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `nodos` */

insert  into `nodos`(`codigo`,`nombre`,`ip`,`sector`,`usuario`,`clave`,`ssid`,`seguridad`) values (1,'Pedro Vic.','192.168.0.52',1,'.','','.','.');

/*Table structure for table `perfil` */

DROP TABLE IF EXISTS `perfil`;

CREATE TABLE `perfil` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `perfil` */

insert  into `perfil`(`codigo`,`nombre`) values (1,'SUPER ADMINISTRADOR'),(2,'ADMINISTRADOR'),(5,'VENDEDOR');

/*Table structure for table `plan` */

DROP TABLE IF EXISTS `plan`;

CREATE TABLE `plan` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) DEFAULT NULL,
  `valor` double(9,2) DEFAULT NULL,
  `pvp1` double(9,2) DEFAULT NULL,
  `pvp2` double(9,2) DEFAULT NULL,
  `pvp3` double(9,2) DEFAULT NULL,
  `pvp4` double(9,2) DEFAULT NULL,
  `tipo` varchar(50) DEFAULT NULL,
  `dias` int(11) DEFAULT NULL,
  `bien` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `plan` */

insert  into `plan`(`codigo`,`nombre`,`valor`,`pvp1`,`pvp2`,`pvp3`,`pvp4`,`tipo`,`dias`,`bien`) values (1,'ADSL 256 KB',15.00,NULL,NULL,NULL,NULL,'BASICO',30,0),(2,'ADSL 512 KB',20.00,NULL,NULL,NULL,NULL,'',0,0),(3,'ADSL 1GB',50.00,NULL,NULL,NULL,NULL,'HOME',0,0),(4,'REINSTALACION SERVICIO',25.00,NULL,NULL,NULL,NULL,'',0,0),(5,'SERVICIO TECNICO',15.00,NULL,NULL,NULL,NULL,'',0,0);

/*Table structure for table `proveedores` */

DROP TABLE IF EXISTS `proveedores`;

CREATE TABLE `proveedores` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `identificacion` varchar(13) DEFAULT NULL,
  `razonsocial` varchar(100) DEFAULT NULL,
  `representante` varchar(100) DEFAULT NULL,
  `direccion` varchar(100) DEFAULT NULL,
  `telefono` varchar(50) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `proveedores` */

insert  into `proveedores`(`codigo`,`identificacion`,`razonsocial`,`representante`,`direccion`,`telefono`,`email`) values (1,'1309700548','JCINFORM SOLUCIONES INFORMATICAS','El representante','DIRECCION','5154-544','EMIAL');

/*Table structure for table `radios` */

DROP TABLE IF EXISTS `radios`;

CREATE TABLE `radios` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) DEFAULT NULL,
  `nodos` int(11) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FK_radios_radios` (`nodos`),
  CONSTRAINT `FK_radios_radios` FOREIGN KEY (`nodos`) REFERENCES `nodos` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `radios` */

insert  into `radios`(`codigo`,`nombre`,`nodos`) values (1,'RADIO',1);

/*Table structure for table `sector` */

DROP TABLE IF EXISTS `sector`;

CREATE TABLE `sector` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) DEFAULT NULL,
  `canton` int(11) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FK_sector_canton` (`canton`),
  CONSTRAINT `FK_sector_canton` FOREIGN KEY (`canton`) REFERENCES `canton` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `sector` */

insert  into `sector`(`codigo`,`nombre`,`canton`) values (1,'COTOCOLLAO',1),(2,'CONDADO',1),(3,'Centro',2),(4,'Sur',1),(5,'Centro',1);

/*Table structure for table `series` */

DROP TABLE IF EXISTS `series`;

CREATE TABLE `series` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `serie` varchar(50) DEFAULT NULL,
  `detallecompra` int(11) DEFAULT NULL,
  `estado` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FK_series_detallecompra` (`detallecompra`),
  CONSTRAINT `FK_series_detallecompra` FOREIGN KEY (`detallecompra`) REFERENCES `detallecompra` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `series` */

/*Table structure for table `soporte` */

DROP TABLE IF EXISTS `soporte`;

CREATE TABLE `soporte` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `clientes` int(11) DEFAULT NULL,
  `fecha` datetime DEFAULT NULL,
  `observacion` longtext,
  `observacion2` longtext,
  `observacion3` longtext,
  PRIMARY KEY (`codigo`),
  KEY `FK_soporte_clientes` (`clientes`),
  CONSTRAINT `FK_soporte_clientes` FOREIGN KEY (`clientes`) REFERENCES `clientes` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `soporte` */

/*Table structure for table `sucursal` */

DROP TABLE IF EXISTS `sucursal`;

CREATE TABLE `sucursal` (
  `codigo` int(11) NOT NULL,
  `serie1` varchar(3) DEFAULT NULL,
  `serie2` varchar(3) DEFAULT NULL,
  `descripcion` varchar(30) DEFAULT NULL,
  `secfactura` int(11) DEFAULT NULL,
  `seccontrato` int(11) DEFAULT NULL,
  `seccompras` int(11) DEFAULT NULL,
  `empresa` varchar(13) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  UNIQUE KEY `UNQ_invbodegas_0` (`codigo`),
  KEY `FK_invbodegas_glbemp_codigo` (`empresa`),
  KEY `FK_invbodegas_cc_secuencial` (`secfactura`),
  CONSTRAINT `FK_sucursal_empresa` FOREIGN KEY (`empresa`) REFERENCES `empresa` (`ruc`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `sucursal` */

insert  into `sucursal`(`codigo`,`serie1`,`serie2`,`descripcion`,`secfactura`,`seccontrato`,`seccompras`,`empresa`) values (1,'001','002','PRINCIPAL',0,0,0,'1309700548001'),(2,'001','001','PEDRO VICENTE',0,0,0,'1309700548001'),(3,'001','003','OTRA BODEGA',0,0,0,'1309700548001');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
