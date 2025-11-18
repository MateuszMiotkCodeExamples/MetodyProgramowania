package com.example.testdoubles.dummy;

/**
 * Implementacja Dummy dla SmsService
 */
class DummySmsService implements SmsService {
    @Override
    public void sendSms(String phoneNumber, String message) {
        throw new UnsupportedOperationException(
            "Dummy: sendSms should not be called");
    }
    
    @Override
    public boolean validatePhoneNumber(String phone) {
        throw new UnsupportedOperationException(
            "Dummy: validatePhoneNumber should not be called");
    }
}

