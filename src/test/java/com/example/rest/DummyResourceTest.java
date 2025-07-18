import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class DummyResourceTest {

    @Test
    public void testHelloEndpoint() {
        RestAssured.given()
                .when().get("/dummy")
                .then()
                .statusCode(200)
                .contentType(ContentType.TEXT)
                .body(is("Hello, Dummy!"));
    }
}