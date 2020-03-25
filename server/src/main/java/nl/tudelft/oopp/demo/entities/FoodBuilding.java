package nl.tudelft.oopp.demo.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class FoodBuilding implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn
    @JsonBackReference
    private Food food;

    @Id
    @ManyToOne
    @JoinColumn
    @JsonBackReference
    private Building building;

    public FoodBuilding() {}

    public FoodBuilding(Food food, Building building) {
        this.food = food;
        this.building = building;
    }

    public Food getFood() {
        return food;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public boolean equals(Object o) {
        if(!(o instanceof FoodBuilding)){
            return false;
        }
        FoodBuilding temp = (FoodBuilding)o;
        if(food.getId() != temp.getFood().getId())
            return false;
        if(building.getId() != temp.getBuilding().getId())
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(food.getId(), building.getId());
    }
}
