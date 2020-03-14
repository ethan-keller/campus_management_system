package nl.tudelft.oopp.demo.views;

import java.io.IOException;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.controllers.RoomViewController;

public class RoomView extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        RoomViewController.thisStage = primaryStage;
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/roomView.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();



        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

