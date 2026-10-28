package SchoolEmployeeProfilingSystem;

public class GovernmentRecord {
    // Attributes linking an employee to their unique regulatory IDs
    private String employeeID;
    private String tinNumber;       // Taxpayer Identification Number
    private String sssNumber;       // Social Security System
    private String philHealthNumber; // PhilHealth Insurance
    private String pagIbigNumber;   // HDMF / Pag-IBIG Fund

    // Constructor to easily create structural government records in memory
    public GovernmentRecord(String employeeID, String tinNumber, String sssNumber, String philHealthNumber, String pagIbigNumber) {
        this.employeeID = employeeID;
        this.tinNumber = tinNumber;
        this.sssNumber = sssNumber;
        this.philHealthNumber = philHealthNumber;
        this.pagIbigNumber = pagIbigNumber;
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

    public String getTinNumber() {
        return tinNumber;
    }

    public void setTinNumber(String tinNumber) {
        this.tinNumber = tinNumber;
    }

    public String getSssNumber() {
        return sssNumber;
    }

    public void setSssNumber(String sssNumber) {
        this.sssNumber = sssNumber;
    }

    public String getPhilHealthNumber() {
        return philHealthNumber;
    }

    public void setPhilHealthNumber(String philHealthNumber) {
        this.philHealthNumber = philHealthNumber;
    }

    public String getPagIbigNumber() {
        return pagIbigNumber;
    }

    public void setPagIbigNumber(String pagIbigNumber) {
        this.pagIbigNumber = pagIbigNumber;
    }
}