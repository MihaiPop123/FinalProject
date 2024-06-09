package com.example.FinalProject.exceptions;

public class PurchaseNotFoundException extends RuntimeException{

    public PurchaseNotFoundException(String message) {
        super(message);
    }
}