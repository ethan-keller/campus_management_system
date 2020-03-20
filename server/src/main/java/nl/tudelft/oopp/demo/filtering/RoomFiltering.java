package nl.tudelft.oopp.demo.filtering;

import java.util.List;
import nl.tudelft.oopp.demo.controllers.ReservationController;
import nl.tudelft.oopp.demo.entities.Reservations;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.repositories.ReservationsRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class RoomFiltering {
    /**
     * Filters all the rooms on the provided variables.
     *
     * @param rooms //TODO
     * @param building The int Id of the building the room should be in.
     * @param teacherOnly True = just show teacher only rooms.
     *                    False = just show non teacher only rooms.
     * @param capacity The minimum int value for capacity the room should have.
     * @return A list of Room in Json format
     */
    public static List<Room> filterRooms(List<Room> rooms, int building,
                                         boolean teacherOnly, int capacity) {

        if (rooms == null) {
            return null;
        }

        for (int i = 0; i != rooms.size(); i++) {
            if (rooms.get(i).getBuilding() != building) {
                rooms.remove(rooms.get(i));
                i--;
            }
        }

        for (int j = 0; j != rooms.size(); j++) {
            if (rooms.get(j).isTeacher_only() != teacherOnly) {
                rooms.remove(rooms.get(j));
                j--;
            }
        }

        for (int k = 0; k != rooms.size(); k++) {
            if (rooms.get(k).getCapacity() <= capacity) {
                rooms.remove(rooms.get(k));
                k--;
            }
        }


        return rooms;
    }
}
