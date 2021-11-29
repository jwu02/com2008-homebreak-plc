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

    public String[] getBooleans() {
        boolean[] booleans = {hasBedLinen, hasTowels};
        String[] booleansToStrings = new String[booleans.length];
        for (int i=0; i<booleans.length; i++) {
            if (booleans[i]) {
                booleansToStrings[i] = "yes";
            } else {
                booleansToStrings[i] = "no";
            }
        }
        return booleansToStrings;
    }
}
