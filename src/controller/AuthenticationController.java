package controller;

import dao.DataAccessLayer;
import model.User;

/**
 * AuthenticationController.java - 
 * Manages login, user sessions, and permission checks. 
 * The single source of truth for current user and role.
 * 
 */

public class AuthenticationController {

    private DataAccessLayer dal;
    private User currentUser;

    public AuthenticationController(DataAccessLayer dal) {
        this.dal = dal;
        this.currentUser = null;
    }


    /**
     * Validate user credentials and creates/stores session
     * @param username - The username 
     * @param password - The plain text password
     * @return - True if login successful, false otherwise
     */
    public boolean login(String username, String password) {

        User user = dal.validateLogin(username, password);

        if (user != null) {
            currentUser = user;
            System.out.println("Login successful. Welcome " + username);
            return true;
        }
        else {
            System.out.println("Login failed: Invalid username or password");
            return false;
        }

    }

    /**
     * Clears the current user session
     */
    public void logout() {

        if (currentUser != null) {
            System.out.println("Goodbye " + currentUser.getUsername());
            currentUser = null;
        }
        else {
            System.out.println("No user is currently logged in.");
        }

    }

    /**
     * Return current logged in user object
     * 
     * @return - User object or null if no session exists
     */
    public User getCurrentUser() {
        return currentUser;
    }
    
    /**
     * Return the role of the current user as a String
     * 
     * @return "HR_Admin", "GENERAL_EMPLOYEE", or null if not logged in
     */
    public String getCurrentUserRole() {
        if (currentUser == null) {
            return null;
        }
        return currentUser.getRole().toString();
    }

    /**
     * Check if user is currently logged in 
     * 
     * @return true if user is logged in, false other wise
     */
    public boolean isLoggedIn() {
        return currentUser != null;
    }

    /**
     * Convenience methods to check if current user is HR Admin
     * 
     * @return - true if user is HR Admin and logged in, false other wise
     */
    public boolean isHRAdmin() {
        return currentUser != null && currentUser.getRole() == User.Role.HR_ADMIN;
    }

    /**
     * Convenience method to check if curretn user is General Employee
     * 
     * @return true if user is General Employee and logged in false otherwise
     */
    public boolean isGeneralEmployee() {
        return currentUser != null && currentUser.getRole() == User.Role.GENERAL_EMPLOYEE;
    }

    /** 
     * IN FILE TESTING
     */
    public static void main(String[] args) {
        System.out.println("=== AuthenticationController Test ===\n");

        // Initialize DataAccessLayer and connect to database
        DataAccessLayer dal = new DataAccessLayer();
        dal.connect();

        // Initialize AuthenticationController
        AuthenticationController auth = new AuthenticationController(dal);

        // Test 1: No user logged in initially
        System.out.println("Test 1: Initial state");
        System.out.println("Is logged in? " + auth.isLoggedIn());
        System.out.println("Is HR Admin? " + auth.isHRAdmin());
        System.out.println("Current user: " + auth.getCurrentUser());
        System.out.println();

        // Test 2: Failed login attempt
        System.out.println("Test 2: Failed login");
        auth.login("wronguser@companyz.com", "wrongpassword");
        System.out.println("Is logged in? " + auth.isLoggedIn());
        System.out.println();

        // Test 3: Successful login with HR Admin (assuming empID 1 is admin)
        System.out.println("Test 3: Successful login (HR Admin)");
        boolean loginSuccess = auth.login("john.doe@companyz.com", "password123");
        if (loginSuccess) {
            System.out.println("User: " + auth.getCurrentUser().getUsername());
            System.out.println("Role: " + auth.getCurrentUserRole());
            System.out.println("Is HR Admin? " + auth.isHRAdmin());
            System.out.println("Is General Employee? " + auth.isGeneralEmployee());
        }
        System.out.println();

        // Test 4: Logout
        System.out.println("Test 4: Logout");
        auth.logout();
        System.out.println("Is logged in after logout? " + auth.isLoggedIn());
        System.out.println();

        // Test 5: Successful login with General Employee (empID 2)
        System.out.println("Test 5: Successful login (General Employee)");
        loginSuccess = auth.login("jane.smith@companyz.com", "password123");
        if (loginSuccess) {
            System.out.println("User: " + auth.getCurrentUser().getUsername());
            System.out.println("Role: " + auth.getCurrentUserRole());
            System.out.println("Is HR Admin? " + auth.isHRAdmin());
            System.out.println("Is General Employee? " + auth.isGeneralEmployee());
        }
        System.out.println();

        // Test 6: Role checking methods
        System.out.println("Test 6: Role checking methods");
        System.out.println("getCurrentUserRole(): " + auth.getCurrentUserRole());
        System.out.println("isHRAdmin(): " + auth.isHRAdmin());
        System.out.println("isGeneralEmployee(): " + auth.isGeneralEmployee());
        System.out.println();

        // Clean up
        auth.logout();
        dal.disconnect();
        
        System.out.println("=== Test Complete ===");
    }

}

