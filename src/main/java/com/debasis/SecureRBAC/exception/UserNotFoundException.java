package com.debasis.SecureRBAC.exception;

public class UserNotFoundException extends CustomException{
    public UserNotFoundException(String message) {
        super(message, 404);
    }
}
