package SchoolEmployeeProfilingSystem;

public class HRDOSecretary extends User {

    private String deskAssignment;

    public HRDOSecretary(String userID, String username, String password,
                         String userType, String deskAssignment) {
        super(userID, username, password, userType);
        this.deskAssignment = deskAssignment;
    }

    public String getDeskAssignment() {
        return deskAssignment;
    }

    public void setDeskAssignment(String deskAssignment) {
        this.deskAssignment = deskAssignment;
    }
}
