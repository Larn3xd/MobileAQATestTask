package com.larn3x.tests.alchemy;

import com.larn3x.annotations.allure.AppType;
import com.larn3x.annotations.allure.TestLayer;
import com.larn3x.annotations.apps.AlchemyPuzzle;
import com.larn3x.tests.MobileBaseTest;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.larn3x.pages.Pages.*;
import static io.qameta.allure.Allure.step;

@AppType("mobile")
@TestLayer("UI")
@AlchemyPuzzle
@DisplayName("Тестирование приложения Алхимия: Головоломка")
public class AlchemyPuzzleTest extends MobileBaseTest {

    @Test
    @Owner("Якушев Кирилл")
    @DisplayName("Проверка получения подсказки")
    public void alchemyClaimHintCountTest() {
        step("Нажимаем 'Play'", alchemyMainPage::pressPlayButton);

        step("Нажимаем на кнопку бесплатные подсказки", alchemyPuzzlePage::pressFreeHintsButton);

        step("Нажимаем получить подсказку", alchemyHintSlideBar::pressClaimHintsButton);

        step("Проверяем, что подсказки появились и значение равно 4", () ->
                alchemyPuzzlePage.verifyThatHintsCountIsEqualTo(4));
    }
}
