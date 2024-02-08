package com.example.teamcity.api.CreateProjectTests;

import com.example.teamcity.api.BaseApiTest;
import com.example.teamcity.api.generators.RandomData;
import com.example.teamcity.api.models.NewProjectDescription;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.requests.unchecked.UncheckedProject;
import com.example.teamcity.api.spec.Specifications;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

public class ProjectInvalidNameTests extends BaseApiTest {
    @Test(groups = {"Regression"})
    public void emptyNameProjectTest() {
        // Equivalence Partitioning:
        // 2. Invalid classes:

        String emptyName = "";

        var projectId = RandomData.getValidID();

        // Create project with unchecked request by superUser
        uncheckedWithSuperUser.getProjectRequest()
                .create(NewProjectDescription.builder()
                        .parentProject(Project.builder()
                                .locator("_Root")
                                .build())
                        .name(emptyName)
                        .id(projectId)
                        .copyAllAssociatedSettings(true)
                        .build())
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString("Project name cannot be empty"));

        // Check project not found and response message
        new UncheckedProject(Specifications.getSpec().superUserSpec()).get(projectId)
                .then().assertThat().statusCode(HttpStatus.SC_NOT_FOUND)
                .body(Matchers.containsString("No project found by locator" +
                        " 'count:1,id:" + projectId + "'"));

        // ? TODO: Delete project if status code would be 200 (would be created)
        // new UncheckedProject(Specifications.getSpec().superUserSpec()).delete(projectId);
    }
    @Test(groups = {"Regression"})
    public void uniqueNameProjectTest() {
        // 1. Create project with Name
        // 2. Create another project with the same Name
        // 3. Check second project status and  response message.
        // 4. delete project1

        var name = "name1";

        var project1 = checkedWithSuperUser.getProjectRequest()
                .create(NewProjectDescription.builder()
                        .parentProject(Project.builder()
                                .locator("_Root")
                                .build())
                        .name(name)
                        .id(RandomData.getValidID())
                        .copyAllAssociatedSettings(true)
                        .build());

        uncheckedWithSuperUser.getProjectRequest()
                .create(NewProjectDescription.builder()
                        .parentProject(Project.builder()
                                .locator("_Root")
                                .build())
                        .name(name)
                        .id(RandomData.getValidID())
                        .copyAllAssociatedSettings(true)
                        .build())
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString("Project with this name already exists: " + project1.getName()));

        new UncheckedProject(Specifications.getSpec().superUserSpec()).delete(project1.getId());
    }

}

