package controller;
/**
 * ReportGenerator.java - 
 * Generates pay statements, job title totals, division totals, and new hire reports.
 *  
 */

import dao.DataAccessLayer;
import model.User;

public class ReportGenerator {

    private DataAccessLayer dal;
    private User currentUser;

    public ReportGenerator(DataAccessLayer dal, User currentUser) {
        this.dal = dal;
        this.currentUser = currentUser;
    }

    public void getMyPayHistory() {
        int empID = currentUser.getEmpID();
        dal.getPayHistory(empID);
    }

    public void getPayHistory(int empID) {
        if (currentUser.getRole() == User.Role.HR_ADMIN) {
            dal.getPayHistory(empID);
        } else {
            System.out.println("Access denied.");
        }
    }

    public void getTotalPayByJobTitle(int month, int year) {
        dal.getTotalPayByJobTitle(month, year);
    }

    public void getTotalPayByDivision(int month, int year) {
        dal.getTotalPayByDivision(month, year);
    }

    public void getNewHiresByDateRange(String start, String end) {
        dal.getNewHiresByDateRange(start, end);
    }
}
