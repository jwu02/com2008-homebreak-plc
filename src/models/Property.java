package models;

import models.facilities.Facility;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Property {
    private int propertyID;
    private String name;
    private String description;
    private Boolean offerBreakfast;
    private User host;
    private Address location;
    private ArrayList<ChargeBand> chargeBands;
    private LinkedHashMap<String,Facility> facilitiesMap;
    //private ArrayList<Booking> bookings;
    //private ArrayList<Review> reviews;

    public Property(int propertyID, String name, String description, Boolean offerBreakfast, User host,
                    Address location, ArrayList<ChargeBand> chargeBands, LinkedHashMap<String,Facility> facilitiesMap) {
        this.propertyID = propertyID;
        this.name = name;
        this.description = description;
        this.offerBreakfast = offerBreakfast;
        this.host = host;
        this.location = location;
        this.chargeBands = chargeBands;
        this.facilitiesMap = facilitiesMap;
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

    public LinkedHashMap<String,Facility> getFacilitiesMap() {
        return facilitiesMap;
    }
}
