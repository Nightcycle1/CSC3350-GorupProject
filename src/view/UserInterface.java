package view;

import controller.*;
import model.Employee;

import java.util.List;
import java.util.Scanner;

public class UserInterface {

    private AuthenticationController auth;
    private SearchController search;
    private EmployeeManagementController empMgmt;
    private ReportGenerator reports;

    private Scanner sc;

    public UserInterface(AuthenticationController auth,
                         SearchController search,
                         EmployeeManagementController empMgmt,
                         ReportGenerator reports) {
        this.auth = auth;
        this.search = search;
        this.empMgmt = empMgmt;
        this.reports = reports;
        this.sc = new Scanner(System.in);
    }

    // start application
    public void start() {
        while (true) {
            System.out.println("\n=== Employee Management System ===");
            System.out.println("1. Login");
            System.out.println("2. Exit");

            int choice = getIntInput();

            if (choice == 1) {
                loginFlow();
            } else if (choice == 2) {
                System.out.println("Goodbye!");
                break;
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

    // login flow
    private void loginFlow() {
        System.out.print("Username: ");
        String username = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        if (auth.login(username, password)) {
            if (auth.isHRAdmin()) {
                hrMenu();
            } else {
                employeeMenu();
            }
        } else {
            System.out.println("Login failed.");
        }
    }

    // general employee menu
    private void employeeMenu() {
        while (auth.isLoggedIn()) {
            System.out.println("\n--- Employee Menu ---");
            System.out.println("1. Search Employee by ID");
            System.out.println("2. Search Employee by Name");
            System.out.println("3. View My Pay History");
            System.out.println("4. Logout");

            int choice = getIntInput();

            switch (choice) {
                case 1:
                    System.out.print("Enter Employee ID: ");
                    int id = getIntInput();
                    Employee emp = search.searchByEmpID(id);
                    System.out.println(emp);
                    break;

                case 2:
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    List<Employee> list = search.searchByName(name);
                    list.forEach(System.out::println);
                    break;

                case 3:
                    System.out.println("\n--- Pay History ---");
                    reports.getMyPayHistory().forEach(System.out::println);
                    break;

                case 4:
                    auth.logout();
                    return;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    // HR admin menu
    private void hrMenu() {
        while (auth.isLoggedIn()) {
            System.out.println("\n--- HR Admin Menu ---");
            System.out.println("1. Search Employee");
            System.out.println("2. Add Employee");
            System.out.println("3. Update Employee");
            System.out.println("4. Delete Employee");
            System.out.println("5. View Any Pay History");
            System.out.println("6. Logout");

            int choice = getIntInput();

            switch (choice) {

                case 1:
                    System.out.print("Enter name: ");
                    String name = sc.nextLine();
                    List<Employee> results = search.searchByName(name);
                    results.forEach(System.out::println);
                    break;

                case 2:
                    Employee newEmp = createEmployeeInput();
                    empMgmt.addEmployee(newEmp);
                    break;

                case 3:
                    System.out.print("Enter Employee ID to update: ");
                    int updateID = getIntInput();
                    Employee updatedEmp = createEmployeeInput();
                    empMgmt.updateEmployee(updateID, updatedEmp);
                    break;

                case 4:
                    System.out.print("Enter Employee ID to delete: ");
                    int deleteID = getIntInput();
                    empMgmt.deleteEmployee(deleteID);
                    break;

                case 5:
                    System.out.print("Enter Employee ID: ");
                    int empID = getIntInput();
                    reports.getPayHistory(empID).forEach(System.out::println);
                    break;

                case 6:
                    auth.logout();
                    return;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    // HELPER: Create Employee
    private Employee createEmployeeInput() {

        System.out.print("First Name: ");
        String fname = sc.nextLine();

        System.out.print("Last Name: ");
        String lname = sc.nextLine();

        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Salary: ");
        double salary = getDoubleInput();

        System.out.print("Address ID: ");
        int addressID = getIntInput();

        System.out.print("SSN: ");
        String ssn = sc.nextLine();

        System.out.print("Username: ");
        String username = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        return new Employee(
            0,
            addressID,
            fname,
            lname,
            email,
            new java.util.Date(),
            ssn,
            salary,
            username,
            password
        );
    }

    // Input Helpers
    private int getIntInput() {
        while (true) {
            try {
                int value = Integer.parseInt(sc.nextLine());
                return value;
            } catch (Exception e) {
                System.out.print("Enter a valid number: ");
            }
        }
    }

    private double getDoubleInput() {
        while (true) {
            try {
                double value = Double.parseDouble(sc.nextLine());
                return value;
            } catch (Exception e) {
                System.out.print("Enter a valid number: ");
            }
        }
    }
}
