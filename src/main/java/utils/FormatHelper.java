package utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FormatHelper {
    private static DateTimeFormatter LOCAL_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static String formatLocalDateTime(LocalDateTime localDateTime){
        return localDateTime.format(LOCAL_DATE_TIME_FORMATTER);
    }
}
