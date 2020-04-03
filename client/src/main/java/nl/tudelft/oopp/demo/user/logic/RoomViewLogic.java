package nl.tudelft.oopp.demo.user.logic;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import nl.tudelft.oopp.demo.entities.Food;
import nl.tudelft.oopp.demo.entities.Reservation;

public class RoomViewLogic {

    /**
     * Method that checks if the chosen timeslot is free.
     *
     * @return true if the timeslot is free, false otherwise
     */
    public static boolean checkTimeSlotValidity(double currentStartValue, double currentEndValue, double startValue, double endValue) {
            // check if the values overlap
            if (!((currentStartValue <= startValue && currentEndValue <= startValue)
                || (currentStartValue >= endValue && currentEndValue >= endValue))) {
            return false;
        }
        return true;
    }

    public static int compare(Reservation o1, Reservation o2){
        String[] o1StartSplit = o1.getStartingTime().get().split(":");
        int o1StartHour = Integer.parseInt(o1StartSplit[0]);
        int o1StartMinute = Integer.parseInt(o1StartSplit[1]);

        String[] o2StartSplit = o2.getStartingTime().get().split(":");
        int o2StartHour = Integer.parseInt(o2StartSplit[0]);
        int o2StartMinute = Integer.parseInt(o2StartSplit[1]);

        // compare hours and minutes
        if (o1StartHour < o2StartHour) {
            return -1;
        } else if (o1StartHour > o2StartHour) {
            return 1;
        }
        if (o1StartMinute < o2StartMinute) {
            return -1;
        } else {
            return 1;
        }
    }

    public static String toString(Number n){
        // calculate hours and remaining minutes to get a correct hh:mm format
        long minutes = n.longValue();
        long hours = TimeUnit.MINUTES.toHours(minutes);
        long remainingMinutes = minutes - TimeUnit.HOURS.toMinutes(hours);
        // '%02d' means that there will be a 0 in front if its only 1 number + it's a long number
        return String.format("%02d", hours) + ":" + String.format("%02d", remainingMinutes);
    }

    public static Number fromString(String time){
        if (time != null) {
            String[] split = time.split(":");
            return Double.parseDouble(split[0]) * 60 + Double.parseDouble(split[1]);
        }
        return null;
    }

    public static LocalDate fromString(String string, DateTimeFormatter dateFormatter){
        if (string != null && !string.isEmpty()) {
            // get correct LocalDate from String format
            return LocalDate.parse(string, dateFormatter);
        } else {
            return null;
        }
    }

    public static String toString(LocalDate date, DateTimeFormatter dateFormatter){
        if (date != null) {
            // get correctly formatted String
            return dateFormatter.format(date);
        } else {
            return "";
        }
    }

    public static Food fromString(String string, List<Food> foodList){
        if (string != null) {
            return foodList.stream()
                    .filter(x -> x.getFoodName().get().equals(string))
                    .collect(Collectors.toList()).get(0);
        } else {
            return null;
        }
    }

    public static String toString(Food object){
        if (object != null) {
            return object.getFoodName().get();
        } else {
            return null;
        }
    }

}
