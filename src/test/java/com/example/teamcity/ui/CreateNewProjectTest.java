package com.example.teamcity.ui;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.example.teamcity.api.requests.checked.CheckedProjects;
import com.example.teamcity.api.spec.Specifications;
import com.example.teamcity.ui.elements.ProjectElement;
import com.example.teamcity.ui.pages.ProjectsPage;
import com.example.teamcity.ui.pages.admin.CreateNewProject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;

public class CreateNewProjectTest extends BaseUiTest {
    @Test(groups = {"Regression"})
    public void authorizedUserShouldBeAbleCreateNewProject() {
        var testData = testDataStorage.addTestData();
        var url = "https://github.com/AlexPshe/spring-core-for-qa";

        // login as systemAdmin, as in testData was generated systemAdmin
        loginAsUser(testData.getUser());

        new CreateNewProject()
                .open(testData.getProject().getParentProject().getLocator())
                .createProjectByUrl(url)
                .setupProject(testData.getProject().getName(), testData.getBuildType().getName());

        new ProjectsPage().open()
                .getSubprojects()
                .stream().reduce((first, second) -> second).get()   // = last ProjectElement
                .getHeader().shouldHave(text(testData.getProject().getName()));

        var project = new CheckedProjects(Specifications.getSpec().superUserSpec()).getProjects().getProject()
                .stream().filter(p -> p.getName().equals(testData.getProject().getName()))
                .findFirst();

        softy.assertThat(project.get().getName()).isEqualTo(testData.getProject().getName());
    }

    @Test(groups = {"Regression"})
    public void authorizedUserShouldNotBeAbleCreateNewProjectWithoutProjectName() {
        var testData = testDataStorage.addTestData();
        var url = "https://github.com/AlexPshe/spring-core-for-qa";
        var projectName = "";


        var projectsBeforeCreatingProject = new CheckedProjects(Specifications.getSpec().superUserSpec())
                .getProjects();


        loginAsUser(testData.getUser());

        new CreateNewProject()
                .open(testData.getProject().getParentProject().getLocator())
                .createProjectByUrl(url)
                .setupProject(projectName, testData.getBuildType().getName());

        // Вариант 1:
        ElementsCollection subprojects = Selenide.$$(new ProjectsPage().open()
                .getSubprojects().stream().map(ProjectElement::getHeader).toList());

        subprojects.excludeWith(exactText(projectName)).isEmpty();

        // Вариант 2:
        new ProjectsPage().open()
                .getSubprojects()
                .stream().reduce((first, second) -> second).get()
                .getHeader().shouldNotHave(text(testData.getProject().getName()));


        var projectsAfterCreatingProject = new CheckedProjects(Specifications.getSpec().superUserSpec())
                .getProjects();
        Assert.assertEquals(projectsBeforeCreatingProject, projectsAfterCreatingProject);
    }

    @Test(groups = {"Regression"})
    public void authorizedUserShouldNotBeAbleCreateNewProjectWithoutBuildConfigName() {
        var testData = testDataStorage.addTestData();
        var url = "https://github.com/AlexPshe/spring-core-for-qa";
        var buildTypeName = "";


        var projectsBeforeCreatingProject = new CheckedProjects(Specifications.getSpec().superUserSpec())
                .getProjects();


        loginAsUser(testData.getUser());

        new CreateNewProject()
                .open(testData.getProject().getParentProject().getLocator())
                .createProjectByUrl(url)
                .setupProject(testData.getProject().getName(), buildTypeName);

        ElementsCollection subprojects = Selenide.$$(new ProjectsPage().open()
                .getSubprojects().stream().map(ProjectElement::getHeader).toList());

        subprojects.excludeWith(exactText(testData.getProject().getName())).isEmpty();


        var projectsAfterCreatingProject = new CheckedProjects(Specifications.getSpec().superUserSpec())
                .getProjects();
        Assert.assertEquals(projectsBeforeCreatingProject, projectsAfterCreatingProject);
    }
}