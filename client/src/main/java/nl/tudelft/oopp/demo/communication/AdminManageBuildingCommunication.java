package nl.tudelft.oopp.demo.communication;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;

import java.net.http.HttpResponse;

public class AdminManageBuildingCommunication {

    public static String createBuilding(Building building) {
        HttpResponse<String> response = null;
        return response.body();
    }

    public static String editBuilding(Building building) {
        HttpResponse<String> response = null;
        return response.body();
    }

    public static String deleteBuilding(int buildingId) {
        HttpResponse<String> response = null;
        return response.body();
    }

    public static String getBuildings() {
        HttpResponse<String> response = null;
        return response.body();
    }

}
