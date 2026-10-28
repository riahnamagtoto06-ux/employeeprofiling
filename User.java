package SchoolEmployeeProfilingSystem;

public abstract class User {
    // Shared attributes across all system accounts
    private String userID;
    private String username;
    private String password;
    private String userType; // "H" for Head, "S" for Secretary

    // Constructor to pass fields up from subclasses
    public User(String userID, String username, String password, String userType) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.userType = userType;
    }

    // Shared Getter and Setter methods inherited by Head and Secretary
    public String getUserID() { return userID; }
    public void setUserID(String userID) { this.userID = userID; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getUserType() { return userType; }
    public void setUserType(String userType) { this.userType = userType; }
}