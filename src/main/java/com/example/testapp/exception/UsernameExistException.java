package com.example.testapp.exception;

public class UsernameExistException extends Exception{
    public UsernameExistException(String message) {
        super(message);
    }
}
