package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.GeneralMethods;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.logic.AdminLogic;
import nl.tudelft.oopp.demo.views.AdminHomePageView;
import nl.tudelft.oopp.demo.views.AdminUserBikeView;
import nl.tudelft.oopp.demo.views.AdminUserHistoryView;
import nl.tudelft.oopp.demo.views.LoginView;
import nl.tudelft.oopp.demo.views.UserEditDialogView;



public class AdminManageUserViewController {

    private static Logger logger = Logger.getLogger("GlobalLogger");

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
            logger.log(Level.SEVERE, e.toString());
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
        return AdminLogic.getSelectedUser(userTable);
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
                AdminLogic.deleteUserLogic(selectedUser);
                refresh();
                // Creates an alert box for transparent communication.
                GeneralMethods.alertBox("Delete user", "", "User deleted!", Alert.AlertType.INFORMATION);
            } else {
                // Creates an alert box.
                GeneralMethods.alertBox("No Selection", "No User Selected",
                        "Please select a User in the table.", Alert.AlertType.WARNING);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
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
                AdminLogic.createUserLogic(tempUser);
            }
            refresh();
            // Informing the admin through a alert box that a new user is created successfully.
            GeneralMethods.alertBox("New user", "", "New User created!", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
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
                AdminLogic.editUserLogic(tempUser);
                refresh();
                // Creates an alert box for transparent communication.
                GeneralMethods.alertBox("Edit user", "", "User edited!", Alert.AlertType.INFORMATION);
            } else {
                // Creates an alert box.
                GeneralMethods.alertBox("No Selection", "No User Selected",
                        "Please select an User in the table.", Alert.AlertType.WARNING);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
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
                // Creates an alert box.
                GeneralMethods.alertBox("No Selection", "No User Selected",
                        "Please select a User in the table.", Alert.AlertType.WARNING);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
        }
    }

    /**
     * Back button redirects the user back to the admin home page.
     *
     * @param event is passed
     * @throws IOException is thrown
     */
    @FXML
    private void bikeClicked(ActionEvent event) throws IOException {
        User selectedUser = getSelectedUser();
        int selectedIndex = getSelectedIndex();
        try {
            if (selectedIndex >= 0) {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentSelectedUser = selectedUser;

                AdminUserBikeView auhv = new AdminUserBikeView();
                auhv.start(stage);
            } else {
                // Creates an alert box.
                GeneralMethods.alertBox("No Selection", "No User Selected",
                        "Please select a User in the table.", Alert.AlertType.WARNING);
            }
        } catch (Exception e) {
            System.out.println("user edit exception");
            e.printStackTrace();
        }
    }

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

}
