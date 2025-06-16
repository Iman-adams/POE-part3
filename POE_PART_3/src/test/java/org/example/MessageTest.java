package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Tests for the Message class
class MessageTest {

    // Tests for Constructor and Getters
    @Test
    @DisplayName("Message constructor should correctly initialize fields and generate ID/Hash")
    void messageConstructor_InitializesFieldsAndGeneratesIdHash() {
        String recipient = "+27123456789";
        String content = "Hello world!";
        String flag = "Sent";

        Message message = new Message(recipient, content, flag);

        assertNotNull(message.getMessageID(), "Message ID should not be null.");
        assertEquals(10, message.getMessageID().length(), "Message ID should be 10 digits long.");
        assertTrue(message.getMessageID().matches("\\d{10}"), "Message ID should consist of only digits.");

        assertNotNull(message.getMessageHash(), "Message Hash should not be null.");
        assertFalse(message.getMessageHash().isEmpty(), "Message Hash should not be empty.");

        assertEquals(recipient, message.getRecipient(), "Recipient should match constructor input.");
        assertEquals(content, message.getContent(), "Content should match constructor input.");
        assertEquals(flag, message.getFlag(), "Flag should match constructor input.");
    }

    // Tests for checkMessageID method
    @Test
    @DisplayName("checkMessageID should return true for a 10-character ID")
    void checkMessageID_ValidLength_ReturnsTrue() {
        Message message = new Message("+27123456789", "Test", "Sent");
        // Manually set ID for specific test, since generateMessageID is random
        try {
            java.lang.reflect.Field field = Message.class.getDeclaredField("messageID");
            field.setAccessible(true);
            field.set(message, "1234567890");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Failed to set messageID for testing: " + e.getMessage());
        }
        assertTrue(message.checkMessageID(), "Message ID '1234567890' should be valid.");
    }

    @Test
    @DisplayName("checkMessageID should return false for ID longer than 10 characters")
    void checkMessageID_TooLong_ReturnsFalse() {
        Message message = new Message("+27123456789", "Test", "Sent");
        try {
            java.lang.reflect.Field field = Message.class.getDeclaredField("messageID");
            field.setAccessible(true);
            field.set(message, "12345678901");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Failed to set messageID for testing: " + e.getMessage());
        }
        assertFalse(message.checkMessageID(), "Message ID '12345678901' should be invalid (too long).");
    }

    @Test
    @DisplayName("checkMessageID should return false for ID shorter than 10 characters")
    void checkMessageID_TooShort_ReturnsFalse() {
        Message message = new Message("+27123456789", "Test", "Sent");
        try {
            java.lang.reflect.Field field = Message.class.getDeclaredField("messageID");
            field.setAccessible(true);
            field.set(message, "12345");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Failed to set messageID for testing: " + e.getMessage());
        }
        assertFalse(message.checkMessageID(), "Message ID '12345' should be invalid (too short).");
    }

    @Test
    @DisplayName("checkMessageID should return false for null ID")
    void checkMessageID_NullID_ReturnsFalse() {
        Message message = new Message("+27123456789", "Test", "Sent");
        try {
            java.lang.reflect.Field field = Message.class.getDeclaredField("messageID");
            field.setAccessible(true);
            field.set(message, null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Failed to set messageID for testing: " + e.getMessage());
        }
        assertFalse(message.checkMessageID(), "Null Message ID should be invalid.");
    }

    // Tests for checkRecipientCell method (static)
    @Test
    @DisplayName("checkRecipientCell should return true for a valid cell number")
    void checkRecipientCell_ValidNumber_ReturnsTrue() {
        assertTrue(Message.checkRecipientCell("+27123456789"), "Cell number '+27123456789' should be valid.");
    }

    @Test
    @DisplayName("checkRecipientCell should return false for cell number with incorrect prefix")
    void checkRecipientCell_IncorrectPrefix_ReturnsFalse() {
        assertFalse(Message.checkRecipientCell("27123456789"), "Cell number '27123456789' should be invalid (missing +).");
        assertFalse(Message.checkRecipientCell("+26123456789"), "Cell number '+26123456789' should be invalid (wrong country code).");
    }

    @Test
    @DisplayName("checkRecipientCell should return false for cell number with wrong length")
    void checkRecipientCell_WrongLength_ReturnsFalse() {
        assertFalse(Message.checkRecipientCell("+2712345678"), "Cell number '+2712345678' should be invalid (too short).");
        assertFalse(Message.checkRecipientCell("+271234567890"), "Cell number '+271234567890' should be invalid (too long).");
    }

    @Test
    @DisplayName("checkRecipientCell should return false for cell number with non-digit characters")
    void checkRecipientCell_NonDigits_ReturnsFalse() {
        assertFalse(Message.checkRecipientCell("+27ABCDEFGH"), "Cell number '+27ABCDEFGH' should be invalid (non-digits).");
    }

    @Test
    @DisplayName("checkRecipientCell should return false for empty cell number")
    void checkRecipientCell_EmptyNumber_ReturnsFalse() {
        assertFalse(Message.checkRecipientCell(""), "Empty cell number should be invalid.");
    }

    @Test
    @DisplayName("checkRecipientCell should return false for null cell number")
    void checkRecipientCell_NullNumber_ReturnsFalse() {
        assertFalse(Message.checkRecipientCell(null), "Null cell number should be invalid.");
    }

    // Tests for createMessageHash method
    @Test
    @DisplayName("createMessageHash should generate correct hash for multi-word content")
    void createMessageHash_MultiWordContent_GeneratesCorrectHash() {
        Message message = new Message("+27123456789", "This is a test message.", "Sent");
        try {
            java.lang.reflect.Field idField = Message.class.getDeclaredField("messageID");
            idField.setAccessible(true);
            idField.set(message, "1234567890"); // Set a known ID for predictable hash prefix
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Failed to set messageID for testing: " + e.getMessage());
        }

        String expectedHash = "12:23:THISMESSAGE."; // "12" from ID, "23" from content length, "THIS" + "MESSAGE." (uppercase)
        assertEquals(expectedHash, message.createMessageHash(), "Hash should be correctly generated for multi-word content.");
    }

    @Test
    @DisplayName("createMessageHash should generate correct hash for single-word content")
    void createMessageHash_SingleWordContent_GeneratesCorrectHash() {
        Message message = new Message("+27123456789", "Hello", "Sent");
        try {
            java.lang.reflect.Field idField = Message.class.getDeclaredField("messageID");
            idField.setAccessible(true);
            idField.set(message, "9876543210"); // Set a known ID
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Failed to set messageID for testing: " + e.getMessage());
        }

        String expectedHash = "98:5:HELLOHELLO"; // "98" from ID, "5" from content length, "HELLO" + "HELLO" (uppercase)
        assertEquals(expectedHash, message.createMessageHash(), "Hash should be correctly generated for single-word content.");
    }

    @Test
    @DisplayName("createMessageHash should handle content with special characters and numbers")
    void createMessageHash_SpecialCharsAndNumbers_GeneratesCorrectHash() {
        Message message = new Message("+27123456789", "Msg@123 Test!", "Sent");
        try {
            java.lang.reflect.Field idField = Message.class.getDeclaredField("messageID");
            idField.setAccessible(true);
            idField.set(message, "0011223344"); // Set a known ID
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Failed to set messageID for testing: " + e.getMessage());
        }
        String expectedHash = "00:14:MSG@123TEST!"; // "00" from ID, "14" from content length, "MSG@123" + "TEST!"
        assertEquals(expectedHash, message.createMessageHash(), "Hash should handle special characters and numbers correctly.");
    }

    @Test
    @DisplayName("createMessageHash should handle empty content")
    void createMessageHash_EmptyContent_GeneratesCorrectHash() {
        Message message = new Message("+27123456789", "", "Sent");
        try {
            java.lang.reflect.Field idField = Message.class.getDeclaredField("messageID");
            idField.setAccessible(true);
            idField.set(message, "5566778899"); // Set a known ID
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Failed to set messageID for testing: " + e.getMessage());
        }
        String expectedHash = "55:0:"; // "55" from ID, "0" from content length, empty first/last words
        assertEquals(expectedHash, message.createMessageHash(), "Hash should be correctly generated for empty content.");
    }

    @Test
    @DisplayName("createMessageHash should handle content with only spaces")
    void createMessageHash_SpacesOnlyContent_GeneratesCorrectHash() {
        Message message = new Message("+27123456789", "   ", "Sent");
        try {
            java.lang.reflect.Field idField = Message.class.getDeclaredField("messageID");
            idField.setAccessible(true);
            idField.set(message, "1122334455"); // Set a known ID
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Failed to set messageID for testing: " + e.getMessage());
        }
        String expectedHash = "11:3:"; // "11" from ID, "3" from content length (original string length), empty first/last words after trim
        assertEquals(expectedHash, message.createMessageHash(), "Hash should be correctly generated for content with only spaces.");
    }

    // Tests for toString method
    @Test
    @DisplayName("toString should return correctly formatted message details")
    void toString_ReturnsFormattedString() {
        String recipient = "+27123456789";
        String content = "Hello JUnit!";
        String flag = "Sent";
        Message message = new Message(recipient, content, flag);

        // Manually set predictable ID and hash for stable toString output
        try {
            java.lang.reflect.Field idField = Message.class.getDeclaredField("messageID");
            idField.setAccessible(true);
            idField.set(message, "0123456789");

            java.lang.reflect.Field hashField = Message.class.getDeclaredField("messageHash");
            hashField.setAccessible(true);
            hashField.set(message, "01:12:HELLOJUNIT!"); // Consistent with content "Hello JUnit!" and ID "01"
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Failed to set fields for toString testing: " + e.getMessage());
        }


        String expected = "Message ID: 0123456789\n" +
                "Message Hash: 01:12:HELLOJUNIT!\n" +
                "Recipient: +27123456789\n" +
                "Message: Hello JUnit!\n" +
                "Status: Sent";
        assertEquals(expected, message.toString(), "toString should return the expected formatted string.");
    }
}