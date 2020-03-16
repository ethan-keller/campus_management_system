package nl.tudelft.oopp.demo.communication;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Window;
import nl.tudelft.oopp.demo.entities.Room;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class GeneralMethods {
    public static String encodeCommunication(String params) throws UnsupportedEncodingException {
        params = URLEncoder.encode(params, StandardCharsets.UTF_8.toString());
        params = params.replaceAll("%26", "&");
        params = params.replaceAll("%3D", "=");
        return params;
    }

    public static Alert createAlert(String title, String content, Window owner, Alert.AlertType type){
        try {
            Alert alert = new Alert(type);
            alert.setTitle(title);
            alert.setContentText(content);
            alert.initOwner(owner);
            alert.initModality(Modality.WINDOW_MODAL);
            return alert;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static ObservableList<Room> filterRoomByBuilding(ObservableList<Room> rooms, int building){
        if(rooms == null){
            return null;
        }

        if(rooms.size() == 0){
            return rooms;
        }


        for(int i = 0; i != rooms.size(); i++){
            if((int) rooms.get(i).getRoomBuilding().getValue() != building){
                rooms.remove(rooms.get(i));
                i--;
            }
        }

        return rooms;
    }

    public static ObservableList<Room> filterRoomByTeacher_only(ObservableList<Room> rooms, boolean teacher_only){
        if(rooms == null){
            return null;
        }

        if(rooms.size() == 0){
            return rooms;
        }

        for(int j = 0; j != rooms.size(); j++){
            if(rooms.get(j).getTeacher_only().getValue() != teacher_only){
                rooms.remove(rooms.get(j));
                j--;
            }
        }

        return rooms;
    }

    public static ObservableList<Room> filterRoomByCapacity(ObservableList<Room> rooms, int capMax, int capMin){
        if(rooms == null){
            return null;
        }

        if(rooms.size() == 0){
            return rooms;
        }

        for(int i = 0; i != rooms.size(); i++){
            if(rooms.get(i).getRoomCapacity().getValue() > capMax || rooms.get(i).getRoomCapacity().getValue() < capMin){
                rooms.remove(rooms.get(i));
                i--;
            }
        }

        return rooms;
    }
}
