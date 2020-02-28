package nl.tudelft.oopp.demo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "room")
public class Room {
    @Id
    @Column(name = "id")
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

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getBuilding(){
        return building;
    }

    public boolean isTeacher_only() {
        return teacher_only;
    }

    public int getCapacity(){
        return capacity;
    }

    public String getPhotos(){
        return photos;
    }

    public String getDescription(){
        return description;
    }

    public String getType() {return type;}

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
