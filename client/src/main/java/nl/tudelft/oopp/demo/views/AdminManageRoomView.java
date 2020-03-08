package nl.tudelft.oopp.demo.views;

import java.io.IOException;
import java.net.URL;
import javafx.application.Application;
import javafx.stage.Modality;
import nl.tudelft.oopp.demo.controllers.RoomEditDialogController;
import nl.tudelft.oopp.demo.entities.Room;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AdminManageRoomView extends Application{

    public AdminManageRoomView(){}

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/adminManageRoomView.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();

        Scene oldScene = primaryStage.getScene();
        primaryStage.setScene(oldScene == null
                ? new Scene(root, primaryStage.getMinWidth(), primaryStage.getMinHeight())
                : new Scene(root, oldScene.getWidth(), oldScene.getHeight()));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}

