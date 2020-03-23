package nl.tudelft.oopp.demo.views;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;

public class SearchView extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {

        SplitPane splitPane = new SplitPane();
        splitPane.setDividerPosition(248, 0.248);

        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/SearchView.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();

        Scene oldScene = primaryStage.getScene();
        primaryStage.setScene(oldScene == null
                ? new Scene(root, primaryStage.getMinWidth(), primaryStage.getMinHeight())
                : new Scene(root, oldScene.getWidth(), oldScene.getHeight()));
        primaryStage.setMinHeight(510);
        primaryStage.setMinWidth(840);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}