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
    private Label passwordLabel;

    @FXML
    private Label rePasswordLabel;

    @FXML
    private Label usernameLabel;

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

        if(username.getText().trim().isEmpty()) {
            usernameLabel.setText("This field cannot be left empty !");
            usernameLabel.setStyle("-fx-text-fill: red");
        }
        if(password.getText().trim().isEmpty()) {
            passwordLabel.setText("This field cannot be left empty !");
            passwordLabel.setStyle("-fx-text-fill: red");
        }
        if(password.getText().length() < 8) {
            passwordLabel.setText("The password needs to have atleast 8 characters !");
            passwordLabel.setStyle("-fx-text-fill: red");
        }
        if(!passwordTxt.equals(rePasswordTxt)) {
            rePasswordLabel.setText("The password needs to be the same !");
            rePasswordLabel.setStyle("-fx-text-fill: red");
        }
        else {
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
