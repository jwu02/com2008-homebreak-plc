package models.facilities;

public class KitchenFacility extends Facility {
    private boolean hasRefrigerator;
    private boolean hasMicrowave;
    private boolean hasOven;
    private boolean hasStove;
    private boolean hasDishwasher;
    private boolean hasTableware;
    private boolean hasCookware;
    private boolean hasBasicProvisions;

    public KitchenFacility(boolean hasRefrigerator, boolean hasMicrowave, boolean hasOven, boolean hasStove,
                           boolean hasDishwasher, boolean hasTableware, boolean hasCookware, boolean hasBasicProvisions) {
        this.hasRefrigerator = hasRefrigerator;
        this.hasMicrowave = hasMicrowave;
        this.hasOven = hasOven;
        this.hasStove = hasStove;
        this.hasDishwasher = hasDishwasher;
        this.hasTableware = hasTableware;
        this.hasCookware = hasCookware;
        this.hasBasicProvisions = hasBasicProvisions;
    }

    public boolean hasRefrigerator() {
        return hasRefrigerator;
    }

    public boolean hasMicrowave() {
        return hasMicrowave;
    }

    public boolean hasOven() {
        return hasOven;
    }

    public boolean hasStove() {
        return hasStove;
    }

    public boolean hasDishwasher() {
        return hasDishwasher;
    }

    public boolean hasTableware() {
        return hasTableware;
    }

    public boolean hasCookware() {
        return hasCookware;
    }

    public boolean hasBasicProvisions() {
        return hasBasicProvisions;
    }
}
