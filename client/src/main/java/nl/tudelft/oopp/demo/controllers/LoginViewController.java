package nl.tudelft.oopp.demo.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.LoginServerCommunication;

import java.io.IOException;

import nl.tudelft.oopp.demo.views.RegisterView;

import java.awt.TextField;

import nl.tudelft.oopp.demo.views.SearchView;


public class LoginViewController {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Hyperlink goToRegister;


    /**
     * Handles clicking the login button.
     */
    public void loginButtonClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        if (LoginServerCommunication.sendLogin(username.getText(), password.getText()).equals("Login granted")) {
            SearchView sv = new SearchView();
            sv.start(stage);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Login attempt");
            alert.setContentText("Wrong credentials");
            alert.showAndWait();
        }
    }

    /**
     * Handles clicking the register link.
     */
    public void goToRegisterClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        RegisterView rv = new RegisterView();
        rv.start(stage);

    }
}
