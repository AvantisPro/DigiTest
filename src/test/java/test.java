import java.io.File;
import java.io.IOException;
import java.net.*;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class test {

    public static void main(String []args) throws MalformedURLException{
        new DesiredCapabilities();
        URL serverurl = new URL("http://104.248.254.158:8081/");
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        WebDriver driver = new RemoteWebDriver(serverurl,capabilities);
        driver.get("http://www.google.com");
        WebElement searchEdit = driver.findElement(By.name("q"));
        String text = driver.findElement(By.name("q")).getText();
        System.out.println(text);
        searchEdit.sendKeys("Selftechy on google");
        searchEdit.submit();

    }
}
