package SchoolEmployeeProfilingSystem;

public class NonTeachingPersonnel extends Employee {
    // Unique attributes for Non-Teaching staff
    private String officeAssignment; // e.g., Registrar, Accounting, HRDO
    private String designation;      // e.g., Clerk, Admin Assistant, Accountant

    // Constructor matching the parameters initialized during an NTP record entry
    public NonTeachingPersonnel(String employeeID, String firstName, String lastName, String gender, String age, 
                                String contactNumber, String address, String employmentStatus, String employmentLoad, 
                                String yearsOfService, String employeeType, String officeAssignment, String designation) {
        // Pass common core attributes up to the parent Employee constructor
        super(employeeID, firstName, lastName, gender, age, contactNumber, address, 
              employmentStatus, employmentLoad, yearsOfService, employeeType);
        this.officeAssignment = officeAssignment;
        this.designation = designation;
    }

    // ==========================================
    // GETTER AND SETTER METHODS
    // ==========================================

    public String getOfficeAssignment() {
        return officeAssignment;
    }

    public void setOfficeAssignment(String officeAssignment) {
        this.officeAssignment = officeAssignment;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
}