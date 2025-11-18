package com.example.testdoubles.mock;

/**
 * System testowany - PaymentProcessor
 */
public class PaymentProcessor {
    private final PaymentGateway gateway;
    
    public PaymentProcessor(PaymentGateway gateway) {
        this.gateway = gateway;
    }
    
    public String processPayment(String cardNumber, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        
        boolean authorized = gateway.authorize(cardNumber, amount);
        if (!authorized) {
            throw new RuntimeException("Payment authorization failed");
        }
        
        return gateway.capture("AUTH-" + cardNumber.hashCode(), amount);
    }
    
    public void processRefund(String transactionId, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Refund amount must be positive");
        }
        gateway.refund(transactionId, amount);
    }
}

