package nl.tudelft.oopp.demo.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import nl.tudelft.oopp.demo.communication.GeneralMethods;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.stream.Collectors;

/**
 * Class that controls the dialog box for admins to create or edit rooms.
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
    private Text fileName;
    @FXML
    private TextField roomDescriptionField;
    @FXML
    private Button uploadRoomPhoto;

    private BufferedImage image;

    public static Room room;

    private Stage dialogStage;

    private String oldFileName;
    private boolean changedImage = false;


    public RoomEditDialogController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        try {
            // set the currently edited room to null
            RoomEditDialogController.room = null;
            // get the room that was selected by the admin
            Room room = AdminManageRoomViewController.currentSelectedRoom;
            // get all the buildings from the database
            ObservableList<Building> ol = Building.getBuildingData();

            // set the chosen file name text to be invisible (no file selected yet)
            fileName.setVisible(false);

            // if there are no buildings (should not be possible) return
            if (ol == null) return;
            // set the buildings in the combobox
            roomBuildingComboBox.setItems(ol);
            // set the converter to only show building name
            this.setRoomBuildingComboBoxConverter(ol);
            // if no room was selected by the admin (he's creating one) stop here
            if (room == null) return;
            // set room info in corresponding fields
            fileName.setText(room.getRoomPhoto().get());
            fileName.setVisible(true);
            roomNameField.setText(room.getRoomName().get());
            roomBuildingComboBox.getSelectionModel().select(ol.stream()
                    .filter(x -> x.getBuildingId().get() == room.getRoomBuilding().get())
                    .collect(Collectors.toList()).get(0));
            if (room.getTeacherOnly().get()) radioButtonYes.setSelected(true);
            else radioButtonNo.setSelected(true);
            roomCapacityField.setText(String.valueOf(room.getRoomCapacity().get()));
            roomTypeField.setText(room.getRoomType().get());
            roomDescriptionField.setText(room.getRoomDescription().get());
            oldFileName = room.getRoomPhoto().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the StringConverter of the buildingComboBox to only show the name of a building.
     *
     * @param ol list of all the buildings in the database
     */
    public void setRoomBuildingComboBoxConverter(ObservableList<Building> ol) {
        // construct the converter
        StringConverter<Building> converter = new StringConverter<Building>() {
            @Override
            public String toString(Building object) {
                if (object == null) return "";
                else return object.getBuildingName().get();
            }

            @Override
            public Building fromString(String id) {
                return ol.stream()
                        .filter(x -> String.valueOf(x.getBuildingId())
                                .equals(id)).collect(Collectors.toList()).get(0);
            }
        };
        // set the converter
        roomBuildingComboBox.setConverter(converter);
    }

    /**
     * Initialize or empty the Room object (when admin creates new room).
     */
    private static void emptyRoom() {
        room = new Room();
    }

    /**
     * Checks if fields are valid and image file anomalies and fills the Room object with the new information.
     * This method also deletes the old image files that are not used anymore.
     *
     * @param event the event that triggered this method
     */
    @FXML
    private void handleOkClicked(ActionEvent event) {
        try {
            // if all fields are valid, fill Room object with the corresponding information given by the admin
            if (isInputValid()) {
                // if the image has been updated delete the old one
                if (changedImage) {
                    // get the image file that is not used anymore
                    File fileToDelete = new File("client/src/main/resources/images/" + oldFileName);
                    // if not deleted properly, show alert
                    if (!fileToDelete.delete()) {
                        Alert alert = GeneralMethods.createAlert("Error"
                                , "Something went wrong, please try again"
                                , dialogStage, Alert.AlertType.ERROR);
                        alert.showAndWait();
                        return;
                    }
                }
                // initialize a new, empty room
                emptyRoom();
                // set all its attributes
                room.setRoomName(this.roomNameField.getText());
                room.setRoomBuilding(this.roomBuildingComboBox.getSelectionModel()
                        .getSelectedItem().getBuildingId().get());
                room.setTeacherOnly(this.radioButtonYes.isSelected());
                room.setRoomCapacity(Integer.parseInt(this.roomCapacityField.getText()));
                room.setRoomType(this.roomTypeField.getText());
                room.setRoomDescription(this.roomDescriptionField.getText());
                room.setRoomPhoto(fileName.getText());

                // if the admin creates a new room or updates an image, save the new file
                if (fileName == null || changedImage) {
                    // split on '.' (dot) to later get the extension of the file (fileName.jpg -> [fileName, jpg])
                    String[] splitFileDot = fileName.getText().split("\\.");
                    // create destination file
                    File newFile = new File("client/src/main/resources/images/" + fileName.getText());
                    // write image to new file with its corresponding extension
                    ImageIO.write(image, splitFileDot[splitFileDot.length - 1], newFile.getAbsoluteFile());
                }

                // get current dialog and close it
                this.dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                dialogStage.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Cancels the room edit/creation and closes the current stage.
     * @param event event that triggered this method
     */
    @FXML
    private void handleCancelClicked(ActionEvent event) {
        // set room to null (for other classes to know that it was canceled)
        room = null;

        // get current stage and close it
        this.dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid, false otherwise
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (roomNameField.getText().equals("")) {
            errorMessage += "No valid room name!\n";
        }
        if (roomBuildingComboBox.getSelectionModel().getSelectedIndex() < 0) {
            errorMessage += "No valid building selected!\n";
        }
        if (!radioButtonYes.isSelected() && !radioButtonNo.isSelected()) {
            errorMessage += "No teacher only button selected!\n";
        }
        if (roomCapacityField.getText().equals("")) {
            errorMessage += "No valid capacity!\n";
        } else {
            try {
                Integer.parseInt(roomCapacityField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid room count (must be an integer)!\n";
            }
        }
        if (roomTypeField.getText().equals("")) {
            errorMessage += "No valid room type!\n";
        }
        if (!fileName.isVisible()) {
            errorMessage += "No image selected!\n";
        }
        // checks if there already exists an image with this name
        File imageFolder = new File("client/src/main/resources/images");
        // if admin creates new room or updates image, check if the image already exists
        if (oldFileName == null || changedImage) {
            for (File f : imageFolder.getAbsoluteFile().listFiles()) {
                if (f.getName().equals(fileName.getText())) {
                    errorMessage += "This file name is already used, please choose another one!\n";
                    break;
                }
            }
        }
        if (roomDescriptionField.getText().equals("")) {
            errorMessage += "No valid room description!\n";
        }

        if (errorMessage.equals("")) {
            return true;
        } else {
            // Show the error message.
            Alert alert = GeneralMethods.createAlert("Please correct the invalid fields"
                    , errorMessage
                    , dialogStage, Alert.AlertType.ERROR);
            alert.showAndWait();
            return false;
        }
    }

    /**
     * Method that opens a file chooser for the admin and reads the selected image.
     * @param event event that triggered this method
     */
    public void uploadImage(ActionEvent event) {
        try {
            // reset attribute
            changedImage = false;
            // create the FileChooser
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Upload image");
            // set file extension filters to only accept images
            chooser.getExtensionFilters().add(
                    new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
            // open file chooser on top of the current stage
            File selectedFile = chooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());

            // if no file was selected, stop here
            if (selectedFile == null) return;

            // split on '\' (backslash) to later get the filename (C:\Images\image.jpg -> [C:, Images, image.jpg]
            String[] splitFileBackSlash = selectedFile.getAbsolutePath().split("\\\\");

            // get filename from backslash split array
            String fileName = splitFileBackSlash[splitFileBackSlash.length - 1];


            // show the admin which file he picked
            this.fileName.setText(fileName);
            this.fileName.setVisible(true);

            if(oldFileName != null && !oldFileName.equals(fileName)) changedImage = true;

            // store the chosen image
            this.image = ImageIO.read(selectedFile);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
