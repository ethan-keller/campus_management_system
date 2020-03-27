package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.entities.Reservation;
import nl.tudelft.oopp.demo.views.LoginView;
import nl.tudelft.oopp.demo.views.SearchView;

public class BookingHistoryController {
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
    private TableColumn<Reservation,String> startingTime;
    @FXML
    private TableColumn<Reservation,String> endingTime;

    public BookingHistoryController() {
    }

    /**
     * Method that is called before the view is functionable to the user.
     * This method initializes all nodes and components of view.
     * JavaFX Standard.
     */
    public void initialize() {
        try {

            //Initializing all the columns created in the table view to inhibit the data passed down
            // through the server.

            id.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(
                    cell.getValue().getId().get())));
            username.setCellValueFactory(cell -> cell.getValue().getUsername());
            room.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(
                    cell.getValue().getRoom().get())));
            date.setCellValueFactory(cell -> cell.getValue().getDate());
            startingTime.setCellValueFactory(cell -> cell.getValue().getStartingTime());
            endingTime.setCellValueFactory(cell -> cell.getValue().getEndingTime());

            //Adding the Observable List Data to the tableView created.
            listReservations.setItems(Reservation.getUserReservation());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the onclick of backButton.
     * Redirects the user back to the searchView.
     *
     * @param event is passed
     * @throws IOException is thrown
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
     * @param event is passed
     */
    @FXML
    public void signOutClicked(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        LoginView lv = new LoginView();
        lv.start(stage);
    }

}
