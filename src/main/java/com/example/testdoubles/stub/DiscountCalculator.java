package com.example.testdoubles.stub;

/**
 * System testowany - DiscountCalculator
 */
public class DiscountCalculator {
    private final CustomerRepository repository;
    
    public DiscountCalculator(CustomerRepository repository) {
        this.repository = repository;
    }
    
    public double calculateDiscount(String customerId, double orderAmount) {
        Customer customer = repository.findById(customerId);
        if (customer == null) {
            return 0.0;
        }
        
        double tierDiscount;
        switch (customer.getTier()) {
            case "GOLD":
                tierDiscount = 0.20;
                break;
            case "SILVER":
                tierDiscount = 0.10;
                break;
            case "BRONZE":
                tierDiscount = 0.05;
                break;
            default:
                tierDiscount = 0.0;
        }
        
        // Dodatkowy rabat dla częstych klientów
        double loyaltyBonus = customer.getPurchaseCount() > 10 ? 0.05 : 0.0;
        
        return orderAmount * (tierDiscount + loyaltyBonus);
    }
}

