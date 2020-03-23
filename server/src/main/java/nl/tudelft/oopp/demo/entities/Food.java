package nl.tudelft.oopp.demo.entities;

import javax.persistence.*;

@Entity
@Table(name = "food")
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private int price;

    public Food() {
    }

    /**
     * Constructor.
     *
     * @param id int
     * @param name String
     * @param price int
     */
    public Food(int id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    /**
     * Retrieves ID of the building from the database.
     *
     * @return Returns the int ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Retrieves the name of the food from the database.
     *
     * @return Returns the String name.
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the price of the food from the database.
     *
     * @return Returns the int value room_count.
     */
    public int getPrice() {
        return price;
    }

    /**
     * Equals
     *
     * @param o An Object to be compared to "this".
     * @return True if o is the same object, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Food)){
            return false;
        }

        Food temp = (Food)o;
        if(id != temp.getId()){
            return false;
        }

        return true;
    }
}