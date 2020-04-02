package nl.tudelft.oopp.demo.views;

import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.tudelft.oopp.demo.communication.GeneralMethods;

public class UserNewDialogView extends Application {

    private static Logger logger = Logger.getLogger("GlobalLogger");

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            URL xmlUrl = getClass().getResource("/userEditDialog.fxml");
            loader.setLocation(xmlUrl);
            Parent root = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();

            GeneralMethods.view(dialogStage, primaryStage, "New User", root);

        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
