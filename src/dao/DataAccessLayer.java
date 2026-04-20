package dao;

import model.Employee;
import model.User;

import java.sql.*;
import java.util.*;
/**
 * DataAccessLayer.java - 
 * Single point of contact with database. All SQL queries executed here. Converts database rows to Employee objects. 
 * 
 */
public class DataAccessLayer {

    private Connection conn;

    public void connect() {
        try {
            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/employeedata",
                "root",
                "password"
            );
            System.out.println("Connected!");
        } catch (Exception e) {
            e.printStackTrace();
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
                    rs.getDouble("Salary")
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
            String sql = "INSERT INTO employees (Fname, Lname, email, HireDate, Salary, SSN, addressID) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, emp.getFname());
            stmt.setString(2, emp.getLname());
            stmt.setString(3, emp.getEmail());
            stmt.setDate(4, new java.sql.Date(emp.getHireDate().getTime()));
            stmt.setDouble(5, emp.getSalary());
            stmt.setString(6, emp.getSSN());
            stmt.setInt(7, emp.getAddressID());

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
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

    public User validateLogin(String username, String password) {
        if (username.equals("admin")) {
            return new User(1, username, User.Role.HR_ADMIN);
        } else {
            return new User(2, username, User.Role.GENERAL_EMPLOYEE);
        }
    }
}
