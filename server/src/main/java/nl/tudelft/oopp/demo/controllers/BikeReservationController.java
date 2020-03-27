package nl.tudelft.oopp.demo.controllers;

import java.util.List;
import nl.tudelft.oopp.demo.encodehash.CommunicationMethods;
import nl.tudelft.oopp.demo.entities.BikeReservation;
import nl.tudelft.oopp.demo.repositories.BikeReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class BikeReservationController {

    @Autowired
    private BikeReservationRepository bikeResRepo;

    @Autowired
    private BuildingController buildingControl;


    /**
     * Adds a bike reservation to the database.
     *
     * @param building The Building id to which the bikes belong
     * @param user The username of the user making the reservation
     * @param numBikes The number of bikes reserved
     * @param date The date of the reservation
     * @param startingTime The starting time of the reservation
     * @param endingTime The ending time of the reservation
     */
    @PostMapping("createBikeReservation")
    @ResponseBody
    public void createBikeReservation(@RequestParam int building, @RequestParam String user,
                                      @RequestParam int numBikes, @RequestParam String date,
                                      @RequestParam String startingTime, @RequestParam String endingTime) {
        try {
            user = CommunicationMethods.decodeCommunication(user);
            date = CommunicationMethods.decodeCommunication(date);
            startingTime = CommunicationMethods.decodeCommunication(startingTime);
            endingTime = CommunicationMethods.decodeCommunication(endingTime);
            bikeResRepo.insertBikeReservation(building, user, numBikes, date, startingTime, endingTime);
            buildingControl.addBikeReservation(building, numBikes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates a previously made bike reservation.
     *
     * @param id The id of the reservation
     * @param building The new building of the reservation
     * @param user The new user of the reservation
     * @param numBikes The new number of bikes reserved
     * @param date The new date of the reservation
     * @param startingTime The new starting time of the reservation
     * @param endingTime The new ending time of the reservation
     */
    @PostMapping("updateBikeReservation")
    @ResponseBody
    public void updateBikeReservation(@RequestParam int id, @RequestParam int building,
                                      @RequestParam String user, @RequestParam int numBikes,
                                      @RequestParam String date, @RequestParam String startingTime,
                                      @RequestParam String endingTime) {
        try {
            buildingControl.removeBikeReservation(id);

            user = CommunicationMethods.decodeCommunication(user);
            date = CommunicationMethods.decodeCommunication(date);
            startingTime = CommunicationMethods.decodeCommunication(startingTime);
            endingTime = CommunicationMethods.decodeCommunication(endingTime);
            bikeResRepo.updateBikeNum(id, numBikes);
            bikeResRepo.updateBuilding(id, building);
            bikeResRepo.updateUser(id, user);
            bikeResRepo.updateDate(id, date);
            bikeResRepo.updateStartingTime(id, startingTime);
            bikeResRepo.updateEndingTime(id, endingTime);

            buildingControl.addBikeReservation(building, numBikes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Deletes a bike reservation from the database.
     *
     * @param id The id of the bike reservation
     */
    @PostMapping("deleteBikeReservation")
    @ResponseBody
    public void deleteBikeReservation(@RequestParam int id) {
        try {
            buildingControl.removeBikeReservation(id);
            bikeResRepo.deleteBikeReservation(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Rretreives a bike reservation from the database by id.
     *
     * @param id The id of the bike reservation
     * @return Returns a BikeReservation object
     */
    @GetMapping("getBikeReservation")
    @ResponseBody
    public BikeReservation getBikeReservation(@RequestParam int id) {
        try {
            return bikeResRepo.getBikeReservation(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Retrieves all bike reservations from the database.
     *
     * @return Returns a list of BikeReservations
     */
    @GetMapping("getAllBikeReservation")
    @ResponseBody
    public List<BikeReservation> getBikeReservation() {
        try {
            return bikeResRepo.getAllBikeReservations();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Retrieves all the bike reservations of the bikes that belong to the building of buildingId.
     *
     * @param building The id of the building
     * @return Returns a list of bike reservations.
     */
    @GetMapping("getBuildingBikeReservations")
    @ResponseBody
    public List<BikeReservation> getBuildingBikeReservation(@RequestParam int building) {
        try {
            return bikeResRepo.getBuildingBikeReservations(building);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Retrieves all bike reservations that have been made by a user.
     * @param user The username of the particular user
     * @return Returns a list of bike reservations
     */
    @GetMapping("getUserBikeReservations")
    @ResponseBody
    public List<BikeReservation> getUserBikeReservations(@RequestParam String user) {
        try {
            user = CommunicationMethods.decodeCommunication(user);
            return bikeResRepo.getUserBikeReservations(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
