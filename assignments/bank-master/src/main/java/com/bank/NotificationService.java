package com.bank;

// ===================================================
// NotificationService - Notification Interface
// We have two types: SMS and Email
// Here we use @Primary and @Qualifier
// ===================================================

public interface NotificationService {
    void send(String message);
}