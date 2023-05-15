package com.cayena.backenddeveloper.exceptions;

import java.time.DateTimeException;

public class ExpirationDateException extends DateTimeException {
    public ExpirationDateException(String message) {
        super(message);
    }
}
