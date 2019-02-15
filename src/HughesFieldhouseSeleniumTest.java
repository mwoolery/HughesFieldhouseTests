import static org.junit.Assert.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

class HughesFieldhouseSeleniumTest {

    public static void main(String args []){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");
        options.addArguments("window-size=1200x600");
        WebDriver browser;
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\mwoolery\\Desktop\\chromedriver.exe");
        browser = new ChromeDriver(options);

        site_header_is_on_home_page(browser);
        login_to_admin_page(browser);
        browser.close();
    }

    @Test
    public static void site_header_is_on_home_page(WebDriver browser) {
        browser.get("http://localhost:3000/");
        WebElement story = browser.findElement(By.id("story"));
        assertTrue(story.isDisplayed());
        System.out.println(story + " - found");

    }
    @Test
    public static void login_to_admin_page(WebDriver browser) {
        browser.get("http://localhost:3000/login");
        browser.findElement(By.id("username")).sendKeys("admin");
        browser.findElement(By.id("password")).sendKeys("admin");
        browser.findElement(By.id("btn")).click();
        // after click it is going to login page, and we just check to see if it ended up loading
        JavascriptExecutor js = (JavascriptExecutor) browser;
        String pageLoadStatus = (String)js.executeScript("return document.readyState");
        assertTrue(pageLoadStatus.equals("complete"));
        System.out.println("Login Page Loaded after login");

    }

}