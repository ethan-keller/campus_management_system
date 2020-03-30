package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import javafx.beans.property.SimpleIntegerProperty;
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
import nl.tudelft.oopp.demo.communication.GeneralMethods;
import nl.tudelft.oopp.demo.entities.Food;
import nl.tudelft.oopp.demo.entities.FoodReservation;
import nl.tudelft.oopp.demo.views.AdminManageReservationView;
import nl.tudelft.oopp.demo.views.AdminUserHistoryView;
import nl.tudelft.oopp.demo.views.FoodReservationEditDialogView;

/**
 * Class that controls the view where the admin can manage food reservations.
 */
public class AdminManageFoodReservationViewController {

    @FXML
    private Label usernameLabel;

    @FXML
    private Label reservationIdLabel;

    @FXML
    private TableView<FoodReservation> foodReservationTable;

    @FXML
    private TableColumn<FoodReservation, Number> foodIdColumn;

    @FXML
    private TableColumn<FoodReservation, String> foodNameColumn;

    @FXML
    private TableColumn<FoodReservation, Number> foodQuantityColumn;


    public static FoodReservation currentSelectedFoodReservation;

    public AdminManageFoodReservationViewController() {

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
            foodIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(
                    cellData.getValue().getFoodId().get()));
            foodNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                    Food.getFoodById(cellData.getValue().getFoodId().get()).getFoodName().get()));
            foodQuantityColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(
                    cellData.getValue().getFoodQuantity().get()));
            foodReservationTable.setItems(FoodReservation.getUserReservationFood());
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
     * Called when admin clicks a food reservation.
     *
     * @return the selected food reservation
     */
    public FoodReservation getSelectedFoodReservation() {
        if (foodReservationTable.getSelectionModel().getSelectedIndex() >= 0) {
            return foodReservationTable.getSelectionModel().getSelectedItem();
        } else {
            return null;
        }
    }

    /**
     * Gets the index of the currently selected iem in the table.
     *
     * @return int the index of the item
     */
    public int getSelectedIndex() {
        return foodReservationTable.getSelectionModel().getSelectedIndex();
    }

    /**
     * Delete a food reservation.
     *
     * @param event the event that triggered this method
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
                // An alert pop up when a reservation deleted successfully
                GeneralMethods.alertBox("Delete food reservation", "",
                        "Food reservation deleted!", Alert.AlertType.INFORMATION);
                refresh();
            } else {
                // An alert pop up when no reservation selected
                GeneralMethods.alertBox("No Selection", "No food Selected",
                        "Please select a food in the table.", Alert.AlertType.WARNING);
            }
        } catch (Exception e) {
            System.out.println("delete food reservation exception");
            e.printStackTrace();
        }
    }

    /**
     * Handles clicking the create new button.
     * Opens a dialog to creat a new food reservation.
     *
     * @param event event that triggered this method
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
            // An alert pop up when a new reservation created.
            GeneralMethods.alertBox("New food reservation", "",
                    "Added new food reservation!", Alert.AlertType.INFORMATION);
            refresh();
        } catch (Exception e) {
            System.out.println("Food reservation creation exception");
            e.printStackTrace();
        }
    }

    /**
     * Handles clicking the back button, redirect to the user reservation history view.
     *
     * @param event evnt that triggered this method
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
