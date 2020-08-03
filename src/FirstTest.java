import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.hamcrest.CoreMatchers;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;


public class FirstTest {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "8.1");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "/Users/macbook/Desktop/JavaAppiumAutomation/apks/org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    @Ignore
    public void firstTest() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5);

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find 'Object-oriented programming language' topic searching by Java",
                5
        );

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by Java",
                15
        );
    }

    @Test
    @Ignore
    public void testCancelSearch() {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Java",
                "Cannot find 'Object-oriented programming language' topic searching by Java",
                5
        );

        waitForElementAndClear(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find Search field",
                5
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find 'X to cancel Search' input",
                5
        );

        waitForElementNotPresent(
                By.id("org.wikipedia:id/search_close_btn"),
                "X button is still here",
                5
        );
    }

    @Test
    public void testCompareArticleTitle() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5);

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find 'Object-oriented programming language' topic searching by Java",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find the article to tap",
                5
        );

        WebElement title_element = waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15
        );

        String article_title = title_element.getAttribute("text");
        Assert.assertEquals("We see unexpected title", "Java (programming language)", article_title);
    }

    @Test
    public void testElementHasText() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5);

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find 'Object-oriented programming language' topic searching by Java",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find the article to tap",
                5
        );

        assertElementHasText(
                By.id("org.wikipedia:id/view_page_subtitle_text"),
                "programming",
                "Element doesn't contain expected text"
        );
    }

    private WebElement waitForElementPresent(By by, String error_message, long timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private WebElement waitForElementPresent(By by, String error_message) {
        return waitForElementPresent(by, error_message, 5);
    }

    private WebElement waitForElementAndClick(By by, String error_message, long timeOutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeOutInSeconds);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeOutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeOutInSeconds);
        element.sendKeys(value);
        return element;
    }

    private boolean waitForElementNotPresent(By by, String error_message, long timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    private WebElement waitForElementAndClear(By by, String error_message, long timeOutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeOutInSeconds);
        element.clear();
        return element;
    }

    private WebElement assertElementHasText(By by, String expected_result, String error_message) {
        WebElement element = waitForElementPresent(
                by,
                error_message,
                15
        );
        String containedText = element.getAttribute("text");
        Assert.assertThat(containedText, CoreMatchers.containsString(expected_result));
        return element;
    }
}

