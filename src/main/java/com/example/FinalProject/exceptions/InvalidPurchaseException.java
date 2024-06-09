package com.example.FinalProject.exceptions;

public class InvalidPurchaseException extends RuntimeException{

    public InvalidPurchaseException(String message) {
        super(message);
    }
}
