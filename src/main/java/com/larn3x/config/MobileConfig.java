package com.larn3x.config;

import org.aeonbits.owner.Config;


@Config.Sources("file:src/test/resources/config/${test.run.type}/${app.name}.properties")
public interface MobileConfig extends Config {

    @Key("deviceName")
    String deviceName();

    @Key("platformName")
    String platformName();

    @Key("platformVersion")
    String platformVersion();

    @Key("appPackage")
    String appPackage();

    @Key("appActivity")
    String appActivity();

    @Key("remoteURL")
    String remoteURL();

    @Key("app")
    String app();

    @Key("automationName")
    @DefaultValue("UiAutomator2")
    String automationName();
}