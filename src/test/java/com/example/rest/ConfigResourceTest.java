package com.example.rest;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class ConfigResourceTest {

    @Test
    public void testVersionEndpoint() {
        given()
          .when().get("/config/version")
          .then()
             .statusCode(200)
             .contentType(ContentType.JSON)
             .body("version", is("1.0.0-SNAPSHOT"))
             .body("artifact_id", is("keystore-demo"))
             .body("group_id", is("at.gattma"))
             .body("build_time", notNullValue());
    }

    @Test
    public void testVersionEndpointContentType() {
        given()
          .when().get("/config/version")
          .then()
             .statusCode(200)
             .contentType(ContentType.JSON);
    }
}
