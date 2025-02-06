package com.example.portfolio_api1.exceptions;

public class NotFoundException extends RuntimeException
{
    public NotFoundException(String message) {
        super(message);
    }
}