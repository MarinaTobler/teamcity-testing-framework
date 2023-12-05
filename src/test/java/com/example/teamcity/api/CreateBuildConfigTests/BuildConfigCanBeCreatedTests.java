package com.example.teamcity.api.CreateBuildConfigTests;

import com.example.teamcity.api.BaseApiTest;
import com.example.teamcity.api.generators.RandomData;
import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.api.models.NewProjectDescription;
import com.example.teamcity.api.requests.checked.CheckedBuildConfig;
import com.example.teamcity.api.requests.checked.CheckedProject;
import com.example.teamcity.api.spec.Specifications;
import org.testng.annotations.Test;

public class BuildConfigCanBeCreatedTests extends BaseApiTest {

    @Test
    public void buildConfigCanBeCreated() {

        var testData = testDataStorage.addTestData();

        var project = new CheckedProject(Specifications.getSpec().superUserSpec())
                .create(testData.getProject());

        var newProjectDescription = NewProjectDescription
                .builder()
                .parentProject(project)
                .name(RandomData.getValidName())
                .id(RandomData.getValidID())
                .copyAllAssociatedSettings(true)
                .build();

        var buildConfigId = RandomData.getString256();

        var buildConfig = new CheckedBuildConfig(Specifications.getSpec().superUserSpec())
                .create(BuildType.builder()
                        .id(buildConfigId)
                        .name(RandomData.getString80())
                        .project(newProjectDescription)
                        .description(RandomData.getString226())
                        .build());

        softy.assertThat(buildConfig.getId()).isEqualTo(buildConfigId);
    }
}
