package nl.tudelft.oopp.demo.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import nl.tudelft.oopp.demo.communication.ServerCommunication;

public class RegisterViewController {
    public void registerClicked(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Quote for you");
        alert.setHeaderText(null);
        alert.setContentText(ServerCommunication.getQuote());
        alert.showAndWait();
    }
}
