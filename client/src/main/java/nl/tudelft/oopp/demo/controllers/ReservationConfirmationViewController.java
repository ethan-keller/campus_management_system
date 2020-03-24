package nl.tudelft.oopp.demo.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.user.CurrentUserManager;
import nl.tudelft.oopp.demo.entities.Room;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class that controls the dialog pop up that asks for a reservation confirmation.
 */
public class ReservationConfirmationViewController implements Initializable {

    @FXML
    private Text confirmationText;

    // current Room
    public static Room room;

    // reservation information
    public static String date;
    public static String startTime;
    public static String endTime;

    // confirmation state
    public static boolean confirmed = false;

    /**
     * Method that gets called before everything (mostly to initialize nodes etc.).
     * JavaFX standard.
     *
     * @param location
     * @param resources
     */
    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO: add food choice into confirmation text
        confirmationText.setText("You (" + CurrentUserManager.getUsername() + ") would like to book the " +
                room.getRoomName().get() + " on " + date + " from " + startTime + " until " +
                endTime + ". Would you like to confirm that?");
    }


    /**
     * When user clicks 'confirm' reservation goes through.
     *
     * @param event
     */
    @FXML
    private void confirmClicked(ActionEvent event) {
        // set confirmed state
        confirmed = true;

        // close current stage
        Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        thisStage.close();
    }

    /**
     * When user clicks 'cancel' reservation does not go through.
     *
     * @param event
     */
    @FXML
    private void cancelClicked(ActionEvent event) {
        // set confirmed state
        confirmed = false;

        // close current stage
        Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        thisStage.close();
    }
}
