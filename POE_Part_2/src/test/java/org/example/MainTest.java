package org.example;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MainTest {

    private Login login;

    @Before
    public void setUp() {
        // Create a valid Login object as done in Main
        login = new Login("kyl_1",  "Ch&&sec@ke99", "Kyle", "Adams", "+27838968976");
    }

    @Test
    public void testUserLoginSuccessAfterRegistration() {
        login.registerUser();  // Register the user first
        boolean loggedIn = login.loginUser("kyl_1", "Ch&&sec@ke99");
        assertTrue(loggedIn);
    }

    @Test
    public void testUserLoginFailureWithWrongPassword() {
        login.registerUser();
        boolean loggedIn = login.loginUser("kyl_1", "WrongPassword");
        assertFalse(loggedIn);
    }

    @Test
    public void testSendMessageAndCheckHistory() {
        int before = Message.returnTotalMessages();

        Message msg = new Message("+27838968976", "Hello, this is Kyle.");
        assertEquals(before + 1, Message.returnTotalMessages());

        String messages = Message.getMessages();
        assertTrue(messages.contains("Hello, this is Kyle."));
    }

    @Test
    public void testMessageHashFormat() {
        Message msg = new Message("+27838968976", "Choco fudge");
        String hash = msg.createMessageHash();

        // Should match format like: "12:1:CHOCOFUDGE"
        assertTrue(hash.matches("^\\d{2}:\\d+:CHOCOFUDGE$"));
    }

    @Test
    public void testInvalidRecipientNumber() {
        assertFalse(Message.checkRecipientCell("0838968976"));   // missing +27
        assertFalse(Message.checkRecipientCell("+2783"));         // too short
        assertFalse(Message.checkRecipientCell(""));              // empty
        assertFalse(Message.checkRecipientCell(null));            // null
    }
}