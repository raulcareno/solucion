/*
SQLyog Community v9.20 
MySQL - 6.0.4-alpha-community : Database - calificador
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`calificador` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `calificador`;

/*Table structure for table `accesos` */

DROP TABLE IF EXISTS `accesos`;

CREATE TABLE `accesos` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `perfil` int(11) DEFAULT NULL,
  `pantalla` varchar(100) DEFAULT NULL,
  `agregar` tinyint(1) DEFAULT '0',
  `modificar` tinyint(1) DEFAULT '0',
  `eliminar` tinyint(1) DEFAULT '0',
  `ingresar` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`codigo`),
  KEY `FK_perfil` (`perfil`),
  KEY `FKB9CEF713F676C6AB` (`perfil`),
  CONSTRAINT `FK_perfil` FOREIGN KEY (`perfil`) REFERENCES `global` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=latin1;

/*Data for the table `accesos` */

insert  into `accesos`(`codigo`,`perfil`,`pantalla`,`agregar`,`modificar`,`eliminar`,`ingresar`) values (1,NULL,'Reconfigurar',1,1,1,1),(2,NULL,'Accesos',1,1,1,1),(3,NULL,'Calificaciones',1,1,1,1),(4,NULL,'Reportes',1,1,1,1),(5,NULL,'Operadores',1,1,1,1),(6,NULL,'Empresa',1,1,1,1),(7,NULL,'Auditoria',1,1,1,1),(8,NULL,'Tipos',1,1,1,1),(10,100,'Reconfigurar',1,1,1,1),(11,100,'Accesos',1,1,1,1),(12,100,'Calificaciones',1,1,1,1),(13,100,'Reportes',1,1,1,1),(14,100,'Operadores',1,1,1,1),(15,100,'Empresa',1,1,1,1),(16,100,'Auditoria',1,1,1,1),(17,100,'Tipos',1,1,1,1);

/*Table structure for table `auditoria` */

DROP TABLE IF EXISTS `auditoria`;

CREATE TABLE `auditoria` (
  `auditoria` int(11) NOT NULL AUTO_INCREMENT,
  `tabla` varchar(100) DEFAULT NULL,
  `maquina` varchar(100) DEFAULT NULL,
  `accion` varchar(100) DEFAULT NULL,
  `campo` varchar(100) DEFAULT NULL,
  `usuario` int(11) DEFAULT NULL,
  `fecha` datetime DEFAULT NULL,
  PRIMARY KEY (`auditoria`),
  KEY `FK_auditoriausuario` (`usuario`),
  KEY `FKB8A64963D17606F` (`usuario`),
  CONSTRAINT `FK_auditoriausuario` FOREIGN KEY (`usuario`) REFERENCES `usuarios` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;

/*Data for the table `auditoria` */

insert  into `auditoria`(`auditoria`,`tabla`,`maquina`,`accion`,`campo`,`usuario`,`fecha`) values (1,'','inform IP: 192.168.10.100','Ingreso al Sistema','',1,'2011-10-31 09:06:39'),(2,'','inform IP: 192.168.10.100','Salir del Sistema','',1,'2011-10-31 09:07:27'),(3,'','inform IP: 192.168.10.100','Ingreso al Sistema','',1,'2011-10-31 09:07:38'),(4,'','inform IP: 192.168.10.100','Salir del Sistema','',1,'2011-10-31 09:17:28'),(5,'','inform IP: 192.168.10.100','Ingreso al Sistema','',1,'2011-10-31 09:17:40'),(6,'','inform IP: 192.168.10.100','Ingreso al Sistema','',1,'2011-10-31 10:47:41'),(7,'','inform IP: 192.168.10.100','Salir del Sistema','',1,'2011-10-31 10:49:15'),(8,'','inform IP: 192.168.10.100','Ingreso al Sistema','',1,'2011-10-31 10:50:33'),(9,'','inform IP: 192.168.10.100','Salir del Sistema','',1,'2011-10-31 10:50:56'),(10,'','inform IP: 192.168.10.100','Ingreso al Sistema','',1,'2011-10-31 10:51:15'),(11,'','inform IP: 192.168.10.100','Salir del Sistema','',1,'2011-10-31 10:52:41'),(12,'','inform IP: 192.168.10.100','Ingreso al Sistema','',1,'2011-10-31 10:52:53'),(13,'','inform IP: 192.168.10.100','Salir del Sistema','',1,'2011-10-31 10:53:57'),(14,'','inform IP: 192.168.10.100','Ingreso al Sistema','',1,'2011-10-31 10:54:07'),(15,'','inform IP: 192.168.10.100','Salir del Sistema','',1,'2011-10-31 10:54:41'),(16,'','inform IP: 192.168.10.100','Ingreso al Sistema','',1,'2011-10-31 11:13:14'),(17,'','inform IP: 192.168.10.100','Salir del Sistema','',1,'2011-10-31 11:28:17');

/*Table structure for table `calificaciones` */

DROP TABLE IF EXISTS `calificaciones`;

CREATE TABLE `calificaciones` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `usuarios` int(11) DEFAULT NULL,
  `tipos` int(11) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `hora` time DEFAULT NULL,
  `fechahora` datetime DEFAULT NULL,
  `clientes` int(11) DEFAULT NULL,
  `consola` int(11) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FK_calificaciones_tipos` (`tipos`),
  KEY `FK_calificaciones_uisuarios` (`usuarios`),
  KEY `FK_calificaciones_clientes` (`clientes`),
  CONSTRAINT `FK_calificaciones_clientes` FOREIGN KEY (`clientes`) REFERENCES `clientes` (`codigo`),
  CONSTRAINT `FK_calificaciones_tipos` FOREIGN KEY (`tipos`) REFERENCES `tipos` (`codigo`),
  CONSTRAINT `FK_calificaciones_uisuarios` FOREIGN KEY (`usuarios`) REFERENCES `usuarios` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `calificaciones` */

/*Table structure for table `clientes` */

DROP TABLE IF EXISTS `clientes`;

CREATE TABLE `clientes` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `identificacion` varchar(16) DEFAULT NULL,
  `nombres` varchar(200) DEFAULT NULL,
  `direccion` varchar(200) DEFAULT NULL,
  `telefono` varchar(50) DEFAULT NULL,
  `tipo` varchar(20) DEFAULT NULL,
  `estado` tinyint(1) DEFAULT '1',
  `ultimoacceso` datetime DEFAULT NULL,
  `acceso` datetime DEFAULT NULL,
  `tarifamensual` double(10,2) DEFAULT NULL,
  `producto` int(11) DEFAULT NULL,
  `valor` double(10,2) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FK_clientes_produc` (`producto`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `clientes` */

/*Table structure for table `empresa` */

DROP TABLE IF EXISTS `empresa`;

CREATE TABLE `empresa` (
  `ruc` varchar(16) NOT NULL,
  `nombre` varchar(200) DEFAULT NULL,
  `razon` varchar(200) DEFAULT NULL,
  `direccion` varchar(200) DEFAULT NULL,
  `telefonos` varchar(50) DEFAULT NULL,
  `documentofac` varchar(20) DEFAULT NULL,
  `documentonota` varchar(20) DEFAULT NULL,
  `documentoticket` varchar(10) DEFAULT NULL,
  `parqueaderos` int(11) DEFAULT NULL,
  `impticket` varchar(50) DEFAULT NULL,
  `impfactura` varchar(50) DEFAULT NULL,
  `impresora` varchar(50) DEFAULT NULL,
  `impresora2` varchar(50) DEFAULT NULL,
  `puerto` varchar(20) DEFAULT NULL,
  `iva` double(5,2) DEFAULT NULL,
  `gracia` int(11) DEFAULT '0',
  `salida` int(11) DEFAULT '0',
  `led` varchar(20) DEFAULT NULL,
  `barras` varchar(20) DEFAULT NULL,
  `barras2` varchar(20) DEFAULT NULL,
  `sale` varchar(4) DEFAULT NULL,
  `sale2` varchar(4) DEFAULT NULL,
  `puerto1` varchar(20) DEFAULT NULL,
  `puerto2` varchar(20) DEFAULT NULL,
  `puerto3` varchar(20) DEFAULT NULL,
  `puerto4` varchar(20) DEFAULT NULL,
  `puerto5` varchar(20) DEFAULT NULL,
  `puerto6` varchar(20) DEFAULT NULL,
  `puerto7` varchar(20) DEFAULT NULL,
  `salida1` varchar(20) DEFAULT NULL,
  `salida2` varchar(20) DEFAULT NULL,
  `salida3` varchar(20) DEFAULT NULL,
  `salida4` varchar(20) DEFAULT NULL,
  `salida5` varchar(20) DEFAULT NULL,
  `salida6` varchar(20) DEFAULT NULL,
  `salida7` varchar(20) DEFAULT NULL,
  `activa1` tinyint(1) DEFAULT '0',
  `activa2` tinyint(1) DEFAULT '0',
  `activa3` tinyint(1) DEFAULT '0',
  `activa4` tinyint(1) DEFAULT '0',
  `activa5` tinyint(1) DEFAULT '0',
  `activa6` tinyint(1) DEFAULT '0',
  `activa7` tinyint(1) DEFAULT '0',
  `activa8` tinyint(1) DEFAULT '0',
  `activa9` tinyint(1) DEFAULT '0',
  `activa10` tinyint(1) DEFAULT '0',
  `activa11` tinyint(1) DEFAULT '0',
  `activa12` tinyint(1) DEFAULT '0',
  `activa13` tinyint(1) DEFAULT '0',
  `activa14` tinyint(1) DEFAULT '0',
  `puerta1` varchar(4) DEFAULT NULL,
  `puerta2` varchar(4) DEFAULT NULL,
  `puerta3` varchar(4) DEFAULT NULL,
  `puerta4` varchar(4) DEFAULT NULL,
  `puerta5` varchar(4) DEFAULT NULL,
  `puerta6` varchar(4) DEFAULT NULL,
  `puerta7` varchar(4) DEFAULT NULL,
  `puerta8` varchar(4) DEFAULT NULL,
  `puerta9` varchar(4) DEFAULT NULL,
  `puerta10` varchar(4) DEFAULT NULL,
  `puerta11` varchar(4) DEFAULT NULL,
  `puerta12` varchar(4) DEFAULT NULL,
  `puerta13` varchar(4) DEFAULT NULL,
  `puerta14` varchar(4) DEFAULT NULL,
  PRIMARY KEY (`ruc`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `empresa` */

insert  into `empresa`(`ruc`,`nombre`,`razon`,`direccion`,`telefonos`,`documentofac`,`documentonota`,`documentoticket`,`parqueaderos`,`impticket`,`impfactura`,`impresora`,`impresora2`,`puerto`,`iva`,`gracia`,`salida`,`led`,`barras`,`barras2`,`sale`,`sale2`,`puerto1`,`puerto2`,`puerto3`,`puerto4`,`puerto5`,`puerto6`,`puerto7`,`salida1`,`salida2`,`salida3`,`salida4`,`salida5`,`salida6`,`salida7`,`activa1`,`activa2`,`activa3`,`activa4`,`activa5`,`activa6`,`activa7`,`activa8`,`activa9`,`activa10`,`activa11`,`activa12`,`activa13`,`activa14`,`puerta1`,`puerta2`,`puerta3`,`puerta4`,`puerta5`,`puerta6`,`puerta7`,`puerta8`,`puerta9`,`puerta10`,`puerta11`,`puerta12`,`puerta13`,`puerta14`) values ('11555555555115','STALIN JADAN','ANTONOV','Quitus Y 9 de Agosto','22266','18','0','9',60,'Microsoft XPS Document Writer','Microsoft XPS Document Writer','Microsoft XPS Document Writer','Microsoft XPS Document Writer','COM1',12.00,0,0,'null',NULL,NULL,'2','2',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,1,0,0,0,0,0,0,0,0,0,0,0,'1','2','2','4',NULL,NULL,NULL,'','','','','','','');

/*Table structure for table `global` */

DROP TABLE IF EXISTS `global`;

CREATE TABLE `global` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) DEFAULT NULL,
  `grupo` varchar(3) DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=latin1;

/*Data for the table `global` */

insert  into `global`(`codigo`,`nombre`,`grupo`) values (100,'Administrador','PER'),(102,'Operador','PER'),(103,'Otros','PER');

/*Table structure for table `tipos` */

DROP TABLE IF EXISTS `tipos`;

CREATE TABLE `tipos` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(200) DEFAULT NULL COMMENT 'Bueno, Muy Bueno, Excelente',
  `codificacion` varchar(10) DEFAULT NULL COMMENT 'Viene de teylor',
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `tipos` */

insert  into `tipos`(`codigo`,`descripcion`,`codificacion`) values (1,'MUY BUENO..','MB'),(2,'BUENO','B001');

/*Table structure for table `usuarios` */

DROP TABLE IF EXISTS `usuarios`;

CREATE TABLE `usuarios` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `cedula` varchar(16) DEFAULT NULL,
  `nombres` varchar(100) DEFAULT NULL,
  `direccion` varchar(100) DEFAULT NULL,
  `usuario` varchar(100) DEFAULT NULL,
  `clave` varchar(100) DEFAULT NULL,
  `perfil` int(11) DEFAULT NULL,
  `horaini` datetime DEFAULT NULL,
  `horafin` datetime DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FK_perfilempleado` (`perfil`),
  KEY `FKA897305F676C6AB` (`perfil`),
  CONSTRAINT `FK_perfilempleado` FOREIGN KEY (`perfil`) REFERENCES `global` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

/*Data for the table `usuarios` */

insert  into `usuarios`(`codigo`,`cedula`,`nombres`,`direccion`,`usuario`,`clave`,`perfil`,`horaini`,`horafin`) values (1,'1309700548','Geovanny Jadan','cotocollao','geova','F1FHsX1prCg=',100,'2011-06-13 06:50:13','2011-06-13 19:50:15'),(2,'1724663582','Pedro Navaja','Calderon','operador','IlL03Js/mkA=',NULL,'2011-06-09 06:00:00','2011-06-09 23:40:40'),(3,'1717171717','Sami Jadan','sa','sami','R9idEtiO3Bo=',102,'1970-01-01 18:38:00','1970-01-01 23:59:59'),(4,'1103475735','Henry Jadán','cotocollao','henryj','IlL03Js/mkA=',102,NULL,NULL),(5,'1818181818','Nombres Direccion','direccion','nombresd','tEER5LcVJgI=',102,NULL,NULL),(6,'795685898755','Nombres001','Direccion','user','wXFr+gz5bHQ=',103,NULL,NULL),(7,'guguo.','hi.hk','.hk.lk','123','R9idEtiO3Bo=',102,NULL,NULL),(8,'1919191919','OTRO USUARIO','USUARIO','otrou','yoTFCcD+QO4=',103,NULL,NULL),(9,'1616161616','ROBERT ORTEGA','.','roberto','R9idEtiO3Bo=',102,NULL,NULL),(10,'1818181818','Taylor Terán','direccion','usuario','e5PlCg2Y0bs=',100,'1970-01-01 06:00:00','1970-01-01 23:59:59');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
