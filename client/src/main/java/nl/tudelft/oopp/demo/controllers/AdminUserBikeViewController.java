package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.BikeReservationCommunication;
import nl.tudelft.oopp.demo.communication.GeneralMethods;
import nl.tudelft.oopp.demo.entities.BikeReservation;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.views.AdminManageUserView;
import nl.tudelft.oopp.demo.views.UserBikeEditDialogView;

public class AdminUserBikeViewController {

    @FXML
    private Label usernameLabel;

    @FXML
    private TableView<BikeReservation> userBikeTable;

    @FXML
    private TableColumn<BikeReservation, Number> bikeIdColumn;

    @FXML
    private TableColumn<BikeReservation, String> bikeBuildingColumn;

    @FXML
    private TableColumn<BikeReservation, Number> bikeQuantityColumn;

    @FXML
    private TableColumn<BikeReservation, String> bikeDateColumn;

    @FXML
    private TableColumn<BikeReservation, String> bikeStartingTimeColumn;

    @FXML
    private TableColumn<BikeReservation, String> bikeEndingTimeColumn;

    @FXML
    private Button editBikeButton;

    @FXML
    private Button deleteBikeButton;

    public static BikeReservation currentSelectedBikeReservation;

    public AdminUserBikeViewController() {

    }

    /**
     * Show all the booking in the table.
     */
    @FXML
    private void initialize() {
        try {
            ObservableList<Building> buildingList = Building.getBuildingData();
            usernameLabel.setText(AdminManageUserViewController.currentSelectedUser.getUsername().get());
            // Initialize the bike reservation table with the five columns.
            bikeIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(
                    cellData.getValue().getBikeReservationId().get()));
            bikeBuildingColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                    buildingList.stream().filter(x -> x.getBuildingId().get()
                            == cellData.getValue().getBikeReservationBuilding().get())
                            .collect(Collectors.toList()).get(0).getBuildingName().get()
            ));
            bikeQuantityColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(
                    cellData.getValue().getBikeReservationQuantity().get()));
            bikeDateColumn.setCellValueFactory(cell -> cell.getValue().getBikeReservationDate());
            bikeStartingTimeColumn.setCellValueFactory(cell -> cell.getValue().getBikeReservationStartingTime());
            bikeEndingTimeColumn.setCellValueFactory(cell -> cell.getValue().getBikeReservationEndingTime());
            userBikeTable.setItems(BikeReservation.getUserBikeReservations(
                    AdminManageUserViewController.currentSelectedUser.getUsername().get()));
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
     * Called when admin clicks a bike reservation.
     * @return the bike reservation that is currently selected
     */
    public BikeReservation getSelectedBikeReservation() {
        if (userBikeTable.getSelectionModel().getSelectedIndex() >= 0) {
            BikeReservation br = userBikeTable.getSelectionModel().getSelectedItem();
            return br;
        } else {
            return null;
        }
    }

    /**
     * Gets a number representing the index of the selected bike reservation.
     * @return int
     */
    public int getSelectedIndex() {
        return userBikeTable.getSelectionModel().getSelectedIndex();
    }

    /**
     * Delete a bike reservation.
     * @param event event that triggered this method
     */
    @FXML
    private void deleteBikeClicked(ActionEvent event) {
        BikeReservation selectedBikeReservation = getSelectedBikeReservation();
        int selectedIndex = getSelectedIndex();
        try {
            if (selectedIndex >= 0) {
                // TODO: Check that bike reservation deletion was successful before displaying alert
                BikeReservationCommunication.deleteBikeReservation(
                        selectedBikeReservation.getBikeReservationId().getValue());
                refresh();
                // An alert pop up when a reservation deleted successfully
                GeneralMethods.alertBox("Delete bike reservation", "",
                        "Bike reservation deleted!", Alert.AlertType.INFORMATION);
            } else {
                // An alert pop up when no bike reservation selected
                GeneralMethods.alertBox("No Selection", "No Bike Reservation Selected",
                        "Please select a bike reservation in the table.", Alert.AlertType.WARNING);
            }
        } catch (Exception e) {
            System.out.println("delete bike reservation exception");
            e.printStackTrace();
        }
    }

    /**
     * Handles clicking the create new button.
     * Opens a dialog to creat a new reservation.
     * @param event is passed
     */
    @FXML
    private void createNewBikeClicked(ActionEvent event) {
        try {
            // Booking edit dialog pop up.
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentSelectedBikeReservation = null;
            UserBikeEditDialogController.edit = false;
            UserBikeEditDialogView view = new UserBikeEditDialogView();
            view.start(stage);
            // Get the reservation from the pop up dialog.
            BikeReservation tempBikeReservation = UserBikeEditDialogController.bikeReservation;
            if (tempBikeReservation == null) {
                return;
            }
            // TODO: Check that reservation creation was successful before displaying alert
            BikeReservationCommunication.createBikeReservation(
                    tempBikeReservation.getBikeReservationBuilding().get(),
                    AdminManageUserViewController.currentSelectedUser.getUsername().get(),
                    tempBikeReservation.getBikeReservationQuantity().get(),
                    tempBikeReservation.getBikeReservationDate().get(),
                    tempBikeReservation.getBikeReservationStartingTime().get(),
                    tempBikeReservation.getBikeReservationEndingTime().get());
            refresh();
            // An alert pop up when a new reservation created.
            GeneralMethods.alertBox("New bike reservation", "",
                    "Successfully added new bike reservation!", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            System.out.println("bike reservation creation exception");
            e.printStackTrace();
        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected bike reservation.
     * @param event event that triggered this method
     */
    @FXML
    private void editBikeClicked(ActionEvent event) {
        BikeReservation selectedBikeReservation = getSelectedBikeReservation();
        int selectedIndex = getSelectedIndex();
        try {
            if (selectedIndex >= 0) {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentSelectedBikeReservation = selectedBikeReservation;
                UserBikeEditDialogController.edit = true;
                UserBikeEditDialogView view = new UserBikeEditDialogView();
                view.start(stage);
                BikeReservation tempBikeReservation = UserBikeEditDialogController.bikeReservation;

                if (tempBikeReservation == null) {
                    return;
                }
                // TODO: Check that reservation edit was successful before displaying alert
                BikeReservationCommunication.updateBikeReservation(
                        selectedBikeReservation.getBikeReservationId().get(),
                        tempBikeReservation.getBikeReservationBuilding().get(),
                        AdminManageUserViewController.currentSelectedUser.getUsername().get(),
                        tempBikeReservation.getBikeReservationQuantity().get(),
                        tempBikeReservation.getBikeReservationDate().get(),
                        tempBikeReservation.getBikeReservationStartingTime().get(),
                        tempBikeReservation.getBikeReservationEndingTime().get());
                refresh();
                // Creates an alert box to display the message.
                GeneralMethods.alertBox("Edit bike reservation", "",
                        "Bike Reservation edited!", Alert.AlertType.INFORMATION);
            } else {
                // Creates an alert box.
                GeneralMethods.alertBox("No Selection", "No Bike Reservation Selected",
                        "Please select a bike reservation in the table.", Alert.AlertType.WARNING);
            }
        } catch (Exception e) {
            System.out.println("bike reservation edit exception");
            e.printStackTrace();
        }
    }


    /**
     * Handles clicking the back button, redirect to the admin home page view.
     * @param event is passed
     * @throws IOException is thrown
     */
    @FXML
    private void backClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        AdminManageUserView amuv = new AdminManageUserView();
        amuv.start(stage);
    }

}
