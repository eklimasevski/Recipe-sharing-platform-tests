package It.example;

import org.example.LoginPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class LoginPageTest extends BasePageTest {

    LoginPage loginPage;

    @ParameterizedTest
    @CsvFileSource(resources = "/WrongPasswords.csv")
    void wrongPasswordInputsTest(String input) {

        loginPage = new LoginPage(driver);

        loginPage.clickOnLoginButtonInNav();
        loginPage.enterEmail("Tetukas123@gmail.com");
        loginPage.enterPassword(input);
        loginPage.clickButtonToLogin();

        String errorMessage = loginPage.passwordInputErrorMessageText();

        Assertions.assertNotNull(errorMessage, "Turėjo parodyti klaidą");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/WrongEmails.csv")
    void wrongEmailInputsTest(String input) {

        loginPage = new LoginPage(driver);

        loginPage.clickOnLoginButtonInNav();
        loginPage.enterEmail(input);
        loginPage.enterPassword("Testukas123!");
        loginPage.clickButtonToLogin();

        String errorMessage = loginPage.emailInputErrorMessageText();

        Assertions.assertNotNull(errorMessage, "Turėjo parodyti klaidą");
    }
}