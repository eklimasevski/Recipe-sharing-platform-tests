package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    @FindBy(xpath = "//a[normalize-space()='Login']")
    WebElement loginButtonInNav;
    @FindBy(css = "div:nth-of-type(2) > .invalid-feedback")
    WebElement passwordInputErrorMessage;

    @FindBy(css = "div:nth-of-type(1) > .invalid-feedback")
    WebElement emailInputErrorMessage;

    @FindBy(css = "form > .btn.btn-primary")
    WebElement loginButton;

    @FindBy(id = "email")
    WebElement emailInput;

    @FindBy(id = "password")
    WebElement passwordInput;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void clickOnLoginButtonInNav() {
        loginButtonInNav.click();
    }

    public void enterEmail(String email) {
        emailInput.sendKeys(email);
    }

    public void enterPassword(String password) {
        passwordInput.sendKeys(password);
    }

    public void clickButtonToLogin() {
        loginButton.click();
    }

    public String emailInputErrorMessageText() {
        return emailInputErrorMessage.getText();
    }

    public String passwordInputErrorMessageText() {
        return passwordInputErrorMessage.getText();
    }
}
