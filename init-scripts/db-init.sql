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

INSERT INTO employees (id, first_name, last_name, email) VALUES (1, 'Leslie', 'Andrews', 'leslie@gmail.com');
INSERT INTO employees (id, first_name, last_name, email) VALUES (2, 'Emma', 'Baumgarten', 'emma@gmail.com');
INSERT INTO employees (id, first_name, last_name, email) VALUES (3, 'Avani', 'Gupta', 'avani@gmail.com');
INSERT INTO employees (id, first_name, last_name, email) VALUES (4, 'Yuri', 'Petrov', 'yuri@gmail.com');
INSERT INTO employees (id, first_name, last_name, email) VALUES (5, 'Juan', 'Vega', 'juan@gmail.com');


