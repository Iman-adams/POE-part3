package org.example;

import java.util.Scanner; // Import the Scanner class for user imput
import java.util.regex.Pattern; // Import the pattern class for regular expressions

import java.util.regex.Pattern;

public class Main {
    // Fields
    String firstName;
    String lastName;
    String username;
    String password;
    String cellPhoneNumber;

    // Constructor with all fields
    public Main(String firstName, String lastName, String username, String password, String cellPhoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.cellPhoneNumber = cellPhoneNumber;
    }

    // Username must have "_" and be <= 5 characters
    public boolean checkUsername() {
        return username.length() <= 5 && username.contains("_");
    }

    // Password must have 1 uppercase, 1 digit, 1 special char, and be >= 8 chars
    public boolean checkPasswordComplexity() {
        String passwordRegex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%#?&])[A-Za-z\\d@$!%*#?&]{8,}$";
        return password.matches(passwordRegex);
    }

    // Cell number must start with +27 and be 12 characters total
    public boolean checkCellPhoneNumber() {
        return cellPhoneNumber.startsWith("+27") && cellPhoneNumber.length() == 12;
    }

    // Register user
    public String registerUser() {
        if (!checkUsername()) return "Username incorrectly formatted";
        if (!checkPasswordComplexity()) return "Password does not meet complexity requirements";
        if (!checkCellPhoneNumber()) return "Cell phone number incorrectly formatted";
        return "Registration successful";
    }

    // Login method
    public boolean loginUser(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    // Main method (optional for direct execution)
    public static void main(String[] args) {
        // Scanner for user input (if you wish to run the program directly)
        java.util.Scanner scanner = new java.util.Scanner(System.in);

        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Enter cell phone number: ");
        String cellPhoneNumber = scanner.nextLine();

        // Create user
        Main main = new Main(firstName, lastName, username, password, cellPhoneNumber);

        // Register and print result
        System.out.println(main.registerUser());

        // Login
        System.out.print("Enter username to login: ");
        String loginUsername = scanner.nextLine();

        System.out.print("Enter password to login: ");
        String loginPassword = scanner.nextLine();

        if (main.loginUser(loginUsername, loginPassword)) {
            System.out.println("Login successful");
        } else {
            System.out.println("Login failed");
        }

        scanner.close();
    }
}
