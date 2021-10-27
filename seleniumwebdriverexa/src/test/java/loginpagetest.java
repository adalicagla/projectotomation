
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;




public class loginpagetest {


    public static String loginUrl = "https://www.lcwaikiki.com/tr-TR/TR/giris";

    public  WebDriver driver;

    @Before
    public void setupDriver(){
        System.setProperty("webdriver.chrome.driver","C:\\driver\\chromedriver.exe");
        driver = new ChromeDriver();
        String url = "https://www.lcwaikiki.com/tr-TR/TR";
        driver.get(url);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }
    @Test
    public void TestSearch() {
        /* control of login */
        driver.get(loginUrl);
        driver.findElement(By.id("E-PostaAdresiniz")).sendKeys("admin");
        driver.findElement(By.id("Şifreniz")).sendKeys("123456");
        driver.findElement(By.id("GirisYap")).click();
        Assert.assertEquals(driver.getTitle(), "Home Page");

        /* Search of 'pantolon'  */
        WebElement searchBox = driver.findElement(By.id("Ürün, kategori veya marka ara"));
        searchBox.click();
        searchBox.sendKeys("pantolon");
        driver.findElement(By.className("ARA")).click();

        /* scroll down */
        JavascriptExecutor js = (JavascriptExecutor) driver;
        driver.get("https://www.lcwaikiki.com/tr-TR/TR/arama?q=pantolon");
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        /* click to "Daha fazla ürün gör" */
        driver.findElement(By.id("Daha fazla ürün gör")).click();

        /* pick a random product */
        driver.findElement(By.xpath(".//*[@id='p-2245505']/div[1]/a[1]")).click();
        WebElement price= driver.findElement(By.xpath(".//*[@class='newPrice']/ins[1]"));
        String priceText= price.getText();

        /* add the selected product to cart */
        WebElement bedenBox = driver.findElement(By.id("S"));
        bedenBox.click();

        WebElement sepet = driver.findElement(By.className("SEPETE EKLE"));
        sepet.click();
        driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
        driver.findElement(By.className("SEPETE EKLE")).click();

        /* compare the prices  */
        WebElement sepetfiyat= driver.findElement(By.className("fiyat"));
        String fiyat2= sepetfiyat.getText();
        if(priceText.compareTo(fiyat2)>0){
            /* increase the amount of product */
            WebElement sepetadet = driver.findElement(By.id("adet"));
            sepetadet.click();
            sepetadet.clear();
            sepetadet.sendKeys("1");
            driver.findElement(By.className("+")).click();
        }
        /* remove the char */
        driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
        driver.findElement(By.className("sil")).click();
    }

    @After
    public void quitDriver(){
        driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
        driver.quit();
    }
}
