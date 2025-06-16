package org.example;

import java.util.Scanner;

public class ApplicationRunner {
    private Login login;
    private MessageManager messageManager;
    private Scanner scanner;

    public ApplicationRunner() {
        this.messageManager = new MessageManager();
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        // Line 18 to 33 is for Registration
        System.out.println("Register a New User");

        System.out.print("Enter username (must include '_' and be max 5 characters): ");
        String username = scanner.nextLine();

        System.out.print("Enter password (min 8 chars, 1 uppercase, 1 number, 1 special char): ");
        String password = scanner.nextLine();

        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter cell number (+27XXXXXXXXX): ");
        String cellNumber = scanner.nextLine();

        this.login = new Login(username, password, firstName, lastName, cellNumber);
        String registrationResult = login.registerUser();
        System.out.println(registrationResult);

        // If registration fails, exit
        if (!registrationResult.equals("User has been registered successfully.")) {
            System.out.println("Exiting program due to invalid registration.");
            return;
        }

        // User Login
        System.out.println("\nLog In");

        System.out.print("Enter username: ");
        String loginUsername = scanner.nextLine();

        // Check login credentials. Exit if incorrect
        System.out.print("Enter password: ");
        String loginPassword = scanner.nextLine();

        if (!login.loginUser(loginUsername, loginPassword)) {
            System.out.println("Username or password incorrect. Exiting...");
            return;
        }

        System.out.println("Welcome to QuickChat!\n");

        // Load some initial message
        messageManager.populateWithTestData();

        String[] options = {
                "Send Message",
                "Show All Sent Messages (Recipient & Content)",
                "Show Longest Sent Message",
                "Search Message by ID",
                "Search Messages by Recipient",
                "Delete Message by Hash",
                "Display Sent Messages Report",
                "Quit"
        };

        int choice = -1;

        // Main application loop
        while (choice != 7) {
            System.out.println("\n--- QuickChat Menu ---");
            for (int i = 0; i < options.length; i++) {
                System.out.println(i + ". " + options[i]);
            }
            System.out.print("Enter your choice (0-7): ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // consume newline after reading int
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume invalid input
                continue; // Loop again
            }

            switch (choice) {
                case 0: // Send Message
                    System.out.print("Enter recipient cell number (+27XXXXXXXXX): ");
                    String recipient = scanner.nextLine();
                    if (!Message.checkRecipientCell(recipient)) {
                        System.out.println("Invalid recipient number.");
                        break;
                    }

                    System.out.print("Enter your message (max 250 chars): ");
                    String content = scanner.nextLine();
                    if (content.trim().isEmpty()) {
                        System.out.println("Message cannot be empty.");
                        break;
                    }
                    if (content.length() > 250) {
                        System.out.println("Please enter a message of less than 250 characters.");
                        break;
                    }

                    System.out.println("Choose message status:");
                    System.out.println("0. Sent\n1. Stored\n2. Disregarded");
                    int statusChoice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    String[] statusOptions = {"Sent", "Stored", "Disregarded"};
                    // Ensure choice is within bounds
                    String messageStatus = statusOptions[Math.max(0, Math.min(statusChoice, 2))];

                    Message newMessage = new Message(recipient, content, messageStatus);
                    messageManager.addMessage(newMessage);
                    System.out.println("Message " + messageStatus.toLowerCase() + " successfully!");
                    break;

                case 1: // Shows All Sent Messages
                    System.out.println(messageManager.displayAllSentMessages());
                    break;

                case 2: // Show Longest Sent Messages
                    Message longest;
                    longest = messageManager.findLongestSentMessage();
                    if (longest != null) {
                        System.out.println("Longest Sent Message:\n" + longest);
                    } else {
                        System.out.println("No sent messages to find the longest one.");
                    }
                    break;

                case 3: // Search Messages by ID
                    System.out.print("Enter Message ID to search: ");
                    String searchID = scanner.nextLine();
                    Message foundByID = messageManager.searchMessageByID(searchID);
                    if (foundByID != null) {
                        System.out.println("Message Found:\n" + foundByID);
                    } else {
                        System.out.println("Message with ID '" + searchID + "' not found.");
                    }
                    break;

                case 4: // Search Messages by Recipient
                    System.out.print("Enter recipient cell number to search (+27XXXXXXXXX): ");
                    String searchRecipient = scanner.nextLine();
                    if (!Message.checkRecipientCell(searchRecipient)) {
                        System.out.println("Invalid recipient number format.");
                        break;
                    }
                    System.out.println(messageManager.searchMessagesByRecipient(searchRecipient));
                    break;

                case 5: // Delete Message by Hash
                    System.out.print("Enter Message Hash to delete: ");
                    String deleteHash = scanner.nextLine();
                    if (messageManager.deleteMessageByHash(deleteHash)) {
                        System.out.println("Message with hash '" + deleteHash + "' successfully deleted.");
                    } else {
                        System.out.println("Message with hash '" + deleteHash + "' not found.");
                    }
                    break;

                case 6: // Display Sent Messages Report
                    System.out.println(messageManager.generateMessageReport());
                    break;

                case 7: // Quit
                    System.out.println("Exiting. Total messages sent: " + messageManager.getTotalSentMessages());
                    break;

                default: // Invalid input
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}