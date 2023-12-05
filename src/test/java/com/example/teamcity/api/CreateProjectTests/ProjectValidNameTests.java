package com.example.teamcity.api.CreateProjectTests;

import com.example.teamcity.api.BaseApiTest;
import com.example.teamcity.api.generators.RandomData;
import com.example.teamcity.api.models.NewProjectDescription;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.requests.checked.CheckedProject;
import com.example.teamcity.api.requests.checked.CheckedUser;
import com.example.teamcity.api.requests.unchecked.UncheckedProject;
import com.example.teamcity.api.spec.Specifications;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ProjectValidNameTests extends BaseApiTest {
    @DataProvider
    public static Object[][] validNameTestData() {
        // Equivalence Partitioning:

        // 1. Valid classes:

        // a) Name contains max 80 characters (as on FE maxlength = "80")
        String a = RandomData.getString();

        // Boundary Value Analysis:
        // b) Name contains more than 80 characters (as on FE maxlength = "80")
        String b = RandomData.getString81();

        // Error Guessing:

        // c) Name contains any characters.
        String c = RandomData.getRandomString();

        // d) Name contains spaces.
        // For example:
        String d = "t abc abc";

        // e) Name starts with space.
        // For example:
        String e = " tabc";

        // f) Name contains more than 1025characters.
        String f = RandomData.getString1025();

        // g) Name contains more than 10000 characters.
        String g = RandomData.getString10001();

        // h) Name contains only special characters:
        String h = "_~!@#$%^&*()+{}[]?.,|-/";

        // i) Name contains only numbers:
        String i = RandomData.getOnlyNumbers();

        return new Object[][]{{a},
                {b}, {c}, {d}, {e}, {f}, {g}, {h}, {i}};
    }

    @Test(dataProvider = "validNameTestData")
    public void positiveProjectNameTest(String val) {

        // Create project by superUser
        var project = checkedWithSuperUser.getProjectRequest()
                .create(NewProjectDescription.builder()
                        .parentProject(Project.builder()
                                .locator("_Root")
                                .build())
                        .name(val)
                        .id(RandomData.getValidID())
                        .copyAllAssociatedSettings(true)
                        .build());

        // Check project is created
        softy.assertThat(project.getName()).isEqualTo(val);

        // Delete created project by superUser
        new UncheckedProject(Specifications.getSpec().superUserSpec()).delete(project.getId());
    }


}