package models.facilities;

import java.util.LinkedHashMap;

public class LivingFacility extends Facility {
    private boolean hasWifi;
    private boolean hasTelevision;
    private boolean hasSatellite;
    private boolean hasStreaming;
    private boolean hasDvdPlayer;
    private boolean hasBoardGames;

    private LinkedHashMap<String, Boolean> livingFacilityMap = new LinkedHashMap<>();

    public LivingFacility(boolean hasWifi, boolean hasTelevision, boolean hasSatellite, boolean hasStreaming,
                          boolean hasDvdPlayer, boolean hasBoardGames) {
        this.hasWifi = hasWifi;
        this.hasTelevision = hasTelevision;
        this.hasSatellite = hasSatellite;
        this.hasStreaming = hasStreaming;
        this.hasDvdPlayer = hasDvdPlayer;
        this.hasBoardGames = hasBoardGames;
    }

    public boolean hasWifi() {
        return hasWifi;
    }

    public boolean hasTelevision() {
        return hasTelevision;
    }

    public boolean hasSatellite() {
        return hasSatellite;
    }

    public boolean hasStreaming() {
        return hasStreaming;
    }

    public boolean hasDvdPlayer() {
        return hasDvdPlayer;
    }

    public boolean hasBoardGames() {
        return hasBoardGames;
    }

    public LinkedHashMap<String, Boolean> getFacilityMap() {
        livingFacilityMap.put("hasWifi", hasWifi);
        livingFacilityMap.put("hasTelevision", hasTelevision);
        livingFacilityMap.put("hasSatellite", hasSatellite);
        livingFacilityMap.put("hasStreaming", hasStreaming);
        livingFacilityMap.put("hasDvdPlayer", hasDvdPlayer);
        livingFacilityMap.put("hasBoardGames", hasBoardGames);

        return livingFacilityMap;
    }
}
