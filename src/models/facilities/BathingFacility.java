package models.facilities;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class BathingFacility extends Facility {
    private boolean hasHairDryer;
    private boolean hasShampoo;
    private boolean hasToiletPaper;
    private ArrayList<Bathroom> bathrooms = new ArrayList<>();

    private LinkedHashMap<String, Boolean> bathingFacilityMap = new LinkedHashMap<>();

    public BathingFacility(boolean hasHairDryer, boolean hasShampoo, boolean hasToiletPaper, ArrayList<Bathroom> bathrooms) {
        this.hasHairDryer = hasHairDryer;
        this.hasShampoo = hasShampoo;
        this.hasToiletPaper = hasToiletPaper;
        this.bathrooms = bathrooms;
    }

    public boolean hasHairDryer() {
        return hasHairDryer;
    }

    public boolean hasShampoo() {
        return hasShampoo;
    }

    public boolean hasToiletPaper() {
        return hasToiletPaper;
    }

    public ArrayList<Bathroom> getBathrooms() {
        return bathrooms;
    }

    public LinkedHashMap<String, Boolean> getFacilityMap() {
        bathingFacilityMap.put("hasHairDryer", hasHairDryer);
        bathingFacilityMap.put("hasShampoo", hasShampoo);
        bathingFacilityMap.put("hasToiletPaper", hasToiletPaper);

        return bathingFacilityMap;
    }

    public String[] getBooleans() {
        boolean[] booleans = {hasHairDryer, hasShampoo, hasToiletPaper};
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
