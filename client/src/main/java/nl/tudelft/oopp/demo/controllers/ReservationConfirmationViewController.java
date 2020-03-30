package nl.tudelft.oopp.demo.controllers;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.user.CurrentUserManager;
import nl.tudelft.oopp.demo.entities.Food;
import nl.tudelft.oopp.demo.entities.Room;

/**
 * Class that controls the dialog pop up that asks for a reservation confirmation.
 */
public class ReservationConfirmationViewController implements Initializable {

    @FXML
    private Text confirmationText;

    // current Room
    public static Room room;

    // reservation information
    public static String date;
    public static String startTime;
    public static String endTime;
    public static boolean foodChosen;
    public static List<Food> foodList;
    public static Map<Food, Integer> foodMap;

    // confirmation state
    public static boolean confirmed = false;

    /**
     * Method that gets called before everything (mostly to initialize nodes etc.).

     * JavaFX standard.
     *
     * @param location  is passed
     * @param resources is passed
     */
    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        confirmed = false;
        String confirmationString = getConfirmationString();

        confirmationText.setText(confirmationString);
    }

    private String getConfirmationString() {
        double totalPrice = 0.0;

        String confirmationString = "You (" + CurrentUserManager.getUsername() + ") would like to book the "
                + room.getRoomName().get() + " on " + date + " from " + startTime + " until " + endTime + ".\n"
                + "You ordered:\n\n";
        for (Food f : foodList) {
            double foodPrice = f.getFoodPrice().get();
            String price = formatPriceString(foodPrice);
            int amount = foodMap.get(f);
            totalPrice += (((double) amount) * foodPrice);
            confirmationString += "- " + amount + "x " + f.getFoodName().get()
                    + "            (" + amount + "x " + price + " euro(s))\n";
        }
        confirmationString += "\nTotal price: " + formatPriceString(totalPrice) + " euro(s)";
        confirmationString += "\nWould you like to confirm that?\n";

        return confirmationString;
    }

    private String formatPriceString(double foodPrice){
        String[] splitPrice = String.valueOf(foodPrice).split("\\.");

        splitPrice[1] = String.valueOf((Math.round(Double.parseDouble(splitPrice[1]))));

        while (splitPrice[1].length() < 2){
            splitPrice[1] += "0";
        }

        return splitPrice[0] + "." + splitPrice[1];
    }

    /**
     * When user clicks 'confirm' reservation goes through.
     *
     * @param event event that triggered this method
     */
    @FXML
    private void confirmClicked(ActionEvent event) {
        // set confirmed state
        confirmed = true;

        // close current stage
        Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        thisStage.close();
    }

    /**
     * When user clicks 'cancel' reservation does not go through.
     *
     * @param event event that triggered this method
     */
    @FXML
    private void cancelClicked(ActionEvent event) {
        // set confirmed state
        confirmed = false;

        // close current stage
        Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        thisStage.close();
    }
}
