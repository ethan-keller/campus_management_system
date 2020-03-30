package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.views.SearchView;

public class UserHomePageController {

    @FXML
    private Rectangle bookANewRoom;

    @FXML
    private Rectangle bookingHistory;

    /**
     * .
     *
     * @param event //TODO.
     * @throws IOException //TODO.
     */
    public void setBookANewRoomClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        SearchView sv = new SearchView();
        sv.start(stage);

    }
}
