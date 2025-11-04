package com.example.library;

public class ReservationResult {
    private final boolean successful;
    private final String message;

    public ReservationResult(boolean successful, String message) {
        this.successful = successful;
        this.message = message;
    }

    public boolean isSuccessful() { return successful; }
    public String getMessage() { return message; }
}


