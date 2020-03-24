package nl.tudelft.oopp.demo.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class FoodReservations implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn
    private Food food;

    @Id
    @ManyToOne
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

    public void setFood(Food food) {
        this.food = food;
    }

    public Reservations getReservation() {
        return reservation;
    }

    public void setReservation(Reservations reservation) {
        this.reservation = reservation;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
