package com.example.teamcity.api.requests.checked;

import com.example.teamcity.api.models.Projects;
import com.example.teamcity.api.requests.Request;
import com.example.teamcity.api.requests.unchecked.UncheckedProjects;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

public class CheckedProjects extends Request {

    public CheckedProjects(RequestSpecification spec) {
        super(spec);
    }

    public Projects getProjects() {
        return new UncheckedProjects(spec)
                .get()
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(Projects.class);
    }
}
