package com.larn3x.tests.vkvideo;

import com.larn3x.annotations.WithoutInternetConnectivity;
import com.larn3x.annotations.allure.AppType;
import com.larn3x.annotations.allure.TestLayer;
import com.larn3x.annotations.apps.VkVideo;
import com.larn3x.tests.MobileBaseTest;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.larn3x.pages.Pages.vkVideoMainPage;
import static com.larn3x.pages.Pages.vkVideoPlayerPage;
import static io.qameta.allure.Allure.step;

@AppType("mobile")
@TestLayer("UI")
@VkVideo
@DisplayName("Тестирование приложения VK Видео")
public class VkVideoTest extends MobileBaseTest {

    @Test
    @Owner("Якушев Кирилл")
    @DisplayName("Проверка воспроизведения видео")
    public void openAndCheckTheVideoIsRunningTest() {
        step("Открываем рекомендованное видео", vkVideoMainPage::openRecommendVideo);
        step("Проверяем, что видео проигрывается", () -> vkVideoPlayerPage.verifyThatOpenedVideoIsRunningMoreThan(20));
    }

    @Test
    @Owner("Якушев Кирилл")
    @DisplayName("Проверка невоспроизведения видео из-за отсутствия интернета - негативный сценарий")
    @WithoutInternetConnectivity
    public void openAndCheckTheVideoIsNotRunningNegativeTest() {
        step("Открываем рекомендованное видео", vkVideoMainPage::openRecommendVideo);
        step("Проверяем, что видео не проигрывается", vkVideoPlayerPage::verifyThatOpenedVideoIsNotRunning);
    }
}
