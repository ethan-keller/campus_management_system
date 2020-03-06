package nl.tudelft.oopp.demo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "building")
public class Building {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "room_count")
    private int room_count;

    @Column(name = "address")
    private String address;

    public Building() {
    }

    /**
     * Constructor.
     *
     * @param id int
     * @param name String
     * @param room_count int
     * @param address String //TODO exact format
     */
    public Building(int id, String name, int room_count, String address) {
        this.id = id;
        this.name = name;
        this.room_count = room_count;
        this.address = address;
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
     * @return Returns the int value room_count.
     */
    public int getRoom_count() {
        return room_count;
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
