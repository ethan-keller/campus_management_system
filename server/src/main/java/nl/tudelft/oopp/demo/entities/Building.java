package nl.tudelft.oopp.demo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "building")
public class Building {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "room_count")
    private int roomCount;

    @Column(name = "address")
    private String address;

    @Column(name = "available_bikes")
    private Integer available_bikes;

    @Column(name = "max_bikes")
    private Integer max_bikes;

    public Building() {
    }

    /**
     * Constructor with optional fields available_bikes and max_bikes
     *
     * @param id int
     * @param name String
     * @param room_count int
     * @param address String
     * @param available_bikes int
     * @param max_bikes int  //TODO exact format
     */
    public Building(int id, String name, int room_count, String address, int available_bikes, int max_bikes) {


        this.id = id;
        this.name = name;
        this.roomCount = room_count;
        this.address = address;
        this.available_bikes = available_bikes;
        this.max_bikes = max_bikes;
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
     * Retrieves the name of the building from the database.
     *
     * @return Returns the String name.
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the room count of the building from the database.
     *
     * @return Returns the int value roomCount.
     */
    public int getRoom_count() {
        return roomCount;
    }

    /**
     * Retrieves the address of the building from the database.
     *
     * @return Returns the String address formatted like: "".//TODO address format
     */
    public String getAddress() {
        return address;
    }

    /**
<<<<<<< HEAD
     * Equals.
=======
     * Retrieves the available bikes for this building from the database.
     *
     * @return Returns the int value available_bikes
     */
    public int getAvailableBikes() {
        return available_bikes;
    }

    /**
     * Retrieves the maximum amount of bikes for this building from the database.
     *
     * @return Returns the int value max_bikes
     */
    public int getMaxBikes() {
        return max_bikes;
    }


    /**
     * Equals
>>>>>>> develop
     *
     * @param o An Object to be compared to "this".
     * @return True if o is the same object, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Building building = (Building) o;

        return this.id == building.id;
    }
}
