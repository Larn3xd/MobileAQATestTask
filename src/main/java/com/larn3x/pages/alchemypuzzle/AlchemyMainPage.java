package com.larn3x.pages.alchemypuzzle;

import com.codeborne.selenide.SelenideElement;
import com.larn3x.pages.BasePage;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Selenide.$x;

public class AlchemyMainPage extends BasePage {

    private final SelenideElement playButton = $x("//android.widget.TextView[@text='Play']/following-sibling::android.widget.Button");

    public AlchemyMainPage pressPlayButton() {
        click(playButton);
        playButton.should(disappear);

        return this;
    }
}