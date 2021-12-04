package models.facilities;

import java.util.LinkedHashMap;

public class KitchenFacility extends Facility {
    private boolean hasRefrigerator;
    private boolean hasMicrowave;
    private boolean hasOven;
    private boolean hasStove;
    private boolean hasDishwasher;
    private boolean hasTableware;
    private boolean hasCookware;
    private boolean hasBasicProvisions;

    private LinkedHashMap<String, Boolean> kitchenFacilityMap = new LinkedHashMap<>();

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

    public LinkedHashMap<String, Boolean> getFacilityMap() {
        kitchenFacilityMap.put("hasRefrigerator", hasRefrigerator);
        kitchenFacilityMap.put("hasMicrowave", hasMicrowave);
        kitchenFacilityMap.put("hasOven", hasOven);
        kitchenFacilityMap.put("hasDishwasher", hasDishwasher);
        kitchenFacilityMap.put("hasTableware", hasTableware);
        kitchenFacilityMap.put("hasCookware", hasCookware);
        kitchenFacilityMap.put("hasBasicProvisions", hasBasicProvisions);

        return kitchenFacilityMap;
    }
}
