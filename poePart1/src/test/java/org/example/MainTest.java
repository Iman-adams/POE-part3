package org.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    public static Main util;

    @org.junit.jupiter.api.BeforeAll
    public static void setUp() {
        util = new Main("kyle", "Adams", "kyl_1", "Ch&&sec@ke99", "+27838968976");
    }

    @Test
    void checkUsername() {
        Assertions.assertAll(
                // Each assertion needs to be a lambda expression
                () -> assertTrue(util.checkUsername()),
                () -> assertEquals("kyl_1", util.username),
                () -> assertTrue(util.username.length() >= 5)
        );
    }

    @Test
    void checkPasswordComplexity() {
        Assertions.assertAll(
                () -> assertTrue(util.checkPasswordComplexity()),
                () -> assertEquals("Ch&&sec@ke99", util.password),
                () -> assertNotNull(util.password)
        );
    }

    @Test
    void checkCellPhoneNumber() {
        Assertions.assertAll(
                () -> assertTrue(util.checkCellPhoneNumber()),
                () -> assertEquals("+27838968976", util.cellPhoneNumber),
                () -> assertTrue(util.cellPhoneNumber.startsWith("+27"))
        );
    }

    @Test
    void loginUser() {
        Assertions.assertAll(
                () -> assertTrue(util.loginUser("kyl_1", "Ch&&sec@ke99")),
                () -> assertFalse(util.loginUser("wrongUser", "wrongPass")),
                () -> assertEquals("kyl_1", util.username),
                () -> assertEquals("Ch&&sec@ke99", util.password)
        );
    }
}