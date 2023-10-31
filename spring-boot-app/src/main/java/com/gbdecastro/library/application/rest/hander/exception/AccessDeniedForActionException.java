package com.gbdecastro.library.application.rest.hander.exception;

public class AccessDeniedForActionException extends RuntimeException {
    public AccessDeniedForActionException() {
        super();
    }

    public AccessDeniedForActionException(String msg) {
        super(msg);
    }
}
