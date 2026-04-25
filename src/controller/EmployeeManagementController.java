package controller;

import dao.DataAccessLayer;
import java.util.List;
import model.Employee;

public class EmployeeManagementController {

    private final AuthenticationController auth;
    private final DataAccessLayer dal;

    public EmployeeManagementController(AuthenticationController auth, DataAccessLayer dal) {
        this.auth = auth;
        this.dal = dal;
    }

    private boolean isHRAdmin() {
        return auth != null && auth.isHRAdmin();
    }

    // ➕ Add Employee
    public boolean addEmployee(Employee emp) {
        if (!isHRAdmin()) {
            System.out.println("Access denied.");
            return false;
        }

        dal.insertEmployee(emp); 
        return true;             
    }

    // ✏️ Update Employee
    public boolean updateEmployee(int empID, Employee newData) {
        if (!isHRAdmin()) {
            System.out.println("Access denied.");
            return false;
        }

        dal.updateEmployee(empID, newData);
        return true;
    }

    // ❌ Delete Employee
    public boolean deleteEmployee(int empID) {
        if (!isHRAdmin()) {
            System.out.println("Access denied.");
            return false;
        }

        dal.deleteEmployee(empID);
        return true;
    }

    // In file test
    public static void main(String[] args) {
        System.out.println("=== EmployeeManagementController Tests ===\n");

        DataAccessLayer dal = new DataAccessLayer();
        dal.connect();
        AuthenticationController auth = new AuthenticationController(dal);
        EmployeeManagementController emc = new EmployeeManagementController(auth, dal);

        Employee testEmp = new Employee(0, 1, "Test", "User", "test@companyz.com",
            new java.util.Date(), "999-99-9999", 50000.0, "test.user", "pass123");

        // Pre-test cleanup: remove any leftover test employee from a previous run
        auth.login("john.doe@companyz.com", "password123");
        List<Employee> existing = dal.findEmployeeByName("Test");
        for (Employee e : existing) {
            if ("test.user".equals(e.getUsername())) {
                emc.deleteEmployee(e.getEmpID());
                System.out.println("Pre-cleanup: removed leftover test employee (empID: " + e.getEmpID() + ")");
                break;
            }
        }
        auth.logout();
        System.out.println();

        // Test 1: addEmployee as General Employee (expect access denied)
        System.out.println("Test 1: addEmployee as General Employee (expect denied)");
        auth.login("jane.smith@companyz.com", "password123");
        boolean addDenied = emc.addEmployee(testEmp);
        System.out.println("Added (expect false): " + addDenied);
        auth.logout();
        System.out.println();

        // Test 2: addEmployee as HR Admin
        System.out.println("Test 2: addEmployee as HR Admin");
        auth.login("john.doe@companyz.com", "password123");
        boolean added = emc.addEmployee(testEmp);
        System.out.println("Added (expect true): " + added);
        auth.logout();
        System.out.println();

        // Test 3: updateEmployee as General Employee (expect access denied)
        System.out.println("Test 3: updateEmployee(1) as General Employee (expect denied)");
        auth.login("jane.smith@companyz.com", "password123");
        boolean updateDenied = emc.updateEmployee(1, testEmp);
        System.out.println("Updated (expect false): " + updateDenied);
        auth.logout();
        System.out.println();

        // Test 4: updateEmployee as HR Admin
        System.out.println("Test 4: updateEmployee(1) as HR Admin");
        auth.login("john.doe@companyz.com", "password123");
        boolean updated = emc.updateEmployee(1, testEmp);
        System.out.println("Updated (expect true): " + updated);
        auth.logout();
        System.out.println();

        // Test 5: deleteEmployee as General Employee (expect access denied)
        System.out.println("Test 5: deleteEmployee as General Employee (expect denied)");
        auth.login("jane.smith@companyz.com", "password123");
        boolean deleteDenied = emc.deleteEmployee(9999);
        System.out.println("Deleted (expect false): " + deleteDenied);
        auth.logout();
        System.out.println();

        // Test 6: deleteEmployee as HR Admin on non-existent ID (safe - no real row deleted)
        System.out.println("Test 6: deleteEmployee(9999) as HR Admin (non-existent ID, safe)");
        auth.login("john.doe@companyz.com", "password123");
        boolean deleted = emc.deleteEmployee(9999);
        System.out.println("Authorized (expect true): " + deleted);
        auth.logout();
        System.out.println();

        // Cleanup: delete the test employee inserted in Test 2 so the test can be re-run cleanly
        System.out.println("Cleanup: removing test employee 'test.user'");
        auth.login("john.doe@companyz.com", "password123");
        List<Employee> matches = dal.findEmployeeByName("Test");
        for (Employee e : matches) {
            if ("test.user".equals(e.getUsername())) {
                emc.deleteEmployee(e.getEmpID());
                System.out.println("Deleted test employee (empID: " + e.getEmpID() + ")");
                break;
            }
        }
        auth.logout();
        System.out.println();

        dal.disconnect();
        System.out.println("=== Tests Complete ===");
    }
}