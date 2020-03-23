package nl.tudelft.oopp.demo.entities;

import javax.persistence.*;

@Entity
@Table(name = "calenderItems")
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

    @Column(name = "startingTime")
    private String startingTime;

    @Column(name = "endingTime")
    private String endingTime;

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
     * @param startingTime
     * @param endingTime
     * @param description
     */
    public Item(int id, String user, String title, String date, String startingTime, String endingTime, String description) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.date = date;
        this.startingTime = startingTime;
        this.endingTime = endingTime;
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
     * getter for the startingTime field
     * @return startingTime of item
     */
    public String getStartingTime() {
        return startingTime;
    }

    /**
     * setter for the startingTime field
     * @param startingTime startingTime of item
     */
    public void setStartingTime(String startingTime) {
        this.startingTime = startingTime;
    }

    /**
     * getter for the endingTime field
     * @return endingTime of item
     */
    public String getEndingTime() {
        return endingTime;
    }

    /**
     * setter for the endingTime field
     * @param endingTime endingTime of item
     */
    public void setEndingTime(String endingTime) {
        this.endingTime = endingTime;
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
