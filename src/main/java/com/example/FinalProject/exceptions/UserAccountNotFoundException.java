package com.example.FinalProject.exceptions;

public class UserAccountNotFoundException extends RuntimeException{

    public UserAccountNotFoundException(String message) {
        super(message);
    }
}
