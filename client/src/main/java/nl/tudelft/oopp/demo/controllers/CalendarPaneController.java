package nl.tudelft.oopp.demo.controllers;

import com.mindfusion.scheduling.Calendar;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import nl.tudelft.oopp.demo.calendar.CustomCalendar;

import javax.swing.*;
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
}
