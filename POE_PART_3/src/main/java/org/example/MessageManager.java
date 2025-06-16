package org.example;

import java.util.ArrayList;
import java.util.List;

public class MessageManager {
    private List<Message> sentMessages;
    private List<Message> disregardedMessages;
    private List<Message> storedMessages;

    public MessageManager() {
        this.sentMessages = new ArrayList<>();
        this.disregardedMessages = new ArrayList<>();
        this.storedMessages = new ArrayList<>();
    }

     // Adds a message to the right list based on its flag.
    public void addMessage(Message message) {
        if (message == null) {
            return;
        }
        switch (message.getFlag()) {
            case "Sent":
                sentMessages.add(message);
                break;
            case "Disregarded":
                disregardedMessages.add(message);
                break;
            case "Stored":
                storedMessages.add(message);
                break;
            default:
                //  Log if we get an unexpected flag
                System.err.println("Warning: Message with unknown flag '" + message.getFlag() + "' not categorized.");
                break;
        }
    }

    // Returns the total number of sent messages.
    public int getTotalSentMessages() {
        return sentMessages.size();
    }

    // Shows recipient and content for all sent messages.
    public String displayAllSentMessages() {
        if (sentMessages.isEmpty()) {
            return "No sent messages to display.";
        }
        StringBuilder sb = new StringBuilder("--- All Sent Messages ---\n");
        for (Message msg : sentMessages) {
            sb.append("Recipient: ").append(msg.getRecipient())
                    .append("\nMessage: ").append(msg.getContent())
                    .append("\n-------------------------\n");
        }
        return sb.toString();
    }

    // Finds the longest sent message.
    public Message findLongestSentMessage() {
        if (sentMessages.isEmpty()) {
            return null;
        }
        Message longestMessage = sentMessages.get(0);
        for (Message msg : sentMessages) {
            if (msg.getContent().length() > longestMessage.getContent().length()) {
                longestMessage = msg;
            }
        }
        return longestMessage;
    }

    // Searches for a message by its ID across all message lists.
    public Message searchMessageByID(String messageID) {
        for (Message msg : sentMessages) {
            if (msg.getMessageID().equals(messageID)) {
                return msg;
            }
        }
        for (Message msg : disregardedMessages) {
            if (msg.getMessageID().equals(messageID)) {
                return msg;
            }
        }
        for (Message msg : storedMessages) {
            if (msg.getMessageID().equals(messageID)) {
                return msg;
            }
        }
        return null;
    }

    // Searches all messages (sent and stored) for a particular recipient.
    public String searchMessagesByRecipient(String recipientNumber) {
        StringBuilder sb = new StringBuilder("--- Messages for Recipient: " + recipientNumber + " ---\n");
        boolean found = false;

        // Search sent messages
        for (Message msg : sentMessages) {
            if (msg.getRecipient().equals(recipientNumber)) {
                sb.append("ID: ").append(msg.getMessageID())
                        .append(", Message: ").append(msg.getContent())
                        .append(" (Sent)\n");
                found = true;
            }
        }
        // Search stored messages
        for (Message msg : storedMessages) {
            if (msg.getRecipient().equals(recipientNumber)) {
                sb.append("ID: ").append(msg.getMessageID())
                        .append(", Message: ").append(msg.getContent())
                        .append(" (Stored)\n");
                found = true;
            }
        }

        if (!found) {
            return "No messages found for recipient: " + recipientNumber;
        }
        return sb.toString();
    }

    // Deletes a message using its message hash from any of the lists.
    public boolean deleteMessageByHash(String messageHash) {
        boolean deleted = false;
        // Search and delete from sentMessages
        for (int i = 0; i < sentMessages.size(); i++) {
            if (sentMessages.get(i).getMessageHash().equals(messageHash)) {
                sentMessages.remove(i);
                deleted = true;
                break;
            }
        }
        if (!deleted) { // Only search other lists if not found in sentMessages
            // Search and delete from disregardedMessages
            for (int i = 0; i < disregardedMessages.size(); i++) {
                if (disregardedMessages.get(i).getMessageHash().equals(messageHash)) {
                    disregardedMessages.remove(i);
                    deleted = true;
                    break;
                }
            }
        }
        if (!deleted) { // Only search other lists if not found in previous ones
            // Search and delete from storedMessages
            for (int i = 0; i < storedMessages.size(); i++) {
                if (storedMessages.get(i).getMessageHash().equals(messageHash)) {
                    storedMessages.remove(i);
                    deleted = true;
                    break;
                }
            }
        }
        return deleted;
    }

    // Generates a report listing full details of all sent messages.
    public String generateMessageReport() {
        if (sentMessages.isEmpty()) {
            return "No sent messages for report.";
        }
        StringBuilder sb = new StringBuilder("--- Sent Messages Report ---\n");
        for (Message msg : sentMessages) {
            sb.append(msg.toString()).append("\n-------------------------\n");
        }
        return sb.toString();
    }

    // Adds some default test data messages
    public void populateWithTestData() {
        // Test Data Message 1: Sent
        addMessage(new Message("+27834557896", "Did you get the cake?", "Sent"));

        // Test Data Message 2: Stored
        addMessage(new Message("+27838884567", "Where are you? You are late! I have asked you to be on time.", "Stored"));

        // Test Data Message 3: Disregarded
        addMessage(new Message("+27834484567", "Yohoooo, I am at your gate.", "Disregarded"));

        // Test Data Message 4: Sent
        // Adjusting to fit recipient pattern +27XXXXXXXXX
        addMessage(new Message("+27838884567", "It is dinner time!", "Sent"));

        // Test Data Message 5: Stored
        addMessage(new Message("+27838884567", "Ok, I am leaving without you.", "Stored"));

        System.out.println("Test data populated successfully.");
    }

    // Getters for the lists (useful for testing)
    public List<Message> getSentMessages() {
        return sentMessages;
    }

    public List<Message> getDisregardedMessages() {
        return disregardedMessages;
    }

    public List<Message> getStoredMessages() {
        return storedMessages;
    }
}