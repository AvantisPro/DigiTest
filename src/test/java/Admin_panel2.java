import java.io.File;
import java.io.IOException;
import java.util.Random;

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


public class Admin_panel2 {
    WebDriver driver = null;

    ChromeDriverService service = new ChromeDriverService.Builder().usingPort(8082).
            usingDriverExecutable(new File("/usr/bin/chromedriver"))
            .build();
    
    
    @BeforeClass
    public void init() throws IOException {
        service.start();
        driver = new RemoteWebDriver(service.getUrl(), new ChromeOptions());
        driver.manage().window().maximize();
        driver.get("https://dev.digisposa.com/auth/login");
    }

    @Test
    public void Test01_send_request_as_brand() {

        System.out.println("1440");
        System.out.println("======================================");
        System.out.println("===> ADMIN USER 12 TESTS <===");
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
    public void Test02_send_request_as_retailer() {

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
    public void Test04_login_as_admin_and_reject_retailers_request() {
        WebDriverWait wait = new WebDriverWait(driver, 60);

        System.out.println("===> TEST 04: LOGIN AS ADMIN AND REJECT RETAILERS REQUEST");

        //WebDriverWait wait = new WebDriverWait(driver, 5);

        //go to retailers requests
        driver.get("https://dev.digisposa.com/admin/request");
        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div[1]/div[1]/div/ul/li[2]/a/span")).click();

        String brand_name = driver.findElement(By.xpath("//*[@id=\"retailers\"]/div/div/div[1]/div[1]/div[1]")).getText();
        Assert.assertEquals(brand_name, "Test Retailer");

        driver.findElement(By.xpath("//*[@id=\"retailers\"]/div/div/div[2]/div[2]/div/div/div[3]/button")).click();

        //login
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(414, 736));
        driver.navigate().refresh();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("profile-dropdown")));

        driver.findElement(By.id("profile-dropdown")).click();
        driver.findElement(By.xpath("//*[@id=\"profile-dropdown-menu\"]/div[3]/form/button")).click();

        String loginText = driver.findElement(By.className("section__title")).getText();
        Assert.assertTrue(loginText.toLowerCase().contains("login"));
        driver.manage().window().maximize();

        System.out.println("===> TEST 04: PASSED");
        System.out.println(" ");
    }

    @Test
    public void Test05_block_brand_on_platform() throws InterruptedException {

        System.out.println("===> TEST 05: BLOCK BRAND ON PLATFORM");
        WebDriverWait wait = new WebDriverWait(driver, 60);

        driver.get("https://dev.digisposa.com/signup");

        //choose brand
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("custom-control-label")));
        driver.findElement(By.className("custom-control-label")).click();
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

        driver.get("https://dev.digisposa.com/auth/login");
        driver.findElement(By.id("loginform-email")).sendKeys("sposadigi@gmail.com");
        driver.findElement(By.id("loginform-password")).sendKeys("12345678");
        WebElement element = driver.findElement(By.name("login-button"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        driver.findElement(By.name("login-button")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("col-auto")));
        String text = driver.findElement(By.className("col-auto")).getText();
        Assert.assertTrue(text.toLowerCase().contains("dashboard"));

        driver.get("https://dev.digisposa.com/admin/request");
        String brand_name = driver.findElement(By.xpath("//*[@id=\"brand\"]/div/div/div[1]/div[1]/div[1]")).getText();
        Assert.assertEquals(brand_name, "Test Brand");

        driver.findElement(By.xpath("//*[@id=\"brand\"]/div/div/div[2]/div[2]/div/div/div[1]/button")).click();
        Thread.sleep(5000);

        driver.get("https://dev.digisposa.com/admin/brand");

        //find brand
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div/table/tbody/tr[11]/td[3]")));
        String brands_name = driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div/table/tbody/tr[11]/td[3]")).getText();
        Assert.assertEquals(brands_name, "AutoTest");

        //click block
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div/table/tbody/tr[11]/td[10]/div")));
        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div/table/tbody/tr[11]/td[10]/div")).click();

        //confirm
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"swal2-title\"]")));
        String title = driver.findElement(By.xpath("//*[@id=\"swal2-title\"]")).getText();
        Assert.assertEquals(title, "Block account");

        //confirm block
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[4]/div/div[3]/button[1]")));
        driver.findElement(By.xpath("/html/body/div[4]/div/div[3]/button[1]")).click();
        Thread.sleep(5000);

        //check
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div/table/tbody/tr[11]/td[10]/div/a")));
        String activate = driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div/table/tbody/tr[11]/td[10]/div/a")).getText();
        Assert.assertEquals(activate, "ACTIVATE");

        System.out.println("===> TEST 05: PASSED");
        System.out.println(" ");
    }

    @Test
    public void Test06_delete_brand_from_platform()  {

        System.out.println("===> TEST 06: DELETE BRAND FROM PLATFORM");
        WebDriverWait wait = new WebDriverWait(driver, 60);

        //find brand
        driver.get("https://dev.digisposa.com/admin/brand");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div/table/tbody/tr[11]/td[3]")));
        String brands_name = driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div/table/tbody/tr[11]/td[3]")).getText();
        Assert.assertEquals(brands_name, "AutoTest");

        //delete acc
        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div/table/tbody/tr[11]/td[11]/a/span")).click();
        String delete_text = driver.findElement(By.xpath("//*[@id=\"swal2-title\"]")).getText();
        Assert.assertEquals(delete_text, "Deleting a brand");
        //confirm
        driver.findElement(By.xpath("/html/body/div[4]/div/div[3]/button[1]")).click();

        //logout
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(414, 736));
        driver.navigate().refresh();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("profile-dropdown")));

        driver.findElement(By.id("profile-dropdown")).click();
        driver.findElement(By.xpath("//*[@id=\"profile-dropdown-menu\"]/div[3]/form/button")).click();

        String loginText = driver.findElement(By.className("section__title")).getText();
        Assert.assertTrue(loginText.toLowerCase().contains("login"));
        driver.manage().window().maximize();

        System.out.println("===> TEST 06: PASSED");
        System.out.println(" ");

    }

    @Test
    public void Test07_check_ticket_opened_and_closed_by_brand( ) throws InterruptedException {

        Random rand = new Random(); //instance of random class
        int upperbound = 100;
        //generate random values from 0-24
        int int_random = rand.nextInt(upperbound);


        System.out.println("===> TEST 07: CHECK TICKET OPENED AND CLOSED BY BRAND");
        WebDriverWait wait = new WebDriverWait(driver, 60);

        driver.get("https://dev.digisposa.com/auth/login");

        //login as brand
        driver.findElement(By.id("loginform-email")).sendKeys("loon_vader@mailinator.com");
        driver.findElement(By.id("loginform-password")).sendKeys("12345678");
        WebElement element1 = driver.findElement(By.name("login-button"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element1);
        driver.findElement(By.name("login-button")).click();

        //check if we on loggedin
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("col-auto")));
        String text = driver.findElement(By.className("col-auto")).getText();
        Assert.assertTrue(text.toLowerCase().contains("dashboard"));

        //send ticket
        driver.get("https://dev.digisposa.com/help-center");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("form-control")));
        driver.findElement(By.className("form-control")).sendKeys("Hi, it's autotests" + " : " + int_random );
        driver.findElement(By.className("fa-telegram-plane")).click();
        Thread.sleep(5000);

        //check if created
        String ticket_text = driver.findElement(By.className("ticket__text")).getText();
        Assert.assertEquals(ticket_text, "Hi, it's autotests" + " : " + int_random);

        //logout
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(414, 736));
        driver.navigate().refresh();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("profile-dropdown")));

        driver.findElement(By.id("profile-dropdown")).click();
        driver.findElement(By.xpath("//*[@id=\"profile-dropdown-menu\"]/div[3]/form/button")).click();

        String loginText = driver.findElement(By.className("section__title")).getText();
        Assert.assertTrue(loginText.toLowerCase().contains("login"));
        driver.manage().window().maximize();

        //login as adm
        driver.get("https://dev.digisposa.com/auth/login");

        driver.findElement(By.id("loginform-email")).sendKeys("sposadigi@gmail.com");
        driver.findElement(By.id("loginform-password")).sendKeys("12345678");
        WebElement element = driver.findElement(By.name("login-button"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        driver.findElement(By.name("login-button")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("col-auto")));
        String text2 = driver.findElement(By.className("col-auto")).getText();
        Assert.assertTrue(text2.toLowerCase().contains("dashboard"));

        //go to ticket
        driver.findElement(By.className("ticket__reply__icon")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("mc-message-text")));
        String ticket_text_from_user = driver.findElement(By.className("mc-message-text")).getText();
        Assert.assertEquals(ticket_text_from_user, "Hi, it's autotests" + " : " + int_random);

        //reply
        driver.findElement(By.className("form-control-lg")).sendKeys("This is reply" + " : " + int_random);
        driver.findElement(By.className("fa-telegram-plane")).click();

        //logout
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(414, 736));
        driver.navigate().refresh();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("profile-dropdown")));

        driver.findElement(By.id("profile-dropdown")).click();
        driver.findElement(By.xpath("//*[@id=\"profile-dropdown-menu\"]/div[3]/form/button")).click();

        Assert.assertTrue(loginText.toLowerCase().contains("login"));
        driver.manage().window().maximize();

        //login as brand
        driver.get("https://dev.digisposa.com/auth/login");

        //login as brand
        driver.findElement(By.id("loginform-email")).sendKeys("loon_vader@mailinator.com");
        driver.findElement(By.id("loginform-password")).sendKeys("12345678");
        WebElement element2 = driver.findElement(By.name("login-button"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element2);
        driver.findElement(By.name("login-button")).click();

        //check if we on loggedin
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("col-auto")));
        Assert.assertTrue(text.toLowerCase().contains("dashboard"));

        //go to ticket
        driver.get("https://dev.digisposa.com/help-center");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("fa-comment-alt")));
        driver.findElement(By.className("fa-comment-alt")).click();

        //check reply
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("mc-message-text")));
        String admins_reply = driver.findElement(By.className("mc-message-text")).getText();
        Assert.assertEquals(admins_reply, "This is reply" + " : " + int_random);

        //close ticket
        driver.findElement(By.className("mc-delete-chat")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div[2]/div[2]/div[2]/h3")));
        String ticket_closed = driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div[2]/div[2]/div[2]/h3")).getText();
        Assert.assertEquals(ticket_closed, "TICKET RESOLVED");

        //logout
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(414, 736));
        driver.navigate().refresh();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("profile-dropdown")));

        driver.findElement(By.id("profile-dropdown")).click();
        driver.findElement(By.xpath("//*[@id=\"profile-dropdown-menu\"]/div[3]/form/button")).click();

        Assert.assertTrue(loginText.toLowerCase().contains("login"));
        driver.manage().window().maximize();

        System.out.println("===> TEST 07: PASSED");
        System.out.println(" ");
    }

    @Test
    public void Test08_check_ticket_opened_and_closed_by_retailer( ) throws InterruptedException {

        Random rand = new Random(); //instance of random class
        int upperbound = 100;
        //generate random values from 0-24
        int int_random = rand.nextInt(upperbound);


        System.out.println("===> TEST 08: CHECK TICKET OPENED AND CLOSED BY RETAILER");
        WebDriverWait wait = new WebDriverWait(driver, 60);

        driver.get("https://dev.digisposa.com/auth/login");

        //login as brand
        driver.findElement(By.id("loginform-email")).sendKeys("loon_test2@mailinator.com");
        driver.findElement(By.id("loginform-password")).sendKeys("12345678");
        WebElement element1 = driver.findElement(By.name("login-button"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element1);
        driver.findElement(By.name("login-button")).click();

        //check if we on loggedin
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("col-auto")));
        String text = driver.findElement(By.className("col-auto")).getText();
        Assert.assertTrue(text.toLowerCase().contains("dashboard"));

        //send ticket
        driver.get("https://dev.digisposa.com/help-center");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("form-control")));
        driver.findElement(By.className("form-control")).sendKeys("Hi, it's autotests" + " : " + int_random );
        driver.findElement(By.className("fa-telegram-plane")).click();
        Thread.sleep(5000);

        //check if created
        String ticket_text = driver.findElement(By.className("ticket__text")).getText();
        Assert.assertEquals(ticket_text, "Hi, it's autotests" + " : " + int_random);

        //logout
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(414, 736));
        driver.navigate().refresh();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("profile-dropdown")));

        driver.findElement(By.id("profile-dropdown")).click();
        driver.findElement(By.xpath("//*[@id=\"profile-dropdown-menu\"]/div[3]/form/button")).click();

        String loginText = driver.findElement(By.className("section__title")).getText();
        Assert.assertTrue(loginText.toLowerCase().contains("login"));
        driver.manage().window().maximize();

        //login as adm
        driver.get("https://dev.digisposa.com/auth/login");

        driver.findElement(By.id("loginform-email")).sendKeys("sposadigi@gmail.com");
        driver.findElement(By.id("loginform-password")).sendKeys("12345678");
        WebElement element = driver.findElement(By.name("login-button"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        driver.findElement(By.name("login-button")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("col-auto")));
        String text2 = driver.findElement(By.className("col-auto")).getText();
        Assert.assertTrue(text2.toLowerCase().contains("dashboard"));

        //go to ticket
        driver.findElement(By.className("ticket__reply__icon")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("mc-message-text")));
        String ticket_text_from_user = driver.findElement(By.className("mc-message-text")).getText();
        Assert.assertEquals(ticket_text_from_user, "Hi, it's autotests" + " : " + int_random);

        //reply
        driver.findElement(By.className("form-control-lg")).sendKeys("This is reply" + " : " + int_random);
        driver.findElement(By.className("fa-telegram-plane")).click();

        //logout
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(414, 736));
        driver.navigate().refresh();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("profile-dropdown")));

        driver.findElement(By.id("profile-dropdown")).click();
        driver.findElement(By.xpath("//*[@id=\"profile-dropdown-menu\"]/div[3]/form/button")).click();

        Assert.assertTrue(loginText.toLowerCase().contains("login"));
        driver.manage().window().maximize();

        //login as brand
        driver.get("https://dev.digisposa.com/auth/login");

        //login as brand
        driver.findElement(By.id("loginform-email")).sendKeys("loon_test2@mailinator.com");
        driver.findElement(By.id("loginform-password")).sendKeys("12345678");
        WebElement element2 = driver.findElement(By.name("login-button"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element2);
        driver.findElement(By.name("login-button")).click();

        //check if we on logged in
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("col-auto")));
        Assert.assertTrue(text.toLowerCase().contains("dashboard"));

        //go to ticket
        driver.get("https://dev.digisposa.com/help-center");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("fa-comment-alt")));
        driver.findElement(By.className("fa-comment-alt")).click();

        //check reply
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("mc-message-text")));
        String admins_reply = driver.findElement(By.className("mc-message-text")).getText();
        Assert.assertEquals(admins_reply, "This is reply" + " : " + int_random);

        //close ticket
        driver.findElement(By.className("mc-delete-chat")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div[2]/div[2]/div[2]/h3")));
        String ticket_closed = driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div[2]/div[2]/div[2]/h3")).getText();
        Assert.assertEquals(ticket_closed, "TICKET RESOLVED");

        //logout
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(414, 736));
        driver.navigate().refresh();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("profile-dropdown")));

        driver.findElement(By.id("profile-dropdown")).click();
        driver.findElement(By.xpath("//*[@id=\"profile-dropdown-menu\"]/div[3]/form/button")).click();

        Assert.assertTrue(loginText.toLowerCase().contains("login"));
        driver.manage().window().maximize();

        System.out.println("===> TEST 08: PASSED");
        System.out.println(" ");
    }

    @Test
    public void Test09_check_ticket_opened_by_brand_and_closed_by_admin( ) throws InterruptedException {

        Random rand = new Random(); //instance of random class
        int upperbound = 100;
        //generate random values from 0-24
        int int_random = rand.nextInt(upperbound);


        System.out.println("===> TEST 09: CHECK TICKET OPENED BY BRAND AND CLOSED BY ADMIN");
        WebDriverWait wait = new WebDriverWait(driver, 60);

        driver.get("https://dev.digisposa.com/auth/login");

        //login as brand
        driver.findElement(By.id("loginform-email")).sendKeys("loon_test2@mailinator.com");
        driver.findElement(By.id("loginform-password")).sendKeys("12345678");
        WebElement element1 = driver.findElement(By.name("login-button"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element1);
        driver.findElement(By.name("login-button")).click();

        //check if we on loggedin
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("col-auto")));
        String text = driver.findElement(By.className("col-auto")).getText();
        Assert.assertTrue(text.toLowerCase().contains("dashboard"));

        //send ticket
        driver.get("https://dev.digisposa.com/help-center");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("form-control")));
        driver.findElement(By.className("form-control")).sendKeys("Hi, it's autotests" + " : " + int_random );
        driver.findElement(By.className("fa-telegram-plane")).click();
        Thread.sleep(5000);

        //check if created
        String ticket_text = driver.findElement(By.className("ticket__text")).getText();
        Assert.assertEquals(ticket_text, "Hi, it's autotests" + " : " + int_random);

        //logout
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(414, 736));
        driver.navigate().refresh();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("profile-dropdown")));

        driver.findElement(By.id("profile-dropdown")).click();
        driver.findElement(By.xpath("//*[@id=\"profile-dropdown-menu\"]/div[3]/form/button")).click();

        String loginText = driver.findElement(By.className("section__title")).getText();
        Assert.assertTrue(loginText.toLowerCase().contains("login"));
        driver.manage().window().maximize();

        //login as adm
        driver.get("https://dev.digisposa.com/auth/login");

        driver.findElement(By.id("loginform-email")).sendKeys("sposadigi@gmail.com");
        driver.findElement(By.id("loginform-password")).sendKeys("12345678");
        WebElement element = driver.findElement(By.name("login-button"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        driver.findElement(By.name("login-button")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("col-auto")));
        String text2 = driver.findElement(By.className("col-auto")).getText();
        Assert.assertTrue(text2.toLowerCase().contains("dashboard"));

        //go to ticket
        driver.findElement(By.className("ticket__reply__icon")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("mc-message-text")));
        String ticket_text_from_user = driver.findElement(By.className("mc-message-text")).getText();
        Assert.assertEquals(ticket_text_from_user, "Hi, it's autotests" + " : " + int_random);

        //reply
        driver.findElement(By.className("form-control-lg")).sendKeys("This is reply" + " : " + int_random);
        driver.findElement(By.className("fa-telegram-plane")).click();

        //close ticket
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("mc-delete-chat")));
        driver.findElement(By.className("mc-delete-chat")).click();
        Thread.sleep(3000);
        String confirm = driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div[2]/div[2]/div[2]/h3")).getText();
        Assert.assertEquals(confirm, "TICKET RESOLVED");

        //logout
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(414, 736));
        driver.navigate().refresh();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("profile-dropdown")));

        driver.findElement(By.id("profile-dropdown")).click();
        driver.findElement(By.xpath("//*[@id=\"profile-dropdown-menu\"]/div[3]/form/button")).click();

        Assert.assertTrue(loginText.toLowerCase().contains("login"));
        driver.manage().window().maximize();

        System.out.println("===> TEST 09: PASSED");
        System.out.println(" ");
    }

    @Test
    public void Test10_create_new_post_as_brand() throws InterruptedException {
        System.out.println("===> TEST 10: CREATE NEW POST AS BRAND");

        WebDriverWait wait = new WebDriverWait(driver, 60);

        driver.get("https://dev.digisposa.com/auth/login");

        //login as brand
        driver.findElement(By.id("loginform-email")).sendKeys("loon_vader@mailinator.com");
        driver.findElement(By.id("loginform-password")).sendKeys("12345678");
        WebElement element1 = driver.findElement(By.name("login-button"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element1);
        driver.findElement(By.name("login-button")).click();

        //check if we on loggedin
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("col-auto")));
        String text = driver.findElement(By.className("col-auto")).getText();
        Assert.assertTrue(text.toLowerCase().contains("dashboard"));

        //go to learning center
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

        //logout
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(414, 736));
        driver.navigate().refresh();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("profile-dropdown")));

        driver.findElement(By.id("profile-dropdown")).click();
        driver.findElement(By.xpath("//*[@id=\"profile-dropdown-menu\"]/div[3]/form/button")).click();

        String loginText = driver.findElement(By.className("section__title")).getText();
        Assert.assertTrue(loginText.toLowerCase().contains("login"));
        driver.manage().window().maximize();

        //login as admin

        driver.get("https://dev.digisposa.com/auth/login");

        driver.findElement(By.id("loginform-email")).sendKeys("sposadigi@gmail.com");
        driver.findElement(By.id("loginform-password")).sendKeys("12345678");
        WebElement element = driver.findElement(By.name("login-button"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        driver.findElement(By.name("login-button")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("col-auto")));
        String text2 = driver.findElement(By.className("col-auto")).getText();
        Assert.assertTrue(text2.toLowerCase().contains("dashboard"));

        driver.get("https://dev.digisposa.com/admin/blog/user-awaiting");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div[2]/div[2]/div/div/table/tbody/tr/td[2]/a")));
        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div[2]/div[2]/div/div/table/tbody/tr/td[2]/a")).getText();
        Assert.assertEquals(title, "Autotest");

        //confirm
        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div[2]/div[2]/div/div/table/tbody/tr/td[6]/div/button[2]")).click();
        Thread.sleep(2000);
        String confirmed = driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div[2]/div[2]/div/div/table/tbody/tr/td[6]/div/button")).getText();
        Assert.assertEquals(confirmed, "CONFIRMED");

        //logout
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(414, 736));
        driver.navigate().refresh();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("profile-dropdown")));

        driver.findElement(By.id("profile-dropdown")).click();
        driver.findElement(By.xpath("//*[@id=\"profile-dropdown-menu\"]/div[3]/form/button")).click();

        Assert.assertTrue(loginText.toLowerCase().contains("login"));
        driver.manage().window().maximize();

        //login as brand
        driver.get("https://dev.digisposa.com/auth/login");
        driver.findElement(By.id("loginform-email")).sendKeys("loon_vader@mailinator.com");
        driver.findElement(By.id("loginform-password")).sendKeys("12345678");
        WebElement element2 = driver.findElement(By.name("login-button"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element2);
        driver.findElement(By.name("login-button")).click();

        //check if we on loggedin
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("col-auto")));
        Assert.assertTrue(text.toLowerCase().contains("dashboard"));

        //publish
        driver.get("https://dev.digisposa.com/learning-center/awaiting");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div[2]/table/tbody/tr/td[2]/a")));
        String post_title = driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div[2]/table/tbody/tr/td[2]/a")).getText();
        Assert.assertEquals(post_title, "Autotest");
        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div[2]/table/tbody/tr/td[6]/button")).click();
        Thread.sleep(5000);

        //check in blog
        driver.get("https://dev.digisposa.com/blog");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("article-title")));
        String title_text = driver.findElement(By.className("article-title")).getText();
        Assert.assertEquals(title_text, "AUTOTEST");

        //logout
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(414, 736));
        driver.navigate().refresh();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("profile-dropdown")));

        driver.findElement(By.id("profile-dropdown")).click();
        driver.findElement(By.xpath("//*[@id=\"profile-dropdown-menu\"]/div[3]/form/button")).click();

        Assert.assertTrue(loginText.toLowerCase().contains("login"));
        driver.manage().window().maximize();

        System.out.println("===> TEST 10: PASSED");
        System.out.println(" ");
    }

    @Test
    public void Test11_hide_post_as_admin() {

        System.out.println("===> TEST 11: HIDE POST AS ADMIN");

        WebDriverWait wait = new WebDriverWait(driver, 60);

        //login as admin

        driver.get("https://dev.digisposa.com/auth/login");

        driver.findElement(By.id("loginform-email")).sendKeys("sposadigi@gmail.com");
        driver.findElement(By.id("loginform-password")).sendKeys("12345678");
        WebElement element = driver.findElement(By.name("login-button"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        driver.findElement(By.name("login-button")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("col-auto")));
        String text = driver.findElement(By.className("col-auto")).getText();
        Assert.assertTrue(text.toLowerCase().contains("dashboard"));

        driver.get("https://dev.digisposa.com/admin/blog/user-published");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div[2]/div[2]/div/div/table/tbody/tr/td[2]/a")));
        String check_title = driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div[2]/div[2]/div/div/table/tbody/tr/td[2]/a")).getText();
        Assert.assertEquals(check_title, "Autotest");

        //hide post
        driver.findElement(By.className("fa-eye")).click();

        //logout
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(414, 736));
        driver.navigate().refresh();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("profile-dropdown")));

        driver.findElement(By.id("profile-dropdown")).click();
        driver.findElement(By.xpath("//*[@id=\"profile-dropdown-menu\"]/div[3]/form/button")).click();

        String loginText = driver.findElement(By.className("section__title")).getText();
        Assert.assertTrue(loginText.toLowerCase().contains("login"));
        driver.manage().window().maximize();

        //login as brand
        driver.get("https://dev.digisposa.com/auth/login");
        driver.findElement(By.id("loginform-email")).sendKeys("loon_vader@mailinator.com");
        driver.findElement(By.id("loginform-password")).sendKeys("12345678");
        WebElement element1 = driver.findElement(By.name("login-button"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element1);
        driver.findElement(By.name("login-button")).click();

        //check if we on loggedin
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("col-auto")));
        String text2 = driver.findElement(By.className("col-auto")).getText();
        Assert.assertTrue(text2.toLowerCase().contains("dashboard"));

        //check if hidden
        driver.get("https://dev.digisposa.com/learning-center");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("fa-eye")));
        driver.findElement(By.className("fa-eye"));

        //logout
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(414, 736));
        driver.navigate().refresh();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("profile-dropdown")));

        driver.findElement(By.id("profile-dropdown")).click();
        driver.findElement(By.xpath("//*[@id=\"profile-dropdown-menu\"]/div[3]/form/button")).click();

        Assert.assertTrue(loginText.toLowerCase().contains("login"));
        driver.manage().window().maximize();

        System.out.println("===> TEST 11: PASSED");
        System.out.println(" ");

    }

    @Test
    public void Test12_delete_post_as_admin() throws InterruptedException {
        System.out.println("===> TEST 12: DELETE POST AS ADMIN");
        WebDriverWait wait = new WebDriverWait(driver, 60);

        //login as admin

        driver.get("https://dev.digisposa.com/auth/login");

        driver.findElement(By.id("loginform-email")).sendKeys("sposadigi@gmail.com");
        driver.findElement(By.id("loginform-password")).sendKeys("12345678");
        WebElement element = driver.findElement(By.name("login-button"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        driver.findElement(By.name("login-button")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("col-auto")));
        String text = driver.findElement(By.className("col-auto")).getText();
        Assert.assertTrue(text.toLowerCase().contains("dashboard"));

        //go to post
        driver.get("https://dev.digisposa.com/admin/blog/user-published");

        //check post
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div[2]/div[2]/div/div/table/tbody/tr/td[2]/a")));
        String check_title = driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div[2]/div[2]/div/div/table/tbody/tr/td[2]/a")).getText();
        Assert.assertEquals(check_title, "Autotest");

        //delete
        driver.findElement(By.className("my-icon-delete")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[4]/div/div[3]/button[1]")));
        driver.findElement(By.xpath("/html/body/div[4]/div/div[3]/button[1]")).click();

        //check if deleted
        Thread.sleep(3000);
        String no_post = driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div[2]/div[2]/div/div/table/tbody/tr/td")).getText();
        Assert.assertEquals(no_post, "No posts");

        //logout
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(414, 736));
        driver.navigate().refresh();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("profile-dropdown")));

        driver.findElement(By.id("profile-dropdown")).click();
        driver.findElement(By.xpath("//*[@id=\"profile-dropdown-menu\"]/div[3]/form/button")).click();

        String loginText = driver.findElement(By.className("section__title")).getText();
        Assert.assertTrue(loginText.toLowerCase().contains("login"));
        driver.manage().window().maximize();

        //login as brand
        driver.get("https://dev.digisposa.com/auth/login");
        driver.findElement(By.id("loginform-email")).sendKeys("loon_vader@mailinator.com");
        driver.findElement(By.id("loginform-password")).sendKeys("12345678");
        WebElement element1 = driver.findElement(By.name("login-button"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element1);
        driver.findElement(By.name("login-button")).click();

        //check if we on loggedin
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("col-auto")));
        String text2 = driver.findElement(By.className("col-auto")).getText();
        Assert.assertTrue(text2.toLowerCase().contains("dashboard"));

        //check if deleted
        driver.get("https://dev.digisposa.com/learning-center");
        String post_check = driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div[2]/table/tbody/tr/td/div")).getText();
        Assert.assertEquals(post_check, "No items");

        //logout
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(414, 736));
        driver.navigate().refresh();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("profile-dropdown")));

        driver.findElement(By.id("profile-dropdown")).click();
        driver.findElement(By.xpath("//*[@id=\"profile-dropdown-menu\"]/div[3]/form/button")).click();

        //String loginText = driver.findElement(By.className("section__title")).getText();
        Assert.assertTrue(loginText.toLowerCase().contains("login"));
        driver.manage().window().maximize();


        System.out.println("===> TEST 12: PASSED");
        System.out.println(" ");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
        service.stop();
    }
}