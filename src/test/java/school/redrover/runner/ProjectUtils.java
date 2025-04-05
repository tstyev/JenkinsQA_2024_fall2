package school.redrover.runner;

import org.openqa.selenium.Alert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.*;
import java.time.Duration;
import java.util.Properties;

public final class ProjectUtils {

    private static final String PREFIX_PROP = "";
    private static final String PROP_HOST = PREFIX_PROP + "host";
    private static final String PROP_PORT = PREFIX_PROP + "port";
    private static final String PROP_ADMIN_USERNAME = PREFIX_PROP + "admin.username";
    private static final String PROP_ADMIN_PAS = PREFIX_PROP + "admin.password";
    private static final String PROP_CHROME_OPTIONS = "chrome_options";
    private static final String CLOSE_BROWSER_IF_ERROR = PREFIX_PROP + "closeBrowserIfError";

    private static final String LOCAL_PROPS = "local.properties";
    private static final String TEST_PROPS = "test.properties";

    private static Properties properties;

    private ProjectUtils() {
        throw new UnsupportedOperationException();
    }

    private static void initProperties() {
        if (properties != null) return;

        properties = new Properties();

        try (InputStream stream = getPropsStream()) {
            if (stream == null) {
                throw new RuntimeException("Neither local.properties nor test.properties found in resources.");
            }
            properties.load(stream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties", e);
        }
    }

    private static InputStream getPropsStream() {
        InputStream local = ProjectUtils.class.getClassLoader().getResourceAsStream(LOCAL_PROPS);
        if (local != null) {
            log("Loaded properties from local.properties");
            return local;
        }

        InputStream test = ProjectUtils.class.getClassLoader().getResourceAsStream(TEST_PROPS);
        if (test != null) {
            log("Loaded properties from test.properties");
            return test;
        }

        return null;
    }

    static final ChromeOptions chromeOptions;

    static {
        initProperties();

        chromeOptions = new ChromeOptions();
        String options = properties.getProperty(PROP_CHROME_OPTIONS);
        if (options != null) {
            for (String argument : options.split(";")) {
                chromeOptions.addArguments(argument);
            }
        }
    }

    static boolean isServerRun() {
        return System.getenv("CI_RUN") != null;
    }

    static boolean closeBrowserIfError() {
        return Boolean.parseBoolean(properties.getProperty(CLOSE_BROWSER_IF_ERROR, "true"));
    }

    public static String getUrl() {
        return String.format("http://%s:%s/",
                properties.getProperty(PROP_HOST),
                properties.getProperty(PROP_PORT));
    }

    public static String getUserName() {
        return properties.getProperty(PROP_ADMIN_USERNAME);
    }

    public static String getPassword() {
        return properties.getProperty(PROP_ADMIN_PAS);
    }

    static void acceptAlert(WebDriver driver) {
        Alert alert = ExpectedConditions.alertIsPresent().apply(driver);
        if (alert != null) {
            alert.accept();
        }
    }

    static WebDriver createDriver() {
        WebDriver driver = new ChromeDriver(ProjectUtils.chromeOptions);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        return driver;
    }

    static void takeScreenshot(WebDriver driver, String className, String methodName) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(new File("screenshots", "%s.%s.png".formatted(className, methodName)))) {
            fileOutputStream.write(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void get(WebDriver driver) {
        driver.get(getUrl());
    }

    public static void log(String str) {
        System.out.println(str);
    }

    public static void logf(String str, Object... arr) {
        log(String.format(str, arr));
    }
}
