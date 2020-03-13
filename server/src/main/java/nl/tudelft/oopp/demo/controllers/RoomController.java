package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.encryption.CommunicationMethods;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
public class RoomController {

    @Autowired
    private RoomRepository roomRepo;

    /**
     * Creates a Room entry in the database
     *
     * @param name Name of the room
     * @param building Name of the building (must be an existing building)
     * @param teacher_only True if it's teacher only, False otherwise
     * @param capacity capacity of the room
     * @param photos URL's to photo's of the room
     * @param description Piece of text that describes the room
     * @param type The type of the room (e.g. lecture hall)
     * @throws UnsupportedEncodingException
     */
    @PostMapping("createRoom")
    @ResponseBody
    public void createRoom(@RequestParam String name, @RequestParam int building, @RequestParam boolean teacher_only, @RequestParam int capacity, @RequestParam String photos, @RequestParam String description, @RequestParam String type) throws UnsupportedEncodingException {
        name = CommunicationMethods.decodeCommunication(name);
        photos = CommunicationMethods.decodeCommunication(photos);
        description = CommunicationMethods.decodeCommunication(description);
        type = CommunicationMethods.decodeCommunication(type);
        try{
            roomRepo.insertRoom(name, building, teacher_only, capacity, photos, description, type);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Replaces the values in the database with the provided ones.
     *
     * @param id The ID of the entry to be updated
     * @param name The new room name
     * @param building The new building name (must be an existing building)
     * @param teacher_only The new teacher_only
     * @param capacity The new capacity
     * @param photos New URL's to photos of the room
     * @param description New description
     * @param type New room-type
     * @throws UnsupportedEncodingException
     */
    @PostMapping("updateRoom")
    @ResponseBody
    public void updateRoom(@RequestParam int id, @RequestParam String name, @RequestParam int building, @RequestParam boolean teacher_only, @RequestParam int capacity, @RequestParam String photos, @RequestParam String description, @RequestParam String type) throws UnsupportedEncodingException {
        name = CommunicationMethods.decodeCommunication(name);
        photos = CommunicationMethods.decodeCommunication(photos);
        description = CommunicationMethods.decodeCommunication(description);
        type = CommunicationMethods.decodeCommunication(type);
        try{
            roomRepo.updateCapacity(id, capacity);
            roomRepo.updateDescription(id, description);
            roomRepo.updateBuilding(id, building);
            roomRepo.updateName(id, name);
            roomRepo.updatePhotos(id, photos);
            roomRepo.updateTeacherOnly(id, teacher_only);
            roomRepo.updateType(id, type);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * removes the room with the provided id from the database.
     *
     * @param id The ID of the to be removed room.
     */
    @PostMapping("deleteRoom")
    @ResponseBody
    public void deleteRoom(@RequestParam int id){
        try{
            roomRepo.deleteRoom(id);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Retrieves a room with the specified id.
     *
     * @param id The id of the to be retrieved room.
     * @return //TODO figure out format
     */
    @GetMapping("getRoom")
    @ResponseBody
    public Room getRoom(@RequestParam int id){
        try {
            return roomRepo.getRoom(id);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves all rooms from the database
     *
     * @return Returns a List of //TODO figure out format within the list
     */
    @GetMapping("getAllRooms")
    @ResponseBody
    public List<Room> getAllRooms(){
        try {
            return roomRepo.getAllRooms();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
