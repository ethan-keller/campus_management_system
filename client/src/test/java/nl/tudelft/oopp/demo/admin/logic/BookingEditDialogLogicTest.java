package nl.tudelft.oopp.demo.admin.logic;

import nl.tudelft.oopp.demo.admin.logic.BookingEditDialogLogic;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookingEditDialogLogicTest {

    @Test
    void isValidInputTest() {


        assertEquals("No valid room selected!\n", BookingEditDialogLogic.isInputValid(null,
                null));
    }
}
