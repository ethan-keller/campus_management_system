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
import nl.tudelft.oopp.demo.communication.ReservationServerCommunication;
import nl.tudelft.oopp.demo.entities.Reservation;
import nl.tudelft.oopp.demo.views.AdminManageUserView;
import nl.tudelft.oopp.demo.views.BookingEditDialogView;


public class AdminUserHistoryViewController {

    @FXML
    private Label usernameLabel;
    @FXML
    private TableView<Reservation> bookingTable;
    @FXML
    private TableColumn<Reservation, String> bookingIdColumn;
    @FXML
    private TableColumn<Reservation, String> bookingDateColumn;
    @FXML
    private TableColumn<Reservation, String> bookingRoomColumn;
    @FXML
    private TableColumn<Reservation, String> bookingStartColumn;
    @FXML
    private TableColumn<Reservation, String> bookingEndColumn;

    public static Reservation currentSelectedReservation;

    public AdminUserHistoryViewController() {

    }

    /**
     * Show all the booking in the table.
     */
    @FXML
    private void initialize() {
        try {
            // Initialize the title of the table
            usernameLabel.setText(AdminManageUserViewController.currentSelectedUser.getUsername().get());
            // Initialize the booking table with the five columns.
            bookingIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(
                    cellData.getValue().getId().get())));
            bookingRoomColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(
                    cellData.getValue().getRoom().get())));
            bookingDateColumn.setCellValueFactory(cell -> cell.getValue().getDate());
            bookingStartColumn.setCellValueFactory(cell -> cell.getValue().getStartingTime());
            bookingEndColumn.setCellValueFactory(cell -> cell.getValue().getEndingTime());
            bookingTable.setItems(Reservation.getSelectedUserReservation());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**.
     * refresh the table when called
     */
    public void refresh() {
        initialize();
    }

    /**
     * Called when admin clicks a reservation.
     */
    public Reservation getSelectedReservation() {
        if (bookingTable.getSelectionModel().getSelectedIndex() >= 0) {
            return bookingTable.getSelectionModel().getSelectedItem();
        } else {
            return null;
        }
    }

    public int getSelectedIndex() {
        return bookingTable.getSelectionModel().getSelectedIndex();
    }

    /**
     * Delete a reservation.
     */
    @FXML
    private void deleteBookingClicked(ActionEvent event) {
        Reservation selectedReservation = getSelectedReservation();
        int selectedIndex = getSelectedIndex();
        try {
            if (selectedIndex >= 0) {
                // TODO: Check that reservation deletion was successful before displaying alert
                ReservationServerCommunication.deleteReservation(selectedReservation.getId().getValue());
                refresh();
                // An alert pop up when a reservation deleted successfully
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Delete reservation");
                alert.setContentText("Reservation deleted!");
                alert.showAndWait();
            } else {
                // An alert pop up when no reservation selected
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No Selection");
                alert.setHeaderText("No reservation Selected");
                alert.setContentText("Please select a reservation in the table.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            System.out.println("delete reservation exception");
            e.printStackTrace();
        }
    }

    /**
     * Handles clicking the create new button.
     * Opens a dialog to creat a new reservation.
     */
    @FXML
    private void createNewBookingClicked(ActionEvent event) {
        try {
            // Booking edit dialog pop up.
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentSelectedReservation = null;
            BookingEditDialogView view = new BookingEditDialogView();
            view.start(stage);
            // Get the reservation from the pop up dialog.
            Reservation tempReservation = BookingEditDialogController.reservation;
            if (tempReservation == null) {
                return;
            }
            // TODO: Check that reservation creation was successful before displaying alert
            ReservationServerCommunication.createReservation(tempReservation.getUsername().get(),
                    tempReservation.getRoom().get(), tempReservation.getDate().get(),
                    tempReservation.getStartingTime().get(), tempReservation.getEndingTime().get());
            refresh();
            // An alert pop up when a new reservation created.
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("New reservation");
            alert.setContentText("Added new reservation!");
            alert.showAndWait();
        } catch (Exception e) {
            System.out.println("reservation creation exception");
            e.printStackTrace();
        }
    }

    /**
     * Handles clicking the back button, redirect to the user view.
     */
    @FXML
    private void backClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        AdminManageUserView amuv = new AdminManageUserView();
        amuv.start(stage);
    }

}
