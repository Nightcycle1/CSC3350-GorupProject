package dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import model.Employee;
import model.User;

/**
 * DataAccessLayer.java - 
 * Single point of contact with database. All SQL queries executed here. Converts database rows to Employee objects. 
 * 
 */
public class DataAccessLayer {

    private Connection conn;

    public void connect() {
        try {
            // Load properties from config file
            Properties props = new Properties();
            FileInputStream fis = new FileInputStream("../../config.properties");
            props.load(fis);
            fis.close();

            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");

            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected!");
            
        } catch (FileNotFoundException e) {
            System.err.println("Config file not found: " + e.getMessage());
            // Fallback to hardcoded values
            try {
                conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/employeedata",
                    "root",
                    "password"
                );
                System.out.println("Connected using fallback credentials!");
            } catch (SQLException ex) {
                System.err.println("Fallback connection failed: " + ex.getMessage());
            }
        } catch (IOException e) {
            System.err.println("Error reading config file: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Connection Failed: " + e.getMessage());
        }
    }

    public void disconnect() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Disconnectred from database.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }

    public Employee findEmployeeById(int empID) {
        try {
            String sql = "SELECT * FROM employees WHERE empID = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, empID);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Employee(
                    rs.getInt("empID"),
                    rs.getInt("addressID"),
                    rs.getString("Fname"),
                    rs.getString("Lname"),
                    rs.getString("email"),
                    rs.getDate("HireDate"),
                    rs.getString("SSN"),
                    rs.getDouble("Salary"),
                    rs.getString("username"),
                    rs.getString("password")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Employee> findEmployeeByName(String name) {
        List<Employee> list = new ArrayList<>();

        try {
            String sql = "SELECT * FROM employees WHERE Fname LIKE ? OR Lname LIKE ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, "%" + name + "%");
            stmt.setString(2, "%" + name + "%");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(findEmployeeById(rs.getInt("empID")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void insertEmployee(Employee emp) {
        try {
            String sql = "INSERT INTO employees (Fname, Lname, email, HireDate, Salary, SSN, addressID, username, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, emp.getFname());
            stmt.setString(2, emp.getLname());
            stmt.setString(3, emp.getEmail());
            stmt.setDate(4, new java.sql.Date(emp.getHireDate().getTime()));
            stmt.setDouble(5, emp.getSalary());
            stmt.setString(6, emp.getSSN());
            stmt.setInt(7, emp.getAddressID());
            stmt.setString(8, emp.getUsername());
            stmt.setString(9, emp.getPassword());

            stmt.executeUpdate();
            System.out.println("Employee inserted successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateEmployee(int empID, Employee data) {
        try {
            String sql = "UPDATE employees SET Fname=?, Lname=?, email=?, HireDate=?, Salary=?, SSN=?, addressID=? WHERE empID=?";            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setString(1, data.getFname());
            statement.setString(2, data.getLname());
            statement.setString(3, data.getEmail());
            statement.setDate(4, new java.sql.Date(data.getHireDate().getTime()));
            statement.setDouble(5, data.getSalary());
            statement.setString(6, data.getSSN());
            statement.setInt(7, data.getAddressID());
            statement.setString(8, data.getUsername());
            statement.setString(9, data.getPassword());
            statement.setInt(10, empID);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Employee " + empID + " updated sucessfully.");
            }
            else {
                System.out.println("No employee found with ID: " + empID);
            }

        } catch (SQLException e) {
            System.err.println("Error updating employee: " + e.getMessage());
        }

    }

    public void deleteEmployee(int empID) {
        try {
            String sql = "DELETE FROM employees WHERE empID=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, empID);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateSalaryByPercentage(double percent, double min, double max) {
        try {
            // Convert percentage to multiplier
            double multiplier = 1 + (percent / 100);

            String sql = "UPDATE employees SET Salary = Salary * ? WHERE Salary BETWEEN ? and ?";
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setDouble(1, multiplier);
            statement.setDouble(2, min);
            statement.setDouble(3, max);

            int rowsAffected = statement.executeUpdate();
            System.out.println(rowsAffected + " employees had salaries updated by " + percent + "%");

        } catch (SQLException e) {
            System.err.println("Error updating salaries: " + e.getMessage());
        }
    }

    public List<String> getPayHistory(int empID) {
        List<String> payHistory = new ArrayList<>();


        try {
            String sql = "SELECT pay_date, earnings, fed_tax, fed_med, fed_SS, state_tax, retire_401k, health_care " + 
            "FROM payroll WHERE empID = ? ORDER BY pay_date DESC"; // descending order

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, empID);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String record = String.format("Date: %s, Earnings: $%.2f, Fed Tax: $%.2f, Fed Med: $%.2f, Fed SS: $%.2f, State Tax: $%.2f, 401k: $%.2f, Health: $%.2f",
                rs.getDate("pay_date"),
                rs.getDouble("earnings"),
                rs.getDouble("fed_tax"),
                rs.getDouble("fed_med"),
                rs.getDouble("fed_SS"),
                rs.getDouble("state_tax"),
                rs.getDouble("retire_401k"),
                rs.getDouble("health_care")
                );
                payHistory.add(record);
            }
         
        } catch (SQLException e) {
            System.err.println("Error getting pay history: " + e.getMessage());
        }
        return payHistory;
    }

    public List<Employee> getNewHiresByDateRange(Date start, Date end) {
        List<Employee> newHires = new ArrayList<>();

        try {
            String sql = "SELECT * FROM employees WHERE HireDate BETWEEN ? AND ? ORDER BY HireDate DESC";

            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setDate(1, new java.sql.Date(start.getTime()));
            statement.setDate(2, new java.sql.Date(end.getTime()));

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                newHires.add(new Employee(
                rs.getInt("empID"),
                rs.getInt("addressID"),
                rs.getString("Fname"),
                rs.getString("Lname"),
                rs.getString("email"),
                rs.getDate("HireDate"),
                rs.getString("SSN"),
                rs.getDouble("Salary"),
                rs.getString("username"),
                rs.getString("password")
                ));
            }
        } catch (Exception e) {
            System.err.println("Error getting new hires: " + e.getMessage());
        }
        return newHires;
    }


    public double getMonthlyTotalPayByTitle(int job_title_id, int month, int year) {
    
        double totalPay = 0.0;
    
        try {
            String sql = "SELECT SUM(p.earnings) AS total " +
                        "FROM payroll p " +
                        "JOIN employee_job_titles ejt ON p.empID = ejt.empID " +
                        "WHERE ejt.job_titleID = ? " +
                        "AND MONTH(p.pay_date) = ? AND YEAR(p.pay_date) = ?";
            
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, job_title_id);
            statement.setInt(2, month);
            statement.setInt(3, year);
            
            ResultSet rs = statement.executeQuery();
            
            if (rs.next()) {
                totalPay = rs.getDouble("total");
            }
            
        } catch (SQLException e) {
            System.err.println("Error calculating pay by title: " + e.getMessage());
        }
    
        return totalPay;
    }

    public double getMonthlyTotalPayByDivision(int div_ID, int month, int year) {
        double totalPay = 0.0;
        
        try {
            String sql = "SELECT SUM(p.earnings) AS total " +
                        "FROM payroll p " +
                        "JOIN employee_division ed ON p.empID = ed.empID " +
                        "WHERE ed.divID = ? " +
                        "AND MONTH(p.pay_date) = ? AND YEAR(p.pay_date) = ?";
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, div_ID);
            stmt.setInt(2, month);
            stmt.setInt(3, year);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                totalPay = rs.getDouble("total");
            }
            
        } catch (SQLException e) {
            System.err.println("Error calculating pay by division: " + e.getMessage());
        }
        
        return totalPay;
    }



    public User validateLogin(String username, String password) {
        try {
            String sql = "SELECT empID, username, password FROM employees WHERE username = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, username);
            
            ResultSet rs = statement.executeQuery();
            
            if (rs.next()) {
                String storedPassword = rs.getString("password");
                
                // Plain text comparison (for testing only)
                if (storedPassword.equals(password)) {
                    int empID = rs.getInt("empID");
                    
                    // Determine role - you may need a roles table or column
                    // For now, assume empID 1 is HR Admin for testing
                    User.Role role = (empID == 1) ? User.Role.HR_ADMIN : User.Role.GENERAL_EMPLOYEE;
                    
                    return new User(empID, username, role);
                }
            }
        } catch (SQLException e) {
            System.err.println("Login validation error: " + e.getMessage());
        }
        
        return null; // Invalid credentials
    }
}
