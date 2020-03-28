package nl.tudelft.oopp.demo.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "food_reservations")
public class FoodReservations implements Serializable {
    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    @JoinColumn
    private Food food;

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    @JoinColumn
    private Reservations reservation;

    @Column(name = "quantity")
    private int quantity;

    /**
     * Empty constructor.
     */
    public FoodReservations() {
    }

    /**
     * Constructor to initialize the variables.
     * @param food The new food entity
     * @param reservation The new Reservation entity
     * @param quantity The quantity of the food that is ordered
     */
    public FoodReservations(Food food, Reservations reservation, int quantity) {
        this.food = food;
        this.reservation = reservation;
        this.quantity = quantity;
    }

    /**
     * Returns the Food entity.
     * @return Returns the Food entity
     */
    public Food getFood() {
        return food;
    }

    /**
     * Returns the Reservation entity.
     * @return Returns the Reservation entity
     */
    public Reservations getReservation() {
        return reservation;
    }

    /**
     * Returns the quantity of the food ordered.
     * @return Returns the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * The equals method to compare this object with other objects.
     * @param o The other object
     * @return Returns if they are equal in a boolean
     */
    public boolean equals(Object o) {
        if (!(o instanceof FoodReservations)) {
            return false;
        }
        FoodReservations temp = (FoodReservations)o;
        if (food != temp.getFood()) {
            return false;
        }
        if (reservation != temp.getReservation()) {
            return false;
        }
        return true;
    }

    /**
     * Generates the hashcode for this object.
     * @return Returns the hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(reservation.getId(), food.getId(), quantity);
    }
}