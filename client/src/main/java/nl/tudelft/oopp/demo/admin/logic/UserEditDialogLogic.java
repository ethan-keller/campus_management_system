package nl.tudelft.oopp.demo.admin.logic;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import nl.tudelft.oopp.demo.entities.BikeReservation;
import nl.tudelft.oopp.demo.entities.Reservation;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.general.GeneralMethods;


public class UserEditDialogLogic {

    List<Reservation> reservationList = Reservation.getAllReservations();

    /**
     * To validate the input of the user.
     *
     * @param usernameField     - Username
     * @param userTypeAdmin     - Admin type
     * @param userTypeTeacher   - Teacher type
     * @param userTypeStudent   - Student type
     * @param userPasswordField - Password
     * @param edit              - Boolean value
     * @return Boolean value to validate
     */
    public static boolean isInputValid(TextField usernameField, RadioButton userTypeAdmin,
                                       RadioButton userTypeTeacher, RadioButton userTypeStudent,
                                       PasswordField userPasswordField, boolean edit) {
        String errorMessage = "";
        // This pattern is used to compare the text to see if it has any punctuations.
        Pattern characters = Pattern.compile("[!@#$%^&*`~<,>./?:;'{|+=_-]");
        // This pattern is used to compare the text to see if it has any spaces.
        Pattern space = Pattern.compile(" ");

        // Checks whether the username field is empty.
        if (!edit) {
            if (usernameField.getText().trim().isEmpty()) {
                errorMessage += "Username field can't be blank!\n";
            }

            // Checks whether the username contains any spaces.
            if (space.matcher(usernameField.getText()).find()) {
                errorMessage += "Username is not allowed to have any spaces.\n";
            }
            // Checks whether the username contains any punctuations.
            if (characters.matcher(usernameField.getText()).find()) {
                errorMessage += "Username is not allowed to have any punctuations.\n";
            }
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
            // Checks whether the password field is at least 8 characters long.
            if (userPasswordField.getLength() < 8) {
                errorMessage += "Password needs to at-least 8 characters.\n";
            }
            // Checks whether the password contains at least one numeric value.
            if (!userPasswordField.getText().matches(".*\\d.*")) {
                errorMessage += "Password needs at-least 1 numeric value.\n";
            }
            // This pattern is used to compare the text to see if it has an upper case character.
            Pattern upperCasePattern = Pattern.compile("[A-Z]");
            // Checks whether the password contains at least one upper case character.
            if (!upperCasePattern.matcher(userPasswordField.getText()).find()) {
                errorMessage += "Password needs at-least 1 upper case character.\n";
            }
            // Checks whether the password contains any spaces.
            if (space.matcher(userPasswordField.getText()).find()) {
                errorMessage += "Password is not allowed to have spaces in them.\n";
            }
            // Checks whether the password contains any punctuations.
            if (characters.matcher(userPasswordField.getText()).find()) {
                errorMessage += "Password is not allowed to have any punctuations.\n";
            }
        }
        if (edit && !userPasswordField.getText().trim().isEmpty()) {
            // Checks whether the password field is at least 8 characters long.
            if (userPasswordField.getLength() < 8) {
                errorMessage += "Password needs to at-least 8 characters.\n";
            }
            // Checks whether the password contains at least one numeric value.
            if (!userPasswordField.getText().matches(".*\\d.*")) {
                errorMessage += "Password needs at-least 1 numeric value.\n";
            }
            // This pattern is used to compare the text to see if it has an upper case character.
            Pattern upperCasePattern = Pattern.compile("[A-Z]");
            // Checks whether the password contains at least one upper case character.
            if (!upperCasePattern.matcher(userPasswordField.getText()).find()) {
                errorMessage += "Password needs at-least 1 upper case character.\n";
            }
            // Checks whether the password contains any spaces.
            if (space.matcher(userPasswordField.getText()).find()) {
                errorMessage += "Password is not allowed to have spaces in them.\n";
            }
            // Checks whether the password contains any punctuations.
            if (characters.matcher(userPasswordField.getText()).find()) {
                errorMessage += "Password is not allowed to have any punctuations.\n";
            }
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

    /**
     * Removes all the old teacher reservations when a user gets switched from teacher to student.
     */
    public static void deleteOldTeacherReservations(User user) {
        List<Reservation> reservationList = Reservation.getUserReservation(user);

        if (reservationList == null) {
            return;
        }
        List<Room> roomList = Room.getRoomData();
        List<Room> rooms = roomList.stream()
                .filter(x -> x.getTeacherOnly().get() == true)
                .collect(Collectors.toList());
        for (Reservation re: reservationList) {
            for (Room ro: rooms) {
                if (re.getRoom().get() == ro.getRoomId().get()) {
                    AdminLogic.deleteReservationLogic(re);
                }
            }
        }
    }

}
