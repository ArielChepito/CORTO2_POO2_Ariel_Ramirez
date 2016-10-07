-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         5.6.16 - MySQL Community Server (GPL)
-- SO del servidor:              Win64
-- HeidiSQL Versión:             9.1.0.4905
-- --------------------------------------------------------


-- SET PASSWORD FOR 'root'@'localhost' = PASSWORD('123');


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;





-- create database PARCIAL01_POO2_ArielRamirez
-- use PARCIAL01_POO2_ArielRamirez
CREATE TABLE IF NOT EXISTS `alumnos` (
  `codi_alum` int(11) NOT NULL AUTO_INCREMENT,
  `nomb_alum` varchar(100) NOT NULL,
  `apel_alum` varchar(100) NOT NULL,
  `fech_naci_alum` date NOT NULL,
  `mail_alum` varchar(100) NOT NULL,
  `tele_alum` varchar(20) NOT NULL,
  `dire_alum` varchar(200) NOT NULL,
  `gene_alum` char(1) NOT NULL,
  PRIMARY KEY (`codi_alum`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- La exportación de datos fue deseleccionada.
-- 16:38:07	CREATE DATABASE PARCIAL01_POO2_ArielRamirez	1 row(s) affected	0.000 sec


-- Volcando estructura para tabla PARCIAL01_POO2_SUNOMBRE.cursos
CREATE TABLE IF NOT EXISTS `cursos` (
  `codi_curs` int(11) NOT NULL AUTO_INCREMENT,
  `nomb_curs` varchar(100) NOT NULL,
  `dura_curs` int(11) NOT NULL,
  `cost_curs` double NOT NULL,
  PRIMARY KEY (`codi_curs`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- La exportación de datos fue deseleccionada.


-- Volcando estructura para tabla PARCIAL01_POO2_SUNOMBRE.grupos
CREATE TABLE IF NOT EXISTS `grupos` (
  `codi_grup` int(11) NOT NULL AUTO_INCREMENT,
  `nomb_grup` varchar(100) NOT NULL,
  `fech_inic_grup` date NOT NULL,
  `fech_fina_grup` date NOT NULL,
  `hora_inic_grup` varchar(5) NOT NULL,
  `hora_fina_grup` varchar(5) NOT NULL,
  `codi_curs` int(11) NOT NULL,
  `codi_prof` int(11) NOT NULL,
  PRIMARY KEY (`codi_grup`),
  KEY `profesores_grupos_fk` (`codi_prof`),
  KEY `cursos_grupos_fk` (`codi_curs`),
  CONSTRAINT `cursos_grupos_fk` FOREIGN KEY (`codi_curs`) REFERENCES `cursos` (`codi_curs`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `profesores_grupos_fk` FOREIGN KEY (`codi_prof`) REFERENCES `profesores` (`codi_prof`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;






-- La exportación de datos fue deseleccionada.


-- Volcando estructura para tabla PARCIAL01_POO2_SUNOMBRE.grupos_alumnos
CREATE TABLE IF NOT EXISTS `grupos_alumnos` (
  `codi_grup_alum` int(11) NOT NULL AUTO_INCREMENT,
  `codi_grup` int(11) NOT NULL,
  `codi_alum` int(11) NOT NULL,
  `esta_grup_alum` char(1) NOT NULL,
  PRIMARY KEY (`codi_grup_alum`),
  KEY `grupos_grupos_alumnos_fk` (`codi_grup`),
  KEY `alumnos_grupos_alumnos_fk` (`codi_alum`),
  CONSTRAINT `alumnos_grupos_alumnos_fk` FOREIGN KEY (`codi_alum`) REFERENCES `alumnos` (`codi_alum`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `grupos_grupos_alumnos_fk` FOREIGN KEY (`codi_grup`) REFERENCES `grupos` (`codi_grup`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- La exportación de datos fue deseleccionada.


-- Volcando estructura para tabla PARCIAL01_POO2_SUNOMBRE.profesores
CREATE TABLE IF NOT EXISTS `profesores` (
  `codi_prof` int(11) NOT NULL AUTO_INCREMENT,
  `nomb_prof` varchar(100) NOT NULL,
  `apel_prof` varchar(100) NOT NULL,
  `mail_prof` varchar(100) NOT NULL,
  `tele_prof` varchar(20) NOT NULL,
  `dire_pro` varchar(200) NOT NULL,
  PRIMARY KEY (`codi_prof`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE AppLog
   (
    Fecha   VARCHAR(50)    NOT NULL,
    Clase  VARCHAR(50)    NOT NULL,
    Nivel   VARCHAR(10)    NOT NULL,
    Mensaje VARCHAR(1000)  NOT NULL
   );
   
CREATE TABLE `roles` (
  `codi_role` int(11) NOT NULL AUTO_INCREMENT,
  `desc_role` varchar(250) DEFAULT NULL,
  `dire_role` varchar(250) DEFAULT NULL,
  `refe_role` int(11) DEFAULT NULL,
  `esta_role` int(1) DEFAULT NULL,
  `fech_alta_role` date DEFAULT NULL,
  `fech_baja_role` date DEFAULT NULL,
  PRIMARY KEY (`codi_role`),
  KEY `refe_role_fk` (`refe_role`),
  CONSTRAINT `refe_role_fk` FOREIGN KEY (`refe_role`) REFERENCES `roles` (`codi_role`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into roles values(null,'Administrador',null,null,1,null,null);
insert into roles values(null,'Empleado',null,null,1,null,null);
/* Table structure for usuarios */
CREATE TABLE `usuarios` (
  `codi_usua` int(11) NOT NULL AUTO_INCREMENT,
  `codi_alum` int(11) DEFAULT NULL,
  `acce_usua` varchar(100) NOT NULL,
  `cont_usua` varchar(100) DEFAULT NULL,
  `esta_usua` int(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`codi_usua`),
  KEY `fk_usua_alum` (`codi_alum`),
  CONSTRAINT `fk_usua_alum` FOREIGN KEY (`codi_alum`) REFERENCES `alumnos` (`codi_alum`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

insert into usuarios values(null,1,'admin','123',1);
insert into usuarios values(null,1,'ariel','123',1);

insert into usuarios values(null,1,'jennyfer','123',1);
insert into usuarios values(null,1,'eduardo','123',1);


/* Table structure for usuarios_roles */
CREATE TABLE `usuarios_roles` (
  `codi_usua_role` int(11) NOT NULL AUTO_INCREMENT,
  `codi_role` int(11) DEFAULT NULL,
  `codi_usua` int(11) DEFAULT NULL,
  `esta_usua_role` int(11) DEFAULT NULL,
  `fech_alta_role` date DEFAULT NULL,
  `fech_baja_role` date DEFAULT NULL,
  PRIMARY KEY (`codi_usua_role`),
  KEY `usuarios_roles_fk1` (`codi_role`),
  KEY `usuarios_roles_fk2` (`codi_usua`),
  CONSTRAINT `usuarios_roles_fk1` FOREIGN KEY (`codi_role`) REFERENCES `roles` (`codi_role`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `usuarios_roles_fk2` FOREIGN KEY (`codi_usua`) REFERENCES `usuarios` (`codi_usua`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


select * from roles
-- inserts 

insert into cursos values(null,'curso 1',8640,20.00);
insert into cursos values(null,'curso 2',8640,20.00);
insert into cursos values(null,'curso 3',8640,20.00);


insert into grupos values(null,'grupo 1','2016-1-1','2016-12-12','12:00','00:00',1,1);
insert into grupos values(null,'grupo 2','2016-1-1','2016-12-12','12:00','00:00',2,2);
insert into grupos values(null,'grupo 3','2016-1-1','2016-12-12','12:00','00:00',3,3);

insert into profesores values(null,'javier','lopez','correo1@correo.com',22324578,'dirreción1');
insert into profesores values(null,'pablo','sanchez','correo1@correo.com',22324578,'dirreción1');
insert into profesores values(null,'carloz','sanchis','correo1@correo.com',22324578,'dirreción1');



select * from alumnos

-- La exportación de datos fue deseleccionada.
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;