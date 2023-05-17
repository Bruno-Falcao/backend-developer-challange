package com.cayena.backenddeveloper;

import com.cayena.backenddeveloper.utils.ResponseAPI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ResponseAPITests {

    private ResponseAPI instance;

    @BeforeEach
    public void setUp() {
        instance = null;
    }

    @Test
    public void getInstanceWithMessageShouldReturnResponseAPIWithMessage() {
        String message = "Success Message";
        instance = ResponseAPI.getInstance(message);

        Assertions.assertEquals(message, instance.getMessage());
        Assertions.assertEquals(0, instance.getError().length);
        Assertions.assertEquals(0, instance.getData().size());
    }

    @Test
    public void getInstanceWithMessageAndErrorsShouldReturnResponseAPIWithMessageAndErrors() {
        String message = "Generic Message";
        String[] errors = {"Error 45", "Error 70"};
        instance = ResponseAPI.getInstance(message, errors);

        Assertions.assertEquals(message, instance.getMessage());
        Assertions.assertArrayEquals(errors, instance.getError());
        Assertions.assertEquals(0, instance.getData().size());
    }

    @Test
    public void getInstanceWithDataShouldReturnResponseAPIWithData() {
        List<Object> data = new ArrayList<>();
        data.add("Data 1");
        data.add("Data 2");
        instance = ResponseAPI.getInstance(data);

        Assertions.assertEquals("", instance.getMessage());
        Assertions.assertEquals(0, instance.getError().length);
        Assertions.assertEquals(data, instance.getData());
    }
}
