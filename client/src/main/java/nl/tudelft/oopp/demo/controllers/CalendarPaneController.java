package nl.tudelft.oopp.demo.controllers;

import com.mindfusion.common.DateTime;
import com.mindfusion.scheduling.Calendar;
import com.mindfusion.scheduling.CalendarView;
import com.mindfusion.scheduling.model.Appointment;
import com.mindfusion.scheduling.model.Item;
import javafx.collections.ObservableList;
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
import nl.tudelft.oopp.demo.communication.ReservationServerCommunication;
import nl.tudelft.oopp.demo.communication.user.CurrentUserManager;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Reservation;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.views.CalenderItemDialogView;
import nl.tudelft.oopp.demo.views.LoginView;
import nl.tudelft.oopp.demo.views.SearchView;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Class that controls the view which contains the calendar with booking history and custom calendar items.
 */
public class CalendarPaneController implements Initializable {

    @FXML
    private AnchorPane pane;
    private Calendar calendar;
    public static Stage thisStage;

    /**
     * Custom initialization of JavaFX components. This method is automatically called
     * after the fxml file has been loaded.
     *
     * @param location
     * @param resources
     */
    @Override
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        try {
            // SwingNode gives the ability to inject Swing components in the JavaFX environment
            SwingNode node = new SwingNode();
            configureNode(node);
            // This adds the node to the anchor pane.
            pane.getChildren().add(node);

            // The calendar gets scrolled to 08:00 instead of 00:00
            // Delay is needed otherwise NPE
            CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS).execute(() -> {
                calendar.setScrollPosition(new Point(0, 16));
            });
            // Add all reservations and items from database to the calendar
            addReservationsToCalendar();
            addItemsToCalendar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds all the items in the database that belong to the current user to the calendar.
     */
    private void addItemsToCalendar() {
        try {
            // get all items from database that belong to the current user
            ObservableList<nl.tudelft.oopp.demo.entities.Item> itemList = nl.tudelft.oopp.demo.entities.Item
                    .getUserItems(CurrentUserManager.getUsername());
            if (itemList == null) return;
            // make an Appointment object for every item to inject in calendar
            for (nl.tudelft.oopp.demo.entities.Item i : itemList) {
                Appointment app = new Appointment();
                app.setHeaderText(i.getTitle().get());
                // split date in [yyyy, MM, dd]
                String[] date = i.getDate().get().split("-");
                // split time in [hh, mm, ss]
                String[] startTime = i.getStartingTime().get().split(":");
                String[] endTime = i.getEndingTime().get().split(":");
                app.setStartTime(new DateTime(Integer.parseInt(date[0]), Integer.parseInt(date[1]),
                        Integer.parseInt(date[2]), Integer.parseInt(startTime[0]), Integer.parseInt(startTime[1]),
                        Integer.parseInt(startTime[2])));
                app.setEndTime(new DateTime(Integer.parseInt(date[0]), Integer.parseInt(date[1]),
                        Integer.parseInt(date[2]), Integer.parseInt(endTime[0]), Integer.parseInt(endTime[1]),
                        Integer.parseInt(endTime[2])));
                app.setDescriptionText(i.getDescription().get());
                // make sure user cannot move items around in calendar
                app.setLocked(true);
                app.setAllowMove(false);
                // give the Appointment object the id that is given in the database (for later reference)
                app.setId(String.valueOf(i.getId().get()));
                // give items an orange side bar color
                app.getStyle().setFillColor(Color.ORANGE);
                // add the item to the calendar
                calendar.getSchedule().getItems().add(app);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds all the reservations in the database that belong to the current user to the calendar.
     */
    private void addReservationsToCalendar() {
        try {
            // get all the reservations from the current user + all the rooms and buildings
            ObservableList<Reservation> reservationList = Reservation.getUserReservation();
            ObservableList<Room> roomList = Room.getRoomData();
            ObservableList<Building> buildingList = Building.getBuildingData();
            if (reservationList == null || roomList == null || buildingList == null) return;

            // make an Appointment object for every reservation to inject in calendar
            for (Reservation r : reservationList) {
                Appointment app = new Appointment();
                // get the room that is booked
                Room room = roomList.stream()
                        .filter(x -> x.getRoomId().get() == r.getRoom().get())
                        .collect(Collectors.toList()).get(0);
                // get building that contains the room that is booked
                Building building = buildingList.stream()
                        .filter(x -> x.getBuildingId().get() == room.getRoomBuilding().get())
                        .collect(Collectors.toList()).get(0);
                app.setHeaderText("Reservation");
                // split date in [yyyy, MM, dd]
                String[] date = r.getDate().get().split("-");
                // split time in [hh, mm, ss]
                String[] startTime = r.getStartingTime().get().split(":");
                String[] endTime = r.getEndingTime().get().split(":");
                app.setStartTime(new DateTime(Integer.parseInt(date[0]), Integer.parseInt(date[1]),
                        Integer.parseInt(date[2]), Integer.parseInt(startTime[0]), Integer.parseInt(startTime[1]),
                        Integer.parseInt(startTime[2])));
                app.setEndTime(new DateTime(Integer.parseInt(date[0]), Integer.parseInt(date[1]),
                        Integer.parseInt(date[2]), Integer.parseInt(endTime[0]), Integer.parseInt(endTime[1]),
                        Integer.parseInt(endTime[2])));
                // add description with info about the reservation
                app.setDescriptionText(room.getRoomName().get() + "\n" +
                        building.getBuildingName().get() + "\n" + startTime[0] + ":" +
                        startTime[1] + " - " + endTime[0] + ":" + endTime[1]);
                // make sure the user cannot move around the reservations in the calendar
                app.setLocked(true);
                app.setAllowMove(false);
                // give the Appointment object the id that is given in the database (for later reference)
                app.setId(String.valueOf(r.getId().get()));
                // give reservations an cyan side bar color
                app.getStyle().setFillColor(Color.CYAN);
                // add the reservation to the calendar
                calendar.getSchedule().getItems().add(app);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes the SwingNode with Swing components. This needs to happen in a separate thread.
     *
     * @param node the node that needs to be configured
     */
    private void configureNode(SwingNode node) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Calendar c = new CustomCalendar();
                // set size of custom calendar
                c.setSize((int) pane.getWidth(), (int) pane.getHeight());
                // set anchor constraints on node
                AnchorPane.setLeftAnchor(node, 0d);
                AnchorPane.setTopAnchor(node, 0d);
                AnchorPane.setBottomAnchor(node, 0d);
                AnchorPane.setRightAnchor(node, 0d);
                // assign configured custom calendar to class attribute for later use
                calendar = c;
                // set the content in the node
                node.setContent(c);
            }
        });
    }

    /**
     * This button opens a dialog box to get the details of an item like date, time and description and adds it to
     * the calender.
     *
     * @param event which is used to get the current stage
     */
    @FXML
    private void addItemClicked(ActionEvent event) {
        try {
            // get current stage
            thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // load and start the dialog box
            CalenderItemDialogView iv = new CalenderItemDialogView();
            iv.start(thisStage);

            // if no item was created (e.g. cancel clicked) return
            if (CalenderItemDialogController.item == null) {
                return;
            } else {
                Appointment app = CalenderItemDialogController.item;
                // get date and time in correct format for database
                String date = app.getStartTime().getYear() + "-" + app.getStartTime().getMonth() +
                        "-" + app.getStartTime().getDay();
                String startTime = app.getStartTime().getHour() + ":" + app.getStartTime().getMinute() + ":00";
                String endTime = app.getEndTime().getHour() + ":" + app.getEndTime().getMinute() + ":00";
                // send info to server
                ItemServerCommunication.createItem(CurrentUserManager.getUsername(), app.getHeaderText(),
                        date, startTime, endTime, app.getDescriptionText());
                // get the id of the last inserted item to assign it to the Appointment object
                app.setId(String.valueOf(Integer.parseInt(ItemServerCommunication.getCurrentId()) - 1));
                // add the item to the calendar
                calendar.getSchedule().getItems().add(app);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that deletes an item from the calendar and database.
     */
    @FXML
    private void cancelItemClicked() {
        try {
            // get all the selected items in the calendar
            Item[] items = calendar.getItemSelection().getItems();
            if (items.length == 0) {
                Alert alert = GeneralMethods.createAlert("No selection",
                        "You didn't select any item to delete", thisStage, Alert.AlertType.WARNING);
                alert.showAndWait();
            }
            for (Item x : items) {
                // if color is other than orange it is not an item
                if (x.getStyle().getFillColor().equals(Color.ORANGE)) {
                    // server method to delete item in database
                    if (ItemServerCommunication.deleteItem(Integer.parseInt(x.getId()))) {
                        // delete from calendar and confirm deletion
                        calendar.getSchedule().getItems().remove(x);
                        Alert alert = GeneralMethods.createAlert("Deletion confirmation",
                                "Your item has successfully been deleted from your schedule",
                                thisStage, Alert.AlertType.INFORMATION);
                        alert.showAndWait();
                    } else {
                        // alert the user that something went wrong
                        Alert alert = GeneralMethods.createAlert("Deletion error",
                                "Something went wrong, your item has not been deleted. Please try again",
                                thisStage, Alert.AlertType.ERROR);
                        alert.showAndWait();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that cancels a reservation and deletes it from the database.
     */
    @FXML
    private void cancelReservationClicked() {
        try {
            // get all selected items from the calendar
            Item[] items = calendar.getItemSelection().getItems();
            if (items.length == 0) {
                Alert alert = GeneralMethods.createAlert("No selection",
                        "You didn't select any reservation to delete", thisStage, Alert.AlertType.WARNING);
                alert.showAndWait();
            }
            for (Item x : items) {
                // if color is other than cyan it's not a reservation
                if (x.getStyle().getFillColor().equals(Color.CYAN)) {
                    if (ReservationServerCommunication.deleteReservation(Integer.parseInt(x.getId()))) {
                        // delete reservation from database and calendar
                        calendar.getSchedule().getItems().remove(x);
                        Alert alert = GeneralMethods.createAlert("Cancel confirmation",
                                "Your reservation has succesfully been canceled", thisStage,
                                Alert.AlertType.INFORMATION);
                        alert.showAndWait();
                    } else {
                        // alert user that there was an error
                        Alert alert = GeneralMethods.createAlert("Cancel error",
                                "Something went wrong, your reservation has not been canceled." +
                                        " Please try again.",
                                thisStage, Alert.AlertType.ERROR);
                        alert.showAndWait();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Switches the calendar to a 7-day week view.
     */
    @FXML
    private void weeklyViewClicked() {
        try {
            calendar.setCurrentView(CalendarView.Timetable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Switches the calendar to a month view.
     */
    @FXML
    private void monthlyViewClicked() {
        try {
            calendar.setCurrentView(CalendarView.SingleMonth);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the login view (logs the user out).
     *
     * @param event
     */
    @FXML
    private void signOutClicked(javafx.event.ActionEvent event) {
        try {
            // get current stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // load and start login view
            LoginView loginView = new LoginView();
            loginView.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This button sends the user back to the search view page.
     *
     * @param event
     */
    @FXML
    private void BackButtonClicked(javafx.event.ActionEvent event) {
        try {
            // get current stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // load and start search view
            SearchView searchView = new SearchView();
            searchView.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
