package com.bank;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

// Default - used when no @Qualifier is specified
@Component("smsNotification")
@Primary
public class SmsNotificationService implements NotificationService {

    @Override
    public void send(String message) {
        System.out.println("SMS: " + message);
    }
}