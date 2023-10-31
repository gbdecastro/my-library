package com.gbdecastro.library.application.rest.hander.exception;

public class AuthorizationException extends RuntimeException {
    public AuthorizationException() {
        super();
    }

    public AuthorizationException(String msg) {
        super(msg);
    }
}
