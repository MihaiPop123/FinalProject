package com.example.FinalProject.exceptions;

public class AccountTypeNotFoundException extends RuntimeException {
    public AccountTypeNotFoundException(String message) {
        super(message);
    }
}
