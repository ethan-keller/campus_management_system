package nl.tudelft.oopp.demo.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import nl.tudelft.oopp.demo.communication.ServerCommunication;

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
    public void registerClicked() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Login Status");
        alert.setHeaderText(null);

        String usernameTxt = username.getText();
        String passwordTxt = password.getText();
        String rePasswordTxt = rePassword.getText();

        if(! passwordTxt.equals(rePasswordTxt)) {
            pwd_error.textProperty().set("Password does not match, try again");
        }
        else{
            alert.setContentText("Account created！");
//            alert.setContentText(ServerCommunication.sendRegister(usernameTxt, passwordTxt));
//            alert.showAndWait();
        }
    }
}
