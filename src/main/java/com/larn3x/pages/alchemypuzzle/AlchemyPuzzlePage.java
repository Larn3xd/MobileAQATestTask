package com.larn3x.pages.alchemypuzzle;

import com.codeborne.selenide.SelenideElement;
import com.larn3x.pages.BasePage;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;
import static org.assertj.core.api.Assertions.assertThat;

public class AlchemyPuzzlePage extends BasePage {

    private final SelenideElement freeHintsButton = $x("//android.view.View[@content-desc='Free hints']/following-sibling::android.widget.Button");
    private final SelenideElement hintsCount = $x("//android.widget.TextView[contains(@text, 'Hints')]");

    @Step("Нажимаем на кнопку 'бесплатные подсказки'")
    public AlchemyPuzzlePage pressFreeHintsButton() {
        click(freeHintsButton);
        freeHintsButton.shouldNotBe(interactable);

        return this;
    }

    @Step("Проверяем количество подсказок после получения")
    public AlchemyPuzzlePage verifyThatHintsCountIsEqualTo(int expectedCount) {
        hintsCount.shouldBe(visible);
        assertThat(hintsCount.getText())
                .as("Проверка того, что подсказок стало 4")
                .endsWith("(" + expectedCount + ")");

        return this;
    }
}
