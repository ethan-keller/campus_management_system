package nl.tudelft.oopp.demo.entities;

import javax.persistence.*;

@Entity
@Table(name = "building")
public class Building {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "roomCount")
    private int roomCount;

    @Column(name = "address")
    private String address;

    @Column(name = "availableBikes")
    private Integer availableBikes;

    @Column(name = "maxBikes")
    private Integer maxBikes;

    public Building() {
    }

    /**
     * Constructor with optional fields availableBikes and maxBikes
     *
     * @param id int
     * @param name String
     * @param roomBount int
     * @param address String
     * @param availableBikes int
     * @param maxBikes int  //TODO exact format
     */
    public Building(int id, String name, int roomCount, String address, int availableBikes, int maxBikes) {
        this.id = id;
        this.name = name;
        this.roomCount = roomCount;
        this.address = address;
        this.availableBikes = availableBikes;
        this.maxBikes = maxBikes;
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
    public int getRoomCount() {
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
     * Retrieves the available bikes for this building from the database.
     *
     * @return Returns the int value availableBikes
     */
    public int getAvailableBikes() {
        return availableBikes;
    }

    /**
     * Retrieves the maximum amount of bikes for this building from the database.
     *
     * @return Returns the int value maxBikes
     */
    public int getMaxBikes() {
        return maxBikes;
    }


    /**
     * Equals
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
