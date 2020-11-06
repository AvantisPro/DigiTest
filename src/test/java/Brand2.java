import java.io.File;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class Brand2 {
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
    public void Test01_check_login_page(){
        System.out.println(" ");
        System.out.println("======================================");
        System.out.println("===> BRAND USER TESTS <===");
        System.out.println("======================================");
        System.out.println(" ");

        System.out.println("===> TEST 01: CHECK LOGIN PAGE");
        //check login page
        String text = driver.findElement(By.className("section__title")).getText();
        Assert.assertTrue(text.toLowerCase().contains("login"));
        System.out.println("===> TEST 01: PASSED");
        System.out.println(" ");
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

    @Test
    public void Test03_createCollection(){

        System.out.println("===> TEST 03: CREATE COLLECTION");

        WebDriverWait wait = new WebDriverWait(driver, 60);

        //add new collection
        driver.get("https://dev.digisposa.com/brand-collection");
        driver.findElement(By.className("add-new-collection")).click();
        driver.findElement(By.id("title")).sendKeys("TestCollection");

        driver.findElement(By.id("description")).sendKeys("Test Desc");

        driver.findElement(By.className("col-8")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("finish-btn"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/form/div[1]/div/div[2]/div/div/div[1]/a"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("text-muted")));

        List<WebElement> collections = driver.findElements(By.className("text-muted"));
        WebElement lastCollection = collections.get(collections.size() - 1);

        String text = lastCollection.getText();

        Assert.assertTrue(text.toLowerCase().contains("0 items"));

        driver.manage().window().setSize(new Dimension(414, 736));
        driver.manage().window().setPosition(new Point(0, 0));
        driver.navigate().refresh();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("my-icon-delete")));

        List<WebElement> deleteBtns = driver.findElements(By.className("my-icon-delete"));
        WebElement lastBtn = deleteBtns.get(1);

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", lastBtn);

        lastBtn.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[4]/div/div[3]/button[1]")));
        driver.findElement(By.xpath("/html/body/div[4]/div/div[3]/button[1]")).click();

        driver.manage().window().maximize();
        System.out.println("===> TEST 03: PASSED");
        System.out.println(" ");

    }

    @Test
    public void Test04_search_retailer() throws InterruptedException {

        //WebDriverWait wait = new WebDriverWait(driver, 60);

        System.out.println("===> TEST 04: CHECK SEARCH");
        driver.manage().window().maximize();
        driver.get("https://dev.digisposa.com/search");
        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div/div[1]/div/div[1]/div/div/input")).sendKeys("sadoni");
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("card-title")));
        Thread.sleep(5000);
        String name = driver.findElement(By.className("card-title")).getText();
        System.out.println(name);
        Assert.assertTrue(name.toLowerCase().contains("sadoni bridal boutique norway, oslo"));
        System.out.println("===> TEST 05: PASSED");
        System.out.println(" ");
    }

    @Test
    public void Test05_send_message() {

        WebDriverWait wait = new WebDriverWait(driver, 60);

        System.out.println("===> TEST 05: SEND MESSAGE");

        driver.get("https://dev.digisposa.com/message-center");
        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div/div[1]/div[1]/div[1]/div[1]/input")).sendKeys("Mike");

        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div/div[1]/div[2]/div[1]")).click();
        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div/div[2]/div/div[2]/div[2]/input")).sendKeys("test");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("fa-telegram-plane")));
        driver.findElement(By.className("fa-telegram-plane")).click();
        String text = driver.findElement(By.className("mc-message-text")).getAttribute("innerHTML");
        System.out.println(text);
        Assert.assertTrue(text.toLowerCase().contains("test"));

        System.out.println("===> TEST 05: PASSED");
        System.out.println(" ");
    }


    @Test
    public void Test06_check_preview_of_brand_page() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 60);

        System.out.println("===> TEST 06: CHECK PREVIEW OF THE BRAND PAGE");

        driver.get("https://dev.digisposa.com/brand-settings/profile");
        driver.findElement(By.id("title")).clear();
        driver.findElement(By.id("title")).sendKeys("Armani123");

        WebElement preview = driver.findElement(By.className("styled-btn-v3"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", preview);
        preview.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("order-1")));
        String previewTitle = driver.findElement(By.className("order-1")).getText();
        Assert.assertEquals(previewTitle, "ARMANI123 ID202020");

        Thread.sleep(5000);
        driver.findElement(By.className("styled-btn-v3")).click();
        WebElement cancel = driver.findElement(By.className("styled-btn-v2"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", cancel);
        cancel.click();

        System.out.println("===> TEST 06: PASSED");
        System.out.println(" ");

    }

    @Test
    public void Test07_invite_retailer() {

        System.out.println("===> TEST 07: INVITE RETAILER");

        driver.get("https://dev.digisposa.com/dashboard");
        driver.get("https://dev.digisposa.com/retailer/invitations");
        driver.findElement(By.className("add-new-user")).click();
        driver.findElement(By.id("company")).sendKeys("test");
        driver.findElement(By.id("comment")).sendKeys("loon_test@mailinator.com");
        driver.findElement(By.xpath("//*[@id=\"inviteModal___BV_modal_body_\"]/div/form/div[3]/div/div[1]")).click();

        System.out.println("===> TEST 07: PASSED");
        System.out.println(" ");
    }

    @Test
    public void Test08_create_post(){
        WebDriverWait wait = new WebDriverWait(driver, 60);

        System.out.println("===> TEST 08: CREATE POST");

        driver.get("https://dev.digisposa.com/learning-center");
        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div[1]/div[2]/a")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("title-post")));
        driver.findElement(By.id("title-post")).sendKeys("Autotest");

        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div[2]/form/div/div[2]/div[1]/div[1]/div/div[1]/div/label")).click();

        //preview
        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div[2]/form/div/div[2]/div[2]/button[2]")).click();

        String article = driver.findElement(By.className("article-title")).getText();
        Assert.assertEquals(article, "AUTOTEST");

        //back to edit and publish
        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div/button")).click();
        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div[2]/form/div/div[2]/button")).click();

        //check new post
        driver.get("https://dev.digisposa.com/learning-center/awaiting");
        String title = driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div[2]/table/tbody/tr/td[2]/a")).getText();
        Assert.assertEquals(title, "Autotest");

        //delete post
        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div[2]/table/tbody/tr/td[7]")).click();
        driver.navigate().refresh();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("justify-content-center")));

        //check
        String empty = driver.findElement(By.className("justify-content-center")).getText();
        Assert.assertEquals(empty, "No items");

        System.out.println("===> TEST 08: PASSED");
        System.out.println(" ");

    }

    @Test
    public void Test08_dashboard_statistic() {
        WebDriverWait wait = new WebDriverWait(driver, 60);

        System.out.println("===> TEST 09: CHECK DASHBOARD STATISTIC");

        driver.get("https://dev.digisposa.com/brand-settings/dashboard");

        //uncheck all checkboxes
        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div[2]/div/div/form/div/div[1]/div/div/div[1]/label")).click();
        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div[2]/div/div/form/div/div[1]/div/div/div[2]/label")).click();
        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div[2]/div/div/form/div/div[1]/div/div/div[3]/label")).click();

        //save
        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div[2]/div/div/form/div/div[2]/button")).click();

        //take text
        String bestsell = driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div[2]/div/div/form/div/div[1]/div/div/div[4]/label")).getText();

        //go to dashboard
        driver.get("https://dev.digisposa.com/dashboard");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div[2]/div/div/div/div[1]/div[1]")));

        //get text of the block
        String bestDash = driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div[2]/div/div/div/div[1]/div[1]")).getText();

        //Compare
        Assert.assertEquals(bestsell, "Bestsellers");

        //go back and activate all
        driver.get("https://dev.digisposa.com/brand-settings/dashboard");

        //check all
        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div[2]/div/div/form/div/div[1]/div/div/div[1]/label")).click();
        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div[2]/div/div/form/div/div[1]/div/div/div[2]/label")).click();
        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div[2]/div/div/form/div/div[1]/div/div/div[3]/label")).click();

        //save
        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div[2]/div/div/form/div/div[2]/button")).click();

        System.out.println("===> TEST 09: PASSED");
        System.out.println(" ");
    }

    @Test
    public void Test10_Newslatter() {

        WebDriverWait wait = new WebDriverWait(driver, 60);

        System.out.println("===> TEST 10: NEWSLATTER CHECK");

        driver.get("https://dev.digisposa.com/message-center");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div/div[1]/div[1]/div[2]/a[2]")));
        //Go to newslatter
        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div/div[1]/div[1]/div[2]/a[2]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div/div[2]/div/div[2]/div[2]/input")));
        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div/div[2]/div/div[2]/div[2]/input")).sendKeys("Autotest_newslatter");
        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div/div[2]/div/div[2]/div[2]/a/span")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("font-14")));
        //check message
        String test_text = driver.findElement(By.className("font-14")).getText();

        Assert.assertEquals(test_text, "Autotest_newslatter");

        System.out.println("===> TEST 10: PASSED");
        System.out.println(" ");
    }

    @Test
    public void Test11_search_order_in_history() {
        WebDriverWait wait = new WebDriverWait(driver, 60);

        System.out.println("===> TEST 11: SEARCH ORDER IN HISTORY");

        driver.get("https://dev.digisposa.com/order#history");

        driver.findElement(By.xpath("//*[@id=\"history\"]/div/div[1]/div/div[1]/div[1]/input")).sendKeys("272");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"history\"]/div/div[2]/div/div/div/div/div/div[1]/h4")));

        String OrderNumb = driver.findElement(By.xpath("//*[@id=\"history\"]/div/div[2]/div/div/div/div/div/div[1]/h4")).getText();

        Assert.assertEquals(OrderNumb, "#0000000272");

        System.out.println("===> TEST 11: PASSED");
        System.out.println(" ");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
        service.stop();
    }
}