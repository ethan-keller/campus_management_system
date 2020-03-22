package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.encode_hash.CommunicationMethods;
import nl.tudelft.oopp.demo.entities.Item;
import nl.tudelft.oopp.demo.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ItemController {

    @Autowired
    private ItemRepository itemRepo;

    /**
     * Endpoint to get item by id from database
     * @param id id of item to get
     * @return Item
     */
    @GetMapping("getItem")
    @ResponseBody
    public Item getItem(@RequestParam int id){
        try {
            id = Integer.parseInt(CommunicationMethods.decodeCommunication(String.valueOf(id)));
            return itemRepo.getItem(id);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Endpoint to get all items from database
     * @return List of items
     */
    @GetMapping("getAllItems")
    @ResponseBody
    public List<Item> getAllItems(){
        try {
            return itemRepo.getAllItems();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Endpoint to create new item in the database
     * @param user user who the item belongs to
     * @param title title of the item
     * @param date date of the item
     * @param starting_time starting_time of the item
     * @param ending_time ending_time of the item
     * @param description description of the item
     * @return true if success, false otherwise
     */
    @PostMapping("createItem")
    @ResponseBody
    public boolean createItem(@RequestParam String user, @RequestParam String title, @RequestParam String date, @RequestParam String starting_time, @RequestParam String ending_time, @RequestParam String description){
        try {
            user = CommunicationMethods.decodeCommunication(user);
            title = CommunicationMethods.decodeCommunication(title);
            date = CommunicationMethods.decodeCommunication(date);
            starting_time = CommunicationMethods.decodeCommunication(starting_time);
            ending_time = CommunicationMethods.decodeCommunication(ending_time);
            description = CommunicationMethods.decodeCommunication(description);
            itemRepo.insertItem(user, title, date, starting_time, ending_time, description);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Endpoint to delete item from database
     * @param id id of item to delete
     * @return true if success, false otherwise
     */
    @PostMapping("deleteItem")
    @ResponseBody
    public boolean deleteItem(@RequestParam int id){
        try {
            id = Integer.parseInt(CommunicationMethods.decodeCommunication(String.valueOf(id)));
            itemRepo.deleteItem(id);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Endpoint to get id of last inserted item in the database
     * @return id of last inserted item
     */
    @GetMapping("currentId")
    @ResponseBody
    public int getCurrentId(){
        try{
            return itemRepo.getCurrentId();
        } catch (Exception e){
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Enpoint to get all the items of a particular user from the database
     * @param user user to get items from
     * @return List of items
     */
    @GetMapping("getUserItems")
    @ResponseBody
    public List<Item> getUserItems(String user){
        try {
            return itemRepo.getUserItems(user);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
