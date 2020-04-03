package nl.tudelft.oopp.demo.admin.controller;

import com.mindfusion.scheduling.Calendar;
import com.mindfusion.scheduling.model.Item;

import java.awt.Color;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import nl.tudelft.oopp.demo.calendar.CalendarListener;
import nl.tudelft.oopp.demo.communication.BikeReservationCommunication;
import nl.tudelft.oopp.demo.general.GeneralMethods;
import nl.tudelft.oopp.demo.communication.ItemServerCommunication;
import nl.tudelft.oopp.demo.communication.ReservationServerCommunication;
import nl.tudelft.oopp.demo.user.controller.CalendarPaneController;

/**
 * Class that controls the dialog when a user clicks an item in his calendar.
 */
public class CalendarItemDialogController implements Initializable {

    // private fields that are used to determine the type of an item
    private boolean bikeReservation = false;
    private boolean reservation = false;
    private boolean item = false;

    public static Item selectedItem;

    @FXML
    private Text header;
    @FXML
    private Text body;
    @FXML
    private HBox buttonBar;
    private Calendar calendar;

    /**
     * .
     * Custom initialization of JavaFX components. This method is automatically called
     * after the fxml file has been loaded.
     *
     * @param location  is passed
     * @param resources is passed
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // sets the boolean fields to the right type
        setItemType();

        // get the calendar
        calendar = CalendarPaneController.calendar;
        // set the header and body
        setTexts();
        // set the correct buttons
        if (bikeReservation || reservation) {
            setCancelBookingButton();
        } else {
            setDeleteItemButton();
        }

    }

    /**
     * Method that sets the class attributes.
     */
    private void setItemType() {
        // color of the item
        Color fill = selectedItem.getStyle().getFillColor();

        if (fill.equals(Color.CYAN)) {
            reservation = true;
        } else if (fill.equals(Color.MAGENTA)) {
            bikeReservation = true;
        } else if (fill.equals(Color.ORANGE)) {
            item = true;
        }
    }

    /**
     * Sets the delete button in the dialog to delete an item.
     */
    private void setDeleteItemButton() {
        // create button
        Button deleteItemButton = new Button("Delete item");
        // style button
        deleteItemButton.setStyle("-fx-background-color: #e6afa8");
        // set mouse click event
        deleteItemButton.setOnMouseClicked(event -> {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            deleteItem(stage);
        });
        // add to button bar
        buttonBar.getChildren().add(0, deleteItemButton);
    }

    /**
     * Method that sends delete request to server and shows result to user.
     * @param thisStage current stage
     */
    private void deleteItem(Stage thisStage) {
        try {
            // reset all fields
            item = false;
            reservation = false;
            bikeReservation = false;
            // close the stage
            thisStage.close();
            // check if communication was successful and alert the user
            if (ItemServerCommunication.deleteItem(Integer.parseInt(selectedItem.getId()))) {
                // delete from calendar and confirm deletion
                calendar.getSchedule().getItems().remove(selectedItem);
                Alert alert = GeneralMethods.createAlert("Deletion confirmation",
                        "Your item has successfully been deleted from your schedule",
                        null, Alert.AlertType.INFORMATION);
                alert.showAndWait();
            } else {
                // alert the user that something went wrong
                Alert alert = GeneralMethods.createAlert("Deletion error",
                        "Something went wrong, your item has not been deleted. Please try again",
                        null, Alert.AlertType.ERROR);
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the cancel booking button in the button bar.
     */
    private void setCancelBookingButton() {
        // create button
        Button cancelBookingButton = new Button("Cancel booking");
        // style button
        cancelBookingButton.setStyle("-fx-background-color: #e6afa8");
        // add event
        cancelBookingButton.setOnMouseClicked(event -> {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            cancelBooking(stage);
        });
        // add to button bar
        buttonBar.getChildren().add(0, cancelBookingButton);
    }

    /**
     * Sends a cancel booking request to the server and alerts the user of its success.
     * @param thisStage current stage
     */
    private void cancelBooking(Stage thisStage) {
        try {
            // create alerts
            Alert alertConfirm = GeneralMethods.createAlert("Cancel confirmation",
                    "Your reservation has succesfully been canceled", null,
                    Alert.AlertType.INFORMATION);

            Alert alertError = GeneralMethods.createAlert("Cancel error",
                    "Something went wrong, your reservation has not been canceled."
                            + " Please try again.",
                    null, Alert.AlertType.ERROR);

            // close stage
            thisStage.close();
            // adapt server request to the type of reservation
            if (reservation) {
                if (ReservationServerCommunication.deleteReservation(Integer.parseInt(selectedItem.getId()))) {
                    // delete reservation from database and calendar
                    calendar.getSchedule().getItems().remove(selectedItem);
                    alertConfirm.showAndWait();
                } else {
                    // alert user that there was an error
                    alertError.showAndWait();
                }
            } else if (bikeReservation) {
                if (BikeReservationCommunication.deleteBikeReservation(Integer.parseInt(selectedItem.getId()))) {
                    // delete reservation from database and calendar
                    calendar.getSchedule().getItems().remove(selectedItem);
                    alertConfirm.showAndWait();
                } else {
                    alertError.showAndWait();
                }
            }
            // reset all fields
            item = false;
            reservation = false;
            bikeReservation = false;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Set the header and body in the dialog.
     */
    private void setTexts() {
        header.setText(CalendarListener.header);
        body.setText(CalendarListener.body);
    }

    /**
     * Closes the dialog when the user clicks close.
     * @param event event that triggered this method
     */
    @FXML
    private void closeClicked(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}

