package nl.tudelft.oopp.demo.filtering;

import nl.tudelft.oopp.demo.controllers.ReservationController;
import nl.tudelft.oopp.demo.entities.Reservations;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.repositories.ReservationsRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RoomFiltering {

    public static List<Room> filterRooms(List<Room> rooms, int building, boolean teacher_only, int capacity){

        if(rooms == null){
            return null;
        }

        if(rooms.size() == 0){
            return rooms;
        }

        for(int i = 0; i != rooms.size(); i++){
            if(rooms.get(i).getBuilding() != building){
                rooms.remove(rooms.get(i));
                i--;
            }
        }

        for(int j = 0; j != rooms.size(); j++){
            if(rooms.get(j).isTeacherOnly() != teacher_only){
                rooms.remove(rooms.get(j));
                j--;
            }
        }

        for(int k = 0; k != rooms.size(); k++){
            if(rooms.get(k).getCapacity() <= capacity){
                rooms.remove(rooms.get(k));
                k--;
            }
        }


        return rooms;
    }

    public static List<Room> filterRoomByBuilding( List<Room> rooms, int building){
        if(rooms == null){
            return null;
        }

        if(rooms.size() == 0){
            return rooms;
        }

        for(int i = 0; i != rooms.size(); i++){
            if(rooms.get(i).getBuilding() != building){
                rooms.remove(rooms.get(i));
                i--;
            }
        }

        return rooms;
    }

    public static List<Room> filterRoomByTeacher_only( List<Room> rooms, boolean teacher_only){
        if(rooms == null){
            return null;
        }

        if(rooms.size() == 0){
            return rooms;
        }

        for(int j = 0; j != rooms.size(); j++){
            if(rooms.get(j).isTeacherOnly() != teacher_only){
                rooms.remove(rooms.get(j));
                j--;
            }
        }

        return rooms;
    }

    public static List<Room> filterRoomByCapacity(List<Room> rooms, int capMax, int capMin){
        if(rooms == null){
            return null;
        }

        if(rooms.size() == 0){
            return rooms;
        }

        for(int i = 0; i != rooms.size(); i++){
            if(rooms.get(i).getCapacity() > capMax || rooms.get(i).getCapacity() < capMin){
                rooms.remove(rooms.get(i));
                i--;
            }
        }

        return rooms;
    }
}
