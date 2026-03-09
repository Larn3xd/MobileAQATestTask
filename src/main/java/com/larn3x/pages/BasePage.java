package com.larn3x.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.clickable;

public class BasePage {

    private static final long DEFAULT_TIMEOUT = 30;

    public void shouldBeVisible(SelenideElement element) {
        element.shouldBe(Condition.visible, Duration.ofSeconds(DEFAULT_TIMEOUT));
    }

    public void click(SelenideElement element) {
        shouldBeVisible(element);
        element.shouldBe(clickable);
        element.click();
    }
}