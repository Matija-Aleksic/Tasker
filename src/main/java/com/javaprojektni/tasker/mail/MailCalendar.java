package com.javaprojektni.tasker.mail;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MailCalendar {
    public static String generateICS(String summary, String description, Date startDate, Date endDate, String location) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");

        StringBuilder sb = new StringBuilder();
        sb.append("BEGIN:VCALENDAR\n");
        sb.append("VERSION:2.0\n");
        sb.append("PRODID:-//Your Organization//NONSGML v1.0//EN\n");
        sb.append("BEGIN:VEVENT\n");
        sb.append("UID:" + java.util.UUID.randomUUID().toString() + "\n");
        sb.append("DTSTAMP:" + sdf.format(new Date()) + "\n");
        sb.append("DTSTART:" + dateFormat.format(startDate) + "\n");
        sb.append("DTEND:" + dateFormat.format(endDate) + "\n");
        sb.append("SUMMARY:" + summary + "\n");
        sb.append("DESCRIPTION:" + description + "\n");
        sb.append("LOCATION:" + location + "\n");
        sb.append("END:VEVENT\n");
        sb.append("END:VCALENDAR\n");

        return sb.toString();
    }
}
