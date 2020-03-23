package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.views.AdminHomePageView;
import nl.tudelft.oopp.demo.views.AdminManageBuildingView;
import nl.tudelft.oopp.demo.views.AdminManageReservationView;
import nl.tudelft.oopp.demo.views.AdminManageRoomView;
import nl.tudelft.oopp.demo.views.AdminManageUserView;
import nl.tudelft.oopp.demo.views.LoginView;

public class AdminHomePageController {
    /**
     * Logs the user out when the log out button is pressed.
     * Also returns the user to the login view.
     *
     * @param event //TODO
     * @throws IOException //TODO
     */
    public void signOutButtonClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        LoginView loginView = new LoginView();
        loginView.start(stage);
    }

    /**
     * //TODO.
     *
     * @param event //TODO.
     * @throws IOException //TODO.
     */
    public void addRoomClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        AdminManageRoomView adminManageRoomView = new AdminManageRoomView();
        adminManageRoomView.start(stage);
    }

    /**
     * //TODO.
     *
     * @param event //TODO.
     * @throws IOException //TODO.
     */
    public void addBuildingClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        AdminManageBuildingView adminManageBuildingView = new AdminManageBuildingView();
        adminManageBuildingView.start(stage);
    }

    /**
     * //TODO.
     *
     * @param event //TODO.
     * @throws IOException //TODO.
     */
    public void addReservationClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        AdminManageReservationView amrv = new AdminManageReservationView();
        amrv.start(stage);
    }

    /**
     * //TODO.
     *
     * @param event //TODO.
     * @throws IOException //TODO.
     */
    public void manageUserClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        AdminManageUserView amuv = new AdminManageUserView();
        amuv.start(stage);
    }

}
