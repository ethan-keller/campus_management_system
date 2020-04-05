package nl.tudelft.oopp.demo.user.controller;

import com.mindfusion.common.DateTime;
import com.mindfusion.scheduling.Calendar;
import com.mindfusion.scheduling.CalendarView;
import com.mindfusion.scheduling.model.Appointment;

import java.awt.Color;
import java.awt.Point;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.SwingUtilities;

import nl.tudelft.oopp.demo.admin.controller.CalenderEditItemDialogController;
import nl.tudelft.oopp.demo.calendar.CustomCalendar;
import nl.tudelft.oopp.demo.communication.ItemServerCommunication;
import nl.tudelft.oopp.demo.entities.BikeReservation;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Food;
import nl.tudelft.oopp.demo.entities.FoodReservation;
import nl.tudelft.oopp.demo.entities.Reservation;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.user.CurrentUserManager;
import nl.tudelft.oopp.demo.views.CalenderEditItemDialogView;
import nl.tudelft.oopp.demo.views.LoginView;
import nl.tudelft.oopp.demo.views.SearchView;

/**
 * .
 * Class that controls the view which contains the calendar with booking history
 * and custom calendar items
 */
public class CalendarPaneController implements Initializable {

    public static Stage thisStage;
    public static volatile Calendar calendar;
    private static Logger logger = Logger.getLogger("GlobalLogger");
    @FXML
    private AnchorPane pane;
    @FXML
    private Button backButton;
    @FXML
    private Button signOutButton;

    /**
     * Custom initialization of JavaFX components. This method is automatically called.
     * after the fxml file has been loaded.
     *
     * @param location  is passed
     * @param resources is passed
     */
    @Override
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        try {
            backButton.getStyleClass().clear();
            signOutButton.getStyleClass().clear();
            backButton.getStyleClass().add("back-button");
            signOutButton.getStyleClass().add("signout-button");

            // SwingNode gives the ability to inject Swing components in the JavaFX environment
            SwingNode node = new SwingNode();
            configureNode(node);
            // This adds the node to the anchor pane.
            pane.getChildren().add(node);

            // The calendar gets scrolled to 08:00 instead of 00:00
            // wait until calendar is initialized and then scroll
            while (calendar == null) {
                Thread.onSpinWait();
            }
            calendar.setScrollPosition(new Point(0, 16));

            // Add all reservations and items from database to the calendar
            addReservationsToCalendar();
            addItemsToCalendar();
            addBikeReservationsToCalendar();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
        }
    }

    /**
     * Adds all the items in the database that belong to the current user to the calendar.
     */
    private void addItemsToCalendar() {
        try {
            // get all items from database that belong to the current user
            ObservableList<nl.tudelft.oopp.demo.entities.Item> itemList =
                    nl.tudelft.oopp.demo.entities.Item.getUserItems(CurrentUserManager.getUsername());
            if (itemList == null) {
                return;
            }
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
            logger.log(Level.SEVERE, e.toString());
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

            if (reservationList == null || roomList == null || buildingList == null) {
                return;
            }

            List<Food> foodList = Food.getAllFoodData();
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

                String description = room.getRoomName().get() + "\n" + building.getBuildingName().get() + "\n"
                        + startTime[0] + ":" + startTime[1] + " - " + endTime[0] + ":" + endTime[1] + "\n";
                List<FoodReservation> frList = FoodReservation.getUserReservationFood(r);
                double totalPrice = 0;
                for (FoodReservation fr : frList) {
                    Food f = foodList.stream()
                            .filter(x -> x.getFoodId().get() == fr.getFoodId().get())
                            .collect(Collectors.toList()).get(0);
                    description += fr.getFoodQuantity().get() + "x " + f.getFoodName().get() + "\n";
                    totalPrice += fr.getFoodQuantity().get() * f.getFoodPrice().get();
                }
                if (frList.size() != 0) {
                    description += "total price = " + Math.round(totalPrice * 100.0) / 100.0 + " euro(s)";
                }

                // add description with info about the reservation
                app.setDescriptionText(description);
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
            logger.log(Level.SEVERE, e.toString());
        }
    }


    /**
     * Adds all the bike reservations in the database that belong to the current user to the calendar.
     */
    private void addBikeReservationsToCalendar() {
        try {
            ObservableList<BikeReservation> reservationList =
                    BikeReservation.getUserBikeReservations(CurrentUserManager.getUsername());
            ObservableList<Building> buildingList = Building.getBuildingData();

            for (BikeReservation br : reservationList) {
                Appointment app = new Appointment();
                Building building = buildingList.stream()
                        .filter(x -> x.getBuildingId().get() == br.getBikeReservationBuilding().get())
                        .collect(Collectors.toList()).get(0);
                app.setHeaderText("Bike booking");
                app.setId(String.valueOf(br.getBikeReservationId().get()));
                String[] start = br.getBikeReservationStartingTime().get().split(":");
                String[] end = br.getBikeReservationEndingTime().get().split(":");
                String[] date = br.getBikeReservationDate().get().split("-");
                app.setStartTime(new DateTime(Integer.parseInt(date[0]), Integer.parseInt(date[1]),
                        Integer.parseInt(date[2]), Integer.parseInt(start[0]), Integer.parseInt(start[1]),
                        Integer.parseInt(start[2])));
                app.setEndTime(new DateTime(Integer.parseInt(date[0]), Integer.parseInt(date[1]),
                        Integer.parseInt(date[2]), Integer.parseInt(end[0]), Integer.parseInt(end[1]),
                        Integer.parseInt(end[2])));

                app.setDescriptionText(building.getBuildingName().get() + "\n"
                        + start[0] + ":" + start[1] + " - " + end[0] + ":" + end[1] + "\n"
                        + br.getBikeReservationQuantity().get() + " bike(s)");

                app.setLocked(true);
                app.setAllowMove(false);
                app.getStyle().setFillColor(Color.MAGENTA);

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
            CalenderEditItemDialogView iv = new CalenderEditItemDialogView();
            iv.start(thisStage);

            // if no item was created (e.g. cancel clicked) return
            if (CalenderEditItemDialogController.item == null) {
                return;
            } else {
                Appointment app = CalenderEditItemDialogController.item;
                // get date and time in correct format for database
                String date = app.getStartTime().getYear() + "-" + app.getStartTime().getMonth() + "-"
                        + app.getStartTime().getDay();
                String startTime = app.getStartTime().getHour() + ":" + app.getStartTime().getMinute() + ":00";
                String endTime = app.getEndTime().getHour() + ":" + app.getEndTime().getMinute() + ":00";
                // send info to server
                ItemServerCommunication.createItem(CurrentUserManager.getUsername(), app.getHeaderText(), date,
                        startTime, endTime, app.getDescriptionText());
                // get the id of the last inserted item to assign it to the Appointment object
                app.setId(String.valueOf(Integer.parseInt(ItemServerCommunication.getCurrentId()) - 1));
                // add the item to the calendar
                calendar.getSchedule().getItems().add(app);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
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
            logger.log(Level.SEVERE, e.toString());
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
            logger.log(Level.SEVERE, e.toString());
        }
    }

    /**
     * Loads the login view (logs the user out).
     *
     * @param event is passed
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
            logger.log(Level.SEVERE, e.toString());
        }
    }

    /**
     * This button sends the user back to the search view page.
     *
     * @param event is passed
     */
    @FXML
    private void backButtonClicked(ActionEvent event) {
        try {
            // get current stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // load and start search view
            SearchView searchView = new SearchView();
            searchView.start(stage);
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
        }
    }

}
