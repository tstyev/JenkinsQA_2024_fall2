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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ProjectUtils {

    private static final String PROP_HOST = "host";
    private static final String PROP_PORT = "port";
    private static final String PROP_ADMIN_USERNAME = "admin.username";
    private static final String PROP_ADMIN_PAS = "admin.password";
    private static final String PROP_CHROME_OPTIONS = "chrome_options";
    private static final String CLOSE_BROWSER_IF_ERROR = "closeBrowserIfError";

    private static Properties properties;

    private ProjectUtils() {
        throw new UnsupportedOperationException();
    }

    private static void replaceEnvVariablesInProperties() {
        for (String name : properties.stringPropertyNames()) {
            String value = properties.getProperty(name);

            if (value != null) {
                if (value.contains("ADMIN_USERNAME")) {
                    String envValue = System.getenv("ADMIN_USERNAME");
                    if (envValue != null) {
                        value = value.replace("ADMIN_USERNAME", envValue);
                    }
                }

                if (value.contains("ADMIN_PASSWORD")) {
                    String envValue = System.getenv("ADMIN_PASSWORD");
                    if (envValue != null) {
                        value = value.replace("ADMIN_PASSWORD", envValue);
                    }
                }

                properties.setProperty(name, value);
            }
        }
    }

    static final ChromeOptions chromeOptions;

    static {
        properties = new Properties();
        try (InputStream inputStream = ProjectUtils.class.getClassLoader().getResourceAsStream("local.properties")) {
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                try (InputStream fallback = ProjectUtils.class.getClassLoader().getResourceAsStream("test.properties")) {
                    if (fallback != null) {
                        properties.load(fallback);
                    } else {
                        log("No local.properties or test.properties found!");
                        System.exit(1);
                    }
                }
            }
            replaceEnvVariablesInProperties();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties file", e);
        }

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
