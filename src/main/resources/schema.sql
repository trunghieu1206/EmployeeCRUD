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

