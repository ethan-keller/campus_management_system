package nl.tudelft.oopp.demo.controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import nl.tudelft.oopp.demo.communication.GeneralMethods;
import nl.tudelft.oopp.demo.entities.Building;
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
    @FXML
    private VBox BuildingBikes;

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

        ObservableList<Building> buildingsList = Building.getBuildingData();
        for(Building b : buildingsList){
            BuildingBikes.getChildren().add(getEachBikes(b));
        }


    }

    private Text getEachBikes(Building b){
        String bName = b.toString();
        int bBikes = b.getBuildingAvailable_bikes().get();

        Text text = new Text();
        text.setText(bName+": "+bBikes);
        text.setFill(Color.WHITE);
        text.setFont(Font.font ("System", 14));

        return text;
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

                if(checkBikeAvailability(selectedBuilding, selectedBike)){
                    Alert alert = GeneralMethods.createAlert("Your Bike Reservation", "Make reservation for "+selectedBike+" bikes" +
                            " from "+selectedBuilding+" on "+selectedDate+"?" , ((Node) event.getSource()).getScene().getWindow(), Alert.AlertType.CONFIRMATION);
                    assert alert != null;
                    alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                    alert.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);

                    Optional<ButtonType> result = alert.showAndWait();
                    if(result.get() == ButtonType.OK){
                        ////ReservationServerCommunication.createReservation(CurrentUserManager.getUsername(), );
                        ////Method not present in Reservation class where it involves the bikes
                        Alert alert2 = GeneralMethods.createAlert("Room booked", "You successfully booked this room!", ((Node) event.getSource()).getScene().getWindow(), Alert.AlertType.CONFIRMATION);
                        alert2.showAndWait();
                    }
                    else if(result.get()==ButtonType.CANCEL){
                    }
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Insufficient Bikes");
                    alert.setContentText("Insufficient Bikes Available. Please check the number of bikes available");
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

    //Checks if there are sufficient bikes in the database
    private Boolean checkBikeAvailability(String buildingName, int num){
        ObservableList<Building> buildingList = Building.getBuildingData();
        Building building= null;
        for (Building b : buildingList) {
            if (b.getBuildingName().get().equals(buildingName)) {
                building = b;
                break;
            }
        }

        assert building != null;
        return building.getBuildingAvailable_bikes().get() - num >= 0;
    }



}
