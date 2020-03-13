package nl.tudelft.oopp.demo.controllers;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
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
    private Text RoomBikes;

    @FXML
    private Text RoomAvailability;


    public  void setRoomBuilding(String building){
        RoomBuilding.setText("Building: "+building);
    }
    public void setRoomCapacity(int capacity){
        RoomCapacity.setText("Capacity: "+capacity);
    }
    public void setRoomBikes(int bikes){
        RoomBikes.setText("Bikes available: "+bikes);
    }
    public void setRoomAvailability(boolean boo){
        if(boo){
            RoomAvailability.setText("Teachers and Students");
        }
        else{
            RoomAvailability.setText("Teachers only");
        }
    }
    public void setImage(Image pic){
        RoomImage.setImage(pic);
    }









    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<String> TimeslotList = FXCollections.observableArrayList();
        ObservableList<String> FoodList = FXCollections.observableArrayList();


        TimeslotList.addAll("8:00 - 9:00","10:00 - 11:00","11:00 - 12:00",
                "12:00 - 13:00","13:00 - 14:00","14:00 - 15:00","15:00 - 16:00",
                "16:00 - 17:00","17:00 - 18:00","18:00 - 19:00","19:00 - 20:00",
                "20:00 - 21:00","21:00 - 22:00","23:00 - 24:00");
        FoodList.addAll("Ham Sandwich", "Cheese Sandwich", "Pasta");

        TimeslotChoiceBox.setItems(TimeslotList);
        FoodChoiceBox.setItems(FoodList);



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

