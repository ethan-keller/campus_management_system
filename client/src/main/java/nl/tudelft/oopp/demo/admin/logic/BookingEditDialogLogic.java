package nl.tudelft.oopp.demo.admin.logic;

import java.util.logging.Logger;


public class BookingEditDialogLogic {

    private static Logger logger = Logger.getLogger("GlobalLogger");

    /**
     * Validates the user input.
     *
     * @return true if the input is valid
     */
    public static String isInputValid(int index, String bookingDate) {

        while (true) {
            // Checks whether a room from the combo box is selected and the index represents if the
            // selection of the room is valid.
            if (index < 0) {
                return "No valid room selected!\n";
            }
            // Checks whether a date from the datePicker is selected
            if (bookingDate.equals("")) {
                return  "No valid date selected!\n";
            } else {
                // This string value means that all the fields are filled.
                return "Good!\n";
            }
        }

    }


}

