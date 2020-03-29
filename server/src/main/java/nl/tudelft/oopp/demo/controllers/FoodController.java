package nl.tudelft.oopp.demo.controllers;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import nl.tudelft.oopp.demo.encodehash.CommunicationMethods;
import nl.tudelft.oopp.demo.entities.Food;
import nl.tudelft.oopp.demo.entities.FoodReservations;
import nl.tudelft.oopp.demo.entities.Reservations;
import nl.tudelft.oopp.demo.repositories.FoodRepository;
import nl.tudelft.oopp.demo.repositories.ReservationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class FoodController {

    @Autowired
    private FoodRepository foodRepo;

    @Autowired
    private ReservationsRepository reservationRepo;

    /**
     * If it receives an HTTP request, it executes the SQL commands to create a food in the database.
     * @param name The name of the new food
     * @param price The price of the new food
     * @throws UnsupportedEncodingException When the decoding does not work
     */
    @PostMapping("createFood")
    @ResponseBody
    public void createFood(@RequestParam String name, @RequestParam double price)
            throws UnsupportedEncodingException {
        name = CommunicationMethods.decodeCommunication(name);
        try {
            foodRepo.insertFood(name, price);
        } catch  (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * If it receives an HTTP request, it executes the SQL commands to<br>
     * create a food to a building in the database.
     * @param food The ID of the food
     * @param building The ID of the building
     */
    @PostMapping("addFoodToBuilding")
    @ResponseBody
    public void addFoodToBuilding(@RequestParam int food, @RequestParam int building) {
        try {
            foodRepo.addFoodToBuilding(food, building);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * If it receives an HTTP request, it executes the SQL commands to create<br>
     * a food to a reservation in the database.
     * @param food The food ID
     * @param reservation The reservation ID
     * @param quantity The quantity
     */
    @PostMapping("addFoodToReservation")
    @ResponseBody
    public void addFoodToReservation(@RequestParam int food, @RequestParam int reservation,
                                     @RequestParam int quantity) {
        try {
            foodRepo.addFoodToReservation(reservation, food, quantity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * If it receives an HTTP request, it executes the SQL commands to update a food in the database.
     * @param id The food ID
     * @param name The new food name
     * @param price The new food price
     * @throws UnsupportedEncodingException Throws when it can't decode the parameters
     */
    @PostMapping("updateFood")
    @ResponseBody
    public void updateFood(@RequestParam int id, @RequestParam String name,
                           @RequestParam double price) throws UnsupportedEncodingException {
        name = CommunicationMethods.decodeCommunication(name);
        try {
            foodRepo.updateName(id, name);
            foodRepo.updatePrice(id, price);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * If it receives an HTTP request, it executes the SQL commands to<br>
     * delete a food from a reservation in the database.
     * @param food The ID of the food to be removed.
     * @param reservation The ID of the reservation to remove the food from
     */
    @PostMapping("deleteFoodFromReservation")
    @ResponseBody
    public void deleteFoodFromReservation(@RequestParam int food, @RequestParam int reservation) {
        try {
            foodRepo.deleteFoodReservation(reservation, food);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * If it receives an HTTP request, it executes the SQL commands to<br>
     * delete a food from a building in the database.
     * @param food The ID of the food
     * @param building The ID of the building
     */
    @PostMapping("deleteFoodFromBuilding")
    @ResponseBody
    public void deleteFoodFromBuilding(@RequestParam int food, @RequestParam int building) {
        try {
            foodRepo.deleteFoodBuilding(building, food);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * If it receives an HTTP request, it executes the SQL commands to update<br>
     * the food quantity for a reservation in the database.
     * @param food The ID of the food
     * @param reservation The ID of the reservation
     * @param quantity The new quantity of the food
     */
    @PostMapping("updateFoodReservationQuantity")
    @ResponseBody
    public void updateFoodReservationQuantity(@RequestParam int food, @RequestParam int reservation,
                                              @RequestParam int quantity) {
        try {
            foodRepo.updateFoodReservationQuantity(reservation, food, quantity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * If it receives an HTTP request, it executes the SQL commands to delete a food in the database.
     * @param id The ID of the food
     */
    @PostMapping("deleteFood")
    @ResponseBody
    public void deleteFood(@RequestParam int id) {
        try {
            foodRepo.deleteFood(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * If it receives an HTTP request, it executes the SQL command to retrieve a food in the database.
     * @param id The food ID
     * @return Returns a Food entity
     */
    @GetMapping("getFood")
    @ResponseBody
    public Food getFood(@RequestParam int id) {
        try {
            return foodRepo.getFood(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * If it receives an HTTP request, it executes the SQL command to<br>
     * retrieve a food in the database based on its name.
     * @param name The food name
     * @return Returns a food entity
     * @throws UnsupportedEncodingException Throws when it can't decode the parameter
     */
    @GetMapping("getFoodByName")
    @ResponseBody
    public Food getFoodByName(@RequestParam String name) throws UnsupportedEncodingException {
        name = CommunicationMethods.decodeCommunication(name);
        try {
            return foodRepo.getFoodByName(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * If it receives an HTTP request, it executes the SQL command to<br>
     * retrieve the foods that are ordered with a reservation based on the reservationID.
     * @param reservation The reservation ID
     * @return Returns a list of Food entities
     */
    @GetMapping("getFoodReservationByReservation")
    @ResponseBody
    public List<FoodReservations> getFoodReservationByReservation(@RequestParam int reservation)  {
        try {
            List<Object[]> result = foodRepo.getFoodReservationByReservationId(reservation);
            return mapFoodReservation(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Maps the query from getFoodByReservation to the proper Objects.
     * @param obj The query result
     * @return Returns a list of FoodReservations
     */
    public List<FoodReservations> mapFoodReservation(List<Object[]> obj) {
        List<FoodReservations> result = new ArrayList<>();
        for (int x = 0; x < obj.size(); x++) {
            Reservations reservation = reservationRepo.getReservation((Integer) obj.get(x)[0]);
            Food food = foodRepo.getFood((Integer) obj.get(x)[1]);
            int quantity = (int)obj.get(x)[2];
            result.add(new FoodReservations(food, reservation, quantity));
        }
        return result;
    }

    /**
     * If it receives an HTTP request, it executes the SQL command to retrieve the foods that<br>
     * are available at a building based on the building ID.
     * @param building The building ID
     * @return Returns a list of Foods
     */
    @GetMapping("getFoodByBuildingId")
    @ResponseBody
    public List<Food> getFoodByBuildingId(@RequestParam int building)  {
        try {
            return foodRepo.getFoodByBuildingId(building);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * If it receives an HTTP request, it executes the SQL command to retrieve the foods that<br>
     *  are available at a building based on the building name.
     * @param name The building name
     * @return Returns a list of Foods
     */
    @GetMapping("getFoodByBuildingName")
    @ResponseBody
    public List<Food> getFoodByBuildingName(@RequestParam String name)  {
        try {
            name = CommunicationMethods.decodeCommunication(name);
            return foodRepo.getFoodByBuildingName(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * If it receives an HTTP request, it executes the SQL command<br>
     * to retrieve all foods from the database.
     * @return Returns a list of Foods
     */
    @GetMapping("getAllFood")
    @ResponseBody
    public List<Food> getAllFood() {
        try {
            return foodRepo.getAllFood();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
