package It.example;

import org.example.HomePage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Dimension;

public class HomePageTest extends BasePageTest {
    HomePage homePage;

    @Test
    public void smallNavBarTest() {
        driver.manage().window().setSize(new Dimension(990, 642));

        String expected = "http://localhost:5173/#";

        String homeButtonText = "Home";
        String registerButtonText = "Register";
        String loginButtonText = "Login";

        homePage = new HomePage(driver);

        homePage.clickOnNavButton();
        Assertions.assertTrue(homePage.getHomeButton().isDisplayed());
        Assertions.assertTrue(homePage.getRegisterButton().isDisplayed());
        Assertions.assertTrue(homePage.getLoginButton().isDisplayed());

        homePage.clickOnHome();
        String actualHomeUrl = driver.getCurrentUrl();
        Assertions.assertEquals(expected, actualHomeUrl);

        homePage.clickOnRegister();
        String actualRegisterUrl = driver.getCurrentUrl();
        Assertions.assertEquals(expected, actualRegisterUrl);

        homePage.clickOnLogin();
        String actualLoginUrl = driver.getCurrentUrl();
        Assertions.assertEquals(expected, actualLoginUrl);

        Assertions.assertEquals(homeButtonText, homePage.getHomeButtonText());
        Assertions.assertEquals(registerButtonText, homePage.getRegisterButtonText());
        Assertions.assertEquals(loginButtonText, homePage.getLoginButtonText());
    }

    @Test
    public void navBarTest() {

        homePage = new HomePage(driver);

        String expected = "http://localhost:5173/#";

        String homeButtonText = "Home";
        String registerButtonText = "Register";
        String loginButtonText = "Login";

        Assertions.assertTrue(homePage.getHomeButton().isDisplayed());
        Assertions.assertTrue(homePage.getRegisterButton().isDisplayed());
        Assertions.assertTrue(homePage.getLoginButton().isDisplayed());

        homePage.clickOnHome();
        String actualHomeUrl = driver.getCurrentUrl();
        Assertions.assertEquals(expected, actualHomeUrl);

        homePage.clickOnRegister();
        String actualRegisterUrl = driver.getCurrentUrl();
        Assertions.assertEquals(expected, actualRegisterUrl);

        homePage.clickOnLogin();
        String actualLoginUrl = driver.getCurrentUrl();
        Assertions.assertEquals(expected, actualLoginUrl);

        Assertions.assertEquals(homeButtonText, homePage.getHomeButtonText());
        Assertions.assertEquals(registerButtonText, homePage.getRegisterButtonText());
        Assertions.assertEquals(loginButtonText, homePage.getLoginButtonText());
    }
}
