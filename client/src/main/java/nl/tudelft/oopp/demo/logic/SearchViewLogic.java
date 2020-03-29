package nl.tudelft.oopp.demo.logic;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import nl.tudelft.oopp.demo.controllers.SearchViewController;
import nl.tudelft.oopp.demo.entities.Building;

import nl.tudelft.oopp.demo.entities.Reservation;
import nl.tudelft.oopp.demo.entities.Room;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SearchViewLogic {
    /**
     * filters rooms by the id of the building.
     *
     * @param rooms list of rooms  to be filtered
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
     * @param rooms list of rooms  to be filtered
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
     * @param rooms list of rooms  to be filtered.
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
     * @param rooms list of rooms  to be filtered
     * @param input input of the searchbar
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
     * @param rooms list of rooms  to be filtered
     * @param input input from the searchbar
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

    public static void filterRoomsByDate(List<Room> roomList, String date){
        // get all the reservations and only keeps the reservations that are from the selected date.
        // the id of the rooms that are of the date are stored in roomsWithDate.
        ObservableList<Reservation> reservations = Reservation.getReservation();
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

    /**
     * Creates a new 'card' (HBox) which contains some information about the room.

     * @param r The Room that we have to show information from
     * @return HBox which is the final 'card'
     */
    public static HBox createRoomCard(SearchViewController svw, Room r) {
        try {
            // initialize javafx components
            final HBox newCard = new HBox();
            final ImageView image = new ImageView();
            final VBox roomInfo = new VBox();
            final Text roomTitle = new Text();
            final Text roomBuilding = new Text();
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
            HBox.setMargin(image, new Insets(10, 5, 10, 10));

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
            VBox.setMargin(roomTitle, new Insets(10, 10, 10, 15));

            // setting building name and text margin
            Building building = Building.getBuildingData().stream()
                    .filter(x -> x.getBuildingId().get() == r.getRoomBuilding().get())
                    .collect(Collectors.toList()).get(0);
            roomBuilding.setText("Building: " + building.getBuildingName().get());
            roomBuilding.setWrappingWidth(200);
            roomBuilding.setFont(Font.font("System", 14));
            VBox.setMargin(roomBuilding, new Insets(0, 0, 5, 15));

            // setting capacity and text margin (+ properties)
            roomCapacity.setText("Capacity: " + r.getRoomCapacity().get());
            roomCapacity.setWrappingWidth(200);
            roomCapacity.setFont(Font.font("System", 14));
            VBox.setMargin(roomCapacity, new Insets(0, 0, 5, 15));

            // setting description and text margin (+ properties)
            roomDescription.setText("Description: " + r.getRoomDescription().get());
            roomDescription.setWrappingWidth(310);
            roomDescription.setFont(Font.font("System", 14));
            VBox.setMargin(roomDescription, new Insets(0, 0, 5, 15));

            // setting 'text box' size
            roomInfo.setPrefSize(354, 378);

            // adding components to their corresponding parent
            roomInfo.getChildren().add(roomId);
            roomInfo.getChildren().add(roomTitle);
            roomInfo.getChildren().add(roomCapacity);
            roomInfo.getChildren().add(roomBuilding);
            roomInfo.getChildren().add(roomDescription);
            newCard.getChildren().add(image);
            newCard.getChildren().add(roomInfo);

            // setting size
            newCard.setPrefWidth(688);
            newCard.setPrefHeight(145);

            // add mouse click listener to individual cards
            newCard.setOnMouseClicked(event -> {
                try {
                    svw.cardClicked(event);
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
}
