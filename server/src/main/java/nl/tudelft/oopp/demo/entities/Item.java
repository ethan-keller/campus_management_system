package nl.tudelft.oopp.demo.entities;

import javax.persistence.*;

@Entity
@Table(name = "calender_items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "user")
    private String user;

    @Column(name = "title")
    private String title;

    @Column(name = "date")
    private String date;

    @Column(name = "starting_time")
    private String starting_time;

    @Column(name = "ending_time")
    private String ending_time;

    @Column(name = "description")
    private String description;

    public Item(){
    }

    /**
     * Constructor
     * @param id
     * @param user
     * @param title
     * @param date
     * @param starting_time
     * @param ending_time
     * @param description
     */
    public Item(int id, String user, String title, String date, String starting_time, String ending_time, String description) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.date = date;
        this.starting_time = starting_time;
        this.ending_time = ending_time;
        this.description = description;
    }

    /**
     * Equals method
     * @param o object to get compared to
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return getId() == item.getId();
    }

    /**
     * getter for the id field
     * @return id of item
     */
    public int getId() {
        return id;
    }

    /**
     * setter for the id field
     * @param id id of item
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * getter for the user field
     * @return user of item
     */
    public String getUser() {
        return user;
    }

    /**
     * setter for the user field
     * @param user user of item
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * getter for the title field
     * @return title of item
     */
    public String getTitle() {
        return title;
    }

    /**
     * setter for the title field
     * @param title title of item
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * getter for the date field
     * @return date of item
     */
    public String getDate() {
        return date;
    }

    /**
     * setter for the date field
     * @param date date of item
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * getter for the starting_time field
     * @return starting_time of item
     */
    public String getStarting_time() {
        return starting_time;
    }

    /**
     * setter for the starting_time field
     * @param starting_time starting_time of item
     */
    public void setStarting_time(String starting_time) {
        this.starting_time = starting_time;
    }

    /**
     * getter for the ending_time field
     * @return ending_time of item
     */
    public String getEnding_time() {
        return ending_time;
    }

    /**
     * setter for the ending_time field
     * @param ending_time ending_time of item
     */
    public void setEnding_time(String ending_time) {
        this.ending_time = ending_time;
    }

    /**
     * getter for the description field
     * @return description of item
     */
    public String getDescription() {
        return description;
    }

    /**
     * setter for the description field
     * @param description description of item
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
