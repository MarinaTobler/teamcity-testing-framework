package com.example.teamcity.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.element;


@Getter
public final class StartUpPage extends Page {


//private static final String LOGIN_PAGE_URL = "/login.html";

    @Getter
    private final SelenideElement proceedButton = $(byId("proceedButton"));
    private final SelenideElement acceptLicense = $(byId("accept"));
    private final SelenideElement restoreFromBackupButton = element("input[id='restoreButton']");
    // private final SelenideElement backFileUploaded = element("password");
    private final SelenideElement continueButton = $(byName("Continue"));
    private final SelenideElement header = $(byId("header"));


    public StartUpPage open() {
        Selenide.open("/");
        return this;
    }

    public StartUpPage setupTeamCityServer() {
        waitUntilPageIsLoaded();
        proceedButton.click();
        waitUntilPageIsLoaded();
        proceedButton.click();
        waitUntilPageIsLoaded();
//        acceptLicense.shouldBe(Condition.enabled, Duration.ofMinutes(5));
        acceptLicense.shouldBe(Condition.enabled, LONG_WAITING);
        acceptLicense.scrollTo();
        acceptLicense.click();
        continueButton.click();
        return this;
    }
}
