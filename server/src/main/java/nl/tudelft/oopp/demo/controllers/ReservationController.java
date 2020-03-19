package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.encode_hash.CommunicationMethods;
import nl.tudelft.oopp.demo.entities.Reservations;
import nl.tudelft.oopp.demo.repositories.ReservationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
public class ReservationController {

    @Autowired
    private ReservationsRepository reservationsRepo;

    /**
     * Puts a new reservation in the database.
     *
     * @param username The username of the person reserving.
     * @param room  The room being reserved.
     * @param date  The day the reservation starts //TODO date format
     * @param starting_time The starting time of the reservation //TODO time format
     * @param ending_time The ending time of the reservation //TODO time format
     * @throws UnsupportedEncodingException
     */
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

    /**
     * Replaces the existing info with the newly provided info.
     *
     * @param id The reservation being updated.
     * @param room The new value for room.
     * @param date The new value for date. //TODO date-format
     * @param starting_time The new value for starting_time. //TODO time-format
     * @param ending_time The new value for ending_time. //TODO time-format
     * @throws UnsupportedEncodingException
     */
    @PostMapping("updateReservation")
    @ResponseBody
    public void updateReservation(@RequestParam int id, @RequestParam int room, @RequestParam String date, @RequestParam String starting_time, @RequestParam String ending_time) throws UnsupportedEncodingException {
        date = CommunicationMethods.decodeCommunication(date);
        starting_time = CommunicationMethods.decodeCommunication(starting_time);
        ending_time = CommunicationMethods.decodeCommunication(ending_time);
        try{
            reservationsRepo.updateDate(id, date);
            reservationsRepo.updateRoom(id, room);
            reservationsRepo.updateStartingTime(id, starting_time);
            reservationsRepo.updateEndingTime(id, ending_time);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Reservation gets removed.
     *
     * @param id The ID of the to be removed reservation.
     */
    @PostMapping("deleteReservation")
    @ResponseBody
    public void deleteReservation(@RequestParam int id){
        try{
            reservationsRepo.deleteReservation(id);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the reservation with the provided ID from the database.
     *
     * @param id The ID of the to be retrieved reservation.
     * @return //TODO Format of retrieved data
     */
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

    /**
     * Retrieves all the reservations.
     *
     * @return //TODO format of retrieved data
     */
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

    @GetMapping("getUserReservations")
    @ResponseBody
    public List<Reservations> getUserReservations(@RequestParam String username){
        try{
            return reservationsRepo.getUserReservations(username);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
