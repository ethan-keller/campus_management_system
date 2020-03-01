package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class RoomController {

    @Autowired
    private RoomRepository roomRepo;

    @PostMapping("createRoom")
    @ResponseBody
    public void createRoom(@RequestParam String name, @RequestParam int building, @RequestParam boolean teacher_only, @RequestParam int capacity, @RequestParam String photos, @RequestParam String description, @RequestParam String type){
        try{
            roomRepo.insertRoom(name, building, teacher_only, capacity, photos, description, type);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @PostMapping("updateRoom")
    @ResponseBody
    public void updateRoom(@RequestParam int id, @RequestParam String name, @RequestParam int building, @RequestParam boolean teacher_only, @RequestParam int capacity, @RequestParam String photos, @RequestParam String description, @RequestParam String type){
        try{
            roomRepo.updateCapacity(id, capacity);
            roomRepo.updateDescription(id, description);
            roomRepo.updateName(id, name);
            roomRepo.updatePhotos(id, photos);
            roomRepo.updateTeacherOnly(id, teacher_only);
            roomRepo.updateType(id, type);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @PostMapping("deleteRoom")
    @ResponseBody
    public void deleteRoom(@RequestParam int id){
        try{
            roomRepo.deleteRoom(id);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

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
