package nl.tudelft.oopp.demo.views;

import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import nl.tudelft.oopp.demo.communication.GeneralMethods;
import nl.tudelft.oopp.demo.controllers.RoomEditDialogController;


public class RoomEditDialogView extends Application {

    private static Logger logger = Logger.getLogger("GlobalLogger");

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            URL xmlUrl = getClass().getResource("/roomEditDialog.fxml");
            loader.setLocation(xmlUrl);
            Parent root = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();

            GeneralMethods.view(dialogStage, primaryStage, "Edit Room", root);

        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
