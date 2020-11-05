import java.io.File;
import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class test {
      WebDriver driver = null;

    ChromeDriverService service = new ChromeDriverService.Builder().usingPort(8082).
            usingDriverExecutable(new File("/usr/bin/chromedriver"))
            //.withWhitelistedIps("")
            //.withVerbose(true)
            .build();

  @BeforeClass
    public void Init() throws IOException {
        service.start();
        driver = new RemoteWebDriver(service.getUrl(), new ChromeOptions());
        driver.get("https://dev.digisposa.com/auth/login");
    }

    @Test
    public void Test01() {
        System.out.println(" ================ TEST RUN ===============");
        String text = driver.findElement(By.className("section__title")).getText();
        Assert.assertTrue(text.toLowerCase().contains("login"));
        System.out.println(" ================ ALL WORKS FINE ===============");
    }

    @Test
    public void Test02_login(){
        System.out.println("===> TEST 02: CHECK LOGIN");
        WebDriverWait wait = new WebDriverWait(driver, 60);

        //login
        driver.findElement(By.id("loginform-email")).sendKeys("loon_vader@mailinator.com");
        driver.findElement(By.id("loginform-password")).sendKeys("12345678");
        WebElement element = driver.findElement(By.name("login-button"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        driver.findElement(By.name("login-button")).click();

        //check if we on loggedin
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("col-auto")));
        String text = driver.findElement(By.className("col-auto")).getText();
        Assert.assertTrue(text.toLowerCase().contains("dashboard"));
        System.out.println("===> TEST 02: PASSED");
        System.out.println(" ");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
        service.stop();
    }
}