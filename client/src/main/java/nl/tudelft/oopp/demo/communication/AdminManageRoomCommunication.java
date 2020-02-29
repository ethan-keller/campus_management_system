package nl.tudelft.oopp.demo.communication;

import nl.tudelft.oopp.demo.entities.Room;

import java.net.http.HttpResponse;

public class AdminManageRoomCommunication {

    public static String createRoom(Room room) {
        HttpResponse<String> response = null;
        return response.body();
    }

    public static String editRoom(Room room) {
        HttpResponse<String> response = null;
        return response.body();
    }

    public static String deleteRoom(int roomId) {
        HttpResponse<String> response = null;
        return response.body();
    }

//    public static String getRoom(int roomId) {
//
//    }
//
//    public static String getRoomList() {
//
//    }
}
