package nl.tudelft.oopp.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
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
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";


        if (usernameField.getText().equals("")) {
            errorMessage += "No valid username!\n";
        }

        if ((userTypeAdmin.isSelected() || userTypeTeacher.isSelected()
                || userTypeStudent.isSelected()) == false) {
            errorMessage += "No valid user type!\n";
        }
        if (!edit) {
            if (userPasswordField.getText().equals("")) {
                errorMessage += "No valid password!\n";
            }
        }

        if (errorMessage.equals("")) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);
            alert.showAndWait();

            return false;
        }
    }

}
