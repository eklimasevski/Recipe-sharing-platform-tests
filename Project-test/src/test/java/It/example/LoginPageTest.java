package It.example;

import org.example.LoginPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

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
    void successfulLoginTest() throws InterruptedException {
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
    void failedLoginTest() throws InterruptedException {
        String exppectedErrorMessage = "Error: The email or password provided is incorrect.";

        loginPageTestSteps("email@gmail.com", password);
//        Thread.sleep(1500);
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
    public void mobileVersionLoginTest() {
        String expectedUrl = "http://localhost:5173";
        driver.manage().window().setSize(new Dimension(375, 667));
        loginPage = new LoginPage(driver);

        loginPage.clickOnHamburgerButton();
        loginPageTestSteps(email, password);

        loginPage.waitForGetUrl("/login-successful");

        String actualUrl = driver.getCurrentUrl();

        Assertions.assertTrue(loginPage.getSuccessfullyAlert().isDisplayed(), "Prisijungimo klaida");
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

    @Test
    public void mobileVersionLogoutTest() {
        driver.manage().window().setSize(new Dimension(375, 667));

        String expectedUrl = "http://localhost:5173";
        String expectedAlert = "Logout Successful!";

        loginPage = new LoginPage(driver);

        WebElement checkButton = loginPage.getRegisterButtonInNav();
        Assertions.assertFalse(checkButton.isDisplayed());

        loginPage.clickOnHamburgerButton();
        loginPageTestSteps(email, password);
        loginPage.waitForGetUrl("/login-successful");
        loginPage.clickOnLogoutButton();

        String actualUrl = driver.getCurrentUrl();
        String actualAlert = loginPage.getLogoutMessageText();

        Assertions.assertTrue(loginPage.getLogoutMessage().isDisplayed());
        Assertions.assertEquals(expectedAlert, actualAlert);
        Assertions.assertEquals(expectedUrl, actualUrl);
    }

    @Test
    public void logoutTest() {
        loginPageTestSteps(email, password);
        loginPage.waitForGetUrl("/login-successful");

       WebElement checkButton = loginPage.getRegisterButtonInNav();
       Assertions.assertFalse(checkButton.isDisplayed());

        loginPage.clickOnLogoutButton();

        String expectedMessage = "Logout Successful!";
        String expectedUrl = "http://localhost:5173";

        Assertions.assertTrue(loginPage.getLogoutMessage().isDisplayed());
        Assertions.assertEquals(expectedMessage, loginPage.getLogoutMessageText());
        String actualUrl = driver.getCurrentUrl();
        Assertions.assertEquals(expectedUrl, actualUrl);
    }
}