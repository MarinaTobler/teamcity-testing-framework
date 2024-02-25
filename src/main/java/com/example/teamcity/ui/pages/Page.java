package com.example.teamcity.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.elements.PageElement;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static com.codeborne.selenide.Selectors.byAttribute;
import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selenide.$;

public abstract class Page {
//   *  private SelenideElement submitButton = element(Selectors.byType("submit"));
        private final SelenideElement submitButton = $(byAttribute("type", "submit"));

//   *  private SelenideElement savingWaitingMarker = element(Selectors.byId("saving"));
        private final SelenideElement savingWaitingMarker = $(byId("saving"));

//   *  private SelenideElement pageWaitingMarker = element(Selectors.byDataTest("ring-loader"));
        private final SelenideElement pageWaitingMarker = $(byAttribute("data-test", "ring-loader"));



        public final void submit() {
                submitButton.click();
                waitUntilDataIsSaved();
        }

        public final void waitUntilPageIsLoaded() {
                pageWaitingMarker.shouldNotBe(Condition.visible, Duration.ofMinutes(1));
        }
        public final void waitUntilDataIsSaved() {
                savingWaitingMarker.shouldNotBe(Condition.visible, Duration.ofSeconds(30));
        }

        public final <T extends PageElement> List<T> generatePageElements(ElementsCollection collection,
                                                                          Function<SelenideElement, T> creator) {
//                var elements = new ArrayList<ProjectElement>();
                var elements = new ArrayList<T>();

//                collection.forEach(webElement -> {
//                                var pageElement = new ProjectElement(webElement);
//                                elements.add(pageElement);
//                        }
//                collection.forEach(webElement -> elements.add(new ProjectElement(webElement)));

                // creating element by webElement
                collection.forEach(webElement -> elements.add(creator.apply(webElement)));

                return elements;
        }
}
