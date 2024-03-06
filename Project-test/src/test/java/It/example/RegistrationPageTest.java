package It.example;

import org.example.RegistrationPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.support.ui.Select;

public class RegistrationPageTest extends BasePageTest {
    RegistrationPage registrationPage;
    String email = "Testukas123@gmail.com";
    String password = "Testukas123!";
    String displayName = "Test";
    String name = "Testas";
    String lastName = "Testukas";
    String gender = "Male";

    public void registrationSteps(
            String email, String password, String displayName, String name, String lastName, String gender) {
        registrationPage = new RegistrationPage(driver);
        registrationPage.clickRegistrationButtonInNav();
        registrationPage.enterEmail(email);
        registrationPage.enterPassword(password);
        registrationPage.enterConfirmPassword(password);
        registrationPage.enterDisplayName(displayName);
        registrationPage.enterFirstName(name);
        registrationPage.enterLastName(lastName);
        registrationPage.clickOnGenderSelector();
        Select select = new Select(registrationPage.getGenderSelector());
        select.selectByVisibleText(gender);
        registrationPage.clickOnSubmitButton();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/WrongEmails")
    void emailInputstest(String input) {

        registrationSteps(input, password, displayName, name, lastName, gender);

        String errorMessage = registrationPage.emailInputErrorMessageText();
        Assertions.assertNotNull(errorMessage, "Turėjo parodyti klaidą");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/WrongPasswords")
    void passwordInputsTest(String input) {

        registrationSteps(email, input, displayName, name, lastName, gender);

        String errorMessage = registrationPage.passswordInputErrorMessageText();
        Assertions.assertNotNull(errorMessage, "Turėjo parodyti klaidą");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/WrongDisplayNames")
    void displayNameInputsTest(String input) {

        registrationSteps(email, password, input, name, lastName, gender);

        String errorMessage = registrationPage.displayNameInputErrorMessageText();
        Assertions.assertNotNull(errorMessage, "Turėjo parodyti klaidą");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/WrongFirstNames")
    void firstNameInputsTest(String input) {

        registrationSteps(email, password, displayName, input, lastName, gender);

        String errorMessage = registrationPage.firstnameInpurerrorMessagetext();
        Assertions.assertNotNull(errorMessage, "Turėjo parodyti klaidą");
    }

    @Test
    public void emailErrorMessageThenFieldEmptyTest() {
        String expectedError = "Email is required!";

        registrationSteps("", password, displayName, name, lastName, gender);

        String actualError = registrationPage.emailInputErrorMessageText();
        Assertions.assertEquals(expectedError, actualError);
    }

    @Test
    public void emailErrorMessageWithDoubleAt() {
        String expectedError = "Invalid email address!";

        registrationSteps("Test@@gmail.com", password, displayName, name, lastName, gender);

        String actualError = registrationPage.emailInputErrorMessageText();
        Assertions.assertEquals(expectedError, actualError);
    }
}