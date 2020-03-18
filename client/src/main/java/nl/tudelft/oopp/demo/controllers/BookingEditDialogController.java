package nl.tudelft.oopp.demo.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Reservation;
import nl.tudelft.oopp.demo.entities.Room;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class BookingEditDialogController {

    @FXML
    private ComboBox<Building> bookingBuildingComboBox;

    @FXML
    private ComboBox<Room> bookingRoomComboBox;

    @FXML
    private DatePicker bookingDate;

    @FXML
    private ComboBox<String> bookingStarting_time;

    @FXML
    private ComboBox<String> bookingEnding_time;

    private ObservableList<Building> olb;

    private ObservableList<Room> olr;

    private ObservableList<String> sTime;

    private ObservableList<String> eTime;

    public static Reservation reservation;

    private Stage dialogStage;

    public BookingEditDialogController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        try {

            // Initialize and add listener to the building combobox
            olb = Building.getBuildingData();
            bookingBuildingComboBox.setItems(olb);
            this.setBookingBuildingComboBoxConverter(olb);
            bookingBuildingComboBox.valueProperty().addListener(((observable, oldValue, newValue) -> {
                buildingSelected(newValue);
            }));

            // Initialize the room combobox
            olr = Room.getRoomData();
            bookingRoomComboBox.setItems(olr);
            this.setBookingRoomComboBoxConverter(olr);

            // Configure the string converters and custom properties (like disabling some dates in the datePicker)
            configureDatePicker();

            // Initialize and add listener to the staring time combobox
            sTime = FXCollections.observableArrayList("08:00:00","09:00:00", "10:00:00", "11:00:00", "12:00:00", "13:00:00", "14:00:00", "15:00:00",
                    "16:00:00", "17:00:00", "18:00:00", "19:00:00", "20:00:00", "21:00:00", "22:00:00", "23:00:00");
            bookingStarting_time.setItems(sTime);
            bookingStarting_time.valueProperty().addListener(((observable, oldValue, newValue) -> {
                starting_timeSelected(newValue);
            }));

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Set the building combobox converter
     * @param olb
     */
    public void setBookingBuildingComboBoxConverter(ObservableList<Building> olb){
        StringConverter<Building> converter = new StringConverter<Building>() {
            @Override
            public String toString(Building object) {
                if (object == null) return "";
                else return object.getBuildingName().get();
            }

            @Override
            public Building fromString(String id) {
                return olb.stream().filter(x -> String.valueOf(x.getBuildingId()).equals(id)).collect(Collectors.toList()).get(0);
            }
        };
        bookingBuildingComboBox.setConverter(converter);
    }

    /**
     * Set the room combobox converter
     * @param olr
     */
    public void setBookingRoomComboBoxConverter(ObservableList<Room> olr){
        StringConverter<Room> converter = new StringConverter<Room>() {
            @Override
            public String toString(Room object) {
                if (object == null) return "";
                else return object.getRoomName().get();
            }

            @Override
            public Room fromString(String id) {
                return olr.stream().filter(x -> String.valueOf(x.getRoomId()).equals(id)).collect(Collectors.toList()).get(0);
            }
        };
        bookingRoomComboBox.setConverter(converter);
    }

    /**
     * Called when a building is selected
     * The room combobox only shows the rooms of the selected building
     */
    public void buildingSelected(Building newBuilding) {
        if(bookingBuildingComboBox.getValue() != null){
            //Get all the rooms
            olr = Room.getRoomData();
            //Create a list of rooms only belongs to the selected building
            List<Room> filteredRooms = olr.stream().filter(x -> x.getRoomBuilding().get() == newBuilding.getBuildingId().get()).collect(Collectors.toList());
            olr.clear();
            bookingRoomComboBox.setItems(olr);
            //Add the filtered rooms to the observable list
            for(Room r: filteredRooms){
                olr.add(r);
            }
            bookingRoomComboBox.setItems(olr);
        }
    }

    /**
     * Called when a starting time is selected
     * Initialize the ending time combobox
     * The earliest time in the ending box should be one hour later than starting time
     */
    public void starting_timeSelected(String newSt) {
        //Initialize the ending time combobox with all time slot
        eTime = FXCollections.observableArrayList("09:00:00", "10:00:00", "11:00:00", "12:00:00", "13:00:00", "14:00:00", "15:00:00", "16:00:00",
                "17:00:00", "18:00:00", "19:00:00", "20:00:00", "21:00:00", "22:00:00", "23:00:00", "24:00:00");
        //Check if a starting time is selected
        if(bookingStarting_time.getValue() != null) {
            int indexSt = sTime.indexOf(bookingStarting_time.getValue());
            //Remove the time slot <= the selected starting time plus one hour.
            eTime.remove(0, indexSt);
            bookingEnding_time.setItems(eTime);
        }
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
            bookingDate.setDayCellFactory(dayCellFactory);
            // converter to convert value to String and vice versa
            StringConverter<LocalDate> converter = getDatePickerConverter();
            // set the converter
            bookingDate.setConverter(converter);
        } catch (Exception e){
            e.printStackTrace();
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
     * Create a new reservation when called
     */
    private static void emptyReservation(){
        reservation = new Reservation();
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
            reservation.setUsername(AdminManageUserViewController.currentSelectedUser.getUsername().get());
            reservation.setRoom(this.bookingRoomComboBox.getSelectionModel().getSelectedItem().getRoomId().get());
            reservation.setDate(this.bookingDate.getValue().toString());
            reservation.setStarting_time(this.bookingStarting_time.getValue());
            reservation.setEnding_time(this.bookingEnding_time.getValue());
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
        reservation = null;
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

        if (bookingRoomComboBox.getSelectionModel().getSelectedIndex() < 0) {
            errorMessage += "No valid room selected!\n";
        }
        if (bookingDate.getValue() == null) {
            errorMessage += "No valid date selected!\n";
        }
        if (bookingStarting_time.getValue() == null) {
            errorMessage += "No valid starting time selected!\n";
        }
        if (bookingEnding_time.getValue() == null) {
            errorMessage += "No valid ending time selected!\n";
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
     * Creates a StringConverter that converts the selected value to a usable Date (in String format).
     *
     * @return a StringConverter object
     */
    private StringConverter<LocalDate> getDatePickerConverter() {
        try {
            return new StringConverter<LocalDate>() {
                // set the wanted pattern (format)
                String pattern = "yyyy-MM-dd";
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

                {
                    // set placeholder text for the datePicker
                    bookingDate.setPromptText(pattern.toLowerCase());
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
