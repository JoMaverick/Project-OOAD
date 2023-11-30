package model;

public class User {
    private int userId;
    private String userRole;
    private String userName;
    private String userEmail;
    private String userPassword;

    public int getUserId() {
        return userId;
    }

    public String getUserRole() {
        return userRole;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
