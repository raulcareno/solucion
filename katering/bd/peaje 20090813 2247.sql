-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.0.3-beta-nt


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
  `codigo` int(11) NOT NULL auto_increment,
  `perfil` int(11) default NULL,
  `pantalla` varchar(100) default NULL,
  `agregar` tinyint(1) default '1',
  `modificar` tinyint(1) default '1',
  `eliminar` tinyint(1) default '1',
  PRIMARY KEY  (`codigo`),
  KEY `FK_perfil` (`perfil`),
  CONSTRAINT `FK_perfil` FOREIGN KEY (`perfil`) REFERENCES `global` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `accesos`
--

/*!40000 ALTER TABLE `accesos` DISABLE KEYS */;
INSERT INTO `accesos` (`codigo`,`perfil`,`pantalla`,`agregar`,`modificar`,`eliminar`) VALUES 
 (1,1,'Usuarios',1,1,1),
 (2,1,'Globales',1,1,1),
 (3,1,'Accesos',1,1,1),
 (4,1,'Productos',1,1,1),
 (5,1,'Factura',1,1,1),
 (6,1,'Productos',1,1,1),
 (7,1,'Clientes',1,1,1),
 (8,1,'Empresa',1,1,1),
 (9,1,'Tarifas',1,1,1),
 (10,1,'Tickets',1,1,1),
 (11,1,'Reportes',1,1,1);
/*!40000 ALTER TABLE `accesos` ENABLE KEYS */;


--
-- Definition of table `auditoria`
--

DROP TABLE IF EXISTS `auditoria`;
CREATE TABLE `auditoria` (
  `auditoria` int(11) NOT NULL auto_increment,
  `tabla` varchar(100) default NULL,
  `maquina` varchar(100) default NULL,
  `accion` varchar(100) default NULL,
  `campo` varchar(100) default NULL,
  `usuario` int(11) default NULL,
  PRIMARY KEY  (`auditoria`),
  KEY `FK_auditoriausuario` (`usuario`),
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
  `codigo` int(11) NOT NULL auto_increment,
  `identificacion` varchar(16) default NULL,
  `nombres` varchar(200) default NULL,
  `direccion` varchar(200) default NULL,
  `telefono` varchar(50) default NULL,
  `tipo` varchar(20) default NULL,
  `estado` tinyint(1) default '1',
  `ultimoacceso` datetime default NULL,
  `acceso` datetime default NULL,
  PRIMARY KEY  (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `clientes`
--

/*!40000 ALTER TABLE `clientes` DISABLE KEYS */;
INSERT INTO `clientes` (`codigo`,`identificacion`,`nombres`,`direccion`,`telefono`,`tipo`,`estado`,`ultimoacceso`,`acceso`) VALUES 
 (1,'9999999999999','CONSUMIDOR FINAL','S/D','9999999999999',NULL,1,NULL,NULL),
 (2,'1717942120','ISMAEL FRANCISCO JADAN','LA FLORESTA','88939393',NULL,1,NULL,NULL),
 (3,'1212121212','SAMI ROMINA JADAN','SAN CARLOS','232323',NULL,1,NULL,NULL),
 (4,'1717942120','ISMAEL FRANCISCO JADAN','LA FLORESTA','88939393',NULL,1,NULL,NULL),
 (5,'1309700548','GEOVANNY JADAN','SD','2132',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `clientes` ENABLE KEYS */;


--
-- Definition of table `detalle`
--

DROP TABLE IF EXISTS `detalle`;
CREATE TABLE `detalle` (
  `codigo` int(11) NOT NULL auto_increment,
  `factura` int(11) default NULL,
  `producto` int(11) default NULL,
  `detalle` varchar(100) default NULL,
  `cantidad` int(11) default NULL,
  `subtotal` decimal(10,0) default NULL,
  `iva` decimal(10,0) default NULL,
  `total` decimal(10,0) default NULL,
  PRIMARY KEY  (`codigo`),
  KEY `FK_factura` (`factura`),
  KEY `FK_detalle` (`producto`),
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
  `nombre` varchar(200) default NULL,
  `razon` varchar(200) default NULL,
  `direccion` varchar(200) default NULL,
  `telefonos` varchar(50) default NULL,
  `documentofac` varchar(20) default NULL,
  `documentonota` varchar(20) default NULL,
  `documentoticket` varchar(10) default NULL,
  `parqueaderos` int(11) default NULL,
  `impticket` varchar(50) default NULL,
  `impfactura` varchar(50) default NULL,
  PRIMARY KEY  (`ruc`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `empresa`
--

/*!40000 ALTER TABLE `empresa` DISABLE KEYS */;
INSERT INTO `empresa` (`ruc`,`nombre`,`razon`,`direccion`,`telefonos`,`documentofac`,`documentonota`,`documentoticket`,`parqueaderos`,`impticket`,`impfactura`) VALUES 
 ('1309700548001','JCINFORM','JCINFORM','SAN CARLOS','023400925','28','001','41',40,'Microsoft Office Document Image Writer','Microsoft Office Document Image Writer');
/*!40000 ALTER TABLE `empresa` ENABLE KEYS */;


--
-- Definition of table `factura`
--

DROP TABLE IF EXISTS `factura`;
CREATE TABLE `factura` (
  `codigo` int(11) NOT NULL auto_increment,
  `numero` varchar(20) default NULL,
  `ticket` varchar(20) default NULL,
  `placa` varchar(20) default NULL,
  `fecha` date default NULL,
  `cliente` int(11) default NULL,
  `subtotal` decimal(10,2) default NULL,
  `iva` decimal(10,2) default NULL,
  `descuento` decimal(10,2) default NULL,
  `total` decimal(10,2) default NULL,
  `fechaini` datetime default NULL,
  `fechafin` datetime default NULL,
  `tiempo` datetime default NULL,
  PRIMARY KEY  (`codigo`),
  KEY `FK_cliente` (`cliente`),
  CONSTRAINT `FK_cliente` FOREIGN KEY (`cliente`) REFERENCES `clientes` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `factura`
--

/*!40000 ALTER TABLE `factura` DISABLE KEYS */;
INSERT INTO `factura` (`codigo`,`numero`,`ticket`,`placa`,`fecha`,`cliente`,`subtotal`,`iva`,`descuento`,`total`,`fechaini`,`fechafin`,`tiempo`) VALUES 
 (1,'11','0','ARH-232','2009-08-09',1,NULL,NULL,NULL,'8.00','2009-08-09 12:50:53','2009-08-09 22:29:42','2009-08-09 09:38:42'),
 (2,'21','1','WEEW-223','2009-08-09',1,NULL,NULL,NULL,'58.80','2009-08-07 19:00:00','2009-08-10 20:09:25','2009-08-10 01:09:25'),
 (3,NULL,'2','PAC-2SS','2009-08-09',NULL,NULL,NULL,NULL,NULL,'2009-08-09 16:11:44',NULL,NULL),
 (4,'24','3','PAI-232','2009-08-09',1,NULL,NULL,NULL,'80.40','2009-08-09 18:14:57','2009-08-13 22:33:57','2009-08-13 04:19:57'),
 (5,NULL,'4','PAL-323','2009-08-09',NULL,NULL,NULL,NULL,NULL,'2009-08-09 19:12:56',NULL,NULL),
 (6,'25','5','EPP-232','2009-08-09',1,NULL,NULL,NULL,'82.00','2009-08-09 16:13:13','2009-08-13 22:35:03','2009-08-13 06:21:03'),
 (7,NULL,'6','PAC-2323','2009-08-09',NULL,NULL,NULL,NULL,NULL,'2009-08-09 16:15:50',NULL,NULL),
 (8,'8','7','PAC-212','2009-08-09',1,NULL,NULL,NULL,NULL,'2009-08-09 16:17:11',NULL,NULL),
 (9,'3','8','PAC-2SS','2009-08-09',1,NULL,NULL,NULL,NULL,'2009-08-09 16:53:20',NULL,NULL),
 (10,'5','9','ABC-2323','2009-08-09',3,NULL,NULL,NULL,NULL,'2009-08-09 19:28:48',NULL,NULL),
 (11,'13','10','PAC-232','2009-08-09',1,NULL,NULL,NULL,'0.40','2009-08-09 20:41:36','2009-08-10 17:16:35','2009-08-10 21:25:35'),
 (12,NULL,'11','PER-566','2009-08-10',NULL,NULL,NULL,NULL,NULL,'2009-08-10 16:46:00',NULL,NULL),
 (13,NULL,'11','POI-788','2009-08-10',NULL,NULL,NULL,NULL,NULL,'2009-08-10 16:47:37',NULL,NULL),
 (14,'22','12','POW-2323','2009-08-10',1,NULL,NULL,NULL,'57.60','2009-08-10 16:51:59','2009-08-13 10:53:21','2009-08-13 00:00:21'),
 (15,NULL,'13','POW-2323','2009-08-10',NULL,NULL,NULL,NULL,NULL,'2009-08-10 16:52:58',NULL,NULL),
 (16,'14','14','WOW-2323','2009-08-10',1,NULL,NULL,NULL,'0.40','2009-08-10 16:54:53','2009-08-10 17:17:23','2009-08-10 00:22:23'),
 (17,'15','15','ERT-2323','2009-08-10',1,NULL,NULL,NULL,'0.40','2009-08-10 16:55:41','2009-08-10 17:20:01','2009-08-10 00:24:01'),
 (18,'16','16','AOW-2323','2009-08-10',1,NULL,NULL,NULL,'0.40','2009-08-10 16:56:35','2009-08-10 17:21:32','2009-08-10 00:24:32'),
 (19,'17','17','ARJ-232','2009-08-10',1,NULL,NULL,NULL,'0.40','2009-08-10 16:57:11','2009-08-10 17:22:17','2009-08-10 00:25:17'),
 (20,'18','18','RTU-456','2009-08-10',1,NULL,NULL,NULL,'0.80','2009-08-10 17:00:52','2009-08-10 17:38:02','2009-08-10 00:37:02'),
 (21,'12','19','TAI-2323','2009-08-10',1,NULL,NULL,NULL,'0.40','2009-08-10 17:10:57','2009-08-10 17:12:32','2009-08-10 00:01:32'),
 (22,'19','20','AWE-3434','2009-08-10',1,NULL,NULL,NULL,'0.00','2009-08-10 17:49:07','2009-08-10 17:49:32','2009-08-10 00:00:32'),
 (23,NULL,'21','PAC-2323','2009-08-10',NULL,NULL,NULL,NULL,NULL,'2009-08-10 19:51:45',NULL,NULL),
 (24,NULL,'22','PAC-ASL','2009-08-11',NULL,NULL,NULL,NULL,NULL,'2009-08-11 17:09:38',NULL,NULL),
 (25,NULL,'23','PAC-232','2009-08-13',NULL,NULL,NULL,NULL,NULL,'2009-08-13 10:53:05',NULL,NULL),
 (26,NULL,'24','hghg-8','2009-08-13',NULL,NULL,NULL,NULL,NULL,'2009-08-13 16:38:26',NULL,NULL),
 (27,NULL,'25','FFF-89','2009-08-13',NULL,NULL,NULL,NULL,NULL,'2009-08-13 16:38:33',NULL,NULL),
 (28,NULL,'26','HJL-897','2009-08-13',NULL,NULL,NULL,NULL,NULL,'2009-08-13 16:38:36',NULL,NULL),
 (29,NULL,'27','fh-89','2009-08-13',NULL,NULL,NULL,NULL,NULL,'2009-08-13 16:38:38',NULL,NULL),
 (30,NULL,'28','fh-89','2009-08-13',NULL,NULL,NULL,NULL,NULL,'2009-08-13 16:38:41',NULL,NULL),
 (31,NULL,'29','fh-89','2009-08-13',NULL,NULL,NULL,NULL,NULL,'2009-08-13 16:38:48',NULL,NULL),
 (32,NULL,'30','AHR-859','2009-08-13',NULL,NULL,NULL,NULL,NULL,'2009-08-13 16:38:54',NULL,NULL),
 (33,NULL,'31','ADSS-877','2009-08-13',NULL,NULL,NULL,NULL,NULL,'2009-08-13 16:39:36',NULL,NULL),
 (34,'26','32','ASE-87','2009-08-13',1,NULL,NULL,NULL,'4.80','2009-08-13 16:39:42','2009-08-13 22:37:01','2009-08-13 05:57:01'),
 (35,NULL,'33','ASE-87','2009-08-13',NULL,NULL,NULL,NULL,NULL,'2009-08-13 16:39:46',NULL,NULL),
 (36,NULL,'34','PAC-2323','2009-08-13',NULL,NULL,NULL,NULL,NULL,'2009-08-13 16:57:06',NULL,NULL),
 (37,'23','35','PAE-2323','2009-08-13',1,NULL,NULL,NULL,'1.20','2009-08-13 17:04:49','2009-08-13 18:07:15','2009-08-13 01:02:15'),
 (38,NULL,'36','PAC-22','2009-08-13',NULL,NULL,NULL,NULL,NULL,'2009-08-13 20:27:13',NULL,NULL),
 (39,NULL,'37','PAC-122','2009-08-13',NULL,NULL,NULL,NULL,NULL,'2009-08-13 22:30:06',NULL,NULL),
 (40,NULL,'38','PY-87','2009-08-13',NULL,NULL,NULL,NULL,NULL,'2009-08-13 22:30:47',NULL,NULL),
 (41,NULL,'39','PLA-3232','2009-08-13',NULL,NULL,NULL,NULL,NULL,'2009-08-13 22:36:36',NULL,NULL),
 (42,'27','40','PAC-232','2009-08-13',1,NULL,NULL,NULL,'0.00','2009-08-13 22:39:15','2009-08-13 22:39:26','2009-08-13 00:00:26');
/*!40000 ALTER TABLE `factura` ENABLE KEYS */;


--
-- Definition of table `global`
--

DROP TABLE IF EXISTS `global`;
CREATE TABLE `global` (
  `codigo` int(11) NOT NULL auto_increment,
  `nombre` varchar(100) default NULL,
  `grupo` varchar(3) default NULL,
  PRIMARY KEY  (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `global`
--

/*!40000 ALTER TABLE `global` DISABLE KEYS */;
INSERT INTO `global` (`codigo`,`nombre`,`grupo`) VALUES 
 (1,'ADMINISTRADOR','PER'),
 (2,'USUARIO','PER');
/*!40000 ALTER TABLE `global` ENABLE KEYS */;


--
-- Definition of table `productos`
--

DROP TABLE IF EXISTS `productos`;
CREATE TABLE `productos` (
  `codigo` int(11) NOT NULL auto_increment,
  `descripcion` varchar(100) default NULL,
  `valor` decimal(10,0) default NULL,
  `bien` tinyint(1) default NULL,
  PRIMARY KEY  (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

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
  `codigo` int(11) NOT NULL auto_increment,
  `tarjeta` varchar(20) default NULL,
  `ticket` varchar(16) default NULL,
  `fechaini` datetime default NULL,
  `fechafin` datetime default NULL,
  `diferencia` datetime default NULL,
  PRIMARY KEY  (`codigo`)
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
  `codigo` int(11) NOT NULL auto_increment,
  `nombre` varchar(30) default NULL,
  `desde` decimal(10,0) default NULL,
  `hasta` decimal(10,0) default NULL,
  `valor` decimal(10,2) default NULL,
  PRIMARY KEY  (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tarifas`
--

/*!40000 ALTER TABLE `tarifas` DISABLE KEYS */;
INSERT INTO `tarifas` (`codigo`,`nombre`,`desde`,`hasta`,`valor`) VALUES 
 (12,NULL,'0','30','0.40'),
 (20,NULL,'30','60','0.80');
/*!40000 ALTER TABLE `tarifas` ENABLE KEYS */;


--
-- Definition of table `tarjetas`
--

DROP TABLE IF EXISTS `tarjetas`;
CREATE TABLE `tarjetas` (
  `tarjeta` varchar(20) NOT NULL default '',
  `cliente` int(11) default NULL,
  `Lunes` tinyint(1) default '0',
  `Martes` tinyint(1) default '0',
  `Miercoles` tinyint(1) default '0',
  `Jueves` tinyint(1) default '0',
  `Viernes` tinyint(1) default '0',
  `Sabado` tinyint(1) default '0',
  `Domingo` tinyint(1) default '0',
  `horainicio` time default NULL,
  `horafin` time default NULL,
  `desde` datetime default NULL,
  `hasta` datetime default NULL,
  `dias` int(11) default NULL,
  `habilitada` tinyint(1) default '1',
  `ingresos` int(11) default '0',
  `placa` varchar(10) default NULL,
  `descripcion` varchar(100) default NULL,
  PRIMARY KEY  (`tarjeta`),
  UNIQUE KEY `NewIndex1` (`tarjeta`),
  KEY `FK_Clientess` (`cliente`),
  CONSTRAINT `FK_Clientess` FOREIGN KEY (`cliente`) REFERENCES `clientes` (`codigo`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tarjetas`
--

/*!40000 ALTER TABLE `tarjetas` DISABLE KEYS */;
INSERT INTO `tarjetas` (`tarjeta`,`cliente`,`Lunes`,`Martes`,`Miercoles`,`Jueves`,`Viernes`,`Sabado`,`Domingo`,`horainicio`,`horafin`,`desde`,`hasta`,`dias`,`habilitada`,`ingresos`,`placa`,`descripcion`) VALUES 
 ('11',1,1,1,1,1,0,0,0,'07:23:00','18:29:00','2009-08-04 00:00:00','2009-08-09 00:00:00',0,1,0,NULL,NULL),
 ('12',1,1,1,1,0,0,0,0,'04:00:00','07:00:00','2007-02-02 00:00:00','2009-08-01 00:00:00',0,1,0,NULL,NULL),
 ('1212',2,1,1,1,0,0,1,0,'19:23:38','19:23:38','2009-08-08 19:23:38','2009-08-08 19:23:38',0,1,0,NULL,NULL),
 ('12345',1,1,1,1,0,0,0,0,'09:39:00','19:39:15','2009-08-08 19:39:15','2009-08-22 19:39:15',0,1,0,NULL,NULL),
 ('124',4,1,1,1,0,0,0,0,'10:54:27','10:54:27','2009-08-13 10:54:27','2009-08-13 10:54:27',0,1,0,NULL,NULL),
 ('13',2,1,1,0,0,0,0,0,'09:00:00','17:00:00','2008-02-02 00:00:00','2009-08-10 00:00:00',0,1,0,NULL,NULL),
 ('14',3,0,0,0,0,0,0,0,'10:00:00','16:00:00','2009-02-02 00:00:00','2009-08-11 00:00:00',1,1,0,NULL,NULL),
 ('2222',5,1,1,1,0,0,0,0,'20:19:42','20:19:42','2009-08-08 20:20:11','2009-08-22 20:20:12',0,0,0,NULL,NULL);
/*!40000 ALTER TABLE `tarjetas` ENABLE KEYS */;


--
-- Definition of table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
CREATE TABLE `usuarios` (
  `codigo` int(11) NOT NULL auto_increment,
  `cedula` varchar(16) default NULL,
  `nombres` varchar(100) default NULL,
  `direccion` varchar(100) default NULL,
  `usuario` varchar(100) default NULL,
  `clave` varchar(100) default NULL,
  `perfil` int(11) default NULL,
  PRIMARY KEY  (`codigo`),
  KEY `FK_perfilempleado` (`perfil`),
  CONSTRAINT `FK_perfilempleado` FOREIGN KEY (`perfil`) REFERENCES `global` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `usuarios`
--

/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` (`codigo`,`cedula`,`nombres`,`direccion`,`usuario`,`clave`,`perfil`) VALUES 
 (1,'1309700548','Geovanny Jadán','san carlos','geova','202cb962ac59075b964b07152d234b70',1);
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
