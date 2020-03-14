package nl.tudelft.oopp.demo.controllers;
import java.io.IOException;
import java.net.URL;
import java.util.EventListener;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jdk.jfr.Event;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.views.SearchView;

public class RoomViewController implements Initializable {


    public Text name;
    public Text capacity;
    public Text building;
    public Text teacher_only;
    public Text type;
    public ImageView image;
    public Text description;
    public ComboBox food_choice;
    public ComboBox timeslot;
    public Button bookButton;
    public Button bookBikesButton;
    public DatePicker datePicker;
    public VBox reservationVbox;
    public VBox roomDetailsVbox;

    public static Stage thisStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<String> TimeslotList = FXCollections.observableArrayList();
        ObservableList<String> FoodList = FXCollections.observableArrayList();

        thisStage.widthProperty().addListener((obs, oldVal, newVal) -> {
            changeWidthConstraints(newVal);
        });

        TimeslotList.addAll("8:00 - 9:00","10:00 - 11:00","11:00 - 12:00",
                "12:00 - 13:00","13:00 - 14:00","14:00 - 15:00","15:00 - 16:00",
                "16:00 - 17:00","17:00 - 18:00","18:00 - 19:00","19:00 - 20:00",
                "20:00 - 21:00","21:00 - 22:00","23:00 - 24:00");
        FoodList.addAll("Ham Sandwich", "Cheese Sandwich", "Pasta");

        timeslot.setItems(TimeslotList);
        food_choice.setItems(FoodList);

    }

    public void changeWidthConstraints(Number newWidth){
        image.setFitWidth(newWidth.doubleValue()/1.55);
        image.setFitHeight(newWidth.doubleValue()/2.67062315);
        reservationVbox.setPrefWidth(newWidth.doubleValue()/4.2);
    }

    public void ReserveNowClicked(Event event){
        if(timeslot==null || datePicker == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Please select data and timeslot!");
            alert.show();
        }
        else{
            ///reservation successful
        }
    }

    public void backButtonClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        SearchView sv = new SearchView();
        sv.start(stage);
    }
}

