package com.example.teamcity.api.requests.unchecked;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public final class UncheckedProjects {
    private static final String PROJECT_ENDPOINT = "/app/rest/projects";
    private final RequestSpecification spec;

    public UncheckedProjects(RequestSpecification spec) {
        this.spec = spec;
    }

    public Response get() {
        return given()
                .spec(spec)
                .get(PROJECT_ENDPOINT);
    }
}
