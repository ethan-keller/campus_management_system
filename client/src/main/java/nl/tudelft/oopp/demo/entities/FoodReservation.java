package nl.tudelft.oopp.demo.entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import nl.tudelft.oopp.demo.communication.FoodServerCommunication;

import org.json.JSONArray;
import org.json.JSONException;

public class FoodReservation {

    private IntegerProperty reservationId;
    private IntegerProperty foodId;
    private IntegerProperty foodQuantity;

    /**
     * Constructor with default fields.
     */
    public FoodReservation() {
        this.reservationId = new SimpleIntegerProperty(-1);
        this.foodId = new SimpleIntegerProperty(-1);
        this.foodQuantity = new SimpleIntegerProperty(-1);
    }


    /**
     * Constructor.
     * @param reservationId int
     * @param foodId int
     * @param foodQuantity int
     */
    public FoodReservation(int reservationId, int foodId, int foodQuantity) {
        this.reservationId = new SimpleIntegerProperty(reservationId);
        this.foodId = new SimpleIntegerProperty(foodId);
        this.foodQuantity = new SimpleIntegerProperty(foodQuantity);
    }

    /**
     * Gets food ID.
     * @return Returns ID
     */
    public IntegerProperty getFoodId() {
        return foodId;
    }

    /**
     * Sets the Food ID.
     * @param foodId The food ID
     */
    public void setFoodId(int foodId) {
        this.foodId.set(foodId);
    }


    /**
     * Gets reservation ID.
     * @return Returns ID
     */
    public IntegerProperty getReservationId() {
        return reservationId;
    }

    /**
     * Sets the reservation ID.
     * @param reservationId The reservation ID
     */
    public void setReservationId(int reservationId) {
        this.reservationId.set(reservationId);
    }


    /**
     * Returns the food reservation quantity.
     * @return The quantiy.
     */
    public IntegerProperty getFoodQuantity() {
        return foodQuantity;
    }

    /**
     * Sets the food quantity.
     * @param foodQuantity The food quantity
     */
    public void setFoodQuantity(int foodQuantity) {
        this.foodQuantity.set(foodQuantity);
    }


    /**
     * Convert server response into an ObservableList of food reservations.
     * @return List of Food reservations of selected reservation
     */
    public static ObservableList<FoodReservation> getUserReservationFood(Reservation r) throws JSONException {
        ObservableList<FoodReservation> foodReservation = FXCollections.observableArrayList();
        JSONArray jsonArrayFoodReservation = new JSONArray(FoodServerCommunication.getFoodReservationByReservation(
                r.getId().get()
        ));

        for (int i = 0; i < jsonArrayFoodReservation.length(); i++) {
            FoodReservation fr = new FoodReservation();
            fr.setFoodId(jsonArrayFoodReservation.getJSONObject(i).getJSONObject("food").getInt("id"));
            fr.setReservationId(jsonArrayFoodReservation.getJSONObject(i).getJSONObject("reservation")
                    .getInt("id"));
            fr.setFoodQuantity(jsonArrayFoodReservation.getJSONObject(i).getInt("quantity"));
            foodReservation.add(fr);
        }
        return foodReservation;
    }

}