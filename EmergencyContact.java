package SchoolEmployeeProfilingSystem;

public class EmergencyContact {
    // Attributes linking an emergency contact to an employee
    private String employeeID;
    private String contactName;
    private String relationship;
    private String phoneNumber;

    // Constructor to easily create emergency contact records in memory
    public EmergencyContact(String employeeID, String contactName, String relationship, String phoneNumber) {
        this.employeeID = employeeID;
        this.contactName = contactName;
        this.relationship = relationship;
        this.phoneNumber = phoneNumber;
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

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}