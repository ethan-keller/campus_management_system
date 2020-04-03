package nl.tudelft.oopp.demo.admin.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
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

import nl.tudelft.oopp.demo.admin.logic.BookingEditDialogLogic;
import nl.tudelft.oopp.demo.communication.GeneralMethods;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Reservation;
import nl.tudelft.oopp.demo.entities.Room;
import org.controlsfx.control.RangeSlider;


public class BookingEditDialogController {

    public static Reservation reservation;
    private static Logger logger = Logger.getLogger("GlobalLogger");
    private final String pathSeparator = File.separator;

    @FXML
    private GridPane grid;
    @FXML
    private ComboBox<Building> bookingBuildingComboBox;
    @FXML
    private ComboBox<Room> bookingRoomComboBox;
    @FXML
    private DatePicker bookingDate;
    @FXML
    private Text startTime;
    @FXML
    private Text endTime;

    private ObservableList<Building> olb;
    private ObservableList<Room> olr;
    // double thumb slider
    private RangeSlider timeSlotSlider;
    private Stage dialogStage;

    public BookingEditDialogController() {
    }

    /**
     * .
     * Create a new reservation when called
     */
    private static void emptyReservation() {
        reservation = new Reservation();
    }

    /**
     * .
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        try {
            // configure the range slider
            configureRangeSlider();

            // Initialize and add listener to the building combobox
            olb = Building.getBuildingData();
            bookingBuildingComboBox.setItems(olb);
            this.setBookingBuildingComboBoxConverter(olb);
            bookingBuildingComboBox.valueProperty().addListener(((observable, oldValue, newValue) -> {
                buildingSelected(newValue);
                setTimeSlotSlider();
                if (bookingRoomComboBox.getSelectionModel().getSelectedIndex() >= 0) {
                    configureCss();
                }
            }));

            // Initialize the room combobox converter
            this.setBookingRoomComboBoxConverter(olr);

            // change css of slider if date or room change
            bookingRoomComboBox.valueProperty().addListener(((observable, oldValue, newValue) -> {
                if (bookingBuildingComboBox.getSelectionModel().getSelectedIndex() >= 0) {
                    configureCss();
                }
            }));
            bookingDate.valueProperty().addListener((observable, oldValue, newValue) -> {
                if (bookingRoomComboBox.getSelectionModel().getSelectedIndex() >= 0
                        && bookingBuildingComboBox.getSelectionModel().getSelectedIndex() >= 0) {
                    configureCss();
                }
            });

            // Configure the string converters and custom properties (like disabling some dates in the datePicker)
            configureDatePicker();

        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
        }
    }

    /**
     * Create a range slider (slider with two 'thumbs') adjusted to hours and minutes.
     */
    private void configureRangeSlider() {
        try {
            timeSlotSlider = new RangeSlider();

            // get and set the StringConverter to show hh:mm format
            final StringConverter<Number> converter = getRangeSliderConverter();

            // initialize the RangeSlider (values are handled as minutes) and the positions of the thumbs
            timeSlotSlider = new RangeSlider(480, 1440, 600, 1080);
            timeSlotSlider.setLowValue(600);
            timeSlotSlider.setMinWidth(100);
            timeSlotSlider.setMaxWidth(200);
            timeSlotSlider.setShowTickLabels(true);
            timeSlotSlider.setShowTickMarks(true);
            timeSlotSlider.setMajorTickUnit(120);

            timeSlotSlider.setLabelFormatter(converter);

            // add listeners to show the current thumb values in separate Text objects
            configureRangeSliderListeners(converter);

            // initialize the Text objects with the current values of the thumbs
            startTime.setText("Start: " + converter.toString(timeSlotSlider.getLowValue()));
            endTime.setText("End: " + converter.toString(timeSlotSlider.getHighValue()));

            // inject the RangeSlider in the JavaFX layout
            grid.add(timeSlotSlider, 1, 3);
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
        }
    }

    private void setTimeSlotSlider() {
        try {
            Building selectedBuilding = bookingBuildingComboBox.getSelectionModel().getSelectedItem();
            StringConverter<Number> converter = getRangeSliderConverter();

            double opening;
            double closing;

            if (selectedBuilding != null) {
                opening = (double) converter.fromString(selectedBuilding.getOpeningTime().get());
                closing = (double) converter.fromString(selectedBuilding.getClosingTime().get());
                if (closing == 1439) {
                    timeSlotSlider.setMax(1440);
                    timeSlotSlider.setMajorTickUnit((1440 - opening) / 3);
                } else {
                    timeSlotSlider.setMax(closing);
                    timeSlotSlider.setMajorTickUnit((closing - opening) / 3);
                }
                timeSlotSlider.setMin(opening);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * .
     * Configure (in CSS) the colors of the track of the range slider to show in green
     * the available timeslots and in red the rest.
     */
    private void configureCss() {
        try {
            // get currently selected room
            Room selectedRoom = bookingRoomComboBox.getSelectionModel().getSelectedItem();
            // get css file and delete its content to fill it again
            File css = new File(getClass().getResource("/RangeSlider.css")
                    .getPath().replace("/", pathSeparator));
            css.delete();
            css.createNewFile();
            BufferedWriter bw = new BufferedWriter(new FileWriter(css));
            if (selectedRoom == null) {
                GeneralMethods.setSliderDefaultCss(timeSlotSlider, bw,
                        getClass().getResource("/RangeSlider.css").toExternalForm());
                return;
            }
            // get reservations for this room on the selected date
            List<Reservation> reservations = Reservation.getRoomReservationsOnDate(selectedRoom.getRoomId().get(),
                    bookingDate.getValue(), BookingEditDialogLogic.getDatePickerConverter(bookingDate));

            // sort them in ascending order
            BookingEditDialogLogic.sortReservations(reservations);

            // first part of css
            bw.write(".track {\n" + "\t-fx-background-color: linear-gradient(to right, ");

            // iterator to loop over all the reservations
            Iterator<Reservation> it = reservations.iterator();

            // if there are no reservations make the track completely green
            if (!it.hasNext()) {
                bw.write("#91ef99 0%, #91ef99 100%);\n");
            }

            Building selectedBuilding = Building.getBuildingById(selectedRoom.getRoomBuilding().getValue());
            StringConverter<Number> converter = getRangeSliderConverter();
            double opening = (double) converter.fromString(selectedBuilding.getOpeningTime().get());
            double closing = (double) converter.fromString(selectedBuilding.getClosingTime().get());

            Reservation res = AdminManageReservationViewController.currentSelectedReservation;

            // calculate and add green and red parts
            while (it.hasNext()) {
                Reservation r = it.next();
                double startTime = (double) converter.fromString(r.getStartingTime().get());
                double endTime = (double) converter.fromString(r.getEndingTime().get());
                double startPercentage = ((startTime - opening) / (closing - opening)) * 100.0;
                double endPercentage = ((endTime - opening) / (closing - opening)) * 100.0;

                bw.write("#91ef99 " + startPercentage + "%, ");
                bw.write("#ffc0cb " + startPercentage + "%, ");
                bw.write("#ffc0cb " + endPercentage + "%, ");
                bw.write("#91ef99 " + endPercentage + "%");

                // if reservation is the one that is being edited, give it a light blue color
                if (res != null && res.getId().get() == r.getId().get()) {
                    bw.write("#91ef99 " + startPercentage + "%, ");
                    bw.write("#70e5fa " + startPercentage + "%, ");
                    bw.write("#70e5fa " + endPercentage + "%, ");
                    bw.write("#91ef99 " + endPercentage + "%");
                    if (!it.hasNext()) {
                        bw.write(");\n");
                    } else {
                        bw.write(", ");
                    }
                    continue;
                }

                if (!it.hasNext()) {
                    bw.write(");\n");
                } else {
                    bw.write(", ");
                }
            }

            // last part of css (more configuration)
            bw.write("\t-fx-background-insets: 0 0 -1 0, 0, 1;\n"
                    + "\t-fx-background-radius: 0.25em, 0.25em, 0.166667em; /* 3 3 2 */\n"
                    + "\t-fx-padding: 0.25em; /* 3 */\n"
                    + "}\n\n" + ".range-bar {\n"
                    + "\t-fx-background-color: rgba(0,0,0,0.3);"
                    + "\n}");
            // flush and close writer
            bw.flush();
            bw.close();
            // remove current stylesheet
            timeSlotSlider.getStylesheets().remove(getClass().getResource("/RangeSlider.css")
                    .toExternalForm());
            // add new stylesheet
            timeSlotSlider.getStylesheets().add(getClass().getResource("/RangeSlider.css")
                    .toExternalForm());
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
        }
    }

    /**
     * .
     * Configure the rangeSlider listeners. The listeners make sure that the user jumps
     * intervals of an hour and sets the texts with the correct value.
     *
     * @param converter String converter that is created in
     */
    private void configureRangeSliderListeners(StringConverter<Number> converter) {
        try {
            // listeners to adjust start and end Text objects when thumbs get moved
            timeSlotSlider.highValueProperty().addListener((observable, oldValue, newValue) -> {
                endTime.setText("End: " + converter.toString(newValue));
            });
            timeSlotSlider.lowValueProperty().addListener((observable, oldValue, newValue) -> {
                startTime.setText("Start: " + converter.toString(newValue));
            });

            // listeners that make sure the user can only select intervals of 30 minutes
            timeSlotSlider.lowValueProperty().addListener((observable, oldValue, newValue) ->
                    timeSlotSlider.setLowValue((newValue.intValue() / 30) * 30));
            timeSlotSlider.highValueProperty().addListener((observable, oldValue, newValue) ->
                    timeSlotSlider.setHighValue((newValue.intValue() / 30) * 30));
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
        }
    }

    /**
<<<<<<< HEAD
=======
     * Creates a StringConverter that converts the selected value to an actual time (in String format).
     *
     * @return a StringConverter object
     */
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
                public Number fromString(String time) {
                    if (time != null) {
                        String[] split = time.split(":");
                        return Double.parseDouble(split[0]) * 60 + Double.parseDouble(split[1]);
                    }
                    return null;
                }
            };
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
        }
        return null;
    }

    /**
>>>>>>> develop
     * Set the building combobox converter.
     *
     * @param olb is passed
     */
    public void setBookingBuildingComboBoxConverter(ObservableList<Building> olb) {
        StringConverter<Building> converter = new StringConverter<Building>() {
            @Override
            public String toString(Building object) {
                if (object == null) {
                    return "";
                } else {
                    return object.getBuildingName().get();
                }
            }

            @Override
            public Building fromString(String id) {
                return olb.stream().filter(x -> String.valueOf(x.getBuildingId()) == id).collect(
                        Collectors.toList()).get(0);
            }
        };
        bookingBuildingComboBox.setConverter(converter);
    }

    /**
     * Set the room combobox converter.
     *
     * @param olr is passed
     */
    public void setBookingRoomComboBoxConverter(ObservableList<Room> olr) {
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
                return olr.stream().filter(x -> String.valueOf(x.getRoomId()) == id).collect(
                        Collectors.toList()).get(0);
            }
        };
        bookingRoomComboBox.setConverter(converter);
    }

    /**
     * .
     * Called when a building is selected
     * The room combobox only shows the rooms of the selected building
     */
    public void buildingSelected(Building newBuilding) {
        try {
            if (bookingBuildingComboBox.getValue() != null) {
                //Get all the rooms
                olr = Room.getRoomData();
                //Create a list of rooms only belongs to the selected building
                List<Room> filteredRooms = olr.stream().filter(x -> x.getRoomBuilding().get()
                        == newBuilding.getBuildingId().get()).collect(Collectors.toList());
                olr.clear();
                //Add the filtered rooms to the observable list
                for (Room r : filteredRooms) {
                    olr.add(r);
                }
                bookingRoomComboBox.setItems(olr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * .
     * Methods that sets the dayCellFactory made in {@link #getDayCellFactory()}
     * and the StringConverter made in
     */
    private void configureDatePicker() {
        try {
            // factory to create cell of DatePicker
            Callback<DatePicker, DateCell> dayCellFactory = this.getDayCellFactory();
            // set the factory
            bookingDate.setDayCellFactory(dayCellFactory);
            // converter to convert value to String and vice versa
            StringConverter<LocalDate> converter = BookingEditDialogLogic.getDatePickerConverter(bookingDate);
            // set the converter
            bookingDate.setConverter(converter);
            // reset css when date changes
            bookingDate.valueProperty().addListener(((observable, oldValue, newValue) -> {
                try {
                    configureCss();
                } catch (Exception e) {
                    logger.log(Level.SEVERE, e.toString());
                }
            }));
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
        }
    }

    /**
     * Create cellFactory for the datePicker that disables all days before today and weekend days.
     * It also marks them red to make sure the user understands why they are disabled.
     *
     * @return a CallBack object used to set the dayCellFactory for the datePicker in
     * {@link #configureDatePicker()}
     */
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
            logger.log(Level.SEVERE, e.toString());
        }
        return null;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOkClicked(ActionEvent event) {
        // Check the validity of user input
        if (BookingEditDialogLogic.isInputValid(bookingRoomComboBox, bookingDate, timeSlotSlider, reservation)) {
            emptyReservation();
            // Set the user input to the reservation
            reservation.setUsername(AdminManageUserViewController.currentSelectedUser.getUsername().get());
            reservation.setRoom(this.bookingRoomComboBox.getSelectionModel().getSelectedItem().getRoomId().get());
            reservation.setDate(this.bookingDate.getValue().toString());
            reservation.setStartingTime(startTime.getText().replace("Start: ", ""));
            reservation.setEndingTime(endTime.getText().replace("End: ", ""));
            // Close the dialog window
            this.dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancelClicked(ActionEvent event) {
        reservation = null;
        // Close the dialog window
        this.dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        dialogStage.close();
    }

    /**
     * Validates the user input.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (bookingBuildingComboBox.getSelectionModel().getSelectedIndex() < 0) {
            errorMessage += "No valid building selected!\n";
        }
        if (bookingRoomComboBox.getSelectionModel().getSelectedIndex() < 0) {
            errorMessage += "No valid room selected!\n";
        }
        if (bookingDate.getValue() == null) {
            errorMessage += "No valid date selected!\n";
        }
        if (!checkTimeSlotValidity() || timeSlotSlider.getLowValue() == timeSlotSlider.getHighValue()) {
            errorMessage += "No valid timeslot selected!\n";
        }
        if (errorMessage.equals("")) {
            return true;
        } else {
            // Show the error message.
            GeneralMethods.alertBox("Invalid Fields", "Please correct invalid fields",
                    errorMessage, Alert.AlertType.ERROR);

            return false;
        }
    }

    /**
     * Method that checks if the chosen timeslot is free.
     *
     * @return true if the timeslot is free, false otherwise
     */
    private boolean checkTimeSlotValidity() {
        // get currently selected room
        Room selectedRoom = bookingRoomComboBox.getSelectionModel().getSelectedItem();
        if (selectedRoom == null) {
            return false;
        }
        // get all reservations for the current room on the chosen date
        List<Reservation> roomReservations = Reservation.getRoomReservationsOnDate(selectedRoom.getRoomId().get(),
                bookingDate.getValue(), getDatePickerConverter());

        // get converter to convert date value to String format hh:mm
        StringConverter<Number> timeConverter = getRangeSliderConverter();

        // if there are no reservations the timeslot is valid
        if (roomReservations.size() == 0) {
            return true;
        }

        Reservation res = AdminUserHistoryViewController.currentSelectedReservation;

        for (Reservation r : roomReservations) {
            if (res != null) {
                // if reservation equals the one we are editing, don't consider it
                if (r.getId().get() == res.getId().get()) {
                    continue;
                }
            }

            // get rangeslider values + reservation values
            double currentStartValue = timeSlotSlider.getLowValue();
            double currentEndValue = timeSlotSlider.getHighValue();
            double startValue = (double) timeConverter.fromString(r.getStartingTime().get());
            double endValue = (double) timeConverter.fromString(r.getEndingTime().get());

            // check if the values overlap
            if (!((currentStartValue <= startValue && currentEndValue <= startValue)
                    || (currentStartValue >= endValue && currentEndValue >= endValue))) {
                return false;
            }

        }
        return true;
    }

    /**
     * Creates a StringConverter that converts the selected value to a usable Date (in String format).
     *
     * @return a StringConverter object
     */
    private StringConverter<LocalDate> getDatePickerConverter() {
        try {
            return new StringConverter<LocalDate>() {
                // set the wanted pattern (format)
                String pattern = "yyyy-MM-dd";
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

                {
                    // set placeholder text for the datePicker
                    bookingDate.setPromptText(pattern.toLowerCase());
                }

                @Override
                public String toString(LocalDate date) {
                    if (date != null) {
                        // get correctly formatted String
                        return dateFormatter.format(date);
                    } else {
                        return "";
                    }
                }

                @Override
                public LocalDate fromString(String string) {
                    if (string != null && !string.isEmpty()) {
                        // get correct LocalDate from String format
                        return LocalDate.parse(string, dateFormatter);
                    } else {
                        return null;
                    }
                }
            };
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
        }
        return null;
    }

}