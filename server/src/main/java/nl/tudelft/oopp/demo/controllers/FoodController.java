package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Food;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import nl.tudelft.oopp.demo.repositories.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class FoodController {

    @Autowired
    private FoodRepository foodRepo;
    @Autowired
    private BuildingRepository buildingRepo;

    @GetMapping("getAllFoods")
    @ResponseBody
    public List<Food> getAllFoods() {
        try {
            return foodRepo.getAllFoods();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("getFoodBuildings")
    @ResponseBody
    public List<Building> getFoodBuildings(@RequestParam int id) {
        try {
            return buildingRepo.getFoodBuildings(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("updateFood")
    @ResponseBody
    public void updateFood(@RequestParam int id, @RequestParam String name, @RequestParam double price) {
        try {
            foodRepo.updateFood(id, name, price);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("deleteFood")
    @ResponseBody
    public void deleteFood(@RequestParam int id) {
        try {
            foodRepo.deleteFood(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("createFood")
    @ResponseBody
    public void createFood(@RequestParam String name, @RequestParam double price) {
        try {
            foodRepo.createFood(name, price);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("deleteFoodFromBuilding")
    @ResponseBody
    public void deleteFoodFromBuilding(@RequestParam int food_id, @RequestParam int building_id) {
        try {
            foodRepo.deleteFoodFromBuilding(food_id, building_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("addFoodToBuilding")
    @ResponseBody
    public void addFoodToBuilding(@RequestParam int food_id, @RequestParam int building_id) {
        try {
            foodRepo.addFoodToBuilding(food_id, building_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
