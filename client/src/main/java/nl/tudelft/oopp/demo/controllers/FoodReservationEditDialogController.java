package nl.tudelft.oopp.demo.controllers;

import java.util.stream.Collectors;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import nl.tudelft.oopp.demo.communication.GeneralMethods;
import nl.tudelft.oopp.demo.entities.Food;
import nl.tudelft.oopp.demo.entities.FoodReservation;
import nl.tudelft.oopp.demo.entities.Room;

public class FoodReservationEditDialogController {

    @FXML
    private ComboBox<Food> foodComboBox;

    @FXML
    private TextField foodQuantityField;

    private ObservableList<Food> olf;

    public static FoodReservation foodReservation;

    private Stage dialogStage;

    public FoodReservationEditDialogController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        try {
            // Initialize and add listener to the building combobox
            if (AdminUserHistoryViewController.currentSelectedReservation != null) {
                olf = Food.getFoodByBuildingId(Room.getRoomById(
                        AdminUserHistoryViewController.currentSelectedReservation.getRoom().get())
                        .getRoomBuilding().get());
            }
            if (AdminManageReservationViewController.currentSelectedReservation != null) {
                olf = Food.getFoodByBuildingId(Room.getRoomById(
                        AdminManageReservationViewController.currentSelectedReservation.getRoom().get())
                        .getRoomBuilding().get());
            }
            foodComboBox.setItems(olf);
            this.setFoodComboBoxConverter(olf);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Set the food combobox converter.
     *
     * @param olf an observable list of foods.
     */
    public void setFoodComboBoxConverter(ObservableList<Food> olf) {
        StringConverter<Food> converter = new StringConverter<Food>() {
            @Override
            public String toString(Food object) {
                if (object == null) {
                    return "";
                } else {
                    return object.getFoodName().get();
                }
            }

            @Override
            public Food fromString(String id) {
                return olf.stream().filter(x -> String.valueOf(x.getFoodId()) == id)
                        .collect(Collectors.toList()).get(0);
            }
        };
        foodComboBox.setConverter(converter);
    }

    /**
     * Create a new reservation when called.
     */
    private static void emptyFoodReservation() {
        foodReservation = new FoodReservation();
    }

    /**
     * Called when the user clicks ok.
     * @param event is passed
     */
    @FXML
    private void handleOkClicked(ActionEvent event) {
        // Check the validity of user input
        if (isInputValid()) {
            emptyFoodReservation();
            // Set the user input to the reservation
            if (AdminUserHistoryViewController.currentSelectedReservation != null) {
                foodReservation.setReservationId(
                        AdminUserHistoryViewController.currentSelectedReservation.getId().get());
            }
            if (AdminManageReservationViewController.currentSelectedReservation != null) {
                foodReservation.setReservationId(
                        AdminManageReservationViewController.currentSelectedReservation.getId().get());
            }
            foodReservation.setFoodId(this.foodComboBox.getSelectionModel().getSelectedItem().getFoodId().get());
            foodReservation.setFoodQuantity(Integer.parseInt(this.foodQuantityField.getText()));
            // Close the dialog window
            this.dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     * @param event is passed.
     */
    @FXML
    private void handleCancelClicked(ActionEvent event) {
        foodReservation = null;
        // Close the dialog window
        this.dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        dialogStage.close();
    }

    /**
     * Validates the user input.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (foodComboBox.getSelectionModel().getSelectedIndex() < 0) {
            errorMessage += "No valid food selected!\n";
        }
        if (foodQuantityField.getText().equals("")) {
            errorMessage += "No valid food quantity!\n";
        } else {
            try {
                int p = Integer.parseInt(foodQuantityField.getText());
                if (p <= 0) {
                    errorMessage += "No valid food quantity (must be positive integer)!\n";
                }
            } catch (NumberFormatException e) {
                errorMessage += "No valid food quantity (must be an integer)!\n";
            }
        }
        if (errorMessage.equals("")) {
            return true;
        } else {
            // Show the error message.
            GeneralMethods.alertBox("Invalid Fields", "Please correct the invalid fields",
                    errorMessage, Alert.AlertType.ERROR);

            return false;
        }
    }

}
