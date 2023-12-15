package com.example.teamcity.api.CreateProjectTests;

import com.example.teamcity.api.BaseApiTest;
import com.example.teamcity.api.generators.RandomData;
import com.example.teamcity.api.requests.checked.CheckedProject;
import com.example.teamcity.api.requests.checked.CheckedUser;
import com.example.teamcity.api.spec.Specifications;
import org.testng.annotations.Test;

public class ProjectCanBeCreatedByValidUserTest extends BaseApiTest {
    @Test
    public void projectCanBeCreatedByValidUser() {

        var testData = testDataStorage.addTestData();

        // Create valid user by superUser to create project by valid user

       var validUser = new CheckedUser(Specifications.getSpec().superUserSpec())
              .create(testData.getUser());

        var projectId = RandomData.getValidID();

        // Create project by valid user
//        var project = new CheckedProject(Specifications.getSpec()
//                .authSpec(testData.getUser()))
//                .create(NewProjectDescription.builder()
//                        .parentProject(Project.builder()
//                                .locator("_Root")
//                                .build())
//                        .name(RandomData.getValidName())
////                        .id(RandomData.getValidID())
//                        .id(projectId)
//                        .copyAllAssociatedSettings(true)
//                        .build());



//        var project = new CheckedProject(Specifications.getSpec()
//                .authSpec(testData.getUser()))
//                .create(NewProjectDescription.builder()
//                        .parentProject(Project.builder()
//                                .locator("_Root")
//                                .build())
//                        .name(testData.getProject().getName())
//                        .id(testData.getProject().getId())
//                        .copyAllAssociatedSettings(true)
//                        .build());


        var project = new CheckedProject(Specifications.getSpec()
                .authSpec(testData.getUser()))
                .create(testData.getProject());

        // Check project is created
//        softy.assertThat(project.getId()).isEqualTo(projectId);
        softy.assertThat(project.getId()).isEqualTo(testData.getProject().getId());


        // No need to delete, as testData is deleting in AfterMethod in BaseApiTest!
        // Delete created project by superUser, ad created Admin pwd and username are "admin", "admin", not that created
        // new UncheckedProject(Specifications.getSpec().authSpec(validUser)).delete(project.getId());
        // new UncheckedProject(Specifications.getSpec().superUserSpec()).delete(project.getId());
    }
}
