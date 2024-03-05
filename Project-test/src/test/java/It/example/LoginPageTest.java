package It.example;

import com.beust.jcommander.Parameters;
import dev.failsafe.internal.util.Assert;
import org.example.HomePage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

public class LoginPageTest extends BasePageTest {

    HomePage homePage;

    @ParameterizedTest
    @CsvFileSource(resources = "/passwordInputs.csv")
    void wrongPasswordInputsTest(String input) {

        homePage = new HomePage(driver);

        homePage.clickOnLogin();
        homePage.enterEmailInLogin("Tetukas123@gmail.com");
        homePage.enterPasswordInlogin(input);
        homePage.clickButtonToLogin();

        String errorMessage = homePage.loginPagePasswordInputErrorMessage();

        Assertions.assertNotNull(errorMessage, "Turėjo parodyti klaidą");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/emailInputs.csv")
    void wrongEmailInputsTest(String input) {

        homePage = new HomePage(driver);

        homePage.clickOnLogin();
        homePage.enterEmailInLogin(input);
        homePage.enterPasswordInlogin("Testukas123!");
        homePage.clickButtonToLogin();

        String errorMessage = homePage.loginPageEmailInputErrorMessage();

        Assertions.assertNotNull(errorMessage, "Turėjo parodyti klaidą");
    }
}