package models;

import models.facilities.Facility;

import java.util.ArrayList;

public class Property {
    private int propertyID;
    private String name;
    private String description;
    private Boolean offerBreakfast;
    private User host;
    private Address location;
    private ArrayList<ChargeBand> chargeBands;
    private ArrayList<Facility> facilities; // use map instead?
    //private ArrayList<Booking> bookings;
    //private ArrayList<Review> reviews;

    public Property(int propertyID, String name, String description, Boolean offerBreakfast, User host,
                    Address location, ArrayList<ChargeBand> chargeBands, ArrayList<Facility> facilities) {
        this.propertyID = propertyID;
        this.name = name;
        this.description = description;
        this.offerBreakfast = offerBreakfast;
        this.host = host;
        this.location = location;
        this.chargeBands = chargeBands;
        this.facilities = facilities;
    }

    public int getPropertyID() {
        return propertyID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getOfferBreakfast() {
        return offerBreakfast;
    }

    public User getHost() {
        return host;
    }

    public Address getLocation() {
        return location;
    }

    public ArrayList<ChargeBand> getChargeBands() {
        return chargeBands;
    }

    public ArrayList<Facility> getFacilities() {
        return facilities;
    }
}
