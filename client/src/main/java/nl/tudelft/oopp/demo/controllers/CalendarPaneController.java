package nl.tudelft.oopp.demo.controllers;

import com.mindfusion.scheduling.Calendar;
import com.mindfusion.scheduling.CalendarView;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.calendar.CustomCalendar;
import nl.tudelft.oopp.demo.views.CalenderAppointmentDialogView;
import nl.tudelft.oopp.demo.views.LoginView;
import nl.tudelft.oopp.demo.views.SearchView;

import javax.swing.*;
import javafx.event.ActionEvent;
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
        // This adds the node created which is basically the calender into the anchor pane.
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

    /**
     * This button opens a dialog box to get the details of an appointment like date, time and description and adds it to
     * the calender.
     * @param event
     */
    public void addAppointmentClicked(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        CalenderAppointmentDialogView calenderAppointmentDialogView = new CalenderAppointmentDialogView();
        calenderAppointmentDialogView.start(stage);
    }
    //To be implemented.
    public void cancelAppointmentClicked() {}
    public void cancelReservationClicked() {}
    public void weeklyViewClicked() {
        Calendar c = new CustomCalendar();
        c.setCurrentView(CalendarView.WeekRange);
    }
    public void monthlyViewClicked() {
        Calendar c = new CustomCalendar();
        c.setCurrentView(CalendarView.SingleMonth);
    }

    /**
     * For a user to book a room, the user needs to go to search view and select the room to book.
     * @param event
     * @throws IOException
     */
    public void addReservationClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        SearchView searchView = new SearchView();
        searchView.start(stage);

    }

    /**
     * Upon clicking this button, the user would be signed out and redirected to the login page.
     * @param event
     * @throws IOException
     */
    public void signOutClicked(javafx.event.ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        LoginView loginView = new LoginView();
        loginView.start(stage);
    }

    /**
     * This button sends the user back to the search view page.
     * @param event
     * @throws IOException
     */
    public void BackButtonClicked(javafx.event.ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        SearchView searchView = new SearchView();
        searchView.start(stage);
    }
}
