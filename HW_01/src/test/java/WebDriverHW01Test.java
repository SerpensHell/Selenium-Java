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

    @BeforeEach
    public void setUp() {
        driver = WebDriverFactory.getDriver(env.toLowerCase());
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

        WebElement link1 = driver.findElement(By.linkText("Бытовая техника"));
        link1.click();

        WebElement pageTitle1 = driver.findElement(By.className("subcategory__page-title"));
        String textPageTitle1 = pageTitle1.getText();
        logger.info("Отображаемый текст: " + textPageTitle1);
        Assertions.assertTrue(textPageTitle1.equals("Бытовая техника"), "Текст Бытовая техника не отображается");

        WebElement link2 = driver.findElement(By.xpath("//span[text() = 'Техника для кухни']"));
        link2.click();

        WebElement pageTitle2 = driver.findElement(By.className("subcategory__page-title"));
        String textPageTitle2 = pageTitle2.getText();
        logger.info("Отображаемый текст: " + textPageTitle2);
        Assertions.assertTrue(textPageTitle2.equals("Техника для кухни"), "Текст Техника для кухни не отображается");

        WebElement link3 = driver.findElement(By.xpath("//a[@class = 'button-ui button-ui_white configurator-links-block__links-link']"));
        String textLink3 = link3.getText();
        logger.info("Отображаемая ссылка: " + textLink3);
        Assertions.assertTrue(textLink3.equals("Собрать свою кухню"), "Ссылка Собрать свою кухню не отображается");

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

        WebElement link1 = driver.findElement(By.linkText("Бытовая техника"));
        Actions actions = new Actions(driver);
        actions.moveToElement(link1).perform();

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

        WebElement link2 = driver.findElement(By.xpath("//a[text()='Приготовление пищи']"));
        actions.moveToElement(link2).perform();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement link3 = driver.findElement(By.xpath("//span[@class='menu-desktop__popup'][1]"));
        int link3Count = Integer.parseInt(link3.getAttribute("childElementCount"));
        logger.info(String.format("Количество ссылок: %d", link3Count));
        Assertions.assertTrue(link3Count > 5, "Количество ссылок в подменю Приготовление пищи меньше 5");

        WebElement link4 = driver.findElement(By.xpath("//a[text() = 'Плиты']"));
        actions.moveToElement(link4).perform();

        link4.click();

        WebElement link5 = driver.findElement(By.xpath("//span[text() = 'Плиты электрические']"));
        link5.click();

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
