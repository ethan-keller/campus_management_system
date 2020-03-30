package nl.tudelft.oopp.demo.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DateCell;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import nl.tudelft.oopp.demo.communication.GeneralMethods;
import nl.tudelft.oopp.demo.communication.user.CurrentUserManager;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Reservation;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.logic.RoomViewLogic;
import nl.tudelft.oopp.demo.views.LoginView;
import nl.tudelft.oopp.demo.views.SearchView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import org.controlsfx.control.RangeSlider;


/**
 * Controller class for the Room view (JavaFX).
 */
public class RoomViewController implements Initializable {
    /**
     * These are the FXML elements that inject some functionality into the application.
     */
    @FXML
    private Text name;
    @FXML
    private Text capacity;
    @FXML
    private Text building;
    @FXML
    private Text teacherOnly;
    @FXML
    private Text type;
    @FXML
    private ImageView image;
    @FXML
    private Text description;
    // TODO: change String to Food entity
    @FXML
    private ComboBox<String> foodChoice;
    @FXML
    private Button bookButton;
    @FXML
    private DatePicker datePicker;
    @FXML
    private VBox reservationVbox;
    @FXML
    private Text startTime;
    @FXML
    private Text endTime;
    @FXML
    private Text dateError;
    @FXML
    private Text foodError;
    @FXML
    private Text timeSlotError;
    @FXML
    private Text teacherOnlyError;

    // double thumb slider
    private RangeSlider timeSlotSlider;

    // current room to show info about
    private static Room currentRoom;
    public static int currentRoomId;

    // current Stage
    public static Stage thisStage;

    /**
     * Method that gets called before everything (mostly to initialize nodes etc.).
     * JavaFX standard.
     *
     * @param location  is passed
     * @param resources is passed
     */
    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            // initialize the Room object that contains the info about this room
            currentRoom = Room.getRoomById(currentRoomId);

            // configure the string converters and custom properties (like disabling some dates in the datePicker)
            configureDatePicker();
            configureRangeSlider();

            // make sure errors are not visible
            hideErrors();

            // if user is a student and the room is teacher only => disable all components
            if (CurrentUserManager.getType() == 2 && currentRoom.getTeacherOnly().get()) {
                teacherOnlyError.setVisible(true);
                disableReservationComponents();
            } else {
                teacherOnlyError.setVisible(false);
            }

            // adjust layout
            changeWidthConstraints(thisStage.getWidth());
            image.setFitHeight(100000);

            // listener that adjusts layout when width of stage changes
            thisStage.widthProperty().addListener((obs, oldVal, newVal) -> {
                changeWidthConstraints(newVal);
            });

            // TODO: adjust the options of this comboBox based on the available food dishes
            ObservableList<String> foodList = FXCollections.observableArrayList();
            foodList.addAll("Ham Sandwich", "Cheese Sandwich", "Pasta", "No Food");
            foodChoice.setItems(foodList);

            // set text and image info about the room
            configureRoomInfoTexts();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that sets all the room info in the text and image fields.
     */
    private void configureRoomInfoTexts() {
        try {
            // sets all the room info text fields (+ image)
            name.setText("Name: " + currentRoom.getRoomName().get());
            capacity.setText("Capacity: " + currentRoom.getRoomCapacity().get());
            building.setText("Building: " + Building.getBuildingById(currentRoom.getRoomBuilding().get())
                    .getBuildingName().get());
            teacherOnly.setText("Teachers only: " + (currentRoom.getTeacherOnly().get() ? "yes" : "no"));
            type.setText("Type: " + currentRoom.getRoomType().get());
            description.setText("Description:\n" + currentRoom.getRoomDescription().get());
            // TODO: change to room's image
            image.setImage(new Image("images/placeholder.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that disables all the components needed to book a reservation.
     */
    private void disableReservationComponents() {
        // disbale all the components that are used to book a reservation
        try {
            bookButton.setDisable(true);
            foodChoice.setDisable(true);
            timeSlotSlider.setDisable(true);
            datePicker.setDisable(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Methods that hides all the error messages.
     */
    private void hideErrors() {
        try {
            // hide each error message
            dateError.setVisible(false);
            foodError.setVisible(false);
            timeSlotError.setVisible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method changes the width of some JavaFX nodes based on the width of the stage.
     * It's the callback method for the stage width listener.
     *
     * @param newWidth The new width of the current stage
     */
    private void changeWidthConstraints(Number newWidth) {
        try {
            // set the width of some nodes based on the calculated ratio between their width and the stages width
            image.setFitWidth((newWidth.doubleValue() - 188) / 1.41550696);
            reservationVbox.setPrefWidth((newWidth.doubleValue() - 188) / 3.3969);
            timeSlotSlider.setMaxWidth((newWidth.doubleValue() - 188) / 5);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * .
     * Methods that sets the dayCellFactory made in {@link #getDayCellFactory()}
     * and the StringConverter made in getDatePickerConverter
     */
    private void configureDatePicker() {
        try {
            // factory to create cell of DatePicker
            Callback<DatePicker, DateCell> dayCellFactory = this.getDayCellFactory();
            // set the factory
            datePicker.setDayCellFactory(dayCellFactory);
            // converter to convert value to String and vice versa
            StringConverter<LocalDate> converter = getDatePickerConverter(datePicker);
            // set the converter
            datePicker.setConverter(converter);
            // set value change listener to adjust css for available timeslots
            datePicker.valueProperty().addListener(((observable, oldValue, newValue) -> {
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
            StringConverter<Number> converter = getRangeSliderConverter();
            timeSlotSlider.setLabelFormatter(converter);

            // add listeners to show the current thumb values in separate Text objects
            configureRangeSliderListeners(converter);

            // configure css of rangeslider to show user what timeslots are free
            configureCss();

            // initialize the Text objects with the current values of the thumbs
            startTime.setText(converter.toString(timeSlotSlider.getLowValue()));
            endTime.setText(converter.toString(timeSlotSlider.getHighValue()));

            // inject the RangeSlider in the JavaFX layout
            reservationVbox.getChildren().add(2, timeSlotSlider);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * .
     * Configure (in CSS) the colors of the track of the range slider to show in green the available timeslots
     * and in red the rest
     */
    private void configureCss() {
        try {
            // get css file and delete its content to fill it again
            File css = new File(getClass().getResource("/RangeSlider.css").getPath());
            css.delete();
            css.createNewFile();
            BufferedWriter bw = new BufferedWriter(new FileWriter(css));
            // if no date picked, make slider track white
            if (datePicker.getValue() == null) {
                GeneralMethods.setSliderDefaultCss(timeSlotSlider, bw,
                        getClass().getResource("/RangeSlider.css").toExternalForm());
                return;
            }
            // get reservations for this room on the selected date
            List<Reservation> reservations = Reservation.getRoomReservationsOnDate(currentRoomId,
                    datePicker.getValue(), getDatePickerConverter(datePicker));

            if (reservations == null) {
                return;
            }

            // sort them in ascending order
            reservations.sort(new Comparator<Reservation>() {
                @Override
                public int compare(Reservation o1, Reservation o2) {
                    // split time in hh:mm
                    String[] o1StartSplit = o1.getStartingTime().get().split(":");
                    int o1StartHour = Integer.parseInt(o1StartSplit[0]);
                    int o1StartMinute = Integer.parseInt(o1StartSplit[1]);

                    String[] o2StartSplit = o2.getStartingTime().get().split(":");
                    int o2StartHour = Integer.parseInt(o2StartSplit[0]);
                    int o2StartMinute = Integer.parseInt(o2StartSplit[1]);

                    // compare hours and minutes
                    if (o1StartHour < o2StartHour) {
                        return -1;
                    } else if (o1StartHour > o2StartHour) {
                        return 1;
                    }
                    if (o1StartMinute < o2StartMinute) {
                        return -1;
                    } else {
                        return 1;
                    }
                }
            });

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
                    + "\t-fx-background-color: rgba(0,0,0,0.3);\n"
                    + "}");
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
     * Configure the rangeSlider listeners.
     * The listeners make sure that the user jumps intervals of
     * 30 minutes and sets the texts with the correct value.
     *
     * @param converter String converter that is created in getRangeSliderConverter
     */
    private void configureRangeSliderListeners(StringConverter<Number> converter) {
        try {
            // listeners to adjust start and end Text objects when thumbs get moved
            timeSlotSlider.highValueProperty().addListener((observable, oldValue, newValue) -> {
                endTime.setText(converter.toString(newValue));
            });
            timeSlotSlider.lowValueProperty().addListener((observable, oldValue, newValue) -> {
                startTime.setText(converter.toString(newValue));
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
     * Method that executes when the backButton is clicked. It returns to the searchview.
     *
     * @param event ActionEvent.
     */
    @FXML
    private void backButtonClicked(ActionEvent event) {
        try {
            // load the searchview
            SearchView sv = new SearchView();
            sv.start(thisStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that executes when book button is clicked. It checks if fields are correctly filled.
     *
     * @param event ActionEvent
     */
    @FXML
    private void bookClicked(ActionEvent event) {
        try {

            // input is valid, assign corresponding values
            if (RoomViewLogic.isInputValid(dateError, foodError, timeSlotError, datePicker, foodChoice, currentRoomId, this, timeSlotSlider)) {

                // if user confirms booking, reservations is sent to server
                if (RoomViewLogic.confirmBooking(currentRoom, thisStage, datePicker, timeSlotSlider)) {
                    // send new reservation to server
                    if (RoomViewLogic.createReservation(currentRoomId, datePicker, timeSlotSlider)) {
                        // create confirmation Alert
                        Alert alert = GeneralMethods.createAlert("Room booked",
                                "You successfully booked this room!",
                                ((Node) event.getSource()).getScene().getWindow(),
                                Alert.AlertType.CONFIRMATION);
                        alert.showAndWait();
                        // go back to search view
                        SearchView sv = new SearchView();
                        sv.start(thisStage);
                    } else {
                        // Throw exception with message that something went wrong
                        throw new Exception("reservation booking went wrong");
                    }
                }
            } else {
                // create error Alert
                Alert alert = GeneralMethods.createAlert("fields incomplete",
                        "Please fill in all the fields",
                        ((Node) event.getSource()).getScene().getWindow(), Alert.AlertType.ERROR);
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
            // create error Alert
            Alert alert = GeneralMethods.createAlert("Something went wrong",
                    "Sorry, something went wrong on our end. We're fixing it now!",
                    ((Node) event.getSource()).getScene().getWindow(), Alert.AlertType.ERROR);
            alert.showAndWait();
        }
    }

    /**
     * Redirects the user back to the login page.
     *
     * @param event ActionEvent
     */
    @FXML
    private void signOutButtonClicked(ActionEvent event) {
        try {
            //loads a new login page
            LoginView loginView = new LoginView();
            loginView.start(thisStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a StringConverter that converts the selected value to an actual time (in String format).
     *
     * @return a StringConverter object
     */
    public static StringConverter<Number> getRangeSliderConverter() {
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
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Creates a StringConverter that converts the selected value to a usable Date (in String format).
     *
     * @return a StringConverter object
     */
    public static StringConverter<LocalDate> getDatePickerConverter(DatePicker datePicker) {
        try {
            return new StringConverter<LocalDate>() {
                // set the wanted pattern (format)
                String pattern = "yyyy-MM-dd";
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

                {
                    // set placeholder text for the datePicker
                    datePicker.setPromptText(pattern.toLowerCase());
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
            e.printStackTrace();
        }
        return null;
    }

}
