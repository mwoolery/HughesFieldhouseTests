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
        //Selenium chrome browser setup
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");
        options.addArguments("window-size=1200x600");
        WebDriver browser;
        // set the chrome driver, change the File path to the location that you have your chrome driver stored
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\mwoolery\\Desktop\\chromedriver.exe");
        browser = new ChromeDriver(options);
        //set up the mongodb connection
        MongoClientURI uri = new MongoClientURI(
                "mongodb://dbUser:HFHsport1@cluster0-shard-00-00-5irub.mongodb.net:27017,cluster0-shard-00-01-5irub.mongodb.net:27017,cluster0-shard-00-02-5irub.mongodb.net:27017/test?ssl=true&replicaSet=Cluster0-shard-0&authSource=admin&retryWrites=true");

        MongoClient mongoClient = new MongoClient(uri);
        // get the test database, it is the default database for Atlas.
        MongoDatabase database = mongoClient.getDatabase("test");




        //controller routes test for main pages, app should be open locally for this to work
        controller_get_index(browser);
        controller_get_login(browser);
        controller_get_awards(browser);
        controller_get_contact(browser);
        controller_get_events(browser);
        controller_get_lockers(browser);
        controller_get_meetingrooms(browser);

        //test login to banner item
        login_to_admin_page(browser);


        //db tests
        connected_to_db(database);

        //banneritem controller tests
        banneritem_create(database,browser);
        // test creating a new banner item
        BannerItemCreatePosted(database);
        banneritem_details(database,browser);
        banneritem_edit(database,browser);
        banneritem_delete(database,browser);
        //test that editing worked as expected on the banner item
        BannerItemEditSuccess(database,browser);
        // delete the test item and test that it was removed
        Delete_BannerItem_From_Database(database,browser);



        //close everything down for normal window size
        // close mongo db connection since we are done with it at this point
        mongoClient.close();
        browser.close();

        //Responsive Design Setup
        ChromeOptions mobile = new ChromeOptions();
        options.addArguments("headless");
        options.addArguments("window-size=360x500");
        WebDriver mobilebrowser;
       // System.setProperty("webdriver.chrome.driver", "C:\\Users\\mwoolery\\Desktop\\chromedriver.exe");
        mobilebrowser = new ChromeDriver(options);

        //Responsive Design Test, see if an element was removed from the DOM on a resize to
        // verify that the css responded to the mobile size
        mobile_design_displays(mobilebrowser);

        // close down the mobile browser
        mobilebrowser.close();
        // Done
    }

    // Test index controller, get the index page with the / as the controller method should allow, should return index
    @Test
    public static void controller_get_index(WebDriver browser) {
        browser.get("http://localhost:3000/");
        // get browser title that is set by script on a succesfull load
        String actualTitle = browser.getTitle();
        // should equal this
        String expectedTitle = "Hughes Fieldhouse";
        assertEquals(expectedTitle,actualTitle);
        // prints on success
        System.out.println(" index found");

    }
    // test the login controller function, by providing /login, the login page should be returned
    @Test
    public static void controller_get_login(WebDriver browser) {
        browser.get("http://localhost:3000/login");
        // get the title of the page that was set by the script on successful load
        String actualTitle = browser.getTitle();
        // wait a couple seconds since selenium sometimes is slow on this page
        try { Thread.sleep(2000); } catch(InterruptedException ex) { Thread.currentThread().interrupt(); }
        // should equal this
        String expectedTitle = "Hughes Fieldhouse | Login";
        assertEquals(expectedTitle,actualTitle);
        // print if successful
        System.out.println(" login found");

    }
    // test controller for awards page, should return awards if /awards is provided to browser
    @Test
    public static void controller_get_awards(WebDriver browser) {
        browser.get("http://localhost:3000/awards");
        // get the title of page set by script on successful load
        String actualTitle = browser.getTitle();
        // title should equal this
        String expectedTitle = "Hughes Fieldhouse | Awards";
        assertEquals(expectedTitle,actualTitle);
        // print if successful
        System.out.println(" awards found");

    }
    // get contact page using controller method with /contact provided
    @Test
    public static void controller_get_contact(WebDriver browser) {
        browser.get("http://localhost:3000/contact");
        // get the title set by the script on successful load
        String actualTitle = browser.getTitle();
        // it should equal this
        String expectedTitle = "Hughes Fieldhouse | Contact";
        assertEquals(expectedTitle,actualTitle);
        // print if successful
        System.out.println(" contact found");

    }
    // test the controller get the events page when provided /events
    @Test
    public static void controller_get_events(WebDriver browser) {
        browser.get("http://localhost:3000/events");
        // get the title set by the script on successful load
        String actualTitle = browser.getTitle();
        // should equal this
        String expectedTitle = "Hughes Fieldhouse | Events";
        assertEquals(expectedTitle,actualTitle);
        // print if successful
        System.out.println(" events found");

    }
    // test the controller get the lockers page when provided /locker
    @Test
    public static void controller_get_lockers(WebDriver browser) {
        browser.get("http://localhost:3000/locker");
        // get the title set by the script on successful load
        String actualTitle = browser.getTitle();
        // should equal this title
        String expectedTitle = "Hughes Fieldhouse | Lockers";
        assertEquals(expectedTitle,actualTitle);
        // print if successful
        System.out.println(" lockers found");

    }
    // test the controller get method of /meetingRooms page, should return the meeting room page
    @Test
    public static void controller_get_meetingrooms(WebDriver browser) {
        browser.get("http://localhost:3000/meetingRooms");
        // get the title set by the script on successful load
        String actualTitle = browser.getTitle();
        // should equal this
        String expectedTitle = "Hughes Fieldhouse | Meeting Rooms";
        assertEquals(expectedTitle,actualTitle);
        // print if successful
        System.out.println(" meeting rooms found");

    }
    // test logining in to the admin page, should direct to the banneritem editor
    @Test
    public static void login_to_admin_page(WebDriver browser) {
        browser.get("http://localhost:3000/login");
        // send the login information to the username and password fields
        browser.findElement(By.id("username")).sendKeys("admin");
        browser.findElement(By.id("password")).sendKeys("admin");
        // click the login button
        browser.findElement(By.id("btn")).click();
        // wait a bit to authenticate
        try { Thread.sleep(4000); } catch(InterruptedException ex) { Thread.currentThread().interrupt(); }
        // get the title set by the script on successful load
        String actualTitle = browser.getTitle();
        // should equal this
        String expectedTitle = "Banner Items";
        assertEquals(expectedTitle,actualTitle);
        // print if successful
        System.out.println("Login Page Loaded after login");

    }
    // test that the database gets connected
    @Test
    public static void connected_to_db(MongoDatabase db) {

       // if we are able to call the db.getName method, the database instance was created properly
        String name = db.getName();
        assertEquals("test",name);
        // if the connection got the name of the database that we will be testing it was successful
        System.out.println("database was connected");

    }
    // test getting the banner item create method, we will go ahead and create a banneritem that will be tested for
    // successful creation in another test method
    @Test
    public static void banneritem_create(MongoDatabase db, WebDriver browser) {
        browser.get("http://localhost:3000/banneritem/create");
        //Make thread sleep to give javascript time to update page title
        try { Thread.sleep(1000); } catch(InterruptedException ex) { Thread.currentThread().interrupt(); }
        // get the title set by the script on successful load
        String actualTitle = browser.getTitle();
        // should equal this
        String expectedTitle = "Banner Item Create";

        // send the test information to the create form so that it can create an item to be tested
        browser.findElement(By.id("Description")).sendKeys("TEST");
        browser.findElement(By.id("startDate")).sendKeys("02-20-2019");
        browser.findElement(By.id("startTime")).sendKeys("12:00 PM");
        browser.findElement(By.id("endDate")).sendKeys("02-21-2019");
        browser.findElement(By.id("endTime")).sendKeys("12:00 PM");
        browser.findElement(By.id("priority")).click();
        browser.findElement(By.id("link")).sendKeys("http://www.nwmissouri.edu");
        browser.findElement(By.id("btn")).click();
        // check that the page was loaded by checking the title
        assertEquals(expectedTitle,actualTitle);
        // print if successful
        System.out.println(" banneritem/create found");
    }
    // test to see if our create method posted properly, we will get the item that we posted and check that it equals
    // an expected json string
    @Test
    public static void BannerItemCreatePosted(MongoDatabase db){
        // get the id of the item that we are testing since the last item that was created is the max id
        int count= (int) db.getCollection("banneritems").countDocuments();
        // should equal this json string
        String expectedJSON = "{ \"_id\" : "+count+", \"description\" : \"TEST\", \"startDate\" : \"02-20-2019\", \"endDate\" : \"02-21-2019\", \"startTime\" : \"12:00 PM\", \"endTime\" : \"12:00 PM\", \"priority\" : true, \"link\" : \"http://www.nwmissouri.edu\", \"__v\" : 0 }";
        // test if we got the created item as expected
        assertEquals(expectedJSON, getTestDBItem(db));
        // print if successful
        System.out.println("Database properly inserted record");
    }
    // get the details of the item that we created for testing purposes, will show that the details controller is working
    @Test
    public static void banneritem_details(MongoDatabase db, WebDriver browser) {
        // get the max id which is the item we created for testing
        int count= (int) db.getCollection("banneritems").countDocuments();
        // go to the details of the item that we created
        String url = "http://localhost:3000/banneritem/details/"+count;
        browser.get(url);
        // wait a bit since the database needs a bit of time to catch up
        browser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        // get the title which was set by a script to indicate successful load
        String actualTitle = browser.getTitle();
        // should equal this
        String expectedTitle = "Banner Item Details";
        assertEquals(expectedTitle,actualTitle);
        // print if successful
        System.out.println(" banneritem/details found");


    }
    // Test that we can get the banneritem edit page via controller /edit/:id method
    @Test
    public static void banneritem_edit(MongoDatabase db, WebDriver browser) {
        // get the id of the item that we created
        int count= (int) db.getCollection("banneritems").countDocuments();
        // go to the edit page of that item
        String url = "http://localhost:3000/banneritem/edit/"+count;
        browser.get(url);
        // give the browser a second to get the script loaded
        try { Thread.sleep(1000); } catch(InterruptedException ex) { Thread.currentThread().interrupt(); }
        // get the title
        String actualTitle = browser.getTitle();
        // title should equal this
        String expectedTitle = "Banner Item Edit";
        assertEquals(expectedTitle,actualTitle);
        // print if successful
        System.out.println(" banneritem/edit found");


    }
    // test the edit form of the banner item editor, we will retrieve from the database after we fill out the form to
    // verify that the data was changed
    @Test
    public static void BannerItemEditSuccess(MongoDatabase db, WebDriver browser){
        // get the id of the item we created for testing
        int count= (int) db.getCollection("banneritems").countDocuments();
        // go to the item's page
        String url = "http://localhost:3000/banneritem/edit/" + count;
        browser.get(url);
        // send information to fill out the form
        browser.findElement(By.id("Description")).sendKeys("TEST2");
        browser.findElement(By.id("startDate")).sendKeys("02-20-2018");
        browser.findElement(By.id("startTime")).sendKeys("12:00 AM");
        browser.findElement(By.id("endDate")).sendKeys("02-21-2018");
        browser.findElement(By.id("endTime")).sendKeys("12:00 AM");
        browser.findElement(By.id("priority")).click();
        browser.findElement(By.id("link")).sendKeys("http://www.google.com");
        // submit the form
        browser.findElement(By.id("btn")).click();
        // check to see if the item was edited
        String expectedJSON = "{ \"_id\" : "+count+", \"description\" : \"TESTTEST2\", \"startDate\" : \"02-20-2019\", \"endDate\" : \"02-21-2019\", \"startTime\" : \"12:00 PM\", \"endTime\" : \"12:00 PM\", \"priority\" : false, \"link\" : \"http://www.nwmissouri.eduhttp://www.google.com\", \"__v\" : 0 }";
        assertEquals(expectedJSON, getTestDBItem(db));
        // print if successful
        System.out.println("Database properly Edited record");
    }
    // test the delete controller of the banner item editor, gets /delete/:id
    @Test
    public static void banneritem_delete(MongoDatabase db, WebDriver browser) {
        // have to give the database a little bit of time to update
        try { Thread.sleep(4000); } catch(InterruptedException ex) { Thread.currentThread().interrupt(); }
        // get the item that we created for testing
        int count= (int) db.getCollection("banneritems").countDocuments();
        // get the delete page
        String url = "http://localhost:3000/banneritem/delete/"+count;
        browser.get(url);
        // give it a second to set the title
        try { Thread.sleep(1000); } catch(InterruptedException ex) { Thread.currentThread().interrupt(); }
        // get the title
        String actualTitle = browser.getTitle();
        // title should equal this
        String expectedTitle = "Banner Item Delete";
        assertEquals(expectedTitle,actualTitle);
        // print if successful
        System.out.println(" banneritem/delete found");

    }
    @Test
    public static void Delete_BannerItem_From_Database(MongoDatabase db, WebDriver browser){
        // get the item we created for testing
        int count= (int) db.getCollection("banneritems").countDocuments();

        String url = "http://localhost:3000/banneritem/delete/"+count;
        browser.get(url);
        // click the delete button
        browser.findElement(By.id("deletebtn")).click();
        // give the database a bit of time to let it do the deletion
        try { Thread.sleep(1000); } catch(InterruptedException ex) { Thread.currentThread().interrupt(); }

        // when we try to get the id of the deleted object, it should return []
        String expectedValue = "[]";
        // initialize a string that we will get the actual value of the empty object
        String actualValue = "";

        try {
            // try to get the deleted item, if we were able to get it, it was not successful in deleting
            MongoCollection<Document> myCollection = db.getCollection("banneritems");
            Document document = myCollection.find(eq("_id",count)).first();
            // if we found the item, get the json string of it to test
            actualValue = document.toJson();
        }catch(NullPointerException ex){
            // if the database finds that we are trying to access something that is null, set the item as empty brackets
            // to indicate that the item is empty
            actualValue = "[]";
        }
        assertEquals(expectedValue, actualValue);
        // print if successful
        System.out.println("Successful deletion of banneritem");
    }

    // function that will get called to get the test item that we created, this method is for convience purposes
    public static String getTestDBItem(MongoDatabase db){
        // get the item at the max index, this was the item that we created
        int count= (int) db.getCollection("banneritems").countDocuments();
        MongoCollection<Document> myCollection = db.getCollection("banneritems");
        Document document = myCollection.find(eq("_id",count)).first();
        // get the test item's json data
        String json = document.toJson();
        // return the json string for the item
        return json;
    }

    // test that the browser is resized properly, on the site, I created a div that shows in the dom,
    // once the browser is set to a mobile screen sized to simulate a mobile device, the div is removed from the dom
    // by knowing it is removed from the dom, we know that the browser is following the resizing for mobile as expected
    @Test
    public static void mobile_design_displays(WebDriver browser) {
        browser.get("http://localhost:3000/");
        // see if the item is displayed in the dom, this is not the visibility on screen but seeing if
        // it is visible in the dom
        Boolean actualVisibility = browser.findElement(By.id("mobileTestDiv")).isDisplayed();
        //should not be displayed on a mobile browser
        assertEquals(false,actualVisibility);
        // print if successful
        System.out.println(" site did perform resizes for a mobile display");

    }
}