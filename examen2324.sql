#---------------------------------------------------------- 
#-- Creamos la BD y tablespace 
#---------------------------------------------------------- 
DROP DATABASE IF EXISTS examenPRG2324;
CREATE DATABASE examenPRG2324;
USE examenPRG2324;

#---------------------------------------------------------- 
#-- Creamos un usuario para el acceso a la base de datos 
#---------------------------------------------------------- 
DROP USER IF EXISTS 'examen'@'%'; 
CREATE USER 'examen'@'%' IDENTIFIED BY '1234'; 
GRANT ALL PRIVILEGES ON examenPRG2324.* TO 'examen'@'%'; 
GRANT ALL PRIVILEGES ON mysql.proc TO 'examen'@'%'; 

#---------------------------------------------------------- 
#-- Creamos las tablas e insertamos datos 
#---------------------------------------------------------- 
CREATE TABLE worker(
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  nombre varchar(50) not null,
  apellidos varchar(100) not null,
  DNI varchar(9) not null,
  edad integer not null,
  email varchar(50) not null,
  experiencia INTEGER
);
CREATE TABLE itworker(
	idWorker INTEGER PRIMARY KEY,
	categoria varchar(20) not null,
    foreign key (idWorker) references worker(id) on update cascade on delete cascade
    );
    
insert into worker values (1, "Joaquín","Alonso","12345678X",35,"jalonso@informatica.es",12);
insert into itworker values (1,"FullStack");
insert into worker values (2, "Javier","García","65432178X",25,"jgarcia@informatica.es",2);
insert into itworker values (2,"BackEnd");
insert into worker values (3, "José Miguel","Fajardo","98765432X",45,"jmfajardo@informatica.es",20);
insert into itworker values (3,"FrontEnd");
insert into worker values (4, "Xavier","Rosillo","87654321X",51,"xrosillo@informatica.es",6);
insert into itworker values (4,"BackEnd");


#---------------------------------------------------------- 
#-- Creamos los procedimientos 
#---------------------------------------------------------- 
DELIMITER @@
DROP FUNCTION IF EXISTS createITWorker @@  
CREATE DEFINER=`root`@`localhost` FUNCTION `createITWorker`(nombreIn varchar(50), apellidosIn varchar(100), DNIIn varchar(9), edadIn INT, emailIN varchar(50), experienciaIn INT, categoriaIN varchar(20)) RETURNS int
    DETERMINISTIC
BEGIN

    declare idOut int default -1;
	insert into worker(nombre, apellidos,DNI,edad,email,experiencia) values (nombreIn,apellidosIn,DNIIn,edadIn,emailIN,experienciaIn);
	set idOut = LAST_INSERT_ID();  
    insert into itworker values(idOut, categoriaIn);

RETURN idOut;
END@@

DELIMITER ;

DELIMITER @@
DROP PROCEDURE IF EXISTS getAllWorkers @@
CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllITWorkers`()
BEGIN
		select id,nombre, apellidos,DNI,edad,email,experiencia,categoria from worker inner join itworker on id = idWorker;        
END@@

DELIMITER ;

