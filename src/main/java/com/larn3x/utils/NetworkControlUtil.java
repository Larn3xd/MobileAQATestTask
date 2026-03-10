package com.larn3x.utils;

import io.appium.java_client.android.AndroidDriver;

import java.util.HashMap;
import java.util.Map;

import static com.larn3x.utils.NetworkControlUtil.NetworkType.MOBILE_DATA;
import static com.larn3x.utils.NetworkControlUtil.NetworkType.WIFI;

public class NetworkControlUtil {

    private static final String MOBILE_SHELL = "mobile: shell";

    private AndroidDriver driver;

    public enum NetworkType {
        WIFI,
        MOBILE_DATA
    }

    public NetworkControlUtil(AndroidDriver driver) {
        this.driver = driver;
    }

    public void disableInternet() {
        setNetworkState(WIFI, false);
        setNetworkState(MOBILE_DATA, false);
    }

    public void setNetworkState(NetworkType netType, boolean enable) {
        String command = "svc " + (netType.equals(WIFI) ? "wifi " : "data ") + (enable ? "enable" : "disable");
        executeShell(command);
        try { Thread.sleep(1500); } catch (InterruptedException ignored) {}
    }

    private void executeShell(String command) {
        Map<String, Object> cmd = new HashMap<>();
        cmd.put("command", command);
        driver.executeScript(MOBILE_SHELL, cmd);
    }

    public boolean isNetworkEnabled(NetworkType netType) {
        Map<String, Object> cmd = new HashMap<>();
        cmd.put("command", "settings get global " + (netType.equals(WIFI) ? "wifi_on" : "mobile_data"));
        String result = (String) driver.executeScript(MOBILE_SHELL, cmd);
        return "1".equals(result.trim());
    }
}