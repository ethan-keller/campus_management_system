package nl.tudelft.oopp.demo.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.AdminManageServerCommunication;
import nl.tudelft.oopp.demo.entities.Reservation;
import nl.tudelft.oopp.demo.views.AdminManageUserView;
import nl.tudelft.oopp.demo.views.BookingEditDialogView;

import java.io.IOException;

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
            usernameLabel.setText(AdminManageUserViewController.currentSelectedUser.getUsername().get());
            // Initialize the booking table with the eight columns.
            bookingIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId().get())));
            bookingRoomColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getRoom().get())));
            bookingDateColumn.setCellValueFactory(cell -> cell.getValue().getDate());
            bookingStartColumn.setCellValueFactory(cell -> cell.getValue().getStarting_time());
            bookingEndColumn.setCellValueFactory(cell -> cell.getValue().getEnding_time());
            bookingTable.setItems(Reservation.getSelectedUserReservation());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void refresh() {
        initialize();
    }

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
     * Delete a room.
     */
    @FXML
    private void deleteBookingClicked(ActionEvent event) {
        Reservation selectedReservation = getSelectedReservation();
        int selectedIndex = getSelectedIndex();
        try {
            if (selectedIndex >= 0) {
                // TODO: Check that reservation deletion was successful before displaying alert
                AdminManageServerCommunication.deleteReservation(selectedReservation.getId().getValue());
                refresh();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Delete reservation");
                alert.setContentText("Reservation deleted!");
                alert.showAndWait();
            } else {
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
     */
    @FXML
    private void createNewBookingClicked(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            currentSelectedReservation = null;
            BookingEditDialogView view = new BookingEditDialogView();
            view.start(stage);
            Reservation tempReservation = BookingEditDialogController.reservation;
            if (tempReservation == null) return;
            // TODO: Check that reservation creation was successful before displaying alert
            AdminManageServerCommunication.createReservation(AdminManageUserViewController.currentSelectedUser.getUsername().get(), tempReservation.getRoom().get(), tempReservation.getDate().get(), tempReservation.getStarting_time().get(), tempReservation.getEnding_time().get());
            refresh();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("New room");
            alert.setContentText("Added new room!");
            alert.showAndWait();
        } catch (Exception e) {
            System.out.println("room creation exception");
            e.printStackTrace();
        }
    }

    @FXML
    private void backClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        AdminManageUserView amuv = new AdminManageUserView();
        amuv.start(stage);
    }

}
