package com.cayena.backenddeveloper.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {

    /**
     * Retrieves the current date and time in a formatted string.
     *
     * @return The current date and time as a formatted string in the brazilian "dd/MM/yyyy HH:mm:ss" pattern.
     */
    public static String currentDate() {
        LocalDateTime creationDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        return creationDate.format(formatter);
    }
}
