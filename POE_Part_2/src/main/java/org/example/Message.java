package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Message {
    private static int messageCount = 0;
    private static List<String> messageHistory = new ArrayList<>();

    private String messageID;
    private String recipient;
    private String content;
    private String messageHash;

    public Message(String recipient, String content) {
        this.messageID = generateMessageID();
        this.recipient = recipient;
        this.content = content;
        this.messageHash = createMessageHash();
        messageCount++;
        storeMessage();
    }

    // Generates a random 10-digit ID
    private String generateMessageID() {
        Random rand = new Random();
        long num = 1000000000L + (long) (rand.nextDouble() * 8999999999L); // random ID generated, the first number is the lowest, and the second is the highest
        return String.valueOf(num);
    }

    // Check if the Message ID is valid (max 10 chars)
    public boolean checkMessageID() {
        return messageID.length() <= 10;
    }

    // Check recipient number format
    public static boolean checkRecipientCell(String number) {
        return number != null && number.trim().matches("\\+27\\d{9}");
    }

    // Create hash using first 2 of ID, number of messages, and first+last words
    public String createMessageHash() {
        String[] words = content.trim().split("\\s+");
        String firstWord = words[0].toUpperCase();
        String lastWord = words[words.length - 1].toUpperCase();

        return messageID.substring(0, 2) + ":" + messageCount + ":" + firstWord + lastWord;
    }

    // User chooses to send/store/disregard message
    public static boolean sendMessageOption(String option) {
        return option.equals("1"); // Only "Send" adds it to list
    }

    // Display all sent messages (for console - you'll use getMessages for JOptionPane)
    public static void printMessages() {
        if (messageHistory.isEmpty()) {
            System.out.println("No messages have been sent.");
        } else {
            for (String msg : messageHistory) {
                System.out.println(msg);
                System.out.println("-------------------------");
            }
        }
    }

    // Return count of messages
    public static int returnTotalMessages() {
        return messageCount;
    }

    // Store message in list
    private void storeMessage() {
        String msg = "Message ID: " + messageID +
                "\nMessage Hash: " + messageHash +
                "\nRecipient: " + recipient +
                "\nMessage: " + content;
        messageHistory.add(msg);
    }

    // New method to get all messages as a single string for JOptionPane
    public static String getMessages() {
        if (messageHistory.isEmpty()) {
            return "No messages have been sent yet.";
        }
        StringBuilder sb = new StringBuilder();
        for (String msg : messageHistory) {
            sb.append(msg).append("\n-------------------------\n");
        }
        return sb.toString();
    }

}
