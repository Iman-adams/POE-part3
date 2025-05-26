package org.example;

public class Login {
    private User registeredUser;
    private String enteredUsername;
    private String enteredPassword;

    // Constructor to register a user
    public Login(String username, String password, String firstName, String lastName, String cellNumber) {
        // Fixed parameter order to match User class constructor
        this.registeredUser = new User(username, password, cellNumber, firstName, lastName);
    }

    public boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    public boolean checkPasswordComplexity(String password) {
        boolean isLongEnough = password.length() >= 8;
        boolean hasUppercase = !password.equals(password.toLowerCase());
        boolean hasNumber = password.matches(".*\\d.*");
        boolean hasSpecialChar = password.matches(".*[!@#$%^&*(),.?\":{}|<>].*");

        return isLongEnough && hasUppercase && hasNumber && hasSpecialChar;
    }

    public boolean checkCellPhoneNumber(String number) {
        // Expecting format: +27 followed by exactly 9 digits
        return number != null && number.trim().matches("\\+27\\d{9}");
    }

    // Validate and register the user
    public String registerUser() {
        String username = registeredUser.getUsername();
        String password = registeredUser.getPassword();
        String cell = registeredUser.getCellNumber();

        System.out.println("DEBUG: Username = [" + username + "]");
        System.out.println("DEBUG: Password = [" + password + "]");
        System.out.println("DEBUG: Cell number = [" + cell + "]");

        if (!checkUserName(username)) {
            return "Username is incorrectly formatted.";
        }
        if (!checkPasswordComplexity(password)) {
            return "Password does not meet the complexity requirements.";
        }
        if (!checkCellPhoneNumber(cell)) {
            return "Cell phone number is incorrectly formatted.";
        }

        return "User has been registered successfully.";
    }

    public boolean loginUser(String username, String password) {
        this.enteredUsername = username;
        this.enteredPassword = password;

        return enteredUsername.equals(registeredUser.getUsername()) &&
                enteredPassword.equals(registeredUser.getPassword());
    }

    public String returnLoginStatus(String username, String password) {
        if (loginUser(username, password)) {
            return "Welcome " + registeredUser.getFirstName() + " " + registeredUser.getLastName() + ", it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }

    // Optional setter if needed
    public void setRegisteredUser(User user) {
        this.registeredUser = user;
    }
}
