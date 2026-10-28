package SchoolEmployeeProfilingSystem;

import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        mainPage();
    } // END OF THE MAIN METHOD

    // Displays the opening main menu with options to Sign Up, Sign In / Log In, or Exit
    public static void mainPage() {
        String choice;
        do {
            System.out.println("================================================================================");
            System.out.println("                 SCHOOL EMPLOYEE INFORMATION MANAGEMENT SYSTEM                  ");
            System.out.println("================================================================================");
            System.out.println(" [1] Sign Up                                                                    ");
            System.out.println(" [2] Sign In / Log In                                                           ");
            System.out.println(" [X] Exit                                                                       ");
            System.out.println("================================================================================");
            System.out.print(" >>> Enter Choice: ");
            choice = sc.next();
            System.out.println("================================================================================");
            
            switch (choice) {
                case "1" -> signupPage();
                case "2" -> loginPage();
                case "X", "x" -> {
                    System.out.println(" || >.< || System terminating...");
                    System.out.println("================================================================================");
                    System.exit(0);
                }
                default -> System.out.println("  !! Error: Invalid choice. Please choose 1, 2, or X.");
            }
        } while (!choice.equalsIgnoreCase("X"));
    } // END OF THE MAIN PAGE METHOD

    // Handles admin (user) account generation, employee verification, and signing up
    public static void signupPage() {
        System.out.println(" || >.< || LOADING...");
        System.out.println("================================================================================");
        System.out.println("                              CREATE SYSTEM ACCOUNT                             ");
        System.out.println("================================================================================");
        System.out.println(" [1] HRDO Head Account                                                          ");
        System.out.println(" [2] HRDO Secretary Account                                                     ");
        System.out.println(" [X] Exit                                                                       ");
        System.out.println("================================================================================");
        System.out.print(" >>> Enter Choice: ");
        String choice = sc.next();
        
        if (choice.equalsIgnoreCase("x")) { return; }

        String targetRoleCode;
        String targetRoleName;
        
        switch (choice) {
            case "1" -> {
                targetRoleCode = "H";
                targetRoleName = "HRDO Head";
            }
            case "2" -> {
                targetRoleCode = "S";
                targetRoleName = "HRDO Secretary";
            }
            default -> {
                System.out.println("\n  !! Error: Invalid choice. Please choose 1, 2, or X.");
                return;
            }
        }
        System.out.println("================================================================================");
        System.out.print("  Enter Employee ID: ");
        String inputID = sc.next();
        
        // Diverts compilation patterns securely to our data storage manager
        String[] verification = DataStorageManager.verifyEmployeeForSignup(inputID, targetRoleCode);
        String status = verification[0];
        String firstName = verification[1];
        String lastName = verification[2];
        String empType = verification[3];

        switch (status) {
            case "NOT FOUND" -> {
                System.out.println("\n  !! Error: Employee ID not found in the school masterlist.");
                return;
            }
            case "ALREADY HAS ACCOUNT" -> {
                System.out.println("\n  !! Error: You are already a registered user.");
                return;
            }
            case "ROLE TAKEN" -> {
                System.out.println("\n  !! Error: The " + targetRoleName + " account has already been registered.");
                System.out.println("--------------------------------------------------------------------------------");
                System.out.println(" NOTE: Only one registered account is permitted for this role.");
                return;
            }
            case "VALID" -> {
                String employeeType = empType.equalsIgnoreCase("F") ? "Faculty" : "Non-Teaching Personnel";
                System.out.println("\n Employee Verified: " + firstName + " " + lastName + " [" + employeeType + "]");
                System.out.println("================================================================================");
            }
            default -> {
                System.out.println("\n  !! Error: Verification failed.");
                return;
            }
        }
        System.out.print(" Create Username: ");
        String inputUser = sc.next();
        System.out.print(" Create Password: ");
        String inputPass = sc.next();
        
        System.out.println("================================================================================");

        if (targetRoleCode.equals("H")) {
            System.out.print("  Enter Office Location: ");
            sc.nextLine(); // Clear scanner buffer
            String office = sc.nextLine();
            
            // Fixed: Now passing all 5 parameters to match the constructor precisely
            HRDOHead head = new HRDOHead(inputID, inputUser, inputPass, "H", office);
            DataStorageManager.insertUser(head);
            DataStorageManager.insertHead(head);
            System.out.println("\n  || ^_^ || Head Account Created Successfully!");
            
            // Redirect straight to dashboard
            hrdoHeadDashboard(inputUser, inputPass);
        } else {
            System.out.print("  Enter Desk Location: ");
            sc.nextLine(); // Clear scanner buffer
            String desk = sc.nextLine();
            
            // Fixed: Now passing all 5 parameters to match the constructor precisely
            HRDOSecretary secretary = new HRDOSecretary(inputID, inputUser, inputPass, "S", desk);
            DataStorageManager.insertUser(secretary);
            DataStorageManager.insertSecretary(secretary);
            System.out.println("\n  || ^_^ || Secretary Account Created Successfully!");
            
            // Redirect straight to dashboard
            hrdoSecretaryDashboard(inputUser, inputPass);
        }
    } // END OF THE SIGN UP PAGE METHOD
    
    // Handles authentication routing to direct the user to their corresponding dashboards
    public static void loginPage() {
        System.out.println(" || ^_^ || LOADING...");
        System.out.println("================================================================================");
        System.out.println("                              LOG IN TO YOUR ACCOUNT                            ");
        System.out.println("================================================================================");
        System.out.println(" [1] HRDO Head Account");
        System.out.println(" [2] HRDO Secretary Account");
        System.out.println(" [X] Back to Main Page");
        System.out.print(" >>> Enter Choice: ");
        String choice = sc.next();

        if (choice.equalsIgnoreCase("X")) return;
        if (!choice.equals("1") && !choice.equals("2")) {
            System.out.println("Invalid choice. Returning to main page.");
            return;
        }

        final int maxAttempts = 3;
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            System.out.print(" Enter Username: ");
            String inputUser = sc.next();
            System.out.print(" Enter Password: ");
            String inputPass = sc.next();

            if (choice.equals("1")) {
                HRDOHead head = DataStorageManager.authenticateHead(inputUser, inputPass);
                if (head != null) {
                    System.out.println("Access granted!");
                    hrdoHeadDashboard(inputUser, inputPass);
                    return;
                }
            } else {
                HRDOSecretary secretary = DataStorageManager.authenticateSecretary(inputUser, inputPass);
                if (secretary != null) {
                    System.out.println("Access granted!");
                    hrdoSecretaryDashboard(inputUser, inputPass);
                    return;
                }
            }

            int remaining = maxAttempts - attempt;
            System.out.println("Incorrect username or password.");
            if (remaining > 0) System.out.println("Attempts remaining: " + remaining);
        }

        System.out.println("Access denied. Returning to the main page.");
        return;
    } // END OF LOGIN PAGE METHOD

    // System management configuration options to change passwords or delete accounts
    public static void accountSettings(String user, String pass, String roleCode) {
        String choice;
        do {
            System.out.println("================================================================================");
            System.out.println("                                ACCOUNT SETTINGS                                ");
            System.out.println("================================================================================");
            System.out.println(" [1] Change Username                                                            ");
            System.out.println(" [2] Change Password                                                            ");
            System.out.println(" [3] Delete Account                                                            ");
            System.out.println(" [X] Go Back                                                                    ");
            System.out.println("================================================================================");
            System.out.print(" >>> Enter Choice: ");
            choice = sc.next();

            switch (choice) {
                case "1" -> {
                    System.out.println("--------------------------------------------------------------------------------");
                    System.out.print("  Enter current username: ");
                    String inputUser = sc.next();
                    
                    if(inputUser.equals(user)){
                        System.out.print("  Enter new username: ");
                        String newUsername = sc.next();
                        if (DataStorageManager.updateUsername(user, newUsername)) {
                        user = newUsername;
                        System.out.println("\n  || ^_^ || Username Updated Successfully!");
                        } else {
                            System.out.println("\n  !! Error: Update failed.");
                        }
                    } else {
                        System.out.println("\n  !! Error: Incorrect username.");
                    }
                }
                case "2" -> {
                    System.out.println("--------------------------------------------------------------------------------");
                    System.out.print("  Enter current Password: ");
                    String inputPass = sc.next();
                    if (inputPass.equals(pass)) {
                        System.out.print("  Enter new Password: ");
                        String newPassword = sc.next();
                        if (DataStorageManager.updatePassword(pass, newPassword)) {
                            pass = newPassword;
                            System.out.println("\n  || ^_^ || Password Updated Successfully!");
                        } else {
                            System.out.println("\n  !! Error: Update failed.");
                        }
                    } else {
                        System.out.println("\n  !! Error: Incorrect password.");
                    }
                }
                case "3" -> {
                    System.out.println("--------------------------------------------------------------------------------");
                    System.out.println("  !! Warning: This action will permanently delete your system account!");
                    System.out.println("--------------------------------------------------------------------------------");
                    System.out.print("  Enter password to confirm: ");
                    String inputPass = sc.next();
                    if (inputPass.equals(pass)) {
                        String targetID = DataStorageManager.getUserID(user);
                        if (targetID != null && DataStorageManager.deleteUserAccount(targetID)) {
                            System.out.println("\n  || O.O || Account Deleted Successfully!");
                            System.out.println("--------------------------------------------------------------------------------");
                            System.out.println("  Safely closing active session... Please wait.");
                            mainPage();
                        } else {
                            System.out.println("\n  !! Error: Account deletion failed.");
                        }
                    } else {
                        System.out.println("\n  !! Error: Incorrect password.");
                    }
                }
                case "X", "x" -> {}
                default -> System.out.println("\n  !! Error: Invalid choice. Please select 1, 2, 3, or X.");
            }
        } while (!choice.equalsIgnoreCase("X"));
    } // END OF THE ACCOUNT SETTINGS METHOD
    
    // Workspace dashboard layouts managing data processing operations for Secretary accounts
    public static void hrdoSecretaryDashboard(String user, String pass) {
        String choice;
        do {
            System.out.println("================================================================================");
            System.out.println("                                   DASHBOARD                                    ");
            System.out.println("================================================================================");
            System.out.println("  || ^_^ || Welcome, HRDO Secretary!                                            ");
            System.out.println("--------------------------------------------------------------------------------");
            System.out.println(" [1] Add Personnel Record Request");
            System.out.println(" [2] Edit Personnel Record Request");
            System.out.println(" [3] Delete Personnel Record Request");
            System.out.println(" [4] View All Employee Records");
            System.out.println(" [5] Search Employee");
            System.out.println(" [6] Search Employee (same options)");
            System.out.println(" [7] Account Settings");
            System.out.println(" [X] Sign Out / Log Out");
            System.out.println("================================================================================");
            System.out.print(" >>> Enter Choice: ");
            choice = sc.next();
            System.out.println("========================================");

            switch (choice) {
                case "1" -> secretaryAddFlow();
                case "2" -> secretaryEditFlow();
                case "3" -> secretaryDeleteFlow();
                case "4" -> viewRecordsSelection(); // Lets secretary filter and view listings
                case "5", "6" -> searchEmployeeMenu();
                case "7" -> accountSettings(user, pass, "S");
                case "X", "x" -> {
                    System.out.println("\n  || >.< || Logging you out safely... Please wait.");
                    mainPage();
                }
                default -> System.out.println("\n  !! Error: Invalid choice. Please choose 1 to 7, or X.");
            }
        } while (!choice.equalsIgnoreCase("X"));
    } // END OF SECRETARY DASHBOARD METHOD

     // Captures new employee profiles and queues them for head master authorization
    public static void secretaryAddFlow() {
        String proceedChoice = null;
        do {
            System.out.println("================================================================================");
            System.out.println("                              REGISTER NEW EMPLOYEE                             ");
            System.out.println("================================================================================");
            System.out.println(" [1] Faculty                                                                    ");
            System.out.println(" [2] Non-Teaching Personnel                                                     ");
            System.out.println(" [X] Go Back                                                                    ");
            System.out.println("================================================================================");
            System.out.print(" >>> Enter Choice: ");
            String typeChoice = sc.next();
            
            if (typeChoice.equalsIgnoreCase("X")) {
                break;
            }

            String typeCode;
            if (typeChoice.equals("1")) {
                typeCode = "F";
            } else if (typeChoice.equals("2")) {
                typeCode = "NTP";
            } else {
                System.out.println("\n  !! Error: Invalid choice sequence. Please select 1, 2, or X.");
                continue;
            }

            System.out.print(" Assign Employee ID: ");
            String empID = sc.next();

            if (DataStorageManager.employeeExists(empID)) {
                System.out.println("\n  !! Error: An employee with ID (" + empID + ") already exists in our registries.");
                System.out.print("\n >>> Try again? [Y/N]: ");
                proceedChoice = sc.next();
                continue;
            }

            sc.nextLine(); // Clear scanner token streams
            System.out.print(" First Name     : "); String fName = sc.nextLine();
            System.out.print(" Last Name      : "); String lName = sc.nextLine();
            System.out.print(" Gender         : "); String gender = sc.nextLine();
            System.out.print(" Age            : "); String age = sc.nextLine();
            System.out.print(" Contact Number : "); String cNum = sc.nextLine();
            System.out.print(" Home Address   : "); String address = sc.nextLine();
            System.out.print(" Status (Permanent/Probationary/Contractual): "); String status = sc.nextLine();
            System.out.print(" Workload Load (Full-Time/Part-Time)       : "); String load = sc.nextLine();
            System.out.print(" Calculated Years of Service              : "); String serviceYears = sc.nextLine();

            String[] dataFields;

            if (typeCode.equals("F")) {
                System.out.print(" Academic Rank  : "); String rank = sc.nextLine();
                System.out.print(" Department     : "); String dept = sc.nextLine();
                
                dataFields = new String[]{
                    empID, fName, lName, gender, age, cNum, address, 
                    status, load, serviceYears, typeCode, rank, dept,
                    "", "", "", "", "", "", "", "", "", 
                    ""
                };
            } else {
                System.out.print(" Office Assignment: "); String office = sc.nextLine();
                System.out.print(" Job Designation  : "); String desig = sc.nextLine();

                dataFields = new String[]{
                    empID, fName, lName, gender, age, cNum, address, 
                    status, load, serviceYears, typeCode, office, desig,
                    "", "", "", "", "", "", "", "", "", 
                    ""
                };
            }

            System.out.println("========================================");
            System.out.println("       SUPPLEMENTARY TRACK FILES        ");
            System.out.println("========================================");
            System.out.print(" Mobile Number  : "); dataFields[13] = sc.nextLine();
            System.out.print(" Landline Number: "); dataFields[14] = sc.nextLine();
            System.out.print(" Email Address  : "); dataFields[15] = sc.nextLine();
            
            System.out.print(" Emergency Contact Name: "); dataFields[16] = sc.nextLine();
            System.out.print(" Relationship Type    : "); dataFields[17] = sc.nextLine();
            System.out.print(" Emergency Tel Phone  : "); dataFields[18] = sc.nextLine();
            
            System.out.print(" Government TIN Number: "); dataFields[19] = sc.nextLine();
            System.out.print(" Government SSS Number: "); dataFields[20] = sc.nextLine();
            System.out.print(" PhilHealth Card ID   : "); dataFields[21] = sc.nextLine();
            System.out.print(" Pag-Ibig Registry ID : "); dataFields[22] = sc.nextLine();

            DataStorageManager.insertPendingAction("INSERT", empID, typeCode, dataFields);
            System.out.println("\n || ^_^ || Success: Registration request dispatched to Head review buffer.");
            
            System.out.print("\n >>> Do you want to queue another registration request? [Y/N]: ");
            proceedChoice = sc.next();
            System.out.println("========================================");
        } while(proceedChoice.equalsIgnoreCase("Y"));
    }

    // Requests an inline value mutation for a registered profile tracking reference
    public static void secretaryEditFlow() {
        System.out.println("========================================");
        System.out.println("       EDIT RECORD MODIFICATION         ");
        System.out.println("========================================");
        System.out.print(" Target Employee ID Number: ");
        String empID = sc.next();

        System.out.println(" [1] Faculty Profile");
        System.out.println(" [2] Non-Teaching Personnel Profile");
        System.out.print(" >>> Choose Classification: ");
        String roleChoice = sc.next();
        String typeCode = roleChoice.equals("1") ? "F" : "NTP";

        System.out.println("\n Choose profile update column track parameter:");
        System.out.println(" [1] First Name     [4] Age            [7] Status");
        System.out.println(" [2] Last Name      [5] Contact Num    [8] Load");
        System.out.println(" [3] Gender         [6] Home Address   [9] Years of Service");
        System.out.print(" >>> Enter target selection choice: ");
        String fieldChoice = sc.next();

        sc.nextLine(); // Clear scanner buffer caches
        System.out.print(" Provide replacement parameter update value: ");
        String value = sc.nextLine();

        // Bundles operation commands tightly within uniform array string lines
        String[] editMetadata = new String[]{ empID, fieldChoice, value };
        DataStorageManager.insertPendingAction("UPDATE", empID, typeCode, editMetadata);
        System.out.println("\n || ^_^ || Success: Profile update sequence logged into head review streams.");
    }

    // Submits deletion requests targeting outdated entries inside the core files
    public static void secretaryDeleteFlow() {
        System.out.println("========================================");
        System.out.println("         REMOVE PROFILE TARGET          ");
        System.out.println("========================================");
        System.out.print(" Target Employee ID Number to Delete: ");
        String empID = sc.next();

        System.out.println(" [1] Faculty Profile");
        System.out.println(" [2] Non-Teaching Personnel Profile");
        System.out.print(" >>> Choose Classification: ");
        String roleChoice = sc.next();
        String typeCode = roleChoice.equals("1") ? "F" : "NTP";

        DataStorageManager.insertPendingAction("DELETE", empID, typeCode);
        System.out.println("\n || ^_^ || Success: Deletion sequence mapped to admin clearance workspace.");
    }

    // Workspace dashboard layouts managing executive actions for Head accounts
    public static void hrdoHeadDashboard(String user, String pass) {
        String choice;
        do {
            System.out.println("================================================================================");
            System.out.println("                                    DASHBOARD                                   ");
            System.out.println("================================================================================");
            System.out.println("  || ^_^ || Welcome, HRDO Head!                                                 ");
            System.out.println("--------------------------------------------------------------------------------");
            System.out.println(" [1] Review Pending Requests (" + DataStorageManager.getRequestCount() + " NEW) ");
            System.out.println(" [2] Add New Employee Record                                                    ");
            System.out.println(" [3] Edit Employee Record                                                       ");
            System.out.println(" [4] Delete Employee Record                                                     ");
            System.out.println(" [5] View All Employee Records                                                  ");
            System.out.println(" [6] Search Employee                                                          ");
            System.out.println(" [7] Search Employee (same options)                                            ");
            System.out.println(" [8] Display Summary Report                                                     ");
            System.out.println(" [9] Account Settings                                                           ");
            System.out.println(" [X] Sign Out / Log Out                                                         ");
            System.out.println("================================================================================");
            System.out.print(" >>> Enter Choice: ");
            choice = sc.next();
            System.out.println("========================================");

            switch (choice) {
                case "1" -> headApprovalFlow(); 
                case "2" -> headDirectAddFlow(); 
                case "3" -> headDirectEditFlow(); 
                case "4" -> headDirectDeleteFlow(); 
                case "5" -> viewRecordsSelection();
                case "6", "7" -> searchEmployeeMenu();
                case "8" -> displayMetricsSummary();
                case "9" -> accountSettings(user, pass, "H");
                case "X", "x" -> {
                    System.out.println("\n  || >.< || Logging you out safely... Please wait.");
                    mainPage();
                }
                default -> System.out.println("\n  !! Error: Invalid choice. Please choose 1 to 9, or X.");
            }
        } while (!choice.equalsIgnoreCase("X"));
    } // END OF HEAD DASHBOARD METHOD
    
    // Allows the HRDO Head to instantly create and store records down in memory
    public static void headDirectAddFlow() {
        System.out.println("========================================");
        System.out.println("     [DIRECT] ADD PERSONNEL RECORD       ");
        System.out.println("========================================");
        System.out.println(" [1] Faculty Member Profile");
        System.out.println(" [2] Non-Teaching Personnel Profile");
        System.out.println("========================================");
        System.out.print(" >>> Select Employee Classification: ");
        String typeChoice = sc.next();
        
        String typeCode = typeChoice.equals("1") ? "F" : (typeChoice.equals("2") ? "NTP" : null);
        if (typeCode == null) {
            System.out.println(" !! Error: Invalid choice sequence. Returning to Dashboard...");
            return;
        }

        System.out.print(" Assign Employee ID: ");
        String empID = sc.next();

        if (DataStorageManager.employeeExists(empID)) {
            System.out.println(" !! Error: An employee with ID (" + empID + ") already exists in our registries.");
            return;
        }

        sc.nextLine(); // Clear scanner buffer
        System.out.print(" First Name     : "); String fName = sc.nextLine();
        System.out.print(" Last Name      : "); String lName = sc.nextLine();
        System.out.print(" Gender         : "); String gender = sc.nextLine();
        System.out.print(" Age            : "); String age = sc.nextLine();
        System.out.print(" Contact Number : "); String cNum = sc.nextLine();
        System.out.print(" Home Address   : "); String address = sc.nextLine();
        System.out.print(" Status (Permanent/Probationary/Contractual): "); String status = sc.nextLine();
        System.out.print(" Workload Load (Full-Time/Part-Time)       : "); String load = sc.nextLine();
        System.out.print(" Calculated Years of Service              : "); String serviceYears = sc.nextLine();

        if (typeCode.equals("F")) {
            System.out.print(" Academic Rank  : "); String rank = sc.nextLine();
            System.out.print(" Department     : "); String dept = sc.nextLine();
            
            Faculty f = new Faculty(empID, fName, lName, gender, age, cNum, address, status, load, serviceYears, typeCode, rank, dept);
            DataStorageManager.insertEmployee(f);
            DataStorageManager.insertFaculty(f);
        } else {
            System.out.print(" Office Assignment: "); String office = sc.nextLine();
            System.out.print(" Job Designation  : "); String desig = sc.nextLine();

            NonTeachingPersonnel ntp = new NonTeachingPersonnel(empID, fName, lName, gender, age, cNum, address, status, load, serviceYears, typeCode, office, desig);
            DataStorageManager.insertEmployee(ntp);
            DataStorageManager.insertNonTeachingPersonnel(ntp);
        }

        System.out.println("========================================");
        System.out.println("       SUPPLEMENTARY TRACK FILES        ");
        System.out.println("========================================");
        System.out.print(" Mobile Number  : "); String mob = sc.nextLine();
        System.out.print(" Landline Number: "); String land = sc.nextLine();
        System.out.print(" Email Address  : "); String email = sc.nextLine();
        DataStorageManager.insertContactDetails(empID, mob, land, email);
        
        System.out.print(" Emergency Contact Name: "); String eName = sc.nextLine();
        System.out.print(" Relationship Type    : "); String eRel = sc.nextLine();
        System.out.print(" Emergency Tel Phone  : "); String ePhone = sc.nextLine();
        DataStorageManager.insertEmergencyContact(empID, eName, eRel, ePhone);
        
        System.out.print(" Government TIN Number: "); String tin = sc.nextLine();
        System.out.print(" Government SSS Number: "); String sss = sc.nextLine();
        System.out.print(" PhilHealth Card ID   : "); String phil = sc.nextLine();
        System.out.print(" Pag-Ibig Registry ID : "); String pag = sc.nextLine();
        DataStorageManager.insertGovernmentRecord(empID, tin, sss, phil, pag);

        System.out.println("\n || ^_^ || Success: Record instantly committed into memory storage arrays.");
    }

    // Commits an inline value modification directly into storage fields
    public static void headDirectEditFlow() {
        System.out.println("========================================");
        System.out.println("          EDIT EMPLOYEE RECORD          ");
        System.out.println("========================================");
        System.out.print("Enter Employee ID: ");
        String empID = sc.next();
        Employee employee = DataStorageManager.getEmployeeByID(empID);
        if (employee == null) {
            System.out.println("Employee ID not found.");
            return;
        }

        ArrayList<String[]> pendingChanges = new ArrayList<>();
        while (true) {
            System.out.println("\nSelect details to edit:");
            System.out.println("[1] Basic personal information");
            System.out.println("[2] Contact details");
            System.out.println("[3] Emergency contact");
            System.out.println("[4] Government records");
            System.out.println("[N] Finish editing");
            System.out.print("Choice: ");
            String category = sc.next();
            if (category.equalsIgnoreCase("N")) break;

            String field;
            String value;
            sc.nextLine();
            if (category.equals("1")) {
                System.out.println("[1] First Name  [2] Last Name  [3] Gender  [4] Age");
                System.out.println("[5] Contact Number  [6] Address  [7] Employment Status");
                System.out.println("[8] Employment Load  [9] Years of Service");
                System.out.print("Field: "); field = sc.nextLine().trim();
                if (!field.matches("[1-9]")) { System.out.println("Invalid field."); continue; }
            } else if (category.equals("2")) {
                System.out.println("[1] Mobile Number  [2] Landline Number  [3] Email Address");
                System.out.print("Field: "); field = sc.nextLine().trim();
                if (!field.matches("[1-3]")) { System.out.println("Invalid field."); continue; }
            } else if (category.equals("3")) {
                System.out.println("[1] Contact Name  [2] Relationship  [3] Phone Number");
                System.out.print("Field: "); field = sc.nextLine().trim();
                if (!field.matches("[1-3]")) { System.out.println("Invalid field."); continue; }
            } else if (category.equals("4")) {
                System.out.println("[1] TIN  [2] SSS  [3] PhilHealth  [4] Pag-Ibig");
                System.out.print("Field: "); field = sc.nextLine().trim();
                if (!field.matches("[1-4]")) { System.out.println("Invalid field."); continue; }
            } else {
                System.out.println("Invalid category.");
                continue;
            }
            System.out.print("New value: ");
            value = sc.nextLine();
            pendingChanges.add(new String[]{category, field, value});
            System.out.print("Edit another detail? Enter Y to continue or N to finish: ");
            if (!sc.nextLine().trim().equalsIgnoreCase("Y")) break;
        }

        if (pendingChanges.isEmpty()) {
            System.out.println("No changes were made.");
            return;
        }
        System.out.println("\nCurrent employee details:");
        DataStorageManager.printEmployeeDetails(empID);
        System.out.println("Proposed changes:");
        for (String[] change : pendingChanges) {
            System.out.printf("Category %s, field %s -> %s%n", change[0], change[1], change[2]);
        }
        System.out.print("Confirm and apply these changes? [Y/N]: ");
        if (!sc.next().equalsIgnoreCase("Y")) {
            System.out.println("Changes cancelled. No updates were applied.");
            return;
        }

        int applied = 0;
        for (String[] change : pendingChanges) {
            boolean success;
            if (change[0].equals("1")) {
                success = DataStorageManager.updateEmployee(employee.getEmployeeType(), empID, change[1], change[2]);
            } else {
                success = DataStorageManager.updateSupplementaryField(empID, change[0], change[1], change[2]);
            }
            if (success) applied++;
            else System.out.println("Could not update category " + change[0] + ", field " + change[1] + ". The related supplementary record may not exist yet.");
        }
        System.out.println("Updates applied: " + applied + " of " + pendingChanges.size());
        DataStorageManager.printEmployeeDetails(empID);
    }

    // Delete employee only after showing the matching name and asking for confirmation.
    public static void headDirectDeleteFlow() {
        System.out.println("========================================");
        System.out.println("          DELETE EMPLOYEE RECORD       ");
        System.out.println("========================================");
        System.out.print("Enter Employee ID: ");
        String empID = sc.next();
        Employee employee = DataStorageManager.getEmployeeByID(empID);
        if (employee == null) {
            System.out.println("Employee ID not found.");
            return;
        }
        System.out.println("Record selected: " + employee.getFirstName() + " " + employee.getLastName()
                + " (" + employee.getEmployeeID() + ")");
        System.out.print("Are you sure you want to permanently delete this employee record? [Y/N]: ");
        String confirm = sc.next();
        if (!confirm.equalsIgnoreCase("Y")) {
            System.out.println("Deletion cancelled.");
            return;
        }
        if (employee.getEmployeeType().equalsIgnoreCase("F")) {
            DataStorageManager.deleteFaculty(empID);
        } else {
            DataStorageManager.deleteNonTeachingPersonnel(empID);
        }
    }

    // Processes, acts upon, and commits pending actions sent by the secretary
    public static void headApprovalFlow() {
        int count = DataStorageManager.getRequestCount();
        if (count == 0) {
            System.out.println(" || ^_^ || Status Clean: There are no pending requests for review.");
            return;
        }

        String[][] requests = DataStorageManager.getPendingActions();
        
        System.out.println("=================================================");
        System.out.println("      PENDING WORKFLOW AUTHORIZATION STAGE       ");
        System.out.println("=================================================");
        System.out.printf("  %-7s | %-10s | %-12s | %s\n", "REQ ID", "ACTION", "EMPLOYEE ID", "CLASS");
        System.out.println("-------------------------------------------------");
        
        // Loop implementation matching your original matrix positions
        for (int i = 0; i < count; i++) {
            System.out.printf("  %-7s | %-10s | %-12s | %s\n", 
                requests[i][0], requests[i][1], requests[i][2], requests[i][3]);
        }
        System.out.println("=================================================");
        System.out.print(" >>> Select Index Number to Process (1 to " + count + ") or 0 to Cancel: ");
        int choice = sc.nextInt();

        if (choice < 1 || choice > count) {
            System.out.println(" Action canceled. Returning to Dashboard...");
            return;
        }

        int targetIndex = choice - 1;
        String opType = requests[targetIndex][1];
        String targetID = requests[targetIndex][2];
        String typeCode = requests[targetIndex][3];

        System.out.println("\n [1] APPROVE / COMMIT ACTION");
        System.out.println(" [2] REJECT / DISMISS REQUEST");
        System.out.print(" >>> Select Final Action: ");
        String decision = sc.next();

        if (decision.equals("1")) {
            switch (opType) {
                case "INSERT" -> {
                    String[] details = DataStorageManager.getInsertDetailsAt(targetIndex);
                    if (typeCode.equals("F")) {
                        Faculty f = new Faculty(details[0], details[1], details[2], details[3], details[4],
                                                details[5], details[6], details[7], details[8], details[9], 
                                                details[10], details[11], details[12]);
                        DataStorageManager.insertEmployee(f);
                        DataStorageManager.insertFaculty(f);
                    } else {
                        NonTeachingPersonnel ntp = new NonTeachingPersonnel(details[0], details[1], details[2], details[3], details[4],
                                                                            details[5], details[6], details[7], details[8], details[9], 
                                                                            details[10], details[11], details[12]);
                        DataStorageManager.insertEmployee(ntp);
                        DataStorageManager.insertNonTeachingPersonnel(ntp);
                    }
                    DataStorageManager.insertContactDetails(targetID, details[13], details[14], details[15]);
                    DataStorageManager.insertEmergencyContact(targetID, details[16], details[17], details[18]);
                    DataStorageManager.insertGovernmentRecord(targetID, details[19], details[20], details[21], details[22]);
                    System.out.println("\n || ^_^ || Success: Record fully committed into storage pools.");
                }
                case "UPDATE" -> {
                    String[] editMeta = DataStorageManager.getInsertDetailsAt(targetIndex);
                    if (DataStorageManager.updateEmployee(typeCode, editMeta[0], editMeta[1], editMeta[2])) {
                        System.out.println("\n || ^_^ || Success: In-memory property modification applied.");
                    } else {
                        System.out.println("\n !! Error: Property update route execution failed.");
                    }
                }
                case "DELETE" -> {
                    if (typeCode.equals("F")) {
                        DataStorageManager.deleteFaculty(targetID);
                    } else {
                        DataStorageManager.deleteNonTeachingPersonnel(targetID);
                    }
                }
            }
        } else {
            System.out.println("\n Request successfully rejected and dropped from system tracking pipelines.");
        }

        DataStorageManager.removeRequestAt(targetIndex);
    }
    // Secondary sub-menu to route specific display filter choices
    public static void viewRecordsSelection() {
        System.out.println("========================================");
        System.out.println("           VIEW EMPLOYEES               ");
        System.out.println("========================================");
        System.out.println("[1] View all employees");
        System.out.println("[2] View employee by ID");
        System.out.println("[3] View employees by employment status");
        System.out.println("[4] View employees by years of service");
        System.out.print("Choice: ");
        String choice = sc.next();
        sc.nextLine();
        switch (choice) {
            case "1" -> DataStorageManager.printAllEmployees();
            case "2" -> {
                System.out.print("Employee ID: ");
                DataStorageManager.printEmployeeDetails(sc.nextLine().trim());
            }
            case "3" -> {
                System.out.print("Employment status (Permanent/Probationary/Contractual): ");
                DataStorageManager.searchEmployees("status", sc.nextLine().trim());
            }
            case "4" -> {
                System.out.print("Years of service: ");
                DataStorageManager.searchEmployees("years", sc.nextLine().trim());
            }
            default -> System.out.println("Invalid choice.");
        }
    }

    // Search options are available to both the HRDO Head and Secretary.
    public static void searchEmployeeMenu() {
        System.out.println("========================================");
        System.out.println("             SEARCH EMPLOYEE            ");
        System.out.println("========================================");
        System.out.println("[1] Search by ID");
        System.out.println("[2] Search by surname");
        System.out.println("[3] Search by employment status");
        System.out.println("[4] Search by years of service");
        System.out.print("Choice: ");
        String choice = sc.next();
        sc.nextLine();
        String mode;
        switch (choice) {
            case "1" -> mode = "id";
            case "2" -> mode = "surname";
            case "3" -> mode = "status";
            case "4" -> mode = "years";
            default -> { System.out.println("Invalid choice."); return; }
        }
        System.out.print("Enter search value: ");
        DataStorageManager.searchEmployees(mode, sc.nextLine().trim());
    }

    public static void displayMetricsSummary() {
        int[] metrics = DataStorageManager.getSummaryMetrics();
        System.out.println("\n+------------------------------------------------+");
        System.out.println("|              EMPLOYEE SUMMARY REPORT           |");
        System.out.println("+------------------------------------------------+");
        System.out.printf("| %-30s | %8s |%n", "Employment Status", "Count");
        System.out.println("|--------------------------------|----------|");
        System.out.printf("| %-30s | %8d |%n", "Permanent", metrics[0]);
        System.out.printf("| %-30s | %8d |%n", "Probationary", metrics[1]);
        System.out.printf("| %-30s | %8d |%n", "Contractual", metrics[2]);
        System.out.println("+------------------------------------------------+");
        System.out.printf("| %-30s | %8s |%n", "Employment Load", "Count");
        System.out.println("|--------------------------------|----------|");
        System.out.printf("| %-30s | %8d |%n", "Full-Time", metrics[3]);
        System.out.printf("| %-30s | %8d |%n", "Part-Time", metrics[4]);
        System.out.println("+------------------------------------------------+");
    }
} 