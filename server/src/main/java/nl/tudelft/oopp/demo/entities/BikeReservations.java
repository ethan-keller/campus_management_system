package nl.tudelft.oopp.demo.entities;

import javax.persistence.*;

@Entity
@Table(name = "bike_reservations")
public class BikeReservations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "building")
    private int building;

    @Column(name = "room_count")
    private int numBikes;

    @Column(name = "date")
    private String date;

    @Column(name = "starting_time")
    private String startingTime;

    @Column(name = "ending_time")
    private String endingTime;

    public BikeReservations() {
    }

    /**
     * Constructor with optional fields available_bikes and max_bikes
     *
     * @param id int
     * @param building int
     * @param numBikes int
     * @Param date String
     * @Param startingTime String
     * @Param endingTime String
     */
    public BikeReservations(int id, int building, int numBikes, String date, String startingTime, String endingTime) {
        this.id = id;
        this.building = building;
        this.numBikes = numBikes;
        this.date = date;
        this.startingTime = startingTime;
        this.endingTime = endingTime;
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
     * Retrieves the id of the building from the database.
     *
     * @return Returns the String name.
     */
    public int getBuilding() {
        return building;
    }

    /**
     * Retrieves the number of bikes reserved.
     *
     * @return Returns the int value room_count.
     */
    public int getNumBikes() {
        return numBikes;
    }

    /**
     * Retrieves the date of the bike reservation.
     *
     * @return Returns the date.
     */
    public String getDate() {
        return date;
    }

    /**
     * Retrieves the starting time of the bike reservation.
     *
     * @return Returns the starting time.
     */
    public String getStartingTime() {
        return this.startingTime;
    }

    /**
     * Retrieves the ending time of the bike reservation.
     *
     * @return Returns the ending time.
     */
    public String getEndingTime() {
        return this.endingTime;
    }

    /**
     * Equals
     *
     * @param o An Object to be compared to "this".
     * @return True if o is the same object, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
            if(!(o instanceof BikeReservations)){
                return false;
            }
            BikeReservations temp = (BikeReservations)o;

            if(id != temp.getId()){
                return false;
            }

        return true;
    }
}
