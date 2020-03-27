package nl.tudelft.oopp.demo.calendar;

import com.mindfusion.common.DateTime;
import com.mindfusion.common.Duration;
import com.mindfusion.scheduling.Calendar;
import com.mindfusion.scheduling.CalendarView;
import com.mindfusion.scheduling.TimetableSettings;

/**.
 * Basic class that customizes the general Calendar object
 */
public class CustomCalendar extends Calendar {

    /**.
     * Constructor
     */
    public CustomCalendar() {

        // Between beginInit() and endInit() the calendar can get configured
        this.beginInit();

        TimetableSettings settings = this.getTimetableSettings();
        settings.getDates().clear();
        // create a 7 day view
        for (int i = 0; i < 7; i++) {
            settings.getDates().add(DateTime.today().addDays(i));
        }
        // make all 7 days visible
        settings.setVisibleColumns(7);
        // make cell size/time 30 minutes
        settings.setCellTime(Duration.fromHours(0.5));
        settings.setCellSize(30);
        // show the current time (gets shown as orange line)
        settings.setShowCurrentTime(true);
        // grant ability to select whole day when clicking the header
        settings.setSelectWholeDayOnHeaderClick(true);
        // grant the ability to navigate through different weeks or months
        settings.setShowNavigationButtons(true);
        // skip 7 days when clicking the arrows (switch weeks)
        settings.setScrollStep(7);
        // disallow ability to edit items in calendar
        this.setAllowInplaceEdit(false);
        // when selecting near the border of the calendar, it auto scrolls
        this.setAllowAutoScroll(true);

        // end of initialization
        this.endInit();

        // set the 'standard' view of the calendar to week view
        this.setCurrentView(CalendarView.Timetable);
    }

}
