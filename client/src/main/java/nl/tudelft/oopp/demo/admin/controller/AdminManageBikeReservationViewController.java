package nl.tudelft.oopp.demo.admin.controller;

import java.io.IOException;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.BikeReservationCommunication;
import nl.tudelft.oopp.demo.communication.GeneralMethods;
import nl.tudelft.oopp.demo.entities.BikeReservation;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.views.AdminHomePageView;
import nl.tudelft.oopp.demo.views.BikeEditDialogView;
import nl.tudelft.oopp.demo.views.BikeNewDialogView;


public class AdminManageBikeReservationViewController {

    @FXML
    private TableView<BikeReservation> bikeTable;

    @FXML
    private TableColumn<BikeReservation, Number> bikeIdColumn;

    @FXML
    private TableColumn<BikeReservation, String> bikeUsernameColumn;

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

    public AdminManageBikeReservationViewController() {

    }

    /**
     * Show all the booking in the table.
     */
    @FXML
    private void initialize() {
        try {
            ObservableList<Building> buildingList = Building.getBuildingData();
            // Initialize the bike reservation table with the six columns.
            bikeIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(
                    cellData.getValue().getBikeReservationId().get()));
            bikeUsernameColumn.setCellValueFactory(cell -> cell.getValue().getBikeReservationUser());
            bikeBuildingColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                    buildingList.stream().filter(x -> x.getBuildingId().get()
                            == cellData.getValue().getBikeReservationBuilding().get())
                            .collect(Collectors.toList()).get(0).getBuildingName().get()
            ));
            bikeQuantityColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(
                    cellData.getValue().getBikeReservationQuantity().get()));
            // To align the text in this column in a centralized manner; looks better
            bikeQuantityColumn.setStyle("-fx-alignment: CENTER");

            bikeDateColumn.setCellValueFactory(cell -> cell.getValue().getBikeReservationDate());
            // To align the text in this column in a centralized manner; looks better
            bikeDateColumn.setStyle("-fx-alignment: CENTER");

            bikeStartingTimeColumn.setCellValueFactory(cell -> cell.getValue().getBikeReservationStartingTime());
            // To align the text in this column in a centralized manner; looks better
            bikeStartingTimeColumn.setStyle("-fx-alignment: CENTER");

            bikeEndingTimeColumn.setCellValueFactory(cell -> cell.getValue().getBikeReservationEndingTime());
            // To align the text in this column in a centralized manner; looks better
            bikeEndingTimeColumn.setStyle("-fx-alignment: CENTER");

            bikeTable.setItems(BikeReservation.getBikeReservationData());
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
     * Called when admin clicks a bike reservation.
     * @return the bike reservation that is currently selected
     */
    public BikeReservation getSelectedBikeReservation() {
        if (bikeTable.getSelectionModel().getSelectedIndex() >= 0) {
            return bikeTable.getSelectionModel().getSelectedItem();
        } else {
            return null;
        }
    }

    /**
     * Gets a number representing the index of the selected bike reservation.
     * @return int
     */
    public int getSelectedIndex() {
        return bikeTable.getSelectionModel().getSelectedIndex();
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
                        selectedBikeReservation.getBikeReservationId().get());
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
     *
     * @param event event that triggered this method
     */
    @FXML
    private void createNewBikeClicked(ActionEvent event) {
        try {
            // Booking edit dialog pop up.
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentSelectedBikeReservation = null;
            BikeEditDialogController.edit = false;
            BikeNewDialogView view = new BikeNewDialogView();
            view.start(stage);
            // Get the reservation from the pop up dialog.
            BikeReservation tempBikeReservation = BikeEditDialogController.bikeReservation;
            if (tempBikeReservation == null) {
                return;
            }
            // TODO: Check that bike reservation creation was successful before displaying alert
            BikeReservationCommunication.createBikeReservation(
                    tempBikeReservation.getBikeReservationBuilding().get(),
                    tempBikeReservation.getBikeReservationUser().get(),
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
     *
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
                BikeEditDialogController.edit = true;
                BikeEditDialogView view = new BikeEditDialogView();
                view.start(stage);
                BikeReservation tempBikeReservation = BikeEditDialogController.bikeReservation;

                if (tempBikeReservation == null) {
                    return;
                }
                // TODO: Check that bike reservation edit was successful before displaying alert
                BikeReservationCommunication.updateBikeReservation(
                        selectedBikeReservation.getBikeReservationId().get(),
                        tempBikeReservation.getBikeReservationBuilding().get(),
                        selectedBikeReservation.getBikeReservationUser().get(),
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
     *
     * @param event event that triggered this method
     * @throws IOException exception that gets thrown if fails
     */
    @FXML
    private void backClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        AdminHomePageView adminHomePageView = new AdminHomePageView();
        adminHomePageView.start(stage);
    }

}
