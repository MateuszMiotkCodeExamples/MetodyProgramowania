package com.example.testdoubles.spy;

/**
 * System testowany - OrderFulfillmentService
 */
public class OrderFulfillmentService {
    private final ShippingService shippingService;
    
    public OrderFulfillmentService(ShippingService shippingService) {
        this.shippingService = shippingService;
    }
    
    public String processOrder(String orderId, String customerEmail, String address) {
        // Generowanie numeru śledzenia
        String trackingNumber = shippingService.generateTrackingNumber();
        
        // Planowanie odbioru
        shippingService.schedulePickup(orderId, address);
        
        // Wysłanie powiadomienia
        shippingService.sendTrackingEmail(customerEmail, trackingNumber);
        
        return trackingNumber;
    }
}

