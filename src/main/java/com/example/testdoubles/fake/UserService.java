package com.example.testdoubles.fake;

/**
 * System testowany - UserService
 */
public class UserService {
    private final UserRepository repository;
    
    public UserService(UserRepository repository) {
        this.repository = repository;
    }
    
    public User registerUser(String id, String username, String email) {
        if (repository.findByUsername(username) != null) {
            throw new IllegalArgumentException(
                "Username already exists: " + username);
        }
        
        User user = new User(id, username, email);
        repository.save(user);
        return user;
    }
    
    public boolean isUsernameTaken(String username) {
        return repository.findByUsername(username) != null;
    }
}

