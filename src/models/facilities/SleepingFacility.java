package models.facilities;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class SleepingFacility extends Facility {
    private boolean hasBedLinen;
    private boolean hasTowels;
    private ArrayList<Bedroom> bedrooms = new ArrayList<>();

    private LinkedHashMap<String, Boolean> sleepingFacilityMap = new LinkedHashMap<>();

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

    public LinkedHashMap<String, Boolean> getFacilityMap() {
        sleepingFacilityMap.put("hasBedLinen", hasBedLinen);
        sleepingFacilityMap.put("hasTowels", hasTowels);

        return sleepingFacilityMap;
    }
}
