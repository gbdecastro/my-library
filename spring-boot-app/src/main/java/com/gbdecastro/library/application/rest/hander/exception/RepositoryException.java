package com.gbdecastro.library.application.rest.hander.exception;

public class RepositoryException extends RuntimeException {

    public RepositoryException() {
        super();
    }

    public RepositoryException(String msg) {
        super(msg);
    }

    public RepositoryException(Exception ex) {
        super(ex);
    }

    public RepositoryException(String msg, Exception ex) {
        super(msg, ex);
    }

}
