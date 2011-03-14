-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	6.0.4-alpha-community


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema peaje
--

CREATE DATABASE IF NOT EXISTS peaje;
USE peaje;

--
-- Definition of table `accesos`
--

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
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `accesos`
--

/*!40000 ALTER TABLE `accesos` DISABLE KEYS */;
INSERT INTO `accesos` (`codigo`,`perfil`,`pantalla`,`agregar`,`modificar`,`eliminar`,`ingresar`) VALUES 
 (1,100,'Clientes',1,1,1,1),
 (2,100,'Globales',1,1,1,1),
 (3,100,'Accesos',1,1,1,1),
 (4,100,'Tarifas',1,1,1,1),
 (5,100,'Tickets',1,1,1,1),
 (6,100,'Factura',1,1,1,1),
 (7,100,'Reportes',1,1,1,1),
 (8,100,'Operadores',1,1,1,1),
 (9,100,'Empresa',1,1,1,1);
/*!40000 ALTER TABLE `accesos` ENABLE KEYS */;


--
-- Definition of table `auditoria`
--

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
  CONSTRAINT `FK_auditoriausuario` FOREIGN KEY (`usuario`) REFERENCES `usuarios` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `auditoria`
--

/*!40000 ALTER TABLE `auditoria` DISABLE KEYS */;
/*!40000 ALTER TABLE `auditoria` ENABLE KEYS */;


--
-- Definition of table `clientes`
--

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

--
-- Dumping data for table `clientes`
--

/*!40000 ALTER TABLE `clientes` DISABLE KEYS */;
INSERT INTO `clientes` (`codigo`,`identificacion`,`nombres`,`direccion`,`telefono`,`tipo`,`estado`,`ultimoacceso`,`acceso`) VALUES 
 (1,'9999999999999','CONSUMIDOR FINAL','S/D','9999999999999',NULL,1,NULL,NULL),
 (3,'1212121212','SAMI ROMINA JADAN','SAN CARLOS','5103843',NULL,1,NULL,NULL),
 (4,'1717942120','DALTON JOSUE ENRIQUEZ','LA FLORESTA','88939393',NULL,1,NULL,NULL),
 (5,'1309700548','GEOVANNY JADAN','SD','2132',NULL,1,NULL,NULL);
/*!40000 ALTER TABLE `clientes` ENABLE KEYS */;


--
-- Definition of table `detalle`
--

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
  CONSTRAINT `FK_detalle` FOREIGN KEY (`producto`) REFERENCES `productos` (`codigo`),
  CONSTRAINT `FK_factura` FOREIGN KEY (`factura`) REFERENCES `factura` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `detalle`
--

/*!40000 ALTER TABLE `detalle` DISABLE KEYS */;
/*!40000 ALTER TABLE `detalle` ENABLE KEYS */;


--
-- Definition of table `empresa`
--

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
  `puerto1` varchar(20) DEFAULT NULL,
  `puerto2` varchar(20) DEFAULT NULL,
  `puerto3` varchar(20) DEFAULT NULL,
  `puerto4` varchar(20) DEFAULT NULL,
  `puerto5` varchar(20) DEFAULT NULL,
  `puerto6` varchar(20) DEFAULT NULL,
  `puerto7` varchar(20) DEFAULT NULL,
  `activa1` tinyint(1) DEFAULT '0',
  `activa2` tinyint(1) DEFAULT '0',
  `activa3` tinyint(1) DEFAULT '0',
  `activa4` tinyint(1) DEFAULT '0',
  `activa5` tinyint(1) DEFAULT '0',
  `activa6` tinyint(1) DEFAULT '0',
  `activa7` tinyint(1) DEFAULT '0',
  `puerta1` varchar(1) DEFAULT NULL,
  `puerta2` varchar(2) DEFAULT NULL,
  `puerta3` varchar(3) DEFAULT NULL,
  `puerta4` varchar(4) DEFAULT NULL,
  `puerta5` varchar(5) DEFAULT NULL,
  `puerta6` varchar(6) DEFAULT NULL,
  `puerta7` varchar(7) DEFAULT NULL,
  PRIMARY KEY (`ruc`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `empresa`
--

/*!40000 ALTER TABLE `empresa` DISABLE KEYS */;
INSERT INTO `empresa` (`ruc`,`nombre`,`razon`,`direccion`,`telefonos`,`documentofac`,`documentonota`,`documentoticket`,`parqueaderos`,`impticket`,`impfactura`,`impresora`,`impresora2`,`puerto`,`puerto1`,`puerto2`,`puerto3`,`puerto4`,`puerto5`,`puerto6`,`puerto7`,`activa1`,`activa2`,`activa3`,`activa4`,`activa5`,`activa6`,`activa7`,`puerta1`,`puerta2`,`puerta3`,`puerta4`,`puerta5`,`puerta6`,`puerta7`) VALUES 
 ('1309700548001','JCINFORM','JCINFORM','SAN CARLOS','023400925','29','001','50',40,'Lexmark 2400 Series','Lexmark 2400 Series','Lexmark 2400 Series',NULL,'COM1','COM3','COM1',NULL,'COM3',NULL,NULL,NULL,1,0,0,0,0,0,0,'1','1',NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `empresa` ENABLE KEYS */;


--
-- Definition of table `factura`
--

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
  CONSTRAINT `FK_cliente` FOREIGN KEY (`cliente`) REFERENCES `clientes` (`codigo`),
  CONSTRAINT `FK_factura_tarjeta` FOREIGN KEY (`tarjeta`) REFERENCES `tarjetas` (`tarjeta`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `factura`
--

/*!40000 ALTER TABLE `factura` DISABLE KEYS */;
INSERT INTO `factura` (`codigo`,`numero`,`ticket`,`placa`,`tarjeta`,`fecha`,`cliente`,`subtotal`,`iva`,`descuento`,`total`,`fechaini`,`fechafin`,`tiempo`) VALUES 
 (52,NULL,'42','PIC-232',NULL,'2009-08-21',NULL,NULL,NULL,NULL,NULL,'2009-08-21 16:38:06',NULL,NULL),
 (53,NULL,'43','POY-877',NULL,'2009-08-21',NULL,NULL,NULL,NULL,NULL,'2009-08-21 16:52:33',NULL,NULL),
 (54,NULL,'44','PXL-352',NULL,'2011-02-01',NULL,NULL,NULL,NULL,NULL,'2011-02-01 09:01:02',NULL,NULL),
 (55,NULL,NULL,'','3A00148A53','2011-02-01',NULL,NULL,NULL,NULL,NULL,'2011-02-01 11:36:53','2011-02-01 11:37:21','2011-02-01 00:00:21'),
 (56,'28','45','PXL-356',NULL,'2011-02-01',1,NULL,NULL,NULL,'0.00','2011-02-01 16:35:00','2011-02-01 16:35:40','2011-02-01 00:00:40'),
 (57,NULL,'46','JJJJj',NULL,'2011-03-04',NULL,NULL,NULL,NULL,NULL,'2011-03-04 15:31:46',NULL,NULL),
 (58,NULL,'47','KJJKj',NULL,'2011-03-04',NULL,NULL,NULL,NULL,NULL,'2011-03-04 15:33:36',NULL,NULL),
 (59,NULL,'48','PXL356',NULL,'2011-03-04',NULL,NULL,NULL,NULL,NULL,'2011-03-04 16:06:57',NULL,NULL),
 (60,NULL,'49','PXKL-54451',NULL,'2011-03-04',NULL,NULL,NULL,NULL,NULL,'2011-03-04 21:34:34',NULL,NULL);
/*!40000 ALTER TABLE `factura` ENABLE KEYS */;


--
-- Definition of table `global`
--

DROP TABLE IF EXISTS `global`;
CREATE TABLE `global` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) DEFAULT NULL,
  `grupo` varchar(3) DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `global`
--

/*!40000 ALTER TABLE `global` DISABLE KEYS */;
INSERT INTO `global` (`codigo`,`nombre`,`grupo`) VALUES 
 (100,'Administrador','PER'),
 (102,'Operador','PER'),
 (103,'Otros','PER');
/*!40000 ALTER TABLE `global` ENABLE KEYS */;


--
-- Definition of table `productos`
--

DROP TABLE IF EXISTS `productos`;
CREATE TABLE `productos` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(100) DEFAULT NULL,
  `valor` decimal(10,0) DEFAULT NULL,
  `bien` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `productos`
--

/*!40000 ALTER TABLE `productos` DISABLE KEYS */;
INSERT INTO `productos` (`codigo`,`descripcion`,`valor`,`bien`) VALUES 
 (1,'Peaje','20',0),
 (2,'Tarjeta Mensual','20',0),
 (3,'Tarjeta Semanal','5',0),
 (4,'Tarjeta Quincenal','15',0),
 (5,'Especial','10',0);
/*!40000 ALTER TABLE `productos` ENABLE KEYS */;


--
-- Definition of table `registro`
--

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

--
-- Dumping data for table `registro`
--

/*!40000 ALTER TABLE `registro` DISABLE KEYS */;
/*!40000 ALTER TABLE `registro` ENABLE KEYS */;


--
-- Definition of table `tarifas`
--

DROP TABLE IF EXISTS `tarifas`;
CREATE TABLE `tarifas` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(30) DEFAULT NULL,
  `desde` decimal(10,0) DEFAULT NULL,
  `hasta` decimal(10,0) DEFAULT NULL,
  `valor` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tarifas`
--

/*!40000 ALTER TABLE `tarifas` DISABLE KEYS */;
INSERT INTO `tarifas` (`codigo`,`nombre`,`desde`,`hasta`,`valor`) VALUES 
 (6,NULL,'120','240','3.00'),
 (7,NULL,'241','300','4.00'),
 (8,NULL,'301','1000000','5.00');
/*!40000 ALTER TABLE `tarifas` ENABLE KEYS */;


--
-- Definition of table `tarjetas`
--

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
  CONSTRAINT `FK_Clientess` FOREIGN KEY (`cliente`) REFERENCES `clientes` (`codigo`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tarjetas`
--

/*!40000 ALTER TABLE `tarjetas` DISABLE KEYS */;
INSERT INTO `tarjetas` (`tarjeta`,`cliente`,`Lunes`,`Martes`,`Miercoles`,`Jueves`,`Viernes`,`Sabado`,`Domingo`,`horainicio`,`horafin`,`desde`,`hasta`,`dias`,`habilitada`,`ingresos`,`placa`,`descripcion`) VALUES 
 ('12',1,1,1,1,0,0,0,0,'04:00:00','07:00:00','2007-02-02 00:00:00','2009-08-01 00:00:00',0,1,0,NULL,NULL),
 ('12345',1,1,1,1,0,0,0,0,'09:39:00','19:39:15','2009-08-08 19:39:15','2009-08-22 19:39:15',0,1,0,NULL,NULL),
 ('124',4,1,1,1,0,0,0,0,'10:54:27','10:54:27','2009-08-13 10:54:27','2009-08-13 10:54:27',0,1,0,NULL,NULL),
 ('148A543A001',3,1,1,1,1,1,1,1,'07:00:00','09:01:45','2011-02-18 00:01:01','2011-02-26 23:59:59',0,1,0,'PXL-359',NULL),
 ('2222',5,1,1,1,0,0,0,0,'20:19:42','20:19:42','2009-08-08 20:20:11','2009-08-22 20:20:12',0,0,0,NULL,NULL),
 ('3A00148A522A',4,1,1,1,1,0,0,0,'07:23:00','18:29:00','2009-08-04 00:00:00','2009-08-09 00:00:00',0,1,0,NULL,NULL),
 ('3A00148A53',5,1,1,1,1,1,1,1,'08:34:00','18:34:00','2011-02-01 00:01:01','2011-02-05 23:59:59',0,1,0,'PXL-359',NULL),
 ('3A00148A532B',3,0,0,0,0,0,0,0,'10:00:00','16:00:00','2009-02-02 00:00:00','2009-08-11 00:00:00',1,1,0,NULL,NULL),
 ('3A00148A533',3,1,1,1,1,1,1,1,'13:26:00','18:26:45','2011-02-18 00:01:01','2011-02-27 23:59:59',0,1,0,'PXL0052',NULL);
/*!40000 ALTER TABLE `tarjetas` ENABLE KEYS */;


--
-- Definition of table `usuarios`
--

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
  CONSTRAINT `FK_perfilempleado` FOREIGN KEY (`perfil`) REFERENCES `global` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `usuarios`
--

/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` (`codigo`,`cedula`,`nombres`,`direccion`,`usuario`,`clave`,`perfil`) VALUES 
 (1,'1309700548','Geovanny Jadán','san carlos','geova','F1FHsX1prCg=',100);
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
