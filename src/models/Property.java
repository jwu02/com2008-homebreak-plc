package models;

import models.facilities.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import static database.OpenConnection.getConnection;

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

    public static ArrayList<Property> selectProperties(ResultSet rs) {
        ArrayList<Property> filteredProperties = new ArrayList<>();
        try (Connection con = getConnection()) {
            while (rs.next()) {
                int propertyID = rs.getInt("PropertyID");
                String propertyName = rs.getString("Name");
                String propertyDescription = rs.getString("Description");
                Boolean offerBreakfast = rs.getBoolean("OfferBreakfast");

                int userID = rs.getInt("p.UserID");
                String forename = rs.getString("Forename");
                String surname = rs.getString("Surname");
                String email = rs.getString("Email");
                String mobile = rs.getString("Mobile");
                String role = rs.getString("Role");
                User host = new User(userID, forename, surname, email, mobile, role);

                int addressID = rs.getInt("p.AddressID");
                String house = rs.getString("House");
                String street = rs.getString("Street");
                String place = rs.getString("Place");
                String postcode = rs.getString("Postcode");
                Address address = new Address(addressID, house, street, place, postcode);

                // convert charge band information to model
                ArrayList<ChargeBand> chargeBandsList = new ArrayList<>();
                String query = "SELECT * FROM ChargeBands WHERE PropertyID = ?";
                PreparedStatement pstChargeBands = con.prepareStatement(query);
                pstChargeBands.setInt(1, propertyID);
                ResultSet rsChargeBands = pstChargeBands.executeQuery();
                while (rsChargeBands.next()) {
                    LocalDate startDate = rsChargeBands.getDate("StartDate").toLocalDate();
                    LocalDate endDate = rsChargeBands.getDate("EndDate").toLocalDate();
                    BigDecimal pricePerNight = rsChargeBands.getBigDecimal("PricePerNight");
                    BigDecimal serviceCharge = rsChargeBands.getBigDecimal("ServiceCharge");
                    BigDecimal cleaningCharge = rsChargeBands.getBigDecimal("CleaningCharge");
                    chargeBandsList.add(new ChargeBand(startDate, endDate, pricePerNight, serviceCharge, cleaningCharge));
                }

                // convert facility information to models
                LinkedHashMap<String, Facility> facilitiesMap = new LinkedHashMap<>();

                ArrayList<Bedroom> bedroomsList = new ArrayList<>();
                query = "SELECT * FROM Bedrooms WHERE PropertyID = ?";
                PreparedStatement pstFacilities = con.prepareStatement(query);
                pstFacilities.setInt(1, propertyID);
                ResultSet rsFacilities = pstFacilities.executeQuery();
                while (rsFacilities.next()) {
                    String bed1 = rsFacilities.getString("Bed1");
                    String bed2 = rsFacilities.getString("Bed2");
                    bedroomsList.add(new Bedroom(bed1, bed2));
                }

                query = "SELECT * FROM SleepingFacilities WHERE PropertyID = ? LIMIT 1";
                pstFacilities = con.prepareStatement(query);
                pstFacilities.clearParameters();
                pstFacilities.setInt(1, propertyID);
                rsFacilities = pstFacilities.executeQuery();
                rsFacilities.next();
                boolean hasBedLinen = rsFacilities.getBoolean("HasBedLinen");
                boolean hasTowels = rsFacilities.getBoolean("HasBedLinen");
                facilitiesMap.put("Sleeping Facility", new SleepingFacility(hasBedLinen, hasTowels, bedroomsList));

                ArrayList<Bathroom> bathroomsList = new ArrayList<>();
                query = "SELECT * FROM Bathrooms WHERE PropertyID = ?";
                pstFacilities = con.prepareStatement(query);
                pstFacilities.clearParameters();
                pstFacilities.setInt(1, propertyID);
                rsFacilities = pstFacilities.executeQuery();
                while (rsFacilities.next()) {
                    boolean hasToilet = rsFacilities.getBoolean("HasToilet");
                    boolean hasBath = rsFacilities.getBoolean("HasBath");
                    boolean hasShower = rsFacilities.getBoolean("HasShower");
                    boolean sharedWithHost = rsFacilities.getBoolean("SharedWithHost");
                    bathroomsList.add(new Bathroom(hasToilet, hasBath, hasShower, sharedWithHost));
                }

                query = "SELECT * FROM BathingFacilities WHERE PropertyID = ? LIMIT 1";
                pstFacilities = con.prepareStatement(query);
                pstFacilities.clearParameters();
                pstFacilities.setInt(1, propertyID);
                rsFacilities = pstFacilities.executeQuery();
                rsFacilities.next();
                boolean hasHairDryer = rsFacilities.getBoolean("HasHairDryer");
                boolean hasShampoo = rsFacilities.getBoolean("HasShampoo");
                boolean hasToiletPaper = rsFacilities.getBoolean("HasToiletPaper");
                facilitiesMap.put("Bathing Facility", new BathingFacility(hasHairDryer, hasShampoo, hasToiletPaper, bathroomsList));

                query = "SELECT * FROM KitchenFacilities WHERE PropertyID = ? LIMIT 1";
                pstFacilities = con.prepareStatement(query);
                pstFacilities.clearParameters();
                pstFacilities.setInt(1, propertyID);
                rsFacilities = pstFacilities.executeQuery();
                rsFacilities.next();
                boolean hasRefrigerator = rsFacilities.getBoolean("HasRefrigerator");
                boolean hasMicrowave = rsFacilities.getBoolean("HasMicrowave");
                boolean hasOven = rsFacilities.getBoolean("HasOven");
                boolean hasStove = rsFacilities.getBoolean("HasStove");
                boolean hasDishwasher = rsFacilities.getBoolean("HasDishwasher");
                boolean hasTableware = rsFacilities.getBoolean("HasTableware");
                boolean hasCookware = rsFacilities.getBoolean("HasCookware");
                boolean hasBasicProvisions = rsFacilities.getBoolean("HasBasicProvisions");
                facilitiesMap.put("Kitchen Facility", new KitchenFacility(hasRefrigerator, hasMicrowave, hasOven, hasStove,
                        hasDishwasher, hasTableware, hasCookware, hasBasicProvisions));

                query = "SELECT * FROM LivingFacilities WHERE PropertyID = ? LIMIT 1";
                pstFacilities = con.prepareStatement(query);
                pstFacilities.clearParameters();
                pstFacilities.setInt(1, propertyID);
                rsFacilities = pstFacilities.executeQuery();
                rsFacilities.next();
                boolean hasWifi = rsFacilities.getBoolean("HasWifi");
                boolean hasTelevision = rsFacilities.getBoolean("HasTelevision");
                boolean hasSatellite = rsFacilities.getBoolean("HasSatellite");
                boolean hasStreaming = rsFacilities.getBoolean("HasStreaming");
                boolean hasDvdPlayer = rsFacilities.getBoolean("HasDvdPlayer");
                boolean hasBoardGames = rsFacilities.getBoolean("HasBoardGames");
                facilitiesMap.put("Living Facility", new LivingFacility(hasWifi, hasTelevision, hasSatellite,
                        hasStreaming, hasDvdPlayer, hasBoardGames));

                query = "SELECT * FROM UtilityFacilities WHERE PropertyID = ? LIMIT 1";
                pstFacilities = con.prepareStatement(query);
                pstFacilities.clearParameters();
                pstFacilities.setInt(1, propertyID);
                rsFacilities = pstFacilities.executeQuery();
                rsFacilities.next();
                boolean hasHeating = rsFacilities.getBoolean("HasHeating");
                boolean hasWashingMachine = rsFacilities.getBoolean("HasWashingMachine");
                boolean hasDryingMachine = rsFacilities.getBoolean("HasDryingMachine");
                boolean hasFireExtinguisher = rsFacilities.getBoolean("HasFireExtinguisher");
                boolean hasSmokeAlarm = rsFacilities.getBoolean("HasSmokeAlarm");
                boolean hasFirstAidKit = rsFacilities.getBoolean("HasFirstAidKit");
                facilitiesMap.put("Utility Facility", new UtilityFacility(hasHeating, hasWashingMachine, hasDryingMachine,
                        hasFireExtinguisher, hasSmokeAlarm, hasFirstAidKit));

                query = "SELECT * FROM OutdoorFacilities WHERE PropertyID = ? LIMIT 1";
                pstFacilities = con.prepareStatement(query);
                pstFacilities.clearParameters();
                pstFacilities.setInt(1, propertyID);
                rsFacilities = pstFacilities.executeQuery();
                rsFacilities.next();
                boolean hasFreeOnsiteParking = rsFacilities.getBoolean("HasFreeOnsiteParking");
                boolean hasOnRoadParking = rsFacilities.getBoolean("HasOnRoadParking");
                boolean hasPaidCarPark = rsFacilities.getBoolean("HasPaidCarPark");
                boolean hasPatio = rsFacilities.getBoolean("HasPatio");
                boolean hasBarbecue = rsFacilities.getBoolean("HasBarbecue");
                facilitiesMap.put("Outdoor Facility", new OutdoorFacility(hasFreeOnsiteParking, hasOnRoadParking,
                        hasPaidCarPark, hasPatio, hasBarbecue));

                Property property = new Property(propertyID, propertyName, propertyDescription, offerBreakfast,
                        host, address, chargeBandsList, facilitiesMap);
                filteredProperties.add(property);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return filteredProperties;
    }
}
