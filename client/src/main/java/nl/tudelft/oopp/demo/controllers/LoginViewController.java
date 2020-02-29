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
import nl.tudelft.oopp.demo.views.AdminHomePageView;
import nl.tudelft.oopp.demo.views.AdminManageRoomView;
import nl.tudelft.oopp.demo.views.RegisterView;
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
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        AdminHomePageView adh = new AdminHomePageView();
        adh.start(stage);
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
