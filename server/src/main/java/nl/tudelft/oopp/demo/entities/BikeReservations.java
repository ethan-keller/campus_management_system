package nl.tudelft.oopp.demo.entities;

import javax.persistence.*;

@Entity
@Table(name = "bike_reservations")
public class BikeReservations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "building")
    private int building;

    @Column(name = "room_count")
    private int num_bikes;

    public BikeReservations() {
    }

    /**
     * Constructor with optional fields available_bikes and max_bikes
     *
     * @param id int
     * @param building int
     * @param num_bikes int
     */
    public BikeReservations(int id, int building, int num_bikes) {
        this.id = id;
        this.building = building;
        this.num_bikes = num_bikes;
    }

    /**
     * Retrieves ID of the building from the database.
     *
     * @return Returns the int ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Retrieves the id of the building from the database.
     *
     * @return Returns the String name.
     */
    public int getBuilding() {
        return building;
    }

    /**
     * Retrieves the number of bikes reserved.
     *
     * @return Returns the int value room_count.
     */
    public int getNum_bikes() {
        return num_bikes;
    }

    /**
     * Equals
     *
     * @param o An Object to be compared to "this".
     * @return True if o is the same object, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
            if(!(o instanceof BikeReservations)){
                return false;
            }
            BikeReservations temp = (BikeReservations)o;

            if(id != temp.getId()){
                return false;
            }

        return true;
    }
}
