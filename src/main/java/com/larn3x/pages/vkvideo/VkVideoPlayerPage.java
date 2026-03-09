package com.larn3x.pages.vkvideo;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.SelenideElement;
import com.larn3x.pages.BasePage;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Selenide.*;
import static com.larn3x.utils.TimeUtils.convertTimeLineToTotalSeconds;
import static org.assertj.core.api.Assertions.*;

public class VkVideoPlayerPage extends BasePage {

    private final SelenideElement videoPlayer = $(AppiumBy.id("com.vk.vkvideo:id/playerContainer"));

    private final SelenideElement timeSeekBar = $(AppiumBy.id("com.vk.vkvideo:id/seek_bar"));

    private final SelenideElement videoSpinLoader = $(AppiumBy.id("com.vk.vkvideo:id/progress_view"));

    private final SelenideElement noConnectionPopup = $x("//android.widget.TextView[@resource-id='com.vk.vkvideo:id/title'][contains(@text, 'No internet')]");

    private final SelenideElement timeCurrentProgress = $(AppiumBy.id("com.vk.vkvideo:id/current_progress"));

    public VkVideoPlayerPage verifyThatOpenedVideoIsRunningMoreThan(int seconds) {
        if (seconds > 30 || seconds < 10) throw new RuntimeException("Допустимый лимит в секундах: не меньше 10 и не больше 30");

        shouldBeVisible(videoPlayer);
        try {
            new FluentWait<>(webdriver())
                    .withTimeout(Duration.ofSeconds(seconds))
                    .pollingEvery(Duration.ofMillis(3500))
                    .until(_ -> {
                        sleep(500L);

                        videoPlayer.click(ClickOptions.withOffset(-200, -40));
                        String timeSeekStringValue = timeSeekBar.should(appear).getText();
                        double timeLineValue = Double.parseDouble(timeSeekStringValue);
                        return timeLineValue >= seconds;
                    });
        }
        catch (org.openqa.selenium.TimeoutException e) {
            throw new AssertionError("Видео дальше не воспроизводится, либо зависло");
        }

        String currentTimeSeekStringValue = timeSeekBar.getText();
        String currentTimeProgressStringValue = timeCurrentProgress.getText();

        int currentTimeSeekValue = Integer.parseInt(currentTimeSeekStringValue.replaceFirst("\\.0\\s*$", ""));
        int currentTimeProgress = convertTimeLineToTotalSeconds(extractTimeStart(currentTimeProgressStringValue));

        assertThat(currentTimeProgress)
                .as("Проверка пройденного времени видео по ползунку прокрутки и таймеру")
                .isCloseTo(currentTimeSeekValue, within(1));

        return this;
    }

    public VkVideoPlayerPage verifyThatOpenedVideoIsNotRunning() {
        shouldBeVisible(videoPlayer);

        new FluentWait<>(webdriver())
                .withTimeout(Duration.ofSeconds(15))
                .pollingEvery(Duration.ofSeconds(5))
                .until(_ -> {
                    videoPlayer.hover().click(ClickOptions.withOffset(-200, -40));
                    return videoSpinLoader.should(appear);
                });

        assertThat(noConnectionPopup.is(appear, Duration.ofSeconds(5)))
                .as("Проверка того, что видео не воспроизводится")
                .isTrue();

        return this;
    }

    private String extractTimeStart(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        int slashIndex = input.indexOf('/');
        if (slashIndex == -1) {
            return input.trim();
        }
        return input.substring(0, slashIndex).trim();
    }
}