import java.io.File;
import java.io.IOException;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.testng.annotations.*;

public class ChromeDriverTest {
    private static ChromeDriverService service;
    public static WebDriver driver;

    @BeforeClass
    public static void createAndStartService() throws IOException {
        service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File("usr/local/bin/chromedriver"))
                .usingAnyFreePort()
                .build();
        service.start();
    }

    @BeforeTest
    public void setUp(){
        driver = new ChromeDriver(service);
    }

    @Test
    public void simpleTest() {
        driver.get("https://www.google.com/");
        String text = driver.findElement(By.name("btnK")).getText();
        System.out.println(text);
    }

    @AfterTest
    public void tearDown(){
        driver.quit();
    }

    @AfterClass
    public static void createAndStopService() {
        service.stop();
    }