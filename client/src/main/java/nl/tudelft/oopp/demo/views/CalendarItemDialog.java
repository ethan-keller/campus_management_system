package nl.tudelft.oopp.demo.views;

import java.net.URL;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * View class for the calendar item dialog box.
 */
public class CalendarItemDialog extends Application {

    /**
     * Starts the thread to load the fxml as a view in the given stage.
     *
     * @param primaryStage stage to load view in
     * @throws Exception exception to be catched if something goes wrong
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/CalendarItemDialog.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();
        root.getStylesheets().add(getClass().getResource("/GeneralStyle.css").toExternalForm());

        primaryStage.setMinWidth(270);
        primaryStage.setMinHeight(320);

        // create the new dialog stage in another runnable (for JavaFX reasons)
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                // Create the dialog Stage.
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setResizable(false);

                // Set the dialog stage properties
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(primaryStage);

                // Show the dialog and wait until the user closes it
                stage.showAndWait();
            }
        });

    }

    /**
     * Main method.
     *
     * @param args command line parameters
     */
    public static void main(String[] args) {
        launch(args);
    }
}
