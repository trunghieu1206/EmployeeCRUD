--
-- Table structure for table employee
--

DROP TABLE IF EXISTS employees;

-- use BIGSERIAL for auto increment ID
CREATE TABLE employees (
  id BIGSERIAL PRIMARY KEY NOT NULL,
  first_name varchar(45) DEFAULT NULL,
  last_name varchar(45) DEFAULT NULL,
  email varchar(45) DEFAULT NULL
);

--
-- Data for table employee
--

-- When inserting do not specify ID part, postgres will handle this id part
INSERT INTO employees (first_name, last_name, email) VALUES ('Leslie', 'Andrews', 'leslie@gmail.com');
INSERT INTO employees (first_name, last_name, email) VALUES ('Emma', 'Baumgarten', 'emma@gmail.com');
INSERT INTO employees (first_name, last_name, email) VALUES ('Avani', 'Gupta', 'avani@gmail.com');
INSERT INTO employees (first_name, last_name, email) VALUES ('Yuri', 'Petrov', 'yuri@gmail.com');
INSERT INTO employees (first_name, last_name, email) VALUES ('Juan', 'Vega', 'juan@gmail.com');
