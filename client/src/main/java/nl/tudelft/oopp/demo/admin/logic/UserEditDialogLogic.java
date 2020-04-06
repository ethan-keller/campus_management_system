package nl.tudelft.oopp.demo.admin.logic;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

<<<<<<< HEAD
=======
import nl.tudelft.oopp.demo.entities.Reservation;
import nl.tudelft.oopp.demo.entities.Room;
>>>>>>> develop
import nl.tudelft.oopp.demo.general.GeneralMethods;


public class UserEditDialogLogic {

    /**
     * To validate the input of the user.
     *
<<<<<<< HEAD
     * @param username                - Username
     * @param userTypeAdminSelected   - Admin type
     * @param userTypeTeacherSelected - Teacher type
     * @param userTypeStudentSelected - Student type
     * @param password                - Password
     * @param edit                    - Boolean value
=======
     * @param usernameField     - Username
     * @param userTypeAdmin     - Admin type
     * @param userTypeTeacher   - Teacher type
     * @param userTypeStudent   - Student type
     * @param userPasswordField - Password
     * @param edit              - Boolean value
>>>>>>> develop
     * @return Boolean value to validate
     */
    public static String isInputValid(String username, boolean userTypeAdminSelected,
                                       boolean userTypeTeacherSelected, boolean userTypeStudentSelected,
                                       String password, boolean edit) {
        String errorMessage = "";
<<<<<<< HEAD
        // Checks whether the type of user is selected.
        if ((userTypeAdminSelected || userTypeTeacherSelected
                || userTypeStudentSelected) == false) {
            errorMessage += "Select the type of user!\n";
        }
        errorMessage = isUsernameValid(errorMessage, username);
        errorMessage = isPasswordValid(errorMessage, password, edit);
        return errorMessage;
    }
=======
        // This pattern is used to compare the text to see if it has any punctuations.
        Pattern characters = Pattern.compile("[!@#$%^&*`~<,>./?:;'{|+=_-]");
        // This pattern is used to compare the text to see if it has any spaces.
        Pattern space = Pattern.compile(" ");
>>>>>>> develop

    /**
     * Checks if the username is valid.
     * Helpermethod for isInputValid.
     *
     * @param errorMessage String
     * @param username     String
     */
    public static String isUsernameValid(String errorMessage, String username) {
        // Checks whether the username field is empty.
<<<<<<< HEAD
        if (username.trim().isEmpty()) {
            errorMessage += "Username field can't be blank!\n";
=======
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
>>>>>>> develop
        }
        // This pattern is used to compare the text to see if it has any spaces.
        Pattern space = Pattern.compile(" ");
        // Checks whether the username contains any spaces.
        if (space.matcher(username).find()) {
            errorMessage += "Username is not allowed to have any spaces.\n";
        }
        // This pattern is used to compare the text to see if it has any punctuations.
        Pattern characters = Pattern.compile("[!@#$%^&*`~<,>./?:;'{|+=_-]");
        // Checks whether the username contains any punctuations.
        if (characters.matcher(username).find()) {
            errorMessage += "Username is not allowed to have any punctuations.\n";
        }
        return errorMessage;
    }

    /**
     * Checks if the password is valid.
     * Helpermethod for isInputValid.
     *
     * @param errorMessage String
     * @param password     String
     * @param edit         Boolean
     */
    public static String isPasswordValid(String errorMessage, String password, boolean edit) {

        // Checks whether the password field is empty.
        if (!edit) {
            if (password.trim().isEmpty()) {
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
<<<<<<< HEAD
        // Checks whether the password field is atleast 8 characters long.
        if (password.length() < 8) {
            errorMessage += "Password needs to at-least 8 characters.\n";
        }
        // Checks whether the password contains atleast one numeric value.
        if (!password.matches(".*\\d.*")) {
            errorMessage += "Password needs at-least 1 numeric value.\n";
        }
        // This pattern is used to compare the text to see if it has an upper case character.
        Pattern upperCasePattern = Pattern.compile("[A-Z]");
        // Checks whether the password contains atleast one upper case character.
        if (!upperCasePattern.matcher(password).find()) {
            errorMessage += "Password needs at-least 1 upper case character.\n";
        }
        // This pattern is used to compare the text to see if it has any spaces.
        Pattern space = Pattern.compile(" ");
        // Checks whether the password contains any spaces.
        if (space.matcher(password).find()) {
            errorMessage += "Password is not allowed to have spaces in them.\n";
        }
        // This pattern is used to compare the text to see if it has any punctuations.
        Pattern characters = Pattern.compile("[!@#$%^&*`~<,>./?:;'{|+=_-]");
        // Checks whether the password contains any punctuations.
        if (characters.matcher(password).find()) {
            errorMessage += "Password is not allowed to have any punctuations.\n";
        }
        return errorMessage;
=======
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
>>>>>>> develop
    }

    /**
     * Removes all the old teacher reservations when a user gets switched from teacher to student.
     */
    public static void deleteOldTeacherReservations() {
        List<Reservation> reservationList = Reservation.getSelectedUserReservation();
        if (reservationList == null) {
            return;
        }
        List<Room> roomList = Room.getRoomData();
        assert roomList != null;
        for (Reservation r : reservationList) {
            Room room = roomList.stream().filter(x -> x.getRoomId().get() == r.getRoom().get())
                    .collect(Collectors.toList()).get(0);
            if (!room.getTeacherOnly().get()) {
                reservationList.remove(r);
            }
        }
        for (Reservation r : reservationList) {
            AdminLogic.deleteReservationLogic(r);
        }
    }

}
