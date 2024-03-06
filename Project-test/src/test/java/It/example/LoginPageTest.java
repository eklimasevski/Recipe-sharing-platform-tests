package It.example;

import org.example.LoginPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

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
}