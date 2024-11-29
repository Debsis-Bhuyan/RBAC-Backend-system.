package com.debasis.SecureRBAC.exception;

public class InvalidCredentialsException extends CustomException{
    public InvalidCredentialsException(String message) {
        super(message, 401);
    }
}
