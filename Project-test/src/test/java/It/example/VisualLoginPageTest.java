package It.example;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.EyesRunner;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.TestResultsSummary;
import com.applitools.eyes.selenium.BrowserType;
import com.applitools.eyes.selenium.ClassicRunner;
import com.applitools.eyes.selenium.Configuration;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.fluent.Target;
import com.applitools.eyes.visualgrid.model.DeviceName;
import com.applitools.eyes.visualgrid.model.ScreenOrientation;
import com.applitools.eyes.visualgrid.services.RunnerOptions;
import com.applitools.eyes.visualgrid.services.VisualGridRunner;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.net.MalformedURLException;
import java.time.Duration;

public class VisualLoginPageTest {
    private static final boolean USE_ULTRAFAST_GRID = true;
    private static final boolean USE_EXECUTION_CLOUD = false;

    private static String applitoolsApiKey;
    private static boolean headless;

    private static BatchInfo batch;
    private static Configuration config;
    private static EyesRunner runner;

    private WebDriver driver;
    private Eyes eyes;

    @BeforeAll
    public static void setUpConfigAndRunner() {

        applitoolsApiKey = "974ixr0STssTTe9LlcEHRn103Aiiz75SNiApv3fCVUHxW8110";

        headless = Boolean.parseBoolean(System.getenv().getOrDefault("HEADLESS", "false"));

        if (USE_ULTRAFAST_GRID) {
            runner = new VisualGridRunner(new RunnerOptions().testConcurrency(5));
        } else {
            runner = new ClassicRunner();
        }
        String runnerName = (USE_ULTRAFAST_GRID) ? "Ultrafast Grid" : "Classic runner";
        batch = new BatchInfo("Login test with " + runnerName);
        config = new Configuration();
        config.setApiKey(applitoolsApiKey);

        config.setBatch(batch);

        if (USE_ULTRAFAST_GRID) {
            config.addBrowser(1920, 1080, BrowserType.CHROME);
            config.addDeviceEmulation(DeviceName.Pixel_2, ScreenOrientation.PORTRAIT);
        }
    }

    @BeforeEach
    public void openBrowserAndEyes(TestInfo testInfo) throws MalformedURLException {
        if (USE_EXECUTION_CLOUD) {
        } else {
            driver = new ChromeDriver();
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        eyes = new Eyes(runner);
        eyes.setConfiguration(config);
        eyes.open(driver, "Recipe sharing platform", testInfo.getDisplayName(), new RectangleSize(1200, 600));
    }

    @Test
    public void VisualLoginPageTest() {
        driver.get("http://localhost:5173/login");

        eyes.check(Target.window().fully().withName("Login Page"));
    }

    @AfterEach
    public void cleanUpTest() {
        eyes.closeAsync();
        driver.quit();
    }

    @AfterAll
    public static void printResults() {
        TestResultsSummary allTestResults = runner.getAllTestResults();
        System.out.println(allTestResults);
    }
}
