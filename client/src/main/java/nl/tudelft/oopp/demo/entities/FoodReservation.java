package nl.tudelft.oopp.demo.entities;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nl.tudelft.oopp.demo.communication.FoodServerCommunication;
import nl.tudelft.oopp.demo.controllers.AdminUserHistoryViewController;
import org.json.JSONArray;
import org.json.JSONException;

public class FoodReservation {

    private IntegerProperty reservationId;
    private IntegerProperty foodId;
    private IntegerProperty foodQuantity;

    public FoodReservation() {
        this.reservationId = new SimpleIntegerProperty(-1);
        this.foodId = new SimpleIntegerProperty(-1);
        this.foodQuantity = new SimpleIntegerProperty(-1);
    }


    public FoodReservation(int reservationId, int foodId, int foodQuantity) {
        this.reservationId = new SimpleIntegerProperty(reservationId);
        this.foodId = new SimpleIntegerProperty(foodId);
        this.foodQuantity = new SimpleIntegerProperty(foodQuantity);
    }

    public IntegerProperty getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) { this.foodId.set(foodId); }



    public IntegerProperty getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) { this.reservationId.set(reservationId); }



    public IntegerProperty getFoodQuantity() {
        return foodQuantity;
    }

    public void setFoodQuantity(int foodQuantity) { this.foodQuantity.set(foodQuantity); }


    /**
     * Convert server response into an ObservableList of food reservations.
     */
    public static ObservableList<FoodReservation> getUserReservationFood() throws JSONException {
        ObservableList<FoodReservation> foodReservation = FXCollections.observableArrayList();
        JSONArray jsonArrayFoodReservation= new JSONArray(FoodServerCommunication.getFoodReservationByReservation(AdminUserHistoryViewController.currentSelectedReservation.getId().get()));
        for(int i=0; i<jsonArrayFoodReservation.length(); i++){
            FoodReservation fr = new FoodReservation();
            fr.setFoodId(2);
            fr.setFoodId(jsonArrayFoodReservation.getJSONObject(i).getInt("food") );
            fr.setReservationId(jsonArrayFoodReservation.getJSONObject(i).getInt("reservation") );
            fr.setFoodQuantity(jsonArrayFoodReservation.getJSONObject(i).getInt("quantity") );
            foodReservation.add(fr);
        }
        return foodReservation;
    }

}
