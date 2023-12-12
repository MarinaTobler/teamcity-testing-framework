package com.example.teamcity.api.CreateProjectTests;

import com.example.teamcity.api.BaseApiTest;
import com.example.teamcity.api.generators.RandomData;
import com.example.teamcity.api.models.NewProjectDescription;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.requests.checked.CheckedProject;
import com.example.teamcity.api.requests.checked.CheckedUser;
import com.example.teamcity.api.requests.unchecked.UncheckedProject;
import com.example.teamcity.api.spec.Specifications;
import org.testng.annotations.Test;

public class ProjectCanBeCreatedByValidUserTest extends BaseApiTest {
    @Test
    public void projectCanBeCreatedByValidUser() {

        var testData = testDataStorage.addTestData();

        // Create valid user by superUser to create project by valid user
        new CheckedUser(Specifications.getSpec().superUserSpec())
                .create(testData.getUser());

        // Create project by valid user
        var project = new CheckedProject(Specifications.getSpec()
                .authSpec(testData.getUser()))
                .create(NewProjectDescription.builder()
                        .parentProject(Project.builder()
                                .locator("_Root")
                                .build())
                        .name(RandomData.getValidName())
                        .id(RandomData.getValidID())
                        .copyAllAssociatedSettings(true)
                        .build());

        // Check project is created
        softy.assertThat(project.getId()).isEqualTo(RandomData.getValidID());

        // Delete created project by valid user
        new UncheckedProject(Specifications.getSpec().authSpec(testData.getUser())).delete(RandomData.getValidID());
    }
}
