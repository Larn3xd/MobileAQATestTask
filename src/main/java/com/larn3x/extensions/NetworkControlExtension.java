package com.larn3x.extensions;

import com.larn3x.annotations.WithoutInternetConnectivity;
import com.larn3x.utils.NetworkControlUtil;
import io.appium.java_client.android.AndroidDriver;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class NetworkControlExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {
    private Boolean originalWifiState;
    private Boolean originalMobileDataState;

    @Override
    public void beforeTestExecution(ExtensionContext context) {
        if (shouldDisableInternet(context)) {
            AndroidDriver driver = (AndroidDriver) getWebDriver();
            NetworkControlUtil netHelper = new NetworkControlUtil(driver);

            originalWifiState = netHelper.isWifiEnabled();
            originalMobileDataState = netHelper.isMobileDataEnabled();

            netHelper.disableInternet();
        }
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {
        if (originalWifiState != null && originalMobileDataState != null) {
            AndroidDriver driver = (AndroidDriver) getWebDriver();
            NetworkControlUtil netHelper = new NetworkControlUtil(driver);

            if (originalWifiState) netHelper.setWifiState(true);
            if (originalMobileDataState) netHelper.setMobileDataState(true);
        }
    }

    private boolean shouldDisableInternet(ExtensionContext context) {
        return context.getElement()
                .map(element -> element.isAnnotationPresent(WithoutInternetConnectivity.class))
                .orElse(false);
    }
}
