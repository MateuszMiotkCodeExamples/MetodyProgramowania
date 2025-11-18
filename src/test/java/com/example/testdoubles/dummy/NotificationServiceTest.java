package com.example.testdoubles.dummy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test wykorzystujący Dummy
 */
class NotificationServiceTest {

    @Test
    void shouldValidateEmailBeforeSending() {
        // Arrange - Dummy dla nieużywanych zależności
        SmsService dummySms = new DummySmsService();
        AuditLogger dummyLogger = new DummyAuditLogger();
        
        // Stub dla EmailService (rzeczywista zależność w tym teście)
        EmailService emailStub = new EmailService() {
            @Override
            public void sendEmail(String recipient, String subject, String body) {
                // Minimalna implementacja dla testu
            }
            
            @Override
            public boolean validateEmailAddress(String email) {
                return email.contains("@");
            }
        };
        
        NotificationService service = new NotificationService(
            emailStub, dummySms, dummyLogger);
        
        // Act & Assert
        assertTrue(service.notifyByEmail("user@example.com", "Hello"));
        assertFalse(service.notifyByEmail("invalid-email", "Hello"));
    }
}

