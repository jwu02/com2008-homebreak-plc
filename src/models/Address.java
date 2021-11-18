package models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Address {
    private int addressID;
    private String house;
    private String street;
    private String place;
    private String postcode;

    public Address(String house, String street, String place, String postcode) {
        this.house = house;
        this.street = street;
        this.place = place;
        this.postcode = postcode;
    }

    public int getAddressID() {
        return addressID;
    }

    public String getHouse() {
        return house;
    }

    public String getStreet() {
        return street;
    }

    public String getPlace() {
        return place;
    }

    public String getPostcode() {
        return postcode;
    }
}
