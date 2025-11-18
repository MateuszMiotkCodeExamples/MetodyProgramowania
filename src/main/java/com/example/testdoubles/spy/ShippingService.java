package com.example.testdoubles.spy;

/**
 * Interfejs serwisu wysyłki
 */
public interface ShippingService {
    String generateTrackingNumber();
    void schedulePickup(String orderId, String address);
    void sendTrackingEmail(String email, String trackingNumber);
}

