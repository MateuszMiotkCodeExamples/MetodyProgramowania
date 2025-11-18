package com.example.testdoubles.mock;

/**
 * Interfejs bramki płatniczej
 */
public interface PaymentGateway {
    boolean authorize(String cardNumber, double amount);
    String capture(String authorizationId, double amount);
    void refund(String transactionId, double amount);
}

