package nl.tudelft.oopp.demo.controllers;

import java.io.UnsupportedEncodingException;
import java.util.List;

import nl.tudelft.oopp.demo.encodehash.CommunicationMethods;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BuildingController {

    @Autowired
    private BuildingRepository buildingRepo;

    /**
     * Adds a building to the database.
     *
     * @param name           The name of the building.
     * @param roomCount      The amount of of rooms inside the building.
     * @param address        The address of the building. //TODO format of address!!
     * @param availableBikes The number of available bikes, int
     * @param maxBikes       The max number of bikes, int
     * @throws UnsupportedEncodingException Tells the user that they have used the wrong encoding.
     */
    @PostMapping("createBuilding")
    @ResponseBody
    public void createBuilding(@RequestParam String name, @RequestParam int roomCount,
                               @RequestParam String address, @RequestParam int availableBikes,
                               @RequestParam int maxBikes) throws UnsupportedEncodingException {

        name = CommunicationMethods.decodeCommunication(name);
        address = CommunicationMethods.decodeCommunication(address);

        try {
            buildingRepo.insertBuilding(name, roomCount, address, availableBikes, maxBikes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Changes the existing building with the provided ID in the database with the provides parameters.
     *
     * @param id              The building ID, this is the building that is going to get changed.
     * @param name            The new name of the building
     * @param room_count      the new room count of the building
     * @param address         the new address of the building //TODO add address format
     * @param available_bikes The number of available bikes, int
     * @param max_bikes       The max number of bikes, int
     * @throws UnsupportedEncodingException Tells the user that they have used the wrong encoding
     */
    @PostMapping("updateBuilding")
    @ResponseBody

    public void updateBuilding(@RequestParam int id, @RequestParam String name,
                               @RequestParam int roomCount, @RequestParam String address,
                               @RequestParam int availableBikes,
                               @RequestParam int maxBikes) throws UnsupportedEncodingException {

        name = CommunicationMethods.decodeCommunication(name);
        address = CommunicationMethods.decodeCommunication(address);

        try {
            buildingRepo.updateAddress(id, address);
            buildingRepo.updateName(id, name);
            buildingRepo.updateRoomCount(id, roomCount);
            buildingRepo.updateAvailableBikes(id, availableBikes);
            buildingRepo.updateMaxBikes(id, maxBikes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes the building from the database.
     *
     * @param id The ID of the building you delete.
     */
    @PostMapping("deleteBuilding")
    @ResponseBody
    public void deleteBuilding(@RequestParam int id) {
        try {
            buildingRepo.deleteBuilding(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the database entry of the building.
     *
     * @param id The ID of the building you requested info of.
     * @return Returns a Json representation of a building
     */
    @GetMapping("getBuilding")
    @ResponseBody
    public Building getBuilding(@RequestParam int id) {
        try {
            return buildingRepo.getBuilding(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns the building with the provided name.
     *
     * @param name The name of the building you're trying to find.
     * @return A Building in Json.
     */
    @GetMapping("getBuildingByName")
    @ResponseBody
    public Building getBuildingByName(@RequestParam String name) {
        try {
            return buildingRepo.getBuildingByName(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns all the buildings.
     *
     * @return Returns a List of Building in Json
     */
    @GetMapping("getAllBuildings")
    @ResponseBody
    public List<Building> getAllBuildings() {
        try {
            return buildingRepo.getAllBuildings();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
