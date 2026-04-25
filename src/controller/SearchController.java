package controller;

import dao.DataAccessLayer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Employee;

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

    public static void main(String[] args) {
        System.out.println("=== SearchController Tests ===\n");

        DataAccessLayer dal = new DataAccessLayer();
        dal.connect();
        AuthenticationController auth = new AuthenticationController(dal);
        SearchController sc = new SearchController(auth, dal);
        // Test 1: searchByEmpID as HR Admin (full data visible)
        System.out.println("Test 1: searchByEmpID(1) as HR Admin");
        auth.login("john.doe@companyz.com", "password123");
        Employee emp = sc.searchByEmpID(1);
        if (emp != null) {
            System.out.println("Found: " + emp.getFname() + " " + emp.getLname());
            System.out.println("SSN (visible to admin): " + emp.getSSN());
            System.out.println("Salary (visible to admin): " + emp.getSalary());
        }
        else {
            System.out.println("No employee found");
        }
        auth.logout();
        System.out.println();

        // Test 2: searchByEmpID as General Employee (sensitive data hidden)
        System.out.println("Test 2: searchByEmpID(1) as General Employee (sensitive data hidden)");
        auth.login("jane.smith@companyz.com", "password123");
        Employee emp2 = sc.searchByEmpID(1);
        if (emp2 != null) {
            System.out.println("    Found: " + emp2.getFname() + " " + emp2.getLname());
            System.out.println("    SSN (expect null): " + emp2.getSSN());
            System.out.println("    Salary (expect 0.0): " + emp2.getSalary());
        }
        else {
            System.out.println("    No employee found");
        }
        auth.logout();
        System.out.println();

        // Test 3: searchByEmpID for non-existent employee
        System.out.println("Test 3: searchByEmpID(9999) non-existent (expect null)");
        auth.login("john.doe@companyz.com", "password123");
        Employee notFound = sc.searchByEmpID(9999);
        System.out.println("    Result (expect null): " + notFound);
        auth.logout();
        System.out.println();

        // Test 4: searchByName as HR Admin (full data visible)
        System.out.println("Test 4: searchByName(\"doe\") as HR Admin");
        auth.login("john.doe@companyz.com", "password123");
        List<Employee> byName = sc.searchByName("doe");
        System.out.println("    Results found: " + byName.size());
        if (!byName.isEmpty()) {
            for (Employee e : byName) {
                System.out.println("    - " + e.getFname() + " " + e.getLname() + " | SSN: " + e.getSSN() + " | Salary: $" + e.getSalary());
            }
        }
        auth.logout();
        System.out.println();

        // Test 5: searchByName as General Employee (sensitive data hidden)
        System.out.println("Test 5: searchByName(\"smith\") as General Employee");
        auth.login("jane.smith@companyz.com", "password123");
        List<Employee> byNameEmp = sc.searchByName("smith");
        System.out.println("    Results found: " + byNameEmp.size());
        if (!byNameEmp.isEmpty()) {
            for (Employee e : byNameEmp) {
                System.out.println("    - " + e.getFname() + " " + e.getLname());
                System.out.println("     SSN (expect null): " + e.getSSN());
                System.out.println("     Salary (expect 0.0): $" + e.getSalary());
            }
        }
        auth.logout();
        System.out.println();

        // Test 6: searchBySSN as HR Admin (stub - currently returns null)
        System.out.println("Test 6: searchBySSN as HR Admin (method is a stub, expect null)");
        auth.login("john.doe@companyz.com", "password123");
        Employee bySSN = sc.searchBySSN("123-45-6789");
        if (bySSN != null) {
            System.out.println("    Found: " + bySSN.getFname() + " " + bySSN.getLname());
        }
        else {
            System.out.println("    Result (null): " + bySSN);
        }
        auth.logout();
        System.out.println();

        // Test 7: searchBySSN as General Employee (expect access denied)
        System.out.println("Test 7: searchBySSN as General Employee (expect denied)");
        auth.login("jane.smith@companyz.com", "password123");
        Employee ssnDenied = sc.searchBySSN("123-45-6789");
        System.out.println("    Result (expect null): " + ssnDenied);
        auth.logout();
        System.out.println();

        dal.disconnect();
        System.out.println("=== Tests Complete ===");
    }
}
