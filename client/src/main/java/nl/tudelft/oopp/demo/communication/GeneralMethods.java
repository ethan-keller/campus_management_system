package nl.tudelft.oopp.demo.communication;

import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Window;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import org.controlsfx.control.RangeSlider;

import java.io.BufferedWriter;
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
<<<<<<< HEAD
     * @param params are passed.
     * @return :Encoded parameters as string.
     * @throws UnsupportedEncodingException is thrown.
=======
     * @param params are passed
     * @return Encoded parameters as string
     * @throws UnsupportedEncodingException is thrown
>>>>>>> develop
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
     * @param title String
     * @param content String containing the text to show to the user.
     * @param owner Window
     * @param type AlertType
     * @return Alert An alert containing the provided information.
     */
    public static Alert createAlert(String title, String content, Window owner, Alert.AlertType type) {
        try {
            // Create a new alert object (dialog box)
            Alert alert = new Alert(type);

            // Setting the title of the alert box.
            alert.setTitle(title);

            // Setting the content of the alert box.
            alert.setContentText(content);

            // Sets the owner of the alert box.
            alert.initOwner(owner);

            // Setting the modality of the window so that the user doesn't interact with the background screen.
            alert.initModality(Modality.WINDOW_MODAL);

            // Return the alert object.
            return alert;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Creates an alert box with a separate set of features compared to the above method.
     *
     * @param title - Title of the alert box (String)
     * @param header - Header of the alert box (String)
     * @param content - Content of the alert box (String)
     * @param type - AlertType
     * @return Alert  An alert containing the provided information.
     */
    public static Alert alertBox(String title, String header, String content, Alert.AlertType type) {

        try {
            // Create a new alert object (dialog box)
            Alert alert = new Alert(type);

            // Setting the title of the alert box.
            alert.setTitle(title);

            // Setting the header of the alert box.
            alert.setHeaderText(header);

            // Setting the content of the alert box.
            alert.setContentText(content);

            // Setting the modality of the window so that the user doesn't interact with the background screen.
            alert.initModality(Modality.WINDOW_MODAL);

            // Wait for the user to close the alert box.
            alert.showAndWait();

            // return the alert object
            return alert;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
<<<<<<< HEAD
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

        /**
     * Sets the RangeSlider to a standard white track.
     *
     * @param rs the RangeSlider to configure
     * @param bw the BufferedWriter which writes to the CSS file
     * @param css the css file that is written to
     */
    public static void setSliderDefaultCss(RangeSlider rs, BufferedWriter bw, String css) {
        try {
            rs.getStylesheets().add(css);
            bw.write(".track {\n"
                    + "\t-fx-background-color: linear-gradient(to right, #f5fdff 0%, #f5fdff 100%);\n"
                    + "    -fx-background-insets: 0 0 -1 0, 0, 1;\n"
                    + "    -fx-background-radius: 0.25em, 0.25em, 0.166667em; /* 3 3 2 */\n"
                    + "    -fx-padding: 0.25em; /* 3 */\n"
                    + "}\n\n"
                    + ".range-bar {\n"
                    + "    -fx-background-color: rgba(0,0,0,0.5);\n"
                    + "}");
            // flush and close writer
            bw.flush();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
