/* 
    SAMPLE DATA POPULATION
    Run this AFTER setup.sql
    THESE ARE FOR dBeaver usage
*/

USE EmployeeDatabase;

/***********************************************************************/

-- STATES
INSERT INTO states
    (state_abbreviation)
VALUES
    ('CA'),
    ('TX'),
    ('NY'),
    ('FL'),
    ('IL'),
    ('WA'),
    ('MA'),
    ('CO'),
    ('GA'),
    ('AZ'),
    ('OR'),
    ('NV');

/***********************************************************************/

-- CITIES
INSERT INTO cities
    (city_name)
VALUES
    ('Los Angeles'),
    ('San Francisco'),
    ('Austin'),
    ('Houston'),
    ('New York'),
    ('Buffalo'),
    ('Miami'),
    ('Orlando'),
    ('Chicago'),
    ('Seattle'),
    ('Boston'),
    ('Denver'),
    ('Atlanta'),
    ('Phoenix'),
    ('Portland'),
    ('Las Vegas');

/***********************************************************************/

-- ADDRESSES (12 employees)
INSERT INTO addresses
    (street, cityID, stateID, zip, DOB, mobile_number, emergency_contact_name, emergency_contact_phone)
VALUES
    ('123 Main St', 1, 1, '90001', '1985-06-15', '555-0101', 'Jane Smith', '555-0102'),
    ('456 Oak Ave', 3, 3, '77001', '1990-03-22', '555-0201', 'Bob Johnson', '555-0202'),
    ('789 Pine Rd', 5, 5, '10001', '1988-11-10', '555-0301', 'Alice Williams', '555-0302'),
    ('321 Elm St', 7, 4, '33101', '1992-07-08', '555-0401', 'Charlie Brown', '555-0402'),
    ('555 Cedar Ln', 9, 6, '60601', '1983-12-01', '555-0501', 'Diana Prince', '555-0502'),
    ('777 Maple Dr', 10, 7, '98101', '1995-04-18', '555-0601', 'Edward Nygma', '555-0602'),
    ('888 Birch Blvd', 12, 8, '80201', '1987-09-25', '555-0701', 'Frank Castle', '555-0702'),
    ('999 Walnut Ct', 13, 9, '30301', '1991-02-14', '555-0801', 'Grace Hopper', '555-0802'),
    ('111 Spruce Way', 14, 10, '85001', '1984-11-30', '555-0901', 'Henry Cavill', '555-0902'),
    ('222 Redwood Rd', 15, 11, '97201', '1993-08-19', '555-1001', 'Irene Adler', '555-1002'),
    ('333 Aspen Ln', 16, 12, '89101', '1989-05-05', '555-1101', 'Jack Ryan', '555-1102'),
    ('444 Willow Dr', 2, 1, '94105', '1994-12-12', '555-1201', 'Kara Zor-El', '555-1202');

/***********************************************************************/

-- EMPLOYEES (12 employees)
INSERT INTO employees
    (empID, Fname, Lname, email, HireDate, Salary, SSN, addressID, username, password)
VALUES
    (1, 'John', 'Doe', 'john.doe@companyz.com', '2020-01-15', 75000.00, '123-45-6789', 1, 'john.doe@companyz.com', 'password123'),
    (2, 'Jane', 'Smith', 'jane.smith@companyz.com', '2019-03-10', 85000.00, '234-56-7890', 2, 'jane.smith@companyz.com', 'password123'),
    (3, 'Bob', 'Johnson', 'bob.johnson@companyz.com', '2021-06-20', 65000.00, '345-67-8901', 3, 'bob.johnson@companyz.com', 'password123'),
    (4, 'Alice', 'Williams', 'alice.williams@companyz.com', '2018-11-05', 95000.00, '456-78-9012', 4, 'alice.williams@companyz.com', 'password123'),
    (5, 'Michael', 'Brown', 'michael.brown@companyz.com', '2022-01-30', 55000.00, '567-89-0123', 5, 'michael.brown@companyz.com', 'password123'),
    (6, 'Sarah', 'Davis', 'sarah.davis@companyz.com', '2020-09-12', 72000.00, '678-90-1234', 6, 'sarah.davis@companyz.com', 'password123'),
    (7, 'David', 'Wilson', 'david.wilson@companyz.com', '2021-03-18', 82000.00, '789-01-2345', 7, 'david.wilson@companyz.com', 'password123'),
    (8, 'Emily', 'Martinez', 'emily.martinez@companyz.com', '2019-11-22', 91000.00, '890-12-3456', 8, 'emily.martinez@companyz.com', 'password123'),
    (9, 'Chris', 'Taylor', 'chris.taylor@companyz.com', '2022-07-08', 58000.00, '901-23-4567', 9, 'chris.taylor@companyz.com', 'password123'),
    (10, 'Jessica', 'Anderson', 'jessica.anderson@companyz.com', '2020-05-14', 78000.00, '012-34-5678', 10, 'jessica.anderson@companyz.com', 'password123'),
    (11, 'Kevin', 'Thomas', 'kevin.thomas@companyz.com', '2021-10-30', 69000.00, '123-45-6780', 11, 'kevin.thomas@companyz.com', 'password123'),
    (12, 'Laura', 'Jackson', 'laura.jackson@companyz.com', '2018-08-25', 88000.00, '234-56-7891', 12, 'laura.jackson@companyz.com', 'password123');

/***********************************************************************/

-- JOB TITLES
INSERT INTO job_titles
    (job_title)
VALUES
    ('Software Engineer'),
    ('Senior Software Engineer'),
    ('Product Manager'),
    ('HR Admin'),
    ('Data Analyst'),
    ('DevOps Engineer'),
    ('QA Tester'),
    ('Technical Lead'),
    ('Sales Director'),
    ('Marketing Manager'),
    ('UX Designer'),
    ('Systems Architect');

/***********************************************************************/

-- DIVISIONS (4 divisions in different US cities)
INSERT INTO division
    (divID, Name, city, addressLine1, addressLine2, state, country, postalCode)
VALUES
    (1, 'Engineering - West Coast', 'San Francisco', '100 Tech Way', 'Suite 200', 'CA', 'USA', '94105'),
    (2, 'Product Management', 'Austin', '500 Innovation Blvd', 'Floor 15', 'TX', 'USA', '78701'),
    (3, 'Human Resources', 'New York', '200 HR Plaza', 'Building A', 'NY', 'USA', '10001'),
    (4, 'Operations', 'Chicago', '750 Operations Dr', 'Suite 300', 'IL', 'USA', '60601');

/***********************************************************************/

-- EMPLOYEE JOB TITLES (mapping employees to job titles)
INSERT INTO employee_job_titles
    (empID, job_titleID)
VALUES
    (1, 1),
    -- John - Software Engineer
    (2, 2),
    -- Jane - Senior Software Engineer
    (3, 1),
    -- Bob - Software Engineer
    (4, 3),
    -- Alice - Product Manager
    (5, 5),
    -- Michael - Data Analyst
    (6, 6),
    -- Sarah - DevOps Engineer
    (7, 8),
    -- David - Technical Lead
    (8, 3),
    -- Emily - Product Manager
    (9, 7),
    -- Chris - QA Tester
    (10, 11),
    -- Jessica - UX Designer
    (11, 12),
    -- Kevin - Systems Architect
    (12, 4);
-- Laura - HR Specialist

/***********************************************************************/

-- EMPLOYEE DIVISIONS (mapping employees to divisions)
INSERT INTO employee_division
    (empID, divID)
VALUES
    (1, 1),
    (2, 1),
    (3, 1),
    (6, 1),
    (7, 1),
    (11, 1),
    -- Engineering (7 employees)
    (4, 2),
    (8, 2),
    -- Product Management (2 employees)
    (5, 4),
    (9, 4),
    (10, 4),
    -- Operations (3 employees)
    (12, 3);
-- HR (1 employee)

/***********************************************************************/

-- PAYROLL RECORDS (each employee has 2-3 months of history)
INSERT INTO payroll
    (pay_date, earnings, fed_tax, fed_med, fed_SS, state_tax, retire_401k, health_care, empID)
VALUES
    -- January 2024
    ('2024-01-31', 6250.00, 937.50, 90.63, 387.50, 312.50, 312.50, 250.00, 1),
    ('2024-01-31', 7083.33, 1062.50, 102.71, 439.17, 354.17, 354.17, 250.00, 2),
    ('2024-01-31', 5416.67, 812.50, 78.54, 335.83, 270.83, 270.83, 250.00, 3),
    ('2024-01-31', 7916.67, 1187.50, 114.79, 490.83, 395.83, 395.83, 250.00, 4),
    ('2024-01-31', 4583.33, 687.50, 66.46, 284.17, 229.17, 229.17, 250.00, 5),
    ('2024-01-31', 6000.00, 900.00, 87.00, 372.00, 300.00, 300.00, 250.00, 6),
    ('2024-01-31', 6833.33, 1025.00, 99.08, 423.67, 341.67, 341.67, 250.00, 7),
    ('2024-01-31', 7583.33, 1137.50, 109.96, 470.17, 379.17, 379.17, 250.00, 8),
    ('2024-01-31', 4833.33, 725.00, 70.08, 299.67, 241.67, 241.67, 250.00, 9),
    ('2024-01-31', 6500.00, 975.00, 94.25, 403.00, 325.00, 325.00, 250.00, 10),
    ('2024-01-31', 5750.00, 862.50, 83.38, 356.50, 287.50, 287.50, 250.00, 11),
    ('2024-01-31', 7333.33, 1100.00, 106.33, 454.67, 366.67, 366.67, 250.00, 12),

    -- February 2024
    ('2024-02-29', 6250.00, 937.50, 90.63, 387.50, 312.50, 312.50, 250.00, 1),
    ('2024-02-29', 7083.33, 1062.50, 102.71, 439.17, 354.17, 354.17, 250.00, 2),
    ('2024-02-29', 5416.67, 812.50, 78.54, 335.83, 270.83, 270.83, 250.00, 3),
    ('2024-02-29', 7916.67, 1187.50, 114.79, 490.83, 395.83, 395.83, 250.00, 4),
    ('2024-02-29', 4583.33, 687.50, 66.46, 284.17, 229.17, 229.17, 250.00, 5),
    ('2024-02-29', 6000.00, 900.00, 87.00, 372.00, 300.00, 300.00, 250.00, 6),
    ('2024-02-29', 6833.33, 1025.00, 99.08, 423.67, 341.67, 341.67, 250.00, 7),
    ('2024-02-29', 7583.33, 1137.50, 109.96, 470.17, 379.17, 379.17, 250.00, 8),
    ('2024-02-29', 4833.33, 725.00, 70.08, 299.67, 241.67, 241.67, 250.00, 9),
    ('2024-02-29', 6500.00, 975.00, 94.25, 403.00, 325.00, 325.00, 250.00, 10),
    ('2024-02-29', 5750.00, 862.50, 83.38, 356.50, 287.50, 287.50, 250.00, 11),
    ('2024-02-29', 7333.33, 1100.00, 106.33, 454.67, 366.67, 366.67, 250.00, 12),

    -- March 2024
    ('2024-03-31', 6250.00, 937.50, 90.63, 387.50, 312.50, 312.50, 250.00, 1),
    ('2024-03-31', 7083.33, 1062.50, 102.71, 439.17, 354.17, 354.17, 250.00, 2),
    ('2024-03-31', 5416.67, 812.50, 78.54, 335.83, 270.83, 270.83, 250.00, 3),
    ('2024-03-31', 7916.67, 1187.50, 114.79, 490.83, 395.83, 395.83, 250.00, 4),
    ('2024-03-31', 4583.33, 687.50, 66.46, 284.17, 229.17, 229.17, 250.00, 5),
    ('2024-03-31', 6000.00, 900.00, 87.00, 372.00, 300.00, 300.00, 250.00, 6),
    ('2024-03-31', 6833.33, 1025.00, 99.08, 423.67, 341.67, 341.67, 250.00, 7),
    ('2024-03-31', 7583.33, 1137.50, 109.96, 470.17, 379.17, 379.17, 250.00, 8),
    ('2024-03-31', 4833.33, 725.00, 70.08, 299.67, 241.67, 241.67, 250.00, 9),
    ('2024-03-31', 6500.00, 975.00, 94.25, 403.00, 325.00, 325.00, 250.00, 10),
    ('2024-03-31', 5750.00, 862.50, 83.38, 356.50, 287.50, 287.50, 250.00, 11),
    ('2024-03-31', 7333.33, 1100.00, 106.33, 454.67, 366.67, 366.67, 250.00, 12);

/***********************************************************************/

-- VERIFICATION QUERIES
SELECT '=== All Employees ===' AS '';
SELECT empID, Fname, Lname, email, username, HireDate, Salary
FROM employees;

SELECT '=== Division Summary ===' AS '';
SELECT divID, Name, city, state
FROM division;

SELECT '=== Employees by Division ===' AS '';
SELECT d.Name AS Division, COUNT(e.empID) AS Employee_Count
FROM employee_division ed
    JOIN division d ON ed.divID = d.divID
    JOIN employees e ON ed.empID = e.empID
GROUP BY d.Name;

SELECT '=== Total Payroll Summary ===' AS '';
SELECT COUNT(DISTINCT empID) AS Total_Employees_Paid, COUNT(*) AS Total_Payroll_Records, SUM(earnings) AS Total_Earnings
FROM payroll;