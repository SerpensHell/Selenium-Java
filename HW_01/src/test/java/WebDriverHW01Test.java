import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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
        driver = WebDriverFactory.getDriver(env.toLowerCase(), pageLoadStrategy);
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
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(100));
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
        WebElement cityConfirm = driver.findElement(By.xpath("//span[text()='Всё верно'][1]"));
        cityConfirm.click();

        //Ожидание загрузки элементов страницы, после перезагрузки
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement linkAppliances = driver.findElement(By.linkText("Бытовая техника"));
        linkAppliances.click();

        WebElement pageTitleAppliances = driver.findElement(By.className("subcategory__page-title"));
        String textPageTitleAppliances = pageTitleAppliances.getText();
        logger.info("Отображаемый текст: " + textPageTitleAppliances);
        Assertions.assertEquals("Бытовая техника", textPageTitleAppliances, "Текст Бытовая техника не отображается");

        WebElement linkKitchenAppliances = driver.findElement(By.xpath("//span[text() = 'Техника для кухни']"));
        linkKitchenAppliances.click();

        WebElement pageTitleKitchenAppliances = driver.findElement(By.className("subcategory__page-title"));
        String textPageTitleKitchenAppliances = pageTitleKitchenAppliances.getText();
        logger.info("Отображаемый текст: " + textPageTitleKitchenAppliances);
        Assertions.assertEquals("Техника для кухни", textPageTitleKitchenAppliances, "Текст Техника для кухни не отображается");

        WebElement linkAssembleYourKitchen = driver.findElement(By.xpath("//a[@class = 'button-ui button-ui_white configurator-links-block__links-link']"));
        String textLinkAssembleYourKitchen = linkAssembleYourKitchen.getText();
        logger.info("Отображаемая ссылка: " + textLinkAssembleYourKitchen);
        Assertions.assertEquals("Собрать свою кухню", textLinkAssembleYourKitchen, "Ссылка Собрать свою кухню не отображается");

        List<WebElement> categories = driver.findElements(By.className("subcategory__title"));
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
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(100));
        driver.get("https://www.dns-shop.ru/");
        logger.info("Открыта страница DNS - " + "https://www.dns-shop.ru/");

        //Подтверждение города, для предостваления доступа к ссылке "Бытовая техника"
        WebElement cityConfrim = driver.findElement(By.xpath("//span[text()='Всё верно'][1]"));
        cityConfrim.click();
        //Ожидание загрузки элементов страницы, после перезагрузки
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement linkAppliances = driver.findElement(By.linkText("Бытовая техника"));
        Actions actions = new Actions(driver);
        actions.moveToElement(linkAppliances).perform();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<WebElement> links = driver.findElements(By.xpath("//a[@class ='ui-link menu-desktop__first-level']"));
        List<String> linksTextTest = Arrays.asList("Техника для кухни", "Техника для дома", "Красота и здоровье");
        for (WebElement link : links) {
            String linkName = link.getText();
            logger.info("Отображаемая ссылка: " + linkName);
            Assertions.assertTrue(linksTextTest.contains(linkName), "Ссылка отображается неверно");
        }

        WebElement linkCooking = driver.findElement(By.xpath("//a[text()='Приготовление пищи']"));
        actions.moveToElement(linkCooking).perform();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement linkSubMenuCooking = driver.findElement(By.xpath("//span[@class='menu-desktop__popup'][1]"));
        int link3Count = Integer.parseInt(linkSubMenuCooking.getAttribute("childElementCount"));
        logger.info(String.format("Количество ссылок: %d", link3Count));
        Assertions.assertTrue(link3Count > 5, "Количество ссылок в подменю Приготовление пищи меньше 5");

        WebElement linkStoves = driver.findElement(By.xpath("//a[text() = 'Плиты']"));
        actions.moveToElement(linkStoves).perform();

        linkStoves.click();

        WebElement linkElectricStoves = driver.findElement(By.xpath("//span[text() = 'Плиты электрические']"));
        linkElectricStoves.click();

        WebElement productCountTitle = driver.findElement(By.className("products-count"));
        String productCountText = productCountTitle.getText();
        int productCount = Integer.parseInt(productCountText.substring(0, productCountText.indexOf(" ")));
        logger.info(String.format("Количество товаров: %d", productCount));
        Assertions.assertTrue(productCount > 100, "Количество товаров меньше 100");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
