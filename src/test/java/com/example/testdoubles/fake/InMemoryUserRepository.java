package com.example.testdoubles.fake;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementacja Fake - InMemoryUserRepository
 */
public class InMemoryUserRepository implements UserRepository {
    private final Map<String, User> storage = new ConcurrentHashMap<>();
    private final Map<String, String> usernameIndex = new ConcurrentHashMap<>();
    
    @Override
    public void save(User user) {
        if (user.getId() == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        storage.put(user.getId(), user);
        usernameIndex.put(user.getUsername(), user.getId());
    }
    
    @Override
    public User findById(String id) {
        return storage.get(id);
    }
    
    @Override
    public User findByUsername(String username) {
        String userId = usernameIndex.get(username);
        return userId != null ? storage.get(userId) : null;
    }
    
    @Override
    public List<User> findAll() {
        return new ArrayList<>(storage.values());
    }
    
    @Override
    public void delete(String id) {
        User user = storage.remove(id);
        if (user != null) {
            usernameIndex.remove(user.getUsername());
        }
    }
    
    @Override
    public boolean exists(String id) {
        return storage.containsKey(id);
    }
    
    // Pomocnicza metoda dla testów - czyszczenie stanu
    public void clear() {
        storage.clear();
        usernameIndex.clear();
    }
}

