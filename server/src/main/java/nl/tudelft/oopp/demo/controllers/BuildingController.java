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

    @PostMapping("deleteBuilding")
    @ResponseBody
    public void deleteBuilding(@RequestParam int id){
        try{
            buildingRepo.deleteBuilding(id);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

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
