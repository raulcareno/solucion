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
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=latin1;

/*Data for the table `accesos` */

insert  into `accesos`(`codigoacc`,`perfil`,`modulo`,`guardar`,`eliminar`,`actualizar`,`ingresar`,`grupo`) values (1,NULL,'Accesos',1,1,1,1,NULL),(2,NULL,'Auditoria',1,1,1,1,NULL),(3,NULL,'Bancos',1,1,1,1,''),(4,NULL,'Compras',1,1,1,1,NULL),(5,NULL,'Cantones',1,1,1,1,NULL),(6,NULL,'Clientes',1,1,1,1,NULL),(7,NULL,'Comisiones',1,1,1,1,NULL),(8,NULL,'Contratos',1,1,1,1,NULL),(9,NULL,'Descuentos',1,1,1,1,NULL),(10,NULL,'Empleados',1,1,1,1,NULL),(11,NULL,'Empresa',1,1,1,1,NULL),(12,NULL,'Equipos',1,1,1,1,NULL),(13,NULL,'Facturacion',1,1,1,1,NULL),(14,NULL,'Marcas',1,1,1,1,NULL),(15,NULL,'Nodos',1,1,1,1,NULL),(16,NULL,'Perfiles',1,1,1,1,NULL),(17,NULL,'Generacion Cobros',1,1,1,1,NULL),(18,NULL,'Proveedores',1,1,1,1,NULL),(19,NULL,'Radios',1,1,1,1,NULL),(20,NULL,'Sectores',1,1,1,1,NULL),(21,NULL,'Planes',1,1,1,1,NULL),(22,NULL,'EmpleadosSectores',1,1,1,1,NULL),(23,NULL,'Reportes',1,1,1,1,NULL),(24,NULL,'Soporte',1,1,1,1,NULL),(25,NULL,'Registro de Cobros',1,1,1,1,NULL),(26,NULL,'RealizarCobros',1,1,1,1,''),(27,1,'Auditoria',1,1,1,1,''),(28,1,'Bancos',1,1,1,1,''),(29,1,'Compras',1,1,1,1,''),(30,1,'Cantones',1,1,1,1,''),(31,1,'Clientes',1,1,1,1,''),(32,1,'Comisiones',1,1,1,1,''),(33,1,'Contratos',1,1,1,1,''),(34,1,'Descuentos',1,1,1,1,''),(35,1,'Empleados',1,1,1,1,''),(36,1,'Empresa',1,1,1,1,''),(37,1,'Equipos',1,1,1,1,''),(38,1,'Facturacion',1,1,1,1,''),(39,1,'Marcas',1,1,1,1,''),(40,1,'Nodos',1,1,1,1,''),(41,1,'Perfiles',1,1,1,1,''),(42,1,'Generacion Cobros',1,1,1,1,''),(43,1,'Proveedores',1,1,1,1,''),(44,1,'Radios',1,1,1,1,''),(45,1,'Sectores',1,1,1,1,''),(46,1,'Planes',1,1,1,1,''),(47,1,'EmpleadosSectores',1,1,1,1,''),(48,1,'Reportes',1,1,1,1,''),(49,1,'Soporte',1,1,1,1,''),(50,1,'Registro de Cobros',1,1,1,1,''),(51,2,'Accesos',1,1,1,1,''),(52,2,'Auditoria',1,1,1,1,''),(53,2,'Bancos',1,1,1,1,''),(54,2,'Compras',1,1,1,1,''),(55,2,'Cantones',1,1,1,1,''),(56,2,'Clientes',1,1,1,1,''),(57,2,'Comisiones',1,1,1,1,''),(58,2,'Contratos',1,1,1,1,''),(59,2,'Descuentos',1,1,1,1,''),(60,2,'Empleados',1,1,1,1,''),(61,2,'Empresa',1,1,1,1,''),(62,2,'Equipos',1,1,1,1,''),(63,2,'Facturacion',1,1,1,1,''),(64,2,'Marcas',1,1,1,1,''),(65,2,'Nodos',1,1,1,1,''),(66,2,'Perfiles',1,1,1,1,''),(67,2,'Generacion Cobros',1,1,1,1,''),(68,2,'Proveedores',1,1,1,1,''),(69,2,'Radios',1,1,1,1,''),(70,2,'Sectores',1,1,1,1,''),(71,2,'Planes',1,1,1,1,''),(72,2,'EmpleadosSectores',1,1,1,1,''),(73,2,'Reportes',1,1,1,1,''),(74,2,'Soporte',1,1,1,1,''),(75,2,'Registro de Cobros',1,1,1,1,''),(76,1,'Accesos',1,1,1,1,''),(77,1,'RealizarCobros',1,1,1,1,''),(78,2,'RealizarCobros',1,1,1,1,'');

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
) ENGINE=InnoDB AUTO_INCREMENT=558 DEFAULT CHARSET=latin1;

/*Data for the table `auditoria` */

insert  into `auditoria`(`codigo`,`empleado`,`fecha`,`tipo`,`ip`,`pc`,`tabla`) values (1,1,'2011-04-17 23:05:13','-','127.0.0.1','Ingreso Sistema','-'),(2,1,'2011-04-17 23:05:48','-','127.0.0.1','Ingreso Sistema','-'),(3,1,'2011-04-18 08:14:33','-','127.0.0.1','Ingreso Sistema','-'),(4,1,'2011-04-18 08:15:56','-','127.0.0.1','Ingreso Sistema','-'),(5,1,'2011-04-18 09:01:24','-','127.0.0.1','Ingreso Sistema','-'),(6,1,'2011-04-18 09:03:45','-','127.0.0.1','Ingreso Sistema','-'),(7,1,'2011-04-18 09:06:58','-','127.0.0.1','Ingreso Sistema','-'),(8,1,'2011-04-18 09:10:14','-','127.0.0.1','Ingreso Sistema','-'),(9,1,'2011-04-18 09:55:46','-','127.0.0.1','Ingreso Sistema','-'),(10,1,'2011-04-18 10:09:22','-','127.0.0.1','Ingreso Sistema','-'),(11,1,'2011-04-18 10:14:05','Guardar','127.0.0.1','persistencia.Perfil[codigo=1]','Accesos'),(12,1,'2011-04-18 10:19:26','-','127.0.0.1','Ingreso Sistema','-'),(13,1,'2011-04-18 10:19:48','Guardar','127.0.0.1','persistencia.Perfil[codigo=1]','Accesos'),(14,1,'2011-04-18 10:20:45','Guardar','127.0.0.1','persistencia.Perfil[codigo=1]','Accesos'),(15,1,'2011-04-18 10:23:42','-','127.0.0.1','Ingreso Sistema','-'),(16,1,'2011-04-18 10:23:59','Guardar','127.0.0.1','persistencia.Perfil[codigo=4]','Accesos'),(17,1,'2011-04-18 10:31:32','-','127.0.0.1','Ingreso Sistema','-'),(18,1,'2011-04-18 10:34:20','-','127.0.0.1','Ingreso Sistema','-'),(19,1,'2011-04-18 10:36:47','Guardar','127.0.0.1','persistencia.Perfil[codigo=4]','Accesos'),(20,1,'2011-04-18 10:42:02','Guardar','127.0.0.1','persistencia.Perfil[codigo=3]','Accesos'),(21,1,'2011-04-18 10:42:08','Guardar','127.0.0.1','persistencia.Perfil[codigo=2]','Accesos'),(22,1,'2011-04-18 10:49:34','-','127.0.0.1','Ingreso Sistema','-'),(23,1,'2011-04-18 10:51:20','-','127.0.0.1','Ingreso Sistema','-'),(24,1,'2011-04-18 11:04:09','-','127.0.0.1','Ingreso Sistema','-'),(25,1,'2011-04-18 11:20:15','-','127.0.0.1','Ingreso Sistema','-'),(26,1,'2011-04-18 11:26:16','Guardar','127.0.0.1','JCINFORM SOLUCIONES INFORMATICAS','Empresa'),(27,1,'2011-04-18 11:28:50','Guardar','127.0.0.1','JCINFORM SOLUCIONES INFORMATICAS','Empresa'),(28,1,'2011-04-18 11:37:56','-','127.0.0.1','Ingreso Sistema','-'),(29,1,'2011-04-18 11:39:20','Guardar','127.0.0.1','JCINFORM SOLUCIONES INFORMATICAS','Empresa'),(30,1,'2011-04-18 11:40:39','Guardar','127.0.0.1','JCINFORM SOLUCIONES INFORMATICAS','Empresa'),(31,1,'2011-04-18 11:45:35','Guardar','127.0.0.1','JCINFORM SOLUCIONES INFORMATICAS','Empresa'),(32,1,'2011-04-18 11:51:55','Guardar','127.0.0.1','JCINFORM SOLUCIONES INFORMATICAS','Empresa'),(33,1,'2011-04-18 12:05:51','Guardar','127.0.0.1','JCINFORM SOLUCIONES INFORMATICAS','Empresa'),(34,1,'2011-04-18 12:14:14','Actualizar','127.0.0.1','2 - Pedro Vicente Maldonado','Cantones'),(35,1,'2011-04-18 12:14:37','Guardar','127.0.0.1','3 - Santo Domingo','Cantones'),(36,1,'2011-04-18 12:15:22','Actualizar','127.0.0.1','3 - Santo Domingo de los Tsachilas','Cantones'),(37,1,'2011-04-18 12:15:29','Eliminar','127.0.0.1','3 - Santo Domingo de los Tsachilas','Cantones'),(38,1,'2011-04-18 12:18:07','Actualizar','127.0.0.1','3 - FACTURADOR','Perfiles'),(39,1,'2011-04-18 12:18:50','Eliminar','127.0.0.1','4 - VENDEDOR','Perfiles'),(40,1,'2011-04-18 12:18:54','Eliminar','127.0.0.1','3 - FACTURADOR','Perfiles'),(41,1,'2011-04-18 12:19:03','Guardar','127.0.0.1','5 - VENDEDOR','Perfiles'),(42,1,'2011-04-18 12:39:38','Guardar','127.0.0.1','3 - Centro','Sectores'),(43,1,'2011-04-18 12:44:04','Actualizar','127.0.0.1','3 - Centro','Sectores'),(44,1,'2011-04-18 12:44:35','Actualizar','127.0.0.1','3 - Centro','Sectores'),(45,1,'2011-04-18 13:31:17','-','127.0.0.1','Ingreso Sistema','-'),(46,1,'2011-04-18 13:36:24','Guardar','127.0.0.1',' JCINFORM SOLUCIONES INFORMATICAS','Proveedores'),(47,1,'2011-04-18 13:38:53','Guardar','127.0.0.1',' JCINFORM SOLUCIONES INFORMATICAS','Proveedores'),(48,1,'2011-04-18 13:39:58','Guardar','127.0.0.1',' JCINFORM SOLUCIONES INFORMATICAS','Proveedores'),(49,1,'2011-04-18 13:41:23','Guardar','127.0.0.1',' CONTADORES ASOCIADOS','Proveedores'),(50,1,'2011-04-18 13:42:14','Guardar','127.0.0.1',' asdf','Proveedores'),(51,1,'2011-04-18 13:42:31','Guardar','127.0.0.1',' asdf','Proveedores'),(52,1,'2011-04-18 13:42:42','Eliminar','127.0.0.1','asdf','Proveedores'),(53,1,'2011-04-18 13:54:17','Guardar','127.0.0.1','persistencia.Perfil[codigo=1]','Accesos'),(54,1,'2011-04-18 13:54:27','-','127.0.0.1','Ingreso Sistema','-'),(55,1,'2011-04-18 13:56:13','Guardar','127.0.0.1','JADAN ORTEGA GEOVANNY','Empleados'),(56,1,'2011-04-18 13:56:47','Guardar','127.0.0.1','Cahueñas Angelica','Empleados'),(57,1,'2011-04-18 13:58:16','-','127.0.0.1','Ingreso Sistema','-'),(58,1,'2011-04-18 14:34:24','-','127.0.0.1','Ingreso Sistema','-'),(59,1,'2011-04-18 16:35:47','-','127.0.0.1','Ingreso Sistema','-'),(60,1,'2011-04-18 20:27:34','-','127.0.0.1','Ingreso Sistema','-'),(61,1,'2011-04-18 20:51:03','-','127.0.0.1','Ingreso Sistema','-'),(62,1,'2011-04-18 21:02:26','-','127.0.0.1','Ingreso Sistema','-'),(63,1,'2011-04-18 21:08:51','-','127.0.0.1','Ingreso Sistema','-'),(64,1,'2011-04-18 21:12:34','Guardar','127.0.0.1','ApeCliente Cliente','Clientes'),(65,1,'2011-04-18 21:13:00','Guardar','127.0.0.1','ApeCliente Cliente','Clientes'),(66,1,'2011-04-18 21:13:17','Guardar','127.0.0.1','ApeCliente Cliente','Clientes'),(67,1,'2011-04-18 21:13:57','Guardar','127.0.0.1','ApeCliente Cliente','Clientes'),(68,1,'2011-04-18 21:15:35','Eliminar','127.0.0.1','2 2','Clientes'),(69,1,'2011-04-18 21:19:18','Guardar','127.0.0.1','1 - TRENDNET','Marcas'),(70,1,'2011-04-18 21:19:26','Guardar','127.0.0.1','2 - DLINK','Marcas'),(71,1,'2011-04-18 21:19:32','Guardar','127.0.0.1','3 - OTRO','Marcas'),(72,1,'2011-04-18 21:19:36','Eliminar','127.0.0.1','3 - OTRO','Marcas'),(73,1,'2011-04-25 22:47:17','-','127.0.0.1','Ingreso Sistema','-'),(74,1,'2011-04-25 22:48:02','Guardar','127.0.0.1','Router Router','Equipos'),(75,1,'2011-04-25 23:10:00','Guardar','127.0.0.1','Switch Switch','Equipos'),(76,1,'2011-04-25 23:12:37','Guardar','127.0.0.1','Antena Antena','Equipos'),(77,1,'2011-04-26 11:00:30','-','127.0.0.1','Ingreso Sistema','-'),(78,1,'2011-04-26 11:04:11','-','127.0.0.1','Ingreso Sistema','-'),(79,1,'2011-04-26 11:38:45','Guardar','127.0.0.1','cantidad2Tue Apr 26 11:38:24 COT 2011','Controlequipos'),(80,1,'2011-04-26 18:02:54','-','127.0.0.1','Ingreso Sistema','-'),(81,1,'2011-04-26 18:14:07','Actualizar','127.0.0.1','3 - Centro','Sectores'),(82,1,'2011-04-26 18:24:39','Actualizar','127.0.0.1','2 - CONDADO','Sectores'),(83,1,'2011-04-26 18:24:55','Actualizar','127.0.0.1','1 - COTOCOLLAO','Sectores'),(84,1,'2011-04-26 18:45:07','Guardar','127.0.0.1','SUPER ADMINISTRADOR','Accesos'),(85,1,'2011-04-26 18:46:42','Guardar','127.0.0.1','SUPER ADMINISTRADOR','Accesos'),(86,1,'2011-04-26 18:47:11','-','127.0.0.1','Ingreso Sistema','-'),(87,1,'2011-04-26 18:49:05','Guardar','127.0.0.1','1 - ADSL 256 kb','Planes'),(88,1,'2011-04-26 18:51:01','Actualizar','127.0.0.1','1 - ADSL 256 kb','Planes'),(89,1,'2011-04-26 18:53:50','Actualizar','127.0.0.1','1 - ADSL 256 kb','Planes'),(90,1,'2011-04-26 18:54:27','Guardar','127.0.0.1','2 - ADSL 512 KB','Planes'),(91,1,'2011-04-26 18:56:37','Actualizar','127.0.0.1','2 - ADSL 512 KB','Planes'),(92,1,'2011-04-26 18:57:09','Guardar','127.0.0.1','3 - ADSL 1GB','Planes'),(93,1,'2011-04-26 18:57:58','Guardar','127.0.0.1','4 - REINSTALACION SERVICIO','Planes'),(94,1,'2011-04-26 18:58:48','Guardar','127.0.0.1','5 - SERVICIO TECNICO','Planes'),(95,1,'2011-04-26 19:57:10','-','127.0.0.1','Ingreso Sistema','-'),(96,1,'2011-04-26 20:31:12','Guardar','127.0.0.1','persistencia.Empleados[codigo=1]','Comisiones'),(97,1,'2011-04-26 20:31:37','Guardar','127.0.0.1','persistencia.Empleados[codigo=1]','Comisiones'),(98,1,'2011-04-26 20:31:58','Guardar','127.0.0.1','persistencia.Empleados[codigo=2]','Comisiones'),(99,1,'2011-04-26 20:35:02','Guardar','127.0.0.1','persistencia.Empleados[codigo=1]','Comisiones'),(100,1,'2011-04-26 20:36:18','Guardar','127.0.0.1','persistencia.Empleados[codigo=2]','Comisiones'),(101,1,'2011-04-26 22:16:44','-','127.0.0.1','Ingreso Sistema','-'),(102,1,'2011-04-26 22:31:22','Guardar','127.0.0.1','persistencia.Empleados[codigo=2]','SectoresEmpleados'),(103,1,'2011-04-26 22:31:41','Guardar','127.0.0.1','persistencia.Empleados[codigo=1]','SectoresEmpleados'),(104,1,'2011-04-26 22:44:23','-','127.0.0.1','Ingreso Sistema','-'),(105,1,'2011-04-26 22:49:31','-','127.0.0.1','Ingreso Sistema','-'),(106,1,'2011-04-26 22:50:48','Guardar','127.0.0.1','persistencia.Empleados[codigo=2]','SectoresEmpleados'),(107,1,'2011-04-26 22:55:01','-','127.0.0.1','Ingreso Sistema','-'),(108,1,'2011-04-26 22:59:26','Guardar','127.0.0.1','SUPER ADMINISTRADOR','Accesos'),(109,1,'2011-04-26 23:00:20','-','127.0.0.1','Ingreso Sistema','-'),(110,1,'2011-04-26 23:00:27','Guardar','127.0.0.1','persistencia.Empleados[codigo=1]','SectoresEmpleados'),(111,1,'2011-05-02 09:41:12','-','127.0.0.1','Ingreso Sistema','-'),(112,1,'2011-05-02 09:44:37','Guardar','127.0.0.1','persistencia.Empleados[codigo=1]','Comisiones'),(113,1,'2011-05-02 10:22:32','-','127.0.0.1','Ingreso Sistema','-'),(114,1,'2011-05-02 10:26:46','Actualizar','127.0.0.1','1 - ADSL 256 KB','Planes'),(115,1,'2011-05-02 10:54:30','-','127.0.0.1','Ingreso Sistema','-'),(116,1,'2011-05-02 11:40:47','Guardar','127.0.0.1','micliente Mi Cliente','Clientes'),(117,1,'2011-05-02 11:47:39','-','127.0.0.1','Ingreso Sistema','-'),(118,1,'2011-05-02 14:17:45','-','127.0.0.1','Ingreso Sistema','-'),(119,1,'2011-05-02 20:15:43','-','127.0.0.1','Ingreso Sistema','-'),(120,1,'2011-05-02 21:09:50','Guardar','127.0.0.1','Cahueñas Angelica','Clientes'),(121,1,'2011-05-02 21:16:04','Guardar','127.0.0.1','Jadán Carlos','Clientes'),(122,1,'2011-05-02 21:16:20','Guardar','127.0.0.1','Jadán Carlos','Clientes'),(123,1,'2011-05-02 21:17:13','Guardar','127.0.0.1','CONSUMIDOR FINAL','Clientes'),(124,1,'2011-05-02 21:17:32','Guardar','127.0.0.1','Jadán Carlos','Clientes'),(125,1,'2011-05-02 21:18:02','Guardar','127.0.0.1','JADAN STALIN','Clientes'),(126,1,'2011-05-02 21:26:19','Guardar','127.0.0.1','JADAN STALIN','Clientes'),(127,1,'2011-05-02 21:26:31','Guardar','127.0.0.1','Jadan Geovanny','Clientes'),(128,1,'2011-05-03 09:58:41','-','127.0.0.1','Ingreso Sistema','-'),(129,1,'2011-05-03 12:59:20','-','127.0.0.1','Ingreso Sistema','-'),(130,1,'2011-05-03 13:24:47','Guardar','127.0.0.1','192.168.0.52 Pedro Vic.','Nodos'),(131,1,'2011-05-03 15:42:03','-','127.0.0.1','Ingreso Sistema','-'),(132,1,'2011-05-03 17:40:08','Guardar','127.0.0.1','JADAN CAHUEÑAS ISMAEL FRANCISCO','Empleados'),(133,1,'2011-05-03 17:40:47','Guardar','127.0.0.1','JADAN CAHUEÑAS ISMAEL FRANCISCO','Empleados'),(134,1,'2011-05-03 17:44:14','Guardar','127.0.0.1','JADAN CAHUEÑAS SAMI ROMINA','Empleados'),(135,1,'2011-05-03 17:45:30','Guardar','127.0.0.1','JADAN CAHUEÑAS ISMAEL FRANCISCO','Empleados'),(136,1,'2011-05-03 18:02:20','-','127.0.0.1','Ingreso Sistema','-'),(137,1,'2011-05-03 18:16:50','-','127.0.0.1','Ingreso Sistema','-'),(138,1,'2011-05-03 18:19:12','-','127.0.0.1','Ingreso Sistema','-'),(139,1,'2011-05-03 18:36:29','-','127.0.0.1','Ingreso Sistema','-'),(140,1,'2011-05-03 18:44:37','-','127.0.0.1','Ingreso Sistema','-'),(141,1,'2011-05-03 19:26:30','-','127.0.0.1','Ingreso Sistema','-'),(142,1,'2011-05-03 19:27:22','-','127.0.0.1','Ingreso Sistema','-'),(143,1,'2011-05-03 19:33:34','Guardar','127.0.0.1','JADAN CAHUEÑAS SAMI ROMINA','Empleados'),(144,1,'2011-05-03 19:35:15','Guardar','127.0.0.1','Cahueñas Angelica','Clientes'),(145,1,'2011-05-03 19:35:38','Guardar','127.0.0.1','JADAN CAHUEÑAS SAMI ROMINA','Empleados'),(146,1,'2011-05-04 08:45:38','-','127.0.0.1','Ingreso Sistema','-'),(147,1,'2011-05-04 11:18:33','-','127.0.0.1','Ingreso Sistema','-'),(148,1,'2011-05-04 12:54:18','-','127.0.0.1','Ingreso Sistema','-'),(149,1,'2011-05-04 13:47:28','Actualizar','127.0.0.1','000001 - null null','Contratos'),(150,1,'2011-05-04 13:56:09','-','127.0.0.1','Ingreso Sistema','-'),(151,1,'2011-05-04 13:58:57','-','127.0.0.1','Ingreso Sistema','-'),(152,1,'2011-05-04 16:11:14','-','127.0.0.1','Ingreso Sistema','-'),(153,1,'2011-05-04 16:11:51','Guardar','127.0.0.1','Bastidas Miguel','Empleados'),(154,2,'2011-05-04 16:11:58','-','127.0.0.1','Ingreso Sistema','-'),(155,2,'2011-05-04 16:11:59','-','186.5.68.74','Ingreso Sistema','-'),(156,2,'2011-05-04 16:18:28','-','186.5.68.74','Ingreso Sistema','-'),(157,1,'2011-05-04 16:20:38','-','127.0.0.1','Ingreso Sistema','-'),(158,1,'2011-05-04 16:48:28','-','127.0.0.1','Ingreso Sistema','-'),(159,1,'2011-05-04 16:52:23','-','127.0.0.1','Ingreso Sistema','-'),(160,1,'2011-05-04 17:11:07','-','127.0.0.1','Ingreso Sistema','-'),(161,1,'2011-05-04 17:13:04','Actualizar','127.0.0.1','000001 - null null','Contratos'),(162,1,'2011-05-04 17:13:40','Actualizar','127.0.0.1','000001 - null null','Contratos'),(163,1,'2011-05-04 17:15:49','Actualizar','127.0.0.1','000001 - null null','Contratos'),(164,1,'2011-05-04 17:16:00','Actualizar','127.0.0.1','000001 - null null','Contratos'),(165,1,'2011-05-04 17:20:21','-','127.0.0.1','Ingreso Sistema','-'),(166,1,'2011-05-05 09:09:32','-','127.0.0.1','Ingreso Sistema','-'),(167,2,'2011-05-05 12:41:30','-','186.5.68.74','Ingreso Sistema','-'),(168,2,'2011-05-05 12:52:00','-','190.95.148.131','Ingreso Sistema','-'),(169,2,'2011-05-05 12:58:03','Guardar','190.95.148.131','gualotuña henriquez juan carlos','Clientes'),(170,2,'2011-05-05 13:00:51','Guardar','190.95.148.131','192.168.0.52 Pedro Vic.','Nodos'),(171,1,'2011-05-09 14:17:27','-','127.0.0.1','Ingreso Sistema','-'),(172,1,'2011-05-09 14:49:14','-','127.0.0.1','Ingreso Sistema','-'),(173,1,'2011-05-10 08:35:26','-','127.0.0.1','Ingreso Sistema','-'),(174,1,'2011-05-10 08:36:44','Guardar','127.0.0.1','SUPER ADMINISTRADOR','Accesos'),(175,1,'2011-05-10 08:37:28','Guardar','127.0.0.1','SUPER ADMINISTRADOR','Accesos'),(176,1,'2011-05-10 08:38:02','Guardar','127.0.0.1','SUPER ADMINISTRADOR','Accesos'),(177,1,'2011-05-10 08:38:34','-','127.0.0.1','Ingreso Sistema','-'),(178,1,'2011-05-10 08:42:13','Guardar','127.0.0.1','1 - RADIO','Radioses'),(179,1,'2011-05-13 08:55:56','-','127.0.0.1','Ingreso Sistema','-'),(180,1,'2011-05-13 09:00:30','Actualizar','127.0.0.1','2 - Pedro Vicente Maldonado','Cantones'),(181,1,'2011-05-13 09:00:37','Actualizar','127.0.0.1','4 - Sur','Sectores'),(182,1,'2011-05-13 09:00:48','Guardar','127.0.0.1','5 - Centro','Sectores'),(183,1,'2011-05-13 09:01:55','Guardar','127.0.0.1',' JCINFORM SOLUCIONES INFORMATICAS','Proveedores'),(184,1,'2011-05-13 09:02:19','Guardar','127.0.0.1','Bastidas Miguel','Empleados'),(185,1,'2011-05-13 09:03:02','Guardar','127.0.0.1','jcinform.persistencia.Empleados[codigo=2]','Comisiones'),(186,1,'2011-05-13 09:05:07','-','127.0.0.1','Ingreso Sistema','-'),(187,1,'2011-05-13 09:06:48','Guardar','127.0.0.1','jcinform.persistencia.Empleados[codigo=1]','SectoresEmpleados'),(188,1,'2011-05-13 09:07:01','Guardar','127.0.0.1','jcinform.persistencia.Empleados[codigo=2]','SectoresEmpleados'),(189,1,'2011-05-13 09:07:32','Actualizar','127.0.0.1','1 - RADIO','Radioses'),(190,1,'2011-05-17 13:09:33','-','127.0.0.1','Ingreso Sistema','-'),(191,1,'2011-05-17 20:50:39','-','127.0.0.1','Ingreso Sistema','-'),(192,1,'2011-05-24 17:02:04','-','127.0.0.1','Ingreso Sistema','-'),(193,1,'2011-05-25 08:39:58','-','127.0.0.1','Ingreso Sistema','-'),(194,1,'2011-05-25 14:55:17','-','127.0.0.1','Ingreso Sistema','-'),(195,1,'2011-06-06 19:44:11','-','127.0.0.1','Ingreso Sistema','-'),(196,1,'2011-06-06 19:59:11','Guardar','127.0.0.1','125 cantidadnullMon Jun 06 19:54:02 COT 2011','Cabeceracompra'),(197,1,'2011-06-06 21:07:56','-','127.0.0.1','Ingreso Sistema','-'),(198,1,'2011-06-06 22:14:26','-','127.0.0.1','Ingreso Sistema','-'),(199,1,'2011-06-06 22:48:47','-','127.0.0.1','Ingreso Sistema','-'),(200,1,'2011-06-06 22:58:25','Guardar','127.0.0.1','null cantidadnullMon Jun 06 22:57:58 COT 2011','Cabeceracompra'),(201,1,'2011-06-06 23:01:25','Guardar','127.0.0.1','1 cantidadnullMon Jun 06 23:00:54 COT 2011','Cabeceracompra'),(202,1,'2011-06-06 23:19:46','-','127.0.0.1','Ingreso Sistema','-'),(203,1,'2011-06-06 23:20:08','Guardar','127.0.0.1','null cantidadnullMon Jun 06 23:19:50 COT 2011','Cabeceracompra'),(204,1,'2011-06-06 23:33:01','-','127.0.0.1','Ingreso Sistema','-'),(205,1,'2011-06-06 23:33:35','Guardar','127.0.0.1','null cantidadnullMon Jun 06 23:33:06 COT 2011','Cabeceracompra'),(206,1,'2011-06-07 08:29:37','-','127.0.0.1','Ingreso Sistema','-'),(207,1,'2011-06-07 08:40:51','Guardar','127.0.0.1','Jadan Geovanny','Clientes'),(208,1,'2011-06-07 09:53:16','-','127.0.0.1','Ingreso Sistema','-'),(209,1,'2011-06-07 10:01:40','-','127.0.0.1','Ingreso Sistema','-'),(210,1,'2011-06-07 10:14:36','Actualizar','127.0.0.1','1 - jcinform.persistencia.Clientes[codigo=2]','Soporte'),(211,1,'2011-06-07 10:18:54','Actualizar','127.0.0.1','2 - jcinform.persistencia.Clientes[codigo=2]','Soporte'),(212,1,'2011-06-07 10:28:45','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Soporte'),(213,1,'2011-06-07 10:33:37','Actualizar','127.0.0.1','2 - jcinform.persistencia.Clientes[codigo=2]','Soporte'),(214,1,'2011-06-07 10:33:50','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Soporte'),(215,1,'2011-06-07 10:59:11','Actualizar','127.0.0.1','1 - jcinform.persistencia.Clientes[codigo=2]','Soporte'),(216,1,'2011-06-07 10:59:36','Actualizar','127.0.0.1','1 - jcinform.persistencia.Clientes[codigo=2]','Soporte'),(217,1,'2011-06-07 11:00:06','Actualizar','127.0.0.1','1 - jcinform.persistencia.Clientes[codigo=2]','Soporte'),(218,1,'2011-06-07 11:06:28','-','127.0.0.1','Ingreso Sistema','-'),(219,1,'2011-06-07 11:06:46','Actualizar','127.0.0.1','1 - jcinform.persistencia.Clientes[codigo=2]','Soporte'),(220,1,'2011-06-07 11:07:27','Actualizar','127.0.0.1','1 - jcinform.persistencia.Clientes[codigo=2]','Soporte'),(221,1,'2011-06-07 11:08:41','Actualizar','127.0.0.1','2 - jcinform.persistencia.Clientes[codigo=2]','Soporte'),(222,1,'2011-06-07 11:09:53','Actualizar','127.0.0.1','1 - jcinform.persistencia.Clientes[codigo=2]','Soporte'),(223,1,'2011-06-07 11:11:47','Actualizar','127.0.0.1','1 - jcinform.persistencia.Clientes[codigo=2]','Soporte'),(224,1,'2011-06-07 11:12:37','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Soporte'),(225,1,'2011-06-07 11:16:25','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Soporte'),(226,1,'2011-06-07 11:18:41','Actualizar','127.0.0.1','1 - jcinform.persistencia.Clientes[codigo=2]','Soporte'),(227,1,'2011-06-07 11:18:48','Actualizar','127.0.0.1','1 - jcinform.persistencia.Clientes[codigo=2]','Soporte'),(228,1,'2011-06-07 11:18:56','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Soporte'),(229,1,'2011-06-07 11:20:05','-','127.0.0.1','Ingreso Sistema','-'),(230,1,'2011-06-07 11:20:22','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Soporte'),(231,1,'2011-06-07 11:25:47','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Soporte'),(232,1,'2011-06-07 14:49:16','-','127.0.0.1','Ingreso Sistema','-'),(233,1,'2011-06-07 14:49:52','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Soporte'),(234,1,'2011-06-07 16:46:57','-','127.0.0.1','Ingreso Sistema','-'),(235,1,'2011-06-07 16:47:17','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Soporte'),(236,1,'2011-06-07 16:51:58','-','127.0.0.1','Ingreso Sistema','-'),(237,1,'2011-06-07 16:52:18','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Soporte'),(238,1,'2011-06-07 16:55:17','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Soporte'),(239,1,'2011-06-07 16:55:55','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Soporte'),(240,1,'2011-06-07 16:56:15','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Soporte'),(241,1,'2011-06-07 17:05:04','-','127.0.0.1','Ingreso Sistema','-'),(242,1,'2011-06-07 17:05:38','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Soporte'),(243,1,'2011-06-07 17:09:59','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Soporte'),(244,1,'2011-06-07 17:12:28','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Soporte'),(245,1,'2011-06-07 17:15:12','Guardar','127.0.0.1','Jadan Geovanny','Clientes'),(246,1,'2011-06-07 17:15:20','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Soporte'),(247,1,'2011-06-07 17:16:19','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Soporte'),(248,1,'2011-06-07 17:16:37','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Soporte'),(249,1,'2011-06-07 17:17:30','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Soporte'),(250,1,'2011-06-07 17:18:29','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Soporte'),(251,1,'2011-06-07 17:19:01','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Soporte'),(252,1,'2011-06-07 17:20:18','-','127.0.0.1','Ingreso Sistema','-'),(253,1,'2011-06-07 17:20:33','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Soporte'),(254,1,'2011-06-07 17:23:14','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Soporte'),(255,1,'2011-06-07 17:24:48','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Soporte'),(256,1,'2011-06-07 17:32:51','-','127.0.0.1','Ingreso Sistema','-'),(257,1,'2011-06-07 17:33:04','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Soporte'),(258,1,'2011-06-07 17:33:39','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Soporte'),(259,1,'2011-06-07 17:35:14','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Soporte'),(260,1,'2011-06-07 17:37:23','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Soporte'),(261,1,'2011-06-07 17:38:27','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Soporte'),(262,1,'2011-06-07 17:39:17','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Soporte'),(263,1,'2011-06-07 17:39:54','-','127.0.0.1','Ingreso Sistema','-'),(264,1,'2011-06-07 17:40:13','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Soporte'),(265,1,'2011-06-07 17:40:56','Actualizar','127.0.0.1','4 - jcinform.persistencia.Clientes[codigo=2]','Soporte'),(266,1,'2011-06-07 17:43:06','Actualizar','127.0.0.1','2 - jcinform.persistencia.Clientes[codigo=2]','Soporte'),(267,1,'2011-06-07 17:43:10','Actualizar','127.0.0.1','1 - jcinform.persistencia.Clientes[codigo=2]','Soporte'),(268,1,'2011-06-07 17:54:23','Guardar','127.0.0.1','APELLIDOS NUEVO CLIENTE','Clientes'),(269,1,'2011-06-07 17:54:58','Guardar','127.0.0.1','Jadan Geovanny','Clientes'),(270,1,'2011-06-07 17:59:48','Guardar','127.0.0.1','CONSUMIDOR FINAL','Clientes'),(271,1,'2011-06-10 09:25:08','-','127.0.0.1','Ingreso Sistema','-'),(272,1,'2011-06-15 09:30:15','-','127.0.0.1','Ingreso Sistema','-'),(273,1,'2011-06-15 09:31:11','Guardar','127.0.0.1','MARURI PEDRO','Clientes'),(274,1,'2011-06-15 09:33:40','Guardar','127.0.0.1','1 - jcinform.persistencia.Clientes[codigo=9]','Contratos'),(275,1,'2011-06-15 09:41:10','Guardar','127.0.0.1','1 - jcinform.persistencia.Clientes[codigo=2]','Contratos'),(276,1,'2011-06-15 09:53:51','Guardar','127.0.0.1','1 - jcinform.persistencia.Clientes[codigo=2]','Contratos'),(277,1,'2011-06-15 10:35:26','-','127.0.0.1','Ingreso Sistema','-'),(278,1,'2011-06-15 10:40:31','Guardar','127.0.0.1','2 - jcinform.persistencia.Clientes[codigo=2]','Contratos'),(279,1,'2011-06-15 10:42:47','Actualizar','127.0.0.1','2 - jcinform.persistencia.Clientes[codigo=2]','Contratos'),(280,1,'2011-06-15 10:43:02','Actualizar','127.0.0.1','2 - jcinform.persistencia.Clientes[codigo=2]','Contratos'),(281,1,'2011-06-15 10:43:12','Actualizar','127.0.0.1','1 - jcinform.persistencia.Clientes[codigo=2]','Contratos'),(282,1,'2011-06-15 10:49:31','Actualizar','127.0.0.1','2 - jcinform.persistencia.Clientes[codigo=2]','Contratos'),(283,1,'2011-06-15 10:59:24','Guardar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Contratos'),(284,1,'2011-06-15 11:11:24','Guardar','127.0.0.1','JCINFORM SOLUCIONES INFORMATICAS','Empresa'),(285,1,'2011-06-15 11:26:31','Actualizar','127.0.0.1','2 - jcinform.persistencia.Clientes[codigo=2]','Contratos'),(286,1,'2011-06-15 14:19:54','-','127.0.0.1','Ingreso Sistema','-'),(287,1,'2011-06-20 08:50:51','-','127.0.0.1','Ingreso Sistema','-'),(288,1,'2011-06-20 09:41:25','-','127.0.0.1','Ingreso Sistema','-'),(289,1,'2011-06-20 10:20:50','-','127.0.0.1','Ingreso Sistema','-'),(290,1,'2011-06-20 10:30:23','Actualizar','127.0.0.1','2 - jcinform.persistencia.Clientes[codigo=2]','Contratos'),(291,1,'2011-06-20 10:30:35','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Contratos'),(292,1,'2011-06-20 10:31:54','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Contratos'),(293,1,'2011-06-21 08:28:31','-','127.0.0.1','Ingreso Sistema','-'),(294,1,'2011-06-21 08:28:32','-','127.0.0.1','Ingreso Sistema','-'),(295,1,'2011-06-21 08:28:46','-','127.0.0.1','Ingreso Sistema','-'),(296,1,'2011-06-21 08:50:28','-','127.0.0.1','Ingreso Sistema','-'),(297,1,'2011-06-21 08:54:31','Guardar','127.0.0.1',' Pedro Vic.','Nodos'),(298,1,'2011-06-21 08:54:42','Guardar','127.0.0.1',' Pedro Vic.','Nodos'),(299,1,'2011-06-21 08:55:39','Actualizar','127.0.0.1','1 - RADIO','Radioses'),(300,1,'2011-06-21 08:56:05','Guardar','127.0.0.1','2 - otro radio','Radioses'),(301,1,'2011-06-21 09:19:06','-','127.0.0.1','Ingreso Sistema','-'),(302,1,'2011-06-21 09:26:44','Guardar','127.0.0.1','2511 cantidadnullTue Jun 21 09:24:45 COT 2011','Cabeceracompra'),(303,1,'2011-06-21 09:28:48','Guardar','127.0.0.1','1 cantidadnullTue Jun 21 09:28:20 COT 2011','Cabeceracompra'),(304,1,'2011-06-21 11:16:18','-','127.0.0.1','Ingreso Sistema','-'),(305,1,'2011-06-21 11:48:35','Guardar','127.0.0.1','2515 - jcinform.persistencia.Clientes[codigo=1]','Contratos'),(306,1,'2011-06-21 13:59:02','-','127.0.0.1','Ingreso Sistema','-'),(307,1,'2011-06-21 14:07:14','-','127.0.0.1','Ingreso Sistema','-'),(308,1,'2011-06-21 14:08:57','-','127.0.0.1','Ingreso Sistema','-'),(309,1,'2011-06-21 14:59:01','-','127.0.0.1','Ingreso Sistema','-'),(310,1,'2011-06-22 09:59:10','-','127.0.0.1','Ingreso Sistema','-'),(311,1,'2011-06-22 11:38:52','-','127.0.0.1','Ingreso Sistema','-'),(312,1,'2011-06-22 14:20:04','-','127.0.0.1','Ingreso Sistema','-'),(313,1,'2011-06-22 14:31:45','-','127.0.0.1','Ingreso Sistema','-'),(314,1,'2011-06-22 17:43:09','-','127.0.0.1','Ingreso Sistema','-'),(315,1,'2011-06-22 18:04:27','-','127.0.0.1','Ingreso Sistema','-'),(316,1,'2011-06-23 08:30:47','-','127.0.0.1','Ingreso Sistema','-'),(317,1,'2011-06-23 20:30:28','-','127.0.0.1','Ingreso Sistema','-'),(318,1,'2011-06-23 21:00:34','-','127.0.0.1','Ingreso Sistema','-'),(319,1,'2011-06-23 22:13:16','Actualizar','127.0.0.1','5 - jcinform.persistencia.Clientes[codigo=1]','Soporte'),(320,1,'2011-06-23 22:13:43','Actualizar','127.0.0.1','5 - jcinform.persistencia.Clientes[codigo=1]','Soporte'),(321,1,'2011-06-23 22:14:16','Actualizar','127.0.0.1','5 - jcinform.persistencia.Clientes[codigo=1]','Soporte'),(322,1,'2011-06-23 22:14:26','Actualizar','127.0.0.1','5 - jcinform.persistencia.Clientes[codigo=1]','Soporte'),(323,1,'2011-06-23 22:44:23','-','127.0.0.1','Ingreso Sistema','-'),(324,1,'2011-06-23 22:45:54','-','127.0.0.1','Ingreso Sistema','-'),(325,1,'2011-06-23 22:48:24','-','127.0.0.1','Ingreso Sistema','-'),(326,1,'2011-06-23 22:56:26','Actualizar','127.0.0.1','2 - jcinform.persistencia.Clientes[codigo=2]','Contratos'),(327,1,'2011-06-23 23:02:21','Actualizar','127.0.0.1','2 - jcinform.persistencia.Clientes[codigo=2]','Contratos'),(328,1,'2011-06-24 15:07:15','-','127.0.0.1','Ingreso Sistema','-'),(329,1,'2011-06-24 17:02:50','-','127.0.0.1','Ingreso Sistema','-'),(330,1,'2011-06-24 17:29:08','Guardar','127.0.0.1','JADAN CAHUEÑAS SAMI ROMINA','Comisiones'),(331,1,'2011-06-24 17:29:13','Guardar','127.0.0.1','Bastidas Miguel','Comisiones'),(332,1,'2011-06-25 17:23:04','-','127.0.0.1','Ingreso Sistema','-'),(333,1,'2011-06-27 09:07:01','-','127.0.0.1','Ingreso Sistema','-'),(334,1,'2011-06-27 15:43:23','-','127.0.0.1','Ingreso Sistema','-'),(335,1,'2011-06-27 19:59:44','-','127.0.0.1','Ingreso Sistema','-'),(336,1,'2011-07-04 10:14:46','-','127.0.0.1','Ingreso Sistema','-'),(337,1,'2011-07-04 10:14:46','-','127.0.0.1','Ingreso Sistema','-'),(338,1,'2011-07-04 12:01:23','-','127.0.0.1','Ingreso Sistema','-'),(339,1,'2011-07-05 17:20:04','-','127.0.0.1','Ingreso Sistema','-'),(340,1,'2011-07-07 09:56:52','-','127.0.0.1','Ingreso Sistema','-'),(341,1,'2011-07-07 18:55:36','-','127.0.0.1','Ingreso Sistema','-'),(342,1,'2011-07-07 19:10:07','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Contratos'),(343,1,'2011-07-07 19:49:05','-','127.0.0.1','Ingreso Sistema','-'),(344,1,'2011-07-07 20:40:35','-','127.0.0.1','Ingreso Sistema','-'),(345,1,'2011-07-09 10:39:13','-','127.0.0.1','Ingreso Sistema','-'),(346,1,'2011-07-09 10:40:26','Guardar','127.0.0.1','JCINFORM SOLUCIONES INFORMATICAS','Empresa'),(347,1,'2011-07-09 11:02:09','-','127.0.0.1','Ingreso Sistema','-'),(348,1,'2011-07-09 12:02:39','-','127.0.0.1','Ingreso Sistema','-'),(349,1,'2011-07-11 09:02:19','-','127.0.0.1','Ingreso Sistema','-'),(350,1,'2011-07-11 09:04:47','Actualizar','127.0.0.1','2 - jcinform.persistencia.Clientes[codigo=2]','Contratos'),(351,1,'2011-07-11 09:34:24','-','127.0.0.1','Ingreso Sistema','-'),(352,1,'2011-07-11 09:37:02','-','127.0.0.1','Ingreso Sistema','-'),(353,1,'2011-07-12 15:42:54','-','127.0.0.1','Ingreso Sistema','-'),(354,1,'2011-07-12 17:48:15','Guardar','127.0.0.1','null cantidadnullTue Jul 12 17:47:34 COT 2011','Cabeceracompra'),(355,1,'2011-07-13 08:25:51','-','127.0.0.1','Ingreso Sistema','-'),(356,1,'2011-07-13 12:39:44','-','127.0.0.1','Ingreso Sistema','-'),(357,1,'2011-07-13 13:45:43','-','127.0.0.1','Ingreso Sistema','-'),(358,1,'2011-07-13 16:14:57','-','127.0.0.1','Ingreso Sistema','-'),(359,1,'2011-07-13 16:20:21','-','127.0.0.1','Ingreso Sistema','-'),(360,1,'2011-07-13 16:22:17','Guardar','127.0.0.1',' Pedro Vic.','Nodos'),(361,1,'2011-07-13 16:27:30','Guardar','127.0.0.1',' Pedro Vic.','Nodos'),(362,1,'2011-07-13 16:30:06','-','127.0.0.1','Ingreso Sistema','-'),(363,1,'2011-07-13 16:30:27','Actualizar','127.0.0.1','1 - RADIO','Radioses'),(364,1,'2011-07-13 16:42:27','-','127.0.0.1','Ingreso Sistema','-'),(365,1,'2011-07-13 17:25:01','Guardar','127.0.0.1','null cantidadnullWed Jul 13 17:24:43 COT 2011','Cabeceracompra'),(366,1,'2011-07-13 17:30:11','Guardar','127.0.0.1','1 cantidadnullWed Jul 13 17:28:29 COT 2011','Cabeceracompra'),(367,1,'2011-07-13 17:33:07','Guardar','127.0.0.1','11110 cantidadnullWed Jul 13 17:32:42 COT 2011','Cabeceracompra'),(368,1,'2011-07-13 17:40:05','Guardar','127.0.0.1','4 cantidadnullWed Jul 13 17:39:34 COT 2011','Cabeceracompra'),(369,1,'2011-07-13 17:41:37','Guardar','127.0.0.1','100 cantidadnullWed Jul 13 17:39:34 COT 2011','Cabeceracompra'),(370,1,'2011-07-14 10:01:35','-','127.0.0.1','Ingreso Sistema','-'),(371,1,'2011-07-14 10:46:16','-','127.0.0.1','Ingreso Sistema','-'),(372,1,'2011-07-14 10:46:46','Guardar','127.0.0.1','23232 cantidadnullThu Jul 14 10:46:20 COT 2011','Cabeceracompra'),(373,1,'2011-07-14 10:49:34','Guardar','127.0.0.1','23 cantidadnullThu Jul 14 10:49:09 COT 2011','Cabeceracompra'),(374,1,'2011-07-14 11:03:14','Guardar','127.0.0.1','23 cantidadnullThu Jul 14 10:49:09 COT 2011','Cabeceracompra'),(375,1,'2011-07-14 11:04:16','Guardar','127.0.0.1','44 cantidadnullThu Jul 14 11:03:47 COT 2011','Cabeceracompra'),(376,1,'2011-07-14 11:20:09','Guardar','127.0.0.1','4141 cantidadnullThu Jul 14 11:19:49 COT 2011','Cabeceracompra'),(377,1,'2011-07-14 11:52:38','Guardar','127.0.0.1','3434 cantidadnullThu Jul 14 11:52:00 COT 2011','Cabeceracompra'),(378,1,'2011-07-14 11:55:25','Guardar','127.0.0.1','4 cantidadnullThu Jul 14 11:54:41 COT 2011','Cabeceracompra'),(379,1,'2011-07-14 11:56:06','Guardar','127.0.0.1','2 cantidadnullThu Jul 14 11:54:41 COT 2011','Cabeceracompra'),(380,1,'2011-07-14 12:48:10','-','127.0.0.1','Ingreso Sistema','-'),(381,1,'2011-07-14 17:40:00','-','127.0.0.1','Ingreso Sistema','-'),(382,1,'2011-07-14 17:42:51','Guardar','127.0.0.1','1 cantidadnullWed Jul 13 00:00:00 COT 2011','Cabeceracompra'),(383,1,'2011-07-14 18:18:57','-','127.0.0.1','Ingreso Sistema','-'),(384,1,'2011-07-14 18:24:19','-','127.0.0.1','Ingreso Sistema','-'),(385,1,'2011-07-14 18:24:27','Guardar','127.0.0.1','jcinform.persistencia.Perfil[codigo=1]','Accesos'),(386,1,'2011-07-14 18:24:34','Guardar','127.0.0.1','jcinform.persistencia.Perfil[codigo=2]','Accesos'),(387,1,'2011-07-14 18:24:50','-','127.0.0.1','Ingreso Sistema','-'),(388,1,'2011-07-14 18:25:03','Guardar','127.0.0.1','1 - Bco. Pichincha','Bancos'),(389,1,'2011-07-14 18:26:11','Actualizar','127.0.0.1','1 - Bco. Pichincha','Bancos'),(390,1,'2011-07-14 18:26:23','Guardar','127.0.0.1','2 - Bco. Guayaquil','Bancos'),(391,1,'2011-07-14 18:26:27','Actualizar','127.0.0.1','2 - Bco. Guayaquil','Bancos'),(392,1,'2011-07-14 18:26:31','Actualizar','127.0.0.1','1 - Bco. Pichincha','Bancos'),(393,1,'2011-07-14 18:38:20','-','127.0.0.1','Ingreso Sistema','-'),(394,1,'2011-07-15 09:12:25','-','127.0.0.1','Ingreso Sistema','-'),(395,1,'2011-07-15 10:05:55','-','127.0.0.1','Ingreso Sistema','-'),(396,1,'2011-07-16 12:58:03','-','127.0.0.1','Ingreso Sistema','-'),(397,1,'2011-07-16 13:59:29','-','127.0.0.1','Ingreso Sistema','-'),(398,1,'2011-07-18 15:07:29','-','127.0.0.1','Ingreso Sistema','-'),(399,1,'2011-07-18 15:49:11','-','127.0.0.1','Ingreso Sistema','-'),(400,1,'2011-07-18 18:06:29','-','127.0.0.1','Ingreso Sistema','-'),(401,1,'2011-07-18 18:09:16','Guardar','127.0.0.1','11110 cantidadnullMon Jul 18 00:00:00 COT 2011','Cabeceracompra'),(402,1,'2011-07-18 19:24:24','-','127.0.0.1','Ingreso Sistema','-'),(403,1,'2011-07-19 14:54:03','-','127.0.0.1','Ingreso Sistema','-'),(404,1,'2011-07-19 16:57:42','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Contratos'),(405,1,'2011-07-19 17:06:02','Guardar','127.0.0.1','3 - Bco.Bolivariano','Bancos'),(406,1,'2011-07-19 17:06:13','Guardar','127.0.0.1','4 - Bco.del Austro','Bancos'),(407,1,'2011-07-19 17:06:25','Guardar','127.0.0.1','5 - Bco.Pacifico','Bancos'),(408,1,'2011-07-19 17:07:45','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Contratos'),(409,1,'2011-07-19 17:08:16','Actualizar','127.0.0.1','2 - jcinform.persistencia.Clientes[codigo=2]','Contratos'),(410,1,'2011-07-19 17:08:42','Actualizar','127.0.0.1','2 - jcinform.persistencia.Clientes[codigo=2]','Contratos'),(411,1,'2011-07-19 17:09:48','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Contratos'),(412,1,'2011-07-19 17:44:03','Guardar','127.0.0.1','3 - 1111','Radioses'),(413,1,'2011-07-20 11:46:51','-','127.0.0.1','Ingreso Sistema','-'),(414,1,'2011-07-20 14:10:43','-','127.0.0.1','Ingreso Sistema','-'),(415,1,'2011-07-20 16:52:28','-','127.0.0.1','Ingreso Sistema','-'),(416,1,'2011-07-21 10:34:46','-','127.0.0.1','Ingreso Sistema','-'),(417,1,'2011-07-21 11:31:16','Eliminar','127.0.0.1','5 - SERVICIO TECNICO','Planes'),(418,1,'2011-07-21 11:31:25','Eliminar','127.0.0.1','4 - REINSTALACION SERVICIO','Planes'),(419,1,'2011-07-21 11:34:33','Guardar','127.0.0.1','3 - S/M','Marcas'),(420,1,'2011-07-21 11:38:36','Guardar','127.0.0.1','Instalación del Servicio Instalación del Servicio','Equipos'),(421,1,'2011-07-21 11:46:21','-','127.0.0.1','Ingreso Sistema','-'),(422,1,'2011-07-21 11:47:29','Guardar','127.0.0.1','Switch Switch','Equipos'),(423,1,'2011-07-21 11:47:36','Guardar','127.0.0.1','Instalación del Servicio Instalación del Servicio','Equipos'),(424,1,'2011-07-21 11:48:56','Actualizar','127.0.0.1','2 - ADSL 512 KB','Planes'),(425,1,'2011-07-21 12:00:37','Guardar','127.0.0.1','Instalación del Servicio Instalación del Servicio','Equipos'),(426,1,'2011-07-21 12:00:44','Guardar','127.0.0.1','Instalación del Servicio Instalación del Servicio','Equipos'),(427,1,'2011-07-21 12:00:53','Guardar','127.0.0.1','Instalación del Servicio Instalación del Servicio','Equipos'),(428,1,'2011-07-21 12:02:38','Guardar','127.0.0.1','Router Router','Equipos'),(429,1,'2011-07-21 12:02:48','Guardar','127.0.0.1','Router Router','Equipos'),(430,1,'2011-07-21 12:10:44','Guardar','127.0.0.1','Instalación del Servicio Instalación del Servicio','Equipos'),(431,1,'2011-07-21 12:10:54','Guardar','127.0.0.1','Instalación del Servicio Instalación del Servicio','Equipos'),(432,1,'2011-07-21 12:10:57','Guardar','127.0.0.1','Antena Antena','Equipos'),(433,1,'2011-07-21 12:11:58','Guardar','127.0.0.1','Instalación del Servicio Instalación del Servicio','Equipos'),(434,1,'2011-07-21 12:12:22','Guardar','127.0.0.1','Reinstalación del servicio Reinstalación del servicio','Equipos'),(435,1,'2011-07-21 12:24:14','Guardar','127.0.0.1','Switch Switch','Equipos'),(436,1,'2011-07-21 12:27:05','Guardar','127.0.0.1','Reinstalación del servicio Reinstalación del servicio','Equipos'),(437,1,'2011-07-21 16:11:42','-','127.0.0.1','Ingreso Sistema','-'),(438,1,'2011-07-21 20:01:16','-','127.0.0.1','Ingreso Sistema','-'),(439,1,'2011-07-21 20:30:29','-','127.0.0.1','Ingreso Sistema','-'),(440,1,'2011-07-22 10:07:51','-','127.0.0.1','Ingreso Sistema','-'),(441,1,'2011-07-22 14:54:23','-','127.0.0.1','Ingreso Sistema','-'),(442,1,'2011-07-22 15:18:23','Guardar','127.0.0.1','1501 - jcinform.persistencia.Clientes[codigo=5]','Contratos'),(443,1,'2011-07-22 15:20:19','Actualizar','127.0.0.1','1501 - jcinform.persistencia.Clientes[codigo=5]','Contratos'),(444,1,'2011-07-22 15:21:27','Actualizar','127.0.0.1','1501 - jcinform.persistencia.Clientes[codigo=5]','Contratos'),(445,1,'2011-07-22 15:22:44','Actualizar','127.0.0.1','1501 - jcinform.persistencia.Clientes[codigo=5]','Contratos'),(446,1,'2011-07-22 15:23:59','Actualizar','127.0.0.1','1501 - jcinform.persistencia.Clientes[codigo=5]','Contratos'),(447,1,'2011-07-22 15:25:04','Actualizar','127.0.0.1','1501 - jcinform.persistencia.Clientes[codigo=5]','Contratos'),(448,1,'2011-07-23 09:56:44','-','127.0.0.1','Ingreso Sistema','-'),(449,1,'2011-07-23 09:58:02','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Contratos'),(450,1,'2011-07-23 12:02:10','-','127.0.0.1','Ingreso Sistema','-'),(451,1,'2011-07-23 13:55:30','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Contratos'),(452,1,'2011-07-23 13:55:59','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Contratos'),(453,1,'2011-07-23 14:00:00','-','127.0.0.1','Ingreso Sistema','-'),(454,1,'2011-07-23 14:00:33','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Contratos'),(455,1,'2011-07-23 14:03:25','Actualizar','127.0.0.1','2 - jcinform.persistencia.Clientes[codigo=2]','Contratos'),(456,1,'2011-07-23 14:11:21','Actualizar','127.0.0.1','2 - jcinform.persistencia.Clientes[codigo=2]','Contratos'),(457,1,'2011-07-23 14:12:24','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Contratos'),(458,1,'2011-07-23 14:17:07','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Contratos'),(459,1,'2011-07-23 14:17:30','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Contratos'),(460,1,'2011-07-23 14:18:02','Actualizar','127.0.0.1','2 - jcinform.persistencia.Clientes[codigo=2]','Contratos'),(461,1,'2011-07-23 14:19:32','Actualizar','127.0.0.1','2 - jcinform.persistencia.Clientes[codigo=2]','Contratos'),(462,1,'2011-07-23 14:20:25','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Contratos'),(463,1,'2011-07-25 10:20:11','-','127.0.0.1','Ingreso Sistema','-'),(464,1,'2011-07-25 12:17:07','-','127.0.0.1','Ingreso Sistema','-'),(465,1,'2011-07-25 12:36:46','Actualizar','127.0.0.1','2 - jcinform.persistencia.Clientes[codigo=2]','Contratos'),(466,1,'2011-07-25 12:50:42','-','127.0.0.1','Ingreso Sistema','-'),(467,1,'2011-07-25 14:20:37','-','127.0.0.1','Ingreso Sistema','-'),(468,1,'2011-07-25 14:24:28','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Contratos'),(469,1,'2011-07-25 14:24:40','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Contratos'),(470,1,'2011-07-25 14:25:10','Actualizar','127.0.0.1','2 - jcinform.persistencia.Clientes[codigo=2]','Contratos'),(471,1,'2011-07-25 14:25:27','Actualizar','127.0.0.1','2 - jcinform.persistencia.Clientes[codigo=2]','Contratos'),(472,1,'2011-07-25 14:25:58','Actualizar','127.0.0.1','2 - jcinform.persistencia.Clientes[codigo=2]','Contratos'),(473,1,'2011-07-25 14:26:33','Actualizar','127.0.0.1','2 - jcinform.persistencia.Clientes[codigo=2]','Contratos'),(474,1,'2011-07-25 14:26:59','Actualizar','127.0.0.1','2 - jcinform.persistencia.Clientes[codigo=2]','Contratos'),(475,1,'2011-07-25 14:28:47','-','127.0.0.1','Ingreso Sistema','-'),(476,1,'2011-07-25 14:29:40','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Contratos'),(477,1,'2011-07-25 14:31:13','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Contratos'),(478,1,'2011-07-25 14:32:33','Actualizar','127.0.0.1','2 - jcinform.persistencia.Clientes[codigo=2]','Contratos'),(479,1,'2011-07-25 15:38:41','-','127.0.0.1','Ingreso Sistema','-'),(480,1,'2011-07-25 16:03:13','-','127.0.0.1','Ingreso Sistema','-'),(481,1,'2011-07-25 16:32:57','-','127.0.0.1','Ingreso Sistema','-'),(482,1,'2011-07-25 16:34:07','Guardar','127.0.0.1','1 cantidadnullMon Jul 25 00:00:00 COT 2011','Cabeceracompra'),(483,1,'2011-07-25 17:24:41','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Contratos'),(484,1,'2011-07-25 17:25:49','Actualizar','127.0.0.1','2 - jcinform.persistencia.Clientes[codigo=2]','Contratos'),(485,1,'2011-07-25 17:27:45','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Contratos'),(486,1,'2011-07-25 17:28:16','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Contratos'),(487,1,'2011-07-25 17:29:27','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Contratos'),(488,1,'2011-07-25 17:31:03','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Contratos'),(489,1,'2011-07-25 17:32:11','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Contratos'),(490,1,'2011-07-25 17:49:54','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Contratos'),(491,1,'2011-07-25 17:56:39','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Contratos'),(492,1,'2011-07-25 22:44:31','-','127.0.0.1','Ingreso Sistema','-'),(493,1,'2011-07-25 22:50:17','Actualizar','127.0.0.1','2515 - jcinform.persistencia.Clientes[codigo=1]','Contratos'),(494,1,'2011-07-25 22:50:36','Actualizar','127.0.0.1','2515 - jcinform.persistencia.Clientes[codigo=1]','Contratos'),(495,1,'2011-07-26 10:22:54','-','127.0.0.1','Ingreso Sistema','-'),(496,1,'2011-07-26 10:24:18','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Contratos'),(497,1,'2011-07-26 11:46:18','-','127.0.0.1','Ingreso Sistema','-'),(498,1,'2011-07-26 11:47:57','-','127.0.0.1','Ingreso Sistema','-'),(499,1,'2011-07-26 11:55:14','Guardar','127.0.0.1','1414 cantidadnullTue Jul 26 00:00:00 COT 2011','Cabeceracompra'),(500,1,'2011-07-26 11:55:23','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Contratos'),(501,1,'2011-07-26 12:07:37','-','127.0.0.1','Ingreso Sistema','-'),(502,1,'2011-07-26 12:22:50','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Contratos'),(503,1,'2011-07-26 12:35:45','-','127.0.0.1','Ingreso Sistema','-'),(504,1,'2011-07-26 13:37:12','-','127.0.0.1','Ingreso Sistema','-'),(505,1,'2011-07-26 13:42:11','-','127.0.0.1','Ingreso Sistema','-'),(506,1,'2011-07-26 15:04:58','-','127.0.0.1','Ingreso Sistema','-'),(507,1,'2011-07-26 15:37:09','-','127.0.0.1','Ingreso Sistema','-'),(508,1,'2011-07-26 15:59:46','-','127.0.0.1','Ingreso Sistema','-'),(509,1,'2011-07-27 14:55:45','-','127.0.0.1','Ingreso Sistema','-'),(510,1,'2011-07-27 16:55:52','-','127.0.0.1','Ingreso Sistema','-'),(511,1,'2011-07-27 18:02:02','Guardar','127.0.0.1',' 4144','Nodos'),(512,1,'2011-07-27 18:03:43','Guardar','127.0.0.1',' Nodo C2ksik','Nodos'),(513,1,'2011-07-27 18:03:55','Guardar','127.0.0.1',' Nodo Ac25','Nodos'),(514,1,'2011-07-27 18:26:28','Actualizar','127.0.0.1','3 - Radio 0012','Radioses'),(515,1,'2011-07-27 18:26:56','Guardar','127.0.0.1','4 - Radio 01571','Radioses'),(516,1,'2011-07-27 18:29:13','Guardar','127.0.0.1','5 - Racks48','Radioses'),(517,1,'2011-07-27 18:33:26','Actualizar','127.0.0.1','4 - Radio 01571','Radioses'),(518,1,'2011-07-27 18:35:30','Actualizar','127.0.0.1','5 - Racks48','Radioses'),(519,1,'2011-07-27 19:07:43','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Contratos'),(520,1,'2011-07-27 19:08:11','Actualizar','127.0.0.1','1 - jcinform.persistencia.Clientes[codigo=2]','Contratos'),(521,1,'2011-07-27 19:08:22','Actualizar','127.0.0.1','1 - jcinform.persistencia.Clientes[codigo=2]','Contratos'),(522,1,'2011-07-27 19:09:20','Actualizar','127.0.0.1','1 - jcinform.persistencia.Clientes[codigo=2]','Contratos'),(523,1,'2011-07-27 19:09:43','Actualizar','127.0.0.1','3 - jcinform.persistencia.Clientes[codigo=2]','Contratos'),(524,1,'2011-07-27 19:17:11','Actualizar','127.0.0.1','2 - jcinform.persistencia.Clientes[codigo=2]','Contratos'),(525,1,'2011-07-27 22:11:24','-','127.0.0.1','Ingreso Sistema','-'),(526,1,'2011-07-27 22:16:47','Guardar','127.0.0.1','jcinform.persistencia.Perfil[codigo=1]','Accesos'),(527,1,'2011-07-27 22:25:05','Guardar','127.0.0.1','jcinform.persistencia.Perfil[codigo=1]','Accesos'),(528,1,'2011-07-27 22:25:11','Guardar','127.0.0.1','jcinform.persistencia.Perfil[codigo=2]','Accesos'),(529,1,'2011-07-27 22:27:41','Guardar','127.0.0.1','Bastidas Miguel','Empleados'),(530,1,'2011-07-27 22:36:16','-','127.0.0.1','Ingreso Sistema','-'),(531,1,'2011-07-27 22:40:19','Guardar','127.0.0.1','jcinform.persistencia.Perfil[codigo=1]','Accesos'),(532,1,'2011-07-27 22:40:29','-','127.0.0.1','Ingreso Sistema','-'),(533,1,'2011-07-27 22:42:37','Guardar','127.0.0.1','jcinform.persistencia.Perfil[codigo=1]','Accesos'),(534,1,'2011-07-27 22:42:41','Guardar','127.0.0.1','jcinform.persistencia.Perfil[codigo=2]','Accesos'),(535,1,'2011-07-27 22:43:12','-','127.0.0.1','Ingreso Sistema','-'),(536,1,'2011-08-01 10:51:11','-','127.0.0.1','Ingreso Sistema','-'),(537,1,'2011-07-31 11:23:22','Guardar','127.0.0.1','55 - jcinform.persistencia.Clientes[codigo=10]','Contratos'),(538,1,'2011-07-31 11:28:40','Guardar','127.0.0.1','55 - jcinform.persistencia.Clientes[codigo=10]','Contratos'),(539,1,'2011-07-31 11:43:56','-','127.0.0.1','Ingreso Sistema','-'),(540,1,'2011-07-31 11:46:19','Guardar','127.0.0.1','55 - jcinform.persistencia.Clientes[codigo=10]','Contratos'),(541,1,'2011-07-31 11:46:45','Actualizar','127.0.0.1','55 - jcinform.persistencia.Clientes[codigo=10]','Contratos'),(542,1,'2011-07-31 11:54:46','-','127.0.0.1','Ingreso Sistema','-'),(543,1,'2011-07-31 11:55:17','Actualizar','127.0.0.1','55 - jcinform.persistencia.Clientes[codigo=10]','Contratos'),(544,1,'2011-08-01 11:58:15','-','127.0.0.1','Ingreso Sistema','-'),(545,1,'2011-08-01 11:58:35','Actualizar','127.0.0.1','55 - jcinform.persistencia.Clientes[codigo=10]','Contratos'),(546,1,'2011-08-01 11:58:54','Actualizar','127.0.0.1','55 - jcinform.persistencia.Clientes[codigo=10]','Contratos'),(547,1,'2011-08-01 12:00:19','Guardar','127.0.0.1','SUPER ADMINISTRADOR','Accesos'),(548,1,'2011-08-01 12:03:14','Actualizar','127.0.0.1','1 - COTOCOLLAO','Sectores'),(549,1,'2011-08-01 12:03:19','Actualizar','127.0.0.1','2 - CONDADO','Sectores'),(550,1,'2011-08-01 12:03:23','Actualizar','127.0.0.1','3 - Centro','Sectores'),(551,1,'2011-08-01 12:03:27','Actualizar','127.0.0.1','4 - Sur','Sectores'),(552,1,'2011-08-01 12:03:32','Actualizar','127.0.0.1','5 - Centro','Sectores'),(553,1,'2011-08-01 13:32:56','-','127.0.0.1','Ingreso Sistema','-'),(554,1,'2011-08-01 15:55:31','-','127.0.0.1','Ingreso Sistema','-'),(555,1,'2011-08-01 16:55:31','-','127.0.0.1','Ingreso Sistema','-'),(556,1,'2011-08-02 10:44:29','-','127.0.0.1','Ingreso Sistema','-'),(557,1,'2011-08-02 12:39:22','-','127.0.0.1','Ingreso Sistema','-');

/*Table structure for table `bancos` */

DROP TABLE IF EXISTS `bancos`;

CREATE TABLE `bancos` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) DEFAULT NULL,
  `cod` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `bancos` */

insert  into `bancos`(`codigo`,`nombre`,`cod`) values (1,'Bco. Pichincha','25'),(2,'Bco. Guayaquil','26'),(3,'Bco.Bolivariano','55'),(4,'Bco.del Austro','558'),(5,'Bco.Pacifico','82');

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
  `series` longtext,
  `documento` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FK_cabeceracompra_proveedores` (`proveedores`),
  KEY `FK_cabeceracompra_sucursal` (`sucursal`),
  CONSTRAINT `FK_cabeceracompra_proveedores` FOREIGN KEY (`proveedores`) REFERENCES `proveedores` (`codigo`),
  CONSTRAINT `FK_cabeceracompra_sucursal` FOREIGN KEY (`sucursal`) REFERENCES `sucursal` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=latin1;

/*Data for the table `cabeceracompra` */

insert  into `cabeceracompra`(`codigo`,`sucursal`,`factura`,`proveedores`,`fecha`,`cantidad`,`pu`,`subtotal`,`iva`,`total`,`series`,`documento`) values (1,NULL,1,1,'2011-07-25',6,NULL,NULL,NULL,22.00,'','COM'),(7,NULL,NULL,NULL,'2011-06-15',0,NULL,NULL,NULL,0.00,NULL,'VEN'),(8,NULL,NULL,NULL,'2011-06-15',0,NULL,NULL,NULL,0.00,NULL,'VEN'),(9,NULL,NULL,NULL,'2011-06-15',0,NULL,NULL,NULL,0.00,NULL,'VEN'),(10,NULL,NULL,NULL,'2011-06-21',0,NULL,NULL,NULL,0.00,NULL,'PRE'),(12,NULL,1414,1,'2011-07-26',5,NULL,NULL,NULL,77.00,'','COM'),(13,NULL,NULL,NULL,'2011-06-15',0,NULL,NULL,NULL,0.00,NULL,'VEN'),(15,NULL,NULL,NULL,'2011-06-15',0,NULL,NULL,NULL,0.00,NULL,'VEN'),(18,NULL,NULL,NULL,'2011-06-15',0,NULL,NULL,NULL,0.00,NULL,'PRE'),(19,NULL,NULL,NULL,'2011-06-15',0,NULL,NULL,NULL,0.00,NULL,'PRE'),(20,NULL,NULL,NULL,'2011-06-15',0,NULL,NULL,NULL,0.00,NULL,'PRE'),(21,NULL,NULL,NULL,'2011-06-15',0,NULL,NULL,NULL,0.00,NULL,'PRE');

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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

/*Data for the table `clientes` */

insert  into `clientes`(`codigo`,`identificacion`,`apellidos`,`nombres`,`direccion`,`telefono`,`sector`,`email`,`usuario`,`clave`,`formapago`) values (0,'9999999999999','CONSUMIDOR','FINAL','S/D','9999999999',1,'S/E','**********','',NULL),(1,'1717996134','Cahueñas','Angelica','Cotocollao','1234-567',1,'email','1717992267','LyGFRW9k08uTcgByx69OOg==',NULL),(2,'1309700548','Jadan','Geovanny','Condado Shopin','5255588',1,'geovanny178@hotmail.com','1309700548','aFHz+z8AQoFNQUxxZCdWHg==',NULL),(4,'1818181818001','Jadán','Carlos','Direccion','1111-111',1,'carlos@email.com','1818181818','6MSLxTVFX3DpkOmvfWWROQ==',NULL),(5,'1919191919','JADAN','STALIN','DIRECCION','8888-888',1,'stalin@hotmail.com','1919191919','px3pGHZORlK5zyK/u7zcfg==',NULL),(6,'1714846043','gualotuña henriquez','juan carlos','el centenario mz.19 cs.10','2763-544',3,'thegualo@hotmail.com','1714846043','1t1YyPcnj3wSw4dxh9tEIw==',NULL),(7,'1515245052','APELLIDOS','NUEVO CLIENTE','DIRECCION','DSFA',1,'','11111','',NULL),(8,'9999999999999','CONSUMIDOR','FINAL','S/D','9999999999',1,'S/E','**********','',NULL),(9,'171717171717','MARURI','PEDRO','DIRECCION','',1,'','','',NULL),(10,'1616161616','Jaramillo','Jorge','San Antonbio de','512251155',NULL,'',NULL,NULL,NULL);

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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

/*Data for the table `comisiones` */

insert  into `comisiones`(`codigo`,`plan`,`vendedores`,`porcentaje`) values (1,1,1,10.00),(2,2,1,10.00),(3,3,1,10.00),(6,1,2,10.00),(7,2,2,10.00),(8,3,2,10.00);

/*Table structure for table `contratos` */

DROP TABLE IF EXISTS `contratos`;

CREATE TABLE `contratos` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `estado` varchar(20) DEFAULT NULL,
  `sucursal` int(11) DEFAULT NULL,
  `contrato` int(11) DEFAULT NULL,
  `direccion` varchar(200) DEFAULT NULL,
  `referencia` varchar(200) DEFAULT NULL,
  `telefono` varchar(50) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `empleados` int(11) DEFAULT NULL,
  `registrador` int(11) DEFAULT NULL,
  `instalador` int(11) DEFAULT NULL,
  `clientes` int(11) DEFAULT NULL,
  `plan` int(11) DEFAULT NULL,
  `formapago` int(11) DEFAULT NULL,
  `nocuenta` varchar(20) DEFAULT NULL,
  `tipocuenta` varchar(20) DEFAULT NULL,
  `bancos` int(11) DEFAULT NULL,
  `diapago` int(11) DEFAULT NULL,
  `fechapago` date DEFAULT NULL,
  `ip` varchar(20) DEFAULT NULL,
  `usuario` varchar(30) DEFAULT NULL,
  `clave` varchar(30) DEFAULT NULL,
  `radios` int(11) DEFAULT NULL,
  `autorizado` tinyint(1) DEFAULT NULL,
  `fechainstalacion` datetime DEFAULT NULL,
  `fechafinal` datetime DEFAULT NULL,
  `equipos1` int(11) DEFAULT NULL,
  `equipos2` int(11) DEFAULT NULL,
  `equipos3` int(11) DEFAULT NULL,
  `serie1` varchar(20) DEFAULT NULL,
  `serie2` varchar(20) DEFAULT NULL,
  `serie3` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FK_contratos_nodos` (`radios`),
  KEY `FK_contratos_clientes` (`clientes`),
  KEY `FK_contratos_plan` (`plan`),
  KEY `FK_contratos_equipos2` (`equipos2`),
  KEY `FK_contratos_equipos1` (`equipos1`),
  KEY `FK_contratos_equipos3` (`equipos3`),
  KEY `FK_contratos` (`empleados`),
  KEY `FK_contratos_registrador` (`registrador`),
  KEY `FK_contratos_instalador` (`instalador`),
  KEY `FK_contratos_sucursal` (`sucursal`),
  KEY `FK_contratos_bancos` (`bancos`),
  CONSTRAINT `FK_contratos` FOREIGN KEY (`empleados`) REFERENCES `empleados` (`codigo`),
  CONSTRAINT `FK_contratos_bancos` FOREIGN KEY (`bancos`) REFERENCES `bancos` (`codigo`),
  CONSTRAINT `FK_contratos_clientes` FOREIGN KEY (`clientes`) REFERENCES `clientes` (`codigo`),
  CONSTRAINT `FK_contratos_instalador` FOREIGN KEY (`instalador`) REFERENCES `empleados` (`codigo`),
  CONSTRAINT `FK_contratos_plan` FOREIGN KEY (`plan`) REFERENCES `plan` (`codigo`),
  CONSTRAINT `FK_contratos_radios` FOREIGN KEY (`radios`) REFERENCES `radios` (`codigo`),
  CONSTRAINT `FK_contratos_registrador` FOREIGN KEY (`registrador`) REFERENCES `empleados` (`codigo`),
  CONSTRAINT `FK_contratos_sucursal` FOREIGN KEY (`sucursal`) REFERENCES `sucursal` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

/*Data for the table `contratos` */

insert  into `contratos`(`codigo`,`estado`,`sucursal`,`contrato`,`direccion`,`referencia`,`telefono`,`fecha`,`empleados`,`registrador`,`instalador`,`clientes`,`plan`,`formapago`,`nocuenta`,`tipocuenta`,`bancos`,`diapago`,`fechapago`,`ip`,`usuario`,`clave`,`radios`,`autorizado`,`fechainstalacion`,`fechafinal`,`equipos1`,`equipos2`,`equipos3`,`serie1`,`serie2`,`serie3`) values (3,'Terminado',1,1,'5255588','','5255588','2011-06-15',2,1,NULL,2,1,1,'','',1,1,NULL,'','','',4,0,'2011-06-15 00:00:00',NULL,NULL,NULL,NULL,'','',NULL),(4,'Suspendido',1,2,'5255588','','5255588','2011-06-15',1,1,NULL,2,1,1,'00012155001','COR',2,13,NULL,'','','',4,0,'2011-07-25 00:00:00',NULL,NULL,NULL,NULL,'','',NULL),(5,'Activo',1,3,'5255588','1','5255588','2011-06-15',2,1,NULL,2,3,1,'58601552187','AHO',4,1,NULL,'1','11','1',4,0,'2011-06-15 00:00:00',NULL,NULL,NULL,NULL,'','',NULL),(6,'Activo',1,4,'Cotocollao','','1234-567','2011-06-21',1,1,NULL,1,3,1,'','',2,1,NULL,'','','',3,0,'2011-06-21 00:00:00',NULL,NULL,NULL,NULL,'','',NULL),(7,'Activo',1,5,'.','.','.','2011-07-22',2,1,NULL,5,1,1,'666666','AHO',1,1,'2011-07-22','.','.','.',3,1,'2011-07-22 15:17:17','2011-07-22 15:17:17',NULL,NULL,NULL,NULL,NULL,NULL),(10,'Activo',1,55,'San Antonbio de','9i99','512251155','2011-07-31',2,1,NULL,10,1,1,'','',NULL,1,'2011-07-31','','','',5,1,'2011-08-01 00:00:00','2011-07-31 11:46:00',NULL,NULL,NULL,NULL,NULL,NULL);

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
  `codigo` int(20) NOT NULL,
  `factura` int(20) DEFAULT NULL,
  `debe` decimal(9,2) DEFAULT NULL,
  `haber` decimal(9,2) DEFAULT NULL,
  `total` decimal(9,2) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `tipo` varchar(10) DEFAULT NULL,
  `descuento` decimal(9,2) DEFAULT NULL,
  `efectivo` decimal(9,2) DEFAULT NULL,
  `cheque` decimal(9,2) DEFAULT NULL,
  `debito` decimal(9,2) DEFAULT NULL,
  `tarjeta` decimal(9,2) DEFAULT NULL,
  `nocheque` varchar(30) DEFAULT NULL,
  `notarjeta` varchar(30) DEFAULT NULL,
  `nocuenta` varchar(30) DEFAULT NULL,
  `empleados` int(11) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FK_cxcobrar_factura` (`factura`),
  KEY `FK_cxcobrar_empleados` (`empleados`),
  CONSTRAINT `FK_cxcobrar_empleados` FOREIGN KEY (`empleados`) REFERENCES `empleados` (`codigo`),
  CONSTRAINT `FK_cxcobrar_fac` FOREIGN KEY (`factura`) REFERENCES `factura` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `cxcobrar` */

insert  into `cxcobrar`(`codigo`,`factura`,`debe`,`haber`,`total`,`fecha`,`tipo`,`descuento`,`efectivo`,`cheque`,`debito`,`tarjeta`,`nocheque`,`notarjeta`,`nocuenta`,`empleados`) values (1,1,'56.00','0.00','56.00','2011-07-01',NULL,NULL,'0.00','0.00','0.00',NULL,'','',NULL,NULL),(2,2,'56.00','0.00','56.00','2011-07-01',NULL,NULL,'0.00','0.00','0.00',NULL,'','',NULL,NULL),(3,3,'16.80','0.00','16.80','2011-07-01',NULL,NULL,'0.00','0.00','0.00',NULL,'','',NULL,NULL),(4,4,'22.65','0.00','22.65','2011-07-26',NULL,NULL,'0.00','0.00','0.00',NULL,'','',NULL,NULL),(5,5,'100.21','0.00','100.21','2011-07-26',NULL,NULL,'0.00','0.00','0.00',NULL,'','',NULL,NULL),(6,1,'0.00','10.00',NULL,'2011-07-26','P','0.00','10.00','0.00','0.00','0.00','','',NULL,NULL),(7,5,'0.00','10.00',NULL,'2011-07-26','P','0.00','10.00','0.00','0.00','0.00','','',NULL,NULL),(8,5,'0.00','10.00',NULL,'2011-07-26','P','0.00','10.00','0.00','0.00','0.00','','',NULL,NULL),(9,5,'0.00','10.00',NULL,'2011-07-26','P','0.00','10.00','0.00','0.00','0.00','','',NULL,NULL),(10,5,'0.00','5.00',NULL,'2011-07-26','P','0.00','5.00','0.00','0.00','0.00','','',NULL,NULL),(11,5,'0.00','65.21',NULL,'2011-07-26','P','0.00','65.21','0.00','0.00','0.00','','',NULL,NULL),(12,4,'0.00','2.00',NULL,'2011-07-26','P','0.00','2.00','0.00','0.00','0.00','','',NULL,NULL),(13,4,'0.00','20.65',NULL,'2011-07-26','P','0.00','20.65','0.00','0.00','0.00','','',NULL,NULL),(14,1,'0.00','10.00',NULL,'2011-07-26','P','0.00','10.00','0.00','0.00','0.00','','',NULL,NULL),(15,1,'0.00','6.00',NULL,'2011-07-26','P','0.00','6.00','0.00','0.00','0.00','','',NULL,NULL),(16,1,'0.00','7.00',NULL,'2011-07-26','P','0.00','7.00','0.00','0.00','0.00','','',NULL,NULL),(17,1,'0.00','12.00',NULL,'2011-07-26','P','0.00','12.00','0.00','0.00','0.00','','',NULL,NULL),(18,1,'0.00','2.00',NULL,'2011-07-26','P','0.00','2.00','0.00','0.00','0.00','','',NULL,NULL),(19,1,'0.00','9.00',NULL,'2011-07-26','P','0.00','9.00','0.00','0.00','0.00','','',NULL,NULL),(20,6,'16.80','0.00','16.80','2011-07-01',NULL,'0.00','0.00','0.00','0.00',NULL,'','',NULL,NULL),(21,6,'0.00','1.00',NULL,'2011-07-27','P','0.00','1.00','0.00','0.00','0.00','','',NULL,NULL),(23,8,'56.00','0.00','56.00','2011-08-01',NULL,'0.00','0.00','0.00','0.00',NULL,'','',NULL,NULL),(24,9,'56.00','0.00','56.00','2011-08-01',NULL,'0.00','0.00','0.00','0.00',NULL,'','',NULL,NULL),(25,10,'16.80','0.00','16.80','2011-08-01',NULL,'0.00','0.00','0.00','0.00',NULL,'','',NULL,NULL),(26,11,'16.80','0.00','16.80','2011-08-01',NULL,'0.00','0.00','0.00','0.00',NULL,'','',NULL,NULL);

/*Table structure for table `descuentos` */

DROP TABLE IF EXISTS `descuentos`;

CREATE TABLE `descuentos` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `factura` int(11) DEFAULT NULL,
  `empleados` int(11) DEFAULT NULL,
  `valor` decimal(9,2) DEFAULT '0.00',
  `aplicado` tinyint(1) DEFAULT '0',
  `fecha` date DEFAULT NULL,
  `fechaaplicado` date DEFAULT NULL,
  `motivo` longtext,
  PRIMARY KEY (`codigo`),
  KEY `FK_descuentos_factura` (`factura`),
  KEY `FK_descuentos_empleados` (`empleados`),
  CONSTRAINT `FK_descuentos_empleados` FOREIGN KEY (`empleados`) REFERENCES `empleados` (`codigo`),
  CONSTRAINT `FK_descuentos_factura` FOREIGN KEY (`factura`) REFERENCES `factura` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `descuentos` */

insert  into `descuentos`(`codigo`,`factura`,`empleados`,`valor`,`aplicado`,`fecha`,`fechaaplicado`,`motivo`) values (1,2,1,'4.00',0,'2011-08-02',NULL,'Para probar nomas....');

/*Table structure for table `detalle` */

DROP TABLE IF EXISTS `detalle`;

CREATE TABLE `detalle` (
  `codigo` int(20) NOT NULL,
  `factura` int(20) DEFAULT NULL,
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
  CONSTRAINT `FK_detalle_equipos` FOREIGN KEY (`equipos`) REFERENCES `equipos` (`codigo`),
  CONSTRAINT `FK_detalle_factira` FOREIGN KEY (`factura`) REFERENCES `factura` (`codigo`),
  CONSTRAINT `FK_detalle_plan_oproducto` FOREIGN KEY (`plan`) REFERENCES `plan` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `detalle` */

insert  into `detalle`(`codigo`,`factura`,`plan`,`equipos`,`mes`,`cantidad`,`precio`,`descuento`,`beca`,`total`,`descripcion`,`anio`,`asignado`) values (1,1,3,NULL,7,1,NULL,NULL,NULL,'50.00','generada',2011,NULL),(2,2,3,NULL,7,1,NULL,NULL,NULL,'50.00','generada',2011,NULL),(3,3,1,NULL,7,1,NULL,NULL,NULL,'15.00','generada',2011,NULL),(4,4,NULL,1,7,1,NULL,NULL,NULL,'20.22',NULL,2011,NULL),(5,5,NULL,2,7,1,NULL,NULL,NULL,'89.47',NULL,2011,NULL),(6,6,1,NULL,7,1,NULL,NULL,NULL,'15.00','generada',2011,NULL),(8,8,3,NULL,8,1,NULL,NULL,NULL,'50.00','generada',2011,NULL),(9,9,3,NULL,8,1,NULL,NULL,NULL,'50.00','generada',2011,NULL),(10,10,1,NULL,8,1,NULL,NULL,NULL,'15.00','generada',2011,NULL),(11,11,1,NULL,8,1,NULL,NULL,NULL,'15.00','generada',2011,NULL);

/*Table structure for table `detallecompra` */

DROP TABLE IF EXISTS `detallecompra`;

CREATE TABLE `detallecompra` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `compra` int(11) DEFAULT NULL,
  `contrato` int(11) DEFAULT NULL,
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
  KEY `FK_detallecompra_contrato` (`contrato`),
  CONSTRAINT `FK_detallecompra_cabecompra` FOREIGN KEY (`compra`) REFERENCES `cabeceracompra` (`codigo`) ON DELETE CASCADE,
  CONSTRAINT `FK_controlequipos` FOREIGN KEY (`equipos`) REFERENCES `equipos` (`codigo`),
  CONSTRAINT `FK_detallecompra_contrato` FOREIGN KEY (`contrato`) REFERENCES `contratos` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=latin1;

/*Data for the table `detallecompra` */

insert  into `detallecompra`(`codigo`,`compra`,`contrato`,`equipos`,`cantidad`,`costo`,`pvp1`,`pvp2`,`pvp3`,`pvp4`,`asignado`,`estado`) values (1,1,NULL,3,3,10.00,NULL,NULL,NULL,NULL,NULL,NULL),(2,1,NULL,1,3,12.00,NULL,NULL,NULL,NULL,NULL,NULL),(6,13,5,1,-3,20.22,NULL,NULL,NULL,NULL,NULL,NULL),(7,8,5,1,-1,20.22,NULL,NULL,NULL,NULL,NULL,NULL),(8,8,5,3,-1,18.00,NULL,NULL,NULL,NULL,NULL,NULL),(9,9,5,4,-1,25.00,NULL,NULL,NULL,NULL,NULL,NULL),(10,10,6,3,-1,18.00,NULL,NULL,NULL,NULL,NULL,NULL),(12,12,NULL,2,2,52.00,NULL,NULL,NULL,NULL,NULL,NULL),(13,12,NULL,1,3,25.00,NULL,NULL,NULL,NULL,NULL,NULL),(15,15,5,2,-1,89.47,NULL,NULL,NULL,NULL,NULL,NULL),(18,20,5,3,-1,18.00,NULL,NULL,NULL,NULL,NULL,NULL);

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

/*Table structure for table `empleadosfacturas` */

DROP TABLE IF EXISTS `empleadosfacturas`;

CREATE TABLE `empleadosfacturas` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `fecha` datetime DEFAULT NULL,
  `empleados` int(11) DEFAULT NULL,
  `factura` int(11) DEFAULT NULL,
  `cobrado` tinyint(1) DEFAULT '0',
  `visitado` tinyint(1) DEFAULT '0',
  `fecharegreso` datetime DEFAULT NULL,
  `saldo` decimal(9,2) DEFAULT '0.00',
  `total` decimal(9,2) DEFAULT '0.00',
  `descuento` decimal(9,2) DEFAULT '0.00',
  `efectivo` decimal(9,2) DEFAULT '0.00',
  `cheque` decimal(9,2) DEFAULT '0.00',
  `nocheque` varchar(20) DEFAULT NULL,
  `debito` decimal(9,2) DEFAULT '0.00',
  `nocuenta` varchar(20) DEFAULT NULL,
  `tarjeta` decimal(9,2) DEFAULT '0.00',
  `notarjeta` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FK_empleadosfacturas_empledos` (`empleados`),
  KEY `FK_empleadosfacturas_factura` (`factura`),
  CONSTRAINT `FK_empleadosfacturas_empledos` FOREIGN KEY (`empleados`) REFERENCES `empleados` (`codigo`),
  CONSTRAINT `FK_empleadosfacturas_factura` FOREIGN KEY (`factura`) REFERENCES `factura` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=latin1;

/*Data for the table `empleadosfacturas` */

insert  into `empleadosfacturas`(`codigo`,`fecha`,`empleados`,`factura`,`cobrado`,`visitado`,`fecharegreso`,`saldo`,`total`,`descuento`,`efectivo`,`cheque`,`nocheque`,`debito`,`nocuenta`,`tarjeta`,`notarjeta`) values (76,'2011-08-02 12:40:06',1,2,NULL,NULL,NULL,'56.00',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(77,'2011-08-02 12:40:06',1,10,NULL,NULL,NULL,'16.80',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(78,'2011-08-02 12:40:06',1,3,NULL,NULL,NULL,'16.80',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(79,'2011-08-02 12:40:06',1,9,NULL,NULL,NULL,'56.00',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(80,'2011-08-02 12:40:06',1,11,NULL,NULL,NULL,'16.80',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

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
  `instalacion` decimal(6,2) DEFAULT '70.00',
  `IVA` decimal(9,2) DEFAULT '12.00',
  PRIMARY KEY (`ruc`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `empresa` */

insert  into `empresa`(`ruc`,`razonsocial`,`representante`,`direccion`,`telefono`,`telefono2`,`movil`,`documento`,`email`,`web`,`reportes`,`fotos`,`usuariomail`,`clavemail`,`autorizacion`,`smtp`,`puerto`,`logo`,`star`,`instalacion`,`IVA`) values ('1309700548001','JCINFORM SOLUCIONES INFORMATICAS','GEOVANNY JADAN','José Miguel Carrión N10-811 y Diego Vaca','510-3843','51515','561551','000001','jpeg','www.jcinform.com','c:\\reportesisp\\','c:\\fotosisp\\','email','claveemail',1,'mail.smtp.gmail','587',NULL,1,'60.00','12.00');

/*Table structure for table `equipos` */

DROP TABLE IF EXISTS `equipos`;

CREATE TABLE `equipos` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) DEFAULT NULL,
  `modelo` varchar(50) DEFAULT NULL,
  `tipo` varchar(50) DEFAULT NULL,
  `caracteristica` varchar(50) DEFAULT NULL,
  `marcas` int(11) DEFAULT NULL,
  `costo` decimal(9,2) DEFAULT NULL,
  `pvp1` decimal(9,2) DEFAULT NULL,
  `pvp2` decimal(9,2) DEFAULT NULL,
  `pvp3` decimal(9,2) DEFAULT NULL,
  `pvp4` decimal(9,2) DEFAULT NULL,
  `bien` decimal(1,0) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FK_equipos_marcas` (`marcas`),
  CONSTRAINT `FK_equipos_marcas` FOREIGN KEY (`marcas`) REFERENCES `marcas` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `equipos` */

insert  into `equipos`(`codigo`,`nombre`,`modelo`,`tipo`,`caracteristica`,`marcas`,`costo`,`pvp1`,`pvp2`,`pvp3`,`pvp4`,`bien`) values (1,'Router','6765',NULL,NULL,1,NULL,'20.22','15.00',NULL,NULL,'1'),(2,'Switch','8677','',NULL,2,NULL,'89.47','18.00',NULL,NULL,'1'),(3,'Antena','677',NULL,NULL,2,NULL,'18.00','19.00',NULL,NULL,'1'),(4,'Instalación del Servicio','s/m','001',NULL,3,NULL,'25.00',NULL,NULL,NULL,'0'),(5,'Reinstalación del servicio','s/m','002',NULL,3,NULL,'14.27',NULL,NULL,NULL,'0');

/*Table structure for table `factura` */

DROP TABLE IF EXISTS `factura`;

CREATE TABLE `factura` (
  `codigo` int(20) NOT NULL,
  `sucursal` int(11) DEFAULT NULL,
  `numero` varchar(20) DEFAULT NULL,
  `contratos` int(11) DEFAULT NULL,
  `sector` int(11) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `clientes` int(11) DEFAULT NULL,
  `subtotal` decimal(9,2) DEFAULT NULL,
  `descuento` decimal(9,2) DEFAULT NULL,
  `baseiva` decimal(9,2) DEFAULT NULL,
  `porcentajeiva` decimal(9,2) DEFAULT NULL,
  `valoriva` decimal(9,2) DEFAULT NULL,
  `total` decimal(9,2) DEFAULT NULL,
  `estado` tinyint(1) DEFAULT NULL,
  `observacion` varchar(500) DEFAULT NULL,
  `impresa` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`codigo`),
  UNIQUE KEY `NewIndex1` (`numero`),
  KEY `FK_cabecerafactura` (`clientes`),
  KEY `FK_factura_sector` (`sector`),
  KEY `FK_factura_sucursal` (`sucursal`),
  KEY `FK_factura_contratos` (`contratos`),
  CONSTRAINT `FK_factura_clientes` FOREIGN KEY (`clientes`) REFERENCES `clientes` (`codigo`),
  CONSTRAINT `FK_factura_contratos` FOREIGN KEY (`contratos`) REFERENCES `contratos` (`codigo`),
  CONSTRAINT `FK_factura_sector` FOREIGN KEY (`sector`) REFERENCES `sector` (`codigo`),
  CONSTRAINT `FK_factura_sucursal` FOREIGN KEY (`sucursal`) REFERENCES `sucursal` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `factura` */

insert  into `factura`(`codigo`,`sucursal`,`numero`,`contratos`,`sector`,`fecha`,`clientes`,`subtotal`,`descuento`,`baseiva`,`porcentajeiva`,`valoriva`,`total`,`estado`,`observacion`,`impresa`) values (1,1,'001002FAC0001023',5,NULL,'2011-07-01',2,'50.00','0.00','50.00','12.00','6.00','56.00',1,NULL,0),(2,1,'001002FAC0000004',6,NULL,'2011-07-01',1,'50.00','0.00','50.00','12.00','6.00','56.00',1,NULL,0),(3,1,'001002FAC0000006',7,NULL,'2011-07-01',5,'15.00','0.00','15.00','12.00','1.80','16.80',1,NULL,0),(4,1,NULL,5,NULL,'2011-07-26',2,'20.22','0.00','20.22','12.00','2.43','22.65',1,'V',0),(5,1,'001002FAC0001024',5,NULL,'2011-07-26',2,'89.47','0.00','89.47','12.00','10.74','100.21',1,'V',0),(6,1,'001002FAC0000001',3,NULL,'2011-07-01',2,'15.00','0.00','15.00','12.00','1.80','16.80',1,NULL,0),(8,1,'001002FAC0000003',5,NULL,'2011-08-01',2,'50.00','0.00','50.00','12.00','6.00','56.00',1,NULL,0),(9,1,'001002FAC0000008',6,NULL,'2011-08-01',1,'50.00','0.00','50.00','12.00','6.00','56.00',1,NULL,0),(10,1,'001002FAC0000005',7,NULL,'2011-08-01',5,'15.00','0.00','15.00','12.00','1.80','16.80',1,NULL,0),(11,1,'001002FAC0000009',10,NULL,'2011-08-01',10,'15.00','0.00','15.00','12.00','1.80','16.80',1,NULL,0);

/*Table structure for table `marcas` */

DROP TABLE IF EXISTS `marcas`;

CREATE TABLE `marcas` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `marcas` */

insert  into `marcas`(`codigo`,`nombre`) values (1,'TRENDNET'),(2,'DLINK'),(3,'S/M');

/*Table structure for table `nodos` */

DROP TABLE IF EXISTS `nodos`;

CREATE TABLE `nodos` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) DEFAULT NULL,
  `direccion` varchar(200) DEFAULT NULL,
  `torre` varchar(200) DEFAULT NULL,
  `observaciones` longtext,
  `sector` int(11) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FK_nodos_sector` (`sector`),
  CONSTRAINT `FK_nodos_sector` FOREIGN KEY (`sector`) REFERENCES `sector` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `nodos` */

insert  into `nodos`(`codigo`,`nombre`,`direccion`,`torre`,`observaciones`,`sector`) values (1,'Nodo Ac25','dire','torr','',1),(2,'Nodo C2ksik','4444','','',3);

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
  `sucursal` int(11) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FK_plan_sucursal` (`sucursal`),
  CONSTRAINT `FK_plan_sucursal` FOREIGN KEY (`sucursal`) REFERENCES `sucursal` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `plan` */

insert  into `plan`(`codigo`,`nombre`,`valor`,`pvp1`,`pvp2`,`pvp3`,`pvp4`,`tipo`,`dias`,`bien`,`sucursal`) values (1,'ADSL 256 KB',15.00,NULL,NULL,NULL,NULL,'BASICO',30,0,1),(2,'ADSL 512 KB',20.00,NULL,NULL,NULL,NULL,'BASICO',30,0,1),(3,'ADSL 1GB',50.00,NULL,NULL,NULL,NULL,'HOME',30,0,1);

/*Table structure for table `procesos` */

DROP TABLE IF EXISTS `procesos`;

CREATE TABLE `procesos` (
  `fechastring` varchar(14) NOT NULL,
  `fecha` date DEFAULT NULL COMMENT 'para ver que mes ya se ejecuto',
  `ejecutado` tinyint(1) DEFAULT NULL COMMENT 'si ya ha sido ejecutado',
  `fechaejecutado` datetime DEFAULT NULL COMMENT 'cuando se ejecuto',
  `problemas` longtext COMMENT 'si se ejecuto y hubo problemas',
  `empleado` varchar(200) DEFAULT NULL COMMENT 'que empleado lo hizo, o subproceso',
  PRIMARY KEY (`fechastring`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `procesos` */

insert  into `procesos`(`fechastring`,`fecha`,`ejecutado`,`fechaejecutado`,`problemas`,`empleado`) values ('120117','2011-07-01',1,'2011-07-01 14:55:53','Re-ejecutado',NULL),('120118','2011-08-01',1,'2011-08-01 10:51:11','Re-ejecutado',NULL),('220117','2011-07-01',1,'2011-07-01 14:55:53',NULL,NULL),('320117','2011-07-01',1,'2011-07-01 14:55:53',NULL,NULL);

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
  `ip` varchar(20) DEFAULT NULL,
  `ssid` varchar(50) DEFAULT NULL,
  `seguridad` varchar(50) DEFAULT NULL,
  `usuario` varchar(50) DEFAULT NULL,
  `clave` varchar(50) DEFAULT NULL,
  `observaciones` longtext,
  PRIMARY KEY (`codigo`),
  KEY `FK_radios_radios` (`nodos`),
  CONSTRAINT `FK_radios_radios` FOREIGN KEY (`nodos`) REFERENCES `nodos` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `radios` */

insert  into `radios`(`codigo`,`nombre`,`nodos`,`ip`,`ssid`,`seguridad`,`usuario`,`clave`,`observaciones`) values (3,'Radio 0012',1,'111','111','11','11','pe5ESRVYB7U=',NULL),(4,'Radio 01571',2,'192.168.10.2','4s52s1d15','515sd1s5d','user','6hWZ90L8uDI=',NULL),(5,'Racks48',1,'10.10.10.1','','','','',NULL);

/*Table structure for table `sector` */

DROP TABLE IF EXISTS `sector`;

CREATE TABLE `sector` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `numero` int(11) DEFAULT NULL,
  `nombre` varchar(100) DEFAULT NULL,
  `canton` int(11) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FK_sector_canton` (`canton`),
  CONSTRAINT `FK_sector_canton` FOREIGN KEY (`canton`) REFERENCES `canton` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `sector` */

insert  into `sector`(`codigo`,`numero`,`nombre`,`canton`) values (1,1,'COTOCOLLAO',1),(2,2,'CONDADO',1),(3,3,'Centro',2),(4,4,'Sur',1),(5,5,'Centro',1);

/*Table structure for table `series` */

DROP TABLE IF EXISTS `series`;

CREATE TABLE `series` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `serie` varchar(50) DEFAULT NULL,
  `detallecompra` int(11) DEFAULT NULL,
  `contratos` int(11) DEFAULT NULL,
  `estado` varchar(50) DEFAULT NULL COMMENT 'Ajuste,Compra,Vendido,Prestamo',
  PRIMARY KEY (`codigo`),
  KEY `FK_series_detallecompra` (`detallecompra`),
  KEY `FK_series_contratos` (`contratos`),
  CONSTRAINT `FK_series_detallecompra` FOREIGN KEY (`detallecompra`) REFERENCES `detallecompra` (`codigo`) ON DELETE CASCADE,
  CONSTRAINT `FK_series_contratos` FOREIGN KEY (`contratos`) REFERENCES `contratos` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=latin1;

/*Data for the table `series` */

insert  into `series`(`codigo`,`serie`,`detallecompra`,`contratos`,`estado`) values (1,'5',1,NULL,'C'),(2,'6',1,NULL,'C'),(3,'7',1,NULL,'C'),(4,'1',2,NULL,'C'),(5,'2',2,NULL,'C'),(6,'3',2,NULL,'C'),(8,'2',6,5,'V'),(9,'3',6,5,'V'),(10,'1',7,5,'V'),(11,'5',8,5,'V'),(12,'001',9,5,'V'),(13,'6',10,6,'P'),(15,'8',12,NULL,'C'),(16,'9',12,NULL,'C'),(17,'10',13,NULL,'C'),(18,'11',13,NULL,'C'),(19,'12',13,NULL,'C'),(20,'10',6,5,'V'),(22,'8',15,5,'V'),(25,'7',18,5,'P');

/*Table structure for table `soporte` */

DROP TABLE IF EXISTS `soporte`;

CREATE TABLE `soporte` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `clientes` int(11) DEFAULT NULL,
  `empleados` int(11) DEFAULT NULL,
  `fecha` datetime DEFAULT NULL,
  `observacion` longtext,
  `observacion2` longtext,
  `observacion3` longtext,
  `generada` tinyint(1) DEFAULT NULL,
  `noorden` int(11) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FK_soporte_clientes` (`clientes`),
  KEY `FK_soporte_empleados` (`empleados`),
  CONSTRAINT `FK_soporte_clientes` FOREIGN KEY (`clientes`) REFERENCES `clientes` (`codigo`),
  CONSTRAINT `FK_soporte_empleados` FOREIGN KEY (`empleados`) REFERENCES `empleados` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `soporte` */

insert  into `soporte`(`codigo`,`clientes`,`empleados`,`fecha`,`observacion`,`observacion2`,`observacion3`,`generada`,`noorden`) values (1,2,1,'2011-06-07 10:14:33','NOdknfgasd',NULL,NULL,0,NULL),(2,2,1,'2011-06-07 10:18:50','Cambios en ',NULL,NULL,0,NULL),(3,2,1,'2011-06-07 10:28:21','NO se realizo correctamente la instalación sdsd',NULL,NULL,1,1),(4,2,1,'2011-06-07 17:40:33','NO se me conecta. ',NULL,NULL,1,2),(5,1,1,'2011-06-23 22:12:53','LLamo a ver que pasa con el servicio, y está cortado debido a falta de pago.',NULL,NULL,1,3);

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
