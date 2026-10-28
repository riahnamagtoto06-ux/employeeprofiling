package SchoolEmployeeProfilingSystem;

public class ContactDetail {
    // Attributes matching employee contact records
    private String employeeID;
    private String mobileNumber;
    private String landlineNumber;
    private String emailAddress;

    // Constructor to easily create contact records in memory
    public ContactDetail(String employeeID, String mobileNumber, String landlineNumber, String emailAddress) {
        this.employeeID = employeeID;
        this.mobileNumber = mobileNumber;
        this.landlineNumber = landlineNumber;
        this.emailAddress = emailAddress;
    }

    // ==========================================
    // GETTER AND SETTER METHODS
    // ==========================================

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getLandlineNumber() {
        return landlineNumber;
    }

    public void setLandlineNumber(String landlineNumber) {
        this.landlineNumber = landlineNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
