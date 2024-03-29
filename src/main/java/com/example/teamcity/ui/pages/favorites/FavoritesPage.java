package com.example.teamcity.ui.pages.favorites;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.pages.Page;

import static com.codeborne.selenide.Selectors.byClassName;
import static com.codeborne.selenide.Selenide.$;

public class FavoritesPage extends Page {
//  *  private SelenideElement header = element(Selectors.byClass("ProjectPageHeader__title--ih"));
    private final SelenideElement header = $(byClassName("ProjectPageHeader__title--ih"));

    public final void waitUntilFavoritePageIsLoaded() {
        waitUntilPageIsLoaded();
//        header.shouldBe(Condition.visible, Duration.ofSeconds(10));
        header.shouldBe(Condition.visible, BASE_WAITING);
    }
}
