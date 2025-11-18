package com.example.testdoubles.stub;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementacja Stub dla CustomerRepository
 */
class CustomerRepositoryStub implements CustomerRepository {
    private final Map<String, Customer> customers = new HashMap<>();
    
    // Konstruktor przyjmuje predefiniowanych klientów
    public CustomerRepositoryStub(Customer... predefinedCustomers) {
        for (Customer customer : predefinedCustomers) {
            customers.put(customer.getId(), customer);
        }
    }
    
    @Override
    public Customer findById(String customerId) {
        return customers.get(customerId);
    }
    
    @Override
    public void save(Customer customer) {
        // Stub nie implementuje zapisu - nie jest potrzebny w teście
        throw new UnsupportedOperationException(
            "Stub: save operation not supported");
    }
}

