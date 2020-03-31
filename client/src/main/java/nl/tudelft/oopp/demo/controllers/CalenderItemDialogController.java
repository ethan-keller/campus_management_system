package nl.tudelft.oopp.demo.controllers;

import com.mindfusion.common.DateTime;
import com.mindfusion.scheduling.model.Appointment;

import java.awt.Color;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

import nl.tudelft.oopp.demo.communication.GeneralMethods;

import org.controlsfx.control.RangeSlider;


/**
 * Class that controls the dialog box to add a calendar item to the users calendar.
 */
public class CalenderItemDialogController implements Initializable {

    private static Logger logger = Logger.getLogger("GlobalLogger");

    @FXML
    private TextField header;
    @FXML
    private DatePicker date;
    @FXML
    private Text startText;
    @FXML
    private Text endText;
    @FXML
    private GridPane gridPane;
    @FXML
    private TextArea description;

    private RangeSlider timeSlot;
    public static Appointment item;
    public static Stage dialogStage;

    /**
     * default constructor needed by JavaFX.
     */
    public CalenderItemDialogController() {
    }

    /**
     * .
     * Custom initialization of JavaFX components. This method is automatically called
     * after the fxml file has been loaded.
     *
     * @param location  is passed
     * @param resources is passed
     */
    @Override
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        item = null;
        configureDatePicker();
        timeSlot = configureRangeSlider();
        gridPane.add(timeSlot, 1, 2);
    }

    /**
     * Method that configures the RangeSlider and returns it ready to use.
     *
     * @return ready to use RangeSlider
     */
    private RangeSlider configureRangeSlider() {
        // initialize the RangeSlider (values are handled as minutes) and the positions of the thumbs
        RangeSlider slider = new RangeSlider(0, 1440, 480, 1080);
        // set value of lower thumb
        slider.setLowValue(480);
        // show ticks and marks
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        // set a major tick unit every 2 hours on the track
        slider.setMajorTickUnit(120);

        // get and set the StringConverter to show hh:mm format
        StringConverter<Number> converter = getRangeSliderConverter();
        slider.setLabelFormatter(converter);

        // add listeners to show the current thumb values in separate Text objects
        configureRangeSliderListeners(converter, slider);

        // initialize the Text objects with the current values of the thumbs
        startText.setText("Start: " + converter.toString(slider.getLowValue()));
        endText.setText("End: " + converter.toString(slider.getHighValue()));

        return slider;
    }

    /**
     * Configures the listeners of the RangeSlider needed for the start and end texts.
     *
     * @param converter converts RangeSlider values to hh:mm format
     * @param slider    the RangeSlider that needs configuration
     */
    private void configureRangeSliderListeners(StringConverter<Number> converter, RangeSlider slider) {
        try {
            // listeners to adjust start and end Text objects when thumbs get moved
            slider.highValueProperty().addListener((observable, oldValue, newValue) ->
                    endText.setText("End: " + converter.toString(newValue)));
            slider.lowValueProperty().addListener((observable, oldValue, newValue) ->
                    startText.setText("Start: " + converter.toString(newValue)));

            // listeners that make sure the user can only select intervals of 30 minutes
            slider.lowValueProperty().addListener((observable, oldValue, newValue) ->
                    slider.setLowValue((newValue.intValue() / 30) * 30));
            slider.highValueProperty().addListener((observable, oldValue, newValue) ->
                    slider.setHighValue((newValue.intValue() / 30) * 30));
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
        }
    }

    /**
     * Method that constructs and returns a ready to use RangeSlider converter for the time format hh:mm.
     *
     * @return the completed StringConverter
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
                    return null;
                }
            };
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
        }
        return null;
    }

    /**
     * .
     * To check if the information filled out by the user is valid.
     *
     * @return true if valid, false otherwise
     */
    private boolean isInputValid() {
        String errorMessage = "";

        // add error message for every error found
        if (date.getValue() == null) {
            errorMessage += "No date provided!\n";
        }
        if (header.getText().equals("")) {
            errorMessage += "No header provided!\n";
        }
        if (description.getText().equals("")) {
            errorMessage += "No description provided\n";
        }

        // If no errors, return true
        if (errorMessage.equals("")) {
            return true;
        } else {
            // Show the error message.
            Alert alert = GeneralMethods.createAlert("Invalid fields", errorMessage, dialogStage,
                    Alert.AlertType.ERROR);
            alert.showAndWait();
            return false;
        }
    }

    /**
     * Configures the date picker to show valid dates and format the dates as needed.
     */
    private void configureDatePicker() {
        try {
            // factory to create cell of DatePicker
            Callback<DatePicker, DateCell> dayCellFactory = this.getDayCellFactory();
            // set the factory
            date.setDayCellFactory(dayCellFactory);
            // converter to convert value to String and vice versa
            StringConverter<LocalDate> converter = getDatePickerConverter();
            // set the converter
            date.setConverter(converter);
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
        }
    }

    /**
     * Constructs the StringConverter for the datepicker to format the date to yyyy-MM-dd.
     *
     * @return StringConverter
     */
    private StringConverter<LocalDate> getDatePickerConverter() {
        try {
            return new StringConverter<LocalDate>() {
                // set the wanted pattern (format)
                String pattern = "yyyy-MM-dd";
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

                {
                    // set placeholder text for the datePicker
                    date.setPromptText(pattern.toLowerCase());
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

    /**
     * Constructs a DayCellFactory for the calendar that only validates future dates.
     *
     * @return
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

                            // Disable all days before today
                            if (item.isBefore(LocalDate.now())) {
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
     * Cancels the item creation.
     *
     * @param event to get current stage
     */
    @FXML
    private void cancelClicked(ActionEvent event) {
        // get current stage and close it
        this.dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        dialogStage.close();
    }

    /**
     * Confirms item creation and sets the class attribute.
     *
     * @param event to get current stage
     */
    @FXML
    private void confirmClicked(ActionEvent event) {
        // check if fields have correct inputs
        if (isInputValid()) {
            // create Appointment object and set the values
            Appointment app = new Appointment();
            app.setHeaderText(header.getText());
            app.setDescriptionText(description.getText());

            String date = this.date.getValue().toString();
            // split date in [yyyy, MM, dd]
            String[] dateSplit = date.split("-");
            int year = Integer.parseInt(dateSplit[0]);
            int month = Integer.parseInt(dateSplit[1]);
            int day = Integer.parseInt(dateSplit[2]);

            // split time in [hh:mm:ss]
            String[] startSplit = startText.getText().replace("Start: ", "").split(":");
            String[] endSplit = endText.getText().replace("End: ", "")
                    .split(":")[0].equals("24") ? new String[]{"23", "59"} : endText.getText()
                    .replace("End: ", "").split(":");

            app.setStartTime(new DateTime(year, month, day, Integer.parseInt(startSplit[0]),
                    Integer.parseInt(startSplit[1]), 0));
            app.setEndTime(new DateTime(year, month, day, Integer.parseInt(endSplit[0]),
                    Integer.parseInt(endSplit[1]), 0));

            // make sure the user cannot move around the item
            app.setLocked(true);
            app.setAllowMove(false);

            // set orange side color
            app.getStyle().setFillColor(Color.ORANGE);

            // assign value to class attribute
            item = app;

            // get current stage and close
            this.dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            dialogStage.close();
        }
    }

}
