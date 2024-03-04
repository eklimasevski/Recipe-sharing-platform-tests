package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    @FindBy(css = "button[type='button']")
    WebElement navButton;

    @FindBy(xpath = "//a[normalize-space()='Home']")
    WebElement homeButtonInNav;

    @FindBy(xpath = "//a[normalize-space()='Register']")
    WebElement registerButtonInNav;

    @FindBy(xpath = "//a[normalize-space()='Login']")
    WebElement loginButtonInNav;

    @FindBy(css = "p[class='logo my-auto navbar-brand']")
    WebElement logo;

    @FindBy(css = "p.logo.my-auto.navbar-brand img[alt]")
    WebElement logoAlt;

    @FindBy(css = ".contact-email")
    WebElement contactEmailElement;

    @FindBy(id = "email")
    WebElement emailInput;

    @FindBy(id = "password")
    WebElement passwordInput;

    @FindBy(css = "form > .btn.btn-primary")
    WebElement loginButton;

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
        return homeButtonInNav.getText();
    }

    public String getRegisterButtonText() {
        return registerButtonInNav.getText();
    }

    public String getLoginButtonText() {
        return loginButtonInNav.getText();
    }

    public WebElement getHomeButtonInNav() {
        return homeButtonInNav;
    }

    public WebElement getRegisterButtonInNav() {
        return registerButtonInNav;
    }

    public WebElement getLoginButtonInNav() {
        return loginButtonInNav;
    }

    public void clickOnHome() {
        homeButtonInNav.click();
    }

    public void clickOnRegister() {
        registerButtonInNav.click();
    }

    public void clickOnLogin() {
        loginButtonInNav.click();
    }

    public WebElement getLogo() {
        return logo;
    }

    public String getLogoAlt() {
        return logoAlt.getAttribute("alt");
    }

    public void enterEmailInLogin(String email){
        emailInput.sendKeys(email);
    }
    public void enterPasswordInlogin(String password){
        passwordInput.sendKeys(password);
    }

    public void clickButtonToLogin(){
        loginButton.click();
    }
}
