package It.example;

import dev.failsafe.internal.util.Assert;
import org.example.HomePage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class LoginPageTest extends BasePageTest{

    HomePage homePage;

    @ParameterizedTest
    @ValueSource(strings = {"1234", "Test", " !@#5"})
    void passwordInputTest(String input){

        homePage = new HomePage(driver);

        homePage.clickOnLogin();
        homePage.enterEmailInLogin("Tetukas123@gmail.com");
        homePage.enterPasswordInlogin(input);
        homePage.clickButtonToLogin();

        String errorMessage = homePage.loginPagePasswordInputErrorMessage();

        Assertions.assertNotNull(errorMessage,"Turėjo parodyti klaida");
    }

    @ParameterizedTest
    @ValueSource(strings = {"Testukas123@@gmail.com", "Testukas123gmail.com","   @gmail.com", "!@#!", "Test","Testukas123@gmailcom"})
    void emailInputTest(String input){

        homePage = new HomePage(driver);

        homePage.clickOnLogin();
        homePage.enterEmailInLogin(input);
        homePage.enterPasswordInlogin("Testukas123!");
        homePage.clickButtonToLogin();

        String errorMessage = homePage.loginPageEmailInputErrorMessage();

        Assertions.assertNotNull(errorMessage,"Turėjo parodyti klaida");
    }
}