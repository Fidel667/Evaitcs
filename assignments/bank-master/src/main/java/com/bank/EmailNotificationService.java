package com.bank;

import org.springframework.stereotype.Component;

// Selected explicitly with @Qualifier("emailNotification")
@Component("emailNotification")
public class EmailNotificationService implements NotificationService {

    @Override
    public void send(String message) {
        System.out.println("Email: " + message);
    }
}