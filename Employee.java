package SchoolEmployeeProfilingSystem;

public abstract class Employee {
    // Core attributes shared across all school employees
    private String employeeID;
    private String firstName;
    private String lastName;
    private String gender;
    private String age;
    private String contactNumber;
    private String address;
    private String employmentStatus; // e.g., Permanent, Probationary, Contractual
    private String employmentLoad;   // e.g., Full-Time, Part-Time
    private String yearsOfService;
    private String employeeType;     // "F" for Faculty, "NTP" for Non-Teaching

    // Common constructor to initialize core parameters from subclasses
    public Employee(String employeeID, String firstName, String lastName, String gender, String age, 
                    String contactNumber, String address, String employmentStatus, String employmentLoad, 
                    String yearsOfService, String employeeType) {
        this.employeeID = employeeID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.age = age;
        this.contactNumber = contactNumber;
        this.address = address;
        this.employmentStatus = employmentStatus;
        this.employmentLoad = employmentLoad;
        this.yearsOfService = yearsOfService;
        this.employeeType = employeeType;
    }

    // ==========================================
    // GETTER AND SETTER METHODS
    // ==========================================

    public String getEmployeeID() { return employeeID; }
    public void setEmployeeID(String employeeID) { this.employeeID = employeeID; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getAge() { return age; }
    public void setAge(String age) { this.age = age; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getEmploymentStatus() { return employmentStatus; }
    public void setEmploymentStatus(String employmentStatus) { this.employmentStatus = employmentStatus; }

    public String getEmploymentLoad() { return employmentLoad; }
    public void setEmploymentLoad(String employmentLoad) { this.employmentLoad = employmentLoad; }

    public String getYearsOfService() { return yearsOfService; }
    public void setYearsOfService(String yearsOfService) { this.yearsOfService = yearsOfService; }

    public String getEmployeeType() { return employeeType; }
    public void setEmployeeType(String employeeType) { this.employeeType = employeeType; }
}