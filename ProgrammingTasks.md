**File Structure**

src/
    controller/
        AuthenticationController.java
│   │   SearchController.java
│   │   EmployeeManagementController.java
│   │   ReportGenerator.java

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



File by File tasks


**Dependency Map**

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


**Due April 18th**

Employee.java
Create class with private fields matching database columns (Refer to assignment doc and past labs sql diagrams)
Add constructor, getters, and setters
Add toString() method for easy printing

User.java
Create class with fields for logged-in user
Choose approach: String or inner enum for role

Option A (String):

private int empID;
private String username;
private String role;  // "HR_ADMIN" or "GENERAL_EMPLOYEE"

Option B (Inner enum):

public enum Role { HR_ADMIN, GENERAL_EMPLOYEE }
private int empID;
private String username;
private Role role;


**Due April 19th**

DataAccessLayer.java
Dependencies: Employee.java
Establish MySQL connection
Implement CRUD operations

Methods to implement (Create or look to see from past labs if they are already created)

**Due April 20th**

AuthenticationController.java

DependenciesL DataAccessLayer.java, User.java
Store Current logged-in user (session)
Implement the following methods


**Due April 21-22**

SearchController.java
Dependencies: AuthenticationController.java, DataAccessLayer.java
Implement search methods:

EmployeeManagementController.java 
Dependencies: AuthenticationController.java, DataAccessLayer.java
Implement following CRUD operations:
Every method must check isHRAdmin() before proceeding

ReportGenerator.java
Dependencies: AuthenticationController.java, DataAccessLayer.java
Implement the following report methods:


**Due April 23-24**

UserInterface.java
Dependencies: All controllers (AuthenticationController.java, SearchController.java, EmployeeManagementController.java, ReportGenerator.java)
Create Console menu system
Login/Exit
General Employee Menu
HR Admin Menu
Methods to implement:

**Due April 25th**

Main.java
Dependencies: UserInterface.java

**Optional Only If there is time:**

GUI.java 
Dependencies: All controller (same as UserInterface)
You could use Swing or JavaFx to help with this
Same functionality as UserInterface but with windows/buttons
Can reuse all controller classes, only the view changes
