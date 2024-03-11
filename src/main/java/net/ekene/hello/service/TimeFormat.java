package net.ekene.hello.service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.Locale;

public class TimeFormat {

    public static void main(String[] args) {
        System.out.println(formatTime(Instant.now()));
    }



    private static String formatTime(Instant time) {
        ZonedDateTime zonedDateTime = time.atZone(ZoneId.of("Africa/Lagos"));

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);
        String formattedDate = dateFormat.format(Date.from(zonedDateTime.toInstant()));

        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.US);
        String formattedTime = timeFormat.format(Date.from(zonedDateTime.toInstant()));

        return formattedDate + formattedTime;
    }
}
