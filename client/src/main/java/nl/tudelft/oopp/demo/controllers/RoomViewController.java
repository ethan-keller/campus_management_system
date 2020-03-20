package nl.tudelft.oopp.demo.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import nl.tudelft.oopp.demo.communication.GeneralMethods;
import nl.tudelft.oopp.demo.communication.ReservationServerCommunication;
import nl.tudelft.oopp.demo.communication.user.CurrentUserManager;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.views.*;
import org.controlsfx.control.RangeSlider;

import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;


/**
 * Controller class for the Room view (JavaFX)
 */
public class RoomViewController implements Initializable {
    /**
     * These are the FXML elements that inject some functionality into the application.
     */
    @FXML
    private Text name;
    @FXML
    private Text capacity;
    @FXML
    private Text building;
    @FXML
    private Text teacher_only;
    @FXML
    private Text type;
    @FXML
    private ImageView image;
    @FXML
    private Text description;
    @FXML
    private ComboBox<String> food_choice;
    @FXML
    private Button bookButton;
    @FXML
    private Button bookBikesButton;
    @FXML
    private DatePicker datePicker;
    @FXML
    private VBox reservationVbox;
    @FXML
    private VBox roomDetailsVbox;
    @FXML
    private Text startTime;
    @FXML
    private Text endTime;
    @FXML
    private Text dateError;
    @FXML
    private Text foodError;
    @FXML
    private Text teacherOnlyError;
    // double thumb slider
    private RangeSlider timeSlotSlider;

    // current room to show info about
    private static Room currentRoom;
    public static int currentRoomId;

    // current Stage
    public static Stage thisStage;

    /**
     * Method that gets called before everything (mostly to initialize nodes etc.)
     * JavaFX standard.
     *
     * @param location
     * @param resources
     */
    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            // initialize the Room object that contains the info about this room
            currentRoom = Room.getRoomById(currentRoomId);

            // configure the string converters and custom properties (like disabling some dates in the datePicker)
            configureDatePicker();
            configureRangeSlider();

            // make sure errors are not visible
            dateError.setVisible(false);
            foodError.setVisible(false);

            // if user is a student and the room is teacher only => disable book button and show error
            if (CurrentUserManager.getType() == 2 && currentRoom.getTeacher_only().get()) {
                teacherOnlyError.setVisible(true);
                bookButton.setDisable(true);
            } else {
                teacherOnlyError.setVisible(false);
            }

            // adjust layout
            changeWidthConstraints(thisStage.getWidth());
            image.setFitHeight(100000);

            // listener that adjusts layout when width of stage changes
            thisStage.widthProperty().addListener((obs, oldVal, newVal) -> {
                changeWidthConstraints(newVal);
            });

            // TODO: adjust the options of this comboBox based on the availabale food dishes
            ObservableList<String> FoodList = FXCollections.observableArrayList();
            FoodList.addAll("Ham Sandwich", "Cheese Sandwich", "Pasta", "No Food");
            food_choice.setItems(FoodList);

            // set text info about the room
            name.setText("Name: " + currentRoom.getRoomName().get());
            capacity.setText("Capacity: " + currentRoom.getRoomCapacity().get());
            building.setText("Building: " + Building.getBuildingById(currentRoom.getRoomBuilding().get()).getBuildingName().get());
            teacher_only.setText("Teachers only: " + (currentRoom.getTeacher_only().get() ? "yes" : "no"));
            type.setText("Type: " + currentRoom.getRoomType().get());
            description.setText("Description:\n" + currentRoom.getRoomDescription().get());
            // TODO: change to room's image
            image.setImage(new Image("images/placeholder.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method changes the width of some JavaFX nodes based on the width of the stage.
     * It's the callback method for the stage width listener.
     *
     * @param newWidth The new width of the current stage
     */
    private void changeWidthConstraints(Number newWidth) {
        try {
            // set the width of some nodes based on the calculated ratio between their width and the stages width
            image.setFitWidth((newWidth.doubleValue() - 188) / 1.41550696);
            reservationVbox.setPrefWidth((newWidth.doubleValue() - 188) / 3.3969);
            timeSlotSlider.setMaxWidth((newWidth.doubleValue() - 188) / 5);
        } catch (Exception e){
            e.printStackTrace();
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

            // get and set the StringConverter to show hh:mm format
            StringConverter<Number> converter = getRangeSliderConverter();
            timeSlotSlider.setLabelFormatter(converter);

            // add listeners to show the current thumb values in separate Text objects
            configureRangeSliderListeners(converter);

            // initialize the Text objects with the current values of the thumbs
            startTime.setText(converter.toString(timeSlotSlider.getLowValue()));
            endTime.setText(converter.toString(timeSlotSlider.getHighValue()));

            // inject the RangeSlider in the JavaFX layout
            reservationVbox.getChildren().add(2, timeSlotSlider);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
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

            // listeners that make sure the user can only select intervals of 1 hour
            timeSlotSlider.lowValueProperty().addListener((observable, oldValue, newValue) ->
                    timeSlotSlider.setLowValue((newValue.intValue() / 60) * 60));
            timeSlotSlider.highValueProperty().addListener((observable, oldValue, newValue) ->
                    timeSlotSlider.setHighValue((newValue.intValue() / 60) * 60));
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

    /**
     * Creates a StringConverter that converts the selected value to an actual time (in String format)
     *
     * @return a StringConverter object
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
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Method that executes when the backButton is clicked. It returns to the searchview.
     *
     * @param event ActionEvent.
     */
    @FXML
    private void backButtonClicked(ActionEvent event) {
        try {
            // load the searchview
            SearchView sv = new SearchView();
            sv.start(thisStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // TODO: add try catch everywhere

    /**
     * Method that executes when book button is clicked. It checks if fields are correctly filled.
     *
     * @param event ActionEvent
     */
    @FXML
    private void bookClicked(ActionEvent event) {
        try {
            // TODO: add food selection
            String selectedDate;
            String selectedStartTime;
            String selectedEndTime;

            // input is valid, assign corresponding values
            if (isInputValid()) {
                selectedDate = getDatePickerConverter().toString(datePicker.getValue());
                selectedStartTime = getRangeSliderConverter().toString(timeSlotSlider.getLowValue());
                selectedEndTime = getRangeSliderConverter().toString(timeSlotSlider.getHighValue());

                // if user confirms booking, reservations is sent to server
                if (confirmBooking(selectedDate, selectedStartTime, selectedEndTime)) {
                    // send new reservation to server
                    ReservationServerCommunication.createReservation(CurrentUserManager.getUsername(), currentRoomId, selectedDate, selectedStartTime, selectedEndTime);
                    // create confirmation Alert
                    Alert alert = GeneralMethods.createAlert("Room booked", "You successfully booked this room!", ((Node) event.getSource()).getScene().getWindow(), Alert.AlertType.CONFIRMATION);
                    alert.showAndWait();
                }
            } else {
                // create error Alert
                Alert alert = GeneralMethods.createAlert("fields incomplete", "Please fill in all the fields", ((Node) event.getSource()).getScene().getWindow(), Alert.AlertType.ERROR);
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
            // create error Alert
            Alert alert = GeneralMethods.createAlert("Something went wrong", "Sorry, something went wrong on our end. We're fixing it now!", ((Node) event.getSource()).getScene().getWindow(), Alert.AlertType.ERROR);
            alert.showAndWait();
        }
    }

    /**
     * Loads a little pop up stage that shows all information about the booking and asks for confirmation.
     *
     * @param date      day of the booking
     * @param startTime starting time of the booking
     * @param endTime   ending time of the booking
     * @return true if the user confirms, false otherwise
     */
    private boolean confirmBooking(String date, String startTime, String endTime) {
        try {
            // set all fields to the current reservation details
            ReservationConfirmationViewController.room = currentRoom;
            ReservationConfirmationViewController.date = date;
            ReservationConfirmationViewController.startTime = startTime;
            ReservationConfirmationViewController.endTime = endTime;
            // load confirmation pop up stage
            ReservationConfirmationView rcv = new ReservationConfirmationView();
            rcv.start(thisStage);
            // return true if confirmed, false otherwise
            return ReservationConfirmationViewController.confirmed;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Checks if fields are correctly filled and shows errors and warnings if
     * the user forgot some fields.
     *
     * @return true if everything is filled in correctly, false otherwise
     */
    public boolean isInputValid() {
        try {
            // true if there are errors, false otherwise
            boolean errors = false;

            // clear error messages
            dateError.setVisible(false);
            foodError.setVisible(false);

            // set error messages if necessary
            if (datePicker.getValue() == null) {
                dateError.setVisible(true);
                errors = true;
            }
            if (food_choice.getSelectionModel().getSelectedItem() == null) {
                foodError.setVisible(true);
                errors = true;
            }

            // check if errors were triggered
            if (errors) return false;
            else return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public void BookBikeClicked(ActionEvent event) throws IOException {


        String selectedDate = getDatePickerConverter().toString(datePicker.getValue());
        String selectedStartTime = getRangeSliderConverter().toString(timeSlotSlider.getLowValue());
        String selectedEndTime = getRangeSliderConverter().toString(timeSlotSlider.getHighValue());
        String selectedFood=food_choice.getValue();

        ReserveAndRentController.RoomDate= selectedDate;
        ReserveAndRentController.startTime= selectedStartTime;
        ReserveAndRentController.endTime= selectedEndTime;
        ReserveAndRentController.RoomFood=selectedFood;

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        ReserveAndRentView rarv = new ReserveAndRentView();
        rarv.start(stage);

    }


}

