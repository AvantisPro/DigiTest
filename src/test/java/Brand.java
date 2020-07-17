import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.List;


public class Brand {
    WebDriver driver;

    @BeforeClass
    public void init(){
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();

//        System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
//        driver = new FirefoxDriver();

        driver.manage().window().maximize();
        driver.get("https://dev.digisposa.com/auth/login");
    }

    @Test
    public void Test01_check_login_page(){
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
        WebDriverWait wait = new WebDriverWait(driver, 5);

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
    public void Test03_createCollection() throws InterruptedException {

        System.out.println("===> TEST 04: CREATE COLLECTION");

        WebDriverWait wait = new WebDriverWait(driver, 5);

        //add new collection
        driver.get("https://dev.digisposa.com/brand-collection");
        driver.findElement(By.className("add-new-collection")).click();
        driver.findElement(By.id("title")).sendKeys("TestCollection");

        driver.findElement(By.id("description")).sendKeys("Test Desc");

        driver.findElement(By.className("col-8")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("finish-btn"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/form/div[1]/div/div[2]/div/div/div[1]/a"))).click();

        Thread.sleep(2000);

        List<WebElement> collections = driver.findElements(By.className("text-muted"));
        WebElement lastCollection = collections.get(collections.size() - 1);

        String text = lastCollection.getText();

        Assert.assertTrue(text.toLowerCase().contains("0 items"));

        driver.manage().window().setSize(new Dimension(414, 736));
        driver.manage().window().setPosition(new Point(0, 0));
        driver.navigate().refresh();

        List<WebElement> deleteBtns = driver.findElements(By.className("my-icon-delete"));
        WebElement lastBtn = deleteBtns.get(1);

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", lastBtn);

        lastBtn.click();

        Thread.sleep(2000);
        driver.findElement(By.xpath("/html/body/div[4]/div/div[3]/button[1]")).click();

        driver.manage().window().maximize();
        System.out.println("===> TEST 04: PASSED");
        System.out.println(" ");

    }

    @Test
    public void Test04_search_retailer() throws InterruptedException {

        System.out.println("===> TEST 05: CHECK SEARCH");
        driver.manage().window().maximize();
        driver.get("https://dev.digisposa.com/search");
        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div/div[1]/div/div[1]/div/div/input")).sendKeys("sadoni");
        Thread.sleep(4000);
        String name = driver.findElement(By.className("card-title")).getText();
        System.out.println(name);
        Assert.assertTrue(name.toLowerCase().contains("sadoni bridal boutique norway, oslo"));
        System.out.println("===> TEST 05: PASSED");
        System.out.println(" ");
    }

    @Test
    public void Test05_send_message() throws InterruptedException {

        System.out.println("===> TEST 06: SEND MESSAGE");

        driver.get("https://dev.digisposa.com/message-center");
        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div/div[1]/div[1]/div[1]/div[1]/input")).sendKeys("Mike");

        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div/div[1]/div[2]/div[1]")).click();
        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div/div[2]/div/div[2]/div[2]/input")).sendKeys("test");
        Thread.sleep(1000);
        driver.findElement(By.className("fa-telegram-plane")).click();
        String text = driver.findElement(By.className("mc-message-text")).getAttribute("innerHTML");
        System.out.println(text);
        Assert.assertTrue(text.toLowerCase().contains("test"));

        System.out.println("===> TEST 06: PASSED");
        System.out.println(" ");
    }


    @Test
    public void Test06_check_preview_of_brand_page() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        System.out.println("===> TEST 08: check_preview_of_brand_page");

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

    }

    @Test
    public void Test07_invite_retailer() throws InterruptedException {

        driver.get("https://dev.digisposa.com/dashboard");
        driver.get("https://dev.digisposa.com/retailer/invitations");
        driver.findElement(By.className("add-new-user")).click();
        driver.findElement(By.id("company")).sendKeys("test");
        driver.findElement(By.id("comment")).sendKeys("loon_test@mailinator.com");
        driver.findElement(By.xpath("//*[@id=\"inviteModal___BV_modal_body_\"]/div/form/div[3]/div/div[1]")).click();
    }

    @Test
    public void Test08_create_post() throws InterruptedException {

        driver.get("https://dev.digisposa.com/learning-center");
        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div[1]/div[2]/a")).click();
        Thread.sleep(2000);
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

        //check
        String empty = driver.findElement(By.className("justify-content-center")).getText();
        Assert.assertEquals(empty, "No items");

    }

    @Test
    public void Test08_dashboard_statistic() {

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
    }

    @Test
    public void Test_Newslatter() throws InterruptedException {

        driver.get("https://dev.digisposa.com/message-center");
        Thread.sleep(1000);
        //Go to newslatter
        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div/div[1]/div[1]/div[2]/a[2]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div/div[2]/div/div[2]/div[2]/input")).sendKeys("Autotest_newslatter");
        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div/div[2]/div/div[2]/div[2]/a/span")).click();
        Thread.sleep(2000);
        //check message
        String test_text = driver.findElement(By.className("font-14")).getText();

        Assert.assertEquals(test_text, "Autotest_newslatter");
    }

    @Test
    public void Test_clear_notic() throws InterruptedException {

        driver.get("https://dev.digisposa.com/dashboard");
        Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[1]/nav/ul[1]/li[3]/a")).click();
        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[1]/nav/ul[1]/li[3]/div/a")).click();
    }

    @AfterClass
    public void end(){
        driver.quit();
    }

}