package nl.tudelft.oopp.demo.controllers;

import com.mindfusion.scheduling.Calendar;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.calendar.CustomCalendar;
import nl.tudelft.oopp.demo.views.SearchView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CalendarPaneController implements Initializable {

    @FXML
    private AnchorPane pane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SwingNode node = new SwingNode();
        configureNode(node);
        pane.getChildren().add(node);
    }

    private void configureNode(SwingNode node) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Calendar c = new CustomCalendar();
                c.setSize((int) pane.getScene().getWindow().getWidth(), (int) pane.getScene().getWindow().getHeight());
                //c.addCalendarListener(new CalendarAdapter());
                node.setContent(c);
            }
        });
    }

    public void BackButtonClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        SearchView searchView = new SearchView();
        searchView.start(stage);
    }
}
