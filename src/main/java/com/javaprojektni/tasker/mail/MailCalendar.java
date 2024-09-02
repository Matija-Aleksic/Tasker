package com.javaprojektni.tasker.mail;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MailCalendar {
    public static String generateICS(String summary, String description, Date startDate, Date endDate, String location) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");

        String sb = "BEGIN:VCALENDAR\n" +
                "VERSION:2.0\n" +
                "PRODID:-//Your Organization//NONSGML v1.0//EN\n" +
                "BEGIN:VEVENT\n" +
                "UID:" + java.util.UUID.randomUUID() + "\n" +
                "DTSTAMP:" + sdf.format(new Date()) + "\n" +
                "DTSTART:" + dateFormat.format(startDate) + "\n" +
                "DTEND:" + dateFormat.format(endDate) + "\n" +
                "SUMMARY:" + summary + "\n" +
                "DESCRIPTION:" + description + "\n" +
                "LOCATION:" + location + "\n" +
                "END:VEVENT\n" +
                "END:VCALENDAR\n";

        return sb;
    }
}
