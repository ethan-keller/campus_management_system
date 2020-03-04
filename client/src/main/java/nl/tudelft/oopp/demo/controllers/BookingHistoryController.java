package nl.tudelft.oopp.demo.controllers;

import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.BookingHistoryCommunication;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.views.SearchView;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookingHistoryController {

    @FXML
    private ListView listRooms;

    public void initialize() {

        /**
         * The regex string seperates all the building objects into multiple tuples. The pattern matcher
         * recognizes the similar pattern of the different building objects. The matcher.find() finds all
         * these objects and groups them.
         * It adds the items to the viewList.
         */
        ObservableList<String> buildings = FXCollections.observableArrayList();
        String regex = "\\{([^}]+)\\}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(BookingHistoryCommunication.getAllBuidings());

        while (matcher.find()) {
            buildings.add(matcher.group());
        }
        listRooms.setItems(buildings);


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
        Building building = gson.fromJson(listRooms.getSelectionModel().getSelectedItems().get(0).toString(), Building.class);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(building.toString());
        alert.showAndWait();
    }

    public void backButtonClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        SearchView sv = new SearchView();
        sv.start(stage);

//
//        int id = 1;
//        BookingHistoryCommunication.getReservations(id);
    }



}
