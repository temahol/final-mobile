package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import drivers.BrowserstackMobileDriver;
import drivers.LocalMobileDriver;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

public class TestBase {

    public static String env = System.getProperty("devicehost", "android");

    @BeforeAll
    static void beforeAll() {

        switch (env) {
            case "local":
                Configuration.browser = LocalMobileDriver.class.getName();
                break;
            case "android":
                Configuration.browser = BrowserstackMobileDriver.class.getName();
                break;
            case "ios":
                Configuration.browser = BrowserstackMobileDriver.class.getName();
                break;
        }

        Configuration.browserSize = null;
    }

    @BeforeEach
    void addListener() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
        open();
    }

    @AfterEach
    void afterEach() {
        String sessionId = Selenide.sessionId().toString();

        Attach.pageSource();
        closeWebDriver();

        switch (env) {
            case "android":
                Attach.addVideo(sessionId);
                break;
            case "iphone":
                Attach.addVideo(sessionId);
                break;
        }
    }

}
