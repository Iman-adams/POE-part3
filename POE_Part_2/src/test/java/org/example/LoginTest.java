package org.example;

import org.junit.Test;
import static org.junit.Assert.*;

public class LoginTest {

    @Test
    public void testValidRegistration() {
        Login login = new Login("user_", "Password1!", "John", "Doe", "+27123456789");
        String result = login.registerUser();
        assertEquals("User has been registered successfully.", result);
    }

    @Test
    public void testInvalidUsername() {
        Login login = new Login("user", "Password1!", "John", "Doe", "+27123456789");
        String result = login.registerUser();
        assertEquals("Username is not correctly formatted.", result);
    }

    @Test
    public void testInvalidPassword() {
        Login login = new Login("user_", "pass", "John", "Doe", "+27123456789");
        String result = login.registerUser();
        assertEquals("Password is not correctly formatted.", result);
    }

    @Test
    public void testInvalidCellNumber() {
        Login login = new Login("user_", "Password1!", "John", "Doe", "123456789");
        String result = login.registerUser();
        assertEquals("Cell number must be in format +27XXXXXXXXX.", result);
    }

    @Test
    public void testLoginSuccess() {
        Login login = new Login("user_", "Password1!", "John", "Doe", "+27123456789");
        login.registerUser(); // assuming validation saves state
        assertTrue(login.loginUser("user_", "Password1!"));
    }

    @Test
    public void testLoginFailure() {
        Login login = new Login("user_", "Password1!", "John", "Doe", "+27123456789");
        login.registerUser();
        assertFalse(login.loginUser("user_", "wrongpass"));
    }
}
