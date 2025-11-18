package com.example.testdoubles.fake;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testy z Fake repository
 */
class UserServiceTest {

    private InMemoryUserRepository fakeRepository;
    private UserService userService;
    
    @BeforeEach
    void setUp() {
        fakeRepository = new InMemoryUserRepository();
        userService = new UserService(fakeRepository);
    }
    
    @Test
    void shouldRegisterNewUser() {
        // Act
        User user = userService.registerUser("U001", "john_doe", "john@example.com");
        
        // Assert - weryfikacja przez fake repository
        assertNotNull(user);
        assertTrue(fakeRepository.exists("U001"));
        assertEquals("john_doe", fakeRepository.findById("U001").getUsername());
    }
    
    @Test
    void shouldPreventDuplicateUsernames() {
        // Arrange - pierwszy użytkownik
        userService.registerUser("U001", "john_doe", "john@example.com");
        
        // Act & Assert - próba duplikatu
        assertThrows(IllegalArgumentException.class, () ->
            userService.registerUser("U002", "john_doe", "other@example.com"));
    }
    
    @Test
    void shouldDetectTakenUsernames() {
        // Arrange
        fakeRepository.save(new User("U001", "existing_user", "user@example.com"));
        
        // Act & Assert
        assertTrue(userService.isUsernameTaken("existing_user"));
        assertFalse(userService.isUsernameTaken("new_user"));
    }
}

