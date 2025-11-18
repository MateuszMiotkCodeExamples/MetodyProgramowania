package com.example.testdoubles.dummy;

/**
 * Interfejs logowania zdarzeń
 */
public interface AuditLogger {
    void logEvent(String eventType, String details);
}

