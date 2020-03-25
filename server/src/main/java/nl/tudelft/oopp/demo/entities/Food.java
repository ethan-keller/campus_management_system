package nl.tudelft.oopp.demo.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "food")
public class Food implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private int price;

    @JsonManagedReference
    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL)
    private Set<FoodReservations> food_reservations;

    @JsonManagedReference
    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL)
    private Set<FoodBuilding> food_building;

    public Food() {
    }

    /**
     * Constructor.
     *
     * @param id int
     * @param name String
     * @param price int
     */
    public Food(int id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.food_building = new HashSet<>();
        this.food_reservations = new HashSet<>();
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
     * Retrieves the name of the food from the database.
     *
     * @return Returns the String name.
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the price of the food from the database.
     *
     * @return Returns the int value room_count.
     */
    public int getPrice() {
        return price;
    }

    public Set<FoodReservations> getFoodReservations() {
        return food_reservations;
    }

    public Set<FoodBuilding> getFoodBuilding() {
        return food_building;
    }


    /**
     * Equals method.
     *
     * @param o An Object to be compared to "this".
     * @return True if o is the same object, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Food)) {
            return false;
        }

        Food temp = (Food)o;
        if(id != temp.getId()) {
            return false;
        }

        return true;
    }
}