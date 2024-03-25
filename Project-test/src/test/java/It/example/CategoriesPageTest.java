package It.example;

import org.example.CategoriePage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.Dimension;

public class CategoriesPageTest extends BasePageTest {

    CategoriePage categoriePage;

    String email = "Testukas1@gmail.com";
    String password = "Testukas123!";
    String category = CategoriePage.generateCategory();

    public void loginSteps(String email, String password) {
        categoriePage = new CategoriePage(driver);
        categoriePage.clickLoginButtonInNav();
        categoriePage.enterEmail(email);
        categoriePage.enterPassword(password);
        categoriePage.clickButtonToLogin();
        categoriePage.clickCategoriesInNav();
    }

    public void addCategorySteps(String category) {
        categoriePage = new CategoriePage(driver);
        categoriePage.clickLoginButtonInNav();
        categoriePage.enterEmail(email);
        categoriePage.enterPassword(password);
        categoriePage.clickButtonToLogin();
        categoriePage.clickCategoriesInNav();
        categoriePage.clickAddCategoriesButton();
        categoriePage.enterCategory(category);
        categoriePage.clickAddButton();
    }

    public void loginStepsForMobile() {
        driver.manage().window().setSize(new Dimension(375, 667));

        categoriePage = new CategoriePage(driver);
        categoriePage.clickOnHamburgerButton();
        loginSteps(email, password);
    }

    public void addCategoryStepsForMobile(String category) {
        driver.manage().window().setSize(new Dimension(375, 667));

        categoriePage = new CategoriePage(driver);
        categoriePage.clickOnHamburgerButton();
        addCategorySteps(category);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/Categories")
    public void checkCategoriesTest(String input) {

        loginSteps(email, password);

        boolean isTextFound = categoriePage.searchingText(input);
        Assertions.assertTrue(isTextFound);
    }

    @Test
    public void addCategoryTest() {

        addCategorySteps(category);

        Assertions.assertTrue(categoriePage.checkAlert().isDisplayed());

        categoriePage.clickClosebutton();
        boolean isTextFound = categoriePage.searchingText(category);
        Assertions.assertTrue(isTextFound);
    }

    @Test
    public void validationTestWhenTextLessThan4Symbols() {
        String expected = "Category names must be at least 4 characters long";

        addCategorySteps("sad");

        String actual = categoriePage.getErrorMessage().getText();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void validationTestWhenInputEmpty() {
        String expected = "Please enter category name";

        addCategorySteps("");

        String actual = categoriePage.getErrorMessage().getText();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void validationTestWhenFirstLetterLowerCase() {
        String expected =
                "Category name must start from an uppercase letter and can contain only letters and single whitespaces";

        addCategorySteps("test");

        String actual = categoriePage.getErrorMessage().getText();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void validationTestWhenTextWithSpaces() {
        String expected = "Category Te already exists. Please choose another name";

        addCategorySteps("Te   ");

        String actual = categoriePage.getExistErrorMessage().getText();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void validationTestWhenSymbolsMoreThat20() {
        String expected = "Category names must not be longer than 20 characters";

        addCategorySteps("TestTestTestTestTests");

        String actual = categoriePage.getErrorMessage().getText();
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/Categories")
    public void checkCategoriesOnMobileTest(String input) {

        loginStepsForMobile();

        boolean isTextFound = categoriePage.searchingText(input);
        Assertions.assertTrue(isTextFound);
    }

    @Test
    public void validationTestWhenTextLessThan4SymbolsOnMobile() {
        String expected = "Category names must be at least 4 characters long";

        addCategoryStepsForMobile("sad");

        String actual = categoriePage.getErrorMessage().getText();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void validationTestWhenInputEmptyOnMobile() {
        String expected = "Please enter category name";

        addCategoryStepsForMobile("");

        String actual = categoriePage.getErrorMessage().getText();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void validationTestWhenFirstLetterLowerCaseOnMobile() {
        String expected =
                "Category name must start from an uppercase letter and can contain only letters and single whitespaces";
        addCategoryStepsForMobile("test");

        String actual = categoriePage.getErrorMessage().getText();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void validationTestWhenTextWithSpacesOnMobile() {
        String expected = "Category Te already exists. Please choose another name";
        addCategoryStepsForMobile("Te   ");

        String actual = categoriePage.getExistErrorMessage().getText();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void validationTestWhenSymbolsMoreThat20onMobile() {
        String expected = "Category names must not be longer than 20 characters";
        addCategoryStepsForMobile("TestTestTestTestTests");

        String actual = categoriePage.getErrorMessage().getText();
        Assertions.assertEquals(expected, actual);
    }
}
