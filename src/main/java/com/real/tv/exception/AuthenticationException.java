package com.real.tv.exception;

/**
 * Custom exception to handle authentication errors
 */
public class AuthenticationException extends RuntimeException {

    public AuthenticationException(String message) {
        super(message);
    }
}
