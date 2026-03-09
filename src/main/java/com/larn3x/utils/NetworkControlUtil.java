package com.larn3x.utils;

import io.appium.java_client.android.AndroidDriver;

import java.util.HashMap;
import java.util.Map;

public class NetworkControlUtil {

    private AndroidDriver driver;

    public NetworkControlUtil(AndroidDriver driver) {
        this.driver = driver;
    }

    public void disableInternet() {
        setWifiState(false);
        setMobileDataState(false);
    }

    public void setWifiState(boolean enable) {
        String command = "svc wifi " + (enable ? "enable" : "disable");
        executeShell(command);
        try { Thread.sleep(1500); } catch (InterruptedException ignored) {}
    }

    public void setMobileDataState(boolean enable) {
        String command = "svc data " + (enable ? "enable" : "disable");
        executeShell(command);
        try { Thread.sleep(1500); } catch (InterruptedException ignored) {}
    }

    private void executeShell(String command) {
        Map<String, Object> cmd = new HashMap<>();
        cmd.put("command", command);
        driver.executeScript("mobile: shell", cmd);
    }

    public boolean isWifiEnabled() {
        Map<String, Object> cmd = new HashMap<>();
        cmd.put("command", "settings get global wifi_on");
        String result = (String) driver.executeScript("mobile: shell", cmd);
        return "1".equals(result.trim());
    }

    public boolean isMobileDataEnabled() {
        Map<String, Object> cmd = new HashMap<>();
        cmd.put("command", "settings get global mobile_data");
        String result = (String) driver.executeScript("mobile: shell", cmd);
        return "1".equals(result.trim());
    }
}