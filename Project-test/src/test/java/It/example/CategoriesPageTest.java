package It.example;

import org.example.CategoriePage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class CategoriesPageTest extends BasePageTest {

    CategoriePage categoriePage;

    String email = "Testukas1@gmail.com";
    String password = "Testukas123!";

    String category = CategoriePage.generateCategory();

    public void login(String email, String password) {
        categoriePage = new CategoriePage(driver);
        categoriePage.clickLoginButtonInNav();
        categoriePage.enterEmail(email);
        categoriePage.enterPassword(password);
        categoriePage.clickButtonToLogin();
        categoriePage.clickCategoriesInNav();
    }

    public void addCategory(String category) {
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

    @ParameterizedTest
    @CsvFileSource(resources = "/Categories")
    public void checkCategoriesTest(String input) {

        login(email, password);

        boolean isTextFound = categoriePage.searchingText(input);
        Assertions.assertTrue(isTextFound);
    }

    @Test
    public void addCategoryTest() {

        addCategory(category);

        Assertions.assertTrue(categoriePage.checkAlert().isDisplayed());

        categoriePage.clickClosebutton();
        boolean isTextFound = categoriePage.searchingText(category);
        Assertions.assertTrue(isTextFound);
    }

    @Test
    public void validationTestWhenTextLessThan4Symbols() {
        String expected = "Category names must be at least 4 characters long";

        addCategory("sad");

        String actual = categoriePage.getErrorMessage().getText();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void validationTestWhenInputEmpty() {
        String expected = "Please enter category name";

        addCategory("");

        String actual = categoriePage.getErrorMessage().getText();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void validationTestWhenFirstLetterLowerCase() {
        String expected =
                "Category name must start from an uppercase letter and can contain only letters and single whitespaces";

        addCategory("test");

        String actual = categoriePage.getErrorMessage().getText();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void validationTestWhenTextWithSpaces() {
        String expected = "Category Te already exists. Please choose another name";

        addCategory("Te   ");

        String actual = categoriePage.getExistErrorMessage().getText();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void validationTestWhenSymbolsMoreThat20() {
        String expected = "Category names must not be longer than 20 characters";

        addCategory("TestTestTestTestTests");

        String actual = categoriePage.getErrorMessage().getText();
        Assertions.assertEquals(expected, actual);
    }
}
