package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// These tests cover adding, retrieving, searching, deleting, and reporting messages.
class MessageManagerTest {

    private MessageManager messageManager;

    // Sample messages for testing various scenarios
    private Message sentMsg1;
    private Message sentMsg2;
    private Message storedMsg1;
    private Message disregardedMsg1;
    private Message sentMsg3;

    @BeforeEach
    void setUp() {
        messageManager = new MessageManager();

        // Create test messages. Using reflection here to set IDs/hashes
        // because Message generates them randomly, and we need predictable values for tests.
        sentMsg1 = createTestMessage("+27111111111", "Hello world!", "Sent", "ID1000000001", "10:12:HELLOWORLD!");
        sentMsg2 = createTestMessage("+27222222222", "This is a much longer message content for testing purposes.", "Sent", "ID1000000002", "10:59:THISPURPOSES.");
        storedMsg1 = createTestMessage("+27111111111", "Save this message.", "Stored", "ID1000000003", "10:18:SAVEMESSAGE.");
        disregardedMsg1 = createTestMessage("+27333333333", "Ignore me.", "Disregarded", "ID1000000004", "10:9:IGNOREME.");
        sentMsg3 = createTestMessage("+27111111111", "Another message for recipient.", "Sent", "ID1000000005", "10:29:ANOTHERRECIPIENT.");
    }

    // Helper to create Message objects with fixed ID/hash for predictable tests.
    private Message createTestMessage(String recipient, String content, String flag, String id, String hash) {
        Message msg = new Message(recipient, content, flag);
        try {
            java.lang.reflect.Field idField = Message.class.getDeclaredField("messageID");
            idField.setAccessible(true);
            idField.set(msg, id); // Force set ID

            java.lang.reflect.Field hashField = Message.class.getDeclaredField("messageHash");
            hashField.setAccessible(true);
            hashField.set(msg, hash); // Force set hash
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Failed to set message fields for testing: " + e.getMessage());
        }
        return msg;
    }


    // Tests for Constructor
    @Test
    @DisplayName("Constructor should initialize all message lists as empty")
    void constructor_InitializesEmptyLists() {
        assertNotNull(messageManager.getSentMessages(), "Sent messages list should not be null.");
        assertTrue(messageManager.getSentMessages().isEmpty(), "Sent messages list should be empty.");
        assertNotNull(messageManager.getDisregardedMessages(), "Disregarded messages list should not be null.");
        assertTrue(messageManager.getDisregardedMessages().isEmpty(), "Disregarded messages list should be empty.");
        assertNotNull(messageManager.getStoredMessages(), "Stored messages list should not be null.");
        assertTrue(messageManager.getStoredMessages().isEmpty(), "Stored messages list should be empty.");
    }

    // Tests for addMessage method
    @Test
    @DisplayName("addMessage should add 'Sent' message to sentMessages list")
    void addMessage_SentMessage_AddsToList() {
        messageManager.addMessage(sentMsg1);
        assertEquals(1, messageManager.getSentMessages().size(), "Sent messages list should contain 1 message.");
        assertTrue(messageManager.getSentMessages().contains(sentMsg1), "Sent messages list should contain the added message.");
    }

    @Test
    @DisplayName("addMessage should add 'Disregarded' message to disregardedMessages list")
    void addMessage_DisregardedMessage_AddsToList() {
        messageManager.addMessage(disregardedMsg1);
        assertEquals(1, messageManager.getDisregardedMessages().size(), "Disregarded messages list should contain 1 message.");
        assertTrue(messageManager.getDisregardedMessages().contains(disregardedMsg1), "Disregarded messages list should contain the added message.");
    }

    @Test
    @DisplayName("addMessage should add 'Stored' message to storedMessages list")
    void addMessage_StoredMessage_AddsToList() {
        messageManager.addMessage(storedMsg1);
        assertEquals(1, messageManager.getStoredMessages().size(), "Stored messages list should contain 1 message.");
        assertTrue(messageManager.getStoredMessages().contains(storedMsg1), "Stored messages list should contain the added message.");
    }

    @Test
    @DisplayName("addMessage should not add null message")
    void addMessage_NullMessage_DoesNotAdd() {
        int initialSentSize = messageManager.getSentMessages().size();
        messageManager.addMessage(null);
        assertEquals(initialSentSize, messageManager.getSentMessages().size(), "List size should not change for null message.");
    }

    @Test
    @DisplayName("addMessage should not add message with unknown flag")
    void addMessage_UnknownFlagMessage_DoesNotAdd() {
        Message unknownFlagMsg = createTestMessage("+27000000000", "Unknown", "Uncategorized", "ID_UNKNOWN", "HASH_UNKNOWN");
        int initialSentSize = messageManager.getSentMessages().size();
        int initialDisregardedSize = messageManager.getDisregardedMessages().size();
        int initialStoredSize = messageManager.getStoredMessages().size();

        messageManager.addMessage(unknownFlagMsg);

        assertEquals(initialSentSize, messageManager.getSentMessages().size(), "Sent list size should not change for unknown flag.");
        assertEquals(initialDisregardedSize, messageManager.getDisregardedMessages().size(), "Disregarded list size should not change for unknown flag.");
        assertEquals(initialStoredSize, messageManager.getStoredMessages().size(), "Stored list size should not change for unknown flag.");
    }

    // Tests for getTotalSentMessages method
    @Test
    @DisplayName("getTotalSentMessages should return 0 when no sent messages")
    void getTotalSentMessages_NoMessages_ReturnsZero() {
        assertEquals(0, messageManager.getTotalSentMessages(), "Should be 0 sent messages.");
    }

    @Test
    @DisplayName("getTotalSentMessages should return correct count for sent messages")
    void getTotalSentMessages_WithMessages_ReturnsCorrectCount() {
        messageManager.addMessage(sentMsg1);
        messageManager.addMessage(sentMsg2);
        messageManager.addMessage(disregardedMsg1); // Should not affect sent count
        assertEquals(2, messageManager.getTotalSentMessages(), "Should return 2 sent messages.");
    }

    // Tests for displayAllSentMessages method
    @Test
    @DisplayName("displayAllSentMessages should return 'No sent messages' when list is empty")
    void displayAllSentMessages_EmptyList_ReturnsNoMessageString() {
        assertEquals("No sent messages to display.", messageManager.displayAllSentMessages(), "Should indicate no messages.");
    }

    @Test
    @DisplayName("displayAllSentMessages should return formatted string for multiple sent messages")
    void displayAllSentMessages_WithMessages_ReturnsFormattedString() {
        messageManager.addMessage(sentMsg1);
        messageManager.addMessage(sentMsg2);

        String expected = "--- All Sent Messages ---\n" +
                "Recipient: " + sentMsg1.getRecipient() +
                "\nMessage: " + sentMsg1.getContent() +
                "\n-------------------------\n" +
                "Recipient: " + sentMsg2.getRecipient() +
                "\nMessage: " + sentMsg2.getContent() +
                "\n-------------------------\n";
        assertEquals(expected, messageManager.displayAllSentMessages(), "Should return correctly formatted string of all sent messages.");
    }

    // Tests for findLongestSentMessage method
    @Test
    @DisplayName("findLongestSentMessage should return null when no sent messages")
    void findLongestSentMessage_EmptyList_ReturnsNull() {
        assertNull(messageManager.findLongestSentMessage(), "Should return null for empty sent messages list.");
    }

    @Test
    @DisplayName("findLongestSentMessage should return the longest message")
    void findLongestSentMessage_WithMultipleMessages_ReturnsLongest() {
        messageManager.addMessage(sentMsg1); // Shorter
        messageManager.addMessage(sentMsg2); // Longer
        messageManager.addMessage(sentMsg3); // Medium

        assertEquals(sentMsg2, messageManager.findLongestSentMessage(), "Should return sentMsg2 as it has the longest content.");
    }

    @Test
    @DisplayName("findLongestSentMessage should return first longest if multiple have same length")
    void findLongestSentMessage_MultipleSameLongestLength_ReturnsFirstEncountered() {
        Message longMsgA = createTestMessage("+27111111111", "Longest message content A.", "Sent", "ID_LONG_A", "HASH_LONG_A");
        Message longMsgB = createTestMessage("+27222222222", "Longest message content B.", "Sent", "ID_LONG_B", "HASH_LONG_B");
        Message shortMsg = createTestMessage("+27333333333", "Short", "Sent", "ID_SHORT", "HASH_SHORT");

        messageManager.addMessage(longMsgA);
        messageManager.addMessage(shortMsg);
        messageManager.addMessage(longMsgB);

        assertEquals(longMsgA, messageManager.findLongestSentMessage(), "Should return the first longest message encountered.");
    }

    // Tests for searchMessageByID method
    @Test
    @DisplayName("searchMessageByID should find message in sentMessages")
    void searchMessageByID_FoundInSentMessages_ReturnsMessage() {
        messageManager.addMessage(sentMsg1);
        messageManager.addMessage(storedMsg1);
        assertEquals(sentMsg1, messageManager.searchMessageByID(sentMsg1.getMessageID()), "Should find sentMsg1 by ID.");
    }

    @Test
    @DisplayName("searchMessageByID should find message in disregardedMessages")
    void searchMessageByID_FoundInDisregardedMessages_ReturnsMessage() {
        messageManager.addMessage(disregardedMsg1);
        messageManager.addMessage(sentMsg1);
        assertEquals(disregardedMsg1, messageManager.searchMessageByID(disregardedMsg1.getMessageID()), "Should find disregardedMsg1 by ID.");
    }

    @Test
    @DisplayName("searchMessageByID should find message in storedMessages")
    void searchMessageByID_FoundInStoredMessages_ReturnsMessage() {
        messageManager.addMessage(storedMsg1);
        messageManager.addMessage(sentMsg1);
        assertEquals(storedMsg1, messageManager.searchMessageByID(storedMsg1.getMessageID()), "Should find storedMsg1 by ID.");
    }

    @Test
    @DisplayName("searchMessageByID should return null if message not found")
    void searchMessageByID_NotFound_ReturnsNull() {
        messageManager.addMessage(sentMsg1);
        assertNull(messageManager.searchMessageByID("NON_EXISTENT_ID"), "Should return null for non-existent ID.");
    }

    @Test
    @DisplayName("searchMessageByID should return null for empty ID")
    void searchMessageByID_EmptyID_ReturnsNull() {
        messageManager.addMessage(sentMsg1);
        assertNull(messageManager.searchMessageByID(""), "Should return null for empty ID.");
    }

    @Test
    @DisplayName("searchMessageByID should return null for null ID")
    void searchMessageByID_NullID_ReturnsNull() {
        messageManager.addMessage(sentMsg1);
        assertNull(messageManager.searchMessageByID(null), "Should return null for null ID.");
    }

    // Tests for searchMessagesByRecipient method
    @Test
    @DisplayName("searchMessagesByRecipient should find messages in sent and stored lists for same recipient")
    void searchMessagesByRecipient_MultipleMatches_ReturnsFormattedString() {
        messageManager.addMessage(sentMsg1);
        messageManager.addMessage(storedMsg1);
        messageManager.addMessage(sentMsg2);
        messageManager.addMessage(sentMsg3);

        String expected = "--- Messages for Recipient: +27111111111 ---\n" +
                "ID: " + sentMsg1.getMessageID() + ", Message: " + sentMsg1.getContent() + " (Sent)\n" +
                "ID: " + sentMsg3.getMessageID() + ", Message: " + sentMsg3.getContent() + " (Sent)\n" +
                "ID: " + storedMsg1.getMessageID() + ", Message: " + storedMsg1.getContent() + " (Stored)\n"; // Order in lists matters

        assertEquals(expected, messageManager.searchMessagesByRecipient("+27111111111"), "Should return all messages for the recipient.");
    }

    @Test
    @DisplayName("searchMessagesByRecipient should return 'No messages found' if no matches")
    void searchMessagesByRecipient_NoMatches_ReturnsNotFoundString() {
        messageManager.addMessage(sentMsg1);
        assertEquals("No messages found for recipient: +27999999999", messageManager.searchMessagesByRecipient("+27999999999"), "Should indicate no messages found.");
    }

    @Test
    @DisplayName("searchMessagesByRecipient should only search sent and stored messages")
    void searchMessagesByRecipient_OnlySentAndStoredConsidered() {
        messageManager.addMessage(disregardedMsg1); // Recipient +27333333333
        String result = messageManager.searchMessagesByRecipient("+27333333333");
        assertEquals("No messages found for recipient: +27333333333", result, "Disregarded messages should not be included in search by recipient.");
    }


    // --- Tests for deleteMessageByHash method ---
    @Test
    @DisplayName("deleteMessageByHash should delete message from sentMessages and return true")
    void deleteMessageByHash_FoundInSentMessages_DeletesAndReturnsTrue() {
        messageManager.addMessage(sentMsg1);
        messageManager.addMessage(storedMsg1); // Ensure other lists are untouched
        int initialSentSize = messageManager.getSentMessages().size();
        int initialStoredSize = messageManager.getStoredMessages().size();

        assertTrue(messageManager.deleteMessageByHash(sentMsg1.getMessageHash()), "Should return true for successful deletion from sent.");
        assertEquals(initialSentSize - 1, messageManager.getSentMessages().size(), "Sent messages list size should decrease by 1.");
        assertFalse(messageManager.getSentMessages().contains(sentMsg1), "Sent messages list should no longer contain the message.");
        assertEquals(initialStoredSize, messageManager.getStoredMessages().size(), "Stored messages list should remain unchanged.");
    }

    @Test
    @DisplayName("deleteMessageByHash should delete message from disregardedMessages and return true")
    void deleteMessageByHash_FoundInDisregardedMessages_DeletesAndReturnsTrue() {
        messageManager.addMessage(disregardedMsg1);
        int initialDisregardedSize = messageManager.getDisregardedMessages().size();

        assertTrue(messageManager.deleteMessageByHash(disregardedMsg1.getMessageHash()), "Should return true for successful deletion from disregarded.");
        assertEquals(initialDisregardedSize - 1, messageManager.getDisregardedMessages().size(), "Disregarded messages list size should decrease by 1.");
        assertFalse(messageManager.getDisregardedMessages().contains(disregardedMsg1), "Disregarded messages list should no longer contain the message.");
    }

    @Test
    @DisplayName("deleteMessageByHash should delete message from storedMessages and return true")
    void deleteMessageByHash_FoundInStoredMessages_DeletesAndReturnsTrue() {
        messageManager.addMessage(storedMsg1);
        int initialStoredSize = messageManager.getStoredMessages().size();

        assertTrue(messageManager.deleteMessageByHash(storedMsg1.getMessageHash()), "Should return true for successful deletion from stored.");
        assertEquals(initialStoredSize - 1, messageManager.getStoredMessages().size(), "Stored messages list size should decrease by 1.");
        assertFalse(messageManager.getStoredMessages().contains(storedMsg1), "Stored messages list should no longer contain the message.");
    }

    @Test
    @DisplayName("deleteMessageByHash should return false if message hash not found")
    void deleteMessageByHash_NotFound_ReturnsFalse() {
        messageManager.addMessage(sentMsg1);
        int initialSentSize = messageManager.getSentMessages().size();

        assertFalse(messageManager.deleteMessageByHash("NON_EXISTENT_HASH"), "Should return false if hash not found.");
        assertEquals(initialSentSize, messageManager.getSentMessages().size(), "List size should remain unchanged if not found.");
    }

    @Test
    @DisplayName("deleteMessageByHash should return false for empty hash")
    void deleteMessageByHash_EmptyHash_ReturnsFalse() {
        messageManager.addMessage(sentMsg1);
        assertFalse(messageManager.deleteMessageByHash(""), "Should return false for empty hash.");
    }

    @Test
    @DisplayName("deleteMessageByHash should return false for null hash")
    void deleteMessageByHash_NullHash_ReturnsFalse() {
        messageManager.addMessage(sentMsg1);
        assertFalse(messageManager.deleteMessageByHash(null), "Should return false for null hash.");
    }

    // --- Tests for generateMessageReport method ---
    @Test
    @DisplayName("generateMessageReport should return 'No sent messages' when list is empty")
    void generateMessageReport_EmptyList_ReturnsNoMessageString() {
        assertEquals("No sent messages for report.", messageManager.generateMessageReport(), "Should indicate no messages for report.");
    }

    @Test
    @DisplayName("generateMessageReport should return formatted report for multiple sent messages")
    void generateMessageReport_WithMessages_ReturnsFormattedReport() {
        messageManager.addMessage(sentMsg1);
        messageManager.addMessage(sentMsg2);
        messageManager.addMessage(disregardedMsg1); // Will not be in report
        messageManager.addMessage(storedMsg1); // Will not be in report

        String expected = "--- Sent Messages Report ---\n" +
                sentMsg1.toString() + "\n-------------------------\n" +
                sentMsg2.toString() + "\n-------------------------\n";

        assertEquals(expected, messageManager.generateMessageReport(), "Should return correctly formatted report of sent messages.");
    }

    // Tests for populateWithTestData method
    @Test
    @DisplayName("populateWithTestData should correctly populate all lists based on test data")
    void populateWithTestData_PopulatesListsCorrectly() {
        messageManager.populateWithTestData();

        // Verify counts as per the problem description's test data
        assertEquals(2, messageManager.getSentMessages().size(), "Sent messages list should have 2 messages after populating test data.");
        assertEquals(1, messageManager.getDisregardedMessages().size(), "Disregarded messages list should have 1 message after populating test data.");
        assertEquals(2, messageManager.getStoredMessages().size(), "Stored messages list should have 2 messages after populating test data.");
    }
}