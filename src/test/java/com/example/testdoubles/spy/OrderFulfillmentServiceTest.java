package com.example.testdoubles.spy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testy wykorzystujące Spy
 */
class OrderFulfillmentServiceTest {

    @Test
    void shouldCallAllShippingOperations() {
        // Arrange
        ShippingServiceSpy spy = new ShippingServiceSpy();
        OrderFulfillmentService service = new OrderFulfillmentService(spy);
        
        // Act
        String trackingNumber = service.processOrder(
            "ORD-123", 
            "customer@example.com", 
            "123 Main St");
        
        // Assert - weryfikacja interakcji
        assertEquals(1, spy.getGenerateTrackingNumberCallCount());
        assertTrue(spy.wasSchedulePickupCalledWith("ORD-123", "123 Main St"));
        assertTrue(spy.wasEmailSentTo("customer@example.com"));
        assertNotNull(trackingNumber);
        assertTrue(trackingNumber.startsWith("TRACK-"));
    }
    
    @Test
    void shouldPassCorrectTrackingNumberToEmail() {
        // Arrange
        ShippingServiceSpy spy = new ShippingServiceSpy();
        OrderFulfillmentService service = new OrderFulfillmentService(spy);
        
        // Act
        String trackingNumber = service.processOrder(
            "ORD-456", 
            "user@example.com", 
            "456 Oak Ave");
        
        // Assert - weryfikacja przekazanych parametrów
        var emailCalls = spy.getSendEmailCalls();
        assertEquals(1, emailCalls.size());
        assertEquals("user@example.com", emailCalls.get(0).email);
        assertEquals(trackingNumber, emailCalls.get(0).trackingNumber);
    }
}

