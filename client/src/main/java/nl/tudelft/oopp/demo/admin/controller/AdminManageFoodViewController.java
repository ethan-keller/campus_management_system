package nl.tudelft.oopp.demo.admin.controller;

import java.io.IOException;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.GeneralMethods;
import nl.tudelft.oopp.demo.entities.Food;
import nl.tudelft.oopp.demo.admin.logic.AdminLogic;
import nl.tudelft.oopp.demo.views.AdminFoodBuildingView;
import nl.tudelft.oopp.demo.views.AdminHomePageView;
import nl.tudelft.oopp.demo.views.FoodEditDialogView;


public class AdminManageFoodViewController {

    @FXML
    private TableView<Food> foodTable;

    @FXML
    private TableColumn<Food, Number> foodIdColumn;

    @FXML
    private TableColumn<Food, String> foodNameColumn;

    @FXML
    private TableColumn<Food, Number> foodPriceColumn;

    public static Food currentSelectedFood;

    public AdminManageFoodViewController() {
    }

    /**
     * Show all the food in the table.
     */
    @FXML
    private void initialize() {
        try {
            // Initialize the food table with the three columns.
            foodIdColumn.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getFoodId().get()));
            foodNameColumn.setCellValueFactory(cell -> cell.getValue().getFoodName());
            foodPriceColumn.setCellValueFactory(cell -> new SimpleDoubleProperty(
                    (double)Math.round((cell.getValue().getFoodPrice().get()) * 100) / 100));
            // Add observable list data to the table
            foodTable.setItems(Food.getAllFoodData());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Used to initialize the view everytime something new is created, edited or deleted.
     */
    public void refresh() {
        initialize();
    }

    /**
     * Called when admin clicks a food.
     * @return a Food object
     */
    public Food getSelectedFood() {
        return AdminLogic.getSelectedFood(foodTable);
    }

    /**
     * Gets a number representing the index of the selected food.
     * @return int
     */
    public int getSelectedIndex() {
        return foodTable.getSelectionModel().getSelectedIndex();
    }

    /**
     * Delete a food.
     * @param event event that triggered this method
     */
    @FXML
    private void deleteFoodClicked(ActionEvent event) {
        Food selectedFood = getSelectedFood();
        int selectedIndex = getSelectedIndex();
        try {
            if (selectedIndex >= 0) {
                // TODO: Check that food deletion was successful before displaying alert
                AdminLogic.deleteFoodLogic(selectedFood);
                refresh();
                // An alert pop up when a food deleted successfully
                GeneralMethods.alertBox("Delete food", "",
                        "Food deleted!", AlertType.INFORMATION);
            } else {
                // An alert pop up when no food selected
                GeneralMethods.alertBox("No Selection", "No Food Selected",
                        "Please select a food in the table.", Alert.AlertType.WARNING);
            }
        } catch (Exception e) {
            System.out.println("delete food exception");
            e.printStackTrace();
        }
    }

    /**
     * Handles clicking the create new button.Opens a dialog to edit
     * details for the new food.
     * @param event is passed
     */
    @FXML
    private void createNewFoodClicked(ActionEvent event) {
        try {
            // Food edit dialog pop up.
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentSelectedFood = null;
            FoodEditDialogView view = new FoodEditDialogView();
            view.start(stage);
            // Get the food from the pop up dialog.
            Food tempFood = FoodEditDialogController.food;
            if (tempFood == null) {
                return;
            }
            // TODO: Check that building creation was successful before displaying alert
            AdminLogic.addFoodLogic(tempFood);
            refresh();
            // An alert pop up when a new food created.
            GeneralMethods.alertBox("New food", "", "New Food added!", AlertType.INFORMATION);
        } catch (Exception e) {
            System.out.println("food creation exception");
            e.printStackTrace();
        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected food.
     * @param event is passed
     */
    @FXML
    private void editFoodClicked(ActionEvent event) {
        Food selectedFood = getSelectedFood();
        int selectedIndex = getSelectedIndex();
        try {
            // Food edit dialog pop up.
            if (selectedIndex >= 0) {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentSelectedFood = selectedFood;
                FoodEditDialogView view = new FoodEditDialogView();
                view.start(stage);
                // Get the food from the pop up dialog.
                Food tempFood = FoodEditDialogController.food;
                if (tempFood == null) {
                    return;
                }
                // TODO: Check that building edit was successful before displaying alert
                AdminLogic.updateFoodLogic(selectedFood, tempFood);
                refresh();
                // An alert pop up when a new food created.
                GeneralMethods.alertBox("Edit food", "", "Food edited!", AlertType.INFORMATION);
            } else {
                // An alert pop up when no food selected
                GeneralMethods.alertBox("No Selection", "No Food Selected",
                        "Please select a food in the table.", Alert.AlertType.WARNING);
            }
        } catch (Exception e) {
            System.out.println("food edit exception");
            e.printStackTrace();
        }
    }

    /**
     * Show all the buildings which provide this food in a table.
     * @param event is passed.
     * @throws IOException is thrown.
     */
    @FXML
    private void foodBuildingClicked(ActionEvent event) throws IOException {
        Food selectedFood = getSelectedFood();
        int selectedIndex = getSelectedIndex();
        try {
            // Switch to the building table of selected food
            if (selectedIndex >= 0) {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentSelectedFood = selectedFood;

                AdminFoodBuildingView afbv = new AdminFoodBuildingView();
                afbv.start(stage);
            } else {
                // An alert pop up when no food selected
                GeneralMethods.alertBox("No Selection", "No Food Selected",
                        "Please select a food in the table.", Alert.AlertType.WARNING);
            }
        } catch (Exception e) {
            System.out.println("food edit exception");
            e.printStackTrace();
        }
    }

    /**
     * Handles clicking the back button, redirect to the admin home page.
     * @param event is passed
     * @throws IOException is thrown
     */
    @FXML
    private void backClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        AdminHomePageView ahpv = new AdminHomePageView();
        ahpv.start(stage);
    }

}
