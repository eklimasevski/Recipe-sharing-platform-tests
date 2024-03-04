package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    @FindBy(css = "button[type='button']")
    WebElement navButton;

    @FindBy(xpath = "//a[normalize-space()='Home']")
    WebElement homeButton;

    @FindBy(xpath = "//a[normalize-space()='Register']")
    WebElement registerButton;

    @FindBy(xpath = "//a[normalize-space()='Login']")
    WebElement loginButton;

    @FindBy(css = "p[class='logo my-auto navbar-brand']")
    WebElement logo;

    @FindBy(css = "p.logo.my-auto.navbar-brand img[alt]")
    WebElement logoAlt;

    @FindBy(css = ".contact-email")
    WebElement contactEmailElement;

    public WebElement getContactEmailElement() {
        return contactEmailElement;
    }

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void clickOnNavButton() {
        navButton.click();
    }

    public String getHomeButtonText() {
        return homeButton.getText();
    }

    public String getRegisterButtonText() {
        return registerButton.getText();
    }

    public String getLoginButtonText() {
        return loginButton.getText();
    }

    public WebElement getHomeButton() {
        return homeButton;
    }

    public WebElement getRegisterButton() {
        return registerButton;
    }

    public WebElement getLoginButton() {
        return loginButton;
    }

    public void clickOnHome() {
        homeButton.click();
    }

    public void clickOnRegister() {
        registerButton.click();
    }

    public void clickOnLogin() {
        loginButton.click();
    }

    public WebElement getLogo() {
        return logo;
    }

    public String getLogoAlt() {
        return logoAlt.getAttribute("alt");
    }
}
