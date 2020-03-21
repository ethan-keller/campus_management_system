package nl.tudelft.oopp.demo.controllers;

import com.mindfusion.common.DateTime;
import com.mindfusion.scheduling.model.Appointment;
import com.mindfusion.scheduling.model.Style;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.controlsfx.control.RangeSlider;

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class CalenderAppointmentDialogController {

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

    private RangeSlider timeSlot;

    @FXML
    private TextArea description;

    public static Appointment appointment;
    public static Stage dialogStage;

    public CalenderAppointmentDialogController() {
    }

    @FXML
    public void initialize() {
        configureDatePicker();
        timeSlot = configureRangeSlider();
        gridPane.add(timeSlot, 1, 2);
    }

    private RangeSlider configureRangeSlider() {
        // initialize the RangeSlider (values are handled as minutes) and the positions of the thumbs
        RangeSlider slider = new RangeSlider(0, 1440, 480, 1080);
        slider.setLowValue(480);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
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
        } catch (Exception e){
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
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * To check if the information filled out by the user is valid.
     * @return boolean value to verify if information is valid.
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if(date.getValue() == null) {
            errorMessage += "No date provided!\n";
        }
        if(header.getText().equals("")) {
            errorMessage += "No header provided!\n";
        }
        if(description.getText().equals("")){
            errorMessage += "No description provided\n";
        }

        // If any of the fields is left blank return true.
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
        } catch (Exception e){
            e.printStackTrace();
        }
    }

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
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
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
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Called when the user clicks cancel.
     * @param event
     */
    @FXML
    public void cancelClicked(ActionEvent event) {
        this.dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        dialogStage.close();
    }

    /**
     * Called when the user clicks confirm.
     * @param
     */
    @FXML
    public void confirmClicked(ActionEvent event) {
        if(isInputValid()){
            Appointment app = new Appointment();
            app.setHeaderText(header.getText());
            app.setDescriptionText(description.getText());

            String date = this.date.getValue().toString();
            String[] dateSplit = date.split("-");
            int year = Integer.parseInt(dateSplit[0]);
            int month = Integer.parseInt(dateSplit[1]);
            int day = Integer.parseInt(dateSplit[2]);

            String[] startSplit = startText.getText().replace("Start: ", "").split(":");
            String[] endSplit = endText.getText().replace("End: ", "").split(":");


            app.setStartTime(new DateTime(year, month, day, Integer.parseInt(startSplit[0]), Integer.parseInt(startSplit[1]), 0));

            app.setEndTime(new DateTime(year, month, day, Integer.parseInt(endSplit[0]), Integer.parseInt(endSplit[1]), 0));

            app.setLocked(true);
            app.setAllowMove(false);
            Style color = new Style();
            color.setFillColor(Color.ORANGE);
            app.setStyle(color);

            appointment = app;

            this.dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            dialogStage.close();
        }
    }

}
