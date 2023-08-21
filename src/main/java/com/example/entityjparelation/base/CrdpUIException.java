package com.example.entityjparelation.base;

public class CrdpUIException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CrdpUIException() {
        super();
    }

    public CrdpUIException(String message, Throwable cause) {
        super(message, cause);
    }

    public CrdpUIException(String message) {
        super(message);
    }

    public CrdpUIException(Throwable cause) {
        super(cause);
    }
}
