package nl.tudelft.oopp.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.user.CurrentUserManager;
import nl.tudelft.oopp.demo.entities.Room;

import java.net.URL;
import java.util.ResourceBundle;

public class ReservationConfirmationViewController implements Initializable {
    public Text confirmationText;
    public static Room room;
    public static String date;
    public static String startTime;
    public static String endTime;
    public static boolean confirmed = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO: add food choice into confirmation text
        confirmationText.setText("You, " + CurrentUserManager.getUsername() + ", would like to book the " + room.getRoomName().get() + " on " +
                date + " from " + startTime + " until " + endTime + ". Would you like to confirm that?");
    };

    public void confirmClicked(ActionEvent event) {
        confirmed = true;
        Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        thisStage.close();
    }

    public void cancelClicked(ActionEvent event) {
        confirmed = false;
        Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        thisStage.close();
    }
}
