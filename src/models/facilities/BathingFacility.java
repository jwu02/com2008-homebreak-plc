package models.facilities;

import java.util.ArrayList;

public class BathingFacility extends Facility {
    private boolean hasHairDryer;
    private boolean hasShampoo;
    private boolean hasToiletPaper;
    private ArrayList<Bathroom> bathrooms = new ArrayList<>();

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
}
