package org.example;

public class Login {
    private User registeredUser;
    private String enteredUsername;
    private String enteredPassword;

    // Set up a new user for registration
    public Login(String username, String password, String firstName, String lastName, String cellNumber) {
        this.registeredUser = new User(username, password, cellNumber, firstName, lastName);
    }

    // Checks if the username format is valid
    public boolean checkUserName(String username) {
        if (username == null){
            return false;
        }
        return username.contains("_") && username.length() <= 5;
    }

    // Validates password strength
    public boolean checkPasswordComplexity(String password) {
        if (password == null) {
            return false;
        }
        boolean isLongEnough = password.length() >= 8;
        boolean hasUppercase = !password.equals(password.toLowerCase());
        boolean hasNumber = password.matches(".*\\d.*");
        boolean hasSpecialChar = password.matches(".*[!@#$%^&*(),.?\":{}|<>].*");

        return isLongEnough && hasUppercase && hasNumber && hasSpecialChar;
    }

    // Checks if the cell number is in the correct format (+27XXXXXXXXX)
    public boolean checkCellPhoneNumber(String number) {
        return number != null && number.trim().matches("\\+27\\d{9}"); // Reusing validation from Message class
    }

    // Attempt to register the user based on provided details
    public String registerUser() {
        String username = registeredUser.getUsername();
        String password = registeredUser.getPassword();
        String cell = registeredUser.getCellNumber();

        // show the user their details entered when the registered
        System.out.println("Username = [" + username + "]");
        System.out.println("Password = [" + password + "]");
        System.out.println("Cell number = [" + cell + "]");

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

    // Authenticates user login
    public boolean loginUser(String username, String password) {
        this.enteredUsername = username;
        this.enteredPassword = password;

        return enteredUsername.equals(registeredUser.getUsername()) &&
                enteredPassword.equals(registeredUser.getPassword());
    }

    // Returns a login message based on credentials
    public String returnLoginStatus(String username, String password) {
        if (loginUser(username, password)) {
            return "Welcome " + registeredUser.getFirstName() + " " + registeredUser.getLastName() + ", it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }

    //  Not currently used, but here if needed later
    public void setRegisteredUser(User user) {
        this.registeredUser = user;
    }
}