package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.entities.BikeReservations;
import nl.tudelft.oopp.demo.repositories.BikeReservationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class BikeReservationController {

    @Autowired
    private BikeReservationsRepository bikeResRepo;

    @PostMapping("createBikeReservation")
    @ResponseBody
    public void createBikeReservation(@RequestParam int building, @RequestParam int num_bikes) {
        try {
            bikeResRepo.insertBikeReservation(building, num_bikes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("updateBikeReservation")
    @ResponseBody
    public void updateBikeReservation(@RequestParam int id, @RequestParam int building, @RequestParam int num_bikes) {
        try{
            bikeResRepo.updateBikeNum(id, num_bikes);
            bikeResRepo.updateBuilding(id, building);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @PostMapping("deleteBikeReservation")
    @ResponseBody
    public void deleteBikeReservation(@RequestParam int id) {
        try{
            bikeResRepo.deleteBikeReservation(id);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @GetMapping("getBikeReservation")
    @ResponseBody
    public BikeReservations getBikeReservation(@RequestParam int id) {
        try{
            return bikeResRepo.getBikeReservation(id);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("getAllBikeReservations")
    @ResponseBody
    public List<BikeReservations> getBikeReservation() {
        try{
            return bikeResRepo.getAllBikeReservations();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("getBuildingBikeReservations")
    @ResponseBody
    public List<BikeReservations> getBuildingBikeReservations(@RequestParam int building) {
        try{
            return bikeResRepo.getBuildingBikeReservations(building);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
