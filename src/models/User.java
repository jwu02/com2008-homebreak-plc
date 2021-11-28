package models;

import java.sql.ResultSet;
import java.sql.SQLException;

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

    public void setForename(String forename) {
        this.forename = forename;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
