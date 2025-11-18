package com.example.testdoubles.dummy;

/**
 * Interfejs serwisu email wymagany przez NotificationService
 */
public interface EmailService {
    void sendEmail(String recipient, String subject, String body);
    boolean validateEmailAddress(String email);
}

