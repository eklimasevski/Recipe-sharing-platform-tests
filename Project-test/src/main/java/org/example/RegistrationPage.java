package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Random;

public class RegistrationPage extends BasePage {
    @FindBy(linkText = "Register")
    WebElement RegisterButtonInNav;

    @FindBy(css = "input#email")
    WebElement emailInput;

    @FindBy(css = "input#password")
    WebElement passwordInput;

    @FindBy(css = "input#confirmPassword")
    WebElement confrimPasswordInput;

    @FindBy(css = "input#displayName")
    WebElement displayNameInput;

    @FindBy(css = "input#firstName")
    WebElement firstNameInput;

    @FindBy(css = "input#lastName")
    WebElement lastNameInput;

    @FindBy(css = "select[name='gender']")
    WebElement genderSelector;

    @FindBy(css = ".btn.btn-primary")
    WebElement submitButton;

    @FindBy(css = "div:nth-of-type(1) > .invalid-feedback")
    WebElement emailInputErrorMessage;

    @FindBy(css = "div:nth-of-type(2) > .invalid-feedback")
    WebElement passwordInputErrorMessage;

    @FindBy(css = "div:nth-of-type(4) > .invalid-feedback")
    WebElement displayNameErrorMessage;

    @FindBy(css = "div:nth-of-type(5) > .invalid-feedback")
    WebElement firstNameErrorMessage;

    @FindBy(css = "div:nth-of-type(6) > .invalid-feedback")
    WebElement lastNameErrorMessage;

    @FindBy(css = "div:nth-of-type(7) > .invalid-feedback")
    WebElement genderErrorMessage;

    @FindBy(className = "navbar-toggler")
    WebElement hamburgerButton;

    public RegistrationPage(WebDriver driver) {
        super(driver);
    }

    public void getGenderSelector(String gender) {
        Select select = new Select(genderSelector);
        select.selectByVisibleText(gender);
    }

    public void clickRegistrationButtonInNav() {
        RegisterButtonInNav.click();
    }

    public void enterEmail(String email) {
        emailInput.sendKeys(email);
    }

    public static String generateEmail() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        String chars = "abcdefghijklmnopqrstuvwxyz";

        for (int i = 0; i < 5; i++) {
            char c = chars.charAt(random.nextInt(chars.length()));
            sb.append(c);
        }

        String email = sb + "@gmail.com";
        return email;
    }

    public static String generateDisplayName() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        String chars = "abcdefghijklmnopqrstuvwxyz";

        for (int i = 0; i < 5; i++) {
            char c = chars.charAt(random.nextInt(chars.length()));
            sb.append(c);
        }
        String displayName = sb.toString();
        return displayName;
    }

    public void enterPassword(String password) {
        passwordInput.sendKeys(password);
    }

    public void enterConfirmPassword(String password) {
        confrimPasswordInput.sendKeys(password);
    }

    public void enterDisplayName(String nickName) {
        displayNameInput.sendKeys(nickName);
    }

    public void enterFirstName(String firstName) {
        firstNameInput.sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        lastNameInput.sendKeys(lastName);
    }

    public void clickOnSubmitButton() {
        submitButton.click();
    }

    public WebElement emailInputErrorMessage() {
        return emailInputErrorMessage;
    }

    public String emailInputErrorMessageText() {
        return emailInputErrorMessage.getText();
    }

    public String passswordInputErrorMessageText() {
        return passwordInputErrorMessage.getText();
    }

    public WebElement passwordInputErrorMessage() {
        return passwordInputErrorMessage;
    }

    public String displayNameInputErrorMessageText() {
        return displayNameErrorMessage.getText();
    }

    public WebElement displayNameInputErrorMessage() {
        return displayNameErrorMessage;
    }

    public String firstNameInputErrorMessagetext() {
        return firstNameErrorMessage.getText();
    }

    public WebElement firstnameInpurErrorMessage() {
        return firstNameErrorMessage;
    }

    public String lastNameInputErrorMessageText() {
        return lastNameErrorMessage.getText();
    }

    public WebElement lastNameInputErrorMessage() {
        return lastNameErrorMessage;
    }

    public String genderErrorMessageText() {
        return genderErrorMessage.getText();
    }

    public WebElement genderErrorMessage() {
        return genderErrorMessage;
    }

    public void clickOnHamburgerButton() {
        hamburgerButton.click();
    }

    public void submitButtonClick() {
        Actions actions = new Actions(driver);
        actions.moveToElement(submitButton).click().perform();
    }

    public WebElement getSuccessfulyRegistrationMessage() {
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(
                ".container.mb-4.mt-4 > div[role='dialog'] > div[role='document'] h2#exampleModalCenterTitle")));
    }

    public void waitUntilBodyLoad() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));
    }
}
