package nl.tudelft.oopp.demo.admin.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import nl.tudelft.oopp.demo.admin.logic.UserEditDialogLogic;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.general.GeneralMethods;

import java.util.Optional;

public class UserEditDialogController {

    public static boolean edit;
    public static User user;
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
    private static Stage dialogStage;

    private static void emptyUser() {
        user = new User();
    }

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
            if (edit && AdminManageUserViewController.currentSelectedUser.getUserType().get() == 2 && user.getUserType().get() == 1) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("User type modified");
                alert.setContentText("Are you sure you want to change? All the student reservations will be deleted.");
                ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
                ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(okButton, cancelButton);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == okButton) {
                    alert.close();
                    this.dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    dialogStage.close();
                } else {
                    alert.close();
                    this.initialize();
                }
            } else {
                this.dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                dialogStage.close();
            }
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
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        return UserEditDialogLogic.isInputValid(usernameField, userTypeAdmin, userTypeTeacher,
                userTypeStudent, userPasswordField, edit);
    }

}
