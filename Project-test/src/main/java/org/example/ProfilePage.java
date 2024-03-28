package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProfilePage extends BasePage {
    @FindBy(xpath = "//a[normalize-space()='Login']")
    WebElement loginButtonInNav;

    @FindBy(id = "email")
    WebElement emailInput;

    @FindBy(id = "password")
    WebElement passwordInput;

    @FindBy(id = "displayName")
    WebElement displayName;

    @FindBy(id = "firstName")
    WebElement firstName;

    @FindBy(id = "lastName")
    WebElement lastName;

    @FindBy(id = "confirmPassword")
    WebElement confirmPassword;

    @FindBy(css = "form > .btn.btn-primary")
    WebElement loginButton;

    @FindBy(css = "li:nth-of-type(3) > .nav-link")
    WebElement profileButton;

    @FindBy(css = "select[name='gender']")
    WebElement genderSelector;

    @FindBy(css = ".btn.btn-primary")
    WebElement updateButton;

    @FindBy(id = "picture")
    WebElement fileInput;

    public ProfilePage(WebDriver driver) {
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

    public void clickProfileButton() {
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li:nth-of-type(3) > .nav-link")));
        profileButton.click();
    }

    public void enterDisplayName(String name) {
        displayName.sendKeys(name);
    }

    public void enterLastName(String name) {
        lastName.sendKeys(name);
    }

    public void enterFirstName(String name) {
        firstName.sendKeys(name);
    }

    public void enterPasswordConfirm(String password) {
        confirmPassword.sendKeys(password);
    }

    public void getGenderSelector(String gender) {
        Select select = new Select(genderSelector);
        select.selectByVisibleText(gender);
    }

    public void clickUpdateButton() {
        updateButton.click();
    }

    public void upldoadPhoto(String path) {
        String filePath = System.getProperty("user.dir") + path;
        fileInput.sendKeys(filePath);
    }

    public WebElement getSuccessfulyRegistrationMessage() {
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(
                ".container.mb-4.mt-4 > div[role='dialog'] > div[role='document'] h2#exampleModalCenterTitle")));
    }
}
