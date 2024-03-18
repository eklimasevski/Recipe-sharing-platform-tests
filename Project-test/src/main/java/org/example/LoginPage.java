package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage extends BasePage {

    @FindBy(xpath = "//a[normalize-space()='Login']")
    WebElement loginButtonInNav;

    @FindBy(linkText = "Logout")
    WebElement logoutButton;

    @FindBy(css = ".input-group > .invalid-feedback")
    WebElement passwordInputErrorMessage;

    @FindBy(css = "form > div:nth-of-type(1) > .invalid-feedback")
    WebElement emailInputErrorMessage;

    @FindBy(css = "form > .btn.btn-primary")
    WebElement loginButton;

    @FindBy(id = "email")
    WebElement emailInput;

    @FindBy(id = "password")
    WebElement passwordInput;

    @FindBy(css = ".container.mt-5.mx-auto > div:nth-of-type(3)")
    WebElement emailInLogin;

    @FindBy(css = "div[role='alert']")
    WebElement getSuccessfullyAlert;

    @FindBy(className = "login")
    WebElement loginPageErrorMessage;

    @FindBy(className = "special-link")
    WebElement createAccountButton;

    @FindBy(className = "navbar-toggler")
    WebElement hamburgerButton;

    @FindBy(css = ".btn.btn-outline-secondary > svg")
    WebElement showPassword;

    @FindBy(css = ".container.mt-5.mx-auto > div:nth-of-type(3)")
    WebElement emailAfterLogout;

    @FindBy(css = ".container.mt-5.mx-auto > div:nth-of-type(4)")
    WebElement roleAfterLogout;

    @FindBy(className = "text-success")
    WebElement logoutMessage;

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

    public String getPasswordFieldValue() {
        return passwordInput.getAttribute("value");
    }

    public WebElement passwordField() {
        return passwordInput;
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

    public String getEmailInLogin() {
        return emailInLogin.getText();
    }

    public WebElement getSuccessfullyAlert() {
        return getSuccessfullyAlert;
    }

    public String incorrectPaswordOrEmailMessage() {
        return loginPageErrorMessage.getText();
    }

    public void clickToCreateAccount() {
        createAccountButton.click();
    }

    public void clickOnHamburgerButton() {
        hamburgerButton.click();
    }

    public void clickOnShowPassword() {
        showPassword.click();
    }

    public void waitForGetUrl(String url) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains(url));
    }

    public void clickOnLogoutButton() {
        logoutButton.click();
    }

    public String getEmailAfterLogout() {
        return emailAfterLogout.getText();
    }

    public String getRoleAfterLogout() {
        return roleAfterLogout.getText();
    }

    public WebElement getLogoutMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.elementToBeClickable(logoutMessage));
    }

    public String getLogoutMessageText() {
        return logoutMessage.getText();
    }
}
