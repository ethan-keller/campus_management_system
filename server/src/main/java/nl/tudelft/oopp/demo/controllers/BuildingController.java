package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.encode_hash.CommunicationMethods;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.io.UnsupportedEncodingException;

import java.util.List;


@Controller
public class BuildingController {

    @Autowired
    private BuildingRepository buildingRepo;

    /**
     * Adds a building to the database.
     *
     * @param name The name of the building.
     * @param room_count The amount of of rooms inside the building.
     * @param address The address of the building. //TODO format of address!!
     * @throws UnsupportedEncodingException
     */
    @PostMapping("createBuilding")
    @ResponseBody
    public void createBuilding(@RequestParam String name, @RequestParam int room_count, @RequestParam String address) throws UnsupportedEncodingException {
        name = CommunicationMethods.decodeCommunication(name);
        address = CommunicationMethods.decodeCommunication(address);
        try{
            buildingRepo.insertBuilding(name, room_count, address);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Changes the existing building with the provided ID in the database with the provides parameters.
     *
     * @param id The building ID, this is the building that is going to get changed.
     * @param name The new name of the building
     * @param room_count the new room count of the building
     * @param address the new address of the building //TODO add address format
     * @throws UnsupportedEncodingException
     */
    @PostMapping("updateBuilding")
    @ResponseBody
    public void updateBuilding(@RequestParam int id, @RequestParam String name, @RequestParam int room_count, @RequestParam String address) throws UnsupportedEncodingException {
        name = CommunicationMethods.decodeCommunication(name);
        address = CommunicationMethods.decodeCommunication(address);

        try{
            buildingRepo.updateAddress(id, address);
            buildingRepo.updateName(id, name);
            buildingRepo.updateRoomCount(id, room_count);
        } catch (Exception e){
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
    public void deleteBuilding(@RequestParam int id){
        try{
            buildingRepo.deleteBuilding(id);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Returns the database entry of the building.
     *
     * @param id The ID of the building you requested info of.
     * @return //TODO figure out the exact way it's returned.
     */
    @GetMapping("getBuilding")
    @ResponseBody
    public Building getBuilding(@RequestParam int id){
        try {
            return buildingRepo.getBuilding(id);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("getBuildingByName")
    @ResponseBody
    public Building getBuildingByName(@RequestParam String name){
        try {
            return buildingRepo.getBuildingByName(name);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns all the buildings.
     *
     * @return Returns a List of //TODO figure out the exact way it's returned.
     */
    @GetMapping("getAllBuildings")
    @ResponseBody
    public List<Building> getAllBuildings(){
        try {
            return buildingRepo.getAllBuildings();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
