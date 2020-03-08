package nl.tudelft.oopp.demo.entities;

import javax.persistence.*;

@Entity
@Table(name = "room")
public class Room {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "building")
    private int building;

    @Column(name = "teacher_only")
    private boolean teacher_only;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "photos")
    private String photos;

    @Column(name = "description")
    private String description;

    @Column(name = "type")
    private String type;

    public Room() {
    }

    /**
     * Builder
     *
     * @param id int
     * @param name String
     * @param building int
     * @param teacher_only boolean
     * @param capacity int
     * @param photos String containing a URL/filepath
     * @param description String
     * @param type String (e.g. lecture hall)
     */
    public Room(int id, String name, int building, boolean teacher_only, int capacity, String photos, String description, String type) {
        this.id = id;
        this.name = name;
        this.building = building;
        this.teacher_only = teacher_only;
        this.capacity = capacity;
        this.photos = photos;
        this.description = description;
        this.type = type;
    }

    /**
     * Retrieves the int id from the database.
     *
     * @return Retruns the int id.
     */
    public int getId() {
        return id;
    }

    /**
     * Retrieves the String name from the database.
     *
     * @return Returns the String name.
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the int building from the database.
     *
     * @return Returns the int building.
     */
    public int getBuilding(){
        return building;
    }

    /**
     * Retrieves the boolean teacher_only from the database.
     *
     * @return Returns the boolean teacher_only.
     */
    public boolean isTeacher_only() {
        return teacher_only;
    }

    /**
     * Retrieves the capacity of the building from the database.
     *
     * @return Returns the int capacity.
     */
    public int getCapacity(){
        return capacity;
    }

    /**
     * Retrieves the String photos containing a URL/filepath from the database.
     *
     * @return Returns the String photos.
     */
    public String getPhotos(){
        return photos;
    }

    /**
     * Retrieves the String description from the database.
     *
     * @return Returns the String description.
     */
    public String getDescription(){
        return description;
    }

    /**
     * Retrieves the String type from the database.
     *
     * @return Returns the String type.
     */
    public String getType() {
        return type;
    }

    /**
     * equals
     *
     * @param o The Object to compare to.
     * @return True if Object and "this" are the same, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Room room = (Room) o;

        return this.id == room.getId();
    }
}
