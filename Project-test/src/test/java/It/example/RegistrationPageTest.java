package It.example;

import org.example.RegistrationPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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
        Actions actions = new Actions(driver);

        registrationPage.clickRegistrationButtonInNav();
        registrationPage.enterEmail(email);
        registrationPage.enterPassword(password);
        registrationPage.enterConfirmPassword(password);
        registrationPage.enterDisplayName(displayName);
        registrationPage.enterFirstName(name);
        registrationPage.enterLastName(lastName);

        Select select = new Select(registrationPage.getGenderSelector());
        select.selectByVisibleText(gender);
        actions.moveToElement(registrationPage.getSubmitButton()).click().perform();
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

        WebElement errorMessage = registrationPage.firstnameInpurErrorMessage();
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

    @Test
    public void displayNameErrorMessageThenFieldEmpty() {
        String expectedError = "Display name is required!";

        registrationSteps(email, password, "", name, lastName, gender);

        String actualError = registrationPage.displayNameInputErrorMessageText();
        Assertions.assertEquals(expectedError, actualError);
    }

    @Test
    public void displayNameErrorMessageWithOffensiveWord() {
        String expectedError = "Display name contains offensive words!";

        registrationSteps(email, password, "damn", name, lastName, gender);

        String actualError = registrationPage.displayNameInputErrorMessageText();
        Assertions.assertEquals(expectedError, actualError);
    }

    @Test
    public void firstNameErrorMessageWithSpaces() {
        String expectedError = "First name cannot have special symbols or spaces!";

        registrationSteps(email, password, displayName, " Test", lastName, gender);

        String actualError = registrationPage.firstNameInputErrorMessagetext();
        Assertions.assertEquals(expectedError, actualError);
    }

    @Test
    public void firstNameErrorMessageThenFirtLetterUppercase() {
        String expectedError = "First name must start with an uppercase letter!";

        registrationSteps(email, password, displayName, "test", lastName, gender);

        String actualError = registrationPage.firstNameInputErrorMessagetext();
        Assertions.assertEquals(expectedError, actualError);
    }

    @Test
    public void mobileVersionRegistrationTest() {
        driver.manage().window().setSize(new Dimension(375, 667));
        registrationPage = new RegistrationPage(driver);

        String expected = "Registration Successful!";

        registrationPage.clickOnHamburgerButton();
        registrationSteps(email, password, displayName, name, lastName, gender);

        WebElement getElement = registrationPage.getSuccessfulyRegistrationMessage();
        String actual = getElement.getText();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void correctRegistrationTest() {
        String expected = "Registration Successful!";

        registrationSteps(email, password, displayName, name, lastName, gender);

        WebElement getElement = registrationPage.getSuccessfulyRegistrationMessage();
        String actual = getElement.getText();

        Assertions.assertEquals(expected, actual);
    }
}