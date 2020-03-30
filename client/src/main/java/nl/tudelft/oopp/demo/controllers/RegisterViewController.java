package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.RegisterServerCommunication;
import nl.tudelft.oopp.demo.logic.RegisterViewLogic;
import nl.tudelft.oopp.demo.views.LoginView;


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
     * .
     * Handles clicking the button.
     */
    public void registerClicked() throws UnsupportedEncodingException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Register Status");
        alert.setHeaderText(null);

        String usernameTxt = username.getText();
        String passwordTxt = password.getText();
        String rePasswordTxt = rePassword.getText();

        String usernameResponse = RegisterViewLogic.checkUsername(usernameTxt);
        String passwordResponse = RegisterViewLogic.checkPassword(passwordTxt);
        String rePasswordResponse = RegisterViewLogic.checkRePassword(passwordTxt, rePasswordTxt);
        if (!usernameResponse.equals("Good!")) {
            usernameLabel.setText(usernameResponse);
            usernameLabel.setStyle("-fx-text-fill: red");
        } else if (!passwordResponse.equals("Good!")) {
            passwordLabel.setText(passwordResponse);
            passwordLabel.setStyle("-fx-text-fill: red");
        } else if (!rePasswordResponse.equals("Good!")) {
            rePasswordLabel.setText("The password needs to be the same !");
            rePasswordLabel.setStyle("-fx-text-fill: red");
        }
        //Server connection is established.
        else {
            alert.setContentText(RegisterServerCommunication.sendRegister(usernameTxt, passwordTxt));
            alert.showAndWait();
        }
    }

    /**
     * .
     * On the click of the back button, the stage is redirected to the login page.
     *
     * @param event is passed
     * @throws IOException is thrown
     */
    public void backClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        LoginView lv = new LoginView();
        lv.start(stage);
    }
}
