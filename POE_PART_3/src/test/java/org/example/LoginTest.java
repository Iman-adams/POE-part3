package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Tests for the Login class
class LoginTest {

    private Login login;
    private String validUsername = "user_";
    private String validPassword = "Password123!";
    private String validFirstName = "Test";
    private String validLastName = "User";
    private String validCellNumber = "+27123456789";

    @BeforeEach
    void setUp() {
        // Initialize a Login with valid user details for registration
        login = new Login(validUsername, validPassword, validFirstName, validLastName, validCellNumber);
    }

    // Tests for checkUserName method
    @Test
    @DisplayName("checkUserName should return true for a valid username")
    void checkUserName_ValidUsername_ReturnsTrue() {
        assertTrue(login.checkUserName("name_"), "Username 'name_' should be valid.");
        assertTrue(login.checkUserName("a_1"), "Username 'a_1' should be valid.");
        assertTrue(login.checkUserName("ab_c"), "Username 'ab_c' should be valid."); // 5 chars
    }

    @Test
    @DisplayName("checkUserName should return false for username without underscore")
    void checkUserName_NoUnderscore_ReturnsFalse() {
        assertFalse(login.checkUserName("username"), "Username 'username' should be invalid (no underscore).");
    }

    @Test
    @DisplayName("checkUserName should return false for username longer than 5 characters")
    void checkUserName_TooLong_ReturnsFalse() {
        assertFalse(login.checkUserName("user_long"), "Username 'user_long' should be invalid (too long).");
        assertFalse(login.checkUserName("long_name"), "Username 'long_name' should be invalid (too long).");
    }

    @Test
    @DisplayName("checkUserName should return false for empty username")
    void checkUserName_EmptyUsername_ReturnsFalse() {
        assertFalse(login.checkUserName(""), "Empty username should be invalid.");
    }

    @Test
    @DisplayName("checkUserName should return false for null username")
    void checkUserName_NullUsername_ReturnsFalse() {
        assertFalse(login.checkUserName(null), "Null username should be invalid.");
    }

    // Tests for checkPasswordComplexity method
    @Test
    @DisplayName("checkPasswordComplexity should return true for a valid password")
    void checkPasswordComplexity_ValidPassword_ReturnsTrue() {
        assertTrue(login.checkPasswordComplexity("Password123!"), "Password 'Password123!' should be valid.");
        assertTrue(login.checkPasswordComplexity("MyPasswd@2024"), "Password 'MyPasswd@2024' should be valid.");
    }

    @Test
    @DisplayName("checkPasswordComplexity should return false for password too short")
    void checkPasswordComplexity_TooShort_ReturnsFalse() {
        assertFalse(login.checkPasswordComplexity("P1!a7"), "Password 'P1!a7' should be invalid (too short).");
        assertFalse(login.checkPasswordComplexity("Short1!"), "Password 'Short1!' (7 chars) should be invalid.");
    }

    @Test
    @DisplayName("checkPasswordComplexity should return false for password missing uppercase")
    void checkPasswordComplexity_MissingUppercase_ReturnsFalse() {
        assertFalse(login.checkPasswordComplexity("password123!"), "Password 'password123!' should be invalid (missing uppercase).");
    }

    @Test
    @DisplayName("checkPasswordComplexity should return false for password missing number")
    void checkPasswordComplexity_MissingNumber_ReturnsFalse() {
        assertFalse(login.checkPasswordComplexity("Password!ABC"), "Password 'Password!ABC' should be invalid (missing number).");
    }

    @Test
    @DisplayName("checkPasswordComplexity should return false for password missing special character")
    void checkPasswordComplexity_MissingSpecialChar_ReturnsFalse() {
        assertFalse(login.checkPasswordComplexity("Password123"), "Password 'Password123' should be invalid (missing special char).");
    }

    @Test
    @DisplayName("checkPasswordComplexity should return false for empty password")
    void checkPasswordComplexity_EmptyPassword_ReturnsFalse() {
        assertFalse(login.checkPasswordComplexity(""), "Empty password should be invalid.");
    }

    @Test
    @DisplayName("checkPasswordComplexity should return false for null password")
    void checkPasswordComplexity_NullPassword_ReturnsFalse() {
        assertFalse(login.checkPasswordComplexity(null), "Null password should be invalid.");
    }

    // Tests for checkCellPhoneNumber method
    @Test
    @DisplayName("checkCellPhoneNumber should return true for a valid cell number")
    void checkCellPhoneNumber_ValidNumber_ReturnsTrue() {
        assertTrue(login.checkCellPhoneNumber("+27123456789"), "Cell number '+27123456789' should be valid.");
        assertTrue(login.checkCellPhoneNumber("+27987654321"), "Cell number '+27987654321' should be valid.");
    }

    @Test
    @DisplayName("checkCellPhoneNumber should return false for cell number with incorrect prefix")
    void checkCellPhoneNumber_IncorrectPrefix_ReturnsFalse() {
        assertFalse(login.checkCellPhoneNumber("27123456789"), "Cell number '27123456789' should be invalid (missing +).");
        assertFalse(login.checkCellPhoneNumber("+26123456789"), "Cell number '+26123456789' should be invalid (wrong country code).");
    }

    @Test
    @DisplayName("checkCellPhoneNumber should return false for cell number with wrong length")
    void checkCellPhoneNumber_WrongLength_ReturnsFalse() {
        assertFalse(login.checkCellPhoneNumber("+2712345678"), "Cell number '+2712345678' should be invalid (too short).");
        assertFalse(login.checkCellPhoneNumber("+271234567890"), "Cell number '+271234567890' should be invalid (too long).");
    }

    @Test
    @DisplayName("checkCellPhoneNumber should return false for cell number with non-digit characters")
    void checkCellPhoneNumber_NonDigits_ReturnsFalse() {
        assertFalse(login.checkCellPhoneNumber("+27ABCDEFGH"), "Cell number '+27ABCDEFGH' should be invalid (non-digits).");
    }

    @Test
    @DisplayName("checkCellPhoneNumber should return false for empty cell number")
    void checkCellPhoneNumber_EmptyNumber_ReturnsFalse() {
        assertFalse(login.checkCellPhoneNumber(""), "Empty cell number should be invalid.");
    }

    @Test
    @DisplayName("checkCellPhoneNumber should return false for null cell number")
    void checkCellPhoneNumber_NullNumber_ReturnsFalse() {
        assertFalse(login.checkCellPhoneNumber(null), "Null cell number should be invalid.");
    }

    // Tests for registerUser method
    @Test
    @DisplayName("registerUser should return success message for valid user details")
    void registerUser_ValidDetails_ReturnsSuccess() {
        String result = login.registerUser();
        assertEquals("User has been registered successfully.", result, "Should register successfully with valid details.");
    }

    @Test
    @DisplayName("registerUser should return error for invalid username")
    void registerUser_InvalidUsername_ReturnsError() {
        // Create a new Login instance with an invalid username
        Login invalidLogin = new Login("user-invalid", validPassword, validFirstName, validLastName, validCellNumber);
        String result = invalidLogin.registerUser();
        assertEquals("Username is incorrectly formatted.", result, "Should return error for invalid username format.");
    }

    @Test
    @DisplayName("registerUser should return error for invalid password")
    void registerUser_InvalidPassword_ReturnsError() {
        // Create a new Login instance with an invalid password
        Login invalidLogin = new Login(validUsername, "short1!", validFirstName, validLastName, validCellNumber);
        String result = invalidLogin.registerUser();
        assertEquals("Password does not meet the complexity requirements.", result, "Should return error for invalid password complexity.");
    }

    @Test
    @DisplayName("registerUser should return error for invalid cell number")
    void registerUser_InvalidCellNumber_ReturnsError() {
        // Create a new Login instance with an invalid cell number
        Login invalidLogin = new Login(validUsername, validPassword, validFirstName, validLastName, "12345");
        String result = invalidLogin.registerUser();
        assertEquals("Cell phone number is incorrectly formatted.", result, "Should return error for invalid cell number format.");
    }

    // Tests for loginUser method
    @Test
    @DisplayName("loginUser should return true for correct username and password")
    void loginUser_CorrectCredentials_ReturnsTrue() {
        // First ensure user is registered for the login attempt
        login.registerUser();
        assertTrue(login.loginUser(validUsername, validPassword), "Should log in successfully with correct credentials.");
    }

    @Test
    @DisplayName("loginUser should return false for incorrect username")
    void loginUser_IncorrectUsername_ReturnsFalse() {
        login.registerUser();
        assertFalse(login.loginUser("wrong_user", validPassword), "Should not log in with incorrect username.");
    }

    @Test
    @DisplayName("loginUser should return false for incorrect password")
    void loginUser_IncorrectPassword_ReturnsFalse() {
        login.registerUser();
        assertFalse(login.loginUser(validUsername, "WrongPass123!"), "Should not log in with incorrect password.");
    }

    @Test
    @DisplayName("loginUser should return false for both incorrect username and password")
    void loginUser_IncorrectBoth_ReturnsFalse() {
        login.registerUser();
        assertFalse(login.loginUser("wrong_user", "WrongPass123!"), "Should not log in with incorrect username and password.");
    }

    // Tests for returnLoginStatus method
    @Test
    @DisplayName("returnLoginStatus should return welcome message for correct credentials")
    void returnLoginStatus_CorrectCredentials_ReturnsWelcomeMessage() {
        String expectedMessage = "Welcome " + validFirstName + " " + validLastName + ", it is great to see you again.";
        assertEquals(expectedMessage, login.returnLoginStatus(validUsername, validPassword), "Should return welcome message.");
    }

    @Test
    @DisplayName("returnLoginStatus should return error message for incorrect credentials")
    void returnLoginStatus_IncorrectCredentials_ReturnsErrorMessage() {
        assertEquals("Username or password incorrect, please try again.", login.returnLoginStatus("wrong_user", "wrongpass"), "Should return error message for incorrect credentials.");
    }
}