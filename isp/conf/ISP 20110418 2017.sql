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
-- Create schema isp
--

CREATE DATABASE IF NOT EXISTS isp;
USE isp;

--
-- Definition of table `accesos`
--

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
) ENGINE=InnoDB AUTO_INCREMENT=93 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `accesos`
--

/*!40000 ALTER TABLE `accesos` DISABLE KEYS */;
INSERT INTO `accesos` (`codigoacc`,`perfil`,`modulo`,`guardar`,`eliminar`,`actualizar`,`ingresar`,`grupo`) VALUES 
 (1,NULL,'Accesos',1,1,1,1,NULL),
 (2,NULL,'Auditoria',1,1,1,1,NULL),
 (3,NULL,'Cantones',1,1,1,1,NULL),
 (4,NULL,'Clientes',1,1,1,1,NULL),
 (5,NULL,'Comisiones',1,1,1,1,NULL),
 (6,NULL,'Contratos',1,1,1,1,NULL),
 (7,NULL,'Equipos',1,1,1,1,NULL),
 (8,NULL,'Empresa',1,1,1,1,NULL),
 (9,NULL,'Marcas',1,1,1,1,NULL),
 (10,NULL,'Nodos',1,1,1,1,NULL),
 (11,NULL,'Perfiles',1,1,1,1,NULL),
 (12,NULL,'Proveedores',1,1,1,1,NULL),
 (13,NULL,'Sectores',1,1,1,1,NULL),
 (14,NULL,'Empleados',1,1,1,1,NULL),
 (79,1,'Accesos',1,1,1,1,''),
 (80,1,'Auditoria',1,1,1,1,''),
 (81,1,'Cantones',1,1,1,1,''),
 (82,1,'Clientes',1,1,1,1,''),
 (83,1,'Comisiones',1,1,1,1,''),
 (84,1,'Contratos',1,1,1,1,''),
 (85,1,'Equipos',1,1,1,1,''),
 (86,1,'Empresa',1,1,1,1,''),
 (87,1,'Marcas',1,1,1,1,''),
 (88,1,'Nodos',1,1,1,1,''),
 (89,1,'Perfiles',1,1,1,1,''),
 (90,1,'Proveedores',1,1,1,1,''),
 (91,1,'Sectores',1,1,1,1,''),
 (92,1,'Empleados',1,1,1,1,'');
/*!40000 ALTER TABLE `accesos` ENABLE KEYS */;


--
-- Definition of table `auditoria`
--

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
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `auditoria`
--

/*!40000 ALTER TABLE `auditoria` DISABLE KEYS */;
INSERT INTO `auditoria` (`codigo`,`empleado`,`fecha`,`tipo`,`ip`,`pc`,`tabla`) VALUES 
 (1,1,'2011-04-17 23:05:13','-','127.0.0.1','Ingreso Sistema','-'),
 (2,1,'2011-04-17 23:05:48','-','127.0.0.1','Ingreso Sistema','-'),
 (3,1,'2011-04-18 08:14:33','-','127.0.0.1','Ingreso Sistema','-'),
 (4,1,'2011-04-18 08:15:56','-','127.0.0.1','Ingreso Sistema','-'),
 (5,1,'2011-04-18 09:01:24','-','127.0.0.1','Ingreso Sistema','-'),
 (6,1,'2011-04-18 09:03:45','-','127.0.0.1','Ingreso Sistema','-'),
 (7,1,'2011-04-18 09:06:58','-','127.0.0.1','Ingreso Sistema','-'),
 (8,1,'2011-04-18 09:10:14','-','127.0.0.1','Ingreso Sistema','-'),
 (9,1,'2011-04-18 09:55:46','-','127.0.0.1','Ingreso Sistema','-'),
 (10,1,'2011-04-18 10:09:22','-','127.0.0.1','Ingreso Sistema','-'),
 (11,1,'2011-04-18 10:14:05','Guardar','127.0.0.1','persistencia.Perfil[codigo=1]','Accesos'),
 (12,1,'2011-04-18 10:19:26','-','127.0.0.1','Ingreso Sistema','-'),
 (13,1,'2011-04-18 10:19:48','Guardar','127.0.0.1','persistencia.Perfil[codigo=1]','Accesos'),
 (14,1,'2011-04-18 10:20:45','Guardar','127.0.0.1','persistencia.Perfil[codigo=1]','Accesos'),
 (15,1,'2011-04-18 10:23:42','-','127.0.0.1','Ingreso Sistema','-'),
 (16,1,'2011-04-18 10:23:59','Guardar','127.0.0.1','persistencia.Perfil[codigo=4]','Accesos'),
 (17,1,'2011-04-18 10:31:32','-','127.0.0.1','Ingreso Sistema','-'),
 (18,1,'2011-04-18 10:34:20','-','127.0.0.1','Ingreso Sistema','-'),
 (19,1,'2011-04-18 10:36:47','Guardar','127.0.0.1','persistencia.Perfil[codigo=4]','Accesos'),
 (20,1,'2011-04-18 10:42:02','Guardar','127.0.0.1','persistencia.Perfil[codigo=3]','Accesos'),
 (21,1,'2011-04-18 10:42:08','Guardar','127.0.0.1','persistencia.Perfil[codigo=2]','Accesos'),
 (22,1,'2011-04-18 10:49:34','-','127.0.0.1','Ingreso Sistema','-'),
 (23,1,'2011-04-18 10:51:20','-','127.0.0.1','Ingreso Sistema','-'),
 (24,1,'2011-04-18 11:04:09','-','127.0.0.1','Ingreso Sistema','-'),
 (25,1,'2011-04-18 11:20:15','-','127.0.0.1','Ingreso Sistema','-'),
 (26,1,'2011-04-18 11:26:16','Guardar','127.0.0.1','JCINFORM SOLUCIONES INFORMATICAS','Empresa'),
 (27,1,'2011-04-18 11:28:50','Guardar','127.0.0.1','JCINFORM SOLUCIONES INFORMATICAS','Empresa'),
 (28,1,'2011-04-18 11:37:56','-','127.0.0.1','Ingreso Sistema','-'),
 (29,1,'2011-04-18 11:39:20','Guardar','127.0.0.1','JCINFORM SOLUCIONES INFORMATICAS','Empresa'),
 (30,1,'2011-04-18 11:40:39','Guardar','127.0.0.1','JCINFORM SOLUCIONES INFORMATICAS','Empresa'),
 (31,1,'2011-04-18 11:45:35','Guardar','127.0.0.1','JCINFORM SOLUCIONES INFORMATICAS','Empresa'),
 (32,1,'2011-04-18 11:51:55','Guardar','127.0.0.1','JCINFORM SOLUCIONES INFORMATICAS','Empresa'),
 (33,1,'2011-04-18 12:05:51','Guardar','127.0.0.1','JCINFORM SOLUCIONES INFORMATICAS','Empresa'),
 (34,1,'2011-04-18 12:14:14','Actualizar','127.0.0.1','2 - Pedro Vicente Maldonado','Cantones'),
 (35,1,'2011-04-18 12:14:37','Guardar','127.0.0.1','3 - Santo Domingo','Cantones'),
 (36,1,'2011-04-18 12:15:22','Actualizar','127.0.0.1','3 - Santo Domingo de los Tsachilas','Cantones'),
 (37,1,'2011-04-18 12:15:29','Eliminar','127.0.0.1','3 - Santo Domingo de los Tsachilas','Cantones'),
 (38,1,'2011-04-18 12:18:07','Actualizar','127.0.0.1','3 - FACTURADOR','Perfiles'),
 (39,1,'2011-04-18 12:18:50','Eliminar','127.0.0.1','4 - VENDEDOR','Perfiles'),
 (40,1,'2011-04-18 12:18:54','Eliminar','127.0.0.1','3 - FACTURADOR','Perfiles'),
 (41,1,'2011-04-18 12:19:03','Guardar','127.0.0.1','5 - VENDEDOR','Perfiles'),
 (42,1,'2011-04-18 12:39:38','Guardar','127.0.0.1','3 - Centro','Sectores'),
 (43,1,'2011-04-18 12:44:04','Actualizar','127.0.0.1','3 - Centro','Sectores'),
 (44,1,'2011-04-18 12:44:35','Actualizar','127.0.0.1','3 - Centro','Sectores'),
 (45,1,'2011-04-18 13:31:17','-','127.0.0.1','Ingreso Sistema','-'),
 (46,1,'2011-04-18 13:36:24','Guardar','127.0.0.1',' JCINFORM SOLUCIONES INFORMATICAS','Proveedores'),
 (47,1,'2011-04-18 13:38:53','Guardar','127.0.0.1',' JCINFORM SOLUCIONES INFORMATICAS','Proveedores'),
 (48,1,'2011-04-18 13:39:58','Guardar','127.0.0.1',' JCINFORM SOLUCIONES INFORMATICAS','Proveedores'),
 (49,1,'2011-04-18 13:41:23','Guardar','127.0.0.1',' CONTADORES ASOCIADOS','Proveedores'),
 (50,1,'2011-04-18 13:42:14','Guardar','127.0.0.1',' asdf','Proveedores'),
 (51,1,'2011-04-18 13:42:31','Guardar','127.0.0.1',' asdf','Proveedores'),
 (52,1,'2011-04-18 13:42:42','Eliminar','127.0.0.1','asdf','Proveedores'),
 (53,1,'2011-04-18 13:54:17','Guardar','127.0.0.1','persistencia.Perfil[codigo=1]','Accesos'),
 (54,1,'2011-04-18 13:54:27','-','127.0.0.1','Ingreso Sistema','-'),
 (55,1,'2011-04-18 13:56:13','Guardar','127.0.0.1','JADAN ORTEGA GEOVANNY','Empleados'),
 (56,1,'2011-04-18 13:56:47','Guardar','127.0.0.1','Cahueñas Angelica','Empleados'),
 (57,1,'2011-04-18 13:58:16','-','127.0.0.1','Ingreso Sistema','-'),
 (58,1,'2011-04-18 14:34:24','-','127.0.0.1','Ingreso Sistema','-'),
 (59,1,'2011-04-18 16:35:47','-','127.0.0.1','Ingreso Sistema','-');
/*!40000 ALTER TABLE `auditoria` ENABLE KEYS */;


--
-- Definition of table `canton`
--

DROP TABLE IF EXISTS `canton`;
CREATE TABLE `canton` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `canton`
--

/*!40000 ALTER TABLE `canton` DISABLE KEYS */;
INSERT INTO `canton` (`codigo`,`nombre`) VALUES 
 (1,'Quito'),
 (2,'Pedro Vicente Maldonado');
/*!40000 ALTER TABLE `canton` ENABLE KEYS */;


--
-- Definition of table `clientes`
--

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
  PRIMARY KEY (`codigo`),
  KEY `FK_clientes_sector` (`sector`),
  CONSTRAINT `FK_clientes_sector` FOREIGN KEY (`sector`) REFERENCES `clientes` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `clientes`
--

/*!40000 ALTER TABLE `clientes` DISABLE KEYS */;
/*!40000 ALTER TABLE `clientes` ENABLE KEYS */;


--
-- Definition of table `comisiones`
--

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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `comisiones`
--

/*!40000 ALTER TABLE `comisiones` DISABLE KEYS */;
/*!40000 ALTER TABLE `comisiones` ENABLE KEYS */;


--
-- Definition of table `contratos`
--

DROP TABLE IF EXISTS `contratos`;
CREATE TABLE `contratos` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `contrato` varchar(15) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `empleados` int(11) DEFAULT NULL,
  `clientes` int(11) DEFAULT NULL,
  `plan` int(11) DEFAULT NULL,
  `fechapago` date DEFAULT NULL,
  `ip` varchar(20) DEFAULT NULL,
  `usuario` varchar(30) DEFAULT NULL,
  `clave` varchar(30) DEFAULT NULL,
  `nodos` int(11) DEFAULT NULL,
  `autorizado` tinyint(1) DEFAULT NULL,
  `fechainstalacion` datetime DEFAULT NULL,
  `fechafinal` datetime DEFAULT NULL,
  `equipos` int(11) DEFAULT NULL,
  `equipos2` int(11) DEFAULT NULL,
  `equipos3` int(11) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FK_contratos_nodos` (`nodos`),
  KEY `FK_contratos_clientes` (`clientes`),
  KEY `FK_contratos_plan` (`plan`),
  KEY `FK_contratos_equipos2` (`equipos2`),
  KEY `FK_contratos_equipos1` (`equipos`),
  KEY `FK_contratos_equipos3` (`equipos3`),
  KEY `FK_contratos` (`empleados`),
  CONSTRAINT `FK_contratos` FOREIGN KEY (`empleados`) REFERENCES `empleados` (`codigo`),
  CONSTRAINT `FK_contratos_clientes` FOREIGN KEY (`clientes`) REFERENCES `clientes` (`codigo`),
  CONSTRAINT `FK_contratos_equipos1` FOREIGN KEY (`equipos`) REFERENCES `controlequipos` (`codigo`),
  CONSTRAINT `FK_contratos_equipos2` FOREIGN KEY (`equipos2`) REFERENCES `controlequipos` (`codigo`),
  CONSTRAINT `FK_contratos_equipos3` FOREIGN KEY (`equipos3`) REFERENCES `controlequipos` (`codigo`),
  CONSTRAINT `FK_contratos_nodos` FOREIGN KEY (`nodos`) REFERENCES `nodos` (`codigo`),
  CONSTRAINT `FK_contratos_plan` FOREIGN KEY (`plan`) REFERENCES `plan` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `contratos`
--

/*!40000 ALTER TABLE `contratos` DISABLE KEYS */;
/*!40000 ALTER TABLE `contratos` ENABLE KEYS */;


--
-- Definition of table `controlequipos`
--

DROP TABLE IF EXISTS `controlequipos`;
CREATE TABLE `controlequipos` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `equipos` int(11) DEFAULT NULL,
  `proveedores` int(11) DEFAULT NULL,
  `fechacompra` date DEFAULT NULL,
  `garantia` int(11) DEFAULT NULL,
  `precio` double(9,2) DEFAULT NULL,
  `asignado` tinyint(1) DEFAULT NULL,
  `mac` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FK_controlequipos` (`equipos`),
  KEY `FK_controlequipos_proveedores` (`proveedores`),
  CONSTRAINT `FK_controlequipos` FOREIGN KEY (`equipos`) REFERENCES `equipos` (`codigo`),
  CONSTRAINT `FK_controlequipos_proveedores` FOREIGN KEY (`proveedores`) REFERENCES `proveedores` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `controlequipos`
--

/*!40000 ALTER TABLE `controlequipos` DISABLE KEYS */;
/*!40000 ALTER TABLE `controlequipos` ENABLE KEYS */;


--
-- Definition of table `empleados`
--

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
  PRIMARY KEY (`codigo`),
  KEY `FK_empleados_perfil` (`perfil`),
  CONSTRAINT `FK_empleados_perfil` FOREIGN KEY (`perfil`) REFERENCES `perfil` (`codigo`) ON DELETE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `empleados`
--

/*!40000 ALTER TABLE `empleados` DISABLE KEYS */;
INSERT INTO `empleados` (`codigo`,`identificacion`,`apellidos`,`nombres`,`direccion`,`telefono`,`email`,`perfil`,`usuario`,`clave`,`estado`) VALUES 
 (1,'1309700548','JADAN ORTEGA','GEOVANNY','COTOCOLLAO','5103843','jcinform@gmail.com',1,'geova','F1FHsX1prCg=',1),
 (2,'1717996134','Cahueñas','Angelica','direccion','5555-555','email',1,'1717996134','kxB2J1b/vufCYYPMgVZ3cw==',1);
/*!40000 ALTER TABLE `empleados` ENABLE KEYS */;


--
-- Definition of table `empleadossector`
--

DROP TABLE IF EXISTS `empleadossector`;
CREATE TABLE `empleadossector` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `sector` int(11) DEFAULT NULL,
  `empleados` int(11) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FK_empleadossector_sector` (`sector`),
  KEY `FK_empleadossector_empleado` (`empleados`),
  CONSTRAINT `FK_empleadossector_empleado` FOREIGN KEY (`empleados`) REFERENCES `empleados` (`codigo`),
  CONSTRAINT `FK_empleadossector_sector` FOREIGN KEY (`sector`) REFERENCES `sector` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `empleadossector`
--

/*!40000 ALTER TABLE `empleadossector` DISABLE KEYS */;
INSERT INTO `empleadossector` (`codigo`,`sector`,`empleados`) VALUES 
 (1,1,1),
 (2,2,1);
/*!40000 ALTER TABLE `empleadossector` ENABLE KEYS */;


--
-- Definition of table `empresa`
--

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

--
-- Dumping data for table `empresa`
--

/*!40000 ALTER TABLE `empresa` DISABLE KEYS */;
INSERT INTO `empresa` (`ruc`,`razonsocial`,`representante`,`direccion`,`telefono`,`telefono2`,`movil`,`documento`,`email`,`web`,`reportes`,`fotos`,`usuariomail`,`clavemail`,`autorizacion`,`smtp`,`puerto`,`logo`,`star`) VALUES 
 ('1309700014488','JCINFORM SOLUCIONES INFORMATICAS','GEOVANNY JADAN','DIRECCION','TELEFONO','51515','561551','000001','png','www.jcinform.com','c:\\reportesisp\\','c:\\fotosisp\\','email','claveemail',1,'smt.gmail','587',NULL,1);
/*!40000 ALTER TABLE `empresa` ENABLE KEYS */;


--
-- Definition of table `equipos`
--

DROP TABLE IF EXISTS `equipos`;
CREATE TABLE `equipos` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) DEFAULT NULL,
  `modelo` varchar(50) DEFAULT NULL,
  `caracteristica` varchar(50) DEFAULT NULL,
  `marcas` int(11) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FK_equipos_marcas` (`marcas`),
  CONSTRAINT `FK_equipos_marcas` FOREIGN KEY (`marcas`) REFERENCES `marcas` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `equipos`
--

/*!40000 ALTER TABLE `equipos` DISABLE KEYS */;
/*!40000 ALTER TABLE `equipos` ENABLE KEYS */;


--
-- Definition of table `marcas`
--

DROP TABLE IF EXISTS `marcas`;
CREATE TABLE `marcas` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `marcas`
--

/*!40000 ALTER TABLE `marcas` DISABLE KEYS */;
/*!40000 ALTER TABLE `marcas` ENABLE KEYS */;


--
-- Definition of table `nodos`
--

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
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `nodos`
--

/*!40000 ALTER TABLE `nodos` DISABLE KEYS */;
/*!40000 ALTER TABLE `nodos` ENABLE KEYS */;


--
-- Definition of table `perfil`
--

DROP TABLE IF EXISTS `perfil`;
CREATE TABLE `perfil` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `perfil`
--

/*!40000 ALTER TABLE `perfil` DISABLE KEYS */;
INSERT INTO `perfil` (`codigo`,`nombre`) VALUES 
 (1,'SUPER ADMINISTRADOR'),
 (2,'ADMINISTRADOR'),
 (5,'VENDEDOR');
/*!40000 ALTER TABLE `perfil` ENABLE KEYS */;


--
-- Definition of table `plan`
--

DROP TABLE IF EXISTS `plan`;
CREATE TABLE `plan` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) DEFAULT NULL,
  `valor` double(9,2) DEFAULT NULL,
  `tipo` varchar(50) DEFAULT NULL,
  `dias` int(11) DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `plan`
--

/*!40000 ALTER TABLE `plan` DISABLE KEYS */;
/*!40000 ALTER TABLE `plan` ENABLE KEYS */;


--
-- Definition of table `proveedores`
--

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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `proveedores`
--

/*!40000 ALTER TABLE `proveedores` DISABLE KEYS */;
INSERT INTO `proveedores` (`codigo`,`identificacion`,`razonsocial`,`representante`,`direccion`,`telefono`,`email`) VALUES 
 (1,'1309700548','JCINFORM SOLUCIONES INFORMATICAS','El representante','DIRECCION','5154-544','EMIAL');
/*!40000 ALTER TABLE `proveedores` ENABLE KEYS */;


--
-- Definition of table `sector`
--

DROP TABLE IF EXISTS `sector`;
CREATE TABLE `sector` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) DEFAULT NULL,
  `canton` int(11) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FK_sector_canton` (`canton`),
  CONSTRAINT `FK_sector_canton` FOREIGN KEY (`canton`) REFERENCES `canton` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sector`
--

/*!40000 ALTER TABLE `sector` DISABLE KEYS */;
INSERT INTO `sector` (`codigo`,`nombre`,`canton`) VALUES 
 (1,'COTOCOLLAO',1),
 (2,'CONDADO',1),
 (3,'Centro',2);
/*!40000 ALTER TABLE `sector` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
