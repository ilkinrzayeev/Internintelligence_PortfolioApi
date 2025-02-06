package com.example.portfolio_api1.exceptions;

public class AlreadyExistsException extends RuntimeException
{
    public AlreadyExistsException(String message) {
        super(message);
    }
}
