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
  `puerta1` varchar(1) DEFAULT NULL,
  `puerta2` varchar(2) DEFAULT NULL,
  `puerta3` varchar(3) DEFAULT NULL,
  `puerta4` varchar(4) DEFAULT NULL,
  `puerta5` varchar(5) DEFAULT NULL,
  `puerta6` varchar(6) DEFAULT NULL,
  `puerta7` varchar(7) DEFAULT NULL,
  PRIMARY KEY (`ruc`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

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
  CONSTRAINT `FKBEEB4778A3E9B9EF` FOREIGN KEY (`cliente`) REFERENCES `clientes` (`codigo`),
  CONSTRAINT `FKBEEB4778C49CBC4F` FOREIGN KEY (`tarjeta`) REFERENCES `tarjetas` (`tarjeta`),
  CONSTRAINT `FK_cliente` FOREIGN KEY (`cliente`) REFERENCES `clientes` (`codigo`),
  CONSTRAINT `FK_factura_tarjeta` FOREIGN KEY (`tarjeta`) REFERENCES `tarjetas` (`tarjeta`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=latin1;

/*Table structure for table `global` */

DROP TABLE IF EXISTS `global`;

CREATE TABLE `global` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) DEFAULT NULL,
  `grupo` varchar(3) DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Table structure for table `productos` */

DROP TABLE IF EXISTS `productos`;

CREATE TABLE `productos` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(100) DEFAULT NULL,
  `valor` decimal(10,0) DEFAULT NULL,
  `bien` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

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

/*Table structure for table `tarifas` */

DROP TABLE IF EXISTS `tarifas`;

CREATE TABLE `tarifas` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(30) DEFAULT NULL,
  `desde` decimal(10,0) DEFAULT NULL,
  `hasta` decimal(10,0) DEFAULT NULL,
  `valor` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

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

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
