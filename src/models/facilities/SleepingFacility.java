package models.facilities;

import java.util.ArrayList;

public class SleepingFacility extends Facility {
    private boolean hasBedLinen;
    private boolean hasTowels;
    private ArrayList<Bedroom> bedrooms = new ArrayList<>();

    public SleepingFacility(boolean hasBedLinen, boolean hasTowels, ArrayList<Bedroom> bedrooms) {
        this.hasBedLinen = hasBedLinen;
        this.hasTowels = hasTowels;
        this.bedrooms = bedrooms;
    }

    public boolean hasBedLinen() {
        return hasBedLinen;
    }

    public boolean hasTowels() {
        return hasTowels;
    }

    public ArrayList<Bedroom> getBedrooms() {
        return bedrooms;
    }
}
