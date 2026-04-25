package controller;

import dao.DataAccessLayer;
import model.Employee;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchController {

    private AuthenticationController auth;
    private DataAccessLayer dal;

    public SearchController(AuthenticationController auth, DataAccessLayer dal) {
        this.auth = auth;
        this.dal = dal;
    }

    public Employee searchByEmpID(int empID) {
        Employee emp = dal.findEmployeeById(empID);
        if (emp == null) {
            System.out.println("No employee found with ID: " + empID);
            return null;
        }
        if (!auth.isHRAdmin()) {
            hideSensitiveData(emp);
        }
        return emp;
    }

    public List<Employee> searchByName(String name) {
        List<Employee> employees = dal.findEmployeeByName(name);
        if (employees == null) return new ArrayList<>();
        if (!auth.isHRAdmin()) {
            for (Employee emp : employees) {
                hideSensitiveData(emp);
            }
        }
        return employees;
    }

    public List<Employee> searchByDOB(Date dob) {
        return new ArrayList<>();
    }

    public Employee searchBySSN(String ssn) {
        if (!auth.isHRAdmin()) {
            System.out.println("Access denied: SSN search is restricted to HR Admins.");
            return null;
        }
        return null;
    }

    private void hideSensitiveData(Employee emp) {
        emp.setSSN(null);
        emp.setSalary(0);
        emp.setPassword(null);
    }
}
