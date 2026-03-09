package com.larn3x.pages.vkvideo;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.larn3x.pages.BasePage;
import io.appium.java_client.AppiumBy;

import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.Random;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class VkVideoMainPage extends BasePage {

    private final ElementsCollection loadingPulseBars = $$x("//android.widget.FrameLayout[@resource-id='com.vk.vkvideo:id/video']");

    private final SelenideElement onboardWidget = $(AppiumBy.id("com.vk.vkvideo:id/onboarding_image"));

    private final SelenideElement closeOnboardWidgetButton = $(AppiumBy.id("com.vk.vkvideo:id/close_btn_left"));

    private final SelenideElement signInWidget = $(AppiumBy.id("com.vk.vkvideo:id/design_bottom_sheet"));

    private final SelenideElement skipButton = $(AppiumBy.id("com.vk.vkvideo:id/fast_login_tertiary_btn"));

    private final ElementsCollection recommendVideos = $$x("//android.widget.ImageView[@resource-id='com.vk.vkvideo:id/preview']");

    public void handleSignInWidgetIfPresent() {
        loadingPulseBars.forEach(loadingBar -> loadingBar.should(disappear));
        try {
            if(onboardWidget.is(visible, Duration.ofSeconds(5))) {
                click(closeOnboardWidgetButton);
                onboardWidget.should(disappear);
            }

            if(signInWidget.is(visible, Duration.ofSeconds(5))) {
                click(skipButton);
                skipButton.should(disappear);
            }
        } catch (AssertionError | NoSuchElementException _) {}
    }

    public VkVideoMainPage openRecommendVideo() {
        SelenideElement video = recommendVideos.get(new Random().nextInt(0, recommendVideos.size() - 1));
        click(video);

        return this;
    }
}