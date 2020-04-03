package nl.tudelft.oopp.demo.admin.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import nl.tudelft.oopp.demo.admin.logic.UserEditDialogLogic;
import nl.tudelft.oopp.demo.communication.GeneralMethods;
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
        String username = usernameField.getText();
        String password = userPasswordField.getText();
        boolean userTypeAdminSelected= userTypeAdmin.isSelected();
        boolean userTypeTeacherSelected = userTypeTeacher.isSelected();
        boolean userTypeStudentSelected = userTypeStudent.isSelected();

        String errorMessage = UserEditDialogLogic.isInputValid(username, userTypeAdminSelected,
                userTypeTeacherSelected, userTypeStudentSelected, password, edit);
        if (errorMessage == "" ){
            return true;
        } else{
            // Show the error message.
            GeneralMethods.alertBox("Invalid Fields", "Please correct the invalid fields",
                    errorMessage, Alert.AlertType.ERROR);
            return false;
        }


    }

}
