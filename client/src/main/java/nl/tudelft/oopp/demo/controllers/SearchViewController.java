package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;


import javafx.util.StringConverter;

import nl.tudelft.oopp.demo.views.BookingHistoryView;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.views.CancelBookingView;
import nl.tudelft.oopp.demo.communication.GeneralMethods;
import nl.tudelft.oopp.demo.views.LoginView;
import nl.tudelft.oopp.demo.entities.Reservation;
import nl.tudelft.oopp.demo.entities.Room;

import nl.tudelft.oopp.demo.views.RoomView;

import java.io.UnsupportedEncodingException;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for SearchView (JavaFX)
 */
public class SearchViewController implements Initializable {
    /**
     * These are the FXML elements that inject some functionality into the application.
     */
    @FXML
    private DatePicker datePicker;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox cardHolder;
    @FXML
    private ComboBox<Building> buildingComboBox;
    @FXML
    private RadioButton yesCheckBoxTeacherOnly;
    @FXML
    private RadioButton noCheckBoxTeacherOnly;
    @FXML
    private RadioButton yesCheckBoxFood;
    @FXML
    private RadioButton noCheckBoxFood;
    @FXML
    private ComboBox<String> capacityComboBox;
    @FXML

    private Button clearFilters;
    @FXML
    private Button bookingHistoryButton;
    @FXML
    private TextField searchBar;
    @FXML
    private ComboBox<String> bikesAvailable;

    private List<Building> buildings;
    private List<Room> roomList;
    private ObservableList<Room> rooms;

    @FXML
    private AnchorPane pane;

    // Declaring the observable list for buildings, capacity and bikes
    // to be inserted into the comboBox.
    // This is necessary due to the format of inserting items into a comboBox.
    private ObservableList<String> capacityList;
    private ObservableList<Building> buildingList;
    private ObservableList<String> bikeList;

    private int building;
    private boolean teacherOnly;
    private int capMin;
    private int capMax;

    /**
     * .
     * Default construct of searchView class.
     */
    public SearchViewController() {
    }

    /**
     * .
     * Handles the bookingHistory Button onclick.
     * Redirects the user to the booking history page.
     *
     * @param event is passed
     * @throws IOException is thrown
     */
    public void bookingHistoryButtonClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        BookingHistoryView bookingHistoryView = new BookingHistoryView();
        bookingHistoryView.start(stage);
    }

    /**
     * .
     * Handles the onclick of signOut Button.
     * Redirects the user back to the login page.
     *
     * @param event is passed
     * @throws IOException is thrown
     */
    public void signOutButtonClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        LoginView loginView = new LoginView();
        loginView.start(stage);
    }

    /**
     * .
     * Handles the onclick of cancelBooking Button.
     * Redirects the user to the cancelBooking page.
     *
     * @param event is passed
     * @throws Exception is thrown
     */
    public void cancelBookingClicked(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        CancelBookingView cancelBookingView = new CancelBookingView();
        cancelBookingView.start(stage);
    }


    /**
     * Method that gets called when loading the view.
     * Loads the buildings and rooms from the database
     * sets actions for when a filter is selected.
     * JavaFX standard.
     *
     * @param location  is passed
     * @param resources is passed
     */
    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            pane = new AnchorPane();

            // assign lists to the initialized ObservableLists
            capacityList = FXCollections.observableArrayList();
            buildingList = Building.getBuildingData();
            bikeList = FXCollections.observableArrayList();


            // the comboBox only shows 6 rows (more => scroll)
            buildingComboBox.setVisibleRowCount(6);

            datePicker.setConverter(getDatePickerStringConverter());
            datePicker.setDayCellFactory(getDayCellFactory());

            // assign values to the observable lists
            capacityList.addAll("1-5", "5-10", "10-20", "20+");
            buildingComboBox.setItems(buildingList);
            buildingComboBox.setConverter(getbuildingComboBoxConverter());
            bikeList.addAll("1-5", "5-10", "10-20", "20+");

            // populating the choicebox
            capacityComboBox.setItems(capacityList);
            buildingComboBox.setItems(buildingList);
            bikesAvailable.setItems(bikeList);

            // get all rooms and buildings from server
            rooms = Room.getRoomData();
            buildings = Building.getBuildingData();

            // load all the cards
            loadCards();


        } catch (Exception e) {
            e.printStackTrace();
        }

        // if a new filter is applied or an filter is removed filter again and load the cards again
        buildingComboBox.setOnAction(event -> {
            try {
                loadCards();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // if a new filter is applied or an filter is removed filter again and load the cards again
        capacityComboBox.setOnAction(event -> {
            try {
                loadCards();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // if a new filter is applied or an filter is removed filter again and load the cards again
        yesCheckBoxTeacherOnly.setOnAction(event -> {
            try {
                yesCheckBoxTeacherOnly.setSelected(true);
                loadCards();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // if a new filter is applied or an filter is removed filter again and load the cards again
        noCheckBoxTeacherOnly.setOnAction(event -> {
            try {
                yesCheckBoxTeacherOnly.setSelected(false);
                loadCards();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // if a key is released only the searchbar gets filtered again.
        // the rest stays the same and the list of the rooms of the other filters is used again.
        searchBar.setOnKeyReleased(event -> {
            try {
                searchbarChanges();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // if a new filter is applied or an filter is removed filter again and load the cards again
        datePicker.setOnAction(event -> {
            try {
                loadCards();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Filters the rooms according to the filters selected.
     * Makes a call to getCardsShown() to show the cards on the view.
     *
     * @throws UnsupportedEncodingException when encoding fails.
     */
    public void loadCards() throws UnsupportedEncodingException {
        //load all rooms back in the roomlist to filter again
        roomList = new ArrayList<Room>();
        for (int i = 0; i != rooms.size(); i++) {
            roomList.add(rooms.get(i));
        }

        //Check if there are any filters selected and if so filter the roomlist
        if (buildingComboBox.getValue() != null) {
            building = buildingComboBox.getValue().getBuildingId().getValue();
            roomList = GeneralMethods.filterRoomByBuilding(roomList, building);
        }

        // if the checkbox is selected it filters according to the checkbox.
        if (yesCheckBoxTeacherOnly.isSelected()) {
            roomList = GeneralMethods.filterRoomByTeacher_only(roomList, true);
        }

        // if the checkbox is selected it filters according to the checkbox.
        if (noCheckBoxTeacherOnly.isSelected()) {
            roomList = GeneralMethods.filterRoomByTeacher_only(roomList, false);
        }

        // if the combobox is selected on a value it filters for that value.
        if (capacityComboBox.getValue() != null) {
            String capacity = capacityComboBox.getValue();
            switch (capacity) {
                case "1-5":
                    capMin = 1;
                    capMax = 5;
                    break;
                case "5-10":
                    capMin = 5;
                    capMax = 10;
                    break;
                case "10-20":
                    capMin = 10;
                    capMax = 20;
                    break;
                case "20+":
                    capMin = 20;
                    capMax = 9999;
                    break;

                default:
                    capMin = 0;
                    capMax = 9999;

            }

            roomList = GeneralMethods.filterRoomByCapacity(roomList, capMax, capMin);
        }

        // if a date is selected it filters out the rooms that are fully booked for that day.
        if (datePicker.getValue() != null) {
            // get all the reservations and only keeps the reservations that are from the selected date.
            // the id of the rooms that are of the date are stored in roomsWithDate.
            ObservableList<Reservation> reservations = Reservation.getReservation();
            List<Integer> roomsWithDate = new ArrayList<Integer>();
            String date = datePicker.getValue().toString();
            for (int i = 0; i != reservations.size(); i++) {
                if (!reservations.get(i).getDate().getValue().equals(date)) {
                    reservations.remove(i);
                    i--;
                } else {
                    if (!roomsWithDate.contains(reservations.get(i).getRoom().getValue())) {
                        roomsWithDate.add(reservations.get(i).getRoom().getValue());
                    }
                }
            }

            // for every room the total hours of bookings on the selected date is calculated
            // if it is 16 the room is fully booked the room will be removed from the rooms to show
            double totalHoursAvailable;
            for (int q = 0; q != roomsWithDate.size(); q++) {
                totalHoursAvailable = 16;
                for (int z = 0; z != reservations.size(); z++) {
                    if (reservations.get(z).getRoom().getValue().equals(roomsWithDate.get(q))) {
                        double starting = Integer.parseInt(
                                reservations.get(z).getStartingTime().getValue().substring(0, 2));
                        double ending = Integer.parseInt(
                                reservations.get(z).getEndingTime().getValue().substring(0, 2));
                        if (reservations.get(z).getStartingTime().getValue().substring(3, 5).equals("30")) {
                            starting = starting + 0.5;
                        }
                        if (reservations.get(z).getEndingTime().getValue().substring(3,5).equals("30")) {
                            ending = ending + 0.5;
                        }
                        if (reservations.get(z).getEndingTime().getValue().equals("23:59:00")) {
                            ending = 24;
                        }
                        if (ending == 0) {
                            ending = 24;
                        }
                        totalHoursAvailable = totalHoursAvailable + starting - ending;
                    }
                }
                if (totalHoursAvailable <= 0) {
                    for (int y = 0; y != roomList.size(); y++) {
                        if (roomList.get(y).getRoomId().getValue().equals(roomsWithDate.get(q))) {
                            roomList.remove(y);
                        }
                    }
                }
            }
        }

        // value of the searchbar is put in searchBarInput
        // and is filtered on building name and room name.
        // the list is put in a new List
        // so if a other key is pressed the other filters don't have to be applied again.
        String searchBarInput = searchBar.getText();
        List<Room> roomsToShow = roomList;
        if (!searchBarInput.equals("")) {
            roomsToShow = GeneralMethods.filterBySearch(roomList, searchBarInput, buildings);
        }

        //Load the cards that need to be shown
        getCardsShown(roomsToShow);

    }

    /**
     * Clears all the cards currently shown in the view and shows the cards that are filtered.
     *
     * @param roomList list of rooms that are going to be shown.
     */
    public void getCardsShown(List<Room> roomList) {
        //Removes cards that are now in the view
        cardHolder.getChildren().clear();

        // create a 'card' showing some information of the room, for every room
        for (Room r : roomList) {
            cardHolder.getChildren().add(createRoomCard(r));
        }
    }

    /**
     * filters the rooms on the searchbar input. It searches for matches in the building name and room name.
     * Makes a call to GeneralMethods.FilterBySearch.
     *
     * @throws UnsupportedEncodingException when encoding fails.
     */
    public void searchbarChanges() throws UnsupportedEncodingException {
        // filters the rooms on the searchbar input. It searches for matches in the building name and room name.
        String searchBarInput = searchBar.getText();
        if (searchBarInput == "") {
            loadCards();
        } else {
            List<Room> roomsToShow = GeneralMethods.filterBySearch(roomList, searchBarInput, buildings);
            //Load the cards that need to be shown
            getCardsShown(roomsToShow);
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
                            if (item.isBefore(LocalDate.now()) || item.getDayOfWeek() == DayOfWeek.SATURDAY
                                    || item.getDayOfWeek() == DayOfWeek.SUNDAY) {
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
    private StringConverter<Building> getbuildingComboBoxConverter() {
        try {
            StringConverter<Building> converter = new StringConverter<Building>() {
                @Override
                public String toString(Building object) {
                    if (object == null) {
                        return "";
                    }
                    return object.getBuildingName().get();
                }

                @Override
                public Building fromString(String id) {
                    return buildingList.stream().filter(x -> String.valueOf(
                            x.getBuildingId()).equals(id)).collect(Collectors.toList()).get(0);
                }
            };
            return converter;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Creates a new 'card' (HBox) which contains some information about the room.
     * @param r The Room that we have to show information from
     * @return HBox which is the final 'card'
     */
    private HBox createRoomCard(Room r) {
        try {
            // initialize javafx components
            final HBox newCard = new HBox();
            final ImageView image = new ImageView();
            final VBox roomInfo = new VBox();
            final Text roomTitle = new Text();
            final Text roomCapacity = new Text();
            final Text roomDescription = new Text();
            final Text roomId = new Text();

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
            roomTitle.setText(r.getRoomName().get());
            roomTitle.setWrappingWidth(200);
            roomTitle.setFont(Font.font("System", FontWeight.BOLD, 18));
            roomTitle.setStyle("-fx-fill: #0ebaf8;");
            roomInfo.setMargin(roomTitle, new Insets(10, 10, 10, 15));

            // setting capacity and text margin (+ properties)
            roomCapacity.setText("Capacity: " + r.getRoomCapacity().get());
            roomCapacity.setWrappingWidth(200);
            roomCapacity.setFont(Font.font("System", 14));
            roomInfo.setMargin(roomCapacity, new Insets(0, 0, 5, 15));

            // setting description and text margin (+ properties)
            roomDescription.setText("Description: " + r.getRoomDescription().get());
            roomDescription.setWrappingWidth(310);
            roomDescription.setFont(Font.font("System", 14));
            roomInfo.setMargin(roomDescription, new Insets(0, 0, 0, 15));

            // setting 'text box' size
            roomInfo.setPrefSize(354, 378);

            // adding components to their corresponding parent
            roomInfo.getChildren().add(roomId);
            roomInfo.getChildren().add(roomTitle);
            roomInfo.getChildren().add(roomCapacity);
            roomInfo.getChildren().add(roomDescription);
            newCard.getChildren().add(image);
            newCard.getChildren().add(roomInfo);

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
     * When a card gets clicked, the RoomView gets loaded with all the corresponding room information.
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
     * Redirects to bookingHistory of the current user to see, edit or cancel bookings.
     *
     * @param event ActionEvent to get current Stage
     */
    @FXML
    private void bookingHistoryClicked(ActionEvent event) {
        // get current Stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // TODO: redirect to bookingHistory
    }

    /**
     * Clears all the filters and sets them back to 'empty'.
     *
     * @param event ActionEvent
     */
    @FXML
    private void clearFiltersClicked(ActionEvent event) {
        try {
            // clear every filter and reload the cards
            datePicker.setValue(null);
            buildingComboBox.setValue(null);
            yesCheckBoxFood.setSelected(false);
            noCheckBoxFood.setSelected(false);
            yesCheckBoxTeacherOnly.setSelected(false);
            teacherOnly = false;
            noCheckBoxTeacherOnly.setSelected(false);
            searchBar.setText("");
            capacityComboBox.setValue(null);
            bikesAvailable.setValue(null);
            loadCards();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
