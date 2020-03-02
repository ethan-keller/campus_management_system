package nl.tudelft.oopp.demo.entities;

public class Building {

    private int buildingId;

    private String buildingName;

    private int buildingRoom_count;

    private String buildingAddress;

    public Building() {
    }

    public Building(int buildingId, String buildingName, int buildingRoom_count, String buildingAddress) {
        this.buildingId = buildingId;
        this.buildingName = buildingName;
        this.buildingRoom_count = buildingRoom_count;
        this.buildingAddress = buildingAddress;
    }

    public int getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(int buildingId) { this.buildingId = (buildingId); }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) { this.buildingName=(buildingName); }

    public int getBuildingRoom_count() {
        return buildingRoom_count;
    }

    public void setBuildingRoom_count(int buildingRoom_count) { this.buildingRoom_count = (buildingRoom_count); }

    public String getBuildingAddress() {
        return buildingAddress;
    }
}
