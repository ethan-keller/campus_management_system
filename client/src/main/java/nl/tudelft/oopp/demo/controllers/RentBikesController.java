package nl.tudelft.oopp.demo.controllers;

import javafx.beans.Observable;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.views.RoomView;
import nl.tudelft.oopp.demo.views.SearchView;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class RentBikesController {

    @FXML
    private VBox roomDetailsVbox;

    @FXML
    private Text name;

    @FXML
    private Text capacity;

    @FXML
    private Text building;

    @FXML
    private Text teacher_only;

    @FXML
    private Text type;

    @FXML
    private ImageView image;

    @FXML
    private VBox reservationVbox;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Text dateError;

    @FXML
    private Text startTime;

    @FXML
    private Text endTime;

    @FXML
    private ComboBox<Building> Building_choice;

    @FXML
    private Text foodError;

    @FXML
    private Spinner<Integer> NumberPicker;

    @FXML
    private Text foodError1;

    @FXML
    void ReserveNowButtonClicked(ActionEvent event) {

    }

    @FXML
    void backButtonClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        SearchView sv = new SearchView();
        sv.start(stage);
    }
    @FXML
    private void initialize(){
        try{
            ObservableList<StringProperty> BuildingList = FXCollections.observableArrayList();

            ObservableList<Building> buildingList = Building.getBuildingData();
            for(Building b: buildingList){
                BuildingList.add(b.getBuildingName());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
