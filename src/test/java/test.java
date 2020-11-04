import java.io.File;
import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class test {
      WebDriver driver;

//    ChromeDriverService service = new ChromeDriverService.Builder().usingPort(8082).
//            usingDriverExecutable(new File("/usr/bin/chromedriver"))
//            //.withWhitelistedIps("")
//            .withVerbose(true)
//            .build();

  @BeforeClass
    public void Init() throws IOException {
        String chromeDriverPath = "/usr/bin/chromedriver";
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        driver = new ChromeDriver();
        //service.start();
    }

    @Test
    public void simpleTest() {
        System.out.println("TESTING");
        driver.get("https://www.google.com/");
        String text = driver.findElement(By.name("btnK")).getText();
        System.out.println(text);
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

//    @AfterClass
//    public static void createAndStopService() {
//        service.stop();
//    }
}