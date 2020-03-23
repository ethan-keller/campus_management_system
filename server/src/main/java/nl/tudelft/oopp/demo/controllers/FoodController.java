package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.encode_hash.CommunicationMethods;
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

    @PostMapping
    @ResponseBody
    public void createFood(@RequestParam String name, @RequestParam int price) throws UnsupportedEncodingException {
        name = CommunicationMethods.decodeCommunication(name);
        try{
            foodRepo.insertFood(name, price);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @PostMapping
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

    @PostMapping
    @ResponseBody
    public void deleteFood(@RequestParam int id) {
        try {
            foodRepo.deleteFood(id);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping
    @ResponseBody
    public Food getFood(@RequestParam int id) {
        try {
            return foodRepo.getFood(id);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping
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

    @GetMapping
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
