package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import nl.tudelft.oopp.demo.communication.BikeReservationCommunication;
import nl.tudelft.oopp.demo.communication.GeneralMethods;
import nl.tudelft.oopp.demo.communication.user.CurrentUserManager;
import nl.tudelft.oopp.demo.entities.BikeReservation;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.views.RentABikeView;
import nl.tudelft.oopp.demo.views.SearchView;
import org.controlsfx.control.RangeSlider;


public class RentABikeController implements Initializable {
    @FXML
    private DatePicker datePicker;
    @FXML
    private Text dateError;
    @FXML
    private ComboBox<String> comboBuilding;
    @FXML
    private Text buildingError;
    @FXML
    private Spinner<Integer> spinner;
    @FXML
    private Text endTime;
    @FXML
    private VBox reservationVbox;
    @FXML
    private Text startTime;
    @FXML
    private ImageView image;

    private RangeSlider timeSlotSlider;

    public static Stage thisStage;

    public ObservableList<Building> buildingList = Building.getBuildingData();
    private static int currentBuilding = 0;
    ObservableList<String> buildList = FXCollections.observableArrayList();


    /**
     * Deals with the back button function.
     *
     * @param event ActionEvent
     */
    @FXML
    private void backButtonClicked(ActionEvent event) throws IOException {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            SearchView sv = new SearchView();
            sv.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Used to initialize nodes and populate elements.
     * Gets called before anything.
     *
     * @param location  passed
     * @param resources passed
     */
    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            comboBuilding.setVisibleRowCount(8);
            // make sure errors are not visible
            dateError.setVisible(false);
            buildingError.setVisible(false);

            // set up the date picker and date slider
            configureDatePicker();
            configureRangeSlider();


            changeWidthConstraints(thisStage.getWidth());
            image.setFitHeight(100000);

            // listener that adjusts layout when width of stage changes
            thisStage.widthProperty().addListener((obs, oldVal, newVal) -> changeWidthConstraints(newVal));

            datePicker.valueProperty().addListener((ov, oldValue, newValue) -> {
                String selectedDate = Objects.requireNonNull(getDatePickerConverter()).toString(datePicker.getValue());
                String selectedStartTime = Objects.requireNonNull(getRangeSliderConverter())
                        .toString(timeSlotSlider.getLowValue());
                String selectedEndTime = getRangeSliderConverter().toString(timeSlotSlider.getHighValue());
                populateBuilding(selectedStartTime, selectedEndTime, selectedDate);
            });

            timeSlotSlider.setOnMouseReleased(event -> {
                String selectedDate = Objects.requireNonNull(getDatePickerConverter()).toString(datePicker.getValue());
                String selectedStartTime = Objects.requireNonNull(getRangeSliderConverter())
                        .toString(timeSlotSlider.getLowValue());
                String selectedEndTime = getRangeSliderConverter().toString(timeSlotSlider.getHighValue());
                populateBuilding(selectedStartTime, selectedEndTime, selectedDate);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Checks if all the options are selected.
     *
     * @return true only when all the options are filled in
     */
    public boolean isInputValid() {
        try {
            // true if there are errors, false otherwise
            boolean input = true;

            // clear error messages
            dateError.setVisible(false);
            buildingError.setVisible(false);

            if (datePicker.getValue() == null) {
                dateError.setVisible(true);
                input = false;
            }
            if (comboBuilding.getValue() == null) {
                buildingError.setVisible(true);
                input = false;
            }

            // return true if no errors where triggered
            return input;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Deals with Reserve Now button clicked
     * Method that retrieves values from the functionalities and returns to the server  if all the.
     * requirements are met.
     *
     * @param event ActionEvent
     * @throws IOException throws exception
     */
    @FXML
    private void reserveNowClicked(ActionEvent event) throws IOException {
        try {
            // only the case when both are filled in
            if (isInputValid()) {
                /// retrieve date, bike number and time slot from the corresponding boxes
                String selectedDate =
                        Objects.requireNonNull(getDatePickerConverter()).toString(datePicker.getValue());
                String selectedStartTime = Objects.requireNonNull(getRangeSliderConverter())
                        .toString(timeSlotSlider.getLowValue());
                String selectedEndTime = getRangeSliderConverter().toString(timeSlotSlider.getHighValue());
                String selectedBuildingAndBike = comboBuilding.getValue();
                String selectedBuilding = getSelectedBuilding(selectedBuildingAndBike);
                Integer selectedBike = spinner.getValue();

                int buildNumber = getBuildingNumber(selectedBuilding);
                Building b = Building.getBuildingById(buildNumber);

                // check to see enough bikes for selected building
                if (getRemainder(b, selectedDate, selectedEndTime, selectedStartTime) - selectedBike >= 0) {
                    // create alert for confirmation with the user
                    Alert alert = GeneralMethods.createAlert("Your Bike Reservation", "Make reservation for "
                                    + selectedBike + " bikes"
                                    + " from " + selectedBuilding
                                    + " on " + selectedDate + " for " + selectedStartTime + "-"
                                    + selectedEndTime + "?", ((Node) event.getSource()).getScene().getWindow(),
                            Alert.AlertType.CONFIRMATION);
                    assert alert != null;
                    //set alert size depending on the text length
                    alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                    alert.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);

                    // allow for different responses of the alert
                    Optional<ButtonType> result = alert.showAndWait();

                    // if the user responds with OK
                    if (result.orElse(null) == ButtonType.OK) {

                        if (selectedEndTime.contains("24")) {
                            selectedEndTime = "23:59";
                        }

                        //send new reservation to the server
                        BikeReservationCommunication.createBikeReservation(getBuildingNumber(selectedBuilding),
                                CurrentUserManager.getUsername(), selectedBike, selectedDate,
                                selectedStartTime, selectedEndTime);
                        // inform the user for successful reservation
                        Alert alert2 = GeneralMethods.createAlert("Bike Reserved",
                                "You successfully reserved the bike(s)!",
                                ((Node) event.getSource()).getScene().getWindow(), Alert.AlertType.CONFIRMATION);
                        assert alert2 != null;
                        alert2.showAndWait();

                        // re-open the scene to update new number of bikes left
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                        RentABikeView rbv = new RentABikeView();
                        rbv.start(stage);

                    }
                    // do nothing if user selects no
                } else {
                    Alert alert = GeneralMethods.createAlert("Insufficient Bikes",
                            "Insufficient Bikes Available. Please check the number of bikes available",
                            ((Node) event.getSource()).getScene().getWindow(), Alert.AlertType.WARNING);
                    alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                    alert.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
                    alert.showAndWait();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create cellFactory for the datePicker that disables all days before today and weekend days.
     * It also marks them red to make sure the user understands why they are disabled.
     *
     * @return a CallBack to set the datePicker in {@link #configureDatePicker()}
     */
    private Callback<DatePicker, DateCell> getDayCellFactory() {
        try {
            return new Callback<>() {

                @Override
                public DateCell call(final DatePicker datePicker1) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Methods that sets the dayCellFactory made in {@link #getDayCellFactory()}
     * and the StringConverter made in {@link #getDatePickerConverter()}.
     */
    private void configureDatePicker() {
        try {
            // factory to create cell of DatePicker
            Callback<DatePicker, DateCell> dayCellFactory = this.getDayCellFactory();
            // set the factory
            datePicker.setDayCellFactory(dayCellFactory);
            // converter to convert value to String and vice versa
            StringConverter<LocalDate> converter = getDatePickerConverter();
            // set the converter
            datePicker.setConverter(converter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a StringConverter that converts the selected value to a usable Date (in String format).
     *
     * @return a StringConverter object
     */
    private StringConverter<LocalDate> getDatePickerConverter() {
        try {
            return new StringConverter<>() {
                // set the wanted pattern (format)
                final String pattern = "yyyy-MM-dd";
                final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

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

    /**
     * .
     * This method gets the Building ID based on the
     * buildingName given in String.
     *
     * @param name The selected Building Name
     */
    private int getBuildingNumber(String name) {
        try {
            int buildingNumber = 0;

            //look for specific buildingID with the given String one by one
            for (Building b : buildingList) {
                if (name.equals(b.getBuildingName().get())) {
                    buildingNumber = b.getBuildingId().get();
                    break;
                }
            }
            return buildingNumber;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * .
     * Creates a StringConverter that converts the selected value to an actual time (in String format).
     *
     * @return a StringConverter object
     */
    private StringConverter<Number> getRangeSliderConverter() {
        try {
            return new StringConverter<>() {
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

    /**
     * .
     * Configure the rangeSlider listeners. The listeners make sure that the user jumps
     * intervals of an hour and sets the texts with the correct value.
     *
     * @param converter String converter that is created in {@link #getRangeSliderConverter()}
     */
    private void configureRangeSliderListeners(StringConverter<Number> converter) {
        try {
            // listeners to adjust start and end Text objects when thumbs get moved
            timeSlotSlider.highValueProperty().addListener((observable, oldValue, newValue) ->
                    endTime.setText(converter.toString(newValue)));
            timeSlotSlider.lowValueProperty().addListener((observable, oldValue, newValue) ->
                    startTime.setText(converter.toString(newValue)));

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
     * .
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

            // initialize the Text objects with the current values of the thumbs
            assert converter != null;
            startTime.setText(converter.toString(timeSlotSlider.getLowValue()));
            endTime.setText(converter.toString(timeSlotSlider.getHighValue()));

            // inject the RangeSlider in the JavaFX layout
            reservationVbox.getChildren().add(2, timeSlotSlider);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Getting value directly from the comboBox includes the number of available bikes, as well as the
     * building name, hence this method is used.
     *
     * @param st Building Name
     * @return Name of the building
     */
    public String getSelectedBuilding(String st) {
        try {
            String result = "";
            for (int i = 0; i < buildingList.size(); i++) {
                if (st.contains(buildingList.get(i).getBuildingName().get())) {
                    result = buildingList.get(i).getBuildingName().get();
                    currentBuilding = i;
                    break;
                }
            }
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Automatically resizes the image as the window size changes.
     *
     * @param newWidth width of the window
     */
    private void changeWidthConstraints(Number newWidth) {
        try {
            // set the width of some nodes based on the calculated ratio between their width and the stages width
            image.setFitWidth((newWidth.doubleValue() - 188) / 1.41550696);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * populates buildingComboBox based on passed values.
     * @param selectedStart start of rent time
     * @param selectedEnd end of rent time
     * @param selectedDate date of rent
     */
    public void populateBuilding(String selectedStart, String selectedEnd, String selectedDate) {
        ///Ensures comboBox is not loaded unless date is selected
        if (selectedDate.equals(null)) {
            return;
        } else {
            //get building data
            ObservableList<Building> buildingList = Building.getBuildingData();
            //create observaleList to at to combobox
            ObservableList<String> buildList = FXCollections.observableArrayList();
            //empties combox before it loads
            comboBuilding.getItems().clear();

            //Set up the string for each building object
            for (Building b: buildingList) {
                String result =
                        b.getBuildingName().get() + ": " + getRemainder(b, selectedDate, selectedEnd, selectedStart);
                buildList.add(result);
            }
            //populate comboBuilding box
            comboBuilding.setItems(buildList);
        }
    }

    /**
     * Parses time into hours. Deals with both the formats.
     * @param time Provided time
     * @return only hour of given string.
     */
    public double parseTime(String time) {
        try {
            double hour =0;
            int minute = 0;
            //Checks the format of given String
            if (time.length() == 5) {
                //create localtime object in the given form
                LocalTime localTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
                hour = localTime.get(ChronoField.CLOCK_HOUR_OF_DAY);
                minute = localTime.get(ChronoField.MINUTE_OF_HOUR);
            } else if (time.length() == 8) {
                LocalTime localTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm:ss"));
                hour = localTime.get(ChronoField.CLOCK_HOUR_OF_DAY);
                minute = localTime.get(ChronoField.MINUTE_OF_HOUR);
            }
            //Slider is compatible til 30 minutes checks if minutes is 30
            if (minute == 30) {
                hour = hour + 0.5;
            }

            return hour;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Gets remaining number of bikes by reducing bikes rented from maximum bike of a building.
     * @param b given object
     * @param selectedDate given date
     * @param selectedEnd given end time of rent
     * @param selectedStart give start time of rent
     * @return
     */
    private int getRemainder(Building b, String selectedDate, String selectedEnd, String selectedStart) {
        try {

            ObservableList<BikeReservation> bikeReservationsList = BikeReservation.getBikeReservationData();
            int remainder = b.getBuildingMaxBikes().get();

            //Using stream to filter out given conditions
            List<BikeReservation> filteredReservations = bikeReservationsList.stream()
                    .filter(x -> x.getBikeReservationBuilding().get() == b.getBuildingId().get())
                    .filter(x -> x.getBikeReservationDate().get().equals(selectedDate))
                    .filter(x -> parseTime(x.getBikeReservationStartingTime().get()) < parseTime(selectedEnd))
                    .filter(x -> parseTime(x.getBikeReservationEndingTime().get()) > parseTime(selectedStart))
                    .collect(Collectors.toList());

            //Go through the list one by one and subtract from total
            for (BikeReservation br : filteredReservations) {
                remainder = remainder - br.getBikeReservationQuantity().get();
            }
            //Accidentally made bike number negative in the developping process
            if (remainder < 0) {
                remainder = 0;
            }
            return remainder;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}