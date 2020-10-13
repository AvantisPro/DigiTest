import java.io.File;
import java.io.IOException;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class test {
//    private static ChromeDriverService service;
      WebDriver driver;


    @BeforeClass
//    public static void createAndStartService() throws IOException {
//        service = new ChromeDriverService.Builder()
//                .usingDriverExecutable(new File("home/www-root/chromedriver"))
//                .usingAnyFreePort()
//                .build();
//        service.start();
//    }
    public void Init() {
        String chromeDriverPath = "/usr/local/bin/chromedriver";
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        driver = new ChromeDriver();
    }

//    @BeforeTest
//    public void setUp() {
//        driver = new ChromeDriver(service);
//    }

    @Test
    public void simpleTest() {
        driver.get("https://www.google.com/");
        String text = driver.findElement(By.name("btnK")).getText();
        System.out.println(text);
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }

//    @AfterClass
//    public static void createAndStopService() {
//        service.stop();
//    }
}