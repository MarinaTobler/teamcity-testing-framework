package com.example.teamcity.api.CreateBuildConfigTests;

import com.example.teamcity.api.BaseApiTest;
import com.example.teamcity.api.generators.RandomData;
import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.api.requests.checked.CheckedBuildConfig;
import com.example.teamcity.api.requests.checked.CheckedProject;
import com.example.teamcity.api.requests.unchecked.UncheckedProject;
import com.example.teamcity.api.spec.Specifications;
import org.testng.annotations.Test;

public class BuildConfigCanBeCreatedTests extends BaseApiTest {

    @Test
    public void buildConfigCanBeCreated() {

        var testData = testDataStorage.addTestData();

        var projectDescription = testData.getProject();

        // create project by superUser
        var project = new CheckedProject(Specifications.getSpec().superUserSpec())
                .create(projectDescription);


//        var buildConfigId = RandomData.getString256(); --> Error has occurred during request processing, status code:
//        500 (Internal Server Error). Details: jetbrains.buildServer.serverSide.InvalidIdentifierException:
//        Build configuration or template ID

//        var buildConfigId = RandomData.getValidID();
        var buildConfigId = testData.getBuildType().getId();

        var buildConfig = new CheckedBuildConfig(Specifications.getSpec().superUserSpec())
                .create(BuildType.builder()
                        .id(buildConfigId)
                        .name(RandomData.getString80())
//                        .project(testData.getProject())
                        .project(projectDescription)
                        .description(RandomData.getString226())
                        .build());

        softy.assertThat(buildConfig.getId()).isEqualTo(buildConfigId);

//        new UncheckedProject(Specifications.getSpec().superUserSpec()).delete(project.getId());
//      No need to delete project, as testData is deleting in AfterMethod in BaseApiTest - buildConfig should be created
//      in same project from testData ??
        new UncheckedProject(Specifications.getSpec().superUserSpec()).delete(projectDescription.getId());
    }
}
