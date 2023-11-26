package com.example.teamcity.api;

import com.example.teamcity.api.requests.checked.CheckedProject;
import com.example.teamcity.api.requests.checked.CheckedUser;
import com.example.teamcity.api.spec.Specifications;
import org.testng.annotations.Test;

public class BuildConfigurationTest extends BaseApiTest {
    @Test
    public void buildConfigurationTest() {

// 1. Create project by auth user with generated data.
// 2. Check created project has generated id.


//        var token = RestAssured.get("http://admin:admin@192.168.1.161:8111/authenticationTest.html?csrf")
//        var token = new AuthRequest(user).getCsrfToken();
//        System.out.println(token);

//        var user = User.builder()
//                .username("admin")
//                .password("admin")
//                .build();
//
//        var projectDescription = NewProjectDescription
//                .builder()
//                .parentProject(Project.builder()
//                        .locator("_Root")
//                        .build())
//                .name("name1")
//                .id("id1")
//                .copyAllAssociatedSettings(true)
//                .build();

//        var testData = new TestDataGenerator().generate();

        new CheckedUser(Specifications.getSpec().superUserSpec())
                .create(testData.getUser());

        var project = new CheckedProject(Specifications.getSpec()
                .authSpec(testData.getUser()))
                .create(testData.getProject());

        softy.assertThat(project.getId()).isEqualTo(testData.getProject().getId());
    }
}
