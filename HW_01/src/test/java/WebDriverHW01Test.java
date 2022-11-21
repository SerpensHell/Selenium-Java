import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class WebDriverHW01Test {
    protected static WebDriver driver;
    private Logger logger = LogManager.getLogger(WebDriverHW01Test.class);

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
        if(driver != null) {
            driver.quit();
            logger.info("Драйвер остановлен!");
        }
    }

    @Test
    public void testCaseOne() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        driver.get("https://www.dns-shop.ru/");
        logger.info("Открыта страница DNS - " + "https://www.dns-shop.ru/");

        String title = driver.getTitle();
        logger.info("title -" + title);

        String currentUrl = driver.getCurrentUrl();
        logger.info("current URL - " + currentUrl);

        int windowWidth = driver.manage().window().getSize().getWidth();
        logger.info(String.format("Ширина окна: %d", windowWidth));

        int windowHeight = driver.manage().window().getSize().getHeight();
        logger.info(String.format("Высота окна: %d", windowHeight));

        //Подтверждение города, для предостваления доступа к ссылке "Бытовая техника"
        By cityConfirmXpath = By.xpath("//span[text()='Всё верно'][1]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(cityConfirmXpath));
        WebElement cityConfirm = driver.findElement(cityConfirmXpath);
        wait.until(ExpectedConditions.elementToBeClickable(cityConfirm));
        cityConfirm.click();
        try {
            Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(700)).takeScreenshot(driver);
            ImageIO.write(screenshot.getImage(), "png", new File("temp\\DnsMainPageС1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        js.executeScript("window.scrollTo(0,0)");

        By appliancesLinkText = By.linkText("Бытовая техника");
        wait.until(ExpectedConditions.visibilityOfElementLocated(appliancesLinkText));
        WebElement linkAppliances = driver.findElement(appliancesLinkText);
        wait.until(ExpectedConditions.elementToBeClickable(linkAppliances));
        linkAppliances.click();
        try {
            Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(700)).takeScreenshot(driver);
            ImageIO.write(screenshot.getImage(), "png", new File("temp\\DnsAppliancesPageС1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        js.executeScript("window.scrollTo(0,0)");

        By pageTitleAppliancesClassName = By.className("subcategory__page-title");
        wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitleAppliancesClassName));
        WebElement pageTitleAppliances = driver.findElement(pageTitleAppliancesClassName);
        String textPageTitleAppliances = pageTitleAppliances.getText();
        logger.info("Отображаемый текст: " + textPageTitleAppliances);
        Assertions.assertEquals("Бытовая техника", textPageTitleAppliances, "Текст Бытовая техника не отображается");

        By kitchenAppliancesXpath = By.xpath("//span[text() = 'Техника для кухни']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(kitchenAppliancesXpath));
        WebElement linkKitchenAppliances = driver.findElement(kitchenAppliancesXpath);
        wait.until(ExpectedConditions.elementToBeClickable(linkKitchenAppliances));
        linkKitchenAppliances.click();
        try {
            Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(700)).takeScreenshot(driver);
            ImageIO.write(screenshot.getImage(), "png", new File("temp\\DnsKitchenAppliancesPageС1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        js.executeScript("window.scrollTo(0,0)");

        By pageTitleKitchenAppliancesClassName = By.className("subcategory__page-title");
        wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitleKitchenAppliancesClassName));
        WebElement pageTitleKitchenAppliances = driver.findElement(pageTitleKitchenAppliancesClassName);
        String textPageTitleKitchenAppliances = pageTitleKitchenAppliances.getText();
        logger.info("Отображаемый текст: " + textPageTitleKitchenAppliances);
        Assertions.assertEquals("Техника для кухни", textPageTitleKitchenAppliances, "Текст Техника для кухни не отображается");

        By AssembleYourKitchenXpath = By.xpath("//a[@class = 'button-ui button-ui_white configurator-links-block__links-link']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(AssembleYourKitchenXpath));
        WebElement linkAssembleYourKitchen = driver.findElement(AssembleYourKitchenXpath);
        String textLinkAssembleYourKitchen = linkAssembleYourKitchen.getText();
        logger.info("Отображаемая ссылка: " + textLinkAssembleYourKitchen);
        Assertions.assertEquals("Собрать свою кухню", textLinkAssembleYourKitchen, "Ссылка Собрать свою кухню не отображается");


        By categoriesClassName = By.className("subcategory__title");
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(categoriesClassName));
        List<WebElement> categories = driver.findElements(categoriesClassName);
        for (WebElement category : categories) {
            String categoryName = category.getText();
            logger.info(categoryName);
        }

        int numberOfCategories = driver.findElements(By.className("subcategory__title")).size();
        logger.info("Количество категорий: " + numberOfCategories);
        Assertions.assertTrue(numberOfCategories > 5, "Количество категорий меньше 5");
    }

    @Test
    public void testCaseTwo() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Actions actions = new Actions(driver);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        driver.get("https://www.dns-shop.ru/");
        logger.info("Открыта страница DNS - " + "https://www.dns-shop.ru/");

        //Подтверждение города, для предостваления доступа к ссылке "Бытовая техника"
        By cityConfirmXpath = By.xpath("//span[text()='Всё верно'][1]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(cityConfirmXpath));
        WebElement cityConfirm = driver.findElement(cityConfirmXpath);
        wait.until(ExpectedConditions.elementToBeClickable(cityConfirm));
        cityConfirm.click();
        try {
            Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(700)).takeScreenshot(driver);
            ImageIO.write(screenshot.getImage(), "png", new File("temp\\DnsMainPageС2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        js.executeScript("window.scrollTo(0,0)");

        By appliancesLinkText = By.linkText("Бытовая техника");
        wait.until(ExpectedConditions.visibilityOfElementLocated(appliancesLinkText));
        WebElement linkAppliances = driver.findElement(appliancesLinkText);
        actions.moveToElement(linkAppliances).perform();
        try {
            Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(700)).takeScreenshot(driver);
            ImageIO.write(screenshot.getImage(), "png", new File("temp\\DnsAppliancesMenuС2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        actions.scrollToElement(linkAppliances).perform();

        By menuLinksXpath = By.xpath("//a[@class ='ui-link menu-desktop__first-level']");
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(menuLinksXpath));
        List<WebElement> menuLinks = driver.findElements(menuLinksXpath);
        List<String> linksTextTest = Arrays.asList("Техника для кухни", "Техника для дома", "Красота и здоровье");
        for (WebElement link : menuLinks) {
            String linkName = link.getText();
            logger.info("Отображаемая ссылка: " + linkName);
            Assertions.assertTrue(linksTextTest.contains(linkName), "Ссылка отображается неверно");
        }

        By linkCookingXpath = By.xpath("//a[text()='Приготовление пищи']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(linkCookingXpath));
        WebElement linkCooking = driver.findElement(linkCookingXpath);
        actions.moveToElement(linkCooking).perform();
        try {
            Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(700)).takeScreenshot(driver);
            ImageIO.write(screenshot.getImage(), "png", new File("temp\\DnsCookingС2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        actions.scrollToElement(linkAppliances).moveToElement(linkCooking).perform();

        By linkSubMenuCookingXpath = By.xpath("//span[@class='menu-desktop__popup'][1]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(linkSubMenuCookingXpath));
        WebElement linkSubMenuCooking = driver.findElement(linkSubMenuCookingXpath);
        int linkSubMenuCount = Integer.parseInt(linkSubMenuCooking.getAttribute("childElementCount"));
        logger.info(String.format("Количество ссылок: %d", linkSubMenuCount));
        Assertions.assertTrue(linkSubMenuCount > 5, "Количество ссылок в подменю Приготовление пищи меньше 5");


        By linkStovesXpath = By.xpath("//a[text() = 'Плиты']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(linkStovesXpath));
        WebElement linkStoves = driver.findElement(linkStovesXpath);
        wait.until(ExpectedConditions.elementToBeClickable(linkStoves));
        actions.moveToElement(linkStoves).click().perform();
        try {
            Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(700)).takeScreenshot(driver);
            ImageIO.write(screenshot.getImage(), "png", new File("temp\\DnsStovesС2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        js.executeScript("window.scrollTo(0,0)");

        By linkElectricStovesXpath = By.xpath("//span[text() = 'Плиты электрические']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(linkElectricStovesXpath));
        WebElement linkElectricStoves = driver.findElement(linkElectricStovesXpath);
        wait.until(ExpectedConditions.elementToBeClickable(linkElectricStoves));
        linkElectricStoves.click();
        try {
            Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(700)).takeScreenshot(driver);
            ImageIO.write(screenshot.getImage(), "png", new File("temp\\DnsElectricStovesС2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        js.executeScript("window.scrollTo(0,0)");

        By productCountTitleClassName = By.className("products-count");
        wait.until(ExpectedConditions.visibilityOfElementLocated(productCountTitleClassName));
        WebElement productCountTitle = driver.findElement(productCountTitleClassName);
        String productCountText = productCountTitle.getText();
        int productCount = Integer.parseInt(productCountText.substring(0, productCountText.indexOf(" ")));
        logger.info(String.format("Количество товаров: %d", productCount));
        Assertions.assertTrue(productCount > 100, "Количество товаров меньше 100");
    }
}
