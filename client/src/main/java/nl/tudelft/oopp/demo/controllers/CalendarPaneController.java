package nl.tudelft.oopp.demo.controllers;

import com.mindfusion.scheduling.Calendar;
import com.mindfusion.scheduling.CalendarView;
import com.mindfusion.scheduling.model.Item;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.calendar.CustomCalendar;
import nl.tudelft.oopp.demo.communication.GeneralMethods;
import nl.tudelft.oopp.demo.views.CalenderAppointmentDialogView;
import nl.tudelft.oopp.demo.views.LoginView;
import nl.tudelft.oopp.demo.views.SearchView;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CalendarPaneController implements Initializable {

    @FXML
    private AnchorPane pane;
    private Calendar calendar;
    public static Stage thisStage;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SwingNode node = new SwingNode();
        configureNode(node);
        // This adds the node created which is basically the calender into the anchor pane.
        pane.getChildren().add(node);
        CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS).execute(() -> {
            calendar.setScrollPosition(new Point(0, 16));
        });
        addReservationsToCalendar();
    }

    private void addReservationsToCalendar() {
        
    }

    private void configureNode(SwingNode node) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Calendar c = new CustomCalendar();
                c.setSize((int) pane.getWidth(), (int) pane.getHeight());
                AnchorPane.setLeftAnchor(node, 0d);
                AnchorPane.setTopAnchor(node, 0d);
                AnchorPane.setBottomAnchor(node, 0d);
                AnchorPane.setRightAnchor(node, 0d);
                calendar = c;
                node.setContent(c);
            }
        });
    }

    /**
     * This button opens a dialog box to get the details of an appointment like date, time and description and adds it to
     * the calender.
     *
     * @param event
     */
    public void addAppointmentClicked(ActionEvent event) {
        thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        CalenderAppointmentDialogView calenderAppointmentDialogView = new CalenderAppointmentDialogView();
        calenderAppointmentDialogView.start(thisStage);
        if(CalenderAppointmentDialogController.appointment == null){
            GeneralMethods.createAlert("Error", "Sorry, something has gone wrong. We are on it now!", thisStage, Alert.AlertType.ERROR);
        } else {
            this.calendar.getSchedule().getItems().add(CalenderAppointmentDialogController.appointment);
        }
    }

    //To be implemented.
    public void cancelAppointmentClicked() {
        Item[] items = calendar.getItemSelection().getItems();
        for(Item x: items){
            calendar.getSchedule().getItems().remove(x);
        }
        GeneralMethods.createAlert("Cancel confirmation", "Your item has succesfully been deleted from your schedule", thisStage, Alert.AlertType.CONFIRMATION);
    }

    public void cancelReservationClicked() {
    }

    public void weeklyViewClicked() {
        calendar.setCurrentView(CalendarView.Timetable);
    }

    public void monthlyViewClicked() {
        calendar.setCurrentView(CalendarView.SingleMonth);
    }

    /**
     * For a user to book a room, the user needs to go to search view and select the room to book.
     *
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
     *
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
     *
     * @param event
     * @throws IOException
     */
    public void BackButtonClicked(javafx.event.ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        SearchView searchView = new SearchView();
        searchView.start(stage);
    }
}
