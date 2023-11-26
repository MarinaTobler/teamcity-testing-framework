package com.example.teamcity.api;

import com.example.teamcity.api.models.Role;
import com.example.teamcity.api.models.Roles;
import com.example.teamcity.api.requests.checked.CheckedProject;
import com.example.teamcity.api.requests.checked.CheckedUser;
import com.example.teamcity.api.requests.unchecked.UncheckedProject;
import com.example.teamcity.api.spec.Specifications;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.Arrays;

public class RolesTest extends BaseApiTest {

    @Test
    public void unauthorizedUser() {
        // 1. Create project by unauth user
        // 2. Check error is "Auth required" (401)
        // 3. Check project is not created (Login with auth user, check project not found (404))

        new UncheckedProject(Specifications.getSpec().unauthSpec())
                .create(testData.getProject())
                .then().assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED)
//                .body(Matchers.containsString("Authentication required"));
                .body(Matchers.equalTo("Authentication required\nTo login manually go to \"/login.html\" page"));

//        testData.getUser()
//                .setRoles(Roles.builder()
//                        .role(Arrays.asList(Role.builder()
//                                .roleId("SYSTEM_ADMIN")
//                                .scope("g")
//                                .build()))
//                        .build());
//
//        new CheckedUser(Specifications.getSpec().superUserSpec())
//                .create(testData.getUser());

//        new UncheckedProject(Specifications.getSpec().authSpec(testData.getUser()))
        new UncheckedProject(Specifications.getSpec().superUserSpec())
                .get(testData.getProject().getId())
                .then().assertThat().statusCode(HttpStatus.SC_NOT_FOUND)
                .body(Matchers.containsString("No project found by locator" +
                        " 'count:1,id:" + testData.getProject().getId() + "'"));
    }

    @Test
    public void unauthorizedUserShouldNotHaveRightToCreateProject() {
        // 1. Create project by unauth user
        // 2. Check error is "Auth required" (401)
        // 3. Check project is not created (Login with auth user, check project not found (404))

        new UncheckedProject(Specifications.getSpec().unauthSpec())
                .create(testData.getProject())
                .then().assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED)
//                .body(Matchers.containsString("Authentication required"));
                .body(Matchers.equalTo("Authentication required\nTo login manually go to \"/login.html\" page"));

        new UncheckedProject(Specifications.getSpec().superUserSpec())
                .get(testData.getProject().getId())
                .then().assertThat().statusCode(HttpStatus.SC_NOT_FOUND)
                .body(Matchers.containsString("No project found by locator" +
                        " 'count:1,id:" + testData.getProject().getId() + "'"));
    }

    @Test
    //    public void systemAdminTest() {
    public void systemAdminShouldHaveRightsToCreateProject() {
        // 1. Create user SYSTEM_ADMIN (login as SYSTEM_ADMIN)
        // 2. Create project by SYSTEM_ADMIN
        // 3. Check project is created

        testData.getUser()
                .setRoles(Roles.builder()
                        .role(Arrays.asList(Role.builder()
                                .roleId("SYSTEM_ADMIN")
                                .scope("g")
                                .build()))
                        .build());

        new CheckedUser(Specifications.getSpec().superUserSpec())
                .create(testData.getUser());

        var project = new CheckedProject(Specifications.getSpec()
                .authSpec(testData.getUser()))
                .create(testData.getProject());

        softy.assertThat(project.getId()).isEqualTo(testData.getProject().getId());
    }
}
