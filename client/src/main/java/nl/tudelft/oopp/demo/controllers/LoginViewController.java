package nl.tudelft.oopp.demo.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.LoginServerCommunication;
import nl.tudelft.oopp.demo.communication.user.CurrentUserManager;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.views.RegisterView;
import nl.tudelft.oopp.demo.views.SearchView;
import nl.tudelft.oopp.demo.views.AdminHomePageView;
import java.io.IOException;


public class LoginViewController {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Hyperlink goToRegister;

    public static User currentUser;

    /**
     * Handles clicking the login button.
     */
    public void loginButtonClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        String login_response = LoginServerCommunication.sendLogin(username.getText(), password.getText());
        if(login_response.equals("admin")){
            CurrentUserManager current_user = new CurrentUserManager(username.getText(), 0);
            AdminHomePageView av = new AdminHomePageView();
            av.start(stage);
        } else if(login_response.equals("student")){
            CurrentUserManager current_user = new CurrentUserManager(username.getText(), 2);
            //current_user.setUsername(username.getText());
            SearchView sv = new SearchView();
            sv.start(stage);
        } else if (login_response.equals("teacher")){
            CurrentUserManager current_user = new CurrentUserManager(username.getText(), 1);
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
