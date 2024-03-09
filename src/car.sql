CREATE DATABASE IF NOT EXISTS car_rental;
USE car_rental;
DROP TABLE IF EXISTS car;

CREATE TABLE car (id INT (2) NOT NULL AUTO_INCREMENT,
                  model VARCHAR (15),
                  brand VARCHAR (20),
                  colour VARCHAR (10),
                  production_year INT (5),
                  price INT (9),
                  PRIMARY KEY (id));

INSERT INTO car VALUES (1,"SUV","Toyota Land Cruiser","Blue",2003,51290);
INSERT INTO car VALUES (2,"Convertible","Alfa Romeo Spider","Red",1989,23574);
INSERT INTO car VALUES (3,"Convertible","Bentley Azure","Black",1997,99642);
INSERT INTO car VALUES (4,"Sedan","Alfa Romeo 156","Grey",2001,23100);
INSERT INTO car VALUES (5,"Coupe","Renault Megane","White",2010,21490);
INSERT INTO car VALUES (6,"Sedan","Volkswagen CC","Blue",2011,40050);
INSERT INTO car VALUES (7,"Hatchback","Daihatsu Cuore","Red",2007,8999);
INSERT INTO car VALUES (8,"Pick-up","Ford Ranger","Yellow",2012,59950);
INSERT INTO car VALUES (9,"SUV","Audi Q3","Blue",2014,36430);
INSERT INTO car VALUES (10,"Coupe","Porsche 911 Carrera","Brown",2018,120328);
INSERT INTO car VALUES (11,"Hatchback","Mazda 2 1.3","Green",2010,11490);
INSERT INTO car VALUES (12,"Convertible","Corvette C6","Red",2005,103290);
INSERT INTO car VALUES (13,"Pick-up","Volkswagen Amarok","Grey",2020,48930);
INSERT INTO car VALUES (14,"SUV","Skoda Enyaq iV","Turquoise",2022,38965);
INSERT INTO car VALUES (15,"Coupe","Lexus RC","Green",2018,48045);