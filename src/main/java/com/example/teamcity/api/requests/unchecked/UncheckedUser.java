package com.example.teamcity.api.requests.unchecked;

import com.example.teamcity.api.requests.CrudInterface;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class UncheckedUser implements CrudInterface {
    private final static String USER_ENDPOINT = "/app/rest/users";
    private final RequestSpecification spec;

    public UncheckedUser(RequestSpecification spec) {
        this.spec = spec; 
    }
    @Override
    public Response create(Object obj) {
        return given()
                .spec(spec)
                .body(obj)
                .post(USER_ENDPOINT);
    }

    @Override
    public Object get(String id) {
        return null;
    }

    @Override
    public Object update(Object obj) {
        return null;
    }

    @Override
    public Response delete(String id) {
        return given().spec(spec)
                .delete(USER_ENDPOINT + "/username:" + id);
    }
}
