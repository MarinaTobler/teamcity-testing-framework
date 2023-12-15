package com.example.teamcity.ui.elements;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

import static com.codeborne.selenide.Selectors.byAttribute;
import static com.codeborne.selenide.Selenide.$;
@Getter
public class ProjectElement extends PageElement{

    private final SelenideElement header;
    private final SelenideElement icon;

    public ProjectElement(SelenideElement element) {
        super(element);

//        this.header = element.find(Selectors.byDataTest("subproject"));
//      *  this.header = findElement(Selectors.byDataTest("subproject"));
        this.header = $(byAttribute("data-test","subproject"));
//        this.icon = element.find("svg");
//      *  this.icon = findElement("svg");
        this.icon = $("svg");
    }
}
