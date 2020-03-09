package nl.tudelft.oopp.demo.controllers;

import com.google.gson.Gson;
import javafx.beans.property.SimpleStringProperty;
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

import nl.tudelft.oopp.demo.entities.Reservation;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.views.SearchView;

import java.io.IOException;
import java.io.PipedReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookingHistoryController {

    @FXML
    private TableView<Reservation> listReservations;
    @FXML
    private TableColumn<Reservation,String> id;
    @FXML
    private TableColumn<Reservation,String> username;
    @FXML
    private  TableColumn<Reservation,String> room;
    @FXML
    private TableColumn<Reservation,String> date;
    @FXML
    private TableColumn<Reservation,String> starting_time;
    @FXML
    private TableColumn<Reservation,String> ending_time;


    public BookingHistoryController() {}

    public void initialize() {
        try {

            //Initializing all the columns created in the table view to inhibit the data passed down through the server.

            id.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getId().get())));
            username.setCellValueFactory(cell -> cell.getValue().getUsername());
            room.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getRoom().get())));
            date.setCellValueFactory(cell -> cell.getValue().getDate());
            starting_time.setCellValueFactory(cell -> cell.getValue().getStarting_time());
            ending_time.setCellValueFactory(cell -> cell.getValue().getEnding_time());

            //Adding the Observable List Data to the tableView created.
            listReservations.setItems(Reservation.getUserReservation());
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void backButtonClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        SearchView sv = new SearchView();
        sv.start(stage);
    }

}
