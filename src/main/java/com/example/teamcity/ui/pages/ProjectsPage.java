package com.example.teamcity.ui.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.example.teamcity.ui.elements.ProjectElement;
import com.example.teamcity.ui.pages.favorites.FavoritesPage;

import java.util.List;

import static com.codeborne.selenide.Selectors.byClassName;
import static com.codeborne.selenide.Selenide.$$;

public class ProjectsPage extends FavoritesPage{
    private final static String FAVORITE_PROJECTS_URL = "/favorite/projects";

//    * private ElementsCollection subprojects = elements(new ByAttribute("class", "Subproject__container--WE"));
//    * private ElementsCollection subprojects = elements(Selectors.byClass("Subproject__container--WE"));
    private final ElementsCollection subprojects = $$(byClassName("Subproject__container--WE"));

    // ElementsCollection -> List<ProjectElement>
        public ProjectsPage open() {
            Selenide.open(FAVORITE_PROJECTS_URL);
            waitUntilFavoritePageIsLoaded();
            return this;
        }

        public List<ProjectElement> getSubprojects() {

//            return generatePageElements(subprojects);
            return generatePageElements(subprojects, ProjectElement::new);    // ProjectElement::new - function (ProjectElement - expected type, new - returns element )
        }


}
