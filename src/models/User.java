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

    }

    public User(ResultSet resultSet) {
        try {
            resultSet.next();
            userID = resultSet.getInt("UserID");
            forename = resultSet.getString("Forename");
            surname = resultSet.getString("Surname");
            email = resultSet.getString("Email");
            mobile = resultSet.getString("Mobile");
            role = resultSet.getString("Role");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
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
