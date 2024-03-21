package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CategoriePage extends BasePage {

    @FindBy(linkText = "Categories")
    WebElement categoriesButtonInNav;

    @FindBy(xpath = "//div[@class='table']/tr/th")
    List<WebElement> allList;

    public CategoriePage(WebDriver driver) {
        super(driver);
    }

    public void clickCategoriesInNav() {
        categoriesButtonInNav.click();
    }

    public boolean searchText(String searchText) {
        for (WebElement element : allList) {
            if (element.getText().equals(searchText)) {
                return true;
            }
        }
        return false;
    }
}
