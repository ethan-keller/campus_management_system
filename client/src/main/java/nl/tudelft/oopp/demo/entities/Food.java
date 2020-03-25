package nl.tudelft.oopp.demo.entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nl.tudelft.oopp.demo.communication.FoodServerCommunication;
import org.json.JSONArray;
import org.json.JSONObject;

public class Food {
    private IntegerProperty foodId;
    private StringProperty foodName;
    private IntegerProperty foodPrice;


    public Food(){
        this.foodId = new SimpleIntegerProperty(-1);
        this.foodName = new SimpleStringProperty(null);
        this.foodPrice = new SimpleIntegerProperty(-1);
    }

    public Food(int id, String name, int price){
        this.foodId = new SimpleIntegerProperty(id);
        this.foodName = new SimpleStringProperty(name);
        this.foodPrice = new SimpleIntegerProperty(price);
    }

    public int getFoodId() {
        return foodId.get();
    }

    public void setFoodId(int foodId) {
        this.foodId.set(foodId);
    }

    public String getFoodName() {
        return foodName.get();
    }

    public void setFoodName(String foodName) {
        this.foodName.set(foodName);
    }

    public int getFoodPrice() {
        return foodPrice.get();
    }

    public void setFoodPrice(int foodPrice) {
        this.foodPrice.set(foodPrice);
    }

    public static ObservableList<Food> getAllFoodData() {
        try {
            ObservableList<Food> foodData = FXCollections.observableArrayList();
            JSONArray jsonArrayFood = new JSONArray(FoodServerCommunication.getAllFood());
            for (int i = 0; i < jsonArrayFood.length(); i++) {
                Food f = new Food();
                f.setFoodId(jsonArrayFood.getJSONObject(i).getInt("id"));
                f.setFoodName(jsonArrayFood.getJSONObject(i).getString("name"));
                f.setFoodPrice(jsonArrayFood.getJSONObject(i).getInt("price"));
                foodData.add(f);
            }
            return foodData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ObservableList<Food> getFoodByReservationId(int id) {
        try {
            ObservableList<Food> foodData = FXCollections.observableArrayList();
            JSONArray jsonArrayFood = new JSONArray(FoodServerCommunication.getFoodByReservation(id));
            for (int i = 0; i < jsonArrayFood.length(); i++) {
                Food f = new Food();
                f.setFoodId(jsonArrayFood.getJSONObject(i).getInt("id"));
                f.setFoodName(jsonArrayFood.getJSONObject(i).getString("name"));
                f.setFoodPrice(jsonArrayFood.getJSONObject(i).getInt("price"));
                foodData.add(f);
            }
            return foodData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ObservableList<Food> getFoodByBuildingId(int id) {
        try {
            ObservableList<Food> foodData = FXCollections.observableArrayList();
            JSONArray jsonArrayFood = new JSONArray(FoodServerCommunication.getFoodByBuildingId(id));
            for (int i = 0; i < jsonArrayFood.length(); i++) {
                Food f = new Food();
                f.setFoodId(jsonArrayFood.getJSONObject(i).getInt("id"));
                f.setFoodName(jsonArrayFood.getJSONObject(i).getString("name"));
                f.setFoodPrice(jsonArrayFood.getJSONObject(i).getInt("price"));
                foodData.add(f);
            }
            return foodData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ObservableList<Food> getFoodByBuildingName(String name) {
        try {
            ObservableList<Food> foodData = FXCollections.observableArrayList();
            JSONArray jsonArrayFood = new JSONArray(FoodServerCommunication.getFoodByBuildingName(name));
            for (int i = 0; i < jsonArrayFood.length(); i++) {
                Food f = new Food();
                f.setFoodId(jsonArrayFood.getJSONObject(i).getInt("id"));
                f.setFoodName(jsonArrayFood.getJSONObject(i).getString("name"));
                f.setFoodPrice(jsonArrayFood.getJSONObject(i).getInt("price"));
                foodData.add(f);
            }
            return foodData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Food getFoodById(int id) {
        try {
            JSONObject jsonObject = new JSONObject(FoodServerCommunication.getFood(id));
            Food f = new Food();
            f.setFoodId(jsonObject.getInt("id"));
            f.setFoodName(jsonObject.getString("name"));
            f.setFoodPrice(jsonObject.getInt("price"));
            return f;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
