package fpt.edu.capstone.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {
    private static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm";

    public static String convertLocalDateTimeToString(String s){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATETIME_PATTERN);
        LocalDateTime dateTime = LocalDateTime.parse(s, formatter);
        return dateTime.toString();
    }
}
