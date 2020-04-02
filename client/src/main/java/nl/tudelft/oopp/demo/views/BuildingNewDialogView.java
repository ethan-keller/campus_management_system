package nl.tudelft.oopp.demo.views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import nl.tudelft.oopp.demo.communication.GeneralMethods;
import nl.tudelft.oopp.demo.controllers.BuildingEditDialogController;

public class BuildingNewDialogView extends Application {

    private static Logger logger = Logger.getLogger("GlobalLogger");

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            URL xmlUrl = getClass().getResource("/buildingEditDialog.fxml");
            loader.setLocation(xmlUrl);
            Parent root = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();

            GeneralMethods.view(dialogStage, primaryStage,"New Building", root);
            dialogStage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST,
                    event -> BuildingEditDialogController.building = null);

        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
