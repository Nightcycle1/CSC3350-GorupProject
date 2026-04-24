package model;

/**
 * User.java -
 * Data container for the currently logged in user. 
 * Created upon user login. Stores role for permission checks and login information. 
 * Stored in AuthenticationController as session.
 * 
*/

public class User {
    public enum Role {
        HR_ADMIN,
        GENERAL_EMPLOYEE
    }

    private int empID;
    private String username;
    private Role role;

    public User(int empID, String username, Role role) {
        this.empID = empID;
        this.username = username;
        this.role = role;
    }

    public int getEmpID() {
        return empID;
    }
    public String getUsername() {
        return username;
    }
    public Role getRole() {
        return role;
    }

    public String toString() {
        return empID + " " + username + " " + role;
    }
}
