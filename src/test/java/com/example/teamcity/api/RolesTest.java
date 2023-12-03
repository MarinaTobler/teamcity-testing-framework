package com.example.teamcity.api;

import com.example.teamcity.api.enums.Role;
import com.example.teamcity.api.generators.TestDataGenerator;
import com.example.teamcity.api.requests.UncheckedRequests;
import com.example.teamcity.api.requests.checked.CheckedBuildConfig;
import com.example.teamcity.api.requests.checked.CheckedProject;
import com.example.teamcity.api.requests.unchecked.UncheckedBuildConfig;
import com.example.teamcity.api.spec.Specifications;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

public class RolesTest extends BaseApiTest {

    @Test
    public void unauthorizedUserShouldNotHaveRightToCreateProject() {

        // 1. Create project by unauth user
        // 2. Check error is "Auth required" (401)
        // 3. Check project is not created (Login with auth user, check project not found (404))


        var testData = testDataStorage.addTestData();

//        new UncheckedProject(Specifications.getSpec().unauthSpec())
//        uncheckedWithSuperUser.getProjectRequest()
                new UncheckedRequests(Specifications.getSpec().unauthSpec()).getProjectRequest()
                .create(testData.getProject())
                .then().assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED)
//                .body(Matchers.containsString("Authentication required"));
                .body(Matchers.equalTo("Authentication required\nTo login manually go to \"/login.html\" page"));

//        new UncheckedProject(Specifications.getSpec().superUserSpec())
//        new UncheckedProject(Specifications.getSpec().authSpec(testData.getUser()))
        uncheckedWithSuperUser.getProjectRequest()
                .get(testData.getProject().getId())
                .then().assertThat().statusCode(HttpStatus.SC_NOT_FOUND)
                .body(Matchers.containsString("No project found by locator" +
                        " 'count:1,id:" + testData.getProject().getId() + "'"));
    }

    @Test
    public void systemAdminShouldHaveRightsToCreateProject() {

        // 1. Create user SYSTEM_ADMIN (login as SYSTEM_ADMIN)
        // 2. Create project by SYSTEM_ADMIN
        // 3. Check project is created


        var testData = testDataStorage.addTestData();

        testData.getUser().setRoles(TestDataGenerator.generateRoles(Role.SYSTEM_ADMIN, "g"));

//        new CheckedUser(Specifications.getSpec().superUserSpec())
        checkedWithSuperUser.getUserRequest()
                .create(testData.getUser());

        var project = new CheckedProject(Specifications.getSpec()
                .authSpec(testData.getUser()))
                .create(testData.getProject());

        softy.assertThat(project.getId()).isEqualTo(testData.getProject().getId());
    }

    @Test
    public void projectAdminShouldHaveRightsToCreateBuildConfigToHisProject() {

        // Test scenario 3. Positive case
        // 1. Create user PROJECT_ADMIN (login as PROJECT_ADMIN)
        // 2. Create build config by PROJECT_ADMIN
        // 3. Check build config is created

        // Test scenario 3. Positive case  VAR:2
        // 1. Create project for user with Admin rights (SYSTEM_ADMIN or SuperUser)
        // 2. Assign PROJECT_ADMIN for that project
        // 3. Create build config by PROJECT_ADMIN
        // 4. Check build config is created


        var testData = testDataStorage.addTestData();

//        new CheckedProject(Specifications.getSpec().superUserSpec())
//                .create(testData.getProject());
        checkedWithSuperUser.getProjectRequest()
                .create(testData.getProject());

        testData.getUser().setRoles(TestDataGenerator.generateRoles(Role.PROJECT_ADMIN, "p:" + testData.getProject().getId()));

//        new CheckedUser(Specifications.getSpec().superUserSpec())
//                .create(testData.getUser());
        checkedWithSuperUser.getUserRequest()
                .create(testData.getUser());

//        new CheckedProject(Specifications.getSpec().authSpec(testData.getUser()))
//                .create(testData.getProject());

        var buildConfig = new CheckedBuildConfig(Specifications.getSpec().authSpec(testData.getUser()))
                .create(testData.getBuildType());

        softy.assertThat(buildConfig.getId()).isEqualTo(testData.getBuildType().getId());
    }

    @Test
    public void projectAdminShouldNotHaveRightsToCreateBuildConfigToAnotherProject() {

        // Test scenario 3. Negative case
        // 1. First User creates project, creates Config for himself
        // 2. Second User creates project
        // 3. Second User creates Config to First User's project (update/change First User's Config)
        // 4. Check build config is NOT created

        // Test scenario 3. Negative case VAR:2
        // 1. Create project for first user with Admin rights (SYSTEM_ADMIN or SuperUser)
        // 2. Create project for second user with Admin rights (SYSTEM_ADMIN or SuperUser)
        // 3. Assign PROJECT_ADMIN for first project / Create first user with SuperAdmin's help
        // 4. Assign PROJECT_ADMIN for second project / Create second user with SuperAdmin's help
        // 5. Second User creates Config to First User's project (update/change First User's Config)
        // 6. Check build config is NOT created


        var firstTestData = testDataStorage.addTestData();
        var secondTestData = testDataStorage.addTestData();

//        var firstUserRequest = new CheckedRequests(Specifications.getSpec().authSpec(firstTestData.getUser()));
//        var secondUserRequest = new CheckedRequests(Specifications.getSpec().authSpec(secondTestData.getUser()));

        //        new CheckedUser(Specifications.getSpec().superUserSpec())
//        checkedWithSuperUser.getUserRequest()
//                .create(firstTestData.getUser());
        // 1.:
        checkedWithSuperUser.getProjectRequest().create(firstTestData.getProject());
        // 2.:
        checkedWithSuperUser.getProjectRequest().create(secondTestData.getProject());

        // 3.:
        firstTestData.getUser().setRoles(TestDataGenerator
                .generateRoles(Role.PROJECT_ADMIN, "p:" + firstTestData.getProject().getId()));
        // new CheckedUser(Specifications.getSpec().superUserSpec())
        checkedWithSuperUser.getUserRequest().create(firstTestData.getUser());

////        new CheckedProject(Specifications.getSpec().authSpec(firstTestData.getUser()))
//        firstUserRequest.getProjectRequest().create(firstTestData.getProject());

        // 4.:
        secondTestData.getUser().setRoles(TestDataGenerator
                .generateRoles(Role.PROJECT_ADMIN, "p:" + secondTestData.getProject().getId()));
//        new CheckedUser(Specifications.getSpec().superUserSpec())
        checkedWithSuperUser.getUserRequest().create(secondTestData.getUser());

        // 5.:
//softy.assertThat(buildConfig.getId()).isEqualTo(testData.getBuildType().getId());

//        new CheckedProject(Specifications.getSpec()
//                .authSpec(secondTestData.getUser()))
//                .create(secondTestData.getProject());

//        var buildConfig = new CheckedBuildConfig(Specifications.getSpec().authSpec(firstTestData.getUser()))
//        var buildConfig = secondUserRequest.getBuildConfigRequest()
//                .create(firstTestData.getBuildType());


        new UncheckedBuildConfig(Specifications.getSpec().authSpec(secondTestData.getUser()))
                .create(firstTestData.getBuildType())
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST);
    }
}
