package nl.tudelft.oopp.demo.views;


import com.mindfusion.common.DateTime;
import com.mindfusion.common.Duration;
import com.mindfusion.scheduling.Calendar;
import com.mindfusion.scheduling.CalendarView;
import com.mindfusion.scheduling.model.Appointment;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame
{
    private Calendar calendar;

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> {
            try {
                new MainWindow().setVisible(true);
            }
            catch (Exception exp) {
            }
        });
    }

    protected MainWindow()
    {
        calendar = new Calendar();
        calendar.beginInit();
        calendar.getTimetableSettings().getDates().clear();
        for (int i = 0; i < 5; i++)
            calendar.getTimetableSettings().getDates().add(DateTime.today().addDays(i));
        calendar.getTimetableSettings().setVisibleColumns(7);
        calendar.getTimetableSettings().setCellTime(Duration.fromHours(0.5));
        calendar.getTimetableSettings().setCellSize(30);
        calendar.getTimetableSettings().setShowCurrentTime(true);
        calendar.getTimetableSettings().setSelectWholeDayOnHeaderClick(true);
        calendar.endInit();

        calendar.setCurrentView(CalendarView.Timetable);

        Appointment app = new Appointment();
        app.setHeaderText("Meet George");
        app.setDescriptionText("This is a sample appointment");
        app.setStartTime(new DateTime(2020, 3, 17, 14, 0, 0));
        app.setEndTime(new DateTime(2020, 3, 17, 16, 30, 0));

        calendar.getSchedule().getItems().add(app);


        getContentPane().add(calendar, BorderLayout.CENTER);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 400);
        setTitle("Tutorial 1");
    }


    private static final long serialVersionUID = 1L;
}