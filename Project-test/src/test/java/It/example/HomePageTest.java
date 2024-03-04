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
        String expected = "http://localhost:5173/#";

        // Mygtuku tekstas.
        String homeButtonText = "Home";
        String registerButtonText = "Register";
        String loginButtonText = "Login";

        // Logotipo "alt" tekstas.
        String logoAlt = "Logo with words: Recipe Sharing Platform stacked on each other with pot with steam on top. ";

        homePage = new HomePage(driver);

        // Tikrina ar atsiranda nav mygtukai po "hamburger" paspaudimo.
        homePage.clickOnNavButton();
        Assertions.assertTrue(homePage.getHomeButton().isDisplayed());
        Assertions.assertTrue(homePage.getRegisterButton().isDisplayed());
        Assertions.assertTrue(homePage.getLoginButton().isDisplayed());

        // Tikrina, ar mygtukas mus nuvedė į kitą puslapį.
        homePage.clickOnHome();
        String actualHomeUrl = driver.getCurrentUrl();
        Assertions.assertEquals(expected, actualHomeUrl);

        // Tikrina, ar mygtukas mus nuvedė į kitą puslapį.
        homePage.clickOnRegister();
        String actualRegisterUrl = driver.getCurrentUrl();
        Assertions.assertEquals(expected, actualRegisterUrl);

        // Tikrina, ar mygtukas mus nuvedė į kitą puslapį.
        homePage.clickOnLogin();
        String actualLoginUrl = driver.getCurrentUrl();
        Assertions.assertEquals(expected, actualLoginUrl);

        // Tikrina ar mygtuku tekstas yra toks koks turi būti
        Assertions.assertEquals(homeButtonText, homePage.getHomeButtonText());
        Assertions.assertEquals(registerButtonText, homePage.getRegisterButtonText());
        Assertions.assertEquals(loginButtonText, homePage.getLoginButtonText());

        // Tikrina, ar po "hamburger" meniu uždarymo mygtukai dingo.
        homePage.clickOnNavButton();
        Assertions.assertFalse(homePage.getHomeButton().isDisplayed());
        Assertions.assertFalse(homePage.getRegisterButton().isDisplayed());
        Assertions.assertFalse(homePage.getLoginButton().isDisplayed());

        // Tikrina ar yra logotipas.
        Assertions.assertTrue(homePage.getLogo().isDisplayed());
        // Tikrina, ar logotipo "alt" atributas yra tinkamas.
        Assertions.assertEquals(homePage.getLogoAlt(), logoAlt);
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


    @Test
    public void footerTest() {
        homePage = new HomePage(driver);

        String expected = "http://localhost:5173/";


// kai iesko webelemento sita eilute turetu buti HomePage.java, kad atitiktu POM
        // tada kvieciam per homePage.(pavadinimas kaip pvz cia contacatEmailElement)

//            WebElement contactEmailElement = driver.findElement(By.cssSelector(".contact-email"));

        homePage.getContactEmailElement().click();

        //Pagal mane po paspaudimo ant email url nesikecia, todel testas realiai nieko netikrina
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