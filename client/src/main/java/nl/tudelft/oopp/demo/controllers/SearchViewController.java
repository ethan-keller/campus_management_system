package nl.tudelft.oopp.demo.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.views.RoomView;


public class SearchViewController {


    public TextField textfield;
    @FXML
    private ChoiceBox BuildingChoiceBox;

    @FXML
    private ChoiceBox TimeslotChoiceBox;

    @FXML
    private ChoiceBox RoleChoiceBox;

    @FXML
    private ChoiceBox FoodAvailableChoiceBox;

    @FXML
    private ChoiceBox CapacityChoiceBox;

    @FXML
    private Button CancelBookingButton;

    @FXML
    private Button BookingHistoryButton;

    @FXML
    private TextField SearchBar;

    @FXML
    private ChoiceBox BikesAvailable;

    @FXML
    private TableColumn<Room, String> Image;

    @FXML
    private TableColumn<Room, String> RoomName;

    @FXML
    private TableColumn<Room, String> RoomDescription;

    @FXML
    private TableView<Room> RoomTable;

    @FXML
    private Button ViewRoomButton;

    public static Room currentSelectedRoom;

     int selectedIndex = 0;

    public SearchViewController() {
    }


    @FXML
    private void initialize() {

        ObservableList<String> TimeslotList = FXCollections.observableArrayList();
        ObservableList<String> RoleList = FXCollections.observableArrayList();
        ObservableList<String> CapacityList = FXCollections.observableArrayList();
        ObservableList<String> BuildingList = FXCollections.observableArrayList();
        ObservableList<String> BikeList = FXCollections.observableArrayList();
        ObservableList<String> FoodList = FXCollections.observableArrayList();


        TimeslotList.addAll("8:00 - 9:00","10:00 - 11:00","11:00 - 12:00",
                "12:00 - 13:00","13:00 - 14:00","14:00 - 15:00","15:00 - 16:00",
                "16:00 - 17:00","17:00 - 18:00","18:00 - 19:00","19:00 - 20:00",
                "20:00 - 21:00","21:00 - 22:00","23:00 - 24:00");
        RoleList.addAll("Teachers only");
        CapacityList.addAll("1-5", "5-10", "5-20", "20-");
        BuildingList.addAll("Ewi", "Library");
        BikeList.addAll("1-5","5-10", "10-20","20-");
        FoodList.addAll("No Food Available", "Food Available");

        //Populating the choicebox
        TimeslotChoiceBox.setItems(TimeslotList);
        RoleChoiceBox.setItems(RoleList);
        CapacityChoiceBox.setItems(CapacityList);
        BuildingChoiceBox.setItems(BuildingList);
        BikesAvailable.setItems(BikeList);
        FoodAvailableChoiceBox.setItems(FoodList);

        try {
            // Initialize the room table with the three columns.
            Image.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getRoomPhoto().get())));
            RoomName.setCellValueFactory(cell -> cell.getValue().getRoomName());
            RoomDescription.setCellValueFactory(cell -> new SimpleStringProperty("Capacity: "+(cell.getValue().getRoomBuilding().get())+
                    "\n"+cell.getValue().getRoomDescription().get()));


            // Add observable list data to the table
            RoomTable.setItems(Room.getRoomData());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public Room getSelectedRoom() {
        if (RoomTable.getSelectionModel().getSelectedIndex() >= 0) {
            return RoomTable.getSelectionModel().getSelectedItem();
        } else {
            return null;
        }
    }

    public int getSelectedIndex() {
        return RoomTable.getSelectionModel().getSelectedIndex();
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
        selectedIndex = getSelectedIndex();
        try {
            if (selectedIndex >= 0) {
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

    public int getSelectedIndexNumber(){return selectedIndex;}





}
