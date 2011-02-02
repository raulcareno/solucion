/*
SQLyog Community v8.55 
MySQL - 6.0.4-alpha-community : Database - peaje
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `accesos` */

DROP TABLE IF EXISTS `accesos`;

CREATE TABLE `accesos` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `perfil` int(11) DEFAULT NULL,
  `pantalla` varchar(100) DEFAULT NULL,
  `agregar` tinyint(1) DEFAULT '1',
  `modificar` tinyint(1) DEFAULT '1',
  `eliminar` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`codigo`),
  KEY `FK_perfil` (`perfil`),
  KEY `FKB9CEF713F676C6AB` (`perfil`),
  CONSTRAINT `FKB9CEF713F676C6AB` FOREIGN KEY (`perfil`) REFERENCES `global` (`codigo`),
  CONSTRAINT `FK_perfil` FOREIGN KEY (`perfil`) REFERENCES `global` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

/*Data for the table `accesos` */

insert  into `accesos`(`codigo`,`perfil`,`pantalla`,`agregar`,`modificar`,`eliminar`) values (1,1,'Usuarios',1,1,1),(2,1,'Globales',1,1,1),(3,1,'Accesos',1,1,1),(4,1,'Productos',1,1,1),(5,1,'Factura',1,1,1),(6,1,'Productos',1,1,1),(7,1,'Clientes',1,1,1),(8,1,'Empresa',1,1,1),(9,1,'Tarifas',1,1,1),(10,1,'Tickets',1,1,1),(11,1,'Reportes',1,1,1);

/*Table structure for table `auditoria` */

DROP TABLE IF EXISTS `auditoria`;

CREATE TABLE `auditoria` (
  `auditoria` int(11) NOT NULL AUTO_INCREMENT,
  `tabla` varchar(100) DEFAULT NULL,
  `maquina` varchar(100) DEFAULT NULL,
  `accion` varchar(100) DEFAULT NULL,
  `campo` varchar(100) DEFAULT NULL,
  `usuario` int(11) DEFAULT NULL,
  PRIMARY KEY (`auditoria`),
  KEY `FK_auditoriausuario` (`usuario`),
  KEY `FKB8A64963D17606F` (`usuario`),
  CONSTRAINT `FKB8A64963D17606F` FOREIGN KEY (`usuario`) REFERENCES `usuarios` (`codigo`),
  CONSTRAINT `FK_auditoriausuario` FOREIGN KEY (`usuario`) REFERENCES `usuarios` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `auditoria` */

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
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `clientes` */

insert  into `clientes`(`codigo`,`identificacion`,`nombres`,`direccion`,`telefono`,`tipo`,`estado`,`ultimoacceso`,`acceso`) values (1,'9999999999999','CONSUMIDOR FINAL','S/D','9999999999999',NULL,1,NULL,NULL),(2,'1717942120','ISMAEL FRANCISCO JADAN','LA FLORESTA','88939393',NULL,1,NULL,NULL),(3,'1212121212','SAMI ROMINA JADAN','SAN CARLOS','5103843',NULL,1,NULL,NULL),(4,'1717942120','DALTON JOSUE ENRIQUEZ','LA FLORESTA','88939393',NULL,1,NULL,NULL),(5,'1309700548','GEOVANNY JADAN','SD','2132',NULL,1,NULL,NULL);

/*Table structure for table `detalle` */

DROP TABLE IF EXISTS `detalle`;

CREATE TABLE `detalle` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `factura` int(11) DEFAULT NULL,
  `producto` int(11) DEFAULT NULL,
  `detalle` varchar(100) DEFAULT NULL,
  `cantidad` int(11) DEFAULT NULL,
  `subtotal` decimal(10,0) DEFAULT NULL,
  `iva` decimal(10,0) DEFAULT NULL,
  `total` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FK_factura` (`factura`),
  KEY `FK_detalle` (`producto`),
  KEY `FK5CD8FD779A2079B7` (`producto`),
  KEY `FK5CD8FD7777775D34` (`factura`),
  CONSTRAINT `FK5CD8FD7777775D34` FOREIGN KEY (`factura`) REFERENCES `factura` (`codigo`),
  CONSTRAINT `FK5CD8FD779A2079B7` FOREIGN KEY (`producto`) REFERENCES `productos` (`codigo`),
  CONSTRAINT `FK_detalle` FOREIGN KEY (`producto`) REFERENCES `productos` (`codigo`),
  CONSTRAINT `FK_factura` FOREIGN KEY (`factura`) REFERENCES `factura` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `detalle` */

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
  `puerto1` varchar(20) DEFAULT NULL,
  `puerto2` varchar(20) DEFAULT NULL,
  `puerto3` varchar(20) DEFAULT NULL,
  `puerto4` varchar(20) DEFAULT NULL,
  `puerto5` varchar(20) DEFAULT NULL,
  `puerto6` varchar(20) DEFAULT NULL,
  `puerto7` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`ruc`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `empresa` */

insert  into `empresa`(`ruc`,`nombre`,`razon`,`direccion`,`telefonos`,`documentofac`,`documentonota`,`documentoticket`,`parqueaderos`,`impticket`,`impfactura`,`puerto1`,`puerto2`,`puerto3`,`puerto4`,`puerto5`,`puerto6`,`puerto7`) values ('1309700548001','JCINFORM','JCINFORM','SAN CARLOS','023400925','29','001','46',40,'Lexmark 2400 Series','Lexmark 2400 Series',NULL,NULL,NULL,NULL,NULL,NULL,NULL);

/*Table structure for table `factura` */

DROP TABLE IF EXISTS `factura`;

CREATE TABLE `factura` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `numero` varchar(20) DEFAULT NULL,
  `ticket` varchar(20) DEFAULT NULL,
  `placa` varchar(20) DEFAULT NULL,
  `tarjeta` varchar(20) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `cliente` int(11) DEFAULT NULL,
  `subtotal` decimal(10,2) DEFAULT NULL,
  `iva` decimal(10,2) DEFAULT NULL,
  `descuento` decimal(10,2) DEFAULT NULL,
  `total` decimal(10,2) DEFAULT NULL,
  `fechaini` datetime DEFAULT NULL,
  `fechafin` datetime DEFAULT NULL,
  `tiempo` datetime DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FK_cliente` (`cliente`),
  KEY `FK_factura_tarjeta` (`tarjeta`),
  KEY `FKBEEB4778A3E9B9EF` (`cliente`),
  KEY `FKBEEB4778C49CBC4F` (`tarjeta`),
  CONSTRAINT `FKBEEB4778C49CBC4F` FOREIGN KEY (`tarjeta`) REFERENCES `tarjetas` (`tarjeta`),
  CONSTRAINT `FKBEEB4778A3E9B9EF` FOREIGN KEY (`cliente`) REFERENCES `clientes` (`codigo`),
  CONSTRAINT `FK_cliente` FOREIGN KEY (`cliente`) REFERENCES `clientes` (`codigo`),
  CONSTRAINT `FK_factura_tarjeta` FOREIGN KEY (`tarjeta`) REFERENCES `tarjetas` (`tarjeta`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=latin1;

/*Data for the table `factura` */

insert  into `factura`(`codigo`,`numero`,`ticket`,`placa`,`tarjeta`,`fecha`,`cliente`,`subtotal`,`iva`,`descuento`,`total`,`fechaini`,`fechafin`,`tiempo`) values (52,NULL,'42','PIC-232',NULL,'2009-08-21',NULL,NULL,NULL,NULL,NULL,'2009-08-21 16:38:06',NULL,NULL),(53,NULL,'43','POY-877',NULL,'2009-08-21',NULL,NULL,NULL,NULL,NULL,'2009-08-21 16:52:33',NULL,NULL),(54,NULL,'44','PXL-352',NULL,'2011-02-01',NULL,NULL,NULL,NULL,NULL,'2011-02-01 09:01:02',NULL,NULL),(55,NULL,NULL,'','3A00148A53','2011-02-01',NULL,NULL,NULL,NULL,NULL,'2011-02-01 11:36:53','2011-02-01 11:37:21','2011-02-01 00:00:21'),(56,'28','45','PXL-356',NULL,'2011-02-01',1,NULL,NULL,NULL,'0.00','2011-02-01 16:35:00','2011-02-01 16:35:40','2011-02-01 00:00:40');

/*Table structure for table `global` */

DROP TABLE IF EXISTS `global`;

CREATE TABLE `global` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) DEFAULT NULL,
  `grupo` varchar(3) DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `global` */

insert  into `global`(`codigo`,`nombre`,`grupo`) values (1,'ADMINISTRADOR','PER'),(2,'USUARIO','PER');

/*Table structure for table `productos` */

DROP TABLE IF EXISTS `productos`;

CREATE TABLE `productos` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(100) DEFAULT NULL,
  `valor` decimal(10,0) DEFAULT NULL,
  `bien` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `productos` */

insert  into `productos`(`codigo`,`descripcion`,`valor`,`bien`) values (1,'Peaje','20',0),(2,'Tarjeta Mensual','20',0),(3,'Tarjeta Semanal','5',0),(4,'Tarjeta Quincenal','15',0),(5,'Especial','10',0);

/*Table structure for table `registro` */

DROP TABLE IF EXISTS `registro`;

CREATE TABLE `registro` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `tarjeta` varchar(20) DEFAULT NULL,
  `ticket` varchar(16) DEFAULT NULL,
  `fechaini` datetime DEFAULT NULL,
  `fechafin` datetime DEFAULT NULL,
  `diferencia` datetime DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `registro` */

/*Table structure for table `tarifas` */

DROP TABLE IF EXISTS `tarifas`;

CREATE TABLE `tarifas` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(30) DEFAULT NULL,
  `desde` decimal(10,0) DEFAULT NULL,
  `hasta` decimal(10,0) DEFAULT NULL,
  `valor` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

/*Data for the table `tarifas` */

/*Table structure for table `tarjetas` */

DROP TABLE IF EXISTS `tarjetas`;

CREATE TABLE `tarjetas` (
  `tarjeta` varchar(20) NOT NULL DEFAULT '',
  `cliente` int(11) DEFAULT NULL,
  `Lunes` tinyint(1) DEFAULT '0',
  `Martes` tinyint(1) DEFAULT '0',
  `Miercoles` tinyint(1) DEFAULT '0',
  `Jueves` tinyint(1) DEFAULT '0',
  `Viernes` tinyint(1) DEFAULT '0',
  `Sabado` tinyint(1) DEFAULT '0',
  `Domingo` tinyint(1) DEFAULT '0',
  `horainicio` time DEFAULT NULL,
  `horafin` time DEFAULT NULL,
  `desde` datetime DEFAULT NULL,
  `hasta` datetime DEFAULT NULL,
  `dias` int(11) DEFAULT NULL,
  `habilitada` tinyint(1) DEFAULT '1',
  `ingresos` int(11) DEFAULT '0',
  `placa` varchar(10) DEFAULT NULL,
  `descripcion` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`tarjeta`),
  UNIQUE KEY `NewIndex1` (`tarjeta`),
  KEY `FK_Clientess` (`cliente`),
  KEY `FKE5D2A406A3E9B9EF` (`cliente`),
  CONSTRAINT `FKE5D2A406A3E9B9EF` FOREIGN KEY (`cliente`) REFERENCES `clientes` (`codigo`),
  CONSTRAINT `FK_Clientess` FOREIGN KEY (`cliente`) REFERENCES `clientes` (`codigo`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `tarjetas` */

insert  into `tarjetas`(`tarjeta`,`cliente`,`Lunes`,`Martes`,`Miercoles`,`Jueves`,`Viernes`,`Sabado`,`Domingo`,`horainicio`,`horafin`,`desde`,`hasta`,`dias`,`habilitada`,`ingresos`,`placa`,`descripcion`) values ('12',1,1,1,1,0,0,0,0,'04:00:00','07:00:00','2007-02-02 00:00:00','2009-08-01 00:00:00',0,1,0,NULL,NULL),('1212',2,1,1,1,0,0,1,0,'19:23:38','19:23:38','2009-08-08 19:23:38','2009-08-08 19:23:38',0,1,0,NULL,NULL),('12345',1,1,1,1,0,0,0,0,'09:39:00','19:39:15','2009-08-08 19:39:15','2009-08-22 19:39:15',0,1,0,NULL,NULL),('124',4,1,1,1,0,0,0,0,'10:54:27','10:54:27','2009-08-13 10:54:27','2009-08-13 10:54:27',0,1,0,NULL,NULL),('2222',5,1,1,1,0,0,0,0,'20:19:42','20:19:42','2009-08-08 20:20:11','2009-08-22 20:20:12',0,0,0,NULL,NULL),('3A00148A522A',4,1,1,1,1,0,0,0,'07:23:00','18:29:00','2009-08-04 00:00:00','2009-08-09 00:00:00',0,1,0,NULL,NULL),('3A00148A53',5,1,1,1,1,1,1,1,'08:34:00','18:34:00','2011-02-01 00:01:01','2011-02-05 23:59:59',0,1,0,'PXL-359',NULL),('3A00148A532B',3,0,0,0,0,0,0,0,'10:00:00','16:00:00','2009-02-02 00:00:00','2009-08-11 00:00:00',1,1,0,NULL,NULL);

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
  PRIMARY KEY (`codigo`),
  KEY `FK_perfilempleado` (`perfil`),
  KEY `FKA897305F676C6AB` (`perfil`),
  CONSTRAINT `FKA897305F676C6AB` FOREIGN KEY (`perfil`) REFERENCES `global` (`codigo`),
  CONSTRAINT `FK_perfilempleado` FOREIGN KEY (`perfil`) REFERENCES `global` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `usuarios` */

insert  into `usuarios`(`codigo`,`cedula`,`nombres`,`direccion`,`usuario`,`clave`,`perfil`) values (1,'1309700548','Geovanny Jadán','san carlos','geova','63a9f0ea7bb98050796b649e85481845',1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
