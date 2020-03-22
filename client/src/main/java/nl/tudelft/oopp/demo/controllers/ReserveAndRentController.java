package nl.tudelft.oopp.demo.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.views.ReservationConfirmationView;
import nl.tudelft.oopp.demo.views.RoomView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ReserveAndRentController implements Initializable {
    @FXML
    private Text name;
    @FXML
    private Text startingTime;
    @FXML
    private Text endingTime;
    @FXML
    private Text RoomInfo;
    @FXML
    private ImageView image;
    @FXML
    private Text date;
    @FXML
    private Text food;
    @FXML
    private Text capacity;
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
        assert currentRoom != null;
        int buildingNum =  currentRoom.getRoomBuilding().get();
        Building b = Building.getBuildingById(buildingNum);

        assert b != null;
        String text = "Number of Available bikes\n in "+b.toString()+": "+b.getBuildingAvailable_bikes().get();
        capacity.setFill(Color.WHITE);
        capacity.setFont(Font.font ("System", 14));
        capacity.setText(text);

        String RoomText = currentRoom.getRoomName().get()+"("+b.getBuildingName().get()+")";
        RoomInfo.setText(RoomText);

    }

    /**
     * Deals with the reserve now button clicks
     */
    @FXML
    private void reserveNowClicked(ActionEvent event) {
        int buildingNumber = currentRoom.getRoomBuilding().get();
        Building building = Building.getBuildingById(buildingNumber);

        assert building != null;
        if(checkBikeAvailability(building.toString(), spinner.getValue())){
            ReservationConfirmationViewController.room = currentRoom;
            ReservationConfirmationViewController.date = RoomDate;
            ReservationConfirmationViewController.startTime = startTime;
            ReservationConfirmationViewController.endTime = endTime;
            ReservationConfirmationViewController.bikes=spinner.getValue();


            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            ReservationConfirmationView rcv = new ReservationConfirmationView();
            rcv.start(stage);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Insufficient Bikes");
            alert.setContentText("Insufficient Bikes Available. Please check the number of bikes available");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
            alert.showAndWait();
        }
    }

    private Boolean checkBikeAvailability(String buildingName, int num){
        ObservableList<Building> buildingList = Building.getBuildingData();
        Building building= null;
        for (Building b : buildingList) {
            if (b.getBuildingName().get().equals(buildingName)) {
                building = b;
                break;
            }
        }

        assert building != null;
        return building.getBuildingAvailable_bikes().get() - num >= 0;
    }



}
