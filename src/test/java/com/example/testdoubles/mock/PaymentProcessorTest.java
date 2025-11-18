package com.example.testdoubles.mock;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testy z Mock i behavior verification
 */
class PaymentProcessorTest {

    @Test
    void shouldAuthorizeAndCapturePayment() {
        // Arrange - definiowanie oczekiwań
        PaymentGatewayMock mock = new PaymentGatewayMock();
        mock.expectAuthorize("4111-1111-1111-1111", 99.99);
        mock.expectCapture(99.99);
        
        PaymentProcessor processor = new PaymentProcessor(mock);
        
        // Act
        String transactionId = processor.processPayment("4111-1111-1111-1111", 99.99);
        
        // Assert - weryfikacja zachowania
        mock.verify();
        assertNotNull(transactionId);
        assertTrue(transactionId.startsWith("TXN-"));
    }
    
    @Test
    void shouldFailOnUnexpectedMethodCall() {
        // Arrange - mock bez oczekiwań dla authorize
        PaymentGatewayMock mock = new PaymentGatewayMock();
        PaymentProcessor processor = new PaymentProcessor(mock);
        
        // Act & Assert - wywołanie nieoczekiwanej metody
        assertThrows(AssertionError.class, () -> 
            processor.processPayment("4111-1111-1111-1111", 50.00));
    }
    
    @Test
    void shouldVerifyRefundWithCorrectParameters() {
        // Arrange
        PaymentGatewayMock mock = new PaymentGatewayMock();
        mock.expectRefund("TXN-12345", 25.50);
        
        PaymentProcessor processor = new PaymentProcessor(mock);
        
        // Act
        processor.processRefund("TXN-12345", 25.50);
        
        // Assert
        mock.verify();
    }
    
    @Test
    void shouldFailWhenExpectedMethodNotCalled() {
        // Arrange
        PaymentGatewayMock mock = new PaymentGatewayMock();
        mock.expectRefund("TXN-99999", 10.00);
        
        // Act - nie wywołujemy processRefund
        
        // Assert - verify() wykryje brak wywołania
        assertThrows(AssertionError.class, mock::verify);
    }
}

