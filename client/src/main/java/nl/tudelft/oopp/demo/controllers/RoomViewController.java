package nl.tudelft.oopp.demo.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import nl.tudelft.oopp.demo.communication.AdminManageServerCommunication;
import nl.tudelft.oopp.demo.communication.GeneralMethods;
import nl.tudelft.oopp.demo.communication.user.CurrentUserManager;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.views.ReservationConfirmationView;
import nl.tudelft.oopp.demo.views.SearchView;
import org.controlsfx.control.RangeSlider;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class RoomViewController implements Initializable {


    public Text name;
    public Text capacity;
    public Text building;
    public Text teacher_only;
    public Text type;
    public ImageView image;
    public Text description;
    public ComboBox food_choice;
    public Button bookButton;
    public Button bookBikesButton;
    public DatePicker datePicker;
    public VBox reservationVbox;
    public VBox roomDetailsVbox;
    public Text startTime;
    public Text endTime;
    public Text dateError;
    public Text foodError;
    public Text teacherOnlyError;
    public RangeSlider timeSlotSlider;

    private static Room currentRoom;
    public static int currentRoomId;
    public static Stage thisStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            currentRoom = Room.getRoomById(currentRoomId);

            configureDatePicker();
            configureRangeSlider();

            dateError.setVisible(false);
            foodError.setVisible(false);
            if(CurrentUserManager.getType() == 2 && currentRoom.getTeacher_only().get()){
                teacherOnlyError.setVisible(true);
                bookButton.setDisable(true);
            } else {
                teacherOnlyError.setVisible(false);
            }

            changeWidthConstraints(thisStage.getWidth());
            image.setFitHeight(100000);

            thisStage.widthProperty().addListener((obs, oldVal, newVal) -> {
                changeWidthConstraints(newVal);
            });

            // TODO: adjust the options of this comboBox based on the availabale food dishes
            ObservableList<String> FoodList = FXCollections.observableArrayList();
            FoodList.addAll("Ham Sandwich", "Cheese Sandwich", "Pasta", "No Food");
            food_choice.setItems(FoodList);

            name.setText("Name: " + currentRoom.getRoomName().get());
            capacity.setText("Capacity: " + currentRoom.getRoomCapacity().get());
            building.setText("Building: " + Building.getBuildingById(currentRoom.getRoomBuilding().get()).getBuildingName().get());
            teacher_only.setText("Teachers only: " + (currentRoom.getTeacher_only().get() ? "yes" : "no"));
            type.setText("Type: " + currentRoom.getRoomType().get());
            description.setText("Description:\n" + currentRoom.getRoomDescription().get());
            // TODO: change to room's image
            image.setImage(new Image("images/placeholder.png"));



        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void changeWidthConstraints(Number newWidth){
        image.setFitWidth((newWidth.doubleValue() - 188)/1.41550696);
        reservationVbox.setPrefWidth((newWidth.doubleValue() - 188)/3.3969);
        timeSlotSlider.setMaxWidth((newWidth.doubleValue() - 188)/5);
    }

    public void configureDatePicker(){
        // Factory to create Cell of DatePicker
        Callback<DatePicker, DateCell> dayCellFactory= this.getDayCellFactory();
        datePicker.setDayCellFactory(dayCellFactory);
        StringConverter<LocalDate> converter = getDatePickerConverter();
        datePicker.setConverter(converter);
    }

    // Factory to create Cell of DatePicker
    private Callback<DatePicker, DateCell> getDayCellFactory() {

        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {

            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        // Disable all days before the current day
                        if (item.isBefore(LocalDate.now())) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                    }
                };
            }
        };
        return dayCellFactory;
    }

    public void configureRangeSlider(){
        timeSlotSlider = new RangeSlider(480, 1440, 600, 1100);
        timeSlotSlider.setMinWidth(100);
        timeSlotSlider.setMaxWidth(200);
        timeSlotSlider.setShowTickLabels(true);
        timeSlotSlider.setShowTickMarks(true);
        timeSlotSlider.setMajorTickUnit(450);
        timeSlotSlider.setBlockIncrement(400);

        StringConverter<Number> converter = getRangeSliderConverter();

        timeSlotSlider.setLabelFormatter(converter);

        configureRangeSliderListeners(converter);


        startTime.setText(converter.toString(timeSlotSlider.getLowValue()));
        endTime.setText(converter.toString(timeSlotSlider.getHighValue()));

        reservationVbox.getChildren().add(2, timeSlotSlider);
    }

    private void configureRangeSliderListeners(StringConverter<Number> converter) {
        timeSlotSlider.highValueProperty().addListener((observable, oldValue, newValue) ->
                endTime.setText(converter.toString(newValue)));
        timeSlotSlider.lowValueProperty().addListener((observable, oldValue, newValue) ->
                startTime.setText(converter.toString(newValue)));

        timeSlotSlider.lowValueProperty().addListener((observable, oldValue, newValue) ->
                timeSlotSlider.setLowValue((newValue.intValue()/10)*10));

        timeSlotSlider.highValueProperty().addListener((observable, oldValue, newValue) ->
                timeSlotSlider.setHighValue((newValue.intValue()/10)*10));
    }

    private StringConverter<LocalDate> getDatePickerConverter(){
        return new StringConverter<LocalDate>() {
            String pattern = "yyyy-MM-dd";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            {
                datePicker.setPromptText(pattern.toLowerCase());
            }

            @Override public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
    }


    private StringConverter<Number> getRangeSliderConverter(){
        return new StringConverter<Number>() {
            @Override
            public String toString(Number n) {
                long minutes = n.longValue();
                long hours = TimeUnit.MINUTES.toHours(minutes);
                long remainingMinutes = minutes - TimeUnit.HOURS.toMinutes(hours);
                return String.format("%02d", hours) + ":" + String.format("%02d", remainingMinutes);
            }

            @Override
            public Number fromString(String string) {
                return null;
            }
        };
    }


    public void backButtonClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        SearchView sv = new SearchView();
        sv.start(stage);
    }

    // TODO: add try catch everywhere
    public void bookClicked(ActionEvent event) {
        try {
            String pattern = "yyyy-MM-dd";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            // TODO: add food selection
            String selectedDate;
            String selectedStartTime;
            String selectedEndTime;

            if (isInputValid()) {
                selectedDate = getDatePickerConverter().toString(datePicker.getValue());
                selectedStartTime = getRangeSliderConverter().toString(timeSlotSlider.getLowValue());
                selectedEndTime = getRangeSliderConverter().toString(timeSlotSlider.getHighValue());

                if(confirmBooking(selectedDate, selectedStartTime, selectedEndTime)){
                    AdminManageServerCommunication.createReservation(CurrentUserManager.getUsername(), currentRoomId, selectedDate, selectedStartTime, selectedEndTime);
                    Alert alert = GeneralMethods.createAlert("Room booked", "You successfully booked this room!", ((Node) event.getSource()).getScene().getWindow(), Alert.AlertType.CONFIRMATION);
                    alert.showAndWait();
                }
            } else {
                Alert alert = GeneralMethods.createAlert("fields incomplete", "Please fill in all the fields", ((Node) event.getSource()).getScene().getWindow(), Alert.AlertType.ERROR);
                alert.showAndWait();
            }
        } catch (Exception e){
            e.printStackTrace();
            Alert alert = GeneralMethods.createAlert("Something went wrong", "Sorry, something went wrong on our end. We're fixing it now!", ((Node) event.getSource()).getScene().getWindow(), Alert.AlertType.ERROR);
            alert.showAndWait();
        }
    }

    private boolean confirmBooking(String date, String startTime, String endTime){
        ReservationConfirmationViewController.room = currentRoom;
        ReservationConfirmationViewController.date = date;
        ReservationConfirmationViewController.startTime = startTime;
        ReservationConfirmationViewController.endTime = endTime;
        ReservationConfirmationView rcv = new ReservationConfirmationView();
        rcv.start(thisStage);
        return ReservationConfirmationViewController.confirmed;
    }


    public boolean isInputValid(){
        boolean errors = false;

        // clear error messages
        dateError.setVisible(false);
        foodError.setVisible(false);

        // set error messages if necessary
        if(datePicker.getValue() == null) {
            dateError.setVisible(true);
            errors = true;
        }
        if(food_choice.getSelectionModel().getSelectedItem() == null) {
            foodError.setVisible(true);
            errors = true;
        }

        // check if errors were triggered
        if(errors) return false;
        else return true;
    }
}

