package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.RegisterServerCommunication;
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

    @FXML
    private RadioButton student;

    @FXML
    private RadioButton teacher;

    @FXML
    private Label radioLabel;

    /**
     * Initializing the radio buttons into toggle groups.
     */
    public void initialize() {
        // This toggle group is created such that the user can't select both the radio boxes.
        ToggleGroup group = new ToggleGroup();
        student.setToggleGroup(group);
        teacher.setToggleGroup(group);
    }

    /**.
     * Handles clicking the button.
     */
    public void registerClicked() throws UnsupportedEncodingException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Register Status");
        alert.setHeaderText(null);

        String usernameTxt = username.getText();
        String passwordTxt = password.getText();
        String rePasswordTxt = rePassword.getText();

        Pattern upperCasePattern = Pattern.compile("[A-Z]");
        Pattern characters = Pattern.compile("[!@#$%^&*`~<,>./?:;'{|+=_-]");
        Pattern space = Pattern.compile(" ");

        //Checks whether the password username field is left empty or not.
        if (username.getText().trim().isEmpty()) {
            usernameLabel.setText("This field cannot be left empty !");
            usernameLabel.setStyle("-fx-text-fill: red");
        
            //Checks whether the password field is left empty.
        } else if (password.getText().trim().isEmpty()) {
            passwordLabel.setText("This field cannot be left empty !");
            passwordLabel.setStyle("-fx-text-fill: red");

            //Checks whether the password field is atleast 8 characters long.
        } else if (password.getText().length() < 8) {
            passwordLabel.setText("The password needs to have atleast 8 characters !");
            passwordLabel.setStyle("-fx-text-fill: red");

            //Checks whether the password matches the repeat password field
        } else if (!passwordTxt.equals(rePasswordTxt)) {
            rePasswordLabel.setText("The password needs to be the same !");
            rePasswordLabel.setStyle("-fx-text-fill: red");

            //Checks whether the password contains atleast one numeric value
        } else if (!passwordTxt.matches(".*\\d.*")) {
            passwordLabel.setText("The password needs to have atleast 1 numeric value !");
            passwordLabel.setStyle("-fx-text-fill: red");
        } else if (!upperCasePattern.matcher(passwordTxt).find()) {
            passwordLabel.setText("The password needs to have atleast 1 Upper Case letter !");
            passwordLabel.setStyle("-fx-text-fill: red");
        } else if (space.matcher(usernameTxt).find()) {
            usernameLabel.setText("The username is not allowed to have any spaces !");
            usernameLabel.setStyle("-fx-text-fill: red");
        } else if (space.matcher(passwordTxt).find()) {
            passwordLabel.setText("The password is not allowed to have any spaces !");
            passwordLabel.setStyle("-fx-text-fill: red");
        } else if (characters.matcher(usernameTxt).find()) {
            usernameLabel.setText("The username is not allowed to have any punctuations !");
            usernameLabel.setStyle("-fx-text-fill: red");
        } else if (characters.matcher(passwordTxt).find()) {
            passwordLabel.setText("The password is not allowed to have any punctuations !");
            passwordLabel.setStyle("-fx-text-fill: red");

            //Server connection is established.
        } else if (!student.isSelected() && !teacher.isSelected()) {
            radioLabel.setText("Please select your role !");
            radioLabel.setStyle("-fx-text-fill: red");
        } else {
            int userType = 0;
            if (student.isSelected()) {
                userType = 1;
            } else {
                userType = 2;
            }
            alert.setContentText(RegisterServerCommunication.sendRegister(usernameTxt, passwordTxt, userType));
            alert.showAndWait();
        }
    }

    /**.
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
