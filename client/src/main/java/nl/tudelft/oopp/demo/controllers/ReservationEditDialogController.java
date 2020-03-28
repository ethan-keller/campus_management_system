package nl.tudelft.oopp.demo.controllers;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import nl.tudelft.oopp.demo.entities.Reservation;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.User;
import org.controlsfx.control.RangeSlider;

/**
 * .
 * This controller class is invokes on the onclick of the newReservationButton/ editReservationButton
 * in the AdminManageReservationViewController class.
 * This controller class displays a dialog box for the admin to create/update reservations.
 */
public class ReservationEditDialogController {

    @FXML
    private ComboBox<User> username;
    @FXML
    private ComboBox<Room> room;
    @FXML
    private DatePicker date;
    @FXML
    private Text startTime;
    @FXML
    private Text endTime;
    @FXML
    private GridPane grid;

    private RangeSlider timeslot;

    public static Reservation reservation;

    public static Stage dialogStage;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * .
     * Default constructor of the ReservationEditDialogController class.
     */
    public ReservationEditDialogController() {
    }

    /**
     * .
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        try {
            final Reservation reservation = AdminManageReservationViewController.currentSelectedReservation;
            date.setConverter(getDateConverter());
            final ObservableList<User> userObservableList = User.getUserData();
            final ObservableList<Room> roomObservableList = Room.getRoomData();
            //Initializing the observable list for the users available!!
            //The admin can make a mistake in writing the name of the user.
            username.setItems(userObservableList);
            this.setUserComboBoxConverter(userObservableList);

            //This method sets up the slider which determines the time of reservation in the dialog view.
            configureRangeSlider();
            date.setDayCellFactory(getDayCellFactory());

            //Initializing the observable list for the rooms available!!
            room.setItems(roomObservableList);
            this.setRoomComboBoxConverter(roomObservableList);

            if (reservation != null) {
                username.getSelectionModel().select(
                        userObservableList.stream().filter(x -> x.getUsername().get().equals(
                        reservation.getUsername().get().toLowerCase())).collect(Collectors.toList()).get(0));
                username.setDisable(true);
                room.getSelectionModel().select(roomObservableList.stream().filter(x -> x.getRoomId().get()
                        == reservation.getRoom().get()).collect(Collectors.toList()).get(0));
                date.setValue(LocalDate.parse(reservation.getDate().get(), formatter));
                String[] startTimeSplit = reservation.getStartingTime().get().split(":");
                timeslot.setLowValue(Double.parseDouble(startTimeSplit[0]) * 60.0
                        + Double.parseDouble(startTimeSplit[1]));
                String[] endTimeSplit = reservation.getEndingTime().get().split(":");
                timeslot.setHighValue(Double.parseDouble(endTimeSplit[0]) * 60.0
                        + Double.parseDouble(endTimeSplit[1]));
                startTime.setText("Start: " + getRangeSliderConverter().toString(timeslot.getLowValue()));
                endTime.setText("End: " + getRangeSliderConverter().toString(timeslot.getHighValue()));
            } else {
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private Callback<DatePicker, DateCell> getDayCellFactory() {
        try {
            final Callback<DatePicker, DateCell> dayCellFactory = new Callback<>() {

                @Override
                public DateCell call(final DatePicker datePicker) {
                    return new DateCell() {
                        @Override
                        public void updateItem(LocalDate item, boolean empty) {
                            super.updateItem(item, empty);

                            // Disable all days before today + weekend days
                            if (item.isBefore(LocalDate.now()) || item.getDayOfWeek() == DayOfWeek.SATURDAY
                                    || item.getDayOfWeek() == DayOfWeek.SUNDAY) {
                                // disable the 'button'
                                setDisable(true);
                                // make them red
                                setStyle("-fx-background-color: #ffc0cb;");
                            }
                        }
                    };
                }
            };
            return dayCellFactory;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void configureRangeSlider() {
        try {
            // initialize the RangeSlider (values are handled as minutes) and the positions of the thumbs
            timeslot = new RangeSlider(480, 1440, 600, 1080);
            timeslot.setLowValue(600);
            timeslot.setMinWidth(100);
            timeslot.setMaxWidth(200);
            timeslot.setShowTickLabels(true);
            timeslot.setShowTickMarks(true);
            timeslot.setMajorTickUnit(120);

            // get and set the StringConverter to show hh:mm format
            StringConverter<Number> converter = getRangeSliderConverter();
            timeslot.setLabelFormatter(converter);

            // add listeners to show the current thumb values in separate Text objects
            configureRangeSliderListeners(converter);

            // initialize the Text objects with the current values of the thumbs
            startTime.setText(converter.toString(timeslot.getLowValue()));
            endTime.setText(converter.toString(timeslot.getHighValue()));

            // inject the RangeSlider in the JavaFX layout
            grid.add(timeslot, 1, 3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void configureRangeSliderListeners(StringConverter<Number> converter) {
        try {
            // listeners to adjust start and end Text objects when thumbs get moved
            timeslot.highValueProperty().addListener((observable, oldValue, newValue) ->
                    endTime.setText("End: " + converter.toString(newValue)));
            timeslot.lowValueProperty().addListener((observable, oldValue, newValue) ->
                    startTime.setText("Start: " + converter.toString(newValue)));

            // listeners that make sure the user can only select intervals of 30 minutes
            timeslot.lowValueProperty().addListener((observable, oldValue, newValue) ->
                    timeslot.setLowValue((newValue.intValue() / 30) * 30));
            timeslot.highValueProperty().addListener((observable, oldValue, newValue) ->
                    timeslot.setHighValue((newValue.intValue() / 30) * 30));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private StringConverter<Number> getRangeSliderConverter() {
        try {
            return new StringConverter<Number>() {
                @Override
                public String toString(Number n) {
                    // calculate hours and remaining minutes to get a correct hh:mm format
                    long minutes = n.longValue();
                    long hours = TimeUnit.MINUTES.toHours(minutes);
                    long remainingMinutes = minutes - TimeUnit.HOURS.toMinutes(hours);
                    // '%02d' means that there will be a 0 in front if its only 1 number + it's a long number
                    return String.format("%02d", hours) + ":" + String.format("%02d", remainingMinutes);
                }


                @Override
                public Number fromString(String string) {
                    return null;
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private StringConverter<LocalDate> getDateConverter() {
        try {
            return new StringConverter<LocalDate>() {
                @Override
                public String toString(LocalDate dateSelected) {
                    if (dateSelected != null) {
                        return formatter.format(dateSelected);
                    }
                    return "null";
                }

                @Override
                public LocalDate fromString(String string) {
                    // The date is formatted in yyyy-MM-dd format from the datePicker.
                    if (string != null && !string.trim().isEmpty()) {
                        return LocalDate.parse(string, formatter);
                    }
                    return null;
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method is to insert the various rooms present in the database into a comboBox.
     *
     * @param observableList - Observable list of Rooms.
     */
    public void setRoomComboBoxConverter(ObservableList<Room> observableList) {
        StringConverter<Room> converter = new StringConverter<Room>() {
            @Override
            public String toString(Room object) {
                if (object == null) {
                    return "";
                } else {
                    return object.getRoomName().get();
                }
            }

            @Override
            public Room fromString(String id) {
                return observableList.stream().filter(x -> String.valueOf(x.getRoomId()).equals(id)).collect(
                        Collectors.toList()).get(0);
            }
        };
        room.setConverter(converter);
    }

    /**
     * This method helps insert the User's present in the database into a comboBox.
     *
     * @param observableList - Observable list of users
     */
    public void setUserComboBoxConverter(ObservableList<User> observableList) {
        StringConverter<User> converter = new StringConverter<User>() {
            @Override
            public String toString(User object) {
                if (object == null) {
                    return "";
                } else {
                    return object.getUsername().get();
                }
            }

            @Override
            public User fromString(String id) {
                return observableList.stream().filter(x -> String.valueOf(x.getUsername()).equals(id)).collect(
                        Collectors.toList()).get(0);
            }
        };
        username.setConverter(converter);
    }

    public static void emptyReservation() {
        reservation = new Reservation();
    }

    /**
     * .
     * Called when the OK button is clicked on the dialog box.
     * This causes the information input by the user to be stored in an object.
     *
     * @param event is passed
     */
    @FXML
    public void okClicked(ActionEvent event) {
        LocalDate dateSelected = date.getValue();
        if (isInputValid()) {
            emptyReservation();
            reservation.setUsername(username.getSelectionModel().getSelectedItem().getUsername().get());
            reservation.setRoom(room.getSelectionModel().getSelectedItem().getRoomId().get());
            reservation.setDate(dateSelected.toString());
            reservation.setStartingTime(startTime.getText().replace("Start: ", ""));
            reservation.setEndingTime(endTime.getText().replace("End: ", "").equals("24:00")
                    ? "23:59" : endTime.getText().replace("End: ", ""));

            this.dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            dialogStage.close();
        }
    }

    /**
     * .
     * Called when the user clicks cancel.
     */
    @FXML
    private void cancelClicked(ActionEvent event) {
        reservation = null;
        this.dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        dialogStage.close();
    }

    /**
     * .
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (username.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "No valid username provided!\n";
        }
        if (room.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "No valid Room provided! \n";
        }
        if (date.getValue() == null) {
            errorMessage += "No date provided!\n";
        }
        if (startTime.getText().equals("")) {
            errorMessage += "No starting Time provided!\n";
        }
        if (startTime.getText().equals("")) {
            errorMessage += "No ending Time provided!\n";
        }

        // If all the fields are valid, then true is returned.
        if (errorMessage.equals("")) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);
            alert.showAndWait();

            return false;
        }
    }
}
