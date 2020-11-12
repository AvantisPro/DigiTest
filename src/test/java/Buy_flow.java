import java.io.File;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class Buy_flow {
    WebDriver driver = null;


    ChromeDriverService service = new ChromeDriverService.Builder().usingPort(8082).
            usingDriverExecutable(new File("/usr/bin/chromedriver"))
            //.withWhitelistedIps("")
            //.withVerbose(true)
            .build();


    String priceForDress = "5000";
    String priceForDressWithDelivery = "5150";


    @BeforeClass
    public void init() throws IOException {
        service.start();
        driver = new RemoteWebDriver(service.getUrl(), new ChromeOptions());
        driver.manage().window().maximize();
        driver.get("https://dev.digisposa.com/auth/login");
    }

    @Test
    public void Test01_login_as_Retailer_and_buy_dress() throws InterruptedException {

        System.out.println(" ");
        System.out.println("======================================");
        System.out.println("===> BUY FLOW TESTS <===");
        System.out.println("======================================");
        System.out.println(" ");

        System.out.println("===> TEST 01: LOGIN AS RETAILER AND BUY DRESS");

        WebDriverWait wait = new WebDriverWait(driver, 60);

        //Login as Retailer H&M
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginform-email")));
        driver.findElement(By.id("loginform-email")).sendKeys("loon_test2@mailinator.com");
        driver.findElement(By.id("loginform-password")).sendKeys("12345678");
        WebElement element = driver.findElement(By.name("login-button"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        driver.findElement(By.name("login-button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("col-auto")));
        String text = driver.findElement(By.className("col-auto")).getText();
        Assert.assertTrue(text.toLowerCase().contains("dashboard"));

        driver.get("https://dev.digisposa.com/brand/20/collection/210/item/214");

        //Add dress to cart
        String priceInCard = driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div[2]/div/div[2]/div/div[2]/div[2]/div[3]/div/div[1]/p[2]")).getText();
        priceInCard = priceInCard.replaceAll("[^0-9]","");

        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div[2]/div/div[2]/div/div[2]/div[2]/div[3]/div/div[2]/div/div[2]/button")).click();
        driver.get("https://dev.digisposa.com/cart");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div/div[2]/div/div/div[3]/div/div[2]/div[2]")));
        //Check price and make order
        String priceInCart = driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div/div[2]/div/div/div[3]/div/div[2]/div[2]")).getText();
        priceInCart = priceInCart.replaceAll("[^0-9]","");

        //get order number
        String orderNumb = driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div/div[1]/div/div/table/tbody/tr[1]/td[2]")).getText();
        orderNumb = orderNumb.replaceAll("[^0-9]","");

        Assert.assertEquals(priceInCart, priceForDress);

        Assert.assertEquals(priceInCard, priceForDress);


        driver.findElement(By.id("delivery_date")).click();
        driver.findElement(By.id("__BVID__29__cell-2020-11-30_")).click();
        driver.findElement(By.id("delivery_date")).click();

        driver.findElement(By.className("w-100")).click();
        Thread.sleep(5000);
        driver.navigate().refresh();

        String placetext = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div/div/h3"))).getText();
        Assert.assertEquals(placetext, "YOU HAVE NO ITEMS IN YOUR SHOPPING CART.");


        //Go to order management and check order number and price
        driver.get("https://dev.digisposa.com/order");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("card-title")));
        String lastOrdNum = driver.findElement(By.className("card-title")).getText();


        lastOrdNum = lastOrdNum.replaceAll("[^0-9]", "");

        System.out.println("Order number " + orderNumb + " " + lastOrdNum);


        driver.findElement(By.className("logo")).click();
        driver.navigate().refresh();

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

        System.out.println("===> TEST 01: PASSED");
        System.out.println(" ");
    }

    @Test
    public void Test02_login_as_Brand_and_accept_order() throws InterruptedException {

        System.out.println("===> TEST 02: LOGIN AS BRAND AND ACCEPT ORDER");

        WebDriverWait wait = new WebDriverWait(driver, 60);

        //Login as Brand "Rock"
        driver.get("https://dev.digisposa.com/auth/login");
        driver.navigate().refresh();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("LoginForm[email]")));
        driver.findElement(By.name("LoginForm[email]")).sendKeys("loon_vader@mailinator.com");
        driver.findElement(By.name("LoginForm[password]")).sendKeys("12345678");
        WebElement element1 = driver.findElement(By.name("login-button"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element1);
        driver.findElement(By.name("login-button")).click();
        element1.click();
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"login-form\"]/div[4]/div[1]/button")));
        //driver.findElement(By.xpath("//*[@id=\"login-form\"]/div[4]/div[1]/button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("col-auto")));
        String text = driver.findElement(By.className("col-auto")).getText();
        Assert.assertTrue(text.toLowerCase().contains("dashboard"));

        //Create order
        driver.get("https://dev.digisposa.com/order");

        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(414, 736));
        driver.navigate().refresh();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("card-img-top")));

        WebElement card = driver.findElement(By.className("card-img-top"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", card);

        //Check price of dress
        String priceInCard = driver.findElement(By.xpath("//*[@id=\"orders\"]/div/div[2]/div/div/div/div[1]/div/div[1]/div/div/div[1]/p[3]/span[1]")).getText();
        priceInCard = priceInCard.replaceAll("[^0-9]","");
        Assert.assertEquals(priceInCard, priceForDress);


        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("card-img-top")));
        card.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("action-confirm-order")));
        driver.findElement(By.className("action-confirm-order")).click();

        driver.findElement(By.id("delivery_company")).sendKeys("test");

        Select drpPrice = new Select(driver.findElement(By.className("custom-select")));
        drpPrice.selectByValue("150");

        driver.findElement(By.className("col-sm-8")).click();

        Thread.sleep(3000);
        driver.navigate().refresh();

        //logout
        driver.get("https://dev.digisposa.com/dashboard");

        driver.findElement(By.id("profile-dropdown")).click();
        driver.findElement(By.className("text-center")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("section__title")));
        String loginText = driver.findElement(By.className("section__title")).getText();
        Assert.assertTrue(loginText.toLowerCase().contains("login"));
        driver.manage().window().maximize();

        System.out.println("===> TEST 02: PASSED");
        System.out.println(" ");
    }

    @Test
    public void Test03_login_as_Retailer_and_accept_order() throws InterruptedException {

        System.out.println("===> TEST 03: LOGIN AS RETAILER AND CONFIRM ORDER");

        WebDriverWait wait = new WebDriverWait(driver, 60);

        //enter as Margot

        driver.get("https://dev.digisposa.com/auth/login");
        driver.navigate().refresh();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginform-email")));
        driver.findElement(By.id("loginform-email")).sendKeys("loon_test2@mailinator.com");
        driver.findElement(By.id("loginform-password")).sendKeys("12345678");
        WebElement element2 = driver.findElement(By.name("login-button"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element2);
        driver.findElement(By.name("login-button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("col-auto")));

        //confirm order
        driver.get("https://dev.digisposa.com/order");
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(414, 736));
        driver.navigate().refresh();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("card-img-top")));

        WebElement card1 = driver.findElement(By.className("card-img-top"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", card1);

        //check price for dress
        String priceInCardWithDelivery = driver.findElement(By.xpath("//*[@id=\"orders\"]/div/div[2]/div/div[1]/div/div/div/div[1]/p[3]/span[1]")).getText();
        priceInCardWithDelivery = priceInCardWithDelivery.replaceAll("[^0-9]","");
        Assert.assertEquals(priceInCardWithDelivery, priceForDressWithDelivery);

        card1.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("action-accept")));
        driver.findElement(By.className("action-accept")).click();
        Thread.sleep(3000);

        //logout
        driver.get("https://dev.digisposa.com/dashboard");

        driver.findElement(By.id("profile-dropdown")).click();
        driver.findElement(By.xpath("//*[@id=\"profile-dropdown-menu\"]/div[3]/form/button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("section__title")));
        String loginText = driver.findElement(By.className("section__title")).getText();
        Assert.assertTrue(loginText.toLowerCase().contains("login"));
        driver.manage().window().maximize();

        System.out.println("===> TEST 03: PASSED");
        System.out.println(" ");
    }

    @Test
    public void Test04_login_as_Brand_and_create_invoice() throws InterruptedException {

        System.out.println("===> TEST 04: LOGIN AS BRAND AND CREATE INVOICE");

        WebDriverWait wait = new WebDriverWait(driver, 60);

        //enter
        driver.get("https://dev.digisposa.com/auth/login");
        driver.navigate().refresh();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("LoginForm[email]")));
        driver.findElement(By.name("LoginForm[email]")).sendKeys("loon_vader@mailinator.com");
        driver.findElement(By.id("loginform-password")).sendKeys("12345678");
        WebElement element2 = driver.findElement(By.name("login-button"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element2);
        driver.findElement(By.name("login-button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("col-auto")));

        driver.get("https://dev.digisposa.com/finances");
        Thread.sleep(5000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("create-new-btn")));

//        driver.manage().window().setPosition(new Point(0, 0));
//        driver.manage().window().setSize(new Dimension(414, 736));
//        driver.navigate().refresh();

        driver.findElement(By.className("create-new-btn")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("dropzone-caption")));
        driver.findElement(By.className("dropzone-caption"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("card-img-top")));
        WebElement lastCard = driver.findElement(By.className("card-img-top"));

        //check price for dress
        String priceInCardWithDelivery = driver.findElement(By.xpath("//*[@id=\"invoices\"]/div/div[2]/div/div[1]/div/div/div/div/div[1]/div[1]/p[3]/span[1]")).getText();
        priceInCardWithDelivery = priceInCardWithDelivery.replaceAll("[^0-9]","");
        Assert.assertEquals(priceInCardWithDelivery, priceForDressWithDelivery);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("dropzone-caption")));
        WebElement To = driver.findElement(By.className("dropzone-caption"));

        final String java_script =
                "var src=arguments[0],tgt=arguments[1];var dataTransfer={dropEffe" +
                        "ct:'',effectAllowed:'all',files:[],items:{},types:[],setData:fun" +
                        "ction(format,data){this.items[format]=data;this.types.append(for" +
                        "mat);},getData:function(format){return this.items[format];},clea" +
                        "rData:function(format){}};var emit=function(event,target){var ev" +
                        "t=document.createEvent('Event');evt.initEvent(event,true,false);" +
                        "evt.dataTransfer=dataTransfer;target.dispatchEvent(evt);};emit('" +
                        "dragstart',src);emit('dragenter',tgt);emit('dragover',tgt);emit(" +
                        "'drop',tgt);emit('dragend',src);";

        ((JavascriptExecutor)driver).executeScript(java_script, lastCard, To);

        List<WebElement> btns = driver.findElements(By.className("styled-btn"));
        WebElement rightBtn = btns.get(btns.size() - 1);
        rightBtn.click();

        driver.findElement(By.className("col-sm-8")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("form-group")));
        List<WebElement> rb = driver.findElements(By.className("form-group"));
        WebElement rb1 = rb.get(1);
        rb1.click();

        driver.findElement(By.className("col-sm-7")).click();
        Thread.sleep(3000);
        driver.get("https://dev.digisposa.com/dashboard");
        driver.navigate().refresh();


        //logout
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(414, 736));
        driver.navigate().refresh();

        driver.findElement(By.id("profile-dropdown")).click();
        driver.findElement(By.xpath("//*[@id=\"profile-dropdown-menu\"]/div[3]/form/button")).click();
        Thread.sleep(3000);
        String loginText = driver.findElement(By.className("section__title")).getText();
        Assert.assertTrue(loginText.toLowerCase().contains("login"));
        driver.manage().window().maximize();

        System.out.println("===> TEST 04: PASSED");
        System.out.println(" ");
    }

    @Test
    public void Test05_login_as_Retailer_mark_invoice_as_paid() throws InterruptedException {

        System.out.println("===> TEST 05: LOGIN AS RETAILER AND MARK INVOICE AS PAID");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        //enter as Margot

        driver.get("https://dev.digisposa.com/auth/login");
        driver.navigate().refresh();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginform-email")));
        driver.findElement(By.id("loginform-email")).sendKeys("loon_test2@mailinator.com");
        driver.findElement(By.id("loginform-password")).sendKeys("12345678");
        WebElement element2 = driver.findElement(By.name("login-button"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element2);
        driver.findElement(By.name("login-button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("col-auto")));

        driver.get("https://dev.digisposa.com/finances");

        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(414, 736));
        driver.navigate().refresh();
        Thread.sleep(2000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("card-img-top")));

        WebElement card = driver.findElement(By.className("card-img-top"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", card);

        //check price for dress
        String priceInCardWithDelivery = driver.findElement(By.xpath("//*[@id=\"invoices\"]/div/div[2]/div/div/div/div[1]/div/div/div/div[1]/p[4]/span[1]")).getText();
        priceInCardWithDelivery = priceInCardWithDelivery.replaceAll("[^0-9]","");
        Assert.assertEquals(priceInCardWithDelivery, priceForDressWithDelivery);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("card-img-top")));
        card.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("action-pay")));
        driver.findElement(By.className("action-pay")).click();
        Thread.sleep(5000);

        //logout
        driver.get("https://dev.digisposa.com/dashboard");

        driver.findElement(By.id("profile-dropdown")).click();
        driver.findElement(By.xpath("//*[@id=\"profile-dropdown-menu\"]/div[3]/form/button")).click();
        Thread.sleep(3000);
        String loginText = driver.findElement(By.className("section__title")).getText();
        Assert.assertTrue(loginText.toLowerCase().contains("login"));
        driver.manage().window().maximize();

        System.out.println("===> TEST 05: PASSED");
        System.out.println(" ");

    }

    @Test
    public void Test06_reject_order() throws InterruptedException {

        System.out.println("===> TEST 06: REJECT ORDER");

        WebDriverWait wait = new WebDriverWait(driver, 60);

        //Login as Retailer H&M
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginform-email")));
        driver.findElement(By.id("loginform-email")).sendKeys("loon_test2@mailinator.com");
        driver.findElement(By.id("loginform-password")).sendKeys("12345678");
        WebElement element = driver.findElement(By.name("login-button"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        driver.findElement(By.name("login-button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("col-auto")));
        String text = driver.findElement(By.className("col-auto")).getText();
        Assert.assertTrue(text.toLowerCase().contains("dashboard"));

        driver.get("https://dev.digisposa.com/brand/20/collection/210/item/214");

        //Add dress to cart
        String priceInCard = driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div[2]/div/div[2]/div/div[2]/div[2]/div[3]/div/div[1]/p[2]")).getText();
        priceInCard = priceInCard.replaceAll("[^0-9]","");

        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div[2]/div/div[2]/div/div[2]/div[2]/div[3]/div/div[2]/div/div[2]/button")).click();
        driver.get("https://dev.digisposa.com/cart");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div/div[2]/div/div/div[3]/div/div[2]/div[2]")));
        //Check price and make order
        String priceInCart = driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div/div[2]/div/div/div[3]/div/div[2]/div[2]")).getText();
        priceInCart = priceInCart.replaceAll("[^0-9]","");

        //get order number
        String orderNumb = driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div/div[1]/div/div/table/tbody/tr[1]/td[2]")).getText();
        orderNumb = orderNumb.replaceAll("[^0-9]","");

        Assert.assertEquals(priceInCart, priceForDress);

        Assert.assertEquals(priceInCard, priceForDress);


        driver.findElement(By.id("delivery_date")).click();
        driver.findElement(By.id("__BVID__29__cell-2020-11-30_")).click();
        driver.findElement(By.id("delivery_date")).click();

        driver.findElement(By.className("w-100")).click();
        Thread.sleep(5000);
        driver.navigate().refresh();

        String placetext = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div/div/h3"))).getText();
        Assert.assertEquals(placetext, "YOU HAVE NO ITEMS IN YOUR SHOPPING CART.");


        //Go to order management and check order number and price
        driver.get("https://dev.digisposa.com/order");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("card-title")));
        String lastOrdNum = driver.findElement(By.className("card-title")).getText();


        lastOrdNum = lastOrdNum.replaceAll("[^0-9]", "");

        System.out.println("Order number " + orderNumb + " " + lastOrdNum);


        driver.findElement(By.className("logo")).click();
        driver.navigate().refresh();

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

        //Login as Brand "Rock"
        driver.get("https://dev.digisposa.com/auth/login");
        driver.navigate().refresh();
        driver.findElement(By.name("LoginForm[email]")).sendKeys("loon_vader@mailinator.com");
        driver.findElement(By.name("LoginForm[password]")).sendKeys("12345678");
        WebElement element1 = driver.findElement(By.name("login-button"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element1);
        driver.findElement(By.name("login-button")).click();
        element1.click();
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"login-form\"]/div[4]/div[1]/button")));
        //driver.findElement(By.xpath("//*[@id=\"login-form\"]/div[4]/div[1]/button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("col-auto")));
        String text2 = driver.findElement(By.className("col-auto")).getText();
        Assert.assertTrue(text2.toLowerCase().contains("dashboard"));

        //Create order
        driver.get("https://dev.digisposa.com/order");

        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(414, 736));
        driver.navigate().refresh();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("card-img-top")));

        WebElement card = driver.findElement(By.className("card-img-top"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", card);

        //Check price of dress
        String priceInCard2 = driver.findElement(By.xpath("//*[@id=\"orders\"]/div/div[2]/div/div/div/div[1]/div/div[1]/div/div/div[1]/p[3]/span[1]")).getText();
        priceInCard2 = priceInCard2.replaceAll("[^0-9]","");
        Assert.assertEquals(priceInCard2, priceForDress);

        //order number
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"orders\"]/div/div[2]/div/div/div/div[1]/div/div/div/div/div[1]/h4")));
        String order_numb = driver.findElement(By.xpath("//*[@id=\"orders\"]/div/div[2]/div/div/div/div[1]/div/div/div/div/div[1]/h4")).getText();

        //reject
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("card-img-top")));
        card.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("action-confirm-order")));
        driver.findElement(By.className("action-reject-order")).click();

        //confirm reject
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("comment")));
        driver.findElement(By.id("comment")).sendKeys("reject test");
        driver.findElement(By.xpath("//*[@id=\"__BVID__18___BV_modal_body_\"]/div/div[2]/div/div[1]/button")).click();

        //check
        String rejected_order = driver.findElement(By.xpath("//*[@id=\"orders\"]/div/div[2]/div/div/div/div/div/div[1]/div/div/div[1]/h4")).getText();
        Assert.assertEquals(rejected_order, order_numb);
        Assert.assertEquals(rejected_order, "#" + orderNumb);

        //logout
        driver.get("https://dev.digisposa.com/dashboard");

        driver.findElement(By.id("profile-dropdown")).click();
        driver.findElement(By.xpath("//*[@id=\"profile-dropdown-menu\"]/div[3]/form/button")).click();
        Thread.sleep(3000);
        String loginText2 = driver.findElement(By.className("section__title")).getText();
        Assert.assertTrue(loginText2.toLowerCase().contains("login"));
        driver.manage().window().maximize();

        System.out.println("===> TEST 06: PASSED");
        System.out.println(" ");

    }

    @Test
    public void Test07_change_address_in_order_and_decline_it_as_retailer() throws InterruptedException {
        System.out.println("===> TEST 07: CHANGE ADDRESS IN ORDER AS BRAND AND DECLINE ORDER AS RETAILER");

        WebDriverWait wait = new WebDriverWait(driver, 60);

        //Login as Retailer H&M
        driver.findElement(By.id("loginform-email")).sendKeys("loon_test2@mailinator.com");
        driver.findElement(By.id("loginform-password")).sendKeys("12345678");
        WebElement element = driver.findElement(By.name("login-button"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        driver.findElement(By.name("login-button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("col-auto")));
        String text = driver.findElement(By.className("col-auto")).getText();
        Assert.assertTrue(text.toLowerCase().contains("dashboard"));

        driver.get("https://dev.digisposa.com/brand/20/collection/210/item/214");

        //Add dress to cart
        String priceInCard = driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div[2]/div/div[2]/div/div[2]/div[2]/div[3]/div/div[1]/p[2]")).getText();
        priceInCard = priceInCard.replaceAll("[^0-9]","");

        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div[2]/div/div[2]/div/div[2]/div[2]/div[3]/div/div[2]/div/div[2]/button")).click();
        driver.get("https://dev.digisposa.com/cart");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div/div[2]/div/div/div[3]/div/div[2]/div[2]")));
        //Check price and make order
        String priceInCart = driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div/div[2]/div/div/div[3]/div/div[2]/div[2]")).getText();
        priceInCart = priceInCart.replaceAll("[^0-9]","");

        //get order number
        String orderNumb = driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div/div[1]/div/div/table/tbody/tr[1]/td[2]")).getText();
        orderNumb = orderNumb.replaceAll("[^0-9]","");

        Assert.assertEquals(priceInCart, priceForDress);

        Assert.assertEquals(priceInCard, priceForDress);


        driver.findElement(By.id("delivery_date")).click();
        driver.findElement(By.id("__BVID__29__cell-2020-11-30_")).click();
        driver.findElement(By.id("delivery_date")).click();

        driver.findElement(By.className("w-100")).click();
        Thread.sleep(5000);
        driver.navigate().refresh();

        String placetext = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div/div/div/h3"))).getText();
        Assert.assertEquals(placetext, "YOU HAVE NO ITEMS IN YOUR SHOPPING CART.");


        //Go to order management and check order number and price
        driver.get("https://dev.digisposa.com/order");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("card-title")));
        String lastOrdNum = driver.findElement(By.className("card-title")).getText();


        lastOrdNum = lastOrdNum.replaceAll("[^0-9]", "");

        System.out.println("Order number " + orderNumb + " " + lastOrdNum);


        driver.findElement(By.className("logo")).click();
        driver.navigate().refresh();

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

        //Login as Brand "Rock"
        driver.get("https://dev.digisposa.com/auth/login");
        driver.navigate().refresh();
        driver.findElement(By.name("LoginForm[email]")).sendKeys("loon_vader@mailinator.com");
        driver.findElement(By.name("LoginForm[password]")).sendKeys("12345678");
        WebElement element1 = driver.findElement(By.name("login-button"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element1);
        driver.findElement(By.name("login-button")).click();
        element1.click();
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"login-form\"]/div[4]/div[1]/button")));
        //driver.findElement(By.xpath("//*[@id=\"login-form\"]/div[4]/div[1]/button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("col-auto")));
        String text2 = driver.findElement(By.className("col-auto")).getText();
        Assert.assertTrue(text2.toLowerCase().contains("dashboard"));

        //Create order
        driver.get("https://dev.digisposa.com/order");

        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(414, 736));
        driver.navigate().refresh();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("card-img-top")));

        WebElement card = driver.findElement(By.className("card-img-top"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", card);

        //номер с нолями
        String dress_order_number = driver.findElement(By.xpath("//*[@id=\"orders\"]/div/div[2]/div/div/div/div[1]/div/div/div/div/div[1]/h4")).getText();

        //Check price of dress
        String priceInCard2 = driver.findElement(By.xpath("//*[@id=\"orders\"]/div/div[2]/div/div/div/div[1]/div/div[1]/div/div/div[1]/p[3]/span[1]")).getText();
        priceInCard2 = priceInCard2.replaceAll("[^0-9]","");
        Assert.assertEquals(priceInCard2, priceForDress);


        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("card-img-top")));
        card.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("action-confirm-order")));
        driver.findElement(By.className("action-confirm-order")).click();

        driver.findElement(By.id("delivery_address")).clear();
        driver.findElement(By.id("delivery_address")).sendKeys("New address test");

        driver.findElement(By.id("delivery_company")).sendKeys("test");

        Select drpPrice = new Select(driver.findElement(By.className("custom-select")));
        drpPrice.selectByValue("150");

        driver.findElement(By.className("col-sm-8")).click();

        Thread.sleep(3000);
        driver.navigate().refresh();

        //logout
        driver.get("https://dev.digisposa.com/dashboard");

        driver.findElement(By.id("profile-dropdown")).click();
        driver.findElement(By.className("text-center")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("section__title")));
        String loginText2 = driver.findElement(By.className("section__title")).getText();
        Assert.assertTrue(loginText2.toLowerCase().contains("login"));
        driver.manage().window().maximize();

        //login as retail
        driver.get("https://dev.digisposa.com/auth/login");
        driver.navigate().refresh();
        driver.findElement(By.id("loginform-email")).sendKeys("loon_test2@mailinator.com");
        driver.findElement(By.id("loginform-password")).sendKeys("12345678");
        WebElement element2 = driver.findElement(By.name("login-button"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element2);
        driver.findElement(By.name("login-button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("col-auto")));

        //confirm order
        driver.get("https://dev.digisposa.com/order");
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(414, 736));
        driver.navigate().refresh();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("card-img-top")));

        WebElement card1 = driver.findElement(By.className("card-img-top"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", card1);

        //check price for dress
        String priceInCardWithDelivery = driver.findElement(By.xpath("//*[@id=\"orders\"]/div/div[2]/div/div[1]/div/div/div/div[1]/p[3]/span[1]")).getText();
        priceInCardWithDelivery = priceInCardWithDelivery.replaceAll("[^0-9]","");
        Assert.assertEquals(priceInCardWithDelivery, priceForDressWithDelivery);

        card1.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("action-details")));
        driver.findElement(By.className("action-details")).click();
        Thread.sleep(3000);

        List<WebElement> lines = driver.findElements(By.className("col-8"));
        WebElement address_line = lines.get(7);
        String new_address = address_line.getText();

        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"ocd-258\"]/div/div[2]/div/div/div/div[2]/div[2]/ul/li[3]/span[2]")));
        //String new_address = driver.findElement(By.xpath("//*[@id=\"ocd-258\"]/div/div[2]/div/div/div/div[2]/div[2]/ul/li[3]/span[2]")).getText();
        Assert.assertEquals(new_address, "New address test");

        card1.click();
        Thread.sleep(3000);
        driver.findElement(By.className("action-decline")).click();

        driver.findElement(By.id("comment")).sendKeys("just testing");
        driver.findElement(By.xpath("//*[@id=\"__BVID__19___BV_modal_body_\"]/div/div[2]/div/div[1]/button")).click();
        Thread.sleep(5000);
        driver.manage().window().maximize();
        driver.navigate().refresh();

        //check declined order
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div[1]/div/div/ul/li[2]/a/span")));
        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div/div/div/div[1]/div/div/ul/li[2]/a/span")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"history\"]/div/div[2]/div/div/div[1]/div/div/div[1]/h4")));
        String declined_order2 = driver.findElement(By.xpath("//*[@id=\"history\"]/div/div[2]/div/div/div[1]/div/div/div[1]/h4")).getText();
        Assert.assertEquals(declined_order2, dress_order_number);

        //logout
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(414, 736));
        driver.navigate().refresh();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("profile-dropdown")));

        driver.findElement(By.id("profile-dropdown")).click();
        driver.findElement(By.xpath("//*[@id=\"profile-dropdown-menu\"]/div[3]/form/button")).click();

        String loginText3 = driver.findElement(By.className("section__title")).getText();
        Assert.assertTrue(loginText3.toLowerCase().contains("login"));
        driver.manage().window().maximize();

        //Login as Brand "Rock"
        driver.get("https://dev.digisposa.com/auth/login");
        //login
        driver.findElement(By.id("loginform-email")).sendKeys("loon_vader@mailinator.com");
        driver.findElement(By.id("loginform-password")).sendKeys("12345678");
        WebElement element4 = driver.findElement(By.name("login-button"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element4);
        driver.findElement(By.name("login-button")).click();

        //check if we on loggedin
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("col-auto")));
        String text4 = driver.findElement(By.className("col-auto")).getText();
        Assert.assertTrue(text4.toLowerCase().contains("dashboard"));

        //check declined order
        driver.get("https://dev.digisposa.com/order#history");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"history\"]/div/div[2]/div/div/div[1]/div/div/div[1]/h4")));
        String declined_order_as_brand = driver.findElement(By.xpath("//*[@id=\"history\"]/div/div[2]/div/div/div[1]/div/div/div[1]/h4")).getText();
        Assert.assertEquals(declined_order_as_brand, declined_order2);

        //logout
        driver.get("https://dev.digisposa.com/dashboard");

        driver.findElement(By.id("profile-dropdown")).click();
        driver.findElement(By.className("text-center")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("section__title")));
        String loginText4 = driver.findElement(By.className("section__title")).getText();
        Assert.assertTrue(loginText4.toLowerCase().contains("login"));
        driver.manage().window().maximize();

        System.out.println("===> TEST 07: PASSED");
        System.out.println(" ");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
        service.stop();
    }
}