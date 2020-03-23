package nl.tudelft.oopp.demo.views;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;



public class ReservationConfirmationView extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            URL xmlUrl = getClass().getResource("/ReservationConfirmationView.fxml");
            loader.setLocation(xmlUrl);
            Parent root = loader.load();

            // Create the dialog Stage.
            Stage stage = new Stage();
            stage.setTitle("Edit Building");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
//            stage.getScene().getWindow().addEventFilter(
//                    WindowEvent.WINDOW_CLOSE_REQUEST,
//                    event -> x
//            );

            // Set the dialog stage properties
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(primaryStage);

            // Show the dialog and wait until the user closes it
            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
