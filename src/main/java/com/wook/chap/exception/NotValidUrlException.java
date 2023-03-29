package com.wook.chap.exception;

public class NotValidUrlException extends RuntimeException{
    public NotValidUrlException() {
    }

    public NotValidUrlException(String message) {
        super(message);
    }

    public NotValidUrlException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotValidUrlException(Throwable cause) {
        super(cause);
    }

    public NotValidUrlException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
