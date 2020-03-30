package nl.tudelft.oopp.demo.controllers;

import java.util.stream.Collectors;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import nl.tudelft.oopp.demo.communication.GeneralMethods;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.logic.RoomEditDialogLogic;

/**
 * Class that controls dialog box to edit a room.
 */
public class RoomEditDialogController {

    @FXML
    private TextField roomNameField;
    @FXML
    private ComboBox<Building> roomBuildingComboBox;
    @FXML
    private ToggleGroup teacherOnly;
    @FXML
    private RadioButton radioButtonYes;
    @FXML
    private RadioButton radioButtonNo;
    @FXML
    private TextField roomCapacityField;
    @FXML
    private TextField roomTypeField;
    @FXML
    private TextField roomDescriptionField;
    @FXML
    private Button uploadRoomPhoto;

    public static Room room;

    private Stage dialogStage;

    /**
     * Default constructor.
     */
    public RoomEditDialogController() {
    }

    /**
     * .
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        try {
            Room room = AdminManageRoomViewController.currentSelectedRoom;
            ObservableList<Building> ol = Building.getBuildingData();
            roomBuildingComboBox.setItems(ol);
            this.setRoomBuildingComboBoxConverter(ol);

            if (room == null) {
                return;
            }
            roomNameField.setText(room.getRoomName().get());
            if (ol == null) {
                return;
            }
            roomBuildingComboBox.getSelectionModel().select(ol.stream()
                    .filter(x -> x.getBuildingId().get() == room.getRoomBuilding().get())
                    .collect(Collectors.toList()).get(0));
            if (room.getTeacherOnly().get()) {
                radioButtonYes.setSelected(true);
            } else {
                radioButtonNo.setSelected(true);
            }
            roomCapacityField.setText(String.valueOf(room.getRoomCapacity().get()));
            roomTypeField.setText(room.getRoomType().get());
            roomDescriptionField.setText(room.getRoomDescription().get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Converting the buildings into items that can be displayed in a comboBox.
     *
     * @param ol is an observable list of buildings
     */
    public void setRoomBuildingComboBoxConverter(ObservableList<Building> ol) {
        StringConverter<Building> converter = new StringConverter<Building>() {
            @Override
            public String toString(Building object) {
                if (object == null) {
                    return "";
                } else {
                    return object.getBuildingName().get();
                }
            }

            @Override
            public Building fromString(String id) {
                return ol.stream().filter(x -> String.valueOf(x.getBuildingId()).equals(id)).collect(
                        Collectors.toList()).get(0);
            }
        };
        roomBuildingComboBox.setConverter(converter);
    }

    private static void emptyRoom() {
        room = new Room();
    }

    /**
     * .
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOkClicked(ActionEvent event) {
        if (isInputValid()) {
            emptyRoom();
            room.setRoomName(this.roomNameField.getText());
            room.setRoomBuilding(this.roomBuildingComboBox.getSelectionModel()
                    .getSelectedItem().getBuildingId().get());
            room.setTeacherOnly(this.radioButtonYes.isSelected());

            room.setRoomCapacity(Integer.parseInt(this.roomCapacityField.getText()));
            room.setRoomType(this.roomTypeField.getText());
            room.setRoomDescription(this.roomDescriptionField.getText());
            room.setRoomPhoto("test.jpg");

            this.dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            dialogStage.close();
        }
    }


    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancelClicked(ActionEvent event) {
        room = null;
        this.dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        return RoomEditDialogLogic.isValidInput(roomNameField, roomBuildingComboBox, radioButtonYes,
                radioButtonNo, roomCapacityField, roomTypeField, roomDescriptionField);
    }

    public void uploadImage(ActionEvent event) {
    }
}
