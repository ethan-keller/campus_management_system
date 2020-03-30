package nl.tudelft.oopp.demo.logic;

import java.util.regex.Pattern;

import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import nl.tudelft.oopp.demo.communication.GeneralMethods;


public class UserEditDialogLogic {

    /**
     * To validate the input of the user.
     * @param usernameField - Username
     * @param userTypeAdmin - Admin type
     * @param userTypeTeacher - Teacher type
     * @param userTypeStudent - Student type
     * @param userPasswordField - Password
     * @param edit - Boolean value
     * @return Boolean value to validate
     */
    public static boolean isInputValid(TextField usernameField, RadioButton userTypeAdmin,
                                       RadioButton userTypeTeacher, RadioButton userTypeStudent,
                                       PasswordField userPasswordField, boolean edit) {
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
            GeneralMethods.alertBox("Invalid Fields", "Please correct the invalid fields",
                    errorMessage, Alert.AlertType.ERROR);
            return false;
        }
    }
}
