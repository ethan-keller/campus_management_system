package nl.tudelft.oopp.demo.views;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import nl.tudelft.oopp.demo.general.GeneralMethods;

public class BikeNewDialogView extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            URL xmlUrl = getClass().getResource("/bikeEditDialog.fxml");
            loader.setLocation(xmlUrl);
            Parent root = loader.load();
            root.getStylesheets().add(getClass().getResource("/GeneralStyle.css").toExternalForm());

            // Create the dialog Stage.
            Stage dialogStage = new Stage();

            GeneralMethods.view(dialogStage, primaryStage, "New Bike Reservation", root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
