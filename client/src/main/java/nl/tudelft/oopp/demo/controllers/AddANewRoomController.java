package nl.tudelft.oopp.demo.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.lang.reflect.Type;

public class AddANewRoomController {

    @FXML
    private ChoiceBox BuildingChoiceBox;

    @FXML
    private TextField BuildingTextField;

    @FXML
    private TextField CapacityTextField;

    @FXML
    private ChoiceBox TypeChoiceBox;

    @FXML
    private TextField RoomNameTextField;

    @FXML
    private TextArea FacilitiesTextField;

    @FXML
    private Spinner BikeSpinner;

    @FXML
    private TextArea FoodAvailableTextField;

    @FXML
    private Button AddButton;

    @FXML
    private Button AddRoomButton;

    public void AddRoomButtonClicked(){
        String room = AddRoomButton.getText();
        BuildingChoiceBox.getItems().add(room);
    }

    public void AddButtonClicked(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if(BuildingChoiceBox==null){
            alert.setTitle("Please select the Building");
            alert.show();
        }
        else if(CapacityTextField == null){
            alert.setTitle("Please enter the capacity");
            alert.show();
        }
        else if(TypeChoiceBox == null){
            alert.setTitle("Please select the Type");
            alert.show();
        }
        else if(BikeSpinner==null){
            alert.setTitle("Please select the number of bikes available");
            alert.show();
        }
        else if(RoomNameTextField==null){
            alert.setTitle("Please provide the name of the building");
            alert.show();
        }
        else{
            //Room room = new Ro
        }
    }


}
