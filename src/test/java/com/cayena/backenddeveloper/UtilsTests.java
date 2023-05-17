package com.cayena.backenddeveloper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.cayena.backenddeveloper.utils.Utils.currentDate;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UtilsTests {

    @Test
    public void testCurrentDate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String expectedDate = now.format(formatter);

        String currentDate = currentDate();

        assertEquals(expectedDate, currentDate);
    }
}
