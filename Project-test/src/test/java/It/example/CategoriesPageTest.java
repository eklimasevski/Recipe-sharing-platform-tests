package It.example;

import org.example.CategoriePage;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class CategoriesPageTest extends BasePageTest {

    CategoriePage categoriePage;

    @ParameterizedTest
    @CsvFileSource(resources = "/Categories")
    public void addCategorieTest(String input) {
        categoriePage = new CategoriePage(driver);

        categoriePage.clickCategoriesInNav();
        categoriePage.searchText(input);
    }
}
