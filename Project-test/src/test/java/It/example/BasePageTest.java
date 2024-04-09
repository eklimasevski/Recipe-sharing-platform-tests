package It.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BasePageTest {
    WebDriver driver;

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
        driver.get("http://localhost:5173/");
        driver.manage().window().setSize(new Dimension(1920, 1080));
        //        driver.manage().window().maximize();
    }

//    @AfterEach
//    void tearDown() {
//        driver.close();
//    }
}
