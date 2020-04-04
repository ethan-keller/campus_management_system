package nl.tudelft.oopp.demo.user.logic;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Food;
import nl.tudelft.oopp.demo.entities.Reservation;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.user.controller.SearchViewController;

public class SearchViewLogic {

    private static Logger logger = Logger.getLogger("GlobalLogger");

    /**
     * filters rooms by the id of the building.
     *
     * @param rooms    list of rooms  to be filtered
     * @param building id of the building the rooms should contain
     * @return list of rooms with the building-id that is given.
     */
    public static List<Room> filterRoomByBuilding(List<Room> rooms, int building) {
        if (rooms == null) {
            return null;
        }

        if (rooms.size() == 0) {
            return rooms;
        }


        for (int i = 0; i != rooms.size(); i++) {
            if ((int) rooms.get(i).getRoomBuilding().getValue() != building) {
                rooms.remove(rooms.get(i));
                i--;
            }
        }

        return rooms;
    }

    /**
     * filters the rooms that are teacher only or not teacher only. Depending on the given boolean.
     *
     * @param rooms       list of rooms  to be filtered
     * @param teacherOnly if a room should be teacher only or not
     * @return list of rooms that are all teacher only or not teacher only. Depending on the boolean given.
     */
    public static List<Room> filterRoomByTeacherOnly(List<Room> rooms, boolean teacherOnly) {
        if (rooms == null) {
            return null;
        }

        if (rooms.size() == 0) {
            return rooms;
        }

        for (int j = 0; j != rooms.size(); j++) {
            if (rooms.get(j).getTeacherOnly().getValue() != teacherOnly) {
                rooms.remove(rooms.get(j));
                j--;
            }
        }

        return rooms;
    }

    /**
     * filters room by a capacity between 2 ints.
     *
     * @param rooms    list of rooms  to be filtered.
     * @param capacity a String that holds the selection of the capacity combobox.
     * @return list of rooms that have a capacity between the two given ints.
     */
    public static List<Room> filterRoomByCapacity(List<Room> rooms, String capacity) {
        int capMin;
        int capMax;
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
        if (rooms == null) {
            return null;
        }

        if (rooms.size() == 0) {
            return rooms;
        }

        for (int i = 0; i != rooms.size(); i++) {
            if (rooms.get(i).getRoomCapacity().getValue() > capMax
                    || rooms.get(i).getRoomCapacity().getValue() < capMin) {
                rooms.remove(rooms.get(i));
                i--;
            }
        }

        return rooms;
    }

    /**
     * filters rooms with the building names and room names that contain the input.
     *
     * @param rooms     list of rooms  to be filtered
     * @param input     input of the searchbar
     * @param buildings buildings that are in the system
     * @return lists of room with the building names and room names that contain the input.
     */
    public static List<Room> filterBySearch(List<Room> rooms, String input, List<Building> buildings) {
        List<Room> roomsFilteredByRoom = filterRoomsBySearch(rooms, input);
        List<Room> roomsFilteredByBuilding = filterBuildingsBySearch(rooms, input, buildings);
        // makes a union of the 2 filtered lists and removes the doubled rooms.
        for (int i = 0; i != roomsFilteredByRoom.size(); i++) {
            if (!roomsFilteredByBuilding.contains(roomsFilteredByRoom.get(i))) {
                roomsFilteredByBuilding.add(roomsFilteredByRoom.get(i));
            }
        }
        return roomsFilteredByBuilding;
    }

    /**
     * filters rooms with the room names that contain the input.
     *
     * @param rooms list of rooms  to be filtered
     * @param input input from the searchbar
     * @return a lists with rooms with the building names that contain the input.
     */
    public static List<Room> filterRoomsBySearch(List<Room> rooms, String input) {
        List<Room> res = new ArrayList<Room>();
        for (int j = 0; j != rooms.size(); j++) {
            res.add(rooms.get(j));
        }
        input = input.toLowerCase();
        for (int i = 0; i != res.size(); i++) {
            String name = res.get(i).getRoomName().getValue().toLowerCase();
            if (!name.contains(input)) {
                res.remove(res.get(i));
                i--;
            }
        }
        return res;
    }

    /**
     * filters rooms with the building names that contain the input.
     *
     * @param rooms     list of rooms  to be filtered
     * @param input     input from the searchbar
     * @param buildings buildings that are present in the database
     * @return a list of rooms with the building names that contain the input.
     */
    public static List<Room> filterBuildingsBySearch(List<Room> rooms, String input, List<Building> buildings) {
        List<Room> res = new ArrayList<Room>();
        List<Integer> buildingIds = new ArrayList<Integer>();
        input = input.toLowerCase();
        for (int i = 0; i != buildings.size(); i++) {
            if (buildings.get(i).getBuildingName().getValue().toLowerCase().contains(input)) {
                buildingIds.add(buildings.get(i).getBuildingId().getValue());
            }
        }
        for (int j = 0; j != rooms.size(); j++) {
            if (buildingIds.contains(rooms.get(j).getRoomBuilding().getValue())) {
                res.add(rooms.get(j));
            }
        }
        return res;
    }

    /**
     * filters the given roomlist and keeps only the rooms that have a free spot.
     * The free spot is on the day of the date given.
     *
     * @param roomList     list of all rooms to filter.
     * @param date         date that is selected.
     * @param reservations all reservations.
     * @param buildings    all buildings.
     */
    public static void filterRoomsByDate(
            List<Room> roomList, String date, List<Reservation> reservations, List<Building> buildings) {
        // get all the reservations and only keeps the reservations that are from the selected date.
        // the id of the rooms that are of the date are stored in roomsWithDate.
        List<Integer> roomsWithDate = new ArrayList<Integer>();
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

        // for every room the total hours of bookings on the selected date is calculated.
        // if it is closing time - opening time then hours available is 0.
        double totalHoursAvailable;
        //gets the room that are in roomsWithDate.
        for (int q = 0; q != roomList.size(); q++) {
            if (roomsWithDate.contains(roomList.get(q).getRoomId().getValue())) {
                Room room = roomList.get(q);
                Building building = null;
                //Gets the building of the room.
                for (int i = 0; i != buildings.size(); i++) {
                    if (buildings.get(i).getBuildingId().get() == room.getRoomBuilding().get()) {
                        building = buildings.get(i);
                    }
                }
                double openingTime = Integer.parseInt(
                        building.getOpeningTime().get().substring(0, 2));
                double closingTime = Integer.parseInt(
                        building.getClosingTime().get().substring(0, 2));
                //If there are half an hours involved the starting time gets +0.5
                //Same for the closing time.
                if (building.getOpeningTime().get().substring(3, 5).equals("30")) {
                    openingTime = openingTime + 0.5;
                }
                if (building.getClosingTime().get().substring(3, 5).equals("30")) {
                    closingTime = closingTime + 0.5;
                }
                totalHoursAvailable = closingTime - openingTime;
                // Gets all the reservations that are in the room on the selected date.
                for (int z = 0; z != reservations.size(); z++) {
                    if (reservations.get(z).getRoom().getValue().equals(roomList.get(q).getRoomId().get())) {
                        double starting = Integer.parseInt(
                                reservations.get(z).getReservationStartingTime().getValue().substring(0, 2));
                        double ending = Integer.parseInt(
                                reservations.get(z).getReservationEndingTime().getValue().substring(0, 2));
                        if (reservations.get(z).getReservationStartingTime()
                                .getValue().substring(3, 5).equals("30")) {
                            starting = starting + 0.5;
                        }
                        if (reservations.get(z).getReservationEndingTime()
                                .getValue().substring(3, 5).equals("30")) {
                            ending = ending + 0.5;
                        }
                        if (reservations.get(z).getReservationEndingTime()
                                .getValue().equals("23:59:00")) {
                            ending = 24;
                        }
                        if (ending == 0) {
                            ending = 24;
                        }
                        totalHoursAvailable = totalHoursAvailable + starting - ending;
                    }
                }
                if (totalHoursAvailable <= 0) {
                    roomList.remove(q);
                }
            }
        }
    }

    /**
     * Creates a new 'card' (HBox) which contains some information about the room.
     *
     * @param r The Room that we have to show information from
     * @return HBox which is the final 'card'
     */
    public static HBox createRoomCard(SearchViewController svw, Room r) {
        try {
            // load the 'skeleton of a new card
            FXMLLoader loader = new FXMLLoader();
            URL xmlUrl = svw.getClass().getResource("/RoomCard.fxml");
            loader.setLocation(xmlUrl);

            HBox newCard = loader.load();

            Image roomImage = null;
            try {
                // get the room image
                roomImage = new Image("images/" + r.getRoomPhoto().get());
            } catch (Exception e) {
                logger.log(Level.SEVERE, e.toString());
                // load placeholder instead
                roomImage = new Image("images/placeholder.png");
            }


            // use lookup to retrieve Nodes by their id and set their content
            ImageView image = ((ImageView) newCard.lookup("#image"));
            image.setImage(roomImage);
            // set the correct width
            image.setFitWidth(300);

            Building b = svw.buildingList.stream()
                    .filter(x -> x.getBuildingId().get() == r.getRoomBuilding().get())
                    .collect(Collectors.toList()).get(0);

            ((Text) newCard.lookup("#idText")).setText(String.valueOf(r.getRoomId().get()));
            ((Text) newCard.lookup("#titleText")).setText(r.getRoomName().get());
            ((Text) newCard.lookup("#buildingText")).setText("Building: " + b.getBuildingName().get());
            ((Text) newCard.lookup("#capacityText")).setText("Capacity: " + r.getRoomCapacity().get());
            ((Text) newCard.lookup("#descriptionText"))
                    .setText("Description: " + r.getRoomDescription().get());

            // set mouse clicked event on card (to redirect to room view)
            newCard.setOnMouseClicked(event -> {
                try {
                    svw.cardClicked(event);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            // set space between the cards
            VBox.setMargin(newCard, new Insets(0, 0, 70, 0));

            return newCard;
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
        }
        return null;
    }


    /**
     * Filters the rooms given by if they have food you can order.
     *
     * @param rooms     rooms to be filtered.
     * @param buildings list of buildings the rooms are in.
     * @return a list of rooms where you can order food.
     */
    public static List<Room> filterByFood(List<Room> rooms, List<Building> buildings) {
        List<Integer> buildingsWithFood = new ArrayList<Integer>();
        for (int i = 0; i != buildings.size(); i++) {
            int buildingId = buildings.get(i).getBuildingId().getValue();
            if (!Food.getFoodByBuildingId(buildingId).isEmpty()) {
                buildingsWithFood.add(buildings.get(i).getBuildingId().getValue());
            }
        }
        for (int i = 0; i != rooms.size(); i++) {
            if (!buildingsWithFood.contains(rooms.get(i).getRoomBuilding().getValue())) {
                rooms.remove(i);
                i--;
            }
        }
        return rooms;
    }

    /**
     * filters the given rooms so that all the building of the rooms have the amount of bikes given.
     *
     * @param rooms     list of rooms to filter.
     * @param buildings list of all buildings.
     * @param bikes     String of the amount of bikes there should be.
     * @return a list of rooms where the building has enough bikes.
     */
    public static List<Room> filterByBike(List<Room> rooms, List<Building> buildings, String bikes) {
        int minBikes;
        switch (bikes) {
            case "1+":
                minBikes = 1;
                break;
            case "5+":
                minBikes = 5;
                break;
            case "10+":
                minBikes = 10;
                break;

            case "20+":
                minBikes = 20;
                break;

            default:
                minBikes = 1;
                break;


        }
        List<Integer> buildingsWithBike = new ArrayList<Integer>();
        for (int i = 0; i != buildings.size(); i++) {
            int buildingBike = buildings.get(i).getBuildingMaxBikes().getValue();
            if (buildingBike >= minBikes) {
                buildingsWithBike.add(buildings.get(i).getBuildingId().getValue());
            }
        }
        for (int i = 0; i != rooms.size(); i++) {
            if (!buildingsWithBike.contains(rooms.get(i).getRoomBuilding().getValue())) {
                rooms.remove(i);
                i--;
            }
        }
        return rooms;
    }
}
