import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.List;


public class Selenium4Listener implements WebDriverListener {
    private Logger logger = LogManager.getLogger(Selenium4Listener.class);
    private int counter = 0;

    @Override
    public void afterClick (WebElement element) {
        logger.info("Выполнено нажатие на веб элемент");
    }

    @Override
    public void afterFindElement (WebDriver driver, By locator, WebElement element) {
        logger.info("Найден веб элемент");
        try {
            Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(700)).takeScreenshot(driver);
            counter++;
            ImageIO.write(screenshot.getImage(), "png", new File("temp\\DNSListenerAFE" + counter + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterFindElements(WebDriver driver, By locator, List<WebElement> result) {
       logger.info("Найдены веб элементы");
        try {
            Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(700)).takeScreenshot(driver);
            counter++;
            ImageIO.write(screenshot.getImage(), "png", new File("temp\\DNSListenerAFES" + counter + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterGetText(WebElement element, String result) {
        logger.info("Получен текст");
    }
}
