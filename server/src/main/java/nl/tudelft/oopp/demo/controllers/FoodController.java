package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.encodehash.CommunicationMethods;
import nl.tudelft.oopp.demo.entities.Food;
import nl.tudelft.oopp.demo.repositories.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
public class FoodController {

    @Autowired
    private FoodRepository foodRepo;

    @PostMapping("createFood")
    @ResponseBody
    public void createFood(@RequestParam String name, @RequestParam int price) throws UnsupportedEncodingException {
        name = CommunicationMethods.decodeCommunication(name);
        try{
            foodRepo.insertFood(name, price);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @PostMapping("addFoodToBuilding")
    @ResponseBody
    public void addFoodToBuilding(@RequestParam int foodId, @RequestParam int buildingId) {
        try {
            foodRepo.addFoodToBuilding(foodId, buildingId);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("addFoodToReservation")
    @ResponseBody
    public void addFoodToReservation(@RequestParam int foodId, @RequestParam int reservationId, @RequestParam int quantity) {
        try {
            foodRepo.addFoodToReservation(foodId, reservationId, quantity);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("updateFood")
    @ResponseBody
    public void updateFood(@RequestParam int id, @RequestParam String name, @RequestParam int price) throws UnsupportedEncodingException {
        name = CommunicationMethods.decodeCommunication(name);
        try {
            foodRepo.updateName(id, name);
            foodRepo.updatePrice(id, price);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("deleteFoodFromReservation")
    @ResponseBody
    public void deleteFoodFromReservation(@RequestParam int foodId, @RequestParam int reservationId) {
        try {
            foodRepo.deleteFoodReservation(reservationId, foodId);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("deleteFoodFromBuilding")
    @ResponseBody
    public void deleteFoodFromBuilding(@RequestParam int foodId, @RequestParam int buildingId) {
        try {
            foodRepo.deleteFoodBuilding(buildingId, foodId);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("updateFoodReservationQuantity")
    @ResponseBody
    public void updateFoodReservationQuantity(@RequestParam int foodId, @RequestParam int reservationId, @RequestParam int quantity) {
        try {
            foodRepo.updateFoodReservationQuantity(reservationId, foodId, quantity);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("deleteFood")
    @ResponseBody
    public void deleteFood(@RequestParam int id) {
        try {
            foodRepo.deleteFood(id);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("getFood")
    @ResponseBody
    public Food getFood(@RequestParam int id) {
        try {
            return foodRepo.getFood(id);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("getFoodByName")
    @ResponseBody
    public Food getFoodByName(@RequestParam String name) throws UnsupportedEncodingException {
        name = CommunicationMethods.decodeCommunication(name);
        try {
            return foodRepo.getFoodByName(name);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("getFoodByReservation")
    @ResponseBody
    public List<Food> getFoodByReservation(@RequestParam int reservationId)  {
        try {
            return foodRepo.getFoodByReservationId(reservationId);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("getFoodByBuildingId")
    @ResponseBody
    public List<Food> getFoodByBuildingId(@RequestParam int buildingId)  {
        try {
            return foodRepo.getFoodByBuildingId(buildingId);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("getFoodByBuildingName")
    @ResponseBody
    public List<Food> getFoodByBuildingId(@RequestParam String name)  {
        try {
            return foodRepo.getFoodByBuildingName(name);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("getAllFood")
    @ResponseBody
    public List<Food> getAllFood() {
        try {
            return foodRepo.getAllFood();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
