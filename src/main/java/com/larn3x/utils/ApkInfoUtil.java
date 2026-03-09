package com.larn3x.utils;

import com.larn3x.config.BaseConfiguration;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.larn3x.utils.DeviceUtil.executeSh;

public class ApkInfoUtil {

    private String apkInfo;

    public ApkInfoUtil() {
        String app = BaseConfiguration.getMobileConfig().app();
        if(app == null || app.isEmpty()){
            throw new RuntimeException("Нет значения для ключа 'app'");
        }
        try {
            apkInfo = executeSh("aapt dumb badging " + BaseConfiguration.getMobileConfig().app());
        } catch (IOException | InterruptedException | ExecutionException e){
            throw new RuntimeException(e);
        }
    }

    public String getAppPackageFromApk() {
        return findGroup1ValueFromString(apkInfo,"package: name='\\s*([^']+?)\\s*'");
    }

    public String getAppMainActivity()  {
        return findGroup1ValueFromString(apkInfo, "launchable-activity: name='\\s*([^']+?)\\s*'");
    }

    private static String findGroup1ValueFromString(String text, String regex){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        if(matcher.find()){
            return matcher.group(1);
        }
        return null;
    }
}