package com.example.testdoubles.stub;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testy wykorzystujące Stub
 */
class DiscountCalculatorTest {

    @Test
    void shouldCalculateGoldTierDiscount() {
        // Arrange - przygotowanie stubowanych danych
        Customer goldCustomer = new Customer("C001", "GOLD", 5);
        CustomerRepository stub = new CustomerRepositoryStub(goldCustomer);
        DiscountCalculator calculator = new DiscountCalculator(stub);
        
        // Act
        double discount = calculator.calculateDiscount("C001", 100.0);
        
        // Assert - weryfikacja stanu (state verification)
        assertEquals(20.0, discount, 0.01); // 20% rabatu
    }
    
    @Test
    void shouldApplyLoyaltyBonus() {
        // Arrange - klient z dużą liczbą zakupów
        Customer loyalCustomer = new Customer("C002", "SILVER", 15);
        CustomerRepository stub = new CustomerRepositoryStub(loyalCustomer);
        DiscountCalculator calculator = new DiscountCalculator(stub);
        
        // Act
        double discount = calculator.calculateDiscount("C002", 100.0);
        
        // Assert
        assertEquals(15.0, discount, 0.01); // 10% tier + 5% loyalty
    }
    
    @Test
    void shouldReturnZeroForNonexistentCustomer() {
        // Arrange - pusta baza klientów
        CustomerRepository stub = new CustomerRepositoryStub();
        DiscountCalculator calculator = new DiscountCalculator(stub);
        
        // Act
        double discount = calculator.calculateDiscount("UNKNOWN", 100.0);
        
        // Assert
        assertEquals(0.0, discount, 0.01);
    }
}

