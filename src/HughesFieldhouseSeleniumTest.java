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

        //controller routes test for main pages
        controller_get_index(browser);
        controller_get_login(browser);
        controller_get_awards(browser);
        controller_get_contact(browser);
        controller_get_events(browser);
        controller_get_lockers(browser);
        controller_get_meetingrooms(browser);
        //test login
        login_to_admin_page(browser);
        browser.close();
    }

    @Test
    public static void controller_get_index(WebDriver browser) {
        browser.get("http://localhost:3000/");
        String actualTitle = browser.getTitle();
        String expectedTitle = "Hughes Fieldhouse";
        assertEquals(expectedTitle,actualTitle);
        System.out.println(" index found");

    }
    @Test
    public static void controller_get_login(WebDriver browser) {
        browser.get("http://localhost:3000/login");
        String actualTitle = browser.getTitle();
        String expectedTitle = "Hughes Fieldhouse | Login";
        assertEquals(expectedTitle,actualTitle);
        System.out.println(" login found");

    }
    @Test
    public static void controller_get_awards(WebDriver browser) {
        browser.get("http://localhost:3000/awards");
        String actualTitle = browser.getTitle();
        String expectedTitle = "Hughes Fieldhouse | Awards";
        assertEquals(expectedTitle,actualTitle);
        System.out.println(" awards found");

    }
    @Test
    public static void controller_get_contact(WebDriver browser) {
        browser.get("http://localhost:3000/contact");
        String actualTitle = browser.getTitle();
        String expectedTitle = "Hughes Fieldhouse | Contact";
        assertEquals(expectedTitle,actualTitle);
        System.out.println(" contact found");

    }
    @Test
    public static void controller_get_events(WebDriver browser) {
        browser.get("http://localhost:3000/events");
        String actualTitle = browser.getTitle();
        String expectedTitle = "Hughes Fieldhouse | Events";
        assertEquals(expectedTitle,actualTitle);
        System.out.println(" events found");

    }
    @Test
    public static void controller_get_lockers(WebDriver browser) {
        browser.get("http://localhost:3000/locker");
        String actualTitle = browser.getTitle();
        String expectedTitle = "Hughes Fieldhouse | Lockers";
        assertEquals(expectedTitle,actualTitle);
        System.out.println(" lockers found");

    }
    @Test
    public static void controller_get_meetingrooms(WebDriver browser) {
        browser.get("http://localhost:3000/meetingRooms");
        String actualTitle = browser.getTitle();
        String expectedTitle = "Hughes Fieldhouse | Meeting Rooms";
        assertEquals(expectedTitle,actualTitle);
        System.out.println(" meeting rooms found");

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