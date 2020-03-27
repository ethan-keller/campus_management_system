package nl.tudelft.oopp.demo.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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

    /**
     * Empty contructor.
     */
    public FoodBuilding() {
    }

    /**
     * Constructor to initialize the variables.
     * @param food The Food entity
     * @param building The Building entity
     */
    public FoodBuilding(Food food, Building building) {
        this.food = food;
        this.building = building;
    }

    /**
     * Returns Food entity.
     * @return Returns Food entity
     */
    public Food getFood() {
        return food;
    }

    /**
     * Sets the Food variable.
     * @param food The new Food entity
     */
    public void setFood(Food food) {
        this.food = food;
    }

    /**
     * Returns Building entity.
     * @return Returns building entity
     */
    public Building getBuilding() {
        return building;
    }

    /**
     * Sets the Building variable.
     * @param building The new Building entity
     */
    public void setBuilding(Building building) {
        this.building = building;
    }

    /**
     * The equals method.
     * @param o The object to compare 'this' to
     * @return Returns a boolean
     */
    public boolean equals(Object o) {
        if (!(o instanceof FoodBuilding)) {
            return false;
        }
        FoodBuilding temp = (FoodBuilding)o;
        if (food.getId() != temp.getFood().getId()) {
            return false;
        }
        if (building.getId() != temp.getBuilding().getId()) {
            return false;
        }
        return true;
    }

    /**
     * The hash method.
     * @return Returns a unique hashcode for the object
     */
    @Override
    public int hashCode() {
        return Objects.hash(food.getId(), building.getId());
    }
}
