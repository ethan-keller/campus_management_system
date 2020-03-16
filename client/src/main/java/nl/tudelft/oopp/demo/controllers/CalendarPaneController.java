package nl.tudelft.oopp.demo.controllers;

import com.mindfusion.common.ScrollEvent;
import com.mindfusion.scheduling.*;
import com.mindfusion.scheduling.model.ItemEvent;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import nl.tudelft.oopp.demo.calendar.CustomCalendar;

import javax.swing.*;
import java.net.URL;
import java.util.EventObject;
import java.util.ResourceBundle;

public class CalendarPaneController implements Initializable {

    @FXML
    private AnchorPane pane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SwingNode node = new SwingNode();
        configureNode(node);
        pane.getChildren().add(node);
    }

    private void configureNode(SwingNode node) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Calendar c = new CustomCalendar().getCalendar();
                c.setSize((int) pane.getScene().getWindow().getWidth(), (int) pane.getScene().getWindow().getHeight());
                c.addCalendarListener(new CalendarListener() {
                    @Override
                    public void visibleDateChanged(DateChangedEvent dateChangedEvent) {

                    }

                    @Override
                    public void scroll(EventObject eventObject) {

                    }

                    @Override
                    public void dateClick(ResourceDateEvent resourceDateEvent) {

                    }

                    @Override
                    public void monthCellClick(DateEvent dateEvent) {

                    }

                    @Override
                    public void hiddenItemClick(DateEvent dateEvent) {

                    }

                    @Override
                    public void itemClick(ItemMouseEvent itemMouseEvent) {
                        
                    }

                    @Override
                    public void itemDeleting(ItemConfirmEvent itemConfirmEvent) {

                    }

                    @Override
                    public void recurringItemDeleting(RecurringItemConfirmEvent recurringItemConfirmEvent) {

                    }

                    @Override
                    public void itemDeleted(ItemEvent itemEvent) {

                    }

                    @Override
                    public void itemInplaceEditStarting(ItemConfirmEvent itemConfirmEvent) {

                    }

                    @Override
                    public void itemInplaceEditEnding(ItemEditConfirmEvent itemEditConfirmEvent) {

                    }

                    @Override
                    public void itemInplaceEdited(ItemEvent itemEvent) {

                    }

                    @Override
                    public void itemCreating(ItemConfirmEvent itemConfirmEvent) {

                    }

                    @Override
                    public void itemCreated(ItemEvent itemEvent) {

                    }

                    @Override
                    public void itemCloning(ItemConfirmEvent itemConfirmEvent) {

                    }

                    @Override
                    public void itemCloned(ItemEvent itemEvent) {

                    }

                    @Override
                    public void itemModifying(ItemModifyConfirmEvent itemModifyConfirmEvent) {

                    }

                    @Override
                    public void itemModified(ItemModifiedEvent itemModifiedEvent) {

                    }

                    @Override
                    public void itemModificationCanceled(ItemEvent itemEvent) {

                    }

                    @Override
                    public void itemCreationCanceled(ItemEvent itemEvent) {

                    }

                    @Override
                    public void monthHeaderClick(DateEvent dateEvent) {

                    }

                    @Override
                    public void monthWeekHeaderClick(DateEvent dateEvent) {

                    }

                    @Override
                    public void monthRangeHeaderClick(DateEvent dateEvent) {

                    }

                    @Override
                    public void listViewHeaderClick(DateEvent dateEvent) {

                    }

                    @Override
                    public void weekRangeHeaderClick(DateEvent dateEvent) {

                    }

                    @Override
                    public void timetableColumnHeaderClick(DateEvent dateEvent) {

                    }

                    @Override
                    public void itemDrawing(CalendarDrawEvent calendarDrawEvent) {

                    }

                    @Override
                    public void beginItemDrawing(EventObject eventObject) {

                    }

                    @Override
                    public void endItemDrawing(EventObject eventObject) {

                    }

                    @Override
                    public void drawing(CalendarDrawEvent calendarDrawEvent) {

                    }

                    @Override
                    public void draw(CalendarDrawEvent calendarDrawEvent) {

                    }

                    @Override
                    public void customizeText(CalendarTextEvent calendarTextEvent) {

                    }

                    @Override
                    public void itemTooltipDisplaying(ItemTooltipEvent itemTooltipEvent) {

                    }

                    @Override
                    public void tooltipDisplaying(TooltipEvent tooltipEvent) {

                    }

                    @Override
                    public void resourceViewTopTimelineClick(DateEvent dateEvent) {

                    }

                    @Override
                    public void resourceViewMiddleTimelineClick(DateEvent dateEvent) {

                    }

                    @Override
                    public void resourceViewBottomTimelineClick(DateEvent dateEvent) {

                    }

                    @Override
                    public void resourceViewRowHeaderClick(ResourceDateEvent resourceDateEvent) {

                    }

                    @Override
                    public void itemSelectionChanged(ItemSelectionEvent itemSelectionEvent) {

                    }

                    @Override
                    public void itemSelectionComplete(EventObject eventObject) {

                    }

                    @Override
                    public void itemSelecting(ItemConfirmEvent itemConfirmEvent) {

                    }

                    @Override
                    public void itemDeselecting(ItemConfirmEvent itemConfirmEvent) {

                    }

                    @Override
                    public void hScroll(ScrollEvent scrollEvent) {

                    }

                    @Override
                    public void vScroll(ScrollEvent scrollEvent) {

                    }

                    @Override
                    public void themeChanged(EventObject eventObject) {

                    }

                    @Override
                    public void itemListLaneChanged(ItemListLaneChangedEvent itemListLaneChangedEvent) {

                    }

                    @Override
                    public void itemsModified(ItemsEvent itemsEvent) {

                    }

                    @Override
                    public void customizeGrouping(CustomizeGroupingEvent customizeGroupingEvent) {

                    }

                    @Override
                    public void filterItem(ItemConfirmEvent itemConfirmEvent) {

                    }

                    @Override
                    public void timeLineUnitsChanged(EventObject eventObject) {

                    }
                });
                node.setContent(c);
            }
        });
    }
}
