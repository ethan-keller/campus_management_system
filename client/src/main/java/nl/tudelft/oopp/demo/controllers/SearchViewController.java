package nl.tudelft.oopp.demo.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.logic.SearchViewLogic;
import nl.tudelft.oopp.demo.views.LoginView;
import nl.tudelft.oopp.demo.views.RoomView;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;



/**
 * Controller class for SearchView (JavaFX).
 */
public class SearchViewController implements Initializable {
    /**
     * These are the FXML elements that inject some functionality into the application.
     */
    @FXML
    private DatePicker datePicker;
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

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    /**
     * Default construct of searchView class.
     */
    public SearchViewController() {
    }


    /**
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

            // configure the date picker
            datePicker.setConverter(getDatePickerStringConverter());
            datePicker.setDayCellFactory(getDayCellFactory());

            // assign values to the observable lists
            capacityList.addAll("1-5", "5-10", "10-20", "20+");
            buildingComboBox.setItems(buildingList);
            buildingComboBox.setConverter(getBuildingComboBoxConverter());
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
        Date now = new Date();
        String strDate = sdf.format(now);
        System.out.println("initialize end: " + strDate);
    }

    /**
     * Filters the rooms according to the filters selected.
     * Makes a call to getCardsShown() to show the cards on the view.
     *
     * @throws UnsupportedEncodingException when encoding fails.
     */
    public void loadCards() throws UnsupportedEncodingException {
        Date now = new Date();
        String strDate = sdf.format(now);
        System.out.println("Filtering start: " + strDate);
        //load all rooms back in the roomlist to filter again
        roomList = new ArrayList<Room>();
        for (int i = 0; i != rooms.size(); i++) {
            roomList.add(rooms.get(i));
        }

        //Check if there are any filters selected and if so filter the roomlist
        if (buildingComboBox.getValue() != null) {
            building = buildingComboBox.getValue().getBuildingId().getValue();
            roomList = SearchViewLogic.filterRoomByBuilding(roomList, building);
        }

        // if the checkbox is selected it filters according to the checkbox.
        if (yesCheckBoxTeacherOnly.isSelected()) {
            roomList = SearchViewLogic.filterRoomByTeacherOnly(roomList, true);
        }

        // if the checkbox is selected it filters according to the checkbox.
        if (noCheckBoxTeacherOnly.isSelected()) {
            roomList = SearchViewLogic.filterRoomByTeacherOnly(roomList, false);
        }

        // if the combobox is selected on a value it filters for that value.
        if (capacityComboBox.getValue() != null) {
            String capacity = capacityComboBox.getValue();

            roomList = SearchViewLogic.filterRoomByCapacity(roomList, capacity);
        }

        // if a date is selected it filters out the rooms that are fully booked for that day.
        Date now2 = new Date();
        String strDate2 = sdf.format(now2);
        System.out.println("Date Filtering start: " + strDate);
        if (datePicker.getValue() != null) {
            String date = datePicker.getValue().toString();
            SearchViewLogic.filterRoomsByDate(roomList, date);
        }
        Date now3 = new Date();
        String strDate3 = sdf.format(now3);
        System.out.println("Date filtering end: " + strDate);

        // value of the searchbar is put in searchBarInput
        // and is filtered on building name and room name.
        // the list is put in a new List
        // so if a other key is pressed the other filters don't have to be applied again.
        String searchBarInput = searchBar.getText();
        List<Room> roomsToShow = roomList;
        if (!searchBarInput.equals("")) {
            roomsToShow = SearchViewLogic.filterBySearch(roomList, searchBarInput, buildings);
        }
        Date now4 = new Date();
        String strDate4 = sdf.format(now4);
        System.out.println("ending filtering: " + strDate2);
        //Load the cards that need to be shown
        getCardsShown(roomsToShow);

    }

    /**
     * Clears all the cards currently shown in the view and shows the cards that are filtered.
     *
     * @param roomList list of rooms that are going to be shown.
     */
    public void getCardsShown(List<Room> roomList) {
        Date now2 = new Date();
        String strDate2 = sdf.format(now2);
        System.out.println("starting getCardsShown: " + strDate2);
        //Removes cards that are now in the view
        cardHolder.getChildren().clear();

        // create a 'card' showing some information of the room, for every room
        for (Room r : roomList) {
            cardHolder.getChildren().add(SearchViewLogic.createRoomCard(this, r));
        }
        Date now = new Date();
        String strDate = sdf.format(now);
        System.out.println("ending getCardShown: " + strDate2);
    }

    /**
     * filters the rooms on the searchbar input. It searches for matches in the building name and room name.
     * Makes a call to SearchViewLogic.FilterBySearch.
     *
     * @throws UnsupportedEncodingException when encoding fails.
     */
    public void searchbarChanges() throws UnsupportedEncodingException {
        // filters the rooms on the searchbar input. It searches for matches in the building name and room name.
        String searchBarInput = searchBar.getText();
        if (searchBarInput == "") {
            loadCards();
        } else {
            List<Room> roomsToShow = SearchViewLogic.filterBySearch(roomList, searchBarInput, buildings);
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
    private StringConverter<Building> getBuildingComboBoxConverter() {
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
     * When a card gets clicked, the RoomView gets loaded with all the corresponding room information.
     *
     * @param event MouseEvent
     */
    public void cardClicked(MouseEvent event) {
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
     * Clears all the filters and sets them back to 'empty'.
     *
     */
    @FXML
    private void clearFiltersClicked() {
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
