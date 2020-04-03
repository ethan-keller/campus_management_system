package nl.tudelft.oopp.demo.admin.logic;

import nl.tudelft.oopp.demo.admin.logic.BookingEditDialogLogic;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookingEditDialogLogicTest {

    @Test
    void isValidInputTest() {
        // Checks whether a room is selected
        assertEquals("No valid room selected!\n", BookingEditDialogLogic.isInputValid(-1,
                "2020-08-30"));
        // Checks whether a time slot is selected
        assertEquals("No valid date selected!\n", BookingEditDialogLogic.isInputValid(2,
                null));
    }

    @Test
    void allIsGoodTest() {
        // Checks whether with valid input, it returns expected results
        assertEquals("Good!\n", BookingEditDialogLogic.isInputValid(3, "2020-04-21"));
    }

    @Test
    void sortTest() {
        // Can someone help with this particular method.
    }
}
