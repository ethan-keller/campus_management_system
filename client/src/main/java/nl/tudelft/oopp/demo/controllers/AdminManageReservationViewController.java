package nl.tudelft.oopp.demo.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.entities.Reservation;

import java.awt.event.ActionEvent;
import java.io.UnsupportedEncodingException;


public class AdminManageReservationViewController {

    @FXML
    private TableView<Reservation> listReservation;
    @FXML
    private TableColumn<Reservation,String> id;
    @FXML
    private TableColumn<Reservation,String> username;
    @FXML
    private  TableColumn<Reservation,String> room;
    @FXML
    private TableColumn<Reservation,String> date;
    @FXML
    private TableColumn<Reservation,String> starting_time;
    @FXML
    private TableColumn<Reservation,String> ending_time;

    public static Reservation currentSelectedReservation;

    public AdminManageReservationViewController() {}

    @FXML
    private void initialize() {
        try {

            //Initializing all the columns created in the table view to inhibit the data passed down through the server.

            id.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getId().get())));
            username.setCellValueFactory(cell -> cell.getValue().getUsername());
            room.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getRoom().get())));
            date.setCellValueFactory(cell -> cell.getValue().getDate());
            starting_time.setCellValueFactory(cell -> cell.getValue().getStarting_time());
            ending_time.setCellValueFactory(cell -> cell.getValue().getEnding_time());

            //Adding the Observable List Data to the tableView created.
            listReservation.setItems(Reservation.getReservation());
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

        public void refresh() {
        initialize();
    }

    public Reservation getSelectedReservation() {

        if(listReservation.getSelectionModel().getSelectedIndex() >= 0) {
            return listReservation.getSelectionModel().getSelectedItem();
        }
        else {
            return null;
        }
    }

    public int getSelectedIndex() {
        return listReservation.getSelectionModel().getSelectedIndex();
    }

    /**
     * Delete a building.
     */
    @FXML
    private void DeleteReservationClicked(ActionEvent event) {
        Reservation selectedReservation = getSelectedReservation();
        int selectedIndex = getSelectedIndex();
        try {
            if(selectedIndex >= 0 ){
                //TODO: Check that Reservation deletion was successful before displaying alert message.
                AdminManageServerCommunication.deleteReservation(selectedReservation.getId().getValue());
                refresh();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Delete Reservation");
                alert.setContentText("Reservation deleted!");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No Selection");
                alert.setHeaderText("No Reservation Selected");
                alert.setContentText("Please select a reservation in the table.");
                alert.showAndWait();
            }
        }
        catch (Exception e) {
            System.out.println("delete reservation exception");
            e.printStackTrace();
        }
    }

    /**
     * Handles clicking the create new button.
     */
    @FXML
    private void NewReservationClicked(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            currentSelectedReservation = null;
            ReservationEditDialogView view = new ReservationEditDialogView();
            view.start(stage);

        }
    }
}
