package org.example;

import java.util.Random;

public class Message {
    private String messageID;
    private String recipient;
    private String content;
    private String messageHash;
    private String flag; // New field to indicate message status: "Sent", "Stored", "Disregarded"

    public Message(String recipient, String content, String flag) {
        this.recipient = recipient;
        this.content = content;
        this.flag = flag; // Set the status
        this.messageID = generateMessageID(); // Generate ID for that message
        this.messageHash = createMessageHash(); // Create hash for easy lookup
    }

    // --- Getters for all fields ---
    public String getMessageID() {
        return messageID;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getContent() {
        return content;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public String getFlag() {
        return flag;
    }

    // Generates a random 10-digit message ID
    private String generateMessageID() {
        Random rand = new Random();
        long num = 1_000_000_000L + (long) (rand.nextDouble() * 9_000_000_000L); // This generates a long between 1,000,000,000 and 9,999,999,999
        return String.valueOf(num);
    }

    // Check if the message ID is valid (max 10 chars)
    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }

    // Static helper to check recipient cell number format
    public static boolean checkRecipientCell(String number) {
        return number != null && number.trim().matches("\\+27\\d{9}"); // Expecting format: +27 followed by exactly 9 digits
    }

    // Create hash using first 2 of ID, content length (instead of messageCount), and first+last words
    public String createMessageHash() {
        String[] words = content.trim().split("\\s+");
        String firstWord = "";
        String lastWord = "";

        if (words.length > 0) {
            firstWord = words[0].toUpperCase();
            if (words.length > 1) {
                lastWord = words[words.length - 1].toUpperCase();
            } else {
                lastWord = firstWord; // If only one word, it's both first and last
            }
        }

        // Get first two chars of ID for the hash prefix. Default to "XX" if ID
        String idPrefix = (messageID != null && messageID.length() >= 2) ? messageID.substring(0, 2) : "XX";
        // Use trimmed content length for consistency with word extraction
        return idPrefix + ":" + content.length() + ":" + firstWord + lastWord;
    }

    // Override toString for easier nicer looking display of the message details
    @Override
    public String toString() {
        return "Message ID: " + messageID +
                "\nMessage Hash: " + messageHash +
                "\nRecipient: " + recipient +
                "\nMessage: " + content +
                "\nStatus: " + flag;
    }
}