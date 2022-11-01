import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class WebDriverFactory {
    private static Logger logger = LogManager.getLogger(WebDriverFactory.class);

    public static WebDriver getDriver(String browserName) {
        switch (browserName) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions optionsChrome = new ChromeOptions();
                optionsChrome.addArguments("--incognito");
                optionsChrome.addArguments("--start-maximized");
                logger.info("Драйвер для браузера Google Chrome");
                return new ChromeDriver(optionsChrome);

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions optionsFirefox = new FirefoxOptions();
                optionsFirefox.addArguments("-private");
                optionsFirefox.addArguments("--kiosk");
                logger.info("Драйвер для браузера Mozilla Firefox");
                return new FirefoxDriver(optionsFirefox);

            default:
                throw new RuntimeException("Введено некорректное название браузера");
        }
    }
}

