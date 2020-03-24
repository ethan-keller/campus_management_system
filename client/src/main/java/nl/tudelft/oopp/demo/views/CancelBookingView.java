package nl.tudelft.oopp.demo.views;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class CancelBookingView extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            URL xmlUrl = getClass().getResource("/cancelBooking.fxml");
            loader.setLocation(xmlUrl);
            Parent root = loader.load();

            // Making sure that the scene doesn't resize
            Scene oldScene = primaryStage.getScene();
            primaryStage.setScene(oldScene == null
                    ? new Scene(root, primaryStage.getMinWidth(), primaryStage.getMinHeight())
                    : new Scene(root, oldScene.getWidth(), oldScene.getHeight()));
            primaryStage.setMinHeight(400);
            primaryStage.setMinWidth(800);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
