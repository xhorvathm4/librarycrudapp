package com.mhexercise.librarycrud.exception;

public class BookException extends RuntimeException{
    public BookException(String message) {
        super(message);
    }

    public BookException(Throwable cause) {
        super(cause);
    }

    public BookException(String message, Throwable cause) {
        super(message, cause);
    }
}
