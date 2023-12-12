package com.example.teamcity.api.CreateProjectTests;

import com.example.teamcity.api.BaseApiTest;
import com.example.teamcity.api.generators.RandomData;
import com.example.teamcity.api.models.NewProjectDescription;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.requests.unchecked.UncheckedProject;
import com.example.teamcity.api.spec.Specifications;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ProjectValidIdTests extends BaseApiTest {
    @DataProvider
    public static Object[][] validIdTestData() {
        // Equivalence Partitioning:
        // 1. Valid classes:

        // a) ID starts with a latin letter and contains only latin letters, digits and underscores (at most 225 characters).
        // For example:
        // String a = "t_abc_123";
        String a = "t" + RandomData.getLettersAndNumbers();

        // b) ID starts with a latin letter and contains only latin letters (at most 225 characters).
        // For example:
        // String b = "tabc";
        String b = "t" + RandomData.getOnlyLatinLetters();

        // c) ID starts with a latin letter and contains only digits (at most 225 characters).
        // For example:
        // static String c = "t123";
        String c = "t" + RandomData.getOnlyNumbers();

        // d) ID starts with a latin letter and contains only underscores (at most 225 characters).
        // For example:
        String d = "t______";

        // Boundary Value Analysis:
        // e) ID contains 225 characters.
        String e = RandomData.getString225();

        return new Object[][]{{a}, {b}, {c}, {d}, {e}};
    }

    @Test(dataProvider = "validIdTestData")
    public void positiveProjectIdTest(String val) {

        // Create project by superUser
        var project = checkedWithSuperUser.getProjectRequest()
                .create(NewProjectDescription.builder()
                        .parentProject(Project.builder()
                                .locator("_Root")
                                .build())
                        .name(RandomData.getValidName())
                        .id(val)
                        .copyAllAssociatedSettings(true)
                        .build());

        // Check project is created
        softy.assertThat(project.getId()).isEqualTo(val);

        // Delete created project by superUser
        new UncheckedProject(Specifications.getSpec().superUserSpec()).delete(val);
    }
}