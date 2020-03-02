package nl.tudelft.oopp.demo.views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.controllers.RoomEditDialogController;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;

import java.io.IOException;
import java.net.URL;

public class AdminManageBuildingView extends Application{

    private Stage primaryStage;

    public AdminManageBuildingView(){}

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        showAdminManageBuildingView();
    }
    /**
     * Shows the room overview.
     */
    public void showAdminManageBuildingView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            URL xmlUrl = getClass().getResource("/adminManageRoomView.fxml");
            loader.setLocation(xmlUrl);
            Parent root = loader.load();

            /**
             * Making sure that the page doesn't resize when we switch between scenes
             */
            Scene oldScene = primaryStage.getScene();
            primaryStage.setScene(oldScene == null
                    ? new Scene(root, primaryStage.getMinWidth(), primaryStage.getMinHeight())
                    : new Scene(root, oldScene.getWidth(), oldScene.getHeight()));
            primaryStage.show();
//            Scene scene = new Scene(root);
//            primaryStage.setScene(scene);
//            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens a dialog to edit details for the specified room. If the user
     * clicks OK, the changes are saved into the provided room object and true
     * is returned.
     *
     * @param building the builiding  object to be edited
     * @return true if the user clicked OK, false otherwise.
     */
    public  boolean showBuildingEditDialog(Building building) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            URL xmlUrl = getClass().getResource("/buildingEditDialog.fxml");
            loader.setLocation(xmlUrl);
            Parent root = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Building");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            // Set the room into the controller.
            RoomEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setBuilding(building);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }


    public static void main(String[] args) {
        launch(args);
    }

}

