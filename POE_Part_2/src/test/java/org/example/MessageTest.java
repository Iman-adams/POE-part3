package org.example;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MessageTest {

    @Before
    public void resetBeforeTest() throws Exception {
        // Reflection-based reset for static fields if needed
        // Or manually adjust logic in Message to allow test reset
    }

    @Test
    public void testCheckRecipientCell_Valid() {
        assertTrue(Message.checkRecipientCell("+27123456789"));
    }

    @Test
    public void testCheckRecipientCell_Invalid() {
        assertFalse(Message.checkRecipientCell("123456789"));
        assertFalse(Message.checkRecipientCell("+28123456789"));
        assertFalse(Message.checkRecipientCell(null));
        assertFalse(Message.checkRecipientCell(""));
    }

    @Test
    public void testMessageIDLength() {
        Message message = new Message("+27123456789", "Hello World");
        assertTrue(message.checkMessageID());
    }

    @Test
    public void testCreateMessageHash() {
        Message message = new Message("+27123456789", "Hello world");
        String hash = message.createMessageHash();

        assertTrue(hash.matches("^\\d{2}:\\d+:HELLOWORLD$"));
    }

    @Test
    public void testSendMessageOption() {
        assertTrue(Message.sendMessageOption("1"));
        assertFalse(Message.sendMessageOption("2"));
    }

    @Test
    public void testMessageStorageAndRetrieval() {
        int countBefore = Message.returnTotalMessages();
        Message msg = new Message("+27123456789", "Test message storage");
        int countAfter = Message.returnTotalMessages();
        assertEquals(countBefore + 1, countAfter);

        String history = Message.getMessages();
        assertTrue(history.contains("Test message storage"));
    }

    @Test
    public void testGetMessagesEmpty() {
        // You would need a way to reset message history for this test to be reliable
        // Could be by exposing a method like Message.clearMessagesForTest() in dev/test environment
    }
}
