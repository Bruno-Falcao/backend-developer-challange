package com.cayena.backenddeveloper.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class Utils {

    public static LocalDateTime currentDate() {
        LocalDateTime creationDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        return LocalDateTime.parse(creationDate.format(formatter));
    }

    public static ResponseEntity<Object> errorResponse(String errorMsg, Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseAPI.getInstance(String.format(errorMsg, ex.getMessage()),
                        Arrays.stream(ex.getSuppressed()).map(Throwable::getMessage)
                                .toArray(String[]::new)));
    }
}
