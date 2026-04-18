# Programming Tasks

## Read before starting

It's a good idea to use a config.properties file to use for database connection. This would contain your database url, username, and password. I added a .gitignore file that would help you ignore this when pushing changes to the repository. Example

config.properties
db.url=YOURurlHERE
db.user=YOURUSERNAME
db.password=YOURPASSWORD


For me I will be using the employeedata table we created from our labs as it has all required primary/foreign key connections. Now not sure about whether or not it contains sample data. Yea it doesn’t so we will need to add that. Read the message regarding the use of an online database or stick to using the databases of our computers. If we do the former, we will only need to upload sample data once. If we do the latter each person has to make sure to upload sample data using the employeedata database from our labs (not 2 or 3 unless you want to reapply foregn/primary key connections). 


## File Structure

<!-- 

Tree structure for Programmer view 

src/
    controller/
        AuthenticationController.java
        SearchController.java
        EmployeeManagementController.java
        ReportGenerator.java

    model/
        User.java
        Employee.java
    
    dao/
        DataAccessLayer.java
    
    view/
        UserInterface.java
        GUI.java

    main.java

lib/ (for mysql-connector-j-9.1.0.jar )

database/
    schema.sql

ProgrammingTasks.md
README.md

-->

<!-- Tree structure for viewer on github -->
| Directory/File                                                                      |
| ----------------------------------------------------------------------------------- |
| `src/`                                                                              |
| &nbsp;&nbsp;&nbsp;&nbsp;`controller/`                                               |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`AuthenticationController.java`     |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`SearchController.java`             |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`EmployeeManagementController.java` |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`ReportGenerator.java`              |
| &nbsp;&nbsp;&nbsp;&nbsp;`model/`                                                    |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`User.java`                         |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`Employee.java`                     |
| &nbsp;&nbsp;&nbsp;&nbsp;`dao/`                                                      |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`DataAccessLayer.java`              |
| &nbsp;&nbsp;&nbsp;&nbsp;`view/`                                                     |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`UserInterface.java`                |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`GUI.java`                          |
| &nbsp;&nbsp;&nbsp;&nbsp;`main.java`                                                 |
| `lib/` (for mysql-connector-j-9.1.0.jar)                                            |
| `database/`                                                                         |
| &nbsp;&nbsp;&nbsp;&nbsp;`schema.sql`                                                |
| `ProgrammingTasks.md`                                                               |
| `README.md`                                                                         |



## Dependency Map
<!-- 
Employee.java (no dependencies)
    ↓
User.java (no dependencies )
    ↓
DataAccessLayer.java (depends on Employee.java)
    ↓
AuthenticationController.java (depends on DataAccessLayer, User.java)
    ↓
SearchController.java (depends on AuthenticationController, DataAccessLayer)
    ↓
EmployeeManagementController.java (depends on AuthenticationController, DataAccessLayer)
    ↓
ReportGenerator.java (depends on AuthenticationController, DataAccessLayer)
    ↓
UserInterface.java (depends on ALL controllers)
    ↓
Main.java (depends on UserInterface)

-->

| Component                         | Depends On                                |
| --------------------------------- | ----------------------------------------- |
| Employee.java                     | (no dependencies)                         |
| User.java                         | (no dependencies)                         |
| DataAccessLayer.java              | Employee.java                             |
| AuthenticationController.java     | DataAccessLayer, User.java                |
| SearchController.java             | AuthenticationController, DataAccessLayer |
| EmployeeManagementController.java | AuthenticationController, DataAccessLayer |
| ReportGenerator.java              | AuthenticationController, DataAccessLayer |
| UserInterface.java                | ALL controllers                           |
| Main.java                         | UserInterface                             |


## File by File tasks


### Due April 18th

Employee.java
- Create class with private fields matching database columns (Refer to assignment doc and past labs sql diagrams)
- Add constructor, getters, and setters
- Add toString() method for easy printing

employees 
empID (PK), Fname, Lname, email, HireDate, Salary, SSN, addressID



User.java
- Create class with fields for logged-in user
- Choose approach: String or inner enum for role

Option A (String):

private int empID;
private String username;
private String role;  // "HR_ADMIN" or "GENERAL_EMPLOYEE"

Option B (Inner enum):

public enum Role { HR_ADMIN, GENERAL_EMPLOYEE }
private int empID;
private String username;
private Role role;


### Due April 19th

DataAccessLayer.java
- Dependencies: Employee.java
- Establish MySQL connection
- Implement CRUD operations
  - findEmployeeBy methods using DOB, SSN, empid editable for HR
  - findEmployeeBy methods using DOB, SSN, empid viewable only for general employee
  - Add AUTO_INCREMENT to empID and payID
  - If you’re using employeedata it should contain necessary foreign key and primary key connections. 
  - Add login credentials to employees if needed, email for username, and a simple password
  - Add sample data for testing.

Methods to implement (Create or look to see from past labs if they are already created). Add on as needed.

| Method                                                             | Description                                                |
| ------------------------------------------------------------------ | ---------------------------------------------------------- |
| `connect()`                                                        | Open database connection                                   |
| `disconnect()`                                                     | Close connection                                           |
| `findEmployeeById(int empID)`                                      | Search by empID → returns Employee                         |
| `findEmployeeByName(String name)`                                  | Search by name → returns List\<Employee\>                  |
| `insertEmployee(Employee emp)`                                     | Add new employee                                           |
| `updateEmployee(int empID, Employee data)`                         | Update employee                                            |
| `deleteEmployee(int empID)`                                        | Delete employee                                            |
| `updateSalaryByPercentage(double percent, double min, double max)` | Salary update. Related to functionality 5.                 |
| `getPayHistory(int empID)`                                         | Get pay stubs for employee sorted by most recent pay date. |
| `getNewHiresByDateRange(Date start, Date end)`                     | New hires report                                           |
| `getMonthlyTotalPayByTitle(int job_title_id, int month, int year)` | Total pay for month by job title. HR Admin only            |
| `getMonthlyTotalPayByDivision(int div_ID, int month, int year)`    | Total pay for month by Division. HR Admin only             |



### Due April 20th

AuthenticationController.java

- Dependencies: DataAccessLayer.java, User.java
- Store Current logged-in user (session)
- Implement the following methods

| Method                                    | Purpose                                                 |
| ----------------------------------------- | ------------------------------------------------------- |
| `login(String username, String password)` | Validate credentials, create User object, store session |
| `logout()`                                | Clear current user                                      |
| `getCurrentUser()`                        | Return current User object                              |
| `getCurrentUserRole()`                    | Return role as String (or Role enum)                    |
| `isLoggedIn()`                            | Check if user is authenticated                          |
| `isHRAdmin()`                             | Convenience method: returns true if role is HR_ADMIN    |



### Due April 21-22

SearchController.java
- Dependencies: AuthenticationController.java, DataAccessLayer.java
- Important! After getting results, check user role to return different results based on the role. Consider limiting data (no sensitive information) for general employe.
- Implement search methods, add on as needed:

| Method                      | Purpose                                                               |
| --------------------------- | --------------------------------------------------------------------- |
| `searchByEmpID(int empID)`  | Search by empID → returns Employee or null                            |
| `searchByName(String name)` | Search by name → returns List\<Employee\> related to functionality 4. |
| `searchByDOB(Date dob)`     | Search by date of birth                                               |
| `searchBySSN(String ssn)`   | Search by SSN                                                         |


EmployeeManagementController.java 
- Dependencies: AuthenticationController.java, DataAccessLayer.java
- Implement following CRUD operations:
- Every method must check isHRAdmin() before proceeding

| Method                                        | Purpose                                         |
| --------------------------------------------- | ----------------------------------------------- |
| `addEmployee(Employee emp)`                   | Insert new employee (check HR Admin role first) |
| `updateEmployee(int empID, Employee newData)` | Update existing employee                        |
| `deleteEmployee(int empID)`                   | Delete employee (with confirmation flow)        |


ReportGenerator.java
- Dependencies: AuthenticationController.java, DataAccessLayer.java
- Implement the following report methods:

| Method                                         | Purpose                                            |
| ---------------------------------------------- | -------------------------------------------------- |
| `getMyPayHistory()`                            | For general employee – uses logged-in user's empID |
| `getPayHistory(int empID)`                     | For HR Admin – get any employee's pay history      |
| `getTotalPayByJobTitle(int month, int year)`   | Sum of pay by job title for given month            |
| `getTotalPayByDivision(int month, int year)`   | Sum of pay by division for given month             |
| `getNewHiresByDateRange(Date start, Date end)` | List employees hired between dates                 |


### Due April 23-24

UserInterface.java
- Dependencies: All controllers (AuthenticationController.java, SearchController.java, EmployeeManagementController.java, ReportGenerator.java)
- Create Console menu system
  - Login/Exit
  - General Employee Menu
  - HR Admin Menu
- Methods to implement:


### Due April 25th

Main.java
Dependencies: UserInterface.java

### Optional Only If there is time:

GUI.java 
- Dependencies: All controller (same as UserInterface)
- You can only use Swing or JavaFx to help with this as specified in project instructions, no micro-frameworks that includes HTML, java script, Node.js, React, or Spring Boot.
- Same functionality as UserInterface but with windows/buttons
- Can reuse all controller classes, only the view changes

