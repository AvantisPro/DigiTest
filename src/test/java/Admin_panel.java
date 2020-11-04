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

import java.io.File;
import java.io.IOException;

public class Admin_panel {

    WebDriver driver = null;

    ChromeDriverService service = new ChromeDriverService.Builder().usingPort(8082).
            usingDriverExecutable(new File("/usr/bin/chromedriver"))
            //.withWhitelistedIps("")
            //.withVerbose(true)
            .build();

    @BeforeClass
    public void init() throws IOException {
        service.start();
        driver = new RemoteWebDriver(service.getUrl(), new ChromeOptions());
        driver.manage().window().maximize();
        driver.get("https://dev.digisposa.com/auth/login");
    }


    @Test
    public void Test01_send_request_as_brand() throws InterruptedException {

        System.out.println(" ");
        System.out.println("======================================");
        System.out.println("===> ADMIN USER TESTS <===");
        System.out.println("======================================");
        System.out.println(" ");

        System.out.println("===> TEST 01: SEND REQUEST AS BRAND");
        WebDriverWait wait = new WebDriverWait(driver, 60);

        driver.get("https://dev.digisposa.com/signup");

        //choose brand
        driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div/form/div[1]/div[1]/div/label")).click();
        driver.findElement(By.id("first_name")).sendKeys("Test");
        driver.findElement(By.id("last_name")).sendKeys("Brand");
        driver.findElement(By.id("email")).sendKeys("loon_auto_brand@mailinator.com");
        driver.findElement(By.id("phone_number")).sendKeys("+380998887722");
        driver.findElement(By.id("website")).sendKeys("https://dev.digisposa.com/");
        driver.findElement(By.id("company")).sendKeys("AutoTest");
        driver.findElement(By.id("comment")).sendKeys("testing");

        driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div/form/div[9]/div/button")).click();

        //check if success
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div/div/h1")));
        String cong = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div/div/h1")).getText();
        Assert.assertEquals(cong, "CONGRATULATIONS!");

        System.out.println("===> TEST 01: PASSED");
        System.out.println(" ");
    }

    @Test
    public void Test02_send_request_as_retailer() throws InterruptedException {

        System.out.println("===> TEST 02: SEND REQUEST AS RETAILER");

        WebDriverWait wait = new WebDriverWait(driver, 60);

        driver.get("https://dev.digisposa.com/signup");

        //choose brand
        driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div/form/div[1]/div[2]/div/label")).click();
        driver.findElement(By.id("first_name")).sendKeys("Test");
        driver.findElement(By.id("last_name")).sendKeys("Retailer");
        driver.findElement(By.id("email")).sendKeys("loon_auto_retailer@mailinator.com");
        driver.findElement(By.id("phone_number")).sendKeys("+380998887722");
        driver.findElement(By.id("website")).sendKeys("https://dev.digisposa.com/");
        driver.findElement(By.id("company")).sendKeys("AutoTest");
        driver.findElement(By.id("comment")).sendKeys("testing");

        driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div/form/div[9]/div/button")).click();

        //check if success
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div/div/h1")));
        String cong = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div/div/h1")).getText();
        Assert.assertEquals(cong, "CONGRATULATIONS!");

        System.out.println("===> TEST 02: PASSED");
        System.out.println(" ");

    }

    @Test
    public void Test03_login_as_admin_and_reject_brands_request() {

        System.out.println("===> TEST 03: LOGIN AS ADMIN AND REJECT BRANDS REQUEST");

        WebDriverWait wait = new WebDriverWait(driver, 60);

        driver.get("https://dev.digisposa.com/auth/login");

        driver.findElement(By.id("loginform-email")).sendKeys("sposadigi@gmail.com");
        driver.findElement(By.id("loginform-password")).sendKeys("12345678");
        WebElement element = driver.findElement(By.name("login-button"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        driver.findElement(By.name("login-button")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("col-auto")));
        String text = driver.findElement(By.className("col-auto")).getText();
        Assert.assertTrue(text.toLowerCase().contains("dashboard"));

        //go to brands requests
        driver.get("https://dev.digisposa.com/admin/request");
        String brand_name = driver.findElement(By.xpath("//*[@id=\"brand\"]/div/div/div[1]/div[1]/div[1]")).getText();
        Assert.assertEquals(brand_name, "Test Brand");

        driver.findElement(By.xpath("//*[@id=\"brand\"]/div/div/div[2]/div[2]/div/div/div[3]/button")).click();

        System.out.println("===> TEST 03: PASSED");
        System.out.println(" ");
    }

    @Test
    public void Test04_login_as_admin_and_reject_retailers_request() throws InterruptedException {

        System.out.println("===> TEST 04: LOGIN AS ADMIN AND REJECT RETAILERS REQUEST");

        WebDriverWait wait = new WebDriverWait(driver, 5);

        //go to retailers requests
        driver.get("https://dev.digisposa.com/admin/request");
        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div[1]/div[1]/div/ul/li[2]/a/span")).click();

        String brand_name = driver.findElement(By.xpath("//*[@id=\"retailers\"]/div/div/div[1]/div[1]/div[1]")).getText();
        Assert.assertEquals(brand_name, "Test Retailer");

        driver.findElement(By.xpath("//*[@id=\"retailers\"]/div/div/div[2]/div[2]/div/div/div[3]/button")).click();

        System.out.println("===> TEST 04: PASSED");
        System.out.println(" ");
    }


    @AfterClass
    public void end(){
        driver.quit();
    }

}
