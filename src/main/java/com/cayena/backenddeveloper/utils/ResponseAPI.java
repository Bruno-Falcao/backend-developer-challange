package com.cayena.backenddeveloper.utils;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

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
     *
     * @param message
     * @param errors
     * @return
     */
    public static ResponseAPI getInstance(String message, String[] errors) {
        ResponseAPI instance = ResponseAPI.getInstance(message);
        instance.setError(errors);
        return instance;
    }

    public static ResponseAPI getInstance(String message, Exception ex) {
        ResponseAPI instance = getInstance(message,
                Arrays.stream(ex.getSuppressed())
                        .map(e -> e.getMessage())
                        .toArray(String[]::new));

        if (Objects.requireNonNull(ex.getCause().getMessage()).isEmpty()) {
            ArrayList<String> erros = new ArrayList<>(Arrays.asList(instance.getError()));
            erros.add(ex.getCause().getMessage());
            erros.sort(Comparator.reverseOrder());
            instance.setError(erros.stream().toArray(String[]::new));
        }

        return instance;
    }

    public static ResponseAPI getInstance(List<Object> data) {
        ResponseAPI instance = ResponseAPI.getInstance("");
        instance.setData(data);
        return instance;
    }

    public static ResponseAPI getInstance(String message, List<Object> data) {
        if (instance == null) {
            instance = new ResponseAPI();
        }
        instance.setMessage(message);
        instance.setError(new String[0]);
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