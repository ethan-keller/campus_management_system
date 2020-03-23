package nl.tudelft.oopp.demo.entities;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nl.tudelft.oopp.demo.communication.FoodServerCommunication;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;

public class Food {

    private IntegerProperty foodId;
    private StringProperty foodName;
    private DoubleProperty foodPrice;

    public Food() {
        this.foodId = new SimpleIntegerProperty(-1);
        this.foodName = new SimpleStringProperty(null);
        this.foodPrice = new SimpleDoubleProperty(-1.0d);
    }


    public Food(int foodId, String foodName, float foodPrice) {
        this.foodId = new SimpleIntegerProperty(foodId);
        this.foodName = new SimpleStringProperty(foodName);
        this.foodPrice = new SimpleDoubleProperty(foodPrice);
    }

    public IntegerProperty getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) { this.foodId.set(foodId); }



    public StringProperty getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) { this.foodName.set(foodName); }



    public DoubleProperty getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(double foodPrice) { this.foodPrice.set(foodPrice); }


    /**
     * Convert server response into an ObservableList of foods.
     */
    public static ObservableList<Food> getFoodData() throws JSONException {
        ObservableList<Food> foodData = FXCollections.observableArrayList();
        JSONArray jsonArrayFoods= new JSONArray(FoodServerCommunication.getAllFoods());
        for(int i=0; i<jsonArrayFoods.length(); i++){
            Food f = new Food();
            f.setFoodId(2);
            f.setFoodId(jsonArrayFoods.getJSONObject(i).getInt("id") );
            f.setFoodName(jsonArrayFoods.getJSONObject(i).getString("name") );
            f.setFoodPrice(jsonArrayFoods.getJSONObject(i).getFloat("price") );
            foodData.add(f);
        }
        return foodData;
    }

    public static ObservableList<Building> getFoodBuilding(int id) throws UnsupportedEncodingException {
        ObservableList<Building> foodBuilding = FXCollections.observableArrayList();
        JSONArray jsonArrayBuildings= new JSONArray(FoodServerCommunication.getFoodBuildings(id));
        for(int i=0; i<jsonArrayBuildings.length(); i++){
            Building b = new Building();
            b.setBuildingId(2);
            b.setBuildingId(jsonArrayBuildings.getJSONObject(i).getInt("id"));
            b.setBuildingName(jsonArrayBuildings.getJSONObject(i).getString("name") );
            b.setBuildingAddress(jsonArrayBuildings.getJSONObject(i).getString("address") );
            b.setBuildingRoomCount(jsonArrayBuildings.getJSONObject(i).getInt("roomCount") );
            foodBuilding.add(b);
        }
        return foodBuilding;
    }

}