package com.example.FinalProject.exceptions;

public class AuctionNotFoundException extends RuntimeException{

    public AuctionNotFoundException(String message) {
        super(message);
    }
}