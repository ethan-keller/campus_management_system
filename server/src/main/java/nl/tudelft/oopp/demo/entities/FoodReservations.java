package nl.tudelft.oopp.demo.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "foodReservations")
public class FoodReservations {
    @Id
    @Column(name = "reservation")
    private int reservation;

    @Id
    @Column(name = "food")
    private int food;

    @Column(name = "quantity")
    private int quantity;

    public FoodReservations() {
    }

    /**
     * Constructor.
     *
     * @param reservation
     * @param food
     * @param quantity
     */
    public FoodReservations(int reservation, int food, int quantity) {
        this.reservation = reservation;
        this.food = food;
        this.quantity = quantity;
    }

    /**
     * Retrieves the reservation ID of the reservation from the database.
     *
     * @return Returns the Integer Reservation ID.
     */
    public int getReservation() {
        return reservation;
    }


    /**
     * Retrieves the id of the food from the database.
     *
     * @return Returns the Integer food.
     */
    public int getFood() {
        return food;
    }


    /**
     * Retrieves the quantity.
     *
     * @return Returns the quantity of the food.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Equals
     *
     * @param o An Object to be compared to "this".
     * @return True if o is the same object, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof FoodBuilding)) {
            return false;
        }

        FoodReservations temp = (FoodReservations) o;
        if (reservation != temp.getReservation()) {
            return false;
        }
        if (food != temp.getFood()) {
            return false;
        }
        return true;
    }
}
