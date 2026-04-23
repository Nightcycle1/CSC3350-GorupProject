package model;

import java.util.Date;
/**
 * Employe.java - 
 * Data container representing any employee record from the database. 
 * Created when DataAccessLayer reads from database. 
 * Stores employee information and provides getters/setters for access. 
 * Used by controllers and DataAccessLayer to pass employee data between layers.
 * 
 */
public class Employee {
    private int empID, addressID;
    private String fname, lname, email, SSN;
    private Date hireDate;
    private double salary;

    public Employee(int empID, int addressID, String fname, String lname, String email, Date hiredate, String SSN, double salary) {
        this.empID = empID;
        this.addressID = addressID;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.hireDate = hiredate;
        this.SSN = SSN;
        this.salary = salary;
    }

    public int getempID() {
        return empID;
    }
    public int getaddressID() {
        return addressID;
    }
    public String getfname() {
        return fname;
    }
    public String getlname() {
        return lname;
    }
    public String getemail() {
        return email;
    }
    public Date gethiredate() {
        return hireDate;
    }
    public String getSSN() {
        return SSN;
    }
    public double getsalary() {
        return salary;
    }

    public void setSSN(String SSN) { this.SSN = SSN; }

    public String toString() {
        return empID + "," + fname + "," + lname + "," + email + "," +
               hireDate + "," + salary + "," + SSN + "," + addressID;
    }
    
}
