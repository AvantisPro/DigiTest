import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class Retailer {

    WebDriver driver;

    @BeforeClass
    public void init(){
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://dev.digisposa.com/auth/login");
    }

    @Test
    public void Test01_check_login_page(){
        System.out.println("===> TEST 01: CHECK LOGIN PAGE");
        String text = driver.findElement(By.className("section__title")).getText();
        Assert.assertTrue(text.toLowerCase().contains("login"));
        System.out.println("===> TEST 01: PASSED");
        System.out.println(" ");
    }

    @Test
    public void Test02_login(){
        System.out.println("===> TEST 02: CHECK LOGIN");
        WebDriverWait wait = new WebDriverWait(driver, 5);

        driver.findElement(By.id("loginform-email")).sendKeys("loon_test2@mailinator.com");
        driver.findElement(By.id("loginform-password")).sendKeys("12345678");
        WebElement element = driver.findElement(By.name("login-button"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        driver.findElement(By.name("login-button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("col-auto")));
        String text = driver.findElement(By.className("col-auto")).getText();
        Assert.assertTrue(text.toLowerCase().contains("dashboard"));
        System.out.println("===> TEST 02: PASSED");
        System.out.println(" ");
    }

    @Test
    public void Test03_write_message() throws InterruptedException {

        System.out.println("===> TEST 03: SEND MESSAGE");

        driver.get("https://dev.digisposa.com/message-center");
        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div/div[1]/div[1]/div[1]/div[1]/input")).sendKeys("Vade");

        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div/div[1]/div[2]/div[1]")).click();
        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div/div[2]/div/div[2]/div[2]/input")).sendKeys("test");
        Thread.sleep(1000);
        driver.findElement(By.className("fa-telegram-plane")).click();
        String text = driver.findElement(By.className("mc-message-text")).getAttribute("innerHTML");
        System.out.println(text);
        Assert.assertTrue(text.toLowerCase().contains("test"));

        System.out.println("===> TEST 03: SEND MESSAGE ===> PASSED");

    }

    @Test
    public void Test04_search_check() throws InterruptedException {

        System.out.println("===> TEST 04: CHECK SEARCH");

        driver.get("https://dev.digisposa.com/search");
        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div/div[1]/div/div[1]/div/div/input")).sendKeys("armani");
        Thread.sleep(4000);
        String name = driver.findElement(By.className("card-title")).getText();
        System.out.println(name);
        Assert.assertEquals(name, ("ARMANI CANADA, TORONTO"));

        System.out.println("===> TEST 04: PASSED");
        System.out.println(" ");
    }

    @Test
    public void Test05_add_brand_to_fav() throws InterruptedException {

        System.out.println("===> TEST 05: add_brand_to_fav");

        //add brand to fav
        driver.get("https://dev.digisposa.com/brand/20");
        Thread.sleep(1000);
        driver.findElement(By.className("my-icon-favorite")).click();

        //check in fav
        driver.get("https://dev.digisposa.com/favorite");

        String favBrand = driver.findElement(By.xpath("//*[@id=\"brands\"]/div/div/div/div/a/span/span/span[2]/h4")).getText();
        Assert.assertEquals(favBrand, "VERSACE");

        //delete from fav
        driver.get("https://dev.digisposa.com/brand/20");
        driver.findElement(By.className("my-icon-favoriteSolid")).click();
        driver.navigate().refresh();

        System.out.println("===> TEST 05: PASSED");
        System.out.println(" ");
    }

    @Test
    public void Test06_add_collection_to_fav() {

        System.out.println("===> TEST 06: add_collection_to_fav");

        //Add collection to fav
        driver.get("https://dev.digisposa.com/brand/20/collection/210");
        driver.findElement(By.className("my-icon-favorite")).click();
        String collName = driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div[1]/div[2]/div[1]/h1/span")).getText();

        //go to fav
        driver.get("https://dev.digisposa.com/favorite");
        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div[2]/ul/li[2]/a/span")).click();
        String favColl = driver.findElement(By.xpath("//*[@id=\"collections\"]/div/div/div/div/a/span/span/span[2]/h4")).getText();
        Assert.assertEquals(collName, favColl);

        driver.get("https://dev.digisposa.com/brand/20/collection/210");
        driver.findElement(By.className("my-icon-favoriteSolid")).click();

        System.out.println("===> TEST 06: PASSED");
        System.out.println(" ");

    }

    @Test
    public void Test07_add_dress_to_fav() {

        System.out.println("===> TEST 07: add_dress_to_fav");

        //add dress to fav
        driver.get("https://dev.digisposa.com/brand/20/collection/210/item/214");
        String nameD = driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div[2]/div/div[2]/div/div[1]/div[1]/h1")).getText();
        driver.findElement(By.className("my-icon-favorite")).click();

        //go to fav
        driver.get("https://dev.digisposa.com/favorite");
        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div[2]/ul/li[3]/a")).click();

        //check
        String nameInFav = driver.findElement(By.xpath("//*[@id=\"dresses\"]/div/div/div/div/a/span/span/span[2]/h4")).getText();
        Assert.assertEquals(nameD, nameInFav);

        //delete from fav
        driver.get("https://dev.digisposa.com/brand/20/collection/210/item/214");
        driver.findElement(By.className("my-icon-favoriteSolid")).click();

        System.out.println("===> TEST 07: PASSED");
        System.out.println(" ");

    }

    @Test
    public void Test08_create_post() throws InterruptedException {

        System.out.println("===> TEST 08: create_post");

        driver.get("https://dev.digisposa.com/learning-center");
        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div[1]/div[2]/a")).click();
        Thread.sleep(1000);
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
        Thread.sleep(3000);
        String title = driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div[2]/table/tbody/tr/td[2]/a")).getText();
        Assert.assertEquals(title, "Autotest");

        //delete post
        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div[2]/table/tbody/tr/td[7]")).click();
        driver.navigate().refresh();

        //check
        String empty = driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div[2]/table/tbody/tr/td/div")).getText();
        Assert.assertEquals(empty, "No items");

        System.out.println("===> TEST 08: PASSED");
        System.out.println(" ");
    }

    @Test
    public void Test09_check_newslatter() throws InterruptedException {

        System.out.println("===> TEST 09: check_newslatter");

        driver.get("https://dev.digisposa.com/message-center");
        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div/div[1]/div[1]/div[2]/a[2]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div/div[1]/div[2]/div[3]")).click();
        Thread.sleep(1000);
        String test_text = driver.findElement(By.className("font-14")).getText();

        Assert.assertEquals(test_text, "Autotest_newslatter");

        System.out.println("===> TEST 09: PASSED");
        System.out.println(" ");
    }



    @AfterClass
    public void end(){
        driver.quit();
    }
}
