package org.example.app.exceptions;

public class BookShelfLoginException extends Exception {

    private final String msg;
    public BookShelfLoginException(String msg) {
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return msg;
    }
}
