package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

// Simple tests for the User class
class UserTest {

    @Test
    @DisplayName("User constructor and getters should correctly store and retrieve data")
    void testUserConstructorAndGetters() {
        String username = "john_d";
        String password = "SecurePassword123!";
        String cellNumber = "+27712345678";
        String firstName = "John";
        String lastName = "Doe";

        // Create a User Object
        User user = new User(username, password, cellNumber, firstName, lastName);

        // Check if all fields are set and retrieved correctly
        assertNotNull(user, "User object should not be null after instantiation.");
        assertEquals(username, user.getUsername(), "Username should match the constructor input.");
        assertEquals(password, user.getPassword(), "Password should match the constructor input.");
        assertEquals(cellNumber, user.getCellNumber(), "Cell number should match the constructor input.");
        assertEquals(firstName, user.getFirstName(), "First name should match the constructor input.");
        assertEquals(lastName, user.getLastName(), "Last name should match the constructor input.");
    }
}