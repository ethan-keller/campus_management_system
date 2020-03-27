package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;

import javafx.scene.control.Label;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Alert;
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
    private Label usernameLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private Hyperlink goToRegister;

    public static User currentUser;

    /**
     * Handles clicking the login button.
     */
    public void loginButtonClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        String loginResponse = LoginServerCommunication.sendLogin(username.getText(), password.getText());
        if (isValidInput()) {
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
    }

    /**
     * This method checks if the username field and the password field are left empty.
     * @return Boolean value to indicate whether the above condition is fullfilled.
     */
    private boolean isValidInput() {

        while (true) {
            // Checks whether the password username field is left empty or not.
            if (username.getText().trim().isEmpty()) {
                usernameLabel.setText("The username field cannot be left empty !");
                usernameLabel.setStyle("-fx-text-fill: red");
                return false;
            } else if (password.getText().trim().isEmpty()) {
                // Checks whether the password field is left empty.
                passwordLabel.setText("The password field cannot be left empty !");
                passwordLabel.setStyle("-fx-text-fill: red");
                return false;
            } else {
                // This boolean value means that all the fields are filled.
                return true;
            }
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
