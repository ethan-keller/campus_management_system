package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.views.*;

public class AdminHomePageController {
    /**
     * This button lets the admin sign out and redirects the admin back to the login page.
     * @param event is passed
     * @throws IOException is thrown
     */
    public void signOutButtonClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        LoginView loginView = new LoginView();
        loginView.start(stage);
    }

    /**
     * This button redirects the admin to a page where the admin can create/edit/delete rooms.
     * This page also displays all the rooms in the database in a tabular view.
     * @param event is passed
     * @throws IOException is thrown
     */
    public void addRoomClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        AdminManageRoomView adminManageRoomView = new AdminManageRoomView();
        adminManageRoomView.start(stage);
    }

    /**
     * This button redirects the admin to a page where the admin can create/edit/delete a
     * building. This page also displays all the buildings present in a database in a tabular view.
     * @param event is passed
     * @throws IOException is thrown
     */
    public void addBuildingClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        AdminManageBuildingView adminManageBuildingView = new AdminManageBuildingView();
        adminManageBuildingView.start(stage);
    }

    /**
     * This button redirects the admin to a page where the admin can create/edit/delete
     * reservations made by a user. This page also displays all the reservations made by
     * all the users in a tabular view.
     * @param event is passed
     * @throws IOException is thrown
     */
    public void addReservationClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        AdminManageReservationView amrv = new AdminManageReservationView();
        amrv.start(stage);
    }

    /**
     * This button would redirects the admin to a page where the admin can create/edit/delete an
     * users' information or look at their reservation history. This button also displays all the
     * users and information related to them in a tabular view.
     * @param event is passed
     * @throws IOException is thrown
     */
    public void manageUserClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        AdminManageUserView amuv = new AdminManageUserView();
        amuv.start(stage);
    }

    /**
     * This button would redirects the admin to a page where the admin can create/edit/delete
     * food information. This button also displays all the foods and information related to
     * them in a tabular view.
     * @param event
     * @throws IOException
     */
    public void manageFoodClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage)  ((Node) event.getSource()).getScene().getWindow();

        AdminManageFoodView amfv = new AdminManageFoodView();
        amfv.start(stage);
    }

    /**
     * This button would redirects the admin to a page where the admin can create/edit/delete
     * bike reservation information. This button also displays all the bike reservation information
     * in a tabular view.
     * @param event
     * @throws IOException
     */
    public void manageBikeClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage)  ((Node) event.getSource()).getScene().getWindow();

        AdminBikeReservationView abrv = new AdminBikeReservationView();
        abrv.start(stage);
    }

}
