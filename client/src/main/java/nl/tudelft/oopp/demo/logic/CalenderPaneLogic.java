package nl.tudelft.oopp.demo.logic;

import com.mindfusion.scheduling.model.Appointment;

import java.io.UnsupportedEncodingException;

import nl.tudelft.oopp.demo.communication.ItemServerCommunication;
import nl.tudelft.oopp.demo.communication.user.CurrentUserManager;
import nl.tudelft.oopp.demo.controllers.CalenderItemDialogController;

public class CalenderPaneLogic {
    public static Appointment makeItem() throws UnsupportedEncodingException {
        Appointment app = CalenderItemDialogController.item;
        // get date and time in correct format for database
        String date = app.getStartTime().getYear() + "-" + app.getStartTime().getMonth() + "-"
                + app.getStartTime().getDay();
        String startTime = app.getStartTime().getHour() + ":" + app.getStartTime().getMinute() + ":00";
        String endTime = app.getEndTime().getHour() + ":" + app.getEndTime().getMinute() + ":00";
        // send info to server
        ItemServerCommunication.createItem(CurrentUserManager.getUsername(), app.getHeaderText(), date,
                startTime, endTime, app.getDescriptionText());
        // get the id of the last inserted item to assign it to the Appointment object
        app.setId(String.valueOf(Integer.parseInt(ItemServerCommunication.getCurrentId()) - 1));
        return app;
    }
}
