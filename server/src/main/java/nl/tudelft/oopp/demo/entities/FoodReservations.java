package nl.tudelft.oopp.demo.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

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

    public FoodReservations() {}

    public FoodReservations(Food food, Reservations reservation, int quantity) {
        this.food = food;
        this.reservation = reservation;
        this.quantity = quantity;
    }

    public Food getFood() {
        return food;
    }

    public Reservations getReservation() {
        return reservation;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean equals(Object o) {
        if(!(o instanceof FoodReservations)){
            return false;
        }
        FoodReservations temp = (FoodReservations)o;
        if(food != temp.getFood())
            return false;
        if(reservation != temp.getReservation())
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservation.getId(), food.getId(), quantity);
    }
}
