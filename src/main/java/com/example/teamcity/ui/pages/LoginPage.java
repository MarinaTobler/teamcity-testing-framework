package com.example.teamcity.ui.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.api.models.User;
import lombok.Getter;

import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selenide.$;
@Getter
public final class LoginPage extends Page {

    private static final String LOGIN_PAGE_URL = "/login.html";

//    private SelenideElement usernameInput = element(new ByAttribute("id", "username"));
//  *  private SelenideElement usernameInput = element(Selectors.byId("username"));
    private SelenideElement usernameInput = $(byId("username"));

//    private SelenideElement passwordInput = element(new ByAttribute("id", "password"));
//  *  private SelenideElement passwordInput = element(Selectors.byId("password"));
    private SelenideElement passwordInput = $(byId("password"));



    public LoginPage open() {
        Selenide.open(LOGIN_PAGE_URL);
        return this;
    }

    public void login(User user) {
        usernameInput.sendKeys(user.getUsername());
        passwordInput.sendKeys(user.getPassword());
        submit();
        waitUntilDataIsSaved();
    }
}
