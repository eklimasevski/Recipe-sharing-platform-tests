package It.example;

import org.example.LoginPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPageTest extends BasePageTest {

    LoginPage loginPage;

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

        loginPageTestSteps("Testukas123@gmail.com", input);

        String errorMessage = loginPage.passwordInputErrorMessageText();
        Assertions.assertNotNull(errorMessage, "Turėjo parodyti klaidą");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/WrongEmails")
    void wrongEmailInputsTest(String input) {

        loginPageTestSteps(input, "Testukas123");

        String errorMessage = loginPage.emailInputErrorMessageText();
        Assertions.assertNotNull(errorMessage, "Turėjo parodyti klaidą");
    }

    @Test
    void successfulLoginTest() {
        String expectedUrl = "http://localhost:5173/login-successful";
        String email = "Testukas1@gmail.com";

        loginPageTestSteps(email, "aA1=ddr");
        loginPage.clickButtonToLogin();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/login-successful"));

        String actualUrl = driver.getCurrentUrl();

        Assertions.assertEquals(expectedUrl, actualUrl);
        Assertions.assertEquals(email, loginPage.getEmailInLogin().replace("Your email is: ", ""));
        Assertions.assertTrue(loginPage.getSuccessfullyLoginAlert().isDisplayed(), "Prisijungimo klaida");
    }
}