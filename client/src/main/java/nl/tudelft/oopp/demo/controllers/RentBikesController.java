package nl.tudelft.oopp.demo.controllers;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import nl.tudelft.oopp.demo.communication.GeneralMethods;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.views.SearchView;

import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class RentBikesController implements Initializable {
    @FXML
    private Text name;
    @FXML
    private Text capacity;
    @FXML
    private ImageView image;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Text dateError;
    @FXML
    private ComboBox<String> ComboBuilding;
    @FXML
    private Text BuildingError;
    @FXML
    private Spinner<Integer> spinner;
    private static Room currentRoom;
    public static int currentRoomId;

    /**
     * deal with the button clicking action
     */
    @FXML
    private void backButtonClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        SearchView sv = new SearchView();
        sv.start(stage);
    }

    /**
     *Sets default values to populate the options
     */
    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources){
        ObservableList<Building> buildingList = Building.getBuildingData();

        ObservableList<String> bList;
        bList = FXCollections.observableArrayList();
        for (Building b : buildingList) {
            bList.add(b.toString());
        }
        ComboBuilding.setItems(bList);

        dateError.setVisible(false);
        BuildingError.setVisible(false);

        configureDatePicker();

        currentRoom = Room.getRoomById(currentRoomId);


        //Building b = Building.getBuildingById(currentRoom.getRoomBuilding().get());

        //capacity.setText("Available bikes: "+b.getBuildingBike_count());

    }

    /**
     * Checks whether if all the fields were filled in
     */
    public int isInputValid(){
        if(datePicker.getValue()==null&&ComboBuilding.getSelectionModel().getSelectedItem() == null){
            return 1;
        }
        else if(ComboBuilding.getSelectionModel().getSelectedItem() == null){
            return 2;
        }
        else if(datePicker.getValue()==null){
            return 3;
        }
        else{
            return 4;
        }
    }

    /**
     * Deals with reserve now button clicks
     */
    @FXML
    private void reserveNowClicked(ActionEvent event) {

            if(isInputValid()==1){
                dateError.setVisible(true);
                BuildingError.setVisible(true);
            }
            if(isInputValid()==2){
                BuildingError.setVisible(true);
            }
            if(isInputValid()==3){
                dateError.setVisible(true);
            }
            if(isInputValid()==4){

                String selectedDate = Objects.requireNonNull(getDatePickerConverter()).toString(datePicker.getValue());
                int selectedBike = spinner.getValue();
                String selectedBuilding = ComboBuilding.getValue();

                Alert alert = GeneralMethods.createAlert("Your Bike Reservation", "Make reservation for "+selectedBike+" bikes" +
                        " from "+selectedBuilding+" on "+selectedDate+"?" , ((Node) event.getSource()).getScene().getWindow(), Alert.AlertType.CONFIRMATION);
                assert alert != null;
                alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                alert.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);

                Optional<ButtonType> result = alert.showAndWait();
                if(result.isEmpty()){
                }
                else if(result.get() == ButtonType.OK){
                    //Return values to the server
                }
                else if(result.get()==ButtonType.CANCEL){
                }
            }
        }



    /**
     * Create cellFactory for the datePicker that disables all days before today and weekend days.
     * It also marks them red to make sure the user understands why they are disabled.
     *
     * @return a CallBack object used to set the dayCellFactory for the datePicker in {@link #configureDatePicker()}
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

    /**
     * Methods that sets the dayCellFactory made in {@link #getDayCellFactory()}
     * and the StringConverter made in {@link #getDatePickerConverter()}
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
        } catch (Exception e){
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
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }



}
