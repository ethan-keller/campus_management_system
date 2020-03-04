package nl.tudelft.oopp.demo.views;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import javafx.application.Application;

import java.io.IOException;
import java.net.URL;


public class BookingHistoryView extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/bookingHistory.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();

        /**
         * Making sure that the scene doesn't resize
         */
        Scene oldScene = primaryStage.getScene();
        primaryStage.setScene(oldScene == null
                ? new Scene(root, primaryStage.getMinWidth(), primaryStage.getMinHeight())
                : new Scene(root, oldScene.getWidth(), oldScene.getHeight()));
        primaryStage.show();

    }

    public static void main(String[] args) { launch(args);}

}

