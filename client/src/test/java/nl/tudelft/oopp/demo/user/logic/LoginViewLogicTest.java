package nl.tudelft.oopp.demo.user.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginViewLogicTest {

    @Test
    /**
     * tests if the input is v
     */
    void isValidInputTest(){
        String usernameInput = "";
        String passwordInput = "";

        assertEquals("The username field cannot be left empty !", LoginViewLogic.isValidInput(
                usernameInput, passwordInput));

        usernameInput = "username";

        assertEquals("The password field cannot be left empty !", LoginViewLogic.isValidInput(
                usernameInput, passwordInput));

        passwordInput = "Password01";

        assertEquals("Good!", LoginViewLogic.isValidInput(usernameInput, passwordInput));
    }
}