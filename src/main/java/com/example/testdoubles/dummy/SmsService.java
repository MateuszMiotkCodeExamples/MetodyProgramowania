package com.example.testdoubles.dummy;

/**
 * Interfejs serwisu SMS również wymagany przez NotificationService
 */
public interface SmsService {
    void sendSms(String phoneNumber, String message);
    boolean validatePhoneNumber(String phone);
}

