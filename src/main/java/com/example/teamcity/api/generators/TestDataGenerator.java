package com.example.teamcity.api.generators;

import com.example.teamcity.api.models.*;

import java.util.Arrays;

public class TestDataGenerator {
    public TestData generate() {
        var user = User.builder()
                .username(RandomData.getString())
                .password(RandomData.getString())
                .email(RandomData.getString() + "@gmail.com")
                .roles(Roles.builder()
                        .role(Arrays.asList(Role.builder()
                                        .roleId("SYSTEM_ADMIN")
                                        .scope("g")
                                .build()))
                        .build())
                .build();

        var project = NewProjectDescription
                .builder()
                .parentProject(Project.builder()
                        .locator("_Root")
                        .build())
                .name(RandomData.getString())
                .id(RandomData.getString())
                .copyAllAssociatedSettings(true)
                .build();

        return TestData.builder()
                .user(user)
                .project(project)
                .build();

    }
}
