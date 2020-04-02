package nl.tudelft.oopp.demo.admin.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
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
import nl.tudelft.oopp.demo.entities.Reservation;
import nl.tudelft.oopp.demo.admin.logic.AdminLogic;
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
            Reservation roomReservation = this.getReservation();
            // Initialize the title of the table
            usernameLabel.setText(roomReservation.getUsername().get());
            reservationIdLabel.setText(String.valueOf(roomReservation.getId().get()));
            // Initialize the booking table with the three columns.
            foodIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(
                    cellData.getValue().getFoodId().get()));
            ObservableList<Food> foodObservableList = Food.getAllFoodData();
            foodNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                    foodObservableList.stream().filter(x -> x.getFoodId().get()
                            == cellData.getValue().getFoodId().get())
                            .collect(Collectors.toList()).get(0).getFoodName().get()));
            foodQuantityColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(
                    cellData.getValue().getFoodQuantity().get()));
            foodReservationTable.setItems(FoodReservation.getUserReservationFood(roomReservation));
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
        return AdminLogic.getSelectedFoodReservation(foodReservationTable);
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
     * Get the original room reservation.
     *
     * @return Reservation
     */
    public Reservation getReservation() {
        Reservation r = new Reservation();
        if (AdminUserHistoryViewController.currentSelectedReservation != null) {
            r = AdminUserHistoryViewController.currentSelectedReservation;
        }
        if (AdminManageReservationViewController.currentSelectedReservation != null) {
            r =  AdminManageReservationViewController.currentSelectedReservation;
        }
        return r;
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
                FoodServerCommunication.deleteFoodFromReservation(selectedFoodReservation.getFoodId().getValue(),
                        this.getReservation().getId().get());
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
            // TODO: Check that food reservation creation was successful before displaying alert
            if (this.getAllFoodInReservation(getReservation())
                    .contains(Food.getFoodById(tempReservation.getFoodId().get()))) {
                FoodServerCommunication.updateFoodReservationQuantity(tempReservation.getFoodId().get(),
                    this.getReservation().getId().get(), (tempReservation.getFoodQuantity().get()
                        + this.getFoodOldQuantity(tempReservation.getFoodId().get(), getReservation())));
            } else {
                FoodServerCommunication.addFoodToReservation(tempReservation.getFoodId().get(),
                        this.getReservation().getId().get(), tempReservation.getFoodQuantity().get());
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

    /**
     * Return the quantity of current food reservation.
     * @param foodId id
     * @return the quantity of food
     */
    private int getFoodOldQuantity(int foodId, Reservation r) {
        ObservableList<FoodReservation> foodReservationObservableList
                = FoodReservation.getUserReservationFood(r);
        List<FoodReservation> filteredFoodReservation = foodReservationObservableList.stream()
                .filter(x -> x.getFoodId().get() == foodId).collect(Collectors.toList());
        FoodReservation foodReservation = filteredFoodReservation.get(0);
        return foodReservation.getFoodQuantity().get();
    }

    /**
     * Return a list of foods in the reservations.
     * @return A list of foods
     */
    private List<Food> getAllFoodInReservation(Reservation r) {
        ObservableList<FoodReservation> foodReservationObservableList = FoodReservation.getUserReservationFood(r);
        ObservableList<Food> foodObservableList = Food.getAllFoodData();
        List<Food> foods = new ArrayList<>();
        for (FoodReservation foodReservation : foodReservationObservableList) {
            int id = foodReservation.getFoodId().get();
            foods.add(foodObservableList.stream().filter(x -> x.getFoodId().get() == id)
                    .collect(Collectors.toList()).get(0));
        }
        return foods;
    }

}
