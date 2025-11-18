package com.example.testdoubles.dummy;

/**
 * Implementacja Dummy dla AuditLogger
 */
class DummyAuditLogger implements AuditLogger {
    @Override
    public void logEvent(String eventType, String details) {
        throw new UnsupportedOperationException(
            "Dummy: logEvent should not be called");
    }
}

