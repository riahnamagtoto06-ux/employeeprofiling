package SchoolEmployeeProfilingSystem;

import java.util.ArrayList;

public class DataStorageManager {

    // In-Memory Data Tables replacing SQL Tables
    private static ArrayList<User> users = new ArrayList<>();
    private static ArrayList<Employee> employees = new ArrayList<>();
    private static ArrayList<ContactDetailRecord> contactDetails = new ArrayList<>();
    private static ArrayList<EmergencyContactRecord> emergencyContacts = new ArrayList<>();
    private static ArrayList<GovernmentRecordRecord> governmentRecords = new ArrayList<>();

     static {
        // 1. Pre-stored Faculty Member (ID: 101)
        employees.add(new Faculty(
            "EMP001", "Cynthia", "Robles", "Female", "35", 
            "09171234567", "Bulacan, Philippines", "Permanent", "Full-Time", 
            "5", "F", "Assistant Professor", "College of Science"
        ));

        // 2. Pre-stored Non-Teaching Personnel (ID: 102)
        employees.add(new NonTeachingPersonnel(
            "EMP002", "Juan", "Dela Cruz", "Male", "40", 
            "09187654321", "Bulacan, Philippines", "Permanent", "Full-Time", 
            "10", "NTP", "HRDO", "Administrative Assistant"
        ));

        // Default system accounts available immediately when the program starts
        users.add(new HRDOHead(
            "HRDO-HEAD-001", "hr_head", "head123", "H", "HRDO Office"
        ));

        users.add(new HRDOSecretary(
            "HRDO-SEC-001", "hr_secretary", "secretary123", "S", "HRDO Front Desk"
        ));
    }
    
    // Secretary Approval Queue structures preserved precisely from original code
    private static final String[][] approvalQueue = new String[5][4];
    private static int requestCount = 0;
    private static final String[][] insertDataQueue = new String[5][23];

    // Local structural wrappers to safely cache relational entities inside arrays
    private static class ContactDetailRecord {
        String contactID, empID, mobile, landline, email;
        ContactDetailRecord(String contactID, String empID, String mobile, String landline, String email) {
            this.contactID = contactID; this.empID = empID; 
            this.mobile = mobile; this.landline = landline; this.email = email;
        }
    }

    private static class EmergencyContactRecord {
        String emergencyID, empID, name, relationship, phone;
        EmergencyContactRecord(String emergencyID, String empID, String name, String relationship, String phone) {
            this.emergencyID = emergencyID; this.empID = empID; 
            this.name = name; this.relationship = relationship; this.phone = phone;
        }
    }

    private static class GovernmentRecordRecord {
        String govID, empID, tin, sss, philhealth, pagibig;
        GovernmentRecordRecord(String govID, String empID, String tin, String sss, String philhealth, String pagibig) {
            this.govID = govID; this.empID = empID; this.tin = tin; 
            this.sss = sss; this.philhealth = philhealth; this.pagibig = pagibig;
        }
    }

    // =========================================================================
    // USER ACCOUNT MANAGEMENT METHODS
    // =========================================================================

    // Saves a new username and password to the user login arraylist
    public static void insertUser(User user) {
        users.add(user);
        System.out.println("----------------------------------------");
        System.out.println(" Success! User '" + user.getUsername() + "' was added.");
    }

    // Saves the specific office location for an HRDO Head account
    public static void insertHead(HRDOHead head) {
        // Stored seamlessly as a polymorphism target inside the users array list
        System.out.println(" Success! HRDO Head was added.");
        System.out.println("----------------------------------------");
    }

    // Saves the specific desk location for an HRDO Secretary account
    public static void insertSecretary(HRDOSecretary secretary) {
        // Tracked uniformly inside our central core storage lists
        System.out.println(" Success! HRDO Secretary was added.");
        System.out.println("----------------------------------------");
    }

    // Checks if the entered username and password match a registered HRDO Head account
    public static HRDOHead authenticateHead(String username, String password) {
        for (User u : users) {
            if (u instanceof HRDOHead && u.getUsername().equals(username) && u.getPassword().equals(password)) {
                return (HRDOHead) u;
            }
        }
        return null;
    }

    // Checks if the entered username and password match a registered HRDO Secretary account
    public static HRDOSecretary authenticateSecretary(String username, String password) {
        for (User u : users) {
            if (u instanceof HRDOSecretary && u.getUsername().equals(username) && u.getPassword().equals(password)) {
                return (HRDOSecretary) u;
            }
        }
        return null;
    }
    // =========================================================================
    // EMPLOYEE DATA INSERTION AND STORAGE METHODS
    // =========================================================================

    // Saves basic personal information of the employee into memory
    public static void insertEmployee(Employee employee) {
        employees.add(employee);
        System.out.println("----------------------------------------");
        System.out.println(" Success! Employee \"" + employee.getLastName() + "\" was inserted.");
        System.out.println("----------------------------------------");
    }

    // Saves faculty details like assigned units, teaching hours, classes, and rank
    public static void insertFaculty(Faculty faculty) {
        // Handled cleanly via polymorphism or shared array matching logic.
        // If your model separates Faculty from Employee arrays natively, you can do:
        // employees.add(faculty);
        System.out.println("----------------------------------------");
        System.out.println(" Success! Faculty record was inserted.");
        System.out.println("----------------------------------------");
    }

    // Saves office assignment and designation for non-teaching personnel
    public static void insertNonTeachingPersonnel(NonTeachingPersonnel ntp) {
        // Keeps tracks of specialized entities within our structural memory arrays
        System.out.println("----------------------------------------");
        System.out.println(" Success! Non-Teaching Personnel record was inserted.");
        System.out.println("----------------------------------------");
    }

    // Saves an employee's mobile number, landline number, and email address
    public static void insertContactDetails(String empID, String mobile, String landline, String email) {
        String contactID = "CD-" + empID;
        // Clean out any stale entries for this employee first to maintain a 1-to-1 relationship mapping
        contactDetails.removeIf(cd -> cd.empID.equals(empID));
        contactDetails.add(new ContactDetailRecord(contactID, empID, mobile, landline, email));
    }

    // Saves the name, relationship, and phone number of an emergency contact
    public static void insertEmergencyContact(String empID, String name, String relationship, String phone) {
        String emergencyID = "EC-" + empID;
        emergencyContacts.removeIf(ec -> ec.empID.equals(empID));
        emergencyContacts.add(new EmergencyContactRecord(emergencyID, empID, name, relationship, phone));
    }

    // Saves government identity numbers like TIN, SSS, PhilHealth, and Pag-Ibig
    public static void insertGovernmentRecord(String empID, String tin, String sss, String philhealth, String pagibig) {
        String govID = "GR-" + empID;
        governmentRecords.removeIf(gr -> gr.empID.equals(empID));
        governmentRecords.add(new GovernmentRecordRecord(govID, empID, tin, sss, philhealth, pagibig));
    }

    // =========================================================================
    // VALIDATION AND VERIFICATION UTILITIES
    // =========================================================================

    // Checks the in-memory collections to see if an Employee ID is already being used
    public static boolean employeeExists(String employeeID) {
        for (Employee e : employees) {
            if (e.getEmployeeID().equals(employeeID)) {
                return true;
            }
        }
        return false;
    }

    // Verifies if an Employee ID exists and is allowed to sign up for a specific system role
    public static String[] verifyEmployeeForSignup(String employeeID, String targetRole) {
        Employee foundEmp = null;
        for (Employee e : employees) {
            if (e.getEmployeeID().equals(employeeID)) {
                foundEmp = e;
                break;
            }
        }

        if (foundEmp == null) {
            return new String[]{"NOT FOUND", "", "", ""};
        }

        String firstName = foundEmp.getFirstName();
        String lastName = foundEmp.getLastName();
        String empType = foundEmp.getEmployeeType();

        // Check if user already has an active account registration token matching their ID
        for (User u : users) {
            if (u.getUserID().equals(employeeID)) {
                return new String[]{"ALREADY HAS ACCOUNT", firstName, lastName, empType};
            }
        }

        // Validate that the system role is not already claimed
        for (User u : users) {
            if (u.getUserType().equals(targetRole)) {
                return new String[]{"ROLE TAKEN", firstName, lastName, empType};
            }
        }

        return new String[]{"VALID", firstName, lastName, empType};
    }
    public static Employee getEmployeeByID(String employeeID) {
        for (Employee e : employees) {
            if (e.getEmployeeID().equalsIgnoreCase(employeeID)) return e;
        }
        return null;
    }

    public static void printAllEmployees() {
        System.out.println("\n+--------------------------------------------------------------------------------+");
        System.out.printf("| %-10s | %-25s | %-16s | %-14s | %-10s |%n",
                "EMP ID", "EMPLOYEE NAME", "STATUS", "YEARS SERVICE", "LOAD");
        System.out.println("+--------------------------------------------------------------------------------+");
        if (employees.isEmpty()) System.out.printf("| %-78s |%n", "No employee records found.");
        for (Employee e : employees) {
            String name = e.getLastName() + ", " + e.getFirstName();
            System.out.printf("| %-10s | %-25s | %-16s | %-14s | %-10s |%n",
                    e.getEmployeeID(), name, e.getEmploymentStatus(), e.getYearsOfService(), e.getEmploymentLoad());
        }
        System.out.println("+--------------------------------------------------------------------------------+");
    }

    public static void printEmployeeDetails(String employeeID) {
        Employee e = getEmployeeByID(employeeID);
        if (e == null) {
            System.out.println("No employee record found for ID: " + employeeID);
            return;
        }
        System.out.println("\n========================================");
        System.out.println("          EMPLOYEE RECORD DETAILS       ");
        System.out.println("========================================");
        System.out.println("Employee ID       : " + e.getEmployeeID());
        System.out.println("First Name        : " + e.getFirstName());
        System.out.println("Last Name         : " + e.getLastName());
        System.out.println("Gender            : " + e.getGender());
        System.out.println("Age               : " + e.getAge());
        System.out.println("Contact Number    : " + e.getContactNumber());
        System.out.println("Address           : " + e.getAddress());
        System.out.println("Employment Status : " + e.getEmploymentStatus());
        System.out.println("Employment Load   : " + e.getEmploymentLoad());
        System.out.println("Years of Service  : " + e.getYearsOfService());
        System.out.println("Employee Type     : " + e.getEmployeeType());
        for (ContactDetailRecord cd : contactDetails) if (cd.empID.equalsIgnoreCase(employeeID)) {
            System.out.println("Mobile Number     : " + cd.mobile);
            System.out.println("Landline Number   : " + cd.landline);
            System.out.println("Email Address     : " + cd.email);
        }
        for (EmergencyContactRecord ec : emergencyContacts) if (ec.empID.equalsIgnoreCase(employeeID)) {
            System.out.println("Emergency Contact : " + ec.name);
            System.out.println("Relationship      : " + ec.relationship);
            System.out.println("Emergency Phone   : " + ec.phone);
        }
        for (GovernmentRecordRecord gr : governmentRecords) if (gr.empID.equalsIgnoreCase(employeeID)) {
            System.out.println("TIN               : " + gr.tin);
            System.out.println("SSS               : " + gr.sss);
            System.out.println("PhilHealth        : " + gr.philhealth);
            System.out.println("Pag-Ibig           : " + gr.pagibig);
        }
        System.out.println("========================================");
    }

    public static void searchEmployees(String mode, String value) {
        boolean found = false;
        for (Employee e : employees) {
            boolean match = switch (mode.toLowerCase()) {
                case "id" -> e.getEmployeeID().equalsIgnoreCase(value);
                case "surname" -> e.getLastName().toLowerCase().contains(value.toLowerCase());
                case "status" -> e.getEmploymentStatus().equalsIgnoreCase(value);
                case "years" -> e.getYearsOfService().equalsIgnoreCase(value);
                default -> false;
            };
            if (match) {
                found = true;
                printEmployeeDetails(e.getEmployeeID());
            }
        }
        if (!found) System.out.println("No employees matched your search.");
    }

    public static boolean updateSupplementaryField(String employeeID, String category, String field, String value) {
        if (category.equals("2")) {
            for (ContactDetailRecord cd : contactDetails) if (cd.empID.equalsIgnoreCase(employeeID)) {
                switch (field) {
                    case "1" -> cd.mobile = value;
                    case "2" -> cd.landline = value;
                    case "3" -> cd.email = value;
                    default -> { return false; }
                }
                return true;
            }
        } else if (category.equals("3")) {
            for (EmergencyContactRecord ec : emergencyContacts) if (ec.empID.equalsIgnoreCase(employeeID)) {
                switch (field) {
                    case "1" -> ec.name = value;
                    case "2" -> ec.relationship = value;
                    case "3" -> ec.phone = value;
                    default -> { return false; }
                }
                return true;
            }
        } else if (category.equals("4")) {
            for (GovernmentRecordRecord gr : governmentRecords) if (gr.empID.equalsIgnoreCase(employeeID)) {
                switch (field) {
                    case "1" -> gr.tin = value;
                    case "2" -> gr.sss = value;
                    case "3" -> gr.philhealth = value;
                    case "4" -> gr.pagibig = value;
                    default -> { return false; }
                }
                return true;
            }
        }
        return false;
    }

    // =========================================================================
    // SEARCH, SELECTION, AND RECORD REMOVAL UTILITIES
    // =========================================================================

    // Searches for a specific Faculty member and prints out their basic record
    public static boolean searchFaculty(String employeeID) {
        for (Employee e : employees) {
            if (e.getEmployeeType().equalsIgnoreCase("F") && e.getEmployeeID().equals(employeeID)) {
                System.out.println("\n========================================");
                System.out.println("            FACULTY RECORD FOUND        ");
                System.out.println("========================================");
                System.out.println(" ID Number  : " + e.getEmployeeID());
                System.out.println(" Full Name  : " + e.getFirstName() + " " + e.getLastName());
                System.out.println(" Load Type  : " + e.getEmploymentLoad());
                System.out.println(" Status     : " + e.getEmploymentStatus());
                System.out.println("========================================");
                return true;
            }
        }
        return false;
    }

    // Searches for a specific Non-Teaching Personnel and prints out their basic record
    public static boolean searchNonTeachingPersonnel(String employeeID) {
        for (Employee e : employees) {
            if (e.getEmployeeType().equalsIgnoreCase("NTP") && e.getEmployeeID().equals(employeeID)) {
                System.out.println("\n========================================");
                System.out.println("       NON-TEACHING RECORD FOUND        ");
                System.out.println("========================================");
                System.out.println(" ID Number  : " + e.getEmployeeID());
                System.out.println(" Full Name  : " + e.getFirstName() + " " + e.getLastName());
                System.out.println(" Load Type  : " + e.getEmploymentLoad());
                System.out.println(" Status     : " + e.getEmploymentStatus());
                System.out.println("========================================");
                return true;
            }
        }
        return false;
    }

    // Removes a Faculty record and cascades deletions down across all supplementary tables
    public static void deleteFaculty(String employeeID) {
        employees.removeIf(e -> e.getEmployeeID().equals(employeeID) && e.getEmployeeType().equalsIgnoreCase("F"));
        contactDetails.removeIf(cd -> cd.empID.equals(employeeID));
        emergencyContacts.removeIf(ec -> ec.empID.equals(employeeID));
        governmentRecords.removeIf(gr -> gr.empID.equals(employeeID));
        System.out.println("----------------------------------------");
        System.out.println(" Faculty record and matching context files deleted successfully.");
        System.out.println("----------------------------------------");
    }

    // Removes a Non-Teaching staff profile and cascades deletions down across all supplementary tables
    public static void deleteNonTeachingPersonnel(String employeeID) {
        employees.removeIf(e -> e.getEmployeeID().equals(employeeID) && e.getEmployeeType().equalsIgnoreCase("NTP"));
        contactDetails.removeIf(cd -> cd.empID.equals(employeeID));
        emergencyContacts.removeIf(ec -> ec.empID.equals(employeeID));
        governmentRecords.removeIf(gr -> gr.empID.equals(employeeID));
        System.out.println("----------------------------------------");
        System.out.println(" Non-Teaching employee record and context files deleted successfully.");
        System.out.println("----------------------------------------");
    }

    // Dynamic field update routing algorithm for individual profile modifications
    public static boolean updateEmployee(String employeeTypeCode, String employeeID, String choice, String newValue) {
        for (Employee e : employees) {
            if (e.getEmployeeID().equals(employeeID) && e.getEmployeeType().equalsIgnoreCase(employeeTypeCode)) {
                switch (choice) {
                    case "1": e.setFirstName(newValue); return true;
                    case "2": e.setLastName(newValue); return true;
                    case "3": e.setGender(newValue); return true;
                    case "4": e.setAge(newValue); return true;
                    case "5": e.setContactNumber(newValue); return true;
                    case "6": e.setAddress(newValue); return true;
                    case "7": e.setEmploymentStatus(newValue); return true;
                    case "8": e.setEmploymentLoad(newValue); return true;
                    case "9": e.setYearsOfService(newValue); return true;
                    default: return false;
                }
            }
        }
        return false;
    }

    // =========================================================================
    // METRICS AND STRATIFIED ANALYTICS COMPILER
    // =========================================================================

    // Aggregates full system tallies for Employment statuses and workloads
    public static int[] getSummaryMetrics() {
        int permanentCount = 0;
        int probationaryCount = 0;
        int contractualCount = 0;
        int fullTimeCount = 0;
        int partTimeCount = 0;

        for (Employee e : employees) {
            String status = e.getEmploymentStatus();
            String load = e.getEmploymentLoad();

            if (status != null) {
                if (status.equalsIgnoreCase("Permanent")) permanentCount++;
                else if (status.equalsIgnoreCase("Probationary")) probationaryCount++;
                else if (status.equalsIgnoreCase("Contractual")) contractualCount++;
            }

            if (load != null) {
                if (load.equalsIgnoreCase("Full-Time")) fullTimeCount++;
                else if (load.equalsIgnoreCase("Part-Time")) partTimeCount++;
            }
        }

        return new int[]{permanentCount, probationaryCount, contractualCount, fullTimeCount, partTimeCount};
    }
    // =========================================================================
    // ACCOUNT SETTINGS CONFIGURATION ACTIONS
    // =========================================================================

    // Updates a user's matching login username token in our local collection pool
    public static boolean updateUsername(String oldUsername, String newUsername) {
        for (User u : users) {
            if (u.getUsername().equals(oldUsername)) {
                u.setUsername(newUsername);
                return true;
            }
        }
        return false;
    }

    // Updates a user's security authorization password credential
    public static boolean updatePassword(String currentPassword, String newPassword) {
        for (User u : users) {
            if (u.getPassword().equals(currentPassword)) {
                u.setPassword(newPassword);
                return true;
            }
        }
        return false;
    }

    // Finds the Unique ID associated with an active username
    public static String getUserID(String username) {
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return u.getUserID();
            }
        }
        return null;
    }

    // Removes a matching user login profile sequence from the core memory storage
    public static boolean deleteUserAccount(String userID) {
        return users.removeIf(u -> u.getUserID().equals(userID));
    }

    // =========================================================================
    // CONDITIONAL RECORD RENDERING LOOPS
    // =========================================================================

    // Prints out formatted lines showing the calculated service years of targeted roles
    public static void viewYearsOfService(String employeeTypeCode) {
        System.out.println("\n------------------------------------------------");
        System.out.println("  ID NUMBER  |  EMPLOYEE NAME   |  YEARS OF SERVICE ");
        System.out.println("------------------------------------------------");
        for (Employee e : employees) {
            if (e.getEmployeeType().equalsIgnoreCase(employeeTypeCode)) {
                System.out.printf("  %-10s | %-16s | %s years\n", 
                    e.getEmployeeID(), (e.getLastName() + ", " + e.getFirstName()), e.getYearsOfService());
            }
        }
        System.out.println("------------------------------------------------");
    }

    // Prints out formatted columns showcasing current job classification statuses
    public static void viewEmploymentStatus(String employeeTypeCode) {
        System.out.println("\n------------------------------------------------");
        System.out.println("  ID NUMBER  |  EMPLOYEE NAME   |  EMPLOYMENT STATUS ");
        System.out.println("------------------------------------------------");
        for (Employee e : employees) {
            if (e.getEmployeeType().equalsIgnoreCase(employeeTypeCode)) {
                System.out.printf("  %-10s | %-16s | %s\n", 
                    e.getEmployeeID(), (e.getLastName() + ", " + e.getFirstName()), e.getEmploymentStatus());
            }
        }
        System.out.println("------------------------------------------------");
    }

    // Prints out formatted fields checking load assignments across matched records
    public static void viewEmploymentLoad(String employeeTypeCode) {
        System.out.println("\n------------------------------------------------");
        System.out.println("  ID NUMBER  |  EMPLOYEE NAME   |  EMPLOYMENT LOAD ");
        System.out.println("------------------------------------------------");
        for (Employee e : employees) {
            if (e.getEmployeeType().equalsIgnoreCase(employeeTypeCode)) {
                System.out.printf("  %-10s | %-16s | %s\n", 
                    e.getEmployeeID(), (e.getLastName() + ", " + e.getFirstName()), e.getEmploymentLoad());
            }
        }
        System.out.println("------------------------------------------------");
    }

    // =========================================================================
    // SECRETARY WORKFLOW / APPROVAL QUEUE SUBSYSTEM
    // =========================================================================

    // Inserts structured modifications into temporary string buffer slots (Update/Delete Actions)
    public static void insertPendingAction(String operationType, String employeeID, String employeeTypeCode) {
        if (requestCount < approvalQueue.length) {
            approvalQueue[requestCount][0] = "REQ-" + (requestCount + 1);
            approvalQueue[requestCount][1] = operationType;
            approvalQueue[requestCount][2] = employeeID;
            approvalQueue[requestCount][3] = employeeTypeCode;
            requestCount++;
        } else {
            System.out.println("⚠️ Storage Error: The workflow queue is currently full.");
        }
    }

    // Overloaded variant storing deep detail arrays for complex object creations (Insert Actions)
    public static void insertPendingAction(String operationType, String employeeID, String employeeTypeCode, String[] deepDetails) {
        if (requestCount < approvalQueue.length) {
            approvalQueue[requestCount][0] = "REQ-" + (requestCount + 1);
            approvalQueue[requestCount][1] = operationType;
            approvalQueue[requestCount][2] = employeeID;
            approvalQueue[requestCount][3] = employeeTypeCode;
            
            // Backup details into data cache matrices using matching track bounds
            for (int i = 0; i < deepDetails.length && i < insertDataQueue[requestCount].length; i++) {
                insertDataQueue[requestCount][i] = deepDetails[i];
            }
            requestCount++;
        } else {
            System.out.println("⚠️ Storage Error: The workflow data matrix layout is fully utilized.");
        }
    }

    // Returns the active total metrics volume currently inside the queue arrays
    public static int getRequestCount() {
        return requestCount;
    }

    // Exports references of the base queue matrices to map inside Main view layouts
    public static String[][] getPendingActions() {
        return approvalQueue;
    }

    // Recovers individual field parameters out of secondary cached parameter tracks
    public static String[] getInsertDetailsAt(int specificIndex) {
        return insertDataQueue[specificIndex];
    }

    // Shifts item index steps to cleanly pull items out of queue frameworks without leaving null gaps
    public static void removeRequestAt(int deletionTargetIndex) {
        if (deletionTargetIndex >= 0 && deletionTargetIndex < requestCount) {
            // Shift elements down across the approval metadata matrix
            for (int i = deletionTargetIndex; i < requestCount - 1; i++) {
                approvalQueue[i] = approvalQueue[i + 1];
                insertDataQueue[i] = insertDataQueue[i + 1];
            }
            
            // Reset trailing edge indicators safely to prevent ghost duplicates
            approvalQueue[requestCount - 1] = new String[4];
            insertDataQueue[requestCount - 1] = new String[23];
            requestCount--;
        }
    }
}