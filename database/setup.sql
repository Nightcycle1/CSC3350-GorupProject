
/* 
    THESE ARE FOR dBeaver usage
    VS Code usage may give you unpredictable results. 	
*/

USE EmployeeDatabase;

/***********************************************************************/


-- Disable foreign key checks for dropping tables in correct order
set FOREIGN_KEY_CHECKS = 0;

-- Drop tables if they exist (order matters due to foreign keys)
DROP TABLE IF EXISTS employee_job_titles;
DROP TABLE IF EXISTS employee_division;
DROP TABLE IF EXISTS payroll;
DROP TABLE IF EXISTS employees;
DROP TABLE IF EXISTS job_titles;
DROP TABLE IF EXISTS division;
DROP TABLE IF EXISTS addresses;
DROP TABLE IF EXISTS cities;
DROP TABLE IF EXISTS states;

-- Re-enable foreign key checks
SET FOREIGN_KEY_CHECKS = 1;

/*
Table Creation
*/

/***********************************************************************/

CREATE TABLE states 
(
    StateID INT PRIMARY KEY AUTO_INCREMENT,
    state_abbreviation VARCHAR(2) NOT NULL UNIQUE
);

/***********************************************************************/

CREATE TABLE cities
(
    cityID INT PRIMARY KEY AUTO_INCREMENT,
    city_name VARCHAR(100) NOT NULL
);

/***********************************************************************/

CREATE TABLE addresses
(
    addressID INT PRIMARY KEY AUTO_INCREMENT,
    street VARCHAR(100) NOT NULL,
    cityID INT NOT NULL,
    stateID INT NOT NULL,
    zip VARCHAR(10) NOT NULL,
    DOB DATE,
    mobile_number VARCHAR(20),
    emergency_contact_name VARCHAR(100),
    emergency_contact_phone VARCHAR(20),
    FOREIGN KEY (cityID) REFERENCES cities(cityID),
    FOREIGN KEY (stateID) REFERENCES states(StateID)
);

/***********************************************************************/

CREATE TABLE employees
(
    empID INT PRIMARY KEY AUTO_INCREMENT,
    Fname VARCHAR(65) NOT NULL,
    Lname VARCHAR(65) NOT NULL,
    email VARCHAR(65) NOT NULL,
    HireDate DATE,
    Salary DECIMAL(10,2) NOT NULL,
    SSN VARCHAR(12),
    addressID INT,
    username VARCHAR(65) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    FOREIGN KEY (addressID) REFERENCES addresses(addressID)
);

/***********************************************************************/

CREATE TABLE job_titles
(
    job_titleID INT PRIMARY KEY AUTO_INCREMENT,
    job_title VARCHAR(125) NOT NULL UNIQUE
);

/***********************************************************************/

CREATE TABLE division
(
  divID INT PRIMARY KEY AUTO_INCREMENT,
  Name VARCHAR(100) NOT NULL,
  city VARCHAR (100),
  addressLine1 VARCHAR(200),
  addressLine2 VARCHAR(200),
  state VARCHAR(50),
  country VARCHAR(50),
  postalCode VARCHAR(20)
);

/***********************************************************************/

CREATE TABLE employee_job_titles
(
    empID INT NOT NULL,
    job_titleID INT NOT NULL,
    PRIMARY KEY (empID, job_titleID),
    FOREIGN KEY (empID) REFERENCES employees(empID) ON DELETE CASCADE,
    FOREIGN KEY (job_titleID) REFERENCES job_titles(job_titleID) ON DELETE CASCADE
);

/***********************************************************************/

CREATE TABLE employee_division
(
    empID INT NOT NULL,
    divID INT NOT NULL,
    PRIMARY KEY (empID, divID),
    FOREIGN KEY (empID) REFERENCES employees(empID) ON DELETE CASCADE,
    FOREIGN KEY (divID) REFERENCES division(divID) ON DELETE CASCADE

);

/***********************************************************************/

CREATE TABLE payroll
(
    payID INT PRIMARY KEY AUTO_INCREMENT,
    pay_date DATE NOT NULL,
    earnings DECIMAL(8,2) NOT NULL,
    fed_tax DECIMAL(7,2) NOT NULL,
    fed_med DECIMAL(7,2) NOT NULL,
    fed_SS DECIMAL(7,2) NOT NULL,
    state_tax DECIMAL(7,2) NOT NULL,
    retire_401k DECIMAL(7,2),
    health_care DECIMAL(7,2),
    empID INT NOT NULL,
    FOREIGN KEY (empID) REFERENCES employees(empID) ON DELETE CASCADE
);

/***********************************************************************/

-- This query shows all foreign key relationships for verification, run inndividually
SHOW CREATE TABLE employees;

SHOW CREATE TABLE payroll;

SHOW CREATE TABLE employee_job_titles;

SHOW CREATE TABLE employee_division;

SHOW CREATE TABLE addresses;

SHOW CREATE TABLE division;

SHOW CREATE TABLE job_titles;