package nl.tudelft.oopp.demo.user.calendar;

import com.mindfusion.scheduling.CalendarAdapter;
import com.mindfusion.scheduling.ItemMouseEvent;
import com.mindfusion.scheduling.model.Item;

import java.util.stream.Collectors;

import javafx.stage.Stage;
import javafx.stage.Window;

import nl.tudelft.oopp.demo.user.calendar.controller.CalendarItemDialogController;
import nl.tudelft.oopp.demo.views.CalendarItemDialog;

/**
 * Class that overrides calendar listener methods.
 */
public class CalendarListener extends CalendarAdapter {

    public static String header;
    public static String body;

    /**
     * When an item in the calendar gets clicked, a pop up with its information appears.
     * This is useful when the calendar is quite full.
     *
     * @param itemMouseEvent event that triggered this method
     */
    @Override
    public void itemClick(ItemMouseEvent itemMouseEvent) {
        try {
            // get the item that was clicked
            Item i = itemMouseEvent.getItem();
            // give that item to the controller
            CalendarItemDialogController.selectedItem = i;
            // set header and body attributes
            header = i.getHeaderText();
            body = i.getDescriptionText();
            // get the pop up
            CalendarItemDialog dialog = new CalendarItemDialog();
            // get current stage
            Stage stage = (Stage) Stage.getWindows().stream()
                    .filter(Window::isShowing)
                    .collect(Collectors.toList()).get(0);
            // start the view (pop up)
            dialog.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
