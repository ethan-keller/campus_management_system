package nl.tudelft.oopp.demo.controllers;

import com.mindfusion.common.DateTime;
import com.mindfusion.scheduling.Calendar;
import com.mindfusion.scheduling.CalendarView;
import com.mindfusion.scheduling.model.Appointment;
import com.mindfusion.scheduling.model.Item;
import com.mindfusion.scheduling.model.Style;
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
import nl.tudelft.oopp.demo.communication.ItemServerCommunication;
import nl.tudelft.oopp.demo.communication.user.CurrentUserManager;
import nl.tudelft.oopp.demo.entities.Reservation;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.views.CalenderAppointmentDialogView;
import nl.tudelft.oopp.demo.views.LoginView;
import nl.tudelft.oopp.demo.views.SearchView;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class CalendarPaneController implements Initializable {

    @FXML
    private AnchorPane pane;
    private Calendar calendar;
    public static Stage thisStage;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            SwingNode node = new SwingNode();
            configureNode(node);
            // This adds the node created which is basically the calender into the anchor pane.
            pane.getChildren().add(node);
            CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS).execute(() -> {
                calendar.setScrollPosition(new Point(0, 16));
            });
            addReservationsToCalendar();
            addItemsToCalendar();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void addItemsToCalendar() {
        List<nl.tudelft.oopp.demo.entities.Item> allItems = nl.tudelft.oopp.demo.entities.Item.getAllItems();
        List<nl.tudelft.oopp.demo.entities.Item> userSpecificItems = allItems.stream().filter(x -> x.getUser().get().equals(CurrentUserManager.getUsername())).collect(Collectors.toList());
        for(nl.tudelft.oopp.demo.entities.Item i: userSpecificItems){
            Appointment app = new Appointment();
            app.setHeaderText(i.getTitle().get());
            String[] date = i.getDate().get().split("-");
            String[] startTime = i.getStarting_time().get().split(":");
            String[] endTime = i.getEnding_time().get().split(":");
            app.setStartTime(new DateTime(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]), Integer.parseInt(startTime[0]), Integer.parseInt(startTime[1]), Integer.parseInt(startTime[2])));
            app.setEndTime(new DateTime(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]), Integer.parseInt(endTime[0]), Integer.parseInt(endTime[1]), Integer.parseInt(endTime[2])));
            app.setDescriptionText(i.getDescription().get());
            app.setLocked(true);
            app.setAllowMove(false);
            Style color = new Style();
            color.setFillColor(Color.ORANGE);
            app.setStyle(color);
            calendar.getSchedule().getItems().add(app);
        }
    }

    private void addReservationsToCalendar() throws UnsupportedEncodingException {
        List<Reservation> allReservations = Reservation.getUserReservation().stream().collect(Collectors.toList());
        List<Room> allRooms = Room.getRoomData().stream().collect(Collectors.toList());
        for (Reservation r: allReservations){
            Appointment app = new Appointment();
            String roomName = allRooms.stream().filter(x -> x.getRoomId().get() == r.getRoom().get()).collect(Collectors.toList()).get(0).getRoomName().get();
            app.setHeaderText("Reservation");
            String[] date = r.getDate().get().split("-");
            String[] startTime = r.getStarting_time().get().split(":");
            String[] endTime = r.getEnding_time().get().split(":");

            app.setDescriptionText(roomName + "\n" + startTime[0] + ":" + startTime[1] + " - " + endTime[0] + ":" + endTime[1]);
            app.setLocked(true);
            app.setAllowMove(false);
            Style color = new Style();
            color.setFillColor(Color.CYAN);
            app.setStyle(color);
            app.setStartTime(new DateTime(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]), Integer.parseInt(startTime[0]), Integer.parseInt(startTime[1]), Integer.parseInt(startTime[2])));
            app.setEndTime(new DateTime(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]), Integer.parseInt(endTime[0]), Integer.parseInt(endTime[1]), Integer.parseInt(endTime[2])));
            calendar.getSchedule().getItems().add(app);
        }
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
        try {
            thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            CalenderAppointmentDialogView calenderAppointmentDialogView = new CalenderAppointmentDialogView();
            calenderAppointmentDialogView.start(thisStage);
            if (CalenderAppointmentDialogController.appointment == null) {
                GeneralMethods.createAlert("Error", "Sorry, something has gone wrong. We are on it now!", thisStage, Alert.AlertType.ERROR);
            } else {
                Appointment app = CalenderAppointmentDialogController.appointment;
                String date = app.getStartTime().getYear()+"-"+app.getStartTime().getMonth()+"-"+app.getStartTime().getDay();
                String startTime = app.getStartTime().getHour()+":"+app.getStartTime().getMinute()+":00";
                String endTime= app.getEndTime().getHour()+":"+app.getEndTime().getMinute()+":00";
                System.out.println(startTime);
                System.out.println(date);
                this.calendar.getSchedule().getItems().add(app);
                ItemServerCommunication.createItem(CurrentUserManager.getUsername(), app.getHeaderText(), date, startTime, endTime, app.getDescriptionText());
            }
        } catch (Exception e){
            e.printStackTrace();
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
