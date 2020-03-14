package nl.tudelft.oopp.demo.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.views.RoomView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;


public class SearchViewController {

    @FXML
    public ScrollPane scrollPane;

    @FXML
    public VBox cardHolder;

    @FXML
    public HBox card1;

    @FXML
    public ImageView card1_img;

    @FXML
    public Text card1_title;

    @FXML
    public Text card1_capacity;

    @FXML
    public Text card1_description;

    @FXML
    public TextField textfield;

    @FXML
    private ChoiceBox BuildingChoiceBox;

    @FXML
    private ChoiceBox TimeslotChoiceBox;

    @FXML
    private RadioButton yesCheckBox;

    @FXML
    private RadioButton noCheckBox;

    @FXML
    private ChoiceBox FoodAvailableChoiceBox;

    @FXML
    private ChoiceBox CapacityChoiceBox;

    @FXML
    private Button CancelBookingButton;

    @FXML
    private Button BookingHistoryButton;

    @FXML
    private TextField searchBar;

    @FXML
    private ChoiceBox BikesAvailable;

    public static Room currentSelectedRoom;

    public SearchViewController() {
    }


    @FXML
    private void initialize() {
        try {
            ObservableList<String> TimeslotList = FXCollections.observableArrayList();
            ObservableList<String> CapacityList = FXCollections.observableArrayList();
            ObservableList<String> BuildingList = FXCollections.observableArrayList();
            ObservableList<String> BikeList = FXCollections.observableArrayList();
            ObservableList<String> FoodList = FXCollections.observableArrayList();


            TimeslotList.addAll("8:00 - 9:00", "10:00 - 11:00", "11:00 - 12:00",
                    "12:00 - 13:00", "13:00 - 14:00", "14:00 - 15:00", "15:00 - 16:00",
                    "16:00 - 17:00", "17:00 - 18:00", "18:00 - 19:00", "19:00 - 20:00",
                    "20:00 - 21:00", "21:00 - 22:00", "23:00 - 24:00");
            CapacityList.addAll("1-5", "5-10", "5-20", "20-");
            BuildingList.addAll("Ewi", "Library");
            BikeList.addAll("1-5", "5-10", "10-20", "20-");
            FoodList.addAll("No Food Available", "Food Available");

            //Populating the choicebox
            TimeslotChoiceBox.setItems(TimeslotList);
            CapacityChoiceBox.setItems(CapacityList);
            BuildingChoiceBox.setItems(BuildingList);
            BikesAvailable.setItems(BikeList);
            FoodAvailableChoiceBox.setItems(FoodList);

            ObservableList<Room> roomList = Room.getRoomData();
            for(Room r: roomList){
                cardHolder.getChildren().add(createRoomCard(r));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    public HBox createRoomCard(Room r){
        try {
            // create javafx components
            HBox newCard = new HBox();
            ImageView image = new ImageView();
            VBox room_info = new VBox();
            Text room_title = new Text();
            Text room_capacity = new Text();
            Text room_description = new Text();

            // loading image from URL + setting size & properties
            Image img = new Image(new FileInputStream("C:\\Users\\ethan\\Desktop\\placeholder.png"));
            image.setImage(img);
            image.setPreserveRatio(true);
            image.setPickOnBounds(true);
            image.setFitWidth(300);

            // adding image margin
            newCard.setMargin(image, new Insets(10, 5, 10, 10));

            // setting title and text margin (+ properties)
            room_title.setText(r.getRoomName().get());
            room_title.setWrappingWidth(200);
            room_title.setFont(Font.font("System", FontWeight.BOLD, 18));
            room_title.setStyle("-fx-fill: #0ebaf8;");
            room_info.setMargin(room_title, new Insets(10, 10, 10, 10));

            // setting capacity and text margin (+ properties)
            room_capacity.setText("Capacity: " + r.getRoomCapacity().get());
            room_capacity.setWrappingWidth(200);
            room_capacity.setFont(Font.font("System", 14));
            room_info.setMargin(room_capacity, new Insets(0, 0, 5, 10));

            // setting description and text margin (+ properties)
            room_description.setText("Description: " + r.getRoomDescription().get());
            room_description.setWrappingWidth(310);
            room_description.setFont(Font.font("System", 14));
            room_info.setMargin(room_description, new Insets(0, 0, 0, 10));

            // setting 'text box' size
            room_info.setPrefSize(354, 378);

            // adding components to their corresponding parent
            room_info.getChildren().add(room_title);
            room_info.getChildren().add(room_capacity);
            room_info.getChildren().add(room_description);
            newCard.getChildren().add(image);
            newCard.getChildren().add(room_info);

            // setting size
            newCard.setPrefWidth(688);
            newCard.setPrefHeight(145);

            return newCard;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public Room getSelectedRoom() {
        return null;
    }

    public int getSelectedIndex() {
        return -2;
    }


    /**
     * handles button clicks
     */
    public void CancelBookingClicked(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        //Cancel Booking view not there yet
        ///CancelBookingView cb = new AdminManageRoomView();
        ///cb.start(stage);
    }

    public void BookingHistoryClicked(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        //Booking history view not there yet
        //BookingHistoryView bh = new AdminManageRoomView();
        //bh.start(stage);
    }

    public void ViewRoomButtonClicked(ActionEvent event) {
        //selectedIndex = getSelectedIndex();
        try {
            if (0 >= 0) {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                RoomView rv = new RoomView();
                rv.start(stage);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No Selection");
                alert.setContentText("Please select a room from the table");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }





}
