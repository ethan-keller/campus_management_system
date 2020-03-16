package nl.tudelft.oopp.demo.calendar;

import com.mindfusion.common.DateTime;
import com.mindfusion.common.Duration;
import com.mindfusion.scheduling.Calendar;
import com.mindfusion.scheduling.CalendarView;
import com.mindfusion.scheduling.TimetableSettings;

public class CustomCalendar extends Calendar {

    private Calendar calendar;

    public CustomCalendar(){
        calendar = new Calendar();
        calendar.beginInit();
        TimetableSettings settings = calendar.getTimetableSettings();
        settings.getDates().clear();
        for (int i = 0; i < 7; i++)
            settings.getDates().add(DateTime.today().addDays(i));
        settings.setVisibleColumns(7);
        settings.setCellTime(Duration.fromHours(0.5));
        settings.setCellSize(30);
        settings.setShowCurrentTime(true);
        settings.setSelectWholeDayOnHeaderClick(true);
        settings.setShowNavigationButtons(true);
        calendar.endInit();

        calendar.setCurrentView(CalendarView.Timetable);
    }

    public Calendar getCalendar(){
        return this.calendar;
    }

}
