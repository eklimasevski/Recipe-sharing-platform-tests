package It.example.mobile;

import org.example.CategoriePage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;


public class CategoriesPageMobileTest extends BasePageMobileTest{
        CategoriePage categoriePage;
        String email = "Testukas1@gmail.com";
        String password = "Testukas123!";
        String category = CategoriePage.generateCategory();

        public void loginSteps(String email, String password) {
            categoriePage = new CategoriePage(driver);
            categoriePage.clickOnHamburgerButton();
            categoriePage.clickLoginButtonInNav();
            categoriePage.enterEmail(email);
            categoriePage.enterPassword(password);
            categoriePage.clickButtonToLogin();
            categoriePage.clickCategoriesInNav();
        }

        public void addCategorySteps(String category) {
            categoriePage = new CategoriePage(driver);
            categoriePage.clickOnHamburgerButton();
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
            String expected = "Category name must be at least 4 characters long";

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
            String expected = "Name must start with an uppercase letter and contain only letters and spaces";

            addCategorySteps("Te   ");

            String actual = categoriePage.getErrorMessage().getText();
            Assertions.assertEquals(expected, actual);
        }

        @Test
        public void validationTestWhenSymbolsMoreThat20() {
            String expected = "Category name must not be longer than 20 characters";

            addCategorySteps("TestTestTestTestTests");

            String actual = categoriePage.getErrorMessage().getText();
            Assertions.assertEquals(expected, actual);
        }
        @ParameterizedTest
        @CsvFileSource(resources = "/CategoriesOffensiveWords")
        public void offensiveWordsTest(String input) {
            String expected = "Display name contains offensive words!";

            addCategorySteps(input);

            String actual = categoriePage.getErrorMessage().getText();
            Assertions.assertEquals(expected,actual);
        }
    }
