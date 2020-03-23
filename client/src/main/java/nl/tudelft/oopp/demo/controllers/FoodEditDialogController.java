package nl.tudelft.oopp.demo.controllers;

import javafx.beans.property.SimpleStringProperty;
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
        foodPriceField.setText(String.valueOf((double)Math.round((food.getFoodPrice().get())*100)/100));
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
            food.setFoodPrice(Double.parseDouble(foodPriceField.getText()));
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
                Double d = Double.parseDouble(foodPriceField.getText());
                String[] splitter = d.toString().split("\\.");
                int decimalLength = splitter[1].length();
                if (decimalLength != 2) {
                    errorMessage += "No valid price (must be in two decimals)!\n";
                }

            } catch (NumberFormatException e) {
                errorMessage += "No valid price (must be numbers)!\n";
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