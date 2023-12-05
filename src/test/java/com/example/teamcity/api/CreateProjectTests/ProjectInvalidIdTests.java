package com.example.teamcity.api.CreateProjectTests;

import com.example.teamcity.api.BaseApiTest;
import com.example.teamcity.api.generators.RandomData;
import com.example.teamcity.api.models.NewProjectDescription;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.requests.unchecked.UncheckedProject;
import com.example.teamcity.api.spec.Specifications;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ProjectInvalidIdTests extends BaseApiTest {

    @DataProvider
    public static Object[][] invalidIdTestData() {
        // Equivalence Partitioning:
        // 2. Invalid classes:

        // a) ID contains any characters.
        // For example:
        //String a = "ёщъ/*Ü!#¤%&@9";
        String a = RandomData.getRandomString();

        // b) ID starts with non latin letter.
        // For example:
        String b = "ё_abc123";

        // c) ID starts with digit.
        // For example:
        String c = "8_abc123";

        // d) ID starts with symbol.
        // For example:
        String d = "%_abc123";

        // e) ID starts with a latin letter, but contains non latin letter.
        // For example:
        String e = "t_ЁЩЖ123";

        // f) ID starts with a latin letter and contains only latin letters, digits and underscores (MORE than 225 characters).
        // For example:
        String f = RandomData.getString226();

        // g) ID contains spaces.
        // For example:
        String g = "t abc 123";

        // h) ID field is empty.
        // For example:
        String h = "";

        // Boundary Value Analysis:
        // i) ID contains 225 characters.
        String i = RandomData.getString226();

        // Error Guessing:
        // j) ID starts with latin letter and contains special characters:
        String j = "t_~!@#$%^&*()+{}[]?.,|-/";

        return new Object[][]{{a},
                {b}, {c}, {d}, {e}, {f}, {g}, {h}, {i}, {j}
        };
    }

    @Test(dataProvider = "invalidIdTestData")
    public void NegativeProjectIdTest(String val) {

        // Create project with unchecked request by superUser
        uncheckedWithSuperUser.getProjectRequest()
                .create(NewProjectDescription.builder()
                        .parentProject(Project.builder()
                                .locator("_Root")
                                .build())
                        .name(RandomData.getValidName())
                        .id(val)
                        .copyAllAssociatedSettings(true)
                        .build())
               .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
//                NB! for all cases:
//              .then().assertThat().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .body(Matchers.containsString("ID should start with a latin letter and contain only latin " +
                        "letters, digits and underscores (at most 225 characters)."));
        //      NB! for case g) -> Response body doesn't match expectation: Project ID must not be empty.


        // Check project not found and response message
        new UncheckedProject(Specifications.getSpec().superUserSpec()).get(val)
//                NB! for case a) ->  status code: 406 (Not Acceptable). Make sure you have supplied correct 'Accept' header:
//                .then().assertThat().statusCode(HttpStatus.SC_NOT_ACCEPTABLE)
                .then().assertThat().statusCode(HttpStatus.SC_NOT_FOUND)
                .body(Matchers.containsString("No project found by locator" +
                        " 'count:1,id:" + val + "'"));

        // Delete project ? if status code was 200 (was created)
        new UncheckedProject(Specifications.getSpec().superUserSpec()).delete(val);
    }
}

