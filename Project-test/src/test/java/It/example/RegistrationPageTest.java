package It.example;

import org.example.RegistrationPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.support.ui.Select;

public class RegistrationPageTest extends BasePageTest {

    RegistrationPage registrationPage;

    @ParameterizedTest
    @CsvFileSource(resources = "/WrongEmails.csv")
    void emailInputstest(String input) {

        String password = "Testukas123!";
        String displayName = "Test";
        String name = "Testas";
        String lastName = "Testukas";
        String maleSelector = "Male";

        registrationPage = new RegistrationPage(driver);

        registrationPage.clickRegistrationButtonInNav();
        registrationPage.enterEmail(input);
        registrationPage.enterPassword(password);
        registrationPage.enterConfirmPassword(password);
        registrationPage.enterDisplayName(displayName);
        registrationPage.enterFirstName(name);
        registrationPage.enterLastName(lastName);
        registrationPage.clickOnGenderSelector();
        Select select = new Select(registrationPage.getGenderSelector());
        select.selectByVisibleText(maleSelector);
        registrationPage.clickOnSubmitButton();

        String errorMessage = registrationPage.emailInputErrorMessageText();

        Assertions.assertNotNull(errorMessage, "Turėjo parodyti klaidą");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/WrongPasswords.csv")
    void passwordInputsTest(String input) {

        String email = "Testukas123@gmail.com";
        String displayName = "Test";
        String name = "Testas";
        String lastName = "Testukas";
        String maleSelector = "Male";

        registrationPage = new RegistrationPage(driver);

        registrationPage.clickRegistrationButtonInNav();
        registrationPage.enterEmail(email);
        registrationPage.enterPassword(input);
        registrationPage.enterConfirmPassword(input);
        registrationPage.enterDisplayName(displayName);
        registrationPage.enterFirstName(name);
        registrationPage.enterLastName(lastName);
        registrationPage.clickOnGenderSelector();
        Select select = new Select(registrationPage.getGenderSelector());
        select.selectByVisibleText(maleSelector);
        registrationPage.clickOnSubmitButton();

        String errorMessage = registrationPage.passswordInputErrorMessageText();

        Assertions.assertNotNull(errorMessage, "Turėjo parodyti klaidą");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/WrongDisplayNames.csv")
    void displayNameInputsTest(String input) {

        String email = "Testukas123@gmail.com";
        String password = "Testukas123!";
        String name = "Test";
        String lastName = "Testukas";
        String maleSelector = "Male";

        registrationPage = new RegistrationPage(driver);

        registrationPage.clickRegistrationButtonInNav();
        registrationPage.enterEmail(email);
        registrationPage.enterPassword(password);
        registrationPage.enterConfirmPassword(password);
        registrationPage.enterDisplayName(input);
        registrationPage.enterFirstName(name);
        registrationPage.enterLastName(lastName);
        registrationPage.clickOnGenderSelector();
        Select select = new Select(registrationPage.getGenderSelector());
        select.selectByVisibleText(maleSelector);
        registrationPage.clickOnSubmitButton();

        String errorMessage = registrationPage.displayNameInputErrorMessageText();

        Assertions.assertNotNull(errorMessage, "Turėjo parodyti klaidą");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/WrongFirstNames.csv")
    void firstNameInputsTest(String input) {

        String email = "Testukas123@gmail.com";
        String password = "Testukas123!";
        String displayName = "Testas";
        String lastName = "Testukas";
        String maleSelector = "Male";

        registrationPage = new RegistrationPage(driver);

        registrationPage.clickRegistrationButtonInNav();
        registrationPage.enterEmail(email);
        registrationPage.enterPassword(password);
        registrationPage.enterConfirmPassword(password);
        registrationPage.enterDisplayName(displayName);
        registrationPage.enterFirstName(input);
        registrationPage.enterLastName(lastName);
        registrationPage.clickOnGenderSelector();
        Select select = new Select(registrationPage.getGenderSelector());
        select.selectByVisibleText(maleSelector);
        registrationPage.clickOnSubmitButton();

        String errorMessage = registrationPage.firstnameInpurerrorMessagetext();

        Assertions.assertNotNull(errorMessage, "Turėjo parodyti klaidą");
    }
}