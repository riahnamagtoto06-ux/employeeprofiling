package SchoolEmployeeProfilingSystem;

public class Faculty extends Employee {
    // Unique attributes for Faculty members
    private String academicRank; // e.g., Instructor, Assistant Professor, Professor
    private String department;   // e.g., College of Science, College of Engineering

    // Constructor matching the parameters initialized during a faculty record entry
    public Faculty(String employeeID, String firstName, String lastName, String gender, String age, 
                   String contactNumber, String address, String employmentStatus, String employmentLoad, 
                   String yearsOfService, String employeeType, String academicRank, String department) {
        // Pass common core attributes up to the parent Employee constructor
        super(employeeID, firstName, lastName, gender, age, contactNumber, address, 
              employmentStatus, employmentLoad, yearsOfService, employeeType);
        this.academicRank = academicRank;
        this.department = department;
    }

    // ==========================================
    // GETTER AND SETTER METHODS
    // ==========================================

    public String getAcademicRank() {
        return academicRank;
    }

    public void setAcademicRank(String academicRank) {
        this.academicRank = academicRank;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}