package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.encryption.CommunicationMethods;
import nl.tudelft.oopp.demo.entities.Reservations;
import nl.tudelft.oopp.demo.repositories.ReservationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
public class ReservationController {

    @Autowired
    private ReservationsRepository reservationsRepo;

    @PostMapping("createReservation")
    @ResponseBody
    public void createReservation(@RequestParam String username, @RequestParam int room, @RequestParam String date, @RequestParam String starting_time, @RequestParam String ending_time) throws UnsupportedEncodingException {
        username = CommunicationMethods.decodeCommunication(username);
        date = CommunicationMethods.decodeCommunication(date);
        starting_time = CommunicationMethods.decodeCommunication(starting_time);
        ending_time = CommunicationMethods.decodeCommunication(ending_time);
        try{
            reservationsRepo.insertReservation(username, room, date, starting_time, ending_time);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @PostMapping("updateReservation")
    @ResponseBody
    public void updateReservation(@RequestParam int id, @RequestParam String username, @RequestParam int room, @RequestParam String date, @RequestParam String starting_time, @RequestParam String ending_time) throws UnsupportedEncodingException {
        date = CommunicationMethods.decodeCommunication(date);
        starting_time = CommunicationMethods.decodeCommunication(starting_time);
        ending_time = CommunicationMethods.decodeCommunication(ending_time);
        try{
            reservationsRepo.updateDate(id, date);
            reservationsRepo.updateUsername(id, username);
            reservationsRepo.updateRoom(id, room);
            reservationsRepo.updateStartingTime(id, starting_time);
            reservationsRepo.updateEndingTime(id, ending_time);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @PostMapping("deleteReservation")
    @ResponseBody
    public void deleteReservation(@RequestParam int id){
        try{
            reservationsRepo.deleteReservation(id);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @GetMapping("getReservation")
    @ResponseBody
    public Reservations getReservation(@RequestParam int id){
        try {
            return reservationsRepo.getReservation(id);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("getAllReservations")
    @ResponseBody
    public List<Reservations> getAllReservations(){
        try {
            return reservationsRepo.getAllReservations();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
