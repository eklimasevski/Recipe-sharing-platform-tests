package It.example;

import org.example.HomePage;
import org.junit.jupiter.api.Test;

public class LoginPageTest extends BasePageTest{

    HomePage homePage;

    @Test
    public void emailInputTest(){
        homePage = new HomePage(driver);


    }
}
