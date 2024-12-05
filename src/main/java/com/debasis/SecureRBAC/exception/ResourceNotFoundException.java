package com.debasis.SecureRBAC.exception;

public class ResourceNotFoundException extends CustomException{

    public ResourceNotFoundException(String message) {
        super(message, 404);
    }
}
