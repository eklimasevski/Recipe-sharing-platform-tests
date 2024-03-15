package It.example;

import org.example.LoginPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPageTest extends BasePageTest {

    LoginPage loginPage;

    String email = "Testukas1@gmail.com";
    String password = "aA1=ddr";

    public void loginPageTestSteps(String email, String password) {
        loginPage = new LoginPage(driver);

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
        String expectedUrl = "http://localhost:5173/login-successful";

        loginPageTestSteps(email, password);

        loginPage.clickButtonToLogin();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/login-successful"));

        String actualUrl = driver.getCurrentUrl();

        Assertions.assertEquals(expectedUrl, actualUrl);
        Assertions.assertEquals(email, loginPage.getEmailInLogin().replace("Your email is: ", ""));
        Assertions.assertTrue(loginPage.getSuccessfullyLoginAlert().isDisplayed(), "Prisijungimo klaida");
    }

    @Test
    void failedLoginTest() {
        String exppectedErrorMessage = "Error: The email or password provided is incorrect.";

        loginPageTestSteps("email@gmail.com", password);
        Assertions.assertEquals(exppectedErrorMessage, loginPage.incorrectPaswordOrEmailMessage());
    }

    @Test
    void redirectToRegisterPageTest() {
        String expectedUrl = "http://localhost:5173/register";

        loginPage = new LoginPage(driver);
        loginPage.clickOnLoginButtonInNav();
        loginPage.clickToCreateAccount();

        Assertions.assertEquals(expectedUrl, driver.getCurrentUrl());
    }

    @Test
    public void mobileVersionRegistrationTest() {
        String expectedUrl = "http://localhost:5173/login-successful";
        driver.manage().window().setSize(new Dimension(375, 667));
        loginPage = new LoginPage(driver);

        loginPage.clickOnHamburgerButton();
        loginPageTestSteps(email, password);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/login-successful"));

        String actualUrl = driver.getCurrentUrl();

        Assertions.assertTrue(loginPage.getSuccessfullyLoginAlert().isDisplayed(), "Prisijungimo klaida");
        Assertions.assertEquals(expectedUrl, actualUrl);
        Assertions.assertEquals(email, loginPage.getEmailInLogin().replace("Your email is: ", ""));
    }

    @Test
    public void showingPasswordTest() {
        loginPage = new LoginPage(driver);

        loginPage.clickOnLoginButtonInNav();
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        loginPage.clickOnShowPassword();

        String actual = loginPage.getPasswordFieldValue();
        String expected = password;

        Assertions.assertEquals(expected, actual);
        Assertions.assertTrue(loginPage.passwordField().getAttribute("type").equals("text"));
    }
}