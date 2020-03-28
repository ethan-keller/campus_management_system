package nl.tudelft.oopp.demo.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import nl.tudelft.oopp.demo.communication.GeneralMethods;
import nl.tudelft.oopp.demo.entities.BikeReservation;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.User;
import org.controlsfx.control.RangeSlider;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class UserBikeEditDialogController {

    @FXML
    private ComboBox<Building> bikeBuildingComboBox;

    @FXML
    private TextField bikeQuantityField;

    @FXML
    private DatePicker bikeDate;

    @FXML
    private Text bikeStartingTime;

    @FXML
    private Text bikeEndingTime;

    @FXML
    private GridPane grid;

    @FXML
    private Label timeslot;

    private ObservableList<User> olu;

    private ObservableList<Building> olb;

    private RangeSlider timeslotSlider;

    public static BikeReservation bikeReservation;

    private Stage dialogStage;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * .
     * Default constructor of the BikeEditDialogController class.
     */
    public UserBikeEditDialogController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        try {
            final BikeReservation bikeReservation = AdminBikeReservationViewController.currentSelectedBikeReservation;
            this.bikeReservation = null;

            bikeDate.setConverter(getDateConverter());

            // Initialize the building combobox
            olb = Building.getBuildingData();
            bikeBuildingComboBox.setItems(olb);
            this.setBikeBuildingComboBoxConverter(olb);

            //This method sets up the slider which determines the time of reservation in the dialog view.
            configureRangeSlider();

            // set stylesheet for range slider
            timeslotSlider.getStylesheets().add(getClass().getResource("/RangeSlider.css").toExternalForm());

            bikeDate.setDayCellFactory(getDayCellFactory());

            // change CSS when date changes or room changes
            bikeDate.valueProperty().addListener(((observable, oldValue, newValue) -> {
                configureCss();
            }));
            bikeBuildingComboBox.valueProperty().addListener(((observable, oldValue, newValue) -> {
                configureCss();
            }));

            if (bikeReservation != null) {

                if (olb == null) {
                    return;
                }
                bikeBuildingComboBox.getSelectionModel().select(olb.stream()
                        .filter(x -> x.getBuildingId().get() == bikeReservation.getBikeReservationBuilding().get())
                        .collect(Collectors.toList()).get(0));

                bikeQuantityField.setText(String.valueOf(bikeReservation.getBikeReservationQuantity().get()));

                bikeDate.setValue(LocalDate.parse(bikeReservation.getBikeReservationDate().get(), formatter));
                String[] startTimeSplit = bikeReservation.getBikeReservationStartingTime().get().split(":");

                timeslotSlider.setLowValue(Double.parseDouble(startTimeSplit[0]) * 60.0
                        + Double.parseDouble(startTimeSplit[1]));
                String[] endTimeSplit = bikeReservation.getBikeReservationEndingTime().get().split(":");
                timeslotSlider.setHighValue(Double.parseDouble(endTimeSplit[0]) * 60.0
                        + Double.parseDouble(endTimeSplit[1]));

                bikeStartingTime.setText("Start: " + getRangeSliderConverter().toString(timeslotSlider.getLowValue()));
                bikeEndingTime.setText("End: " + getRangeSliderConverter().toString(timeslotSlider.getHighValue()));
            } else {
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructor for the cellFactory of the DatePicker.
     * Makes sure no day before today + weekend days are available.
     *
     * @return a ready to use CallBack that invalidates unwanted dates in the date picker
     */
    private Callback<DatePicker, DateCell> getDayCellFactory() {
        try {
            return new Callback<DatePicker, DateCell>() {

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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Configures the RangeSlider.
     * Sets the CSS, converter and listeners as well.
     */
    private void configureRangeSlider() {
        try {
            // initialize the RangeSlider (values are handled as minutes) and the positions of the thumbs
            timeslotSlider = new RangeSlider(480, 1440, 600, 1080);
            timeslotSlider.setLowValue(600);
            timeslotSlider.setMinWidth(100);
            timeslotSlider.setMaxWidth(200);
            timeslotSlider.setShowTickLabels(true);
            timeslotSlider.setShowTickMarks(true);
            timeslotSlider.setMajorTickUnit(120);

            // configure css of rangeslider to show user what timeslots are free
            configureCss();

            // get and set the StringConverter to show hh:mm format
            StringConverter<Number> converter = getRangeSliderConverter();
            timeslotSlider.setLabelFormatter(converter);

            // add listeners to show the current thumb values in separate Text objects
            configureRangeSliderListeners(converter);

            // initialize the Text objects with the current values of the thumbs
            bikeStartingTime.setText(converter.toString(timeslotSlider.getLowValue()));
            bikeEndingTime.setText(converter.toString(timeslotSlider.getHighValue()));

            // inject the RangeSlider in the JavaFX layout
            grid.add(timeslotSlider, 1, 3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Configures the CSS of the RangeSlider.
     * Configure (in CSS) the colors of the track of the range slider to show in green the available timeslots
     * and in red the rest
     */
    private void configureCss() {
        try {
            // get currently selected builiding
            Building selectedBuilding = bikeBuildingComboBox.getSelectionModel().getSelectedItem();
            // get css file and delete its content to fill it again
            File css = new File(getClass().getResource("/RangeSlider.css").getPath());
            css.delete();
            css.createNewFile();
            BufferedWriter bw = new BufferedWriter(new FileWriter(css));

            if (selectedBuilding == null) {
                // make track completely white
                GeneralMethods.setSliderDefaultCss(timeslotSlider, bw,
                        getClass().getResource("/RangeSlider.css").toExternalForm());
                return;
            }
            // get reservations for this room on the selected date
            List<BikeReservation> bikeReservations = BikeReservation.getBikeReservationsOnDate(selectedBuilding.getBuildingId().get(),
                    bikeDate.getValue(), getDateConverter());

            // sort them in ascending order
            bikeReservations.sort(new Comparator<BikeReservation>() {
                @Override
                public int compare(BikeReservation o1, BikeReservation o2) {
                    // split time in hh:mm
                    String[] o1StartSplit = o1.getBikeReservationStartingTime().get().split(":");
                    int o1StartHour = Integer.parseInt(o1StartSplit[0]);
                    int o1StartMinute = Integer.parseInt(o1StartSplit[1]);

                    String[] o2StartSplit = o2.getBikeReservationStartingTime().get().split(":");
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
            Iterator<BikeReservation> it = bikeReservations.iterator();

            // if there are no reservations make the track completely green
            if (!it.hasNext()) {
                bw.write("#91ef99 0%, #91ef99 100%);\n");
            }

            BikeReservation bres = AdminBikeReservationViewController.currentSelectedBikeReservation;

            // calculate and add green and red parts
            while (it.hasNext()) {
                BikeReservation br = it.next();
                // split start and end times into hours and minutes
                String[] startTime = br.getBikeReservationStartingTime().get().split(":");
                String[] endTime = br.getBikeReservationEndingTime().get().split(":");

                // calculate the percentage of the track that the reservation should cover
                double startPercentage = ((Double.parseDouble(startTime[0]) - 8.0) * 60.0
                        + Double.parseDouble(startTime[1])) / 9.60;
                double endPercentage = ((Double.parseDouble(endTime[0]) - 8.0) * 60.0
                        + Double.parseDouble(endTime[1])) / 9.60;
                // if reservation is the one that is being edited, give it a light blue color
                if (bres != null && bres.getBikeReservationId().get() == br.getBikeReservationId().get()) {
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
            timeslotSlider.getStylesheets().remove(getClass().getResource("/RangeSlider.css").toExternalForm());
            // add new stylesheet
            timeslotSlider.getStylesheets().add(getClass().getResource("/RangeSlider.css").toExternalForm());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Configure RangeSlider listeners that change the Start and End text values.
     * This method also makes sure that the user can only pick timeslots of 30 min
     *
     * @param converter StringConverter that converts slider value to String hh:mm format
     */
    private void configureRangeSliderListeners(StringConverter<Number> converter) {
        try {
            // listeners to adjust start and end Text objects when thumbs get moved
            timeslotSlider.highValueProperty().addListener((observable, oldValue, newValue) ->
                    bikeEndingTime.setText("End: " + converter.toString(newValue)));
            timeslotSlider.lowValueProperty().addListener((observable, oldValue, newValue) ->
                    bikeStartingTime.setText("Start: " + converter.toString(newValue)));

            // listeners that make sure the user can only select intervals of 30 minutes
            timeslotSlider.lowValueProperty().addListener((observable, oldValue, newValue) ->
                    timeslotSlider.setLowValue((newValue.intValue() / 30) * 30));
            timeslotSlider.highValueProperty().addListener((observable, oldValue, newValue) ->
                    timeslotSlider.setHighValue((newValue.intValue() / 30) * 30));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructor for the RangeSlider converter that converts the slider values to String hh:mm format.
     *
     * @return constructed StringConverter
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
                public Number fromString(String string) {
                    String[] split = string.split(":");
                    double hours = Double.parseDouble(split[0]);
                    double minutes = Double.parseDouble(split[1]);
                    return hours * 60 + minutes;
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Constructor for the converter that converts LocalDate objects to String yyyy-MM-dd format.
     *
     * @return constructed StringConverter
     */
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
     * Set the building combobox converter
     *
     * @param olb
     */
    public void setBikeBuildingComboBoxConverter(ObservableList<Building> olb) {
        StringConverter<Building> converter = new StringConverter<Building>() {
            @Override
            public String toString(Building object) {
                if (object == null) return "";
                else return object.getBuildingName().get();
            }

            @Override
            public Building fromString(String id) {
                return olb.stream().filter(x -> String.valueOf(x.getBuildingId()) == id).collect(Collectors.toList()).get(0);
            }
        };
        bikeBuildingComboBox.setConverter(converter);
    }

    /**
     * Create a new bike reservation when called
     */
    private static void emptyReservation() {
        bikeReservation = new BikeReservation();
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOkClicked(ActionEvent event) {
        // Check the validity of user input
        if (isInputValid()) {
            emptyReservation();
            // Set the user input to the reservation
            bikeReservation.setBikeReservationUser(AdminManageUserViewController.currentSelectedUser.getUsername().get());
            bikeReservation.setBikeReservationBuilding(this.bikeBuildingComboBox.getSelectionModel().getSelectedItem().getBuildingId().get());
            bikeReservation.setBikeReservationQuantity(Integer.parseInt(this.bikeQuantityField.getText()));
            bikeReservation.setBikeReservationDate(this.bikeDate.getValue().toString());
            bikeReservation.setBikeReservationStartingTime(bikeStartingTime.getText().replace("Start: ", ""));
            bikeReservation.setBikeReservationEndingTime(bikeEndingTime.getText().replace("End: ", "").equals("24:00")
                    ? "23:59" : bikeEndingTime.getText().replace("End: ", ""));
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
        bikeReservation = null;
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

        if (bikeBuildingComboBox.getSelectionModel().getSelectedIndex() < 0) {
            errorMessage += "No valid building selected!\n";
        }
        if (bikeQuantityField.getText().equals("")) {
            errorMessage += "No valid bike quantity!\n";
        } else {
            try {
                Integer.parseInt(bikeQuantityField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid bike quantity (must be an integer)!\n";
            }
        }
        if (bikeDate.getValue() == null) {
            errorMessage += "No valid date selected!\n";
        }
        if (!checkTimeSlotValidity() || timeslotSlider.getLowValue() == timeslotSlider.getHighValue()) {
            errorMessage += "No valid timeslot selected!\n";
        }
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

    /**
     * Method that checks if the chosen timeslot is free.
     *
     * @return true if the timeslot is free, false otherwise
     */
    private boolean checkTimeSlotValidity() {
        try {
            // get currently selected room
            Building selectedBuilding = bikeBuildingComboBox.getSelectionModel().getSelectedItem();
            if (selectedBuilding == null) {
                return false;
            }
            // get all reservations for the current room on the chosen date
            List<BikeReservation> bikeReservations = BikeReservation.getBikeReservationsOnDate(
                    selectedBuilding.getBuildingId().get(),
                    bikeDate.getValue(), getDateConverter());

            // if something went wrong with the server communication return false
            if (bikeReservations == null) {
                return false;
            }

            // get converter to convert date value to String format hh:mm
            StringConverter<Number> timeConverter = getRangeSliderConverter();

            // if there are no reservations the timeslot is valid
            if (bikeReservations.size() == 0) {
                return true;
            }

            BikeReservation bres = AdminBikeReservationViewController.currentSelectedBikeReservation;

            for (BikeReservation br : bikeReservations) {
                // if reservation equals the one we are editing, don't consider it
                if (bres != null) {
                    if (br.getBikeReservationId().get() == bres.getBikeReservationId().get()) {
                        continue;
                    }
                }

                // get rangeslider values + reservation values
                double currentStartValue = timeslotSlider.getLowValue();
                double currentEndValue = timeslotSlider.getHighValue();
                double startValue = (double) timeConverter.fromString(br.getBikeReservationStartingTime().get());
                double endValue = (double) timeConverter.fromString(br.getBikeReservationEndingTime().get());

                // check if the values overlap
                if (!((currentStartValue <= startValue && currentEndValue <= startValue)
                        || (currentStartValue >= endValue && currentEndValue >= endValue))) {
                    return false;
                }

            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
