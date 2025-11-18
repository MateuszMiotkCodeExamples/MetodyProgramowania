package com.example.testdoubles.spy;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementacja Spy dla ShippingService
 */
class ShippingServiceSpy implements ShippingService {
    // Rejestrowanie wywołań
    private int generateTrackingNumberCallCount = 0;
    private final List<SchedulePickupCall> schedulePickupCalls = new ArrayList<>();
    private final List<SendEmailCall> sendEmailCalls = new ArrayList<>();
    
    // Klasy pomocnicze dla przechowywania argumentów
    static class SchedulePickupCall {
        final String orderId;
        final String address;
        
        SchedulePickupCall(String orderId, String address) {
            this.orderId = orderId;
            this.address = address;
        }
    }
    
    static class SendEmailCall {
        final String email;
        final String trackingNumber;
        
        SendEmailCall(String email, String trackingNumber) {
            this.email = email;
            this.trackingNumber = trackingNumber;
        }
    }
    
    @Override
    public String generateTrackingNumber() {
        generateTrackingNumberCallCount++;
        return "TRACK-" + System.currentTimeMillis();
    }
    
    @Override
    public void schedulePickup(String orderId, String address) {
        schedulePickupCalls.add(new SchedulePickupCall(orderId, address));
    }
    
    @Override
    public void sendTrackingEmail(String email, String trackingNumber) {
        sendEmailCalls.add(new SendEmailCall(email, trackingNumber));
    }
    
    // Metody weryfikacyjne dla testów
    public int getGenerateTrackingNumberCallCount() {
        return generateTrackingNumberCallCount;
    }
    
    public List<SchedulePickupCall> getSchedulePickupCalls() {
        return new ArrayList<>(schedulePickupCalls);
    }
    
    public List<SendEmailCall> getSendEmailCalls() {
        return new ArrayList<>(sendEmailCalls);
    }
    
    public boolean wasSchedulePickupCalledWith(String orderId, String address) {
        return schedulePickupCalls.stream()
            .anyMatch(call -> call.orderId.equals(orderId) && 
                            call.address.equals(address));
    }
    
    public boolean wasEmailSentTo(String email) {
        return sendEmailCalls.stream()
            .anyMatch(call -> call.email.equals(email));
    }
}

