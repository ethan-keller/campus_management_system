package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

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
import nl.tudelft.oopp.demo.views.AdminHomePageView;
import nl.tudelft.oopp.demo.views.ReservationEditDialogView;

public class AdminManageReservationViewController {
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

    public static Reservation currentSelectedReservation;

    /**
     * Default constructor of the class.
     */
    public AdminManageReservationViewController() {
    }

    /**
     * To show all the bookings in the table.
     */
    @FXML
    private void initialize() {
        try {

            //Initializing all the columns created in the table view to inhibit the data passed
            // down through the server.

            id.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(
                    cellData.getValue().getId().get())));
            username.setCellValueFactory(cell -> cell.getValue().getUsername());
            room.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(
                    cellData.getValue().getRoom().get())));
            date.setCellValueFactory(cell -> cell.getValue().getDate());
            startingTime.setCellValueFactory(cell -> cell.getValue().getStartingTime());
            endingTime.setCellValueFactory(cell -> cell.getValue().getEndingTime());

            //Adding the Observable List Data to the tableView created.
            listReservations.setItems(Reservation.getReservation());
        }
        catch (Exception e) {
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
     * The index of the reserrvation is selected.
     * @return the index of the selected reservation.
     */
    public int getSelectedIndex() {
        return listReservations.getSelectionModel().getSelectedIndex();
    }

    /**
     * Delete a Reservation.
     */
    @FXML
    public void DeleteReservationClicked(ActionEvent event) {
        Reservation selectedReservation = getSelectedReservation();
        int selectedIndex = getSelectedIndex();
        try {
            if (selectedIndex >= 0) {
                //TODO: Check that Reservation deletion was successful before displaying alert message.
                ReservationServerCommunication.deleteReservation(selectedReservation.getId().getValue());
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
        } catch (Exception e) {
            System.out.println("delete reservation exception");
            e.printStackTrace();
        }
    }

    /**
     * Handles clicking the create new button.
     */
    @FXML
    public void NewReservationClicked(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            //currentSelectedReservation = null;
            ReservationEditDialogView view = new ReservationEditDialogView();
            view.start(stage);

            //If none of the items in the dialog box is selected.
            if(ReservationEditDialogController.reservation == null) {
                return;
            }
            //TODO: Checking if the reservation creating was successful before displaying the alert.
            else {
                Reservation tempReservation = ReservationEditDialogController.reservation;
                ReservationServerCommunication.createReservation(tempReservation.getUsername().get(),
                        tempReservation.getRoom().get(), tempReservation.getDate().get(),
                        tempReservation.getStartingTime().get(), tempReservation.getEndingTime().get());
                refresh();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("New Reservation");
                alert.setContentText("New Reservation created!");
                alert.showAndWait();
            }
        }
        catch (Exception e) {
            System.out.println("Reservation creation exception");
            e.printStackTrace();
        }
    }

    /**
     * Handles the clicking of Edit button.
     * @param event is passed
     */
    @FXML
    public void EditReservationClicked(ActionEvent event) {
        Reservation selectedReservation = getSelectedReservation();
        int selectedIndex = getSelectedIndex();
        try {
            if (selectedIndex >= 0) {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentSelectedReservation = selectedReservation;

                ReservationEditDialogView view = new ReservationEditDialogView();
                view.start(stage);
                Reservation tempResevation = ReservationEditDialogController.reservation;

                if (tempResevation == null) {
                    return;
                }

                //TODO: Making sure that the reservation is created properly, before displaying the alert box.

                ReservationServerCommunication.updateReservation(selectedReservation.getId().get(),
                        tempResevation.getRoom().get(), tempResevation.getDate().get(),
                        tempResevation.getStartingTime().get(), tempResevation.getEndingTime().get());
                refresh();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Edit Reservation");
                alert.setContentText("Edited Reservation!");
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("No selection");
                alert.setHeaderText("No Reservation Selected!");
                alert.setContentText("Please select a reservation from the table.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            System.out.println("Reservation edit exception");
            e.printStackTrace();
        }
    }

    /**
     * This will redirect the adminManageReservation view back to the home page for the admin to have
     * for options to choose from.
     * @param event is passed
     * @throws IOException is thrown
     */
    @FXML
    private void BackButtonClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        AdminHomePageView ahpv = new AdminHomePageView();
        ahpv.start(stage);
    }
}
