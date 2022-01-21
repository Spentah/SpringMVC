package org.example.app.exceptions;

public class FileException extends Exception{

    private final String msg;

    public FileException(String msg) {
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return msg;
    }
}
