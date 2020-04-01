package nl.tudelft.oopp.demo.controllers;

import java.util.concurrent.TimeUnit;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import nl.tudelft.oopp.demo.communication.GeneralMethods;
import nl.tudelft.oopp.demo.entities.Building;

import org.controlsfx.control.RangeSlider;

public class BuildingEditDialogController {

    public static Building building;
    @FXML
    private GridPane grid;
    @FXML
    private TextField buildingNameField;
    @FXML
    private TextField buildingAddressField;
    @FXML
    private Spinner<Integer> maxBikesField;
    @FXML
    private Text openingTime;
    @FXML
    private Text closingTime;
    // double thumb slider
    private RangeSlider openingHoursSlider;
    private Stage dialogStage;

    private static void emptyBuilding() {
        building = new Building();
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        Building building = AdminManageBuildingViewController.currentSelectedBuilding;
        StringConverter<Number> converter = getRangeSliderConverter();

        setMaxBikesSpinnerValueFactory();
        configureRangeSlider();

        if (building == null) {
            return;
        }

        String closing = building.getClosingTime().get();
        String opening = building.getOpeningTime().get();

        if (closing.contains("59")) {
            openingHoursSlider.setHighValue(1440);
        } else {
            openingHoursSlider.setHighValue((double) converter.fromString(closing));
        }
        openingHoursSlider.setLowValue((double) converter.fromString(opening));
        maxBikesField.getValueFactory().setValue(building.getBuildingMaxBikes().getValue());
        buildingNameField.setText(building.getBuildingName().get());
        buildingAddressField.setText(building.getBuildingAddress().get());
    }

    private void configureRangeSlider() {
        try {
            // initialize the RangeSlider (values are handled as minutes) and the positions of the thumbs
            openingHoursSlider = new RangeSlider(0, 1440, 480, 1760);
            openingHoursSlider.setLowValue(480);
            openingHoursSlider.setMinWidth(100);
            openingHoursSlider.setMaxWidth(270);
            openingHoursSlider.setShowTickLabels(true);
            openingHoursSlider.setShowTickMarks(true);
            openingHoursSlider.setMajorTickUnit(120);
            openingHoursSlider.setMinorTickCount(4);

            // get and set the StringConverter to show hh:mm format
            StringConverter<Number> converter = getRangeSliderConverter();
            openingHoursSlider.setLabelFormatter(converter);

            // add listeners to show the current thumb values in separate Text objects
            configureRangeSliderListeners(converter);

            // initialize the Text objects with the current values of the thumbs
            openingTime.setText(converter.toString(openingHoursSlider.getLowValue()));
            closingTime.setText(converter.toString(openingHoursSlider.getHighValue()));

            // inject the RangeSlider in the JavaFX layout
            grid.add(openingHoursSlider, 1, 3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void configureRangeSliderListeners(StringConverter<Number> converter) {
        try {
            // listeners to adjust start and end Text objects when thumbs get moved
            openingHoursSlider.highValueProperty().addListener((observable, oldValue, newValue) -> {
                closingTime.setText(converter.toString(newValue));
            });
            openingHoursSlider.lowValueProperty().addListener((observable, oldValue, newValue) -> {
                openingTime.setText(converter.toString(newValue));
            });

            // listeners that make sure the user can only select intervals of 30 minutes
            openingHoursSlider.lowValueProperty().addListener((observable, oldValue, newValue) ->
                    openingHoursSlider.setLowValue((newValue.intValue() / 30) * 30));
            openingHoursSlider.highValueProperty().addListener((observable, oldValue, newValue) ->
                    openingHoursSlider.setHighValue((newValue.intValue() / 30) * 30));
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
     * .
     * Creates and assigns a Spinner Value Factory to the max bikes spinner which restricts the
     * minimum, maximum, initial value and step size
     */
    private void setMaxBikesSpinnerValueFactory() {
        try {
            // create new value factory
            SpinnerValueFactory<Integer> factory = new SpinnerValueFactory.IntegerSpinnerValueFactory(
                    0, Integer.MAX_VALUE, 0, 1);
            // set the factory
            maxBikesField.setValueFactory(factory);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOkClicked(ActionEvent event) {
        if (isInputValid()) {
            emptyBuilding();
            StringConverter<Number> converter = getRangeSliderConverter();
            building.setBuildingName(buildingNameField.getText());
            building.setBuildingMaxBikes(maxBikesField.getValue());
            building.setBuildingAddress(buildingAddressField.getText());
            building.setOpeningTime(converter.toString(openingHoursSlider.getLowValue()));
            // if building closes at 12am it should be put in the database as 11:59pm
            if (openingHoursSlider.getHighValue() == 1440) {
                building.setClosingTime("23:59");
            } else {
                building.setClosingTime(converter.toString(openingHoursSlider.getHighValue()));
            }
            this.dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancelClicked(ActionEvent event) {
        building = null;
        this.dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (buildingNameField.getText().equals("")) {
            errorMessage += "No valid building name!\n";
        }
        if (buildingAddressField.getText().equals("")) {
            errorMessage += "No valid building address!\n";
        }
        if (openingHoursSlider.getLowValue() == openingHoursSlider.getHighValue()) {
            errorMessage += "No valid opening hours!\n";
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

}
