package nl.tudelft.oopp.demo.filtering;

import nl.tudelft.oopp.demo.entities.Room;

import java.util.List;

public class RoomFiltering {

    public static List<Room> filterRooms(List<Room> rooms, int building, boolean teacherOnly, int capacity){

        if(rooms == null){
            return null;
        }

        for(int i = 0; i != rooms.size(); i++){
            if(rooms.get(i).getBuilding() != building){
                rooms.remove(rooms.get(i));
                i--;
            }
        }

        for(int j = 0; j != rooms.size(); j++){
            if(rooms.get(j).isTeacherOnly() != teacherOnly){
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
}
