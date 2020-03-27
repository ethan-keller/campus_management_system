package nl.tudelft.oopp.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import java.util.regex.Pattern;
import javafx.stage.Stage;

import nl.tudelft.oopp.demo.entities.User;

public class UserEditDialogController {

    @FXML
    private TextField usernameField;
    @FXML
    private RadioButton userTypeAdmin;
    @FXML
    private RadioButton userTypeTeacher;
    @FXML
    private RadioButton userTypeStudent;
    @FXML
    private PasswordField userPasswordField;

    public static boolean edit;

    public static User user;

    private Stage dialogStage;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        User user = AdminManageUserViewController.currentSelectedUser;
        if (user == null) {
            return;
        }
        usernameField.setText(user.getUsername().get());
        if (edit) {
            usernameField.setDisable(true);
        }
        //        userPasswordField.setText(user.getUserPassword().get());
        if (user.getUserType().get() == 0) {
            userTypeAdmin.setSelected(true);
        }
        if (user.getUserType().get() == 1) {
            userTypeTeacher.setSelected(true);
        }
        if (user.getUserType().get() == 2) {
            userTypeStudent.setSelected(true);
        }
    }

    private static void emptyUser() {
        user = new User();
    }

    /**
     * Called when the admin clicks ok.
     */
    @FXML
    private void handleOkClicked(ActionEvent event) {
        if (isInputValid()) {
            emptyUser();
            user.setUsername(usernameField.getText());
            if (userTypeAdmin.isSelected()) {
                user.setUserType(0);
            }
            if (userTypeTeacher.isSelected()) {
                user.setUserType(1);
            }
            if (userTypeStudent.isSelected()) {
                user.setUserType(2);
            }

            if (!userPasswordField.getText().equals("")) {
                user.setUserPassword(userPasswordField.getText());
            }

            this.dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancelClicked(ActionEvent event) {
        user = null;
        this.dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     * This method is used to validate if the admin has entered a valid username and password during
     * creation/update.
     * @return true if the input is valid
     */
    private boolean isInputValid() {

        String errorMessage = "";

        // Checks whether the username field is empty.
        if (usernameField.getText().trim().isEmpty()) {
            errorMessage += "Username field can't be blank!\n";
        }
        // Checks whether the type of user is selected.
        if ((userTypeAdmin.isSelected() || userTypeTeacher.isSelected()
                || userTypeStudent.isSelected()) == false) {
            errorMessage += "Select the type of user!\n";
        }
        // Checks whether the password field is empty.
        if (!edit) {
            if (userPasswordField.getText().trim().isEmpty()) {
                errorMessage += "Password field can't be blank!\n";
            }
        }
        // Checks whether the password field is atleast 8 characters long.
        if (userPasswordField.getLength() < 8) {
            errorMessage += "Password needs to at-least 8 characters.\n";
        }
        // Checks whether the password contains atleast one numeric value.
        if (!userPasswordField.getText().matches(".*\\d.*")) {
            errorMessage += "Password needs at-least 1 numeric value.\n";
        }
        // This pattern is used to compare the text to see if it has an upper case character.
        Pattern upperCasePattern = Pattern.compile("[A-Z]");
        // Checks whether the password contains atleast one upper case character.
        if (!upperCasePattern.matcher(userPasswordField.getText()).find()) {
            errorMessage += "Password needs at-least 1 upper case character.\n";
        }
        // This pattern is used to compare the text to see if it has any spaces.
        Pattern space = Pattern.compile(" ");
        // Checks whether the password contains any spaces.
        if (space.matcher(userPasswordField.getText()).find()) {
            errorMessage += "Password is not allowed to have spaces in them.\n";
        }
        // Checks whether the username contains any spaces.
        if (space.matcher(usernameField.getText()).find()) {
            errorMessage += "Username is not allowed to have any spaces.\n";
        }
        // This pattern is used to compare the text to see if it has any punctuations.
        Pattern characters = Pattern.compile("[!@#$%^&*`~<,>./?:;'{|+=_-]");
        // Checks whether the password contains any punctuations.
        if (characters.matcher(userPasswordField.getText()).find()) {
            errorMessage += "Password is not allowed to have any punctuations.\n";
        }
        // Checks whether the username contains any punctuations.
        if (characters.matcher(usernameField.getText()).find()) {
            errorMessage += "Username is not allowed to have any punctuations.\n";
        }

        // When all the criteria of creating a username and password are met, we can return true.
        if (errorMessage.equals("")) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            // Setting the title of the alert box.
            alert.setTitle("Invalid Fields");
            // Setting the header of the alert box.
            alert.setHeaderText("Please correct invalid fields");
            // Setting the content of the alert box.
            alert.setContentText(errorMessage);
            // Wait till the user reads the message and closes the alert box.
            alert.showAndWait();

            return false;
        }
    }

}
