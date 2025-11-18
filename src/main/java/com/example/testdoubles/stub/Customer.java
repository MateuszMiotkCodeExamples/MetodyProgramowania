package com.example.testdoubles.stub;

/**
 * Model domenowy Customer
 */
public class Customer {
    private final String id;
    private final String tier; // "BRONZE", "SILVER", "GOLD"
    private final int purchaseCount;
    
    public Customer(String id, String tier, int purchaseCount) {
        this.id = id;
        this.tier = tier;
        this.purchaseCount = purchaseCount;
    }
    
    public String getId() {
        return id;
    }
    
    public String getTier() {
        return tier;
    }
    
    public int getPurchaseCount() {
        return purchaseCount;
    }
}

