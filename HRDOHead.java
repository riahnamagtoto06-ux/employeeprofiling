
package SchoolEmployeeProfilingSystem;

public class HRDOHead extends User {

    private String officeLocation;

    public HRDOHead(String userID, String username, String password,
                    String userType, String officeLocation) {
        super(userID, username, password, userType);
        this.officeLocation = officeLocation;
    }

    public String getOfficeLocation() {
        return officeLocation;
    }

    public void setOfficeLocation(String officeLocation) {
        this.officeLocation = officeLocation;
    }
}
