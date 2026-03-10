package com.larn3x.pages.alchemypuzzle;

import com.codeborne.selenide.SelenideElement;
import com.larn3x.pages.BasePage;
import io.appium.java_client.AppiumBy;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class AlchemyHintSlideBar extends BasePage {

    private final SelenideElement claimHintsButton = $x("//android.widget.TextView[@text='Claim!']/following-sibling::android.widget.Button");

    @Step("Нажимаем на получение бесплатных подсказок")
    public AlchemyHintSlideBar pressClaimHintsButton() {
        click(claimHintsButton);
        claimHintsButton.should(disappear);

        String regex = "^\\d{1,2}:\\d{2}$";
        SelenideElement activatedTimerText = $(AppiumBy.androidUIAutomator(
                String.format("new UiSelector().textMatches(\"%s\")", regex)
        ));
        activatedTimerText.should(appear);

        return this;
    }
}
