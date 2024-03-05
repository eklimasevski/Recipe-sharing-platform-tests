package It.example;

import org.example.HomePage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;


public class HomePageTest extends BasePageTest {
    HomePage homePage;


    @Test
    public void smallNavBarTest() {
        driver.manage().window().setSize(new Dimension(990, 642));

        // Linkas, į kuri nukreipiama po mygtuko paspaudimo.
        String expectedLoginUrl = "http://localhost:5173/login";
        String expectedRegisterUrl = "http://localhost:5173/register";
        String expectedHomeUrl = "http://localhost:5173/";

        // Mygtuku tekstas.
        String homeButtonText = "Home";
        String registerButtonText = "Register";
        String loginButtonText = "Login";

        // Logotipo "alt" tekstas.
        String logoAlt = "Logo with words: Recipe Sharing Platform stacked on each other with pot with steam on top. ";

        homePage = new HomePage(driver);

        // Tikrina ar atsiranda nav mygtukai po "hamburger" paspaudimo.
        homePage.clickOnNavButton();
        Assertions.assertTrue(homePage.getHomeButtonInNav().isDisplayed());
        Assertions.assertTrue(homePage.getRegisterButtonInNav().isDisplayed());
        Assertions.assertTrue(homePage.getLoginButtonInNav().isDisplayed());

        // Tikrina, ar mygtukas mus nuvedė į kitą puslapį.
        homePage.clickOnHome();
        String actualUrl = driver.getCurrentUrl();
        Assertions.assertEquals(expectedHomeUrl, actualUrl);

        // Tikrina, ar mygtukas mus nuvedė į kitą puslapį.
        homePage.clickOnRegister();
        String actualRegisterUrl = driver.getCurrentUrl();
        Assertions.assertEquals(expectedRegisterUrl, actualRegisterUrl);

        // Tikrina, ar mygtukas mus nuvedė į kitą puslapį.
        homePage.clickOnLogin();
        String actualLoginUrl = driver.getCurrentUrl();
        Assertions.assertEquals(expectedLoginUrl, actualLoginUrl);

        // Tikrina ar mygtuku tekstas yra toks koks turi būti
        Assertions.assertEquals(homeButtonText, homePage.getHomeButtonText());
        Assertions.assertEquals(registerButtonText, homePage.getRegisterButtonText());
        Assertions.assertEquals(loginButtonText, homePage.getLoginButtonText());

        // Tikrina, ar po "hamburger" meniu uždarymo mygtukai dingo.
        homePage.clickOnNavButton();
        Assertions.assertFalse(homePage.getHomeButtonInNav().isDisplayed());
        Assertions.assertFalse(homePage.getRegisterButtonInNav().isDisplayed());
        Assertions.assertFalse(homePage.getLoginButtonInNav().isDisplayed());

        // Tikrina ar yra logotipas.
        Assertions.assertTrue(homePage.getLogo().isDisplayed());
        // Tikrina, ar logotipo "alt" atributas yra tinkamas.
        Assertions.assertEquals(homePage.getLogoAlt(), logoAlt);
    }

    @Test
    public void navBarTest() {

        homePage = new HomePage(driver);

        String expectedLoginUrl = "http://localhost:5173/login";
        String expectedRegisterUrl = "http://localhost:5173/register";
        String expectedHomeUrl = "http://localhost:5173/";

        String homeButtonText = "Home";
        String registerButtonText = "Register";
        String loginButtonText = "Login";

        Assertions.assertTrue(homePage.getHomeButtonInNav().isDisplayed());
        Assertions.assertTrue(homePage.getRegisterButtonInNav().isDisplayed());
        Assertions.assertTrue(homePage.getLoginButtonInNav().isDisplayed());

        homePage.clickOnHome();
        String actualHomeUrl = driver.getCurrentUrl();
        Assertions.assertEquals(expectedHomeUrl, actualHomeUrl);

        homePage.clickOnRegister();
        String actualRegisterUrl = driver.getCurrentUrl();
        Assertions.assertEquals(expectedRegisterUrl, actualRegisterUrl);

        homePage.clickOnLogin();
        String actualLoginUrl = driver.getCurrentUrl();
        Assertions.assertEquals(expectedLoginUrl, actualLoginUrl);

        Assertions.assertEquals(homeButtonText, homePage.getHomeButtonText());
        Assertions.assertEquals(registerButtonText, homePage.getRegisterButtonText());
        Assertions.assertEquals(loginButtonText, homePage.getLoginButtonText());
    }


    @Test
    public void footerTest() {
        homePage = new HomePage(driver);

        String expected = "http://localhost:5173/";

//      WebElement contactEmailElement = driver.findElement(By.cssSelector(".contact-email"));

        homePage.getContactEmailElement().click();

        String actualLoginUrl = driver.getCurrentUrl();
        Assertions.assertEquals(expected, actualLoginUrl);

        WebElement phoneNumberElement = driver.findElement(By.cssSelector(".col-lg-3.col-md-4.col-sm-12 > p:nth-of-type(4)"));
        WebElement addressElement = driver.findElement(By.cssSelector(".col-lg-3.col-md-4.col-sm-12 > p:nth-of-type(2)"));


        String actualPhoneNumber = phoneNumberElement.getText();
        String actualAddress = addressElement.getText();


        String expectedPhoneNumber = "+370 5 269 7455";
        String expectedAddress = "Trinapolio g. 2, Vilnius";


        Assertions.assertEquals(expectedPhoneNumber, actualPhoneNumber, "Phone numbers do not match");
        Assertions.assertEquals(expectedAddress, actualAddress, "Addresses do not match");

    }
}