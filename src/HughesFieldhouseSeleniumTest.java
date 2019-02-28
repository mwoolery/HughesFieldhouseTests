import static com.mongodb.client.model.Filters.eq;
import static org.junit.Assert.*;

import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.concurrent.TimeUnit;
import org.junit.Test;

import javax.print.Doc;
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
        banneritem_create(database,browser);
        BannerItemCreatePosted(database);
        banneritem_details(database,browser);
        banneritem_edit(database,browser);
        banneritem_delete(database,browser);
        BannerItemEditSuccess(database,browser);
        Delete_BannerItem_From_Database(database,browser);



        //close everything down for normal window
        mongoClient.close();
        browser.close();

        //Responsive Design Setup
        ChromeOptions mobile = new ChromeOptions();
        options.addArguments("headless");
        options.addArguments("window-size=360x500");
        WebDriver mobilebrowser;
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\mwoolery\\Desktop\\chromedriver.exe");
        mobilebrowser = new ChromeDriver(options);

        //Responsive Design Test
        mobile_design_displays(mobilebrowser);

        // close down the mobile browser
        mobilebrowser.close();
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
        try { Thread.sleep(1000); } catch(InterruptedException ex) { Thread.currentThread().interrupt(); }
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
    public static void banneritem_create(MongoDatabase db, WebDriver browser) {
        browser.get("http://localhost:3000/banneritem/create");
        //Make thread sleep to give javascript time to update page title
        try { Thread.sleep(1000); } catch(InterruptedException ex) { Thread.currentThread().interrupt(); }
        String actualTitle = browser.getTitle();
        String expectedTitle = "Banner Item Create";


        browser.findElement(By.id("Description")).sendKeys("TEST");
        browser.findElement(By.id("startDate")).sendKeys("02-20-2019");
        browser.findElement(By.id("startTime")).sendKeys("12:00 PM");
        browser.findElement(By.id("endDate")).sendKeys("02-21-2019");
        browser.findElement(By.id("endTime")).sendKeys("12:00 PM");
        browser.findElement(By.id("priority")).click();
        browser.findElement(By.id("link")).sendKeys("http://www.nwmissouri.edu");
        browser.findElement(By.id("btn")).click();

        assertEquals(expectedTitle,actualTitle);
        System.out.println(" banneritem/create found");
    }
    @Test
    public static void BannerItemCreatePosted(MongoDatabase db){
        String expectedJSON = "{ \"_id\" : 1, \"description\" : \"TEST\", \"startDate\" : \"02-20-2019\", \"endDate\" : \"02-21-2019\", \"startTime\" : \"12:00 PM\", \"endTime\" : \"12:00 PM\", \"priority\" : true, \"link\" : \"http://www.nwmissouri.edu\", \"__v\" : 0 }";
        assertEquals(expectedJSON, getFirstDBItem(db));
        System.out.println("Database properly inserted record");
    }
    @Test
    public static void banneritem_details(MongoDatabase db, WebDriver browser) {

        browser.get("http://localhost:3000/banneritem/details/1");
        browser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //Make thread sleep to give javascript time to update page title

        String actualTitle = browser.getTitle();
        String expectedTitle = "Banner Item Details";
        assertEquals(expectedTitle,actualTitle);
        System.out.println(" banneritem/details found");


    }
    @Test
    public static void banneritem_edit(MongoDatabase db, WebDriver browser) {
        browser.get("http://localhost:3000/banneritem/edit/1" );
        try { Thread.sleep(1000); } catch(InterruptedException ex) { Thread.currentThread().interrupt(); }
        String actualTitle = browser.getTitle();
        String expectedTitle = "Banner Item Edit";
        assertEquals(expectedTitle,actualTitle);
        System.out.println(" banneritem/edit found");


    }
    @Test
    public static void BannerItemEditSuccess(MongoDatabase db, WebDriver browser){
        browser.get("http://localhost:3000/banneritem/edit/1" );
        browser.findElement(By.id("Description")).sendKeys("TEST2");
        browser.findElement(By.id("startDate")).sendKeys("02-20-2018");
        browser.findElement(By.id("startTime")).sendKeys("12:00 AM");
        browser.findElement(By.id("endDate")).sendKeys("02-21-2018");
        browser.findElement(By.id("endTime")).sendKeys("12:00 AM");
        browser.findElement(By.id("priority")).click();
        browser.findElement(By.id("link")).sendKeys("http://www.google.com");
        browser.findElement(By.id("btn")).click();
        String expectedJSON = "{ \"_id\" : 1, \"description\" : \"TESTTEST2\", \"startDate\" : \"02-20-2019\", \"endDate\" : \"02-21-2019\", \"startTime\" : \"12:00 PM\", \"endTime\" : \"12:00 PM\", \"priority\" : false, \"link\" : \"http://www.nwmissouri.eduhttp://www.google.com\", \"__v\" : 0 }";
        assertEquals(expectedJSON, getFirstDBItem(db));
        System.out.println("Database properly Edited record");
    }
    @Test
    public static void banneritem_delete(MongoDatabase db, WebDriver browser) {


        browser.get("http://localhost:3000/banneritem/delete/1");
        try { Thread.sleep(1000); } catch(InterruptedException ex) { Thread.currentThread().interrupt(); }
        String actualTitle = browser.getTitle();
        String expectedTitle = "Banner Item Delete";
        assertEquals(expectedTitle,actualTitle);
        System.out.println(" banneritem/delete found");

    }
    @Test
    public static void Delete_BannerItem_From_Database(MongoDatabase db, WebDriver browser){

        browser.get("http://localhost:3000/banneritem/delete/1");
        browser.findElement(By.id("deletebtn")).click();
        try { Thread.sleep(1000); } catch(InterruptedException ex) { Thread.currentThread().interrupt(); }

        String expectedValue = "[]";
        String actualValue = "";

        try {
            MongoCollection<Document> myCollection = db.getCollection("banneritems");
            Document document = myCollection.find(eq("_id",1)).first();
            actualValue = document.toJson();
        }catch(NullPointerException ex){
            actualValue = "[]";
        }
        assertEquals(expectedValue, actualValue);
        System.out.println("Successful deletion of banneritem");
    }


    public static String getFirstDBItem(MongoDatabase db){

        MongoCollection<Document> myCollection = db.getCollection("banneritems");
        Document document = myCollection.find(eq("_id",1)).first();
// { "_id" : 1, "description" : "TEST", "startDate" : "02-20-2019", "endDate" : "02-21-2019", "startTime" : "12:00 PM", "endTime" : "12:00 PM", "priority" : true, "link" : "http://www.nwmissouri.edu", "__v" : 0 }
        String json = document.toJson();
        return json;
    }

    @Test
    public static void mobile_design_displays(WebDriver browser) {
        browser.get("http://localhost:3000/");
        Boolean actualVisibility = browser.findElement(By.id("mobileTestDiv")).isDisplayed();
        //should not be displayed on a mobile browser
        assertEquals(false,actualVisibility);
        System.out.println(" site did perform resizes for a mobile display");

    }
}