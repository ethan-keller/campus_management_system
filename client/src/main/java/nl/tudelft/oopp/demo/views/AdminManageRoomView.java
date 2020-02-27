package nl.tudelft.oopp.demo.views;

import java.io.IOException;
import java.net.URL;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import nl.tudelft.oopp.demo.controllers.RoomEditDialogController;
import nl.tudelft.oopp.demo.controllers.AdminManageRoomViewController;
import nl.tudelft.oopp.demo.item.Room;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AdminManageRoomView extends Application{

    private Stage primaryStage;
    private ObservableList<Room> roomData = FXCollections.observableArrayList();
    public ObservableList<Room> getRoomData() {
        return roomData;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        roomData.add(new Room ("123456", "einstein", "Pulse", "Project room", 5, "TV"));
        this.primaryStage = primaryStage;
        showAdminManageRoomView();
    }
    /**
     * Shows the room overview.
     */
    public void showAdminManageRoomView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            URL xmlUrl = getClass().getResource("/adminManageRoomView.fxml");
            loader.setLocation(xmlUrl);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            //System.out.println(root.lookup("#roomIdColumn"));
            //TableView<Room> tableView = (TableView<Room>)root.lookup("#roomTable");
            //ObservableList<Room> rooms = FXCollections.observableArrayList();
            //rooms.add(new Room ("123456", "einstein", "Pulse", "Project room", 5, "TV"));
            //tableView.setItems(rooms);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens a dialog to edit details for the specified room. If the user
     * clicks OK, the changes are saved into the provided room object and true
     * is returned.
     *
     * @param room the room object to be edited
     * @return true if the user clicked OK, false otherwise.
     */
    public  boolean showRoomEditDialog(Room room) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            URL xmlUrl = getClass().getResource("/roomEditDialog.fxml");
            loader.setLocation(xmlUrl);
            Parent root = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Room");
            //dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            // Set the room into the controller.
            //RoomEditDialogController controller = loader.getController();
            //controller.setDialogStage(dialogStage);
            //controller.setRoom(room);

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
    public AdminManageRoomView(){}

    public static void main(String[] args) {
        launch(args);
    }

}

