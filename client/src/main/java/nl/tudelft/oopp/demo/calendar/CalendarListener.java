package nl.tudelft.oopp.demo.calendar;

import com.mindfusion.scheduling.CalendarAdapter;
import com.mindfusion.scheduling.ItemMouseEvent;
import com.mindfusion.scheduling.model.Item;

import java.util.stream.Collectors;

import javafx.stage.Stage;
import javafx.stage.Window;

import nl.tudelft.oopp.demo.admin.controller.CalendarItemDialogController;
import nl.tudelft.oopp.demo.views.CalendarItemDialog;

/**
 * Class that overrides calendar listener methods.
 */
public class CalendarListener extends CalendarAdapter {

    public static String header;
    public static String body;
    private ItemMouseEvent itemMouseEvent;

    /**
     * When an item in the calendar gets clicked it shows a pop up with the information.
     * This is useful when the calendar is quite full.
     * @param itemMouseEvent event that triggered this method
     */
    @Override
    public void itemClick(ItemMouseEvent itemMouseEvent) {
        try {
            // get the item that was clicked
            Item i = itemMouseEvent.getItem();
            // gie that item to the controller
            CalendarItemDialogController.selectedItem = i;
            // give header and body for the pop up
            header = i.getHeaderText();
            body = i.getDescriptionText();
            // open the new dialog box
            CalendarItemDialog dialog = new CalendarItemDialog();
            // get current stage
            Stage stage = (Stage) Stage.getWindows().stream()
                    .filter(Window::isShowing)
                    .collect(Collectors.toList()).get(0);
            dialog.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
