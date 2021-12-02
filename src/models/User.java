package models;

public class User {
    private int userID;
    private String forename;
    private String surname;
    private String email;
    private String mobile;
    private String role;

    public User() {
        // keep empty constructor for initial startup of application
        // when no users are logged in
    }

    public User(int userID, String forename, String surname, String email, String mobile, String role) {
        this.userID = userID;
        this.forename = forename;
        this.surname = surname;
        this.email = email;
        this.mobile = mobile;
        this.role = role;
    }

    public int getUserID() {
        return userID;
    }

    public String getForename() {
        return forename;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getRole() {
        return role;
    }

    public boolean isSuperHost() {
        // TODO implement functionality for super host check
        return false;
    };
}
