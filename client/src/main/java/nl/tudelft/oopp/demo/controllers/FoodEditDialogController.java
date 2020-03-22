package nl.tudelft.oopp.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.entities.Food;

public class FoodEditDialogController {

    @FXML
    private TextField foodNameField;
    @FXML
    private TextField foodPriceField;

    public static Food food;

    private Stage dialogStage;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        Food food = AdminManageFoodViewController.currentSelectedFood;
        if (food == null) return;
        foodNameField.setText(food.getFoodName().get());
        foodPriceField.setText(String.valueOf(food.getFoodPrice().get()));
    }

    private static void emptyFood() {
        food = new Food();
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOkClicked(ActionEvent event) {
        if (isInputValid()) {
            emptyFood();
            food.setFoodName(foodNameField.getText());
            food.setFoodPrice(Float.parseFloat(foodPriceField.getText()));
            this.dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancelClicked(ActionEvent event) {
        food = null;
        this.dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (foodNameField.getText().equals("")) {
            errorMessage += "No valid food name!\n";
        }
        if (foodPriceField.getText().equals("")) {
            errorMessage += "No valid price!\n";
        } else {
            try {
                Float.parseFloat(foodPriceField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid price (must be in format x.xx)!\n";
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
