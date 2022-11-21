import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Sleeper;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class WebDriverHW02Test {
    protected static WebDriver driver;
    private Logger logger = LogManager.getLogger(WebDriverHW02Test.class);

    // Чтение передаваемого параметра browser (-Dbrowser)
    String env = System.getProperty("browser", "chrome");
    String pageLoadStrategy = System.getProperty("loadstrategy", "normal");

    @BeforeEach
    public void setUp() {
        driver = WebDriverFactory.getDriver(env.toLowerCase(), pageLoadStrategy.toLowerCase());
        logger.info("Драйвер стартовал!");
    }

    @AfterEach
    public void setDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Драйвер остановлен!");
        }
    }

    @Test
    public void testCaseThree() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Actions actions = new Actions(driver);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Selenium4Listener listener = new Selenium4Listener();
        WebDriver eventFiringWebDriver = new EventFiringDecorator<>(listener).decorate(driver);


        driver.get("https://www.dns-shop.ru/");
        logger.info("Открыта страница DNS - " + "https://www.dns-shop.ru/");
        try {
            Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(700)).takeScreenshot(driver);
            ImageIO.write(screenshot.getImage(), "png", new File("temp\\DnsMainPageС3.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        By computersAndParaphernaliaLinkText = By.linkText("ПК, ноутбуки, периферия");
        wait.until(ExpectedConditions.visibilityOfElementLocated(computersAndParaphernaliaLinkText));
        WebElement computersAndParaphernalia = eventFiringWebDriver.findElement(computersAndParaphernaliaLinkText);
        actions.scrollToElement(computersAndParaphernalia).moveToElement(computersAndParaphernalia).perform();
        try {
            Sleeper.SYSTEM_SLEEPER.sleep(Duration.ofSeconds(3));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(700)).takeScreenshot(driver);
            ImageIO.write(screenshot.getImage(), "png", new File("temp\\DnsComputersAndParaphernaliaС3.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        actions.scrollToElement(computersAndParaphernalia).perform();
        By linkLaptopXpath = By.xpath("//a[text() = 'Ноутбуки']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(linkLaptopXpath));
        WebElement linkLaptop = eventFiringWebDriver.findElement(linkLaptopXpath);
        actions.scrollToElement(computersAndParaphernalia).perform();
        wait.until(ExpectedConditions.elementToBeClickable(linkLaptop));
        linkLaptop.click();
        try {
            Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(700)).takeScreenshot(driver);
            ImageIO.write(screenshot.getImage(), "png", new File("temp\\DnsLaptopPageС3.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        js.executeScript("window.scrollTo(0,0)");

        String script = "document.querySelector('header').style.display='none'";
        js.executeScript(script);
        try {
            Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(700)).takeScreenshot(driver);
            ImageIO.write(screenshot.getImage(), "png", new File("temp\\DnsLaptopPageNoneHeaderС3.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        js.executeScript("window.scrollTo(0,0)");
        js.executeScript("window.scrollBy(0,600)");

        By companyCheckboxXpath = By.xpath("//span[text() = 'ASUS  ']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(companyCheckboxXpath));
        WebElement companyCheckbox = eventFiringWebDriver.findElement(companyCheckboxXpath);
        actions.scrollToElement(companyCheckbox).perform();
        companyCheckbox.click();

        js.executeScript("window.scrollBy(0,600)");

        By accordeonRamXpath = By.xpath("//span[text() = 'Объем оперативной памяти (ГБ)']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(accordeonRamXpath));
        WebElement accordeonRam = eventFiringWebDriver.findElement(accordeonRamXpath);
        actions.scrollToElement(accordeonRam).perform();
        wait.until(ExpectedConditions.elementToBeClickable(accordeonRam));
        accordeonRam.click();

        By ramCheckboxXpath = By.xpath("//span[text() = '32 ГБ  ']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(ramCheckboxXpath));
        WebElement ramCheckbox = eventFiringWebDriver.findElement(ramCheckboxXpath);
        actions.scrollToElement(ramCheckbox).perform();
        wait.until(ExpectedConditions.elementToBeClickable(ramCheckbox));
        ramCheckbox.click();

        js.executeScript("window.scrollBy(0,600)");

        By applyButtonXpath = By.xpath("//button[text() = 'Применить']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(applyButtonXpath));
        WebElement applyButton = eventFiringWebDriver.findElement(applyButtonXpath);
        actions.scrollToElement(applyButton).perform();
        wait.until(ExpectedConditions.elementToBeClickable(applyButton));
        applyButton.click();
        try {
            Sleeper.SYSTEM_SLEEPER.sleep(Duration.ofSeconds(3));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(700)).takeScreenshot(driver);
            ImageIO.write(screenshot.getImage(), "png", new File("temp\\DnsLaptopPageFiltersС3.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        By accordeonSortXpath = By.xpath("//a[@class = 'ui-link ui-link_blue'][1]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(accordeonSortXpath));
        WebElement accordeonSort = eventFiringWebDriver.findElement(accordeonSortXpath);
        actions.scrollToElement(accordeonSort).perform();
        wait.until(ExpectedConditions.elementToBeClickable(accordeonSort));
        accordeonSort.click();

        By radioButtonSortXpath = By.xpath("//span[text() = 'Сначала дорогие']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(radioButtonSortXpath));
        WebElement radioButtonSort = eventFiringWebDriver.findElement(radioButtonSortXpath);
        actions.scrollToElement(radioButtonSort).perform();
        wait.until(ExpectedConditions.elementToBeClickable(radioButtonSort));
        radioButtonSort.click();
        try {
            Sleeper.SYSTEM_SLEEPER.sleep(Duration.ofSeconds(3));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(700)).takeScreenshot(driver);
            ImageIO.write(screenshot.getImage(), "png", new File("temp\\DnsLaptopPageSortС3.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        js.executeScript("window.scrollTo(0,0)");

        By firstProductLaptopXpath = By.xpath("//a[@class = 'catalog-product__name ui-link ui-link_black'][1]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstProductLaptopXpath));
        WebElement firstProductLaptopLink = eventFiringWebDriver.findElement(firstProductLaptopXpath);
        actions.scrollToElement(firstProductLaptopLink).perform();
        String URL = firstProductLaptopLink.getAttribute("href");
        String firstLaptopLinkName = firstProductLaptopLink.getText();
        driver.switchTo().newWindow(WindowType.WINDOW).manage().window().maximize();
        driver.get(URL);
        try {
            Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(700)).takeScreenshot(driver);
            ImageIO.write(screenshot.getImage(), "png", new File("temp\\DnsFirstLaptopPageС3.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        js.executeScript("window.scrollTo(0,0)");

        By laptopPageTitleXpath = By.xpath("//h1[@class = 'product-card-top__title']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(laptopPageTitleXpath));
        WebElement laptopPageTitle = eventFiringWebDriver.findElement(laptopPageTitleXpath);
        actions.scrollToElement(laptopPageTitle).perform();
        String laptopPageTitleText = laptopPageTitle.getText();
        Assertions.assertTrue(firstLaptopLinkName.contains(laptopPageTitleText), "Заголовок не соответствует ожидаемому");

        js.executeScript("window.scrollBy(0,400)");

        By laptopPageSpecificationsTitleXpath = By.xpath("//div[@class = 'product-card-description__title']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(laptopPageSpecificationsTitleXpath));
        WebElement laptopPageSpecificationTitle = eventFiringWebDriver.findElement(laptopPageSpecificationsTitleXpath);
        actions.scrollToElement(laptopPageSpecificationTitle).perform();
        String laptopPageSpecificationTitleText = laptopPageSpecificationTitle.getText();
        Assertions.assertTrue(laptopPageSpecificationTitleText.contains("ASUS"), "Заголовок не содержит ASUS");

        By laptopAllSpecificationsButtonXpath = By.xpath("//button[text() = 'Развернуть все']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(laptopAllSpecificationsButtonXpath));
        WebElement laptopAllSpecificationsButton = eventFiringWebDriver.findElement(laptopAllSpecificationsButtonXpath);
        actions.scrollToElement(laptopAllSpecificationsButton).perform();
        wait.until(ExpectedConditions.elementToBeClickable(laptopAllSpecificationsButton));
        laptopAllSpecificationsButton.click();

        js.executeScript("window.scrollBy(0,1500)");

        By laptopRAMSpecificationsID = By.xpath("//*[text() = ' Объем оперативной памяти ']/following-sibling::div");
        wait.until(ExpectedConditions.visibilityOfElementLocated(laptopRAMSpecificationsID));
        WebElement laptopRAMSpecification = eventFiringWebDriver.findElement(laptopRAMSpecificationsID);
        actions.scrollToElement(laptopRAMSpecification).perform();
        String laptopRAMSpecificationText = laptopRAMSpecification.getText();
        Assertions.assertEquals(laptopRAMSpecificationText, "32 ГБ", "Объем оперативной памяти не равен 32 ГБ");
    }
}
