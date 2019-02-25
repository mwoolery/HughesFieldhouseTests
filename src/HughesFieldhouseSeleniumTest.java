import static org.junit.Assert.*;

import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.junit.Test;
import com.mongodb.DB;
import com.mongodb.MongoClient;

import java.util.concurrent.TimeUnit;

class HughesFieldhouseSeleniumTest {

    public static void main(String args []){
        //setup
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");
        options.addArguments("window-size=1200x600");
        WebDriver browser;
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\mwoolery\\Desktop\\chromedriver.exe");
        browser = new ChromeDriver(options);
        MongoClientURI uri = new MongoClientURI(
                "mongodb://dbUser:HFHsport1@cluster0-shard-00-00-5irub.mongodb.net:27017,cluster0-shard-00-01-5irub.mongodb.net:27017,cluster0-shard-00-02-5irub.mongodb.net:27017/test?ssl=true&replicaSet=Cluster0-shard-0&authSource=admin&retryWrites=true");

        MongoClient mongoClient = new MongoClient(uri);
        MongoDatabase database = mongoClient.getDatabase("test");
        int count= (int) database.getCollection("banneritems").countDocuments();



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


        //db tests
        connected_to_db(database);
        //banneritem controller tests
        banneritem_create(database,browser,count);
        banneritem_details(database,browser,count);
        banneritem_edit(database,browser,count);
        banneritem_delete(database,browser,count);
        

        //close everything down
        mongoClient.close();
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
        String actualTitle = browser.getTitle();
        String expectedTitle = "Banner Items";
        assertEquals(expectedTitle,actualTitle);
        System.out.println("Login Page Loaded after login");

    }
    @Test
    public static void connected_to_db(MongoDatabase db) {

       // if we are able to call the db.getName method, the database instance was created properly
        String name = db.getName();
        assertEquals("test",name);
        System.out.println("database was connected");

    }
    @Test
    public static void banneritem_create(MongoDatabase db, WebDriver browser, int expectedID) {
        browser.get("http://localhost:3000/banneritem/create");
        String actualTitle = browser.getTitle();
        String expectedTitle = "Banner Item Create";
        assertEquals(expectedTitle,actualTitle);
        System.out.println(" banneritem/create found");


    }
    @Test
    public static void banneritem_details(MongoDatabase db, WebDriver browser, int id) {
        String idStr = String.valueOf(id);
        browser.get("http://localhost:3000/banneritem/details/" + idStr);
        String actualTitle = browser.getTitle();
        String expectedTitle = "Banner Item Details";
        assertEquals(expectedTitle,actualTitle);
        System.out.println(" banneritem/details found");


    }
    @Test
    public static void banneritem_edit(MongoDatabase db, WebDriver browser,  int id) {
        String idStr = String.valueOf(id);
        browser.get("http://localhost:3000/banneritem/edit/" + idStr);
        String actualTitle = browser.getTitle();
        String expectedTitle = "Banner Item Edit";
        assertEquals(expectedTitle,actualTitle);
        System.out.println(" banneritem/edit found");


    }
    @Test
    public static void banneritem_delete(MongoDatabase db, WebDriver browser, int id) {

        String idStr = String.valueOf(id);
        browser.get("http://localhost:3000/banneritem/delete/" + idStr);
        String actualTitle = browser.getTitle();
        String expectedTitle = "Banner Item Delete";
        assertEquals(expectedTitle,actualTitle);
        System.out.println(" banneritem/delete found");

    }


}