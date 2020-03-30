package nl.tudelft.oopp.demo.controllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import nl.tudelft.oopp.demo.communication.GeneralMethods;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Reservation;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.logic.BookingEditDialogLogic;
import org.controlsfx.control.RangeSlider;


public class BookingEditDialogController {

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

    public static Reservation reservation;

    // double thumb slider
    private RangeSlider timeSlotSlider;

    private Stage dialogStage;

    public BookingEditDialogController() {
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

            // add stylesheet to rangeslider
            timeSlotSlider.getStylesheets().add(getClass().getResource("/RangeSlider.css").toExternalForm());

            // Initialize and add listener to the building combobox
            olb = Building.getBuildingData();
            bookingBuildingComboBox.setItems(olb);
            this.setBookingBuildingComboBoxConverter(olb);
            bookingBuildingComboBox.valueProperty().addListener(((observable, oldValue, newValue) -> {
                buildingSelected(newValue);
            }));

            // Initialize the room combobox
            olr = Room.getRoomData();
            bookingRoomComboBox.setItems(olr);
            this.setBookingRoomComboBoxConverter(olr);

            // change css of slider if date or room change
            bookingRoomComboBox.valueProperty().addListener(((observable, oldValue, newValue) -> {
                configureCss();
            }));
            bookingDate.valueProperty().addListener((observable, oldValue, newValue) -> {
                configureCss();
            });

            // Configure the string converters and custom properties (like disabling some dates in the datePicker)
            configureDatePicker();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Create a range slider (slider with two 'thumbs') adjusted to hours and minutes.
     */
    private void configureRangeSlider() {
        try {
            // initialize the RangeSlider (values are handled as minutes) and the positions of the thumbs
            timeSlotSlider = new RangeSlider(480, 1440, 600, 1080);
            timeSlotSlider.setLowValue(600);
            timeSlotSlider.setMinWidth(100);
            timeSlotSlider.setMaxWidth(200);
            timeSlotSlider.setShowTickLabels(true);
            timeSlotSlider.setShowTickMarks(true);
            timeSlotSlider.setMajorTickUnit(120);
            timeSlotSlider.setMinorTickCount(4);

            // get and set the StringConverter to show hh:mm format
            StringConverter<Number> converter = BookingEditDialogLogic.getRangeSliderConverter();
            timeSlotSlider.setLabelFormatter(converter);

            // add listeners to show the current thumb values in separate Text objects
            configureRangeSliderListeners(converter);

            // configure css of rangeslider to show user what timeslots are free
            configureCss();

            // initialize the Text objects with the current values of the thumbs
            startTime.setText("Start: " + converter.toString(timeSlotSlider.getLowValue()));
            endTime.setText("End: " + converter.toString(timeSlotSlider.getHighValue()));

            // inject the RangeSlider in the JavaFX layout
            grid.add(timeSlotSlider, 1, 3);
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
            File css = new File(getClass().getResource("/RangeSlider.css").getPath());
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

            // calculate and add green and red parts
            while (it.hasNext()) {
                Reservation r = it.next();
                String[] startTime = r.getStartingTime().get().split(":");
                String[] endTime = r.getEndingTime().get().split(":");
                double startPercentage = ((Double.parseDouble(startTime[0]) - 8.0) * 60.0
                        + Double.parseDouble(startTime[1])) / 9.60;
                double endPercentage = ((Double.parseDouble(endTime[0]) - 8.0) * 60.0
                        + Double.parseDouble(endTime[1])) / 9.60;
                bw.write("#91ef99 " + startPercentage + "%, ");
                bw.write("#ffc0cb " + startPercentage + "%, ");
                bw.write("#ffc0cb " + endPercentage + "%, ");
                bw.write("#91ef99 " + endPercentage + "%");
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }

    /**
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
                    e.printStackTrace();
                }
            }));
        } catch (Exception e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return null;
    }

    /**
     * .
     * Create a new reservation when called
     */
    private static void emptyReservation() {
        reservation = new Reservation();
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
}
