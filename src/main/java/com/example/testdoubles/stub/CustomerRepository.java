package com.example.testdoubles.stub;

/**
 * Interfejs repozytorium
 */
public interface CustomerRepository {
    Customer findById(String customerId);
    void save(Customer customer);
}

