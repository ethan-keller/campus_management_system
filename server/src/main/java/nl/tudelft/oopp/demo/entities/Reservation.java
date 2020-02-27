package nl.tudelft.oopp.demo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "reservation")
public class Reservation {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "room")
    private String room;

    @Column(name = "date")
    private Date date;

    @Column(name = "starting_time")
    private int starting_time;

    @Column(name = "ending_time")
    private int ending_time;

    public Reservation() {
    }


    public Reservation(int id, String username, Date date, int starting_time, int ending_time) {
        this.id = id;
        this.username = username;
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

    public Date getDate() {
        return date;
    }

    public int getStarting_time() {
        return starting_time;
    }

    public int getEnding_time() {
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

        Reservation reservation = (Reservation) o;

        return this.getId() == (reservation.getId());
    }

}
