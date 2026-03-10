package com.larn3x.utils;

import com.larn3x.annotations.apps.AlchemyPuzzle;
import com.larn3x.annotations.apps.VkVideo;

public class AnnotationUtils {
    public static String getAppNameFromAnnotation(Class<?> testClass) {
        if (testClass.isAnnotationPresent(VkVideo.class)) {
            return testClass.getAnnotation(VkVideo.class).value();
        }

        if (testClass.isAnnotationPresent(AlchemyPuzzle.class)) {
            return testClass.getAnnotation(AlchemyPuzzle.class).value();
        }

        throw new RuntimeException("Тестовый класс должен быть отмечен одной аннотацией приложения, например," +
                "@VkVideo");
    }
}