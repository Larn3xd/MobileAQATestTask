package com.larn3x.pages.alchemypuzzle;

import com.codeborne.selenide.SelenideElement;
import com.larn3x.pages.BasePage;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Selenide.$x;

public class AlchemyMainPage extends BasePage {

    private final SelenideElement playButton = $x("//android.widget.TextView[@text='Play']/following-sibling::android.widget.Button");

    @Step("Нажимаем на кнопку играть главной страницы")
    public AlchemyMainPage pressPlayButton() {
        click(playButton);
        playButton.should(disappear);

        return this;
    }
}