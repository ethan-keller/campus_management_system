package nl.tudelft.oopp.demo.views;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class AdminManageReservationView extends Application {

    /**.
     * Constructor
     */
    public AdminManageReservationView() {
    }

    /**
     * This method is to start the view.
     * @param primaryStage - is passed as parameter
     */
    public void start(Stage primaryStage) {

        try {
            FXMLLoader loader = new FXMLLoader();
            URL xmlUrl = getClass().getResource("/adminManageReservationView.fxml");
            loader.setLocation(xmlUrl);
            Parent root = loader.load();

            //Making sure that the page doesn't resize when we switch between scenes
            Scene oldScene = primaryStage.getScene();
            primaryStage.setScene(oldScene == null
                    ? new Scene(root, primaryStage.getMinWidth(), primaryStage.getMinHeight())
                    : new Scene(root, oldScene.getWidth(), oldScene.getHeight()));
            primaryStage.setMinHeight(400);
            primaryStage.setMinWidth(710);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
