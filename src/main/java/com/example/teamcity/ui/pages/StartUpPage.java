package com.example.teamcity.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.element;


@Getter
public class StartUpPage extends Page {


    // 22:34

//private static final String LOGIN_PAGE_URL = "/login.html";

    @Getter
    private final SelenideElement
                            proceedButton = $(byId("proceedButton")),
                            acceptLicense = $(byId("accept")),
                            restoreFromBackupButton = element("input[id='restoreButton']"),
                         // backFileUploaded = element("password"),
                            continueButton = $(byName("Continue")),
                            header = $(byId("header"));


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
        acceptLicense.shouldBe(Condition.enabled, Duration.ofMinutes(5));
        acceptLicense.scrollTo();
        acceptLicense.click();
        continueButton.click();
        return this;
    }
}