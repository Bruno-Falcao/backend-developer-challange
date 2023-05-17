package com.cayena.backenddeveloper.utils;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class ResponseAPI {

    @JsonProperty("data")
    private List<Object> data;

    private String message;
    private String[] error;

    private static ResponseAPI instance;

    /**
     * Gets a singleton instance of the ResponseApi with a specified message
     *
     * @param message to be set in the response
     * @return A singleton instance of ResponseApi
     */
    public static ResponseAPI getInstance(String message) {
        if (instance == null) {
            instance = new ResponseAPI();
        }
        instance.setMessage(message);
        instance.setError(new String[0]);
        instance.setData(new ArrayList<>());
        return instance;
    }

    /**

     Creates a new instance of ResponseAPI with the specified message and errors.
     @param message The message to be set in the ResponseAPI instance.
     @param errors The array of errors to be set in the ResponseAPI instance.
     @return ResponseAPI instance with the specified message and errors.
     */
    public static ResponseAPI getInstance(String message, String[] errors) {
        ResponseAPI instance = ResponseAPI.getInstance(message);
        instance.setError(errors);
        return instance;
    }

    public static ResponseAPI getInstance(List<Object> data) {
        ResponseAPI instance = ResponseAPI.getInstance("");
        instance.setData(data);
        return instance;
    }

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String[] getError() {
        return error;
    }

    public void setError(String[] error) {
        this.error = error;
    }

    public static ResponseAPI getInstance() {
        return instance;
    }

    public static void setInstance(ResponseAPI instance) {
        ResponseAPI.instance = instance;
    }
}