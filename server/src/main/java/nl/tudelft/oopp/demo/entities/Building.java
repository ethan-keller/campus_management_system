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


    public Building(int id, String name, int room_count, String address) {
        this.id = id;
        this.name = name;
        this.room_count = room_count;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getRoom_count() {
        return room_count;
    }

    public String getAddress() {
        return address;
    }

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
