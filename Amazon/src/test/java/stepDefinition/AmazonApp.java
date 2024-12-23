package stepDefinition;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AmazonApp {

    private WebDriver driver;
    private Scenario scenario;
    private AmazonObject obj;
    private WebDriverWait wait;

    @SuppressWarnings("deprecation")
    @Before
    public void setUp(Scenario scenario) {
        this.scenario = scenario;

        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Selenium\\chromedriverFolder\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--incognito");
        driver = new ChromeDriver(options);

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        obj = new AmazonObject(driver);
    }

    @Given("Launch the Application")
    public void launch_the_application() {
        driver.get("https://www.amazon.in/");
        driver.manage().window().maximize();
        wait.until(ExpectedConditions.titleContains("Amazon"));

        takeScreenshot("Amazon Homescreen");

        Assert.assertTrue("Title does not contain 'Amazon'", driver.getTitle().contains("Amazon"));
    }

    @Given("Check for list of broken links")
    public void check_for_list_of_broken_links() {
    	List<WebElement> links = driver.findElements(By.tagName("a")); 
        System.out.println("Total links found: " + links.size());

        for (WebElement link : links) {
            @SuppressWarnings("deprecation")
			String url = link.getAttribute("href");

            if (url == null || url.isEmpty()) {
                System.out.println("Empty or null URL: " + link.getText());
                continue;
            }

            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setRequestMethod("HEAD");
                connection.connect();

                int responseCode = connection.getResponseCode();
                if (responseCode >= 400) {
                    System.out.println("Broken link: " + url + " | Response code: " + responseCode);
                } else {
                    System.out.println("Valid link: " + url + " | Response code: " + responseCode);
                }
            } catch (Exception e) {
                System.out.println("Error checking link: " + url + " | Exception: " + e.getMessage());
            }
        }
    }

    @Given("Click on signin button")
    public void click_on_signin_button() {
        WebElement signinButton = ((AmazonObject) obj).signinButton();
        wait.until(ExpectedConditions.elementToBeClickable(signinButton));
        signinButton.click();

        wait.until(ExpectedConditions.titleContains("Amazon Sign In"));
        Assert.assertTrue("Sign-In page not displayed", driver.getTitle().contains("Amazon Sign In"));
    }

    @When("Enter the email and Click on Continue")
    public void enter_the_email_and_click_on_continue() {
        String email = ConfigReader.getProperty("email");
        wait.until(ExpectedConditions.visibilityOf(((AmazonObject) obj).Email()));
        ((AmazonObject) obj).Email().sendKeys(email);

        takeScreenshot("Email entered");

        ((AmazonObject) obj).Continue().click();

        wait.until(ExpectedConditions.visibilityOf(((AmazonObject) obj).Password()));
        Assert.assertTrue("Password field not visible", ((AmazonObject) obj).Password().isDisplayed());
    }

    @When("Enter Password and Click on SignIn")
    public void enter_password_and_click_on_sign_in() {
        String password = ConfigReader.getProperty("password");
        wait.until(ExpectedConditions.visibilityOf(((AmazonObject) obj).Password()));
        ((AmazonObject) obj).Password().sendKeys(password);

        takeScreenshot("Password entered");

        ((AmazonObject) obj).SignIn().click();

        wait.until(ExpectedConditions.visibilityOf(((AmazonObject) obj).VerifyHello()));
        String expectedText = "Hello, Swathi";
        String actualText = ((AmazonObject) obj).VerifyHello().getText();
        Assert.assertEquals("User login failed. Incorrect text.", expectedText, actualText);
    }

    @Then("User loggedIn Successfully")
    public void user_logged_in_successfully() {
        wait.until(ExpectedConditions.visibilityOf(((AmazonObject) obj).VerifyHello()));
        String expectedText = "Hello, Swathi";
        String actualText = ((AmazonObject) obj).VerifyHello().getText();

        Assert.assertEquals("User login failed. Incorrect text.", expectedText, actualText);

        takeScreenshot("logged in successfully");

        Assert.assertTrue("User not logged in", actualText.contains("Hello"));
    }

    @When("User Search for product {string}")
    public void user_search_for_product_iphone(String productName) {
    	WebElement searchBox = wait.until(ExpectedConditions.visibilityOf(((AmazonObject)obj).SearchBox()));
        searchBox.sendKeys(productName);
        
        takeScreenshot("Search for product");

        WebElement selectButton = wait.until(ExpectedConditions.elementToBeClickable(((AmazonObject)obj).Select()));
        selectButton.click();
    }

    @Then("User clicks on Add to cart")
    public void user_clicks_on_add_to_cart() {
        wait.until(ExpectedConditions.elementToBeClickable(((AmazonObject) obj).AddToCart()));
        ((AmazonObject) obj).AddToCart().click();

        wait.until(ExpectedConditions.visibilityOf(((AmazonObject) obj).CartValue()));
        String cartValue = ((AmazonObject) obj).CartValue().getText();
        Assert.assertTrue("Cart value is not updated.", !cartValue.isEmpty());

        takeScreenshot("Product added to cart");

        Assert.assertTrue("Cart value did not update", Integer.parseInt(cartValue) > 0);
    }

    private void takeScreenshot(String screenshotName) {
        if (driver instanceof TakesScreenshot) {
            TakesScreenshot screenshotDriver = (TakesScreenshot) driver;
            byte[] screenshot = screenshotDriver.getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", screenshotName);
        }
    }
}
