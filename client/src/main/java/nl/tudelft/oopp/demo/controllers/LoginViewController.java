package nl.tudelft.oopp.demo.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.LoginServerCommunication;
import javafx.event.ActionEvent;
import nl.tudelft.oopp.demo.communication.RegisterServerCommunication;
import nl.tudelft.oopp.demo.views.RegisterView;
import nl.tudelft.oopp.demo.views.SearchView;
import nl.tudelft.oopp.demo.views.AdminHomePageView;
import nl.tudelft.oopp.demo.views.UserHomePageView;

import java.io.IOException;
import java.net.URL;

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
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();

        String login_response = LoginServerCommunication.sendLogin(username.getText(), password.getText());
        if(login_response.equals("admin")){
            AdminHomePageView av = new AdminHomePageView();
            av.start(stage);
        } else if(login_response.equals("student") || login_response.equals("teacher")){
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
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();

        RegisterView rv = new RegisterView();
        rv.start(stage);

    }
}
