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
    public void validationTextLessThan4SymbolsTest() {
        String expected = "Category names must be at least 4 characters long";

        addCategory("sad");

        String actual = categoriePage.getErrorMessage().getText();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void validationTextwhenInputEmptyTest() {
        String expected = "Please enter category name";

        addCategory("");

        String actual = categoriePage.getErrorMessage().getText();
        Assertions.assertEquals(expected, actual);
    }
}
