package nl.tudelft.oopp.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.GeneralMethods;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.views.ReservationConfirmationView;
import nl.tudelft.oopp.demo.views.RoomView;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ReserveAndRentController implements Initializable {
    @FXML
    private Text name;
    @FXML
    private Text startingTime;
    @FXML
    private Text endingTime;
    @FXML
    private ImageView image;
    @FXML
    private Text date;
    @FXML
    private Text food;
    @FXML
    private Spinner<Integer> spinner;
    @FXML
    private Text BikesUnselectedError;
    @FXML
    private Button reserveBike;

    public static String RoomDate;
    public static String startTime;
    public static String endTime;
    public static String RoomFood;
    private static Room currentRoom;
    public static int currentRoomId;


    /**
     * Deals with the back button clicks
     */
    @FXML
    void backButtonClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        RoomView rv = new RoomView();
        rv.start(stage);
    }


    /**
     * Receives the values from RoomView and sets them to populate the page
     */
    @FXML
    public void initialize(URL location, ResourceBundle resources){


        date.setText(RoomDate);
        startingTime.setText(startTime);
        endingTime.setText(endTime);
        food.setText(RoomFood);
        currentRoom = Room.getRoomById(currentRoomId);

    }

    /**
     * Deals with the reserve now button clicks
     */
    @FXML
    private void reserveNowClicked(ActionEvent event) {
        ReservationConfirmationViewController.room = currentRoom;
        ReservationConfirmationViewController.date = RoomDate;
        ReservationConfirmationViewController.startTime = startTime;
        ReservationConfirmationViewController.endTime = endTime;
        ReservationConfirmationViewController.bikes=spinner.getValue();


        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        ReservationConfirmationView rcv = new ReservationConfirmationView();
        rcv.start(stage);
    }



}
