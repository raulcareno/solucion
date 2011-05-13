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
) ENGINE=InnoDB AUTO_INCREMENT=190 DEFAULT CHARSET=latin1;

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
  CONSTRAINT `FK_cabeceracompra_sucursal` FOREIGN KEY (`sucursal`) REFERENCES `sucursal` (`codigo`),
  CONSTRAINT `FK_cabeceracompra_proveedores` FOREIGN KEY (`proveedores`) REFERENCES `proveedores` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `canton` */

DROP TABLE IF EXISTS `canton`;

CREATE TABLE `canton` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

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
  CONSTRAINT `FK_contratos_equipos1` FOREIGN KEY (`equipos1`) REFERENCES `detallecompra` (`codigo`),
  CONSTRAINT `FK_contratos` FOREIGN KEY (`empleados`) REFERENCES `empleados` (`codigo`),
  CONSTRAINT `FK_contratos_clientes` FOREIGN KEY (`clientes`) REFERENCES `clientes` (`codigo`),
  CONSTRAINT `FK_contratos_equipos2` FOREIGN KEY (`equipos2`) REFERENCES `detallecompra` (`codigo`),
  CONSTRAINT `FK_contratos_equipos3` FOREIGN KEY (`equipos3`) REFERENCES `detallecompra` (`codigo`),
  CONSTRAINT `FK_contratos_instalador` FOREIGN KEY (`instalador`) REFERENCES `empleados` (`codigo`),
  CONSTRAINT `FK_contratos_nodos` FOREIGN KEY (`nodos`) REFERENCES `nodos` (`codigo`),
  CONSTRAINT `FK_contratos_plan` FOREIGN KEY (`plan`) REFERENCES `plan` (`codigo`),
  CONSTRAINT `FK_contratos_registrador` FOREIGN KEY (`registrador`) REFERENCES `empleados` (`codigo`),
  CONSTRAINT `FK_contratos_sucursal` FOREIGN KEY (`sucursal`) REFERENCES `sucursal` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Table structure for table `contratosplanes` */

DROP TABLE IF EXISTS `contratosplanes`;

CREATE TABLE `contratosplanes` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `plan` int(11) DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

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
  CONSTRAINT `FK_detalle_equipos` FOREIGN KEY (`equipos`) REFERENCES `equipos` (`codigo`),
  CONSTRAINT `FK_detalle` FOREIGN KEY (`factura`) REFERENCES `factura` (`codigo`),
  CONSTRAINT `FK_detalle_plan_oproducto` FOREIGN KEY (`plan`) REFERENCES `plan` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

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
  CONSTRAINT `FK_detallecompra_cabecompra` FOREIGN KEY (`compra`) REFERENCES `cabeceracompra` (`codigo`),
  CONSTRAINT `FK_controlequipos` FOREIGN KEY (`equipos`) REFERENCES `equipos` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

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
  CONSTRAINT `FK_empleadossector_sucursal` FOREIGN KEY (`sucursal`) REFERENCES `sucursal` (`codigo`),
  CONSTRAINT `FK_empleadossector_empleado` FOREIGN KEY (`empleados`) REFERENCES `empleados` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

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
  CONSTRAINT `FK_factura_sucursal` FOREIGN KEY (`sucursal`) REFERENCES `sucursal` (`codigo`),
  CONSTRAINT `FK_factura_clientes` FOREIGN KEY (`clientes`) REFERENCES `clientes` (`codigo`),
  CONSTRAINT `FK_factura_sector` FOREIGN KEY (`sector`) REFERENCES `sector` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `marcas` */

DROP TABLE IF EXISTS `marcas`;

CREATE TABLE `marcas` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

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

/*Table structure for table `perfil` */

DROP TABLE IF EXISTS `perfil`;

CREATE TABLE `perfil` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

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

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
