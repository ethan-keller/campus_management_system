package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

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

import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javafx.util.Callback;
import javafx.util.StringConverter;

import nl.tudelft.oopp.demo.communication.BikeReservationCommunication;
import nl.tudelft.oopp.demo.communication.GeneralMethods;
import nl.tudelft.oopp.demo.communication.user.CurrentUserManager;
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
    private VBox buildingBikes;
    @FXML
    private Text endTime;
    @FXML
    private VBox reservationVbox;
    @FXML
    private Text startTime;

    private RangeSlider timeSlotSlider;


    /**
     * deal with the button clicking action.
     */
    @FXML
    private void backButtonClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        SearchView sv = new SearchView();
        sv.start(stage);
    }

    /**
     *Sets default values to populate the options.
     */
    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Retrieving buildings into ObservableList
        ObservableList<Building> buildingList = Building.getBuildingData();
        //Creating Observable List for holding String values to apply to dropdownBox
        ObservableList<String> buildList = FXCollections.observableArrayList();
        //Ensure buildingLIst cannot be null
        assert buildingList != null;

        //Converting buildingName into String for each item and getting number of available bikes
        for (Building b : buildingList) {
            buildList.add(b.getBuildingName().get());
            buildingBikes.getChildren().add(getEachBikes(b));
        }
        //Setting the values to comboBox
        comboBuilding.setItems(buildList);

        // make sure errors are not visible
        dateError.setVisible(false);
        buildingError.setVisible(false);

        // set up the date picker and date slider
        configureDatePicker();
        configureRangeSlider();


    }

    /**
     * .
     * This method gets a building object and returns the available number
     * of bikes together with corresponding building.
     *
     * @param b Building object
     */
    private Text getEachBikes(Building b) {
        // get buildingName in String
        String buildName = b.getBuildingName().get();
        // get available number of bikes
        int buildBikes = b.getBuildingAvailableBikes().get();

        // create new text object for each building object
        Text text = new Text();
        //set building name and bike number in text format
        text.setText(buildName + ": " + buildBikes);
        // set color of text
        text.setFill(Color.WHITE);
        // set font and size
        text.setFont(Font.font("System", 14));

        return text;
    }




    /**
     * Checks whether if all the fields were filled in.
     */
    public boolean isInputValid() {
        boolean input = false;
        while (!input) {
            // If both datePicker and  ComboBuilding are null
            if (datePicker.getValue() == null && comboBuilding.getSelectionModel().getSelectedItem() == null) {
                // set both text visible
                dateError.setVisible(true);
                buildingError.setVisible(true);
                input = false;
            } else if (comboBuilding.getSelectionModel().getSelectedItem() == null) {
                // only sets buildingError visible
                buildingError.setVisible(true);
                input = false;
            } else if (datePicker.getValue() == null) {
                // only sets datePicker visible
                dateError.setVisible(true);
                input = false;
            } else {
                input = true;
            }
        }
        return true;
    }

    /**
     * Deals with reserve now button clicks.
     */
    @FXML
    private void reserveNowClicked(ActionEvent event) throws IOException {
        // only the case when both are filled in
        if (isInputValid()) {
            /// retrieve date, bike number and time slot from the corresponding boxes
            String selectedDate = Objects.requireNonNull(getDatePickerConverter()).toString(datePicker.getValue());
            int selectedBike = spinner.getValue();
            String selectedStartTime = Objects.requireNonNull(getRangeSliderConverter())
                    .toString(timeSlotSlider.getLowValue());
            String selectedEndTime = getRangeSliderConverter().toString(timeSlotSlider.getHighValue());
            String selectedBuilding = comboBuilding.getValue();

            // check to see enough bikes for selected building
            if (checkBikeAvailability(selectedBuilding, selectedBike)) {
                // create alert for confirmation with the user
                Alert alert = GeneralMethods.createAlert("Your Bike Reservation", "Make reservation for "
                                + selectedBike + " bikes"
                        + " from " + selectedBuilding + " on " + selectedDate + "for " + selectedStartTime + "-"
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

                    //send new reservation to the server
                    BikeReservationCommunication.createBikeReservation(getBuildingNumber(selectedBuilding),
                            CurrentUserManager.getUsername(), selectedBike,selectedDate,
                            selectedStartTime, selectedEndTime);
                    // inform the user for successful reservation
                    Alert alert2 = GeneralMethods.createAlert("Room booked", "You successfully booked this room!",
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

    //Checks if there are sufficient bikes in the database
    private Boolean checkBikeAvailability(String buildingName, int num) {
        //get available building data
        ObservableList<Building> buildingList = Building.getBuildingData();
        // ensure the list cannot be null
        Building building = null;
        assert buildingList != null;
        //looks for same name of the building as buildingName
        for (Building b : buildingList) {
            if (b.getBuildingName().get().equals(buildingName)) {
                building = b;
                //stops for loop as soon as it is found
                break;
            }
        }

        int id = building.getBuildingId().get();
        Building selectedBuilding = Building.getBuildingById(id);
        int availableBikes = selectedBuilding.getBuildingAvailableBikes().get();

        if ((availableBikes - num) >= 0) {
            return true;
        } else {
            return  false;
        }
    }

    /**
     * .
     * This method gets the Building ID based on the
     * buildingName given in String.
     *
     * @param name The selected Building Name
     */
    private int getBuildingNumber(String name) {
        int buildingNumber = 0;
        ObservableList<Building> buildingList = Building.getBuildingData();
        assert buildingList != null;
        //look for specific buildingID with the given String one by one
        for (Building b: buildingList) {
            if (name.equals(b.getBuildingName().get())) {
                buildingNumber = b.getBuildingId().get();
                break;
            }
        }
        return buildingNumber;

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



}
