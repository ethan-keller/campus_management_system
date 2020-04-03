package nl.tudelft.oopp.demo.user.calendar.logic;

import com.mindfusion.common.DateTime;

import java.awt.Color;

public interface AbstractCalendarItem {

    String getId();

    String getHeader();

    DateTime getStartTime();

    DateTime getEndTime();

    String getDescription();

    Color getColor();

}
