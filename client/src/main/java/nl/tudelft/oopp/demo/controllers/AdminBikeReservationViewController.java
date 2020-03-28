package nl.tudelft.oopp.demo.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.BikeReservationCommunication;
import nl.tudelft.oopp.demo.entities.*;
import nl.tudelft.oopp.demo.views.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class AdminBikeReservationViewController {

    @FXML
    private TableView<BikeReservation> bikeTable;

    @FXML
    private TableColumn<BikeReservation, String> bikeIdColumn;

    @FXML
    private TableColumn<BikeReservation, String> bikeUsernameColumn;

    @FXML
    private TableColumn<BikeReservation, String> bikeBuildingColumn;

    @FXML
    private TableColumn<BikeReservation, String> bikeQuantityColumn;

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

    public AdminBikeReservationViewController() {

    }

    /**
     * Show all the booking in the table.
     */
    @FXML
    private void initialize() {
        try {
            // Initialize the bike reservation table with the six columns.
            bikeIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getBikeReservationId().get())));
            bikeUsernameColumn.setCellValueFactory(cell -> cell.getValue().getBikeReservationUser());
            bikeBuildingColumn.setCellValueFactory(cellData -> new SimpleStringProperty(Building.getBuildingById(cellData.getValue().getBikeReservationBuilding().get()).getBuildingName().get()));
            bikeQuantityColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getBikeReservationQuantity().get())));
            bikeDateColumn.setCellValueFactory(cell -> cell.getValue().getBikeReservationDate());
            bikeStartingTimeColumn.setCellValueFactory(cell -> cell.getValue().getBikeReservationStartingTime());
            bikeEndingTimeColumn.setCellValueFactory(cell -> cell.getValue().getBikeReservationEndingTime());
            bikeTable.setItems(BikeReservation.getBikeReservationData());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * refresh the table when called
     */
    public void refresh() {
        initialize();
    }

    /**
     * Called when admin clicks a bike reservation.
     */
    public BikeReservation getSelectedBikeReservation() {
        if (bikeTable.getSelectionModel().getSelectedIndex() >= 0) {
            BikeReservation br = bikeTable.getSelectionModel().getSelectedItem();
//            if(getSelectedBikeReservationDate(br.getBikeReservationDate().get()).isBefore(LocalDate.now()) || getSelectedBikeReservationTime(br.getBikeReservationStartingTime().get()).isBefore(LocalTime.now())){
//                editBikeButton.setDisable(true);
//                deleteBikeButton.setDisable(true);
//            }
            return br;
        } else {
            return null;
        }
    }

    public int getSelectedIndex() {
        return bikeTable.getSelectionModel().getSelectedIndex();
    }

    /**
     * Delete a bike reservation.
     */
    @FXML
    private void deleteBikeClicked(ActionEvent event) {
        BikeReservation selectedBikeReservation = getSelectedBikeReservation();
        int selectedIndex = getSelectedIndex();
        try {
            if (selectedIndex >= 0) {
                // TODO: Check that bike reservation deletion was successful before displaying alert
                BikeReservationCommunication.deleteBikeReservation(selectedBikeReservation.getBikeReservationId().get());
                refresh();
                // An alert pop up when a reservation deleted successfully
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Delete bike reservation");
                alert.setContentText("Bike reservation deleted!");
                alert.showAndWait();
            } else {
                // An alert pop up when no bike reservation selected
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No Selection");
                alert.setHeaderText("No bike reservation Selected");
                alert.setContentText("Please select a bike reservation in the table.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            System.out.println("delete bike reservation exception");
            e.printStackTrace();
        }
    }

    /**
     * Handles clicking the create new button.
     * Opens a dialog to creat a new reservation.
     */
    @FXML
    private void createNewBikeClicked(ActionEvent event) {
        try {
            // Booking edit dialog pop up.
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentSelectedBikeReservation = null;
            BikeEditDialogController.edit = false;
            BikeEditDialogView view = new BikeEditDialogView();
            view.start(stage);
            // Get the reservation from the pop up dialog.
            BikeReservation tempBikeReservation = BikeEditDialogController.bikeReservation;
            if (tempBikeReservation == null) return;
            // TODO: Check that reservation creation was successful before displaying alert
            BikeReservationCommunication.createBikeReservation(tempBikeReservation.getBikeReservationBuilding().get(), tempBikeReservation.getBikeReservationUser().get(),
                    tempBikeReservation.getBikeReservationQuantity().get(), tempBikeReservation.getBikeReservationDate().get(), tempBikeReservation.getBikeReservationStartingTime().get(), tempBikeReservation.getBikeReservationEndingTime().get());
            refresh();
            // An alert pop up when a new reservation created.
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("New bike reservation");
            alert.setContentText("Added new bike reservation!");
            alert.showAndWait();
        } catch (Exception e) {
            System.out.println("bike reservation creation exception");
            e.printStackTrace();
        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected bike reservation.
     */
    @FXML
    private void editBikeClicked(ActionEvent event) {
        BikeReservation selectedBikeReservation = getSelectedBikeReservation();
        int selectedIndex = getSelectedIndex();
        try {
            if (selectedIndex >= 0) {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentSelectedBikeReservation = selectedBikeReservation;
                BikeEditDialogController.edit = true;
                BikeEditDialogView view = new BikeEditDialogView();
                view.start(stage);
                BikeReservation tempBikeReservation = BikeEditDialogController.bikeReservation;

                if (tempBikeReservation == null) return;
                // TODO: Check that building edit was successful before displaying alert
                BikeReservationCommunication.updateBikeReservation(selectedBikeReservation.getBikeReservationId().get(), tempBikeReservation.getBikeReservationBuilding().get(), selectedBikeReservation.getBikeReservationUser().get(),
                        tempBikeReservation.getBikeReservationQuantity().get(), tempBikeReservation.getBikeReservationDate().get(), tempBikeReservation.getBikeReservationStartingTime().get(), tempBikeReservation.getBikeReservationEndingTime().get());
                refresh();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Edit bike reservation");
                alert.setContentText("edited bike reservation!");
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No Selection");
                alert.setHeaderText("No bike reservation selected");
                alert.setContentText("Please select a bike reservation in the table.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            System.out.println("bike reservation edit exception");
            e.printStackTrace();
        }
    }


    /**
     * Handles clicking the back button, redirect to the admin home page view.
     */
    @FXML
    private void backClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        AdminHomePageView adminHomePageView = new AdminHomePageView();
        adminHomePageView.start(stage);
    }

    /**
     * get the selected bike reservation date
     * @param selectedDateString
     * @return
     */
    private LocalDate getSelectedBikeReservationDate(String selectedDateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //convert String to LocalDate
        LocalDate selectedDate = LocalDate.parse(selectedDateString, formatter);
        return selectedDate;
    }

    /**
     * get the selected bike reservation time
     * @param selectedTimeString
     * @return
     */
    private LocalTime getSelectedBikeReservationTime(String selectedTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        //convert String to LocalTime
        LocalTime selectedTime = LocalTime.parse(selectedTimeString, formatter);
        return selectedTime;
    }

}
