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
import nl.tudelft.oopp.demo.views.RegisterView;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;

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

        Pattern UpperCasePattern = Pattern.compile("[A-Z ]");

        /**
         *Checks whether the password username field is left empty or not.
         **/
        if(username.getText().trim().isEmpty()) {
            usernameLabel.setText("This field cannot be left empty !");
            usernameLabel.setStyle("-fx-text-fill: red");
        }/**
         *Checks whether the password field is left empty.
         **/
        else if(password.getText().trim().isEmpty()) {
            passwordLabel.setText("This field cannot be left empty !");
            passwordLabel.setStyle("-fx-text-fill: red");
        }/**
         *Checks whether the password field is atleast 8 characters long.
         **/
        else if(password.getText().length() < 8) {
            passwordLabel.setText("The password needs to have atleast 8 characters !");
            passwordLabel.setStyle("-fx-text-fill: red");
        }/**
         *Checks whether the password matches the repeat password field
         **/
        else if(!passwordTxt.equals(rePasswordTxt)) {
            rePasswordLabel.setText("The password needs to be the same !");
            rePasswordLabel.setStyle("-fx-text-fill: red");
        }/**
         *Checks whether the password contains atleast one numeric value
         **/
        else if(!passwordTxt.matches(".*\\d.*")) {
            passwordLabel.setText("The password needs to have atleast 1 numeric value !");
            passwordLabel.setStyle("-fx-text-fill: red");
        }
        else if(!UpperCasePattern.matcher(passwordTxt).find()) {
            passwordLabel.setText("The password needs to have atleast 1 Upper Case letter !");
            passwordLabel.setStyle("-fx-text-fill: red");
        }
        /**
         *Server connection is established.
         **/
        else {
            alert.setContentText(RegisterServerCommunication.sendRegister(usernameTxt, passwordTxt));
            alert.showAndWait();
        }
    }


    /**
     *On the click of the back button, the stage is redirected to the login page.
     * @param event
     * @throws IOException
     */
    public void backClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        LoginView lv = new LoginView();
        lv.start(stage);
    }
}
