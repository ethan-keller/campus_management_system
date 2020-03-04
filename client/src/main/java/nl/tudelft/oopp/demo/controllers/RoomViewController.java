package nl.tudelft.oopp.demo.controllers;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import jdk.jfr.Event;

public class RoomViewController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView RoomImage;

    @FXML
    private DatePicker Datepicker;

    @FXML
    private ChoiceBox TimeslotChoiceBox;

    @FXML
    private ChoiceBox FoodChoiceBox;

    @FXML
    private Button ReserveButton;

    @FXML
    private Text RoomName;

    @FXML
    private Text RoomBuilding;

    @FXML
    private Text RoomCapacity;

    @FXML
    private Text RoomFacilities;

    @FXML
    private Text RoomBikes;

    @FXML
    private Text RoomAvailability;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Adding choices for Timeslot choicebox
        List<String> TimeslotList = new ArrayList<String>();
        TimeslotList.add("8:00 - 9:00");
        TimeslotList.add("9:00 - 10:00");
        TimeslotList.add("10:00 - 11:00");
        TimeslotList.add("11:00 - 12:00");
        TimeslotList.add("12:00 - 13:00");
        TimeslotList.add("13:00 - 14:00");
        TimeslotList.add("14:00 - 15:00");
        TimeslotList.add("15:00 - 16:00");
        TimeslotList.add("16:00 - 17:00");
        TimeslotList.add("17:00 - 18:00");
        TimeslotList.add("18:00 - 19:00");
        TimeslotList.add("19:00 - 20:00");
        TimeslotList.add("20:00 - 21:00");
        TimeslotList.add("21:00 - 22:00");
        TimeslotList.add("22:00 - 23:00");
        TimeslotList.add("23:00 - 24:00");
        ObservableList TimeslotobList = FXCollections.observableList(TimeslotList);
        TimeslotChoiceBox.setItems(TimeslotobList);


        //Adding choices for food choice box
        List<String> FoodList = new ArrayList<String>();
        FoodList.add("Add food here from the database");
        ObservableList FoodobList = FXCollections.observableList(FoodList);
        FoodChoiceBox.setItems(FoodobList);

    }

    public void ReserveNowClicked(Event event){
        if(TimeslotChoiceBox==null || Datepicker == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Please select data and timeslot!");
            alert.show();
        }
        else{
            ///reservation successful
        }
    }

}

