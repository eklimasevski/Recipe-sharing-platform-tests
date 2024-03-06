package It.example;

import org.example.RegistrationPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.WebElement;
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
    void emailInputTest(String input) {

        registrationSteps(input, password, displayName, name, lastName, gender);

        WebElement errorMessage = registrationPage.emailInputErrorMessage();
        Assertions.assertTrue(errorMessage.isDisplayed(), "Turėjo parodyti klaidą");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/WrongPasswords")
    void passwordInputTest(String input) {

        registrationSteps(email, input, displayName, name, lastName, gender);

        WebElement errorMessage = registrationPage.passwordInputErrorMessage();
        Assertions.assertTrue(errorMessage.isDisplayed(), "Turėjo parodyti klaidą");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/WrongDisplayNames")
    void displayNameInputTest(String input) {

        registrationSteps(email, password, input, name, lastName, gender);

        WebElement errorMessage = registrationPage.displayNameInputErrorMessage();
        Assertions.assertTrue(errorMessage.isDisplayed(), "Turėjo parodyti klaidą");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/WrongFirstNames")
    void firstNameInputTest(String input) {

        registrationSteps(email, password, displayName, input, lastName, gender);

        WebElement errorMessage = registrationPage.firstnameInpurerrorMessage();
        Assertions.assertTrue(errorMessage.isDisplayed(), "Turėjo parodyti klaidą");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/WrongLastNames")
    void lastNameInputTest(String input) {
        registrationSteps(email, password, displayName, name, input, gender);

        WebElement errorMessage = registrationPage.lastNameInputErrorMessage();
        Assertions.assertTrue(errorMessage.isDisplayed(), "Turėjo parodyti klaidą");
    }

    @Test
    public void genderSelectorTest() {
        String expectedErrorMesage = "Gender field is required!";
        registrationSteps(email, password, displayName, name, lastName, "Select...");

        WebElement errorMessage = registrationPage.genderErrorMessage();
        String actualErrorMessageText = registrationPage.genderErrorMessageText();
        Assertions.assertTrue(errorMessage.isDisplayed());
        Assertions.assertEquals(expectedErrorMesage, actualErrorMessageText);
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

    @Test
    public void passwordErrorMessageWithNumberOnly() {
        String expectedError = "Password must contain at least one uppercase letter!";

        registrationSteps(email, "123123", displayName, name, lastName, gender);

        String actualError = registrationPage.passswordInputErrorMessageText();
        Assertions.assertEquals(expectedError, actualError);
    }

    @Test
    public void passwordErrorMessageWithoutNumbers() {
        String expectedError = "Password must contain at least one number!";

        registrationSteps(email, "Testas", displayName, name, lastName, gender);

        String actualError = registrationPage.passswordInputErrorMessageText();
        Assertions.assertEquals(expectedError, actualError);
    }
}