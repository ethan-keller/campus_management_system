package nl.tudelft.oopp.demo.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import nl.tudelft.oopp.demo.model.Table;


public class AllBookingsController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button BackButton;

    @FXML
    private TableView<Table> Table;

    @FXML
    private TableColumn<Table, String> Date;

    @FXML
    private TableColumn<Table, Integer> User;

    @FXML
    private TableColumn<Table, String> Timeslot;

    @FXML
    private TableColumn<Table, String> Building;

    @FXML
    private TableColumn<Table, String> Room;


    //Creating default table data
    final ObservableList<Table> data = FXCollections.observableArrayList(
            new Table("06/03/2020", 12345, "12:00-13:00", "Library", "Edison"),
            new Table("07/03/2020", 1424, "13:00-14:00", "EWI", "Ampere")
    );


    public void initialize(URL location, ResourceBundle resources){
        Date.setCellValueFactory(new PropertyValueFactory<Table, String>("date"));
        User.setCellValueFactory(new PropertyValueFactory<Table, Integer>("user"));
        Timeslot.setCellValueFactory(new PropertyValueFactory<Table, String>("timeslot"));
        Building.setCellValueFactory(new PropertyValueFactory<Table, String>("building"));
        Room.setCellValueFactory(new PropertyValueFactory<Table, String>("room"));
        Table.setItems(data);
    }

}
