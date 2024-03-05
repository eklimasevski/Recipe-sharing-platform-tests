package It.example;

import org.example.RegistrationPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class RegistrationPageTest extends BasePageTest {

    RegistrationPage registrationPage;

    @ParameterizedTest
    @CsvFileSource(resources = "/WrongEmailInputs.csv")
    void wrongEmailInputsTest(String input) {

        String password = "Testukas123!";
        String displayName = "Test";
        String name = "Testas";
        String lastName = "Testukas";

        registrationPage = new RegistrationPage(driver);

        registrationPage.clickRegistrationButtonInNav();
        registrationPage.enterEmail(input);
        registrationPage.enterPassword(password);
        registrationPage.enterConfirmPassword(password);
        registrationPage.enterDisplayName(displayName);
        registrationPage.enterFirstName(name);
        registrationPage.enterLastName(lastName);
        registrationPage.clickOnGenderSelector();
        registrationPage.clickOnSubmitButton();

        String emailErrorMessage = registrationPage.emailInputErrorMessageText();

        Assertions.assertNotNull(emailErrorMessage, "Turėjo parodyti klaidą");
    }
}
