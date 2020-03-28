package nl.tudelft.oopp.demo.communication;

import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Window;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


/**
 * This method encodes all communication that occurs between the server and the client.
 * This method is used in all the communication classes that need to send or receive information
 * from the server.
 *
 * @return the body of a get request to the server.
 * @throws Exception if communication has unsupported encoding mechanism.
 */
public class GeneralMethods {
    /**
     * This method is to encode all communication across the data stream.
     *
     * @param params are passed.
     * @return :Encoded parameters as string.
     * @throws UnsupportedEncodingException is thrown.
     */
    public static String encodeCommunication(String params) throws UnsupportedEncodingException {
        params = URLEncoder.encode(params, StandardCharsets.UTF_8.toString());
        params = params.replaceAll("%26", "&");
        params = params.replaceAll("%3D", "=");
        return params;
    }


    /**
     * Creates a pop up, aka an alert.
     *
     * @param title   String
     * @param content String containing the text to show to the user.
     * @param owner   Window
     * @param type    AlertType
     * @return Alert  An alert containing the provided information.
     */
    public static Alert createAlert(String title, String content, Window owner,
                                    Alert.AlertType type) {
        try {
            Alert alert = new Alert(type);
            alert.setTitle(title);
            alert.setContentText(content);
            alert.initOwner(owner);
            alert.initModality(Modality.WINDOW_MODAL);
            return alert;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

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
     * @param teacher_only if a room should be teacher only or not
     * @return list of rooms that are all teacher only or not teacher only. Depending on the boolean given.
     */
    public static List<Room> filterRoomByTeacher_only(List<Room> rooms, boolean teacher_only) {
        if (rooms == null) {
            return null;
        }

        if (rooms.size() == 0) {
            return rooms;
        }

        for (int j = 0; j != rooms.size(); j++) {
            if (rooms.get(j).getTeacherOnly().getValue() != teacher_only) {
                rooms.remove(rooms.get(j));
                j--;
            }
        }

        return rooms;
    }

    /**
     * filters room by a capacity between 2 ints.
     *
     * @param rooms list of rooms  to be filtered
     * @param capMax maximum capacity wanted
     * @param capMin minimum capacity wanted
     * @return list of rooms that have a capacity between the two given ints.
     */
    public static List<Room> filterRoomByCapacity(List<Room> rooms, int capMax, int capMin) {
        if (rooms == null) {
            return null;
        }

        if (rooms.size() == 0) {
            return rooms;
        }

        for (int i = 0; i != rooms.size(); i++) {
            if (rooms.get(i).getRoomCapacity().getValue() > capMax || rooms.get(i).getRoomCapacity().getValue() < capMin) {
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
}
