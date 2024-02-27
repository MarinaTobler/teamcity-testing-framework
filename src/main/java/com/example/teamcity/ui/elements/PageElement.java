package com.example.teamcity.ui.elements;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

public abstract class PageElement {
    private final SelenideElement element;

    public PageElement(SelenideElement element) {
        this.element = element;
    }

    public final SelenideElement findElement(By by) {
        return element.find(by);
    }

    public final SelenideElement findElement(String value) {
        return element.find(value);
    }

    public final ElementsCollection findElements(By by) {
        return element.$$(by);
    }
}
