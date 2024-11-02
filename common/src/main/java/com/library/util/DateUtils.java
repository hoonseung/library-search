package com.library.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    public static LocalDate parseYYYYMMDD(String date) {
        return LocalDate.parse(date, DateTimeFormatter.BASIC_ISO_DATE);
    }

    public static LocalDate parseYYYYdashMMdashDD(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
    }
}
