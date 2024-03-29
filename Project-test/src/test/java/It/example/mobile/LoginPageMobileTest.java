package It.example.mobile;

import org.example.LoginPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class LoginPageMobileTest extends BasePageMobileTest {

    LoginPage loginPage;

    String email = "Testukas1@gmail.com";
    String password = "Testukas123!";

    public void loginPageTestSteps(String email, String password) {
        loginPage = new LoginPage(driver);

        loginPage.clickOnHamburgerButton();
        loginPage.clickOnLoginButtonInNav();
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        loginPage.clickButtonToLogin();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/WrongPasswords")
    void wrongPasswordInputsTest(String input) {

        loginPageTestSteps(email, input);

        String errorMessage = loginPage.passwordInputErrorMessageText();
        Assertions.assertNotNull(errorMessage, "Turėjo parodyti klaidą");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/WrongEmails")
    void wrongEmailInputsTest(String input) {

        loginPageTestSteps(input, password);

        String errorMessage = loginPage.emailInputErrorMessageText();
        Assertions.assertNotNull(errorMessage, "Turėjo parodyti klaidą");
    }

    @Test
    void successfulLoginTest() {
        String expectedUrl = "http://localhost:5173/";

        loginPageTestSteps(email, password);

        loginPage.clickButtonToLogin();

        loginPage.waitForGetUrl("http://localhost:5173/");

        String actualUrl = driver.getCurrentUrl();

        Assertions.assertEquals(expectedUrl, actualUrl);
        Assertions.assertEquals(email, loginPage.getEmailInLogin().replace("Your email is: ", ""));
        Assertions.assertTrue(loginPage.getSuccessfullyAlert().isDisplayed(), "Prisijungimo klaida");
    }

    @Test
    void failedLoginTest() {
        String exppectedErrorMessage = "Error: The email or password provided is incorrect.";

        loginPageTestSteps("email@gmail.com", password);
        loginPage.waitForGetUrl("http://localhost:5173/login");
        Assertions.assertEquals(exppectedErrorMessage, loginPage.incorrectPaswordOrEmailMessage());
    }

    @Test
    void redirectToRegisterPageTest() {
        String expectedUrl = "http://localhost:5173/register";

        loginPage = new LoginPage(driver);
        loginPage.clickOnHamburgerButton();
        loginPage.clickOnLoginButtonInNav();
        loginPage.clickToCreateAccount();

        Assertions.assertEquals(expectedUrl, driver.getCurrentUrl());
    }

    @Test
    public void showingPasswordTest() {
        loginPage = new LoginPage(driver);

        loginPage.clickOnHamburgerButton();
        loginPage.clickOnLoginButtonInNav();
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        loginPage.clickOnShowPassword();

        String actual = loginPage.getPasswordFieldValue();
        String expected = password;

        Assertions.assertEquals(expected, actual);
        Assertions.assertTrue(loginPage.passwordField().getAttribute("type").equals("text"));
    }

    @Test
    public void logoutTest() {
        loginPage = new LoginPage(driver);

        loginPage.clickOnHamburgerButton();
        String buttonTextBefore = loginPage.getRegisterButtonInNav().getText();
        Assertions.assertEquals("Register", buttonTextBefore);

        loginPage.clickOnHamburgerButton();
        loginPageTestSteps(email, password);

        loginPage.waitForGetUrl("http://localhost:5173/");
        loginPage.clickOnHamburgerButton();
        String buttonTextAfter = loginPage.getRegisterButtonInNav().getText();
        Assertions.assertEquals("Categories", buttonTextAfter);

        loginPage.clickOnLogoutButton();

        String expectedMessage = "Logout Successful!";
        String expectedUrl = "http://localhost:5173/";

        Assertions.assertTrue(loginPage.getLogoutMessage().isDisplayed());
        Assertions.assertEquals(expectedMessage, loginPage.getLogoutMessageText());
        String actualUrl = driver.getCurrentUrl();
        Assertions.assertEquals(expectedUrl, actualUrl);
    }
}