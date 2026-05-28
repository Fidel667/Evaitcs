package com.example.expensetracker.service;

public class AccountInUseException extends RuntimeException {
    public AccountInUseException(String message) {
        super(message);
    }
}
