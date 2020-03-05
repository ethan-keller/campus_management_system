package nl.tudelft.oopp.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.views.AdminHomePageView;
import nl.tudelft.oopp.demo.views.AdminManageBuildingView;
import nl.tudelft.oopp.demo.views.AdminManageRoomView;
import nl.tudelft.oopp.demo.views.LoginView;
import java.io.IOException;

public class AdminHomePageController {

    public AdminHomePageView adminHomePageView;

    public void signOutButtonClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        LoginView lv = new LoginView();
        lv.start(stage);
    }

    public void addRoomClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        AdminManageRoomView adm = new AdminManageRoomView();
        adm.start(stage);
    }

    public void addBuildingClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage)  ((Node) event.getSource()).getScene().getWindow();

        AdminManageBuildingView adbv = new AdminManageBuildingView();
        adbv.start(stage);
    }

}