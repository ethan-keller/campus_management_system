package nl.tudelft.oopp.demo.controllers;

import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.AdminManageServerCommunication;
import nl.tudelft.oopp.demo.entities.Reservation;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.views.SearchView;

import java.io.IOException;
import java.io.PipedReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookingHistoryController {

    @FXML
    private TableView<Reservation> listRooms;
    @FXML
    private TableColumn<Reservation,Integer> id;
    @FXML
    private TableColumn<Reservation,String> username;
    @FXML
    private  TableColumn<Reservation, Integer> room;
    @FXML
    private TableColumn<Reservation,String> date;
    @FXML
    private TableColumn<Reservation,String> starting_time;
    @FXML
    private TableColumn<Reservation,String> ending_time;



    public void initialize() {

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        room.setCellValueFactory(new PropertyValueFactory<>("room"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        starting_time.setCellValueFactory(new PropertyValueFactory<>("starting_time"));
        ending_time.setCellValueFactory(new PropertyValueFactory<>("ending_time"));
        /**
         * The regex string seperates all the building objects into multiple tuples. The pattern matcher
         * recognizes the similar pattern of the different building objects. The matcher.find() finds all
         * these objects and groups them.
         * It adds the items to the viewList.
         */
        ObservableList<Reservation> reservations = Reservation.getReservation();
        listRooms.setItems(reservations);
        //ObservableList<String> rooms = FXCollections.observableArrayList();
//        String regex = "\\{([^}]+)\\}";
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(AdminManageServerCommunication.getAllReservations().);
//
//        while (matcher.find()) {
//            reservations.add(matcher.group());
//        }
//        listRooms.setItems(rooms.);


        //Test code to check if the database has any sort of reservations present in itself at all.
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setContentText(rooms.toString());
//        alert.showAndWait();


//        buildings.add(BookingHistoryCommunication.getAllBuidings());
//        JSONArray arrary = new JSONArray(BookingHistoryCommunication.getAllBuidings());
//        //Building building = gson.fromJson(BookingHistoryCommunication.getAllBuidings(), Building.class);
//        Building building = (Building) arrary.get(0);
//        buildings.add(building.toString());
//        listRooms.setItems(buildings);
    }

    /**
     * Get the selected item
     * Parse it into a building
     * toString() to display it as a string.
     */
    public void testButtonClicked() {
        Gson gson = new Gson();
        Room rooms = gson.fromJson(listRooms.getSelectionModel().getSelectedItems().get(0).toString(), Room.class);
//        Object rooms = new Object();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(rooms.toString());
        alert.showAndWait();
//        listRooms.setItems(Room.getRoomData().sorted());
    }

    public void backButtonClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        SearchView sv = new SearchView();
        sv.start(stage);


//        int id = 1;
//        BookingHistoryCommunication.getReservations(id);
    }

}
