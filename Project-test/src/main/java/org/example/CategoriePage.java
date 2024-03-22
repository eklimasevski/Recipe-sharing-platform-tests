package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class CategoriePage extends BasePage {

    @FindBy(linkText = "Categories")
    WebElement categoriesButtonInNav;

    @FindBy(linkText = "Login")
    WebElement loginButtonInNav;

    @FindBy(css = "form > .btn.btn-primary")
    WebElement buttonToLogin;

    @FindBy(css = "tr > th")
    List<WebElement> allList;

    @FindBy(css = "input#email")
    WebElement emailInput;

    @FindBy(css = "input#password")
    WebElement passwordInput;

    @FindBy(css = "th > .btn.button-add-category")
    WebElement addCategoryButton;

    @FindBy(css = "tr:nth-of-type(2)")
    WebElement secondCategoryInList;

    @FindBy(css = "input#disabledTextInput")
    WebElement addCategoryInput;

    @FindBy(css = ".modal-dialog.modal-dialog-centered .btn.button-add-category")
    WebElement addButton;

    @FindBy(css = "div[role='alert']")
    WebElement addedCategoryAlert;

    @FindBy(css = ".btn.button-close")
    WebElement closeButton;

    @FindBy(css = ".invalid-feedback")
    WebElement errorMessage;

    @FindBy(css = ".category-name-exists.text-danger")
    WebElement existNameErrorMessage;

    public CategoriePage(WebDriver driver) {
        super(driver);
    }

    public void clickCategoriesInNav() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(categoriesButtonInNav));
        categoriesButtonInNav.click();
    }

    public boolean searchingText(String searchingText) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(secondCategoryInList));

        for (WebElement element : allList) {
            if (element.getText().equals(searchingText)) {
                return true;
            }
        }
        return false;
    }

    public void waitForGetUrl(String url) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains(url));
    }

    public void clickLoginButtonInNav() {
        loginButtonInNav.click();
    }

    public void clickButtonToLogin() {
        buttonToLogin.click();
    }

    public void enterEmail(String email) {
        emailInput.sendKeys(email);
    }

    public void enterPassword(String password) {
        passwordInput.sendKeys(password);
    }

    public void clickAddCategoriesButton() {
        addCategoryButton.click();
    }

    public void enterCategory(String name) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(addCategoryInput));

        addCategoryInput.sendKeys(name);
    }

    public void clickAddButton() {
        addButton.click();
    }

    public WebElement checkAlert() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(addedCategoryAlert));

        return addedCategoryAlert;
    }

    public void clickClosebutton() {
        closeButton.click();
    }

    public static String generateCategory() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        String chars = "abcdefghijklmnopqrstuvwxyz";

        for (int i = 0; i < 5; i++) {
            char c = chars.charAt(random.nextInt(chars.length()));
            sb.append(c);
        }

        String category = String.valueOf(sb);
        return category.substring(0, 1).toUpperCase() + category.substring(1);
    }

    public WebElement getErrorMessage() {
        return errorMessage;
    }

    public WebElement getExistErrorMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(existNameErrorMessage));

        return existNameErrorMessage;
    }
}
