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
        String chromeDriverPath = "/home/www-root/chromedriver";
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        driver = new ChromeDriver();
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