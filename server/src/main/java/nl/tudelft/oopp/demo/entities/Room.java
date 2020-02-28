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

    @Column(name = "username")
    private String username;

    @Column(name = "building")
    private int building;

    @Column(name = "teacher_only")
    private boolean teacher_only;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "photos")
    private String photos;

    @Column(name = "describtion")
    private String describtion;

    public Room() {
    }


    public Room(int id, String username, int building, Boolean teacher_only, int capacity, String photos, String describtion) {
        this.id = id;
        this.username = username;
        this.building = building;
        this.teacher_only = teacher_only;
        this.capacity = capacity;
        this.photos = photos;
        this.describtion = describtion;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
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

    public String getDescribtion(){
        return describtion;
    }

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
