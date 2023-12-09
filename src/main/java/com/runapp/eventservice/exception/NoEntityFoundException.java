package com.runapp.eventservice.exception;

public class NoEntityFoundException extends RuntimeException {
    public NoEntityFoundException(String message) {
        super(message);
    }
}
