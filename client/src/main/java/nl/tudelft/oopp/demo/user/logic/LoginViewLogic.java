package nl.tudelft.oopp.demo.user.logic;

public class LoginViewLogic {

    /**
<<<<<<< HEAD
     * Checks if the username input and password input are valid.
     * @param usernameInput input of the username.
     * @param passwordInput input of the password.
     * @return a String with what is wrong or that it is good.
     */
    public static String isValidInput(String usernameInput, String passwordInput) {
        while (true) {
            // Checks whether the password username field is left empty or not.
            if (usernameInput.isEmpty()) {
                return "The username field cannot be left empty !";
            } else if (passwordInput.isEmpty()) {
                // Checks whether the password field is left empty.
                return "The password field cannot be left empty !";
            } else {
                // This string value means that all the fields are filled.
                return "Good!";
            }
=======
     * Verifying if the username and password field are left empty or not.
     *
     * @param username      - Username entered
     * @param usernameLabel - Label to display if username is not entered
     * @param password      - Password Entered
     * @param passwordLabel - Label to display if password is not entered
     * @return - Boolean value which indicates whether the username and password fields are empty.
     */
    public static boolean isValidInput(TextField username, Label usernameLabel, PasswordField password,
                                       Label passwordLabel) {

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
>>>>>>> develop
        }

    }
}
