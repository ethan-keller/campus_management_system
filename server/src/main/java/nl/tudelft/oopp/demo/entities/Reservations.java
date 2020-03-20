package nl.tudelft.oopp.demo.entities;

import javax.persistence.*;

@Entity
@Table(name = "reservation")
public class Reservations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "room")
    private int room;

    @Column(name = "date")
    private String date;

    @Column(name = "startingTime")
    private String startingTime;

    @Column(name = "endingTime")
    private String endingTime;

    public Reservations() {
    }

    /**
     * Builder.
     *
     * @param id            int
     * @param username      String
     * @param date          String formatted like "". //TODO address format
     * @param startingTime String formatted like "". //TODO time format
     * @param endingTime   String formatted like "". //TODO time format
     */
    public Reservations(int id, String username, int room, String date,
                        String startingTime, String endingTime) {

        this.id = id;
        this.username = username;
        this.room = room;
        this.date = date;
        this.startingTime = startingTime;
        this.endingTime = endingTime;

    }

    /**
     * Retrieves the int id from the database.
     *
     * @return Returns the int id.
     */
    public int getId() {
        return id;
    }

    /**
     * Retrieves the String username from the database.
     *
     * @return Returns the String username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Retrieves the int room from the database.
     *
     * @return Returns the int room.
     */
    public int getRoom() {
        return room;
    }

    /**
     * Retrieves the String date from the database.
     *
     * @return Returns the String date formatted like "". //TODO dateformat
     */
    public String getDate() {
        return date;
    }

    /**
     * Retrieves the String startingTime from the database.
     *
     * @return Returns the String startingTime formatted like "". //TODO time format
     */
    public String getStarting_time() {
        return startingTime;
    }

    /**
     * Retrieves the String endingTime from the database.
     *
     * @return Returns the String endingTime formatted like "". //TODO time format
     */
    public String getEnding_time() {
        return endingTime;
    }

    /**
     * equals.
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

        Reservations reservation = (Reservations) o;

        return this.getId() == (reservation.getId());
    }

}
