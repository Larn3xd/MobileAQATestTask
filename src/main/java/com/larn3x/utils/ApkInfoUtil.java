package com.larn3x.utils;

import com.larn3x.config.BaseConfiguration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ApkInfoUtil {

    private String apkInfo;

    public ApkInfoUtil() {
        String app = BaseConfiguration.getMobileConfig().app();
        if (app == null || app.isEmpty()) {
            throw new RuntimeException("Нет значения для ключа 'app'");
        }
        try {
            apkInfo = executeCommand("aapt dumb badging " + BaseConfiguration.getMobileConfig().app());
        } catch (IOException | InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public String getAppPackageFromApk() {
        return findGroupValueFromString(apkInfo, "package: name='\\s*([^']+?)\\s*'");
    }

    public String getAppMainActivity() {
        return findGroupValueFromString(apkInfo, "launchable-activity: name='\\s*([^']+?)\\s*'");
    }

    private static String findGroupValueFromString(String text, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    private static String executeCommand(String command) throws IOException, ExecutionException, InterruptedException {
        Process p = Runtime.getRuntime().exec(command);

        FutureTask<String> future = new FutureTask<>(() -> new BufferedReader(new InputStreamReader(p.getInputStream()))
                .lines().map(Object::toString)
                .collect(Collectors.joining("\n")));

        new Thread(future).start();
        return future.get();
    }
}