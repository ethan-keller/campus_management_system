package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.FoodServerCommunication;
import nl.tudelft.oopp.demo.entities.Food;
import nl.tudelft.oopp.demo.entities.FoodReservation;
import nl.tudelft.oopp.demo.views.AdminManageReservationView;
import nl.tudelft.oopp.demo.views.AdminUserHistoryView;
import nl.tudelft.oopp.demo.views.FoodReservationEditDialogView;


public class AdminFoodReservationViewController {

    @FXML
    private Label usernameLabel;

    @FXML
    private Label reservationIdLabel;

    @FXML
    private TableView<FoodReservation> foodReservationTable;

    @FXML
    private TableColumn<FoodReservation, String> foodIdColumn;

    @FXML
    private TableColumn<FoodReservation, String> foodNameColumn;

    @FXML
    private TableColumn<FoodReservation, String> foodQuantityColumn;


    public static FoodReservation currentSelectedFoodReservation;

    public AdminFoodReservationViewController() {

    }

    /**
     * Show all the selected food reservation in the table.
     */
    @FXML
    private void initialize() {
        try {
            // Initialize the title of the table
            if (AdminUserHistoryViewController.currentSelectedReservation != null) {
                usernameLabel.setText(AdminManageUserViewController.currentSelectedUser.getUsername().get());
                reservationIdLabel.setText(String.valueOf(
                        AdminUserHistoryViewController.currentSelectedReservation.getId().get()));
            }
            if (AdminManageReservationViewController.currentSelectedReservation != null) {
                usernameLabel.setText(
                        AdminManageReservationViewController.currentSelectedReservation.getUsername().get());
                reservationIdLabel.setText(String.valueOf(
                        AdminManageReservationViewController.currentSelectedReservation.getId().get()));
            }
            // Initialize the booking table with the three columns.
            foodIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                    String.valueOf(cellData.getValue().getFoodId().get())));
            foodNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                    Food.getFoodById(cellData.getValue().getFoodId().get()).getFoodName().get()));
            foodQuantityColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                    String.valueOf(cellData.getValue().getFoodQuantity().get())));
            foodReservationTable.setItems(FoodReservation.getUserReservationFood());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * refresh the table when called.
     */
    public void refresh() {
        initialize();
    }

    /**
     * Called when admin clicks a food reservation.
     */
    public FoodReservation getSelectedFoodReservation() {
        if (foodReservationTable.getSelectionModel().getSelectedIndex() >= 0) {
            return foodReservationTable.getSelectionModel().getSelectedItem();
        } else {
            return null;
        }
    }

    public int getSelectedIndex() {
        return foodReservationTable.getSelectionModel().getSelectedIndex();
    }

    /**
     * Delete a food reservation.
     */
    @FXML
    private void deleteFoodClicked(ActionEvent event) {
        FoodReservation selectedFoodReservation = getSelectedFoodReservation();
        int selectedIndex = getSelectedIndex();
        try {
            if (selectedIndex >= 0) {
                // TODO: Check that food reservation deletion was successful before displaying alert
                if (AdminUserHistoryViewController.currentSelectedReservation != null) {
                    FoodServerCommunication.deleteFoodFromReservation(
                            selectedFoodReservation.getFoodId().getValue(),
                            AdminUserHistoryViewController.currentSelectedReservation.getId().get());
                }
                if (AdminManageReservationViewController.currentSelectedReservation != null) {
                    FoodServerCommunication.deleteFoodFromReservation(
                            selectedFoodReservation.getFoodId().getValue(),
                            AdminManageReservationViewController.currentSelectedReservation.getId().get());
                }

                refresh();
                // An alert pop up when a reservation deleted successfully
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Delete food reservation");
                alert.setContentText("Food reservation deleted!");
                alert.showAndWait();
            } else {
                // An alert pop up when no reservation selected
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No Selection");
                alert.setHeaderText("No food Selected");
                alert.setContentText("Please select a food in the table.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            System.out.println("delete food reservation exception");
            e.printStackTrace();
        }
    }

    /**
     * Handles clicking the create new button.
     * Opens a dialog to creat a new food reservation.
     */
    @FXML
    private void createNewFoodClicked(ActionEvent event) {
        try {
            // Booking edit dialog pop up.
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentSelectedFoodReservation = null;
            FoodReservationEditDialogView view = new FoodReservationEditDialogView();
            view.start(stage);
            // Get the reservation from the pop up dialog.
            FoodReservation tempReservation = FoodReservationEditDialogController.foodReservation;
            if (tempReservation == null) {
                return;
            }
            // TODO: Check that reservation creation was successful before displaying alert
            if (AdminUserHistoryViewController.currentSelectedReservation != null) {
                FoodServerCommunication.addFoodToReservation(tempReservation.getFoodId().get(),
                        AdminUserHistoryViewController.currentSelectedReservation.getId().get(),
                        tempReservation.getFoodQuantity().get());
            }
            if (AdminManageReservationViewController.currentSelectedReservation != null) {
                FoodServerCommunication.addFoodToReservation(tempReservation.getFoodId().get(),
                        AdminManageReservationViewController.currentSelectedReservation.getId().get(),
                        tempReservation.getFoodQuantity().get());
            }
            refresh();
            // An alert pop up when a new reservation created.
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("New food reservation");
            alert.setContentText("Added new food reservation!");
            alert.showAndWait();
        } catch (Exception e) {
            System.out.println("Food reservation creation exception");
            e.printStackTrace();
        }
    }

    /**
     * Handles clicking the back button, redirect to the user reservation history view.
     */
    @FXML
    private void backClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        if (AdminUserHistoryViewController.currentSelectedReservation != null) {
            AdminUserHistoryView auhv = new AdminUserHistoryView();
            auhv.start(stage);
        }

        if (AdminManageReservationViewController.currentSelectedReservation != null) {
            AdminManageReservationView amrv = new AdminManageReservationView();
            amrv.start(stage);
        }
    }

}
