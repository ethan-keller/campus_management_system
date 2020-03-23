package nl.tudelft.oopp.demo.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.ReservationServerCommunication;
import nl.tudelft.oopp.demo.entities.Reservation;
import nl.tudelft.oopp.demo.views.LoginView;
import nl.tudelft.oopp.demo.views.SearchView;


import java.io.IOException;

public class CancelBookingController {
    /**
     * These are the FXML elements that inject some functionality into the application.
     */
    @FXML
    private TableView<Reservation> listReservations;
    @FXML
    private TableColumn<Reservation, String> id;
    @FXML
    private TableColumn<Reservation, String> username;
    @FXML
    private TableColumn<Reservation, String> room;
    @FXML
    private TableColumn<Reservation, String> date;
    @FXML
    private TableColumn<Reservation, String> starting_time;
    @FXML
    private TableColumn<Reservation, String> ending_time;

    /**
     * Default constructor of cancelBooking class.
     */
    public CancelBookingController() {
    }

    /**
     * Method that is called before the view is functionable to the user.
     * This method initializes all nodes and components of view.
     * JavaFX Standard.
     */
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
            listReservations.setItems(Reservation.getUserReservation());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is refresh the initialization class to get all the data from the server.
     * Since there is change of items in the table view, this method helps update the changes.
     */
    public void refresh() {
        initialize();
    }

    /**
     * This method selects a particular reservation from the table.
     *
     * @return Returns the selected reservation.
     */
    public Reservation getSelectedReservation() {

        if (listReservations.getSelectionModel().getSelectedIndex() >= 0) {
            return listReservations.getSelectionModel().getSelectedItem();
        } else {
            return null;
        }
    }

    /**
     * @return the index of the selected reservation.
     */
    public int getSelectedIndex() {
        return listReservations.getSelectionModel().getSelectedIndex();
    }

    /**
     * Delete a building.
     */
    @FXML
    public void cancelReservationClicked() {
        Reservation selectedReservation = getSelectedReservation();
        int selectedIndex = getSelectedIndex();
        try {
            if (selectedIndex >= 0) {
                //TODO: Check that Reservation deletion was successful before displaying alert message.
                ReservationServerCommunication.deleteReservation(selectedReservation.getId().getValue());
                refresh();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Cancel Reservation");
                alert.setContentText("Reservation canceled!");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No Selection");
                alert.setHeaderText("No Reservation Selected");
                alert.setContentText("Please select a reservation in the table.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            System.out.println("delete reservation exception");
            e.printStackTrace();
        }
    }

    /**
     * Handles the onclick of backButton.
     * Redirects the user back to the searchView.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void backButtonClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        SearchView sv = new SearchView();
        sv.start(stage);
    }

    /**
     * Handles the onclick of signOut Button.
     * Redirects the user back to the loginView.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void signOutClicked(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        LoginView lv = new LoginView();
        lv.start(stage);
    }

}
