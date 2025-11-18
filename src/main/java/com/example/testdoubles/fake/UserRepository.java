package com.example.testdoubles.fake;

import java.util.List;

/**
 * Interfejs repozytorium
 */
public interface UserRepository {
    void save(User user);
    User findById(String id);
    User findByUsername(String username);
    List<User> findAll();
    void delete(String id);
    boolean exists(String id);
}

