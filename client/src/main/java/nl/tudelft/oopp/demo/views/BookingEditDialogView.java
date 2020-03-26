package nl.tudelft.oopp.demo.views;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import nl.tudelft.oopp.demo.controllers.BookingEditDialogController;


public class BookingEditDialogView extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            URL xmlUrl = getClass().getResource("/bookingEditDialog.fxml");
            loader.setLocation(xmlUrl);
            Parent root = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Reservation");
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);
            dialogStage.setResizable(false);
            dialogStage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST,
            event -> BookingEditDialogController.reservation = null);

            // Set the dialog stage properties
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
