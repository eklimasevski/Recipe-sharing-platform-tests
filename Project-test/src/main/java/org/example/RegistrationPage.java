package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RegistrationPage extends BasePage{
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
    public RegistrationPage(WebDriver driver) {
        super(driver);
    }
    public void clickRegistrationButtonInNav(){
        RegisterButtonInNav.click();
    }
    public void enterEmail(String email){
        emailInput.sendKeys(email);
    }
    public void enterPassword(String password){
        passwordInput.sendKeys(password);
    }
    public void enterConfirmPassword(String password){
        confrimPasswordInput.sendKeys(password);
    }
    public void enterDisplayName(String nickName){
        displayNameInput.sendKeys(nickName);
    }
    public void enterFirstName(String firstName){
        firstNameInput.sendKeys(firstName);
    }
    public void enterLastName(String lastName){
        lastNameInput.sendKeys(lastName);
    }
    public void clickOnGenderSelector(){
        genderSelector.click();
    }
    public void clickOnSubmitButton(){
        submitButton.click();
    }
    public String emailInputErrorMessageText(){
        return emailInputErrorMessage.getText();
    }
}
