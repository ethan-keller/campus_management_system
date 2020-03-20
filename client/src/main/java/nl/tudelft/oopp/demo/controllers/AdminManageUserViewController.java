package nl.tudelft.oopp.demo.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.UserServerCommunication;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.views.AdminHomePageView;
import nl.tudelft.oopp.demo.views.UserEditDialogView;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AdminManageUserViewController {

    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<User, String> usernameColumn;

    @FXML
    private TableColumn<User, String> userTypeColumn;

    @FXML
    private TableColumn<User, String> userPasswordColumn;

    public static User currentSelectedUser;

    public AdminManageUserViewController() {
    }

    /**
     * Show all the buildings in the left side table.
     */
    @FXML
    private void initialize() {
        try {
            // Initialize the room table with the four columns.
            usernameColumn.setCellValueFactory(cell -> cell.getValue().getUsername());
            List<String> availableUserType = Arrays.asList("Admin", "Teacher", "Student");
            userTypeColumn.setCellValueFactory(cell -> new SimpleStringProperty(availableUserType.get(cell.getValue().getUserType().get())));
            userPasswordColumn.setCellValueFactory(cell -> cell.getValue().getUserPassword());
            // Add observable list data to the table
            userTable.setItems(User.getUserData());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refresh() {
        initialize();
    }

    public User getSelectedUser() {
        if (userTable.getSelectionModel().getSelectedIndex() >= 0) {
            return userTable.getSelectionModel().getSelectedItem();
        } else {
            return null;
        }
    }

    public int getSelectedIndex() {
        return userTable.getSelectionModel().getSelectedIndex();
    }

    /**
     * Delete a building.
     */
    @FXML
    private void deleteUserClicked(ActionEvent event) {
        User selectedUser = getSelectedUser();
        int selectedIndex = getSelectedIndex();
        try {
            if (selectedIndex >= 0) {
                // TODO: Check that building deletion was succesful before displaying alert
                UserServerCommunication.deleteUser(selectedUser.getUsername().getValue());
                refresh();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Delete user");
                alert.setContentText("User deleted!");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No Selection");
                alert.setHeaderText("No User Selected");
                alert.setContentText("Please select a user in the table.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            System.out.println("delete user exception");
            e.printStackTrace();
        }
    }

    /**
     * Handles clicking the create new button.
     */
    @FXML
    private void createNewUserClicked(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            currentSelectedUser = null;
            UserEditDialogController.edit = false;
            UserEditDialogView view = new UserEditDialogView();
            view.start(stage);
            User tempUser = UserEditDialogController.user;
            if (tempUser == null) return;
                // TODO: Check that user creation was succesful before displaying alert
            else
                UserServerCommunication.createUser(tempUser.getUsername().get(), tempUser.getUserPassword().get(), tempUser.getUserType().get());
            refresh();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("New user");
            alert.setContentText("Added new user!");
        } catch (Exception e) {
            System.out.println("user creation exception");
            e.printStackTrace();
        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected building.
     */
    @FXML
    private void editUserClicked(ActionEvent event) {
        User selectedUser = getSelectedUser();
        int selectedIndex = getSelectedIndex();
        try {
            if (selectedIndex >= 0) {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentSelectedUser = selectedUser;

                UserEditDialogController.edit = true;
                UserEditDialogView view = new UserEditDialogView();
                view.start(stage);
                User tempUser = UserEditDialogController.user;

                if (tempUser == null) return;
                if (tempUser.getUserPassword().get() == null) {
                    UserServerCommunication.updateUser(tempUser.getUsername().get(), tempUser.getUserPassword().get(), tempUser.getUserType().get());
                } else
                    UserServerCommunication.updateUser(tempUser.getUsername().get(), tempUser.getUserPassword().get(), tempUser.getUserType().get());
                refresh();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Edit user");
                alert.setContentText("edited user!");
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No Selection");
                alert.setHeaderText("No User Selected");
                alert.setContentText("Please select a user in the table.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            System.out.println("user edit exception");
            e.printStackTrace();
        }
    }

    @FXML
    private void backClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        AdminHomePageView ahpv = new AdminHomePageView();
        ahpv.start(stage);
    }

//    @FXML
//    private void historyClicked(ActionEvent event) throws IOException {
//        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//
//        bookingHistoryView bhv = new bookingHistoryView();
//        bhv.start(stage);
//    }
}
