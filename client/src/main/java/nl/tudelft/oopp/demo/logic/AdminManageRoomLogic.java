package nl.tudelft.oopp.demo.logic;

import java.io.UnsupportedEncodingException;
import javafx.scene.control.TableView;
import nl.tudelft.oopp.demo.communication.RoomServerCommunication;
import nl.tudelft.oopp.demo.entities.Room;


public class AdminManageRoomLogic {

    /**.
     * This method is used to select a room from the tabular view of the rooms
     * Constraints are added; if the room index is less than 0, null is returned.
     * @param roomTable - The selected room from the table
     * @return - Room
     */
    public static Room getSelectedRoom(TableView<Room> roomTable) {
        if (roomTable.getSelectionModel().getSelectedIndex() >= 0) {
            return roomTable.getSelectionModel().getSelectedItem();
        } else {
            return null;
        }
    }

    /**.
     * This method is used in the adminManageRoomViewController class to communicate with the server to
     * command them to delete the selected room.
     * @param selectedRoom - Selected room from the table
     */
    public static void deleteRoomLogic(Room selectedRoom) {
        try {
            RoomServerCommunication.deleteRoom(selectedRoom.getRoomId().getValue());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**.
     * This method is used in adminManageRoomViewController class to communicate with the server to
     * command them to create a new room.
     * @param tempRoom - A room with all the required features to be created.
     */
    public static void createRoomLogic(Room tempRoom) {
        try {
            RoomServerCommunication.createRoom(tempRoom.getRoomName().get(), tempRoom.getRoomBuilding().get(),
                    tempRoom.getTeacherOnly().get(), tempRoom.getRoomCapacity().get(),
                    tempRoom.getRoomPhoto().get(), tempRoom.getRoomDescription().get(),
                    tempRoom.getRoomType().get());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();;
        }
    }

    /**.
     * This method is used in the adminManageRoomViewController class to communicate with the server to
     * command them to edit the selected room.
     * @param selectedRoom - This is used to get the id of the selected room.
     * @param tempRoom - These are the edited features of the room object passed as parameter.
     */
    public static void editRoomLogic(Room selectedRoom, Room tempRoom) {
        try {
            RoomServerCommunication.updateRoom(selectedRoom.getRoomId().get(), tempRoom.getRoomName().get(),
                    tempRoom.getRoomBuilding().get(), tempRoom.getTeacherOnly().get(),
                    tempRoom.getRoomCapacity().get(),
                    tempRoom.getRoomPhoto().get(), tempRoom.getRoomDescription().get(),
                    tempRoom.getRoomType().get());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
