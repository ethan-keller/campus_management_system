package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

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
import nl.tudelft.oopp.demo.views.AdminUserHistoryView;
import nl.tudelft.oopp.demo.views.LoginView;
import nl.tudelft.oopp.demo.views.UserEditDialogView;


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
            userTypeColumn.setCellValueFactory(cell -> new SimpleStringProperty(availableUserType.get(
                    cell.getValue().getUserType().get())));
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

    /**
     * The user from the table view is selected.
     *
     * @return User selected.
     */
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
            if (tempUser == null) {
                return;

                // TODO: Check that user creation was successful before displaying alert
            } else {
                UserServerCommunication.createUser(tempUser.getUsername().get(), tempUser.getUserPassword().get(),
                        tempUser.getUserType().get());
            }
            refresh();
            // Informing the admin through a alert box that a new user is created successfully.
            AlertBox("New User","", "Added new user!");
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

                if (tempUser == null) {
                    return;
                }
                if (tempUser.getUserPassword().get() == null) {
                    UserServerCommunication.updateUser(tempUser.getUsername().get(),
                            tempUser.getUserPassword().get(), tempUser.getUserType().get());
                } else {
                    UserServerCommunication.updateUser(tempUser.getUsername().get(),
                            tempUser.getUserPassword().get(), tempUser.getUserType().get());
                }
                refresh();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Edit user");
                alert.setContentText("edited user!");
            } else {
                AlertBox("No Selection","No User Selected", "Please select a user in the table.");
            }
        } catch (Exception e) {
            System.out.println("user edit exception");
            e.printStackTrace();
        }
    }

    /**
     * The history of the particular user is displayed.
     *
     * @param event is passed
     * @throws IOException is thrown
     */
    @FXML
    private void historyClicked(ActionEvent event) throws IOException {
        User selectedUser = getSelectedUser();
        int selectedIndex = getSelectedIndex();
        try {
            if (selectedIndex >= 0) {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentSelectedUser = selectedUser;

                AdminUserHistoryView auhv = new AdminUserHistoryView();
                auhv.start(stage);
            } else {
                AlertBox("No Selection","No User Selected", "Please select a user in the table.");
            }
        } catch (Exception e) {
            System.out.println("user edit exception");
            e.printStackTrace();
        }
    }

    /**
     * Back button redirects the user back to the admin home page.
     *
     * @param event is passed
     * @throws IOException is thrown
     */
    @FXML
    private void backClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // This starts a new admin home page view.
        AdminHomePageView ahpv = new AdminHomePageView();
        ahpv.start(stage);
    }

    /**
     * This button redirects the user back to the login page.
     * @param event is passed.
     * @throws IOException is thrown.
     */
    @FXML
    private void signOutClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // This loads up a new login page.
        LoginView loginView = new LoginView();
        loginView.start(stage);
    }

    /**
     * This method is used to reduce code length so that whenever the developer wants to use an alert method;
     * he/she would use this method with the given parameter.
     * @param title - Title of the alert dialog box
     * @param header - Header of the alert dialog box
     * @param content - Content of the alert dialog box
     */
    private void AlertBox(String title, String header, String content) {

        // Creates a new alert dialog box for displaying the constraints that were not met.
        Alert alert = new Alert(Alert.AlertType.WARNING);

        // Setting the title of the alert box.
        alert.setTitle(title);

        // Setting the header of the alert box.
        alert.setHeaderText(header);

        // Setting the content of the alert box.
        alert.setContentText(content);

        // Wait till the user reads the message and closes the alert box.
        alert.showAndWait();
    }

}
