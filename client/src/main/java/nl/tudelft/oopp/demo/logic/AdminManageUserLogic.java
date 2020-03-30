package nl.tudelft.oopp.demo.logic;

import java.io.UnsupportedEncodingException;

import javafx.scene.control.TableView;
import nl.tudelft.oopp.demo.communication.UserServerCommunication;
import nl.tudelft.oopp.demo.entities.User;

public class AdminManageUserLogic {
    /**.
     * This method is used to select a User from the tabular view of the Users
     * Constraints are added; if the User index is less than 0, null is returned.
     * @param userTable - The selected User from the table
     * @return - User
     */
    public static User getSelectedUser(TableView<User> userTable) {
        if (userTable.getSelectionModel().getSelectedIndex() >= 0) {
            return userTable.getSelectionModel().getSelectedItem();
        } else {
            return null;
        }
    }

    /**.
     * This method is used in the adminManageUserViewController class to communicate with the server to
     * command them to delete the selected User.
     * @param selectedUser - Selected User from the table
     */
    public static void deleteUserLogic(User selectedUser) {
        try {
            UserServerCommunication.deleteUser(selectedUser.getUsername().getValue());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**.
     * This method is used in adminManageUserViewController class to communicate with the server to
     * command them to create a new User.
     * @param tempUser - A User with all the required features to be created.
     */
    public static void createUserLogic(User tempUser) {
        try {
            UserServerCommunication.createUser(tempUser.getUsername().get(), tempUser.getUserPassword().get(),
                    tempUser.getUserType().get());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();;
        }
    }

    /**.
     * This method is used in the adminManageUserViewController class to communicate with the server to
     * command them to edit the selected User.
     * @param tempUser - These are the edited features of the User object passed as parameter.
     */
    public static void editUserLogic(User tempUser) {
        try {
            UserServerCommunication.updateUser(tempUser.getUsername().get(),
                    tempUser.getUserPassword().get(), tempUser.getUserType().get());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
