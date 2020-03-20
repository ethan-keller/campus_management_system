package nl.tudelft.oopp.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.RegisterServerCommunication;
import nl.tudelft.oopp.demo.views.LoginView;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class RegisterViewController {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField rePassword;

    @FXML
    private Label pwd_error;

    /**
     * Handles clicking the button.
     */
    public void registerClicked() throws UnsupportedEncodingException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Register Status");
        alert.setHeaderText(null);

        String usernameTxt = username.getText();
        String passwordTxt = password.getText();
        String rePasswordTxt = rePassword.getText();

        if (!passwordTxt.equals(rePasswordTxt)) {
            pwd_error.textProperty().set("Password does not match, try again");
        } else {
            alert.setContentText(RegisterServerCommunication.sendRegister(usernameTxt, passwordTxt));
            alert.showAndWait();
        }
    }

    public void backClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        LoginView lv = new LoginView();
        lv.start(stage);
    }
}
