-- 
-- Table structure for table employee
--

DROP TABLE IF EXISTS employees;

CREATE TABLE employees (
  id INT PRIMARY KEY NOT NULL,
  first_name varchar(45) DEFAULT NULL,
  last_name varchar(45) DEFAULT NULL,
  email varchar(45) DEFAULT NULL
);

--
-- Data for table employee
--

INSERT INTO employees (id, first_name, last_name, email) VALUES
	(1,'Leslie','Andrews','leslie@gmail.com'),
	(2,'Emma','Baumgarten','emma@gmail.com'),
	(3,'Avani','Gupta','avani@gmail.com'),
	(4,'Yuri','Petrov','yuri@gmail.com'),
	(5,'Juan','Vega','juan@gmail.com');

