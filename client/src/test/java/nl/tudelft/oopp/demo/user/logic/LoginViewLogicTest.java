package nl.tudelft.oopp.demo.user.logic;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginViewLogicTest {
    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label passwordLabel;

    @Test
    void isValidInputTest(){
        username = new TextField();
        username.setText("");
    }
}