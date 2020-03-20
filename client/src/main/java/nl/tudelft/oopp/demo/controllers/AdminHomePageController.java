package nl.tudelft.oopp.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.views.*;

import java.io.IOException;

public class AdminHomePageController {

    public void signOutButtonClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        LoginView loginView = new LoginView();
        loginView.start(stage);
    }

    public void addRoomClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        AdminManageRoomView adminManageRoomView = new AdminManageRoomView();
        adminManageRoomView.start(stage);
    }

    public void addBuildingClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage)  ((Node) event.getSource()).getScene().getWindow();

        AdminManageBuildingView adminManageBuildingView = new AdminManageBuildingView();
        adminManageBuildingView.start(stage);
    }

    public void addReservationClicked(ActionEvent event) throws IOException{
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        AdminManageReservationView amrv = new AdminManageReservationView();
        amrv.start(stage);
    }

    public void manageUserClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage)  ((Node) event.getSource()).getScene().getWindow();

        AdminManageUserView amuv = new AdminManageUserView();
        amuv.start(stage);
    }

}
