package com.telekom.phone.exception;

public class PhoneNotFoundException extends RuntimeException {
    public PhoneNotFoundException() {
        super("Phone not found");
    }

    public PhoneNotFoundException(String message) {
        super(message);
    }

    public PhoneNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PhoneNotFoundException(Throwable cause) {
        super(cause);
    }
}
