package org.example;
import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null, "Register a New User"); // Registration

        String username = JOptionPane.showInputDialog("Enter username (must include '_' and be max 5 characters):");
        String password = JOptionPane.showInputDialog("Enter password (min 8 chars, 1 uppercase, 1 number, 1 special char):");
        String firstName = JOptionPane.showInputDialog("Enter first name:");
        String lastName = JOptionPane.showInputDialog("Enter last name:");
        String cellNumber = JOptionPane.showInputDialog("Enter cell number (+27XXXXXXXXX):");

        Login login = new Login(username, password, firstName, lastName, cellNumber);
        String registrationResult = login.registerUser();
        JOptionPane.showMessageDialog(null, registrationResult);

        if (!registrationResult.equals("User has been registered successfully.")) {
            JOptionPane.showMessageDialog(null, "Exiting program due to invalid registration.");
            return;
        }

        JOptionPane.showMessageDialog(null, "Log In"); // Login

        String loginUsername = JOptionPane.showInputDialog("Enter username:");
        String loginPassword = JOptionPane.showInputDialog("Enter password:");

        if (!login.loginUser(loginUsername, loginPassword)) {
            JOptionPane.showMessageDialog(null, "Username or password incorrect. Exiting...");
            return;
        }

        JOptionPane.showMessageDialog(null, "Welcome to QuickChat!");

        // Message Menu
        int choice = -1;
        while (choice != 3) {
            String[] options = { "Send Message", "Show Recently Sent Messages", "Quit" };
            choice = JOptionPane.showOptionDialog(null, "Choose an option:", "QuickChat Menu",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

            switch (choice) {
                case 0:
                    // Send message
                    String recipient = JOptionPane.showInputDialog("Enter recipient cell number (+27XXXXXXXXX):");
                    if (!Message.checkRecipientCell(recipient)) {
                        JOptionPane.showMessageDialog(null, "Invalid recipient number.");
                        break;
                    }

                    String content = JOptionPane.showInputDialog("Enter your message (max 250 chars):");
                    if (content.length() > 250) {
                        JOptionPane.showMessageDialog(null, "Please enter a message of less than 250 characters.");
                        break;
                    }

                    Message newMessage = new Message(recipient, content);
                    JOptionPane.showMessageDialog(null, "Message sent successfully!");
                    break;

                case 1:
                    // Show messages
                    String messages = Message.getMessages(); // You’ll need to implement this to return a string.
                    JOptionPane.showMessageDialog(null, "Recently sent messages:\n" + messages);
                    break;

                case 2:
                    JOptionPane.showMessageDialog(null, "Exiting. Total messages sent: " + Message.returnTotalMessages());
                    choice = 3; // To break loop
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Invalid choice. Try again.");
            }
        }
    }
}
