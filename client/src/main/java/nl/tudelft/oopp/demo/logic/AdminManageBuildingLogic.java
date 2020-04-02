package nl.tudelft.oopp.demo.logic;

import java.io.UnsupportedEncodingException;

import nl.tudelft.oopp.demo.communication.BuildingServerCommunication;
import nl.tudelft.oopp.demo.entities.Building;

public class AdminManageBuildingLogic {

    /**
     * .
     * This method is used in the adminManageBuildingViewController class to communicate with the server to
     * command them to delete the selected building.
     *
     * @param selectedBuilding - The selected building from the tabular view passed as a parameter.
     */
    public static void deleteBuildingLogic(Building selectedBuilding) {
        try {
            // Communication with the server.
            BuildingServerCommunication.deleteBuilding(selectedBuilding.getBuildingId().getValue());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * .
     * This method is used in adminManageBuildingViewController class to communicate with the server to
     * command them to create a new building.
     *
     * @param tempBuilding - The features of the building are passed through this variable.
     */
    public static void createBuildingLogic(Building tempBuilding) {
        try {
            // Communication with the server.
            BuildingServerCommunication.createBuilding(tempBuilding.getBuildingName().get(),
                    tempBuilding.getBuildingRoomCount().get(), tempBuilding.getBuildingAddress().get(),
                    tempBuilding.getBuildingMaxBikes().get(), tempBuilding.getOpeningTime().get(),
                    tempBuilding.getClosingTime().get());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used in the adminManageBuildingViewController class to communicate with the server to
     * command them to edit the selected building.
     *
     * @param selectedBuilding - The selected building from the tabular view passed as a parameter.
     * @param tempBuilding     - The features of the building are passed through this variable.
     */
    public static void editBuildingLogic(Building selectedBuilding, Building tempBuilding) {
        try {
            // Communication with the server.
            BuildingServerCommunication.updateBuilding(selectedBuilding.getBuildingId().get(),
                    tempBuilding.getBuildingName().get(), tempBuilding.getBuildingRoomCount().get(),
                    tempBuilding.getBuildingAddress().get(), tempBuilding.getBuildingMaxBikes().get(),
                    tempBuilding.getOpeningTime().get(), tempBuilding.getClosingTime().get());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
