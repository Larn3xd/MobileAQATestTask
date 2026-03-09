package com.larn3x.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;

import com.larn3x.config.BaseConfiguration;
import com.larn3x.config.MobileConfig;
import com.larn3x.extensions.NetworkControlExtension;
import com.larn3x.extensions.PopupWidgetHandlerExtension;
import com.larn3x.utils.AllureUtils;
import com.larn3x.runtype.TestRunType;
import com.larn3x.utils.ApkInfoUtil;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Allure;
import io.qameta.allure.selenide.AllureSelenide;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.ByteArrayInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;

import static com.codeborne.selenide.Selenide.*;
import static com.larn3x.utils.AnnotationUtils.getAppNameFromAnnotation;
import static io.qameta.allure.Allure.step;

@ExtendWith(PopupWidgetHandlerExtension.class)
@ExtendWith(NetworkControlExtension.class)
public class MobileBaseTest {

    protected static ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();
    protected MobileConfig mobileConfig;

    public static AppiumDriver getDriver() {
        return driver.get();
    }

    @BeforeEach
    public void setUp() throws MalformedURLException {
        String appName = getAppNameFromAnnotation(this.getClass());
        TestRunType runType = getRunType();

        BaseConfiguration.init(appName, runType);
        mobileConfig = BaseConfiguration.getMobileConfig();

        Configuration.browser = "android";
        Configuration.timeout = 10000;
        Configuration.reportsFolder = "build/reports/tests";

        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(true)
                .includeSelenideSteps(true));

        String appPackage = mobileConfig.appPackage();
        String appActivity = mobileConfig.appActivity();

        System.out.println("app from config: " + mobileConfig.app());
        System.out.println("deviceName from config: " + mobileConfig.deviceName());

        ApkInfoUtil helper = new ApkInfoUtil();
        appPackage = appPackage.isEmpty() ? helper.getAppPackageFromApk() : appPackage;
        appActivity = appActivity.isEmpty() ? helper.getAppMainActivity() : appActivity;

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("appium:deviceName", mobileConfig.deviceName());
        capabilities.setCapability("appium:platformName", mobileConfig.platformName());
        capabilities.setCapability("appium:platformVersion", mobileConfig.platformVersion());
        capabilities.setCapability("appium:automationName", mobileConfig.automationName());
        capabilities.setCapability("appium:appPackage", appPackage);
        capabilities.setCapability("appium:appActivity", appActivity);
        capabilities.setCapability("appium:noReset", false);
        capabilities.setCapability("appium:fullReset", true);

        if (runType == TestRunType.SELENOID) {
            capabilities.setCapability("enableVNC", true);
            capabilities.setCapability("enableVideo", true);
            capabilities.setCapability("enableLog", true);
        }

        String appPath = mobileConfig.app();
        if (appPath.startsWith("/") || appPath.contains(":")) {
            capabilities.setCapability("appium:app", appPath);
        } else {
            capabilities.setCapability("appium:app", Paths.get("src/test/resources/" + appPath).toAbsolutePath().toString());
        }

        AndroidDriver createdDriver = new AndroidDriver(new URL(mobileConfig.remoteURL()), capabilities);
        driver.set(createdDriver);
        WebDriverRunner.setWebDriver(createdDriver);

        step(String.format("Запуск мобильного приложения %s на эмуляторе %s, версия ОС %s",
                        appName,
                        mobileConfig.platformName(),
                        mobileConfig.platformVersion()),
                () -> open());

        Allure.label("app", appName);
        Allure.label("runType", runType.name());
        Allure.label("device", mobileConfig.deviceName());
    }

    @AfterEach
    public void tearDown() {
        try {
            if (WebDriverRunner.hasWebDriverStarted()) {
                AllureUtils.attachScreenshot("Screenshot attchament");
                AllureUtils.attachPageSource();
            }

            if (BaseConfiguration.getRunType() == TestRunType.SELENOID && driver != null) {
                String sessionId = getDriver().getSessionId().toString();
                String videoUrl = String.format("http://localhost:8080/video/%s.mp4", sessionId);
                Allure.addAttachment("Video", "video/mp4",
                        new ByteArrayInputStream(videoUrl.getBytes()), "mp4");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (getDriver() != null) {
                getDriver().quit();
            }
            step("Очистка конфигурации, закрытие браузера", () -> {
                BaseConfiguration.clear();
                closeWebDriver();
                driver.remove();
            });
        }
    }

    protected TestRunType getRunType() {
        String runType = System.getProperty("test.run.type", "local");
        return runType.equalsIgnoreCase("selenoid") ?
                TestRunType.SELENOID : TestRunType.LOCAL;
    }
}