package com.larn3x.extensions;

import com.larn3x.annotations.WithoutInternetConnectivity;
import com.larn3x.utils.NetworkControlUtil;
import io.appium.java_client.android.AndroidDriver;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static com.larn3x.utils.NetworkControlUtil.NetworkType.MOBILE_DATA;
import static com.larn3x.utils.NetworkControlUtil.NetworkType.WIFI;

public class NetworkControlExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {
    private Boolean originalWifiState;
    private Boolean originalMobileDataState;

    @Override
    public void beforeTestExecution(ExtensionContext context) {
        if (shouldDisableInternet(context)) {
            NetworkControlUtil netHelper = new NetworkControlUtil((AndroidDriver) getWebDriver());

            originalWifiState = netHelper.isNetworkEnabled(WIFI);
            originalMobileDataState = netHelper.isNetworkEnabled(MOBILE_DATA);

            netHelper.disableInternet();
        }
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {
        if (originalWifiState != null && originalMobileDataState != null) {
            NetworkControlUtil netHelper = new NetworkControlUtil((AndroidDriver) getWebDriver());

            if (originalWifiState) netHelper.setNetworkState(WIFI, true);
            if (originalMobileDataState) netHelper.setNetworkState(MOBILE_DATA, true);
        }
    }

    private boolean shouldDisableInternet(ExtensionContext context) {
        return context.getElement()
                .map(element -> element.isAnnotationPresent(WithoutInternetConnectivity.class))
                .orElse(false);
    }
}
