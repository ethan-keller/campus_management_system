package nl.tudelft.oopp.demo.controllers;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.views.RoomView;

import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ReserveAndRentController implements Initializable {
    @FXML
    private VBox roomDetailsVbox;
    @FXML
    private Text name;
    @FXML
    private Text startingTime;
    @FXML
    private Text endingTime;
    @FXML
    private ImageView image;
    @FXML
    private Text date;
    @FXML
    private Text food;
    @FXML
    private VBox reservationVbox;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Text dateError;
    @FXML
    private ComboBox<StringProperty> ComboBuilding;
    @FXML
    private Text BuildingError;
    @FXML
    private Text BuildingError1;
    @FXML
    private Spinner<StringProperty> spinner;
    @FXML
    private Text BikesUnselectedError;
    @FXML
    private Button reserveBike;

    public static String RoomDate;
    public static String startTime;
    public static String endTime;


    @FXML
    void backButtonClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        RoomView rv = new RoomView();
        rv.start(stage);
    }


    @FXML
    public void initialize(URL location, ResourceBundle resources){


        date.setText(RoomDate);
        startingTime.setText(startTime);
        endingTime.setText(endTime);


    }

    public int isInputValid(){
        if(datePicker.getValue()==null){
            return 1;
        }
        if(ComboBuilding.getSelectionModel().getSelectedItem() == null){
            return 2;
        }
        else{
            return 3;
        }
    }

    @FXML
    private void reserveNowClicked(ActionEvent event) throws IOException {
        if(isInputValid()==1){
            dateError.setVisible(true);
        }
        if(isInputValid()==2){
            BuildingError.setVisible(true);
        }
        else{
            System.out.println("ferfe");
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
                            if (item.isBefore(LocalDate.now()) || item.getDayOfWeek() == DayOfWeek.SATURDAY || item.getDayOfWeek() == DayOfWeek.SUNDAY) {
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
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
