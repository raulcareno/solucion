/*
SQLyog Community Edition- MySQL GUI v8.04 
MySQL - 6.0.4-alpha-community : Database - clinicas
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

CREATE DATABASE /*!32312 IF NOT EXISTS*/`clinicas` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `clinicas`;

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
  PRIMARY KEY (`codigoacc`),
  KEY `FK_perfil` (`perfil`),
  KEY `FKB9CEF71382A62D53` (`perfil`),
  CONSTRAINT `FKB9CEF71382A62D53` FOREIGN KEY (`perfil`) REFERENCES `global` (`codigo`),
  CONSTRAINT `FK_perfil` FOREIGN KEY (`perfil`) REFERENCES `global` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=latin1;

/*Table structure for table `cobros` */

DROP TABLE IF EXISTS `cobros`;

CREATE TABLE `cobros` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `pacientes` int(11) DEFAULT NULL,
  `fecha` datetime DEFAULT NULL,
  `tipo` varchar(50) DEFAULT NULL,
  `estado` varchar(50) DEFAULT NULL,
  `servicios` int(11) DEFAULT NULL,
  `dientes` int(11) DEFAULT NULL,
  `debe` double(8,2) DEFAULT NULL,
  `haber` double(8,2) DEFAULT NULL,
  `saldo` double(8,2) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FK_cobros` (`pacientes`),
  KEY `FK_cobros_servicios` (`servicios`),
  CONSTRAINT `FK_cobros` FOREIGN KEY (`pacientes`) REFERENCES `pacientes` (`codigo`),
  CONSTRAINT `FK_cobros_servicios` FOREIGN KEY (`servicios`) REFERENCES `servicios` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `dientes` */

DROP TABLE IF EXISTS `dientes`;

CREATE TABLE `dientes` (
  `codigo` int(11) NOT NULL,
  `referencia` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `dientesestado` */

DROP TABLE IF EXISTS `dientesestado`;

CREATE TABLE `dientesestado` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `dientes` int(11) DEFAULT NULL,
  `pacientes` int(11) DEFAULT NULL,
  `sup` int(11) DEFAULT NULL,
  `inf` int(11) DEFAULT NULL,
  `izq` int(11) DEFAULT NULL,
  `der` int(11) DEFAULT NULL,
  `cen` int(11) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FK_dientesestado` (`pacientes`),
  KEY `FK_dientesestado_dientes` (`dientes`),
  KEY `FK_dientesestado_estados` (`cen`),
  KEY `FK_sup` (`sup`),
  KEY `FK_inf` (`inf`),
  KEY `FK_izq` (`izq`),
  KEY `FK_der` (`der`),
  CONSTRAINT `FK_der` FOREIGN KEY (`der`) REFERENCES `estados` (`codigo`),
  CONSTRAINT `FK_dientesestado` FOREIGN KEY (`pacientes`) REFERENCES `pacientes` (`codigo`),
  CONSTRAINT `FK_dientesestado_dientes` FOREIGN KEY (`dientes`) REFERENCES `dientes` (`codigo`),
  CONSTRAINT `FK_dientesestado_estados` FOREIGN KEY (`cen`) REFERENCES `estados` (`codigo`),
  CONSTRAINT `FK_inf` FOREIGN KEY (`inf`) REFERENCES `estados` (`codigo`),
  CONSTRAINT `FK_izq` FOREIGN KEY (`izq`) REFERENCES `estados` (`codigo`),
  CONSTRAINT `FK_sup` FOREIGN KEY (`sup`) REFERENCES `estados` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `empleados` */

DROP TABLE IF EXISTS `empleados`;

CREATE TABLE `empleados` (
  `codigoemp` int(11) NOT NULL AUTO_INCREMENT,
  `identificacion` varchar(13) DEFAULT ' ',
  `nombres` varchar(60) DEFAULT NULL,
  `apellidos` varchar(60) DEFAULT NULL,
  `direccion` varchar(100) DEFAULT NULL,
  `telefono` varchar(30) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `foto` longblob,
  `usuario` varchar(20) DEFAULT NULL,
  `clave` varchar(100) DEFAULT NULL,
  `estado` tinyint(1) DEFAULT NULL,
  `perfil` int(11) DEFAULT NULL,
  PRIMARY KEY (`codigoemp`),
  UNIQUE KEY `usuarioUnico` (`usuario`),
  UNIQUE KEY `cedu` (`identificacion`),
  KEY `FK9CA2190882A62D53` (`perfil`),
  CONSTRAINT `FK_empleadosperfil` FOREIGN KEY (`perfil`) REFERENCES `global` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Table structure for table `estados` */

DROP TABLE IF EXISTS `estados`;

CREATE TABLE `estados` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) DEFAULT NULL,
  `imagen` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `global` */

DROP TABLE IF EXISTS `global`;

CREATE TABLE `global` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(100) DEFAULT NULL,
  `grupo` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

/*Table structure for table `historias` */

DROP TABLE IF EXISTS `historias`;

CREATE TABLE `historias` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `fecha` datetime DEFAULT NULL,
  `descripcion` longtext,
  `pacientes` int(11) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FK_historias_pacientes` (`pacientes`),
  CONSTRAINT `FK_historias_pacientes` FOREIGN KEY (`pacientes`) REFERENCES `pacientes` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `pacientes` */

DROP TABLE IF EXISTS `pacientes`;

CREATE TABLE `pacientes` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `historia` varchar(20) DEFAULT NULL,
  `nombre` varchar(100) DEFAULT NULL,
  `apellido` varchar(100) DEFAULT NULL,
  `nacido` date DEFAULT NULL,
  `direccion` varchar(300) DEFAULT NULL,
  `telefono` varchar(100) DEFAULT NULL,
  `sanguineo` varchar(20) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `descripcion` longtext,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Table structure for table `servicios` */

DROP TABLE IF EXISTS `servicios`;

CREATE TABLE `servicios` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) DEFAULT NULL,
  `valor1` double(8,2) DEFAULT NULL,
  `valor2` double(8,2) DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
