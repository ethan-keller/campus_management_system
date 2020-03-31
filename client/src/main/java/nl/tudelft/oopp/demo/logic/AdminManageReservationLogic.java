package nl.tudelft.oopp.demo.logic;

import java.io.UnsupportedEncodingException;
import javafx.scene.control.TableView;
import nl.tudelft.oopp.demo.communication.ReservationServerCommunication;
import nl.tudelft.oopp.demo.entities.Reservation;


public class AdminManageReservationLogic {

    /**.
     * This method is used to select a reservation from the tabular view of the reservations
     * Constraints are added; if the reservation index is less than 0, null is returned.
     * @param reservationTableView - TableView of Reservations
     * @return - Reservation
     */
    public static Reservation getSelectedReservation(TableView<Reservation> reservationTableView) {
        // If reservation selection is valid:
        if (reservationTableView.getSelectionModel().getSelectedIndex() >= 0) {
            // Returns the item ( of type Reservation ) back to the user.
            return reservationTableView.getSelectionModel().getSelectedItem();
        } else {
            // If no item is selected, then null is returned.
            return null;
        }
    }

    /**.
     * This method is used in the adminManageReservationViewController class to communicate with the server to
     * command them to delete the selected reservation.
     * @param selectedReservation - The selected reservation from the tabular view passed as a parameter.
     */
    public static void deleteReservationLogic(Reservation selectedReservation) {
        try {
            ReservationServerCommunication.deleteReservation(selectedReservation.getId().getValue());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**.
     * This method is used in adminManageReservationViewController class to communicate with the server to
     *  command them to create a new reservation.
     * @param tempReservation - The features of the new reservation passed as parameter.
     */
    public static void createReservationLogic(Reservation tempReservation) {
        try {
            ReservationServerCommunication.createReservation(tempReservation.getUsername().get(),
                    tempReservation.getRoom().get(), tempReservation.getDate().get(),
                    tempReservation.getStartingTime().get(), tempReservation.getEndingTime().get());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**.
     * This method is used in the adminManageReservationViewController class to communicate with the server to
     * command them to edit the selected reservation.
     * @param selectedReservation - This is used to get the id of the selected reservation.
     * @param tempReservation - These are the edited features of the reservation object passed as parameter.
     */
    public static void editReservationLogic(Reservation selectedReservation, Reservation tempReservation) {
        try {
            ReservationServerCommunication.updateReservation(selectedReservation.getId().get(),
                    tempReservation.getRoom().get(), tempReservation.getDate().get(),
                    tempReservation.getStartingTime().get(), tempReservation.getEndingTime().get());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
