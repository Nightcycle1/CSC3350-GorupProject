package controller;
/**
 * ReportGenerator.java -
 * Generates pay statements, job title totals, division totals, and new hire reports.
 *
 */

import dao.DataAccessLayer;
import java.sql.Date;
import java.util.Collections;
import java.util.List;
import model.Employee;
import model.User;

public class ReportGenerator {

    private final DataAccessLayer dal; // doesn't need to be changed
    private final User currentUser; // doesn't need to be changed

    
    public ReportGenerator(DataAccessLayer dal, User currentUser) {
        this.dal = dal;
        this.currentUser = currentUser;
    }

    public List<String> getMyPayHistory() {
        int empID = currentUser.getEmpID();
        return dal.getPayHistory(empID);
    }

    public List<String> getPayHistory(int empID) {
        if (currentUser.getRole() == User.Role.HR_ADMIN) {
            return dal.getPayHistory(empID);
        }
        System.out.println("Access denied.");
        return Collections.emptyList();
    }

    public double getTotalPayByJobTitle(int jobTitleId, int month, int year) {
        if (currentUser.getRole() == User.Role.HR_ADMIN) {
            return dal.getMonthlyTotalPayByTitle(jobTitleId, month, year);
        }
        System.out.println("Access denied.");
        return 0.0;
    }

    public double getTotalPayByDivision(int divID, int month, int year) {
        if (currentUser.getRole() == User.Role.HR_ADMIN) {
            return dal.getMonthlyTotalPayByDivision(divID, month, year);
        }
        System.out.println("Access denied.");
        return 0.0;
    }

    public List<Employee> getNewHiresByDateRange(Date start, Date end) {
        if (currentUser.getRole() == User.Role.HR_ADMIN) {
            return dal.getNewHiresByDateRange(start, end);
        }
        System.out.println("Access denied.");
        return Collections.emptyList();
    }

    public static void main(String[] args) {
        System.out.println("=== ReportGenerator Tests ===\n");

        DataAccessLayer dal = new DataAccessLayer();
        dal.connect();

        User adminUser = new User(1, "john.doe@companyz.com", User.Role.HR_ADMIN);
        User empUser   = new User(2, "jane.smith@companyz.com", User.Role.GENERAL_EMPLOYEE);
        ReportGenerator adminRG = new ReportGenerator(dal, adminUser);
        ReportGenerator empRG   = new ReportGenerator(dal, empUser);

        // Test 1: getMyPayHistory - own pay history as HR Admin
        System.out.println("Test 1: getMyPayHistory() as HR Admin (empID 1)");
        List<String> ownHistory = adminRG.getMyPayHistory();
        System.out.println("Records returned: " + ownHistory.size());
        for (String record : ownHistory) {
            System.out.println("    " + record);
        }
        System.out.println();


        // Test 2: getMyPayHistory - own pay history as General Employee (no role restriction)
        System.out.println("Test 2: getMyPayHistory() as General Employee (empID 2)");
        List<String> empOwnHistory = empRG.getMyPayHistory();
        System.out.println("Records returned: " + empOwnHistory.size());
        for (String record : empOwnHistory) {
            System.out.println("    " + record);
        }
        System.out.println();

        // Test 3: getPayHistory as HR Admin viewing another employee
        System.out.println("Test 3: getPayHistory(2) as HR Admin");
        List<String> payHistory = adminRG.getPayHistory(2);
        System.out.println("Records returned: " + payHistory.size());
        for (String record : payHistory) {
            System.out.println("    " + record);
        }
        System.out.println();

        // Test 4: getPayHistory as General Employee (expect access denied)
        System.out.println("Test 4: getPayHistory(1) as General Employee (expect denied)");
        List<String> denied = empRG.getPayHistory(1);
        System.out.println("Records returned (expect 0): " + denied.size());
        for (String record : denied) {
            System.out.println("    " + record);
        }
        System.out.println();

        // Test 5: getTotalPayByJobTitle as HR Admin
        System.out.println("Test 5: getTotalPayByJobTitle(1, 1, 2024) as HR Admin");
        double titleTotal = adminRG.getTotalPayByJobTitle(1, 1, 2024);
        System.out.println("Total: $" + titleTotal);
        System.out.println();

        // Test 6: getTotalPayByJobTitle as General Employee (expect access denied)
        System.out.println("Test 6: getTotalPayByJobTitle as General Employee (expect denied)");
        double titleDenied = empRG.getTotalPayByJobTitle(1, 1, 2024);
        System.out.println("Total (expect 0.0): " + titleDenied);
        System.out.println();

        // Test 7: getTotalPayByDivision as HR Admin
        System.out.println("Test 7: getTotalPayByDivision(1, 1, 2024) as HR Admin");
        double divTotal = adminRG.getTotalPayByDivision(1, 1, 2024);
        System.out.println("Total: $" + divTotal);
        System.out.println();

        // Test 8: getTotalPayByDivision as General Employee (expect access denied)
        System.out.println("Test 8: getTotalPayByDivision as General Employee (expect denied)");
        double divDenied = empRG.getTotalPayByDivision(1, 1, 2024);
        System.out.println("Total (expect 0.0): " + divDenied);
        System.out.println();

        // Test 9: getNewHiresByDateRange as HR Admin
        System.out.println("Test 9: getNewHiresByDateRange as HR Admin (2020-01-01 to 2025-12-31)");
        Date start = Date.valueOf("2020-01-01");
        Date end   = Date.valueOf("2025-12-31");
        List<Employee> newHires = adminRG.getNewHiresByDateRange(start, end);
        System.out.println("New hires found: " + newHires.size());
        for (Employee emp : newHires) {
            System.out.println("    " + emp);
        }
        System.out.println();

        // Test 10: getNewHiresByDateRange as General Employee (expect access denied)
        System.out.println("Test 10: getNewHiresByDateRange as General Employee (expect denied)");
        List<Employee> hiresDenied = empRG.getNewHiresByDateRange(start, end);
        System.out.println("Records returned (expect 0): " + hiresDenied.size());
        for (Employee emp : hiresDenied) {
            System.out.println("    " + emp);
        }
        System.out.println();

        dal.disconnect();
        System.out.println("=== Tests Complete ===");
    }
}
