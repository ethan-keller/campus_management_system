package nl.tudelft.oopp.demo.views;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class AdminManageUserView extends Application {

    private static Logger logger = Logger.getLogger("GlobalLogger");

    /**.
     * Constructor
     */
    public AdminManageUserView() {
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            URL xmlUrl = getClass().getResource("/adminManageUserView.fxml");
            loader.setLocation(xmlUrl);
            Parent root = loader.load();

            /*
             * Making sure that the page doesn't resize when we switch between scenes
             */
            Scene oldScene = primaryStage.getScene();
            primaryStage.setScene(oldScene == null
                    ? new Scene(root, primaryStage.getMinWidth(), primaryStage.getMinHeight())
                    : new Scene(root, oldScene.getWidth(), oldScene.getHeight()));

            primaryStage.setMinHeight(500);
            primaryStage.setMinWidth(980);
            primaryStage.show();


        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}

