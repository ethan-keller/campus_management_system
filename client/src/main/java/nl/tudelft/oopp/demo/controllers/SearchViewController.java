package nl.tudelft.oopp.demo.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.views.RoomView;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Controller class for SearchView (JavaFX)
 */
public class SearchViewController implements Initializable {

    @FXML
    private DatePicker datePicker;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox cardHolder;
    @FXML
    private ComboBox<Building> BuildingComboBox;
    @FXML
    private RadioButton yesCheckBoxTeacherOnly;
    @FXML
    private RadioButton noCheckBoxTeacherOnly;
    @FXML
    private RadioButton yesCheckBoxFood;
    @FXML
    private RadioButton noCheckBoxFood;
    @FXML
    private ComboBox<String> CapacityComboBox;
    @FXML
    private Button clearFilters;
    @FXML
    private Button BookingHistoryButton;
    @FXML
    private TextField searchBar;
    @FXML
    private ComboBox<String> BikesAvailable;

    private ObservableList<String> capacityList;
    private ObservableList<Building> buildingList;
    private ObservableList<String> bikeList;

    public SearchViewController() {
    }

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
            // assign lists to the initialized ObservableLists
            capacityList = FXCollections.observableArrayList();
            buildingList = Building.getBuildingData();
            bikeList = FXCollections.observableArrayList();

            // the comboBox only shows 6 rows (more => scroll)
            BuildingComboBox.setVisibleRowCount(6);

            datePicker.setConverter(getDatePickerStringConverter());
            datePicker.setDayCellFactory(getDayCellFactory());

            // assign values to the observable lists
            capacityList.addAll("1-5", "5-10", "10-20", "20+");
            BuildingComboBox.setItems(buildingList);
            BuildingComboBox.setConverter(getBuildingComboBoxConverter());
            bikeList.addAll("1-5", "5-10", "10-20", "20+");

            // populating the choicebox
            CapacityComboBox.setItems(capacityList);
            BuildingComboBox.setItems(buildingList);
            BikesAvailable.setItems(bikeList);

            // get all rooms from server
            ObservableList<Room> roomList = Room.getRoomData();
            // create a 'card' showing some information of the room, for every room
            for (Room r : roomList) {
                cardHolder.getChildren().add(createRoomCard(r));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create cellFactory for the datePicker that disables all days before today and weekend days.
     * It also marks them red to make sure the user understands why they are disabled.
     *
     * @return a CallBack object used to set the dayCellFactory for the datePicker
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Creates a StringConverter that converts the selected value to a usable Date (in String format).
     *
     * @return a StringConverter object
     */
    private StringConverter<LocalDate> getDatePickerStringConverter() {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Create a StringConverter that shows the name of the building for each building in the comboBox.
     *
     * @return StringConverter
     */
    private StringConverter<Building> getBuildingComboBoxConverter() {
        try {
            StringConverter<Building> converter = new StringConverter<Building>() {
                @Override
                public String toString(Building object) {
                    if (object == null) return "";
                    return object.getBuildingName().get();
                }

                @Override
                public Building fromString(String id) {
                    return buildingList.stream().filter(x -> String.valueOf(x.getBuildingId()).equals(id)).collect(Collectors.toList()).get(0);
                }
            };
            return converter;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Creates a new 'card' (HBox) which contains some information about the room
     *
     * @param r The Room that we have to show information from
     * @return HBox which is the final 'card'
     */
    private HBox createRoomCard(Room r) {
        try {
            // initialize javafx components
            HBox newCard = new HBox();
            ImageView image = new ImageView();
            VBox room_info = new VBox();
            Text room_title = new Text();
            Text room_capacity = new Text();
            Text room_description = new Text();
            Text roomId = new Text();

            // loading image from URL + setting size & properties
            Image img = new Image("images/placeholder.png");
            image.setImage(img);
            image.setPreserveRatio(true);
            image.setPickOnBounds(true);
            image.setFitWidth(300);

            // adding image margin
            newCard.setMargin(image, new Insets(10, 5, 10, 10));

            /* set the roomId visibility to false such that it is not visible for the user but still useful to
               get the specific room information later in the RoomView
             */
            roomId.setText(String.valueOf(r.getRoomId().get()));
            roomId.setVisible(false);

            // setting title and text margin (+ properties)
            room_title.setText(r.getRoomName().get());
            room_title.setWrappingWidth(200);
            room_title.setFont(Font.font("System", FontWeight.BOLD, 18));
            room_title.setStyle("-fx-fill: #0ebaf8;");
            room_info.setMargin(room_title, new Insets(10, 10, 10, 15));

            // setting capacity and text margin (+ properties)
            room_capacity.setText("Capacity: " + r.getRoomCapacity().get());
            room_capacity.setWrappingWidth(200);
            room_capacity.setFont(Font.font("System", 14));
            room_info.setMargin(room_capacity, new Insets(0, 0, 5, 15));

            // setting description and text margin (+ properties)
            room_description.setText("Description: " + r.getRoomDescription().get());
            room_description.setWrappingWidth(310);
            room_description.setFont(Font.font("System", 14));
            room_info.setMargin(room_description, new Insets(0, 0, 0, 15));

            // setting 'text box' size
            room_info.setPrefSize(354, 378);

            // adding components to their corresponding parent
            room_info.getChildren().add(roomId);
            room_info.getChildren().add(room_title);
            room_info.getChildren().add(room_capacity);
            room_info.getChildren().add(room_description);
            newCard.getChildren().add(image);
            newCard.getChildren().add(room_info);

            // setting size
            newCard.setPrefWidth(688);
            newCard.setPrefHeight(145);

            // add mouse click listener to individual cards
            newCard.setOnMouseClicked(event -> {
                try {
                    cardClicked(event);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            return newCard;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * When a card gets clicked, the RoomView gets loaded with all the corresponding room information
     *
     * @param event MouseEvent
     */
    private void cardClicked(MouseEvent event) {
        try {
            // get current Stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // get the card that was clicked
            HBox selectedCard = (HBox) event.getSource();

            // get the VBox that contains the 'invisible' room id
            VBox cardInfo = (VBox) selectedCard.getChildren().get(1);

            // get room id from that VBox and parse to int
            int roomId = Integer.parseInt(((Text) cardInfo.getChildren().get(0)).getText());

            // set the currentRoomID such that the RoomView controller knows which room to show information from
            RoomViewController.currentRoomId = roomId;

            // load RoomView
            RoomView rv = new RoomView();
            rv.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Redirects to bookingHistory of the current user to see, edit or cancel bookings
     *
     * @param event ActionEvent to get current Stage
     */
    @FXML
    private void BookingHistoryClicked(ActionEvent event) {
        // get current Stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // TODO: redirect to bookingHistory
    }

    /**
     * Clears all the filters and sets them back to 'empty'
     *
     * @param event ActionEvent
     */
    @FXML
    private void clearFiltersClicked(ActionEvent event) {
        try {
            // clear every filter
            datePicker.setValue(null);
            BuildingComboBox.setValue(null);
            yesCheckBoxFood.setSelected(false);
            noCheckBoxFood.setSelected(false);
            yesCheckBoxTeacherOnly.setSelected(false);
            noCheckBoxTeacherOnly.setSelected(false);
            CapacityComboBox.setValue(null);
            BikesAvailable.setValue(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
