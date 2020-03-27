package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.LoginServerCommunication;
import nl.tudelft.oopp.demo.communication.user.CurrentUserManager;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.views.AdminHomePageView;
import nl.tudelft.oopp.demo.views.RegisterView;
import nl.tudelft.oopp.demo.views.SearchView;



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

        String loginResponse = LoginServerCommunication.sendLogin(username.getText(), password.getText());
        if (loginResponse.equals("admin")) {
            CurrentUserManager currentUser = new CurrentUserManager(username.getText(), 0);
            AdminHomePageView av = new AdminHomePageView();
            av.start(stage);
        } else if (loginResponse.equals("student")) {
            CurrentUserManager currentUser = new CurrentUserManager(username.getText(), 2);
            //currentUser.setUsername(username.getText());
            SearchView sv = new SearchView();
            sv.start(stage);
        } else if (loginResponse.equals("teacher")) {
            CurrentUserManager currentUser = new CurrentUserManager(username.getText(), 1);
            SearchView sv = new SearchView();
            sv.start(stage);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Login attempt");
            alert.setContentText("Wrong credentials");
            alert.showAndWait();
        }
    }

    /**.
     * Handles clicking the register link.
     */
    public void goToRegisterClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        RegisterView rv = new RegisterView();
        rv.start(stage);

    }
}
