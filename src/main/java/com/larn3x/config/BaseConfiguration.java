package com.larn3x.config;

import com.larn3x.runtype.TestRunType;
import org.aeonbits.owner.ConfigFactory;

import java.util.HashMap;
import java.util.Map;

public class BaseConfiguration {
    private static final ThreadLocal<MobileConfig> mobileConfig = new ThreadLocal<>();
    private static final ThreadLocal<TestRunType> runType = new ThreadLocal<>();

    public static void init(String appName, TestRunType type) {
        runType.set(type);

        Map<String, String> params = new HashMap<>();
        params.put("test.run.type", type.name().toLowerCase());
        params.put("app.name", appName);

        System.setProperty("test.run.type", type.name().toLowerCase());
        System.setProperty("app.name", appName);
        MobileConfig config = ConfigFactory.create(MobileConfig.class, params);
        mobileConfig.set(config);
    }

    public static MobileConfig getMobileConfig() {
        return mobileConfig.get();
    }

    public static TestRunType getRunType() {
        return runType.get();
    }

    public static void clear() {
        mobileConfig.remove();
        runType.remove();
    }
}