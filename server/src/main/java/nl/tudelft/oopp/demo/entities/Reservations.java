package nl.tudelft.oopp.demo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "reservation")
public class Reservations {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "room")
    private int room;

    @Column(name = "date")
    private String date;

    @Column(name = "starting_time")
    private String starting_time;

    @Column(name = "ending_time")
    private String ending_time;

    public Reservations() {
    }

    public Reservations(int id, String username, String date, String starting_time, String ending_time) {
        this.id = id;
        this.username = username;
        this.room = room;
        this.date = date;
        this.starting_time = starting_time;
        this.ending_time = ending_time;

    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public int getRoom(){
        return room;
    }

    public String getDate() {
        return date;
    }

    public String getStarting_time() {
        return starting_time;
    }

    public String getEnding_time() {
        return ending_time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Reservations reservation = (Reservations) o;

        return this.getId() == (reservation.getId());
    }

}
