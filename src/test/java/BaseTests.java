import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class BaseTests {

    @Test
    void simpleGetTest () {
        var response = given().baseUri("https://superhero.qa-test.csssr.com").contentType(ContentType.JSON)
                .when().get("/superheroes")
                .then().statusCode(200).extract().response();
        System.out.println("Response: " + response.asPrettyString());
    }

    @Test
    void simplePostTest() {
        var requestBody = "{" +
        "\"birthDate\": \"2000-03-15\"," +
        "\"city\": \"New York\"," +
        "\"fullName\": \"Blade\"," +
        "\"gender\": \"M\"," +
        "\"id\": \"571\"," +
        "\"mainSkill\": \"Martial Arts\"," +
        "\"phone\": \"+9959550431\"" +
        "}";

        RestAssured.given().baseUri("https://superhero.qa-test.csssr.com").contentType(ContentType.JSON)
                .body(requestBody)
                .when().post("/superheroes")
                .then().statusCode(200);
    }
}
