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

    public Item(int id, String user, String title, String date, String starting_time, String ending_time, String description) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.date = date;
        this.starting_time = starting_time;
        this.ending_time = ending_time;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return getId() == item.getId();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStarting_time() {
        return starting_time;
    }

    public void setStarting_time(String starting_time) {
        this.starting_time = starting_time;
    }

    public String getEnding_time() {
        return ending_time;
    }

    public void setEnding_time(String ending_time) {
        this.ending_time = ending_time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
