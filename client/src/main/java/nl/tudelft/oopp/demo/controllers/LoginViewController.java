package nl.tudelft.oopp.demo.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import javafx.event.ActionEvent;
import nl.tudelft.oopp.demo.views.RegisterView;
import java.io.IOException;

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
//    public void loginButtonClicked() {
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle("Login Status");
//        alert.setHeaderText(null);
//        String usernameTxt = username.getText();
//        String passwordTxt = password.getText();
//        alert.setContentText(ServerCommunication.sendLogin(usernameTxt, passwordTxt));
//        alert.showAndWait();
//    }

    /**
     * Handles clicking the register link.
     */
    public void goToRegisterClicked(ActionEvent event) throws IOException {
        RegisterView rv = new RegisterView();
        Stage stage = new Stage();
        rv.start(stage);
    }
}
