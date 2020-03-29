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
import nl.tudelft.oopp.demo.entities.Food;
import nl.tudelft.oopp.demo.entities.FoodReservation;

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
            olf = Food.getAllFoodData();
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
                Integer.parseInt(foodQuantityField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid food quantity (must be an integer)!\n";
            }
        }
        if (errorMessage.equals("")) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);
            alert.showAndWait();

            return false;
        }
    }

}
