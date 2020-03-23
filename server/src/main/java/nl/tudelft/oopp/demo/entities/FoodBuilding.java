package nl.tudelft.oopp.demo.entities;

import javax.persistence.*;

@Entity
@Table(name = "foodBuilding")
public class FoodBuilding {
    @Id
    @Column(name = "building")
    private int building;

    @Id
    @Column(name = "food")
    private int food;

    public FoodBuilding() {
    }

    /**
     * Constructor.
     *
     * @param building
     * @param food
     */
    public FoodBuilding(int building, int food) {
        this.building = building;
        this.food = food;
    }

    /**
     * Retrieves ID of the building from the database.
     *
     * @return Returns the int ID.
     */
    public int getBuilding() {
        return building;
    }


    /**
     * Retrieves the id of the food from the database.
     *
     * @return Returns the String name.
     */
    public int getFood() {
        return food;
    }


    /**
     * Equals
     *
     * @param o An Object to be compared to "this".
     * @return True if o is the same object, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if(!(o instanceof FoodBuilding)){
            return false;
        }

        FoodBuilding temp = (FoodBuilding) o;
        if(building != temp.getBuilding()){
            return false;
        } if (food != temp.getFood()){
            return false;
        }
        return true;
    }
}